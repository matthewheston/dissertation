package edu.northwestern.socialmedia.hestondissertationtest;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by matthewheston on 1/23/18.
 */

@Dao
public interface MessageDao {
    @Query("SELECT * FROM Message")
    List<Message> getAll();

    @Query("SELECT * FROM Message WHERE handled != 1")
    List<Message> getUnhandled();

    @Query("SELECT * FROM Message WHERE uid = :uid LIMIT 1")
    Message getById(String uid);

    @Query("UPDATE Message SET handled = 1 WHERE uid = :uid")
    int makeHandled(long uid);

    @Insert
    void insertAll(Message... message);

    @Insert
    long insert(Message message);

    @Delete
    void delete(Message message);
}
