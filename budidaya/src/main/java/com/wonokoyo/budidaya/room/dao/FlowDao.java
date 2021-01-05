package com.wonokoyo.budidaya.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.wonokoyo.budidaya.model.Real;
import com.wonokoyo.budidaya.model.RealWithDetail;
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
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(Real real);

    @Transaction
    @Update
    void update(Real real);

    @Transaction
    @Update
    void update(List<Real> reals);

    @Transaction
    @Query("SELECT * FROM tara WHERE rit = :rit AND tgl_tara = :tgl_tara LIMIT 5")
    LiveData<List<Tara>> getTaraByRitAndDate(String rit, String tgl_tara);

    @Transaction
    @Query("SELECT COUNT(*) as quantity FROM tara WHERE nodo_real = :no_do")
    LiveData<Integer> getTaraByDo(String no_do);

    @Transaction
    @Query("SELECT * FROM weigh where nodo_real = :no_do")
    LiveData<List<Weigh>> getWeighByDo(String no_do);

    @Transaction
    @Query("SELECT * FROM real WHERE status = 0")
    LiveData<List<RealWithDetail>> getAllReal();

    @Transaction
    @Query("SELECT COUNT(*) as quantity FROM real WHERE status = 0")
    LiveData<Integer> getRealCount();
}
