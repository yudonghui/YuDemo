package com.ydh.yudemo.utils;

import android.content.Context;
import android.os.Environment;

import com.nostra13.universalimageloader.utils.L;
import com.ydh.yudemo.retrofitrxjava.HttpCallBack;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * Created by UI on 2018/9/28.
 */

public class FileUtils {
    public static File createFile(Context context) {
        File file = null;
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/xungen.apk");
        } else {
            file = new File(context.getCacheDir().getAbsolutePath() + "/xungen.apk");
        }
        L.d("vivi", "file " + file.getAbsolutePath());
        return file;
    }

    public static void writeFile2Disk(Response<ResponseBody> response, File file, HttpCallBack httpCallBack) {
        long currentLength = 0;
        OutputStream os = null;
        InputStream is = response.body().byteStream();
        long totalLength = response.body().contentLength();
        try {
            os = new FileOutputStream(file);
            int len;
            byte[] buff = new byte[1024];
            while ((len = is.read(buff)) != -1) {
                os.write(buff, 0, len);
                currentLength += len;
                httpCallBack.onLoading(currentLength, totalLength);
            } // httpCallBack.onLoading(currentLength,totalLength,true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
