package com.example.xdcao.diandiapp;

import android.app.Application;


import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;

public class AppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader();
    }

    private void initImageLoader() {
        ImageLoaderConfiguration configuration =new ImageLoaderConfiguration.Builder(this).
                diskCacheFileCount(1000).
                diskCache(new UnlimitedDiskCache(new File(this.getApplicationContext().getCacheDir()+"/bmob/"))).build();
        ImageLoader.getInstance().init(configuration);

    }
}
