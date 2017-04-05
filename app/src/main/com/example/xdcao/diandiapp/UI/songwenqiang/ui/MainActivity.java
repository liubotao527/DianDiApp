package com.example.xdcao.diandiapp.UI.songwenqiang.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.xdcao.diandiapp.DdService.liubotao.activity.*;
import com.example.xdcao.diandiapp.R;
import com.example.xdcao.diandiapp.UI.songwenqiang.Fragment.ContactFragment;
import com.example.xdcao.diandiapp.UI.songwenqiang.Fragment.SettingFragment;
import com.example.xdcao.diandiapp.UI.songwenqiang.Fragment.ShareFragment;


public class MainActivity extends AppCompatActivity {

    private Toolbar mToolBar;
    private RelativeLayout mRlNotes, mRlShare, mRlContact, mRlSettings;
    private ImageView mImNotes, mImShare, mImContact, mImSettings;
    private TextView mTvNotes, mTvShare, mTvContact, mTvSettings;
    private FloatingActionButton mFab;
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private LinearLayout mLLDrawer;

    private String[] mTitles = {"DianDi","衣","食","住","行"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        resetFragmemt(R.id.notes);


        mRlNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFragmemt(R.id.notes);
            }
        });

        mRlShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFragmemt(R.id.share);
            }
        });

        mRlContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFragmemt(R.id.contact);
            }
        });

        mRlSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFragmemt(R.id.settings);
            }
        });

        setSupportActionBar(mToolBar);


        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,NoteActivity.class);
                startActivity(intent);
            }
        });

        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,GravityCompat.START);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,R.layout.drawer_list_item,mTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolBar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();


    }

    private void initViews() {
        mToolBar = (Toolbar) findViewById(R.id.toolbar);

        mRlNotes = (RelativeLayout) findViewById(R.id.notes);
        mRlShare = (RelativeLayout) findViewById(R.id.share);
        mRlContact =(RelativeLayout) findViewById(R.id.contact);
        mRlSettings = (RelativeLayout) findViewById(R.id.settings);

        mImNotes = (ImageView)findViewById(R.id.tab_img_notes);
        mImShare = (ImageView) findViewById(R.id.tab_img_share);
        mImContact = (ImageView) findViewById(R.id.tab_img_contact);
        mImSettings = (ImageView)findViewById(R.id.tab_img_setting);

        mTvNotes = (TextView) findViewById(R.id.tab_text_notes);
        mTvShare = (TextView) findViewById(R.id.tab_text_share);
        mTvContact = (TextView) findViewById(R.id.tab_text_contact);
        mTvSettings = (TextView) findViewById(R.id.tab_text_setting);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mLLDrawer = (LinearLayout) findViewById(R.id.ll_drawer);
        mFab = (FloatingActionButton) findViewById(R.id.fab);
    }

    private void resetFragmemt(int id) {
        resetImage();
        FragmentManager fm= getFragmentManager();
        FragmentTransaction ft= fm.beginTransaction();
        int colorpress = R.color.colorPrimary;
        switch (id){
            case R.id.notes:
                mImNotes.setImageResource(R.drawable.ic_note_press);
                mTvNotes.setTextColor(getResources().getColor(colorpress));
                MainFragment mf = new MainFragment();
                ft.replace(R.id.content_main2,mf);
                mFab.setVisibility(View.VISIBLE);
                break;
            case R.id.share:
                mImShare.setImageResource(R.drawable.ic_share_press);
                mTvShare.setTextColor(getResources().getColor(colorpress));
                ShareFragment sf = new ShareFragment();
                ft.replace(R.id.content_main2,sf);
                mFab.setVisibility(View.INVISIBLE);
                break;
            case R.id.contact:
                mImContact.setImageResource(R.drawable.ic_contact_press);
                mTvContact.setTextColor(getResources().getColor(colorpress));
                ContactFragment cf = new ContactFragment();
                ft.replace(R.id.content_main2,cf);
                mFab.setVisibility(View.INVISIBLE);
                break;
            case R.id.settings:
                mImSettings.setImageResource(R.drawable.ic_settings_press);
                mTvSettings.setTextColor(getResources().getColor(colorpress));
                SettingFragment stf = new SettingFragment();
                ft.replace(R.id.content_main2,stf);
                mFab.setVisibility(View.INVISIBLE);
                break;
        }
        ft.commit();

    }

    private void resetImage() {
        mImNotes.setImageResource(R.drawable.ic_note_normal);
        mImShare.setImageResource(R.drawable.ic_share_normal);
        mImContact.setImageResource(R.drawable.ic_contact_noraml);
        mImSettings.setImageResource(R.drawable.ic_settings_normal);

        mTvNotes.setTextColor(getResources().getColor(R.color.normal));
        mTvShare.setTextColor(getResources().getColor(R.color.normal));
        mTvContact.setTextColor(getResources().getColor(R.color.normal));
        mTvSettings.setTextColor(getResources().getColor(R.color.normal));
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        System.out.println(id);
        switch (id){
            case R.id.action_search:
                Intent intent = new Intent(this,SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.action_add:
                break;
            case R.id.action_information:
                break;
        }
        return true;
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        FragmentManager fm= getFragmentManager();
        FragmentTransaction ft= fm.beginTransaction();
        MainFragment mf1 = new MainFragment();
        ft.replace(R.id.content_main2,mf1);
        ft.commit();
        mDrawerList.setItemChecked(position, true);
        mToolBar.setTitle(mTitles[position]);
        mDrawerLayout.closeDrawer(mLLDrawer);

    }


}
