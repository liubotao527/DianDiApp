package com.example.xdcao.diandiapp.UI.songwenqiang.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xdcao.diandiapp.BackUp.caohao.bean.MyUser;
import com.example.xdcao.diandiapp.BackUp.caohao.bean.Post;
import com.example.xdcao.diandiapp.BackUp.caohao.cons.HandlerCons;
import com.example.xdcao.diandiapp.R;
import com.example.xdcao.diandiapp.UI.songwenqiang.Fragment.ContactFragment;
import com.example.xdcao.diandiapp.UI.songwenqiang.Fragment.ShareFragment;
import com.example.xdcao.diandiapp.UI.songwenqiang.bean.ContactItem;
import com.example.xdcao.diandiapp.UI.songwenqiang.bean.SNotes;
import com.example.xdcao.diandiapp.UI.songwenqiang.ui.widget.RoundImageView;
import com.example.xdcao.diandiapp.UI.songwenqiang.utils.SnackbarUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class ContactShareActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<SNotes> notes;
    private RoundImageView mRivPhoto;
    private ImageLoader imageLoader;
    private List<Post> mPostList;

    Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case HandlerCons.QUERY_SOMEONE_POST:
                    Log.d("bmob", "handleMessage: "+"get handler mList.size: "+mPostList.size());
                    NoteAdapter noteAdapter=new NoteAdapter();
                    recyclerView.setAdapter(noteAdapter);
            }
            super.handleMessage(msg);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_share);
        ContactItem contactItem = (ContactItem) getIntent().getSerializableExtra("contactItem");
        System.out.println(contactItem.getNickName());
        mPostList=new ArrayList<>();
        initView();
        Intent intent=getIntent();
        ContactItem contactItem1= (ContactItem) intent.getExtras().getSerializable("contactItem");
        if(contactItem1!=null){
            new QueryForPostThread(contactItem1).start();
        }
        initImageLoader();


        notes = new ArrayList<>();
        for(int i=0;i<10;i++){
            SNotes note = new SNotes();
            note.setLabel("label_share"+i);
            note.setContent("content_share"+i);
            note.setTime("ABC分享与Time_share"+i);
            notes.add(note);
        }

        // Todo  将联系人的头像放到mRivPhoto里面


        if (contactItem1.getAvatar()!=null){
            Bitmap bitmap= BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+ File.separator+contactItem1.getAvatar().getFilename());
            if(bitmap!=null){
                Log.d("bmob", "onCreate: ");
                mRivPhoto.setImageBitmap(bitmap);
            }else {
                Log.d("bmob", "onBindViewHolder: imageloader");
                imageLoader.displayImage(contactItem1.getAvatar().getFileUrl(),mRivPhoto);
            }
        }

    }

    private void initImageLoader() {
        imageLoader= ImageLoader.getInstance();
        ImageLoaderConfiguration configuration=new ImageLoaderConfiguration.Builder(this).build();
        imageLoader.init(configuration);
    }


    private void initView() {
        mRivPhoto = (RoundImageView) findViewById(R.id.riv_photo);
        recyclerView = (RecyclerView)findViewById(R.id.line_recycler);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(ContactShareActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        NoteAdapter noteAdapter = new NoteAdapter();
        recyclerView.setAdapter(noteAdapter);
    }


    class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> implements View.OnClickListener{


        @Override
        public NoteAdapter.NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(ContactShareActivity.this).inflate(R.layout.notes_item_layout,parent,false);
            NoteAdapter.NoteViewHolder holder = new NoteAdapter.NoteViewHolder(view);
            view.setOnClickListener(this);
            return holder;
        }

        @Override
        public void onBindViewHolder(NoteAdapter.NoteViewHolder holder, int position) {
            if (mPostList.size()>position){
                holder.tv_label.setText(mPostList.get(position).getTitle());
                holder.tv_content.setText(mPostList.get(position).getContent());
                holder.tv_time.setText(mPostList.get(position).getCreatedAt());
//            holder.iv_content.setImageResource();
                if (mPostList.get(position).getImages().size()>0){
                    Log.d("bmob", "onBindViewHolder: "+mPostList.get(position).getImages().size());
                    Log.d("bmob", "onBindViewHolder: "+mPostList.get(position).getImages().get(0));
                    Bitmap bitmap= BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+ File.separator+mPostList.get(position).getFilenames().get(0));
                    if(bitmap!=null){
                        holder.iv_content.setImageBitmap(bitmap);
                        Log.d("bmob", "onBindViewHolder: 本地加载");
                    }else {
                        Log.d("bmob", "onBindViewHolder: 外部加载");
                        imageLoader.displayImage(mPostList.get(position).getImages().get(0),holder.iv_content);
                    }

                }
            }
        }

        @Override
        public int getItemCount() {
            return notes.size();
        }

        @Override
        public void onClick(View v) {
            int position = recyclerView.getChildAdapterPosition(v);
            Intent intent = new Intent(ContactShareActivity.this,DetailActivity.class);
            //将数据传到DetailActivity
            startActivity(intent);
        }

        class NoteViewHolder extends RecyclerView.ViewHolder{
            private TextView tv_label;
            private ImageView iv_content;
            private TextView tv_content;
            private TextView tv_time;
            public NoteViewHolder(View view){
                super(view);
                tv_label = (TextView) view.findViewById(R.id.note_label_text);
                tv_content = (TextView) view.findViewById(R.id.note_content_text);
                tv_time = (TextView) view.findViewById(R.id.note_last_edit_text);
                iv_content = (ImageView) view.findViewById(R.id.note_content_image);
            }
        }
    }

    class QueryForPostThread extends Thread{

        private ContactItem user;

        public QueryForPostThread(ContactItem user) {
            this.user=user;
        }

        @Override
        public void run() {
            queryForSomeOnePosts(user);
        }
    }


    private void queryForSomeOnePosts(ContactItem user){

        BmobQuery<Post> query=new BmobQuery<>();
        query.addWhereEqualTo("author",user.getMyUser());
        query.findObjects(new FindListener<Post>() {
            @Override
            public void done(List<Post> list, BmobException e) {
                if (e==null){
                    Log.d("bmob", "done: 有这个联系人");
                    if (list.size()>0){
                        Log.d("bmob", "done: 找到联系人帖子");
                        for (Post post:list){
                            Log.d("bmob", "done: "+post.getImages().get(0));
                            mPostList.add(post);
                        }
                        Message message=new Message();
                        message.what= HandlerCons.QUERY_SOMEONE_POST;
                        handler.sendMessage(message);
                    }
                }else {
                    Log.d("bmob", "done: "+e);
                }
            }
        });

    }


}