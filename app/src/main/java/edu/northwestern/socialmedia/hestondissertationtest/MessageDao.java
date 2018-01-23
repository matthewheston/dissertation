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
    @Query("SELECT * FROM message")
    List<Message> getAll();

    @Insert
    void insertAll(Message... message);

    @Insert
    void insert(Message message);

    @Delete
    void delete(Message message);
}
