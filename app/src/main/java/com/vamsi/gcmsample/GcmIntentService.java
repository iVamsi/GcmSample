package com.vamsi.gcmsample;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GcmListenerService;

/**
 * Created by m1033421 on 04/03/16.
 */
public class GcmIntentService extends GcmListenerService {

    public static final String PUSH_MESSAGE = "push_message";

    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");

        Intent intent = new Intent(this, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, 0);

        NotificationManager notificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Push message")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setContentText(message);
        notificationManager.notify(1, mBuilder.build());


        SharedPreferences sharedPreferences = getSharedPreferences(PUSH_MESSAGE, MODE_PRIVATE);
        sharedPreferences.edit().putString("message", message).apply();

    }
}
