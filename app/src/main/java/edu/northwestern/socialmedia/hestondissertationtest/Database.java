package edu.northwestern.socialmedia.hestondissertationtest;

import android.arch.persistence.room.Room;
import android.content.Context;

/**
 * Created by matthewheston on 2/1/18.
 */

public class Database {
    private static AppDatabase db;

    public static AppDatabase getDb(Context context) {
        if (db == null) {
            db = Room.databaseBuilder(context,
                    AppDatabase.class, "database-name").allowMainThreadQueries().build();
        }
        return db;
    }
}
