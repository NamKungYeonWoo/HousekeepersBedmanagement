package com.example.admin.mountalverniacleaningteam;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by admin on 11/26/2017.
 */

public class FcmMessagingService extends FirebaseMessagingService {

    Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        String title = remoteMessage.getNotification().getTitle();
        String message = remoteMessage.getNotification().getBody();

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder.setContentTitle(title);
        notificationBuilder.setSound(defaultSoundUri);
//        notificationBuilder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });
        notificationBuilder.setContentText(message);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
        notificationBuilder.setContentIntent(pendingIntent);
        NotificationManager notificationManger = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManger.notify(0, notificationBuilder.build());
    }
}
