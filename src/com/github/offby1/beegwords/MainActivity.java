package com.github.offby1.beegwords;

import com.github.offby1.beegwords.R;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MainActivity extends Activity {
    public final static String KEY = "com.github.offby1.beegwords.MESSAGE";

    EditText editText;
    MainActivity mainActivity;

    private SharedPreferences sharedPref;
    private void updateBeegWords (String s) {
        // Update the big text view.
        FunkyTextView tv = (FunkyTextView)findViewById(R.id.TextView1);
        tv.setText (s);
        tv.invalidate ();
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

        editText = (EditText) findViewById(R.id.edit_message);
        editText.setText (savedText);

        FunkyTextView funkyText = (FunkyTextView) findViewById (R.id.TextView1);
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

        editText.addTextChangedListener(new TextWatcher(){

                @Override
                public void afterTextChanged(Editable s) {

                    String text = s.toString ();

                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString(KEY, text);
                    editor.commit();

                    updateBeegWords(text);
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count,
                                              int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before,
                                          int count) {

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
