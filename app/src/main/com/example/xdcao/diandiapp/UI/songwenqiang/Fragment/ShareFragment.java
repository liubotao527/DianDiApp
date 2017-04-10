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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xdcao.diandiapp.BackUp.caohao.bean.MyUser;
import com.example.xdcao.diandiapp.BackUp.caohao.bean.Post;
import com.example.xdcao.diandiapp.BackUp.caohao.cons.HandlerCons;
import com.example.xdcao.diandiapp.R;
import com.example.xdcao.diandiapp.UI.songwenqiang.bean.ContactItem;
import com.example.xdcao.diandiapp.UI.songwenqiang.bean.SNotes;
import com.example.xdcao.diandiapp.UI.songwenqiang.ui.ContactShareActivity;
import com.example.xdcao.diandiapp.UI.songwenqiang.ui.DetailActivity;
import com.example.xdcao.diandiapp.UI.songwenqiang.utils.SnackbarUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


/**
 * Created by wewarrios on 2017/3/14.
 */

public class ShareFragment extends Fragment{
    private RecyclerView recyclerView;
    private CoordinatorLayout coordinatorLayout;
    private List<Post> notes;
    private LinearLayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Context context;
    private int frindCounter=0;
    private int postCounter=0;
    private int friendSize=0;
    private int postSize=0;
    private ImageLoader imageLoader;

    Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case HandlerCons.QUERY_ALL_SHARE:
                    Log.d("bmob", "handleMessage: "+"get handler mList.size: "+notes.size());
                    NoteAdapter noteAdapter=new NoteAdapter();
                    recyclerView.setAdapter(noteAdapter);
            }
            super.handleMessage(msg);
        }
    };

    class QueryForSharesThread extends Thread{
        @Override
        public void run() {
            queryforShare();
        }
    }

    private void queryforShare() {

        MyUser me=MyUser.getCurrentUser(MyUser.class);
        BmobQuery<MyUser> userQuery=new BmobQuery<>();
        userQuery.addWhereRelatedTo("friends",new BmobPointer(me));
        userQuery.findObjects(new FindListener<MyUser>() {
            @Override
            public void done(List<MyUser> list, BmobException e) {
                friendSize=list.size();
                for (MyUser friend:list){
                    frindCounter++;
                    BmobQuery<Post> isShareQuery=new BmobQuery<>();
                    isShareQuery.addWhereEqualTo("isShared",true);
                    BmobQuery<Post> isFirendQuery=new BmobQuery<Post>();
                    isFirendQuery.addWhereEqualTo("author",friend);
                    List<BmobQuery<Post>> queries=new ArrayList<BmobQuery<Post>>();
                    queries.add(isShareQuery);
                    queries.add(isFirendQuery);
                    BmobQuery<Post> mainQuery=new BmobQuery<Post>();
                    mainQuery.and(queries);
                    mainQuery.findObjects(new FindListener<Post>() {
                        @Override
                        public void done(List<Post> list, BmobException e) {
                            if(e==null){
                                if (list.size()>0){
                                    postSize=list.size();
                                    for (Post post:list){
                                        notes.add(post);
                                        postCounter++;
                                        if ((postCounter==postSize)&&(frindCounter==friendSize)){
                                            Message message=new Message();
                                            message.what= HandlerCons.QUERY_ALL_SHARE;
                                            handler.sendMessage(message);
                                        }
                                    }
                                }
                            }
                        }
                    });
                }



            }
        });


    }


    public ShareFragment(){

    }

    private void initImageLoader() {
        imageLoader= ImageLoader.getInstance();
        ImageLoaderConfiguration configuration=new ImageLoaderConfiguration.Builder(this.context).build();
        imageLoader.init(configuration);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notes = new ArrayList<>();
        initImageLoader();
        new QueryForSharesThread().start();
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
        View view = inflater.inflate(R.layout.fragment_share, container, false);
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

            }
        });
//        recyclerView.addItemDecoration(new DividerItemDecoration(context,DividerItemDecoration.VERTICAL));
        NoteAdapter noteAdapter = new NoteAdapter();
        recyclerView.setAdapter(noteAdapter);


    }

    class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> implements View.OnClickListener{


        @Override
        public NoteAdapter.NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.notes_item_layout,parent,false);
            NoteViewHolder holder = new NoteViewHolder(view);
            view.setOnClickListener(this);
            return holder;
        }

        @Override
        public void onBindViewHolder(NoteViewHolder holder, int position) {
            holder.tv_label.setText(notes.get(position).getAuthorName());
            Log.d("bmob", "onBindViewHolder: username"+notes.get(position).getAuthor().getUsername());
            holder.tv_content.setText(notes.get(position).getContent());
            holder.tv_time.setText(notes.get(position).getCreatedAt());
//            holder.iv_content.setImageResource();
            if (notes.get(position).getImages().size()>0){
                Log.d("bmob", "onBindViewHolder: "+notes.get(position).getImages().size());
                Log.d("bmob", "onBindViewHolder: "+notes.get(position).getImages().get(0));
                Bitmap bitmap= BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+ File.separator+notes.get(position).getFilenames().get(0));
                if(bitmap!=null){
                    holder.iv_content.setImageBitmap(bitmap);
                    Log.d("bmob", "onBindViewHolder: 本地加载");
                }else {
                    Log.d("bmob", "onBindViewHolder: 外部加载");
                    imageLoader.displayImage(notes.get(position).getImages().get(0),holder.iv_content);
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
            Intent intent = new Intent(getActivity(),DetailActivity.class);
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
        public void addItem(Post note,int position){
            notes.add(position,note);
            notifyItemInserted(position);
            recyclerView.scrollToPosition(position);
        }

        public void removeItem(final int position){
            final Post removed = notes.get(position);
            notes.remove(position);
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
}
