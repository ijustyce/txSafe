
package com.ijustyce.contacts;

import com.ijustyce.safe.R;
import com.ijustyce.safe.baseclass;

import android.os.Bundle;
import android.widget.TextView;

public class about extends baseclass{
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		
		TextView version = (TextView)findViewById(R.id.version);
		String text = tx.getVersion();
		if(!text.equals("")){
			version.setText(text);
		}
	}
}
