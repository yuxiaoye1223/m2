package com.skyworth.View;

import java.util.ArrayList;
import java.util.List;

import com.skyworth.Listener.SkyworthMenuListener;
import com.skyworth.SkyworthMenu.GlobalConst;
import com.skyworth.SkyworthMenu.SearchDrawable;
import com.skyworth.XmlParse.StringItem;
import com.skyworth.control.tvsetting;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

public class Balancer extends View {
    private Bitmap bgBtp, unfocus, focus, flag, nameBG, bar_frame;
    private SkyworthMenuListener mylistener;
    private List<Line> fiveLine = new ArrayList<Line>();
    private List<BalancerName> myBalancerSet = new ArrayList<BalancerName>();
    private int soundmode = 0;

    public Balancer(Context context, AttributeSet attrs) {
        super(context, attrs);
        SearchDrawable.InitSearchDrawable(context);
        addLine(fiveLine);
        String str_lan = this.getResources().getConfiguration().locale
            .getLanguage();
        
        if (str_lan.equals("en"))
            bgBtp = SearchDrawable.getBitmap("setup_eq_bg_en");
        else
            bgBtp = SearchDrawable.getBitmap("setup_eq_bg");
        bar_frame = SearchDrawable.getBitmap("setup_eq_frame");
        flag = SearchDrawable.getBitmap("setup_eq_bar");
        focus = Bitmap.createBitmap(flag, 0, 0, 6, 19);
        unfocus = Bitmap.createBitmap(flag, 6, 0, 6, 19);
        nameBG = SearchDrawable.getBitmap("setup_eq_sel");

        this.setFocusable(true);
    }

    public void addLine(List<Line> myline) {
        myline.add(new Line(184, 95 + 3, 0, 10, false));
        myline.add(new Line(184, 150 + 3, 0, 10, false));
        myline.add(new Line(184, 205 + 3, 0, 10, false));
        myline.add(new Line(184, 260 + 3, 0, 10, false));
        myline.add(new Line(184, 315 + 3, 0, 10, false));
    }

    public void initBalancerRescource(List<StringItem> stringItems, int eqMode) {
        for (StringItem si : stringItems) {
            if (si.name.equals("SM_STD"))
                myBalancerSet.add(new BalancerName(si.value,
                    BalancerName.BalancerName_unfocus));
            else if (si.name.equals("SM_MUSIC"))
                myBalancerSet.add(new BalancerName(si.value,
                    BalancerName.BalancerName_unfocus));
            else if (si.name.equals("SM_NEWS"))
                myBalancerSet.add(new BalancerName(si.value,
                    BalancerName.BalancerName_unfocus));
            else if (si.name.equals("SM_THEATER"))
                myBalancerSet.add(new BalancerName(si.value,
                    BalancerName.BalancerName_unfocus));
            else if (si.name.equals("SM_USER"))
                myBalancerSet.add(new BalancerName(si.value,
                    BalancerName.BalancerName_unfocus));
        }

        InitSetData(fiveLine);

        soundmode = eqMode;
        myBalancerSet.get(soundmode).setfocusmark(
            BalancerName.BalancerName_focus);
    }

    public void InitSetData(List<Line> myline) {
        int[] dataBuf = {
        0, 0, 0, 0, 0, 0
        };

        tvsetting.GetCurEQGain(dataBuf);
        myline.get(0).setData(
            dataBuf[0 + GlobalConst.LOAD_AUDIO_EQGAIN_POS]
                + GlobalConst.AUDIO_EQGAIN_RANGE / 2);
        myline.get(1).setData(
            dataBuf[1 + GlobalConst.LOAD_AUDIO_EQGAIN_POS]
                + GlobalConst.AUDIO_EQGAIN_RANGE / 2);
        myline.get(2).setData(
            dataBuf[2 + GlobalConst.LOAD_AUDIO_EQGAIN_POS]
                + GlobalConst.AUDIO_EQGAIN_RANGE / 2);
        myline.get(3).setData(
            dataBuf[3 + GlobalConst.LOAD_AUDIO_EQGAIN_POS]
                + GlobalConst.AUDIO_EQGAIN_RANGE / 2);
        myline.get(4).setData(
            dataBuf[4 + GlobalConst.LOAD_AUDIO_EQGAIN_POS]
                + GlobalConst.AUDIO_EQGAIN_RANGE / 2);
    }

    public void drawmyline(Canvas canvas, List<Line> myline, Paint paint) {
        for (int ii = 0; ii < myline.size(); ii++) {
            Bitmap temp;
            if (myline.get(ii).mydata != 0) {
                if (myline.get(ii).Getfocusmark())
                    temp = Bitmap
                        .createScaledBitmap(
                            focus,
                            (int) ((536.0 - 184.0) * myline.get(ii).GetData() / GlobalConst.AUDIO_EQGAIN_RANGE),
                            19, true);
                else
                    temp = Bitmap
                        .createScaledBitmap(
                            unfocus,
                            (int) ((536.0 - 184.0) * myline.get(ii).GetData() / GlobalConst.AUDIO_EQGAIN_RANGE),
                            19, true);
                canvas.drawBitmap(temp, myline.get(ii).myleft,
                    myline.get(ii).mytop, null);
                temp.recycle();
            }
            canvas.drawText(Integer.toString(myline.get(ii).mydata * 5), 580, 115 + ii * 55, paint);
        }
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bgBtp, 0, 0, null);
        if (!(myBalancerSet.get(soundmode).Getfocusmark() == BalancerName.BalancerName_focus)) {
            canvas.drawBitmap(bar_frame, 184,
                (94 + getCurFocusItem() % 5 * 55), null);
        }
        if (myBalancerSet.get(soundmode).Getfocusmark() == BalancerName.BalancerName_focus
            || myBalancerSet.get(soundmode).Getfocusmark() == BalancerName.BalancerName_press) {
            Paint paint = new Paint();
            paint.setColor(Color.WHITE);
            paint.setTextSize(24);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setAntiAlias(true);
            if (myBalancerSet.get(soundmode).Getfocusmark() == BalancerName.BalancerName_focus) {
                canvas.drawBitmap(nameBG, 270, 0, null);
            }
            canvas.drawText(myBalancerSet.get(soundmode).GetData(), 397, 52,
                paint);
            drawmyline(canvas, fiveLine, paint);
        }
    }

    private int getCurFocusItem() {
        for (int ii = 0; ii < fiveLine.size(); ii++) {
            if (fiveLine.get(ii).Getfocusmark()) {
                // Log.d("Haha","Focus is:" + ii);
                return ii;
            }
        }
        return 0;
    }

    public void invalidateMyLineUp(int i, List<Line> myline) {
        for (int ii = 0; ii < myline.size(); ii++) {
            if (myline.get(ii).Getfocusmark()) {
                if (ii != 0) {
                    myline.get(ii - 1).setfocusmark(true);
                    myline.get(ii).setfocusmark(false);
                    this.invalidate();
                } else {
                    myline.get(ii).setfocusmark(false);
                    myBalancerSet.get(i).setfocusmark(
                        BalancerName.BalancerName_focus);
                    this.invalidate();
                }
                break;
            } else if (ii == (myline.size() - 1)) {
                myline.get(ii).setfocusmark(true);
                myBalancerSet.get(i).setfocusmark(
                    BalancerName.BalancerName_press);
                this.invalidate();
                break;
            }
        }
    }

    public void invalidateMyLineDown(int i, List<Line> myline) {
        for (int ii = 0; ii < myline.size(); ii++) {
            int iii;
            for (iii = 0; iii < myline.size(); iii++) {
                if (myline.get(iii).Getfocusmark())
                    break;
            }
            if (iii == myline.size()) {
                myline.get(0).setfocusmark(true);
                myBalancerSet.get(i).setfocusmark(
                    BalancerName.BalancerName_press);
                this.invalidate();
                break;
            }
            if (myline.get(ii).Getfocusmark()) {
                if (ii != myline.size() - 1) {
                    myline.get(ii + 1).setfocusmark(true);
                    myline.get(ii).setfocusmark(false);
                    this.invalidate();
                } else {
                    myline.get(ii).setfocusmark(false);
                    myBalancerSet.get(i).setfocusmark(
                        BalancerName.BalancerName_focus);
                    this.invalidate();
                }
                break;
            }
        }
    }

    public void invalidateMyLineLeft(List<Line> myline) {
        for (int ii = 0; ii < myline.size(); ii++) {
            if (myline.get(ii).Getfocusmark()) {
                if (0 < myline.get(ii).GetData()) {
                    myline.get(ii).setData(myline.get(ii).GetData() - 1);
                } else {
                    myline.get(ii).setData(0);
                }
                if (mylistener != null)
                    mylistener.BalancerKeyListener(true, ii, myline.get(ii)
                        .GetData());
                this.invalidate();
                break;
            }
        }
    }

    public void invalidateMyLineRight(List<Line> myline) {
        for (int ii = 0; ii < myline.size(); ii++) {
            if (myline.get(ii).Getfocusmark()) {
                if (GlobalConst.AUDIO_EQGAIN_RANGE > myline.get(ii).GetData()) {
                    myline.get(ii).setData(myline.get(ii).GetData() + 1);
                } else {
                    myline.get(ii).setData(GlobalConst.AUDIO_EQGAIN_RANGE);
                }
                if (mylistener != null)
                    mylistener.BalancerKeyListener(true, ii, myline.get(ii)
                        .GetData());
                this.invalidate();
                break;
            }
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_UP:
                invalidateMyLineUp(soundmode, fiveLine);
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                invalidateMyLineDown(soundmode, fiveLine);
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                if (myBalancerSet.get(soundmode).Getfocusmark() == BalancerName.BalancerName_focus) {
                    myBalancerSet.get(soundmode).setfocusmark(
                        BalancerName.BalancerName_unfocus);
                    if (soundmode == 0) {
                        soundmode = myBalancerSet.size() - 1;
                    } else {
                        soundmode = soundmode - 1;
                    }
                    myBalancerSet.get(soundmode).setfocusmark(
                        BalancerName.BalancerName_focus);
                    if (mylistener != null)
                        mylistener.BalancerKeyListener(soundmode);
                    InitSetData(fiveLine);
                    this.invalidate();
                } else if (myBalancerSet.get(soundmode).Getfocusmark() == BalancerName.BalancerName_press) {
                    Log.d("Balancer",
                        "........ KEYCODE_DPAD_LEFT BalancerName_press .........\n");
                    if (soundmode != GlobalConst.SOUNDMODE_USER) {
                        myBalancerSet.get(soundmode).setfocusmark(
                            BalancerName.BalancerName_unfocus);
                        soundmode = GlobalConst.SOUNDMODE_USER;
                        myBalancerSet.get(soundmode).setfocusmark(
                            BalancerName.BalancerName_press);
                    }
                    invalidateMyLineLeft(fiveLine);
                }
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if (myBalancerSet.get(soundmode).Getfocusmark() == BalancerName.BalancerName_focus) {
                    myBalancerSet.get(soundmode).setfocusmark(
                        BalancerName.BalancerName_unfocus);
                    if (soundmode == myBalancerSet.size() - 1) {
                        soundmode = 0;
                    } else {
                        soundmode = soundmode + 1;
                    }
                    myBalancerSet.get(soundmode).setfocusmark(
                        BalancerName.BalancerName_focus);
                    if (mylistener != null)
                        mylistener.BalancerKeyListener(soundmode);
                    InitSetData(fiveLine);
                    this.invalidate();
                } else if (myBalancerSet.get(soundmode).Getfocusmark() == BalancerName.BalancerName_press) {
                    Log.d("Balancer",
                        "........ KEYCODE_DPAD_RIGHT BalancerName_press .........\n");
                    if (soundmode != GlobalConst.SOUNDMODE_USER) {
                        myBalancerSet.get(soundmode).setfocusmark(
                            BalancerName.BalancerName_unfocus);
                        soundmode = GlobalConst.SOUNDMODE_USER;
                        myBalancerSet.get(soundmode).setfocusmark(
                            BalancerName.BalancerName_press);
                    }
                    invalidateMyLineRight(fiveLine);
                }
                break;
            case KeyEvent.KEYCODE_DPAD_CENTER:

                break;

            case KeyEvent.KEYCODE_BACK:
                onKeyDown(KeyEvent.KEYCODE_ENTER, event);
                return true;

//            case KeyEvent.KEYCODE_MUTE:
            case 94:// IMAGE_MODE
            case 95:// VOICE_MODE
            case 96:// DISP_MODE
            case 98:// 3D FORMAT
            case 99:// 3D
            case 100:// SOURCE INPUT
            case KeyEvent.KEYCODE_MENU:
                onKeyDown(KeyEvent.KEYCODE_ENTER, event);
                break;

            case KeyEvent.KEYCODE_ENTER:
                if (mylistener != null) {
                    mylistener.BalancerKeyListener(false, 0, 0);
                }
                break;

            default:
                break;
        }

        return super.onKeyDown(keyCode, event);
    }

    public void setBalancerKeyListener(SkyworthMenuListener listener) {
        mylistener = listener;
    }

    class Line {
        private boolean focusMark = false;
        private int num = 0;
        private int mytop = 0;
        private int myleft = 0;
        private int mydata = 0;

        Line(int left, int top, int number, int data, boolean focusmark) {
            mytop = top;
            myleft = left;
            num = number;
            focusMark = focusmark;
            mydata = data;
        }

        void setfocusmark(final boolean data) {
            focusMark = data;
        }

        void setNum(final int data) {
            num = data;
        }

        void setData(final int data) {
            mydata = data;
        }

        boolean Getfocusmark() {
            return focusMark;
        }

        int GetNum() {
            return num;
        }

        int GetData() {
            return mydata;
        }
    }

    class BalancerName {
        private int focusMark = BalancerName_focus;
        private String mydata = "";
        final static int BalancerName_focus = 1 << 2;
        final static int BalancerName_unfocus = 1 << 3;
        final static int BalancerName_press = 1 << 4;

        BalancerName(String data, int focusmark) {
            mydata = data;
            focusMark = focusmark;
        }

        void setfocusmark(final int data) {
            focusMark = data;
        }

        void setData(final String data) {
            mydata = data;
        }

        int Getfocusmark() {
            return focusMark;
        }

        String GetData() {
            return mydata;
        }
    }

}
