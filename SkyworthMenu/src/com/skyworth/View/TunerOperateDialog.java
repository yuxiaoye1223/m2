package com.skyworth.View;

import java.text.DecimalFormat;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;

import com.skyworth.Listener.SkyworthMenuListener;
import com.skyworth.SkyworthMenu.GlobalConst;
import com.skyworth.SkyworthMenu.Menucontrol;
import com.skyworth.SkyworthMenu.SearchDrawable;
import com.skyworth.control.tvsetting;

public class TunerOperateDialog extends View {
    private int mTunerOpType = 0;
    private String mDialogTitleName = "";
//    private String mChannelTitleName = Menucontrol.getResXmlString("title_channel");
    private String mFrequencyTitleName = Menucontrol.getResXmlString("title_frequency");
    private String mBandTitleName = Menucontrol.getResXmlString("title_band");

    private String operateTips = "";
    private String mBandStr = "";
    private int mSearchProgressBar = 0;
    //private SearchDrawable SearchID;
    private Bitmap bg;
    private Bitmap bar;
    private Bitmap focus = null, newfocus = null;
    private Bitmap bgbmp = null;
//    private Bitmap progressbarBorder=null;
    private Bitmap leftArrow=null,rightArrow= null;
    private String mChannelStr = "";
    private String mFrequencyStr = "";
    private String str_lan = "";
    private boolean bLeftArrow=false;
    private boolean bRightArrow=false;
    
    private SkyworthMenuListener TunerOperateDialogListener;

    public TunerOperateDialog(Context context, AttributeSet attrs,
        String MenuItemName) {
        super(context, attrs);

        str_lan = context.getResources().getConfiguration().locale.getLanguage();
        if (MenuItemName.equals("shortcut_program_manual_search_")) {
            mTunerOpType = GlobalConst.TUNER_OP_MANUAL_SEARCH;
        } else if (MenuItemName.equals("shortcut_program_fine_")) {
            mTunerOpType = GlobalConst.TUNER_OP_FINE;
        } else if (MenuItemName.equals("shortcut_program_auto_search_")) {
            mTunerOpType = GlobalConst.TUNER_OP_AUTO_SEARCH;
        }
        transTunerChannel(tvsetting.num());

        SetShowData();

            tvsetting.setSearchHandler();

            if ((mTunerOpType == GlobalConst.TUNER_OP_AUTO_SEARCH)
                && (tvsetting.getAutoSearchOn() == false)) {
                operateTips = Menucontrol.getResXmlString("inautosearch_tips");

                tvsetting.SearchChanel(true, false, false);
            }
        }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int osd_half_offset = 1;
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(30);
        paint.setAntiAlias(true);
        // paint.setTextAlign(Paint.Align.CENTER);
        
        if(GlobalConst.OSD_DISPLAY_HALF_FLAG == 1)
        {
        	osd_half_offset =  2;
        	paint.setTextScaleX(0.5f);
        }

        String tmp_str;
        if(GlobalConst.OSD_DISPLAY_HALF_FLAG == 1)
        {
        	Matrix matrix=new Matrix();
            matrix.postScale(0.5f, 1);
            Bitmap dstbmp=Bitmap.createBitmap(bgbmp,0,0,bgbmp.getWidth(),
            		bgbmp.getHeight(),matrix,true);
        	if (bgbmp != null) {
                canvas.drawBitmap(dstbmp, this.getPaddingLeft()/ osd_half_offset,
                    this.getPaddingTop(), null);
        	}
        }
        else
          if (bgbmp != null) {
              canvas.drawBitmap(bgbmp, this.getPaddingLeft()/ osd_half_offset,
                  this.getPaddingTop(), null);
        }

        canvas.drawText(mDialogTitleName, (this.getPaddingLeft() + 100 )/ osd_half_offset,
            this.getPaddingTop() + 70, paint);

//        canvas.drawText(mChannelTitleName, (this.getPaddingLeft() + 400) / osd_half_offset,
//            this.getPaddingTop() + 70, paint);
        tmp_str = mChannelStr;
//        if(str_lan.equals("en"))
//        	canvas.drawText(tmp_str, (this.getPaddingLeft() + 576) / osd_half_offset,
//        			this.getPaddingTop() + 70, paint);
//        else
        	canvas.drawText(tmp_str, (this.getPaddingLeft() + 440) / osd_half_offset,
        			this.getPaddingTop() + 70, paint);        	

        canvas.drawText(mFrequencyTitleName, (this.getPaddingLeft() + 100) / osd_half_offset,
            this.getPaddingTop() + 130, paint);
        tmp_str = mFrequencyStr + " MHz";
        if(str_lan.equals("en"))
        	canvas.drawText(tmp_str, (this.getPaddingLeft() + 260) / osd_half_offset,
        			this.getPaddingTop() + 130, paint);
        else
        	canvas.drawText(tmp_str, (this.getPaddingLeft() + 190) / osd_half_offset,
        			this.getPaddingTop() + 130, paint);
        canvas.drawText(mBandTitleName, (this.getPaddingLeft() + 440) / osd_half_offset,
            this.getPaddingTop() + 130, paint);
        canvas.drawText(mBandStr, (this.getPaddingLeft() + 540) / osd_half_offset,
            this.getPaddingTop() + 130, paint);

        if(GlobalConst.OSD_DISPLAY_HALF_FLAG == 1)
        {
        	Matrix matrix=new Matrix();
            matrix.postScale(0.5f, 1);
            Bitmap dstbmp=Bitmap.createBitmap(bg,0,0,bg.getWidth(),
            		bg.getHeight(),matrix,true);
        	canvas.drawBitmap(dstbmp, (this.getPaddingLeft() + 120) / osd_half_offset,
                    this.getPaddingTop() + 150, paint);
        	if(bLeftArrow)
        	{
            	canvas.drawBitmap(leftArrow, (this.getPaddingLeft() + 120) / osd_half_offset,
                        this.getPaddingTop() + 150, paint);
        	}
        	if(bRightArrow)
        	{
            	canvas.drawBitmap(rightArrow, (this.getPaddingLeft() + 120+402) / osd_half_offset,
                        this.getPaddingTop() + 150, paint);
        	}
        }
        else
        {
        	canvas.drawBitmap(bg, (this.getPaddingLeft() + 120) / osd_half_offset,
              this.getPaddingTop() + 150, paint);
	      	if(bLeftArrow)
	    	{
	        	canvas.drawBitmap(leftArrow, (this.getPaddingLeft() + 120) / osd_half_offset,
	                    this.getPaddingTop() + 150, paint);
	    	}
	    	if(bRightArrow)
	    	{
	        	canvas.drawBitmap(rightArrow, (this.getPaddingLeft() + 120+402) / osd_half_offset,
	                    this.getPaddingTop() + 150, paint);
	    	}         
        }
        if (newfocus != null)
            newfocus.recycle();
        if (mSearchProgressBar > 0) {
            newfocus = Bitmap.createScaledBitmap(focus,
                (mSearchProgressBar * 334 / 300) / osd_half_offset, 21, true);
            canvas.drawBitmap(newfocus, (this.getPaddingLeft() + 184) / osd_half_offset,
                this.getPaddingTop() + 187, paint);
        }

//        paint.setColor(Color.RED);
//        paint.setTextSize(22);
        paint.setTextAlign(Paint.Align.CENTER);
        if(operateTips.contains("\\n"))
        {
            char[] tempChar=operateTips.toCharArray();
            int line=0,startPos=0,endPos=tempChar.length;
            String subSentence="";
        	for(int i=0;i<tempChar.length;i++)
	        {
	        	if(tempChar[i]=='\\'&& i!=(tempChar.length-1))
	        	{
	        		if(tempChar[i+1]=='n')
	        		{
	        			subSentence = operateTips.substring(startPos, i);
	        	        canvas.drawText(subSentence, (this.getPaddingLeft() + 350) / osd_half_offset,
	        		            this.getPaddingTop() + 280+line*25, paint);
	        	        line++;
	        	        startPos=i+2;
	        		}
	        	}
	        }
	        if(startPos<tempChar.length)
	        {
	        	subSentence = operateTips.substring(startPos, endPos);
    	        canvas.drawText(subSentence, (this.getPaddingLeft() + 350) / osd_half_offset,
    		            this.getPaddingTop() + 280+(line)*25, paint);	        	
	        }
        }
        else
        {
	        canvas.drawText(operateTips, (this.getPaddingLeft() + 350) / osd_half_offset,
	            this.getPaddingTop() + 280, paint);
        }
        if(GlobalConst.OSD_DISPLAY_HALF_FLAG == 1)
        {
        	paint.setTextScaleX(1f);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
//            case KeyEvent.KEYCODE_DPAD_UP:
//                if ((mTunerOpType == GlobalConst.TUNER_OP_FINE)
//                    || ((mTunerOpType == GlobalConst.TUNER_OP_MANUAL_SEARCH) && (tvsetting
//                        .getSearchStop()))) {
//                	tvsetting.setTunerLastChannel();
//                    if (tvsetting.num() < GlobalConst.TV_CHANNEL_TOTAL_PROGRAM_COUNT - 1) {
//                    	tvsetting.addorreducenum(1);
//                    } else {
//                    	tvsetting.setnum(GlobalConst.TV_CHANNEL_MIN_NUMBER);
//                    }
//                    tvsetting.setTunerChannel(tvsetting.num());
//                    tvsetting.SaveTvCurrentChannel();
//                    transTunerChannel(tvsetting.num());
//                    tvsetting.updateTunerFrequencyUI();
//                    this.invalidate();
//                }
//                break;
//            case KeyEvent.KEYCODE_DPAD_DOWN:
//                if ((mTunerOpType == GlobalConst.TUNER_OP_FINE)
//                    || ((mTunerOpType == GlobalConst.TUNER_OP_MANUAL_SEARCH) && (tvsetting
//                        .getSearchStop()))) {
//                	tvsetting.setTunerLastChannel();
//                    if (tvsetting.num() > GlobalConst.TV_CHANNEL_MIN_NUMBER) {
//                    	tvsetting.addorreducenum(0);
//                    } else {
//                    	tvsetting.setnum(GlobalConst.TV_CHANNEL_TOTAL_PROGRAM_COUNT - 1);
//                    }
//                    tvsetting.setTunerChannel(tvsetting.num());
//                    tvsetting.SaveTvCurrentChannel();
//                    transTunerChannel(tvsetting.num());
//                    tvsetting.updateTunerFrequencyUI();
//                    this.invalidate();
//                }
//                break;
            case 119:// /factoty 21H seach up
            case KeyEvent.KEYCODE_DPAD_LEFT:
                if (mTunerOpType == GlobalConst.TUNER_OP_MANUAL_SEARCH) {
                        if (tvsetting.getSearchStop()) {
//                            operateTips = Menucontrol.getResXmlString("leftmanualsearch_tips");
                            // manual down start
                            tvsetting.SearchChanel(false, true, false);
                            bRightArrow = false;
                            bLeftArrow = true;
                        } else {
                            if (tvsetting.getSearchDown() == false) {
//                                operateTips = Menucontrol.getResXmlString("leftmanualsearch_tips");
                                // change to manual down
                                tvsetting.setSearchDown(true);
                                bRightArrow = false;
                                bLeftArrow = true;
                            }
                        }
                    }
                if (mTunerOpType == GlobalConst.TUNER_OP_FINE) {
                    	tvsetting.FintTune(-50000);
                        bRightArrow = false;
                        bLeftArrow = true;
                        this.invalidate();
                    }
                break;
            case 118:// /factoty 20H seach down
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if (mTunerOpType == GlobalConst.TUNER_OP_MANUAL_SEARCH) {
                        if (tvsetting.getSearchStop()) {
//                            operateTips = Menucontrol.getResXmlString("rightmanualsearch_tips");
                            // manual up start
                            tvsetting.SearchChanel(false, false, false);
                            bRightArrow = true;
                            bLeftArrow = false;
                        } else {
                            if (tvsetting.getSearchDown() == true) {
//                                operateTips = Menucontrol.getResXmlString("rightmanualsearch_tips");
                                // change to manual up
                                tvsetting.setSearchDown(false);
                                bRightArrow = true;
                                bLeftArrow = false;                                
                            }
                        }
                    }
                if (mTunerOpType == GlobalConst.TUNER_OP_FINE) {
                    	tvsetting.FintTune(50000);
                        bRightArrow = true;
                        bLeftArrow = false;   
                        this.invalidate();
                    }
                break;
//            case KeyEvent.KEYCODE_ENTER:
//                    if ((mTunerOpType == GlobalConst.TUNER_OP_AUTO_SEARCH)
//                        && (tvsetting.getAutoSearchOn() == false)) {
//                        operateTips = Menucontrol.getResXmlString("inautosearch_tips");
// 
//                        tvsetting.SearchChanel(true, false, false);
//                    }
//                break;
            case KeyEvent.KEYCODE_BACK:
                if (TunerOperateDialogListener != null) {
                        if (tvsetting.getSearchStop()) {
                        TunerOperateDialogListener
                             .TunerOperateToMenuHandle(false);
                        } else {
                            // stop search
                        	tvsetting.SearchChanel(false, false, true);
                        }
                    }
                return true;

			case KeyEvent.KEYCODE_MUTE:
			    if (tvsetting.getSearchStop() == false) {
                    return true;
                }
                break;
                
            case 94:// IMAGE_MODE 图像模式
            case 95:// VOICE_MODE 声音模式
            case 96:// DISP_MODE 显示模式
            case 97:// CHANNEL INFO DISPLAY
            case 98:// 3D格式
            case 99:// 3D
            case 100:// SOURCE 通道选择
                if (TunerOperateDialogListener != null) {
                    if (tvsetting.getSearchStop()) {
                        TunerOperateDialogListener.TunerOperateToMenuHandle(false);
                    }
                    return true;
                }
                break;
            case KeyEvent.KEYCODE_MENU:
                if (TunerOperateDialogListener != null) {
                    if (tvsetting.getSearchStop()) {
                        TunerOperateDialogListener
                            .TunerOperateToMenuHandle(false);
                    } else {
                        // stop search
                        tvsetting.SearchChanel(false, false, true);
                    return true;
                    }
                }
                break;

            default:
                break;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
    	switch(keyCode)
    	{
        case KeyEvent.KEYCODE_DPAD_LEFT:
        case KeyEvent.KEYCODE_DPAD_RIGHT:
            if (mTunerOpType == GlobalConst.TUNER_OP_FINE) {
                    bRightArrow = false;
                    bLeftArrow = false; 
                    this.invalidate();
                }
            break;    		
    	}
		return super.onKeyUp(keyCode, event);
	}

	public void setTunerOperateDialogListener(SkyworthMenuListener SFL) {
        TunerOperateDialogListener = SFL;
    }

    private void transTunerFrequency(int freq) {
        if (freq > tvsetting.getMaxFreq()) {
            freq = tvsetting.getMaxFreq();
        } else if (freq < tvsetting.getMinFreq()) {
            freq = tvsetting.getMinFreq();
        }
        DecimalFormat df = new DecimalFormat("0.00");
        mFrequencyStr = df.format((double) freq / 1000000);

    }

    private void getTunerBandStr(int freq) {
        if (freq > tvsetting.getMaxVHFHFreq()) {
            mBandStr = Menucontrol.getResXmlString("uhf_band");
        } else if (freq > tvsetting.getMaxVHFLFreq()) {
            mBandStr = Menucontrol.getResXmlString("vhfh_band");
        } else {
            mBandStr = Menucontrol.getResXmlString("vhfl_band");
        }
    }

    private void setSearchProgressBar(int freq) {
        if (freq > tvsetting.getMaxFreq()) {
            freq = tvsetting.getMaxFreq();
        } else if (freq < tvsetting.getMinFreq()) {
            freq = tvsetting.getMinFreq();
        }
        mSearchProgressBar = (freq - tvsetting.getMinFreq())
            / ((tvsetting.getMaxFreq() - tvsetting.getMinFreq()) / 300);
    }

    public void updateAutoSearchFinished() {
        if (TunerOperateDialogListener != null)
        TunerOperateDialogListener.TunerOperateToMenuHandle(true);
    }

    public void updateManualSearchFinished() {
//        operateTips = Menucontrol.getResXmlString("manualsearchfinish_tips");
        bRightArrow = false;
        bLeftArrow = false;
        this.invalidate();
    }

    public void updateManualSearchAborted() {
        if (TunerOperateDialogListener != null)
        TunerOperateDialogListener.TunerOperateToMenuHandle(false);
    }

    private void transTunerChannel(int channel) {
        mChannelStr = String.format("%3d", channel);
    }

    public void updateTunerOpFrequency(int freq) {
        transTunerFrequency(freq);
        getTunerBandStr(freq);
        setSearchProgressBar(freq);
        this.invalidate();
    }

    public void updateTunerOpFindChannel(int channel) {
        transTunerChannel(channel);
        this.invalidate();
    }

    private void SetShowData() {
        switch (mTunerOpType) {
            case GlobalConst.TUNER_OP_FINE:
            	
                mDialogTitleName = Menucontrol.getResXmlString("title_finetune");

                operateTips = Menucontrol.getResXmlString("finetune_tips");
 
                break;
            case GlobalConst.TUNER_OP_AUTO_SEARCH:
                mDialogTitleName = Menucontrol.getResXmlString("title_autosearch");

                operateTips = Menucontrol.getResXmlString("autosearch_tips");
                bRightArrow = true;
                bLeftArrow = false;

                break;
            case GlobalConst.TUNER_OP_MANUAL_SEARCH:
            default:
                mDialogTitleName = Menucontrol.getResXmlString("title_manualsearch");

                operateTips = Menucontrol.getResXmlString("manualsearch_tips");

                break;
        }
        //SearchID = new SearchDrawable(this.getContext());
        SearchDrawable.InitSearchDrawable(mContext);
        bg = SearchDrawable.getBitmap("search_bar_border");
//        bg = Bitmap.createScaledBitmap(bg, 600, 60, true);
        bar = SearchDrawable.getBitmap("bar_sc_slide_element");
        focus = Bitmap.createBitmap(bar, 0, 14, 10, 11);
        bgbmp = SearchDrawable.getBitmap("bg_info_content");
        leftArrow = SearchDrawable.getBitmap("search_dirc_left");
        rightArrow = SearchDrawable.getBitmap("search_dirc_right");
        updateTunerOpFrequency(tvsetting.manuFreq());
    }
}
