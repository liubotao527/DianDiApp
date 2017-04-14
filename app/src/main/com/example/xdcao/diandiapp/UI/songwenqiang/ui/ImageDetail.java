package com.example.xdcao.diandiapp.UI.songwenqiang.ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import com.example.xdcao.diandiapp.DdService.liubotao.ninegridlayout.util.ImageLoaderUtil;
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
        String oneImg=bundle.getString("img");
        imageView= (ImageView) findViewById(R.id.img);
        ImageLoaderUtil.getImageLoader(ImageDetail.this).displayImage(oneImg, imageView , ImageLoaderUtil.getPhotoImageOption());
    }

}
