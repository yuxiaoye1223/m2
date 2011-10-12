package com.skyworth.View;

import com.skyworth.SkyworthMenu.SearchDrawable;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.graphics.Matrix;
import com.skyworth.SkyworthMenu.GlobalConst;

public class VolumeMuteInfoView extends View {

    //private SearchDrawable SearchID;
    private Bitmap typebmp = null;
    // private String muteStr = null;
    private Paint pa = new Paint();
    private String str_lan;

    public VolumeMuteInfoView(Context context) {
        super(context);
        str_lan = context.getResources().getConfiguration().locale.getLanguage();
        this.setFocusable(false);
        setShowData();
    }

    public void setShowData() {
        //SearchID = new SearchDrawable(this.getContext());
    	SearchDrawable.InitSearchDrawable(mContext);
        str_lan = this.getResources().getConfiguration().locale.getLanguage();
        if (str_lan.equals("en"))
            typebmp = SearchDrawable.getBitmap("shortcut_common_mute_en");
        else
            typebmp = SearchDrawable.getBitmap("shortcut_common_mute_ch");
        // muteStr = Menucontrol.getResXmlString("title_mute");

        pa.setColor(Color.WHITE);
        pa.setTextSize(30);
        pa.setAntiAlias(true);
    }
    
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (GlobalConst.OSD_DISPLAY_HALF_FLAG == 1) {
            Matrix matrix = new Matrix();
            matrix.postScale(0.5f, 1);
            Bitmap dstbmp = Bitmap.createBitmap(typebmp, 0, 0,
                typebmp.getWidth(), typebmp.getHeight(), matrix, true);
            if (typebmp != null)
                canvas.drawBitmap(dstbmp, this.getPaddingLeft(),
                    this.getPaddingTop(), null);
        } else {
            if (typebmp != null)
                canvas.drawBitmap(typebmp, this.getPaddingLeft(),
                    this.getPaddingTop(), null);

            // if (muteStr != null)
            // canvas.drawText(muteStr, this.getPaddingLeft() + 60,
            // this.getPaddingTop() + 120, pa);
        }
    }

}
