
package com.ijustyce.sms;

import com.ijustyce.safe.MainActivity;
import com.ijustyce.safe.R;
import com.ijustyce.safe.txApplication;

import android.os.Bundle;
import android.widget.TextView;

public class about extends MainActivity{
	
	private txApplication tx;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
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
