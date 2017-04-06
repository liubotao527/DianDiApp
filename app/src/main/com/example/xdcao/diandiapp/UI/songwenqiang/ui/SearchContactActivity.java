package com.example.xdcao.diandiapp.UI.songwenqiang.ui;

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

import com.example.xdcao.diandiapp.BackUp.caohao.bean.MyUser;
import com.example.xdcao.diandiapp.BackUp.caohao.cons.HandlerCons;
import com.example.xdcao.diandiapp.R;
import com.example.xdcao.diandiapp.UI.songwenqiang.ui.widget.RoundImageView;

import java.util.List;
import java.util.logging.Handler;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

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
                    MyUser myUser = (MyUser) msg.getData().getSerializable("MyUser");
                    Log.d(TAG, "handleMessage: "+"get handler user name: "+myUser.getUsername());

                    mTvName.setText(myUser.getUsername());
                    //TODO 将个性签名添加到mTvSign   将个人头像添加到 mIvAdd
                    mCvAddContact.setVisibility(View.VISIBLE);




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
        mIvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 将此联系人添加到联系人列表
            }
        });
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
                        Log.d(TAG, "done: lllll");
                        returnedUser=list.get(0);
                        flag=true;
                    }
                }
                if (flag){
                    Message message=handler.obtainMessage();
                    message.what=HandlerCons.QUERY_GIVEN_USER;
                    Bundle b = new Bundle();
                    b.putSerializable("MyUser",returnedUser);
                    message.setData(b);
                    handler.sendMessage(message);
                }
            }
        });
    }


}
