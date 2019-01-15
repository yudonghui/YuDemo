package com.ydh.yudemo;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;

public class FileUtils {

    // 将字符串写入到文本文件中
    public static void writeTxtToFile(String strcontent, String fileName) {
        //生成文件夹之后，再生成文件，不然会出错
        makeFilePath(Environment.getExternalStorageDirectory().getAbsolutePath()+"/test/", fileName);

        String strFilePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/test/"+fileName;
        // 每次写入时，都换行写
        String strContent = strcontent + "\r\n";
        try {
            File file = new File(strFilePath);
            if (!file.exists()) {
                Log.d("TestFile", "Create the file:" + strFilePath);
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            raf.seek(file.length());
            raf.write(strContent.getBytes());
            raf.close();
        } catch (Exception e) {
            Log.e("TestFile", "Error on write File:" + e);
        }
    }

    // 生成文件
    public static File makeFilePath(String filePath, String fileName) {
        File file = null;
        makeRootDirectory(filePath);
        try {
            file = new File(filePath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    // 生成文件夹
    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
            Log.i("error:", e+"");
        }
    }
    /**
     * 向指定的文件中写入指定的数据
     */
    public static void writeFileData(String content) {
        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File externalStorageDirectory = Environment.getExternalStorageDirectory();
                File file = new File(externalStorageDirectory + "/test/");
                if (!file.exists()) {
                    file.mkdirs();
                }
                File file1 = new File(externalStorageDirectory + "/test/", "test.txt");
                file1.createNewFile();
                FileOutputStream fos = new FileOutputStream(file1);
                byte[] bytes = content.getBytes();
                fos.write(bytes);
                fos.close();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
