package com.example.xdcao.diandiapp.UI.songwenqiang.ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import com.example.xdcao.diandiapp.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Administrator on 2017-4-11.
 */

public class ImageDetail extends Activity {
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.one_image);

        Bundle bundle = this.getIntent().getExtras();
        int num=bundle.getInt("num");
        imageView= (ImageView) findViewById(R.id.img);
        imageView.setImageBitmap(getLoacalBitmap(num+10+".jpg"));
    }


    public static Bitmap getLoacalBitmap(String name) {
        try {
            File f=getFileFromSd(name,"A1206");
            FileInputStream fis = new FileInputStream(f);
            return BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    static File getFileFromSd(String filename, String path) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            // 获取SD卡的目录
            File sdcardDir = Environment.getExternalStorageDirectory();//get sdcard path
            File baseDir = new File(sdcardDir, path);
            File file = new File(baseDir, filename);
            if (!baseDir.exists()) {
                baseDir.mkdir();
            }
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return file;
        } else {
            Log.w("data2", "SD卡不存在！");
            return null;
        }
    }
}
