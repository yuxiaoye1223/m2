package test.TestMenu;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import test.TestMenu.mtwo.BarcodeView;
import test.TestMenu.mtwo.NonKeyActionView;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsoluteLayout;
import android.view.SurfaceView;

import com.skyworth.MenuDataBase;
import com.skyworth.Listener.MenuCallbackListener;
import com.skyworth.Listener.OtherViewCallbackListener;
import com.skyworth.SkyworthMenu.GlobalConst;
import com.skyworth.SkyworthMenu.Menucontrol;
import com.skyworth.SkyworthMenu.OtherInfoControl;
import com.skyworth.View.ChannelInfoView;
import com.skyworth.View.NumberInputInfoView;
import com.skyworth.View.VolumeInfoView;
import com.skyworth.control.tvsetting;

public class TestMenu extends Activity implements MenuCallbackListener,
    OtherViewCallbackListener, ViewListener {
    private AbsoluteLayout layout;
    private NumberInputInfoView numberInputInfoView = null;
    private ChannelInfoView channelInfoView = null;
    private VolumeInfoView volumeInfoView = null;
    private OtherInfoControl MuteInfoView = null;
    private static boolean numberInputInfoKey = false;
    private static boolean channelInfoKey = false;
    private static boolean volumeInfoKey = false;
    private long lastNumberInputInfoTime = 0;
    private long lastChannelInfoTime = 0;
    private long lastVolumeInfoTime = 0;

    private MView mview = null;
    private NoSignalView nosignalView = null;
    private CountDownDialog cddialog = null;
    private Menucontrol Mcontrol = null;
    private UpdateUIReceiver mReceiver = null;

    private static boolean b_onKey = false;
    private long lastKeyDownTime = 0;
    private static final String TAG = "TestMenu";
    private static final int REQUEST_CODE = 1978;

    private int factoryflag = 0;
    private static int countNum = GlobalConst.TV_CHANNEL_DEFAULT_NUMBER;
    private NonKeyActionView viewInfoText = null;
    private BarcodeView viewBarCode = null;
    private int singleKeySwitch;
    private String str_lan = "";
    private String myMode = null;
    private boolean isCreateDone = false;
//    public static boolean isBurnModeFinish = true;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                // TODO Auto-generated method stub
                isCreateDone = true;
            }
        }, 1700);

        InitWindow();
        setContentView(layout);

        initUpdateUIReceiver();
    }

    private void initUpdateUIReceiver() {
        mReceiver = new UpdateUIReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(GlobalConst.RECEIVE_TVSERVICE_MSG);
        filter.addAction(GlobalConst.RECEIVE_FACOTRY_MSG);
        registerReceiver(mReceiver, filter);
    }

    private class UpdateUIReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(GlobalConst.RECEIVE_TVSERVICE_MSG)) {
                Message msg = intent.getParcelableExtra("msg");
                if (msg.arg1 == GlobalConst.MSG_VDINJNI_DISPLAY_CHANNEL_INFO) {
                    menuHandler.sendEmptyMessage(GlobalConst.DISPLAY_CHANNEL_INFO);
                } else if (msg.arg1 == GlobalConst.MSG_VDINJNI_DISPLAY_SOURCE_INFO) {
                    menuHandler.sendEmptyMessage(GlobalConst.DISPLAY_SOURCE_INFO);
                } else if (msg.arg1 == GlobalConst.MSG_NO_SIGNAL_DETECT
                    || msg.arg1 == GlobalConst.MSG_SIGNAL_NOT_SUPPORT) {

                    if (cddialog == null && channelInfoView == null
                        && numberInputInfoView == null
                        && volumeInfoView == null && !viewBarCode.isShown()
                        && !viewInfoText.isShown() && factoryflag == 0
                        && !tvsetting.FacGet_BURN_FLAG())
                        if (Mcontrol == null
                            || (Mcontrol != null && Mcontrol.getVisibility() == View.INVISIBLE))
                            ShowNoSignalView(msg, getCurSource());
                } else if (msg.arg1 == GlobalConst.MSG_SIGNAL_STABLE) {
                    if (Mcontrol != null) {
                        Mcontrol.handleServiceMessage(msg);
                    }
                    RemoveNoSignalView();
                    TestMenuHandleMessage(msg);
                } else {
                    if (msg.arg1 == GlobalConst.MSG_SIGNAL_VGA_NO_SIGNAL)
                        TestMenuHandleMessage(msg);
                    if (Mcontrol != null) {
                        Mcontrol.handleServiceMessage(msg);
                    }
                }
                switch (msg.arg1) {
                    case GlobalConst.UART_GOTO_FACTORY_MENU:
                        removeAllInfo();
                        if (Mcontrol.getVisibility() == View.VISIBLE) {
                            hideMenucontrol();
                        }
                        factoryflag = 1;
                        // tvsetting.SetfactoryFlag(1);
                        Intent it = new Intent("android.intent.action.FactoryMenuMain");
                        it.putExtra("listpage", 0);
                        startActivityForResult(it, REQUEST_CODE);
                        specialOne = 0;
                        specialTwo = 0;
                        break;
                    case GlobalConst.UART_GOTO_BURN_MODE:
                        if (tvsetting.FacGet_BURN_FLAG() == false) {
                            removeAllInfo();
                            if (Mcontrol.getVisibility() == View.VISIBLE) {
                                hideMenucontrol();
                            }
                            tvsetting.FacSet_BURN_FLAG(true);
                            tvsetting.SetStandbyKeyMode(true);
//                            isBurnModeFinish = false;
                            tvsetting.setWhiteScreen();
                            mview.setVisibility(View.VISIBLE);
                            tvsetting.SetBurnDefault();                            
                            myMode = "TV";
                            Mcontrol = null;
                            initMenucontrol(myMode);
                        }
                        break;
                    case GlobalConst.UART_GOOUT_BURN_MODE:
                        if (tvsetting.FacGet_BURN_FLAG() == false) {
                            mview.setVisibility(View.INVISIBLE);
                            tvsetting.factoryBurnMode(false);
                            tvsetting.SetStandbyKeyMode(false);
                        }
                        break;
                    case GlobalConst.UART_GOTO_IICBusOff:

                        if (viewInfoText.isShown()) {
                            ;
                        } else {
                            viewInfoText = new NonKeyActionView(TestMenu.this);
                            AbsoluteLayout.LayoutParams params = new AbsoluteLayout.LayoutParams(500, 100, 710, 490);
                            viewInfoText.setInfoText(R.string.busoff);
                            layout.addView(viewInfoText, params);
                            viewInfoText.requestFocus();
                            tvsetting.IICBusOn(); // i2c stop
                            break;

                        }
                        break;
                    case GlobalConst.UART_GOOUT_IICBusOff:
                        if (viewInfoText.isShown()) {
                            tvsetting.IICBusOff(); // recovery
                            layout.removeView(viewInfoText);
                            break;
                        }
                        break;
                    case GlobalConst.UART_KEY_testdefault:
                        removeAllInfo();
                        if (Mcontrol.getVisibility() == View.VISIBLE) {
                            hideMenucontrol();
                        }
                        // tvsetting.uartsend("shortcut_setup_sys_sleep_time_off","");
                        tvsetting.SetBurnDefault();
                        myMode = "TV";
                        Mcontrol = null;
                        initMenucontrol(myMode);
                        tvsetting.SaveCurAudioVolume(50);
                        tvsetting.uartsend("shortcut_setup_sys_dream_panel_off", "");
                        if (tvsetting.ismCustomVolumeMute()) {
                            MuteInfoView.setUnMute();
                        }
                        tvsetting.SetUsbbootState(1);

                        break;
                    case GlobalConst.UART_KEY_outfactory:// /3bh

                        mview.setVisibility(View.INVISIBLE);
                        // tvsetting.uartsend("shortcut_setup_sys_sleep_time_off","");
                        tvsetting.factoryBurnMode(false);
                        tvsetting.SetStandbyKeyMode(false);
                        tvsetting.SetFacOutDefault();
                        myMode = "TV";
                        Mcontrol = null;
                        initMenucontrol(myMode);
                        tvsetting.SetUsbbootState(0);
                        tvsetting.FacSet_ONEKEY_ONOFF(0);
                        MenuDataBase mdb = new MenuDataBase(TestMenu.this);
                        mdb.saveTvOtherParam("shortcut_setup_sys_filelist_mode_folder", "SysSetupMenu");
                        str_lan = getResources().getConfiguration().locale.getLanguage();
                        if (str_lan.equals("en")) {
                            String[] mentemp = {
                                "shortcut_setup_sys_language_zh", ""
                            };
                            tvsetting.SettingLanguage(mentemp);
                        }
                        viewInfoText = new NonKeyActionView(TestMenu.this);
                        AbsoluteLayout.LayoutParams params = new AbsoluteLayout.LayoutParams(500, 100, 710, 490);
                        viewInfoText.setInfoText(R.string.OUTSET_OK);
                        layout.addView(viewInfoText, params);
                        viewInfoText.requestFocus();
                        Timer clock = new Timer();
                        clock.schedule(new TimerTask() {

                            public void run() {
                                // TODO Auto-generated method stub
                                menuHandler.sendEmptyMessage(GlobalConst.HIDE_FAC_DIALOG);
                            }
                        }, 1500);
                        if (tvsetting.ismCustomVolumeMute()) {
                            MuteInfoView.setUnMute();
                        }

                        break;
                    case GlobalConst.UART_KEY_COCOK:// /2ch
                        GogoCocok();
                        break;
                    case GlobalConst.UART_KEY_LAN_TEST:// /2ah
                        removeAllInfo();
                        if (Mcontrol.getVisibility() == View.VISIBLE) {
                            hideMenucontrol();
                        }
                        Intent it2 = new Intent("android.intent.action.WireSetup");
                        startActivity(it2);
                        TestMenu.this.finish();
                        break;
                    default:
                        break;

                }
            } else if (action.equals(GlobalConst.RECEIVE_FACOTRY_MSG)) {
                Message msg = intent.getParcelableExtra("msg");
                if (msg.arg1 == GlobalConst.MSG_VDINJNI_DISPLAY_CHANNEL_INFO)
                    menuHandler.sendEmptyMessage(GlobalConst.DISPLAY_CHANNEL_INFO);
            }
        }

    };

    private boolean updateinfoflag = false;

    private void TestMenuHandleMessage(Message msg) {
        if (msg.arg1 == GlobalConst.MSG_SIGNAL_VGA_NO_SIGNAL) {
            if (updateinfoflag == false) {
                updateinfoflag = true;
                if (channelInfoView != null) {
                    removeChannelInfo();
                    initChannelInfo();
                }
                Log.d(TAG, "Update the no signal channel info....");
            }
            return;
        } else if (msg.arg1 == GlobalConst.MSG_SIGNAL_STABLE) {
            updateinfoflag = false;
            return;
        }
    }

    public void onConfigurationChanged(Configuration newConfig) {
        Log.d(TAG, "~~~~~~~~ testmenu UI onConfigurationChanged ~~~~~~~~~~~~");
        Mcontrol.updateAll();
        Mcontrol.menuUIOp.getMGInstance().initAllItem();
        if (channelInfoView != null)
            removeChannelInfo();
        super.onConfigurationChanged(newConfig);
    }

    public void removePowerOffCountDown() {
        // TODO Auto-generated method stub
        RemoveCountDownDialog();
        EarlyPowerOff();
    }

    private void EarlyPowerOff() {
        tvsetting.SaveSysStandby();
        try {
            // echo P > /sys/class/simkey/keyset
            BufferedWriter writer = new BufferedWriter(new FileWriter("/sys/class/simkey/keyset"), 2);
            try {
                writer.write("S");
            } finally {
                writer.close();
            }
            Log.d(TAG, "CountDownDialogView EarlyPowerOff "
                + "/sys/class/simkey/keyset ok");
        } catch (IOException e) {
            Log.e(TAG, "CountDownDialogView EarlyPowerOff "
                + "/sys/class/simkey/keyset", e);
        }
    }

    private void ShowCountDownDialog() {
        // TODO Auto-generated method stub
        if (cddialog == null) {
            cddialog = new CountDownDialog(this, null);
            AbsoluteLayout.LayoutParams paramp = new AbsoluteLayout.LayoutParams(1920, 1080, (1920 - 702) / 2, (1080 - 480) / 2);
            layout.addView(cddialog, paramp);
            cddialog.setViewListener(this);
        }
    }

    private void RemoveCountDownDialog() {
        // TODO Auto-generated method stub
        if (cddialog != null) {
            cddialog.RemoveCountDownDialog();
            layout.removeView(cddialog);
            cddialog = null;
        }
    }

    private void ResetNosignalTime() {
        if (menuHandler.hasMessages(GlobalConst.SHOW_COUNTDOWN))
            menuHandler.removeMessages(GlobalConst.SHOW_COUNTDOWN);
        RemoveCountDownDialog();
    }

    private void ShowNoSignalView(Message msg, int curSource) {
        // TODO Auto-generated method stub
        if (nosignalView == null) {
            nosignalView = new NoSignalView(this, curSource);
            if (msg.arg1 == GlobalConst.MSG_SIGNAL_NOT_SUPPORT) {
                nosignalView.setNosignalFlag(false);
            } else {
                nosignalView.setNosignalFlag(true);
            }

            AbsoluteLayout.LayoutParams paramp = new AbsoluteLayout.LayoutParams(1920, 1080, 0, 0);
            layout.addView(nosignalView, paramp);
            nosignalView.setVisibility(View.VISIBLE);
            animation = true;
            StartAnimation();
            if (msg.arg1 == GlobalConst.MSG_NO_SIGNAL_DETECT)
                menuHandler.sendEmptyMessageDelayed(GlobalConst.SHOW_COUNTDOWN, (5 - 1) * 60 * 1000);
        } else {
            if (msg.arg1 == GlobalConst.MSG_SIGNAL_NOT_SUPPORT) {
                nosignalView.setNosignalFlag(false);
            } else {
                nosignalView.setNosignalFlag(true);
            }
        }
    }

    private void RemoveNoSignalView() {
        // TODO Auto-generated method stub
        if (nosignalView != null) {
            nosignalView.setVisibility(View.INVISIBLE);
            animation = false;
            layout.removeView(nosignalView);
            nosignalView = null;
        }
        ResetNosignalTime();
    }

    private boolean animation = true;

    private void StartAnimation() {
        new Thread(new Runnable() {
            public void run() {
                while (animation) {
                    if (nosignalView != null) {
                        nosignalView.UpdatePosition();
                        nosignalView.postInvalidate();
                    }
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void onStart() {
        Log.d(TAG, "in TvMenu.java::onStart()");

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            myMode = bundle.getString("mode");
        }
        initMenucontrol(myMode);

        if (GlobalConst.DEBUG_MODE_SELECT == GlobalConst.DEBUG_MODE_CONSOLE) {
            if (bundle != null) {
                menuHandler.sendEmptyMessageDelayed(0xff10, 100);
            } else {
                menuHandler.sendEmptyMessageDelayed(0xff11, 100);
            }
        }

        viewInfoText = new NonKeyActionView(this);
        viewBarCode = new BarcodeView(this);
        try {
            String cmd1[] = {
                "/system/bin/sh",
                "-c",
                "echo 1 > /sys/module/adc_keypad/parameters/report_key_enable"
            };
            Runtime.getRuntime().exec(cmd1);
            String cmd2[] = {
                "/system/bin/sh",
                "-c",
                "echo 1 > /sys/module/remote/parameters/report_key_enable"
            };
            Runtime.getRuntime().exec(cmd2);
            String cmd3[] = {
                "/system/bin/sh",
                "-c",
                "echo 1 > /sys/module/key_input/parameters/report_key_enable"
            };
            Runtime.getRuntime().exec(cmd3);
            Log.d(TAG, "Enable key report");
        } catch (Exception e) {
            Log.w(TAG, "Exception echo 0 > /sys/module/remote/parameters/report_key_enable");
        }
        super.onStart();
    }

    protected void onResume() {
        // TODO Auto-generated method stub
        singleKeySwitch = tvsetting.FacGet_ONEKEY_ONOFF(); // save single key
                                                           // state
        IntentFilter formattFilter = new IntentFilter("mutekey_press"); // add
        registerReceiver(formatReceiver, formattFilter); // add

        super.onResume();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            boolean bMmode = data.getExtras().getBoolean("M_mode");
            boolean bInitMenu = data.getExtras().getBoolean("Source_changed");
            boolean bOutsetOk = data.getExtras().getBoolean("outset_ok");
            if (tvsetting.FacGet_BURN_FLAG() == true) {
                // tvsetting.SetfactoryFlag(0);
                if (mview != null) {
                    mview.setVisibility(View.VISIBLE);
                }
            }
            if (bOutsetOk) {
                viewInfoText = new NonKeyActionView(this);
                AbsoluteLayout.LayoutParams params = new AbsoluteLayout.LayoutParams(500, 100, 710, 490);
                viewInfoText.setInfoText(R.string.OUTSET_OK);
                layout.addView(viewInfoText, params);
                viewInfoText.requestFocus();
                Timer clock = new Timer();
                clock.schedule(new TimerTask() {

                    public void run() {
                        // TODO Auto-generated method stub
                        menuHandler.sendEmptyMessage(GlobalConst.HIDE_FAC_DIALOG);
                    }
                }, 1500);
                if (tvsetting.ismCustomVolumeMute()) {
                    MuteInfoView.setUnMute();
                }
            }
            if (bInitMenu) {
                // tvsetting.SetfactoryFlag(0);
                int currentSource = tvsetting.GetTvParam("shortcut_common_source_");
                switch (currentSource) {
                    case 0:
                        myMode = "TV";
                        break;
                    case 1:
                        myMode = "AV1";
                        break;
                    case 2:
                        myMode = "YUV1";
                        break;
                    case 3:
                        myMode = "HDMI1";
                        break;
                    case 4:
                        myMode = "HDMI2";
                        break;
                    case 5:
                        myMode = "HDMI3";
                        break;
                    case 6:
                        myMode = "VGA";
                        break;
                }
                Mcontrol = null;
                initMenucontrol(myMode);
            }
            // factoryflag=tvsetting.GetfactoryFlag();
            factoryflag = 0;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected void onStop() {
        // Settings.System.putInt(getContentResolver(),
        // "inputdev.enable.remoter", 0);

        tvsetting.uartsend("shortcut_video_3d_mode_off", "");
        unregisterReceiver(formatReceiver); // add
        if (menuHandler.hasMessages(GlobalConst.SOURCE_SWITCH))
            menuHandler.removeMessages(GlobalConst.SOURCE_SWITCH);

        // tvsetting.SetfactoryFlag(0);
        factoryflag = 0;
        unregisterReceiver(mReceiver);

        if (GlobalConst.DEBUG_MODE_SELECT == GlobalConst.DEBUG_MODE_CONSOLE) {
            Log.d(TAG, "CloseTv()");
            tvsetting.CloseTv();
        }
        super.onStop();
        killProcess();
    }

    private final BroadcastReceiver formatReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("mutekey_press")) {
                MuteInfoView.ChangeVolumeMuteInfoStatus();

            }
        }
    };

    private int killProcess() {
        // TODO Auto-generated method stub
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
        return 0;
    }

    private void InitWindow() {
        this.getWindow().setFormat(PixelFormat.RGBA_8888);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        layout = (AbsoluteLayout) new AbsoluteLayout(this);
        layout.addView(new SurfaceView(this, null));

        AbsoluteLayout.LayoutParams paramp = new AbsoluteLayout.LayoutParams(1920, 1080, 0, 0);
        mview = new MView(this);
        mview.setVisibility(View.INVISIBLE);
        layout.addView(mview, paramp);
    }

    public int GetAdcCh1Vaule() {
        File f1 = new File("/sys/class/saradc/saradc_ch1");
        try {
            int adcvaule = 0;
            // FileWriter fw = new FileWriter(f1);
            // BufferedWriter buf = new BufferedWriter(fw);
            FileReader fw = new FileReader(f1);
            BufferedReader buf = new BufferedReader(fw);
            // buf.write(state);
            adcvaule = Integer.parseInt(buf.readLine());
            buf.close();
            Log.d(TAG, "GetAdcCh1Vaule, adc ch1: " + adcvaule);
            return adcvaule;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void setstandby() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("/sys/class/simkey/keyset"), 2);
            try {
                writer.write("S");// //KEY_POWER->down
            } finally {
                writer.close();
            }
            Log.e(TAG, "setstandby, sandby " + "ok");
        } catch (IOException e) {
            Log.e(TAG, "setstandby, sandby " + "error ", e);
        }
    }

    public void SetExit3DMode() {
        if (Mcontrol != null) {
            if ((myMode.equals("HDMI1")) || (myMode.equals("HDMI2"))
                || (myMode.equals("HDMI3"))) {
                Mcontrol.setCheckedIDValue("shortcut_video_3d_mode_", 1);
                tvsetting.uartsend("shortcut_video_3d_mode_auto", "");
            } else {
                Mcontrol.setCheckedIDValue("shortcut_video_3d_mode_", 0);
                tvsetting.uartsend("shortcut_video_3d_mode_off", "");
            }
        }
    }

    public void GogoCocok() {

        String SendPath;
        String TotalPath;
        String SongName = "00483.MUS";
        String RootPath = "";
        String ReceiveName;
        Intent intent = new Intent();
        intent.setClassName("com.multak.Karaoke", "com.multak.Karaoke.Karaoke");
        Bundle bundle = new Bundle();
        bundle.putString("KARAOKE_URL", "null");
        RootPath = "/mnt/sda/sda1/";
        TotalPath = RootPath + SongName;
        File test = new File(TotalPath);
        Log.d(TAG, "TotalPath = " + TotalPath);
        if (test.canRead()) {
            SendPath = "11" + RootPath + SongName;
        } else {
            RootPath = "/mnt/sdb/sdb1/";
            TotalPath = RootPath + SongName;
            test = new File(TotalPath);
            if (test.canRead()) {
                SendPath = "11" + RootPath + SongName;
            } else {
                RootPath = "/mnt/sda/sda/";
                TotalPath = RootPath + SongName;
                test = new File(TotalPath);
                Log.d(TAG, "GogoCocok, TotalPath = " + TotalPath);
                if (test.canRead()) {
                    SendPath = "11" + RootPath + SongName;
                } else {
                    RootPath = "/mnt/sdb/sdb/";
                    TotalPath = RootPath + SongName;
                    test = new File(TotalPath);
                    Log.d(TAG, "GogoCocok, TotalPath = " + TotalPath);
                    if (test.canRead()) {
                        SendPath = "11" + RootPath + SongName;
                    } else {
                        viewInfoText = new NonKeyActionView(this);
                        AbsoluteLayout.LayoutParams params = new AbsoluteLayout.LayoutParams(500, 250, 710, 420);
                        viewInfoText.setInfoText(R.string.insert_udisk);
                        viewInfoText.setInfoHeight(250);
                        viewInfoText.setInfoWidth(500);
                        layout.addView(viewInfoText, params);
                        viewInfoText.requestFocus();
                        Timer clock = new Timer();
                        clock.schedule(new TimerTask() {

                            public void run() {
                                menuHandler.sendEmptyMessage(GlobalConst.HIDE_FAC_DIALOG);
                            }
                        }, 3000);
                        return;
                    }
                }

            }
        }
        Log.d(TAG, "GogoCocok, SendPath = " + SendPath);
        bundle.putString("KARAOKE_INFO", SendPath);
        bundle.putString("KARAOKE_STATUS", "FACTORYMODE");
        intent.putExtras(bundle);
        startActivity(intent);
        TestMenu.this.finish();

    }

    int specialOne = 0, specialTwo = 0;
    boolean doexit = false;

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Log.d(TAG, "onKeyDown keyCode is = "+keyCode);

        if (isCreateDone == false) {
            Log.e(TAG, "onKeyDown keyCode is = " + keyCode);
            return true;
        }

        if (keyCode == 113 && tvsetting.FacGet_BURN_FLAG() != true) {
            if (doexit)
                return true;
            if (Mcontrol == null || !Mcontrol.IsConnectSerDone()
                || !tvsetting.isSrcSwtichDone()) {
                Log.e(TAG, "!tvsetting.isSrcSwtichDone " + keyCode);
                return true;
            } else {
                doexit = true;
                Log.e(TAG, "doexit = true " + keyCode);
            }
        }

        // Mcontrol.RemoveSourceSwitchMessage();
        if (keyCode != 100) {
            if (menuHandler.hasMessages(GlobalConst.SOURCE_SELECT))
                menuHandler.removeMessages(GlobalConst.SOURCE_SELECT);
        }

        RemoveNoSignalView();

        if ((Mcontrol != null) && (Mcontrol.isAudioMutePanel() == true)) {
            switch (keyCode) {
                case 114:// KeyEvent.KEYCODE_VOLUME_UP
                case 115:// KeyEvent.KEYCODE_VOLUME_DOWN
                case KeyEvent.KEYCODE_DPAD_LEFT:
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    int volume = tvsetting.GetCurAudioVolume();
                    if ((keyCode == 114)
                        || (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT)) {
                        if (volume < 100)
                            volume++;
                    } else {
                        if (volume > 0)
                            volume--;
                    }
                    tvsetting.SaveCurAudioVolume(volume);
                    tvsetting.setmCustomVolumeMute(false);
                    return true;

                default:
                    Mcontrol.setAudioMutePanel(false);
                    tvsetting.UiSetAudioMutePanel(1);
                    // if (keyCode == 148)
                    return true;
                    // break;
            }
        }

        if (tvsetting.FacGet_BURN_FLAG() == true) {
            int standbykey_flag = 0;
            switch (keyCode) {
                case 112:// //grade display
                    specialOne++;
                case 111:// //del
                    specialTwo++;
                    break;
                case 69:// //stand by key
                    mview.setVisibility(View.INVISIBLE);
                    tvsetting.factoryBurnMode(false);
                    // tvsetting.SetStandbyKeyMode(false);
                    Timer clock2 = new Timer();
                    clock2.schedule(new TimerTask() {

                        public void run() {
                            // TODO Auto-generated method stub
                            tvsetting.SetStandbyKeyMode(false);
                            setstandby();
                            // tvsetting.SetStandbyKeyMode(false);
                        }
                    }, 1000);
                    return true;
                case 140: // m_mode key ///A CLASS KE
                    if (tvsetting.FacGet_ONEKEY_ONOFF() == 1) {
                        mview.setVisibility(View.INVISIBLE);
                        tvsetting.factoryBurnMode(false);
                        tvsetting.SetStandbyKeyMode(false);
                        return true;
                    }

                case 142:// /factoty 3BH go out factory ///A CLASS KEY
                    if (tvsetting.FacGet_ONEKEY_ONOFF() == 1) {
                        mview.setVisibility(View.INVISIBLE);
                        // tvsetting.uartsend("shortcut_setup_sys_sleep_time_off","");
                        tvsetting.factoryBurnMode(false);
                        tvsetting.SetStandbyKeyMode(false);
                        tvsetting.SetFacOutDefault();
                        myMode = "TV";
                        Mcontrol = null;
                        initMenucontrol(myMode);
                        tvsetting.SetUsbbootState(0);
                        tvsetting.FacSet_ONEKEY_ONOFF(0);
                        MenuDataBase mdb = new MenuDataBase(this);
                        mdb.saveTvOtherParam("shortcut_setup_sys_filelist_mode_folder", "SysSetupMenu");
                        str_lan = getResources().getConfiguration().locale.getLanguage();
                        if (str_lan.equals("en")) {
                            String[] mentemp = {
                                "shortcut_setup_sys_language_zh", ""
                            };
                            tvsetting.SettingLanguage(mentemp);
                        }
                        viewInfoText = new NonKeyActionView(this);
                        AbsoluteLayout.LayoutParams params = new AbsoluteLayout.LayoutParams(500, 100, 710, 490);
                        viewInfoText.setInfoText(R.string.OUTSET_OK);
                        layout.addView(viewInfoText, params);
                        viewInfoText.requestFocus();
                        Timer clock = new Timer();
                        clock.schedule(new TimerTask() {

                            public void run() {
                                // TODO Auto-generated method stub
                                menuHandler.sendEmptyMessage(GlobalConst.HIDE_FAC_DIALOG);
                            }
                        }, 1500);
                        if (tvsetting.ismCustomVolumeMute()) {
                            MuteInfoView.setUnMute();
                        }
                        return true;
                    }

                default:
                    specialOne = 0;
                    specialTwo = 0;
                    break;
            }
            if (specialOne == 2 && specialTwo == 4) {
                mview.setVisibility(View.INVISIBLE);
                tvsetting.factoryBurnMode(false);
                tvsetting.SetStandbyKeyMode(false);
                specialOne = 0;
                specialTwo = 0;
            }

            return true;
        }

        if (null == Mcontrol) {
            return super.onKeyDown(keyCode, event);
        }

        if (viewBarCode.isShown()) {
            layout.removeView(viewBarCode);
            return super.onKeyDown(keyCode, event);
        }
        if (Mcontrol.getVisibility() == View.VISIBLE) {
            b_onKey = true;
            // } else if (Mcontrol.getVisibility() == View.INVISIBLE
            // && KeyEvent.KEYCODE_ENTER == keyCode) {
            // return onKeyDown(KeyEvent.KEYCODE_MENU, event);
        }

        switch (keyCode) {
            case 112:// //grade display
                specialOne++;
            case 111:// //del
                specialTwo++;
                break;
            case 114:// KeyEvent.KEYCODE_VOLUME_UP
            case 115:// KeyEvent.KEYCODE_VOLUME_DOWN
            case KeyEvent.KEYCODE_DPAD_LEFT:
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if (volumeInfoView != null) {
                    volumeInfoKey = true;
                    tvsetting.SaveCurAudioVolume(VolumeInfoView.getProgress());
                    if ((tvsetting.ismCurAudioVolumeInc() == false)
                        || (tvsetting.GetAmplifierMute() == 1)) {
                        tvsetting.SetAudioMainVolume();
                    } else {
                        MuteInfoView.setUnMute();
                    }
                } else if (Mcontrol.getVisibility() == View.INVISIBLE) {
                    removeNumberInputInfo();
                    removeChannelInfo();
                    initVolumeInfo();
                }
                break;

            case KeyEvent.KEYCODE_0: // number input
            case KeyEvent.KEYCODE_1:
            case KeyEvent.KEYCODE_2:
            case KeyEvent.KEYCODE_3:
            case KeyEvent.KEYCODE_4:
            case KeyEvent.KEYCODE_5:
            case KeyEvent.KEYCODE_6:
            case KeyEvent.KEYCODE_7:
            case KeyEvent.KEYCODE_8:
            case KeyEvent.KEYCODE_9:
                if (Mcontrol.getVisibility() == View.INVISIBLE) {
                    if (numberInputInfoView != null) {
                        numberInputInfoKey = true;
                    } else {
                        if (tvsetting.GetCurSourceTV()) {
                            removeChannelInfo();
                            removeVolumeInfo();
                            int number = keyCode - 7;
                            initNumberInputInfo(tvsetting.LoadNumberInputLimit(), number);
                            if (NumberInputInfoView.getNumberLimit() == GlobalConst.ONE_TOOGLE_NUMBER) {
                                NumberInputCallback(number);
                            }
                        }
                    }
                }
                break;

            case KeyEvent.KEYCODE_DEL: // TOOGLE KEY -/--/---
                if (Mcontrol.getVisibility() == View.INVISIBLE) {
                    if (numberInputInfoView != null) {
                        numberInputInfoKey = true;
                        tvsetting.SaveNumberInputLimit(NumberInputInfoView.getNumberLimit());
                    } else {
                        if (tvsetting.GetCurSourceTV()) {
                            removeChannelInfo();
                            removeVolumeInfo();
                            initNumberInputInfo(tvsetting.LoadNumberInputLimit(), GlobalConst.DISPLAY_TOOGLE_INFO);
                        }
                    }
                }
                break;

            case KeyEvent.KEYCODE_MUTE:
                // if (Mcontrol.getVisibility() == View.VISIBLE) {
                // hideMenucontrol();
                // }
                if (Mcontrol.getVisibility() == View.INVISIBLE) {
                    if (tvsetting.GetCurAudioVolume() == 0) {
                        int adcch1;
                        adcch1 = GetAdcCh1Vaule();
                        // if key back and vol key push volume= =0 display
                        // barcode
                        if ((adcch1 > 703) && (adcch1 < 823)) {
                            removeVolumeInfo();
                            viewBarCode = new BarcodeView(this);
                            AbsoluteLayout.LayoutParams params = new AbsoluteLayout.LayoutParams(900, 400, 510, 340);
                            layout.addView(viewBarCode, params);
                            viewBarCode.requestFocus();
                            return true;
                        }
                    }
                }
                MuteInfoView.ChangeVolumeMuteInfoStatus();
                break;
            case KeyEvent.KEYCODE_ENTER: {
                int adcch1;
                adcch1 = GetAdcCh1Vaule();
                // //////////////if key back and vol key push volume= =0 display
                // barcode
                if ((adcch1 > 703) && (adcch1 < 823)) {
                    if (tvsetting.GetCurAudioVolume() == 0) {
                        if (Mcontrol.getVisibility() == View.VISIBLE) {
                            hideMenucontrol();
                        }
                        removeVolumeInfo();
                        viewBarCode = new BarcodeView(this);
                        AbsoluteLayout.LayoutParams params = new AbsoluteLayout.LayoutParams(900, 400, 510, 340);
                        layout.addView(viewBarCode, params);
                        viewBarCode.requestFocus();
                        break;
                    }
                }
                break;
            }

            case 135:// KEYCODE_CHANNEL_UP
            case 136:// KEYCODE_CHANNEL_DOWN
            case KeyEvent.KEYCODE_DPAD_UP:
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if (Mcontrol.getVisibility() == View.INVISIBLE) {
                    if (tvsetting.GetCurSourceTV()) {
                        removeAllInfo();
                        if (keyCode == KeyEvent.KEYCODE_DPAD_UP
                            || keyCode == 135) {
                            countNum = tvsetting.changeTunerChannel(GlobalConst.DIRECTION_UP);
                        } else {
                            countNum = tvsetting.changeTunerChannel(GlobalConst.DIRECTION_DOWN);
                        }
                        if (menuHandler.hasMessages(GlobalConst.CH_UP_DOWN))
                            menuHandler.removeMessages(GlobalConst.CH_UP_DOWN);
                        menuHandler.sendEmptyMessage(GlobalConst.CH_UP_DOWN);
                        initChannelInfo();
                    }
                }
                break;

            case 93:// EXCHANGE
                if (Mcontrol.getVisibility() == View.INVISIBLE) {
                    if (tvsetting.GetCurSourceTV()) {
                        removeAllInfo();
                        if (menuHandler.hasMessages(GlobalConst.CH_EXCHANGE))
                            menuHandler.removeMessages(GlobalConst.CH_EXCHANGE);
                        menuHandler.sendEmptyMessageDelayed(GlobalConst.CH_EXCHANGE, 250);
                        initChannelInfo();
                    }
                }
                break;

            case 97:// CHANNEL INFO DISPLAY
            {
                int adcch1;
                adcch1 = GetAdcCh1Vaule();
                // //////////////if key back and vol key push volume= =0 goto
                // factory menu
                if ((adcch1 > 703) && (adcch1 < 823)) {
                    if (tvsetting.GetCurAudioVolume() == 0) {
                        if (Mcontrol.getVisibility() == View.VISIBLE) {
                            hideMenucontrol();
                        }
                        removeVolumeInfo();
                        Intent intent = new Intent("android.intent.action.FactoryMenuMain");
                        intent.putExtra("listpage", 0);
                        startActivityForResult(intent, REQUEST_CODE);
                        factoryflag = 1;
                        // tvsetting.SetfactoryFlag(1);
                        specialOne = 0;
                        specialTwo = 0;
                        break;
                    }
                }
            }
                if (Mcontrol.getVisibility() == View.VISIBLE) {
                    hideMenucontrol();
                    initChannelInfo();
                } else if (channelInfoView != null) {
                    removeChannelInfo();
                } else {
                    removeNumberInputInfo();
                    removeVolumeInfo();
                    initChannelInfo();
                }
                break;

            case 94:// IMAGE_MODE
                clearOtherView();
                Mcontrol.SelectFrameShortCut("shortcut_setup_video_picture_mode_");
                break;

            case 95:// VOICE_MODE
                clearOtherView();
                Mcontrol.SelectFrameShortCut("shortcut_setup_audio_sound_mode_");
                break;

            case 96:// DISP_MODE
                clearOtherView();
                Mcontrol.SelectFrameShortCut("shortcut_setup_video_display_mode_");
                break;

            // case 98:// 3D FORMAT
            case 99:// 3D
                clearOtherView();
                Mcontrol.Select3DModeShortCut("shortcut_video_3d_setup_");
                break;

            case 100:// SOURCE SELECT
                if (menuHandler.hasMessages(GlobalConst.SOURCE_SELECT))
                    break;
                menuHandler.sendEmptyMessageDelayed(GlobalConst.SOURCE_SELECT, 500);
                // if(tvsetting.isSrcSwtichDone()){
                // clearOtherView();
                // Mcontrol.SelectFrameShortCut("shortcut_common_source_");
                // }
                break;

            case KeyEvent.KEYCODE_MENU:
                if (Mcontrol.getVisibility() == View.VISIBLE) {
                    hideMenucontrol();
                } else {
                    clearOtherView();
                    Mcontrol.BackMenuHandle();
                    Mcontrol.setVisibility(View.VISIBLE);
                    Mcontrol.set_focus(true);
                }
                break;

            case 113: // main menu
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                if (true) {
                    if (Mcontrol.getVisibility() == View.VISIBLE)
                        hideMenucontrol();
                } else {
                    Log.d(TAG, "KEYCODE_MAIN::wait for closeTv()...return!!!");
                    return true;
                }
                removeAllInfo();
                onStop();
                break;

            case KeyEvent.KEYCODE_BACK:
                if (numberInputInfoView != null) {
                    removeNumberInputInfo();
                    return true;
                } else if (Mcontrol.getVisibility() == View.VISIBLE) {
                    hideMenucontrol();
                    return true;
                } else if (volumeInfoView != null) {
                    removeVolumeInfo();
                    return true;
                } else {
                    int adcch1;
                    adcch1 = GetAdcCh1Vaule();
                    // if key back and vol key push volume= =0 goto factory menu
                    if ((adcch1 > 703) && (adcch1 < 823)) {
                        if (tvsetting.GetCurAudioVolume() == 0) {
                            if (Mcontrol.getVisibility() == View.VISIBLE) {
                                hideMenucontrol();
                            }
                            removeVolumeInfo();
                            Intent intent = new Intent("android.intent.action.FactoryMenuMain");
                            intent.putExtra("listpage", 0);
                            startActivityForResult(intent, REQUEST_CODE);
                            factoryflag = 1;
                            // tvsetting.SetfactoryFlag(1);
                            specialOne = 0;
                            specialTwo = 0;
                            return true;
                        }
                    } else {

                        if (doexit)
                            return true;
                        if (Mcontrol == null || !Mcontrol.IsConnectSerDone()
                            || !tvsetting.isSrcSwtichDone()) {
                            Log.e(TAG, "!tvsetting.isSrcSwtichDone " + keyCode);
                            return true;
                        } else {
                            doexit = true;
                            Log.e(TAG, "doexit = true " + keyCode);
                        }

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        if (true) {
                            Log.d(TAG, "KEYCODE_BACK::finish closeTv()");
                            if (Mcontrol.getVisibility() == View.VISIBLE)
                                hideMenucontrol();
                        } else {
                            Log.d(TAG, "KEYCODE_BACK::wait for closeTv()...return!!!");
                            return true;
                        }
                        removeAllInfo();
                        onStop();
                        return true;
                    }
                }
                break;

            // ///////////////////////factory mode
            case 145:// /factory 3eH
                removeAllInfo();
                if (Mcontrol.getVisibility() == View.VISIBLE) {
                    hideMenucontrol();
                }
                // tvsetting.uartsend("shortcut_setup_sys_sleep_time_off","");
                tvsetting.SetBurnDefault();
                myMode = "TV";
                Mcontrol = null;
                initMenucontrol(myMode);
                tvsetting.SaveCurAudioVolume(50);
                tvsetting.uartsend("shortcut_setup_sys_dream_panel_off", "");
                if (tvsetting.ismCustomVolumeMute()) {
                    MuteInfoView.setUnMute();
                }
                tvsetting.SetUsbbootState(1);
                break;
            case 144:// /factory 3dH channel add+
            case 143:// /factory 3cH channel -
            {
                removeAllInfo();
                if (Mcontrol.getVisibility() == View.VISIBLE) {
                    hideMenucontrol();
                }
                int currnetch = 0;
                String common_source = "shortcut_common_source_";
                currnetch = tvsetting.GetTvParam(common_source);
                Log.d(TAG, "tv" + currnetch);
                if (keyCode == 144) // ch+
                {
                    if (currnetch++ >= 6)
                        currnetch = 0;
                } else // ch-
                {
                    if (currnetch-- <= 0)
                        currnetch = 6;
                }
                switch (currnetch) {
                    case 0:
                        myMode = "TV";
                        break;
                    case 1:
                        myMode = "AV1";
                        break;
                    case 2:
                        myMode = "YUV1";
                        break;
                    case 3:
                        myMode = "HDMI1";
                        break;
                    case 4:
                        myMode = "HDMI2";
                        break;
                    case 5:
                        myMode = "HDMI3";
                        break;
                    case 6:
                        myMode = "VGA";
                        break;
                }
                menuHandler.sendEmptyMessageDelayed(GlobalConst.SOURCE_SWITCH, 0);
                Mcontrol = null;
                initMenucontrol(myMode);
                break;
            }
            case 138:// /factory 37H goto AV1
            {
                if (!tvsetting.GetCurSourceAV1()) {
                    removeAllInfo();
                    if (Mcontrol.getVisibility() == View.VISIBLE) {
                        hideMenucontrol();
                    }
                    myMode = "AV1";

                    menuHandler.sendEmptyMessageDelayed(GlobalConst.SOURCE_SWITCH, 0);
                    Mcontrol = null;
                    initMenucontrol(myMode);
                }
                break;
            }
            case 134:// /factoty 35H goto AV2
                break;
            case 133:// /factoty 34H goto AV3
                break;
            case 132:// /factoty 33H goto S1
                break;
            case 131:// /factoty 32H goto
            {
                if (!tvsetting.GetCurSourceYPBPR1()) {
                    removeAllInfo();
                    if (Mcontrol.getVisibility() == View.VISIBLE) {
                        hideMenucontrol();
                    }
                    myMode = "YUV1";

                    menuHandler.sendEmptyMessageDelayed(GlobalConst.SOURCE_SWITCH, 0);
                    Mcontrol = null;
                    initMenucontrol(myMode);
                }
                break;
            }
            case 130:// /factoty 31H goto
                break;
            case 129:// /factoty 30H goto VGA
            {
                if (!tvsetting.GetCurSourceVGA()) {
                    removeAllInfo();
                    if (Mcontrol.getVisibility() == View.VISIBLE) {
                        hideMenucontrol();
                    }
                    myMode = "VGA";

                    menuHandler.sendEmptyMessageDelayed(GlobalConst.SOURCE_SWITCH, 0);
                    Mcontrol = null;
                    initMenucontrol(myMode);
                }
                break;
            }
            case 128:// /factoty 2FH goto HDMI1
            {
                if (!tvsetting.GetCurSourceHDMI1()) {
                    removeAllInfo();
                    if (Mcontrol.getVisibility() == View.VISIBLE) {
                        hideMenucontrol();
                    }
                    myMode = "HDMI1";

                    menuHandler.sendEmptyMessageDelayed(GlobalConst.SOURCE_SWITCH, 0);
                    Mcontrol = null;
                    initMenucontrol(myMode);
                }
                break;
            }
            case 127:// /factoty 2EH goto HDMI2
            {
                if (!tvsetting.GetCurSourceHDMI2()) {
                    removeAllInfo();
                    if (Mcontrol.getVisibility() == View.VISIBLE) {
                        hideMenucontrol();
                    }
                    myMode = "HDMI2";

                    menuHandler.sendEmptyMessageDelayed(GlobalConst.SOURCE_SWITCH, 0);
                    Mcontrol = null;
                    initMenucontrol(myMode);
                }
                break;
            }
            case 126:// /factoty 2DH goto HDMI3
            {
                if (!tvsetting.GetCurSourceHDMI3()) {
                    removeAllInfo();
                    if (Mcontrol.getVisibility() == View.VISIBLE) {
                        hideMenucontrol();
                    }
                    myMode = "HDMI3";

                    menuHandler.sendEmptyMessageDelayed(GlobalConst.SOURCE_SWITCH, 0);
                    Mcontrol = null;
                    initMenucontrol(myMode);
                }
                break;
            }
            case 125:// /factoty 2CH goto coco chat
            {
                GogoCocok();
                break;
            }
            case 124:// /factoty 2BH goto Uplayer
            {
                removeAllInfo();
                if (Mcontrol.getVisibility() == View.VISIBLE) {
                    hideMenucontrol();
                }
                Intent intent = new Intent("android.intent.action.VideoPlayer");
                intent.putExtra("factoryFlag", true);
                startActivity(intent);
                this.finish();
                break;
            }
            case 123:// /factoty 2AH goto LAN test
                removeAllInfo();
                if (Mcontrol.getVisibility() == View.VISIBLE) {
                    hideMenucontrol();
                }
                Intent it2 = new Intent("android.intent.action.WireSetup");
                startActivity(it2);
                this.finish();
                break;
            case 122:// /factoty 29H goto dream panel
                removeAllInfo();
                if (Mcontrol.getVisibility() == View.VISIBLE) {
                    hideMenucontrol();
                }
                String dreamPanel = "shortcut_setup_sys_dream_panel_";
                int valueDream = tvsetting.GetTvParam(dreamPanel);
                if (valueDream != 0) // ecept off
                {
                    dreamPanel = "shortcut_setup_sys_dream_panel_off";
                } else {
                    dreamPanel = "shortcut_setup_sys_dream_panel_sensor";
                }
                tvsetting.uartsend(dreamPanel, "");
                clearOtherView();
                Mcontrol.SelectFrameShortCut("shortcut_setup_sys_dream_panel_");
                break;
            case 148:// /factoty 24H goto
                removeAllInfo();
                if (Mcontrol.getVisibility() == View.VISIBLE) {
                    hideMenucontrol();
                }
                Mcontrol.setAudioMutePanel(true);
                tvsetting.UiSetAudioMutePanel(2);
                if (tvsetting.ismCustomVolumeMute())
                    MuteInfoView.setUnMute();
                break;
            case 120:// /factoty 22H goto display MAC...
            {
                removeAllInfo();
                if (Mcontrol.getVisibility() == View.VISIBLE) {
                    hideMenucontrol();
                }
                viewBarCode = new BarcodeView(this);
                AbsoluteLayout.LayoutParams params = new AbsoluteLayout.LayoutParams(900, 400, 510, 340);
                layout.addView(viewBarCode, params);
                viewBarCode.requestFocus();

            }
                break;
            case 119:// /factoty 21H goto seach up
            case 118:// /factoty 20H goto seach down
                if (tvsetting.GetCurSourceTV()) {
                    removeAllInfo();
                    if (Mcontrol.getVisibility() == View.INVISIBLE) {
                        Mcontrol.setVisibility(View.VISIBLE);
                        Mcontrol.set_focus(true);
                    }
                    if (keyCode == 119)
                        Mcontrol.ShowTvManualSearchDialog(GlobalConst.DIRECTION_DOWN);
                    else
                        Mcontrol.ShowTvManualSearchDialog(GlobalConst.DIRECTION_UP);
                }
                break;

            // //////////////////////goto factory mode
            case 146:// /factoty 3FH goto factory mode ///A CLASS KEY
                if (tvsetting.FacGet_ONEKEY_ONOFF() == 0)
                    break;
                removeAllInfo();
                if (Mcontrol.getVisibility() == View.VISIBLE) {
                    hideMenucontrol();
                }
                factoryflag = 1;
                // tvsetting.SetfactoryFlag(1);
                Intent it = new Intent("android.intent.action.FactoryMenuMain");
                it.putExtra("listpage", 0);
                startActivityForResult(it, REQUEST_CODE);
                specialOne = 0;
                specialTwo = 0;
                break;
            case 142:// /factoty 3BH go out factory ///A CLASS KEY
            {
                if (tvsetting.FacGet_ONEKEY_ONOFF() == 0)
                    break;
                if (tvsetting.FacGet_BURN_FLAG() == true) {
                    mview.setVisibility(View.INVISIBLE);
                    tvsetting.factoryBurnMode(false);
                    tvsetting.SetStandbyKeyMode(false);
                }
                tvsetting.FacSet_BURN_FLAG(false);
                removeAllInfo();
                if (Mcontrol.getVisibility() == View.VISIBLE) {
                    hideMenucontrol();
                }
                // tvsetting.uartsend("shortcut_setup_sys_sleep_time_off","");
                tvsetting.SetFacOutDefault();
                myMode = "TV";
                Mcontrol = null;
                initMenucontrol(myMode);
                tvsetting.SetUsbbootState(0);
                tvsetting.FacSet_ONEKEY_ONOFF(0);
                MenuDataBase mdb = new MenuDataBase(this);
                mdb.saveTvOtherParam("shortcut_setup_sys_filelist_mode_folder", "SysSetupMenu");
                str_lan = getResources().getConfiguration().locale.getLanguage();
                if (str_lan.equals("en")) {
                    String[] mentemp = {
                        "shortcut_setup_sys_language_zh", ""
                    };
                    tvsetting.SettingLanguage(mentemp);
                }
                viewInfoText = new NonKeyActionView(this);
                AbsoluteLayout.LayoutParams params = new AbsoluteLayout.LayoutParams(500, 100, 710, 490);
                viewInfoText.setInfoText(R.string.OUTSET_OK);
                layout.addView(viewInfoText, params);
                viewInfoText.requestFocus();
                Timer clock = new Timer();
                clock.schedule(new TimerTask() {

                    public void run() {
                        // TODO Auto-generated method stub
                        menuHandler.sendEmptyMessage(GlobalConst.HIDE_FAC_DIALOG);
                        // updateMenuUI();

                    }
                }, 1500);
                if (tvsetting.ismCustomVolumeMute()) {
                    MuteInfoView.setUnMute();
                }
                break;
            }
            case 141:// /factoty 3AH IIC BUS ///A CLASS KEY
                if (singleKeySwitch == 0)
                    break;
                removeAllInfo();
                if (Mcontrol.getVisibility() == View.VISIBLE) {
                    hideMenucontrol();
                }
                if (viewInfoText.isShown()) {
                    tvsetting.IICBusOff(); // recovery
                    layout.removeView(viewInfoText);
                    break;
                } else {
                    viewInfoText = new NonKeyActionView(this);
                    AbsoluteLayout.LayoutParams params = new AbsoluteLayout.LayoutParams(500, 100, 710, 490);
                    viewInfoText.setInfoText(R.string.busoff);
                    layout.addView(viewInfoText, params);
                    viewInfoText.requestFocus();
                    tvsetting.IICBusOn(); // i2c stop
                    break;
                }

            case 140:// /factoty 39H burn mode ///A CLASS KEY
                if (tvsetting.FacGet_ONEKEY_ONOFF() == 0)
                    break;
                if (tvsetting.FacGet_BURN_FLAG() == false) {
                    removeAllInfo();
                    if (Mcontrol.getVisibility() == View.VISIBLE) {
                        hideMenucontrol();
                    }
                    tvsetting.FacSet_BURN_FLAG(true);
                    tvsetting.SetStandbyKeyMode(true);
//                    isBurnModeFinish = false;
                    tvsetting.setWhiteScreen();
                    mview.setVisibility(View.VISIBLE);
                    tvsetting.SetBurnDefault();                   
                    myMode = "TV";
                    Mcontrol = null;
                    initMenucontrol(myMode);
                }
                break;
        }
        if (specialOne == 2 && specialTwo == 4) {
            removeAllInfo();
            if (Mcontrol.getVisibility() == View.VISIBLE) {
                hideMenucontrol();
            }
            Intent intent = new Intent("android.intent.action.FactoryMenuMain");
            intent.putExtra("listpage", 0);
            startActivityForResult(intent, REQUEST_CODE);
            factoryflag = 1;
            // tvsetting.SetfactoryFlag(1);
            specialOne = 0;
            specialTwo = 0;
        }

        return super.onKeyDown(keyCode, event);
    }

    public void waitToHideMenu() {
        if (0 != lastKeyDownTime)
            return;

        lastKeyDownTime = new Date().getTime();
        Thread t = new Thread() {
            public void run() {
                for (int i = 0; i < GlobalConst.MENU_DISAPPEAR_TIME; i++) {
                    try {
                        Thread.sleep(1000);
                        // Log.d(TAG, "waitToHideMenu sleep 1000!");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // Log.d(TAG, "b_onKey: " + b_onKey);
                    if (b_onKey
                        || ((Mcontrol != null) && (Mcontrol.isHideMenu() == false))) {
                        // lastKeyDownTime = new Date().getTime();
                        i = 0;
                        b_onKey = false;
                    }
                }

                // long interval = new Date().getTime() - lastKeyDownTime;
                // if ( interval >= GlobalConst.MENU_DISAPPEAR_TIME*1000 )
                {
                    lastKeyDownTime = 0;
                    menuHandler.sendEmptyMessage(GlobalConst.HIDE_MENU);
                }
            }
        };
        t.start();
    }

    private Handler menuHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GlobalConst.HIDE_MENU:
                    if ((Mcontrol != null)
                        && (Mcontrol.getVisibility() == View.VISIBLE)) {
                        hideMenucontrol();
                    }
                    break;
                case GlobalConst.REMOVE_NUMBER_INPUT_INFO:
                    if (numberInputInfoView != null)
                        NumberInputCallback(NumberInputInfoView.getInputNumber());
                    break;
                case GlobalConst.REMOVE_CHANNEL_INFO:
                    removeChannelInfo();
                    break;
                case GlobalConst.REMOVE_VOLUME_INFO:
                    removeVolumeInfo();
                    break;
                case GlobalConst.DISPLAY_CHANNEL_INFO:
                    if ((Mcontrol != null) && !tvsetting.FacGet_BURN_FLAG()
                        && (Mcontrol.getVisibility() == View.INVISIBLE)) {
                        removeAllInfo();
                        initChannelInfo();
                    }
                    break;
                case GlobalConst.DISPLAY_SOURCE_INFO:
                    if ((Mcontrol != null) && !tvsetting.FacGet_BURN_FLAG()
                        && (Mcontrol.getVisibility() == View.INVISIBLE)) {
                        removeAllInfo();
                        initChannelInfo(true);
                    }
                    break;
                case GlobalConst.LOAD_NAVIGATE_FLAG:
                    if (tvsetting.LoadNavigateFlag() == 1
                        && !tvsetting.FacGet_BURN_FLAG()) {
                        if (Mcontrol != null) {
                            Mcontrol.setVisibility(View.VISIBLE);
                            Mcontrol.set_focus(true);
                            Mcontrol.ShowTvSearchNavigateDialog();
                        }
                    }
                    break;
                case GlobalConst.SOURCE_SWITCH:
                    if (myMode.equals("TV")) {
                        Log.d(TAG, "in TV");
                        tvsetting.uartsend("shortcut_common_source_tv", "");
                    } else if (myMode.equals("AV1")) {
                        Log.d(TAG, "in AV1");
                        tvsetting.uartsend("shortcut_common_source_av1", "");
                    } else if (myMode.equals("YUV1")) {
                        Log.d(TAG, "in YUV1");
                        tvsetting.uartsend("shortcut_common_source_yuv1", "");
                    } else if (myMode.equals("HDMI1")) {
                        Log.d(TAG, "in HDMI1");
                        tvsetting.uartsend("shortcut_common_source_hdmi1", "");
                    } else if (myMode.equals("HDMI2")) {
                        Log.d(TAG, "in HDMI2");
                        tvsetting.uartsend("shortcut_common_source_hdmi2", "");
                    } else if (myMode.equals("HDMI3")) {
                        Log.d(TAG, "in HDMI3");
                        tvsetting.uartsend("shortcut_common_source_hdmi3", "");
                    } else if (myMode.equals("VGA")) {
                        Log.d(TAG, "in VGA-0");
                        tvsetting.uartsend("shortcut_common_source_vga", "");
                    } else {
                        Log.d(TAG, "default in TV");
                        tvsetting.uartsend("shortcut_common_source_tv", "");
                    }
                    tvsetting.setSignalDetectHandler();
                    break;
                case GlobalConst.START_TV:
                    tvsetting.StartTv();
                    break;
                case GlobalConst.GET_FACTORY_FLAG:
                    // factoryflag = tvsetting.GetfactoryFlag();
                    singleKeySwitch = tvsetting.FacGet_ONEKEY_ONOFF();
                    break;
                case GlobalConst.GET_BURN_FLAG:
                    if (mview != null) {
                        if (tvsetting.FacGet_BURN_FLAG()) {
                            mview.setVisibility(View.VISIBLE);
                            tvsetting.SetStandbyKeyMode(true);
                        } else
                            mview.setVisibility(View.INVISIBLE);
                    }
                    break;
                case GlobalConst.SHOW_COUNTDOWN:
                    RemoveNoSignalView();
                    ShowCountDownDialog();
                    break;
                case GlobalConst.MUTEINFO:
                    if (MuteInfoView != null) {
                        if (tvsetting.ismCustomVolumeMute())
                            MuteInfoView.setVisibile();
                        else
                            MuteInfoView.setInVisibile();
                    }
                    break;
                case GlobalConst.HIDE_FAC_DIALOG:
                    layout.removeView(viewInfoText);
                    break;
                case GlobalConst.CH_UP_DOWN:
                    if (menuHandler.hasMessages(GlobalConst.CH_UP_DOWN))
                        menuHandler.removeMessages(GlobalConst.CH_UP_DOWN);
                    removeChannelInfo();
                    tvsetting.changeCurrentChannelToChannel(countNum);
                    initChannelInfo();
                    break;
                case GlobalConst.CH_EXCHANGE:
                    if (menuHandler.hasMessages(GlobalConst.CH_EXCHANGE))
                        menuHandler.removeMessages(GlobalConst.CH_EXCHANGE);
                    removeChannelInfo();
                    tvsetting.changeCurrentChannelToLastChannel();
                    tvsetting.setTunerChannel(tvsetting.num());
                    tvsetting.SaveTvCurrentChannel();
                    initChannelInfo();
                    break;
                case GlobalConst.CH_NUMBER:
                    if (countNum != GlobalConst.DISPLAY_TOOGLE_INFO) {
                        if (tvsetting.GetCurSourceTV()) {
                            tvsetting.changeCurrentChannelToChannel(countNum);
                            removeNumberInputInfo();
                            initChannelInfo();
                        }
                    } else {
                        removeNumberInputInfo();
                    }
                    break;
                case GlobalConst.SOURCE_SELECT:
                    if (menuHandler.hasMessages(GlobalConst.SOURCE_SELECT))
                        menuHandler.removeMessages(GlobalConst.SOURCE_SELECT);
                    clearOtherView();
                    Mcontrol.SelectFrameShortCut("shortcut_common_source_");
                    break;
                case 0xff10:
                case 0xff11:
                    if (Mcontrol.IsConnectSerDone()) {
                        this.sendEmptyMessageDelayed(GlobalConst.GET_FACTORY_FLAG, 10);
                        this.sendEmptyMessageDelayed(GlobalConst.GET_BURN_FLAG, 20);
                        this.sendEmptyMessageDelayed(GlobalConst.START_TV, 40);
                        if (msg.what == 0xff10)
                            this.sendEmptyMessageDelayed(GlobalConst.SOURCE_SWITCH, 80);
                    } else {
                        this.sendEmptyMessageDelayed(msg.what, 100);
                    }
                    break;
                default:
                    break;

            }
        }
    };

    private void initMenucontrol(String entrance) {
        if (Mcontrol == null) {
            if (entrance != null) {
                if (entrance.equals("TV"))
                    Mcontrol = new Menucontrol(this, null, GlobalConst.ENTRANCE_TYPE_ANALOG_TV, null);
                else if (entrance.equals("AV1"))
                    Mcontrol = new Menucontrol(this, null, GlobalConst.ENTRANCE_TYPE_AV, null);
                else if (entrance.equals("YUV1"))
                    Mcontrol = new Menucontrol(this, null, GlobalConst.ENTRANCE_TYPE_YUV, null);
                else if ((entrance.equals("HDMI1"))
                    || (entrance.equals("HDMI2")) || (entrance.equals("HDMI3")))
                    Mcontrol = new Menucontrol(this, null, GlobalConst.ENTRANCE_TYPE_HDMI, null);
                else if (entrance.equals("VGA"))
                    Mcontrol = new Menucontrol(this, null, GlobalConst.ENTRANCE_TYPE_VGA, null);
                else {
                    entrance = "TV";
                    Mcontrol = new Menucontrol(this, null, GlobalConst.ENTRANCE_TYPE_ANALOG_TV, null);
                }
            } else {
                entrance = "TV";
                Mcontrol = new Menucontrol(this, null, GlobalConst.ENTRANCE_TYPE_ANALOG_TV, null);
            }

            myMode = entrance;

            AbsoluteLayout.LayoutParams paramp = new AbsoluteLayout.LayoutParams(1920, 1080, 0, 0);
            Mcontrol.setMenuCallbackListener(this);
            layout.addView(Mcontrol, paramp);

            if (entrance.equals("TV")) {
                menuHandler.sendEmptyMessageDelayed(GlobalConst.LOAD_NAVIGATE_FLAG, 500);
            }
            Mcontrol.setVisibility(View.INVISIBLE);
            Mcontrol.set_focus(false);
        }
        if (MuteInfoView == null) {
            MuteInfoView = new OtherInfoControl(this);
            MuteInfoView.initVolumeMuteInfo(layout);
            menuHandler.sendEmptyMessageDelayed(GlobalConst.MUTEINFO, 200);
        }
    }

    private void hideMenucontrol() {
        if (Mcontrol != null) {
            Mcontrol.BackMenuHandle();
            Mcontrol.setVisibility(View.INVISIBLE);
            Mcontrol.set_focus(false);
        }
    }

    private void clearOtherView() {
        if ((Mcontrol != null) && (Mcontrol.getVisibility() == View.INVISIBLE)) {
            removeAllInfo();
            waitToHideMenu();
        }
    }

    private void removeAllInfo() {
        removeNumberInputInfo();
        removeChannelInfo();
        removeVolumeInfo();
    }

    private void initNumberInputInfo(int numberLimit, int displayvalue) {
        if (numberInputInfoView == null) {
            numberInputInfoView = new NumberInputInfoView(this, numberLimit, displayvalue);
            AbsoluteLayout.LayoutParams paramp = new AbsoluteLayout.LayoutParams(256, 94, 1620, 50);
            numberInputInfoView.setOtherViewCallbackListener(this);
            layout.addView(numberInputInfoView, paramp);
            numberInputInfoView.requestFocus();
            waitToHideNumberInputInfo();
        }
    }

    private void removeNumberInputInfo() {
        if (numberInputInfoView != null) {
            layout.removeView(numberInputInfoView);
            numberInputInfoView = null;
        }
    }

    public void waitToHideNumberInputInfo() {
        if (0 != lastNumberInputInfoTime)
            return;
        lastNumberInputInfoTime = new Date().getTime();
        Thread t = new Thread() {
            public void run() {
                for (int i = 0; i < GlobalConst.NUMBERINPUTINFO_DISAPPEAR_TIME; i++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (numberInputInfoKey) {
                        i = 0;
                        numberInputInfoKey = false;
                    }
                }

                lastNumberInputInfoTime = 0;
                menuHandler.sendEmptyMessage(GlobalConst.REMOVE_NUMBER_INPUT_INFO);
            }
        };
        t.start();
    }

    private void initChannelInfo() {
        initChannelInfo(false);
    }

    private void initChannelInfo(boolean newSource) {
        if (channelInfoView == null) {
            channelInfoKey = true;
            AbsoluteLayout.LayoutParams paramp;
            int curSource = getCurSource();
            channelInfoView = new ChannelInfoView(this, curSource, newSource);
            switch (curSource) {
                case GlobalConst.TV_SOURCE:
                    paramp = new AbsoluteLayout.LayoutParams(256, 204, 1620, 50);
                    break;
                case GlobalConst.AV1_SOURCE:
                    paramp = new AbsoluteLayout.LayoutParams(256, 149, 1620, 50);
                    break;
                case GlobalConst.YPBPR1_SOURCE:
                case GlobalConst.HDMI1_SOURCE:
                case GlobalConst.HDMI2_SOURCE:
                case GlobalConst.HDMI3_SOURCE:
                case GlobalConst.VGA_SOURCE:
                default:
                    paramp = new AbsoluteLayout.LayoutParams(320, 149, 1556, 50);
                    break;
            }
            layout.addView(channelInfoView, paramp);
            waitToHideChannelInfo();
        }
    }

    private void removeChannelInfo() {
        if (channelInfoView != null) {
            layout.removeView(channelInfoView);
            channelInfoView = null;
        }
    }

    public void waitToHideChannelInfo() {
        if (0 != lastChannelInfoTime)
            return;
        lastChannelInfoTime = new Date().getTime();
        Thread t = new Thread() {
            public void run() {
                for (int i = 0; i < GlobalConst.CHANNELINFO_DISAPPEAR_TIME; i++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (channelInfoKey) {
                        i = 0;
                        channelInfoKey = false;
                    }
                }

                lastChannelInfoTime = 0;
                menuHandler.sendEmptyMessage(GlobalConst.REMOVE_CHANNEL_INFO);
            }
        };
        t.start();
    }

    private void initVolumeInfo() {
        if (volumeInfoView == null) {
            int volum_offset_y = 0;
            if (GlobalConst.OSD_DISPLAY_HALF_FLAG == 2)
                volum_offset_y = 540;
            volumeInfoView = new VolumeInfoView(this, tvsetting.GetCurAudioVolume());
            AbsoluteLayout.LayoutParams paramp = new AbsoluteLayout.LayoutParams(1920, 152 + volum_offset_y, 0, 1080 - 152 - volum_offset_y);
            layout.addView(volumeInfoView, paramp);
            volumeInfoView.requestFocus();
            waitToHideVolumeInfo();
        }
    }

    private void removeVolumeInfo() {
        if (volumeInfoView != null) {
            layout.removeView(volumeInfoView);
            volumeInfoView = null;
        }
    }

    public void waitToHideVolumeInfo() {
        if (0 != lastVolumeInfoTime)
            return;
        lastVolumeInfoTime = new Date().getTime();
        Thread t = new Thread() {
            public void run() {
                for (int i = 0; i < GlobalConst.VOLUMEINFO_DISAPPEAR_TIME; i++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (volumeInfoKey) {
                        i = 0;
                        volumeInfoKey = false;
                    }
                }

                lastVolumeInfoTime = 0;
                menuHandler.sendEmptyMessage(GlobalConst.REMOVE_VOLUME_INFO);
            }
        };
        t.start();
    }

    class PInfo {
        private String appname = "";
        private String pname = "";
        private String versionName = "";
        private int versionCode = 0;
        private Drawable icon;
    }

    private boolean CheckDemoupdateExist() {
        ArrayList<PInfo> apps = getInstalledApps(false);
        final int max = apps.size();
        for (int i = 0; i < max; i++) {
            if ("com.skyworth.demoupdate".equals(apps.get(i).pname)) {
                return true;
            }
        }
        return false;
    }

    private ArrayList<PInfo> getInstalledApps(boolean getSysPackages) {
        ArrayList<PInfo> res = new ArrayList<PInfo>();
        List<PackageInfo> packs = getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packs.size(); i++) {
            PackageInfo p = packs.get(i);
            if ((!getSysPackages) && (null == p.versionName)) {
                continue;
            }
            PInfo newInfo = new PInfo();
            newInfo.appname = p.applicationInfo.loadLabel(getPackageManager()).toString();
            newInfo.pname = p.packageName;
            newInfo.versionName = p.versionName;
            newInfo.versionCode = p.versionCode;
            newInfo.icon = p.applicationInfo.loadIcon(getPackageManager());
            res.add(newInfo);
        }
        return res;
    }

    public void CallbackMenuState(String... Menu) {
        if ((Menu[0].equals("shortcut_common_mute_"))
            || (Menu[0].equals("shortcut_common_vol_"))) {
            if (tvsetting.ismCustomVolumeMute())
                MuteInfoView.setVisibile();
            else
                MuteInfoView.setInVisibile();
        } else if ((Menu[0].equals("shortcut_setup_sys_woofer_switch_on"))
            || (Menu[0].equals("shortcut_setup_sys_woofer_switch_off"))
            || (Menu[0].equals("shortcut_setup_sys_woofer_vol_"))
            || (Menu[0].equals("shortcut_setup_audio_bass_"))
            || (Menu[0].equals("shortcut_setup_audio_treble_"))
            || (Menu[0].equals("shortcut_setup_audio_balance_"))
            || (Menu[0].equals("shortcut_program_sound_sys_dk"))
            || (Menu[0].equals("shortcut_program_sound_sys_i"))
            || (Menu[0].equals("shortcut_program_sound_sys_bg"))
            || (Menu[0].equals("shortcut_program_sound_sys_m"))
            || (Menu[0].equals("shortcut_program_sound_sys_auto"))
            || (Menu[0].equals("shortcut_program_vol_correct_"))
            || (Menu[0].equals("shortcut_setup_audio_srs_on"))
            || (Menu[0].equals("shortcut_setup_audio_srs_off"))
            || (Menu[0].equals("shortcut_setup_audio_voice_on"))
            || (Menu[0].equals("shortcut_setup_audio_voice_off"))
            || (Menu[0].equals("shortcut_setup_audio_increase_bass_on"))
            || (Menu[0].equals("shortcut_setup_audio_increase_bass_off"))
            || (Menu[0].equals("shortcut_setup_audio_sound_mode_std"))
            || (Menu[0].equals("shortcut_setup_audio_sound_mode_news"))
            || (Menu[0].equals("shortcut_setup_audio_sound_mode_theater"))
            || (Menu[0].equals("shortcut_setup_audio_sound_mode_music"))
            || (Menu[0].equals("shortcut_setup_audio_sound_mode_user"))
            || (Menu[0].equals("shortcut_setup_sys_wall_effects_on"))
            || (Menu[0].equals("shortcut_setup_sys_wall_effects_off"))
            || (Menu[0].equals("shortcut_setup_audio_equalizer_"))
            || (Menu[0].equals("shortcut_setup_audio_mute_panel_"))) {
            MuteInfoView.setInVisibile();
        } else if (Menu[0].contains("shortcut_common_source")) {
            String entrance = null;
            if (Menu[0].equals("shortcut_common_source_tv")) {
                entrance = "TV";
            } else if (Menu[0].equals("shortcut_common_source_av1")) {
                entrance = "AV1";
            } else if (Menu[0].equals("shortcut_common_source_yuv1")) {
                entrance = "YUV1";
            } else if (Menu[0].equals("shortcut_common_source_hdmi1")) {
                entrance = "HDMI1";
            } else if (Menu[0].equals("shortcut_common_source_hdmi2")) {
                entrance = "HDMI2";
            } else if (Menu[0].equals("shortcut_common_source_hdmi3")) {
                entrance = "HDMI3";
            } else if (Menu[0].equals("shortcut_common_source_vga")) {
                entrance = "VGA";
            }
            if (entrance != null) {
                if (Mcontrol != null) {
                    layout.removeView(Mcontrol);
                    // Mcontrol.removeAllViews();
                    Mcontrol = null;
                }
                initMenucontrol(entrance);
            }
        } else if (Menu[0].equals("BackTo3D")) {
            // this.onKeyDown(KeyEvent.KEYCODE_BACK, new
            // KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK));
        } else if (Menu[0].equals("shortcut_shopping_demo_")) {
            if (CheckDemoupdateExist()) {
                Intent marketDemo = new Intent("android.intent.action.SkyworthDemoUpdate");
                marketDemo.putExtra("source", "Haha"); // disable half size of
                                                       // UI currently
                startActivity(marketDemo);
            } else {
                Intent marketDemo = new Intent("android.intent.action.SkyworthDemo");
                marketDemo.putExtra("source", "Haha"); // disable half size of
                                                       // UI currently
                startActivity(marketDemo);
            }
        }

    }

    public void NumberInputCallback(int chNumber) {
        countNum = chNumber;

        if (menuHandler.hasMessages(GlobalConst.CH_NUMBER))
            menuHandler.removeMessages(GlobalConst.CH_NUMBER);
        menuHandler.sendEmptyMessageDelayed(GlobalConst.CH_NUMBER, 250);
    }

    private int getCurSource() {
        if (tvsetting.GetCurSourceTV())
            return GlobalConst.TV_SOURCE;
        else if (tvsetting.GetCurSourceAV1())
            return GlobalConst.AV1_SOURCE;
        else if (tvsetting.GetCurSourceYPBPR1())
            return GlobalConst.YPBPR1_SOURCE;
        else if (tvsetting.GetCurSourceHDMI1())
            return GlobalConst.HDMI1_SOURCE;
        else if (tvsetting.GetCurSourceHDMI2())
            return GlobalConst.HDMI2_SOURCE;
        else if (tvsetting.GetCurSourceHDMI3())
            return GlobalConst.HDMI3_SOURCE;
        else if (tvsetting.GetCurSourceVGA())
            return GlobalConst.VGA_SOURCE;
        else
            return -1;
    }

}
