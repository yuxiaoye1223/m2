package com.skyworth.View;

import com.skyworth.Listener.SkyworthMenuListener;
import com.skyworth.SkyworthMenu.GlobalConst;
import com.skyworth.SkyworthMenu.Menucontrol;
import com.skyworth.SkyworthMenu.SearchDrawable;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;

public class ProgramEditDialog extends View {
    //private SearchDrawable SearchID;
    private Bitmap bgbmp = null;
    private Bitmap focusbmp = null;
    private String sourceitem = null;
    private String targetitem = null;
    private String exchangeitem = null;
    private int focusID = 0;
    private int sourceCh = 0;
    private int targetCh = 1;
    private int currentCh = 0;
    private SkyworthMenuListener ProgramEditDialogListener;

    public ProgramEditDialog(Context context, AttributeSet attrs, int chNumber) {
        super(context, attrs);

        currentCh = chNumber;
        sourceCh = currentCh;
        if (sourceCh < GlobalConst.TV_CHANNEL_TOTAL_PROGRAM_COUNT - 1)
            targetCh = sourceCh + 1;
        else
            targetCh = GlobalConst.TV_CHANNEL_MIN_NUMBER;
    }

    public void initDialogResource() {
        //SearchID = new SearchDrawable(this.getContext());
    	SearchDrawable.InitSearchDrawable(mContext);
        bgbmp = SearchDrawable.getBitmap("bg_info_content");
        focusbmp = SearchDrawable.getBitmap("list_item_sel");
        getString();
    }

    private void getString() {
        sourceitem = Menucontrol.getResXmlString("title_sourceitem");
        targetitem = Menucontrol.getResXmlString("title_targetitem");
        exchangeitem = Menucontrol.getResXmlString("title_exchangeitem");
    }

    public void setProgramEditDialogListener(SkyworthMenuListener SPL) {
        ProgramEditDialogListener = SPL;
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (bgbmp != null) {
            canvas.drawBitmap(bgbmp, this.getPaddingLeft(),
                this.getPaddingTop(), null);
        }
        if (focusbmp != null) {
            canvas.drawBitmap(focusbmp, this.getPaddingLeft() + 80,
                this.getPaddingTop() + focusID * 100 + 30, null);
        }
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(30);
        paint.setAntiAlias(true);

        String tmp_str;
        tmp_str = String.format("%3d", sourceCh);
        if (sourceitem != null) {
            canvas.drawText(sourceitem, getPaddingLeft() + 100,
                getPaddingTop() + 90, paint);
        }
        canvas.drawText(tmp_str, this.getPaddingLeft() + 460,
            this.getPaddingTop() + 90, paint);

        tmp_str = String.format("%3d", targetCh);
        if (targetitem != null) {
            canvas.drawText(targetitem, getPaddingLeft() + 100,
                getPaddingTop() + 190, paint);
        }
        canvas.drawText(tmp_str, this.getPaddingLeft() + 460,
            this.getPaddingTop() + 190, paint);

        if (exchangeitem != null) {
            canvas.drawText(exchangeitem, getPaddingLeft() + 100,
                getPaddingTop() + 290, paint);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_UP:
                if (focusID > 0) {
                    focusID--;
                } else {
                    focusID = 2;
                }
                this.invalidate();
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if (focusID < 2) {
                    focusID++;
                } else {
                    focusID = 0;
                }
                this.invalidate();
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                if (focusID == 0) {
                    if (sourceCh > GlobalConst.TV_CHANNEL_MIN_NUMBER) {
                        sourceCh--;
                    } else {
                        sourceCh = GlobalConst.TV_CHANNEL_TOTAL_PROGRAM_COUNT - 1;
                    }
                    this.invalidate();
                } else if (focusID == 1) {
                    if (targetCh > GlobalConst.TV_CHANNEL_MIN_NUMBER) {
                        targetCh--;
                    } else {
                        targetCh = GlobalConst.TV_CHANNEL_TOTAL_PROGRAM_COUNT - 1;
                    }
                    this.invalidate();
                }
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if (focusID == 0) {
                    if (sourceCh < (GlobalConst.TV_CHANNEL_TOTAL_PROGRAM_COUNT - 1)) {
                        sourceCh++;
                    } else {
                        sourceCh = GlobalConst.TV_CHANNEL_MIN_NUMBER;
                    }
                    this.invalidate();
                } else if (focusID == 1) {
                    if (targetCh < (GlobalConst.TV_CHANNEL_TOTAL_PROGRAM_COUNT - 1)) {
                        targetCh++;
                    } else {
                        targetCh = GlobalConst.TV_CHANNEL_MIN_NUMBER;
                    }
                    this.invalidate();
                }
                break;
            case KeyEvent.KEYCODE_ENTER:
                switch (focusID) {
                    case 0:
                        focusID = 1;
                        break;
                    case 1:
                        focusID = 2;
                        break;
                    case 2:
                        if (sourceCh != targetCh) {
                            ProgramEditDialogListener.ProgramEditToMenuHandle(
                                GlobalConst.EXCHANGE_CHANNEL, sourceCh,
                                targetCh);
                            if ((sourceCh == currentCh)
                                || (targetCh == currentCh)) {
                                ProgramEditDialogListener
                                    .ProgramEditToMenuHandle(
                                        GlobalConst.UPDATE_CHANNEL, currentCh,
                                        0);
                            }
                        }
                        focusID = 0;
                        break;
                    default:
                        break;
                }
                this.invalidate();
                break;
            case KeyEvent.KEYCODE_BACK:
                if (ProgramEditDialogListener != null) {
                    ProgramEditDialogListener.ProgramEditToMenuHandle(
                        GlobalConst.BACK_TO_MENU, 0, 0);
                }
                return true;

//            case KeyEvent.KEYCODE_MUTE:
            case 94:// IMAGE_MODE 图像模式
            case 95:// VOICE_MODE 声音模式
            case 96:// DISP_MODE 显示模式
            case 98:// 3D格式
            case 99:// 3D
            case 100:// SOURCE 通道选择
            case KeyEvent.KEYCODE_MENU:
                if (ProgramEditDialogListener != null) {
                    ProgramEditDialogListener.ProgramEditToMenuHandle(
                        GlobalConst.BACK_TO_MENU, 0, 0);
                }
                break;

            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

}
