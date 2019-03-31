package samsoya.cameroonapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

public class NotificationsRunner {
    private static NotificationsRunner runner;
    private final String CHANNEL_ID = "default";
    private boolean notificationChannelCreated = false;


    public static NotificationsRunner getInstance() {
        if (runner == null) {
            runner = new NotificationsRunner();
        }

        return runner;
    }

    private void onCreate() {
        runner = this;
    }

    public void postSuccessNotification(Context context) {
        NotificationCompat.Builder notificationBuilder
                = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_play_arrow_black_36dp)
                .setContentTitle("Game name")
                .setContentText("Your high score has been uploaded")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);
        if (!notificationChannelCreated) {
            createNotificationChannel(context);
        }

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(0, notificationBuilder.build());
    }

    private void createNotificationChannel(Context context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getString(R.string.channel_name);
            String description = context.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
