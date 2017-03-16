package com.example.xdcao.diandiapp.BackUp.caohao.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.xdcao.diandiapp.BackUp.caohao.bean.MyUser;
import com.example.xdcao.diandiapp.R;

import java.util.List;

/**
 * Created by xdcao on 2017/1/17.
 */

public class FriendAdapter extends BaseAdapter{


    private List<MyUser> friends=null;
    private Context context;
    private LayoutInflater itemContainer;

    public FriendAdapter(Context context,List<MyUser> friends) {
        this.context = context;
        this.friends=friends;
        itemContainer=LayoutInflater.from(context);
    }

    public class FriendItemView{
        public TextView username_item_view;
        public TextView usersex_item_view;
        public TextView userage_item_view;
    }

    public void addAll(List<MyUser> friends){
        this.friends=friends;
    }

    @Override
    public int getCount() {
        return friends.size();
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

        FriendItemView friendItemView = null;
        if (convertView == null) {
            friendItemView = new FriendItemView();
            //获取list_item布局文件的视图
            convertView = itemContainer.inflate(R.layout.friend_item_view, null);
            //获取控件对象
            friendItemView.username_item_view = (TextView)convertView.findViewById(R.id.username_item_view);
            friendItemView.usersex_item_view = (TextView)convertView.findViewById(R.id.usersex_item_view);
            friendItemView.userage_item_view= (TextView) convertView.findViewById(R.id.userage_item_view);
            //设置控件集到convertView
            convertView.setTag(friendItemView);
        }else {
            friendItemView = (FriendItemView) convertView.getTag();
        }

        //设置文字和图片
        friendItemView.username_item_view.setText(friends.get(position).getUsername());
        friendItemView.usersex_item_view.setText(friends.get(position).getSex()?"男":"女");
        friendItemView.userage_item_view.setText(friends.get(position).getAge().toString());

        return convertView;

    }
}
