package edu.northwestern.socialmedia.hestondissertationtest;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

/**
 * Created by matthewheston on 2/10/18.
 */

@Dao
public interface AllMessageDao {

    @Insert
    void insertAll(AllMessage... allMessages);

    @Insert
    long insert(AllMessage allMessage);
}
