package com.skyworth.View;

import com.skyworth.Listener.SkyworthMenuListener;
import com.skyworth.SkyworthMenu.GlobalConst;
import com.skyworth.SkyworthMenu.Menucontrol;
import com.skyworth.SkyworthMenu.SearchDrawable;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;

public class progressBarView extends View {

    private Bitmap bg;
    private Bitmap bar;
    private Bitmap focus = null, newfocus = null;
    private Bitmap tail = null;
    private int progress = 0;
    private SkyworthMenuListener ProgressListener = null;
    private String MenuItemName = "";
    //private SearchDrawable SearchID;
    private Bitmap BgBtp = null;
    private Bitmap typebmp = null;
    private int leftOffset = 480;
    private int topOffset = 55;
    private int MType = MenuGroup.noplayer;
    private Paint pa = new Paint();
    private String ItemValue = null;

    public progressBarView(Context context,AttributeSet attrs,String MenuItemName,int MenuType) {
        super(context, attrs);
        // this.setFocusable(true);
        this.MenuItemName = MenuItemName;
        init();
        MType = MenuType;
        setBgBtp(MenuType);
        this.setFocusable(true);

        pa.setColor(Color.WHITE);
        pa.setTextSize(30);
        pa.setAntiAlias(true);
    }

    public String GetCurrentMenuItemName(){
    	return MenuItemName;
    }
    
    public void init() {
        //SearchID = new SearchDrawable(this.getContext());
    	SearchDrawable.InitSearchDrawable(mContext);
        bg = SearchDrawable.getBitmap("bar_sc_slide_bg");
        bar = SearchDrawable.getBitmap("bar_sc_slide_element");
        BgBtp = SearchDrawable.getBitmap("shortcut_bg_bar_info");
        typebmp = SearchDrawable.getBitmap(MenuItemName + "unsel");
        ItemValue = Menucontrol.getShortCutValue(MenuItemName);
        focus = Bitmap.createBitmap(bar, 0, 0, 10, 41);
        tail = Bitmap.createBitmap(bar, 10, 0, 10, 41);
    }

    public void setBgBtp(int Type) {
        int BGhight = 0;
        String BGName;
        if (Type == MenuGroup.noplayer) {
            BGhight = 152;
            BGName = "shortcut_bg_bar";
        } else {
            BGhight = 238;
            BGName = "shortcut_bg_bar_info";
        }
        BgBtp = SearchDrawable.getBitmap(BGName);
    }

    public void setProgress(int num) {
        progress = num;
        if (MenuItemName.equals("shortcut_video_3d_dof_")){
        	if(progress>16 || progress<-16)
        		progress = 0;//default value
        }
    }

    public int getProgress() {
        return progress;
    }

    public void setProgressBarListener(SkyworthMenuListener SML) {
        ProgressListener = SML;
    }

    private void showdrawBitmap(Canvas canvas, Bitmap bitmap1, float left,
        float top, Paint paint) {
        if (GlobalConst.OSD_DISPLAY_HALF_FLAG == 1) {
            Matrix matrix = new Matrix();
            matrix.postScale(0.5f, 1);
            Bitmap dstbmp = Bitmap.createBitmap(bitmap1, 0, 0,
                bitmap1.getWidth(), bitmap1.getHeight(), matrix, true);
            canvas.drawBitmap(dstbmp, left, top, paint);
            canvas.drawBitmap(dstbmp, left + 960, top, paint);
        }
        if (GlobalConst.OSD_DISPLAY_HALF_FLAG == 2) {
            int osd_half_offset_y = 152 / 2;
            Matrix matrix = new Matrix();
            matrix.postScale(1, 0.5f);
            Bitmap dstbmp = Bitmap.createBitmap(bitmap1, 0, 0,
                bitmap1.getWidth(), bitmap1.getHeight(), matrix, true);
            canvas.drawBitmap(dstbmp, left, top / 2 + osd_half_offset_y, paint);
            canvas.drawBitmap(dstbmp, left, top / 2 + osd_half_offset_y + 540,
                paint);
        } else
            canvas.drawBitmap(bitmap1, left, top, paint);
    }

    private void showdrawText(Canvas canvas, String arg0, float arg1, float arg2) {
        int osd_half_offset = 1;
        if (GlobalConst.OSD_DISPLAY_HALF_FLAG == 1) {
            osd_half_offset = 2;
            pa.setTextScaleX(0.5f);
        }
        if (GlobalConst.OSD_DISPLAY_HALF_FLAG == 2) {
            int osd_half_offset_y = 152 / 2;
            pa.setTextSize(15);
            pa.setTextScaleX(2f);
            canvas.drawText(arg0, arg1 / osd_half_offset, arg2 / 2
                + osd_half_offset_y, pa);
            canvas.drawText(arg0, arg1 / osd_half_offset, arg2 / 2
                + osd_half_offset_y + 540, pa);
            pa.setTextScaleX(1f);
            pa.setTextSize(30);
        } else if (arg0 != null)
            canvas.drawText(arg0, arg1 / osd_half_offset, arg2, pa);
        if (GlobalConst.OSD_DISPLAY_HALF_FLAG == 1) {
            canvas.drawText(arg0, arg1 / osd_half_offset + 960, arg2, pa);
            pa.setTextScaleX(1f);
        }
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if ((focus == null) || (tail == null) || (bg == null))
            return;
        int osd_half_offset = 1;

        if (GlobalConst.OSD_DISPLAY_HALF_FLAG == 1) {
            osd_half_offset = 2;
        }
        if (BgBtp != null)
            showdrawBitmap(canvas, BgBtp, this.getPaddingLeft()
                / osd_half_offset, this.getPaddingTop(), null);
        if (typebmp != null)
            showdrawBitmap(canvas, typebmp, (this.getPaddingLeft() + 330)
                / osd_half_offset, this.getPaddingTop(), null);
        if (ItemValue != null)
            showdrawText(canvas, ItemValue, (this.getPaddingLeft() + 330 + 60),
                this.getPaddingTop() + 120);

        showdrawBitmap(canvas, bg, (getPaddingLeft() + leftOffset)
            / osd_half_offset, getPaddingTop() + topOffset, null);
        if (newfocus != null)
            newfocus.recycle();
        if (progress > GlobalConst.MIN_VALUE_MENU_SHOW || (MenuItemName.equals("shortcut_video_3d_dof_")
        		&& progress > -16)) {
            if (GlobalConst.OSD_DISPLAY_HALF_FLAG != 2) {
                if (MenuItemName.equals("shortcut_program_vol_correct_"))
                    newfocus = Bitmap.createScaledBitmap(focus, progress * 5
                        * 935/100 / osd_half_offset, 41, true);
                else if (MenuItemName.equals("shortcut_video_3d_dof_"))
                    newfocus = Bitmap.createScaledBitmap(focus, (progress+16) * 100/32
                            * 935/100 / osd_half_offset, 41, true);
                else
                    newfocus = Bitmap.createScaledBitmap(focus, progress * 935/100
                    		/ osd_half_offset, 41, true);
                canvas.drawBitmap(newfocus,
                    (getPaddingLeft() + leftOffset + 10) / osd_half_offset,
                    getPaddingTop() + topOffset, null);
                if (GlobalConst.OSD_DISPLAY_HALF_FLAG == 1)
                    canvas.drawBitmap(newfocus,
                        (getPaddingLeft() + leftOffset + 10) / osd_half_offset
                            + 960, getPaddingTop() + topOffset, null);
            } else {
                if (MenuItemName.equals("shortcut_program_vol_correct_"))
                    newfocus = Bitmap.createScaledBitmap(focus, progress * 5
                        * 935/100 / osd_half_offset, 41 / 2, true);
                else  if (MenuItemName.equals("shortcut_video_3d_dof_"))
                    newfocus = Bitmap.createScaledBitmap(focus, (progress+16) * 100/32
                            * 935/100 / osd_half_offset, 41 / 2, true);
                else
                    newfocus = Bitmap.createScaledBitmap(focus, progress * 935/100
                    		/ osd_half_offset, 41 / 2, true);
                canvas.drawBitmap(newfocus,
                    (getPaddingLeft() + leftOffset + 10) / osd_half_offset,
                    (getPaddingTop() + topOffset) / 2 + 152 / 2 + 1, null);
                canvas
                    .drawBitmap(newfocus, (getPaddingLeft() + leftOffset + 10)
                        / osd_half_offset, (getPaddingTop() + topOffset) / 2
                        + 152 / 2 + 1 + 540, null);
            }
        }
        	
        if (MenuItemName.equals("shortcut_program_vol_correct_"))
            showdrawBitmap(canvas, tail, (getPaddingLeft() + progress * 5 * 935/100
            		+ leftOffset+2)
                / osd_half_offset, getPaddingTop() + topOffset, null);
        else if(MenuItemName.equals("shortcut_video_3d_dof_"))
            showdrawBitmap(canvas, tail, (getPaddingLeft() + (progress+16) * 100/32f * 935/100
            		+ leftOffset+2)
                    / osd_half_offset, getPaddingTop() + topOffset, null);
        else
            showdrawBitmap(canvas, tail,
                (getPaddingLeft() + progress * 935/100 + leftOffset + 2)
                    / osd_half_offset, getPaddingTop() + topOffset, null);

        showdrawText(canvas, String.valueOf(getProgressDisplayValue()),
            (getPaddingLeft() + 960 + leftOffset), getPaddingTop() + 30
                + topOffset);

    }

    private int getProgressDisplayValue() {
        int displayVal;
        if (MenuItemName.equals("shortcut_setup_audio_balance_")) {
            // -50 <----> +50
            displayVal = progress - 50;
        } else if (MenuItemName.equals("shortcut_program_vol_correct_")) {
            // PROGRAM_VOL_CORRECT_MAXVALUE is 20
            displayVal = progress - 10;
        } else {
            displayVal = progress;
        }
        return displayVal;
    }

    // @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        super.onKeyDown(keyCode, event);
        switch (keyCode) {
            case 0xCF:
            case 0xCE:
            case 0xCD:
            case 0xCC:
            case 0xCB:
            case 0xCA:
            case 0xC9:
            case 0xC8:
            case 0xC7:
            case 0xC6:
            case 0xC5:
            case 0xC4:
            case 0xC3:
            case 0xC2:
            case 0xC1:
                KeyOp(KeyEvent.KEYCODE_DPAD_LEFT);
                break;
            case 0xFF:
            case 0xFE:
            case 0xFD:
            case 0xFC:
            case 0xFB:
            case 0xFA:
            case 0xF9:
            case 0xF8:
            case 0xF7:
            case 0xF6:
            case 0xF5:
            case 0xF4:
            case 0xF3:
            case 0xF2:
            case 0xF1:
                KeyOp(KeyEvent.KEYCODE_DPAD_RIGHT);
                break;

            case KeyEvent.KEYCODE_DPAD_UP:
            case KeyEvent.KEYCODE_DPAD_DOWN:
                break;

            case 115: // KeyEvent.KEYCODE_VOLUME_DOWN
                if (!MenuItemName.equals("shortcut_common_vol_"))
                    break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                KeyOp(KeyEvent.KEYCODE_DPAD_LEFT);
                break;
            case 114: // KeyEvent.KEYCODE_VOLUME_UP
                if (!MenuItemName.equals("shortcut_common_vol_"))
                    break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                KeyOp(KeyEvent.KEYCODE_DPAD_RIGHT);
                break;
            case KeyEvent.KEYCODE_BACK:
                onKeyDown(KeyEvent.KEYCODE_ENTER, event);
                return true;
            case KeyEvent.KEYCODE_ENTER:
                if (ProgressListener != null)
                    ProgressListener.ProgressBarToMenuHandle(progress, false,
                        MenuItemName);
                break;
            case KeyEvent.KEYCODE_MENU:
                if (ProgressListener != null)
                    ProgressListener.ProgressBarToMenuHandle(progress, false,
                        "__Nothing__");
                break;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void KeyOp(int keyCode) {

        int osd_half_offset = 1;
        int bar_offset_y = 0;

        if (GlobalConst.OSD_DISPLAY_HALF_FLAG == 1) {
            osd_half_offset = 2;
        }
        if (GlobalConst.OSD_DISPLAY_HALF_FLAG == 2) {
            bar_offset_y = 540 - 41 + 152 - 55;
        }
        if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
            if (progress > GlobalConst.MIN_VALUE_MENU_SHOW) {
                progress--;
                if (MType == MenuGroup.noplayer) {

                    // this.invalidate(leftOffset/ osd_half_offset, topOffset,
                    // (leftOffset + 1010)/ osd_half_offset,
                    // topOffset + 41);
                    this.invalidate(leftOffset / osd_half_offset, topOffset,
                        1920, topOffset + 41 + bar_offset_y);
                } else {

                    // this.invalidate(leftOffset / osd_half_offset, topOffset,
                    // (leftOffset + 1010)/ osd_half_offset,
                    // topOffset + 41);
                    this.invalidate(leftOffset / osd_half_offset, topOffset,
                        1920, topOffset + 41 + bar_offset_y);
                }
                if (ProgressListener != null)
                    ProgressListener.ProgressBarToMenuHandle(progress, true,
                        MenuItemName);
            }
            else if (MenuItemName.equals("shortcut_video_3d_dof_")) {
                if (progress > -16) {
                	// -16  16                                               
                    progress--;
                    this.invalidate(leftOffset / osd_half_offset, topOffset,
                        1920, topOffset + 41 + bar_offset_y);
                    if (ProgressListener != null)
                        ProgressListener.ProgressBarToMenuHandle(progress, true,
                            MenuItemName);
                }
            }
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
            if (progress < GlobalConst.MAX_VALUE_MENU_SHOW) {
                if (MenuItemName.equals("shortcut_program_vol_correct_")) {
                    if (progress < GlobalConst.PROGRAM_VOL_CORRECT_MAXVALUE) 
                    	// range is 20                                               
                        progress++;
                }
                else if (MenuItemName.equals("shortcut_video_3d_dof_")) {
                    if (progress < 16) 
                    	// -16  16                                               
                        progress++;
                }
                else {
                    progress++;
                }
                if (MType == MenuGroup.noplayer) {

                    // this.invalidate(leftOffset / osd_half_offset, topOffset,
                    // (leftOffset + 1010)/ osd_half_offset,
                    // topOffset + 41);
                    this.invalidate(leftOffset / osd_half_offset, topOffset,
                        1920, topOffset + 41 + bar_offset_y);
                } else {

                    // this.invalidate(leftOffset / osd_half_offset, topOffset,
                    // (leftOffset + 1010)/ osd_half_offset,
                    // topOffset + 41);
                    this.invalidate(leftOffset / osd_half_offset, topOffset,
                        1920, topOffset + 41 + bar_offset_y);
                }
                if (ProgressListener != null)
                    ProgressListener.ProgressBarToMenuHandle(progress, true,
                        MenuItemName);
            }
        }

    }
}
