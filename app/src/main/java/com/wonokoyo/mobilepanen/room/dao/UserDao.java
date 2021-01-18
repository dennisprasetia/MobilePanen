package com.wonokoyo.mobilepanen.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.wonokoyo.mobilepanen.model.User;

@Dao
public interface UserDao {
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(User user);

    @Transaction
    @Query("SELECT * FROM user WHERE deviceId = :deviceId")
    LiveData<User> getUserApp(String deviceId);
}
