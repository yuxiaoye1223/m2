package com.amlogic.android.afeapi;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.*;

import com.amlogic.android.eepromapi.eepromJNI;
import com.amlogic.android.eepromapi.eepromJNI.*;
import com.amlogic.android.vdinapi.vdinJNI;
import com.amlogic.android.vdinapi.vdinJNI.*;
import com.amlogic.tvjni.GlobalConst;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

class AdcCalibrationThr implements Runnable {
    final static String DBTAG = "AFE";
    private static AtomicBoolean isRun = new AtomicBoolean(true);
    private static AtomicInteger funcSelect = new AtomicInteger(0);

    AdcCalibrationThr(int funcSel) {
        vdinJNI.StopSigDetectThr();
 
        this.funcSelect.set(funcSel);       
        if (funcSel == 0){
        	afeJNI.autoCalStatus = afeJNI.AutoStauts.DOING.ordinal();
        	afeJNI.isAutoCalOk = -1;
        	Log.d(DBTAG,"going to do ADC auto-calibration....");
        }
    }

    private static void DelayMs(int ms) {
        try {
            TimeUnit.MILLISECONDS.sleep(ms);
        } catch (InterruptedException e) {
            Log.d(DBTAG, "delay error!!");
            e.printStackTrace();
        }
    }

    public void run() {
    	boolean isFailure = true;
    	int srcId = vdinJNI.GetCurrentSrcInput();
    	
        while (isRun.get() == true) {
            isRun.set(false);
            DelayMs(500);

            if (funcSelect.get() == 0) {
              	/** VGA auto-calibration **/
                if(srcId != SrcId.VGA0.toInt()){
                	vdinJNI.CloseSrcPort();
                	vdinJNI.OpenSrcPort(SourceInput.VGA);
                	DelayMs(100);
                }	
	            afeJNI.isAutoCalOk = afeJNI.NewADCAutoCal();// afeJNI.ADCAutoCalibration();
	            afeJNI.GetADCGainOffset(afeJNI.vgaGainOffset);
	            eepromJNI.SaveADCGainOffset(ADCSrcId.VGA, afeJNI.vgaGainOffset);
	            afeJNI.vgaGainOffset.DbPrint();
	            if(afeJNI.isAutoCalOk <0)
	               	isFailure = true;
	            else
	            	isFailure = false;
	                    
	            /** YPbPr auto-calibration **/
	            vdinJNI.CloseSrcPort();
	            vdinJNI.OpenSrcPort(SourceInput.YPBPR1);
	            DelayMs(100);
	            afeJNI.isAutoCalOk = afeJNI.NewADCAutoCal();
	            afeJNI.GetADCGainOffset(afeJNI.comp0GainOffset);                 
	            eepromJNI.SaveADCGainOffset(ADCSrcId.COMP0,afeJNI.comp0GainOffset);
	            afeJNI.comp0GainOffset.DbPrint();  
	            if(isFailure != true && afeJNI.isAutoCalOk >=0){
	               	Log.d(DBTAG,"ADC auto-cal all done, OK!!");
	            }else{
	               	Log.e(DBTAG,"ADC auto-cal all done, Failure!!");
	                afeJNI.isAutoCalOk = -1;
	            }
	            
	            /** switch to pre-srcinput **/
	            vdinJNI.CloseSrcPort();
	            vdinJNI.OpenSrcId(srcId);
	            
            } else {
                if (vdinJNI.GetCurrentSrcInput() == vdinJNI.SrcId.VGA0.toInt()) {
                    afeJNI.GetMemData(1);
                } else if (vdinJNI.GetCurrentSrcInput() == vdinJNI.SrcId.COMP0
                    .toInt()
                    || vdinJNI.GetCurrentSrcInput() == vdinJNI.SrcId.COMP1
                        .toInt()) {
                    afeJNI.GetMemData(0);
                }
            }
        }
        DelayMs(100);
        vdinJNI.StartSigDetectThr();
        afeJNI.autoCalStatus = afeJNI.AutoStauts.DONE.ordinal();
        Log.d(DBTAG, "ADC AutoCal is done!!");
        isRun.set(true);
    }
}

class AdcAutoAdjThr implements Runnable {
    final static String DBTAG = "AFE";
    private static AtomicBoolean isRun = new AtomicBoolean(true);
    int signalFmt;

    AdcAutoAdjThr() {
        signalFmt = vdinJNI.GetSigFormat();
        // vdinJNI.KillSigDetectThr();
        afeJNI.autoAdjStatus = afeJNI.AutoStauts.DOING.ordinal();
        Log.d(DBTAG, "VGA AutoAdjustment is in process!!");
    }

    public static void SaveVgaAllAdjustment(int vgaFmt,
        afeJNI.VgaTimingAdj adjParam) {
        int uiHpos, uiVpos, uiClock, uiPhase;

        uiHpos = 50 - adjParam.HPosition;
        uiVpos = adjParam.VPosition + 50;
        uiClock = adjParam.Clock + 50;
        uiPhase = adjParam.Phase * 3 + 2;
        uiClock = 0x80 | uiClock; // mark this fmt's autoadjustment is already
                                  // done

        eepromJNI.SaveVgaAdjustment(vgaFmt, VGAFunc.HPOSITION, uiHpos);
        eepromJNI.SaveVgaAdjustment(vgaFmt, VGAFunc.VPOSITION, uiVpos);
        eepromJNI.SaveVgaAdjustment(vgaFmt, VGAFunc.CLOCK, uiClock);
        eepromJNI.SaveVgaAdjustment(vgaFmt, VGAFunc.PHASE, uiPhase);
    }

    public void run() {
        afeJNI.sendMessageToUI(GlobalConst.MSG_VGA_AUTO_BEGIN,
            "vga_auto_begin", 0);
        while (vdinJNI.isDetectThrDone() == false)
            ;
        while (isRun.get() == true) {
            isRun.set(false);
            for (int i = 0; i < 4; i++) {
                try {
                    TimeUnit.MILLISECONDS.sleep(250);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (afeJNI.VGAAutoAdj(afeJNI.vgaTimingAdj) < 0) {
                    Log.e(DBTAG, "vga auto adjustment fail, re-do...");
                    afeJNI.isAutoAdjOk = -1;
                } else{
                	afeJNI.isAutoAdjOk = 1;
                    break;
                }    
            }
            
            if(afeJNI.isAutoAdjOk <0){
            	Log.e(DBTAG, "vga auto adjustment fail,set default value...");
            	afeJNI.GetVGACurTimingAdj(afeJNI.vgaTimingAdj);
            }
            
            // Log.d(DBTAG,"after auto: Hpositon = " +
            // afeJNI.vgaTimingAdj.HPosition);
            // Log.d(DBTAG,"after auto: Vpositon = " +
            // afeJNI.vgaTimingAdj.VPosition);
            // Log.d(DBTAG,"after auto: Clock = " + afeJNI.vgaTimingAdj.Clock);
            // Log.d(DBTAG,"after auto: Phase = " + afeJNI.vgaTimingAdj.Phase);
            
            SaveVgaAllAdjustment(signalFmt, afeJNI.vgaTimingAdj);
        }
        // afeJNI.vgaTimingAdj = eepromJNI.LoadVgaAllAdjustment(signalFmt);
        afeJNI.SetVGAAdjParam(afeJNI.vgaTimingAdj);
        // vdinJNI.CreateSigDetectThr();
        afeJNI.autoAdjStatus = afeJNI.AutoStauts.DONE.ordinal();
        Log.d(DBTAG, "VGA AutoAdjustment is done!!");
        isRun.set(true);
        afeJNI.sendMessageToUI(GlobalConst.MSG_VGA_AUTO_DONE, "vga_auto_done",
            0);
    }
}

public class afeJNI {
    final static String DBTAG = "AFE";
    
    public static ComponentWss compWssData = new ComponentWss();
    public static VgaTimingAdj vgaTimingAdj = new VgaTimingAdj();
    public static AdcGainOffset adcGainOffset = new AdcGainOffset();   
    public static AdcGainOffset vgaGainOffset = new AdcGainOffset();
    public static AdcGainOffset comp0GainOffset = new AdcGainOffset();
    // public static AdcGainOffset comp1GainOffset = new AdcGainOffset();
    public static int autoAdjStatus = AutoStauts.UNDO.ordinal();
    public static int autoCalStatus = AutoStauts.UNDO.ordinal();
    public static int isAutoCalOk = -1;
    public  static int isAutoAdjOk = -1;
    private static Handler mHandler = null;
    
    public enum AutoStauts{
    	UNDO, DOING, DONE
    }
    
    public static void InitAfe() {
        OpenAFEModule();
        SetVgaEdidData();
        // load adc setting in eeprom
    }

    /*********************************************************************
     * UI Relate Function
     ********************************************************************/
    public static void setVGAMessageHandler(Handler tmpHandler) {
        mHandler = tmpHandler;
    }

    private static void VGASendMessage(int MsgType, int arg2, Bundle b) {
        if (mHandler != null) {
            Message msg = new Message();
            if (msg != null) {
                msg.arg1 = MsgType;
                msg.arg2 = arg2;
                msg.setData(b);
                Log.d("Haha", "22 The MsgType is:" + MsgType);
                mHandler.sendMessage(msg);
            }
        }
    }

    public static void sendMessageToUI(int MsgType, String name, int value) {
        Bundle b = new Bundle();
        // Log.d("Haha", "11 The MsgType is:" + MsgType);
        if (b != null) {
            b.putInt(name, value);

            VGASendMessage(MsgType, 0, b);
        }
    }

    public static void CloseAfe() {
        CloseAFEModule();
    }

    public static void SetAdcGainOffset(AdcGainOffset adcGainOffset) {
        if (true) // according to eeprom setting
            SetADCGainOffset(adcGainOffset);
    }

    public static void GetAdcGainOffset(AdcGainOffset adcGainOffset) {
        GetADCGainOffset(adcGainOffset);
    }

    public static int SetAdcAutoCal() {
        RunAdcCalirationThr(0);
        return isAutoCalOk;
    }

    public static int GetAdcAutoCalStatus(){
    	return autoCalStatus;
    }
    
    public static int GetAdcAutoCalResult(){
    	return isAutoCalOk;
    }
    
    public static void UiSetVgaHpos(int uiValue) {
        eepromJNI.SaveVgaAdjustment(vdinJNI.GetSigFormat(),
            eepromJNI.VGAFunc.HPOSITION, uiValue);
        vgaTimingAdj.HPosition = 50 - uiValue;

        Log.d(DBTAG, "set vga h-pos = " + vgaTimingAdj.HPosition);
        SetVGACurTimingAdj(vgaTimingAdj);
    }

    public static void UiSetVgaVpos(int uiValue) {
        eepromJNI.SaveVgaAdjustment(vdinJNI.GetSigFormat(),
            eepromJNI.VGAFunc.VPOSITION, uiValue);
        vgaTimingAdj.VPosition = uiValue - 50;

        Log.d(DBTAG, "set vga v-pos = " + vgaTimingAdj.VPosition);
        SetVGACurTimingAdj(vgaTimingAdj);
    }

    public static void UiSetVgaClock(int uiValue) {
        eepromJNI.SaveVgaAdjustment(vdinJNI.GetSigFormat(),
            eepromJNI.VGAFunc.CLOCK, uiValue);
        vgaTimingAdj.Clock = uiValue - 50;

        Log.d(DBTAG, "set vga clock = " + vgaTimingAdj.Clock);
        SetVGACurTimingAdj(vgaTimingAdj);
    }

    public static void UiSetVgaPhase(int uiValue) {
        eepromJNI.SaveVgaAdjustment(vdinJNI.GetSigFormat(),
            eepromJNI.VGAFunc.PHASE, uiValue);

        if (uiValue >= 95)
            vgaTimingAdj.Phase = 31;
        else if (uiValue < 5)
            vgaTimingAdj.Phase = 0;
        else
            vgaTimingAdj.Phase = (uiValue - 2) / 3;

        Log.d(DBTAG, "set vga phase = " + vgaTimingAdj.Phase);

        SetVGACurTimingAdj(vgaTimingAdj);
    }

    public static int GetVgaHposition() {
        GetVGACurTimingAdj(vgaTimingAdj);
        return vgaTimingAdj.HPosition;
    }

    public static int GetVgaVposition() {
        GetVGACurTimingAdj(vgaTimingAdj);
        // Log.d(DBTAG, "vga v-position = " +
        // afeJNI.vgaTimingAdj.VPosition);
        return vgaTimingAdj.VPosition;
    }

    public static int GetVgaClcok() {
        GetVGACurTimingAdj(vgaTimingAdj);
        // Log.d(DBTAG, "vga clock = " + afeJNI.vgaTimingAdj.Clock);
        return vgaTimingAdj.Clock;
    }

    public static int GetVgaPhase() {
        GetVGACurTimingAdj(vgaTimingAdj);
        // Log.d(DBTAG, "vga phase = " + afeJNI.vgaTimingAdj.Phase);
        return vgaTimingAdj.Phase;
    }

    public static void ADCAutoCalibration() {
        final int TYP_COMPONENT = 0;
        final int TYP_VGA = 1;
        final int SIGNAL_RANGE_100 = 0;
        final int SIGNAL_RANGE_75 = 1;

        // if (vdinJNI.GetCurrentSrcInput() == vdinJNI.SrcId.COMP0.toInt()) {
        // Log.d(DBTAG, "Do COMP-0 auto calibration...");
        // ADCAutoCal(TYP_COMPONENT, SIGNAL_RANGE_100);
        // GetADCGainOffset(adcGainOffset);
        // eepromJNI.SaveADCGainOffset(eepromJNI.ADCSrcId.COMP0.ordinal(),
        // adcGainOffset);
        // } else if (vdinJNI.GetCurrentSrcInput() ==
        // vdinJNI.SrcId.COMP1.toInt()) {
        // Log.d(DBTAG, "Do COMP-1 auto calibration...");
        // ADCAutoCal(TYP_COMPONENT, SIGNAL_RANGE_100);
        // GetADCGainOffset(adcGainOffset);
        // eepromJNI.SaveADCGainOffset(eepromJNI.ADCSrcId.COMP1.ordinal(),
        // adcGainOffset);
        // } else if (vdinJNI.GetCurrentSrcInput() ==
        // vdinJNI.SrcId.VGA0.toInt()) {
        // Log.d(DBTAG, "Do VGA auto calibration...");
        // ADCAutoCal(TYP_VGA, SIGNAL_RANGE_100);
        // GetADCGainOffset(adcGainOffset);
        // eepromJNI.SaveADCGainOffset(eepromJNI.ADCSrcId.VGA.ordinal(),
        // adcGainOffset);
        // }
    }

    public static void RunAdcCalirationThr(int funcSel) {
    	if(autoCalStatus == AutoStauts.DOING.ordinal()){
    		Log.d(DBTAG,"ADC AutoCalibration is in process...return!");
    		return;
    	}

        Thread calibrationThr = new Thread(new AdcCalibrationThr(funcSel));
        calibrationThr.setName("AdcCalirationThr");
        calibrationThr.setPriority(Thread.NORM_PRIORITY);
        calibrationThr.start();
    }

    public static void RunVgaAutoAdjThr() {
        if (autoAdjStatus == AutoStauts.DOING.ordinal()){
    		Log.d(DBTAG,"VGA AutoAdjustment is in process...return!");
    		return;
    	}
        Thread vgaAutoAdjThr = new Thread(new AdcAutoAdjThr());
        vgaAutoAdjThr.setName("VgaAutoAdjThr");
        vgaAutoAdjThr.setPriority(Thread.NORM_PRIORITY);
        vgaAutoAdjThr.start();
    }

    public static void SetVGAAdjParam(VgaTimingAdj VGAAdjParam) {
        // VGAAdjParam.DbPrint();
        SetVGACurTimingAdj(VGAAdjParam);
    }

    public static int SetVgaEdidData() {
        int[] edidBuf = new int[256];

        edidBuf = eepromJNI.LoadVgaEdid();

        return SetVGAEdid(edidBuf);
    }

    public static void testADCSetGet() {
        adcGainOffset.a_analog_clamp = 0x10;
        adcGainOffset.a_analog_gain = 0x11;
        adcGainOffset.a_digital_offset1 = 0x12;
        adcGainOffset.a_digital_gain = 0x13;
        adcGainOffset.a_digital_offset2 = 0x14;

        adcGainOffset.b_analog_clamp = 0x20;
        adcGainOffset.b_analog_gain = 0x21;
        adcGainOffset.b_digital_offset1 = 0x22;
        adcGainOffset.b_digital_gain = 0x23;
        adcGainOffset.b_digital_offset2 = 0x24;

        adcGainOffset.c_analog_clamp = 0x30;
        adcGainOffset.c_analog_gain = 0x31;
        adcGainOffset.c_digital_offset1 = 0x32;
        adcGainOffset.c_digital_gain = 0x33;
        adcGainOffset.c_digital_offset2 = 0x34;

        SetADCGainOffset(adcGainOffset);
        GetADCGainOffset(adcGainOffset);

    }

    public static class AdcGainOffset implements Cloneable{
        // ADC A
        public int a_analog_clamp = 0x4b;
        public int a_analog_gain = 0xaf;
        public int a_digital_offset1 = 0x7f4;
        public int a_digital_gain = 0x37c;
        public int a_digital_offset2 = 0x40;
        // ADC B
        public int b_analog_clamp = 0x3f;
        public int b_analog_gain = 0x9b;
        public int b_digital_offset1 = 0x7fc;
        public int b_digital_gain = 0x3be;
        public int b_digital_offset2 = 0x40;
        // ADC C
        public int c_analog_clamp = 0x3f;
        public int c_analog_gain = 0xa7;
        public int c_digital_offset1 = 0x7fc;
        public int c_digital_gain = 0x39c;
        public int c_digital_offset2 = 0x40;
        // ADC D
        public int d_analog_clamp = 0x3f;
        public int d_analog_gain = 0x9f;
        public int d_digital_offset1 = 0x380;
        public int d_digital_gain = 0x4000;
        public int d_digital_offset2 = 0x40;

        public AdcGainOffset() {
        }

        public Object clone() {  
        	AdcGainOffset obj = null;  
    	        try {  
    	            obj = (AdcGainOffset) super.clone();  
    	        } catch (CloneNotSupportedException e) {  
    	            e.printStackTrace();  
    	        }  
    	        return obj;  
    	    } 
        
        public int[] ToByteArrary() {
            int[] retbuf = new int[40];

            retbuf[0] = this.a_analog_clamp & 0xff;
            retbuf[1] = this.a_analog_clamp >> 8;
            retbuf[2] = this.a_analog_gain & 0xff;
            retbuf[3] = this.a_analog_gain >> 8;
            retbuf[4] = this.a_digital_offset1 & 0xff;
            retbuf[5] = this.a_digital_offset1 >> 8;
            retbuf[6] = this.a_digital_gain & 0xff;
            retbuf[7] = this.a_digital_gain >> 8;
            retbuf[8] = this.a_digital_offset2 & 0xff;
            retbuf[9] = this.a_digital_offset2 >> 8;
            // ADC B
            retbuf[10] = this.b_analog_clamp & 0xff;
            retbuf[11] = this.b_analog_clamp >> 8;
            retbuf[12] = this.b_analog_gain & 0xff;
            retbuf[13] = this.b_analog_gain >> 8;
            retbuf[14] = this.b_digital_offset1 & 0xff;
            retbuf[15] = this.b_digital_offset1 >> 8;
            retbuf[16] = this.b_digital_gain & 0xff;
            retbuf[17] = this.b_digital_gain >> 8;
            retbuf[18] = this.b_digital_offset2 & 0xff;
            retbuf[19] = this.b_digital_offset2 >> 8;
            // ADC C
            retbuf[20] = this.c_analog_clamp & 0xff;
            retbuf[21] = this.c_analog_clamp >> 8;
            retbuf[22] = this.c_analog_gain & 0xff;
            retbuf[23] = this.c_analog_gain >> 8;
            retbuf[24] = this.c_digital_offset1 & 0xff;
            retbuf[25] = this.c_digital_offset1 >> 8;
            retbuf[26] = this.c_digital_gain & 0xff;
            retbuf[27] = this.c_digital_gain >> 8;
            retbuf[28] = this.c_digital_offset2 & 0xff;
            retbuf[29] = this.c_digital_offset2 >> 8;
            // ADC D
            retbuf[30] = this.d_analog_clamp & 0xff;
            retbuf[31] = this.d_analog_clamp >> 8;
            retbuf[32] = this.d_analog_gain & 0xff;
            retbuf[33] = this.d_analog_gain >> 8;
            retbuf[34] = this.d_digital_offset1 & 0xff;
            retbuf[35] = this.d_digital_offset1 >> 8;
            retbuf[36] = this.d_digital_gain & 0xff;
            retbuf[37] = this.d_digital_gain >> 8;
            retbuf[38] = this.d_digital_offset2 & 0xff;
            retbuf[39] = this.d_digital_offset2 >> 8;

            return retbuf;
        }

        public AdcGainOffset FromByteArrary(int buf[]) {
            // for(int j=0; j<40;j++){
            // Log.d(DBTAG,"buf-"+j+" = "+buf[j]);
            // }
            AdcGainOffset gainoffset = new AdcGainOffset();
            gainoffset.a_analog_clamp = buf[1] << 8 | buf[0];
            gainoffset.a_analog_gain = buf[3] << 8 | buf[2];
            gainoffset.a_digital_offset1 = buf[5] << 8 | buf[4];
            gainoffset.a_digital_gain = buf[7] << 8 | buf[6];
            gainoffset.a_digital_offset2 = buf[9] << 8 | buf[8];

            gainoffset.b_analog_clamp = buf[11] << 8 | buf[10];
            gainoffset.b_analog_gain = buf[13] << 8 | buf[12];
            gainoffset.b_digital_offset1 = buf[15] << 8 | buf[14];
            gainoffset.b_digital_gain = buf[17] << 8 | buf[16];
            gainoffset.b_digital_offset2 = buf[19] << 8 | buf[18];

            gainoffset.c_analog_clamp = buf[21] << 8 | buf[20];
            gainoffset.c_analog_gain = buf[23] << 8 | buf[22];
            gainoffset.c_digital_offset1 = buf[25] << 8 | buf[24];
            gainoffset.c_digital_gain = buf[27] << 8 | buf[26];
            gainoffset.c_digital_offset2 = buf[29] << 8 | buf[28];

            gainoffset.d_analog_clamp = buf[31] << 8 | buf[30];
            gainoffset.d_analog_gain = buf[33] << 8 | buf[32];
            gainoffset.d_digital_offset1 = buf[35] << 8 | buf[34];
            gainoffset.d_digital_gain = buf[37] << 8 | buf[36];
            gainoffset.d_digital_offset2 = buf[39] << 8 | buf[38];

            // Log.d("EEPROM", "a analog clamp =" + gainoffset.a_analog_clamp);
            // Log.d("EEPROM", "a analog gain =" + gainoffset.a_analog_gain);
            // Log.d("EEPROM", "b analog clamp =" + gainoffset.b_analog_clamp);
            // Log.d("EEPROM", "b analog gain =" + gainoffset.b_analog_gain);
            // Log.d("EEPROM", "c analog clamp =" + gainoffset.c_analog_clamp);
            // Log.d("EEPROM", "c analog gain =" + gainoffset.c_analog_gain);

            return gainoffset;
        }

        public void DbPrint() {
            final String TAG = "AFE";

            Log.d(TAG, "a_analog_clamp = " + a_analog_clamp);
            Log.d(TAG, "a_analog_gain = " + a_analog_gain);
            Log.d(TAG, "a_digital_offset1 = " + a_digital_offset1);
            Log.d(TAG, "a_digital_gain = " + a_digital_gain);
            Log.d(TAG, "a_digital_offset2 = " + a_digital_offset2);
            Log.d(TAG, "=====================================================");
            Log.d(TAG, "b_analog_clamp = " + b_analog_clamp);
            Log.d(TAG, "b_analog_gain = " + b_analog_gain);
            Log.d(TAG, "b_digital_offset1 = " + b_digital_offset1);
            Log.d(TAG, "b_digital_gain = " + b_digital_gain);
            Log.d(TAG, "b_digital_offset2 = " + b_digital_offset2);
            Log.d(TAG, "=====================================================");
            Log.d(TAG, "c_analog_clamp = " + c_analog_clamp);
            Log.d(TAG, "c_analog_gain = " + c_analog_gain);
            Log.d(TAG, "c_digital_offset1 = " + c_digital_offset1);
            Log.d(TAG, "c_digital_gain = " + c_digital_gain);
            Log.d(TAG, "c_digital_offset2 = " + c_digital_offset2);
            Log.d(TAG, "=====================================================");
            Log.d(TAG, "d_analog_clamp = " + d_analog_clamp);
            Log.d(TAG, "d_analog_gain = " + d_analog_gain);
            Log.d(TAG, "d_digital_offset1 = " + d_digital_offset1);
            Log.d(TAG, "d_digital_gain = " + d_digital_gain);
            Log.d(TAG, "d_digital_offset2 = " + d_digital_offset2);
            Log.d(TAG, "=====================================================");
        }
    }

    public static class ComponentWss {
        public long wss1[] = new long[5];
        public long wss2[] = new long[5];

        public long[] GetWSS1data() {
            return this.wss1;
        }

        public long[] GetWSS2data() {
            return this.wss2;
        }
    }

    public static class VgaTimingAdj {
        public int HPosition = 50;
        public int VPosition = 50;
        public int Clock = 50;
        public int Phase = 50;

        public VgaTimingAdj() {
        }

        public void DbPrint() {
            Log.d(DBTAG, "H position = " + this.HPosition);
            Log.d(DBTAG, "V position = " + this.VPosition);
            Log.d(DBTAG, "Clock = " + this.Clock);
            Log.d(DBTAG, "Phase = " + this.Phase);
        }
    }

    static {
        System.loadLibrary("afe_api");
    }

    static native int OpenAFEModule();

    static native void CloseAFEModule();

    static native int SetVGACurTimingAdj(VgaTimingAdj VGAAdjParam);

    static native int GetVGACurTimingAdj(VgaTimingAdj VGAAdjParam);

    static native int VGAAutoAdj(VgaTimingAdj VGAAdjParam);

    static native int ADCAutoCal(int typeSelect, int signalRangeSel); // 0-component,
                                                                      // 1-vga

    static native int GetADCGainOffset(AdcGainOffset adcGainOffset);

    static native int SetADCGainOffset(AdcGainOffset adcGainOffset);

    static native int GetYPbPrWSSInfo(ComponentWss WSSinfo);

    static native int GetMemData(int typeSelect);

    static native int SetVGAEdid(int[] edidData);

    static native int GetVGAEdid(int[] edidData);

    static native int NewADCAutoCal();

    public static native int CVBSLockStatus();

    public static native int SetCVBSStd(int value);
}
