package com.skyworth.View;

import com.skyworth.Listener.SkyworthMenuListener;
import com.skyworth.SkyworthMenu.Menucontrol;
import com.skyworth.SkyworthMenu.SearchDrawable;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.View;

public class TvSearchNavigateDialog extends View {
    //private SearchDrawable SearchID;
    private Bitmap bgbmp = null;
    private String title = null;
    private String firstLine = null;
    private String secondLine = null;
    private String thirdLine = null;
    private SkyworthMenuListener navigateDialogListener;
    private String str_lan = "";

    public TvSearchNavigateDialog(Context context) {
        super(context);
        str_lan = context.getResources().getConfiguration().locale
            .getLanguage();
    }

    public void initDialogResource() {
        //SearchID = new SearchDrawable(this.getContext());
    	SearchDrawable.InitSearchDrawable(mContext);
        bgbmp = SearchDrawable.getBitmap("bg_info_content");
        getString();
    }

    private void getString() {
        title = Menucontrol.getResXmlString("navigate_title");
        firstLine = Menucontrol.getResXmlString("navigate_firstline");
        secondLine = Menucontrol.getResXmlString("navigate_secondline");
        thirdLine = Menucontrol.getResXmlString("navigate_thirdline");
    }

    public void setNavigateDialogListener(SkyworthMenuListener SPL) {
        navigateDialogListener = SPL;
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (bgbmp != null) {
            canvas.drawBitmap(bgbmp, this.getPaddingLeft(),
                this.getPaddingTop(), null);
        }
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        if (str_lan.equals("en"))
            paint.setTextSize(30);
        else
            paint.setTextSize(36);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAntiAlias(true);
        if (title != null) {
            if (title.contains("\\n")) {
                char[] tempChar = title.toCharArray();
                int line = 0, startPos = 0, endPos = tempChar.length;
                String subSentence = "";
                for (int i = 0; i < tempChar.length; i++) {
                    if (tempChar[i] == '\\' && i != (tempChar.length - 1)) {
                        if (tempChar[i + 1] == 'n') {
                            subSentence = title.substring(startPos, i);
                            canvas.drawText(subSentence,
                                (this.getPaddingLeft() + 350),
                                this.getPaddingTop() + 70 + line * 30, paint);
                            line++;
                            startPos = i + 2;
                        }
                    }
                }
                if (startPos < tempChar.length) {
                    subSentence = title.substring(startPos, endPos);
                    canvas.drawText(subSentence, (this.getPaddingLeft() + 350),
                        this.getPaddingTop() + 70 + (line) * 30, paint);
                }
            } else {
                canvas.drawText(title, getPaddingLeft()
                    + (str_lan.equals("en") ? 390 : 340), getPaddingTop() + 90,
                    paint);
            }
        }
        paint.setTextAlign(Paint.Align.LEFT);
        if (str_lan.equals("en"))
            paint.setTextSize(28);
        else
            paint.setTextSize(30);
        if (firstLine != null) {
            canvas.drawText(firstLine, getPaddingLeft()
                + (str_lan.equals("en") ? 100 : 150), getPaddingTop() + 170,
                paint);
        }
        if (secondLine != null) {
            canvas.drawText(secondLine,
                getPaddingLeft() + (str_lan.equals("en") ? 100 : 150),
                getPaddingTop() + 230, paint);
        }
        if (thirdLine != null) {
            canvas.drawText(thirdLine, getPaddingLeft()
                + (str_lan.equals("en") ? 100 : 150), getPaddingTop() + 290,
                paint);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_ENTER:
                if (navigateDialogListener != null) {
                    navigateDialogListener.NavigateDialogToMenuHandle(true);
                }
                break;

            case KeyEvent.KEYCODE_BACK:
                if (navigateDialogListener != null) {
                    navigateDialogListener.NavigateDialogToMenuHandle(false);
                }
                return true;

            case 100:// SOURCE INPUT
            case KeyEvent.KEYCODE_MENU:
                if (navigateDialogListener != null) {
                    navigateDialogListener.NavigateDialogToMenuHandle(false);
                }
                break;

            case KeyEvent.KEYCODE_POWER:
                break;

            default:
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
