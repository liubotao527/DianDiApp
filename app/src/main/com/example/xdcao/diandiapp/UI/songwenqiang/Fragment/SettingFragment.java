package com.example.xdcao.diandiapp.UI.songwenqiang.Fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.xdcao.diandiapp.BackUp.caohao.actions.UserAction;
import com.example.xdcao.diandiapp.R;
import com.example.xdcao.diandiapp.UI.songwenqiang.ui.ChangeNickNameActivity;
import com.example.xdcao.diandiapp.UI.songwenqiang.ui.ChangeSignNameActivity;
import com.example.xdcao.diandiapp.UI.songwenqiang.ui.ChangePassWordActivity;
import com.example.xdcao.diandiapp.UI.songwenqiang.ui.SignUpActivity;
import com.example.xdcao.diandiapp.UI.songwenqiang.ui.widget.MySettingItemView;
import com.example.xdcao.diandiapp.UI.songwenqiang.ui.widget.RoundImageView;

/**
 * Created by wewarrios on 2017/3/14.
 */

public class SettingFragment extends Fragment {

    private RoundImageView mRvPhoto;

    private MySettingItemView mStNickname;
    private MySettingItemView mStSignName;
    private MySettingItemView mStChangePassWord;

    private AppCompatButton mAcbQuit;

    private Context context;

    @Override
    public void onAttach(Activity activity) {
        this.context =activity;
        super.onAttach(activity);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mRvPhoto = (RoundImageView) view.findViewById(R.id.riv_photo);

        mStNickname = (MySettingItemView) view.findViewById(R.id.st_nickname);
        mStSignName = (MySettingItemView) view.findViewById(R.id.st_sign_name);
        mStChangePassWord = (MySettingItemView) view.findViewById(R.id.st_change_password);

        mAcbQuit = (AppCompatButton) view.findViewById(R.id.btn_quit);

        mStNickname.setOnLSettingItemClick(new MySettingItemView.OnLSettingItemClick() {
            @Override
            public void click() {
                //Todo 修改昵称
                Intent intent = new Intent(context, ChangeNickNameActivity.class);
                startActivity(intent);
            }
        });

        mStSignName.setOnLSettingItemClick(new MySettingItemView.OnLSettingItemClick() {
            @Override
            public void click() {
                //Todo 修改个性签名
                Intent intent = new Intent(context, ChangeSignNameActivity.class);
                startActivity(intent);
            }
        });

        mStChangePassWord.setOnLSettingItemClick(new MySettingItemView.OnLSettingItemClick() {
            @Override
            public void click() {
                //Todo 修改密码
                Intent intent = new Intent(context, ChangePassWordActivity.class);
                startActivity(intent);
            }
        });
        mAcbQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ToDo  退出登录
                UserAction.logout();
                Intent intent = new Intent(context, SignUpActivity.class);
                startActivity(intent);
            }
        });


    }
}
