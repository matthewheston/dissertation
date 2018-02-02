package edu.northwestern.socialmedia.hestondissertationtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ThankYouActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you);
    }

    public void gotoMessageList(View view) {
        Intent intent = new Intent(this, MessageListActivity.class);
        startActivity(intent);
    }
}
