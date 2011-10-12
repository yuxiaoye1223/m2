package com.skyworth.View;

import java.util.List;
import com.skyworth.Listener.SkyworthMenuListener;
import com.skyworth.SkyworthMenu.SearchDrawable;
import com.skyworth.XmlParse.StringItem;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;

public class DialogView extends View {
    //private SearchDrawable SearchID;
    private Bitmap bgbmp = null;
    private Bitmap focusbutton = null;
    private Bitmap unfocusbutton = null;
    private String info = null;
    private String yes = null;
    private String no = null;
    private String recovering = null;
    private boolean focusID = false;
    private SkyworthMenuListener mylistener;
    private boolean recoverflag = false;
    private Paint pa = new Paint();
    public DialogView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // this.setFocusable(true);
        recoverflag = false;
        pa.setColor(Color.WHITE);
        pa.setTextSize(40);
        pa.setAntiAlias(true);
        pa.setTextAlign(Align.CENTER);
    }

    public void initDialogResource(List<StringItem> stringItems) {
        //SearchID = new SearchDrawable(this.getContext());
    	SearchDrawable.InitSearchDrawable(mContext);
        bgbmp = SearchDrawable.getBitmap("bg_info_content");
        focusbutton = SearchDrawable.getBitmap("button_info_content_sel");
        unfocusbutton = SearchDrawable.getBitmap("button_info_content_unsel");
        getString(stringItems);
    }

    private void getString(List<StringItem> stringItems) {
        for (StringItem si : stringItems) {
            if (si.name.equals("BUTTON_OK"))
                yes = si.value;
            else if (si.name.equals("BUTTON_CANCEL"))
                no = si.value;
            else if (si.name.equals("SET_DEFAULT_INFO"))
                info = si.value;
            else if (si.name.equals("RECOVERING"))
            	recovering = si.value;
        }
    }

    public void setDialogListener(SkyworthMenuListener listener) {
        mylistener = listener;
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (bgbmp != null)
            canvas.drawBitmap(bgbmp, this.getPaddingLeft(),
                this.getPaddingTop(), null);

        if(recoverflag)
        {
	        if (recovering != null)
	            canvas.drawText(recovering, getPaddingLeft() + 351,
	                getPaddingTop() + 150, pa);
        }
        else
        {
            if (focusbutton != null && unfocusbutton != null) {
                if (focusID) {
                    canvas.drawBitmap(focusbutton, this.getPaddingLeft() + 40,
                        this.getPaddingTop() + 200, null);
                    canvas.drawBitmap(unfocusbutton, this.getPaddingLeft() + 351,
                        this.getPaddingTop() + 200, null);
                } else {
                    canvas.drawBitmap(focusbutton, this.getPaddingLeft() + 351,
                        this.getPaddingTop() + 200, null);
                    canvas.drawBitmap(unfocusbutton, this.getPaddingLeft() + 40,
                        this.getPaddingTop() + 200, null);
                }
            }
            
	        if (info != null)
	            canvas.drawText(info, getPaddingLeft() + 351,
	                getPaddingTop() + 106, pa);
	        if (yes != null)
	            canvas.drawText(yes, getPaddingLeft() + 190, getPaddingTop() + 280,
	                pa);
	        if (no != null)
	            canvas.drawText(no, getPaddingLeft() + 501, getPaddingTop() + 280,
	                pa);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_LEFT:
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if (focusID)
                    focusID = false;
                else
                    focusID = true;
                this.invalidate();
                break;
            case KeyEvent.KEYCODE_ENTER:
                if (mylistener != null) {
                    if (focusID){
                    	recoverflag = true;
                    	this.invalidate();
                    	mylistener.SetDefaultMessage();
						//mylistener.DialogManage(true);
                    }
                    else
                        mylistener.DialogManage(false);
                }
                break;
        }

        return super.onKeyDown(keyCode, event);
        // return true;
    }

}
