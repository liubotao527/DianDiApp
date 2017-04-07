package com.example.xdcao.diandiapp.DdService.liubotao.PicsSelect.multiphotopicker.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.xdcao.diandiapp.DdService.liubotao.PicsSelect.multiphotopicker.adapter.ImageBucketAdapter;
import com.example.xdcao.diandiapp.DdService.liubotao.PicsSelect.multiphotopicker.adapter.ImageGridAdapter;
import com.example.xdcao.diandiapp.DdService.liubotao.PicsSelect.multiphotopicker.model.ImageBucket;
import com.example.xdcao.diandiapp.DdService.liubotao.PicsSelect.multiphotopicker.model.ImageItem;
import com.example.xdcao.diandiapp.DdService.liubotao.PicsSelect.multiphotopicker.util.CustomConstants;
import com.example.xdcao.diandiapp.DdService.liubotao.PicsSelect.multiphotopicker.util.ImageFetcher;
import com.example.xdcao.diandiapp.DdService.liubotao.PicsSelect.multiphotopicker.util.IntentConstants;
import com.example.xdcao.diandiapp.R;

/**
 * 图片选择界面
 * 
 * @author assur
 *
 */
public class ImageChooseActivity extends Activity implements ImageGridAdapter.SeleteChangeCallBack{
	
	// 相片列表相关
	public static final int REQUEST_CODE = 1;
	private ImageFetcher mHelper;
	private List<ImageItem> mDataList = new ArrayList<ImageItem>();
	private String mBucketName;
	private int availableSize;
	private GridView mGridView;
	private Button backBtn;
	private TextView headerTitleTv;
	private Button selectBtn;
	private Button selectPreviewBtn;
	private ImageGridAdapter mAdapter;
	private Button mSelectImageBucketBtn;
	private HashMap<String, ImageItem> selectedImgs = new HashMap<String, ImageItem>();
	
	// 相册列表相关
	private LinearLayout imageBucketLayout;
	private LinearLayout imageBucketLayoutBg;
	private ListView imageBucketListView;
	private List<ImageBucket> mImageBucketList = new ArrayList<ImageBucket>();
	private ImageBucketAdapter imageBucketAdapter;
	private int currentBucketIndex = 0;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_choose);

		showFirstBucketDefault();
		initView();
		initListener();
	}
	
	/**
	 * 默认显示第一个相册的图片
	 */
	private void showFirstBucketDefault() {
		mHelper = ImageFetcher.getInstance(getApplicationContext());

		mImageBucketList = mHelper.getImagesBucketList(false);

		ImageBucket imageBucket = mImageBucketList.get(currentBucketIndex);
		mDataList = imageBucket.imageList;
		mBucketName = imageBucket.bucketName;
		availableSize = CustomConstants.MAX_IMAGE_SIZE;
		if (mDataList == null) mDataList = new ArrayList<ImageItem>();
		if (TextUtils.isEmpty(mBucketName))
		{
			mBucketName = "相册为空";
		}
	}
	
	
	/**
	 * 控件初始化
	 */
	private void initView() {

		mGridView = (GridView) findViewById(R.id.gridview);
		mGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		mAdapter = new ImageGridAdapter(ImageChooseActivity.this, mDataList, this);
		mGridView.setAdapter(mAdapter);
		mSelectImageBucketBtn = (Button) findViewById(R.id.select_imagebucket_btn);
		backBtn =  (Button) findViewById(R.id.back_btn);
		headerTitleTv = (TextView) findViewById(R.id.tv_headerTitle);
		selectBtn = (Button) findViewById(R.id.select_btn);
		selectPreviewBtn = (Button) findViewById(R.id.select_preview_btn);
		changeBtnStatu(0);
		
		imageBucketLayout = (LinearLayout) findViewById(R.id.imagebucket_layout);
		imageBucketLayoutBg = (LinearLayout) findViewById(R.id.imagebucket_layout_bg);
		imageBucketListView = (ListView) findViewById(R.id.imagebucket_listview);
		imageBucketAdapter = new ImageBucketAdapter(this, mImageBucketList);
		imageBucketListView.setAdapter(imageBucketAdapter);
		
		headerTitleTv.setText(mBucketName);	
		mAdapter.notifyDataSetChanged();
	}

	/**
	 * 监听事件初始化
	 */
	private void initListener() {
		
		// 按钮监听事件，弹出/移除相册列表界面
		mSelectImageBucketBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(imageBucketLayout.getVisibility() == View.GONE) {
					for(int i = 0; i < mDataList.size(); i++) {
						mDataList.get(i).isSelected = false;
					}
					mAdapter.notifyDataSetChanged();
					selectedImgs.clear();
					changeBtnStatu(0);
				}
				showHideBucketLayout();
			}
		});
		
		// 相册列表单项点击事件
		imageBucketListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				currentBucketIndex = position;
				ImageBucket imageBucket = mImageBucketList.get(currentBucketIndex);
				mDataList = imageBucket.imageList;
				mBucketName = imageBucket.bucketName;
				showHideBucketLayout();
				mAdapter = new ImageGridAdapter(ImageChooseActivity.this, mDataList, ImageChooseActivity.this);
				mGridView.setAdapter(mAdapter);
				headerTitleTv.setText(mBucketName);		
			}
		});
		

		// 图片列表单项点击事件
		mGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// 预览界面
				startPreViewActivity(mDataList, position);
			}
		});

		// 返回按钮点击事件
		backBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setResult(0);
				finish();
			}
		});

		// 选择完成按钮点击事件
		selectBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				List<ImageItem> selectedImgsList = new ArrayList<ImageItem>(selectedImgs.values());
				Intent intent = new Intent(ImageChooseActivity.this, MainActivity.class);
				intent.putExtra(IntentConstants.EXTRA_IMAGE_LIST, (Serializable) selectedImgsList);
				setResult(1, intent);
				finish();
			}
		});
		
		// 预览按钮点击事件
		selectPreviewBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// 进入预览界面
				List<ImageItem> selectedImgsList = new ArrayList<ImageItem>(selectedImgs.values());
				// 对selectedImgsList进行排序，从按照position由小到大排序
				int size = selectedImgsList.size();
				ImageItem temp;
				for (int i = 0; i < size; i++) {
				   for (int j = i+1; j < size; j++) {
					    if (selectedImgsList.get(i).listPosition > selectedImgsList.get(j).listPosition) {
					    	temp = selectedImgsList.get(i);
					    	selectedImgsList.remove(i);
					    	selectedImgsList.add(i, selectedImgsList.get(j-1));
					    	selectedImgsList.remove(j);
					    	selectedImgsList.add(j, temp);
					    }
				   }
				}
				
				startPreViewActivity(selectedImgsList, 0);
			}
		});
	}	
	
	
	/**
	 * 改变预览按钮状态和文字显示
	 * @param selectNum
	 */
	private void changeBtnStatu(int selectNum) {
		if(selectNum > 0) {
			selectBtn.setEnabled(true);
			selectBtn.setText("确定(" + selectNum + "/"+ availableSize + ")");
			selectPreviewBtn.setEnabled(true);
			selectPreviewBtn.setText("预览(" + selectedImgs.size() + ")");
		} else {
			selectBtn.setEnabled(false);
			selectBtn.setText("确定");
			selectPreviewBtn.setEnabled(false);
			selectPreviewBtn.setText("预览");
		}
		
	}
	

	/**
	 * 启动图片预览界面
	 * @param data
	 * @param startPosition
	 */
	private void startPreViewActivity(List<ImageItem> datas, int startPosition) {
		// 获取选中的图片position集合(注意：是ImageItem对象里面的成员listPosition)
		ArrayList<Integer> selectImagePositionList = new ArrayList<Integer>();
		List<ImageItem> selectedImgsList = new ArrayList<ImageItem>(selectedImgs.values());
		for(int i = 0; i < selectedImgsList.size(); i++) {
			selectImagePositionList.add(selectedImgsList.get(i).listPosition);
		}
		
		Intent intent = new Intent(ImageChooseActivity.this,ImageZoomActivity.class);
		intent.putExtra(IntentConstants.EXTRA_IMAGE_LIST,(Serializable) datas);
		intent.putIntegerArrayListExtra(IntentConstants.EXTRA_SELECT_IMAGE_POSITION_LIST, selectImagePositionList);
		intent.putExtra(IntentConstants.EXTRA_CURRENT_IMG_POSITION, startPosition);	
		startActivityForResult(intent, REQUEST_CODE);
	}
	
	/**
	 * 弹出/移除相册列表界面
	 */
	private void showHideBucketLayout() {
		if(imageBucketLayout.getVisibility() == View.GONE) {
			imageBucketLayoutBg.setVisibility(View.VISIBLE);
			imageBucketLayoutBg.setAnimation(getAlphaAnimation(true));
			imageBucketLayout.setVisibility(View.VISIBLE);
			imageBucketLayout.startAnimation(getTranslationAnimation(true));	
			imageBucketListView.setEnabled(true);
		} else {
			imageBucketLayoutBg.setVisibility(View.GONE);
			imageBucketLayoutBg.setAnimation(getAlphaAnimation(false));
			imageBucketLayout.setVisibility(View.GONE);
			imageBucketLayout.startAnimation(getTranslationAnimation(false));
			imageBucketListView.setEnabled(false);
		}
	}
	
	/**
	 * 相册列表界面 弹出/移除动画
	 * @param show
	 * @return
	 */
	private Animation getTranslationAnimation(boolean show) {
		Animation anim;
		if(show) {
			anim = new TranslateAnimation(
	                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
	                Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
		} else {
			anim = new TranslateAnimation(
	                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
	                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f);
		}  
		anim.setDuration(300);  
		return anim;
	}
	
	/**
	 * 相册列表界面弹出/移除时，背景渐变动画
	 * @param show
	 * @return
	 */
	private Animation getAlphaAnimation(boolean show) {
		Animation anim;
		if(show) {
			anim = new AlphaAnimation(0.0f, 1.0f);
			
		} else {
			anim = new AlphaAnimation(1.0f, 0.0f);
		}  
		anim.setDuration(300);  
		return anim;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 按下返回键时也会执行此函数，但是resultCode=0，把返回键过滤掉
		if(REQUEST_CODE == resultCode) {
			// 获取预览界面选中的图片position集合，判断是否有选中的，更新界面
			ArrayList<Integer> selectPositionList = data.getIntegerArrayListExtra(IntentConstants.EXTRA_SELECT_IMAGE_POSITION_LIST);
			ArrayList<Integer> unSelectPositionList = data.getIntegerArrayListExtra(IntentConstants.EXTRA_UNSELECT_IMAGE_POSITION_LIST);
			if (selectPositionList != null){
				selectedImgs.clear();
				for(int i = 0; i < selectPositionList.size(); i++) {
					ImageItem imageItem = mDataList.get(selectPositionList.get(i));
					Log.e("path",imageItem.sourcePath);
					selectedImgs.put(imageItem.imageId, imageItem);
					mDataList.get(selectPositionList.get(i)).isSelected = true;
					
				}
				for(int i = 0; i < unSelectPositionList.size(); i++) {
					mDataList.get(unSelectPositionList.get(i)).isSelected = false;
				}
				changeBtnStatu(selectedImgs.size());
				mAdapter.notifyDataSetChanged();
			} 
		} 
		
	}
	
	/**
	 *  适配器中选中/取消选中回调接口
	 * @param position
	 */
	@Override
	public void selectChange(int position) {
		// TODO Auto-generated method stub
		ImageItem item = mDataList.get(position);
		if (item.isSelected) {
			item.isSelected = false;
			selectedImgs.remove(item.imageId);
		} else {
			if (selectedImgs.size() >= availableSize) {
				Toast.makeText(ImageChooseActivity.this,
						"最多选择" + availableSize + "张图片",
						Toast.LENGTH_SHORT).show();
				return;
			}
			item.isSelected = true;
			selectedImgs.put(item.imageId, item);
		}
		
		changeBtnStatu(selectedImgs.size());
		
		mAdapter.notifyDataSetChanged();
	
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// 因为是单例模式，图片数据依赖它，所以实例要销毁，再次进入此activity需要重新加载图片数据
		ImageFetcher.destoryInstance();	
	}
}