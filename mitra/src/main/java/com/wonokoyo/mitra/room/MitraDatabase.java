package com.wonokoyo.mitra.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.wonokoyo.mitra.model.Plan;
import com.wonokoyo.mitra.model.Real;
import com.wonokoyo.mitra.model.Tara;
import com.wonokoyo.mitra.model.Weigh;
import com.wonokoyo.mitra.room.dao.FlowDao;
import com.wonokoyo.mitra.room.dao.PlanDao;

@Database(entities = {Plan.class, Tara.class, Weigh.class, Real.class}, version = 1, exportSchema = false)
public abstract class MitraDatabase extends RoomDatabase {
    private static volatile MitraDatabase INSTANCE;

    public abstract PlanDao planDao();

    public abstract FlowDao flowDao();

    public static MitraDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (MitraDatabase.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), MitraDatabase.class,
                        "muserp_mitra")
                        .fallbackToDestructiveMigration()
                        .addCallback(sRoomDatabaseCallback)
                        .build();
            }
        }

        return INSTANCE;
    }

    private static Callback sRoomDatabaseCallback = new Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };
}
