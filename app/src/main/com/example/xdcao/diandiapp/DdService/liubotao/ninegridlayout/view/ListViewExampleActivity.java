package com.example.xdcao.diandiapp.DdService.liubotao.ninegridlayout.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;


import com.example.xdcao.diandiapp.DdService.liubotao.activity.NoteActivity;
import com.example.xdcao.diandiapp.DdService.liubotao.ninegridlayout.adapter.NineGridTestAdapter;
import com.example.xdcao.diandiapp.MyDdNote;
import com.example.xdcao.diandiapp.R;

import java.io.Serializable;
import java.util.List;

public class ListViewExampleActivity extends AppCompatActivity {

    private static final String ARG_LIST = "list";

    private ListView mListView;
    private NineGridTestAdapter mAdapter;
    private List<MyDdNote> mList;
    private ImageButton imageButton;

    public static void startActivity(Context context, List<MyDdNote> list) {
        Intent intent = new Intent(context, ListViewExampleActivity.class);
        intent.putExtra(ARG_LIST, (Serializable) list);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index_page);
        getIntentData();
        initView();
    }

    private void getIntentData() {
        mList = (List<MyDdNote>) getIntent().getSerializableExtra(ARG_LIST);
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.list);

        mAdapter = new NineGridTestAdapter(this);
        mAdapter.setList(mList);
        mListView.setAdapter(mAdapter);
        imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newNote();
            }
        });
    }

    private void newNote() {
        Intent i = new Intent();
        i.putExtra("Open_Type", "newNote");
        i.setClass(ListViewExampleActivity.this, NoteActivity.class);
        startActivity(i);
    }

}
