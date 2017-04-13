package com.example.xdcao.diandiapp.DdService.liubotao.PicsSelect.multiphotopicker.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;

import com.example.xdcao.diandiapp.DdService.liubotao.activity.NoteActivity;
import com.example.xdcao.diandiapp.DdService.liubotao.ninegridlayout.util.ImageLoaderUtil;
import com.example.xdcao.diandiapp.R;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.HashMap;


public class ImageDisplayer
{
	private static ImageDisplayer instance;
	private Context context;
	private static final int THUMB_WIDTH = 256;
	private static final int THUMB_HEIGHT = 256;
	private int mScreenWidth;
	private int mScreenHeight;

	public static ImageDisplayer getInstance(Context context)
	{
		if (instance == null)
		{
			synchronized (ImageDisplayer.class)
			{
				instance = new ImageDisplayer(context);
			}
		}

		return instance;
	}

	public ImageDisplayer(Context context)
	{
		if (context.getApplicationContext() != null) this.context = context
				.getApplicationContext();
		else
			this.context = context;

		DisplayMetrics dm = new DisplayMetrics();
		dm = this.context.getResources().getDisplayMetrics();
		mScreenWidth = dm.widthPixels;
		mScreenHeight = dm.heightPixels;
	}

	public Handler h = new Handler();
	public final String TAG = getClass().getSimpleName();
	private HashMap<String, SoftReference<Bitmap>> imageCache = new HashMap<String, SoftReference<Bitmap>>();

	public void put(String key, Bitmap bmp)
	{
		if (!TextUtils.isEmpty(key) && bmp != null)
		{
			imageCache.put(key, new SoftReference<Bitmap>(bmp));
		}
	}

	public void displayBmp(final ImageView iv, final String thumbPath,
                            String sourcePath)
	{
        if(sourcePath!=null) {
			if(!sourcePath.startsWith("http")){
				sourcePath=getImageContentUri(context,sourcePath).toString();
				Log.e("sourcepath", sourcePath);
				displayBmp(iv, thumbPath, sourcePath, true);

			}else {
				Log.e("sourcepath", sourcePath);
				//ImageLoaderUtil.getImageLoader(context).displayImage(sourcePath, iv, ImageLoaderUtil.getPhotoImageOption());
				ImageLoaderUtil.getImageLoader(context).displayImage(sourcePath, iv, ImageLoaderUtil.getPhotoImageOption());
			}
        }else{
			Log.e("sourcepath", "null!!");
		}
	}

	public void displayBmp(final ImageView iv, final String thumbPath,
                           final String sourcePath, final boolean showThumb)
	{
		if (TextUtils.isEmpty(thumbPath) && TextUtils.isEmpty(sourcePath))
		{
			Log.e(TAG, "no paths pass in");
			return;
		}

		if (iv.getTag() != null && iv.getTag().equals(sourcePath))
		{
			return;
		}

		showDefault(iv, showThumb);

		final String path;
		if (!TextUtils.isEmpty(thumbPath) && showThumb)
		{
			path = thumbPath;
		}
		else if (!TextUtils.isEmpty(sourcePath))
		{
			path = sourcePath;
		}
		else
		{
			return;
		}

		iv.setTag(path);

		if (imageCache.containsKey(showThumb ? path + THUMB_WIDTH
				+ THUMB_HEIGHT : path))
		{
			SoftReference<Bitmap> reference = imageCache.get(showThumb ? path
					+ THUMB_WIDTH + THUMB_HEIGHT : path);
			// 可以用LruCahche会好些
			Bitmap imgInCache = reference.get();
			if (imgInCache != null)
			{
				refreshView(iv, imgInCache, path);
				return;
			}
		}
		iv.setImageBitmap(null);

		// 不在缓存则加载图片
		new Thread()
		{
			Bitmap img;

			public void run()
			{

				try
				{
					if (path != null && path.equals(thumbPath))
					{
						img = BitmapFactory.decodeFile(path);
					}
					if (img == null)
					{
						img = compressImg(sourcePath, showThumb);
					}
				}
				catch (Exception e)
				{

				}

				if (img != null)
				{
					put(showThumb ? path + THUMB_WIDTH + THUMB_HEIGHT : path,
							img);

				}
				h.post(new Runnable()
				{
					@Override
					public void run()
					{
						refreshView(iv, img, path);
					}
				});
			}
		}.start();

	}

	private void refreshView(ImageView imageView, Bitmap bitmap, String path)
	{
		if (imageView != null && bitmap != null)
		{
			if (path != null)
			{
				((ImageView) imageView).setImageBitmap(bitmap);
				imageView.setTag(path);
			}
		}
	}

	private void showDefault(ImageView iv, boolean isShowThumb)
	{
		
		if(isShowThumb) 
			// 图片列表默认背景
			iv.setBackgroundResource(R.drawable.bg_img);
		else 
			// 图片预览默认背景
			iv.setBackgroundColor(0x00000000);
	}

	public Bitmap compressImg(String path, boolean showThumb)
			throws IOException
	{
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(
				new File(path)));
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(in, null, opt);
		in.close();
		int i = 0;
		Bitmap bitmap = null;
		if (showThumb)
		{
			while (true)
			{
				if ((opt.outWidth >> i <= THUMB_WIDTH)
						&& (opt.outHeight >> i <= THUMB_HEIGHT))
				{
					in = new BufferedInputStream(new FileInputStream(new File(
							path)));
					opt.inSampleSize = (int) Math.pow(2.0D, i);
					opt.inJustDecodeBounds = false;
					bitmap = BitmapFactory.decodeStream(in, null, opt);
					break;
				}
				i += 1;
			}
		}
		else
		{
			while (true)
			{
				if ((opt.outWidth >> i <= mScreenWidth)
						&& (opt.outHeight >> i <= mScreenHeight))
				{
					in = new BufferedInputStream(new FileInputStream(new File(
							path)));
					opt.inSampleSize = (int) Math.pow(2.0D, i);
					opt.inJustDecodeBounds = false;
					bitmap = BitmapFactory.decodeStream(in, null, opt);
					break;
				}
				i += 1;
			}
		}
		return bitmap;
	}

	public interface ImageCallback
	{
		public void imageLoad(ImageView imageView, Bitmap bitmap,
                              Object... params);
	}

	public static Uri getImageContentUri(Context context, String filePath) {
		File imageFile=new File(filePath);

		Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				new String[] { MediaStore.Images.Media._ID }, MediaStore.Images.Media.DATA + "=? ",
				new String[] { filePath }, null);
		if (cursor != null && cursor.moveToFirst()) {
			int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
			Uri baseUri = Uri.parse("content://media/external/images/media");
			return Uri.withAppendedPath(baseUri, "" + id);
		} else {
			if (imageFile.exists()) {
				ContentValues values = new ContentValues();
				values.put(MediaStore.Images.Media.DATA, filePath);
				return context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
			} else {
				return null;
			}
		}
	}
}
