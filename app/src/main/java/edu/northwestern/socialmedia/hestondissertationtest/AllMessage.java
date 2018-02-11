package edu.northwestern.socialmedia.hestondissertationtest;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;

/**
 * Created by matthewheston on 2/10/18.
 */

@Entity
public class AllMessage {
    @PrimaryKey(autoGenerate = true)
    private int uid;
    @ColumnInfo(name = "body")
    private String body;
    @ColumnInfo(name = "thread_id")
    private int threadId;
    @ColumnInfo(name = "type")
    private int type;
    @ColumnInfo(name = "received_at")
    @TypeConverters({Converters.class})
    public Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getThreadId() {
        return threadId;
    }

    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUid() {

        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
