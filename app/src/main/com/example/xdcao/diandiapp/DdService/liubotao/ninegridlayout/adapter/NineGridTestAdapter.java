package com.example.xdcao.diandiapp.DdService.liubotao.ninegridlayout.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.xdcao.diandiapp.DdService.liubotao.ninegridlayout.view.NineGridTestLayout;
import com.example.xdcao.diandiapp.MyDdNote;
import com.example.xdcao.diandiapp.R;

import java.util.List;

public class NineGridTestAdapter extends BaseAdapter {

    private Context mContext;
    private List<MyDdNote> mList;

    protected LayoutInflater inflater;
    private TextView note_txt,note_time;

    public NineGridTestAdapter(Context context) {
        mContext = context;
        inflater = LayoutInflater.from(context);
    }

    public void setList(List<MyDdNote> list) {
        mList = list;
        Log.e("temp","adapter:"+list.size());
    }

    /*public void setNote(String note){
        this.note=note;
    }

    public void setTime(String time){
        this.time=time;
    }*/


    //@Override
    public int getCount() {
       return getListSize(mList);
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.e("temp","position:"+position);

        ViewHolder holder = null;
        if (convertView == null || convertView.getTag() == null) {
            convertView = inflater.inflate(R.layout.item_bbs_nine_grid, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.layout.setIsShowAll(mList.get(position).isShowAll);
        holder.layout.setUrlList(mList.get(position).urlList);
        note_txt=(TextView) convertView.findViewById(R.id.note_text);
        note_time=(TextView) convertView.findViewById(R.id.note_time);
        note_txt.setText(mList.get(position).note);
        //Log.e("temp","viewholder:"+mList.get(position).note);
        note_time.setText(mList.get(position).time);
        return convertView;
    }


    public class ViewHolder {
        NineGridTestLayout layout;

        public ViewHolder(View view) {
            layout = (NineGridTestLayout) view.findViewById(R.id.layout_nine_grid);

        }
    }

    private int getListSize(List<MyDdNote> list) {
        if (list == null || list.size() == 0) {
            return 0;
        }
        return list.size();
    }


}
