package com.upc.movilmarket;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessageReceiver extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size()>0){

            mostrarnotificacion(remoteMessage.getData().get("title"), remoteMessage.getData().get("message"));
        }if (remoteMessage.getNotification() != null){
            mostrarnotificacion(remoteMessage.getNotification().getTitle(),
                    remoteMessage.getNotification().getBody());
        }

    }

    private RemoteViews costumNotificacion(String titulo, String mensaje){

        RemoteViews objremoteViews = new RemoteViews(getApplicationContext().getPackageName(),R.layout.notificacion);
        objremoteViews .setTextViewText(R.id.titulo_noti,titulo);
        objremoteViews.setTextViewText(R.id.txt_noti,mensaje);
        objremoteViews.setImageViewResource(R.id.imgnoti, R.drawable.logo);
        return  objremoteViews;
    }

    private void mostrarnotificacion(String titulo, String mensaje){

        Intent intent   = new Intent(this,InicioActivity.class);
        String channel_id = "web_app_channel";
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
        Uri uri = RingtoneManager.getDefaultUri((RingtoneManager.TYPE_NOTIFICATION));
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channel_id)
                .setSmallIcon(R.drawable.common_full_open_on_phone)
                .setSound(uri)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){

            builder = builder.setContent(costumNotificacion(titulo, mensaje));

        }else {
            builder = builder.setContentTitle(titulo)
                    .setContentText(mensaje)
                    .setSmallIcon(R.drawable.common_full_open_on_phone);
        }

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {

            NotificationChannel notificationChannel = new NotificationChannel(channel_id, "web_app", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setSound(uri, null);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        notificationManager.notify(0,builder.build());




        }

    }

