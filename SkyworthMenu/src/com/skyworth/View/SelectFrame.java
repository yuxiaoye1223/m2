package com.skyworth.View;

import java.util.ArrayList;

import com.skyworth.BreakText;
import com.skyworth.Listener.SkyworthMenuListener;
import com.skyworth.SkyworthMenu.GlobalConst;
import com.skyworth.SkyworthMenu.Menucontrol;
import com.skyworth.SkyworthMenu.SearchDrawable;
import com.skyworth.control.tvsetting;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

public class SelectFrame extends View {
    private ArrayList<String> SelectContext;
    private ArrayList<String> SelectContextID;

    private Bitmap AllBtpBG;
    private int total_item_count = 0;
    private int cur_page_index = 0;
    private int cur_page_show_item_count = 0;
    private int cc_page_show_item_count = 0;

    private Bitmap BtpFoucs = null;
    private Bitmap BtpCheck = null;
    private int FoucsID;
    private int CheckedID;
    private boolean focusFlag = false;
    private SkyworthMenuListener SelectMenuListener;
    //private SearchDrawable SearchID;
    private boolean IfSelect;
    private int StartfocusID = -1;
    private boolean TVSelectFlag = false;
    private Paint paint = new Paint();
    private Paint ablepaint = new Paint();
    private Paint disablepaint = new Paint();
    private String _entrance = null;

    public SelectFrame(Context context, AttributeSet attrs, String entrance) {
        super(context, attrs);
        _entrance = entrance;
        this.setVisibility(View.INVISIBLE);
        //SearchID = new SearchDrawable(this.getContext());
        SearchDrawable.InitSearchDrawable(mContext);
        ablepaint.setColor(Color.WHITE);
        ablepaint.setTextSize(28);
        ablepaint.setTextAlign(Paint.Align.CENTER);
        ablepaint.setAntiAlias(true);
        disablepaint.setColor(Color.GRAY);
        disablepaint.setTextSize(28);
        disablepaint.setTextAlign(Paint.Align.CENTER);
        disablepaint.setAntiAlias(true);
//        this.setOnFocusChangeListener(focusListener);
    }
    
//    public void onWindowFocusChanged(boolean hasWindowFocus){
//    	Log.d("Haha","SF onWindowFocusChanged...hasWindowFocus:" + hasWindowFocus);
//    	this.setFocusable(false);
//    }
//    
//    OnFocusChangeListener focusListener = new OnFocusChangeListener(){
//        public void onFocusChange(View v, boolean hasFocus) {
//        	Log.d("Haha","SF onFocusChange...hasFocus:" + hasFocus);
//        }
//    };
    
    public void setSFData(ArrayList<String> list, ArrayList<String> listID,
        boolean ifselect) {
        int checked_id = -1;
        SelectContext = list;
        SelectContextID = listID;
        total_item_count = SelectContext.size();
        if (total_item_count > GlobalConst.ITEM_COUNT_PERMENURPAGE_SHOW) {
            cc_page_show_item_count = GlobalConst.ITEM_COUNT_PERMENURPAGE_SHOW;
        } else {
            cc_page_show_item_count = total_item_count;
        }

        IfSelect = ifselect;
        if (IfSelect == true) {

            int focus = filterIDGetFocus();
            if (focus >= 0) {
                CheckedID = focus;
                TVSelectFlag = true; // initState is not save data of checkedID
            } else if (Menucontrol.InitState != null) {
                for (int i = 0; i < Menucontrol.InitState.size(); i++) {
                    for (int j = 0; j < listID.size(); j++) {
                        if (listID.get(j).equals(Menucontrol.InitState.get(i))) {
                            if (total_item_count > GlobalConst.ITEM_COUNT_PERMENURPAGE_SHOW) {
                                cur_page_index = j / cc_page_show_item_count;
                                FoucsID = j % cc_page_show_item_count;

                            } else {
                                cur_page_index = 0;
                                FoucsID = j;
                            }
                            CheckedID = j;
                            checked_id = CheckedID;
                            StartfocusID = i;
                            i = Menucontrol.InitState.size();
                            break;
                        }
                    }
                }
            }
        } else {
            FoucsID = 0;
        }

        if (checked_id != -1) {
            if (checked_id >= total_item_count) {
                checked_id = 0;
            }
            if (total_item_count > GlobalConst.ITEM_COUNT_PERMENURPAGE_SHOW) {
                cur_page_index = checked_id / cc_page_show_item_count;
                FoucsID = checked_id % cc_page_show_item_count;
            } else {
                cur_page_index = 0;
                FoucsID = checked_id;
            }
            CheckedID = checked_id;
        }
        setCurPageShowItemCount();
        GetBitmapBG();
    }

    public void initSelectItem(int focusid) {

        TVSelectFlag = true;
        if (focusid >= total_item_count)
            focusid = total_item_count - 1;
        if (total_item_count > GlobalConst.ITEM_COUNT_PERMENURPAGE_SHOW) {
            cur_page_index = focusid / cc_page_show_item_count;
            FoucsID = focusid % cc_page_show_item_count;
            CheckedID = focusid;
        } else {
            cur_page_index = 0;
            FoucsID = focusid;
            CheckedID = focusid;
        }
        setCurPageShowItemCount();
    }

    public void restoreSFData() {
        focusFlag = false;
        TVSelectFlag = false;
        StartfocusID = -1;
        CheckedID = 0;
        FoucsID = 0;
        total_item_count = 0;
        cur_page_index = 0;
        cur_page_show_item_count = 0;
        cc_page_show_item_count = 0;
        this.setVisibility(View.INVISIBLE);
    }

    public boolean getFocusFlag() {
        return focusFlag;
    }

    public void sfupdate() {
        this.invalidate();
    }
    
    public void setmyFocus() {
        focusFlag = true;
        this.invalidate();
    }

    private void showdrawBitmap(Canvas canvas, Bitmap bitmap1, float left,
        float top, Paint paint) {
        if (GlobalConst.OSD_DISPLAY_HALF_FLAG == 1) {
            int osd_half_offset = 2;
            Matrix matrix = new Matrix();
            matrix.postScale(0.5f, 1);
            Bitmap dstbmp = Bitmap.createBitmap(bitmap1, 0, 0,
                bitmap1.getWidth(), bitmap1.getHeight(), matrix, true);
            canvas.drawBitmap(dstbmp, left, top, paint);
        } else if (GlobalConst.OSD_DISPLAY_HALF_FLAG == 2) {
            int osd_half_offset = 2;
            Matrix matrix = new Matrix();
            matrix.postScale(1, 0.5f);
            Bitmap dstbmp = Bitmap.createBitmap(bitmap1, 0, 0,
                bitmap1.getWidth(), bitmap1.getHeight(), matrix, true);
            canvas.drawBitmap(dstbmp, left, top, paint);
        } else
            canvas.drawBitmap(bitmap1, left, top, paint);
    }

    private void showdrawText(Canvas canvas, String arg0, float arg1, float arg2) {
        int osd_half_offset = 1;
        if (GlobalConst.OSD_DISPLAY_HALF_FLAG == 1) {
            osd_half_offset = 2;
            paint.setTextScaleX(0.5f);
        }
        if (GlobalConst.OSD_DISPLAY_HALF_FLAG == 2) {
            osd_half_offset = 2;
            paint.setTextSize(14);
            paint.setTextScaleX(2f);
        }
        if (arg0 != null)
            canvas.drawText(arg0, arg1, arg2, paint);
        if (GlobalConst.OSD_DISPLAY_HALF_FLAG != 0) {
            paint.setTextScaleX(1f);
            paint.setTextSize(28);
        }
    }

    // @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // if( null == AllBtpBG )
        // return ;
        int item_count_offset = 55;
        if (GlobalConst.OSD_DISPLAY_HALF_FLAG == 2)
            item_count_offset = 55 / 2;

        int show_start = cur_page_index * cc_page_show_item_count;
        if (null == AllBtpBG)
            return;

        showdrawBitmap(canvas, AllBtpBG, this.getPaddingLeft(),
            this.getPaddingTop(), null);
        if (focusFlag) {

            showdrawBitmap(canvas, BtpFoucs, this.getPaddingLeft(),
                this.getPaddingTop() + FoucsID * item_count_offset, null);
        }

        if (IfSelect) {
            if (total_item_count > GlobalConst.ITEM_COUNT_PERMENURPAGE_SHOW) {
                if (CheckedID >= show_start
                    && CheckedID < show_start + cur_page_show_item_count) {

                    showdrawBitmap(canvas, BtpCheck, this.getPaddingLeft(),
                        this.getPaddingTop() + CheckedID
                            % cc_page_show_item_count * item_count_offset, null);
                }
            } else {

                showdrawBitmap(canvas, BtpCheck, this.getPaddingLeft(),
                    this.getPaddingTop() + CheckedID * item_count_offset, null);// 20101108
            }
        }
        for (int i = 0; i < cur_page_show_item_count; i++) {
            String modifyText = BreakText.breakText(
                SelectContext.get(show_start + i), 28, 160);
            if (SelectMenuListener != null) {
                if (SelectMenuListener
                    .CheckSelectFrameItemDisable(SelectContextID.get(show_start
                        + i)))
                    paint = disablepaint;
                else
                    paint = ablepaint;
            }
            if (GlobalConst.OSD_DISPLAY_HALF_FLAG == 1)// //osd 缩小一半
            {

                if (modifyText != null)
                    showdrawText(canvas, modifyText,
                        this.getPaddingLeft() + 128 / 2, this.getPaddingTop()
                            + item_count_offset * i + 45);

            } else if (GlobalConst.OSD_DISPLAY_HALF_FLAG == 2)// //osd 缩小一半
            {

                if (modifyText != null)
                    showdrawText(canvas, modifyText,
                        this.getPaddingLeft() + 128, this.getPaddingTop()
                            + item_count_offset * i + 45 / 2);

            } else if (modifyText != null)
                showdrawText(canvas, modifyText, this.getPaddingLeft() + 128,
                    this.getPaddingTop() + item_count_offset * i + 45);
        }

    }

    // @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_UP:
 
                if (FoucsID > 0) {
                    if(SelectContextID.get(FoucsID).equals("shortcut_video_3d_mode_auto")){
                    	break;
                    }
                    FoucsID--;

                    if (SelectMenuListener != null) {
                        if (SelectContextID.get(FoucsID).equals(
                            "shortcut_video_3d_mode_auto")
                            || SelectContextID.get(FoucsID).equals(
                                "shortcut_video_3d_mode_2d3d")) {
                            if (SelectMenuListener
                                .CheckSelectFrameItemDisable(SelectContextID
                                    .get(FoucsID))) {
                                FoucsID--;
                            }
                        } else if (SelectContextID.get(FoucsID).equals(
                            "shortcut_video_3d_mode_lr")) {
                            if (SelectMenuListener
                                .CheckSelectFrameItemDisable(SelectContextID
                                    .get(FoucsID))) {
                                FoucsID--;
                                if (SelectMenuListener
                                    .CheckSelectFrameItemDisable(SelectContextID
                                        .get(FoucsID))) {
                                    FoucsID--;
                                }
                            }
                        } else if (SelectContextID.get(FoucsID).equals(
                            "shortcut_video_3d_mode_ud")) {
                            if (SelectMenuListener
                                .CheckSelectFrameItemDisable(SelectContextID
                                    .get(FoucsID))) {
                                FoucsID -= 2;
                                if (SelectMenuListener
                                    .CheckSelectFrameItemDisable(SelectContextID
                                        .get(FoucsID))) {
                                    FoucsID--;
                                }
                            }
                        } else if(SelectContextID.get(FoucsID).equals(
                        		"shortcut_setup_video_display_mode_subtitle")){
                        	if (SelectMenuListener
                                    .CheckSelectFrameItemDisable(SelectContextID
                                        .get(FoucsID))) {
                                	FoucsID = 0;  //16:9
                            }
                        } 
                    } 
                } else {
                    ChangeToNextPage(true);
                    if (SelectContextID.get(FoucsID).equals(
                        "shortcut_video_3d_mode_ud")) {
                        if (SelectMenuListener
                            .CheckSelectFrameItemDisable(SelectContextID
                                .get(FoucsID))) {
                            FoucsID -= 2;
                            if (SelectMenuListener
                                .CheckSelectFrameItemDisable(SelectContextID
                                    .get(FoucsID))) {
                                FoucsID--;
                            }
                        }
                    }
                    else if (SelectContextID.get(FoucsID).equals(
                        "shortcut_setup_sys_six_color_splitdemo")) {
                        if (SelectMenuListener
                            .CheckSelectFrameItemDisable(SelectContextID
                                .get(FoucsID))) {
                            FoucsID --;
                        }
                    }
                    else if(SelectContextID.get(FoucsID).equals(
            			"shortcut_setup_video_display_mode_panorama")){
		            	if (SelectMenuListener
		                        .CheckSelectFrameItemDisable(SelectContextID
		                            .get(FoucsID))) {
		                    	FoucsID = 4;  //4:3
		                }
		            }
                }
                postEnterMessage(SelectContextID.get(FoucsID), true);
                this.invalidate();
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:

                if (FoucsID < cur_page_show_item_count - 1) {
                	if(SelectContextID.get(FoucsID).equals("shortcut_video_3d_mode_auto")){
                    	break;
                    }
                    FoucsID++;

                    if (SelectMenuListener != null) {
                        if (SelectContextID.get(FoucsID).equals(
                            "shortcut_video_3d_mode_auto")) {
                            if (SelectMenuListener
                                .CheckSelectFrameItemDisable(SelectContextID
                                    .get(FoucsID))) {
                                FoucsID++;
                            }
                        } else if (SelectContextID.get(FoucsID).equals(
                            "shortcut_video_3d_mode_2d3d")
                            || SelectContextID.get(FoucsID).equals(
                                "shortcut_video_3d_mode_lr")
                            || SelectContextID.get(FoucsID).equals(
                                "shortcut_video_3d_mode_ud")) {
                            if (SelectMenuListener
                                .CheckSelectFrameItemDisable(SelectContextID
                                    .get(FoucsID))) {
                            	if(tvsetting.GetSigTransFormat()==0)
                            		FoucsID = 0;
                            	else
                            		FoucsID = 1;
                            }
                        } else if (SelectContextID.get(FoucsID).equals(
                        "shortcut_setup_sys_six_color_splitdemo")) {
                        if (SelectMenuListener
                            .CheckSelectFrameItemDisable(SelectContextID
                                .get(FoucsID))) {
                        	FoucsID = 0;
                        	}
                        } else if(SelectContextID.get(FoucsID).equals(
                        		"shortcut_setup_video_display_mode_personal")){
                        	if (SelectMenuListener
                                    .CheckSelectFrameItemDisable(SelectContextID
                                        .get(FoucsID))) {
                                	FoucsID = 4;  //4:3
                            }
                        } else if(SelectContextID.get(FoucsID).equals(
                        		"shortcut_setup_video_display_mode_panorama")){
                        	if (SelectMenuListener
                                    .CheckSelectFrameItemDisable(SelectContextID
                                        .get(FoucsID))) {
                                	FoucsID = 0;  //16:9
                            }
                        }
                        	
                    }
                } else {
                    ChangeToNextPage(false);
                }
                postEnterMessage(SelectContextID.get(FoucsID), true);
                this.invalidate();
                break;
//            case KeyEvent.KEYCODE_DPAD_LEFT:
//                if (SelectMenuListener != null) {
//                     if(IfSelect)
//                     {
//                     if(Menucontrol.InitState != null)
//                     Menucontrol.InitState.add(SelectContextID.get(CheckedID));
//                     }
//                     SelectMenuListener.SelectFrameToMenuHandle(CheckedID,"__LEFT__");
//                }
//                break;
//            case KeyEvent.KEYCODE_DPAD_RIGHT:
//                if (SelectMenuListener != null) {
//                     if(IfSelect)
//                     {
//                     if(Menucontrol.InitState != null)
//                     Menucontrol.InitState.add(SelectContextID.get(CheckedID));
//                     }
//                     SelectMenuListener.SelectFrameToMenuHandle(CheckedID,"__RIGHT__");
//                }
//                break;
            case KeyEvent.KEYCODE_BACK:
                if (SelectMenuListener != null) {
                    postEnterMessage(SelectContextID.get(FoucsID), false);
                    SelectMenuListener.SelectFrameToMenuHandle(CheckedID,
                        "__BACK__");
                }
                return true;

            case KeyEvent.KEYCODE_ENTER:
                postEnterMessage(SelectContextID.get(FoucsID), false);
                if (CheckedID != (cur_page_index * cc_page_show_item_count + FoucsID)) {
                    CheckedID = cur_page_index * cc_page_show_item_count
                        + FoucsID;
                    this.invalidate();
                }
                if (IfSelect) {
                    if (!TVSelectFlag)
                        if (Menucontrol.InitState != null) {
                            if (StartfocusID != -1) {
                                if (StartfocusID < Menucontrol.InitState.size())
                                    Menucontrol.InitState.remove(StartfocusID);
                            }
                            Menucontrol.InitState.add(SelectContextID
                                .get(CheckedID));
                            StartfocusID = Menucontrol.InitState.size() - 1;
                        }
                }
                if (SelectMenuListener != null) {
                    SelectMenuListener.SelectFrameToMenuHandle(CheckedID,
                        SelectContextID.get(CheckedID));
                }
                break;
                
            case KeyEvent.KEYCODE_MENU:
                if (SelectMenuListener != null) {
                    postEnterMessage(SelectContextID.get(FoucsID), false);
                }
                break;

            default:
                break;
        }
        if (SFother != null) {
            SFother.SetotherFrame(null);
            SFother.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);

    }

    private SelectFrame SFother = null;

    public void SetotherFrame(SelectFrame SF) {
        SFother = SF;
    }

    public SelectFrame GetotherFrame() {
        return SFother;
    }

    public void setSelectFrameListener(SkyworthMenuListener SFL) {
        SelectMenuListener = SFL;
    }

    private void setCurPageShowItemCount() {
        if (total_item_count < cc_page_show_item_count)
            cur_page_show_item_count = total_item_count;
        else {
            if (cur_page_index < total_item_count / cc_page_show_item_count)
                cur_page_show_item_count = cc_page_show_item_count;
            else
                cur_page_show_item_count = total_item_count - cur_page_index
                    * cc_page_show_item_count;
        }
    }

    private void ChangeToNextPage(boolean up_key) {
        int max_page_index;

        if (cc_page_show_item_count == 0)
            max_page_index = 0;
        else
            max_page_index = (total_item_count - 1) / cc_page_show_item_count;

        if (up_key) {
            if (max_page_index > 0) {
                if (cur_page_index > 0) {
                    cur_page_index--;
                } else {
                    cur_page_index = max_page_index;
                }
                setCurPageShowItemCount();
                FoucsID = cur_page_show_item_count - 1;
            } else {
                FoucsID = cur_page_show_item_count - 1;
            }
        } else {
            if (max_page_index > 0) {
                if (cur_page_index < max_page_index) {
                    cur_page_index++;
                } else {
                    cur_page_index = 0;
                }
                setCurPageShowItemCount();
                FoucsID = 0;
            } else {
                FoucsID = 0;
            }
        }
    }

    private Bitmap GetBGBitmapBG(int item_count) {
        if (item_count == 0)
            return null;
        if (item_count == 1) {
            return SearchDrawable.getBitmap("shortcut_bg_box_1");
        } else if (item_count == 2) {
            return SearchDrawable.getBitmap("shortcut_bg_box_2");
        } else if (item_count == 3) {
            return SearchDrawable.getBitmap("shortcut_bg_box_3");
        } else if (item_count == 4) {
            return SearchDrawable.getBitmap("shortcut_bg_box_4");
        } else if (item_count == 5) {
            return SearchDrawable.getBitmap("shortcut_bg_box_5");
        } else if (item_count == 6) {
            return SearchDrawable.getBitmap("shortcut_bg_box_6");
        } else if (item_count == 7) {
            return SearchDrawable.getBitmap("shortcut_bg_box_7");
        } else if (item_count == 8) {
            return SearchDrawable.getBitmap("shortcut_bg_box_8");
        } else if (item_count == 9) {
            return SearchDrawable.getBitmap("shortcut_bg_box_9");
        } else
            return SearchDrawable.getBitmap("shortcut_bg_box_10");
    }

    private void GetBitmapBG() {
        AllBtpBG = GetBGBitmapBG(SelectContext.size());
        if (BtpFoucs == null)
            BtpFoucs = SearchDrawable.getBitmap("shortcut_bg_sel");
        if (BtpCheck == null)
            BtpCheck = SearchDrawable.getBitmap("shortcut_bg_check");
    }

    private int filterIDGetFocus() {
        int focusID = -1;
        if (SelectContextID.get(0).contains("shortcut_common_sync_control_")) {
            if (Menucontrol.MediaType.equals("music")) {
                for (int i = 0; i < SelectContextID.size(); i++)
                    if (SelectContextID.get(i).contains("music"))
                        return i;

            } else if (Menucontrol.MediaType.equals("picture")) {
                for (int i = 0; i < SelectContextID.size(); i++)
                    if (SelectContextID.get(i).contains("picture"))
                        return i;
            } else if (Menucontrol.MediaType.equals("txt")) {
                for (int i = 0; i < SelectContextID.size(); i++)
                    if (SelectContextID.get(i).contains("txt"))
                        return i;
            }
        }
        return focusID;
    }

    private void postEnterMessage(String menuItemName, boolean isSend) {
        if (_entrance.equals(GlobalConst.ENTRANCE_TYPE_ANALOG_TV)
            || _entrance.equals(GlobalConst.ENTRANCE_TYPE_AV)
            || _entrance.equals(GlobalConst.ENTRANCE_TYPE_YUV)
            || _entrance.equals(GlobalConst.ENTRANCE_TYPE_VGA)
            || _entrance.equals(GlobalConst.ENTRANCE_TYPE_HDMI)) {
        if (menuItemName.equals("shortcut_common_source_tv")
            || menuItemName.equals("shortcut_common_source_av1")
            || menuItemName.equals("shortcut_common_source_yuv1")
            || menuItemName.equals("shortcut_common_source_hdmi1")
            || menuItemName.equals("shortcut_common_source_hdmi2")
            || menuItemName.equals("shortcut_common_source_hdmi3")
            || menuItemName.equals("shortcut_common_source_vga")) {
                if (handlerEnter.hasMessages(GlobalConst.SOURCE_ENTER))
            handlerEnter.removeMessages(GlobalConst.SOURCE_ENTER);
            if (isSend) {
                Message msg = handlerEnter
                    .obtainMessage(GlobalConst.SOURCE_ENTER);
                handlerEnter.sendMessageDelayed(msg, 2000);
            }
        }
    }
    }

    private Handler handlerEnter = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GlobalConst.SOURCE_ENTER:
                    onKeyDown(KeyEvent.KEYCODE_ENTER, new KeyEvent(
                        KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                    break;

                default:
                    break;
            }
        }
    };

}
