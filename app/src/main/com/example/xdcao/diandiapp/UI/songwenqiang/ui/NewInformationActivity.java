package com.example.xdcao.diandiapp.UI.songwenqiang.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.xdcao.diandiapp.BackUp.caohao.bean.MyUser;
import com.example.xdcao.diandiapp.BackUp.caohao.bean.Post;
import com.example.xdcao.diandiapp.BackUp.caohao.bean.Supply;
import com.example.xdcao.diandiapp.BackUp.caohao.cons.HandlerCons;
import com.example.xdcao.diandiapp.R;
import com.example.xdcao.diandiapp.UI.songwenqiang.Fragment.ContactFragment;
import com.example.xdcao.diandiapp.UI.songwenqiang.ui.widget.RoundImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class NewInformationActivity extends AppCompatActivity {

    private List<Supply> requests=null;
    private ImageLoader imageLoader;


    Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case HandlerCons.QUERY_FOR_REQUESTS:
                    Log.d("bmob", "handleMessage: "+"get handler requests.size: "+requests.size());
                    NewInformationAdapter requestAdapter = new NewInformationAdapter();
                    mRvNewInformation.setAdapter(requestAdapter);
            }
            super.handleMessage(msg);
        }
    };

    private void initImageLoader() {
        imageLoader= ImageLoader.getInstance();
        ImageLoaderConfiguration configuration=new ImageLoaderConfiguration.Builder(this).build();
        imageLoader.init(configuration);
    }


    class QueryForRequest extends Thread{
        @Override
        public void run() {
            queryForRequest();
        }
    }

    private void queryForRequest() {

        MyUser me=BmobUser.getCurrentUser(MyUser.class);
        BmobQuery<Supply> query=new BmobQuery<>();
        query.addWhereEqualTo("responsor",me);
        query.findObjects(new FindListener<Supply>() {
            @Override
            public void done(List<Supply> list, BmobException e) {
                if (e==null){
                    if (list.size()>0){
                        for (Supply supply:list){
                            Log.d("bmob", "done: "+supply.getReqNickName());
                            Log.d("bmob", "done: "+supply.getReqUserName());
                            Log.d("bmob", "done: "+supply.getReqAvatar());
                            requests.add(supply);
                        }
                        Message message=new Message();
                        message.what= HandlerCons.QUERY_FOR_REQUESTS;
                        handler.sendMessage(message);
                    }else {
                        Log.d("bmob", "done: 没有该条目");
                    }
                }else {
                    Log.d("bmob", "done: "+e);
                }
            }
        });

    }

    private RecyclerView mRvNewInformation;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_information);
        mRvNewInformation = (RecyclerView) findViewById(R.id.rv_new_information);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(NewInformationActivity.this);

        mRvNewInformation.setLayoutManager(mLayoutManager);
        mRvNewInformation.setAdapter(new NewInformationAdapter());
        initImageLoader();
        requests=new ArrayList<>();
        new QueryForRequest().start();

    }



    class NewInformationAdapter extends RecyclerView.Adapter<NewInformationAdapter.NewInformationViewHolder>{

        @Override
        public NewInformationAdapter.NewInformationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(NewInformationActivity.this).inflate(R.layout.new_information_item,parent,false);
            return new NewInformationViewHolder(view);
        }

        @Override
        public void onBindViewHolder(NewInformationAdapter.NewInformationViewHolder holder, final int position) {
//            holder.mRivPhoto.setImageResource();
//            holder.mTvName.setText();
//            holder.mBtAdd.setText();
            if (requests.size()>position){
                Log.d("bmob", "onBindViewHolder: nickname: "+requests.get(position).getRequester().getNickName());
                holder.mTvName.setText(requests.get(position).getReqNickName());
                if (requests.get(position).getReqAvatar()!=null){
                    Bitmap bitmap= BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+ File.separator+requests.get(position).getReqAvatar().getFilename());
                    if(bitmap!=null){
                        holder.mRivPhoto.setImageBitmap(bitmap);
                    }else {
                        Log.d("bmob", "onBindViewHolder: imageloader");
                        imageLoader.displayImage(requests.get(position).getReqAvatar().getFileUrl(),holder.mRivPhoto);
                    }
                }
                holder.mBtAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Supply supply=requests.get(position);
                        supply.setAccepted(true);
                        supply.update(supply.getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e==null){
                                    BmobQuery<MyUser> query=new BmobQuery<MyUser>();
                                    query.addWhereEqualTo("username",requests.get(position).getReqUserName());
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
                                                            }
                                                        }
                                                    });
                                                }
                                            }else {
                                                Log.d("bmob", "done: "+e);
                                            }
                                        }
                                    });
                                }else {
                                    Log.d("bmob", "done: "+e);
                                }
                            }
                        });
                    }
                });
            }



        }

        @Override
        public int getItemCount() {
            return 6;
        }

        class NewInformationViewHolder extends RecyclerView.ViewHolder {
            private RoundImageView mRivPhoto;
            private TextView mTvName;
            private Button mBtAdd;
            public NewInformationViewHolder(View itemView) {
                super(itemView);
                mRivPhoto = (RoundImageView) itemView.findViewById(R.id.riv_photo);
                mTvName = (TextView) itemView.findViewById(R.id.tv_name);
                mBtAdd = (Button) itemView.findViewById(R.id.bt_add);
            }
        }
    }
}
