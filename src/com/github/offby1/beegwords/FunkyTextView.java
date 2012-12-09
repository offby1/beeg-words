package com.github.offby1.beegwords;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class FunkyTextView extends View {
    String mText = "Hey you";
    Paint mTextPaint;
    int mViewWidth;
    int mViewHeight;
    int mTextBaseline;

    public void setText (String message) {
        mText = message;

        adjustTextSize ();
    }

    public CharSequence getText () {
        return mText;
    }

    public FunkyTextView(Context context) {
        super (context);
        init ();
    }

    public FunkyTextView(Context context, AttributeSet attrs) {
        super (context, attrs);
        init ();
    }

    public FunkyTextView(Context context, AttributeSet attrs, int defStyle) {
        super (context, attrs, defStyle);
        init ();
    }

    private void init() {
        mTextPaint = new TextPaint();
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor (Color.WHITE);

        // The prevents some letters from vanishing; it appears to
        // work around a bug in Android.
        // http://stackoverflow.com/questions/6253528/font-size-too-large-to-fit-in-cache

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB){
            this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        this.setBackgroundColor(Color.BLACK);
    }

    void adjustTextSize() {
        if (mText.length() == 0) {
            return;
        }
        mTextPaint.setTextSize(100);
        mTextPaint.setTextScaleX(1.0f);
        Rect bounds = new Rect();

        // ask the paint for the bounding rect if it were to draw this
        // text
        mTextPaint.getTextBounds(mText, 0, mText.length(), bounds);

        // get the height and width that would have been produced
        int h = bounds.bottom - bounds.top;
        int w = bounds.right - bounds.left;

        // make the text take up 70% of the height or width, whichever is smaller

        float targetH = (float) mViewHeight * .7f;
        float targetW = (float) mViewWidth  * .9f;

        // figure out what textSize setting would create that height
        // or width of text
        float sizeH = ((targetH / h) * 100f);
        float sizeW = ((targetW / w) * 100f);

        // and set it into the paint
        mTextPaint.setTextSize(sizeH < sizeW ? sizeH : sizeW);
    }

    /**
     * When the view size is changed, recalculate the paint settings to have the
     * text on the fill the view area
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mViewWidth  = w;
        mViewHeight = h;

        adjustTextSize ();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawText(mText,
                        mViewWidth / 2,
                        mViewHeight / 2 ,
                        mTextPaint);

    }
}
