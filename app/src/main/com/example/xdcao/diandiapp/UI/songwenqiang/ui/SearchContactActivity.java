package com.example.xdcao.diandiapp.UI.songwenqiang.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.os.Environment;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xdcao.diandiapp.BackUp.caohao.actions.FileAction;
import com.example.xdcao.diandiapp.BackUp.caohao.bean.MyUser;
import com.example.xdcao.diandiapp.BackUp.caohao.bean.Supply;
import com.example.xdcao.diandiapp.BackUp.caohao.cons.HandlerCons;
import com.example.xdcao.diandiapp.R;
import com.example.xdcao.diandiapp.UI.songwenqiang.ui.widget.RoundImageView;

import java.io.File;
import java.util.List;
import java.util.logging.Handler;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class SearchContactActivity extends AppCompatActivity {

    private static final String TAG = "SearchContactActivity";

    private SearchView mSearchView;
    private TextView mTvName,mTvSign;
    private RoundImageView mRivPhoto;
    private ImageView mIvAdd;

    private CardView mCvAddContact;



    android.os.Handler handler=new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case HandlerCons.QUERY_GIVEN_USER:
                    final MyUser myUser = (MyUser) msg.getData().getSerializable("MyUser");
                    Log.d("bmob", "handleMessage: "+"get handler user name: "+myUser.getUsername());

                    mTvName.setText(myUser.getUsername());

                    if (myUser.getAvatar()!=null){
                        Bitmap bitmap= BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+File.separator+myUser.getAvatar().getFilename());
                        mRivPhoto.setImageBitmap(bitmap);
                    }

                    mCvAddContact.setVisibility(View.VISIBLE);

                    mIvAdd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            updateMyFriendList(myUser);
                        }
                    });

            }
            super.handleMessage(msg);
        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_contact);
        initView();


        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //添加查询的代码
                //在一个线程中查询
                final String queryName = query;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        queryForGivenName(queryName);
                    }
                }).start();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                return false;
            }
        });
    }

    private void initView() {
        mSearchView = (SearchView) findViewById(R.id.search_view);
        mTvName = (TextView) findViewById(R.id.tv_name);
        mTvSign = (TextView) findViewById(R.id.tv_sign);
        mIvAdd = (ImageView) findViewById(R.id.iv_add);
        mRivPhoto = (RoundImageView) findViewById(R.id.riv_photo);
        mCvAddContact = (CardView) findViewById(R.id.contact_add_item);

    }

    /*
    添加好友后更新联系人列表
     */
    private void updateMyFriendList(MyUser friend) {

        Log.d(TAG, "updateMyFriendList: ");

        MyUser me= BmobUser.getCurrentUser(MyUser.class);
//
//        // TODO: 2017/4/7 发出一个请求
        Supply supply=new Supply();
        supply.setRequester(me);
        supply.setResponsor(friend);
        supply.setAccepted(false);

        supply.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e==null){
                    Log.d(TAG, "done: 请求成功发出");
                }else {
                    Log.d(TAG, "done: "+e);
                }
            }
        });

//         TODO: 2017/4/7 这里先注释掉，等对方同意再加上这里的逻辑
//        if (me.getFriends()==null){
//            BmobRelation bmobRelation=new BmobRelation();
//            bmobRelation.add(friend);
//            me.setFriends(bmobRelation);
//        }else {
//            BmobRelation bmobRelation=me.getFriends();
//            bmobRelation.add(friend);
//            me.setFriends(bmobRelation);
//        }
//        me.update(me.getObjectId(), new UpdateListener() {
//            @Override
//            public void done(BmobException e) {
//                if(e==null){
//                    Log.d(TAG, "done: 更新联系人列表成功");
//                }
//            }
//        });

    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    /*
        根据用户名查询指定用户
         */
    private void queryForGivenName(String username){

        BmobQuery<MyUser> query=new BmobQuery<>();
        query.addWhereEqualTo("username",username);
        query.findObjects(new FindListener<MyUser>() {
            @Override
            public void done(List<MyUser> list, BmobException e) {
                boolean flag=false;
                MyUser returnedUser=null;
                if(e==null){
                    if (list.size()>0){
                        Log.d("bmob", "done: lllll");
                        returnedUser=list.get(0);
                        Log.d("bmob", "done: "+returnedUser.getUsername());
                        if (returnedUser.getAvatar()!=null){
                            File saveFile = new File(Environment.getExternalStorageDirectory(), returnedUser.getAvatar().getFilename());
                            final MyUser finalReturnedUser = returnedUser;
                            returnedUser.getAvatar().download(saveFile, new DownloadFileListener() {

                                @Override
                                public void onStart() {
                                    Log.d("bmob", "onStart: ");
                                }

                                @Override
                                public void done(String savePath,BmobException e) {
                                    if(e==null){
                                        Log.d("bmob", "done: downloadSuccess");
                                        Message message=handler.obtainMessage();
                                        message.what=HandlerCons.QUERY_GIVEN_USER;
                                        Bundle b = new Bundle();
                                        b.putSerializable("MyUser", finalReturnedUser);
                                        message.setData(b);
                                        handler.sendMessage(message);
                                    }else{
                                        Log.d("bmob", "done: downloadFailure");
                                    }
                                }

                                @Override
                                public void onProgress(Integer value, long newworkSpeed) {
                                    Log.d("bmob","下载进度："+value+","+newworkSpeed);
                                }

                            });
                        }else {
                            Log.d("bmob", "done: 发出message");
                            Message message=handler.obtainMessage();
                            message.what=HandlerCons.QUERY_GIVEN_USER;
                            Bundle b = new Bundle();
                            b.putSerializable("MyUser", returnedUser);
                            message.setData(b);
                            handler.sendMessage(message);
                        }
                    }
                }
            }
        });
    }





}
