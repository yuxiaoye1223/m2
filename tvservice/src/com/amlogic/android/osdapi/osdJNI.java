package com.amlogic.android.osdapi;

import android.util.Log;
import java.lang.*;
import android.os.SystemProperties;

public class osdJNI {
    private static boolean nocst02board = false;

    public static void InitOsd() {
        OpenOSDModule(0);
        OpenOSDModule(1);
        SetOSDColorkey(0, 1, 0); // blending blackground
        nocst02board = SystemProperties.getBoolean("hw.nocst02", false);
    }

    public static int SetGpioCtrl(String cmd) {
        if (nocst02board)
            return 0;
        else
            return GpioCtrl(cmd);
    }

    public static int SetDebugcmd(String cmd) {
        if (nocst02board)
            return 0;
        else
            return DebugCmd(cmd);
    }

    /***************************************************
     * // load liberary & JNI methods
     ***************************************************/
    static {
        System.loadLibrary("osd_api");
    }

    public static native int OpenOSDModule(int osdlayer);

    public static native void CloseOSDodule(int osdlayer);

    public static native int SetGBLAlpha(int osdlayer, int value);

    public static native int SetOSDColorkey(int osdlayer, int onoff, int value);

    public static native int GpioCtrl(String cmd);

    public static native int DebugCmd(String cmd);
}
