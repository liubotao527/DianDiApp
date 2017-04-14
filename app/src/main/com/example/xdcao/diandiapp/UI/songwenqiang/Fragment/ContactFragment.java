package com.example.xdcao.diandiapp.UI.songwenqiang.Fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xdcao.diandiapp.BackUp.caohao.bean.MyUser;
import com.example.xdcao.diandiapp.BackUp.caohao.bean.Supply;
import com.example.xdcao.diandiapp.BackUp.caohao.cons.HandlerCons;
import com.example.xdcao.diandiapp.DdService.liubotao.ninegridlayout.util.ImageLoaderUtil;
import com.example.xdcao.diandiapp.R;
import com.example.xdcao.diandiapp.UI.songwenqiang.bean.ContactItem;
import com.example.xdcao.diandiapp.UI.songwenqiang.ui.ContactShareActivity;
import com.example.xdcao.diandiapp.UI.songwenqiang.ui.MainActivity;
import com.example.xdcao.diandiapp.UI.songwenqiang.ui.widget.RoundImageView;
import com.example.xdcao.diandiapp.UI.songwenqiang.utils.SnackbarUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;


/**
 * Created by wewarrios on 2017/3/14.
 */

public class ContactFragment extends Fragment{
    private RecyclerView recyclerView;
    private CoordinatorLayout coordinatorLayout;
    private List<ContactItem> mContactList;
    private LinearLayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Context context;
    private ImageLoader imageLoader;
    private final static int QUERY_SWIPE = 23;
    private static final String TAG = "ContactFragment";
    private ContactAdapter contactAdapter;



    Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case HandlerCons.UPDATE_FRIEND:
                    new QueryForUsersThread().start();
                case HandlerCons.QUERY_ALL_USER:
                    Log.d(TAG, "handleMessage: "+"get handler mList.size: "+mContactList.size());
                    contactAdapter = new ContactAdapter();
                    recyclerView.setAdapter(contactAdapter);
                    break;
                case QUERY_SWIPE:
                    swipeRefreshLayout.setRefreshing(false);
                    if(contactAdapter!=null){
                        contactAdapter.notifyDataSetChanged();
                    }

            }
            super.handleMessage(msg);
        }
    };



    public ContactFragment(){

    }




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContactList = new ArrayList<>();
        new UpdateMyFriends().start();

        initImageLoader();
    }

    private void initImageLoader() {
        imageLoader=ImageLoaderUtil.getImageLoader(this.context);
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
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.line_swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,R.color.colorPrimaryDark,R.color.colorAccent);
        swipeRefreshLayout.setProgressViewOffset(false,0,(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,24,getResources().getDisplayMetrics()));
        setListener();
    }

    private void setListener() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mContactList=new ArrayList<ContactItem>();
                //TODO 开启一个线程刷新数据
                BmobQuery<MyUser> query=new BmobQuery<MyUser>();
                MyUser me=MyUser.getCurrentUser(MyUser.class);
                query.addWhereRelatedTo("friends",new BmobPointer(me));
                query.findObjects(new FindListener<MyUser>() {
                    @Override
                    public void done(final List<MyUser> list, BmobException e) {
                        if(e==null){
                            Log.d("bmob", "done: "+"success, size:"+list.size());
                            for (MyUser myUser:list){
                                ContactItem contactItem=new ContactItem();
                                contactItem.setId(myUser.getObjectId());
                                contactItem.setNickName(myUser.getNickName());
                                contactItem.setAvatar(myUser.getAvatar());
                                contactItem.setMyUser(myUser);
                                mContactList.add(contactItem);
                            }

                            handler.sendEmptyMessage(QUERY_SWIPE);

                        }

                    }
                });

            }
        });
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

            if (mContactList.get(position).getAvatar()!=null){
                Bitmap bitmap=BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+ File.separator+mContactList.get(position).getAvatar().getFilename());
                if(bitmap!=null){
                    holder.mRivPhoto.setImageBitmap(bitmap);
                }else {
                    Log.d(TAG, "onBindViewHolder: imageloader");
                    imageLoader.displayImage(mContactList.get(position).getAvatar().getFileUrl(),holder.mRivPhoto,ImageLoaderUtil.getPhotoImageOption());
                }
            }
        }

        @Override
        public int getItemCount() {
            return mContactList.size();
        }

        @Override
        public void onClick(View v) {
            int position = recyclerView.getChildAdapterPosition(v);
            ContactItem contactItem = mContactList.get(position);
            Bundle bundle = new Bundle();
            bundle.putSerializable("contactItem",contactItem);
            Intent intent = new Intent(getActivity(),ContactShareActivity.class);
            //将数据传到DetailActivity
            intent.putExtras(bundle);
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

    private class UpdateMyFriends extends  Thread{
        @Override
        public void run() {
            updateMyFriends();
        }
    }



    /*
    查询好友列表
     */
    private void queryUsers() {
        BmobQuery<MyUser> query=new BmobQuery<MyUser>();
        MyUser me=MyUser.getCurrentUser(MyUser.class);
        query.addWhereRelatedTo("friends",new BmobPointer(me));
        query.findObjects(new FindListener<MyUser>() {
            @Override
            public void done(final List<MyUser> list, BmobException e) {
                if(e==null){
                    Log.d("bmob", "done: "+"success, size:"+list.size());
                    for (MyUser myUser:list){
                        ContactItem contactItem=new ContactItem();
                        contactItem.setId(myUser.getObjectId());
                        contactItem.setNickName(myUser.getNickName());
                        contactItem.setAvatar(myUser.getAvatar());
                        contactItem.setMyUser(myUser);
                        mContactList.add(contactItem);
                    }

                    Message message=new Message();
                    message.what= HandlerCons.QUERY_ALL_USER;
                    handler.sendMessage(message);

                }

            }
        });
    }

    private void updateMyFriends() {
        MyUser me= BmobUser.getCurrentUser(MyUser.class);
        BmobQuery<Supply> query=new BmobQuery<>();
        query.addWhereEqualTo("requester",me);
        BmobQuery<Supply> query1=new BmobQuery<>();
        query1.addWhereEqualTo("isAccepted",true);
        List<BmobQuery<Supply>> queries=new ArrayList<>();
        queries.add(query);
        queries.add(query1);
        BmobQuery<Supply> mainQuery=new BmobQuery<>();
        mainQuery.and(queries);
        mainQuery.findObjects(new FindListener<Supply>() {
            @Override
            public void done(List<Supply> list, BmobException e) {
                if (e==null){
                    if (list.size()>0){
                        for (final Supply supply:list){
                            Log.d("bmob", "done: "+supply.getResUserName());
                            BmobQuery<MyUser> query=new BmobQuery<MyUser>();
                            query.addWhereEqualTo("username",supply.getResUserName());
                            query.findObjects(new FindListener<MyUser>() {
                                @Override
                                public void done(List<MyUser> list, BmobException e) {
                                    if (e==null){
                                        if (list.size()>0){
                                            MyUser me=BmobUser.getCurrentUser(MyUser.class);
                                            if (me.getFriends()==null){
                                                BmobRelation bmobRelation=new BmobRelation();
                                                bmobRelation.add(list.get(0));
                                                me.setFriends(bmobRelation);
                                            }else {
                                                BmobRelation bmobRelation=me.getFriends();
                                                bmobRelation.add(list.get(0));
                                                me.setFriends(bmobRelation);
                                            }
                                            me.update(me.getObjectId(), new UpdateListener() {
                                                @Override
                                                public void done(BmobException e) {
                                                    if(e==null){
                                                        Log.d("bmob", "done: 更新联系人列表成功");
                                                        supply.delete(supply.getObjectId(), new UpdateListener() {
                                                            @Override
                                                            public void done(BmobException e) {
                                                                if(e==null){
                                                                    Message message=new Message();
                                                                    message.what= HandlerCons.UPDATE_FRIEND;
                                                                    handler.sendMessage(message);
                                                                }else {
                                                                    Log.d("bmob", "done: "+e);
                                                                }
                                                            }
                                                        });
                                                    }
                                                }
                                            });
                                        }else {

                                        }
                                    }else {
                                        Log.d("bmob", "done: "+e);
                                    }
                                }
                            });
                        }

                    }else {
                        Log.d("bmob", "done: 没有该条目");
                        Message message=new Message();
                        message.what= HandlerCons.UPDATE_FRIEND;
                        handler.sendMessage(message);
                    }
                }else {
                    Log.d("bmob", "done: "+e);
                }

            }
        });
    }

}
