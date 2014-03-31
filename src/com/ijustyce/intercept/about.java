package com.ijustyce.intercept;

import com.ijustyce.safe.R;
import com.ijustyce.safe.txApplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class about extends Activity{
	
	private txApplication tx;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		
		tx = (txApplication)getApplication();
		
		TextView version = (TextView)findViewById(R.id.version);
		String text = tx.getVersion();
		if(!text.equals("")){
			version.setText(text);
		}
	}
}