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

public class OutgoingSMSReceiver extends Service {
    public OutgoingSMSReceiver() {
    }

    class smsObserver extends ContentObserver {

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").build();

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
            if ((sentOrReceived == 2) && (smsChecker(id))) {

                String address = cur.getString(cur.getColumnIndex("address"));
                String message = cur.getString(cur.getColumnIndex("body"));


                //get sender info
                final String SMS_URI_INBOX = "content://sms/inbox";
                Uri uri = Uri.parse(SMS_URI_INBOX);
                String[] projection = new String[] { "_id", "address", "person", "body", "date", "type" };
                Cursor cur2 = getContentResolver().query(uri, projection, String.format("address = '%s'", address), null, "date desc");
                if (cur2.moveToFirst() ) {
                    String lastMessage = cur2.getString(cur2.getColumnIndex("body"));
                    Date lastReceived = new Date(cur2.getLong(cur2.getColumnIndex("date")));
                    final String SMS_URI = "content://sms/";
                    Uri uri2 = Uri.parse(SMS_URI);
                    String[] projection2 = new String[] { "_id", "address", "person", "body", "date", "type" };
                    Cursor cur3 = getContentResolver().query(uri2, projection2, String.format("address = '%s'", address), null, "date desc");
                    if(cur3.moveToFirst()) {
                        cur3.moveToNext();
                        if (cur3.getInt(cur3.getColumnIndex(("type"))) == 1) {
                            long diff = new Date().getTime() - lastReceived.getTime();
                            long diffMinutes = diff / (60 * 1000) % 60;
                            if (diffMinutes > 30) {
                                notifyBaby(message, address);
                            }
                        }
                    }
                }
            }
        }

        private void notifyBaby(String message, String address) {
            Intent intent = new Intent(this.context, MainActivity.class);
            intent.putExtra("sent_message", message);
            PendingIntent pIntent = PendingIntent.getActivity(this.context, 0, intent, 0);

// build notification
// the addAction re-use the same intent to keep the example short
            Notification n = new Notification.Builder(this.context)
                    .setContentTitle("Texting Study Survey")
                    .setContentText("Texting Study Survey")
                    .setSmallIcon(R.mipmap.icon)
                    .setContentIntent(pIntent).build();


            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            notificationManager.notify(0, n);

            Message savedMessage = new Message();
            savedMessage.setMessageText(message);
            savedMessage.setMessageFrom(address);
            db.messageDao().insert(savedMessage);
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
