package com.skyworth.View;

import java.sql.Date;
import java.text.SimpleDateFormat;

import com.skyworth.BreakText;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class MenuProgress {
    private SimpleDateFormat sdf;
    private Paint Rectpaint = new Paint();
    private Paint Fontpaint = new Paint();
    private Paint PosScalepaint = new Paint();
    private boolean myVisible = false;
    public int bartotal = 0;
    public int bar_cur_pos = 0;
    public String playername = null;
    private int myType;
    public String playerPosScale = null;
    private int dst_width = 0;
    private String mytime = null;

    MenuProgress() {
        sdf = new SimpleDateFormat("HH:mm:ss");
        Fontpaint.setColor(Color.WHITE);
        Fontpaint.setTextSize(28);
        Fontpaint.setAntiAlias(true);

        Rectpaint.setARGB(0xaf, 0x6c, 0xc0, 0xff);

        PosScalepaint.setColor(Color.WHITE);
        PosScalepaint.setTextSize(28);
        PosScalepaint.setAntiAlias(true);
        PosScalepaint.setTextAlign(Paint.Align.RIGHT);
    }

    public void setVisible(boolean visible) {
        myVisible = visible;
    }

    public void update() {
        if (bartotal != 0 && bar_cur_pos != 0) {
            dst_width = 1840 * bar_cur_pos / bartotal;
            if (dst_width < 1)
                dst_width = 1;
            mytime = formatSeconds2HHmmss(bar_cur_pos) + '/'
                + formatSeconds2HHmmss(bartotal);
        }
    }

    public void myDraw(Canvas canvas) {
        if (myVisible) {
            if (bartotal != 0 && bar_cur_pos != 0) {
                canvas.drawRect(44, 70 + 5, 44 + dst_width, 90 - 5, Rectpaint);
                canvas.drawText(mytime, 1600, 45, Fontpaint);
            }
            if (playername != null) {
                playername = BreakText.breakText(playername, 28, 900);
                if (playername != null) {
                    if (myType == MenuGroup.bar_player)
                        canvas.drawText(playername, 80, 45, Fontpaint);
                    else if (myType == MenuGroup.nobar_player)
                        canvas.drawText(playername, 80, 50, Fontpaint);
                }
            }
            if (playerPosScale != null)
                canvas.drawText(playerPosScale, 1520, 45, PosScalepaint);

        }
    }

    public void setType(int type) {
        myType = type;
    }

    private String formatSeconds2HHmmss(int seconds) {

        Date date = new Date(seconds * 1000);
        return sdf.format(date);
    }
}
