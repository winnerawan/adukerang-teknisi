package co.ipb.adukerang.teknisi;

/**
 * Created by winnerawan on 3/29/16.
 */
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;

import co.ipb.adukerang.R;
import co.ipb.adukerang.activity.DashboardActivity;
import co.ipb.adukerang.activity.NotifActivity;
import co.ipb.adukerang.controller.AppConfig;
import co.ipb.adukerang.gcm.CommonUtilities;

import static co.ipb.adukerang.gcm.CommonUtilities.displayMessage;

public class GCMIntentService extends GCMBaseIntentService {

    private static final String TAG = "GCMIntentService";
    public static final int NOTIFICATION_ID = 1;
    Context context;

    public GCMIntentService() {
        super(AppConfig.SENDER_ID);
    }

    /**
     * Method called on device registered
     **/
    @Override
    protected void onRegistered(Context context, String registrationId) {
        Log.i(TAG, "Device registered: regId = " + registrationId);
        CommonUtilities.displayMessage(context, "Your device registred with GCM");

        //Log.d("NAME", DashboardActivity.gname);
        //ServerUtilities.register(context, DashboardActivity.gname, DashboardActivity.gemail, registrationId);
    }

    /**
     * Method called on device un registred
     * */
    @Override
    protected void onUnregistered(Context context, String registrationId) {
        Log.i(TAG, "Device unregistered");
        CommonUtilities.displayMessage(context, getString(R.string.gcm_unregistered));
        //ServerUtilities.unregister(context, registrationId);
    }

    /**
     * Method called on Receiving a new message
     * */
    @Override
    protected void onMessage(Context context, Intent intent) {
        Log.i(TAG, "Received message");
        String message = intent.getExtras().getString("message");
        String barang = intent.getExtras().getString("barang");
        CommonUtilities.displayMessage(context, message);
        // notifies user
        generateNotification(context, message);
    }

    /**
     * Method called on receiving a deleted message
     * */
    @Override
    protected void onDeletedMessages(Context context, int total) {
        Log.i(TAG, "Received deleted messages notification");
        String message = getString(R.string.gcm_deleted, total);
        CommonUtilities.displayMessage(context, message);
        // notifies user
        generateNotification(context, message);
    }

    /**
     * Method called on Error
     * */
    @Override
    public void onError(Context context, String errorId) {
        Log.i(TAG, "Received error: " + errorId);
        CommonUtilities.displayMessage(context, getString(R.string.gcm_error, errorId));
    }

    @Override
    protected boolean onRecoverableError(Context context, String errorId) {
        // log message
        Log.i(TAG, "Received recoverable error: " + errorId);
        CommonUtilities.displayMessage(context, getString(R.string.gcm_recoverable_error,
                errorId));
        return super.onRecoverableError(context, errorId);
    }

    /**
     * Issues a notification to inform the user that server has sent a message.
     */
    private void generateNotification(Context context, String message) {
        int icon = R.drawable.iconadukerang;
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        //Notification notification = new Notification(icon, message, when);

        String title = context.getString(R.string.app_name);

        Intent notificationIntent = new Intent(context, NotifActivity.class);
        notificationIntent.putExtra("message", message);
        //notificationIntent.putExtra()
        // set intent so it does not start a new activity
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent =
                PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        final Uri notif = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                +context.getPackageName()+"/raw/notification");

        NotificationCompat.Builder notificationCompat = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.iconadukerang)
                .setContentTitle("Adu Kerang")
                .setContentText(message)
                .setSound(notif)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message));

        //notificationCompat.setAutoCancel(true);
        notificationCompat.setContentIntent(intent);

        notificationCompat.setDefaults(Notification.DEFAULT_SOUND);
        notificationCompat.setDefaults(Notification.DEFAULT_VIBRATE);

        notificationManager.notify(NOTIFICATION_ID, notificationCompat.build());


        /*

        DEPRECATED
        notification.setLatestEventInfo(context, title, message, intent);
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        // Play default notification sound
        notification.defaults |= Notification.DEFAULT_SOUND;

        // Vibrate if vibrate is enabled
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notificationManager.notify(0, notification);
 */
    }
    //@Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Message: " + message);
        // Handle received message here.
    }

}