package com.github.offby1.myfirstapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {
    public final static String EXTRA_MESSAGE = "com.github.offby1.myfirstapp.MESSAGE";

    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        String message = sharedPref.getString("message", "");
        EditText editText = (EditText) findViewById(R.id.edit_message);
        editText.setText(message);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    /** Called when the user clicks the Send button */
    public void sendMessage(View view) {
        Intent   intent   = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String   message  = editText.getText().toString();

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("message", message);
        editor.commit();

        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
