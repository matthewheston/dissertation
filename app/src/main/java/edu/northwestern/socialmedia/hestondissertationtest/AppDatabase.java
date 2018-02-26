package edu.northwestern.socialmedia.hestondissertationtest;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Message.class, SurveyResult.class, Thread.class, AllMessage.class}, version = 9)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MessageDao messageDao();
    public abstract SurveyResultDao surveyResultDao();
    public abstract AllMessageDao allMessageDao();
    public abstract ThreadDao threadDao();
}