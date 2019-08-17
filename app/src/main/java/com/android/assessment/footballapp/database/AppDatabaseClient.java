package com.android.assessment.footballapp.database;

/*Signature: Uche Emmanuel
 * Developersunesis*/

import android.content.Context;

import androidx.room.Room;

/**
 * AppDatabaseClient class
 * To sustain a database instance
 */
public class AppDatabaseClient {

    private static AppDatabaseClient newInstance;

    private AppDatabase appDatabase;

    /**
     * This method builds the app database 'Currencies'
     * @param context = set the app context
     */
    private AppDatabaseClient(Context context){
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, "footballDB").build();
    }

    /**
     * This method returns an instance to be used
     * @param context = set method context
     * @return = returns instance
     */
    public static synchronized AppDatabaseClient getInstance(Context context){
        if(newInstance == null){
            newInstance = new AppDatabaseClient(context);
        }

        return newInstance;
    }

    public AppDatabase getAppDatabase(){
        return appDatabase;
    }
}
