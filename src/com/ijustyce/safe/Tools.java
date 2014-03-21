package com.ijustyce.safe;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ScrollView;

public class Tools extends baseclass{
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tools);
		init();
	}
	
	private void init(){
		
		ScrollView left = (ScrollView)findViewById(R.id.quick_left);
        
        Button bt = new Button(this);
        bt.setText("erweima");
        bt.setBackgroundColor(R.drawable.bkcolor);
        
        left.addView(bt);       
	}
}
