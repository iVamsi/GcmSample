package com.vamsi.gcmsample;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    TextView gcm_token_label, push_msg_label;
    EditText gcm_token, edt_push_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gcm_token_label = (TextView) findViewById(R.id.textView);
        push_msg_label = (TextView) findViewById(R.id.textView2);
        gcm_token = (EditText) findViewById(R.id.editText);
        edt_push_message = (EditText) findViewById(R.id.editText1);

        new DownloadTask().execute();

    }

    private class DownloadTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {

            InstanceID instanceID = InstanceID.getInstance(MainActivity.this);
            String token = null;
            try {
                token = instanceID.getToken(getString(R.string.gcm_sender_id), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (token != null) {
                //ToDo: send token to app server
            }

            return token;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.i("GCM_TOKEN", s);
            gcm_token.setText(s);
            SharedPreferences sharedPreferences = getSharedPreferences(GcmIntentService.PUSH_MESSAGE, MODE_PRIVATE);
            String push_message = sharedPreferences.getString("message", "");
            edt_push_message.setText(push_message);
        }
    }
}
