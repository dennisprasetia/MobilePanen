package com.wonokoyo.budidaya.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.wonokoyo.budidaya.model.Tara;
import com.wonokoyo.budidaya.model.Weigh;

import java.util.List;

@Dao
public interface FlowDao {
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Tara tara);

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Weigh weigh);

    @Transaction
    @Query("SELECT * FROM tara WHERE rit = :rit AND tgl_tara = :tgl_tara")
    LiveData<List<Tara>> getTaraByRitAndTgl(String rit, String tgl_tara);
}
