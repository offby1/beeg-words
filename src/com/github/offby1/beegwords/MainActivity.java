package com.github.offby1.beegwords;

import com.github.offby1.beegwords.MyEditText;
import com.github.offby1.beegwords.R;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
//import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MainActivity extends Activity {
    public final static String KEY = "com.github.offby1.beegwords.MESSAGE";

    MyEditText editText;
    MainActivity mainActivity;

    private SharedPreferences sharedPref;
    public static void updateBeegWords (CharSequence charSequence, FunkyTextView tv) {
        tv.setText (charSequence.toString());
        tv.invalidate ();
    }

    public void updateBeegWords (CharSequence charSequence) {
        // Update the big text view.
        FunkyTextView tv = (FunkyTextView)findViewById(R.id.TextView1);
        updateBeegWords (charSequence, tv);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);

        mainActivity = this;

        // Go to full-screen mode.
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        String savedText = sharedPref.getString(KEY, "");
        updateBeegWords(savedText);

        FunkyTextView funkyText = (FunkyTextView) findViewById (R.id.TextView1);
        editText                = (MyEditText) findViewById(R.id.edit_message);

        editText.setUpdateTarget (funkyText);
        editText.setText (savedText);

        funkyText.setOnLongClickListener (new OnLongClickListener () {
                @Override
                public boolean onLongClick (View v) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, ((FunkyTextView) v).getText ());
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);

                    return true;
                }
            });

        editText.setOnEditorActionListener(new OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    //Toast.makeText(mainActivity, String.format ("actionId is %d", actionId), Toast.LENGTH_SHORT).show();
                    boolean handled = false;

                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        updateBeegWords (v.getText ());

                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString(KEY, v.getText().toString());
                        editor.commit();

                        InputMethodManager inputManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);

                        handled = true;
                    }
                    return handled;
                }
            });

        Button button = (Button)this.findViewById(R.id.button1);
        button.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    editText.setText("");
                }

            });
    }
}
