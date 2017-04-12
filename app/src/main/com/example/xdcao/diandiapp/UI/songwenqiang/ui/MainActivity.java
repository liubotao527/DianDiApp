package com.example.xdcao.diandiapp.UI.songwenqiang.ui;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import android.widget.Toast;

import com.example.xdcao.diandiapp.BackUp.caohao.bean.MyUser;
import com.example.xdcao.diandiapp.BackUp.caohao.bean.Supply;
import com.example.xdcao.diandiapp.BackUp.caohao.cons.HandlerCons;
import com.example.xdcao.diandiapp.DdService.liubotao.activity.*;
import com.example.xdcao.diandiapp.R;
import com.example.xdcao.diandiapp.UI.songwenqiang.Fragment.ContactFragment;
import com.example.xdcao.diandiapp.UI.songwenqiang.Fragment.MainFragment;
import com.example.xdcao.diandiapp.UI.songwenqiang.Fragment.SettingFragment;
import com.example.xdcao.diandiapp.UI.songwenqiang.Fragment.ShareFragment;
import com.example.xdcao.diandiapp.UI.songwenqiang.ui.widget.RoundImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobRealTimeData;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import cn.bmob.v3.listener.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    private Toolbar mToolBar;
    private RelativeLayout mRlNotes, mRlShare, mRlContact, mRlSettings;
    private ImageView mImNotes, mImShare, mImContact, mImSettings;
    private TextView mTvNotes, mTvShare, mTvContact, mTvSettings,mTvNickname,mTvSignName;
    private FloatingActionButton mFab;
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;
    private LinearLayout mLLDrawer;
    private RoundImageView mRivPhoto;
    private ImageLoader imageLoader;
    BmobRealTimeData realTimeData=new BmobRealTimeData();

    private static final String TAG = "bmob";

    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;

    private Uri imageUri;

    private String[] mTitles = {"DianDi","衣","食","住","行"};
    private String outputImagePath;
    private MenuItem actionInformation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initImageLoader();
        realDataInit();
        initViews();
        resetFragment(R.id.notes);
        Intent intent = getIntent();
        if(intent!=null){
            String flag,data;
            flag = "extra_data";
            data = intent.getStringExtra(flag);
            if(data!=null){
                switch (data){
                    case "mIvBack":
                        resetFragment(R.id.settings);
                        break;
                    case "mIvNote":
                        resetFragment(R.id.notes);
                        break;
                }
            }

            Log.d(TAG, "onCreate: "+data);

        }
        
        


        mRlNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFragment(R.id.notes);
            }
        });

        mRlShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFragment(R.id.share);
            }
        });

        mRlContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFragment(R.id.contact);
            }
        });

        mRlSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFragment(R.id.settings);
            }
        });

        mLLDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        setSupportActionBar(mToolBar);


        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newNote();
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

    /*
    开启数据实时同步
     */
    private void realDataInit() {


        realTimeData.start(new ValueEventListener() {
            @Override
            public void onConnectCompleted(Exception e) {
                Log.d("bmob", "onConnectCompleted: ");
                if (realTimeData.isConnected()){
                    realTimeData.subTableUpdate("Supply");
                }
            }

            @Override
            public void onDataChange(JSONObject arg0) {
                Log.d("bmob", "onDataChange: "+"("+arg0.optString("action")+")"+"数据："+arg0);
                JSONObject data=arg0.optJSONObject("data");
                if (data.optString("responsor").equals(BmobUser.getCurrentUser().getObjectId())){
                    // TODO: 2017/4/7显示好友请求逻辑
                    if(actionInformation!=null){
                        actionInformation.setIcon(R.drawable.ic_menu_information_press);
                        actionInformation.setTitle("newInformation");
                    }
                }
            }
        });



    }



    /*
    初始化imageloader
     */
    private void initImageLoader() {
        imageLoader=ImageLoader.getInstance();
        ImageLoaderConfiguration configuration=new ImageLoaderConfiguration.Builder(this).build();
        imageLoader.init(configuration);
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
        mTvNickname = (TextView) findViewById(R.id.tv_nickname);
        mTvSignName = (TextView) findViewById(R.id.tv_sign_name);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mLLDrawer = (LinearLayout) findViewById(R.id.ll_drawer);
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setVisibility(View.VISIBLE);

        mRivPhoto = (RoundImageView) findViewById(R.id.iv_photo);



        SharedPreferences pref = getSharedPreferences("data", Context.MODE_PRIVATE);
        String nickname = pref.getString("nickname","DianDi");
        mTvNickname.setText(nickname);
        String signName = pref.getString("signName","点滴--记录生活的美好");
        mTvSignName.setText(signName);



        //加载头像
        MyUser curUser=BmobUser.getCurrentUser(MyUser.class);
        Log.d(TAG, "initViews: "+curUser.getNickName());
        if(curUser.getAvatar()!=null){
            Bitmap bitmap=BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+ File.separator+curUser.getAvatar().getFilename());
            if(bitmap!=null){
                mRivPhoto.setImageBitmap(bitmap);
            }else {
                imageLoader.displayImage(curUser.getAvatar().getFileUrl(),mRivPhoto);
            }
        }


        mRivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                //    指定下拉列表的显示数据
                final String[] camera = {"相机", "相册"};
                //    设置一个下拉的列表选择项
                builder.setItems(camera, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        switch (which){
                            case 0:
                                openCameraStart();
                                break;
                            case 1:
                                openAlbumStart();
                                break;
                            default:
                                break;

                        }
                        dialog.dismiss();
                    }
                });

                builder.show();
            }
        });


    }

    private void resetFragment(int id) {
        resetImage();
        FragmentManager fm= getFragmentManager();
        FragmentTransaction ft= fm.beginTransaction();
        int colorPress = R.color.colorPrimary;
        switch (id){
            case R.id.notes:
                mImNotes.setImageResource(R.drawable.ic_note_press);
                mTvNotes.setTextColor(getResources().getColor(colorPress));
                MainFragment mf = new MainFragment();
                ft.replace(R.id.content_main2,mf);
                mFab.setVisibility(View.VISIBLE);
                break;
            case R.id.share:
                mImShare.setImageResource(R.drawable.ic_share_press);
                mTvShare.setTextColor(getResources().getColor(colorPress));
                ShareFragment sf = new ShareFragment();
                ft.replace(R.id.content_main2,sf);
                //mFab.setVisibility(View.INVISIBLE);
                break;
            case R.id.contact:
                mImContact.setImageResource(R.drawable.ic_contact_press);
                mTvContact.setTextColor(getResources().getColor(colorPress));
                ContactFragment cf = new ContactFragment();
                ft.replace(R.id.content_main2,cf);
               // mFab.setVisibility(View.INVISIBLE);
                break;
            case R.id.settings:
                mImSettings.setImageResource(R.drawable.ic_settings_press);
                mTvSettings.setTextColor(getResources().getColor(colorPress));
                SettingFragment stf = new SettingFragment();
                ft.replace(R.id.content_main2,stf);
                //mFab.setVisibility(View.INVISIBLE);
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
        actionInformation = menu.getItem(2);
        Log.d(TAG, "onCreateOptionsMenu: "+ actionInformation.getItemId());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        System.out.println(id);
        switch (id){
            case R.id.action_search:
                intent = new Intent(this,SearchContentActivity.class);
                startActivity(intent);
                break;
            case R.id.action_add:
                intent = new Intent(this,SearchContactActivity.class);
                startActivity(intent);
                break;
            case R.id.action_information:
                // TODO: 2017/4/11
                item.setIcon(R.drawable.ic_menu_information_normal);
                intent = new Intent(MainActivity.this,NewInformationActivity.class);
                startActivity(intent);
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

    private void openAlbumStart(){
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{ Manifest.permission. WRITE_EXTERNAL_STORAGE }, 1);
        } else {
            openAlbum();
        }
    }
    private void openCameraStart(){
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{ Manifest.permission. CAMERA }, 2);
        } else {
            openCamera();
        }

    }
    private void openCamera() {
        // 创建File对象，用于存储拍照后的图片
        File outputImage = new File(getExternalCacheDir(), "output_image.jpg");
        outputImagePath = outputImage.getPath();
        System.out.println("path"+ outputImagePath);
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT < 24) {
            imageUri = Uri.fromFile(outputImage);
        } else {
            imageUri = FileProvider.getUriForFile(MainActivity.this, "com.example.dcameraalbumtest.fileprovider", outputImage);
        }
        // 启动相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHOTO);
    }


    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO); // 打开相册
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        // 将拍摄的照片显示出来
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        mRivPhoto.setImageBitmap(bitmap);

                        //outPutImagePath  文件的地址
                        uploadPhoto(outputImagePath);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    // 判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        // 4.4及以上系统使用这个方法处理图片
                        handleImageOnKitKat(data);
                    } else {
                        // 4.4以下系统使用这个方法处理图片
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }



    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        Log.d("TAG", "handleImageOnKitKat: uri is " + uri);
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        //在这里可以将imagePath保存起来
        System.out.println("imagePath"+imagePath);

        displayImage(imagePath); // 根据图片路径显示图片
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
           mRivPhoto.setImageBitmap(bitmap);
            uploadPhoto(imagePath);
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 图片的文件本地地址
     * @param ImagePath
     */
    private void uploadPhoto(String ImagePath) {
        //在线程中将图片上传
        UploadThread thread=new UploadThread(ImagePath);
        thread.start();

    }


    /*
    头像上传线程
     */
    class UploadThread extends Thread{

        private String path;

        public UploadThread(String path) {
            this.path = path;
        }

        @Override
        public void run() {
            File img=new File(path);
            final BmobFile bmobImg=new BmobFile(img);
            bmobImg.uploadblock(new UploadFileListener() {
                @Override
                public void done(BmobException e) {
                    if(e==null){
                        MyUser user= BmobUser.getCurrentUser(MyUser.class);
                        user.setAvatar(bmobImg);
                        Log.d(TAG, "done: "+user.getObjectId());
                        user.update(user.getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                Log.d(TAG, "done: "+e);
                                if(e==null){
                                    Log.d(TAG, "done: 用户头像上传成功");
                                    Toast.makeText(MainActivity.this,"头像上传成功",Toast.LENGTH_SHORT).show();
                                }else {
                                    Log.d(TAG, "done: "+e);
                                    Toast.makeText(MainActivity.this,"头像上传失败",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            });
        }
    }

    //跳转到编辑页面
    private void newNote() {
        Intent i = new Intent();
        i.putExtra("Open_Type", "newNote");
        i.setClass(MainActivity.this, NoteActivity.class);
        startActivity(i);
    }

}
