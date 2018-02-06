package edu.northwestern.socialmedia.hestondissertationtest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by matthewheston on 1/29/18.
 */

public class MessageAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Message> mDataSource;

    public MessageAdapter(Context context, ArrayList<Message> items) {
        mContext = context;
        mDataSource = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mDataSource.size();
    }

    //2
    @Override
    public Object getItem(int position) {
        return mDataSource.get(position);
    }

    //3
    @Override
    public long getItemId(int position) {
        return position;
    }

    //4
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get view for row item
        View rowView = mInflater.inflate(R.layout.list_item_message, parent, false);

        TextView messageHolder = rowView.findViewById(R.id.list_message_holder);
        TextView dateHolder = rowView.findViewById(R.id.list_date_holder);

        Message msg = (Message) getItem(position);
        String msgText = msg.getMessageText();
        messageHolder.setText(msgText);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm");
        String respondedAt = dateFormat.format(msg.getRespondedAt());
        dateHolder.setText(respondedAt);

        return rowView;
    }
}
