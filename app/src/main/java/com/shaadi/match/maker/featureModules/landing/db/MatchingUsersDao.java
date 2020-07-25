package com.shaadi.match.maker.featureModules.landing.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.shaadi.match.maker.db.tables.MatchingUsersTable;

import java.util.List;

/**
 * Created by ajay
 */
@Dao
public interface MatchingUsersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MatchingUsersTable... matchingUsersTables);

    @Query("SELECT * FROM matching_user_table")
    List<MatchingUsersTable> getMatchingUsersData();


    @Query("SELECT COUNT(results) FROM matching_user_table")
    Integer getMatchingUsersDataCount();

}
