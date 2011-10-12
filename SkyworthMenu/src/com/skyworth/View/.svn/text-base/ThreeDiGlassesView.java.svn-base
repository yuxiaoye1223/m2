package com.skyworth.View;

import com.skyworth.SkyworthMenu.Menucontrol;
import com.skyworth.SkyworthMenu.SearchDrawable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class ThreeDiGlassesView extends View {
    private Bitmap bgbmp = null;
    private Paint txtPaintInfo = null;
    private String txtInfo = Menucontrol.getResXmlString("wear3dglass_tip");
    //private SearchDrawable SearchID;
    private String str_lan = "";
    
    public ThreeDiGlassesView(Context context) {
        super(context);
        str_lan = context.getResources().getConfiguration().locale.getLanguage();
        //SearchID = new SearchDrawable(this.getContext());
        SearchDrawable.InitSearchDrawable(mContext);
        bgbmp = SearchDrawable.getBitmap("bg_info_content");
        txtPaintInfo = new Paint();
        txtPaintInfo.setAntiAlias(true);
        if(str_lan.equals("en"))
        	txtPaintInfo.setTextSize(40);
        else
        	txtPaintInfo.setTextSize(50);
        txtPaintInfo.setColor(0xFFFFFFFF);
    }

    public void onDraw(Canvas canvas) {
    	super.onDraw(canvas);
        canvas.drawBitmap(bgbmp, 0, 0, null);
        canvas.drawText(txtInfo, str_lan.equals("en")?135:175, 180, txtPaintInfo);
    }

}