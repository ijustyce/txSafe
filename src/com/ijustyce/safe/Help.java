package com.ijustyce.safe;

import android.os.Bundle;
import android.util.Log;

public class Help extends baseclass {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle bundle = this.getIntent().getExtras();
		if(bundle!=null){
			
			String layout = bundle.getString("layout");
			Log.i("---layout---", layout);
		}
		setContentView(R.layout.help);
	}
}
