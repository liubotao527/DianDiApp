package com.example.xdcao.diandiapp;

import android.app.Application;


import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;

public class AppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader();
        BmobInit();
    }

    private void initImageLoader() {
        ImageLoaderConfiguration configuration =new ImageLoaderConfiguration.Builder(this).
                diskCacheFileCount(1000).
                diskCache(new UnlimitedDiskCache(new File(this.getApplicationContext().getCacheDir()+"/bmob/"))).build();
        ImageLoader.getInstance().init(configuration);

    }

    private void BmobInit() {
//        Bmob.initialize(this,"2e17b59fa1eb4f65250b5e02abc93c31");
        BmobConfig config=new BmobConfig.Builder(this).setApplicationId("2e17b59fa1eb4f65250b5e02abc93c31")
                .build();
        Bmob.initialize(config);

    }

}
