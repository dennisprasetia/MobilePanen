package com.wonokoyo.budidaya.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.wonokoyo.budidaya.model.Plan;

import java.util.List;

@Dao
public interface PlanDao {
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Plan plan);

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Plan> plans);

    @Transaction
    @Update
    void update(Plan plan);

    @Transaction
    @Delete
    void delete(Plan plan);

    @Transaction
    @Query("SELECT * FROM `plan` WHERE tgl_panen BETWEEN :start AND :end")
    LiveData<List<Plan>> getPlansByDate(String start, String end);

    @Transaction
    @Query("SELECT * FROM `plan` WHERE no_sj = :no_dosj OR no_do = :no_dosj")
    LiveData<Plan> getPlanByDoSj(String no_dosj);
}
