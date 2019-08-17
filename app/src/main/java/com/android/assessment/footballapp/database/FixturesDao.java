package com.android.assessment.footballapp.database;


/*Signature: Uche Emmanuel
 * Developersunesis*/

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.android.assessment.footballapp.models.FixtureModel;

import java.util.List;

/**
 * SymbolsDao
 * to carry out SQLite operations CRUD
 * insert, delete and update
 */
@Dao
public interface FixturesDao {

    @Query("SELECT * FROM fixturesmodel")
    List<FixtureModel> getAllSymbols();

    /**
     * This inserts a Symbols model to the database
     * @param fixture = a model
     */
    @Insert
    void insertSymbol(FixtureModel fixture);

    /**
     * This delete a Symbols model to the database
     * @param fixture = a model
     */
    @Delete
    void deleteSymbol(FixtureModel fixture);

    /**
     * This update a Symbols model to the database
     * @param fixture = a model
     */
    @Update
    void updateSymbol(FixtureModel fixture);
}