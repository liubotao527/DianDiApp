package com.example.xdcao.diandiapp.UI.songwenqiang.ui;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
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
import com.example.xdcao.diandiapp.DdService.liubotao.database.DbInfo;
import com.example.xdcao.diandiapp.DdService.liubotao.ninegridlayout.util.ImageLoaderUtil;
import com.example.xdcao.diandiapp.MyDdNote;
import com.example.xdcao.diandiapp.R;
import com.example.xdcao.diandiapp.UI.songwenqiang.bean.SNotes;
import com.example.xdcao.diandiapp.UI.songwenqiang.utils.SnackbarUtil;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import java.util.logging.LogRecord;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


/**
 * Created by wewarrios on 2017/3/15.
 */

public class MainFragment extends Fragment {
    private  RecyclerView recyclerView;
    private CoordinatorLayout coordinatorLayout;
    private List<SNotes> notes;
    private LinearLayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Context context;
    private List<MyDdNote> mList;
    private Cursor mCursor;
    static final String TAG="TAG";


    Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0x123:

                    Log.d(TAG, "handleMessage: "+"get handler");

            }
            super.handleMessage(msg);
        }
    };

    public MainFragment(){


    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        this.context = activity;
        super.onAttach(activity);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateDisplay();
        notes = new ArrayList<>();
        for(int i=0;i<10;i++){
            SNotes note = new SNotes();
            note.setLabel("label"+i);
            note.setContent("content"+i);
            note.setTime("Time"+i);
            notes.add(note);
        }
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateDisplay();
        NoteAdapter noteAdapter = new NoteAdapter();
        recyclerView.setAdapter(noteAdapter);
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
        //recyclerView.addItemDecoration(new DividerItemDecoration(context,DividerItemDecoration.VERTICAL));
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

            //    holder.tv_label.setText("temp");
        //    Log.e("temp",mList.get(position).note+"--"+mList.get(position).time);

            Log.d(TAG,"1888888");
            System.out.println(mList.size());
            holder.tv_content.setText(mList.get(position).getNote());
            holder.tv_time.setText(mList.get(position).getTime());

            if(mList.get(position).urlList.size()>0) {
                Log.e("tem",mList.get(position).getUrlList().get(0));
                ImageLoaderUtil.getImageLoader(context).displayImage(mList.get(position).getUrlList().get(0), holder.iv_content, ImageLoaderUtil.getPhotoImageOption());
            }
            //holder.tv_content.setText("今天天气真好");
            //holder.tv_time.setText("Time");
//            holder.iv_content.setImageResource();

        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        @Override
        public void onClick(View v) {
            int position = recyclerView.getChildAdapterPosition(v);
            MyDdNote note=mList.get(position);
            Intent intent = new Intent(getActivity(),DetailActivity.class);

            Bundle bundle = new Bundle();
            bundle.putSerializable("note", note);

            intent.putExtras(bundle);
            startActivity(intent);
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
        public void addItem(SNotes note,int position){
            notes.add(position,note);
            notifyItemInserted(position);
            recyclerView.scrollToPosition(position);
        }

        public void removeItem(final int position){
            final SNotes removed = notes.get(position);
            notes.remove(position);
            notifyItemRemoved(position);
            SnackbarUtil.ShortSnackbar(coordinatorLayout,"你删除了第"+position+"个item",SnackbarUtil.Warning).setAction("撤销", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addItem(removed,position);
                    SnackbarUtil.ShortSnackbar(coordinatorLayout,"撤销了删除第"+position+"个item", SnackbarUtil.Confirm).show();
                }
            }).setActionTextColor(Color.WHITE).show();
        }
    }

    //查询用户发过的状态
    public void queryForMyPosts(){
        BmobQuery<Post> query=new BmobQuery<>();
        MyUser me= BmobUser.getCurrentUser(MyUser.class);
        query.addWhereEqualTo("author",me);
        query.findObjects(new FindListener<Post>() {
            @Override
            public void done(List<Post> list, BmobException e) {
                if(e==null){
                    Log.d(TAG, "done: "+"拿到数据"+"size: "+list.size());
                    for(Post post:list){
                        MyDdNote model=new MyDdNote();
                        model.note=post.getContent();
                        Log.d(TAG, "Content: "+post.getContent());
                        model.time=post.getCreatedAt();
                        mList.add(model);
                    }
                }else {
                    Log.d(TAG, "done: "+"没拿到，空的");
                }
            }
        });
    }

    private class QueryPostThread extends Thread{
        @Override
        public void run() {
           queryForMyPosts();
            Message message=new Message();
            message.what=0x123;
            handler.sendMessage(message);
        }
    }

    // 负责更新ListView中的数据
    private void updateDisplay() {
        mList = new ArrayList<>();
        List<Post> postList=new ArrayList<>();
        // 查询条件，查询所有文件夹记录及显示在主页的便签记录
        String selection = DbInfo.NoteItems.IS_FOLDER + " = '" + "yes" + "' or "
                + DbInfo.NoteItems.PARENT_FOLDER + " = " + "-1";
        //getContentResolver().delete(NoteItems.CONTENT_URI,selection,null);

        mCursor = context.getContentResolver().query(DbInfo.NoteItems.CONTENT_URI, null,
                selection, null, null);

        // This method allows the activity to take care of managing the given
        // Cursor's lifecycle for you based on the activity's lifecycle.
        if(mCursor==null){
            Log.e(TAG,"1111111111");
        }

        // TODO: 2017/4/5 缓存没数据从网上拿
        if(mCursor.getCount()==0){
            Log.d(TAG, "updateDisplay: "+"从网上拿url");
            new QueryPostThread().start();
        }



        if (mCursor != null && mCursor.moveToFirst()) {
            do {
                int contentColumn = mCursor.getColumnIndex(DbInfo.NoteItems.CONTENT);
                int dateColumn = mCursor.getColumnIndex(DbInfo.NoteItems.UPDATE_DATE);
                int timeColumn = mCursor.getColumnIndex(DbInfo.NoteItems.UPDATE_TIME);
                int pics=mCursor.getColumnIndex(DbInfo.NoteItems.PICS);
                //notes.add(mCursor.getString(contentColumn));··                                    ····
                //times.add(mCursor.getString(dateColumn)+" "+mCursor.getString(timeColumn));

                MyDdNote model = new MyDdNote();
                //for(int j=0;j<i;j++){
                //    model.urlList.add(mUrls[j]);
                //}
                model.note= mCursor.getString(contentColumn);
                model.time= mCursor.getString(dateColumn)+" "+mCursor.getString(timeColumn);
                //model.urlList.add("file:///mnt/sdcard/DCIM/Camera/IMG_20170301_114357.jpg");
                //model.urlList.add("content://media/external/images/media/9666");
                getImgUri(model.urlList,mCursor.getString(pics));
                //Log.e("temp",mCursor.getString(contentColumn)+"-----"+model.note);
                mList.add(model);
            } while (mCursor.moveToNext());
        }


        //initView();
    }



    public static void getImgUri(List<String> uris, String s){
        //ArrayList<String> uris=new ArrayList<String>();
        Log.e("temp",s);
        int i;
        while((i=s.indexOf("\n"))>0){
            uris.add(s.substring(0, i));
            s=s.substring(i+1);
        }
        for(int j=0;j<uris.size();j++) {
            Log.e("temp","num"+j+":::"+uris.get(j));
        }
    }
}
