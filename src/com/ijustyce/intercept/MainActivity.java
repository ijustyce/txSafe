package com.ijustyce.intercept;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.waps.AppConnect;
import cn.waps.UpdatePointsNotifier;

import com.ijustyce.safe.R;
import com.ijustyce.sms.Constants;
import com.txh.Api.md5;
import com.txh.Api.sqlite;

public class MainActivity extends Activity implements UpdatePointsNotifier {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intercept_main);
		firstUse();
		init();

	}

	private boolean firstUse() {

		File dbFile = new File(Constants.dbFile);
		if (!dbFile.exists()) {
			initIntercepter();
			return true;
		}
		return false;
	}

	private void initIntercepter() {

		sqlite api;
		api = new sqlite();

		String dbFile = Constants.dbFile;
		String[] intercept = { "phone" };
		api.createTable("intercept", intercept, dbFile);
		api.createTable("allow", intercept, dbFile);
		String[] history = { "phone", "date" };
		api.createTable("history", history, dbFile);
	}

	private void init() {

		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);

		int width = metric.widthPixels;
		int height = metric.heightPixels - 24;

		int h = 3 * height / 16;
		int w = 11 * width / 30;

		Button bt9 = (Button) findViewById(R.id.bt9);
		bt9.setBackgroundResource(R.drawable.exit);
		Button bt10 = (Button) findViewById(R.id.bt10);
		bt10.setBackgroundResource(R.drawable.setting);

		int j = R.id.bt1;

		for (int i = j; i < j + 8; i++) {
			Button bt = (Button) findViewById(i);
			bt.setHeight(h);
			bt.setWidth(w);
		}

		AppConnect.getInstance("bd7b9fb2373b7d31e4d450a657029e7d", "default",
				this);
		AppConnect.getInstance(this).getPoints(this);
		SharedPreferences shared = getSharedPreferences("point",
				Context.MODE_PRIVATE);
		String point = shared.getString("point", "5");
		if (md5.afterMd5("0").equals(point)) {

			LinearLayout adlayout = (LinearLayout) findViewById(R.id.miniAdLinearLayout);
			AppConnect.getInstance(this).showMiniAd(this, adlayout, 3);

			TextView t = (TextView) findViewById(R.id.textview);
			t.setVisibility(8);
		}

		else {
			AppConnect.getInstance(this).spendPoints(1, this);
		}
	}

	public void btClick(View v) {

		switch (v.getId()) {
		case R.id.bt9:
			exit();
			break;
		case R.id.bt10:
			startActivity(new Intent(this, setting.class));
			break;
		case R.id.bt1:
			startActivity(new Intent(this, black.class));
			this.finish();
			break;
		case R.id.bt2:
			startActivity(new Intent(this, white.class));
			this.finish();
			break;
		case R.id.bt3:
			startActivity(new Intent(this, help.class));
			break;
		case R.id.bt4:
			startActivity(new Intent(this, about.class));
			break;
		case R.id.bt5:
			more();
			break;
		case R.id.bt6:
			point();
			break;
		case R.id.bt7:
			startActivity(new Intent(this, history.class));
			this.finish();
			break;
		case R.id.bt8:
			startActivity(new Intent(this, advance.class));
			break;
		}
	}

	private void exit() {

		finish();
		System.exit(0);
	}

	private void more() {

		startActivity(new Intent(this, feedback.class));
	}

	private void point() {

		AppConnect.getInstance(this).showOffers(this);
	}

	protected void onDestroy() {

		AppConnect.getInstance(this).close();
		super.onDestroy();
	}

	@Override
	public void getUpdatePoints(String arg0, int arg1) {

		SharedPreferences shared = getSharedPreferences("point",
				Context.MODE_PRIVATE);
		shared.edit().putString("point", md5.afterMd5(String.valueOf(arg1)))
				.commit();
		Log.i("---point---", "" + arg1);
	}

	@Override
	public void getUpdatePointsFailed(String arg0) {

	}
}