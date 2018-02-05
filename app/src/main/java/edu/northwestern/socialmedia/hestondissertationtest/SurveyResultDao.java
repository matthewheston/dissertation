package edu.northwestern.socialmedia.hestondissertationtest;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;

/**
 * Created by matthewheston on 2/5/18.
 */

@Dao
public interface SurveyResultDao {

    @Insert
    void insertAll(SurveyResult... surveyResult);

    @Insert
    long insert(SurveyResult surveyResult);

    @Delete
    void delete(SurveyResult surveyResult);
}
