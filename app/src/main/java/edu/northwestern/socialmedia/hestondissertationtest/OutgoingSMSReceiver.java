package edu.northwestern.socialmedia.hestondissertationtest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;

public class OutgoingSMSReceiver extends Service {
    public OutgoingSMSReceiver() {
    }

    class smsObserver extends ContentObserver {

        private String lastSmsId;
        Context context;

        public smsObserver(Context context) {
            super(null);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            Uri uriSMSURI = Uri.parse("content://sms/sent");
            Cursor cur = getContentResolver().query(uriSMSURI, null, null, null, null);
            cur.moveToNext();
            String id = cur.getString(cur.getColumnIndex("_id"));
            if (smsChecker(id)) {
                String address = cur.getString(cur.getColumnIndex("address"));
                String message = cur.getString(cur.getColumnIndex("body"));
                // Use message content for desired functionality

                Intent intent = new Intent(this.context, MainActivity.class);
                intent.putExtra("sent_message", message);
                PendingIntent pIntent = PendingIntent.getActivity(this.context, 0, intent, 0);

// build notification
// the addAction re-use the same intent to keep the example short
                Notification n  = new Notification.Builder(this.context)
                        .setContentTitle("New mail from " + "test@gmail.com")
                        .setContentText("Subject")
                        .setSmallIcon(R.mipmap.icon)
                        .setContentIntent(pIntent).build();


                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                notificationManager.notify(0, n);
            }
        }

        // Prevent duplicate results without overlooking legitimate duplicates
        public boolean smsChecker(String smsId) {
            boolean flagSMS = true;

            if (smsId.equals(lastSmsId)) {
                flagSMS = false;
            }
            else {
                lastSmsId = smsId;
            }

            return flagSMS;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public void onCreate() {
        smsObserver contentObserver = new smsObserver(getApplicationContext());
        ContentResolver contentResolver = getBaseContext().getContentResolver();
        contentResolver.registerContentObserver(Uri.parse("content://sms/"),true, contentObserver);
        //Log.v("Caller History: Service Started.", "OutgoingSMSReceiverService");
    }
}
