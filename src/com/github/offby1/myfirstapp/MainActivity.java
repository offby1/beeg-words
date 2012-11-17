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

public class MainActivity extends Activity {
    public final static String KEY = "com.github.offby1.myfirstapp.MESSAGE";

    SharedPreferences sharedPref;
    EditText editText;
    private ShareActionProvider mShareActionProvider;
    private Intent sendIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPref = this.getPreferences(Context.MODE_PRIVATE);

        String message = sharedPref.getString(KEY, "");
        editText = (EditText) findViewById(R.id.edit_message);
        editText.setText(message);

        editText.setOnKeyListener(new OnKeyListener() {

                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    // Save the text on every keystroke!

                    if (event.getAction() == KeyEvent.ACTION_UP) {
                        EditText editText = (EditText) v;
                        String   message  = editText.getText().toString();

                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString(KEY, message);
                        editor.commit();

                        // Update what we'll share, while we're at it.
                        sendIntent.putExtra(Intent.EXTRA_TEXT, message);
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

        sendIntent.putExtra(Intent.EXTRA_TEXT,
                            ((EditText) findViewById(R.id.edit_message)).getText().toString());

        return true;
    }
}
