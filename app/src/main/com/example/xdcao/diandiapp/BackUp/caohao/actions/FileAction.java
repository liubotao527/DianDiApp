package com.example.xdcao.diandiapp.BackUp.caohao.actions;

import android.os.Environment;
import android.util.Log;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;

/**
 * Created by xdcao on 2017/4/6.
 */

public class FileAction {

    public static void downloadFile(BmobFile file){
        //允许设置下载文件的存储路径，默认下载文件的目录为：context.getApplicationContext().getCacheDir()+"/bmob/"
        File saveFile = new File(Environment.getExternalStorageDirectory(), file.getFilename());
        file.download(saveFile, new DownloadFileListener() {

            @Override
            public void onStart() {
                Log.d("bmob", "onStart: ");
            }

            @Override
            public void done(String savePath,BmobException e) {
                if(e==null){
                    Log.d("bmob", "done: downloadSuccess");
                }else{
                    Log.d("bmob", "done: downloadFailure");
                }
            }

            @Override
            public void onProgress(Integer value, long newworkSpeed) {
                Log.d("bmob","下载进度："+value+","+newworkSpeed);
            }

        });
    }

}
