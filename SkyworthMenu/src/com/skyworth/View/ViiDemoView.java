package com.skyworth.View;

import com.skyworth.Listener.SkyworthMenuListener;
import com.skyworth.SkyworthMenu.Menucontrol;
import android.content.Context;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ViiDemoView extends RelativeLayout {
    private TextView viewRed, viewGreen, viewBlue, viewYellow, viewCyan,
        viewMagenta, viewText;
    private int currentString = 0;
    private String stringNum[] = {
        Menucontrol.getResXmlString("allcolors"), 
        Menucontrol.getResXmlString("yellowclose"), 
        Menucontrol.getResXmlString("cyanclose"), 
        Menucontrol.getResXmlString("greenclose"), 
        Menucontrol.getResXmlString("magendaclose"), 
        Menucontrol.getResXmlString("redclose"), 
        Menucontrol.getResXmlString("blueclose"), 
        Menucontrol.getResXmlString("rgbclose"), 
        Menucontrol.getResXmlString("ymcclose"), 
        Menucontrol.getResXmlString("allclose"),
    };
    private String strSplitDemo = Menucontrol.getResXmlString("splitdemo");
    private boolean isSplitDemo = false;
    private SkyworthMenuListener viiDemoViewListener;

    public ViiDemoView(Context context, String menuItemName) {
        super(context);
        if (menuItemName == "shortcut_setup_sys_six_color_colordemo") {
            isSplitDemo = false;
        } else {
            isSplitDemo = true;
        }
        if (isSplitDemo == false) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
            240, 210);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.setMargins(0, 0, 60, 0);
        this.setLayoutParams(params);
        this.setFocusable(true);

        viewYellow = new TextView(this.getContext());
        viewYellow.setId(1);
        viewYellow.setBackgroundColor(0xffffff00);
        params = new RelativeLayout.LayoutParams(40, 150);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        this.addView(viewYellow, params);

        viewCyan = new TextView(this.getContext());
        viewCyan.setText(" ");
        viewCyan.setId(2);
        viewCyan.setBackgroundColor(0xff00ffff);
        params = new RelativeLayout.LayoutParams(40, 150);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params.addRule(RelativeLayout.RIGHT_OF, 1);
        this.addView(viewCyan, params);

        viewGreen = new TextView(this.getContext());
        viewGreen.setText(" ");
        viewGreen.setId(3);
        viewGreen.setBackgroundColor(0xff00ff00);
        params = new RelativeLayout.LayoutParams(40, 150);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params.addRule(RelativeLayout.RIGHT_OF, 2);
        this.addView(viewGreen, params);

        viewMagenta = new TextView(this.getContext());
        viewMagenta.setText(" ");
        viewMagenta.setId(4);
        viewMagenta.setBackgroundColor(0xffff00ff);
        params = new RelativeLayout.LayoutParams(40, 150);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params.addRule(RelativeLayout.RIGHT_OF, 3);
        this.addView(viewMagenta, params);

        viewRed = new TextView(this.getContext());
        viewRed.setText(" ");
        viewRed.setId(5);
        viewRed.setBackgroundColor(0xffff0000);
        params = new RelativeLayout.LayoutParams(40, 150);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params.addRule(RelativeLayout.RIGHT_OF, 4);
        this.addView(viewRed, params);

        viewBlue = new TextView(this.getContext());
        viewBlue.setText(" ");
        viewBlue.setId(6);
        viewBlue.setBackgroundColor(0xff0000ff);
        params = new RelativeLayout.LayoutParams(40, 150);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params.addRule(RelativeLayout.RIGHT_OF, 5);
        this.addView(viewBlue, params);

        viewText = new TextView(this.getContext());
        viewText.setId(7);
        viewText.setText(stringNum[currentString]);
        viewText.setTextSize(30);
        viewText.setTextColor(0xffffffff);
        viewText.setGravity(Gravity.CENTER_HORIZONTAL);
        params = new RelativeLayout.LayoutParams(240, 60);
        params.addRule(RelativeLayout.ALIGN_LEFT, 1);
        params.addRule(RelativeLayout.BELOW, 1);
        this.addView(viewText, params);
        } else {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                320, 1080 - 900);
            this.setLayoutParams(params);
            this.setFocusable(true);
            
            viewText = new TextView(this.getContext());
            viewText.setText(strSplitDemo);
            viewText.setTextSize(36);
            viewText.setTextColor(0xffffffff);
//            this.addView(viewText, params);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_LEFT:
                if (isSplitDemo == false) {
                changeColors(-1);
                viiDemoViewListener.ViiDemoViewToDemoHandle(currentString);
                return true;
                } else {
                    break;
                }

            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if (isSplitDemo == false) {
                changeColors(1);
                viiDemoViewListener.ViiDemoViewToDemoHandle(currentString);
                return true;
                } else {
                    break;
                }

            case KeyEvent.KEYCODE_BACK:
                if (viiDemoViewListener != null) {
                    viiDemoViewListener.ViiDemoViewToMenuHandle();
                }
                return true;

            case KeyEvent.KEYCODE_MUTE:
            case 94:// IMAGE_MODE
            case 95:// VOICE_MODE
            case 96:// DISP_MODE
            case 98:// 3D FORMAT
            case 99:// 3D
            case 100:// SOURCE INPUT
            case KeyEvent.KEYCODE_MENU:
            case 97:// CHANNEL INFO DISPLAY
            case KeyEvent.KEYCODE_ENTER:
                if (viiDemoViewListener != null) {
                    viiDemoViewListener.ViiDemoViewToMenuHandle();
                }
                break;

            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void changeColors(int direction) {
        currentString += direction;
        if (currentString >= stringNum.length)
            currentString = 0;
        else if (currentString < 0)
            currentString = stringNum.length - 1;
        viewText.setText(stringNum[currentString]);
        boolean bRed = true, bGreen = true, bBlue = true, bCyan = true, bMagenda = true, bYellow = true;
        switch (currentString) {
            case 1:
                bYellow = false;
                break;
            case 2:
                bCyan = false;
                break;
            case 3:
                bGreen = false;
                break;
            case 4:
                bMagenda = false;
                break;
            case 5:
                bRed = false;
                break;
            case 6:
                bBlue = false;
                break;
            case 7:
                bRed = false;
                bGreen = false;
                bBlue = false;
                break;
            case 8:
                bYellow = false;
                bCyan = false;
                bMagenda = false;
                break;
            case 9:
                bRed = false;
                bGreen = false;
                bBlue = false;
                bYellow = false;
                bCyan = false;
                bMagenda = false;
                break;
        }
        viewRed.setBackgroundColor(bRed ? 0xffff0000 : 0xffaaaaaa);
        viewGreen.setBackgroundColor(bGreen ? 0xff00ff00 : 0xffaaaaaa);
        viewBlue.setBackgroundColor(bBlue ? 0xff0000ff : 0xffaaaaaa);
        viewCyan.setBackgroundColor(bCyan ? 0xff00ffff : 0xffaaaaaa);
        viewMagenta.setBackgroundColor(bMagenda ? 0xffff00ff : 0xffaaaaaa);
        viewYellow.setBackgroundColor(bYellow ? 0xffffff00 : 0xffaaaaaa);
    }

    public void setViiDemoViewListener(SkyworthMenuListener SPL) {
        viiDemoViewListener = SPL;
    }

}
