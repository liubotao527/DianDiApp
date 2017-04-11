package com.example.xdcao.diandiapp.UI.songwenqiang.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.xdcao.diandiapp.R;
import com.example.xdcao.diandiapp.UI.songwenqiang.ui.widget.RoundImageView;

public class NewInformationActivity extends AppCompatActivity {

    private RecyclerView mRvNewInformation;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_information);
        mRvNewInformation = (RecyclerView) findViewById(R.id.rv_new_information);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(NewInformationActivity.this);

        mRvNewInformation.setLayoutManager(mLayoutManager);
        mRvNewInformation.setAdapter(new NewInformationAdapter());
    }



    class NewInformationAdapter extends RecyclerView.Adapter<NewInformationAdapter.NewInformationViewHolder>{

        @Override
        public NewInformationAdapter.NewInformationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(NewInformationActivity.this).inflate(R.layout.new_information_item,parent,false);
            return new NewInformationViewHolder(view);
        }

        @Override
        public void onBindViewHolder(NewInformationAdapter.NewInformationViewHolder holder, int position) {
//            holder.mRivPhoto.setImageResource();
//            holder.mTvName.setText();
//            holder.mBtAdd.setText();

        }

        @Override
        public int getItemCount() {
            return 6;
        }

        class NewInformationViewHolder extends RecyclerView.ViewHolder {
            private RoundImageView mRivPhoto;
            private TextView mTvName;
            private Button mBtAdd;
            public NewInformationViewHolder(View itemView) {
                super(itemView);
                mRivPhoto = (RoundImageView) itemView.findViewById(R.id.riv_photo);
                mTvName = (TextView) itemView.findViewById(R.id.tv_name);
                mBtAdd = (Button) itemView.findViewById(R.id.bt_add);
            }
        }
    }
}
