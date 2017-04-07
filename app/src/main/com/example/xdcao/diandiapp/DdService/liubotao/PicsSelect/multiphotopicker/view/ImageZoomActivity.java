package com.example.xdcao.diandiapp.DdService.liubotao.PicsSelect.multiphotopicker.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.xdcao.diandiapp.DdService.liubotao.PicsSelect.multiphotopicker.model.ImageItem;
import com.example.xdcao.diandiapp.DdService.liubotao.PicsSelect.multiphotopicker.util.CustomConstants;
import com.example.xdcao.diandiapp.DdService.liubotao.PicsSelect.multiphotopicker.util.ImageDisplayer;
import com.example.xdcao.diandiapp.DdService.liubotao.PicsSelect.multiphotopicker.util.IntentConstants;
import com.example.xdcao.diandiapp.DdService.liubotao.PicsSelect.multiphotopicker.view.zoomphotoview.PhotoView;
import com.example.xdcao.diandiapp.R;

/**
 * 图片预览缩放界面
 * 
 * @author assur
 *
 */

public class ImageZoomActivity extends Activity
{

	private ViewPager pager;
	private MyPageAdapter adapter;
	private int currentPosition;
	private List<ImageItem> mDataList = new ArrayList<ImageItem>();
	private TextView photoTextViewNum;
	private CheckBox selectCheckBox;
	private HashMap<String, Integer> selectImagePositionMap = new HashMap<String, Integer>();
	private HashMap<String, Integer> unSelectImagePositionMap = new HashMap<String, Integer>();
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_zoom);

		// 初始化图片数据
		initData();
		// 返回按钮
		Button photo_bt_exit = (Button) findViewById(R.id.photo_bt_exit);
		photo_bt_exit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finishActivity();
			}
		});
		
		photoTextViewNum = (TextView) findViewById(R.id.photo_tv_num);
		photoTextViewNum.setText((currentPosition + 1) + "/" + mDataList.size());
		
		// 选择勾选框
		selectCheckBox = (CheckBox) findViewById(R.id.select_checkbox);
		selectCheckBox.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// 判断是否已经大于最大图片数目（9）						
				if(selectImagePositionMap.size() >= CustomConstants.MAX_IMAGE_SIZE) {
					// 如果当前是选中状态，可以让其取消
					if(selectImagePositionMap.containsKey(mDataList.get(currentPosition).listPosition + "")) {
						return false;
					} else {
						if(arg1.getAction() == MotionEvent.ACTION_DOWN) {
							Toast.makeText(ImageZoomActivity.this, "最多选择" + CustomConstants.MAX_IMAGE_SIZE + "张图片", Toast.LENGTH_SHORT).show();
						}
						return true;
					}
				} else {
					return false;
				}
				
			}
		});
		
		selectCheckBox.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				int listPosition = mDataList.get(currentPosition).listPosition;
				if(selectCheckBox.isChecked()) {
					mDataList.get(currentPosition).isSelected = true;
					selectImagePositionMap.put(listPosition+"", listPosition);
					unSelectImagePositionMap.remove(listPosition+"");
				} else {
					mDataList.get(currentPosition).isSelected = false;
					selectImagePositionMap.remove(listPosition+"");	
					unSelectImagePositionMap.put(listPosition+"", listPosition);
				}
				
			}
		});
		

		// ViewPager和MyPageAdapter初始化
		pager = (ViewPager) findViewById(R.id.viewpager);
		pager.setOnPageChangeListener(pageChangeListener);
		pager.setOffscreenPageLimit(2);  //设置缓存view 的个数（实际有3个，缓存2个+正在显示的1个）
		pager.setPageMargin(50);         //设置viewpager每个页卡的间距，与gallery的spacing属性类似
		adapter = new MyPageAdapter(mDataList);
		pager.setAdapter(adapter);
		pager.setCurrentItem(currentPosition);
		
		if(mDataList.get(currentPosition).isSelected) {
			selectCheckBox.setChecked(true);
		}
	}

	/**
	 * 初始化图片数据
	 */
	private void initData()
	{
		// 获取第一个显示的图片position
		currentPosition = getIntent().getIntExtra(IntentConstants.EXTRA_CURRENT_IMG_POSITION, 0);
		// 获取第所有要显示的图片集合
		List<ImageItem> incomingDataList = (List<ImageItem>) getIntent().getSerializableExtra(IntentConstants.EXTRA_IMAGE_LIST);
		if (incomingDataList != null)
		{
			mDataList.addAll(incomingDataList);		
		}
		// 获取已选中的图片position集合，并保存selectImagePositionMap，保持同步
		ArrayList<Integer> selectImagePositionList = getIntent().getIntegerArrayListExtra(IntentConstants.EXTRA_SELECT_IMAGE_POSITION_LIST);
		if(selectImagePositionList != null) {
			for(int i = 0; i < selectImagePositionList.size(); i++) {
				selectImagePositionMap.put(selectImagePositionList.get(i) + "", selectImagePositionList.get(i));	
			}
		}
	}


	/**
	 * 页面改变监听事件
	 */
	private OnPageChangeListener pageChangeListener = new OnPageChangeListener()
	{
		public void onPageSelected(int arg0)
		{
			
			currentPosition = arg0;
			if(selectImagePositionMap.containsKey(currentPosition+"") || mDataList.get(currentPosition).isSelected) {
				selectCheckBox.setChecked(true);
			} else {
				selectCheckBox.setChecked(false);
			}
			
			photoTextViewNum.setText((currentPosition + 1) + "/" + mDataList.size());
			
		}

		public void onPageScrolled(int arg0, float arg1, int arg2)
		{
		}

		public void onPageScrollStateChanged(int arg0)
		{
		}
	};

	/**
	 * 自定义PagerAdapter适配器
	 * 
	 * @author assur
	 *
	 */
	class MyPageAdapter extends PagerAdapter
	{
		private List<ImageItem> dataList = new ArrayList<ImageItem>();
		private ArrayList<ImageView> mViews = new ArrayList<ImageView>();

		public MyPageAdapter(List<ImageItem> dataList)
		{
			this.dataList = dataList;
			
		}

		public int getItemPosition(Object object)
		{
			return POSITION_NONE;
		}

		public Object instantiateItem(View arg0, int arg1)
		{
			/*ImageView iv = new ImageView(ImageZoomActivity.this);
			ImageDisplayer.getInstance(ImageZoomActivity.this).displayBmp(
					iv, null, dataList.get(arg1).sourcePath, false);
			iv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT));
			((ViewPager) arg0).addView(iv);*/
			
			PhotoView photoView = new PhotoView(ImageZoomActivity.this);
			ImageDisplayer.getInstance(ImageZoomActivity.this).displayBmp(
					photoView, null, dataList.get(arg1).sourcePath, false);
			photoView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT));
			((ViewPager) arg0).addView(photoView);
			
			return photoView;
		}

		public void destroyItem(View arg0, int arg1, Object arg2)
		{
	
			((ViewPager) arg0).removeView((ImageView)arg2);
		}

		public boolean isViewFromObject(View arg0, Object arg1)
		{
			return arg0 == arg1;
		}

		@Override
		public int getCount()
		{
			return dataList.size();
		}

		public void removeView(int position)
		{
			if (position + 1 <= mViews.size())
			{
				mViews.remove(position);
			}
		}
	}
	
	/**
	 * 返回结果，结束此界面
	 */
    private void finishActivity() {
    	ArrayList<Integer> selectImagePositionList = new ArrayList<Integer>(selectImagePositionMap.values());
		ArrayList<Integer> unSelectImagePositionList = new ArrayList<Integer>(unSelectImagePositionMap.values());
		Intent intent = new Intent(ImageZoomActivity.this, ImageChooseActivity.class);
		intent.putIntegerArrayListExtra(IntentConstants.EXTRA_SELECT_IMAGE_POSITION_LIST, selectImagePositionList);
		intent.putIntegerArrayListExtra(IntentConstants.EXTRA_UNSELECT_IMAGE_POSITION_LIST, unSelectImagePositionList);	
		setResult(ImageChooseActivity.REQUEST_CODE,intent);
		finish();
    }
	
    /**
     * 返回按钮拦截
     */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		 
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
        	finishActivity();
            return true;
         }
         return super.onKeyDown(keyCode, event);
     }

}