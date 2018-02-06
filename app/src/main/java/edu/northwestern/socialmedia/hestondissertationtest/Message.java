package edu.northwestern.socialmedia.hestondissertationtest;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;


/**
 * Created by matthewheston on 1/23/18.
 */

@Entity(indices = {@Index("uid")})
public class Message {
    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "message_text")
    private String messageText;

    @ColumnInfo(name = "message_from")
    private String messageFrom;

    public boolean isHandled() {
        return handled;
    }

    public void setHandled(boolean handled) {
        this.handled = handled;
    }

    @ColumnInfo(name = "handled")
    private boolean handled;

    @ColumnInfo(name = "received_at")
    @TypeConverters({Converters.class})
    public Date receivedAt;

    public Date getRespondedAt() {
        return respondedAt;
    }

    public void setRespondedAt(Date respondedAt) {
        this.respondedAt = respondedAt;
    }

    @ColumnInfo(name = "responded_at")
    @TypeConverters({Converters.class})
    public Date respondedAt;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageFrom() {
        return messageFrom;
    }

    public void setMessageFrom(String messageFrom) {
        this.messageFrom = messageFrom;
    }

    public Date getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(Date receivedAt) {
        this.receivedAt = receivedAt;
    }
}

