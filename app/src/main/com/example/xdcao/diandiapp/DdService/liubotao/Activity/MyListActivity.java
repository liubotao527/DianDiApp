package com.example.xdcao.diandiapp.DdService.liubotao.Activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.gesture.Gesture;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.xdcao.diandiapp.R;
import com.example.xdcao.diandiapp.DdService.liubotao.adapter.MyCursorAdapter;
import com.example.xdcao.diandiapp.DdService.liubotao.database.DbInfo.NoteItems;

/*
 * 显示所有的便签
 */
public class MyListActivity extends Activity implements OnGesturePerformedListener {
    /** Called when the activity is first created. */
    private static final String SETTINGS = "user_configurations";

    private GestureOverlayView mGestureOverlayView;
    private GestureLibrary mGestureLibrary;
    private String GestureName_Add = "add_Record";

    private ImageButton imageButton;
    private ListView mListview;

    private MyCursorAdapter mAdapter;
    private Cursor mCursor;
    // 菜单
    private static final int MENU_NEW_NOTE = Menu.FIRST;
    private static final int MENU_NEW_FOLDER = Menu.FIRST + 1;
    private static final int MENU_MOVE_TO_FOLDER = Menu.FIRST + 2;
    private static final int MENU_DELETE = Menu.FIRST + 3;
    private static final int MENU_EXPORT_TO_TEXT = Menu.FIRST + 4;
    private static final int MENU_BACKUP_DATA = Menu.FIRST + 5;
    private static final int MENU_RESTORE_DATA_FROM_SDCARD = Menu.FIRST + 6;
    private static final int MENU_SET_PASSWORD = Menu.FIRST + 7;
    private static final int MENU_ABOUT = Menu.FIRST + 8;

    public static final String TAG = "Note";


    private int count = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 取消标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.index_page);

        //this.inputPsd();



        mListview = (ListView) findViewById(R.id.list);
        // 更新ListView数据
        this.updateDisplay();
        mListview.setOnItemClickListener(new OnItemClickListener() {
            // 点击文件夹或者便签执行该回调函数
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent();
                // mCursor在updateDisplay函数中进行初始化
                mCursor.moveToPosition(position);
                //MyLog.d(TAG, "MainActivity==>被点击的记录的Position : " + position);
                // 传递被选中记录的ID
                intent.putExtra(NoteItems._ID,
                        mCursor.getInt(mCursor.getColumnIndex(NoteItems._ID)));
                // 取得此记录的IS_FOLDER字段的值,用以判断选中文件夹还是便签
                String is_Folder = mCursor.getString(mCursor
                        .getColumnIndex(NoteItems.IS_FOLDER));
                if (is_Folder.equals("no")) {
                    // 不是文件夹
                    // 跳转到详细内容页面
                    // 传递此记录的CONTENT字段的值
                    intent.putExtra(NoteItems.CONTENT, mCursor
                            .getString(mCursor
                                    .getColumnIndex(NoteItems.CONTENT)));
                    // 告诉NoteActivity打开它是为了编辑便签
                    intent.putExtra("Open_Type", "editNote");
                    intent.setClass(MyListActivity.this, NoteActivity.class);
                } else if (is_Folder.equals("yes")) {
                    // 是文件夹
                    // 跳转到FileNotesActivity,显示选中的文件夹下所有的便签
                    //intent.setClass(MainActivity.this,FolderNotesActivity.class);
                }
                startActivity(intent);
            }
        });
        // 调用该函数,执行一些初始化的操作
        initViews();
    }

    // 创建菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 新建便签
        menu.add(Menu.NONE, MENU_NEW_NOTE, 1, "New note").setIcon(
                R.drawable.new_note);

        // 删除
        menu.add(Menu.NONE, MENU_DELETE, 4, "Delete").setIcon(
                R.drawable.delete);

        // 设置密码
        menu.add(Menu.NONE, MENU_SET_PASSWORD, 8, "Set Password");

        return super.onCreateOptionsMenu(menu);
    }

    // 菜单选中事件处理函数
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_NEW_NOTE:
                newNote();
                break;
            case MENU_NEW_FOLDER:
                //newFolder();
                break;
            case MENU_MOVE_TO_FOLDER:
                //moveToFolder();
                break;
            case MENU_DELETE:
                delete();
                break;
            case MENU_EXPORT_TO_TEXT:
                //this.exportToTxt();
                break;
            case MENU_BACKUP_DATA:
                //this.backupData();
                break;
            case MENU_RESTORE_DATA_FROM_SDCARD:
                //this.restoreDataFromSDCard();
                break;
            case MENU_SET_PASSWORD:
                //psdDialog();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // 新建便签函数
    private void newNote() {
        Intent i = new Intent();
        i.putExtra("Open_Type", "newNote");
        i.setClass(MyListActivity.this, NoteActivity.class);
        startActivity(i);
    }



    // 删除函数
    private void delete() {
        //	Intent i = new Intent(getApplicationContext(), DeleteRecordsActivity.class);
        //	startActivity(i);
    }






    // 负责更新ListView中的数据
    private void updateDisplay() {
        // 查询条件，查询所有文件夹记录及显示在主页的便签记录
        String selection = NoteItems.IS_FOLDER + " = '" + "yes" + "' or "
                + NoteItems.PARENT_FOLDER + " = " + "-1";

        mCursor = getContentResolver().query(NoteItems.CONTENT_URI, null,
                selection, null, null);
        // This method allows the activity to take care of managing the given
        // Cursor's lifecycle for you based on the activity's lifecycle.
        startManagingCursor(mCursor);
        mAdapter = new MyCursorAdapter(this, mCursor, true);
        mListview.setAdapter(mAdapter);
        //MyLog.d(TAG, "MainActivity==>Update Display finished...");
    }

    // 初始化组件
    private void initViews() {
        imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newNote();
            }
        });
    }

    @Override
    public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {

    }



}