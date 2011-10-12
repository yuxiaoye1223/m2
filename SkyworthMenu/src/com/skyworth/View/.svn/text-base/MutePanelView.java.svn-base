package com.skyworth.View;

import com.skyworth.Listener.SkyworthMenuListener;
import com.skyworth.SkyworthMenu.Menucontrol;
import com.skyworth.SkyworthMenu.SearchDrawable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;

public class MutePanelView extends View {
    private Bitmap bgbmp = null;
    private Paint txtPaintInfo = null;
    private Paint txtPaintNum = null;
    private String txtInfo = Menucontrol.getResXmlString("title_audioonly");
//    private String indexInfo = Menucontrol.getResXmlString("index_audioonly");
    
    private String txtNum = "5";
    private int countDown = 5;
    //private SearchDrawable SearchID;
    private SkyworthMenuListener mylistener;
    
    public MutePanelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        
        //SearchID = new SearchDrawable(this.getContext());
        SearchDrawable.InitSearchDrawable(mContext);
        bgbmp = SearchDrawable.getBitmap("bg_info_content");
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

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        public void run() {
            MutePanelView.this.setCountDownValue(--countDown);
            MutePanelView.this.invalidate();
            if (countDown <= 0) {
                if (countDown == 0) {
                    countDown = -1;

                    if (mylistener != null) {
                        mylistener.MutePanelViewToMenuHandle(true);
                    }
                }
            } else
                handler.postDelayed(this, 1000);
        }
    };

    public void setCountDownValue(int value) {
        txtNum = "" + value;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        RemoveCountDownHandler();
        mylistener.MutePanelViewToMenuHandle(false);
        return true;
    }

    private void RemoveCountDownHandler() {
        countDown = -1;
        handler.removeCallbacks(runnable);
    }

    public void onDraw(Canvas canvas) {
    	super.onDraw(canvas);
        canvas.drawBitmap(bgbmp, 0, 0, null);
        canvas.drawText(txtInfo, 351, 60, txtPaintInfo);
//        canvas.drawText(indexInfo, 50, 100, txtPaintInfo);
        canvas.drawText(txtNum, 270, 300, txtPaintNum);
    }

    public void setMutePanelViewListener(SkyworthMenuListener listener) {
        mylistener = listener;
    }

}