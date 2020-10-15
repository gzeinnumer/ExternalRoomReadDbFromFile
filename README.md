# ExternalRoomReadDbFromFile
 part 5, setelah zip dibuat , buat instanse untuk room
 
Using [Room](https://developer.android.com/training/data-storage/room?hl=id) as your local database.

#### Code for AppDataBase
```java
@Database(entities = {SampleTable.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {

    private static final String TAG = "AppDatabase";
    public static String dbName = "ExternalBase64Md5ToZip.db";

    public abstract SampleTableDao sampleTableDao();

    public static AppDatabase getInstance(Context context) {
        File folder = new File(Environment.getExternalStorageDirectory().toString()+"FolderOnExternal"+"/db"+dbName);
        return Room.databaseBuilder(context,
                AppDatabase.class, dbName)
                .createFromFile(folder)
                .allowMainThreadQueries()
                .build();
    }

    public void copyFile(AppDatabase appDatabase) {
        appDatabase.close();
        try {
            File localDir = new File(Environment.getExternalStorageDirectory().toString()+"FolderOnExternal"+"/db");
            File sd = Environment.getExternalStorageDirectory();

            if (sd.canWrite()) {
                String currentDBPath = appDatabase.getOpenHelper().getWritableDatabase().getPath();
                String backupDBPath = AppDatabase.dbName;
                File currentDB = new File(currentDBPath);
                File backupDB = new File(localDir, backupDBPath);
                Log.d(TAG, "path local  "+localDir.toString());
                Log.d(TAG, "path sumber  "+currentDB.toString());
                Log.d(TAG, "path tujuan  "+backupDB.toString());

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            } else {
                Log.d(TAG, "path Cannon write");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "path "+e.getMessage());
        }
    }
}
```

#### Important
Every new transaction process `Insert, Update, Delete` or `ON APP DESTROY` you should use `copyFile` to close Database and backup Database `Room` from root to eksternal. Here is the example.

```java
AppDatabase appDatabase = AppDatabase.getInstance(getApplicationContext());

appDatabase.sampleTableDao().insertAll(new SampleTable(0, "data baru"));
appDatabase.copyFile(appDatabase);
```

---

```
Copyright 2020 M. Fadli Zein
```
