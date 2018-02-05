package edu.northwestern.socialmedia.hestondissertationtest;

import android.arch.persistence.room.Room;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        List<Message> messages = db.messageDao().getUnhandled();

       MessageAdapter adapter = new MessageAdapter(this, new ArrayList<Message>(messages));
        mListView.setAdapter(adapter);
    }
}
