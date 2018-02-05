package edu.northwestern.socialmedia.hestondissertationtest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.arch.persistence.room.Room;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;

import java.util.Date;
import java.util.UUID;

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
            Uri uriSMSURI = Uri.parse("content://sms/");
            Cursor cur = getContentResolver().query(uriSMSURI, null, null, null, null);
            cur.moveToNext();
            String id = cur.getString(cur.getColumnIndex("_id"));
            int sentOrReceived = cur.getInt(cur.getColumnIndex("type"));
            String protocol = cur.getString(cur.getColumnIndex("protocol"));
            if ((sentOrReceived == 2) && (protocol == null) && (smsChecker(id))) {

                String address = cur.getString(cur.getColumnIndex("address"));
                String message = cur.getString(cur.getColumnIndex("body"));
                cur.close();


                //get sender info
                final String SMS_URI_INBOX = "content://sms/inbox";
                Uri uri = Uri.parse(SMS_URI_INBOX);
                String[] projection = new String[] { "_id", "address", "person", "body", "date", "type" };
                Cursor cur2 = getContentResolver().query(uri, projection, String.format("address = '%s'", address), null, "date desc");
                if (cur2.moveToFirst() ) {
                    String lastMessage = cur2.getString(cur2.getColumnIndex("body"));
                    Date lastReceived = new Date(cur2.getLong(cur2.getColumnIndex("date")));
                    cur2.close();
                    final String SMS_URI = "content://sms/";
                    Uri uri2 = Uri.parse(SMS_URI);
                    String[] projection2 = new String[] { "_id", "address", "person", "body", "date", "type" };
                    Cursor cur3 = getContentResolver().query(uri2, projection2, String.format("address = '%s'", address), null, "date desc");
                    if(cur3.moveToFirst()) {
                        cur3.moveToNext();
                        if (cur3.getInt(cur3.getColumnIndex(("type"))) == 1) {
                            long diff = new Date().getTime() - lastReceived.getTime();
                            long diffMinutes = diff / (60 * 1000);
                            if (diffMinutes > 30) {
                                notifyBaby(message, address);
                            }
                        cur3.close();
                        }
                    }
                }
            }
        }

        public void notifyBaby(String message, String address) {
            AppDatabase db = Database.getDb(getApplicationContext());
            Message savedMessage = new Message();
            savedMessage.setMessageText(message);
            savedMessage.setMessageFrom(address);
            long messageId = db.messageDao().insert(savedMessage);

            Intent intent = new Intent(this.context, MainActivity.class);
            intent.putExtra("message_id", messageId);
            PendingIntent pIntent = PendingIntent.getActivity(this.context, UUID.randomUUID().hashCode(), intent, 0);

// build notification
// the addAction re-use the same intent to keep the example short
            Notification n = new Notification.Builder(this.context)
                    .setContentTitle("Texting Study Survey")
                    .setContentText("Texting Study Survey")
                    .setSmallIcon(R.mipmap.icon)
                    .setContentIntent(pIntent)
                    .setAutoCancel(true).build();


            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            notificationManager.notify(0, n);


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

        Intent notificationIntent = new Intent(this, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        Notification n = new Notification.Builder(this)
                .setContentTitle("Texting Study Service")
                .setContentText("Texting Study Service")
                .setSmallIcon(R.mipmap.icon)
                .setContentIntent(pendingIntent).build();

        startForeground(1337, n);
    }
}
