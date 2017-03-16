package com.example.xdcao.diandiapp.BackUp.caohao.actions;

import java.util.List;

import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListListener;

import static com.example.xdcao.diandiapp.BackUp.caohao.activity.BaseActivity.log;
import static com.example.xdcao.diandiapp.BackUp.caohao.activity.BaseActivity.loge;


/**
 * Created by xdcao on 2017/1/18.
 */

public class BatchAction {

    /**
     * 批量添加
     */
    public static void batchInsert(List<BmobObject> objects){

        new BmobBatch().insertBatch(objects).doBatch(new QueryListListener<BatchResult>() {

            @Override
            public void done(List<BatchResult> o, BmobException e) {
                if(e==null){
                    for(int i=0;i<o.size();i++){
                        BatchResult result = o.get(i);
                        BmobException ex =result.getError();
                        if(ex==null){
                            log("第"+i+"个数据批量添加成功："+result.getCreatedAt()+","+result.getObjectId()+","+result.getUpdatedAt());
                        }else{
                            log("第"+i+"个数据批量添加失败："+ex.getMessage()+","+ex.getErrorCode());
                        }
                    }
                }else{
                    loge(e);
                }
            }
        });
    }

    /**
     * 批量更新
     */
    public static void batchUpdate(List<BmobObject> objects){


        new BmobBatch().updateBatch(objects).doBatch(new QueryListListener<BatchResult>() {

            @Override
            public void done(List<BatchResult> o, BmobException e) {
                if(e==null){
                    for(int i=0;i<o.size();i++){
                        BatchResult result = o.get(i);
                        BmobException ex =result.getError();
                        if(ex==null){
                            log("第"+i+"个数据批量更新成功："+result.getUpdatedAt());
                        }else{
                            log("第"+i+"个数据批量更新失败："+ex.getMessage()+","+ex.getErrorCode());
                        }
                    }
                }else{
                    loge(e);
                }
            }
        });

    }

    /**
     * 批量删除
     */
    public static void batchDelete(List<BmobObject> objects){

        new BmobBatch().deleteBatch(objects).doBatch(new QueryListListener<BatchResult>() {

            @Override
            public void done(List<BatchResult> o, BmobException e) {
                if(e==null){
                    for(int i=0;i<o.size();i++){
                        BatchResult result = o.get(i);
                        BmobException ex =result.getError();
                        if(ex==null){
                            log("第"+i+"个数据批量删除成功");
                        }else{
                            log("第"+i+"个数据批量删除失败："+ex.getMessage()+","+ex.getErrorCode());
                        }
                    }
                }else{
                    loge(e);
                }
            }
        });
    }




}
