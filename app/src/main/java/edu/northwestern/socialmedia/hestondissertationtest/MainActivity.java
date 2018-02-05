package edu.northwestern.socialmedia.hestondissertationtest;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(ContextCompat.checkSelfPermission(getBaseContext(), "android.permission.READ_SMS") != PackageManager.PERMISSION_GRANTED) {
            final int REQUEST_CODE_ASK_PERMISSIONS = 123;
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{"android.permission.READ_SMS"}, REQUEST_CODE_ASK_PERMISSIONS);
        }
        Intent intent = new Intent(this, OutgoingSMSReceiver.class);
        startService(intent);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent incomingIntent = getIntent();
        if (incomingIntent != null) {
            AppDatabase db = Database.getDb(getApplicationContext());
            long messageId = incomingIntent.getLongExtra("message_id", -1);
            if (messageId != -1) {
                Message msg = db.messageDao().getById(Long.toString(messageId));

                // Capture the layout's TextView and set the string as its text
                TextView textView = findViewById(R.id.message_container);
                textView.setText(msg.getMessageText());
            }
        }
    }

    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, ThankYouActivity.class);
        startActivity(intent);

        RatingBar availabilityRating = (RatingBar) findViewById(R.id.availabilityRating);
        int availability = Math.round(availabilityRating.getRating());

        RatingBar urgencyRating = (RatingBar) findViewById(R.id.urgencyRating);
        int urgency = Math.round(urgencyRating.getRating());

        Intent incomingIntent = getIntent();
        int messageId = (int) incomingIntent.getLongExtra("message_id", -1);

        SurveyResult result = new SurveyResult();
        result.setAvailability(availability);
        result.setUrgency(urgency);
        result.setMessageId(messageId);

        Database.getDb(getApplicationContext()).surveyResultDao().insert(result);
    }
}

