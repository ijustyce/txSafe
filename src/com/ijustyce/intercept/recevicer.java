package com.ijustyce.intercept;

import com.ijustyce.safe.txApplication;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.telephony.TelephonyManager;
import android.util.Log;

public class recevicer extends BroadcastReceiver {
	
	String TAG = "receiver" ,mIncomingNumber;
	private txApplication tx;
	public void onReceive(Context context, Intent intent) {
	
		tx = (txApplication)context.getApplicationContext();
		 if (intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)){
			 
			 String phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
			 Log.i(TAG, "call OUT:" + phoneNumber);
		  } 
		 else{
		   TelephonyManager tManager =
		   (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
		   
		   switch (tManager.getCallState()){
		   case TelephonyManager.CALL_STATE_RINGING:
			   if(!tx.getCallState()&&!tx.runing){
				   
				   mIncomingNumber = intent.getStringExtra("incoming_number");
				   Log.i(TAG, "RINGING :" + mIncomingNumber);
				   tx.setPhone(mIncomingNumber);
				   Intent intercept = new Intent(context, service.class);
				   intercept.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				   context.startService(intercept);
				   tx.runing = true;
			   }
			   break;
		   case TelephonyManager.CALL_STATE_OFFHOOK:
			   tx.setCallState(true);
			   Log.i(TAG, "incoming ACCEPT");
			   break;
		   case TelephonyManager.CALL_STATE_IDLE:
			   tx.setCallState(false);
			   Log.i(TAG, "end call");
			   
			   AudioManager audio = (AudioManager)context.
			                         getSystemService(Context.AUDIO_SERVICE); 
			   audio.setRingerMode(AudioManager.RINGER_MODE_SILENT);
			   break;
		   }
		}
	}
}
