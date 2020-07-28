package com.gzeinnumer.externalroomreaddbfromfile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.gzeinnumer.externalroomreaddbfromfile.helper.FunctionGlobalDir;
import com.gzeinnumer.externalroomreaddbfromfile.helper.FunctionGlobalZip;

import java.util.ArrayList;
import java.util.List;

public class SplashScreenActivity extends AppCompatActivity {

    private static final String TAG = "SplashScreenActivity";

    String[] permissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        if (checkPermissions()) {
            onSuccessCheckPermitions();
        } else {
            Toast.makeText(this, "Beri izin dulu", Toast.LENGTH_SHORT).show();
            ((TextView) findViewById(R.id.tv)).setText("Beri izin dulu");
        }
    }

    private void onSuccessCheckPermitions() {
        if (FunctionGlobalDir.initFolder()){
            if (FunctionGlobalDir.isFileExists(FunctionGlobalDir.appFolder)){
                Toast.makeText(this, "Sudah bisa lanjut", Toast.LENGTH_SHORT).show();
                ((TextView) findViewById(R.id.tv)).setText("Sudah bisa lanjut");

                if (FunctionGlobalZip.initFileFromStringToZipToFile()){
                    Toast.makeText(this, "Sudah Success load Data", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(this, "Gagal load Data", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(this, "Direktory tidak ditemukan", Toast.LENGTH_SHORT).show();
                ((TextView) findViewById(R.id.tv)).setText("Direktory tidak ditemukan");
            }
        } else {
            Toast.makeText(this, "Gagal membuat folder", Toast.LENGTH_SHORT).show();
            ((TextView) findViewById(R.id.tv)).setText("Gagal membuat folder");
        }
    }

    int MULTIPLE_PERMISSIONS = 1;
    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();

        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(getApplicationContext(), p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[0]), MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MULTIPLE_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onSuccessCheckPermitions();
            } else {
                StringBuilder perStr = new StringBuilder();
                for (String per : permissions) {
                    perStr.append("\n").append(per);
                }
            }
        }
    }
}