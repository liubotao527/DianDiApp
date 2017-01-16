package com.example.xdcao.diandiapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by xdcao on 2017/1/14.
 */

public class LostAdapter extends BaseAdapter {

    private List<Lost> losts=null;
    private Context context;
    private LayoutInflater itemContainer;

    public LostAdapter(Context context,List<Lost> losts) {
        this.context = context;
        this.losts=losts;
        itemContainer=LayoutInflater.from(context);
    }

    public class ItemView{
        public TextView title_item_view;
        public TextView describe_item_view;
        public Button phone_item_view;
    }

    public void addAll(List<Lost> losts){
        this.losts=losts;
    }

    @Override
    public int getCount() {
        return losts.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        System.out.print("get view ..............");

            ItemView itemView = null;
            if (convertView == null) {
                itemView = new ItemView();
                //获取list_item布局文件的视图
                convertView = itemContainer.inflate(R.layout.item_view, null);
                //获取控件对象
                itemView.title_item_view = (TextView)convertView.findViewById(R.id.title_item_view);
                itemView.describe_item_view = (TextView)convertView.findViewById(R.id.describe_item_view);
                itemView.phone_item_view= (Button)convertView.findViewById(R.id.phone_item_view);
                //设置控件集到convertView
                convertView.setTag(itemView);
            }else {
                itemView = (ItemView)convertView.getTag();
            }
          Log.e("image", (String)losts.get(position).getDescribe());  //测试
          Log.e("image", (String)losts.get(position).getTitle());

            //设置文字和图片
            itemView.title_item_view.setText(losts.get(position).getTitle());
            itemView.describe_item_view.setText(losts.get(position).getDescribe());
            itemView.phone_item_view.setText(losts.get(position).getPhone());

            return convertView;

    }
}
