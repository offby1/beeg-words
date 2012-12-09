package com.github.offby1.beegwords;

import com.github.offby1.beegwords.R;

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
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MainActivity extends Activity {
    public final static String KEY = "com.github.offby1.beegwords.MESSAGE";

    SharedPreferences sharedPref;
    EditText editText;
    MainActivity mainActivity;
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
        FunkyTextView tv = (FunkyTextView)findViewById(R.id.TextView1);
        tv.setText (message);
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

        String message = sharedPref.getString(KEY, "");
        editText = (EditText) findViewById(R.id.edit_message);
        editText.setText(message);

        syncFromEditText ();

        FunkyTextView funkyText = (FunkyTextView) findViewById (R.id.TextView1);
        funkyText.setOnLongClickListener (new OnLongClickListener () {
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
                    Toast.makeText(mainActivity, String.format ("actionId is %d", actionId), Toast.LENGTH_SHORT).show();
                    boolean handled = false;
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        syncFromEditText ();
                        FunkyTextView funkyText = (FunkyTextView) findViewById (R.id.TextView1);
                        funkyText.invalidate ();
                        handled = true;
                    }
                    return handled;
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
