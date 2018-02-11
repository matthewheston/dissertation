package edu.northwestern.socialmedia.hestondissertationtest;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;


/**
 * Created by matthewheston on 2/10/18.
 */

@Entity(indices = {@Index("uid")})
public class Thread {
    @PrimaryKey
    private int uid;
    @ColumnInfo(name = "contact_name")
    private String contactName;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @ColumnInfo(name = "address")
    private String address;

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
