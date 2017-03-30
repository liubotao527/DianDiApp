package com.example.xdcao.diandiapp.DdService.liubotao.database;

/**
 * Created by Administrator on 2017-3-2.
 */

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import com.example.xdcao.diandiapp.BackUp.caohao.activity.MainActivity;
import java.util.HashMap;

public class MyDbInfoProvider extends ContentProvider {
    private static final String TABLE_NOTEITEMS = "mynoteitems";
    private CupboardSQLiteOpenHelper mOpenHelper;
    private static UriMatcher mUriMatcher;

    // Uri指定到NoteItems表
    private static final int NOTEITEMS = 1;
    // Uri指定到NoteItems表中的一条数据
    private static final int NOTEITEMS_ITEM = 2;
    // 查询表(NoteItems)时要查询的列
    private static HashMap<String, String> mProjectionMap_NoteItems;
    // 静态初始化UriMatcher对象,以及所要使用的ProjectionMap
    static {
        mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        // NoteItems表
        mUriMatcher.addURI(DbInfo.AUTHORITY, "noteitems", NOTEITEMS);
        mUriMatcher.addURI(DbInfo.AUTHORITY, "noteitems/#", NOTEITEMS_ITEM);

        // 初始化查询列
        mProjectionMap_NoteItems = new HashMap<String, String>();
        mProjectionMap_NoteItems.put(DbInfo.NoteItems._ID, DbInfo.NoteItems._ID);
        mProjectionMap_NoteItems.put(DbInfo.NoteItems.CONTENT, DbInfo.NoteItems.CONTENT);
        mProjectionMap_NoteItems.put(DbInfo.NoteItems.UPDATE_DATE, DbInfo.NoteItems.UPDATE_DATE);
        mProjectionMap_NoteItems.put(DbInfo.NoteItems.UPDATE_TIME, DbInfo.NoteItems.UPDATE_TIME);
        mProjectionMap_NoteItems.put(DbInfo.NoteItems.ALARM_TIME, DbInfo.NoteItems.ALARM_TIME);
        mProjectionMap_NoteItems.put(DbInfo.NoteItems.BACKGROUND_COLOR, DbInfo.NoteItems.BACKGROUND_COLOR);
        mProjectionMap_NoteItems.put(DbInfo.NoteItems.IS_FOLDER, DbInfo.NoteItems.IS_FOLDER);
        mProjectionMap_NoteItems.put(DbInfo.NoteItems.PARENT_FOLDER, DbInfo.NoteItems.PARENT_FOLDER);
        mProjectionMap_NoteItems.put(DbInfo.NoteItems.PICS, DbInfo.NoteItems.PICS);
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new CupboardSQLiteOpenHelper(getContext());
        return true;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // 只能通过NoteItems表中的CONTENT_URI来插入数据
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        String table;
        long id;
        Uri tmpUri;
        switch (mUriMatcher.match(uri)) {
            case NOTEITEMS:// Uri匹配到NoteItems表
                table = TABLE_NOTEITEMS;
                id = db.insert(table, DbInfo.NoteItems.CONTENT, values);

                if (id > 0) {
                    tmpUri = ContentUris.withAppendedId(DbInfo.NoteItems.CONTENT_URI, id);
                    getContext().getContentResolver().notifyChange(tmpUri, null);
                    Log.d(MainActivity.TAG, "ContentProvider==>insert()");
                    return tmpUri;
                }
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        throw new SQLException("Failed to insert row into " + uri);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // SQL查询构造器
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        // If no sort order is specified use the default
        String orderBy = "";
        switch (mUriMatcher.match(uri)) {
            case NOTEITEMS_ITEM:// 注意：没有使用break，我们让它使用后面一条case语句的break
                // 组建SQL的where
                queryBuilder.appendWhere(DbInfo.NoteItems._ID + " = "
                        + uri.getPathSegments().get(1));
            case NOTEITEMS:
                // 设置待查询的表
                queryBuilder.setTables(TABLE_NOTEITEMS);
                // 设置要查询的列
                queryBuilder.setProjectionMap(mProjectionMap_NoteItems);
                // 设置排序
                if (TextUtils.isEmpty(sortOrder)) {
                    orderBy = DbInfo.NoteItems.DEFAULT_SORT_ORDER;
                } else {
                    orderBy = sortOrder;
                }
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        Cursor c = queryBuilder.query(db, projection, selection, selectionArgs,
                null, null, orderBy);
        // Tell the cursor what uri to watch, so it knows when its source data
        // changes
        c.setNotificationUri(getContext().getContentResolver(), uri);
        Log.d(MainActivity.TAG, "ContentProvider==>query()");
        return c;
    }










    @Override
    public String getType(Uri uri) {
        switch (mUriMatcher.match(uri)) {
            case NOTEITEMS:
                return DbInfo.NoteItems.CONTENT_TYPE;
            case NOTEITEMS_ITEM:
                return DbInfo.NoteItems.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        // 记录被删除记录条数
        int count = 0;
        String tmpId;
        switch (mUriMatcher.match(uri)) {
            case NOTEITEMS:
                count = db.delete(TABLE_NOTEITEMS, selection, selectionArgs);
                break;
            case NOTEITEMS_ITEM:
                tmpId = uri.getPathSegments().get(1);
                count = db.delete(TABLE_NOTEITEMS, DbInfo.NoteItems._ID
                        + "="
                        + tmpId
                        + (!TextUtils.isEmpty(selection) ? " AND (" + selection
                        + ')' : ""), selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        Log.d(MainActivity.TAG, "ContentProvider==>delete()");
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        // 记录被修改记录条数
        int count = 0;
        String tmpId;
        switch (mUriMatcher.match(uri)) {
            case NOTEITEMS:
                count = db.update(TABLE_NOTEITEMS, values, selection, selectionArgs);
                break;
            case NOTEITEMS_ITEM:
                tmpId = uri.getPathSegments().get(1);
                count = db.update(TABLE_NOTEITEMS, values, DbInfo.NoteItems._ID
                        + "="
                        + tmpId
                        + (!TextUtils.isEmpty(selection) ? " AND (" + selection
                        + ')' : ""), selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        Log.d(MainActivity.TAG, "ContentProvider==>update()");
        return count;
    }

}