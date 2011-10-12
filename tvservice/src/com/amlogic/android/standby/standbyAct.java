package com.amlogic.android.standby;


import android.app.Activity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
public class standbyAct extends Activity {
    private static final String TAG = "standbyAct";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFormat(PixelFormat.RGBA_8888);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		Log.d(TAG, "-----standbyAct onCreate");
		this.finish();
    }

    public void onPause() {
        super.onPause();
		Log.d(TAG, "-----standbyAct onPause");
    }

	public void onStop() {
        super.onStop();
		Log.d(TAG, "-----standbyAct onStop");
		this.finish();
    }
    public void onDestroy() {
	super.onDestroy();	
	Log.d(TAG, "-----standbyAct onDestroy");
    }
}
