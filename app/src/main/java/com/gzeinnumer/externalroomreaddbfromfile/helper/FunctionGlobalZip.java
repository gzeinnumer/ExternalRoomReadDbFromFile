package com.gzeinnumer.externalroomreaddbfromfile.helper;

import android.util.Base64;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class FunctionGlobalZip {

    private static final String TAG = "FunctionGLobalZip_";

    //start genFile
    public static boolean initFileFromStringToZipToFile() {
        //dari file zip diubah jadi base64
        //https://base64.guru/converter/encode/file
        String base64EncodeFromFile = "UEsDBBQAAAAIAJK6+FDfGqHQdAEAAABAAAAZAAAARXh0ZXJuYWxCYXNlNjRNZDVUb1ppcC5kYu3aQU7CQBQG4BlKgJJgWZh042JsQgIBTNQLiKYhRCgIJYqbZqRj0tgWgXIAbuQJvIk3MC516xRMhLowLiX/l2nmvXnpm25f0sFV24sEu5/MAh6xU1IklJIzxgghqnzS5Fs6kVPyO5UcHTwVZKDsPRPN03S5AQAAAAAAAPzRtZLR63U6jvidL3joziae6wQi4i6PeDJPX/TNhm0yu3HeNlmyysr+ZMx9wWzzxq70Uhm9WqWjVeP51JczsjMX04UIx8lU2WqbKJZDHoiazCrLfZrVSyW6fFj35MGjL5wfcWqrm7FZMlg5rxqea6gtyzabZp9ZXZtZw3a7Js/jiww1/vjN416/1Wn0R+zSHJXjV1ljaHdblrykY1p2JV9ZzebaC9HetVe5AQAAAAAAAMB/U1AUcti8FV5oLQIxy6UUosfZSY5+Rcfx/E+1NyIXAAAAAAAAAOyEIlVKdPOfAmU9/38QuQAAAAAAAABgt2RpShehMxx8AlBLAQI/ABQAAAAIAJK6+FDfGqHQdAEAAABAAAAZACQAAAAAAAAAgAAAAAAAAABFeHRlcm5hbEJhc2U2NE1kNVRvWmlwLmRiCgAgAAAAAAABABgAgEsYXNZh1gH+eSEy1mHWASKHEDLWYdYBUEsFBgAAAAABAAEAawAAAKsBAAAAAA==";

        //dari file zip diubah jadi md5
        //https://emn178.github.io/online-tools/md5_checksum.html
        String md5EncodeFromFile = "966af03a49f85b0df0afd3d9a42d0264";

        if (converToZip(base64EncodeFromFile)){
            if (compareMd5(md5EncodeFromFile)){
                try {
                    if (unzip()){
                        FunctionGlobalDir.myLogD(TAG, "Success Zip");
                        return true;
                    } else {
                        FunctionGlobalDir.myLogD(TAG, "Failed Zip");
                        return false;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    FunctionGlobalDir.myLogD(TAG, "Failed Zip " +e.getMessage());
                    return false;
                }
            }
        }
        return false;
    }

    private static boolean converToZip(String base64) {
        FunctionGlobalDir.myLogD(TAG,"converToZip");
        final File fileZip = new File(FunctionGlobalDir.getStorageCard +  FunctionGlobalDir.appFolder+ FunctionGlobalDir.appFile);
        byte[] pdfAsBytes ;
        try {
            pdfAsBytes = Base64.decode(base64, 0);
            FileOutputStream os;
            try {
                os = new FileOutputStream(fileZip, false);
                os.write(pdfAsBytes);
                os.flush();
                os.close();
                return true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return false;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    private static boolean compareMd5(String md5OriginValue) {
        FunctionGlobalDir.myLogD(TAG, "compareMd5");
        try {
            String md5Origin = md5OriginValue;
            String filePath = FunctionGlobalDir.getStorageCard + FunctionGlobalDir.appFolder+ FunctionGlobalDir.appFile;
            FileInputStream fis = new FileInputStream(filePath);
            String md5Checksum = Md5Checksum.md5(fis);
            return md5Checksum.equals(md5Origin);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            FunctionGlobalDir.myLogD(TAG, "onCreate");
        }
        return false;
    }

    private static boolean unzip() throws IOException {
        FunctionGlobalDir.myLogD(TAG,"unzip");
        String zipFile = FunctionGlobalDir.getStorageCard + FunctionGlobalDir.appFolder+ FunctionGlobalDir.appFile;
        String unzipLocation = FunctionGlobalDir.getStorageCard + FunctionGlobalDir.appFolder+"/db/";
        ZipFile archive = new ZipFile(zipFile);
        Enumeration<? extends ZipEntry> e = archive.entries();
        while (e.hasMoreElements()) {
            ZipEntry entry = e.nextElement();
            File file = new File(unzipLocation, entry.getName());
            if (entry.isDirectory()) {
                file.mkdirs();
            } else {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                InputStream in = archive.getInputStream(entry);
                OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
                byte[] buffer = new byte[1024];
                int read;
                while ((read = in.read(buffer)) != -1) {
                    out.write(buffer, 0, read);
                }
                in.close();

                // write the output file (You have now copied the file)
                out.flush();
                out.close();
                return true;
            }
        }
        return false;
    }
    //end genFile
}
