package com.github.offby1.myfirstapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.ShareActionProvider;
import android.widget.TextView;

public class MainActivity extends Activity {
    public final static String KEY = "com.github.offby1.myfirstapp.MESSAGE";

    SharedPreferences sharedPref;
    EditText editText;
    private ShareActionProvider mShareActionProvider;
    private Intent sendIntent;

    private void syncFromEditText () {
        String   message  = editText.getText().toString();

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(KEY, message);
        editor.commit();

        if (sendIntent != null) {
            // Update what we'll share.
            sendIntent.putExtra(Intent.EXTRA_TEXT, message);
        }

        // Update the big text view.
        TextView tv = (TextView)findViewById(R.id.TextView1);
        tv.setText (message);

        // TODO -- change the text size to be as large as possible,
        // while still showing the entire message.
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        String message = sharedPref.getString(KEY, "");
        editText = (EditText) findViewById(R.id.edit_message);
        editText.setText(message);

        syncFromEditText ();

        editText.setOnKeyListener(new OnKeyListener() {

                public boolean onKey(View v, int keyCode, KeyEvent event) {

                    // Save the text on every keystroke!
                    if (event.getAction() == KeyEvent.ACTION_UP) {
                        syncFromEditText ();
                    }

                    return false;
                }
            });
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);

        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.menu_item_share);

        // Fetch and store ShareActionProvider
        mShareActionProvider = (ShareActionProvider) item.getActionProvider();
        sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        mShareActionProvider.setShareIntent(sendIntent);

        return true;
    }
}
