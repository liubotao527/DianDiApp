package com.example.xdcao.diandiapp.UI.songwenqiang.Fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.xdcao.diandiapp.BackUp.caohao.actions.UserAction;
import com.example.xdcao.diandiapp.BackUp.caohao.bean.MyUser;
import com.example.xdcao.diandiapp.BackUp.caohao.cons.HandlerCons;
import com.example.xdcao.diandiapp.R;
import com.example.xdcao.diandiapp.UI.songwenqiang.ui.ChangeNickNameActivity;
import com.example.xdcao.diandiapp.UI.songwenqiang.ui.ChangeSignNameActivity;
import com.example.xdcao.diandiapp.UI.songwenqiang.ui.ChangePassWordActivity;
import com.example.xdcao.diandiapp.UI.songwenqiang.ui.SignUpActivity;
import com.example.xdcao.diandiapp.UI.songwenqiang.ui.widget.MySettingItemView;
import com.example.xdcao.diandiapp.UI.songwenqiang.ui.widget.RoundImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;

import cn.bmob.v3.BmobUser;

/**
 * Created by wewarrios on 2017/3/14.
 */

public class SettingFragment extends Fragment {

    private final String TAG = "SettingFragment";

    private RoundImageView mRvPhoto;

    private MySettingItemView mStNickname;
    private MySettingItemView mStSignName;
    private MySettingItemView mStChangePassWord;
    private ImageLoader imageLoader;

    private AppCompatButton mAcbQuit;

    private Context context;
    private Activity activity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context =activity;
        this.activity = activity;
    }

    /*
   初始化imageloader
    */
    private void initImageLoader() {
        imageLoader= ImageLoader.getInstance();
        ImageLoaderConfiguration configuration=new ImageLoaderConfiguration.Builder(this.context).build();
        imageLoader.init(configuration);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initImageLoader();
    }

    private void showAvatar() {
        MyUser curUser= BmobUser.getCurrentUser(MyUser.class);
        if(curUser.getAvatar()!=null){
            Bitmap bitmap= BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+ File.separator+curUser.getAvatar().getFilename());
            if(bitmap!=null){
                mRvPhoto.setImageBitmap(bitmap);
            }else {
                Log.d("bmob", "initView: "+curUser.getAvatar().getFileUrl());
//                imageLoader.displayImage(curUser.getAvatar().getFileUrl(),mRvPhoto);
                Log.d("bmob", "showAvatar: "+mRvPhoto);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mRvPhoto = (RoundImageView) view.findViewById(R.id.iv_photo);
        Log.d("bmob", "initView: "+mRvPhoto);

        mStNickname = (MySettingItemView) view.findViewById(R.id.st_nickname);
        mStSignName = (MySettingItemView) view.findViewById(R.id.st_sign_name);
        mStChangePassWord = (MySettingItemView) view.findViewById(R.id.st_change_password);

        mAcbQuit = (AppCompatButton) view.findViewById(R.id.btn_quit);

        SharedPreferences pref = context.getSharedPreferences("data",Context.MODE_PRIVATE);
        String signName = pref.getString("signName","点滴--记录生活的美好");
        mStSignName.setName(signName);
        String nickname = pref.getString("nickname","DianDi");
        mStNickname.setName(nickname);


//        Bundle bundle = getArguments();
//        if(bundle!=null){
//            String data = bundle.getString("nickname");
//            if(!TextUtils.isEmpty(data)){
//                mStNickname.setName(data);
//                Log.d("SettingFragment",data);
//            }
//            data = bundle.getString("signName");
//            if(!TextUtils.isEmpty(data)){
//                mStSignName.setName(data);
//                Log.d("SettingFragment",data);
//            }
//
//            System.out.println("Ssss"+data);
//
//        }

        mStNickname.setOnLSettingItemClick(new MySettingItemView.OnLSettingItemClick() {
            @Override
            public void click() {
                //Todo 修改昵称
                Intent intent = new Intent(context, ChangeNickNameActivity.class);
                startActivity(intent);
                activity.finish();
            }
        });

        mStSignName.setOnLSettingItemClick(new MySettingItemView.OnLSettingItemClick() {
            @Override
            public void click() {
                //Todo 修改个性签名
                Intent intent = new Intent(context, ChangeSignNameActivity.class);
                startActivity(intent);
                activity.finish();

            }
        });

        mStChangePassWord.setOnLSettingItemClick(new MySettingItemView.OnLSettingItemClick() {
            @Override
            public void click() {
                //Todo 修改密码
                Intent intent = new Intent(context, ChangePassWordActivity.class);
                startActivity(intent);
                activity.finish();
            }
        });
        mAcbQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ToDo  退出登录
                UserAction.logout();
                Intent intent = new Intent(context, SignUpActivity.class);
                startActivity(intent);
                SharedPreferences pref = context.getSharedPreferences("data",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor= pref.edit();
                editor.clear();
                editor.commit();
                activity.finish();
            }
        });



    }
}
