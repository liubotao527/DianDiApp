package com.example.xdcao.diandiapp.UI.songwenqiang.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.xdcao.diandiapp.R;

/**
 * Created by wewarrios on 2017/4/9.
 */

public class MySettingItemView extends RelativeLayout {

    //左侧显示文本
    private String mLeftText;
    //右侧显示文本
    private String mRightText;
    //文本大小
    private int mTextSize;
    //文本颜色
    private int mTextColor;
    //整体根布局
    private View mView;
    //根布局
    private RelativeLayout mRootLayout;
    //左侧文本控件
    private TextView mTvLeftText;
    //分割线
    private View mUnderLine;
    //右侧图标控件区域，默认展示图标
    private FrameLayout mRightLayout;
    //右侧图标控件，默认展示图标
    private ImageView mIvRightIcon;
    //右侧文本控件
    private TextView mTvRightText;
    //右侧图标展示风格
    private int mRightStyle = 0;

    //点击事件
    private OnLSettingItemClick mOnLSettingItemClick;


    public MySettingItemView(Context context) {
        this(context,null);
    }

    public MySettingItemView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public MySettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        getCustomStyle(context,attrs);
        //获取到右侧的展示风格，进行样式切换
        switchRightStyle(mRightStyle);
        mRootLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                clickOn();
            }
        });
    }

    public void setOnLSettingItemClick(OnLSettingItemClick mOnLSettingItemClick){
        this.mOnLSettingItemClick = mOnLSettingItemClick;
    }

    private void clickOn() {
        if(null!= mOnLSettingItemClick){
            mOnLSettingItemClick.click();
        }

    }


    private void initView(Context context) {
        mView = View.inflate(context,R.layout.settingitem,this);
        mRootLayout = (RelativeLayout) mView.findViewById(R.id.rootLayout);
        mUnderLine = mView.findViewById(R.id.underline);
        mTvLeftText = (TextView) mView.findViewById(R.id.tv_left_text);
        mRightLayout = (FrameLayout) mView.findViewById(R.id.right_layout);
        mIvRightIcon = (ImageView) mView.findViewById(R.id.iv_right_icon);
        mTvRightText = (TextView) mView.findViewById(R.id.tv_right_text);
    }

    private void getCustomStyle(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.MySettingView);
        int n = a.getIndexCount();
        for(int i = 0; i<n; i++){
            int attr = a.getIndex(i);
            if(attr == R.styleable.MySettingView_leftText){
                mLeftText = a.getString(attr);
                mTvLeftText.setText(mLeftText);
            }else if(attr == R.styleable.MySettingView_rightText){
                mRightText = a.getString(attr);
                mTvRightText.setText(mRightText);
            }else if(attr == R.styleable.MySettingView_mtextSize){
                mTextSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                mTvLeftText.setTextSize(mTextSize);
                mTvRightText.setTextSize(mTextSize);
            }else if(attr == R.styleable.MySettingView_textColor){
                mTextColor = a.getColor(attr, Color.LTGRAY);
                mTvLeftText.setTextColor(mTextColor);
                mTvRightText.setTextColor(mTextColor);
            }else if(attr == R.styleable.MySettingView_rightStyle){
                mRightStyle = a.getInt(attr,0);
            }else if(attr == R.styleable.MySettingView_isShowUnderLine){
                //默认显示分割线
                if(!a.getBoolean(attr,true)){
                    mUnderLine.setVisibility(View.GONE);
                }
            }
        }
        a.recycle();
    }

    private void switchRightStyle(int mRightStyle) {
        switch (mRightStyle){
            case 0:
                mIvRightIcon.setVisibility(View.VISIBLE);
                mTvRightText.setVisibility(View.GONE);
                break;
            case 1:
                mIvRightIcon.setVisibility(View.GONE);
                mTvRightText.setVisibility(View.VISIBLE);
                break;
        }
    }


    public interface OnLSettingItemClick{
        void click();
    }


}
