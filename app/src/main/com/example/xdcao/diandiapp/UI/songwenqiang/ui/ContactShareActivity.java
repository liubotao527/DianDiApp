package com.example.xdcao.diandiapp.UI.songwenqiang.ui;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xdcao.diandiapp.R;
import com.example.xdcao.diandiapp.UI.songwenqiang.Fragment.ShareFragment;
import com.example.xdcao.diandiapp.UI.songwenqiang.bean.ContactItem;
import com.example.xdcao.diandiapp.UI.songwenqiang.bean.SNotes;
import com.example.xdcao.diandiapp.UI.songwenqiang.ui.widget.RoundImageView;
import com.example.xdcao.diandiapp.UI.songwenqiang.utils.SnackbarUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ContactShareActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<SNotes> notes;
    private RoundImageView mRivPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_share);
        ContactItem contactItem = (ContactItem) getIntent().getSerializableExtra("contactItem");
        System.out.println(contactItem.getNickName());
        initView();

        notes = new ArrayList<>();
        for(int i=0;i<10;i++){
            SNotes note = new SNotes();
            note.setLabel("label_share"+i);
            note.setContent("content_share"+i);
            note.setTime("ABC分享与Time_share"+i);
            notes.add(note);
        }

        // Todo  将联系人的头像放到mRivPhoto里面


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
            holder.tv_label.setText(notes.get(position).getLabel());
            holder.tv_content.setText(notes.get(position).getContent());
            holder.tv_time.setText(notes.get(position).getTime());
//            holder.iv_content.setImageResource();

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
}
