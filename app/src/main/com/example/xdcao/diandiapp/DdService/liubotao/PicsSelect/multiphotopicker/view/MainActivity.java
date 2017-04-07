package com.example.xdcao.diandiapp.DdService.liubotao.PicsSelect.multiphotopicker.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import com.example.xdcao.diandiapp.DdService.liubotao.PicsSelect.multiphotopicker.adapter.ImageSelectResultAdapter;
import com.example.xdcao.diandiapp.DdService.liubotao.PicsSelect.multiphotopicker.model.ImageItem;
import com.example.xdcao.diandiapp.DdService.liubotao.PicsSelect.multiphotopicker.util.CustomConstants;
import com.example.xdcao.diandiapp.DdService.liubotao.PicsSelect.multiphotopicker.util.IntentConstants;
import com.example.xdcao.diandiapp.R;

/**
 * 主界面，显示已选中返回的图片
 * 
 * @author assur
 *
 */
public class MainActivity extends Activity
{
	private GridView mGridView;
	private ImageSelectResultAdapter mAdapter;
	public static List<ImageItem> mDataList = new ArrayList<ImageItem>();

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picgrid);

		initData();
		initView();
	}

	private void initData()
	{
		List<ImageItem> incomingDataList = (List<ImageItem>) getIntent()
				.getSerializableExtra(IntentConstants.EXTRA_IMAGE_LIST);

		Log.e("temp", "temp");

		if(incomingDataList!=null) {
			Log.e("temp", "temp2");

			for (int i = 0; i < incomingDataList.size(); i++) {
				Log.e("temp", incomingDataList.get(i).sourcePath);
			}
		}else{
			Log.e("temp", "temp3");
		}

		if (incomingDataList != null)
		{
			mDataList.addAll(incomingDataList);
		}
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		notifyDataChanged(); 
	}

	public void initView()
	{


		mGridView = (GridView) findViewById(R.id.gridview1);

		mGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		mAdapter = new ImageSelectResultAdapter(this, mDataList);
		mGridView.setAdapter(mAdapter);
		mGridView.setOnItemClickListener(new OnItemClickListener()
		{
			public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id)
			{
				if (position == getDataSize())
				{	
					Intent intent = new Intent(MainActivity.this, ImageChooseActivity.class);
					intent.putExtra(IntentConstants.EXTRA_CAN_ADD_IMAGE_SIZE, CustomConstants.MAX_IMAGE_SIZE);
					startActivityForResult(intent, 0);		
				}
				
			}
		});
	}

	private int getDataSize()
	{
		return mDataList == null ? 0 : mDataList.size();
	}

	public String getString(String s)
	{
		String path = null;
		if (s == null) return "";
		for (int i = s.length() - 1; i > 0; i++)
		{
			s.charAt(i);
		}
		return path;
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if(resultCode == 0) {
	
		} else if(resultCode == 1) {
			List<ImageItem> incomingDataList = (List<ImageItem>) data.getSerializableExtra(IntentConstants.EXTRA_IMAGE_LIST);
			if (incomingDataList != null)
			{
				for(int i=0;i<incomingDataList.size();i++){
					Log.e("tttt",incomingDataList.get(i).sourcePath);
				}
				mDataList.addAll(incomingDataList);
				notifyDataChanged();
				initView();
			}
		}
	}

	private void notifyDataChanged()
	{
		mAdapter.notifyDataSetChanged();
	}

}