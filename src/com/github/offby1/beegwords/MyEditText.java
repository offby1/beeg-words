/**
 * Like the parent class, but updates the given TextView in its
 * onKeyUp method.
 */
package com.github.offby1.beegwords;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @author erich
 *
 */
public class MyEditText extends EditText {
    public MyEditText(Context context) {
        super(context);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super (context, attrs);
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyle) {
        super (context, attrs, defStyle);
    }

    // We invoke updateBeegWords on this on every keystroke.  Ideally
    // this would simply be an anonymous function that we invoke, but
    // I don't think Java has anonymous functions :-|
    FunkyTextView updateTarget;

    public void setUpdateTarget (FunkyTextView funkyText) {
        updateTarget = funkyText;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        MainActivity.updateBeegWords (updateTarget.getText (), updateTarget);
        return false;
    }
}
