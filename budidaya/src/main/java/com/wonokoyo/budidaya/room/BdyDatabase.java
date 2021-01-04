package com.wonokoyo.budidaya.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.wonokoyo.budidaya.model.Plan;
import com.wonokoyo.budidaya.model.Real;
import com.wonokoyo.budidaya.model.Tara;
import com.wonokoyo.budidaya.model.Weigh;
import com.wonokoyo.budidaya.room.dao.FlowDao;
import com.wonokoyo.budidaya.room.dao.PlanDao;

@Database(entities = {Plan.class, Tara.class, Weigh.class, Real.class}, version = 3, exportSchema = false)
public abstract class BdyDatabase extends RoomDatabase {
    private static volatile BdyDatabase INSTANCE;

    public abstract PlanDao planDao();

    public abstract FlowDao flowDao();

    public static BdyDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (BdyDatabase.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), BdyDatabase.class,
                        "muserp_bdy")
                        .fallbackToDestructiveMigration()
                        .addCallback(sRoomDatabaseCallback)
                        .build();
            }
        }

        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };
}
