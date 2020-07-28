package com.gzeinnumer.externalroomreaddbfromfile.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.gzeinnumer.externalroomreaddbfromfile.dao.SampleTableDao;
import com.gzeinnumer.externalroomreaddbfromfile.helper.FunctionGlobalDir;
import com.gzeinnumer.externalroomreaddbfromfile.model.SampleTable;

import java.io.File;

@Database(entities = {SampleTable.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public abstract SampleTableDao sampleTableDao();

    public static AppDatabase getInstance(Context context) {

        File folder = new File(FunctionGlobalDir.getStorageCard + FunctionGlobalDir.appFolder + "/db/ExternalBase64Md5ToZip.db");
        return Room.databaseBuilder(context,
                AppDatabase.class, "ExternalBase64Md5ToZip.db")
//                .addMigrations(AppDatabase.MIGRATION_1)
                .createFromFile(folder)
                .allowMainThreadQueries()
                .build();
    }

    static final Migration MIGRATION_1 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE `sample_table` (`id` INTEGER, `name` TEXT, PRIMARY KEY(`id`))");
        }
    };
}
