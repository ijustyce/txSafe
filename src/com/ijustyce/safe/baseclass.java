package com.ijustyce.safe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

public class baseclass extends Activity {

	public txApplication tx;
	private String className;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		className = this.getLocalClassName().toString();
		tx = (txApplication) getApplication();
		tx.theme(baseclass.this);

		String lock = tx.getPreferences("lock", "pass");
		Log.i("lock", lock);
		if (!lock.equals("null")&&!tx.pw) {

			Intent intent = new Intent(this, Password.class);
			intent.putExtra("lock",lock);
			startActivityForResult(intent, 0);
			tx.pw = true;
		}
	}

	public void anim() {

		String anim = tx.getAnim();
		if (anim.equals("fade")) {
			overridePendingTransition(R.anim.fade, R.anim.hold);
		} else if (anim.equals("zoom")) {
			overridePendingTransition(R.anim.my_scale_action,
					R.anim.my_alpha_action);
		} else if (anim.equals("roll")) {
			overridePendingTransition(R.anim.slide_in_left,
					R.anim.slide_out_right);
		} else if (anim.equals("iphone")) {
			overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
		} else if (anim.equals("Staggered")) {
			overridePendingTransition(R.anim.slide_up_in, R.anim.slide_down_out);
		} else if (anim.equals("unfold")) {
			overridePendingTransition(R.anim.unfold_enter, R.anim.unfold_exit);
		}
		// default is ubuntu style
		else {
			overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {

			if (!className.equals("MainActivity")) {
				startActivity(new Intent(this, MainActivity.class));
				anim();
			}
			this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}
	
	public void onActivityResult(int reqCode, int resultCode, Intent data){  
		 
		 super.onActivityResult(reqCode, resultCode, data);  
		 switch(reqCode){
		 
		 case 0:
			 if(resultCode==RESULT_OK){
				 
				 String text = data.getStringExtra("result");
				 Log.i("result", text);
				 if(text.equals("cancel")){
					 
					 System.gc();
					 System.exit(0);
					 
				 }
			 }
		 }
	}
}
