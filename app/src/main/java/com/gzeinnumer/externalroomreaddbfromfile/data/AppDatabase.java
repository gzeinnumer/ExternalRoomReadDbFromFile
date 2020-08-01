package com.gzeinnumer.externalroomreaddbfromfile.data;

import android.content.Context;
import android.os.Environment;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.gzeinnumer.externalroomreaddbfromfile.dao.SampleTableDao;
import com.gzeinnumer.externalroomreaddbfromfile.helper.FunctionGlobalDir;
import com.gzeinnumer.externalroomreaddbfromfile.model.SampleTable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

@Database(entities = {SampleTable.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {

    private static final String TAG = "AppDatabase";
    public static String dbName = "ExternalBase64Md5ToZip.db";

    public abstract SampleTableDao sampleTableDao();

    public static AppDatabase getInstance(Context context) {
        File folder = new File(FunctionGlobalDir.getStorageCard + FunctionGlobalDir.appFolder + "/db/"+dbName);
        return Room.databaseBuilder(context,
                AppDatabase.class, dbName)
//                .addMigrations(AppDatabase.MIGRATION_1)
                .createFromFile(folder)
                .allowMainThreadQueries()
                .build();
    }

    public void copyFile(AppDatabase appDatabase) {
        appDatabase.close();
        try {
            File localDir = new File(FunctionGlobalDir.getStorageCard+FunctionGlobalDir.appFolder+"/db");
            File sd = Environment.getExternalStorageDirectory();

            if (sd.canWrite()) {
                String currentDBPath = appDatabase.getOpenHelper().getWritableDatabase().getPath();
                String backupDBPath = AppDatabase.dbName;
                File currentDB = new File(currentDBPath);
                File backupDB = new File(localDir, backupDBPath);
                FunctionGlobalDir.myLogD(TAG, "path local  "+localDir.toString());
                FunctionGlobalDir.myLogD(TAG, "path sumber  "+currentDB.toString());
                FunctionGlobalDir.myLogD(TAG, "path tujuan  "+backupDB.toString());

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            } else {
                FunctionGlobalDir.myLogD(TAG, "path Cannon write");
            }
        } catch (Exception e) {
            e.printStackTrace();
            FunctionGlobalDir.myLogD(TAG, "path "+e.getMessage());
        }
    }

    static final Migration MIGRATION_1 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE `sample_table` (`id` INTEGER, `name` TEXT, PRIMARY KEY(`id`))");
        }
    };

}
