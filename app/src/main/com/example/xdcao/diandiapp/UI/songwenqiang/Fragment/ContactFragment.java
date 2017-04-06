package com.example.xdcao.diandiapp.UI.songwenqiang.Fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xdcao.diandiapp.BackUp.caohao.activity.FriendsActivity;
import com.example.xdcao.diandiapp.BackUp.caohao.adapters.FriendAdapter;
import com.example.xdcao.diandiapp.BackUp.caohao.bean.MyUser;
import com.example.xdcao.diandiapp.BackUp.caohao.cons.HandlerCons;
import com.example.xdcao.diandiapp.R;
import com.example.xdcao.diandiapp.UI.songwenqiang.bean.ContactItem;
import com.example.xdcao.diandiapp.UI.songwenqiang.ui.DetailActivity;
import com.example.xdcao.diandiapp.UI.songwenqiang.ui.MainFragment;
import com.example.xdcao.diandiapp.UI.songwenqiang.ui.widget.RoundImageView;
import com.example.xdcao.diandiapp.UI.songwenqiang.utils.SnackbarUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by wewarrios on 2017/3/14.
 */

public class ContactFragment extends Fragment{
    private RecyclerView recyclerView;
    private CoordinatorLayout coordinatorLayout;
    private List<ContactItem> mContactList;
    private LinearLayoutManager mLayoutManager;
    //    private SwipeRefreshLayout swipeRefreshLayout;
    private Context context;

    Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case HandlerCons.QUERY_ALL_USER:
                    Log.d(TAG, "handleMessage: "+"get handler mList.size: "+mContactList.size());
                    ContactAdapter ContactAdapter = new ContactAdapter();
                    recyclerView.setAdapter(ContactAdapter);
            }
            super.handleMessage(msg);
        }
    };

    private static final String TAG = "ContactFragment";

    public ContactFragment(){

    }


    @Override
    public void onStart() {
        super.onStart();
        new QueryForUsersThread().start();
        ContactAdapter ContactAdapter = new ContactAdapter();
        recyclerView.setAdapter(ContactAdapter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContactList = new ArrayList<>();
//        for(int i=0;i<10;i++){
//            ContactItem contactItem = new ContactItem();
//            contactItem.setNickName("dsdsdd"+i);
//            contactItem.setSignName("sdsdsfgggggg"+i);
//            mContactList.add(contactItem);
//        }
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        System.out.print(context);// Activity@890778
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.context = null;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.line_coordinatorLayout);
        recyclerView = (RecyclerView)view.findViewById(R.id.line_recycler);
        mLayoutManager = new LinearLayoutManager(context);

        recyclerView.setLayoutManager(mLayoutManager);
//        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.line_swipe_refresh);
//        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,R.color.colorPrimaryDark,R.color.colorAccent);
//        swipeRefreshLayout.setProgressViewOffset(false,0,(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,24,getResources().getDisplayMetrics()));
        setListener();
    }

    private void setListener() {
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//
//            }
//        });
//        recyclerView.addItemDecoration(new DividerItemDecoration(context,DividerItemDecoration.VERTICAL));
        ContactAdapter ContactAdapter = new ContactAdapter();
        recyclerView.setAdapter(ContactAdapter);


    }

    class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> implements View.OnClickListener{


        @Override
        public ContactAdapter.ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.contact_item_layout,parent,false);
            ContactViewHolder holder = new ContactViewHolder(view);
            view.setOnClickListener(this);
            return holder;
        }

        @Override
        public void onBindViewHolder(ContactViewHolder holder, int position) {
            holder.mTvName.setText(mContactList.get(position).getNickName());
            holder.mTvSign.setText(mContactList.get(position).getSignName());

//            holder.iv_content.setImageResource();

        }

        @Override
        public int getItemCount() {
            return mContactList.size();
        }

        @Override
        public void onClick(View v) {
            int position = recyclerView.getChildAdapterPosition(v);
            Intent intent = new Intent(getActivity(),DetailActivity.class);
            //将数据传到DetailActivity
            startActivity(intent);
        }

        class ContactViewHolder extends RecyclerView.ViewHolder{
            private TextView mTvName;
            private RoundImageView mRivPhoto;
            private TextView mTvSign;
            public ContactViewHolder(View view){
                super(view);
                mRivPhoto = (RoundImageView) view.findViewById(R.id.riv_photo);
                mTvName = (TextView) view.findViewById(R.id.tv_name);
                mTvSign = (TextView) view.findViewById(R.id.tv_sign);
            }
        }
        public void addItem(ContactItem contactItem,int position){
            mContactList.add(position,contactItem);
            notifyItemInserted(position);
            recyclerView.scrollToPosition(position);
        }

        public void removeItem(final int position){
            final ContactItem removed = mContactList.get(position);
            mContactList.remove(position);
            notifyItemRemoved(position);
            SnackbarUtil.ShortSnackbar(coordinatorLayout,"你删除了第"+position+"个item",SnackbarUtil.Warning).setAction("撤销", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addItem(removed,position);
                    SnackbarUtil.ShortSnackbar(coordinatorLayout,"撤销了删除第"+position+"个item",SnackbarUtil.Confirm).show();
                }
            }).setActionTextColor(Color.WHITE).show();
        }
    }

    private class QueryForUsersThread extends Thread{
        @Override
        public void run() {
            queryUsers();
        }
    }

    private void queryUsers() {
        BmobQuery<MyUser> query=new BmobQuery<MyUser>();
        query.findObjects(new FindListener<MyUser>() {
            @Override
            public void done(List<MyUser> list, BmobException e) {
                boolean isSend=false;
                if(e==null){
                    Log.d(TAG, "done: "+"success, size:"+list.size());
                    for(MyUser myUser:list){
                        ContactItem contactItem=new ContactItem();
                        contactItem.setNickName(myUser.getUsername());
                        mContactList.add(contactItem);
                    }
                    isSend=true;
                    System.out.print("查询用户成功： 用户数： "+list.size());
                }else {
                    System.out.print("查询用户失败");
                }

                if (isSend){
                    Message message=new Message();
                    message.what= HandlerCons.QUERY_ALL_USER;
                    handler.sendMessage(message);
                }

            }
        });
    }



}
