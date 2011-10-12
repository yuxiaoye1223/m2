package test.TestMenu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

public class CountDownDialog extends View {
    private String TAG = "CountDownDialogView";
    private Bitmap bgbmp = null;
    private Paint txtPaintInfo = null;
    private Paint txtPaintNum = null;
    private String txtInfo = null;
//    private String indexInfo = null;
    private String txtNum = "60";
    private int countDown = 60;
    private ViewListener cdlistener;
    private String str_lan;

    public CountDownDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
        bgbmp = BitmapFactory
            .decodeResource(this.getResources(), R.drawable.bg);
        str_lan = context.getResources().getConfiguration().locale
            .getLanguage();
        if (str_lan.equals("en")) {
            txtInfo = this.getContext().getString(R.string.nosignalsleep_en);
//            indexInfo = this.getContext().getString(R.string.sleepexit_en);
        } else {
            txtInfo = this.getContext().getString(R.string.nosignalsleep_ch);
//            indexInfo = this.getContext().getString(R.string.sleepexit_ch);
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
        this.setFocusable(true);
        this.requestFocus();
        handler.postDelayed(runnable, 1000);
    }

    public void setViewListener(ViewListener listener) {
        this.cdlistener = listener;
    }

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        public void run() {
            CountDownDialog.this.setCountDownValue(--countDown);
            Log.e(TAG, "CountDownDialogView countDown is =  "+countDown);
            CountDownDialog.this.invalidate();
            if (countDown <= 0) {
                // do standby
                if (countDown == 0) {
                    countDown = -1;

                    if (cdlistener != null)
                        cdlistener.removePowerOffCountDown();

                }
            } else
                handler.postDelayed(this, 1000);
        }
    };

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        //RemoveCountDownDialog();
        Log.e(TAG, "onKeyDown keyCode is = "+keyCode);
        return super.onKeyDown(keyCode, event);
    }

    public void RemoveCountDownDialog() {
        countDown = -1;
        handler.removeCallbacks(runnable);
    }

    public void onDraw(Canvas canvas) {
    	super.onDraw(canvas);
        canvas.drawBitmap(bgbmp, 0, 0, null);
        canvas.drawText(txtInfo, 351, 60, txtPaintInfo);
//        canvas.drawText(indexInfo, 50, 100, txtPaintInfo);
        canvas.drawText(txtNum, 220, 300, txtPaintNum);
    }

    private void setCountDownValue(int value) {
        txtNum = "" + value;
    }

}