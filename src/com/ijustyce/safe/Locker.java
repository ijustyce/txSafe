package com.ijustyce.safe;

import java.util.List;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class Locker extends baseclass {

	private TableLayout tab;
	private String clickPkg[];
	private int num = 0;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.locker);

		loadApp();
	}

	private void loadApp() {
		int j = 0;
		Intent intent = new Intent(Intent.ACTION_MAIN, null);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		List<ResolveInfo> list = this.getPackageManager()
				.queryIntentActivities(intent, PackageManager.GET_ACTIVITIES);

		clickPkg = new String[list.size()];
		String[] pkg = new String[2];
		String[] appName = new String[2];
		String[] className = new String[2];

		ScrollView s = (ScrollView) findViewById(R.id.appList);

		tab = new TableLayout(this);
		tab.setStretchAllColumns(true);

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).activityInfo.name.contains(".")) {
				className[j] = list.get(i).activityInfo.name;
			} else {
				className[j] = list.get(i).activityInfo.packageName + "."
						+ list.get(i).activityInfo.name;
			}
			pkg[j] = list.get(i).activityInfo.packageName;
			appName[j] = list.get(i).activityInfo
					.loadLabel(getPackageManager()).toString();
			j++;
			if (j == 2) {
				showApp(pkg, className, appName);
				j = 0;
			}
		}
		s.addView(tab);
	}

	private void showApp(String[] pkg, String[] className, String[] apkName) {
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		int height = metric.heightPixels;
		int h = 3 * height / 16;
		int width = metric.widthPixels;
		int w = 13 * width / 30;

		TableRow row = new TableRow(this);
		for (int i = 0; i < 2; i++) {
			final Button b = new Button(this);
			if (apkName[i].length() > 10) {

				apkName[i] = apkName[i].substring(0, 10);
			}
			b.setText(apkName[i]);
			b.setWidth(w);
			b.setHeight(h);
			final String temp = pkg[i];
			b.setOnLongClickListener(new OnLongClickListener() {

				public boolean onLongClick(View v) {
					// TODO Auto-generated method stub

					clickPkg[num] = temp;
					num++;
					b.setBackgroundColor(getResources().getColor(
							R.color.black_overlay));
					return false;
				}

			});

			b.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {

					if (num > 0) {

						clickPkg[num] = temp;
						num++;
						b.setBackgroundColor(getResources().getColor(
								R.color.black_overlay));
					}
				}
			});

			b.setBackgroundColor(getResources().getColor(R.color.appBj));
			TextView space = new TextView(this);
			space.setWidth(8);
			row.addView(space);
			row.addView(b);
		}
		TextView t = new TextView(this);
		t.setHeight(8);

		tab.addView(row);
		tab.addView(t);
	}

	public void btClick(View v) {

		switch (v.getId()) {

		case R.id.lockBt:
			lockApp();
			break;

		default:
			break;
		}
	}
	
	private void lockApp(){
		
		if(num<1){
			
			Toast.makeText(this, getResources().getString(R.string.lock_error), 
					Toast.LENGTH_LONG).show();
		}
	}
}
