package com.amlogic.android.tunerapi;

import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

import com.amlogic.android.afeapi.afeJNI;
import com.amlogic.android.audioctlapi.AudioCustom;
import com.amlogic.android.eepromapi.eepromJNI;
import com.amlogic.android.eepromapi.eepromJNI.*;
import com.amlogic.android.osdapi.osdJNI;
import com.amlogic.android.vdinapi.vdinJNI;
import com.amlogic.android.vppapi.vppJNI;
import com.amlogic.tvjni.GlobalConst;
import com.amlogic.tvjni.tvservice;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

class TunerInit {
    public static int MaxFreq = 868250000;
    public static int MinFreq = 44250000;
    public static int VHFLFreq = 143250000;
    public static int VHFHFreq = 426250000;
    public static int Tune_1MHz = 1000000;
    public static int Tune_2MHz = 2000000;
    public static int Tune_3MHz = 3000000;
    public static int Tune_6MHz = 6000000;
    public static int TUNER_WDIVIDE_STEP = 1;
    public static int AFCWIN_BEST_RANGE = 2;
}

class SearchThr implements Runnable {
    private int[] allchannelinfo = new int[1024];

    SearchThr() {
        if (tunerJNI.getAutoSearchOn() == true) {
            initAllChannelInfo();
        }
    }

    private void initAllChannelInfo() {
        allchannelinfo = eepromJNI.LoadAllCHInfo();
        int jump = 0x80;
        for (int i = 3; i < 1024; i = i + 4) {
            allchannelinfo[i] = jump;
        }
    }

    private void SaveAutoChInfo(int channelIdx) {
        tunerJNI.SaveChInfo2Eeprom(channelIdx);
        allchannelinfo[channelIdx * 4 + 0] = tunerJNI.ChInfo.volcomp;
        allchannelinfo[channelIdx * 4 + 1] = (tunerJNI.ChInfo.div & 0xff00) >> 8;
        allchannelinfo[channelIdx * 4 + 2] = tunerJNI.ChInfo.div & 0x00ff;
        allchannelinfo[channelIdx * 4 + 3] = 0x00 | 0x40
            | (tunerJNI.ChInfo.videostd << 4 & 0x0030)
            | (tunerJNI.ChInfo.soundstd & 0x0007);
    }

    public void run() {
        while ((tunerJNI.getSearchStop() == false)) {
            if (tunerJNI.getAutoSearchOn() == true) {
                if (tunerJNI.manuFreq >= TunerInit.MaxFreq) {
                    boolean test = false;
                    if (test) {
                        tunerJNI.manuFreq = TunerInit.MinFreq;
                        tunerJNI.SetTunerFreq(tunerJNI.manuFreq);
                        Log.d("VPP_JNI", "found ch = " + tunerJNI.num);
                    } else {
                        tunerJNI.setSearchStop(true);
                        // eepromJNI.cleanEmptyChannelSkip(tunerJNI.num);
                        eepromJNI.SaveAutoSearchInfo(allchannelinfo);
                        break;
                    }
                }
            }

            if (tunerJNI.TunerIsAFC() == 1
                && tunerJNI.GetTunerLockStatus() == 1) { // start 1
                if (tunerJNI.TunerIsBestLockFreq() == 1
                    && tunerJNI.GetTunerLockStatus() == 1) { // start 2
                    if (tunerJNI.getAutoSearchOn() == true) {
                        tunerJNI.mins5Freq();
                    }
                    tunerJNI.updateTunerFrequencyUI(tunerJNI.manuFreq);
                    Log.d("TUNER", "AFC get one channel !!!"
                        + tunerJNI.manuFreq);

                    if (tunerJNI.IsCVBSLock() == 1) { // start 3

                        if (tunerJNI.getAutoSearchOn() == false) { // start 4
                            // manual search find stop
                            tunerJNI.tunech = false;
                            Log.d("TUNER", "manual search break!!!");
                            tunerJNI.SaveChInfo2Eeprom(tunerJNI.num);
                            break;
                        } else {
                            // auto search tony add signal detect
                            tunerJNI.DelayMs(1000); // wait for cvd2
                            // tunerJNI.GetCHSoundStd();
                            // tunerJNI.GetCHVideoStd();
                            // tunerJNI.SaveChInfo2Eeprom(tunerJNI.num);
                            SaveAutoChInfo(tunerJNI.num);
                            tunerJNI.incSearchChannel();
                            tunerJNI.updateTunerChannelUI(tunerJNI.num);
                            tunerJNI.tunech = false;
                            tunerJNI.manuFreq += TunerInit.Tune_6MHz;
                            tunerJNI.CheckFreqRange(3);
                            tunerJNI.SetTunerFreq(tunerJNI.manuFreq);
                            Log.d("TUNER", "auto found channel add 6MHZ "
                                + tunerJNI.manuFreq);

                            tunerJNI.updateTunerFrequencyUI(tunerJNI.manuFreq);
                        }// end 4
                    } else {
                        tunerJNI.tunech = false;
                        if (tunerJNI.getSearchDown() == true) {
                            tunerJNI.manuFreq -= TunerInit.Tune_1MHz;

                            tunerJNI.CheckFreqRange(1);
                            tunerJNI.SetTunerFreq(tunerJNI.manuFreq);
                            Log.d("TUNER", "dec 1 MHZ " + tunerJNI.manuFreq);

                            tunerJNI.updateTunerFrequencyUI(tunerJNI.manuFreq);
                        } else {

                            if (tunerJNI.getAutoSearchOn() == true) {
                                tunerJNI.manuFreq += 1500000;// Add 1.5 MHZ
                                tunerJNI.CheckFreqRange(3);
                            } else {
                                tunerJNI.manuFreq += TunerInit.Tune_1MHz;
                                tunerJNI.CheckFreqRange(0);
                            }
                            tunerJNI.SetTunerFreq(tunerJNI.manuFreq);
                            Log.d("TUNER", "add 1 MHZ " + tunerJNI.manuFreq);

                            tunerJNI.updateTunerFrequencyUI(tunerJNI.manuFreq);
                        }
                    } // end 3
                } else {
                    tunerJNI.tunech = true;
                    if (tunerJNI.getSearchDown() == true) {
                        tunerJNI.ProcTunerAdujustFreq();
                        tunerJNI.CheckFreqRange(1);
                        tunerJNI.SetTunerFreq(tunerJNI.manuFreq);
                        tunerJNI.updateTunerFrequencyUI(tunerJNI.manuFreq);
                    } else {
                        if (tunerJNI.getAutoSearchOn() == true) {
                            tunerJNI.ProcTunerAdujustFreq();
                            tunerJNI.CheckFreqRange(3);
                        } else {
                            tunerJNI.ProcTunerAdujustFreq();
                            tunerJNI.CheckFreqRange(0);
                        }
                        tunerJNI.SetTunerFreq(tunerJNI.manuFreq);
                        tunerJNI.updateTunerFrequencyUI(tunerJNI.manuFreq);
                    }
                } // end 2
            } else {
                if (tunerJNI.getAutoSearchOn() == false) {
                    if (tunerJNI.getSearchDown() == true) { // down
                        tunerJNI.manuFreq -= TunerInit.Tune_1MHz;
                        tunerJNI.CheckFreqRange(1);
                        tunerJNI.SetTunerFreq(tunerJNI.manuFreq);
                        Log.d("TUNER", "out AFC Win dec 1MHZ "
                            + tunerJNI.manuFreq);

                        tunerJNI.updateTunerFrequencyUI(tunerJNI.manuFreq);
                    } else { // up
                        tunerJNI.manuFreq += TunerInit.Tune_1MHz;
                        tunerJNI.CheckFreqRange(0);
                        tunerJNI.SetTunerFreq(tunerJNI.manuFreq);
                        Log.d("TUNER", "out AFC Win add 1MHZ "
                            + tunerJNI.manuFreq);

                        tunerJNI.updateTunerFrequencyUI(tunerJNI.manuFreq);
                    }
                }
                if (tunerJNI.getAutoSearchOn() == true) { // autosearch
                    tunerJNI.manuFreq += TunerInit.Tune_1MHz;

                    tunerJNI.CheckFreqRange(3);
                    tunerJNI.minsFreq();
                    tunerJNI.SetTunerFreq(tunerJNI.manuFreq);
                    Log.d("TUNER", "auto out AFC Win add 1MHZ "
                        + tunerJNI.manuFreq);

                    tunerJNI.updateTunerFrequencyUI(tunerJNI.manuFreq);
                }

            }// end 1
            Thread.yield();
        }
        osdJNI.SetOSDColorkey(0, 1, 0);
        tunerJNI.setSearchDown(false);
        tunerJNI.searchflag = false;
        if (tunerJNI.getAutoSearchOn() == false) {
            if (tunerJNI.getSearchStop() == false) {
                // manualsearch finished
                tunerJNI.setSearchStop(true);
                tunerJNI.TunerManualSearchFinished();
            } else {
                // manualsearch abort
                tunerJNI.TunerManualSearchAborted();
            }
            if (!tvservice.inCloseTv) {
                tunerJNI.manualSetFreq(tunerJNI.num);
            }
        } else {
            // autosearch finished or abort
            tunerJNI.setAutoSearchOn(false);
            tunerJNI.TunerAutoSearchFinished();
            if (!tvservice.inCloseTv) {
                tunerJNI.num = GlobalConst.AUTO_SEARCH_START_CHANNEL;
                tunerJNI.manualSetFreq(tunerJNI.num); // show first channel
                eepromJNI.SaveTvCurrentChannel(tunerJNI.num);
            }
        }
        Log.d("TUNER", "in search thread, CVBS locked ");
    }
}

class AutoAFCThr implements Runnable {
    public void run() {
        while (tunerJNI.isTurnOnAutoAFCThr == true
            && vdinJNI.GetCurrentSrcInput() == vdinJNI.SrcId.CVBS0.toInt()) {
            if (tunerJNI.isStartAutoAFC() == true) {
                tunerJNI.autoAFC();
            }

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class tunerJNI {
    public static int num;
    public static int lastnum;
    public static int manuFreq;
    private static Handler mHandler = null;
    public static ChannelInfo ChInfo = new ChannelInfo();
    public static boolean searchflag = false;
    public static boolean changech = false;
    public static boolean isTurnOnAutoAFCThr = false;
    public static boolean isStopAutoAFCExecDone = true;
    public static boolean isStartAutoAFCThr = false;

    public static boolean AutoSearchOn = false;
    public static boolean SearchStop = true;
    public static boolean SearchDown = false;
    public static boolean tunech = false;

    public static void SearchChanel(boolean isAutoSearch, boolean isDown,
        boolean isStop) {
        if (isStop) {
            setSearchStop(true);
        } else {
            eepromJNI.SaveNavigateFlag(0);
            AudioCustom.AudioAmplifierMuteOn();
            AudioCustom.SetAudioLineOutMute(true);
            searchflag = true;
            waitThread();
            SetTunerStd(ChInfo.soundstd);

            if (isAutoSearch == true) {// auto search
                num = GlobalConst.AUTO_SEARCH_START_CHANNEL;
                manuFreq = TunerInit.MinFreq;
                SetTunerFreq(manuFreq);
                DelayMs(100);
                if (ChInfo.videostd == GlobalConst.CVBS_COLORSYS_AUTO) {
                    afeJNI.SetCVBSStd(GlobalConst.CVBS_COLORSYS_PAL);
                }
                updateTunerChannelUI(num);
                updateTunerFrequencyUI(manuFreq);
            } else {
                CheckFreqRange(2);

                if (isDown == true) { // manual down
                    if (afeJNI.CVBSLockStatus() != 0
                        || TunerIsBestLockFreq() == 1) {
                        manuFreq -= TunerInit.Tune_3MHz;
                    } else {
                        manuFreq -= TunerInit.Tune_1MHz;
                    }
                    CheckFreqRange(1);
                } else { // manual up
                    if (afeJNI.CVBSLockStatus() != 0
                        || TunerIsBestLockFreq() == 1) {
                        manuFreq += TunerInit.Tune_3MHz;
                    } else {
                        manuFreq += TunerInit.Tune_1MHz;
                    }
                    CheckFreqRange(0);
                }
                SetTunerFreq(manuFreq);
                DelayMs(100);
                if (ChInfo.videostd == GlobalConst.CVBS_COLORSYS_AUTO) {
                    afeJNI.SetCVBSStd(GlobalConst.CVBS_COLORSYS_PAL);
                }
                Log.d("TUNER", "set FREQ =" + manuFreq);
                updateTunerFrequencyUI(manuFreq);
            }

            setAutoSearchOn(isAutoSearch);
            setSearchStop(isStop);
            setSearchDown(isDown);
            Thread searchCh = new Thread(new SearchThr());
            searchCh.setName("TunerSearchThr");
            searchCh.start();
            if (vdinJNI.Get3DStatus() == 2) {
                vdinJNI.Set3DMode(vdinJNI.Mode3D.DISABLE.ordinal());
            }
        }
    }

    public static class ChannelInfo {
        public int volcomp; // 1 byte
        public int div; // 2 byte
        public boolean jump; // 1 bit
        public boolean aft; // 1 bit
        public int videostd; // 2 bit
        public int soundstd; // 3 bit
    }

    public static void SaveChInfo2Eeprom(int channelIdx) {
        ChInfo.volcomp = GlobalConst.PROGRAM_VOL_CORRECT_DEFAULT;
        ChInfo.div = Freq2Div(manuFreq);
        ChInfo.jump = false;
        ChInfo.aft = true;
        Log.d("TUNER", "channelInfo.jump= " + ChInfo.jump);
        Log.d("TUNER", "channelInfo.aft= " + ChInfo.aft);
        Log.d("TUNER", "channelInfo.num= " + channelIdx);
        Log.d("TUNER", "channelInfo.div= " + ChInfo.div);
        Log.d("TUNER", "channelInfo.videostd= " + ChInfo.videostd);
        Log.d("TUNER", "channelInfo.soundstd= " + ChInfo.soundstd);
        Log.d("TUNER", "channelInfo.volcomp= " + ChInfo.volcomp);
        eepromJNI.SaveChannelInfo(channelIdx, ChInfo);
    }

    public static void LoadChInfoFromEeprom(int channelIdx) {
        ChInfo = eepromJNI.LoadChannelInfo(channelIdx);
        Log.d("TUNER", "LoadChInfoFromEeporm.jump= " + ChInfo.jump);
        Log.d("TUNER", "LoadChInfoFromEeporm.aft= " + ChInfo.aft);
        Log.d("TUNER", "LoadChInfoFromEeporm.num= " + channelIdx);
        Log.d("TUNER", "LoadChInfoFromEeporm.div= " + ChInfo.div);
        Log.d("TUNER", "LoadChInfoFromEeporm.videostd= " + ChInfo.videostd);
        Log.d("TUNER", "LoadChInfoFromEeporm.soundstd= " + ChInfo.soundstd);
        Log.d("TUNER", "LoadChInfoFromEeporm.volcomp= " + ChInfo.volcomp);
    }

    public static int Freq2Div(int freq) {
        int temp = 0, div = 0;
        int step_size = 50000, f_if = 38900000;
        // Log.d("TUNER", "Freq2Div freq = " + freq);
        temp = (freq + f_if) / step_size;
        div = temp;
        // Log.d("TUNER", "Freq2Div div = " + div);
        return div;
    }

    public static int Div2Freq(int div) {
        int temp = 0, freq = 0;
        int step_size = 50000, f_if = 38900000;
        // Log.d("TUNER", "Div2Freq div = " + div);
        temp = (div * step_size) - f_if;
        freq = temp;
        // Log.d("TUNER", "Div2Freq freq = " + freq);
        return freq;
    }

    public enum VDStd {
        AUTO, PAL, NTSC
    }

    public enum TunerStd {
        DK, I, BG, M, AUTO
    }

    public static void SetVideoSTD(VDStd Std) {
        switch (Std) {
            case AUTO:
                afeJNI.SetCVBSStd(GlobalConst.CVBS_COLORSYS_AUTO);
                ChInfo.videostd = GlobalConst.CVBS_COLORSYS_AUTO;
                break;
            case PAL:
                afeJNI.SetCVBSStd(GlobalConst.CVBS_COLORSYS_PAL);
                ChInfo.videostd = GlobalConst.CVBS_COLORSYS_PAL;
                break;
            case NTSC:
                afeJNI.SetCVBSStd(GlobalConst.CVBS_COLORSYS_NTSC);
                ChInfo.videostd = GlobalConst.CVBS_COLORSYS_NTSC;
                break;
            default:
                afeJNI.SetCVBSStd(GlobalConst.CVBS_COLORSYS_PAL);
                ChInfo.videostd = GlobalConst.CVBS_COLORSYS_PAL;
                break;
        }
    }

    public static void SetSoundSTD(TunerStd Std) {
        switch (Std) {
            case DK:
                SetTunerStd(GlobalConst.TV_CHANNEL_SOUNDSYS_DK);
                ChInfo.soundstd = GlobalConst.TV_CHANNEL_SOUNDSYS_DK;
                break;
            case I:
                SetTunerStd(GlobalConst.TV_CHANNEL_SOUNDSYS_I);
                ChInfo.soundstd = GlobalConst.TV_CHANNEL_SOUNDSYS_I;
                break;
            case BG:
                SetTunerStd(GlobalConst.TV_CHANNEL_SOUNDSYS_BG);
                ChInfo.soundstd = GlobalConst.TV_CHANNEL_SOUNDSYS_BG;
                break;
            case M:
                SetTunerStd(GlobalConst.TV_CHANNEL_SOUNDSYS_M);
                ChInfo.soundstd = GlobalConst.TV_CHANNEL_SOUNDSYS_M;
                break;
            case AUTO:
                GetCHSoundStd();
                ChInfo.soundstd = GlobalConst.TV_CHANNEL_SOUNDSYS_AUTO;
                break;
            default:
                SetTunerStd(GlobalConst.TV_CHANNEL_SOUNDSYS_DK);
                ChInfo.soundstd = GlobalConst.TV_CHANNEL_SOUNDSYS_DK;
                break;
        }
        eepromJNI.SaveChannelInfoSoundstd(num, ChInfo.soundstd);
    }

    public static void GetCHSoundStd() {
        if (ChInfo.soundstd == GlobalConst.TV_CHANNEL_SOUNDSYS_AUTO) {
            int i = 0;

            while (i < 4) {
                SetTunerStd(i);
                if (GetAudioStatus() == 1) {
                    break;
                }
                i++;
            }
            Log.d("TUNER", "AUTO GET SOUND STD = " + i);
        }
    }

    public static int GetAudioStatus() {
        int value;
        value = (GetTunerAFCVal() & 0x20); // IF bit5 FM carrier detect
        Log.d("TUNER", "IF bit 5 = " + Integer.toHexString(value));
        if (value == 0x20) {
            return 1;
        } else {
            return 0;
        }
    }

    public static void GetCHVideoStd() {
        if (ChInfo.videostd == GlobalConst.CVBS_COLORSYS_AUTO) {
            int VideoStd;
            VideoStd = vdinJNI.GetSigFormat();
            Log.d("TUNER", "SigFormat = " + VideoStd);
            if (VideoStd == vdinJNI.SigFormat.CVBS_NTSC_M.ordinal()) {
                afeJNI.SetCVBSStd(GlobalConst.CVBS_COLORSYS_NTSC);
            } else if (VideoStd == vdinJNI.SigFormat.CVBS_PAL_I.ordinal()) {
                afeJNI.SetCVBSStd(GlobalConst.CVBS_COLORSYS_PAL);
            } else {
                afeJNI.SetCVBSStd(GlobalConst.CVBS_COLORSYS_PAL);
                Log.d("TUNER", "Other STD Set CVD2 PAL_I");
            }
        }
    }

    public static void muteTunerAudio() {
        /* IF Switching(B data) bit5 forced mute audio */
        SetTunerStd(GlobalConst.TV_CHANNEL_SOUNDSYS_MUTE);
    }

    public static void manualSetFreq(int channel) {
        tunerJNI.LoadChInfoFromEeprom(channel);
        int tempfreq = Div2Freq(ChInfo.div);
        if (tempfreq <= TunerInit.MinFreq || tempfreq >= TunerInit.MaxFreq) {
            tempfreq = TunerInit.MinFreq;
        }
        SetTunerStd(ChInfo.soundstd);
        SetTunerFreq(tempfreq);
        afeJNI.SetCVBSStd(ChInfo.videostd);
        manuFreq = tempfreq;
        num = channel;
        Log.d("TUNER", "manual write Freq = " + tempfreq);
        if (vdinJNI.channelchange != true) {
            DelayMs(800);
            for (int i = 0; i < 10; i++) {
                if (vdinJNI.GetSigStatus() == vdinJNI.SigStatus.STABLE.ordinal()) {
                    AudioCustom.AudioAmplifierMuteOff();
                    AudioCustom.SetAudioLineOutMute(false);
                    Log.d("TUNER", "open sound");
                    break;
                } else {
                    DelayMs(100);
                }
            }
        }
        changech = true;
        startAuotAFC();
    }

    public static void FastSetFreq(int channel) {
        stopAutoAFC();
        AudioCustom.AudioAmplifierMuteOn();
        AudioCustom.SetAudioLineOutMute(true);
        // AudioCustom.AudioMainVolumeMuteOn();

        if (vdinJNI.Get3DStatus() == 2) {
            vdinJNI.TurnOnBlueScreen(2);
            Log.d("TUNER", "Enable blackscreen in tuner FastSetFreq when 2D->3D");
        }
        vdinJNI.StopDecoder(0);
        int tempfreq = Div2Freq(ChInfo.div);
        if (tempfreq <= TunerInit.MinFreq || tempfreq >= TunerInit.MaxFreq) {
            tempfreq = TunerInit.MinFreq;
        }
        SetTunerStd(ChInfo.soundstd);
        SetTunerFreq(tempfreq);
        manuFreq = tempfreq;
        num = channel;
        Log.d("TUNER", "Fast write Freq = " + tempfreq);
        afeJNI.SetCVBSStd(ChInfo.videostd);
        DelayMs(500);
        if (afeJNI.CVBSLockStatus() != 0) {
            AudioCustom.SetAudioLineOutMute(false);
        }
        vdinJNI.SetCheckStableCount(1);
        changech = true;
        startAuotAFC();
    }

    public static void setTunerChannel(int channel) {
        LoadChInfoFromEeprom(channel);
        FastSetFreq(channel);
    }

    public static void setTunerLastChannel() {
        tunerJNI.lastnum = tunerJNI.num;
    }

    public static int getTunerLastChannel() {
        if (tunerJNI.lastnum < GlobalConst.TV_CHANNEL_MIN_NUMBER
            || tunerJNI.lastnum >= GlobalConst.TV_CHANNEL_TOTAL_PROGRAM_COUNT)
            tunerJNI.lastnum = GlobalConst.TV_CHANNEL_DEFAULT_NUMBER;
        return tunerJNI.lastnum;
    }

    public static void changeCurrentChannelToChannel(int channelIdx) {
        if (channelIdx < GlobalConst.TV_CHANNEL_MIN_NUMBER
            || channelIdx >= GlobalConst.TV_CHANNEL_TOTAL_PROGRAM_COUNT)
            channelIdx = GlobalConst.TV_CHANNEL_DEFAULT_NUMBER;
        tunerJNI.setTunerLastChannel();
        tunerJNI.num = channelIdx;
        if (tunerJNI.num != eepromJNI.LoadTvCurrentChannel()) {
            tunerJNI.setTunerChannel(tunerJNI.num);
            eepromJNI.SaveTvCurrentChannel(tunerJNI.num);
        }
    }

    public static void changeCurrentChannelToLastChannel() {
        int tmpnum;
        tmpnum = getTunerLastChannel();
        setTunerLastChannel();
        tunerJNI.num = tmpnum;
    }

    public static void FintTune(int val) {
        ChInfo.aft = false;
        manuFreq = manuFreq + val;
        CheckFreqRange(2);
        SetTunerFreq(manuFreq);
        ChInfo.div = Freq2Div(manuFreq);
        eepromJNI.SaveChannelInfoFreq(num, ChInfo.div);
        eepromJNI.SaveChannelInfoAft(num, ChInfo.aft);
        updateTunerFrequencyUI(manuFreq);
    }

    public static void setChannelJump(boolean chjump) {
        if (chjump) {
            ChInfo.jump = true;
            eepromJNI.SaveChannelInfoJump(num, ChInfo.jump);
        } else {
            ChInfo.jump = false;
            eepromJNI.SaveChannelInfoJump(num, ChInfo.jump);
        }
    }

    public static int changeTunerChannel(boolean direction) {
        int channelIdx = num;
        do {
            if (direction) {
                if (channelIdx < (GlobalConst.TV_CHANNEL_TOTAL_PROGRAM_COUNT - 1)) {
                    channelIdx++;
                } else {
                    channelIdx = GlobalConst.TV_CHANNEL_MIN_NUMBER;
                }
            } else {
                if (channelIdx > GlobalConst.TV_CHANNEL_MIN_NUMBER) {
                    channelIdx--;
                } else {
                    channelIdx = GlobalConst.TV_CHANNEL_TOTAL_PROGRAM_COUNT - 1;
                }
            }
        } while ((eepromJNI.LoadChannelInfoJump(channelIdx) == true)
            && (channelIdx != num));

        return channelIdx;
        // changeCurrentChannelToChannel(channelIdx);
    }

    public static void exchangeChannelInfo(int sourceCh, int targetCh) {
        ChannelInfo SourceInfo = new ChannelInfo();
        ChannelInfo TargetChInfo = new ChannelInfo();
        SourceInfo = eepromJNI.LoadChannelInfo(sourceCh);
        TargetChInfo = eepromJNI.LoadChannelInfo(targetCh);
        eepromJNI.SaveChannelInfo(sourceCh, TargetChInfo);
        eepromJNI.SaveChannelInfo(targetCh, SourceInfo);
    }

    public static int getMaxFreq() {// GetFreqRangeHigh()
        return TunerInit.MaxFreq;
    }

    public static int getMinFreq() {// GetFreqRangeLow()
        return TunerInit.MinFreq;
    }

    public static int getMaxVHFLFreq() {
        return TunerInit.VHFLFreq;
    }

    public static int getMaxVHFHFreq() {
        return TunerInit.VHFHFreq;
    }

    public static void SetFreqRange(int newband) {
        int oriFreq = eepromJNI.LoadChannelInfoFreq(num);
        if (newband == getChannelBand(oriFreq)) {
            manuFreq = oriFreq;
        } else {
            float rate = 0;

            int curBand = getChannelBand(manuFreq);
            switch (curBand) {
                case 0:
                    rate = ((float) manuFreq - TunerInit.MinFreq)
                        / (TunerInit.VHFLFreq - TunerInit.MinFreq);
                    break;
                case 1:
                    rate = ((float) manuFreq - TunerInit.VHFLFreq)
                        / (TunerInit.VHFHFreq - TunerInit.VHFLFreq);
                    break;
                case 2:
                    rate = ((float) manuFreq - TunerInit.VHFHFreq)
                        / (TunerInit.MaxFreq - TunerInit.VHFHFreq);
                    break;
                default:
                    break;
            }
            DecimalFormat df = new DecimalFormat("0.0");
            rate = Float.parseFloat(df.format(rate));

            switch (newband) {
                case 0:
                    manuFreq = TunerInit.MinFreq
                        + (int) ((TunerInit.VHFLFreq - TunerInit.MinFreq) * rate);
                    break;
                case 1:
                    manuFreq = TunerInit.VHFLFreq
                        + (int) ((TunerInit.VHFHFreq - TunerInit.VHFLFreq) * rate);
                    break;
                case 2:
                    manuFreq = TunerInit.VHFHFreq
                        + (int) ((TunerInit.MaxFreq - TunerInit.VHFHFreq) * rate);
                    break;
                default:
                    break;
            }
        }
        SetTunerFreq(manuFreq);
    }

    public static int getChannelBand(int freq) {
        int band = 0;
        if (freq > TunerInit.VHFHFreq)
            band = 2;
        else if (freq > TunerInit.VHFLFreq)
            band = 1;
        return band;
    }

    public static void CheckFreqRange(int value) {

        switch (value) {
            case 0:
                if (manuFreq > TunerInit.MaxFreq)
                    manuFreq = TunerInit.MinFreq;
                break;
            case 1:
                if (manuFreq < TunerInit.MinFreq)
                    manuFreq = TunerInit.MaxFreq;
                break;
            case 2:
                if (manuFreq > TunerInit.MaxFreq)
                    manuFreq = TunerInit.MinFreq;
                else if (manuFreq < TunerInit.MinFreq)
                    manuFreq = TunerInit.MaxFreq;
                break;
            case 3:
                if (manuFreq > TunerInit.MaxFreq)
                    manuFreq = TunerInit.MaxFreq;
                break;
        }
    }

    public static int IsCVBSLock() {
        int i = 0, rd_val, return_val = 0;
        if (tunech == false) {
            DelayMs(100);
        }
        while (i < 5) {
            rd_val = afeJNI.CVBSLockStatus();
            Log.d("TUNER", "CVBSLock = " + rd_val);
            // if (rd_val != 0) {
            if (rd_val == 1 || rd_val == 3) {
                return_val = 1;
                break;
            }
            i++;
            DelayMs(50); // need wait 30ms
        }
        return return_val;
    }

    public static int TunerIsAFC() {
        int value;
        value = GetTunerAFCVal();
        if ((value & 0x80) == 0x80 && IsCVBSLock() != 0) {
            return 1;
        } else {
            return 0;
        }
    }

    public static int TunerIsBestLockFreq() {
        int value;
        value = (GetTunerAFCVal() & 0x1e) >> 1;
        if (value >= 0x08)
            value = 0x0f - value;
        if (value <= TunerInit.AFCWIN_BEST_RANGE) {
            return 1;
        } else {
            return 0;
        }
    }

    public static void ProcTunerAdujustFreq() {
        int bAFCStatus, fSigned = 0, AFCStatus = 0, wdivider;
        // get AFC Value
        bAFCStatus = (GetTunerAFCVal() & 0x1e) >> 1;
        // current freq > IF
        if (bAFCStatus >= 0x08) {
            fSigned = 1;
            AFCStatus = 0x0f - bAFCStatus;// convert 8-15 to 7-0
        } else {
            AFCStatus = bAFCStatus;
        }

        while (AFCStatus > TunerInit.AFCWIN_BEST_RANGE) {
            wdivider = Freq2Div(manuFreq);
            AFCStatus = AFCStatus >> 1;
            if (fSigned == 1) {
                wdivider -= AFCStatus;
            } else {
                wdivider += AFCStatus;
            }

            manuFreq = Div2Freq(wdivider);
            if (TunerIsAutoAFC() == 0 || manuFreq > TunerInit.MaxFreq
                || manuFreq < TunerInit.MinFreq) {
                if (getSearchDown() == true) {
                    manuFreq -= TunerInit.Tune_2MHz;
                } else {
                    manuFreq += TunerInit.Tune_2MHz;
                }
                Log.d("TUNER", "TunerIsAutoAFC manuFreq = " + manuFreq);
                return;
            }
            SetTunerFreq(manuFreq);
            Log.d("TUNER", "!!!!!after ADJ freq ===" + manuFreq);
        }
    }

    public static void mins5Freq() {
        int i = manuFreq;
        i = (i % 1000000) / 10000;
        if (i == 30 || i == 80) {
            manuFreq -= 50000;
        } else {
            return;
        }

        // Log.d("TUNER","mins5Freq = " + manuFreq);
        SetTunerFreq(manuFreq);
    }

    public static void minsFreq() {
        int i = manuFreq;
        int x = (i % 1000000) / 10000;
        int y = (i % 1000000000) / 1000000;
        if (x != 25) {
            manuFreq = y * 1000000 + 250000;
        } else {
            return;
        }
    }

    public static int TunerIsAutoAFC() {
        int value;
        value = GetTunerAFCVal();
        if ((value & 0x80) == 0x80) {
            return 1;
        } else {
            return 0;
        }
    }

    public static void autoAFC() {
        if (isStartAutoAFC() == false) {
            return;
        }
        isStopAutoAFCExecDone = false;
        if (changech == true) {
            if (TunerIsAFC() == 1) {
                changech = false;
                Log.d("TUNER", "coarse tune CVBSLock exit changech = "
                    + changech);
                return;
            }

            changech = false;
            int curFreq, minFreq, maxFreq;
            curFreq = eepromJNI.LoadChannelInfoFreq(num);
            minFreq = curFreq - 2500000;
            maxFreq = curFreq + 2500000;
            SetTunerFreq(minFreq);
            manuFreq = minFreq;
            // Log.d("TUNER","coarse tune Set MINFreq = " + manuFreq);
            while (isTurnOnAutoAFCThr == true && isStartAutoAFCThr == true
                && TunerIsAFC() == 0 && manuFreq <= maxFreq
                && manuFreq >= minFreq) {
                manuFreq += 1000000;
                SetTunerFreq(manuFreq);
                // Log.d("TUNER","coarse tune SetTunerFreq = " + manuFreq);
                DelayMs(200);
                if (manuFreq > maxFreq || manuFreq < minFreq) {
                    SetTunerFreq(curFreq);
                    manuFreq = curFreq;
                    protectFreq();
                    // Log.d("TUNER","coarse tune out range SetCurFreq = " +
                    // manuFreq);
                    return;
                }
            }
            if (IsCVBSLock() == 0) {
                SetTunerFreq(curFreq);
                manuFreq = curFreq;
                // Log.d("TUNER","coarse tune CVBSLock 0 SetCurFreq = " +
                // manuFreq);
            }
            protectFreq();
            // Log.d("TUNER","EXIT coarse tune SetFreq = " + manuFreq);
        } else {
            int bAFCStatus, fSigned = 0, AFCStatus = 0, wdivider, curFreq, minFreq, maxFreq;
            curFreq = eepromJNI.LoadChannelInfoFreq(num);
            minFreq = curFreq - 2500000;
            maxFreq = curFreq + 2500000;

            if (isTurnOnAutoAFCThr == true && isStartAutoAFCThr == true
                && TunerIsBestLockFreq() != 1 && manuFreq <= maxFreq
                && manuFreq >= minFreq) {
                bAFCStatus = (GetTunerAFCVal() & 0x1e) >> 1;
                if (bAFCStatus >= 0x08) {
                    fSigned = 1;
                    AFCStatus = 0x0f - bAFCStatus;
                } else {
                    AFCStatus = bAFCStatus;
                }

                wdivider = Freq2Div(manuFreq);
                AFCStatus = AFCStatus >> 1;
                if (fSigned == 1) {
                    wdivider -= AFCStatus;
                } else {
                    wdivider += AFCStatus;
                }

                manuFreq = Div2Freq(wdivider);
                if (manuFreq > maxFreq || manuFreq < minFreq
                    || TunerIsAutoAFC() == 0) {
                    SetTunerFreq(curFreq);
                    manuFreq = curFreq;
                    protectFreq();
                    // Log.d("TUNER","fine tune out range SetCurFreq = " +
                    // manuFreq);
                    return;
                } else {
                    SetTunerFreq(manuFreq);
                    // Log.d("TUNER","fine tune SetTunerFreq = " + manuFreq);
                    if (TunerIsAutoAFC() == 0) {
                        SetTunerFreq(curFreq);
                        manuFreq = curFreq;
                        protectFreq();
                        // Log.d("TUNER","fine tune out AFC SetCurFreq = " +
                        // manuFreq);
                        return;
                    }
                }
                protectFreq();
            }
        }
        isStopAutoAFCExecDone = true;
    }

    public static void waitThread() {
        for (int i = 0; i < 10; i++) {
            if (isStopAutoAFCExecDone == true) {
                // Log.d("TUNER","wait last run finish isStopAutoAFCExecDone break = "
                // + isStopAutoAFCExecDone);
                break;
            }
            DelayMs(100);
            // Log.d("TUNER","wait last run finish isStopAutoAFCExecDone = " +
            // isStopAutoAFCExecDone);
        }
    }

    public static void protectFreq() {
        if (changech == true) {
            int tempFreq = eepromJNI.LoadChannelInfoFreq(num);
            if (manuFreq != tempFreq) {
                manuFreq = tempFreq;
                SetTunerFreq(manuFreq);
                // Log.d("TUNER","protectFreq changech == true SetCurFreq = " +
                // manuFreq);
            }
        }
    }

    public static boolean isStartAutoAFC() {
        if (isTurnOnAutoAFCThr == true && isStartAutoAFCThr == true
            && eepromJNI.IsBusOff() == false
            && eepromJNI.IsBurnFlagOn() == false && searchflag == false
            && ChInfo.jump == false && ChInfo.aft == true) {
            return true;
        } else {
            return false;
        }
    }

    public static void CreateAutoAFCThr() {
        if (isTurnOnAutoAFCThr == true) {
            return;
        }
        Thread autoAFCThr = new Thread(new AutoAFCThr());
        autoAFCThr.setName("AutoAFCThr");
        isTurnOnAutoAFCThr = true;
        isStartAutoAFCThr = false;
        autoAFCThr.start();
        Log.d("TUNER", "start autoafc Thread");
    }

    public static void KillAutoAFCThr() {
        isTurnOnAutoAFCThr = false;
        changech = false;
        for (int i = 0; i < 10; i++) {
            if (isStopAutoAFCExecDone == false) {
                DelayMs(100);
            }
        }
        Log.d("TUNER", "kill autoafc Thread ok");
    }

    public static void startAuotAFC() {
        isStartAutoAFCThr = true;
        Log.d("TUNER", "start AutoAFC");
    }

    public static void stopAutoAFC() {
        isStartAutoAFCThr = false;
        waitThread();
        Log.d("TUNER", "stop AutoAFC");
    }

    public static int AddVolComp(int vol) {
        int newVol;
        newVol = vol + ChInfo.volcomp - 10;

        if (newVol < GlobalConst.MIN_VALUE_MENU_SHOW) {
            newVol = GlobalConst.MIN_VALUE_MENU_SHOW;
        } else if (newVol > GlobalConst.MAX_VALUE_MENU_SHOW) {
            newVol = GlobalConst.MAX_VALUE_MENU_SHOW;
        }
        Log.d("TUNER", "set mainvol = " + newVol);
        return newVol;
    }

    public static void factoryBurnMode(boolean onoff) {
        if (onoff) {
            eepromJNI.SaveFactorySetting(FactoryFunc.BURN_FLAG, GlobalConst.BURN_FLAG_ON);
            AudioCustom.AudioAmplifierMuteOn();
            AudioCustom.SetAudioLineOutMute(true);
            vdinJNI.StopSigDetectThr();
            vdinJNI.StopDecoder(0);
            stopAutoAFC();
            muteTunerAudio();
            SetTunerFreq(826000000);
            vppJNI.SetScreenColor(0, 235, 128, 128);
            // eepromJNI.setBurnDefault();
        } else {
            eepromJNI.SaveFactorySetting(FactoryFunc.BURN_FLAG, 0);
            num = eepromJNI.LoadTvCurrentChannel();
            setTunerLastChannel();
            vdinJNI.StopDecoder(0);
            manualSetFreq(tunerJNI.num);
            vdinJNI.StartSigDetectThr();
        }
    }

    public static void DelayMs(int ms) {
        try {
            TimeUnit.MILLISECONDS.sleep(ms);
        } catch (InterruptedException e) {
            Log.d("TV JAVA", "delay error!!");
            e.printStackTrace();
        }
    }

    /*********************************************************************
     * UI Relate Function
     ********************************************************************/
    public static void setSearchHandler(Handler tmpHandler) {
        mHandler = tmpHandler;
    }

    private static void tunerSendMessage(int MsgType, int arg2, Bundle b) {
        if (mHandler != null) {
            Message msg = new Message();
            if (msg != null) {
                msg.arg1 = MsgType;
                msg.arg2 = arg2;
                msg.setData(b);
                mHandler.sendMessage(msg);
            }
        }
    }

    private static void sendMessageToUI(int MsgType, String name, int value) {
        Bundle b = new Bundle();

        if (b != null) {
            b.putInt(name, value);

            tunerSendMessage(MsgType, 0, b);
        }
    }

    public static int getCurrnetChNumber() {
        return tunerJNI.num;
    }

    public static boolean getAutoSearchOn() {
        return tunerJNI.AutoSearchOn;
    }

    public static boolean getSearchStop() {
        return tunerJNI.SearchStop;
    }

    public static boolean getSearchDown() {
        return tunerJNI.SearchDown;
    }

    public static void setAutoSearchOn(boolean flag) {
        tunerJNI.AutoSearchOn = flag;
    }

    public static void setSearchStop(boolean flag) {
        tunerJNI.SearchStop = flag;
    }

    public static void setSearchDown(boolean flag) {
        tunerJNI.SearchDown = flag;
    }

    public static void incSearchChannel() {
        if (num < (GlobalConst.TV_CHANNEL_TOTAL_PROGRAM_COUNT - 1)) {
            num++;
        } else {
            num = GlobalConst.TV_CHANNEL_TOTAL_PROGRAM_COUNT - 1;
        }
    }

    public static void TunerAutoSearchFinished() {
        tunerJNI.sendMessageToUI(GlobalConst.MSG_TUNER_AUTOSEARCH_FINISHED, "tuner_auto_search_finished", 0);
    }

    public static void TunerManualSearchFinished() {
        tunerJNI.sendMessageToUI(GlobalConst.MSG_TUNER_MANUALSEARCH_FINISHED, "tuner_manual_search_finished", 0);
    }

    public static void TunerManualSearchAborted() {
        tunerJNI.sendMessageToUI(GlobalConst.MSG_TUNER_MANUALSEARCH_ABORTED, "tuner_manual_search_aborted", 0);
    }

    public static void updateTunerFrequencyUI(int freq) {
        sendMessageToUI(GlobalConst.MSG_TUNER_UPDATE_FREQUENCY, "tuner_freq", freq);
    }

    public static void updateTunerChannelUI(int channel) {
        sendMessageToUI(GlobalConst.MSG_TUNER_FIND_CHANNEL, "tuner_channel", channel);
    }

    // ===================================================================
    // methods
    // ===================================================================
    static {
        System.loadLibrary("tuner_api");
    }

    public static native int OpenTuner();

    public static native void CloseTuner();

    public static native long GetTunerStd();

    public static native int SetTunerStd(int value);

    public static native int GetTunerFreq();

    public static native int SetTunerFreq(int setfreq);

    public static native int SetIfValue(int setfreq, int value);

    public static native int GetFreqRangeLow();

    public static native int GetFreqRangeHigh();

    public static native int GetTunerLockStatus();

    public static native int GetTunerAFCVal();
}
