package com.gzeinnumer.externalroomreaddbfromfile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.gzeinnumer.externalroomreaddbfromfile.data.AppDatabase;
import com.gzeinnumer.externalroomreaddbfromfile.helper.FunctionGlobalDir;
import com.gzeinnumer.externalroomreaddbfromfile.helper.FunctionGlobalZip;
import com.gzeinnumer.externalroomreaddbfromfile.model.SampleTable;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity_";

    TextView tv;
    String msg="externalroomreaddbfromfile\n";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(TAG);

        tv = findViewById(R.id.tv);

        AppDatabase appDatabase = AppDatabase.getInstance(getApplicationContext());

        FunctionGlobalDir.myLogD(TAG, "Jumlah Data dalam dalam table sample_data ada "+appDatabase.sampleTableDao().getAll().size());
        msg+="Jumlah Data dalam dalam table sample_data ada "+appDatabase.sampleTableDao().getAll().size()+"\n";

        appDatabase.sampleTableDao().insertAll(new SampleTable(0, "data baru"));
        appDatabase.copyFile(appDatabase);
        msg+="Data baru diinsert"+"\n";

        FunctionGlobalDir.myLogD(TAG, "Jumlah Data dalam dalam table sample_data ada "+appDatabase.sampleTableDao().getAll().size());
        msg+="Jumlah Data dalam dalam table sample_data ada "+appDatabase.sampleTableDao().getAll().size()+"\n";

        tv.setText(msg);
    }
}