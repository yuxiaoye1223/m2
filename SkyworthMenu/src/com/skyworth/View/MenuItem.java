package com.skyworth.View;

import com.skyworth.SkyworthMenu.Menucontrol;
import com.skyworth.SkyworthMenu.SearchDrawable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Matrix;
import com.skyworth.SkyworthMenu.GlobalConst;

public class MenuItem {

    int myleft, mytop, myright, mybutton;
    private Bitmap unfocus = null;
    private Bitmap focus = null;
    private Bitmap disable = null;
    private Bitmap fixed = null;
    private MenuStatus myStatus = MenuStatus.unfocus;
    private boolean myVisible = false;
    private boolean myFixedID = false;
    private String MenuID = null;
    //private SearchDrawable SearchID = null;
    private Context mycontext;
    private String ItemValue = null;

    private Paint disablePaint = new Paint();

    public static enum MenuStatus {
        focus, unfocus, disable;
    }

    MenuItem(Context context, String id, Rect rect) {
        myleft = rect.left;
        mytop = rect.top;
        myright = rect.right;
        mybutton = rect.bottom;
        MenuID = id;
        mycontext = context;
        //SearchID = new SearchDrawable(mycontext);
        SearchDrawable.InitSearchDrawable(mycontext);
        ItemValue = Menucontrol.getShortCutValue(MenuID);
        disablePaint.setColor(Color.GRAY);
        disablePaint.setTextSize(30);
        disablePaint.setAntiAlias(true);
        disablePaint.setTextAlign(Paint.Align.CENTER);
    }

    MenuItem(Context context, String id, Rect rect, boolean fixid) {
        myleft = rect.left;
        mytop = rect.top;
        myright = rect.right;
        mybutton = rect.bottom;
        MenuID = id;
        mycontext = context;
        //SearchID = new SearchDrawable(mycontext);
        SearchDrawable.InitSearchDrawable(mycontext);
        myFixedID = fixid;
        ItemValue = Menucontrol.getShortCutValue(MenuID);
        disablePaint.setColor(Color.GRAY);
        disablePaint.setTextSize(30);
        disablePaint.setAntiAlias(true);
        disablePaint.setTextAlign(Paint.Align.CENTER);
    }
    
    public void CheckMenuName(String ShowState) {
        if (ItemValue != null && ShowState != null) {
            if (MenuID.equals("shortcut_common_prev_")) {
                if (ShowState.equals("music"))
                    ItemValue = Menucontrol.getResXmlString("PREMUSIC");
                else if (ShowState.equals("picture"))
                    ItemValue = Menucontrol.getResXmlString("PREPICTRUE");
                else if (ShowState.equals("txt"))
                    ItemValue = Menucontrol.getResXmlString("PRETEXT");
            } else if (MenuID.equals("shortcut_common_next_")) {
                if (ShowState.equals("music"))
                    ItemValue = Menucontrol.getResXmlString("NEXTMUSIC");
                else if (ShowState.equals("picture"))
                    ItemValue = Menucontrol.getResXmlString("NEXTPICTURE");
                else if (ShowState.equals("txt"))
                    ItemValue = Menucontrol.getResXmlString("NEXTTEXT");
            }
        }
    }

    public void setStatus(MenuStatus status) {
        myStatus = status;
    }

    public MenuStatus getStatus() {
        return myStatus;
    }

    public void setVisible(boolean visible) {
        myVisible = visible;
    }

    public String getItemName() {
        return MenuID;
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
            canvas.drawBitmap(dstbmp, left + 960, top, paint);
        } else if (GlobalConst.OSD_DISPLAY_HALF_FLAG == 2) {
            int osd_half_offset_y = 152 / 2;
            Matrix matrix = new Matrix();
            matrix.postScale(1, 0.5f);
            Bitmap dstbmp = Bitmap.createBitmap(bitmap1, 0, 0,
                bitmap1.getWidth(), bitmap1.getHeight(), matrix, true);
            canvas.drawBitmap(dstbmp, left, top + osd_half_offset_y, paint);
            canvas.drawBitmap(dstbmp, left, top + osd_half_offset_y + 540,
                paint);
        } else
            canvas.drawBitmap(bitmap1, left, top, paint);
    }

    private void showdrawText(Canvas canvas, String arg0, float arg1,
        float arg2, Paint arg3) {
        int osd_half_offset = 1;

        if (GlobalConst.OSD_DISPLAY_HALF_FLAG == 1) {
            // osd_half_offset = 2;
            arg3.setTextScaleX(0.5f);
        }
        if (GlobalConst.OSD_DISPLAY_HALF_FLAG == 2) {
            arg3.setTextSize(15);
            arg3.setTextScaleX(2f);
        }
        if (GlobalConst.OSD_DISPLAY_HALF_FLAG == 2) {
            int osd_half_offset_y = 152 / 2;
            if (arg0 != null) {
                canvas.drawText(arg0, arg1 / osd_half_offset, arg2
                    + osd_half_offset_y, arg3);
                canvas.drawText(arg0, arg1 / osd_half_offset, arg2
                    + osd_half_offset_y + 540, arg3);
            }
        } else {
            if (arg0 != null)
                canvas.drawText(arg0, arg1 / osd_half_offset, arg2, arg3);
        }
        if (GlobalConst.OSD_DISPLAY_HALF_FLAG == 1) {
            canvas.drawText(arg0, arg1 / osd_half_offset + 960, arg2, arg3);
            arg3.setTextScaleX(1f);
        }
        if (GlobalConst.OSD_DISPLAY_HALF_FLAG == 2) {
            arg3.setTextScaleX(1f);
            arg3.setTextSize(30);
        }
    }

    public void myDraw(Canvas canvas, Paint paint ,String ShowState) {
        int item_offset = 92;
        int item_offset_y = 125;
        if (GlobalConst.OSD_DISPLAY_HALF_FLAG == 1)
            item_offset = 92 / 2;
        if (GlobalConst.OSD_DISPLAY_HALF_FLAG == 2)
            item_offset_y = 125 / 2;
        if (myVisible) {
            if (myFixedID) {
                if (fixed == null)
                    fixed = SearchDrawable.getBitmap(MenuID);
                showdrawBitmap(canvas, fixed, myleft, mytop, null);
            } else {
                if (myStatus == MenuStatus.focus) {
                    if (focus == null)
                        focus = SearchDrawable.getBitmap(MenuID + "sel");

                    showdrawBitmap(canvas, focus, myleft, mytop, null);
                    CheckMenuName(ShowState);
                    if (ItemValue != null)
                        showdrawText(canvas, ItemValue, myleft + item_offset,
                            mytop + item_offset_y, paint);

                } else if (myStatus == MenuStatus.unfocus) {
                    if (unfocus == null)
                        unfocus = SearchDrawable.getBitmap(MenuID + "unsel");

                    showdrawBitmap(canvas, unfocus, myleft, mytop, null);
                    CheckMenuName(ShowState);
                    if (ItemValue != null)
                        showdrawText(canvas, ItemValue, myleft + item_offset,
                            mytop + item_offset_y, paint);

                } else if (myStatus == MenuStatus.disable) {
                    if (disable == null)
                        disable = SearchDrawable.getBitmap(MenuID + "disable");

                    showdrawBitmap(canvas, disable, myleft, mytop, null);
                    CheckMenuName(ShowState);
                    if (ItemValue != null)
                        showdrawText(canvas, ItemValue, myleft + item_offset,
                            mytop + item_offset_y, disablePaint);

                }
            }
        }
    }

    public Rect getRefreshRect() {
        return new Rect(myleft, mytop, myright, mybutton);
    }

    public void recycleBitmap() {
        if (focus != null) {
            focus.recycle();
            focus = null;
        }
        if (unfocus != null) {
            unfocus.recycle();
            unfocus = null;
        }
        if (disable != null) {
            disable.recycle();
            disable = null;
        }
    }

    public void setItemID(String ID) {
        if (ID != null) {
            MenuID = ID;
            ItemValue = Menucontrol.getShortCutValue(MenuID);
        }
    }

}
