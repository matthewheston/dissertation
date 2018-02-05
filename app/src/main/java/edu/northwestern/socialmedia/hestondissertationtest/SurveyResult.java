package edu.northwestern.socialmedia.hestondissertationtest;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by matthewheston on 2/5/18.
 */

@Entity(foreignKeys = @ForeignKey(entity = Message.class,
                                    parentColumns = "uid",
                                    childColumns = "message_id"))
public class SurveyResult {

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }

    public int getUrgency() {
        return urgency;
    }

    public void setUrgency(int urgency) {
        this.urgency = urgency;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name="availability")
    private int availability;

    @ColumnInfo(name="urgency")
    private int urgency;

    @ColumnInfo(name="message_id")
    private int messageId;
}
