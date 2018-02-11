package edu.northwestern.socialmedia.hestondissertationtest;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

/**
 * Created by matthewheston on 2/10/18.
 */

@Dao
public interface ThreadDao {

    @Query("SELECT * FROM Thread WHERE uid = :uid LIMIT 1")
    Thread getById(String uid);


    @Insert
    void insertAll(Thread... threads);

    @Insert
    long insert(Thread thread);
}
