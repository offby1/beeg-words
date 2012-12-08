package com.github.offby1.beegwords;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
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
        adjustTextHorizontalStretch ();
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

        // The prevents some letters from vanishing; it appears to
        // work around a bug in Android.
        // http://stackoverflow.com/questions/6253528/font-size-too-large-to-fit-in-cache
        this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
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

        // get the height that would have been produced
        int h = bounds.bottom - bounds.top;

        // make the text take up 70% of the height
        float target = (float) mViewHeight * .7f;

        // figure out what textSize setting would create that height
        // of text
        float size = ((target / h) * 100f);

        // and set it into the paint
        mTextPaint.setTextSize(size);
    }

    void adjustTextHorizontalStretch () {
        mTextPaint.setTextScaleX(1.0f);
        Rect bounds = new Rect();
        mTextPaint.getTextBounds(mText, 0, mText.length(), bounds);

        int w         = bounds.right  - bounds.left;
        int text_h    = bounds.bottom - bounds.top;
        mTextBaseline = bounds.bottom + ((mViewHeight - text_h) / 2);

        float xscale = ((float) (mViewWidth - getPaddingLeft() - getPaddingRight()))
            / w;

        mTextPaint.setTextScaleX(xscale);
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
        adjustTextHorizontalStretch ();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // draw the text
        // position is centered on width
        // and the baseline is calculated to be positioned from the
        // view bottom
        canvas.drawText(mText, mViewWidth / 2, mViewHeight - mTextBaseline,
                        mTextPaint);

    }
}
