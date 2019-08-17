package com.android.assessment.footballapp.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.android.assessment.footballapp.models.FixtureModel;

@Database(entities = {FixtureModel.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract FixturesDao fixturesDao();
}
