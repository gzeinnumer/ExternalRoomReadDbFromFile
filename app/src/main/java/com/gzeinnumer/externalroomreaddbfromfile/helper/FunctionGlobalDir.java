package com.gzeinnumer.externalroomreaddbfromfile.helper;

import android.os.Environment;
import android.util.Log;

import java.io.File;

public class FunctionGlobalDir {

    public static String getStorageCard = Environment.getExternalStorageDirectory().toString();
    public static String appFolder = "/ExternalRoomReadDbFromFile";
    public static String appFile = "/ExternalBase64Md5ToZip.zip";

    private static final String TAG = "FunctionGLobalDir_";

    public static void myLogD(String tag,String msg){
        Log.d("MyZein", tag+"_"+msg);
    }

    public static boolean initFolder() {
        File folder;

        // create folder
        folder = new File(getStorageCard + appFolder);
        if (!folder.exists()) {
            if (!creatingFolder(folder)){
                return false;
            }
        }
        folder = new File(getStorageCard + appFolder + "/db");
        if (!folder.exists()) {
            if (!creatingFolder(folder)){
                return false;
            }
        }
        return true;
    }

    private static boolean creatingFolder(File folder){
        try{
            if (folder.mkdirs()){
                myLogD(TAG, "Folder created");
            }
        } catch (Exception e){
            myLogD(TAG, "Folder not created");
            return false;
        }
        return true;
    }

    public static boolean isFileExists(String path){
        File file = new File(getStorageCard + path);
        return file.exists();
    }
}
