package com.skyworth.View;

import com.skyworth.Listener.SkyworthMenuListener;
import com.skyworth.SkyworthMenu.Menucontrol;
import com.skyworth.control.tvsetting;

import android.content.Context;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DreamPanelDemoView extends RelativeLayout {
    private TextView viewBar;
    private BorderTextView border;
    private SkyworthMenuListener dreamPanelDemoListener;

    private static final int BORDER1 = 1;
    private static final int BORDER2 = 2;
    private static final int BORDER17 = 17;
    private static final int BORDER18 = 18;
    private static final int SENSOR_AREA = 19;

    private static final int BORDER_EDGE_WIDTH = 1;
    private static final int BORDER_WIDTH = 40;
    private static final int BAR_WIDTH = BORDER_WIDTH - 2 * BORDER_EDGE_WIDTH;
    private static final int BORDER_HEIGHT = 257;
    private static final int BAR_MAX_HEIGHT = BORDER_HEIGHT - 2
        * BORDER_EDGE_WIDTH;

    private static final int MARGIN_TO_PARENT = 350;
    private static final int MARGIN_TO_SENSOR = 100;
    private static final int MARGIN_TO_PWM = 150;
    private static final int MARGIN_BETWEEN_BAR = 50;

    public DreamPanelDemoView(Context context) {
        super(context);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
            500, 210);
        this.setFocusable(true);
        tvsetting.setDreampanelHandler();

        int barData[] = {
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
        }; // 18
        int backgroudColor = 0;
        int marginLeft, maginTop = 0, marginRight, marginBottom = 100;
        for (int i = BORDER1; i <= BORDER18; i++) {
            border = new BorderTextView(this.getContext(), null);
            border.setId(i);
            border.setWidth(BORDER_WIDTH);
            border.setHeight(BORDER_HEIGHT);
            params = new RelativeLayout.LayoutParams(BORDER_WIDTH,
                BORDER_HEIGHT);
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            if (i == BORDER1) {
                marginLeft = MARGIN_TO_PARENT;
                marginRight = MARGIN_TO_SENSOR;
            } else if (i == BORDER18) {
                marginLeft = MARGIN_TO_PARENT + MARGIN_TO_SENSOR
                    + MARGIN_BETWEEN_BAR * i + MARGIN_TO_PWM;
                marginRight = 0;
            } else {
                marginLeft = MARGIN_TO_PARENT + MARGIN_TO_SENSOR
                    + MARGIN_BETWEEN_BAR * i;
                marginRight = 0;
            }
            params.setMargins(marginLeft, maginTop, marginRight, marginBottom);
            this.addView(border, params);
            viewBar = new TextView(this.getContext());
            if (i == BORDER1 || i == BORDER18)
                backgroudColor = 0xff3197FF;
            else if (i == BORDER2)
                backgroudColor = 0xff000000;
            else if (i == BORDER17)
                backgroudColor = 0xffffffff;
            else
                backgroudColor = 0xff000000 + 0x000c0c0c * (i - 2);
            viewBar.setBackgroundColor(backgroudColor);
            viewBar.setText(" ");
            viewBar.setId(i + BORDER18);
            if (barData[i - 1] > BAR_MAX_HEIGHT)
                barData[i - 1] = BAR_MAX_HEIGHT;
            viewBar.setHeight(barData[i - 1]);
            viewBar.setWidth(BAR_WIDTH);
            params = new RelativeLayout.LayoutParams(BAR_WIDTH,
                LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.ALIGN_LEFT, i);
            params.addRule(RelativeLayout.ALIGN_BOTTOM, i);
            params.setMargins(BORDER_EDGE_WIDTH, 0, 0, BORDER_EDGE_WIDTH);
            this.addView(viewBar, params);
        }

        // characor "¡¡"
        TextView tempText = new TextView(this.getContext());
        tempText.setText(Menucontrol.getResXmlString("bright"));
        tempText.setTextSize(28);
        tempText.setGravity(Gravity.RIGHT);
        params = new RelativeLayout.LayoutParams(100, 40);
        params.addRule(RelativeLayout.ALIGN_TOP, BORDER1);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        params.setMargins(MARGIN_TO_PARENT - 110, 0, 0, 0);
        this.addView(tempText, params);

        tempText = new TextView(this.getContext());
        tempText.setText(Menucontrol.getResXmlString("bright"));
        tempText.setTextSize(28);
        tempText.setGravity(Gravity.LEFT);
        params = new RelativeLayout.LayoutParams(100, 40);
        params.addRule(RelativeLayout.ALIGN_TOP, BORDER18);
        params.addRule(RelativeLayout.RIGHT_OF, BORDER18);
        params.setMargins(10, 0, 0, 0);
        this.addView(tempText, params);

        tempText = new TextView(this.getContext());
        tempText.setText(Menucontrol.getResXmlString("bright"));
        tempText.setTextSize(28);
        tempText.setGravity(Gravity.LEFT);
        params = new RelativeLayout.LayoutParams(100, 40);
        params.addRule(RelativeLayout.ALIGN_BOTTOM, BORDER17);
        params.addRule(RelativeLayout.RIGHT_OF, BORDER17);
        params.setMargins(10, 0, 0, 0);
        this.addView(tempText, params);
        // characor "∞µ"
        tempText = new TextView(this.getContext());
        tempText.setText(Menucontrol.getResXmlString("dark"));
        tempText.setTextSize(28);
        tempText.setGravity(Gravity.RIGHT);
        params = new RelativeLayout.LayoutParams(100, 40);
        params.addRule(RelativeLayout.ALIGN_BOTTOM, BORDER1);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        params.setMargins(MARGIN_TO_PARENT - 110, 0, 0, 0);
        this.addView(tempText, params);

        tempText = new TextView(this.getContext());
        tempText.setText(Menucontrol.getResXmlString("dark"));
        tempText.setTextSize(28);
        tempText.setGravity(Gravity.LEFT);
        params = new RelativeLayout.LayoutParams(100, 40);
        params.addRule(RelativeLayout.ALIGN_BOTTOM, BORDER18);
        params.setMargins(10, 0, 0, 0);
        params.addRule(RelativeLayout.RIGHT_OF, BORDER18);

        this.addView(tempText, params);
        tempText = new TextView(this.getContext());
        tempText.setText(Menucontrol.getResXmlString("dark"));
        tempText.setTextSize(28);
        tempText.setGravity(Gravity.CENTER);
        params = new RelativeLayout.LayoutParams(100, 40);
        params.addRule(RelativeLayout.ALIGN_BOTTOM, BORDER2);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        params.setMargins(MARGIN_TO_PARENT + MARGIN_TO_SENSOR
            + MARGIN_BETWEEN_BAR * 2 - 80, 0, 0, 0);
        this.addView(tempText, params);

        tempText = new TextView(this.getContext());
        tempText.setTextSize(28);
        tempText.setGravity(Gravity.CENTER);
        tempText.setText(Menucontrol.getResXmlString("sensorDetect"));
        params = new RelativeLayout.LayoutParams(200, 40);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        params.setMargins(MARGIN_TO_PARENT - 80, 0, 0, 50);
        this.addView(tempText, params);

        tempText = new TextView(this.getContext());
        tempText.setTextSize(28);
        tempText.setGravity(Gravity.CENTER);
        tempText.setText(Menucontrol.getResXmlString("imageDetect"));
        params = new RelativeLayout.LayoutParams(200, 40);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        params.setMargins(MARGIN_TO_PARENT + MARGIN_TO_SENSOR
            + MARGIN_BETWEEN_BAR * 2 + 300, 0, 0, 50);
        this.addView(tempText, params);

        tempText = new TextView(this.getContext());
        tempText.setTextSize(28);
        tempText.setGravity(Gravity.CENTER);
        tempText.setText(Menucontrol.getResXmlString("pwmOut"));
        params = new RelativeLayout.LayoutParams(200, 40);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.addRule(RelativeLayout.ALIGN_RIGHT, BORDER18);
        params.setMargins(0, 0, -80, 50);
        this.addView(tempText, params);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (dreamPanelDemoListener != null) {
                    dreamPanelDemoListener.DreampanelDemoToMenuHandle();
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
                // case KeyEvent.KEYCODE_ENTER:
                if (dreamPanelDemoListener != null) {
                    dreamPanelDemoListener.DreampanelDemoToMenuHandle();
                }
                break;

            default:
                break;

        }
        return super.onKeyDown(keyCode, event);
    }

    public void refreshDreamPanelDisplay(int[] value) {
        for (int i = 0; i < 18; i++) {
            viewBar = (TextView) this.findViewById(i + SENSOR_AREA);
            if (value[i] > BAR_MAX_HEIGHT)
                value[i] = BAR_MAX_HEIGHT;
            viewBar.setHeight(value[i]);
        }
    }

    public void setDreamPanelDemoListener(SkyworthMenuListener SPL) {
        dreamPanelDemoListener = SPL;
    }

}
