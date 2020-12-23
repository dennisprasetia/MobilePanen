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
    @Query("SELECT * FROM `plan` WHERE no_do NOT IN (SELECT no_do FROM real) AND tgl_panen BETWEEN :start AND :end")
    LiveData<List<Plan>> getPlansByDate(String start, String end);

    @Transaction
    @Query("SELECT * FROM `plan` WHERE no_sj = :no_dosj AND no_sj NOT IN (SELECT no_sj FROM real) OR no_do = :no_dosj" +
            " AND no_do NOT IN (SELECT no_do FROM real)")
    LiveData<Plan> getPlanByDoSj(String no_dosj);

    @Transaction
    @Query("SELECT COUNT(*) as quantity FROM `plan` WHERE no_do NOT IN (SELECT no_do FROM real) AND tgl_panen BETWEEN " +
            ":start AND :end")
    LiveData<Integer> getCountPlan(String start, String end);
}
