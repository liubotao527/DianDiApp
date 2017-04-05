package com.example.xdcao.diandiapp.DdService.liubotao.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.xdcao.diandiapp.BackUp.caohao.bean.MyUser;
import com.example.xdcao.diandiapp.BackUp.caohao.bean.Post;
import com.example.xdcao.diandiapp.BackUp.caohao.util.uriUtil;
import com.example.xdcao.diandiapp.R;
import com.example.xdcao.diandiapp.DdService.liubotao.database.DateTimeUtil;
import com.example.xdcao.diandiapp.DdService.liubotao.database.DbInfo.NoteItems;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import static android.app.PendingIntent.getActivity;

/*
 * 一条便签的详细信息页面。
 */
public class NoteActivity extends Activity {

	private static final String TAG = "NoteActivity";

	private LinearLayout mLinearLayout_Header;
	private ImageButton ib_bgcolor,add_photo;
	private TextView tv_note_title,photo_text;
	private EditText et_content;

	private int mBackgroud_Color;
	// 用户创建或更新便签的日期/时间
	private String updateDate;
	private String updateTime;
	private int mYear;// 提醒时间的年份
	private int mMonth;// 提醒时间的月份
	private int mDay;// 提醒时间的日(dayOfMonth)
	private int mHour;// 提醒时间的小时
	private int mMinute;// 提醒时间的分钟
	private boolean hasSetAlartTime = false;// 用于标识用户是否设置Alarm
	// 用于判断是新建便签还是更新便签
	private String openType;
	// 数据库中原有的便签的内容
	private String oldContent;
	// 接受传递过来的Intent对象
	private Intent intent;
	// 被编辑的便签的ID
	private int _id;
	// 被编辑便签所在的文件夹的ID
	private int folderId;
	// 设置shortcut时使用该字段
	private final String ACTION_ADD_SHORTCUT = "com.android.launcher.action.INSTALL_SHORTCUT";

	// 菜单
	private static final int MENU_DELETE = Menu.FIRST;
	private static final int MENU_REMIND = Menu.FIRST + 1;
	private static final int MENU_SEND_HOME = Menu.FIRST + 2;
	private static final int MENU_SHARE = Menu.FIRST + 3;
	private ArrayList<Uri> pics=new ArrayList<>();
	private String temp;
	private String imgs="";
	int count=0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 取消标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.note_detail);
		// 得到有前一个Activity传递过来的Intent对象
		intent = getIntent();
		// 如果没有传递Intent对象,则返回主页(MainActivity)
		if (intent.equals(null)) {
			startActivity(new Intent(NoteActivity.this, MyListActivity.class));
		}
		// 取得Open_Type的值,判断是新建便签还是更新便签
		openType = intent.getStringExtra("Open_Type");
		//MyLog.d(MainActivity.TAG, "NoteActivity==>" + String.valueOf(openType));
		// 被编辑的便签的ID
		//_id = intent.getIntExtra(NoteItems._ID, -1);
		//MyLog.d(MainActivity.TAG, "NoteActivity==>被编辑的便签的id:" + _id);
		// 得到文件夹的ID(如果从文件夹页面内新建或编辑便签则要求传递文件夹的ID)
		folderId = intent.getIntExtra("FolderId", -1);
		//MyLog.d(MainActivity.TAG, "NoteActivity==>要操作的文件夹的 id :" + folderId);
		// 在AlarmReceiver中定义
		if (intent.getIntExtra("alarm", -1) == 3080905) {
			// 显示提醒
			//noteAlarm(_id);
		}
		initViews();
		initButton();
	}



	@Override
	protected void onResume() {
		// 恢复Keyguard
		KeyguardManager km = (KeyguardManager) this
				.getSystemService(Context.KEYGUARD_SERVICE);
		KeyguardLock kl = km.newKeyguardLock(MyListActivity.TAG);
		kl.reenableKeyguard();
		photo_text.setText("已选择"+count+"张图片");
		super.onResume();
	}

	@Override
	protected void onPause() {
		// 释放 wakelock
		//WakeLockOpration.release();
		super.onPause();
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// 删除
		menu.add(Menu.NONE, MENU_DELETE, 1, "Delete").setIcon(
				R.drawable.delete);
		// 设置闹铃
		/*menu.add(Menu.NONE, MENU_REMIND, 2, R.string.alarm_time).setIcon(
				R.drawable.alarm_time);
		// 添加到桌面
		menu.add(Menu.NONE, MENU_SEND_HOME, 3, R.string.add_shortcut_to_home)
				.setIcon(R.drawable.add_shortcut_to_home);
		// 修改文件夹
		menu.add(Menu.NONE, MENU_SHARE, 4, R.string.share_sms_or_email)
				.setIcon(R.drawable.share);*/

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_DELETE:
			deleteNote();
			break;
		case MENU_REMIND:
			//setAlarm();
			break;
		case MENU_SEND_HOME:
			//addShortCut();
			break;
		case MENU_SHARE:
			//shareNote();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	// 初始化组件
	private void initViews() {
		mLinearLayout_Header = (LinearLayout) findViewById(R.id.note_detail_header);
		// ImageButton,点击改变便签背景颜色
		ib_bgcolor = (ImageButton) findViewById(R.id.imagebutton_bgcolor);
		tv_note_title = (TextView) findViewById(R.id.tv_note_date_time);
		et_content = (EditText) findViewById(R.id.et_content);
		if (_id != -1) {// 正常得到_id,编辑主页或文件夹下的便签
			// 根据便签的ID查询该便签的详细内容
			Cursor c = getContentResolver().query(
					ContentUris.withAppendedId(NoteItems.CONTENT_URI, _id),
					null, null, null, null);
			c.moveToFirst();
			// 最后更新便签的日期时间及其内容
//			oldContent = c.getString(c.getColumnIndex(NoteItems.CONTENT));
//			updateDate = c.getString(c.getColumnIndex(NoteItems.UPDATE_DATE));
//			updateTime = c.getString(c.getColumnIndex(NoteItems.UPDATE_TIME));


			c.close();
		}
		// 判断打开方式
		if (openType.equals("newNote")) {// 新建"顶级便签",即没有放在文件夹内的便签
			// 初始化新建便签的日期时间
			updateDate = DateTimeUtil.getDate();
			updateTime = DateTimeUtil.getTime();
			// 使用默认的背景颜色
			//et_content.setBackgroundResource(R.drawable.item_light_blue);
		} else if (openType.equals("editNote")) {// 编辑顶级便签(不在文件夹内的便签)
			et_content.setText(oldContent);
			// 根据数据库中的值设定背景颜色
			if (mBackgroud_Color != 0) {
				//et_content.setBackgroundResource(mBackgroud_Color);
				//mLinearLayout_Header.setBackgroundResource(headerBackground(mBackgroud_Color));
			}
		} else if (openType.equals("newFolderNote")) {// 在某文件夹下新建便签
			// 初始化新建便签的日期时间
			updateDate = DateTimeUtil.getDate();
			updateTime = DateTimeUtil.getTime();
			// 使用默认的背景颜色
			//et_content.setBackgroundResource(R.drawable.item_light_blue);
		} else if (openType.equals("editFolderNote")) {// 编辑某文件夹下的便签
			et_content.setText(oldContent);
			if (mBackgroud_Color != 0) {
				//et_content.setBackgroundResource(mBackgroud_Color);
				//mLinearLayout_Header.setBackgroundResource(headerBackground(mBackgroud_Color));
			}
		}

		tv_note_title.setText(updateDate + "\t" + updateTime.substring(0, 5));
	}

	private void initButton() {
		add_photo= (ImageButton) findViewById(R.id.add_photo);
		photo_text= (TextView) findViewById(R.id.photo_text);
		photo_text.setText("已选择"+count+"张图片");
		add_photo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
                /* 开启Pictures画面Type设定为image */
				intent.setType("image/*");
                /* 使用Intent.ACTION_GET_CONTENT这个Action */
				intent.setAction(Intent.ACTION_GET_CONTENT);
                /* 取得相片后返回本画面 */
				startActivityForResult(intent, 1);
			}
		});
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			Uri uri = data.getData();
			Log.e("uri", uri.toString());
			pics.add(uri);
			if(uri!=null){
				count++;
				imgs=imgs+uri.toString()+"\n";
			}

			/*ContentResolver cr = this.getContentResolver();
			try {
				Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
				String path = Bimp.tempSelectBitmap.get(position).getImagePath();

				ImageView imageView = (ImageView) findViewById(R.id.iv01);

				imageView.setImageBitmap(bitmap);
			} catch (FileNotFoundException e) {
				Log.e("Exception", e.getMessage(),e);
			}*/
		}
		super.onActivityResult(requestCode, resultCode, data);
	}





	public void onBackPressed() {
	//	MyLog.d(MainActivity.TAG,
				//"NoteActivity==>onBackPressed()-->用户选择的背景颜色 : "
					//	+ mBackgroud_Color);
		//if (mBackgroud_Color == 0) {// 如果mBackgroud_Color==0,我们使用蓝色作为默认背景
		//	mBackgroud_Color = R.drawable.item_light_blue;
		//}
		// 得到EditText中当前的内容
		String content = et_content.getText().toString();
		// 判断是更新还是新建便签
		if (openType.equals("newNote")) {
			// 创建主页上的便签(顶级便签)
			if (!TextUtils.isEmpty(content)) {
				ContentValues values = new ContentValues();
				values.put(NoteItems.CONTENT, content);
				values.put(NoteItems.UPDATE_DATE, DateTimeUtil.getDate());
				values.put(NoteItems.UPDATE_TIME, DateTimeUtil.getTime());
				values.put(NoteItems.BACKGROUND_COLOR, mBackgroud_Color);
				values.put(NoteItems.IS_FOLDER, "no");
				values.put(NoteItems.PARENT_FOLDER, -1);

				values.put(NoteItems.PICS,imgs);
				getContentResolver().insert(NoteItems.CONTENT_URI, values);

				// TODO: 2017/4/4 向服务器传数据
				Log.d(TAG, "onBackPressed: "+pics.size());
//				Log.d(TAG, "onBackPressed: "+ uriUtil.getImageAbsolutePath(NoteActivity.this,pics.get(0)));
//				Log.d(TAG, "onBackPressed: "+ uriUtil.getImageAbsolutePath(NoteActivity.this,pics.get(0)));
//				Log.d(TAG, "onBackPressed: "+ uriUtil.getImageAbsolutePath(NoteActivity.this,pics.get(0)));
//				Log.d(TAG, "onBackPressed: "+ uriUtil.getImageAbsolutePath(NoteActivity.this,pics.get(0)));
				Post post=new Post();
				post.setContent(content);
				post.setAuthor(BmobUser.getCurrentUser(MyUser.class));
				post.setCreateDate(new BmobDate(new Date()));
				if(pics.size()>0){
					List<BmobFile> bmobFiles=new ArrayList<>();
					for(Uri uri:pics){
						File file=new File(uriUtil.getImageAbsolutePath(NoteActivity.this,uri));
						BmobFile bmobFile=new BmobFile(file);
						bmobFiles.add(bmobFile);
					}
					post.setImages(bmobFiles);
				}
				post.save(new SaveListener<String>() {
					@Override
					public void done(String s, BmobException e) {
						if(e==null){
							Log.d(TAG, "done: "+"状态发送成功");
						}else {
							Log.d(TAG, "done: "+"状态发送失败");
						}
					}
				});
			}
		} else if (openType.equals("newFolderNote")) {
			// 创建文件夹下的便签
			if (!TextUtils.isEmpty(content)) {
				ContentValues values = new ContentValues();
				values.put(NoteItems.CONTENT, content);
				values.put(NoteItems.UPDATE_DATE, DateTimeUtil.getDate());
				values.put(NoteItems.UPDATE_TIME, DateTimeUtil.getTime());
				values.put(NoteItems.BACKGROUND_COLOR, mBackgroud_Color);
				values.put(NoteItems.IS_FOLDER, "no");
				values.put(NoteItems.PARENT_FOLDER, folderId);
				getContentResolver().insert(NoteItems.CONTENT_URI, values);
			}
		} else if (openType.equals("editNote")) {
			// 编辑主页上的便签
			if (!TextUtils.isEmpty(content)) {
				// 内容不为空,更新记录
				ContentValues values = new ContentValues();
				values.put(NoteItems.CONTENT, content);
				values.put(NoteItems.UPDATE_DATE, DateTimeUtil.getDate());
				values.put(NoteItems.UPDATE_TIME, DateTimeUtil.getTime());
				if (hasSetAlartTime) {// 如果用户设置了Alarm,则更新
					values.put(NoteItems.ALARM_TIME, mYear + "-" + mMonth + "-"
							+ mDay + " " + mHour + ":" + mMinute);
					//MyLog.d(MainActivity.TAG, "NoteActivity==>提醒时间:" + mYear
					//		+ "-" + mMonth + "-" + mDay + " " + mHour + ":"
					//		+ mMinute);
				}
				values.put(NoteItems.BACKGROUND_COLOR, mBackgroud_Color);
				//MyLog.d(MainActivity.TAG, "NoteActivity==>用户最终使用的背景颜色: "
				//		+ mBackgroud_Color);
				getContentResolver().update(
						ContentUris.withAppendedId(NoteItems.CONTENT_URI, _id),
						values, null, null);
			}
		} else if (openType.equals("editFolderNote")) {
			// 更新文件夹下的便签
			if (!TextUtils.isEmpty(content)) {
				// 更新记录
				ContentValues values = new ContentValues();
				values.put(NoteItems.CONTENT, content);
				values.put(NoteItems.UPDATE_DATE, DateTimeUtil.getDate());
				values.put(NoteItems.UPDATE_TIME, DateTimeUtil.getTime());
				if (hasSetAlartTime) {
					values.put(NoteItems.ALARM_TIME, mYear + "-" + mMonth + "-"
							+ mDay + " " + mHour + ":" + mMinute);
					//MyLog.d(MainActivity.TAG, "提醒时间:" + mYear + "-" + mMonth
					//		+ "-" + mDay + " " + mHour + ":" + mMinute);
				}
				values.put(NoteItems.BACKGROUND_COLOR, mBackgroud_Color);
				values.put(NoteItems.IS_FOLDER, "no");
				values.put(NoteItems.PARENT_FOLDER, folderId);
				getContentResolver().update(
						ContentUris.withAppendedId(NoteItems.CONTENT_URI, _id),
						values, null, null);
				//MyLog.d(MainActivity.TAG, "NoteActivity==>编辑文件夹下的记录时,文件夹的id : "
				//		+ folderId);
			}
		}
		if (!TextUtils.isEmpty(content)) {
			oldContent = content;
		}
		super.onBackPressed();
	}



	// 删除便签
	private void deleteNote() {
		Context mContext = NoteActivity.this;
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setTitle("Confirm to delete this notes?");
		builder.setPositiveButton("Ok",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 构造Uri
						Uri deleUri = ContentUris.withAppendedId(
								NoteItems.CONTENT_URI, _id);
						getContentResolver().delete(deleUri, null, null);
						//MyLog.d(MainActivity.TAG,"NoteActivity==>deleteNote() via ContentResolver");
						// 返回上一级
						Intent intent = new Intent();
						if (openType.equals("editNote")) {
							// 显示主页
							intent.setClass(NoteActivity.this,
									MyListActivity.class);
						} else if (openType.equals("editFolderNote")) {
							// 显示便签所属文件夹下页面
							intent.putExtra(NoteItems._ID, folderId);
							//intent.setClass(NoteActivity.this,FolderNotesActivity.class);
						}
						startActivity(intent);
					}
				});
		builder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 点击取消按钮,撤销删除便签对话框
						dialog.dismiss();
					}
				});
		AlertDialog ad = builder.create();
		ad.show();
	}
}