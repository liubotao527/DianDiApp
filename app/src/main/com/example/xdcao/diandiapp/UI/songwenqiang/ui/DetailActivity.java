package com.example.xdcao.diandiapp.UI.songwenqiang.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xdcao.diandiapp.DdService.liubotao.ninegridlayout.adapter.NineGridTestAdapter;
import com.example.xdcao.diandiapp.DdService.liubotao.ninegridlayout.view.NineGridTestLayout;
import com.example.xdcao.diandiapp.MyDdNote;
import com.example.xdcao.diandiapp.R;

import static com.example.xdcao.diandiapp.R.id.note_time;


public class DetailActivity extends AppCompatActivity {
    private NineGridTestLayout nineGridTestLayout;
    private TextView name,text,time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mynote_detail);
        Intent intent=this.getIntent();
        Bundle bundle = intent.getExtras();
        MyDdNote note= (MyDdNote) bundle.getSerializable("note");
        nineGridTestLayout= (NineGridTestLayout) findViewById(R.id.layout_nine_grid);
        text= (TextView) findViewById(R.id.note_text);
        time= (TextView) findViewById(R.id.note_time);
        text.setText(note.getNote());
        time.setText(note.getTime());
        nineGridTestLayout.setIsShowAll(true);
        nineGridTestLayout.setUrlList(note.getUrlList());


    }

}
