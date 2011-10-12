package com.skyworth.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

import com.skyworth.SkyworthMenu.GlobalConst;
import com.skyworth.SkyworthMenu.Menucontrol;
import com.skyworth.SkyworthMenu.SearchDrawable;
import com.skyworth.control.tvsetting;

public class ChannelInfoView extends View {
    //private SearchDrawable SearchID;
    private Bitmap bgbmp = null;
    private int sourceName = 0;
    private String mChL0Str = "";
    private String mChL1Str = "";
    private String mChL2Str = "";
    private String mChL3Str = "";

    public ChannelInfoView(Context context, int sourceName, boolean newSource) {
        super(context);

        this.setFocusable(false);
        this.sourceName = sourceName;
        setShowData(newSource);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (bgbmp != null) {
            canvas.drawBitmap(bgbmp, this.getPaddingLeft(),
                this.getPaddingTop(), null);
        }

        Paint paint = new Paint();
        paint.setTextSize(30);
        paint.setTextAlign(Paint.Align.RIGHT);
        paint.setAntiAlias(true);
        if (sourceName == GlobalConst.TV_SOURCE) {
            paint.setColor(Color.WHITE);
            canvas.drawText(mChL0Str, this.getPaddingLeft() + 100,
                this.getPaddingTop() + 50, paint);
            if (tvsetting.ChInfojump())
                paint.setColor(Color.RED);
            else if (tvsetting.ChInfoaft() == false)
                paint.setColor(Color.YELLOW);
            else
                paint.setColor(Color.WHITE);
            canvas.drawText(mChL1Str, this.getPaddingLeft() + 200,
                this.getPaddingTop() + 50, paint);

            paint.setColor(Color.WHITE);
            canvas.drawText(mChL2Str, this.getPaddingLeft() + 200,
                this.getPaddingTop() + 105, paint);

            canvas.drawText(mChL3Str, this.getPaddingLeft() + 200,
                this.getPaddingTop() + 160, paint);
        } else if (sourceName == GlobalConst.AV1_SOURCE) {
            paint.setColor(Color.WHITE);
            canvas.drawText(mChL0Str, this.getPaddingLeft() + 200,
                this.getPaddingTop() + 50, paint);
            canvas.drawText(mChL2Str, this.getPaddingLeft() + 200,
                this.getPaddingTop() + 105, paint);
        } else {
            paint.setColor(Color.WHITE);
            canvas.drawText(mChL0Str, this.getPaddingLeft() + 264,
                this.getPaddingTop() + 50, paint);
            canvas.drawText(mChL2Str, this.getPaddingLeft() + 264,
                this.getPaddingTop() + 105, paint);
        }
    }

    private void setShowData(boolean newSource) {
        //SearchID = new SearchDrawable(this.getContext());
    	SearchDrawable.InitSearchDrawable(mContext);
        setmChL0Str();
        switch (sourceName) {
            case GlobalConst.TV_SOURCE:
                bgbmp = SearchDrawable.getBitmap("shortcut_bg_box_3");
                setmChL1Str(tvsetting.num());
                setmChL2Str(tvsetting.ChInfovideostd());
                setmChL3Str(tvsetting.ChInfosoundstd());
                break;

            case GlobalConst.AV1_SOURCE:
                bgbmp = SearchDrawable.getBitmap("shortcut_bg_box_2");
                setmChL2Str(tvsetting.GetAvColorSys());
                break;

            case GlobalConst.YPBPR1_SOURCE:
            case GlobalConst.HDMI1_SOURCE:
            case GlobalConst.HDMI2_SOURCE:
            case GlobalConst.HDMI3_SOURCE:
            case GlobalConst.VGA_SOURCE:
                bgbmp = SearchDrawable.getBitmap("channel_info_bg");
                if (newSource == false)
                mChL2Str = tvsetting.GetSigFormatName();
//                Log.d("Haha"," UI get format info is:" + mChL2Str);
                break;

            default:
                break;
        }
    }

    private void setmChL0Str() {
        switch (sourceName) {
            case GlobalConst.TV_SOURCE:
                mChL0Str = Menucontrol.getResXmlString("tv_source_name");
                // mChL0Str = this.getResources()
                // .getString(R.string.tv_source_name);
                break;

            case GlobalConst.AV1_SOURCE:
                mChL0Str = Menucontrol.getResXmlString("av1_source_name");
                // mChL0Str = this.getResources()
                // .getString(R.string.av1_source_name);
                break;

            case GlobalConst.YPBPR1_SOURCE:
                mChL0Str = Menucontrol.getResXmlString("ypbpr1_source_name");
                // mChL0Str = this.getResources()
                // .getString(R.string.ypbpr1_source_name);
                break;

            case GlobalConst.HDMI1_SOURCE:
                mChL0Str = Menucontrol.getResXmlString("hdmi1_source_name");
                // mChL0Str = this.getResources()
                // .getString(R.string.hdmi1_source_name);
                break;

            case GlobalConst.HDMI2_SOURCE:
                mChL0Str = Menucontrol.getResXmlString("hdmi2_source_name");
                // mChL0Str = this.getResources()
                // .getString(R.string.hdmi2_source_name);
                break;

            case GlobalConst.HDMI3_SOURCE:
                mChL0Str = Menucontrol.getResXmlString("hdmi3_source_name");
                // mChL0Str = this.getResources()
                // .getString(R.string.hdmi3_source_name);
                break;

            case GlobalConst.VGA_SOURCE:
                mChL0Str = Menucontrol.getResXmlString("vga_source_name");
                // mChL0Str = this.getResources()
                // .getString(R.string.vga_source_name);
                break;

            default:
                break;
        }
    }

    private void setmChL1Str(int StrId) {
        mChL1Str = String.format("%3d", StrId);
    }

    private void setmChL2Str(int StrId) {
            switch (StrId) {
                case GlobalConst.CVBS_COLORSYS_AUTO:
                    mChL2Str = Menucontrol.getResXmlString("colorsys_auto");
                    break;
                case GlobalConst.CVBS_COLORSYS_PAL:
                    mChL2Str = Menucontrol.getResXmlString("colorsys_pal");
                    break;
                case GlobalConst.CVBS_COLORSYS_NTSC:
                    mChL2Str = Menucontrol.getResXmlString("colorsys_ntsc");
                    break;
                default:
                    mChL2Str = Menucontrol.getResXmlString("colorsys_auto");
                    break;
            }
    }

    private void setmChL3Str(int StrId) {
        switch (StrId) {
            case GlobalConst.TV_CHANNEL_SOUNDSYS_DK:
                mChL3Str = Menucontrol.getResXmlString("soundsys_dk");

                break;
            case GlobalConst.TV_CHANNEL_SOUNDSYS_I:
                mChL3Str = Menucontrol.getResXmlString("soundsys_i");

                break;
            case GlobalConst.TV_CHANNEL_SOUNDSYS_BG:
                mChL3Str = Menucontrol.getResXmlString("soundsys_bg");

                break;
            case GlobalConst.TV_CHANNEL_SOUNDSYS_M:
                mChL3Str = Menucontrol.getResXmlString("soundsys_m");

                break;
            case GlobalConst.TV_CHANNEL_SOUNDSYS_AUTO:
                mChL3Str = Menucontrol.getResXmlString("soundsys_auto");

                break;
            default:
                mChL3Str = Menucontrol.getResXmlString("soundsys_dk");

                break;
        }
    }

}
