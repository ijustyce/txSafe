package com.ijustyce.safe;

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

		Button bt9 = (Button) findViewById(R.id.bt9);
		bt9.setBackgroundResource(R.drawable.exit);

		Button bt10 = (Button) findViewById(R.id.bt10);
		bt10.setBackgroundResource(R.drawable.setting);

		int j = R.id.bt1;

		for (int i = j; i < j + 8; i++) {

			Button bt = (Button) findViewById(i);

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

		case R.id.bt10:
			startActivity(new Intent(this, settings.class));
			anim();
			this.finish();
			break;

		case R.id.bt9:
			exit();
			break;

		case R.id.bt8:
			help();
			break;

		case R.id.bt7:
			startActivity(new Intent(this, feedback.class));
			anim();
			this.finish();
			break;

		case R.id.bt6:
			tools();
			break;

		case R.id.bt5:
			privacy();
			break;

		case R.id.bt4:
			scan();
			break;

		case R.id.bt3:
			intercept();
			break;

		case R.id.bt2:
			health();
			break;

		case R.id.bt1:
			locker();
			break;

		}
	}

	private void privacy() {

	}

	private void tools() {

		startActivity(new Intent(this, Tools.class));
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
