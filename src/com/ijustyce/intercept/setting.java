package com.ijustyce.intercept;

import com.ijustyce.safe.R;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class setting extends PreferenceActivity {
    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting);
        
    }
}
