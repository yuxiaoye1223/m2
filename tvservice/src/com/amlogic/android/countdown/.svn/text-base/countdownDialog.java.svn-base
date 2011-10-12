package com.amlogic.android.countdown;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import com.amlogic.R;
import com.amlogic.android.eepromapi.eepromJNI;
import com.amlogic.android.eepromapi.eepromJNI.SysStatus;
import com.amlogic.tvjni.GlobalConst;

public class countdownDialog extends Activity {
    private static final String TAG = "countdownDialogActivity";
    private UpdateUIReceiver mReceiver = null;
    private dlgView dview = null;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFormat(PixelFormat.RGBA_8888);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // BitmapFactory.setDefaultConfig(Bitmap.Config.ARGB_8888);
        setContentView(R.layout.main);
        
        dview = (dlgView) findViewById(R.id.mDlgView);
        handler.postDelayed(runnable, 1000);
        initUpdateUIReceiver();
    }

    int countDown = 60;
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            RemoveCountDownDialog();
        }
    };
    Runnable runnable = new Runnable() {
        public void run() {
        	dview.setCountDownValue(--countDown);
        	dview.invalidate();
            if (countDown <= 0) {
                // do standby
                if (countDown == 0) {
                    countDown = -1;
                    PowerOff();
                }
                handler.sendEmptyMessage(0);
            } else
                handler.postDelayed(this, 1000);
        }
    };

    public void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
        // handler.removeCallbacks(runnable);
        // killProcess();
    }

    private int killProcess() {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
        return 0;
    }

    private void initUpdateUIReceiver() {
        mReceiver = new UpdateUIReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(GlobalConst.RECEIVE_TVSERVICE_MSG);
        registerReceiver(mReceiver, filter);
    }

    private class UpdateUIReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(GlobalConst.RECEIVE_TVSERVICE_MSG)) {
                Message msg = intent.getParcelableExtra("msg");
                if (msg.arg1 == GlobalConst.MSG_SIGNAL_STABLE) {
                    // RemoveCountDownDialog();
                }
            }
        }
    };

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
        // case KeyEvent.KEYCODE_BACK:
        // return true;
        }
        RemoveCountDownDialog();
        // setsleeptimeoff
        return super.onKeyDown(keyCode, event);
    }

    public void RemoveCountDownDialog() {
        countDown = -1;
        handler.removeCallbacks(runnable);
        countdownDialog.this.finish();
        Log.d(TAG,"RemoveCountDownDialog");
    }

    public void PowerOff() {
    	eepromJNI.SetSystemAutoSuspending(1);
    	eepromJNI.SaveSysStatus(SysStatus.STANDBY);
        try {
            // echo P > /sys/class/simkey/keyset
            // chmod 0777 /sys/class/simkey/keyset
            BufferedWriter writer = new BufferedWriter(new FileWriter(
                "/sys/class/simkey/keyset"), 2);
            try {
                writer.write("S");
            } finally {
                writer.close();
            }
            Log.d(TAG, "countdownDialogActivity Early PowerOff "
                + "/sys/class/simkey/keyset ok");
        } catch (IOException e) {
            Log.e(TAG,
                "countdownDialogActivity Early PowerOff " + "/sys/class/simkey/keyset", e);
        }
    }
}

class dlgView extends View {
    Bitmap bgbmp = null;
    Paint txtPaintInfo = null;
    Paint txtPaintNum = null;
    String txtInfo = null;
//    String indexInfo = null;
    String txtNum = null;
    private String str_lan;

    public dlgView(Context context, AttributeSet attrs) {
        super(context, attrs);
        bgbmp = BitmapFactory
            .decodeResource(this.getResources(), R.drawable.bg);
        str_lan = context.getResources().getConfiguration().locale
            .getLanguage();
        if (str_lan.equals("en")) {
            txtInfo = this.getContext().getString(R.string.sleeptimer_en);
//            indexInfo = this.getContext().getString(R.string.sleeptimerexit_en);
        } else {
            txtInfo = this.getContext().getString(R.string.sleeptimer_ch);
//            indexInfo = this.getContext().getString(R.string.sleeptimerexit_ch);
        }
        txtPaintInfo = new Paint();
        txtPaintInfo.setAntiAlias(true);
        txtPaintInfo.setTextSize(30);
        txtPaintInfo.setColor(0xFFFFFFFF);
        txtPaintInfo.setTextAlign(Paint.Align.CENTER);
        txtPaintNum = new Paint();
        txtPaintNum.setAntiAlias(true);
        txtPaintNum.setTextSize(230);
        txtPaintNum.setColor(0xFFFFFFFF);
        txtNum = "60";
    }

    public void onDraw(Canvas canvas) {
        canvas.drawBitmap(bgbmp, 0, 0, null);
        canvas.drawText(txtInfo, 351, 60, txtPaintInfo);
//        canvas.drawText(indexInfo, 50, 100, txtPaintInfo);
        canvas.drawText(txtNum, 220, 300, txtPaintNum);
    }

    public void setCountDownValue(int value) {
        txtNum = "" + value;
    }
}