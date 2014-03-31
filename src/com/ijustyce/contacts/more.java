package com.ijustyce.contacts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.txh.Api.sqlite;

@SuppressLint("HandlerLeak")
public class more extends baseclass {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more);

		init();
		initDatabase();
	}

	private void init() {

		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);

		int width = metric.widthPixels;
		int height = metric.heightPixels;

		int h = 1 * height / 12;
		int w = 6 * width / 7;

		Button bt = (Button) findViewById(R.id.backup);
		bt.setHeight(h);
		bt.setWidth(w);

		bt = (Button) findViewById(R.id.restore);
		bt.setHeight(h);
		bt.setWidth(w);

		bt = (Button) findViewById(R.id.about);
		bt.setHeight(h);
		bt.setWidth(w);

		bt = (Button) findViewById(R.id.contacts);
		bt.setHeight(h);
		bt.setWidth(w);
	}

	public void btClick(View v) {

		switch (v.getId()) {

		case R.id.backup:
			backupPre();
			break;
		case R.id.restore:
			restorePre();
			break;
		case R.id.about:
			startActivity(new Intent(this, about.class));
			anim();
			this.finish();
		case R.id.contacts:
			startContacts();
			break;
		default:
			break;
		}
	}

	private void initDatabase() {
		File filePath = new File(Environment.getExternalStorageDirectory()
				.getPath() + "/.txh");

		if (!filePath.exists()) {
			filePath.mkdir();
		}
	}

	private void startContacts() {

		Thread thread = new Thread(null, domywork, "Background");
		thread.start();
	}

	private Runnable domywork = new Runnable() {

		public void run() {

			restoreContacts();
			handler.sendEmptyMessage(0);
		}

		/* deal dialog! */
		Handler handler = new Handler() {

			public void handleMessage(Message msg) {

				/* because there just a message */
				Log.i("---tag---", "finish restore");
			}
		};
	};

	private void restoreContacts() {

		sqlite sql = new sqlite();
		String[] column = { "name", "phone" };
		String[][] value = sql.getData(tx.getDbFile(), "contacts",
				"select * from contacts", null, column);
		
		for (int i = 0; i < value.length; i++) {

			AddContact(value[i][0], value[i][1]);
		}
	}

	private void AddContact(String name, String phone) {
		
		ContentResolver resolver = this.getContentResolver();
		Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
		ContentValues values = new ContentValues();

		long id = ContentUris.parseId(resolver.insert(uri, values));
		uri = Uri.parse("content://com.android.contacts/data");

		values.put("raw_contact_id", id);
		values.put("data2", name);
		values.put("mimetype", "vnd.android.cursor.item/name");
		resolver.insert(uri, values);

		values.clear();

		values.put("raw_contact_id", id);
		values.put("data1", phone);
		values.put("data2", "2");
		values.put("mimetype", "vnd.android.cursor.item/phone_v2");
		resolver.insert(uri, values);
	}

	private void backupPre() {

		String title = getResources().getString(R.string.backup);
		String msg = getResources().getString(R.string.warn);
		clickOk(title, msg, "Backup");
	}

	private void backup() {

		File dst = new File(Environment.getExternalStorageDirectory().getPath()
				+ "/.txh/contacts_backup.db");
		File src = new File(tx.getDbFile());

		if (!src.exists()) {
			tx.showToast(R.string.error_fileNotFound);
			return;
		}

		if (dst.exists()) {
			dst.delete();
		}
		copy(src, dst);
	}

	private void restorePre() {

		String title = getResources().getString(R.string.restore);
		String msg = getResources().getString(R.string.warn);

		clickOk(title, msg, "Restore");
	}

	private void restore() {

		File src = new File(Environment.getExternalStorageDirectory().getPath()
				+ "/.txh/contacts_backup.db");
		File dst = new File(tx.getDbFile());

		if (!src.exists()) {
			tx.showToast(R.string.error_fileNotFound);
			return;
		}

		if (dst.exists()) {
			dst.delete();
		}
		copy(src, dst);
	}

	private boolean copy(File src, File dst) {

		boolean result = true;
		try {
			InputStream in = new FileInputStream(src);
			OutputStream out = new FileOutputStream(dst);
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
		} catch (FileNotFoundException e) {
			result = false;
			e.printStackTrace();
			tx.showToast(R.string.error_fileNotFound);
		} catch (IOException e) {
			result = false;
			e.printStackTrace();
		}
		return result;
	}

	private void clickOk(String title, String msg, final String action) {
		new AlertDialog.Builder(this)
				.setTitle(title)
				.setMessage(msg)
				.setNegativeButton(getResources().getString(R.string.no),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {

							}
						})
				.setPositiveButton(getResources().getString(R.string.yes),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								if (action.equals("Backup")) {
									backup();
								} else if (action.equals("Restore")) {
									restore();
								}
							}
						}).show();
	}
}
