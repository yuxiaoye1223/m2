package com.amlogic.android.dreampanel;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.amlogic.android.eepromapi.eepromJNI;
import com.amlogic.android.eepromapi.eepromJNI.PanelType;
import com.amlogic.android.tvmisc.TvMiscJNI;
import com.amlogic.android.vdinapi.vdinJNI;
import com.amlogic.android.vppapi.vppJNI;
import com.amlogic.tvjni.GlobalConst;

public class dreampanelJNI {
    static {
        System.loadLibrary("dreampanel");
    }

    public static final int Off_Mode = 0;
    public static final int Environ_Light_Mode = 1;
    public static final int Image_Content_Mode = 2;
    public static final int Multi_Detect_Mode = 3;
    public static final int Backlightcontrol_Mode_Invalid = 4;

	private static int curMode = 0;
    private static boolean isDreampanelDemo = false;
    private static Handler mHandler = null;
    private static final String TAG = "dreampanelJNI";
    private static int[] demoData = new int[18];
    private static long[] tmpValue = new long[16];
    private static int HistgramBuf[] = {
        0, 0, 0, 0, // 0
        0,
        0,
        0,
        0, // 1
        0,
        0,
        0,
        0, // 2
        0,
        0,
        0,
        0, // 3
        0,
        0,
        0,
        0, // 4
        0,
        0,
        0,
        0, // 5
        0,
        0,
        0,
        0, // 6
        0,
        0,
        0,
        0, // 7
        0,
        0,
        0,
        0, // 8
        0,
        0,
        0,
        0, // 9
        0,
        0,
        0,
        0, // 10
        0,
        0,
        0,
        0, // 11
        0,
        0,
        0,
        0, // 12
        0,
        0,
        0,
        0, // 13
        0,
        0,
        0,
        0, // 14
        0,
        0,
        0,
        0
    // 15
    };

    private static int ImageHistgramGetted[] = {
        0, 0, 0, 0, // 0---3
        0,
        0,
        0,
        0, // 4---7
        0,
        0,
        0,
        0, // 8---11
        0,
        0,
        0,
        0
    // 12---15
    };

    private static int GetLightSensorValue() {
        int value;
        value = TvMiscJNI.ReadADCSpecialChannelValue(2);
        value = value >> 2;
        if (value > 200) {      // about 2.6v
            value = 200;
        } else if (value < 5) {
            value = 0;
        }
        value = value * 255 / 200;
        return value;
    }

    private static int GetImageHistgramValue() {
        vdinJNI.SrcType srctype = vdinJNI.GetSrcType();
        int HistgramIsOk = -1;
        
        if (srctype == vdinJNI.SrcType.AV
            || srctype == vdinJNI.SrcType.COMPONENT
            || srctype == vdinJNI.SrcType.HDMI
            || srctype == vdinJNI.SrcType.VGA) {
            HistgramIsOk = vdinJNI.GetHistgram(HistgramBuf);
        }
        if (HistgramIsOk == 0) { // data is right.
            long histgramSum = 0;
            
            for (int i = 0; i < 16; i++) {
                tmpValue[i] = 0;
                for (int j = 0; j < 4; j++) {
                    if (HistgramBuf[i * 4 + j] < 0) {
                        HistgramBuf[i * 4 + j] = 0;
                    }
                    tmpValue[i] += HistgramBuf[i * 4 + j];
                }
                histgramSum += tmpValue[i];
            }
            for (int i = 0; i < 16; i++) {
                if (histgramSum == 0) {
                    ImageHistgramGetted[i] = 0;
                } else {
                    ImageHistgramGetted[i] = (int) (tmpValue[i] * 255 / histgramSum);
                }
            }
        } else {
            for (int i = 0; i < 16; i++) {
                if (i == 8) {
                    ImageHistgramGetted[i] = 255;
                } else {
                    ImageHistgramGetted[i] = 0;
                }
            }
        }
        return HistgramIsOk;
    }

    public static void BackLightControlModeInit() {
        int curPanel = eepromJNI.GetCurPanel();
        setCurMode(eepromJNI.LoadMiscSetting(eepromJNI.MISCFunc.DREAMPANEL));
        int UIvalue = eepromJNI.LoadMiscSetting(eepromJNI.MISCFunc.BACKLIGHT);
        int LastREGvalue = 0;

        int REGvalue = BackLight_ControlMode_Init(curPanel, curMode, UIvalue, LastREGvalue);
        vppJNI.DreamPanelSetBL(REGvalue,60);
    }

    public static void BackLightControlModeSet(int mode) {
        setCurMode(mode);
        if (curMode == Off_Mode) {
            int UIvalue = eepromJNI.LoadMiscSetting(eepromJNI.MISCFunc.BACKLIGHT);
            BackLightManualValueSet(UIvalue);
        } else {
            int lastREGvalue = BackLight_LastLight_Get();
            int REGvalue = BackLight_ControlMode_Set(curMode, 0, lastREGvalue);
            vppJNI.DreamPanelSetBL(REGvalue, 60);
        }
    }

    public static void BackLightManualValueSet(int UIvalue) {
        int REGvalue = BackLight_ManualValue_Set(UIvalue);
        vppJNI.DreamPanelSetBL(REGvalue,60);

        eepromJNI.SaveMiscSetting(eepromJNI.MISCFunc.DREAMPANEL,
            BackLight_ControlMode_Get());
        setCurMode(eepromJNI.LoadMiscSetting(eepromJNI.MISCFunc.DREAMPANEL));
    }

    public static void BackLightControlHandler() {
        if (eepromJNI.IsBusOff() == true || eepromJNI.IsBurnFlagOn() == true) {
            return;
        }
        
        int LightSensorValueGetted = 255;
        int REGvalue = 255;
        
        switch (curMode) {
            case Environ_Light_Mode:
                LightSensorValueGetted = GetLightSensorValue();
                REGvalue = LightSense_Backlight_adjust(LightSensorValueGetted);
		        vppJNI.DreamPanelSetBL(REGvalue,60);
                break;

            case Multi_Detect_Mode:
                LightSensorValueGetted = GetLightSensorValue();
            case Image_Content_Mode:
                int result = GetImageHistgramValue();
                if ((result != 0)
                    || (vdinJNI.GetSigStatus() == vdinJNI.SigStatus.STABLE
                        .ordinal())) {
                    REGvalue = ImageSense_Backlight_adjust(
                        LightSensorValueGetted, ImageHistgramGetted);
		  		    vppJNI.DreamPanelSetBL(REGvalue,60);
                } else {
                    REGvalue = BackLight_LastLight_Get();
                }
                break;

            case Off_Mode:
            default:
                break;
        }

	    if (isDreampanelDemo() && (curMode == Multi_Detect_Mode)) {
            demoData[0] = LightSensorValueGetted;
            for (int i = 0; i < 16; i++) {
                if (ImageHistgramGetted[i] < 11) {
                    demoData[i + 1] = ImageHistgramGetted[i] * 9; // 0--10 to
                                                                  // 0--90
                } else if (ImageHistgramGetted[i] < 21) {
                    demoData[i + 1] = (ImageHistgramGetted[i] * 6) + 30; // 10--20
                                                                         // to
                                                                         // 90--150
                } else if (ImageHistgramGetted[i] < 51) {
                    demoData[i + 1] = (ImageHistgramGetted[i] * 2) + 110; // 20--50
                                                                          // to
                                                                          // 150--210
                } else if (ImageHistgramGetted[i] < 251) {
                    demoData[i + 1] = (ImageHistgramGetted[i] / 5) + 200; // 50--250
                                                                          // to
                                                                          // 210--250
                } else {
                    demoData[i + 1] = 255; // 250--255 to 255
                }
            }
            demoData[17] = REGvalue;

            updateDreampanelDemoUI(demoData);
        }
    }

    public static void setCurMode(int mode) {
        curMode = mode;
    }
    
    private static void backToLastMode() {
        dreampanelJNI.BackLightControlModeSet(eepromJNI.LoadMiscSetting(eepromJNI.MISCFunc.DREAMPANEL));
        eepromJNI.SaveMiscSetting(eepromJNI.MISCFunc.BACKLIGHT, 100);
    }
    
    // ===================================================================
    // UI Relate Function
    // ===================================================================
    public static void setDreampanelHandler(Handler tmpHandler) {
        mHandler = tmpHandler;
    }

    public static void updateDreampanelDemoUI(int[] data) {
        sendMessageToUI(GlobalConst.MSG_DERAMPANEL_DEMO_UPDATE,
            "dreampanel_data", data);
    }

    public static boolean isDreampanelDemo() {
        return isDreampanelDemo;
    }

    public static void setDreampanelDemo(boolean flag) {
        isDreampanelDemo = flag;
        if (flag == false)
            backToLastMode();
    }
    
    private static Bundle b = new Bundle();
    private static void sendMessageToUI(int MsgType, String name, int[] data) {
        if (b != null) {
            b.putIntArray(name, data);
            dreampanelSendMessage(MsgType, b);
        }
    }
    
    private static void dreampanelSendMessage(int MsgType, Bundle b) {
        if (mHandler != null) {
            Message msg = mHandler.obtainMessage();
            if (msg != null) {
                msg.arg1 = MsgType;
                msg.setData(b);
                mHandler.sendMessage(msg);
                msg = null;
            }
        }
    }

    // ===================================================================
    // methods
    // ===================================================================
    public static native int BackLight_ControlMode_Set(int mode,
        int FirstPowerOn, int REGvalue);

    public static native int BackLight_ManualValue_Set(int UIvalue);

    public static native int BackLight_ControlMode_Init(int curPanel, int mode,
        int UIvalue, int LastREGvalue);

    public static native int BackLight_LastLight_Get();

    public static native int BackLight_ControlMode_Get();

    public static native void BackLight_NoSignalState_Set(int Nosignal);

    public static native int ImageSense_Backlight_adjust(
        int LightSensorValueGetted, int[] ImageHistgramGetted);

    public static native int LightSense_Backlight_adjust(
        int LightSensorValueGetted);

    public static native void DreamPanel_LightData_Get(int[] Light);

}
