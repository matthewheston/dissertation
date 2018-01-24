package edu.northwestern.socialmedia.hestondissertationtest;

import android.arch.persistence.room.Room;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class MessageListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").allowMainThreadQueries().build();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);

        ListView mListView = findViewById(R.id.messageList);
        List<Message> messages = db.messageDao().getAll();

        String[] listItems = new String[messages.size()];

        for(int i = 0; i < messages.size(); i++){
            Message msg = messages.get(i);
            listItems[i] = msg.getMessageFrom();
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems);
        mListView.setAdapter(adapter);
    }
}
