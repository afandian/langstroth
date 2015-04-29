package com.afandian.langstroth;

import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by joe on 26/04/2015.
 */
public class Storage {
    // The 'langstroth' base directory.
    private File baseDir;

    // The top level 'documents' directory.
    public File veryBaseDir;

    private String basePath;


    public Storage() {
        this.veryBaseDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        this.baseDir = new File(this.veryBaseDir, "Langstroth");
        this.basePath = this.baseDir.getAbsolutePath();
    }

    private int fileCount(File base) {
        int count = 0;
        File[] files = base.listFiles();
        if (files == null) {
            return 0;
        } else {
            for (File file : files) {
                if (file.isFile()) {
                    count ++;
                } else if (file.isDirectory()) {
                    count = count + fileCount(file);
                }
            }
        }

        return count;
    }

    public int fileCount() {
        // Expect a /samples/«duration»/files* so this is recursive.
        return this.fileCount(this.baseDir);
    }

    public void clear() {
        File[] files = baseDir.listFiles();
        for(File file : files) {
            file.delete();
        }
    }

    public void upload() {
        File[] files = baseDir.listFiles();
        for(File file : files) {
            // TODO
        }
    }

    public void save(String path, Date date, Context context) {
        // TODO - maybe save in a database

        Intent mediaScannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri fileContentUri = Uri.fromFile(this.baseDir);
        mediaScannerIntent.setData(fileContentUri);
        context.sendBroadcast(mediaScannerIntent);
    }

    public String getPathForRecording(Date now, int duration) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH-mm-ssZ", Locale.UK);
        String directoryPath = this.basePath + "/sample/" + duration;

        // TODO do something with result...
        boolean success = new File(directoryPath).mkdirs();

        return directoryPath + "/" + sdf.format(now) + ".wav";
    }
}