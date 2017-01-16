package com.example.xdcao.diandiapp;

import android.app.Activity;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by xdcao on 2017/1/16.
 */

public class FileUploadActivity extends Activity{

    private static String TAG="fileupload";

    private Button sdsc;
    private Button sdmc;
    private Button mdsc;
    private Button mdmc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_layout);
        initActivity();
    }

    private void initActivity() {
        sdsc=(Button)findViewById(R.id.sdsc);
        sdmc=(Button)findViewById(R.id.sdmc);
        mdsc=(Button)findViewById(R.id.mdsc);
        mdmc=(Button)findViewById(R.id.mdmc);
        sdsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sdscUpload(new File("/storage/emulated/0/新文件夹/叶炫清-这一生只为你.ape"));
            }
        });
    }

    private void insertObject(final BmobObject obj){
        obj.save(new SaveListener<String>() {

            @Override
            public void done(String s, BmobException e) {
                if(e==null){
                    System.out.print("-->创建数据成功：" + s);
                }else{
                    System.out.print("-->创建数据失败：" + e.getErrorCode()+",msg = "+e.getMessage());
                }
            }
        });
    }


    private void sdscUpload(File file) {

        final BmobFile bmobFile=new BmobFile(file);
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                Log.i(TAG, "done: "+bmobFile.getFileUrl());
                insertObject(new music("hahah","me",bmobFile));
            }
        });

    }


}
