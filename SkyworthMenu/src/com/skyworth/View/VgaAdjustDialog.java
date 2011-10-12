package com.skyworth.View;

import com.skyworth.Listener.SkyworthMenuListener;
import com.skyworth.SkyworthMenu.Menucontrol;
import com.skyworth.SkyworthMenu.SearchDrawable;
import com.skyworth.control.tvsetting;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;

public class VgaAdjustDialog extends View {

    private Bitmap focus, unfocus, flag, nameBG, background, bar_frame;
    //private SearchDrawable SearchID;
    private int focusId;
    private String title, autoAdjItem, autoAdjInfo;
    private VgaAdjustItem itemList[];

    private boolean bAdjust = false;
    private SkyworthMenuListener vgaAdjustDialogListener;

    public VgaAdjustDialog(Context context) {
        super(context);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
            627, 376);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        this.setLayoutParams(params);
        this.setFocusable(true);

        //SearchID = new SearchDrawable(this.getContext());
        flag = SearchDrawable.getBitmap("setup_eq_bar");
        focus = Bitmap.createBitmap(flag, 0, 0, 6, 19);
        unfocus = Bitmap.createBitmap(flag, 6, 0, 6, 19);
        nameBG = SearchDrawable.getBitmap("list_item_sel");
        background = SearchDrawable.getBitmap("vga_adjust_bg");
        bar_frame = SearchDrawable.getBitmap("setup_eq_frame");
        title = Menucontrol.getResXmlString("vga_title");
        autoAdjItem = Menucontrol.getResXmlString("vga_autoadjust");
        autoAdjInfo = Menucontrol.getResXmlString("vga_inadjust");

        itemList = new VgaAdjustItem[4];
        itemList[0] = new VgaAdjustItem(
            tvsetting.GetCurVgaData("shortcut_setup_sys_pc_set_hor_"),
            Menucontrol.getResXmlString("vga_horizental"));
        itemList[1] = new VgaAdjustItem(
            tvsetting.GetCurVgaData("shortcut_setup_sys_pc_set_ver_"),
            Menucontrol.getResXmlString("vga_vertical"));
        itemList[2] = new VgaAdjustItem(
            tvsetting.GetCurVgaData("shortcut_setup_sys_pc_set_freq_"),
            Menucontrol.getResXmlString("vga_frequency"));
        itemList[3] = new VgaAdjustItem(
            tvsetting.GetCurVgaData("shortcut_setup_sys_pc_set_pha_"),
            Menucontrol.getResXmlString("vga_phase"));

        tvsetting.setVGAMessageHandler();
    }

    private void refreshData() {
        itemList[0].Data = tvsetting
            .GetCurVgaData("shortcut_setup_sys_pc_set_hor_");
        itemList[1].Data = tvsetting
            .GetCurVgaData("shortcut_setup_sys_pc_set_ver_");
        itemList[2].Data = tvsetting
            .GetCurVgaData("shortcut_setup_sys_pc_set_freq_");
        itemList[3].Data = tvsetting
            .GetCurVgaData("shortcut_setup_sys_pc_set_pha_");
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(30);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setAntiAlias(true);
        Bitmap temp;

        canvas.drawBitmap(background, 0, 0, null);
        canvas.drawText(title, 304, 50, paint);
        paint.setTextSize(24);
        if (focusId == 0) {
            temp = Bitmap.createScaledBitmap(nameBG, 160, 40, true);
            canvas.drawBitmap(temp, 10, 90, null);
        }
        canvas.drawText(autoAdjItem, 90, 120, paint);
        if (bAdjust)
            canvas.drawText(autoAdjInfo, 400, 120, paint);
        else
            canvas.drawText(">>", 400, 120, paint);
        for (int i = 1; i < 5; i++) {
            canvas.drawText(itemList[i - 1].Name, 90, 120 + i * 54, paint);
            // canvas.drawText("0", 170, 120+i*54, paint);
            //Log.d("Haha","index is:" + i + "  data is:" + itemList[i - 1].Data + " focusId:" + focusId);
        	
            if (itemList[i - 1].Data != 0) {//(536.0 - 184.0)
                temp = Bitmap.createScaledBitmap(
                    focusId == i ? focus : unfocus, (int) ((347.0) * itemList[i - 1].Data / 100), 19, true);
                if(i==1){
                	if(focusId == 1)
                		canvas.drawBitmap(bar_frame, 183, 102 + 47, null);
                	canvas.drawBitmap(temp, 187, 102 + 51, null);
                }
                else if(i==2){
                	if(focusId == 2)
                		canvas.drawBitmap(bar_frame, 184, 102 + 102, null);
                	canvas.drawBitmap(temp, 188, 102 + 106, null);
                }
                else if(i==3){
                	if(focusId == 3)
                		canvas.drawBitmap(bar_frame, 183, 102 + 157, null);
                	canvas.drawBitmap(temp, 187, 102 + 161, null);
                }
                else if(i==4){
                	if(focusId == 4)
                		canvas.drawBitmap(bar_frame, 184, 102 + 212, null);
                	canvas.drawBitmap(temp, 188, 102 + 216, null);
                }
                else
					;
            }else if(itemList[i - 1].Data == 0){
        	if(focusId == 1 && i == 1)
        		canvas.drawBitmap(bar_frame, 184, 102 + 46, null);
        	else if(focusId == 2 && i == 2)
        		canvas.drawBitmap(bar_frame, 184, 102 + 101, null);
        	else if(focusId == 3 && i == 3)
        		canvas.drawBitmap(bar_frame, 184, 102 + 157, null);
        	else if(focusId == 4 && i == 4)
        		canvas.drawBitmap(bar_frame, 184, 102 + 212, null);
            }
            
            canvas.drawText(Integer.toString(itemList[i-1].Data), 570,120+i*54, paint);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (bAdjust)
            return true;

        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_UP:
                if (focusId-- <= 0)
                    focusId = 4;
                this.invalidate();
                break;

            case KeyEvent.KEYCODE_DPAD_DOWN:
                if (focusId++ >= 4)
                    focusId = 0;
                this.invalidate();
                break;

            case KeyEvent.KEYCODE_DPAD_LEFT:
                if (bAdjust == false) {
                    if (focusId != 0 && itemList[focusId - 1].Data-- <= 0)
                        itemList[focusId - 1].Data = 0;

                    if (vgaAdjustDialogListener != null) {
                        if (focusId > 0)
                            vgaAdjustDialogListener.VgaAdjustDialogHandle(
                                focusId, itemList[focusId - 1].Data);
                    }

                    this.invalidate();
                }
                break;

            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if (bAdjust == false) {
                    if (focusId != 0 && itemList[focusId - 1].Data++ >= 100)
                        itemList[focusId - 1].Data = 100;

                    if (vgaAdjustDialogListener != null) {
                        if (focusId > 0) {
                            vgaAdjustDialogListener.VgaAdjustDialogHandle(
                                focusId, itemList[focusId - 1].Data);
                        } else {
                            bAdjust = true;
                            vgaAdjustDialogListener.VgaAdjustDialogHandle(
                                focusId, 0);
                        }
                    }

                    this.invalidate();
                }
                break;

            case KeyEvent.KEYCODE_ENTER:
                if (vgaAdjustDialogListener != null) {
                    if ((focusId == 0) && (bAdjust == false)) {
                        bAdjust = true;
                        vgaAdjustDialogListener.VgaAdjustDialogHandle(focusId,
                            0);
                        this.invalidate();
                    }
                }
                break;

            case KeyEvent.KEYCODE_BACK:
                if (vgaAdjustDialogListener != null) {
                    vgaAdjustDialogListener.VgaAdjustDialogToMenuHandle();
                }
                return true;

//            case KeyEvent.KEYCODE_MUTE:
            case 94:// IMAGE_MODE
            case 95:// VOICE_MODE
            case 96:// DISP_MODE
            case 98:// 3D FORMAT
            case 99:// 3D
            case 100:// SOURCE INPUT
            case KeyEvent.KEYCODE_MENU:
            case 97:// CHANNEL INFO DISPLAY
                if (vgaAdjustDialogListener != null) {
                    vgaAdjustDialogListener.VgaAdjustDialogToMenuHandle();
                }
                break;

            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void SetAdjustFlag(boolean flag) {
        bAdjust = flag;
        if (bAdjust == false)
            refreshData();
        // this.invalidate();
    }

    class VgaAdjustItem {
        int Data;
        String Name;

        VgaAdjustItem(int d, String n) {
            this.Data = d;
            this.Name = n;
        }
    }

    public void setVgaAdjustDialogListener(SkyworthMenuListener SPL) {
        vgaAdjustDialogListener = SPL;
    }

}
