package com.skyworth.control;

import java.util.Locale;

import android.app.ActivityManagerNative;
import android.app.IActivityManager;
import android.app.backup.BackupManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.RemoteException;
//import com.skyworth.contentprovider.UserContentProvider;

import com.amlogic.tvjni.Itvservice;
import com.skyworth.SkyworthMenu.GlobalConst;

public class tvsetting {
    public static String entrance;

    // private UserContentProvider ucp=null;
    // public void ucpInstance(Context context){
    // if(ucp==null)
    // ucp=new UserContentProvider(context);
    // }

    public tvsetting(Context context) {
        // ucpInstance(context);
    }

    public static void setEntrance(String entry) {
        entrance = entry;
    }

    private static Itvservice mItvservice = null;

    public static void setItvService(Itvservice tvservice) {
        mItvservice = tvservice;
    }

    public static String GetEntrance() {
        return entrance;
    }

    public static int GetCurAudioSupperBassSwitch() {
        if (mItvservice != null)
            try {
                return mItvservice.GetCurAudioSupperBassSwitch();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return 0;
    }

    public static int GetCurAudioSRSSurround() {
        if (mItvservice != null)
            try {
                return mItvservice.GetCurAudioSRSSurround();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return 0;
    }

    public static void exchangeChannelInfo(int sourceCh, int targetCh) {
        if (mItvservice != null)
            try {
                mItvservice.exchangeChannelInfo(sourceCh, targetCh);
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    public static int num() {
        if (mItvservice != null)
            try {
                return mItvservice.num();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return 0;
    }

    public static boolean ChInfojump() {
        if (mItvservice != null)
            try {
                return mItvservice.ChInfojump();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return false;
    }

    public static boolean ChInfoaft() {
        if (mItvservice != null)
            try {
                return mItvservice.ChInfoaft();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return false;
    }

    public static int ChInfovideostd() {
        if (mItvservice != null)
            try {
                return mItvservice.ChInfovideostd();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return 0;
    }

    public static int ChInfosoundstd() {
        if (mItvservice != null)
            try {
                return mItvservice.ChInfosoundstd();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return 0;
    }

    public static void setTunerChannel(int num) {
        if (mItvservice != null)
            try {
                mItvservice.setTunerChannel(num);
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    public static void setTunerLastChannel() {
        if (mItvservice != null)
            try {
                mItvservice.setTunerLastChannel();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    public static void SaveTvCurrentChannel() {
        if (mItvservice != null)
            try {
                mItvservice.SaveTvCurrentChannel();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    public static int getCurrnetChNumber() {
        if (mItvservice != null)
            try {
                return mItvservice.getCurrnetChNumber();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return 0;
    }

    public static void updateTunerFrequencyUI() {
        if (mItvservice != null)
            try {
                mItvservice.updateTunerFrequencyUI();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    public static boolean getSearchStop() {
        if (mItvservice != null)
            try {
                return mItvservice.getSearchStop();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return false;
    }

    public static void SearchChanel(boolean f, boolean s, boolean t) {
        if (mItvservice != null)
            try {
                mItvservice.SearchChanel(f, s, t);
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    public static boolean getSearchDown() {
        if (mItvservice != null)
            try {
                return mItvservice.getSearchDown();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return false;
    }

    public static void setSearchDown(boolean flag) {
        if (mItvservice != null)
            try {
                mItvservice.setSearchDown(flag);
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    public static void FintTune(int val) {
        if (mItvservice != null)
            try {
                mItvservice.FintTune(val);
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    public static boolean getAutoSearchOn() {
        if (mItvservice != null)
            try {
                return mItvservice.getAutoSearchOn();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return false;
    }

    public static int getMaxFreq() {
        if (mItvservice != null)
            try {
                return mItvservice.getMaxFreq();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return 0;
    }

    public static int getMinFreq() {
        if (mItvservice != null)
            try {
                return mItvservice.getMinFreq();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return 0;
    }

    public static int getMaxVHFHFreq() {
        if (mItvservice != null)
            try {
                return mItvservice.getMaxVHFHFreq();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return 0;
    }

    public static int getMaxVHFLFreq() {
        if (mItvservice != null)
            try {
                return mItvservice.getMaxVHFLFreq();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return 0;
    }

    public static int manuFreq() {
        if (mItvservice != null)
            try {
                return mItvservice.manuFreq();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return 0;
    }

    public static int GetCurAudioVolume() {
        if (mItvservice != null)
            try {
                return mItvservice.GetCurAudioVolume();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return 0;
    }

    public static void SaveCurAudioVolume(int progress) {
        if (mItvservice != null)
            try {
                mItvservice.SaveCurAudioVolume(progress);
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    public static void setnum(int num) {
        if (mItvservice != null)
            try {
                mItvservice.setnum(num);
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    public static void addorreducenum(int flag) {
        if (mItvservice != null)
            try {
                mItvservice.addorreducenum(flag);
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    public static void setSearchHandler() {
        if (mItvservice != null)
            try {
                mItvservice.setSearchHandler();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    public static void setVGAMessageHandler() {
        if (mItvservice != null)
            try {
                mItvservice.setVGAMessageHandler();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    public static void setSignalDetectHandler() {
        if (mItvservice != null)
            try {
                mItvservice.setSignalDetectHandler();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    public static void setDreampanelHandler() {
        if (mItvservice != null)
            try {
                mItvservice.setDreampanelHandler();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    public static void setDreampanelDemo(boolean flag) {
        if (mItvservice != null)
            try {
                mItvservice.setDreampanelDemo(flag);
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    public static void uartsend(String... Menu) {
        try {
            if (mItvservice != null)
                mItvservice.UartSend(Menu[0], Menu[1]);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static int GetTvParam(String MenuItemName) {
        try {
            if (mItvservice != null)
                return mItvservice.GetTvParam(MenuItemName);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    public static void GetCurEQGain(int[] dataBuf) {
        try {
            if (mItvservice != null)
                mItvservice.GetCurEQGain(dataBuf);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static boolean ismCurAudioVolumeInc() {
        if (mItvservice != null)
            try {
                return mItvservice.ismCurAudioVolumeInc();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return false;
    }

    public static void SetAudioMainVolume() {
        try {
            if (mItvservice != null)
                mItvservice.SetAudioMainVolume();
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static boolean GetCurSourceTV() {
        if (mItvservice != null)
            try {
                return mItvservice.GetCurSourceTV();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return false;
    }

    public static boolean GetCurSourceAV1() {
        if (mItvservice != null)
            try {
                return mItvservice.GetCurSourceAV1();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return false;
    }

    public static boolean GetCurSourceYPBPR1() {
        if (mItvservice != null)
            try {
                return mItvservice.GetCurSourceYPBPR1();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return false;
    }

    public static boolean GetCurSourceHDMI1() {
        if (mItvservice != null)
            try {
                return mItvservice.GetCurSourceHDMI1();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return false;
    }

    public static boolean GetCurSourceHDMI2() {
        if (mItvservice != null)
            try {
                return mItvservice.GetCurSourceHDMI2();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return false;
    }

    public static boolean GetCurSourceHDMI3() {
        if (mItvservice != null)
            try {
                return mItvservice.GetCurSourceHDMI3();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return false;
    }

    public static boolean GetCurSourceVGA() {
        if (mItvservice != null)
            try {
                return mItvservice.GetCurSourceVGA();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return false;
    }

    public static int changeTunerChannel(boolean flag) {
        try {
            if (mItvservice != null)
                return mItvservice.changeTunerChannel(flag);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    public static void changeCurrentChannelToLastChannel() {
        try {
            if (mItvservice != null)
                mItvservice.changeCurrentChannelToLastChannel();
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static boolean ismCustomVolumeMute() {
        if (mItvservice != null)
            try {
                return mItvservice.ismCustomVolumeMute();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return false;
    }

    public static void setmCustomVolumeMute(boolean flag) {
        try {
            if (mItvservice != null)
                mItvservice.setmCustomVolumeMute(flag);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static int GetAmplifierMute() {
        if (mItvservice != null)
            try {
                return mItvservice.GetAmplifierMute();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return 0;
    }

    public static void SetAudioSupperBassVolume() {
        try {
            if (mItvservice != null)
                mItvservice.SetAudioSupperBassVolume();
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static int GetAvColorSys() {
        if (mItvservice != null)
            try {
                return mItvservice.GetAvColorSys();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return 0;
    }

    public static String GetSigFormatName() {
        if (mItvservice != null)
            try {
                return mItvservice.GetSigFormatName();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return " ";
    }

    public static int LoadNumberInputLimit() {
        if (mItvservice != null)
            try {
                return mItvservice.LoadNumberInputLimit();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return 0;
    }

    public static void SaveNumberInputLimit(int flag) {
        try {
            if (mItvservice != null)
                mItvservice.SaveNumberInputLimit(flag);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static int LoadNavigateFlag() {
        if (mItvservice != null)
            try {
                return mItvservice.LoadNavigateFlag();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return 0;
    }

    public static void SaveNavigateFlag(int flag) {
        try {
            if (mItvservice != null)
                mItvservice.SaveNavigateFlag(flag);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void changeCurrentChannelToChannel(int flag) {
        try {
            if (mItvservice != null)
                mItvservice.changeCurrentChannelToChannel(flag);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void setViiColorDemo(int demoMode) {
        try {
            if (mItvservice != null)
                mItvservice.setViiColorDemo(demoMode);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void setBaseColor(int cmMode) {
        try {
            if (mItvservice != null)
                mItvservice.setBaseColor(cmMode);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public static int GetCurVgaData(String ItemName) {
        int val = 0;
        try {
            if (mItvservice != null)
                val = mItvservice.GetTvProgressBar(ItemName);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (val == -1)
            val = 50;
        return val;
    }

    public static void StartTv() {
        try {
            if (mItvservice != null)
                mItvservice.StartTv();
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static boolean CloseTv() {
        try {
            if (mItvservice != null)
                return mItvservice.CloseTv();
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    public static void IICBusOn() {
        if (mItvservice != null)
            try {
                mItvservice.IICBusOn();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    public static void IICBusOff() {
        if (mItvservice != null)
            try {
                mItvservice.IICBusOff();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    public static void Factory_WriteEepromOneByte(int offset, int value) {
        if (mItvservice != null)
            try {
                mItvservice.Factory_WriteEepromOneByte(offset, value);
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    public static void Factory_WriteEepromNByte(int offset, int len, int[] buf) {
        if (mItvservice != null)
            try {
                mItvservice.Factory_WriteEepromNByte(offset, len, buf);
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    public static int Factory_ReadEepromOneByte(int offset) {
        if (mItvservice != null)
            try {
                return mItvservice.Factory_ReadEepromOneByte(offset);
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return 0;
    }

    public static int[] Factory_ReadEepromNByte(int offset, int len) {
        if (mItvservice != null)
            try {
                return mItvservice.Factory_ReadEepromNByte(offset, len);
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        int[] buf = new int[len];
        return buf;
    }

    public static void FacSet_RGBogo(String channelSelect,
        String ganioffsetSel, int value) {
        if (mItvservice != null)
            try {
                mItvservice.FacSet_RGBogo(channelSelect, ganioffsetSel, value);
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    public static int FacGet_RGBogo(String channelSelect, String ganioffsetSel) {
        if (mItvservice != null)
            try {
                return mItvservice.FacGet_RGBogo(channelSelect, ganioffsetSel);
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return 0;
    }

    public static int FacSet_AdcAutoCal() {
        if (mItvservice != null)
            try {
                return mItvservice.FacSet_AdcAutoCal();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return -1;
    }

    public static int FacGet_AdcAutoCalResult(){
        if (mItvservice != null)
            try {
                return mItvservice.FacGet_AdcAutoCalResult();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return -1;
    }
    
    public static int FacGet_AdcAutoCalStatus(){
        if (mItvservice != null)
            try {
                return mItvservice.FacGet_AdcAutoCalStatus();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return 0;
    }
    
    // /////////// factory mode flag
    public static void FacSet_BURN_FLAG(boolean turnOn) {
        if (mItvservice != null)
            try {
                mItvservice.FacSet_BURN_FLAG(turnOn);
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    public static boolean FacGet_BURN_FLAG() {
        if (mItvservice != null)
            try {
                return mItvservice.FacGet_BURN_FLAG();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return false;
    }

    // ////////////////////////////////factory key flag

    public static void FacSet_ONEKEY_ONOFF(int value) {
        if (mItvservice != null)
            try {
                mItvservice.FacSet_ONEKEY_ONOFF(value);
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    public static int FacGet_ONEKEY_ONOFF() {
        if (mItvservice != null)
            try {
                return mItvservice.FacGet_ONEKEY_ONOFF();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return 0;
    }

    // /////////////////////mac add

    public static void SetDefaultMacAddr() {
        if (mItvservice != null)
            try {
                mItvservice.SetDefaultMacAddr();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    // ///////////////////////////ssc

    public static void SetSSC(int range, int lowrange, int cycle, boolean onoff) {
        if (mItvservice != null)
            try {
                mItvservice.SetSSC(range, lowrange, cycle, onoff);
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    public static int[] GetSscDate() {
        if (mItvservice != null)
            try {
                return mItvservice.GetSscDate();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        int[] buf = new int[4];
        return buf;
    }

    // ////////////////set panel type
    public static void FacSetPanelType(int paneltype) {
        if (mItvservice != null)
            try {
                mItvservice.FacSetPanelType(paneltype);
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    public static String FacGetPanelType() {
        if (mItvservice != null)
            try {
                return mItvservice.FacGetPanelType();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return "ERROR";
    }

    // ////////////////set stand by key

    public static void SetStandbyKeyMode(boolean SetSSC) {
        if (mItvservice != null)
            try {
                mItvservice.SetStandbyKeyMode(SetSSC);
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    public static int[] GetMacAddr() {
        if (mItvservice != null)
            try {
                return mItvservice.GetMacAddr();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        int[] buf = new int[6];
        return buf;
    }

    public static void SaveMacAddr() {
        if (mItvservice != null)
            try {
                mItvservice.SaveMacAddr();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    // /////////////////////////adb concrol

    public static int GetAdbState() {
        if (mItvservice != null)
            try {
                return mItvservice.GetAdbState();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        return -1;
    }

    public static void SetAdbState(int flag) {
        if (mItvservice != null)
            try {
                mItvservice.SetAdbState(flag);
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    // ////////////////////////////// usb boot

    public static int GetUsbbootState() {
        if (mItvservice != null)
            try {
                return mItvservice.GetUsbbootState();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        return -1;
    }

    public static void SetUsbbootState(int flag) {
        if (mItvservice != null)
            try {
                mItvservice.SetUsbbootState(flag);
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }
    
    
    //////////////////////////////lvds
    
    public static int GetLvdsState() {
        if (mItvservice != null)
            try {
                return mItvservice.GetLvdsState();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        return -1;
    }

    public static void SetLvdsState(int flag) {
        if (mItvservice != null)
            try {
                mItvservice.SetLvdsState(flag);
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    public static String LoadBarCode() {
        if (mItvservice != null)
            try {
                return mItvservice.LoadBarCode();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return "";
    }

    public static void factoryBurnMode(boolean flag) {
        if (mItvservice != null)
            try {
                mItvservice.factoryBurnMode(flag);
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    // //////////////////////get factory menu flag

    public static void SetfactoryFlag(int flag) {
        if (mItvservice != null)
            try {
                mItvservice.SetfactoryFlag(flag);
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    public static int GetfactoryFlag() {
        if (mItvservice != null)
            try {
                return mItvservice.GetfactoryFlag();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return 0;
    }

    // ////////////////////////////////factory option
    public static boolean GetHdcpFlag() {
        if (mItvservice != null)
            try {
                return mItvservice.FacGet_DEF_HDCP_FLAG();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return false;
    }

    public static void SetHdcpFlag(boolean loadDef) {
        if (mItvservice != null)
            try {
                mItvservice.FacSet_DEF_HDCP_FLAG(loadDef);
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    public static boolean GetOnLineMusicFlag() {
        if (mItvservice != null)
            try {
                return mItvservice.GetOnLineMusicFlag();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return true;
    }
    
    public static void SetOnLineMusicFlag(boolean isOn) {
        if (mItvservice != null)
            try {
                mItvservice.SetOnLineMusicFlag(isOn);
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }
    
    public static boolean GetStartPicFlag() {
        if (mItvservice != null)
            try {
                return mItvservice.GetStartPicFlag();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return false;
    }
    
    public static void SetStartPicFlag(boolean isOn) {
        if (mItvservice != null)
            try {
                mItvservice.SetStartPicFlag(isOn);
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }
    public static boolean GetStandByModeFlag() {
        if (mItvservice != null)
            try {
                return mItvservice.GetStandByModeFlag();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return false;
    }
    
    public static void SetStandByModeFlag(boolean isOn) {
        if (mItvservice != null)
            try {
                mItvservice.SetStandByModeFlag(isOn);
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }    
    
    public static int FacGetHdmiEQMode() {
        try {
            if (mItvservice != null)
                return mItvservice.FacGetHdmiEQMode();
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return -1;
    }

    public static void FacSetHdmiEQMode(int value) {
        if (GlobalConst.DEBUG_MODE_SELECT == GlobalConst.DEBUG_MODE_EMULATOR)
            return;

        try {
            if (mItvservice != null) {
                mItvservice.FacSetHdmiEQMode(value);
            }
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static int FacGetHdmiInternalMode() {
        try {
            if (mItvservice != null)
                return mItvservice.FacGetHdmiInternalMode();
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return -1;
    }

    public static void FacSetHdmiInternalMode(int value) {
        if (GlobalConst.DEBUG_MODE_SELECT == GlobalConst.DEBUG_MODE_EMULATOR)
            return;

        try {
            if (mItvservice != null) {
                mItvservice.FacSetHdmiInternalMode(value);
            }
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static boolean FacGetSerialPortSwitchFlag() {
        try {
            if (mItvservice != null)
                return mItvservice.FacGetSerialPortSwitchFlag();
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    public static void FacSetSerialPortSwitchFlag(boolean flag) {
        if (GlobalConst.DEBUG_MODE_SELECT == GlobalConst.DEBUG_MODE_EMULATOR)
            return;

        try {
            if (mItvservice != null) {
                mItvservice.FacSetSerialPortSwitchFlag(flag);
            }
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    // ////////////////////////////////EEPROM DATA INIT
    public static int InitEepromData() {
        if (mItvservice != null)
            try {
                return mItvservice.InitEepromData();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return -1;
    }

    // //////////////////////////////////EEPROM DATE

    public static String LoadEepromDate() {
        if (mItvservice != null)
            try {
                return mItvservice.LoadEepromDate();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return "";
    }

    ////////////////////////////////////OTHERS
    public static void UiSetAudioMutePanel(int value) {
        if (mItvservice != null)
            try {
                mItvservice.UiSetAudioMutePanel(value);
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    public static int GetSigTransFormat() {
        if (mItvservice != null)
            try {
                return mItvservice.GetSigTransFormat();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return -1;
    }

    public static int Get3DStatus() {
        if (mItvservice != null)
            try {
                return mItvservice.Get3DStatus();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return -1;
    }

    public static void SetViewMode(int mode) {
    	 if (mItvservice != null)
             try {
                 mItvservice.SetViewMode(mode);
             } catch (RemoteException e) {
                 // TODO Auto-generated catch block
                 e.printStackTrace();
             }
    }
    
	public static void UiSetScrMode(int mode) {
		// TODO Auto-generated method stub
		if (mItvservice != null)
            try {
                mItvservice.UiSetScrMode(mode);
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
	}
	
    public static void SaveSysStandby() {
   	 if (mItvservice != null)
            try {
                mItvservice.SaveSysStandby();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }
    
    public static boolean isSrcSwtichDone() {
        if (mItvservice != null)
            try {
                return mItvservice.isSrcSwtichDone();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return true;
    }
    
    public static int isVgaFmtInHdmi() {
        try {
            if (mItvservice != null)
                return mItvservice.isVgaFmtInHdmi();
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return -1;
    }
    
    public static void ResumeLastBLValue() {
        if (mItvservice != null)
            try {
                mItvservice.ResumeLastBLValue();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }
    
    public static void setWhiteScreen() {
        if (mItvservice != null)
            try {
                mItvservice.setWhiteScreen();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }
    // //////////////////////////default burn setting

    public static void SetBurnDefault() {
        if (mItvservice != null)
            try {
                mItvservice.SetBurnDefault();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    // ///////////////default factory out setting

    public static void SetFacOutDefault() {
        if (mItvservice != null)
            try {
                mItvservice.SetFacOutDefault();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    ////////////////////////////////////////////////UI OPERATION
    public static void SettingTVControl(String... Menu) {
        if ((Menu[0].equals("shortcut_common_source_coocaa"))
            || (Menu[0].equals("shortcut_common_source_tv"))
            || (Menu[0].equals("shortcut_common_source_av1"))
            || (Menu[0].equals("shortcut_common_source_yuv1"))
            || (Menu[0].equals("shortcut_common_source_hdmi1"))
            || (Menu[0].equals("shortcut_common_source_hdmi2"))
            || (Menu[0].equals("shortcut_common_source_hdmi3"))
            || (Menu[0].equals("shortcut_common_source_vga"))) {
            if ((entrance.equals(GlobalConst.ENTRANCE_TYPE_ANALOG_TV))
                || (entrance.equals(GlobalConst.ENTRANCE_TYPE_AV))
                || (entrance.equals(GlobalConst.ENTRANCE_TYPE_YUV))
                || (entrance.equals(GlobalConst.ENTRANCE_TYPE_HDMI))
                || (entrance.equals(GlobalConst.ENTRANCE_TYPE_VGA)))
                SettingMISC(Menu);
        } else if ((Menu[0].equals("shortcut_setup_sys_poweron_source_memory"))
            || (Menu[0].equals("shortcut_setup_sys_poweron_source_coocaa"))
            || (Menu[0].equals("shortcut_setup_sys_poweron_source_tv"))
            || (Menu[0].equals("shortcut_setup_sys_poweron_source_av1"))
            || (Menu[0].equals("shortcut_setup_sys_poweron_source_yuv1"))
            || (Menu[0].equals("shortcut_setup_sys_poweron_source_hdmi1"))
            || (Menu[0].equals("shortcut_setup_sys_poweron_source_hdmi2"))
            || (Menu[0].equals("shortcut_setup_sys_poweron_source_hdmi3"))
            || (Menu[0].equals("shortcut_setup_sys_poweron_source_vga"))
            || (Menu[0].equals("shortcut_setup_sys_keysound_on"))
            || (Menu[0].equals("shortcut_setup_sys_keysound_off"))
            || (Menu[0].equals("shortcut_setup_sys_six_color_off"))
            || (Menu[0].equals("shortcut_setup_sys_six_color_opti"))
            || (Menu[0].equals("shortcut_setup_sys_six_color_enhance"))
            || (Menu[0].equals("shortcut_setup_sys_six_color_colordemo"))
            || (Menu[0].equals("shortcut_setup_sys_six_color_splitdemo"))
            || (Menu[0].equals("shortcut_setup_sys_back_light_"))
            || (Menu[0].equals("shortcut_setup_sys_dream_panel_off"))
            || (Menu[0].equals("shortcut_setup_sys_dream_panel_sensor"))
            || (Menu[0].equals("shortcut_setup_sys_dream_panel_scene"))
            || (Menu[0].equals("shortcut_setup_sys_dream_panel_all"))
            || (Menu[0].equals("shortcut_setup_sys_dream_panel_demo"))
            || (Menu[0].equals("shortcut_setup_sys_poweron_music_off"))
            || (Menu[0].equals("shortcut_setup_sys_poweron_music_on"))
            || (Menu[0].equals("shortcut_setup_sys_filelist_mode_file"))
            || (Menu[0].equals("shortcut_setup_sys_filelist_mode_folder"))
            || (Menu[0].equals("shortcut_common_playmode_normal"))
            || (Menu[0].equals("shortcut_common_playmode_folder"))
            || (Menu[0].equals("shortcut_common_playmode_rand"))
            || (Menu[0].equals("shortcut_picture_switch_time_3s"))
            || (Menu[0].equals("shortcut_picture_switch_time_5s"))
            || (Menu[0].equals("shortcut_picture_switch_time_10s"))
            || (Menu[0].equals("shortcut_picture_switch_time_hand"))
            || (Menu[0].equals("shortcut_picture_switch_mode_1"))
            || (Menu[0].equals("shortcut_picture_switch_mode_2"))
        	|| (Menu[0].equals("shortcut_setup_sys_sleep_time_off"))
        	|| (Menu[0].equals("shortcut_setup_sys_sleep_time_5"))
        	|| (Menu[0].equals("shortcut_setup_sys_sleep_time_15"))
        	|| (Menu[0].equals("shortcut_setup_sys_sleep_time_30"))
        	|| (Menu[0].equals("shortcut_setup_sys_sleep_time_60"))
        	|| (Menu[0].equals("shortcut_setup_sys_sleep_time_90"))
        	|| (Menu[0].equals("shortcut_setup_sys_sleep_time_120"))){
            SettingMISC(Menu);
        } else if ((Menu[0].equals("shortcut_setup_sys_language_en"))
            || (Menu[0].equals("shortcut_setup_sys_language_zh"))) {
            SettingLanguage(Menu);
        } else if ((Menu[0].equals("shortcut_common_mute_"))
            || (Menu[0].equals("shortcut_setup_audio_equalizer_"))
            || (Menu[0].equals("shortcut_common_vol_"))
            || (Menu[0].equals("shortcut_setup_audio_balance_"))
            || (Menu[0].equals("shortcut_setup_audio_srs_on"))
            || (Menu[0].equals("shortcut_setup_audio_srs_off"))
            || (Menu[0].equals("shortcut_setup_audio_voice_on"))
            || (Menu[0].equals("shortcut_setup_audio_voice_off"))
            || (Menu[0].equals("shortcut_setup_audio_increase_bass_on"))
            || (Menu[0].equals("shortcut_setup_audio_increase_bass_off"))
            || (Menu[0].equals("shortcut_setup_sys_woofer_switch_on"))
            || (Menu[0].equals("shortcut_setup_sys_woofer_switch_off"))
            || (Menu[0].equals("shortcut_setup_sys_woofer_vol_"))
            || (Menu[0].equals("shortcut_setup_sys_wall_effects_on"))
            || (Menu[0].equals("shortcut_setup_sys_wall_effects_off"))
            || (Menu[0].equals("shortcut_setup_audio_sound_mode_std"))
            || (Menu[0].equals("shortcut_setup_audio_sound_mode_news"))
            || (Menu[0].equals("shortcut_setup_audio_sound_mode_theater"))
            || (Menu[0].equals("shortcut_setup_audio_sound_mode_music"))
            || (Menu[0].equals("shortcut_setup_audio_sound_mode_user"))
            || (Menu[0].equals("shortcut_setup_audio_bass_"))
            || (Menu[0].equals("shortcut_setup_audio_treble_"))
            || (Menu[0].equals("shortcut_setup_audio_mute_panel_"))) {
            SettingAudioControl(Menu);
        } else if ((Menu[0].equals("shortcut_setup_video_display_mode_169"))
            || (Menu[0].equals("shortcut_setup_video_display_mode_43"))
            || (Menu[0].equals("shortcut_setup_video_display_mode_subtitle"))
            || (Menu[0].equals("shortcut_setup_video_display_mode_theater"))
            || (Menu[0].equals("shortcut_setup_video_display_mode_personal"))
            || (Menu[0].equals("shortcut_setup_video_display_mode_panorama"))
            || (Menu[0].equals("shortcut_setup_video_picture_mode_std"))
            || (Menu[0].equals("shortcut_setup_video_picture_mode_vivid"))
            || (Menu[0].equals("shortcut_setup_video_picture_mode_soft"))
            || (Menu[0].equals("shortcut_setup_video_picture_mode_user"))
            || (Menu[0].equals("shortcut_setup_video_brightness_"))
            || (Menu[0].equals("shortcut_setup_video_contrast_"))
            || (Menu[0].equals("shortcut_setup_video_hue_"))
            || (Menu[0].equals("shortcut_setup_video_color_"))
            || (Menu[0].equals("shortcut_setup_video_sharpness_"))
            || (Menu[0].equals("shortcut_setup_video_temperature_cold"))
            || (Menu[0].equals("shortcut_setup_video_temperature_std"))
            || (Menu[0].equals("shortcut_setup_video_temperature_warm"))
            || (Menu[0].equals("shortcut_setup_video_dnr_off"))
            || (Menu[0].equals("shortcut_setup_video_dnr_weak"))
            || (Menu[0].equals("shortcut_setup_video_dnr_mid"))
            || (Menu[0].equals("shortcut_setup_video_dnr_strong"))
            || (Menu[0].equals("shortcut_setup_video_dnr_auto"))) {
            SettingVideoControl(Menu);
        } else if ((Menu[0].equals("shortcut_program_video_sys_auto"))
            || (Menu[0].equals("shortcut_program_video_sys_pal"))
            || (Menu[0].equals("shortcut_program_video_sys_ntsc"))) {
            SettingProgramControl(Menu);
        } else if ((Menu[0].equals("shortcut_setup_sys_pc_set_"))
            || (Menu[0].equals("shortcut_setup_sys_pc_set_auto_"))
            || (Menu[0].equals("shortcut_setup_sys_pc_set_hor_"))
            || (Menu[0].equals("shortcut_setup_sys_pc_set_ver_"))
            || (Menu[0].equals("shortcut_setup_sys_pc_set_freq_"))
            || (Menu[0].equals("shortcut_setup_sys_pc_set_pha_"))) {
            SettingPCSetControl(Menu);
        } else {
            // do nothing.
        }
    }

    public static void SettingVideoControl(String... Menu) {
        if (GlobalConst.DEBUG_MODE_SELECT == GlobalConst.DEBUG_MODE_EMULATOR)
            return;

        try {
            if (mItvservice != null) {
                if ((Menu[0].equals("shortcut_setup_video_brightness_"))
                    || (Menu[0].equals("shortcut_setup_video_contrast_"))
                    || (Menu[0].equals("shortcut_setup_video_color_"))
                    || (Menu[0].equals("shortcut_setup_video_hue_"))
                    || (Menu[0].equals("shortcut_setup_video_sharpness_")))
                    mItvservice.UartSend(Menu[0], Menu[1]);
                else
                    mItvservice.UartSend(Menu[0], "");
            }
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void SettingAudioControl(String... Menu) {
        if (GlobalConst.DEBUG_MODE_SELECT == GlobalConst.DEBUG_MODE_EMULATOR)
            return;

        try {
            if (mItvservice != null) {
                if ((Menu[0].equals("shortcut_common_vol_"))
                    || (Menu[0].equals("shortcut_setup_audio_bass_"))
                    || (Menu[0].equals("shortcut_setup_audio_treble_"))
                    || (Menu[0].equals("shortcut_setup_audio_balance_"))
                    || (Menu[0].equals("shortcut_setup_sys_woofer_vol_")))
                    mItvservice.UartSend(Menu[0], Menu[1]);
                else
                    mItvservice.UartSend(Menu[0], "");
            }
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void SettingLanguage(String... Menu) {
        if (GlobalConst.DEBUG_MODE_SELECT == GlobalConst.DEBUG_MODE_EMULATOR)
            return;

        try {
            IActivityManager am = ActivityManagerNative.getDefault();
            Configuration _config = am.getConfiguration();

            if (Menu[0].equals("shortcut_setup_sys_language_en")) {
                // Log.d("Language", "....Set to English...........");
                _config.locale = Locale.ENGLISH;
                if (null != mItvservice)
                    mItvservice.UartSend(Menu[0], "0");
            } else if (Menu[0].equals("shortcut_setup_sys_language_zh")) {
                // Log.d("Language", "*******Set to Simple Chinese...........");
                _config.locale = Locale.SIMPLIFIED_CHINESE;
                if (null != mItvservice)
                    mItvservice.UartSend(Menu[0], "1");
            }

            // indicate this isn't some passing default - the user wants this
            // remembered
            _config.userSetLocale = true;

            am.updateConfiguration(_config);
            // Trigger the dirty bit for the Settings Provider.
            BackupManager.dataChanged("com.android.providers.settings");
        } catch (RemoteException e) {
            // Intentionally left blank
            e.printStackTrace();
        }
    }

    public static void SettingMISC(String... Menu) {
        if (GlobalConst.DEBUG_MODE_SELECT == GlobalConst.DEBUG_MODE_EMULATOR)
            return;

        try {
            if (mItvservice != null) {
                if (Menu[0].equals("shortcut_setup_sys_back_light_"))
                    mItvservice.UartSend(Menu[0], Menu[1]);
                else
                    mItvservice.UartSend(Menu[0], "");
            }
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void SettingProgramControl(String... Menu) {
        if (GlobalConst.DEBUG_MODE_SELECT == GlobalConst.DEBUG_MODE_EMULATOR)
            return;

        try {
            if (mItvservice != null) {
                if (Menu[0].equals("shortcut_program_vol_correct_"))
                    mItvservice.UartSend(Menu[0], Menu[1]);
                else
                    mItvservice.UartSend(Menu[0], "");
            }
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void SettingPCSetControl(String... Menu) {
        if (GlobalConst.DEBUG_MODE_SELECT == GlobalConst.DEBUG_MODE_EMULATOR)
            return;

        try {
            if (mItvservice != null) {
                if (Menu[0].equals("shortcut_setup_sys_pc_set_auto_"))
                    mItvservice.UartSend(Menu[0], "");
                else
                    mItvservice.UartSend(Menu[0], Menu[1]);
            }
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void SettingSetup3DControl(String... Menu) {
        if (GlobalConst.DEBUG_MODE_SELECT == GlobalConst.DEBUG_MODE_EMULATOR)
            return;

        try {
            if (mItvservice != null){
            	if (Menu[0].equals("shortcut_video_3d_dof_"))
                    mItvservice.UartSend(Menu[0], Menu[1]);
                else
                	mItvservice.UartSend(Menu[0], "");
            }
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
