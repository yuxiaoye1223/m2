package com.amlogic.tvjni;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Timer;
import java.util.TimerTask;

import com.amlogic.R;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import android.app.Service;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.util.Log;
import com.amlogic.android.afeapi.*;
import com.amlogic.android.audioctlapi.*;
import com.amlogic.android.vdinapi.*;
import com.amlogic.android.vdinapi.vdinJNI.SrcType;
import com.amlogic.android.vppapi.*;
import com.amlogic.android.osdapi.*;
import com.amlogic.android.serialport.SerialPort;
import com.amlogic.android.serialport.SettingParam;
import com.amlogic.android.tunerapi.*;
import com.amlogic.android.dreampanel.dreampanelJNI;
import com.amlogic.android.eepromapi.eepromJNI;
import com.amlogic.android.eepromapi.eepromJNI.*;
import com.amlogic.android.tvmisc.TvMiscJNI;
import com.amlogic.android.tvmisc.TvMiscJNI.*;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;

public class tvservice extends Service {
    private boolean DreamEnable = true;
    private boolean SerialEnable = true;
    private static final String TAG = "tvservice";
    private boolean uartForTV = false;
    private boolean nocst02board = false;
    protected int counttime = 0;
    public static boolean setdefault = false;
    public static boolean inCloseTv = false;
    private EarlyStandbyReceiver earlystandby;
    private WakeLock wakeLock = null;
    private static int UserCount = 0;

    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind\n");
        return binder;
    }

    // ---------------------------------------------------
    // This API is test LED GPIO only
    public static void SetLEDStatus() {
        Thread t = new Thread() {
            public void run() {
                for (int i = 0; i < 60; i++) {
                    try {
                        Thread.sleep(200);
                        if ((i % 2) == 0) {
                            osdJNI.SetGpioCtrl("w x 56 0");
                        } else {
                            osdJNI.SetGpioCtrl("w x 56 1");
                        }
                        Log.e(TAG, "hold amlogic_tvservice wake lock ! >>>>>>>>>>>>>:"
                            + i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();
    }

    // EarlyStandby RECEIVER
    class EarlyStandbyReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            if (intent.getAction().equals("android.intent.action.EARLY_STANDBY")) {
                Log.d(TAG, "android.intent.action.EARLY_STANDBY received");
                // =======CloseTv========
                AudioCtlJNI.SetAmplifierMute(AudioCustom.CC_MUTE_ON);

                AudioCustom.SetVolumeDigitLUTBuf(AudioCustom.CC_LUT_SEL_MPEG, 0);

                setSleepTimerOff();

                vdinJNI.CloseTvin(0);

                if (tunerJNI.searchflag == true) {
                    tunerJNI.setSearchStop(true);
                    tunerJNI.DelayMs(100);
                }
                tunerJNI.KillAutoAFCThr();
                tunerJNI.muteTunerAudio();
                tunerJNI.CloseTuner();
                afeJNI.CloseAfe();

                AudioCustom.AudioCtlUninit();

                // It should be mute off when resume as customer request.
                // we should clear it and set correct audio volume when mute key
                // pressed.
                if (AudioCustom.ismCustomVolumeMute()) {
                    AudioCustom.setmCustomVolumeMute(false); // Mute Off
                    AudioCtlJNI.SetAmplifierMute(AudioCustom.CC_MUTE_ON);
                }

                // stop video decode
                osdJNI.SetOSDColorkey(0, 0, 0);
                osdJNI.SetOSDColorkey(1, 0, 0);
                osdJNI.SetGBLAlpha(1, 0xff); // no blending mouse icon
                osdJNI.SetGBLAlpha(0, 0xff); // no blending mouse icon

                // eepromJNI.CloseEeprom(); // current usage: always set '0'
                vppJNI.UninitTvVpp();
                Log.d(TAG, "EARLY_STANDBY Close Tvin called");

                // Power off backlight
                vppJNI.StandbyAudioMutePanel(2);
                eepromJNI.SetSystemAutoSuspending(1);

                // start home luncher
                Intent HomeIntent = new Intent(Intent.ACTION_MAIN, null);
                HomeIntent.addCategory(Intent.CATEGORY_HOME);
                HomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                context.startActivity(HomeIntent);

                Log.d(TAG, "EARLY_STANDBY -----start home intent send!");
                PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
                wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "amlogic_tvservice");
                wakeLock.acquire();
                Log.d(TAG, "EARLY_STANDBY -----wakeLock.acquire()--amlogic_tvservice");
                String StrIRPreStandby = "" + 1;
                String StrIREnable = "" + 1;
                try {
                    BufferedWriter IRwriter = new BufferedWriter(new FileWriter("/sys/module/remote/parameters/ir_pre_standby"), 6);
                    try {
                        IRwriter.write(StrIRPreStandby);
                    } finally {
                        IRwriter.close();
                    }
                } catch (IOException e) {
                    Log.e(TAG, "IO Exception when write: "
                        + "/sys/module/remote/parameters/ir_pre_standby", e);
                }
                Log.d(TAG, "EARLY_STANDBY -----set ir_pre_standby.");
                /*
                 * try { BufferedWriter IRwriter = new BufferedWriter(new
                 * FileWriter
                 * ("/sys/module/remote/parameters/report_key_enable"), 6); try
                 * { IRwriter.write(StrIREnable); } finally { IRwriter.close();
                 * } } catch (IOException e) { Log.e(TAG,
                 * "IO Exception when write: " +
                 * "/sys/module/remote/parameters/report_key_enable", e); }
                 * Log.d(TAG,
                 * "EARLY_STANDBY -----set report_key_enable--amlogic_tvservice"
                 * );
                 */
                // Set to LED red
                osdJNI.SetGpioCtrl("w x 56 0");
                // wait go home and onStop front activity
                SystemClock.sleep(3000);

                Thread t = new Thread() {
                    public void run() {
                        for (int i = 0; i < 2; i++) {
                            try {
                                Thread.sleep(1000);
                                Log.e(TAG, "hold amlogic_tvservice wake lock:"
                                    + i);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        if (wakeLock.isHeld()) {
                            wakeLock.release();
                            Log.e(TAG, "wakeLock.release() from amlogic_tvservice.");
                        }
                    }
                };
                t.start();
                // Set system status flag and AutoSuspending flag
                int data = pm.getAmlInt();
                Log.d(TAG, "EARLY_STANDBY -----getAmlInt() = " + data);
                if (data > 1) {
                    pm.setBacklightBrightness(255);
                    try {
                        BufferedWriter IRwriter = new BufferedWriter(new FileWriter("/sys/module/remote/parameters/ir_pre_standby"), 6);
                        try {
                            IRwriter.write("0");
                        } finally {
                            IRwriter.close();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "IO Exception when write: "
                            + "/sys/module/remote/parameters/ir_pre_standby", e);
                    }
                    Log.d(TAG, "EARLY_STANDBY -----clear ir_pre_standby.");
                    try {
                        BufferedWriter IRwriter = new BufferedWriter(new FileWriter("/sys/module/remote/parameters/report_key_enable"), 6);
                        try {
                            IRwriter.write(StrIREnable);
                        } finally {
                            IRwriter.close();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "IO Exception when write: "
                            + "/sys/module/remote/parameters/report_key_enable", e);
                    }
                    Log.d(TAG, "EARLY_STANDBY -----set report_key_enable.");
                } else {
                    eepromJNI.SaveSysStatus(SysStatus.STANDBY);
                    eepromJNI.SetSystemAutoSuspending(0);
                    PowerOff();
                }
                pm.setAmlInt(0);
            }
        }
    }

    private void registerEarlyStandby() {
        earlystandby = new EarlyStandbyReceiver();
        this.registerReceiver(earlystandby, new IntentFilter("android.intent.action.EARLY_STANDBY"));
    }

    private void PowerOff() {
        try {
            // echo P > /sys/class/simkey/keyset
            BufferedWriter writer = new BufferedWriter(new FileWriter("/sys/class/simkey/keyset"), 2);
            try {
                writer.write("P");
            } finally {
                writer.close();
            }
            Log.e(TAG, "tvservice PowerOff---------- "
                + "/sys/class/simkey/keyset ok");
        } catch (IOException e) {
            Log.e(TAG, "tvservice PowerOff------- " + "/sys/class/simkey/keyse", e);
        }
    }

    // ---------------------------------------------------

    // public void writeFile() {
    // File file = new File("/data/app/com.skyworth.controlservice.apk");
    // FileChannel wChannel;
    // InputStream is;
    // try {
    // if (!file.exists() && !CheckApkExist()) {
    // Log.d(TAG,
    // "com.skyworth.controlservice.apk not exist,so write it from raw \n");
    // is = getResources().openRawResource(R.raw.controlservice);
    // int length = is.available();
    // byte[] byteBuffer = new byte[length];
    // is.read(byteBuffer);
    // is.close();
    // wChannel = new FileOutputStream(file, false).getChannel();
    // ByteBuffer bb = ByteBuffer.allocate(length);
    // bb.put(byteBuffer);
    // bb.position(0);
    // wChannel.write(bb);
    // wChannel.close();
    // chmodApk();
    // } else
    // Log.d(TAG, "com.skyworth.controlservice...apk exist already \n");
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // }
    //
    // private void chmodApk() {
    // try {
    // Process p;
    // p =
    // Runtime.getRuntime().exec("chmod 666 /data/app/com.skyworth.controlservice.apk");
    // int status;
    // status = p.waitFor();
    // if (status == 0) {
    // Log.d(TAG, "com.skyworth.controlservice.apk  chmod succeed ");
    // } else {
    // Log.d(TAG, "com.skyworth.controlservice.apk  chmod failed ");
    // }
    // p.destroy();
    // } catch (InterruptedException e) {
    // e.printStackTrace();
    // Log.d(TAG, "InterruptedException " + e.toString());
    // } catch (IOException e) {
    // e.printStackTrace();
    // Log.d(TAG, "IOException " + e.toString());
    // }
    // }
    //
    // private boolean CheckApkExist() {
    // boolean exist = false;
    // File file = new File("/data/app");
    // if (file.exists()) {
    // File[] lf = file.listFiles();
    // for (File f : lf) {
    // if (f.getName().toLowerCase().startsWith("com.skyworth.controlservice"))
    // return true;
    // }
    // }
    // /*
    // * PackageManager mPackageManager = this.getPackageManager(); Intent
    // * mainIntent = new Intent(Intent.ACTION_MAIN, null);
    // * mainIntent.addCategory(Intent.CATEGORY_LAUNCHER); List<ResolveInfo>
    // * resolveInfo = mPackageManager.queryIntentActivities(mainIntent, 0);
    // * Collections.sort(resolveInfo, new
    // * ResolveInfo.DisplayNameComparator(mPackageManager)); for (int i = 0;
    // * i < resolveInfo.size(); i++) { boolean flag = false; if
    // * ((resolveInfo.get(i).activityInfo.applicationInfo.flags &
    // * ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) { flag = true; } else
    // * if ((resolveInfo.get(i).activityInfo.applicationInfo.flags &
    // * ApplicationInfo.FLAG_SYSTEM) == 0) { flag = true; }
    // *
    // * if (flag) { if (resolveInfo.get(i).activityInfo.packageName.equals(
    // * "com.skyworth.controlservice")) { exist = true; break; } } }
    // */
    // return exist;
    // }

    public void onCreate() {
        super.onCreate();
        SystemProperties.set("persist.service.adb.enable", "1"); // set Adbd
                                                                 // Enable
                                                                 // default

        uartForTV = SystemProperties.getBoolean("ro.serialport.tvmode", false);
        nocst02board = SystemProperties.getBoolean("hw.nocst02", false);
        if (nocst02board) {
            Log.d(TAG, "Please note, you are using aml reference board.");
        }
        // writeFile();

        Log.v(TAG, "0713-- on create");
        // todo init
        eepromJNI.InitEeprom();
        vppJNI.InitTvVpp();
        vdinJNI.Open3DModule();

        if (vdinJNI.GetSrcType() == SrcType.MPEG) {
            Log.d(TAG, "tvservice oncreate set 16:9 display mode");
            vppJNI.SetScaleParam(0, 0, (1920 - 1), (1080 - 1));
        }
        // load audio setting from e2prom
        AudioCustom.FirstLoadAudioCtl();

        dreampanelJNI.BackLightControlModeInit();

        writeBarcode();
        WriteMac();
        WriteLVDS();
        
        //////////////////////////uart setting
        {
        	boolean uartswitchflag;
        	uartswitchflag = eepromJNI.FacGetSerialPortSwitchFlag();
        	if(uartswitchflag)
        	{
        		eepromJNI.SetUartSwitch(1);
        	}else{
        		eepromJNI.SetUartSwitch(0);
        	}
        }

        // /////////////////////////himi setting
        {
            int hdmieqvalue = eepromJNI.LoadFactoryOption(FactoryOption.HDMI_EQ_MODE);
            if (hdmieqvalue > GetEqModeMax()) {
                eepromJNI.SaveFactoryOption(FactoryOption.HDMI_EQ_MODE, 0);
                SetEqModeValue(0);
                Log.d("test", "SetEqModeValue(0);");
            } else {
                SetEqModeValue(hdmieqvalue);
                Log.d("test", "SetEqModeValue(hdmieqvalue)=" + hdmieqvalue);
            }

            int InternalModeValue = eepromJNI.FacGetHdmiInternalMode();
            if (InternalModeValue != 0xffffffff) {
                // eepromJNI.FacSetHdmiInternalMode(InternalModeValue);
                SetInternalModeValue(InternalModeValue);
                Log.d("test", "SetInternalModeValue(InternalModeValue)="
                    + InternalModeValue);
            } else {
                eepromJNI.FacSetHdmiInternalMode(0);
                SetInternalModeValue(0);
                Log.d("test", "SetInternalModeValue(0);");
            }
        }

        // //////////////////load SSC SET IN POWER ON
        int[] buf = new int[4];
        buf = eepromJNI.GetSscDate();
        if (buf[3] == 1) {
            switch (buf[0]) {
                case 0:
                    vppJNI.SetSSC(vppJNI.SSCRange.RATIO_1, buf[1], buf[2], true);
                    break;
                case 1:
                    vppJNI.SetSSC(vppJNI.SSCRange.RATIO_1D5, buf[1], buf[2], true);
                    break;
                case 2:
                    vppJNI.SetSSC(vppJNI.SSCRange.RATIO_2, buf[1], buf[2], true);
                    break;
                case 3:
                    vppJNI.SetSSC(vppJNI.SSCRange.RATIO_3D5, buf[1], buf[2], true);
                    break;
                case 4:
                    vppJNI.SetSSC(vppJNI.SSCRange.RATIO_4D5, buf[1], buf[2], true);
                    break;
                default:
                    vppJNI.SetSSC(vppJNI.SSCRange.RATIO_1, buf[1], buf[2], true);
                    break;
            }
        } else
            vppJNI.SetSSC(vppJNI.SSCRange.RATIO_1, 0, 0, false);

        Thread dreampanel = new Thread(new Runnable() {
            public void run() {
                while (DreamEnable) {
                    dreampanelJNI.BackLightControlHandler();

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        dreampanel.setName("dreampanel-thread");
        dreampanel.start();

        Thread Tvthread = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    if (++UserCount == 0x7FFFFFFF)
                        UserCount = 1;
                    TvMiscJNI.SetUserCounter(UserCount);
                    // Log.d(TAG, "Tvthread SetUserCounter: " + UserCount);
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        TvMiscJNI.SetUserCounterTimeOut(30);
        Tvthread.setName("Tvthread");
        Tvthread.setPriority(Thread.MAX_PRIORITY);
        Tvthread.start();

        if (uartForTV) {
            SerialPort.startmain();
            Log.d(TAG, "uartForTV true \n");
            Thread serial = new Thread(new Runnable() {
                public void run() {
                    while (SerialEnable) {
                        SettingParam[] a = (SettingParam[]) SerialPort.UartGetJ();
                        for (SettingParam sp : a) {
                            Log.d(TAG, "for(SettingParam sp:a)  start\n");
                            Log.d(TAG, "sp.name is = "
                                + String.valueOf(sp.name));
                            Log.d(TAG, "sp.value is = "
                                + String.valueOf(sp.value));

                            if (sp.name <= 0x25)
                                OtherSelect(sp.name, sp.value);
                            else if (sp.name <= 0x38)
                                ChannelSelect(sp.name, sp.value);
                            else if (sp.name <= 0x49)
                                ImageSetting(sp.name, sp.value);
                            else if (sp.name <= 0x6a)
                                WhiteBalance(sp.name, sp.value);
                            else if (sp.name <= 0x7a)
                                VoiceSetting(sp.name, sp.value);

                        }

                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            serial.setName("serialport-thread");
            serial.start();
        }

        //
        registerEarlyStandby();
    }

    // //////////////////hdmi setting funtion

    public int GetEqModeMax() {
        File f1 = new File("/sys/module/tvin_hdmirx/parameters/eq_mode_max");
        try {
            int adcvaule = 0;
            // FileWriter fw = new FileWriter(f1);
            // BufferedWriter buf = new BufferedWriter(fw);
            FileReader fw = new FileReader(f1);
            BufferedReader buf = new BufferedReader(fw);
            // buf.write(state);
            adcvaule = Integer.parseInt(buf.readLine());
            buf.close();
            Log.d("test", "get /sys/module/tvin_hdmirx/parameters/eq_mode_max: "
                + adcvaule);
            return adcvaule;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int GetEqModeValue() {
        File f1 = new File("/sys/module/tvin_hdmirx/parameters/eq_mode");
        try {
            int adcvaule = 0;
            // FileWriter fw = new FileWriter(f1);
            // BufferedWriter buf = new BufferedWriter(fw);
            FileReader fw = new FileReader(f1);
            BufferedReader buf = new BufferedReader(fw);
            // buf.write(state);
            adcvaule = Integer.parseInt(buf.readLine());
            buf.close();
            Log.d("test", "get /sys/module/tvin_hdmirx/parameters/eq_mode: "
                + adcvaule);
            return adcvaule;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void SetEqModeValue(int value) {
        try {
            String writerstring;
            writerstring = "eq_mode" + Integer.toString(value) + "\n";
            BufferedWriter writer = new BufferedWriter(new FileWriter("/sys/class/hdmirx/hdmirx0/debug"), 2);
            try {
                writer.write(writerstring);
            } finally {
                writer.close();
            }
            Log.d("test", "/sys/module/tvin_hdmirx/parameters/eq_mode "
                + "/sys/module/tvin_hdmirx/parameters/eq_mode  ok!" + value);
        } catch (IOException e) {
            Log.e("test", "/sys/module/tvin_hdmirx/parameters/eq_mode "
                + "/sys/module/tvin_hdmirx/parameters/eq_mode", e);
        }
    }

    public int GetInternalModeValue() {
        File f1 = new File("/sys/module/tvin_hdmirx/parameters/internal_mode");
        try {
            int adcvaule = 0;
            // FileWriter fw = new FileWriter(f1);
            // BufferedWriter buf = new BufferedWriter(fw);
            FileReader fw = new FileReader(f1);
            BufferedReader buf = new BufferedReader(fw);
            // buf.write(state);
            adcvaule = Integer.parseInt(buf.readLine());
            buf.close();
            Log.d("test", "get /sys/module/tvin_hdmirx/parameters/eq_mode: "
                + adcvaule);
            return adcvaule;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void SetInternalModeValue(int value) {
        try {
            String writerstring;
            writerstring = "internal_mode" + Integer.toString(value) + "\n";
            BufferedWriter writer = new BufferedWriter(new FileWriter("/sys/class/hdmirx/hdmirx0/debug"), 2);
            try {
                writer.write(writerstring);
            } finally {
                writer.close();
            }
            Log.d("test", "/sys/module/tvin_hdmirx/parameters/internal_mode "
                + "/sys/module/tvin_hdmirx/parameters/internal_mode ok");
        } catch (IOException e) {
            Log.e("test", "/sys/module/tvin_hdmirx/parameters/internal_mode "
                + "/sys/module/tvin_hdmirx/parameters/internal_mode", e);
        }
    }

    // ///////////////// factory test interface
    public void OtherSelect(int name, int value) // ///0 0x25
    {
        switch (name) {
            case 0x10:
                if (value == 0x01) {
                    Message msg = mServiceHandler.obtainMessage();
                    msg.arg1 = GlobalConst.UART_GOTO_FACTORY_MENU;
                    SendMsgtoActivty(msg);
                    ;// go to factory mode
                } else if (value == 0) {
                    Message msg = mServiceHandler.obtainMessage();
                    msg.arg1 = GlobalConst.UART_GOOUT_FACTORY_MENU;
                    SendMsgtoActivty(msg);
                    ;// exit factory mode
                }
                break;
            case 0x11:
                if (value == 0x01) {
                    Message msg = mServiceHandler.obtainMessage();
                    msg.arg1 = GlobalConst.UART_GOTO_BURN_MODE;
                    SendMsgtoActivty(msg);
                    ;// go to burn mode
                } else if (value == 0) {
                    Message msg = mServiceHandler.obtainMessage();
                    msg.arg1 = GlobalConst.UART_GOOUT_BURN_MODE;
                    SendMsgtoActivty(msg);
                    ;// exit burn mode
                }
                break;
            case 0x12:
                if (value == 0x01) {
                    eepromJNI.SaveFactorySetting(FactoryFunc.ONEKEY_ONOFF, 1);
                    ;// open single mode
                } else if (value == 0) {
                    eepromJNI.SaveFactorySetting(FactoryFunc.ONEKEY_ONOFF, 0);
                    ;// close single mode
                }
                break;
            case 0x13:
                if (value == 0x01) {
                    Message msg = mServiceHandler.obtainMessage();
                    msg.arg1 = GlobalConst.UART_GOTO_IICBusOff;
                    SendMsgtoActivty(msg);
                } else if (value == 0) {
                    Message msg = mServiceHandler.obtainMessage();
                    msg.arg1 = GlobalConst.UART_GOOUT_IICBusOff;
                    SendMsgtoActivty(msg);
                }
                break;
            case 0x14:
                break;
            case 0x15:
                break;
            case 0x16: // /key 3eh
            {
                Message msg = mServiceHandler.obtainMessage();
                msg.arg1 = GlobalConst.UART_KEY_testdefault;
                SendMsgtoActivty(msg);
            }
                break;
            case 0x17:// /key 3bh
            {
                Message msg = mServiceHandler.obtainMessage();
                msg.arg1 = GlobalConst.UART_KEY_outfactory;
                SendMsgtoActivty(msg);
            }
                break;
            case 0x18:// /2CH
            {
                Message msg = mServiceHandler.obtainMessage();
                msg.arg1 = GlobalConst.UART_KEY_COCOK;
                SendMsgtoActivty(msg);
            }
                break;
            case 0x19:// /2aH
            {
                Message msg = mServiceHandler.obtainMessage();
                msg.arg1 = GlobalConst.UART_KEY_LAN_TEST;
                SendMsgtoActivty(msg);
            }
                break;
            case 0x1a:
                break;
            case 0x1b:
                break;
            case 0x1c:
                if (value == 0x01) {
                    if (vdinJNI.GetCurSource() == eepromJNI.SourceInput.TV.ordinal()) {
                        Message msg = mServiceHandler.obtainMessage();
                        msg.arg1 = GlobalConst.UART_DIRECTION_UP;
                        SendMsgtoActivty(msg);
                    }
                } else if (value == 0x00) {
                    if (vdinJNI.GetCurSource() == eepromJNI.SourceInput.TV.ordinal()) {
                        Message msg = mServiceHandler.obtainMessage();
                        msg.arg1 = GlobalConst.UART_DIRECTION_DOWN;
                        SendMsgtoActivty(msg);
                    }
                }
                break;
            case 0x1d:
                break;
            case 0x1e:
                break;
            case 0x20:
                break;
            case 0x21:
                break;
            case 0x22:
                break;
            case 0x23:
                break;
            case 0x24:
                break;
            case 0x25:
                break;
        }
    }

    public void ChannelSelect(int name, int value)// //0x25 0x38
    {
        switch (name) {
            case 0x30:
                break;
            case 0x31:
                break;
            case 0x32:
                break;
            case 0x33:
                break;
            case 0x34:
                break;
            case 0x35:
                break;
            case 0x36:
                break;
            case 0x37:
                break;
            case 0x38:
                break;

        }

    }

    public void ImageSetting(int name, int value)// /0x38 0x49
    {
        switch (name) {
            case 0x40:
                break;
            case 0x41:
                break;
            case 0x42:
                break;
            case 0x43:
                break;
            case 0x44:
                break;
            case 0x45:
                break;
            case 0x46:
                break;
            case 0x47:
                break;
            case 0x48:
                break;
            case 0x49:
                break;
        }
    }

    public void WhiteBalance(int name, int value)// //0x49 0x6a
    {
        int isAutoCalOk = -1;

        switch (name) {
            case 0x60:
                break;
            case 0x61:
                isAutoCalOk = afeJNI.SetAdcAutoCal(); //
                break;
            case 0x62:
                // if( isAutoCalOk < 0)
                // return 0x00; //fail
                // else
                // return 0x01; //pass
                break;
            case 0x63:
                break;
            case 0x64:
                vppJNI.gRGBogo.r_gain = value;
                vppJNI.SetRGBogo(vppJNI.gRGBogo);
                break;
            case 0x65:
                vppJNI.gRGBogo.g_gain = value;
                vppJNI.SetRGBogo(vppJNI.gRGBogo);
                break;
            case 0x66:
                vppJNI.gRGBogo.b_gain = value;
                vppJNI.SetRGBogo(vppJNI.gRGBogo);
                break;
            case 0x67:
                vppJNI.gRGBogo.r_post_offset = value;
                vppJNI.SetRGBogo(vppJNI.gRGBogo);
                break;
            case 0x68:
                vppJNI.gRGBogo.g_post_offset = value;
                vppJNI.SetRGBogo(vppJNI.gRGBogo);
                break;
            case 0x69:
                vppJNI.gRGBogo.b_post_offset = value;
                vppJNI.SetRGBogo(vppJNI.gRGBogo);
                break;
            case 0x6a:
                eepromJNI.SaveRgbOgo(vppJNI.gRGBogo); // save to eeprom
                break;
        }
    }

    public void VoiceSetting(int name, int value)// / 0x6a 0x7a
    {
        switch (name) {
            case 0x70:
                break;
            case 0x71:
                break;
            case 0x72:
                break;
            case 0x73:
                break;
            case 0x74:
                break;
            case 0x75:
                break;
            case 0x76:
                break;
            case 0x77:
                break;
            case 0x78:
                break;
            case 0x79:
                break;
            case 0x7a:
                break;
        }
    }

    // ///////////////////////
    public void writeBarcode() {
        String varible = "barcode";
        String prefix = SystemProperties.get("ro.ubootenv.varible.prefix");
        SystemProperties.set(prefix + "." + varible, eepromJNI.LoadBarCode());
        Log.d(TAG, "barcode is = " + eepromJNI.LoadBarCode());
    }

    public void WriteMac() {
        int[] buf = new int[6];
        buf = eepromJNI.GetMacAddr();
        String mac = FormatToHexStr(buf[0] & 0xff) + ":"
            + FormatToHexStr(buf[1] & 0xff) + ":"
            + FormatToHexStr(buf[2] & 0xff) + ":"
            + FormatToHexStr(buf[3] & 0xff) + ":"
            + FormatToHexStr(buf[4] & 0xff) + ":"
            + FormatToHexStr(buf[5] & 0xff);

        Log.d(TAG, "mac is = " + String.valueOf(mac));
        String varible = "ethaddr";
        String prefix = SystemProperties.get("ro.ubootenv.varible.prefix");
        SystemProperties.set(prefix + "." + varible, mac);
    }

    public void WriteLVDS() {
        int lvdsstate, lvdsstate2;
        lvdsstate = eepromJNI.GetLvdsState();
        lvdsstate2 = eepromJNI.LoadFactoryOption(FactoryOption.LVDS);
        if (lvdsstate != lvdsstate2) {
            if (lvdsstate2 != 1)
                eepromJNI.SetLvdsState(0); // set VESA
            else
                eepromJNI.SetLvdsState(1); // set JEIDA
        }
    }

    public String FormatToHexStr(int hlbyte) {
        String hex = Integer.toHexString(hlbyte);
        if (hex.length() == 1) {
            hex = '0' + hex;
        }
        return hex.toUpperCase();
    }

    Itvservice.Stub binder = new Itvservice.Stub() {

        public void UartSend(String name, String value) {
            Log.d(TAG, "in UartSend");
            int uiValue = 0;
            if (value != null && !value.equals(""))
                uiValue = Integer.parseInt(value);

            // videoctrl
            vdinJNI.SrcType srcType = vdinJNI.GetSrcType();
            if (name.equals("shortcut_setup_video_brightness_")) { // Brightness
                vppJNI.UiSetUserModeVideoSetting();
                vppJNI.UiSetBrightness(uiValue);
                vppJNI.UiSetPicMode(vppJNI.PictureMode.USER.ordinal(), 0);
            } else if (name.equals("shortcut_setup_video_contrast_")) { // Contrast
                vppJNI.UiSetUserModeVideoSetting();
                vppJNI.UiSetContrast(uiValue);
                vppJNI.UiSetPicMode(vppJNI.PictureMode.USER.ordinal(), 0);
            } else if (name.equals("shortcut_setup_video_color_")) { // Color
                vppJNI.UiSetUserModeVideoSetting();
                vppJNI.UiSetColor(uiValue);
                vppJNI.UiSetPicMode(vppJNI.PictureMode.USER.ordinal(), 0);
            } else if (name.equals("shortcut_setup_video_hue_")) { // Hue
                // vppJNI.UiSetUserModeVideoSetting();
                vppJNI.UiSetHue(uiValue);
                // vppJNI.UiSetPicMode(vppJNI.PictureMode.USER.ordinal(), 0);
            } else if (name.equals("shortcut_setup_video_sharpness_")) { // Sharpness
                vppJNI.UiSetUserModeVideoSetting();
                vppJNI.UiSetSharpness(true, uiValue);
                vppJNI.UiSetPicMode(vppJNI.PictureMode.USER.ordinal(), 0);
            } else if (name.equals("shortcut_setup_video_temperature_std")) { // Color
                                                                              // Temperature--->standard
                vppJNI.UiSetColorTemp(srcType, vppJNI.ColorTemp.STANDARD.ordinal());
            } else if (name.equals("shortcut_setup_video_temperature_warm")) { // Color
                                                                               // Temperature--->warm
                vppJNI.UiSetColorTemp(srcType, vppJNI.ColorTemp.WARM.ordinal());
            } else if (name.equals("shortcut_setup_video_temperature_cold")) { // Color
                                                                               // Temperature--->cold
                vppJNI.UiSetColorTemp(srcType, vppJNI.ColorTemp.COLD.ordinal());
            } else if (name.equals("shortcut_setup_video_dnr_off")) { // denoise--->OFF
                vppJNI.UiSetNoiseRd(srcType, vppJNI.NoiseRd.OFF.ordinal());
                // vppJNI.UiSetPicMode(vppJNI.PictureMode.USER.ordinal(), 0);
            } else if (name.equals("shortcut_setup_video_dnr_weak")) { // denoise--->weak
                vppJNI.UiSetNoiseRd(srcType, vppJNI.NoiseRd.LOW.ordinal());
                // vppJNI.UiSetPicMode(vppJNI.PictureMode.USER.ordinal(), 0);
            } else if (name.equals("shortcut_setup_video_dnr_mid")) { // denoise--->middle
                vppJNI.UiSetNoiseRd(srcType, vppJNI.NoiseRd.MID.ordinal());
                // vppJNI.UiSetPicMode(vppJNI.PictureMode.USER.ordinal(), 0);
            } else if (name.equals("shortcut_setup_video_dnr_strong")) { // denoise--->strong
                vppJNI.UiSetNoiseRd(srcType, vppJNI.NoiseRd.HIGH.ordinal());
                // vppJNI.UiSetPicMode(vppJNI.PictureMode.USER.ordinal(), 0);
            } else if (name.equals("shortcut_setup_video_dnr_auto")) { // denoise--->auto
                vppJNI.UiSetNoiseRd(srcType, vppJNI.NoiseRd.AUTO.ordinal());
                // vppJNI.UiSetPicMode(vppJNI.PictureMode.USER.ordinal(), 0);
            } else if (name.equals("shortcut_setup_video_picture_mode_std")) { // picture
                                                                               // mode--->standard
                vppJNI.UiSetPicMode(vppJNI.PictureMode.STANDARD.ordinal(), 1);
            } else if (name.equals("shortcut_setup_video_picture_mode_vivid")) { // picture
                                                                                 // mode--->vivid
                vppJNI.UiSetPicMode(vppJNI.PictureMode.VIVID.ordinal(), 1);
                // ucp.setParams("PictureMode", "VIVID");
            } else if (name.equals("shortcut_setup_video_picture_mode_soft")) { // picture
                                                                                // mode--->soft
                vppJNI.UiSetPicMode(vppJNI.PictureMode.SOFT.ordinal(), 1);
            } else if (name.equals("shortcut_setup_video_picture_mode_user")) { // picture
                                                                                // mode--->user
                vppJNI.UiSetPicMode(vppJNI.PictureMode.USER.ordinal(), 1);
            } else if (name.equals("shortcut_setup_video_display_mode_169_without_save")) {

                vppJNI.UiSetScrMode(0xff);
            } else if (name.equals("shortcut_setup_video_display_mode_169")) { // display
                                                                               // mode--->16:9
                vppJNI.UiSetScrMode(vppJNI.ScreenMode.MODE169.ordinal());
            } else if (name.equals("shortcut_setup_video_display_mode_43")) { // display
                                                                              // mode--->4:3
                vppJNI.UiSetScrMode(vppJNI.ScreenMode.MODE43.ordinal());
            } else if (name.equals("shortcut_setup_video_display_mode_subtitle")) { // display
                                                                                    // mode--->subtitle
                vppJNI.UiSetScrMode(vppJNI.ScreenMode.CAPTION.ordinal());
            } else if (name.equals("shortcut_setup_video_display_mode_theater")) { // display
                                                                                   // mode--->theater
                vppJNI.UiSetScrMode(vppJNI.ScreenMode.MOVIE.ordinal());
            } else if (name.equals("shortcut_setup_video_display_mode_personal")) {
                vppJNI.UiSetScrMode(vppJNI.ScreenMode.PERSON.ordinal());
            } else if (name.equals("shortcut_setup_video_display_mode_panorama")) {
                vppJNI.UiSetScrMode(vppJNI.ScreenMode.FULL.ordinal());
            }

            // audioctrl
            else if (name.equals("shortcut_common_mute_")) {
                if (AudioCustom.ismCustomVolumeMute()) {
                    AudioCustom.setmCustomVolumeMute(false); // Mute Off
                } else {
                    AudioCustom.setmCustomVolumeMute(true); // Mute On
                }
            } else if (name.equals("shortcut_setup_audio_equalizer_")) {
                AudioCustom.setmCustomVolumeMute(false); // Mute Off
            } else if (name.equals("shortcut_common_vol_")) { // Audio Volume
                Log.d(TAG, "in shortcut_common_vol_");
                Log.d(TAG, String.valueOf(uiValue));
                AudioCustom.SaveCurAudioVolume(uiValue);
                if ((AudioCustom.ismCurAudioVolumeInc() == false)
                    || (AudioCtlJNI.GetAmplifierMute() == 1)) {
                    AudioCustom.SetAudioMainVolume();
                } else {
                    AudioCustom.setmCustomVolumeMute(false); // Mute Off
                }
            } else if (name.equals("shortcut_setup_audio_bass_")) { // Bass
                AudioCustom.SetCurAudioBassVolume(uiValue);
                AudioCustom.setmCustomVolumeMute(false); // Mute Off
            } else if (name.equals("shortcut_setup_audio_treble_")) { // Treble
                AudioCustom.SetCurAudioTrebleVolume(uiValue);
                AudioCustom.setmCustomVolumeMute(false); // Mute Off
            } else if (name.equals("shortcut_setup_audio_mute_panel_")) {// Audio
                                                                         // mute
                                                                         // panel
                vppJNI.UiSetAudioMutePanel(2);
                AudioCustom.setmCustomVolumeMute(false);
            } else if (name.equals("shortcut_setup_audio_balance_")) { // Audio
                                                                       // LR
                                                                       // balance
                AudioCustom.SaveCurAudioBalance(uiValue);
                AudioCustom.setmCustomVolumeMute(false); // Mute Off
            } else if (name.equals("shortcut_setup_audio_srs_on")) { // SRS
                                                                     // Trusurround--->ON
                AudioCustom.SaveCurAudioSrsSurround(1);
                AudioCustom.setmCustomVolumeMute(false); // Mute Off
            } else if (name.equals("shortcut_setup_audio_srs_off")) { // SRS
                                                                      // Trusurround--->OFF
                AudioCustom.SaveCurAudioSrsSurround(0);
                AudioCustom.setmCustomVolumeMute(false); // Mute Off
            } else if (name.equals("shortcut_setup_audio_voice_on")) { // SRS
                                                                       // Dialog
                                                                       // Clarity--->ON
                AudioCustom.SaveCurAudioSrsDialogClarity(1);
                AudioCustom.setmCustomVolumeMute(false); // Mute Off
            } else if (name.equals("shortcut_setup_audio_voice_off")) { // SRS
                                                                        // Dialog
                                                                        // Clarity--->OFF
                AudioCustom.SaveCurAudioSrsDialogClarity(0);
                AudioCustom.setmCustomVolumeMute(false); // Mute Off
            } else if (name.equals("shortcut_setup_audio_increase_bass_on")) { // SRS
                                                                               // TruBass--->ON
                AudioCustom.SaveCurAudioSrsTruBass(1);
                AudioCustom.setmCustomVolumeMute(false); // Mute Off
            } else if (name.equals("shortcut_setup_audio_increase_bass_off")) { // SRS
                                                                                // TruBass--->OFF
                AudioCustom.SaveCurAudioSrsTruBass(0);
                AudioCustom.setmCustomVolumeMute(false); // Mute Off
            } else if (name.equals("shortcut_setup_audio_sound_mode_std")) { // Audio
                                                                             // Mode--->Normal
                AudioCustom.SaveCurAudioSoundMode(GlobalConst.SOUNDMODE_STD);
                AudioCustom.setmCustomVolumeMute(false); // Mute Off
            } else if (name.equals("shortcut_setup_audio_sound_mode_music")) { // Audio
                                                                               // Mode--->Music
                AudioCustom.SaveCurAudioSoundMode(GlobalConst.SOUNDMODE_MUSIC);
                AudioCustom.setmCustomVolumeMute(false); // Mute Off
            } else if (name.equals("shortcut_setup_audio_sound_mode_news")) { // Audio
                                                                              // Mode--->News
                AudioCustom.SaveCurAudioSoundMode(GlobalConst.SOUNDMODE_NEWS);
                AudioCustom.setmCustomVolumeMute(false); // Mute Off
            } else if (name.equals("shortcut_setup_audio_sound_mode_theater")) { // Audio
                                                                                 // Mode--->Theater
                AudioCustom.SaveCurAudioSoundMode(GlobalConst.SOUNDMODE_THEATER);
                AudioCustom.setmCustomVolumeMute(false); // Mute Off
            } else if (name.equals("shortcut_setup_audio_sound_mode_user")) { // Audio
                                                                              // Mode--->User
                AudioCustom.SaveCurAudioSoundMode(GlobalConst.SOUNDMODE_USER);
                AudioCustom.setmCustomVolumeMute(false); // Mute Off
            } else if (name.equals("shortcut_setup_sys_woofer_switch_on")) { // SupperBassSwitch--->ON
                AudioCustom.SaveCurAudioSupperBassSwitch(1);
                if (AudioCtlJNI.GetAmplifierMute() == 1) {
                    AudioCustom.SetAudioSupperBassVolume();
                } else {
                    AudioCustom.setmCustomVolumeMute(false); // Mute Off
                }
            } else if (name.equals("shortcut_setup_sys_woofer_switch_off")) { // SupperBassSwitch--->OFF
                AudioCustom.SaveCurAudioSupperBassSwitch(0);
                if (AudioCtlJNI.GetAmplifierMute() == 1) {
                    AudioCustom.SetAudioSupperBassVolume();
                } else {
                    AudioCustom.setmCustomVolumeMute(false); // Mute Off
                }
            } else if (name.equals("shortcut_setup_sys_woofer_vol_")) { // Audio
                                                                        // SupperBassVolume
                AudioCustom.SaveCurAudioSupperBassVolume(uiValue);
                if (AudioCtlJNI.GetAmplifierMute() == 1) {
                    AudioCustom.SetAudioSupperBassVolume();
                } else {
                    AudioCustom.setmCustomVolumeMute(false); // Mute Off
                }
            } else if (name.equals("shortcut_setup_sys_wall_effects_on")) { // Wall_effects--->ON
                AudioCustom.SaveCurAudioWallEffect(1);
                AudioCustom.setmCustomVolumeMute(false); // Mute Off
            } else if (name.equals("shortcut_setup_sys_wall_effects_off")) { // Wall_effects--->OFF
                AudioCustom.SaveCurAudioWallEffect(0);
                AudioCustom.setmCustomVolumeMute(false); // Mute Off
            }

            // SettingMISC
            else if (name.equals("shortcut_setup_sys_language_en")) {
                eepromJNI.SaveMiscSetting(eepromJNI.MISCFunc.LANGUAGE, 0);
            } else if (name.equals("shortcut_setup_sys_language_zh")) {
                eepromJNI.SaveMiscSetting(eepromJNI.MISCFunc.LANGUAGE, 1);
            } else if (name.equals("shortcut_setup_sys_poweron_source_memory")) {
                eepromJNI.SaveMiscSetting(eepromJNI.MISCFunc.POWERONSRC, 0);
            } else if (name.equals("shortcut_setup_sys_poweron_source_coocaa")) {
                eepromJNI.SaveMiscSetting(eepromJNI.MISCFunc.POWERONSRC, 1);
            } else if (name.equals("shortcut_setup_sys_poweron_source_tv")) {
                eepromJNI.SaveMiscSetting(eepromJNI.MISCFunc.POWERONSRC, 2);
            } else if (name.equals("shortcut_setup_sys_poweron_source_av1")) {
                eepromJNI.SaveMiscSetting(eepromJNI.MISCFunc.POWERONSRC, 3);
            } else if (name.equals("shortcut_setup_sys_poweron_source_yuv1")) {
                eepromJNI.SaveMiscSetting(eepromJNI.MISCFunc.POWERONSRC, 4);
            } else if (name.equals("shortcut_setup_sys_poweron_source_hdmi1")) {
                eepromJNI.SaveMiscSetting(eepromJNI.MISCFunc.POWERONSRC, 5);
            } else if (name.equals("shortcut_setup_sys_poweron_source_hdmi2")) {
                eepromJNI.SaveMiscSetting(eepromJNI.MISCFunc.POWERONSRC, 6);
            } else if (name.equals("shortcut_setup_sys_poweron_source_hdmi3")) {
                eepromJNI.SaveMiscSetting(eepromJNI.MISCFunc.POWERONSRC, 7);
            } else if (name.equals("shortcut_setup_sys_poweron_source_vga")) {
                eepromJNI.SaveMiscSetting(eepromJNI.MISCFunc.POWERONSRC, 8);
            } else if (name.equals("shortcut_setup_sys_recovery_")) {
                eepromJNI.setFacOutDefault();
                setSleepTimerOff();
                int stdVal = eepromJNI.LoadVideoSetting(srcType, eepromJNI.VideoFunc.COLOR_TEMP);
                vppJNI.UiSetColorTemp(srcType, stdVal);
                stdVal = eepromJNI.LoadMiscSetting(eepromJNI.MISCFunc.DREAMPANEL);
                dreampanelJNI.BackLightControlModeSet(stdVal);
            } else if (name.equals("shortcut_setup_sys_sleep_time_off")) {
                setCountDown(true, 0);
                counttime = 0;
                Log.d(TAG, "setCountDown(true,0)");
            } else if (name.equals("shortcut_setup_sys_sleep_time_5")) {
                setCountDown(true, 5);
                counttime = 1;
                Log.d(TAG, "setCountDown(true,5)");
            } else if (name.equals("shortcut_setup_sys_sleep_time_15")) {
                setCountDown(true, 15);
                counttime = 2;
            } else if (name.equals("shortcut_setup_sys_sleep_time_30")) {
                setCountDown(true, 30);
                counttime = 3;
            } else if (name.equals("shortcut_setup_sys_sleep_time_60")) {
                setCountDown(true, 60);
                counttime = 4;
            } else if (name.equals("shortcut_setup_sys_sleep_time_90")) {
                setCountDown(true, 90);
                counttime = 5;
            } else if (name.equals("shortcut_setup_sys_sleep_time_120")) {
                setCountDown(true, 120);
                counttime = 6;
            } else if (name.equals("shortcut_setup_sys_keysound_off")) {
                eepromJNI.SaveMiscSetting(eepromJNI.MISCFunc.KEYSOUND, 0);
            } else if (name.equals("shortcut_setup_sys_keysound_on")) {
                eepromJNI.SaveMiscSetting(eepromJNI.MISCFunc.KEYSOUND, 1);
            } else if (name.equals("shortcut_setup_sys_six_color_off")) { // Color--->OFF
                eepromJNI.SaveMiscSetting(eepromJNI.MISCFunc.COLOR_MANAGEMENT, 0);
                vppJNI.UiSetBaseColor(vppJNI.ColorManageMode.OFF.ordinal());
            } else if (name.equals("shortcut_setup_sys_six_color_opti")) { // Color--->optimize
                eepromJNI.SaveMiscSetting(eepromJNI.MISCFunc.COLOR_MANAGEMENT, 1);
                vppJNI.UiSetBaseColor(vppJNI.ColorManageMode.OPTIMIZE.ordinal());
            } else if (name.equals("shortcut_setup_sys_six_color_enhance")) { // Color--->increase
                eepromJNI.SaveMiscSetting(eepromJNI.MISCFunc.COLOR_MANAGEMENT, 2);
                vppJNI.UiSetBaseColor(vppJNI.ColorManageMode.ENHANCE.ordinal());
            } else if (name.equals("shortcut_setup_sys_six_color_colordemo")) { // Color--->colordemo
                eepromJNI.SaveMiscSetting(eepromJNI.MISCFunc.COLOR_MANAGEMENT, 1);
                vppJNI.UiSetViiColorDemo(vppJNI.ColorDemoMode.ALLON.ordinal());
            } else if (name.equals("shortcut_setup_sys_six_color_splitdemo")) { // Color--->splitdemo
                eepromJNI.SaveMiscSetting(eepromJNI.MISCFunc.COLOR_MANAGEMENT, 1);
                vppJNI.UiSetViiSplitDemo();
            } else if (name.equals("shortcut_setup_sys_back_light_")) {
                eepromJNI.SaveMiscSetting(eepromJNI.MISCFunc.BACKLIGHT, uiValue);
                dreampanelJNI.BackLightManualValueSet(uiValue);
            } else if (name.equals("shortcut_setup_sys_dream_panel_off")) {
                eepromJNI.SaveMiscSetting(eepromJNI.MISCFunc.DREAMPANEL, 0);
                dreampanelJNI.BackLightControlModeSet(0);
                // eepromJNI.SaveMiscSetting(eepromJNI.MISCFunc.BACKLIGHT, 100);
            } else if (name.equals("shortcut_setup_sys_dream_panel_sensor")) {
                eepromJNI.SaveMiscSetting(eepromJNI.MISCFunc.DREAMPANEL, 1);
                dreampanelJNI.BackLightControlModeSet(1);
                eepromJNI.SaveMiscSetting(eepromJNI.MISCFunc.BACKLIGHT, 100);
            } else if (name.equals("shortcut_setup_sys_dream_panel_scene")) {
                eepromJNI.SaveMiscSetting(eepromJNI.MISCFunc.DREAMPANEL, 2);
                dreampanelJNI.BackLightControlModeSet(2);
                eepromJNI.SaveMiscSetting(eepromJNI.MISCFunc.BACKLIGHT, 100);
            } else if (name.equals("shortcut_setup_sys_dream_panel_all")) {
                eepromJNI.SaveMiscSetting(eepromJNI.MISCFunc.DREAMPANEL, 3);
                dreampanelJNI.BackLightControlModeSet(3);
                eepromJNI.SaveMiscSetting(eepromJNI.MISCFunc.BACKLIGHT, 100);
            } else if (name.equals("shortcut_setup_sys_dream_panel_demo")) {
                // eepromJNI.SaveMiscSetting(eepromJNI.MISCFunc.DREAMPANEL, 3);
                dreampanelJNI.BackLightControlModeSet(3);
                eepromJNI.SaveMiscSetting(eepromJNI.MISCFunc.BACKLIGHT, 100);
                dreampanelJNI.setDreampanelDemo(true);
            } else if (name.equals("shortcut_setup_sys_poweron_music_off")) {
                eepromJNI.SaveMiscSetting(eepromJNI.MISCFunc.ONOFFMUSIC, 0);
            } else if (name.equals("shortcut_setup_sys_poweron_music_on")) {
                eepromJNI.SaveMiscSetting(eepromJNI.MISCFunc.ONOFFMUSIC, 1);
            } else if (name.equals("shortcut_setup_sys_filelist_mode_file")) {
                eepromJNI.SaveMiscSetting(eepromJNI.MISCFunc.LISTMODE, 0);
            } else if (name.equals("shortcut_setup_sys_filelist_mode_folder")) {
                eepromJNI.SaveMiscSetting(eepromJNI.MISCFunc.LISTMODE, 1);
            } else if (name.equals("shortcut_common_playmode_normal")) {
                eepromJNI.SaveMiscSetting(eepromJNI.MISCFunc.REPEATMODE, 0);
            } else if (name.equals("shortcut_common_playmode_folder")) {
                eepromJNI.SaveMiscSetting(eepromJNI.MISCFunc.REPEATMODE, 1);
            } else if (name.equals("shortcut_common_playmode_rand")) {
                eepromJNI.SaveMiscSetting(eepromJNI.MISCFunc.REPEATMODE, 2);
            } else if (name.equals("shortcut_picture_switch_time_3s")) {
                eepromJNI.SaveMiscSetting(eepromJNI.MISCFunc.SWITCHMODE, 0);
            } else if (name.equals("shortcut_picture_switch_time_5s")) {
                eepromJNI.SaveMiscSetting(eepromJNI.MISCFunc.SWITCHMODE, 1);
            } else if (name.equals("shortcut_picture_switch_time_10s")) {
                eepromJNI.SaveMiscSetting(eepromJNI.MISCFunc.SWITCHMODE, 2);
            } else if (name.equals("shortcut_picture_switch_time_hand")) {
                eepromJNI.SaveMiscSetting(eepromJNI.MISCFunc.SWITCHMODE, 3);
            } else if (name.equals("shortcut_picture_switch_mode_1")) {
                eepromJNI.SaveMiscSetting(eepromJNI.MISCFunc.PLAYMODE, 0);
            } else if (name.equals("shortcut_picture_switch_mode_2")) {
                eepromJNI.SaveMiscSetting(eepromJNI.MISCFunc.PLAYMODE, 1);
            } else if (name.equals("shortcut_common_source_tv")) { // Channel
                // Select--->TV
                Log.d(TAG, "in TV");
                // vdinJNI.SetCurrent3DMode(vdinJNI.Mode3D.DISABLE.ordinal());
                // vdinJNI.SetCurrent3DStatus(vdinJNI.Mode3D.DISABLE.ordinal());
                if (nocst02board)
                    vdinJNI.SourceSwitch(0, vdinJNI.SrcId.CVBS0.toInt(), 7);
                else
                    vdinJNI.SourceSwitch(0, vdinJNI.SrcId.CVBS0.toInt(), 0);
            } else if (name.equals("shortcut_common_source_av1")) { // Channel
                // Select--->AV1
                Log.d(TAG, "in AV-0");
                // vdinJNI.SetCurrent3DMode(vdinJNI.Mode3D.DISABLE.ordinal());
                // vdinJNI.SetCurrent3DStatus(vdinJNI.Mode3D.DISABLE.ordinal());
                if (nocst02board)
                    vdinJNI.SourceSwitch(0, vdinJNI.SrcId.CVBS1.toInt(), 0);
                else
                    vdinJNI.SourceSwitch(0, vdinJNI.SrcId.CVBS1.toInt(), 5);
            } else if (name.equals("shortcut_common_source_yuv1")) { // Channel
                // Select--->YUV1
                Log.d(TAG, "in COMP-0");
                // vdinJNI.SetCurrent3DMode(vdinJNI.Mode3D.DISABLE.ordinal());
                // vdinJNI.SetCurrent3DStatus(vdinJNI.Mode3D.DISABLE.ordinal());
                if (nocst02board)
                    vdinJNI.SourceSwitch(0, vdinJNI.SrcId.COMP0.toInt(), 1);
                else
                    vdinJNI.SourceSwitch(0, vdinJNI.SrcId.COMP0.toInt(), 3);
            } else if (name.equals("shortcut_common_source_hdmi1")) { // Channel
                // Select--->HDMI1
                Log.d(TAG, "in HDMI-0");
                // vdinJNI.SetCurrent3DMode(vdinJNI.Mode3D.AUTO.ordinal());
                // vdinJNI.SetCurrent3DStatus(vdinJNI.Mode3D.AUTO.ordinal());
                if (nocst02board)
                    vdinJNI.SourceSwitch(0, vdinJNI.SrcId.HDMI0.toInt(), 0);
                else
                    vdinJNI.SourceSwitch(0, vdinJNI.SrcId.HDMI0.toInt(), 0);
            } else if (name.equals("shortcut_common_source_hdmi2")) { // Channel
                // Select--->HDMI2
                Log.d(TAG, "in HDMI-1");
                // vdinJNI.SetCurrent3DMode(vdinJNI.Mode3D.AUTO.ordinal());
                // vdinJNI.SetCurrent3DStatus(vdinJNI.Mode3D.AUTO.ordinal());
                if (nocst02board)
                    vdinJNI.SourceSwitch(0, vdinJNI.SrcId.HDMI1.toInt(), 1);
                else
                    vdinJNI.SourceSwitch(0, vdinJNI.SrcId.HDMI1.toInt(), 4);
            } else if (name.equals("shortcut_common_source_hdmi3")) { // Channel
                // Select--->HDMI3
                Log.d(TAG, "in HDMI-2");
                // vdinJNI.SetCurrent3DMode(vdinJNI.Mode3D.AUTO.ordinal());
                // vdinJNI.SetCurrent3DStatus(vdinJNI.Mode3D.AUTO.ordinal());
                if (nocst02board)
                    vdinJNI.SourceSwitch(0, vdinJNI.SrcId.HDMI2.toInt(), 2);
                else
                    vdinJNI.SourceSwitch(0, vdinJNI.SrcId.HDMI2.toInt(), 6);
            } else if (name.equals("shortcut_common_source_vga")) { // Channel
                // Select--->PC
                Log.d(TAG, "in VGA-0");
                // vdinJNI.SetCurrent3DMode(vdinJNI.Mode3D.DISABLE.ordinal());
                // vdinJNI.SetCurrent3DStatus(vdinJNI.Mode3D.DISABLE.ordinal());
                if (nocst02board)
                    vdinJNI.SourceSwitch(0, vdinJNI.SrcId.VGA0.toInt(), 2);
                else
                    vdinJNI.SourceSwitch(0, vdinJNI.SrcId.VGA0.toInt(), 1);
            } else if (name.equals("shortcut_setup_sys_pc_set_")) {
                // PC Set
            }

            // SettingProgramControl
            else if (name.contains("shortcut_program_no_")) { // program no
                String[] tmp_str = name.split("shortcut_program_no_");
                tunerJNI.changeCurrentChannelToChannel(Integer.parseInt(tmp_str[1]));
            } else if (name.equals("shortcut_program_skip_on")) { // Program
                                                                  // Skip--->ON
                tunerJNI.setChannelJump(true);
            } else if (name.equals("shortcut_program_skip_off")) { // Program
                                                                   // Skip--->OFF
                tunerJNI.setChannelJump(false);
            } else if (name.equals("shortcut_program_video_sys_auto")) { // videosystem--->auto
                if (vdinJNI.GetCurSource() == eepromJNI.SourceInput.TV.ordinal()) {
                    tunerJNI.SetVideoSTD(tunerJNI.VDStd.AUTO);
                    eepromJNI.SaveChannelInfoVideostd(tunerJNI.num, tunerJNI.ChInfo.videostd);
                } else {
                    vdinJNI.SetAvColorSys(GlobalConst.CVBS_COLORSYS_AUTO);
                    afeJNI.SetCVBSStd(vdinJNI.GetAvColorSys());
                    eepromJNI.SaveAvColorSys(vdinJNI.GetAvColorSys());
                }
            } else if (name.equals("shortcut_program_video_sys_pal")) { // videosystem--->PAL
                if (vdinJNI.GetCurSource() == eepromJNI.SourceInput.TV.ordinal()) {
                    tunerJNI.SetVideoSTD(tunerJNI.VDStd.PAL);
                    eepromJNI.SaveChannelInfoVideostd(tunerJNI.num, tunerJNI.ChInfo.videostd);
                } else {
                    vdinJNI.SetAvColorSys(GlobalConst.CVBS_COLORSYS_PAL);
                    afeJNI.SetCVBSStd(vdinJNI.GetAvColorSys());
                    eepromJNI.SaveAvColorSys(vdinJNI.GetAvColorSys());
                }
            } else if (name.equals("shortcut_program_video_sys_ntsc")) { // videosystem--->NTSC
                if (vdinJNI.GetCurSource() == eepromJNI.SourceInput.TV.ordinal()) {
                    tunerJNI.SetVideoSTD(tunerJNI.VDStd.NTSC);
                    eepromJNI.SaveChannelInfoVideostd(tunerJNI.num, tunerJNI.ChInfo.videostd);
                } else {
                    vdinJNI.SetAvColorSys(GlobalConst.CVBS_COLORSYS_NTSC);
                    afeJNI.SetCVBSStd(vdinJNI.GetAvColorSys());
                    eepromJNI.SaveAvColorSys(vdinJNI.GetAvColorSys());
                }
            } else if (name.equals("shortcut_program_sound_sys_dk")) { // soundsystem--->DK
                tunerJNI.SetSoundSTD(tunerJNI.TunerStd.DK);
                AudioCustom.setmCustomVolumeMute(false); // Mute Off
            } else if (name.equals("shortcut_program_sound_sys_i")) { // soundsystem--->I
                tunerJNI.SetSoundSTD(tunerJNI.TunerStd.I);
                AudioCustom.setmCustomVolumeMute(false); // Mute Off
            } else if (name.equals("shortcut_program_sound_sys_bg")) { // soundsystem--->BG
                tunerJNI.SetSoundSTD(tunerJNI.TunerStd.BG);
                AudioCustom.setmCustomVolumeMute(false); // Mute Off
            } else if (name.equals("shortcut_program_sound_sys_m")) { // soundsystem--->M
                tunerJNI.SetSoundSTD(tunerJNI.TunerStd.M);
                AudioCustom.setmCustomVolumeMute(false); // Mute Off
            } else if (name.equals("shortcut_program_sound_sys_auto")) { // soundsystem--->AUTO
                tunerJNI.SetSoundSTD(tunerJNI.TunerStd.AUTO);
                AudioCustom.setmCustomVolumeMute(false); // Mute Off
            } else if (name.equals("shortcut_program_band_vhfl")) { // Band--->VHFL
                tunerJNI.SetFreqRange(0);
            } else if (name.equals("shortcut_program_band_vhfh")) { // Band--->VHFH
                tunerJNI.SetFreqRange(1);
            } else if (name.equals("shortcut_program_band_ufh")) { // Band--->UHF
                tunerJNI.SetFreqRange(2);
            } else if (name.equals("shortcut_program_manual_search_")) { // Manual
                                                                         // search
            } else if (name.equals("shortcut_program_fine_")) { // fine
            } else if (name.equals("shortcut_program_auto_search_")) { // Auto
                                                                       // search
            } else if (name.equals("shortcut_program_edit_")) { // program edit
            } else if (name.equals("shortcut_program_vol_correct_")) { // Volume
                                                                       // correct
                tunerJNI.ChInfo.volcomp = Integer.parseInt(value);
                eepromJNI.SaveChannelInfoVolcomp(tunerJNI.num, tunerJNI.ChInfo.volcomp);
                if (AudioCtlJNI.GetAmplifierMute() == 1) {
                    AudioCustom.SetAudioMainVolume();
                } else {
                    AudioCustom.setmCustomVolumeMute(false); // Mute Off
                }
            }

            // SettingPCSetControl
            else if (name.equals("shortcut_setup_sys_pc_set_auto_")) { // PC
                // Set--->self-correcting
                afeJNI.RunVgaAutoAdjThr();
            } else if (name.equals("shortcut_setup_sys_pc_set_hor_")) { // PC
                // Set--->horizontal
                int val = Integer.parseInt(value);
                afeJNI.UiSetVgaHpos(val);
                // afeJNI.RunAdcCalirationThr(1);
            } else if (name.equals("shortcut_setup_sys_pc_set_ver_")) { // PC
                // Set--->Vertical
                int val = Integer.parseInt(value);
                afeJNI.UiSetVgaVpos(val);
            } else if (name.equals("shortcut_setup_sys_pc_set_freq_")) { // PC
                // Set--->frequency
                int val = Integer.parseInt(value);
                afeJNI.UiSetVgaClock(val);
            } else if (name.equals("shortcut_setup_sys_pc_set_pha_")) { // PC
                // Set--->Phase
                int val = Integer.parseInt(value);
                afeJNI.UiSetVgaPhase(val);
            }

            // SettingSetup3DControl
            else if (name.equals("shortcut_video_3d_mode_off")) {
                vdinJNI.Set3DMode(vdinJNI.Mode3D.DISABLE.ordinal());
            } else if (name.equals("shortcut_video_3d_mode_auto")) {
                vdinJNI.Set3DMode(vdinJNI.Mode3D.AUTO.ordinal());
            } else if (name.equals("shortcut_video_3d_mode_2d3d")) {
                vdinJNI.Set3DMode(vdinJNI.Mode3D.MODE_2D_TO_3D.ordinal());
            } else if (name.equals("shortcut_video_3d_mode_lr")) {
                vdinJNI.Set3DMode(vdinJNI.Mode3D.LR.ordinal());
            } else if (name.equals("shortcut_video_3d_mode_ud")) {
                vdinJNI.Set3DMode(vdinJNI.Mode3D.BT.ordinal());
            } else if (name.equals("shortcut_video_3d_lr_switch_off")) {
                if (vdinJNI.Get3DStatus() == vdinJNI.Mode3D.BT.ordinal())
                    vdinJNI.Set3DMode(vdinJNI.Mode3D.OFF_LR_SWITCH_BT.ordinal());
                else
                    vdinJNI.Set3DMode(vdinJNI.Mode3D.OFF_LR_SWITCH.ordinal());
            } else if (name.equals("shortcut_video_3d_lr_switch_on")) {
                if (vdinJNI.Get3DStatus() == vdinJNI.Mode3D.BT.ordinal())
                    vdinJNI.Set3DMode(vdinJNI.Mode3D.ON_LR_SWITCH_BT.ordinal());
                else
                    vdinJNI.Set3DMode(vdinJNI.Mode3D.ON_LR_SWITCH.ordinal());
            } else if (name.equals("shortcut_video_3d_dof_")) {
                vdinJNI.SetDepthOf2Dto3D(uiValue);
            } else if (name.equals("shortcut_video_3d_3d2d_off")) {
                if (vdinJNI.Get3DStatus() == vdinJNI.Mode3D.BT.ordinal())
                    vdinJNI.Set3DMode(vdinJNI.Mode3D.OFF_3D_TO_2D_BT.ordinal());
                else
                    vdinJNI.Set3DMode(vdinJNI.Mode3D.OFF_3D_TO_2D.ordinal());
            } else if (name.equals("shortcut_video_3d_3d2d_left")) {
                if (vdinJNI.Get3DStatus() == vdinJNI.Mode3D.BT.ordinal())
                    vdinJNI.Set3DMode(vdinJNI.Mode3D.L_3D_TO_2D_BT.ordinal());
                else
                    vdinJNI.Set3DMode(vdinJNI.Mode3D.L_3D_TO_2D.ordinal());
            } else if (name.equals("shortcut_video_3d_3d2d_right")) {
                if (vdinJNI.Get3DStatus() == vdinJNI.Mode3D.BT.ordinal())
                    vdinJNI.Set3DMode(vdinJNI.Mode3D.R_3D_TO_2D_BT.ordinal());
                else
                    vdinJNI.Set3DMode(vdinJNI.Mode3D.R_3D_TO_2D.ordinal());
            }
        }

        public void SaveParam(String name, String value) {

        }

        public String GetParam(String name) {

            return null;
        }

        public int GetTvParam(String MenuItemName) {

            int tmp_val = 0;

            if (MenuItemName.equals("shortcut_setup_video_temperature_")) {
                tmp_val = eepromJNI.LoadVideoSetting(vdinJNI.GetSrcType(), eepromJNI.VideoFunc.COLOR_TEMP);
            } else if (MenuItemName.equals("shortcut_setup_video_dnr_")) {
                tmp_val = eepromJNI.LoadVideoSetting(vdinJNI.GetSrcType(), eepromJNI.VideoFunc.NOISE_RE);
            } else if (MenuItemName.equals("shortcut_setup_video_picture_mode_")) {
                tmp_val = eepromJNI.LoadVideoSetting(vdinJNI.GetSrcType(), eepromJNI.VideoFunc.PICTURE_MODE);
            } else if (MenuItemName.equals("shortcut_setup_video_display_mode_")) {
                tmp_val = eepromJNI.LoadVideoSetting(vdinJNI.GetSrcType(), eepromJNI.VideoFunc.ASPECT_RAT);
            } else if (MenuItemName.equals("shortcut_common_source_")) {
                // tmp_val = vdinJNI.GetCurSource();
                tmp_val = eepromJNI.LoadSourceInput(0);
            } else if (MenuItemName.equals("shortcut_setup_sys_language_")) {
                tmp_val = eepromJNI.LoadMiscSetting(eepromJNI.MISCFunc.LANGUAGE);
            } else if (MenuItemName.equals("shortcut_setup_sys_poweron_source_")) {
                tmp_val = eepromJNI.LoadMiscSetting(eepromJNI.MISCFunc.POWERONSRC);
            } else if (MenuItemName.equals("shortcut_setup_sys_keysound_")) {
                tmp_val = eepromJNI.LoadMiscSetting(eepromJNI.MISCFunc.KEYSOUND);
            } else if (MenuItemName.equals("shortcut_setup_sys_six_color_")) {
                tmp_val = eepromJNI.LoadMiscSetting(eepromJNI.MISCFunc.COLOR_MANAGEMENT);
            } else if (MenuItemName.equals("shortcut_setup_sys_dream_panel_")) {
                tmp_val = eepromJNI.LoadMiscSetting(eepromJNI.MISCFunc.DREAMPANEL);
            } else if (MenuItemName.equals("shortcut_setup_sys_poweron_music_")) {
                tmp_val = eepromJNI.LoadMiscSetting(eepromJNI.MISCFunc.ONOFFMUSIC);
            } else if (MenuItemName.equals("shortcut_setup_sys_filelist_mode_")) {
                tmp_val = eepromJNI.LoadMiscSetting(eepromJNI.MISCFunc.LISTMODE);
            } else if (MenuItemName.equals("shortcut_common_playmode_")) {
                tmp_val = eepromJNI.LoadMiscSetting(eepromJNI.MISCFunc.REPEATMODE);
            } else if (MenuItemName.equals("shortcut_picture_switch_time_")) {
                tmp_val = eepromJNI.LoadMiscSetting(eepromJNI.MISCFunc.SWITCHMODE);
            } else if (MenuItemName.equals("shortcut_picture_switch_mode_")) {
                tmp_val = eepromJNI.LoadMiscSetting(eepromJNI.MISCFunc.PLAYMODE);
            } else if (MenuItemName.equals("shortcut_setup_audio_srs_")) {
                tmp_val = AudioCustom.GetCurAudioSRSSurround();
                Log.d(TAG, "SRS TruSurround = " + tmp_val);
                // tmp_val ^= 1;
            } else if (MenuItemName.equals("shortcut_setup_audio_voice_")) {
                tmp_val = AudioCustom.GetCurAudioSrsDialogClarity();
                Log.d(TAG, "SRS Dialog Clarity = " + tmp_val);
                // tmp_val ^= 1;
            } else if (MenuItemName.equals("shortcut_setup_audio_increase_bass_")) {
                tmp_val = AudioCustom.GetCurAudioSrsTruBass();
                Log.d(TAG, "SRS TurBass = " + tmp_val);
                // tmp_val ^= 1;
            } else if (MenuItemName.equals("shortcut_setup_audio_sound_mode_")) {
                tmp_val = AudioCustom.GetCurAudioSoundMode();
                Log.d(TAG, "Audio Mode = " + tmp_val);
            } else if (MenuItemName.equals("shortcut_setup_sys_woofer_switch_")) {
                tmp_val = AudioCustom.GetCurAudioSupperBassSwitch();
                Log.d(TAG, "SupperBassSwitch = " + tmp_val);
            } else if (MenuItemName.equals("shortcut_setup_sys_wall_effects_")) {
                tmp_val = AudioCustom.GetCurAudioWallEffect();
                Log.d(TAG, "WallEffectSwitch = " + tmp_val);
                // } else if
                // (MenuItemName.equals("shortcut_setup_audio_sound_eq_mode_"))
                // {
                // tmp_val = AudioCustom.GetCurAudioEQMode();
                // Log.d(TAG, "EQ Mode = " + tmp_val);
            } else if (MenuItemName.equals("shortcut_program_skip_")) {
                if (tunerJNI.ChInfo.jump)
                    tmp_val = 1;
                else
                    tmp_val = 0;
            } else if (MenuItemName.equals("shortcut_program_video_sys_")) {
                if (vdinJNI.GetCurSource() == eepromJNI.SourceInput.TV.ordinal())
                    tmp_val = tunerJNI.ChInfo.videostd;
                else
                    tmp_val = vdinJNI.GetAvColorSys();
            } else if (MenuItemName.equals("shortcut_program_sound_sys_")) {
                tmp_val = tunerJNI.ChInfo.soundstd;
            } else if (MenuItemName.equals("shortcut_program_band_")) {
                tmp_val = tunerJNI.getChannelBand(tunerJNI.manuFreq);
            } else if (MenuItemName.equals("shortcut_setup_sys_sleep_time_")) {
                tmp_val = counttime;
                // } else if (MenuItemName.equals("shortcut_video_3d_mode_")) {
                // tmp_val = Get3DStatus();
            } else
                return -1;

            return tmp_val;
        }

        public int GetTvProgressBar(String MenuItemName) {
            int val = 0;
            if (MenuItemName.equals("shortcut_setup_video_brightness_")) {
                val = eepromJNI.LoadVideoSetting(vdinJNI.GetSrcType(), eepromJNI.VideoFunc.BRIGHTNESS);
            } else if (MenuItemName.equals("shortcut_setup_video_contrast_")) {
                val = eepromJNI.LoadVideoSetting(vdinJNI.GetSrcType(), eepromJNI.VideoFunc.CONTRAST);
            } else if (MenuItemName.equals("shortcut_setup_video_color_")) {
                val = eepromJNI.LoadVideoSetting(vdinJNI.GetSrcType(), eepromJNI.VideoFunc.COLOR);
            } else if (MenuItemName.equals("shortcut_setup_video_hue_")) {
                val = eepromJNI.LoadVideoSetting(vdinJNI.GetSrcType(), eepromJNI.VideoFunc.HUE);
            } else if (MenuItemName.equals("shortcut_setup_video_sharpness_")) {
                val = eepromJNI.LoadVideoSetting(vdinJNI.GetSrcType(), eepromJNI.VideoFunc.SHARPNESS);
            } else if (MenuItemName.equals("shortcut_setup_sys_pc_set_hor_")) {
                val = eepromJNI.LoadVgaAdjustment(vdinJNI.GetSigFormat(), eepromJNI.VGAFunc.HPOSITION);
            } else if (MenuItemName.equals("shortcut_setup_sys_pc_set_ver_")) {
                val = eepromJNI.LoadVgaAdjustment(vdinJNI.GetSigFormat(), eepromJNI.VGAFunc.VPOSITION);
            } else if (MenuItemName.equals("shortcut_setup_sys_pc_set_freq_")) {
                val = eepromJNI.LoadVgaAdjustment(vdinJNI.GetSigFormat(), eepromJNI.VGAFunc.CLOCK);
            } else if (MenuItemName.equals("shortcut_setup_sys_pc_set_pha_")) {
                val = eepromJNI.LoadVgaAdjustment(vdinJNI.GetSigFormat(), eepromJNI.VGAFunc.PHASE);
            } else if (MenuItemName.equals("shortcut_program_vol_correct_")) {
                val = tunerJNI.ChInfo.volcomp;
            } else if (MenuItemName.equals("shortcut_common_vol_")) {
                // int tmp_val_buf[] = {0, 0};
                // AudioCustom.GetAmplifierVolume(tmp_val_buf, 0, 100, 0, 100);
                val = AudioCustom.GetCurAudioVolume();
            } else if (MenuItemName.equals("shortcut_setup_audio_bass_")) {
                val = AudioCustom.GetCurAudioBassVolume();
            } else if (MenuItemName.equals("shortcut_setup_audio_treble_")) {
                val = AudioCustom.GetCurAudioTrebleVolume();
            } else if (MenuItemName.equals("shortcut_setup_audio_balance_")) {
                val = AudioCustom.GetCurAudioBalance() + 50; // change from
                                                             // -50---+50 to
                                                             // 0---+100
            } else if (MenuItemName.equals("shortcut_setup_sys_woofer_vol_")) {
                val = AudioCustom.GetCurAudioSupperBassVolume();
            } else if (MenuItemName.equals("shortcut_setup_sys_back_light_")) {
                val = eepromJNI.LoadMiscSetting(eepromJNI.MISCFunc.BACKLIGHT);
            } else
                return -1;

            return val;

        }

        public void SaveCustomEQGain(int item, int val) {
            AudioCustom.SaveCustomEQGain(item, val);
        }

        public void GetCurEQGain(int[] dataBuf) {
            AudioCustom.GetCurEQGain(dataBuf);
        }

        public int GetCurAudioSoundMode() {
            return AudioCustom.GetCurAudioSoundMode();
        }

        public void SaveCurAudioSoundMode(int soundmode) {
            AudioCustom.SaveCurAudioSoundMode(soundmode);
        }

        public void SetCurAudioTrebleVolume(int cur_vol) {
            AudioCustom.SetCurAudioTrebleVolume(cur_vol);
        }

        public void SetCurAudioBassVolume(int cur_vol) {
            AudioCustom.SetCurAudioBassVolume(cur_vol);
        }

        public int GetCurAudioSupperBassSwitch() {
            return AudioCustom.GetCurAudioSupperBassSwitch();
        }

        public int GetCurAudioSRSSurround() {
            return AudioCustom.GetCurAudioSRSSurround();
        }

        public int GetCurAudioVolume() {
            return AudioCustom.GetCurAudioVolume();
        }

        public void SaveCurAudioVolume(int progress) {
            AudioCustom.SaveCurAudioVolume(progress);
        }

        public int num() {
            return tunerJNI.num;
        }

        public void setnum(int num) {
            tunerJNI.num = num;
        }

        public void addorreducenum(int flag) {
            if (flag == 1)
                tunerJNI.num++;
            else
                tunerJNI.num--;
        }

        public void setSearchHandler() {
            tunerJNI.setSearchHandler(mServiceHandler);
        }

        public void setSignalDetectHandler() {
            vdinJNI.setSignalDetectHandler(mServiceHandler);
        }

        public void setVGAMessageHandler() {
            afeJNI.setVGAMessageHandler(mServiceHandler);
        }

        public void setDreampanelHandler() {
            dreampanelJNI.setDreampanelHandler(mServiceHandler);
        }

        public void setDreampanelDemo(boolean flag) {
            dreampanelJNI.setDreampanelDemo(flag);
        }

        public void setTunerLastChannel() {
            tunerJNI.setTunerLastChannel();
        }

        public void setTunerChannel(int num) {
            tunerJNI.setTunerChannel(num);
        }

        public void updateTunerFrequencyUI() {
            tunerJNI.updateTunerFrequencyUI(tunerJNI.manuFreq);
        }

        public boolean getSearchStop() {
            return tunerJNI.getSearchStop();
        }

        public void SearchChanel(boolean f, boolean s, boolean t) {
            tunerJNI.SearchChanel(f, s, t);
        }

        public boolean getSearchDown() {
            return tunerJNI.getSearchDown();
        }

        public void setSearchDown(boolean flag) {
            tunerJNI.setSearchDown(flag);
        }

        public void FintTune(int val) {
            tunerJNI.FintTune(val);
        }

        public boolean getAutoSearchOn() {
            return tunerJNI.getAutoSearchOn();
        }

        public int getMaxFreq() {
            return tunerJNI.getMaxFreq();
        }

        public int getMinFreq() {
            return tunerJNI.getMinFreq();
        }

        public int getMaxVHFHFreq() {
            return tunerJNI.getMaxVHFHFreq();
        }

        public int getMaxVHFLFreq() {
            return tunerJNI.getMaxVHFLFreq();
        }

        public int manuFreq() {
            return tunerJNI.manuFreq;
        }

        public void exchangeChannelInfo(int sourceCh, int targetCh) {
            tunerJNI.exchangeChannelInfo(sourceCh, targetCh);
        }

        public void SaveTvCurrentChannel() {
            eepromJNI.SaveTvCurrentChannel(tunerJNI.num);
        }

        public int getCurrnetChNumber() {
            return tunerJNI.getCurrnetChNumber();
        }

        public boolean ChInfojump() {
            return tunerJNI.ChInfo.jump;
        }

        public boolean ChInfoaft() {
            return tunerJNI.ChInfo.aft;
        }

        public int ChInfovideostd() {
            return tunerJNI.ChInfo.videostd;
        }

        public int ChInfosoundstd() {
            return tunerJNI.ChInfo.soundstd;
        }

        public boolean ismCurAudioVolumeInc() {
            return AudioCustom.ismCurAudioVolumeInc();
        }

        public void SetAudioMainVolume() {
            AudioCustom.SetAudioMainVolume();
        }

        public boolean GetCurSourceTV() {
            if (vdinJNI.GetCurSource() == eepromJNI.SourceInput.TV.ordinal())
                return true;
            else
                return false;
        }

        public boolean GetCurSourceAV1() {
            if (vdinJNI.GetCurSource() == eepromJNI.SourceInput.AV1.ordinal())
                return true;
            else
                return false;
        }

        public boolean GetCurSourceYPBPR1() {
            if (vdinJNI.GetCurSource() == eepromJNI.SourceInput.YPBPR1.ordinal())
                return true;
            else
                return false;
        }

        public boolean GetCurSourceHDMI1() {
            if (vdinJNI.GetCurSource() == eepromJNI.SourceInput.HDMI1.ordinal())
                return true;
            else
                return false;
        }

        public boolean GetCurSourceHDMI2() {
            if (vdinJNI.GetCurSource() == eepromJNI.SourceInput.HDMI2.ordinal())
                return true;
            else
                return false;
        }

        public boolean GetCurSourceHDMI3() {
            if (vdinJNI.GetCurSource() == eepromJNI.SourceInput.HDMI3.ordinal())
                return true;
            else
                return false;
        }

        public boolean GetCurSourceVGA() {
            if (vdinJNI.GetCurSource() == eepromJNI.SourceInput.VGA.ordinal())
                return true;
            else
                return false;
        }

        public int changeTunerChannel(boolean flag) {
            return tunerJNI.changeTunerChannel(flag);
        }

        public void changeCurrentChannelToLastChannel() {
            tunerJNI.changeCurrentChannelToLastChannel();
        }

        public boolean ismCustomVolumeMute() {
            return AudioCustom.ismCustomVolumeMute();
        }

        public void setmCustomVolumeMute(boolean flag) {
            AudioCustom.setmCustomVolumeMute(flag);
        }

        public int GetAmplifierMute() {
            return AudioCtlJNI.GetAmplifierMute();
        }

        public void SetAudioSupperBassVolume() {
            AudioCustom.SetAudioSupperBassVolume();
        }

        public int GetAvColorSys() {
            return vdinJNI.GetAvColorSys();
        }

        public String GetSigFormatName() {
            return vdinJNI.GetSigFormatName();
        }

        public int LoadNumberInputLimit() {
            return eepromJNI.LoadNumberInputLimit();
        }

        public void SaveNumberInputLimit(int flag) {
            eepromJNI.SaveNumberInputLimit(flag);
        }

        public int LoadNavigateFlag() {
            return eepromJNI.LoadNavigateFlag();
        }

        public void SaveNavigateFlag(int flag) {
            eepromJNI.SaveNavigateFlag(flag);
        }

        public void changeCurrentChannelToChannel(int flag) {
            tunerJNI.changeCurrentChannelToChannel(flag);
        }

        public void setViiColorDemo(int demoMode) {
            vppJNI.UiSetViiColorDemo(demoMode);
        }

        public void setBaseColor(int cmMode) {
            vppJNI.UiSetBaseColor(cmMode);
        }

        public int isVgaFmtInHdmi() { // return: 1->vga format -1 ->not vga
                                      // format
            return vdinJNI.isVgaFmtInHdmi();
        }

        public void StartTv() {
            inCloseTv = false;

            osdJNI.OpenOSDModule(0);
            osdJNI.OpenOSDModule(1);
            // vppJNI.InitTvVpp();
            osdJNI.SetOSDColorkey(0, 1, 0); // blending blackground
            tunerJNI.OpenTuner();
            afeJNI.InitAfe();
            vdinJNI.InitTvin(0);
            AudioCustom.AudioCtlInit();
            SetWatchDog(1);
            eepromJNI.SaveSysStatus(SysStatus.NORMAL);
        }

        public boolean CloseTv() {
            boolean ret;
            inCloseTv = true;

            AudioCustom.SetVolumeDigitLUTBuf(AudioCustom.CC_LUT_SEL_MPEG, 0);
            AudioCustom.AudioAmplifierMuteOn();

            ret = vdinJNI.CloseTvin(0); // current usage: always set '0'

            if (tunerJNI.searchflag == true) {
                tunerJNI.setSearchStop(true);
                tunerJNI.DelayMs(100);
            }
            tunerJNI.KillAutoAFCThr();
            tunerJNI.muteTunerAudio();
            tunerJNI.CloseTuner();
            afeJNI.CloseAfe();

            AudioCustom.AudioAmplifierMuteOff();
            AudioCustom.SetAudioLineOutMute(false);
            AudioCustom.AudioCtlUninit();

            // stop video decode
            osdJNI.SetOSDColorkey(0, 0, 0);
            osdJNI.SetOSDColorkey(1, 0, 0);
            osdJNI.SetGBLAlpha(1, 0xff); // no blending mouse icon
            osdJNI.SetGBLAlpha(0, 0xff); // no blending mouse icon

            // vpp
            vppJNI.SetScaleParam(0, 0, (1920 - 1), (1080 - 1));

            vppJNI.UninitTvVpp();
            SetWatchDog(0);
            return ret;
        }

        public void IICBusOn() {
            Log.d(TAG, "FACTORY, IIC bus on.");
            eepromJNI.IICBusOn();
        }

        public void IICBusOff() {
            Log.d(TAG, "FACTORY, IIC bus off.");
            eepromJNI.IICBusOff();
        }

        public void Factory_WriteEepromOneByte(int offset, int value) {
            eepromJNI.WriteEepromOneByte(offset, value);
        }

        public void Factory_WriteEepromNByte(int offset, int len, int[] buf) {
            eepromJNI.WriteEepromNByte(offset, len, buf);
        }

        public int Factory_ReadEepromOneByte(int offset) {
            return eepromJNI.ReadEepromOneByte(offset);
        }

        public int[] Factory_ReadEepromNByte(int offset, int len) {
            return eepromJNI.ReadEepromNByte(offset, len);
        }

        public int LineInSelectChannel(int num) {
            return AudioCtlJNI.LineInSelectChannel(num);
        }

        public void FacSet_RGBogo(String channelSelect, String ganioffsetSel,
            int value) {
            eepromJNI.FacSet_RGBogo(channelSelect, ganioffsetSel, value);
        }

        public int FacGet_RGBogo(String channelSelect, String ganioffsetSel) {
            return eepromJNI.FacGet_RGBogo(channelSelect, ganioffsetSel);
        }

        public int FacSet_AdcAutoCal() {
            return afeJNI.SetAdcAutoCal();
        }

        public int FacGet_AdcAutoCalStatus() {
            return afeJNI.autoCalStatus;
        }

        public int FacGet_AdcAutoCalResult() {
            return afeJNI.isAutoCalOk;
        }

        public void FacSet_BURN_FLAG(boolean turnOn) {
            if (turnOn)
                eepromJNI.SaveFactorySetting(FactoryFunc.BURN_FLAG, GlobalConst.BURN_FLAG_ON);
            else
                eepromJNI.SaveFactorySetting(FactoryFunc.BURN_FLAG, 0);
        }

        public boolean FacGet_BURN_FLAG() {
            return eepromJNI.IsBurnFlagOn();
        }

        public void FacSet_ONEKEY_ONOFF(int value) {
            eepromJNI.SaveFactorySetting(FactoryFunc.ONEKEY_ONOFF, value);
        }

        public int FacGet_ONEKEY_ONOFF() {
            return eepromJNI.IsOneKeyFlagOn();
        }

        public void SetDefaultMacAddr() {
            eepromJNI.SetDefaultMacAddr();
        }

        public int[] GetMacAddr() {
            return eepromJNI.GetMacAddr();
        }

        public void SaveMacAddr() {
            eepromJNI.SaveMacAddr();
        }

        public int GetAdbState() {
            return eepromJNI.GetAdbState();
        }

        public void SetAdbState(int flag) {
            eepromJNI.SetAdbState(flag);
        }

        // /////////////////usbboot
        public int GetUsbbootState() {
            return eepromJNI.GetUsbbootState();
        }

        public void SetUsbbootState(int flag) {
            eepromJNI.SetUsbbootState(flag);
        }

        // /////////////////////LVDS
        public int GetLvdsState() {
            return eepromJNI.GetLvdsState();
        }

        public void SetLvdsState(int flag) {
            eepromJNI.SetLvdsState(flag);
        }

        public String LoadBarCode() {
            return eepromJNI.LoadBarCode();
        }

        // ///////////////////////////////////get factory menu flag
        public int GetfactoryFlag() {
            return eepromJNI.IsFactoryModeOn();

        }

        public void SetfactoryFlag(int flag) {
            eepromJNI.SaveFactorySetting(FactoryFunc.FACTORY_MODE_FLAG, flag);
        }

        // /////////////////////////////////////////////////factory option
        public boolean FacGet_DEF_HDCP_FLAG() {
            return eepromJNI.IsLoadDefaultHdcp();
        }

        public void FacSet_DEF_HDCP_FLAG(boolean loadDef) {
            if (loadDef)
                eepromJNI.SaveFactoryOption(FactoryOption.DEF_HDCP_FLAG, GlobalConst.LOAD_DEF_HDCP);
            else
                eepromJNI.SaveFactoryOption(FactoryOption.DEF_HDCP_FLAG, 0);
        }

        public boolean GetOnLineMusicFlag() {
            return eepromJNI.getOnLineMusicFlag();
        }

        public void SetOnLineMusicFlag(boolean isOn) {
            if (isOn)
                eepromJNI.SaveFactoryOption(FactoryOption.ONLINE_MUSIC_FLAG, 1);
            else
                eepromJNI.SaveFactoryOption(FactoryOption.ONLINE_MUSIC_FLAG, GlobalConst.ONLINE_MUSIC_OFF);
        }

        public boolean GetStartPicFlag() {
            return eepromJNI.getStartPicFlag();
        }

        public void SetStartPicFlag(boolean isOn) {
            if (isOn)
                eepromJNI.SaveFactoryOption(FactoryOption.START_PIC_FALG, GlobalConst.START_PIC_ON);
            else
                eepromJNI.SaveFactoryOption(FactoryOption.START_PIC_FALG, 0);
        }

        public boolean GetStandByModeFlag() {
            return eepromJNI.getStandByModeFlag();
        }

        public void SetStandByModeFlag(boolean isOn) {
            if (isOn) {
                eepromJNI.SaveFactoryOption(FactoryOption.STANDBY_MODE_FLAG, GlobalConst.STANDBY_FAST);
                // Log.d("good","sunny:eep write fast 0x8a");
            } else {
                eepromJNI.SaveFactoryOption(FactoryOption.STANDBY_MODE_FLAG, 0);
                // Log.d("good","sunny:eep write completely 0");
            }
        }

        public void SetViewMode(int mode) {
            vdinJNI.SetViewMode(mode);
        }

        public void UiSetScrMode(int mode) {
            vppJNI.UiSetScrMode(mode);
        }

        // ////////////////////////////////EEPROM INIT
        public int InitEepromData() {
            eepromJNI.SetEEPToDefault();
            return 1;
        }

        // //////////////////////////////////EEPROM DATE
        public String LoadEepromDate() {
            return eepromJNI.LoadEepromDate();
        }

        // ////////////////////////////////// set SSC funtion

        public void SetSSC(int range, int lowrange, int cycle, boolean onoff) {
            switch (range) {
                case 0:
                    vppJNI.SetSSC(vppJNI.SSCRange.RATIO_1, lowrange, cycle, onoff);
                    break;
                case 1:
                    vppJNI.SetSSC(vppJNI.SSCRange.RATIO_1D5, lowrange, cycle, onoff);
                    break;
                case 2:
                    vppJNI.SetSSC(vppJNI.SSCRange.RATIO_2, lowrange, cycle, onoff);
                    break;
                case 3:
                    vppJNI.SetSSC(vppJNI.SSCRange.RATIO_3D5, lowrange, cycle, onoff);
                    break;
                case 4:
                    vppJNI.SetSSC(vppJNI.SSCRange.RATIO_4D5, lowrange, cycle, onoff);
                    break;
                default:
                    vppJNI.SetSSC(vppJNI.SSCRange.RATIO_1, lowrange, cycle, onoff);
                    break;
            }
            int[] buf = new int[4];
            buf[0] = range;
            buf[1] = lowrange;
            buf[2] = cycle;
            if (onoff)
                buf[3] = 1;
            else
                buf[3] = 0;
            eepromJNI.SetSscDate(buf, 4);

        }

        public int[] GetSscDate() {
            return eepromJNI.GetSscDate();
        }

        // //////////////////////////////////select panel
        public void FacSetPanelType(int paneltype) {
            eepromJNI.SetCurPanelType(paneltype);
        }

        public String FacGetPanelType() {
            PanelType pantype = eepromJNI.LoadCurPanelType();

            return pantype.toString();
        }

        // ///////////////////////////////Set stand by
        public void SetStandbyKeyMode(boolean SetSSC) {
            vppJNI.SetStandbyKeyMode(SetSSC);
        }

        // /////////////////////////////
        public void factoryBurnMode(boolean flag) {
            tunerJNI.factoryBurnMode(flag);
            setSleepTimerOff();
        }

        public void UiSetAudioMutePanel(int value) {
            vppJNI.UiSetAudioMutePanel(value);
        }

        public int GetSigTransFormat() {
            return vdinJNI.GetSigTransFormat();
        }

        public int Get3DStatus() {
            return vdinJNI.Get3DStatus();
        }

        public void SetBurnDefault() {
            setdefault = true;
            eepromJNI.setBurnDefault();
            setSupperBassOn();
            setSleepTimerOff();
            vdinJNI.SourceSwitch(0, vdinJNI.SrcId.CVBS0.toInt(), 0);
            AudioCustom.InitLoadAudioCtl();
        }

        public void SetFacOutDefault() {
            setdefault = true;
            eepromJNI.setFacOutDefault();
            int stdVal = eepromJNI.LoadMiscSetting(eepromJNI.MISCFunc.DREAMPANEL);
            dreampanelJNI.BackLightControlModeSet(stdVal);
            setSleepTimerOff();
            vdinJNI.SourceSwitch(0, vdinJNI.SrcId.CVBS0.toInt(), 0);
            AudioCustom.InitLoadAudioCtl();
        }

        public int GetSysStatus() {
            return eepromJNI.GetSysStatus();
        }

        public void SaveSysStandby() {
            eepromJNI.SetSystemAutoSuspending(1);
            eepromJNI.SaveSysStatus(SysStatus.STANDBY);
        }

        public int GetSystemAutoSuspending() {
            return eepromJNI.GetSystemAutoSuspending();
        }

        public void SetSystemAutoSuspending(int num) {
            eepromJNI.SetSystemAutoSuspending(num);
        }

        public void FacSetHdmiEQMode(int value) {
            eepromJNI.SaveFactoryOption(FactoryOption.HDMI_EQ_MODE, value);
        }

        public int FacGetHdmiEQMode() {
            return eepromJNI.LoadFactoryOption(FactoryOption.HDMI_EQ_MODE);
        }

        public void FacSetHdmiInternalMode(int value) {
            eepromJNI.FacSetHdmiInternalMode(value);
        }

        public int FacGetHdmiInternalMode() {
            return eepromJNI.FacGetHdmiInternalMode();
        }

        public boolean isSrcSwtichDone() {
            return vdinJNI.isSrcSwtichDone();
        }

        public void FacSetSerialPortSwitchFlag(boolean flag) {
            eepromJNI.FacSetSerialPortSwitchFlag(flag);
        }

        public boolean FacGetSerialPortSwitchFlag() {
            return eepromJNI.FacGetSerialPortSwitchFlag();
        }

        public void ResumeLastBLValue() {
            vppJNI.ResumeLastBLValue();
        }

        public void setWhiteScreen() {
            vppJNI.SetScreenColor(0, 235, 128, 128);
        }
    };

    public void SetCurrent3DStatus(int mode) {
        switch (mode) {
            case 0:
                vdinJNI.SetCurrent3DStatus(vdinJNI.Mode3D.DISABLE.ordinal());
                break;
            case 1:
                vdinJNI.SetCurrent3DStatus(vdinJNI.Mode3D.AUTO.ordinal());
                break;
            case 2:
                vdinJNI.SetCurrent3DStatus(vdinJNI.Mode3D.MODE_2D_TO_3D.ordinal());
                break;
            case 3:
                vdinJNI.SetCurrent3DStatus(vdinJNI.Mode3D.LR.ordinal());
                break;
            case 4:
                vdinJNI.SetCurrent3DStatus(vdinJNI.Mode3D.BT.ordinal());
                break;
            default:
                break;
        }
    }

    private void setSleepTimerOff() {
        setCountDown(true, 0);
        counttime = 0;
        Log.d(TAG, "setSleepTimerOff");
    }

    private void setSupperBassOn() {
        AudioCustom.SaveCurAudioSupperBassSwitch(1);
    }
    
    public void setCountDown(boolean manual, int timeLeft) {
        if (manual) {
            // Set timer case

            if (timeLeft > 0) {
                if (timeLeft >= 1)
                    SendSleepMsg(1, (timeLeft - 1) * MIN, true);
            } else if (timeLeft == 0)
                SendSleepMsg(1, 0, false);
        } else {
            // No signal case

        }
    }

    public void SetWatchDog(int flag) {
    }

    public void onDestroy() {
        super.onDestroy();
        DreamEnable = false;
        SerialEnable = false;
        Log.v(TAG, "on destroy");
        vdinJNI.Close3DModule();
        eepromJNI.CloseEeprom();
    }

    private Handler mServiceHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if ((msg.arg1 == GlobalConst.MSG_TUNER_UPDATE_FREQUENCY)
                || (msg.arg1 == GlobalConst.MSG_TUNER_FIND_CHANNEL)
                || (msg.arg1 == GlobalConst.MSG_TUNER_AUTOSEARCH_FINISHED)
                || (msg.arg1 == GlobalConst.MSG_TUNER_MANUALSEARCH_FINISHED)
                || (msg.arg1 == GlobalConst.MSG_TUNER_MANUALSEARCH_ABORTED)
                || (msg.arg1 == GlobalConst.MSG_VDINJNI_DISPLAY_CHANNEL_INFO)
                || (msg.arg1 == GlobalConst.MSG_VGA_AUTO_BEGIN)
                || (msg.arg1 == GlobalConst.MSG_VGA_AUTO_DONE)
                || (msg.arg1 == GlobalConst.MSG_SIGNAL_VGA_NO_SIGNAL)
                || (msg.arg1 == GlobalConst.MSG_SIGNAL_NOT_SUPPORT)
                || (msg.arg1 == GlobalConst.MSG_SIGNAL_FORMAT_NTSC)
                || (msg.arg1 == GlobalConst.MSG_SIGNAL_FORMAT_NOT_NTSC)
                || (msg.arg1 == GlobalConst.MSG_NO_SIGNAL_DETECT)
                || (msg.arg1 == GlobalConst.MSG_SIGNAL_STABLE)
                || (msg.arg1 == GlobalConst.MSG_DERAMPANEL_DEMO_UPDATE)
                || (msg.arg1 == GlobalConst.MSG_VDINJNI_DISPLAY_SOURCE_INFO)) {
                SendMsgtoActivty(msg);
            } else if (msg.arg1 == GlobalConst.MSG_CHANGE_CHANNEL) {
                vdinJNI.channelchange = false;
            }
        }
    };

    Intent intent = new Intent(GlobalConst.RECEIVE_TVSERVICE_MSG);

    private void SendMsgtoActivty(Message msg) {
        intent.putExtra("msg", msg);
        sendBroadcast(intent);
    }

    private static final long MIN = 60 * 1000;
    private Timer mSleepTimer = null;
    private TimerTask mSleepTimerTask = null;
    private long countnum = 0;

    public void CreatSleepTimer(final long delayMillis) {
        mSleepTimer = new Timer();
        mSleepTimerTask = new TimerTask() {
            public void run() {
                countnum += 2000;
                if (countnum == delayMillis) {
                    Message message = new Message();
                    message.what = 1;
                    counthandler.sendMessage(message);
                }
            }
        };
        mSleepTimer.schedule(mSleepTimerTask, 0, 2000);
        Log.d(TAG, "CreatSleepTimer, CreatSleepTimer delayMillis is = "
            + delayMillis);
    }

    public void StopSleepTimer() {
        if (mSleepTimerTask != null) {
            while (!mSleepTimerTask.cancel())
                ;
            mSleepTimerTask = null;
        }
        if (mSleepTimer != null) {
            mSleepTimer.cancel();
            mSleepTimer.purge();
            mSleepTimer = null;
        }
        countnum = 0;
        Log.d(TAG, "StopSleepTimer.");
    }

    public void SendSleepMsg(int msgtype, long delayMillis, boolean sendmsg) {
        // if (counthandler.hasMessages(msgtype))
        // counthandler.removeMessages(msgtype);
        StopSleepTimer();
        if (sendmsg) {
            CreatSleepTimer(delayMillis);
            // counthandler.sendEmptyMessageDelayed(msgtype, delayMilli);
            Log.d(TAG, "SendSleepMsg, SystemClock.uptimeMillis() is = "
                + SystemClock.uptimeMillis() / MIN);
            Log.d(TAG, "SendSleepMsg, delayMillis is = " + delayMillis / MIN);
        }
    }

    private Handler counthandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.d(TAG, "counthandler, SystemClock.uptimeMillis() is = "
                + SystemClock.uptimeMillis() / MIN);
            switch (msg.what) {
                case 1:
                    StopSleepTimer();
                    Intent it = new Intent("com.amlogic.android.countdown.countdownDialog");
                    it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    counttime = 0;
                    startActivity(it);
                    break;
            }
        }
    };
}
