package compindia.intext;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by compindia-fujitsu on 28-09-2017.
 */

public class StorageClass {
    static File intFolder;
    static File extFoldder;
    String inFolderName = "MY_IMGS";
    String extFolderName = "MY_IMGS2";
    static Context context;

    protected StorageClass(Context context) {
        this.context = context;
        intFolder = new File(context.getFilesDir().getAbsolutePath(), inFolderName);
        if (!intFolder.exists()) {
            intFolder.mkdirs();
        }
        extFoldder = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), extFolderName);
        if (!extFoldder.exists()) {
            extFoldder.mkdirs();
        }
    }

    public void saveToInternal(Bitmap bitmap, int i) {
        if (!intFolder.exists()) {
            intFolder.mkdirs();
        }
        File outputFile = new File(intFolder, "photo_in_" + i + ".png");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            Toast.makeText(context.getApplicationContext(), "Image Saved in Internal Storage", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveToExternal(Bitmap bitmap, int i) {
        if (!extFoldder.exists()) {
            extFoldder.mkdirs();
        }

        File outputFile = new File(extFoldder, "photo_ext_" + i + ".png");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            Toast.makeText(context.getApplicationContext(), "Image Saved in External Storage", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getNoOfFilesInInternal() {
        if (!intFolder.exists()) {
            intFolder.mkdirs();
        }
        return intFolder.listFiles().length;
    }

    public int getNoOfFilesInExternal() {
        if (!extFoldder.exists()) {
            extFoldder.mkdirs();
        }
        return extFoldder.listFiles().length;
    }

    File[] getInternalFolderList() {
        if (!intFolder.exists()) {
            intFolder.mkdirs();
        }
        return intFolder.listFiles();
    }

    File[] getExternalFolderList() {
        if (!extFoldder.exists()) {
            extFoldder.mkdirs();
        }
        return extFoldder.listFiles();
    }

    void clearInternalImages(File[] files) {

        if (intFolder.exists()) {
            for (File f : files) {
                if (f.exists()) {
                    f.delete();
                }
            }
        }
    }

    void clearExternalImages(File[] files) {
        if (extFoldder.exists()) {
            for (File f : files) {
                if (f.exists()) {
                    f.delete();
                }
            }
        }
    }

    void clearAllImages() {
        clearExternalImages(getExternalFolderList());
        clearInternalImages(getInternalFolderList());
    }
}
