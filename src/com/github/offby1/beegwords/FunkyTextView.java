package com.github.offby1.beegwords;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

public class FunkyTextView extends TextView {
    String mText = "Hey you";
    Paint mTextPaint;
    int mViewWidth;
    int mViewHeight;
    int mTextBaseline;

    public FunkyTextView(Context context) {
        super (context);
        init_text_paint ();
    }

    public FunkyTextView(Context context, AttributeSet attrs) {
        super (context, attrs);
        init_text_paint ();
    }

    public FunkyTextView(Context context, AttributeSet attrs, int defStyle) {
        super (context, attrs, defStyle);
        init_text_paint ();
    }

    private void init_text_paint() {
        mTextPaint = new TextPaint();
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setAntiAlias(true);
    }

    /**
     * When the view size is changed, recalculate the paint settings to have the
     * text on the fill the view area
     */
    @Override
    protected void onSizeChanged(int mViewWidth, int mViewHeight, int oldw, int oldh) {
        super.onSizeChanged(mViewWidth, mViewHeight, oldw, oldh);

        Rect bounds = new Rect();
        mTextPaint.getTextBounds(mText, 0, mText.length(), bounds);

        int text_h = bounds.bottom - bounds.top;
        mTextBaseline = bounds.bottom + ((mViewHeight - text_h) / 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // draw the text
        // position is centered on width
        // and the baseline is calculated to be positioned from the
        // view bottom
        canvas.drawText(mText, mViewWidth / 2, mViewHeight - mTextBaseline,
                        mTextPaint);

    }
}
