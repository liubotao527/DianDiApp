package com.example.xdcao.diandiapp.UI.songwenqiang.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.xdcao.diandiapp.R;
import com.example.xdcao.diandiapp.UI.songwenqiang.Fragment.ContactFragment;
import com.example.xdcao.diandiapp.UI.songwenqiang.Fragment.SettingFragment;
import com.example.xdcao.diandiapp.UI.songwenqiang.Fragment.ShareFragment;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    RelativeLayout rlnotes,rlshare,rlcontact,rlsettings;
    ImageView imnotes,imshare,imcontact,imsettings;
    TextView tvnotes,tvshare,tvcontact,tvsettings;
    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        resetFragmemt(R.id.notes);


        rlnotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFragmemt(R.id.notes);
            }
        });

        rlshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFragmemt(R.id.share);
            }
        });

        rlcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFragmemt(R.id.contact);
            }
        });

        rlsettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFragmemt(R.id.settings);
            }
        });

        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,NoteActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initViews() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        rlnotes = (RelativeLayout) findViewById(R.id.notes);
        rlshare = (RelativeLayout) findViewById(R.id.share);
        rlcontact=(RelativeLayout) findViewById(R.id.contact);
        rlsettings = (RelativeLayout) findViewById(R.id.settings);

        imnotes = (ImageView)findViewById(R.id.tab_img_notes);
        imshare = (ImageView) findViewById(R.id.tab_img_share);
        imcontact= (ImageView) findViewById(R.id.tab_img_contact);
        imsettings = (ImageView)findViewById(R.id.tab_img_setting);

        tvnotes = (TextView) findViewById(R.id.tab_text_notes);
        tvshare = (TextView) findViewById(R.id.tab_text_share);
        tvcontact = (TextView) findViewById(R.id.tab_text_contact);
        tvsettings = (TextView) findViewById(R.id.tab_text_setting);
    }

    private void resetFragmemt(int id) {
        resetImage();
        FragmentManager fm= getFragmentManager();
        FragmentTransaction ft= fm.beginTransaction();
        int colorpress = R.color.colorPrimary;
        switch (id){
            case R.id.notes:
                imnotes.setImageResource(R.drawable.ic_note_press);
                tvnotes.setTextColor(getResources().getColor(colorpress));
                MainFragment mf = new MainFragment();
                ft.replace(R.id.content_main2,mf);
                break;
            case R.id.share:
                imshare.setImageResource(R.drawable.ic_share_press);
                tvshare.setTextColor(getResources().getColor(colorpress));
                ShareFragment sf = new ShareFragment();
                ft.replace(R.id.content_main2,sf);
                break;
            case R.id.contact:
                imcontact.setImageResource(R.drawable.ic_contact_press);
                tvcontact.setTextColor(getResources().getColor(colorpress));
                ContactFragment cf = new ContactFragment();
                ft.replace(R.id.content_main2,cf);
                break;
            case R.id.settings:
                imsettings.setImageResource(R.drawable.ic_settings_press);
                tvsettings.setTextColor(getResources().getColor(colorpress));
                SettingFragment stf = new SettingFragment();
                ft.replace(R.id.content_main2,stf);
                break;
        }
        ft.commit();

    }

    private void resetImage() {
        imnotes.setImageResource(R.drawable.ic_note_normal);
        imshare.setImageResource(R.drawable.ic_share_normal);
        imcontact.setImageResource(R.drawable.ic_contact_noraml);
        imsettings.setImageResource(R.drawable.ic_settings_normal);

        tvnotes.setTextColor(getResources().getColor(R.color.normal));
        tvshare.setTextColor(getResources().getColor(R.color.normal));
        tvcontact.setTextColor(getResources().getColor(R.color.normal));
        tvsettings.setTextColor(getResources().getColor(R.color.normal));
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        FragmentManager fm= getFragmentManager();
        FragmentTransaction ft= fm.beginTransaction();
        if (id == R.id.nav_camera) {
            toolbar.setTitle("camera");
//            MainFragment mF1 = new MainFragment(MainActivity.this);
//            Bundle bundle = new Bundle();
//            bundle.putString("name","camera");
//            mF1.setArguments(bundle);
//            ft.replace(R.id.content_main2,mF1);
            MainFragment mf1 = new MainFragment();
            ft.replace(R.id.content_main2,mf1);
        } else if (id == R.id.nav_gallery) {

            toolbar.setTitle("gallery");

        } else if (id == R.id.nav_slideshow) {
            toolbar.setTitle("slideshow");
        } else if (id == R.id.nav_manage) {
            toolbar.setTitle("manage");
        }
        ft.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
