package edu.northwestern.socialmedia.hestondissertationtest;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MessageListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppDatabase db = Database.getDb(getApplicationContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);

        ListView mListView = findViewById(R.id.messageList);
        final List<Message> messages = db.messageDao().getUnhandled();

       MessageAdapter adapter = new MessageAdapter(this, new ArrayList<Message>(messages));
        mListView.setAdapter(adapter);

        final Context context = this;
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 1
                Message selectedMessage = messages.get(position);

                // 2
                Intent rateIntent = new Intent(context, MainActivity.class);

                // 3
                rateIntent.putExtra("message_id", (long) selectedMessage.getUid());

                // 4
                startActivity(rateIntent);
            }

        });
    }
}
