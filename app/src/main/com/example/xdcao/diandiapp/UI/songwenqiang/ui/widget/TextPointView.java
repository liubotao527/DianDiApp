package com.example.xdcao.diandiapp.UI.songwenqiang.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.annotation.ColorRes;

import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.xdcao.diandiapp.R;
import com.example.xdcao.diandiapp.UI.songwenqiang.utils.DisplayUtil;

import java.util.Random;

import uk.co.chrisjenx.calligraphy.TypefaceUtils;

/**
 * Created by wewarrios on 2017/2/14.
 */

public class TextPointView extends FrameLayout {

    private final long random = new Random().nextLong();
    private Context context;
    private static final int DEFAULT_SIZE_DP = 50;
    private static final int DEFAULT_TEXT_SIZE = 16;
    private  int size;
    private String singleText;
    private  int textSize;
    private @ColorRes
    int circleColorRes;
    private View circleView;
    private TextView textView;
    public TextPointView(Context context) {
        this(context,null);
    }

    public TextPointView(Context context, AttributeSet attrs) {
       this(context,attrs,0);
    }

    public TextPointView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.TextPointView,0,0);
        singleText = typedArray.getString(R.styleable.TextPointView_text);
        circleColorRes = typedArray.getInt(R.styleable.TextPointView_redPointViewBgColor,R.color.white);
        textSize = typedArray.getDimensionPixelSize(R.styleable.TextPointView_textSize,DEFAULT_TEXT_SIZE);
        typedArray.recycle();

        circleView = new View(context);
        circleView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setCircleBackgroundColor(circleColorRes);


        textView = new TextView(context);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);

        params.gravity = Gravity.CENTER;
        textView.setLayoutParams(params);
        textView.setTypeface(TypefaceUtils.load(context.getAssets(),"fonts/jianshi_default.otf"));
        textView.setTextSize(textSize);
        textView.setTextColor(ContextCompat.getColor(context,R.color.colorPrimary));
        textView.setText(singleText);
        addView(circleView);
        addView(textView);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (MeasureSpec.getMode(widthMeasureSpec) != MeasureSpec.EXACTLY
                || MeasureSpec.getMode(heightMeasureSpec) != MeasureSpec.EXACTLY) {
            size = DisplayUtil.dip2px(context, DEFAULT_SIZE_DP);
        } else {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = MeasureSpec.getSize(heightMeasureSpec);
            size = Math.min(width, height);
        }
        super.onMeasure(MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY));
    }

    private void setCircleBackgroundColor(int circleColorRes) {
        ShapeDrawable circleShapeDrawable = new ShapeDrawable();
        circleShapeDrawable.setShape(new OvalShape());
        circleShapeDrawable.getPaint().setColor(ContextCompat.getColor(context, circleColorRes));
        circleView.setBackgroundDrawable(circleShapeDrawable);
    }


}
