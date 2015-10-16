package com.dzhenyu.test.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by onlymem on 2015/9/12.
 */
public class FileSearchUtils {

    /**
     * 搜索文件 通过扩展名
     *
     * @param extension
     */
    public static List<File> searchFilesByExtension(Context context, String extension) {
        List<File> listFile = new ArrayList<>();
        searchFiles(listFile, android.os.Environment.getExternalStorageDirectory().getPath(), extension);
        return listFile;
    }

    private static void searchFiles(List<File> listFiles, String path, String extension) {
        File rootFile = new File(path);
        if (!rootFile.exists()) {
            return;
        }
        File[] files = rootFile.listFiles();
        if(files==null|| files.length==0){
            return;
        }
        for (File file : files) {
            if(file.isHidden() || !file.canRead()){
                continue;
            }else if(file.isDirectory()) {
                searchFiles(listFiles, file.getPath(), extension);
            } else if (file.getName().endsWith(extension) ) {
                listFiles.add(file);
            }
        }
    }
}
