package com.example.xdcao.diandiapp.UI.songwenqiang.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.xdcao.diandiapp.BackUp.caohao.bean.Post;
import com.example.xdcao.diandiapp.DdService.liubotao.ninegridlayout.util.ImageLoaderUtil;
import com.example.xdcao.diandiapp.MyDdNote;
import com.example.xdcao.diandiapp.R;
import com.example.xdcao.diandiapp.UI.songwenqiang.Fragment.ShareFragment;
import com.example.xdcao.diandiapp.UI.songwenqiang.utils.SnackbarUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.io.FileDescriptor;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


public class SearchContentActivity extends AppCompatActivity {

    private static final String TAG = "bmob";

    private SearchView mSearchView;
    private RecyclerView mRvSearch;
    private List<Post> notes;
    private ImageLoader imageLoader;
    //Todo  初始化Imageloader

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_content);
        notes=new ArrayList<>();
        mSearchView = (SearchView) findViewById(R.id.searchview);
        mRvSearch = (RecyclerView) findViewById(R.id.rv_search);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // TODO  将包含query的note放入List<Post> notes;
                queryPostForContainText(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)) {
                    // TODO  将包含newText的note放入List<Post> notes;
                    queryPostForContainText(newText);
                }else{

                }

                return false;
            }
        });
//        NoteAdapter noteAdapter=new NoteAdapter();
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(SearchContentActivity.this);

        mRvSearch.setLayoutManager(mLayoutManager);
//        mRvSearch.setAdapter(noteAdapter);
    }

    private void queryPostForContainText(String text) {

        BmobQuery<Post> query1=new BmobQuery<>();
        query1.addWhereEqualTo("isShared",true);
        BmobQuery<Post> query2=new BmobQuery<>();
        query2.addWhereContains("content",text);
        List<BmobQuery<Post>> queries=new ArrayList<>();
        queries.add(query1);
        queries.add(query2);
        BmobQuery<Post> mainQuery=new BmobQuery<>();
        mainQuery.and(queries);
        mainQuery.findObjects(new FindListener<Post>() {
            @Override
            public void done(List<Post> list, BmobException e) {
                if (e==null){
                    if (list.size()>0){
                        for (Post post:list){
                            notes.add(post);
                            Log.d(TAG, "done:"+notes.size());
                        }
                    }else {
                        Log.d(TAG, "done: 空的");
                    }
                }else {
                    Log.d(TAG, "done: "+e);
                }
            }
        });

    }


    class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> implements View.OnClickListener{


        @Override
        public NoteAdapter.NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(SearchContentActivity.this).inflate(R.layout.notes_item_layout,parent,false);
            NoteAdapter.NoteViewHolder holder = new NoteAdapter.NoteViewHolder(view);
            view.setOnClickListener(this);
            return holder;
        }

        @Override
        public void onBindViewHolder(NoteAdapter.NoteViewHolder holder, int position) {
            holder.tv_label.setText(notes.get(position).getAuthorName());
            Log.d("bmob", "onBindViewHolder: username"+notes.get(position).getAuthor().getUsername());
            holder.tv_content.setText(notes.get(position).getContent());
            holder.tv_time.setText(notes.get(position).getCreatedAt());
//            holder.iv_content.setImageResource();

            if (notes.get(position).getImages()!=null){
                if (notes.get(position).getImages().size()>0){

                    ImageLoaderUtil.displayImage(SearchContentActivity.this,holder.iv_content,notes.get(position).getImages().get(0),ImageLoaderUtil.getPhotoImageOption());
                }
            }

        }

        @Override
        public int getItemCount() {
            return notes.size();
        }

        @Override
        public void onClick(View v) {
            int position = mRvSearch.getChildAdapterPosition(v);
            Intent intent = new Intent(SearchContentActivity.this,DetailActivity.class);
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
}
