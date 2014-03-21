package com.ijustyce.safe;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

public class health extends baseclass {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.health);

		init();
	}

	private void init() {

		txApplication tx = (txApplication) getApplication();
		String themeString = tx.theme(health.this);

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
			startActivity(new Intent(this, feedback.class));
			anim();
			this.finish();
			break;

		case R.id.bt7:
			help();
			break;

		case R.id.bt6:

			break;

		case R.id.bt5:

			break;

		case R.id.bt4:

			break;

		case R.id.bt3:

			break;

		case R.id.bt2:

			break;

		case R.id.bt1:

			break;

		}
	}

	private void help() {

		Intent intent = new Intent(this, Help.class);
		intent.putExtra("layout", "health_help");
		startActivity(intent);
		anim();
		this.finish();
	}
	
	private void exit(){
		
		MainActivity m = new MainActivity();
		m.exit();
	}
}
