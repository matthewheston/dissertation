package edu.northwestern.socialmedia.hestondissertationtest;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Message.class, SurveyResult.class}, version = 4)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MessageDao messageDao();
    public abstract SurveyResultDao surveyResultDao();
}