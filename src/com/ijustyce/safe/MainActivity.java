package com.ijustyce.safe;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

public class MainActivity extends baseclass {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		init();
	}

	private void init() {

		txApplication tx = (txApplication) getApplication();
		String themeString = tx.theme(MainActivity.this);

		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);

		int width = metric.widthPixels;
		int height = metric.heightPixels;

		int h = 3 * height / 16;
		int w = 11 * width / 30;

		Button exit = (Button) findViewById(R.id.exit);
		exit.setBackgroundResource(R.drawable.exit);

		Button setting = (Button) findViewById(R.id.setting);
		setting.setBackgroundResource(R.drawable.setting);
		
		Map<String,Integer> map = new HashMap<String,Integer>();
		map.put("id", R.id.safeContacts);
		map.put("id", R.id.safeMessage);
		map.put("id", R.id.interceptList);
		map.put("id", R.id.trashList);
		map.put("id", R.id.interceptSetting);
		map.put("id", R.id.help);
		map.put("id", R.id.feedBack);
		map.put("id", R.id.about);


		for (Map.Entry<String, Integer> entry : map.entrySet()) {

			int id = entry.getValue().intValue();
			Button bt = (Button) findViewById(id);

			if (themeString.equals("beauty")) {

				bt.setAlpha((float) 0.4);
				bt.setBackgroundResource(R.drawable.newmessage);
			}
			bt.setHeight(h);
			bt.setWidth(w);
		}
	}

	public void btClick(View v) {

		switch (v.getId()) {

		case R.id.setting:
			startActivity(new Intent(this, settings.class));
			anim();
			this.finish();
			break;

		case R.id.exit:
			exit();
			break;

		case R.id.about:
			about();
			break;

		case R.id.feedBack:
			startActivity(new Intent(this, feedback.class));
			anim();
			this.finish();
			break;

		case R.id.help:
			help();
			break;

		case R.id.interceptSetting:
			privacy();
			break;

		case R.id.trashList:
			scan();
			break;

		case R.id.interceptList:
			intercept();
			break;

		case R.id.safeMessage:
			health();
			break;

		case R.id.safeContacts:
			locker();
			break;

		}
	}

	private void privacy() {

	}

	private void about() {

	//	startActivity(new Intent(this, Tools.class));
		anim();
		this.finish();
	}

	private void scan() {

	}

	private void intercept() {
		
		
	}

	private void health() {

		startActivity(new Intent(this, health.class));
		anim();
		this.finish();
	}

	private void locker() {

		startActivity(new Intent(this, Locker.class));
		anim();
		this.finish();
	}
	
	private void help(){
		
		Intent intent = new Intent(this, Help.class);
		intent.putExtra("layout", "MainActivity_help");
		startActivity(intent);
		anim();
		this.finish();
	}

	public void exit() {

		System.exit(0);
	}
}
