package com.github.offby1.myfirstapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;

public class MainActivity extends Activity {
    public final static String EXTRA_MESSAGE = "com.github.offby1.myfirstapp.MESSAGE";

    SharedPreferences sharedPref;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        String message = sharedPref.getString("message", "");
        editText = (EditText) findViewById(R.id.edit_message);
        editText.setText(message);

        editText.setOnKeyListener(new OnKeyListener() {

                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    // Save the text on every keystroke!

                    // TODO -- I suspect I'm saving everything twice
                    // -- once on keyDown, and again on keyUp.  Fix
                    // that.
                    EditText editText = (EditText) v;
                    String   message  = editText.getText().toString();

                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("message", message);
                    editor.commit();

                    return false;
                }
            });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
