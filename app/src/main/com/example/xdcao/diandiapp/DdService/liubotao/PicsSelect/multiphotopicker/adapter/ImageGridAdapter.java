package com.example.xdcao.diandiapp.DdService.liubotao.PicsSelect.multiphotopicker.adapter;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


import com.example.xdcao.diandiapp.DdService.liubotao.PicsSelect.multiphotopicker.model.ImageItem;
import com.example.xdcao.diandiapp.DdService.liubotao.PicsSelect.multiphotopicker.util.ImageDisplayer;
import com.example.xdcao.diandiapp.R;

/**
 * 相片列表适配器
 * @author assur
 *
 */

public class ImageGridAdapter extends BaseAdapter
{
	public interface SeleteChangeCallBack {
		  public void selectChange(int position);  
	}

	
	private Context mContext;
	private List<ImageItem> mDataList;
	private SeleteChangeCallBack seleteChangeCallBack;
	
	public ImageGridAdapter(Context context, List<ImageItem> dataList, SeleteChangeCallBack change)
	{
		this.mContext = context;
		this.mDataList = dataList;
		seleteChangeCallBack = change;
	}

	@Override
	public int getCount()
	{
		return mDataList == null ? 0 : mDataList.size();
	}

	@Override
	public Object getItem(int position)
	{
		return mDataList.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		final ViewHolder mHolder;

		if (convertView == null)
		{
			convertView = View.inflate(mContext, R.layout.item_image_list, null);
			mHolder = new ViewHolder();
			mHolder.imageIv = (ImageView) convertView.findViewById(R.id.image);
			mHolder.selectedBgIv = (ImageView) convertView.findViewById(R.id.selected_bg);
			mHolder.selectedIv = (ImageView) convertView.findViewById(R.id.selected_tag);
			mHolder.selectedBgTv = (TextView) convertView.findViewById(R.id.image_selected_bg);
			convertView.setTag(mHolder);
		}
		else
		{
			mHolder = (ViewHolder) convertView.getTag();
		}

		// 设置点击回调事件
		mHolder.selectedIv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				seleteChangeCallBack.selectChange(position);
			}
		});
		
		final ImageItem item = mDataList.get(position);

		ImageDisplayer.getInstance(mContext).displayBmp(mHolder.imageIv,
				item.thumbnailPath, item.sourcePath);

		if (item.isSelected)
		{
			mHolder.selectedIv.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tag_selected));
			//mHolder.selectedIv.setVisibility(View.VISIBLE);
			mHolder.selectedBgIv.setVisibility(View.VISIBLE);
			mHolder.selectedBgTv.setBackgroundResource(R.drawable.image_selected);
		}
		else
		{
			mHolder.selectedIv.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tag_unselected));
			//mHolder.selectedIv.setVisibility(View.VISIBLE);
			mHolder.selectedBgIv.setVisibility(View.GONE);
			mHolder.selectedBgTv.setBackgroundResource(R.color.light_gray);
		}

		return convertView;
	}

	static class ViewHolder
	{
		private ImageView imageIv;
		private ImageView selectedBgIv;
		private ImageView selectedIv;
		private TextView selectedBgTv;
	}
	

}
