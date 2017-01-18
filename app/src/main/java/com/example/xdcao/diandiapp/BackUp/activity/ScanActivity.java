package com.example.xdcao.diandiapp.BackUp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.xdcao.diandiapp.BackUp.bean.Lost;
import com.example.xdcao.diandiapp.BackUp.adapters.LostAdapter;
import com.example.xdcao.diandiapp.R;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by xdcao on 2017/1/14.
 */

public class ScanActivity extends BaseActivity{

    private ListView scanlist;
    private LostAdapter lostAdapter=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_layout);
        scanlist=(ListView)findViewById(R.id.scanlist);
        queryData();

    }

    private void queryData() {
        BmobQuery<Lost> query=new BmobQuery<Lost>();
        query.order("-createdAt");
        query.findObjects(new FindListener<Lost>() {
            @Override
            public void done(List<Lost> list, BmobException e) {
                if(e==null){
                    System.out.print("listSize:   "+list.size()+"   ");
                    lostAdapter=new LostAdapter(ScanActivity.this,list);
                    scanlist.setAdapter(lostAdapter);
                }else{
                    System.out.print("Error:   "+e.toString());
                }
            }
        });
    }
}
