package com.amlogic.android.audioctlapi;

import android.util.Log;

import com.amlogic.tvjni.GlobalConst;
import com.amlogic.android.eepromapi.eepromJNI;
import com.amlogic.android.osdapi.osdJNI;
import com.amlogic.android.tunerapi.tunerJNI;
import com.amlogic.android.vdinapi.vdinJNI;

public class AudioCustom {
    private final static String DBTAG = "TV_AUDIO";
    // audio source in type
    public static final int AUDIO_SOURCE_IN_LINE_IN = 0;
    public static final int AUDIO_SOURCE_IN_HDMI = 1;

    // audio effect EQ mode type
    // public static final int CC_EQ_MODE_START = 0;
    //
    // public static final int CC_EQ_MODE_NOMAL = CC_EQ_MODE_START + 0;
    // public static final int CC_EQ_MODE_POP = CC_EQ_MODE_START + 1;
    // public static final int CC_EQ_MODE_JAZZ = CC_EQ_MODE_START + 2;
    // public static final int CC_EQ_MODE_ROCK = CC_EQ_MODE_START + 3;
    // public static final int CC_EQ_MODE_CLASSIC = CC_EQ_MODE_START + 4;
    // public static final int CC_EQ_MODE_DANCE = CC_EQ_MODE_START + 5;
    // public static final int CC_EQ_MODE_PARTY = CC_EQ_MODE_START + 6;
    // public static final int CC_EQ_MODE_BASS = CC_EQ_MODE_START + 7;
    // public static final int CC_EQ_MODE_TREBLE = CC_EQ_MODE_START + 8;
    // public static final int CC_EQ_MODE_CUSTOM = CC_EQ_MODE_START + 9;
    //
    // public static final int CC_EQ_MODE_END = CC_EQ_MODE_CUSTOM;

    public static final int CC_EQ_BAND_CNT = 6;

    public static final int CC_MUTE_OFF = 1;
    public static final int CC_MUTE_ON = 0;

    public static final int CC_SWITCH_OFF = 0;
    public static final int CC_SWITCH_ON = 1;

    public static final int CC_DEF_SOUND_VOL = 22;
    public static final int CC_DEF_BASS_TREBLE_VOL = 50;
    public static final int CC_DEF_SUPPERBASS_VOL = 50;
    public static final int CC_MIN_SOUND_VOL = 0;
    public static final int CC_MAX_SOUND_VOL = 100;
    public static final int CC_MIN_SUPPERBASS_VOL = 0;
    public static final int CC_MAX_SUPPERBASS_VOL = 100;
    public static final int CC_MIN_SOUND_BALANCE_VAL = -50;
    public static final int CC_MAX_SOUND_BALANCE_VAL = 50;
    public static final int CC_DEF_SOUND_BALANCE_VAL = 0;
    public static final int CC_SRS_TRUBASS_GAIN_VAL = 0;
    public static final int CC_SRS_DIALOG_CLARITY_GAIN_VAL = 0;
    public static final int CC_MIN_EQ_GAIN_VAL = -10;
    public static final int CC_MAX_EQ_GAIN_VAL = 10;
    public static final int CC_DEF_EQ_GAIN_VAL = 0;
    public static final int CC_LUT_SEL_TV = 0;
    public static final int CC_LUT_SEL_AV = 1;
    public static final int CC_LUT_SEL_MPEG = 2;

    public static final int DEF_TV_VOL_001 = 170;
    public static final int DEF_TV_VOL_010 = 125;
    public static final int DEF_TV_VOL_030 = 114;
    public static final int DEF_TV_VOL_050 = 97;
    public static final int DEF_TV_VOL_070 = 91;
    public static final int DEF_TV_VOL_090 = 81;
    public static final int DEF_TV_VOL_100 = 75;

    public static final int DEF_AV_VOL_001 = 164;
    public static final int DEF_AV_VOL_010 = 124;
    public static final int DEF_AV_VOL_030 = 91;
    public static final int DEF_AV_VOL_050 = 84;
    public static final int DEF_AV_VOL_070 = 77;
    public static final int DEF_AV_VOL_090 = 72;
    public static final int DEF_AV_VOL_100 = 69;
    
    public static final int DEF_MPEG_VOL_001 = 164;
    public static final int DEF_MPEG_VOL_010 = 124;
    public static final int DEF_MPEG_VOL_030 = 91;
    public static final int DEF_MPEG_VOL_050 = 86;
    public static final int DEF_MPEG_VOL_070 = 80;
    public static final int DEF_MPEG_VOL_090 = 73;
    public static final int DEF_MPEG_VOL_100 = 71;

    private static boolean LoadAudioCtlUsingThread = true;

    private static int mCurAudioVolume = CC_DEF_SOUND_VOL;
    private static int mCustomMainVolume = CC_DEF_SOUND_VOL;
    private static boolean mCustomVolumeMute = false;
    private static boolean mCurAudioVolumeInc = true;
    private static int mCurSupperBassVolume = CC_DEF_SUPPERBASS_VOL;
    private static int mRealSupperBassVolume = CC_DEF_SUPPERBASS_VOL;
    private static int mCurAudioSupperBassSwitch = 0;
    private static int mCurAudioBassVolume = CC_DEF_BASS_TREBLE_VOL;
    private static int mCurAudioTrebleVolume = CC_DEF_BASS_TREBLE_VOL;
    private static int mCustomBassVolume = CC_DEF_BASS_TREBLE_VOL;
    private static int mCustomTrebleVolume = CC_DEF_BASS_TREBLE_VOL;
    private static boolean disableEQ = true;
    private static int mCurAudioBalance = 0;
    private static int mCurAudioSRSSurround = 0;
    private static int mCurAudioSrsDialogClarity = 0;
    private static int mCurAudioSrsTruBass = 0;
    private static int mCurAudioWallEffect = 0;
    private static int mCurAudioSoundMode = GlobalConst.SOUNDMODE_STD;
    // private static int mCurAudioEQMode = CC_EQ_MODE_NOMAL;

    private static int mCustomEQGainBuf[] = {
        0, 0, 0, 0, 0, 0
    };
    
    private static int mCurEQGainBuf[] = {
        0, 0, 0, 0, 0, 0
    };

    private static final int mWallEffectValeBuf[] = {
        5, 5, 5, 5, 5, 0
    };
    
    private static final int mEQPresetValueBuf[] = {
        0, 0, 0, 0, 0, 0, // SM_STD
        -6, 0, -6, -4, 0, 0, // SM_MUSIC
        0, -3, -7, -5, -2, 0, // SM_NEWS
        -4, -4, 0, -2, -1, 0, // SM_THEATER
    // -8, 0, -8, -8, -6, 0, // EQ_MODE_CLASSIC
    // 0, 0, 0, 0, 0, 0, // EQ_MODE_DANCE
    // 0, 0, 0, 0, 0, 0, // EQ_MODE_PARTY
    // 0, 0, 0, 0, 0, 0, // EQ_MODE_BASS
    // 0, 0, 0, 0, 0, 0, // EQ_MODE_TREBLE
    };

    private static final int mBassVolume[] = {
        50,     // SM_STD
        60,     // SM_MUSIC
        45,     // SM_NEWS
        65,     // SM_THEATER
    };
    
    private static final int mTrebleVolume[] = {
        50,     // SM_STD
        55,     // SM_MUSIC
        55,     // SM_NEWS
        60,     // SM_THEATER
    };
    
    private static final int mWEBsssVolume = 0;
    
    private static final int mWETrebleVolume = 10;
    
    private static int mTVDigitVol001 = DEF_TV_VOL_001;
    private static int mTVDigitVol010 = DEF_TV_VOL_010;
    private static int mTVDigitVol030 = DEF_TV_VOL_030;
    private static int mTVDigitVol050 = DEF_TV_VOL_050;
    private static int mTVDigitVol070 = DEF_TV_VOL_070;
    private static int mTVDigitVol090 = DEF_TV_VOL_090;
    private static int mTVDigitVol100 = DEF_TV_VOL_100;

    private static int mAVDigitVol001 = DEF_AV_VOL_001;
    private static int mAVDigitVol010 = DEF_AV_VOL_010;
    private static int mAVDigitVol030 = DEF_AV_VOL_030;
    private static int mAVDigitVol050 = DEF_AV_VOL_050;
    private static int mAVDigitVol070 = DEF_AV_VOL_070;
    private static int mAVDigitVol090 = DEF_AV_VOL_090;
    private static int mAVDigitVol100 = DEF_AV_VOL_100;

    // 237 is minimum volume, 0 is maximum volume.
    private static int mTVMainVolumeDigitLutBuf[] = {
        // 0 1 2 3 4 5 6 7 8 9
   237, DEF_TV_VOL_001, 165, 160, 155, 150, 145, 140, 135, 130,
        DEF_TV_VOL_010, 123, 123, 122, 122, 121, 121, 120, 120, 119, 119, 118, 118, 117, 117, 116, 116, 115, 115, 114,
        DEF_TV_VOL_030, 113, 112, 111, 110, 109, 108, 107, 106, 105, 104, 103, 102, 101, 100, 100, 99, 99, 98, 98, 
        DEF_TV_VOL_050, 97, 97, 96, 96, 96, 95, 95, 95, 94, 94, 94, 93, 93, 93, 92, 92, 92, 91, 91,
        DEF_TV_VOL_070, 90, 90, 90, 89, 89, 88, 88, 87, 87, 86, 86, 85, 85, 84, 84, 83, 83, 82, 82, 
        DEF_TV_VOL_090, 81, 80, 80, 79, 79, 78, 78, 77, 76, 
        DEF_TV_VOL_100
    };

    private static int mAVMainVolumeDigitLutBuf[] = {
    //0   1    2    3    4    5    6    7    8    9
   237, DEF_AV_VOL_001, 160, 155, 150, 145, 140, 135, 130, 127,
        DEF_AV_VOL_010, 121, 118, 116, 114, 112, 110, 108, 106, 104, 102, 100, 98, 96, 94, 92, 92, 92, 91, 91,
        DEF_AV_VOL_030, 90, 90, 90, 89, 89, 89, 88, 88, 88, 87, 87, 87, 86, 86, 86, 85, 85, 85, 84, 
        DEF_AV_VOL_050, 83, 83, 83, 82, 82, 82, 81, 81, 81, 80, 80, 80, 79, 79, 79, 78, 78, 78, 78,
        DEF_AV_VOL_070, 77, 77, 77, 76, 76, 76, 76, 75, 75, 75, 75, 74, 74, 74, 74, 73, 73, 73, 73, 
        DEF_AV_VOL_090, 72, 72, 72, 71, 71, 71, 70, 70, 70, 
        DEF_AV_VOL_100
     };
    
    private static int mMPEGMainVolumeDigitLutBuf[] = {
     // 0 1 2 3 4 5 6 7 8 9
   237, DEF_MPEG_VOL_001, 160, 155, 150, 145, 140, 135, 130, 127,
        DEF_MPEG_VOL_010, 121, 118, 116, 114, 112, 110, 108, 106, 104, 102, 100, 98, 96, 94, 92, 92, 92, 91, 91,
        DEF_MPEG_VOL_030, 90, 90, 90, 90, 89, 89, 89, 89, 88, 88, 88, 88, 87, 87, 87, 87, 86, 86, 86, 
        DEF_MPEG_VOL_050, 85, 85, 85, 84, 84, 84, 83, 83, 83, 82, 82, 82, 81, 81, 81, 80, 80, 80, 80,
        DEF_MPEG_VOL_070, 79, 79, 79, 79, 78, 78, 78, 77, 77, 77, 76, 76, 76, 75, 75, 75, 74, 74, 74, 
        DEF_MPEG_VOL_090, 73, 73, 73, 72, 72, 72, 71, 71, 71, 
        DEF_MPEG_VOL_100
       };

    // 237 is minimum volume, 0 is maximum volume.
    private static int mSupperBassDigitLutBuf[] = {
        // 0 1 2 3 4 5 6 7 8 9
   237, 164, 162, 160, 158, 156, 154, 152, 150, 148,
   146, 144, 142, 140, 138, 135, 130, 125, 120, 115, 
   110, 105, 100, 95, 90, 86, 86, 86, 86, 86,           //25
   85, 85, 85, 85, 85, 84, 84, 84, 84, 84, 
   83, 83, 83, 83, 83, 82, 82, 82, 82, 82,  
   81, 81, 81, 81, 81, 80, 80, 80, 80, 80,              //50
   79, 79, 79, 79, 79, 78, 78, 78, 78, 78, 
   77, 77, 77, 77, 77, 76, 76, 76, 76, 76,              //75
   75, 75, 75, 75, 75, 74, 74, 74, 74, 74, 
   73, 73, 73, 73, 72, 72, 72, 71, 71, 71, 
   70
   };
    
    private static int mBassVolBuf[] = {
        -10, -10, -10, -10, -10, -10, -10, -10, -10, -10, // 0~9
        -10, -10, -10, -10, -10, -10, -10, -10, -10, -10, // 10~19
        -10, -10, -10, -10, -10, -10, -10, -10, -10, -10, // 20~29
        -8, -8, -8, -8, -8, -6, -6, -6, -6, -6, // 30~39
        -4, -4, -4, -4, -4, -2, -2, -2, -2, -2, // 40~49
        0, 0, 0, 0, 0, 2, 2, 2, 2, 2, // 50~59
        4, 4, 4, 4, 4, 6, 6, 6, 6, 6, // 60~69
        8, 8, 8, 8, 8, 10, 10, 10, 10, 10, // 70~79
        10, 10, 10, 10, 10, 10, 10, 10, 10, 10, // 80~89
        10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10// 90~100
    };

    private static int mTrebleVolBuf[] = {
        -10, -10, -10, -10, -10, -10, -10, -10, -10, -10, // 0~9
        -10, -10, -10, -10, -10, -10, -10, -10, -10, -10, // 10~19
        -10, -10, -10, -10, -10, -10, -10, -10, -10, -10, // 20~29
        -8, -8, -8, -8, -8, -6, -6, -6, -6, -6, // 30~39
        -4, -4, -4, -4, -4, -2, -2, -2, -2, -2, // 40~49
        0, 0, 0, 0, 0, 2, 2, 2, 2, 2, // 50~59
        4, 4, 4, 4, 4, 6, 6, 6, 6, 6, // 60~69
        8, 8, 8, 8, 8, 10, 10, 10, 10, 10, // 70~79
        10, 10, 10, 10, 10, 10, 10, 10, 10, 10, // 80~89
        10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10// 90~100
    };
    
    private static int mSta339xBiquads_00[] = {
        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x40, 0x00, 0x00,//biquad 1
        0x80, 0xC4, 0x96, 0x7F, 0x3B, 0x6A, 0x7F, 0x3A, 0xD3, 0x81, 0x87, 0xFD, 0x3F, 0x9D, 0xB5,//biquad 2
        0x8D, 0x18, 0x8C, 0x67, 0x10, 0x01, 0x72, 0xE7, 0x74, 0x96, 0x03, 0x40, 0x41, 0x76, 0x5F,//biquad 3
        0xAC, 0xE8, 0xCE, 0x32, 0x55, 0xB8, 0x53, 0x17, 0x32, 0xC1, 0xEA, 0x5F, 0x45, 0xDF, 0xF4,//biquad 4
    };

    private static int mSta339xBiquads_01[] = {
        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x40, 0x00, 0x00,//biquad 1
        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x40, 0x00, 0x00,//biquad 2
        0x8D, 0x18, 0x8C, 0x67, 0x10, 0x01, 0x72, 0xE7, 0x74, 0x96, 0x03, 0x40, 0x41, 0x76, 0x5F,//biquad 3
        0xAC, 0xE8, 0xCE, 0x32, 0x55, 0xB8, 0x53, 0x17, 0x32, 0xC1, 0xEA, 0x5F, 0x45, 0xDF, 0xF4,//biquad 4
    };

    static {
        System.loadLibrary("audioctl");
    }

    public static void AudioCtlInit() {
        AudioCtlJNI.AudioUtilsInit();
        AudioCtlJNI.mAudioEffectInit();
        AudioCtlJNI.SetLineInCaptureVolume(64, 0, 84, 64, 0, 84);
        AudioCtlJNI.SetLineOutPlayBackVolume(84, 0, 84, 84, 0, 84);
    }

    public static void AudioCtlUninit() {
        int old_mute_state = AudioCtlJNI.GetAmplifierMute();

        if (old_mute_state == CC_MUTE_OFF) {
            SetCustomMainVolume(0);
            AudioCtlJNI.SetAmplifierMute(CC_MUTE_ON);
        }

        AudioCtlJNI.mAudioEffectUninit();
        AudioCtlJNI.mAudioDspStop();
        AudioCtlJNI.mAlsaUninit(0);
        AudioCtlJNI.AudioUtilsUninit();

        if (old_mute_state == CC_MUTE_OFF) {
            SetCustomMainVolume(GetCurAudioVolume());
            AudioCtlJNI.SetAmplifierMute(CC_MUTE_OFF);
        }
    }

    private static void InitSetSTA339Registers() {
        AudioCtlJNI.SetSTA339XSpecialRegister(0x02, 0x9F);
        AudioCtlJNI.SetSTA339XSpecialRegister(0x10, 0xA0);
        AudioCtlJNI.SetSTA339XSpecialRegister(0x12, 0x64);
        AudioCtlJNI.SetSTA339XSpecialRegister(0x32, 0xB1);
        AudioCtlJNI.SetSTA339XSpecialRegister(0x33, 0xB1);
        AudioCtlJNI.SetSTA339XSpecialRegister(0x34, 0xAE);
        AudioCtlJNI.SetSTA339XSpecialRegister(0x35, 0xAE);
    }
    
    public static void FirstLoadAudioCtl() {
        InitSetSTA339Registers();
        
        SetAudioLineOutMute(false);
        
        // LoadSectionPointHDVol(); //wk 110805 temply cancel this.

        SetVolumeDigitLUTBuf(AudioCustom.CC_LUT_SEL_MPEG, 0);

        InitLoadAudioCtl();
    }

    public static void InitLoadAudioCtl() {
        // Get Current Audio Volume
        LoadCurAudioVolume();
        // Get Current Audio Balance
        LoadCurAudioBalance();

        // Get Current Supper Bass Switch
        LoadCurAudioSupperBassSwitch();
        // Get Current Supper Bass Volume
        LoadCurAudioSupperBassVolume();

        SetAudioMainVolume();

        // Get Current SRSSurround
        LoadCurAudioSrsSurround();

        // Get Current SRS DialogClarity
        LoadCurAudioSrsDialogClarity();

        // Get Current SRS TruBass
        LoadCurAudioSrsTruBass();

        // Get Current Audio Sound Mode
        LoadCurAudioSoundMode();
        LoadCurAudioWallEffect();
        // Get Custom Audio Bass and Treble
        LoadCustomBassVolume();
        LoadCustomTrebleVolume();
        if (disableEQ) {
            SetSpecialModeBTVolume(GetCurAudioSoundMode());
        } else {
        // Load Custom EQ Gain Values
        LoadCustomEQGain(GetEQMinGainVal(), GetEQMaxGainVal());
        SetSpecialModeEQGain(GetCurAudioSoundMode());
            SetCurAudioBassVolume(mCurAudioBassVolume);
            SetCurAudioTrebleVolume(mCurAudioTrebleVolume);
        }

        // Get Current EQ mode
        // LoadCurAudioEQMode();
        // SetSpecialModeEQGain(GetCurAudioEQMode());
    }
    
    private static void SetDspSRS() {
        int surround_switch = GetCurAudioSRSSurround();
            vdinJNI.SrcType srctype = vdinJNI.GetSrcType();

        if (surround_switch == CC_SWITCH_ON) {
            AudioCtlJNI.SetSrsSurroundSwitch(surround_switch);

            AudioCtlJNI.SetSrsTruBassSwitch(GetCurAudioSrsTruBass());
            AudioCtlJNI.SetSrsTruBassGain(CC_SRS_TRUBASS_GAIN_VAL);

            AudioCtlJNI.SetSrsDialogClaritySwitch(GetCurAudioSrsDialogClarity());
            AudioCtlJNI.SetSrsDialogClarityGain(CC_SRS_DIALOG_CLARITY_GAIN_VAL);
            
            if (srctype == vdinJNI.SrcType.AV
                || srctype == vdinJNI.SrcType.COMPONENT
                || srctype == vdinJNI.SrcType.HDMI
                || srctype == vdinJNI.SrcType.VGA) {
                if (vdinJNI.GetCurrentSrcInput() == vdinJNI.SrcId.CVBS0.toInt()) {
                    SetVolumeDigitLUTBuf(AudioCustom.CC_LUT_SEL_TV, 1);
                } else {
                    SetVolumeDigitLUTBuf(AudioCustom.CC_LUT_SEL_AV, 1);
                }
                SetAudioMainVolume();
            }
        } else {
            AudioCtlJNI.SetSrsSurroundSwitch(CC_SWITCH_OFF);
            AudioCtlJNI.SetSrsTruBassSwitch(CC_SWITCH_OFF);
            AudioCtlJNI.SetSrsDialogClaritySwitch(CC_SWITCH_OFF);
            
            if (srctype == vdinJNI.SrcType.AV
                || srctype == vdinJNI.SrcType.COMPONENT
                || srctype == vdinJNI.SrcType.HDMI
                || srctype == vdinJNI.SrcType.VGA) {
                if (vdinJNI.GetCurrentSrcInput() == vdinJNI.SrcId.CVBS0.toInt()) {
                    SetVolumeDigitLUTBuf(AudioCustom.CC_LUT_SEL_TV, 0);
                } else {
                    SetVolumeDigitLUTBuf(AudioCustom.CC_LUT_SEL_AV, 0);
                }
                SetAudioMainVolume();
            }
        }
    }

    public static int AudioDspStart() {
        if (AudioCtlJNI.mAudioDspStart() == 0) {
            SetDspSRS();
            SetCurEQGain();

            return 0;
        }

        return -1;
    }

    private static int RealCalVolDigitLUTBuf(int start_ind, int end_ind,
        int start_val, int end_val, int lut_buf[]) {
        int i = 0;
        float tmp_step = 0;

        if (end_ind < start_ind) {
            return 0;
        }

        if (start_val < end_val) {
            return 0;
        }

        tmp_step = (float) (start_val - end_val)
            / (float) (end_ind - start_ind);

        for (i = start_ind; i < end_ind; i++) {
            lut_buf[i] = start_val - (int) (tmp_step * (i - start_ind));
        }
        lut_buf[end_ind] = end_val;

        return 1;
    }

    private static int CalVolumeDigitLUTBuf(int lut_sel_flag, int lut_buf[]) {
        int tmpDigitVol001 = DEF_TV_VOL_001;
        int tmpDigitVol010 = DEF_TV_VOL_010;
        int tmpDigitVol030 = DEF_TV_VOL_030;
        int tmpDigitVol050 = DEF_TV_VOL_050;
        int tmpDigitVol070 = DEF_TV_VOL_070;
        int tmpDigitVol090 = DEF_TV_VOL_090;
        int tmpDigitVol100 = DEF_TV_VOL_100;

        if (lut_sel_flag == CC_LUT_SEL_TV) {
            tmpDigitVol001 = mTVDigitVol001;
            tmpDigitVol010 = mTVDigitVol010;
            tmpDigitVol030 = mTVDigitVol030;
            tmpDigitVol050 = mTVDigitVol050;
            tmpDigitVol070 = mTVDigitVol070;
            tmpDigitVol090 = mTVDigitVol090;
            tmpDigitVol100 = mTVDigitVol100;
        } else if (lut_sel_flag == CC_LUT_SEL_AV) {
            tmpDigitVol001 = mAVDigitVol001;
            tmpDigitVol010 = mAVDigitVol010;
            tmpDigitVol030 = mAVDigitVol030;
            tmpDigitVol050 = mAVDigitVol050;
            tmpDigitVol070 = mAVDigitVol070;
            tmpDigitVol090 = mAVDigitVol090;
            tmpDigitVol100 = mAVDigitVol100;
        }

        lut_buf[0] = 237;

        RealCalVolDigitLUTBuf(1, 10, tmpDigitVol001, tmpDigitVol010, lut_buf);
        RealCalVolDigitLUTBuf(10, 30, tmpDigitVol010, tmpDigitVol030, lut_buf);
        RealCalVolDigitLUTBuf(30, 50, tmpDigitVol030, tmpDigitVol050, lut_buf);
        RealCalVolDigitLUTBuf(50, 70, tmpDigitVol050, tmpDigitVol070, lut_buf);
        RealCalVolDigitLUTBuf(70, 90, tmpDigitVol070, tmpDigitVol090, lut_buf);
        RealCalVolDigitLUTBuf(90, 100, tmpDigitVol090, tmpDigitVol100, lut_buf);

        return 1;
    }

    private static int AddVolumeLUTBufGain(int lut_buf[], int add_srs_gain_flag) {
        int i = 0;
        int add_gain_val = 0;

        if (add_srs_gain_flag == 1) {
            if (GetCurAudioSRSSurround() == CC_SWITCH_ON) {
                add_gain_val = 12;

                if ((GetCurAudioSrsTruBass() == CC_SWITCH_ON)
                    || (GetCurAudioSrsDialogClarity() == CC_SWITCH_ON)) {
                    add_gain_val = 6;
                }
            } else {
                add_gain_val = 0;
            }
        } else {
            add_gain_val = 0;
        }

        add_gain_val += 6;

        if (add_gain_val == 0) {
            return 0;
        }
        
        // add gain value of LUT buffer items except index 0 which would be mute on.
        for (i = 1; i < 101; i++) {
            if (lut_buf[i] > add_gain_val) {
                lut_buf[i] -= add_gain_val;
            }
        }
        return 1;
    }

    public static void SetMainVolumeDigitLUTBuf(int lut_sel_flag,
        int vol_buf[], int add_srs_gain_flag) {
        int i;
        int TempVolumeDigitLutBuf[] = {
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        };

        if (vol_buf == null) {
            CalVolumeDigitLUTBuf(lut_sel_flag, TempVolumeDigitLutBuf);
        } else {
            for (i = 0; i < 101; i++) {
                TempVolumeDigitLutBuf[i] = vol_buf[i];
            }
        }
        
        AddVolumeLUTBufGain(TempVolumeDigitLutBuf, add_srs_gain_flag);

        AudioCtlJNI.SetMainVolumeDigitLUTBuf(TempVolumeDigitLutBuf);
    }

    public static void SetVolumeDigitLUTBuf(int lut_sel_flag, int add_srs_gain_flag) {
        if (lut_sel_flag == CC_LUT_SEL_TV) {
            SetMainVolumeDigitLUTBuf(lut_sel_flag, mTVMainVolumeDigitLutBuf, add_srs_gain_flag);
        } else if (lut_sel_flag == CC_LUT_SEL_AV) {
            SetMainVolumeDigitLUTBuf(lut_sel_flag, mAVMainVolumeDigitLutBuf, add_srs_gain_flag);
        }else if (lut_sel_flag == CC_LUT_SEL_MPEG) {
            SetMainVolumeDigitLUTBuf(lut_sel_flag, mMPEGMainVolumeDigitLutBuf, add_srs_gain_flag);
        }
        
/*        if (lut_sel_flag == CC_LUT_SEL_MPEG) {//wk 110805 temply cancel this.
            SetMainVolumeDigitLUTBuf(lut_sel_flag, mMPEGMainVolumeDigitLutBuf, add_srs_gain_flag);
        } else {
            SetMainVolumeDigitLUTBuf(lut_sel_flag, null, add_srs_gain_flag);  
        }*/
        
        AudioCtlJNI.SetSupperBassVolumeDigitLUTBuf(mSupperBassDigitLutBuf);
    }

    private static void CheckAndSetSectionPointHDVol(int hd_vol_type,
        int hd_vol_buf[]) {
        int i = 0;

        for (i = 0; i < 6; i++) {
            if (hd_vol_buf[i] <= hd_vol_buf[i + 1]) {
                break;
            }
        }

        if (i == 6) {
            if (hd_vol_type == CC_LUT_SEL_TV) {
                mTVDigitVol001 = hd_vol_buf[0];
                mTVDigitVol010 = hd_vol_buf[1];
                mTVDigitVol030 = hd_vol_buf[2];
                mTVDigitVol050 = hd_vol_buf[3];
                mTVDigitVol070 = hd_vol_buf[4];
                mTVDigitVol090 = hd_vol_buf[5];
                mTVDigitVol100 = hd_vol_buf[6];
            } else if (hd_vol_type == CC_LUT_SEL_AV) {
                mAVDigitVol001 = hd_vol_buf[0];
                mAVDigitVol010 = hd_vol_buf[1];
                mAVDigitVol030 = hd_vol_buf[2];
                mAVDigitVol050 = hd_vol_buf[3];
                mAVDigitVol070 = hd_vol_buf[4];
                mAVDigitVol090 = hd_vol_buf[5];
                mAVDigitVol100 = hd_vol_buf[6];
            }
        }
    }

    public static void LoadSectionPointHDVol() {
        int hd_vol_buf[] = {
            0, 0, 0, 0, 0, 0, 0
        };

        eepromJNI.LoadHDVol(true, hd_vol_buf);
        CheckAndSetSectionPointHDVol(CC_LUT_SEL_TV, hd_vol_buf);

        eepromJNI.LoadHDVol(false, hd_vol_buf);
        CheckAndSetSectionPointHDVol(CC_LUT_SEL_AV, hd_vol_buf);
    }

    public static void AudioAmplifierMuteOn() {
        int MuteStatus = AudioCtlJNI.GetAmplifierMute();
        if (MuteStatus != CC_MUTE_ON) {
            AudioMainVolumeMuteOn();
            AudioSupperBassVolumeMuteOn();
            AudioCtlJNI.SetAmplifierMute(CC_MUTE_ON);
            Log.d(DBTAG, "AudioAmplifierMuteOn succeed, PreMuteStatus : " + MuteStatus);
        } else {
            Log.w(DBTAG, "AudioAmplifierMuteOn failed, MuteStatus : " + MuteStatus);
        }
    }

    public static void AudioAmplifierMuteOff() {
        int MuteStatus = AudioCtlJNI.GetAmplifierMute();
        if (MuteStatus != CC_MUTE_OFF) {
            if (ismCustomVolumeMute() == false) {
                AudioCtlJNI.SetAmplifierMute(CC_MUTE_OFF);
                Log.d(DBTAG, "AudioAmplifierMuteOff succeed, PreMuteStatus : " + MuteStatus);
            } else {
                Log.w(DBTAG, "AudioAmplifierMuteOff failed, ismCustomVolumeMute() == true, MuteStatus : " + MuteStatus);
            }
        } else {
            Log.w(DBTAG, "AudioAmplifierMuteOff failed, MuteStatus : " + MuteStatus);
        }
        SetAudioMainVolume();
    }

    private static int RealCalculateBalanceVol(int main_vol, int balance_val) {
        int bal_cal_len;

        if (balance_val < CC_MIN_SOUND_BALANCE_VAL
            || balance_val > CC_MAX_SOUND_BALANCE_VAL) {
            return main_vol;
        }

        if (balance_val == 0) {
            return main_vol;
        }

        if (balance_val < 0) {
            balance_val = 0 - balance_val;
        }

        bal_cal_len = (CC_MAX_SOUND_BALANCE_VAL - CC_MIN_SOUND_BALANCE_VAL) / 2;

        return (main_vol - ((main_vol * balance_val) / bal_cal_len));
    }

    public static void CalculateBalanceVol(int main_vol, int balance_val,
        int vol_buf[]) {
        if (main_vol < CC_MIN_SOUND_VOL) {
            main_vol = CC_MIN_SOUND_VOL;
        }

        if (main_vol > CC_MAX_SOUND_VOL) {
            main_vol = CC_MAX_SOUND_VOL;
        }

        vol_buf[0] = main_vol; // Left Channel Volume
        vol_buf[1] = main_vol; // Right Channel Volume

        if (main_vol == CC_MIN_SOUND_VOL) {
            return;
        }

        if (balance_val < CC_MIN_SOUND_BALANCE_VAL
            || balance_val > CC_MAX_SOUND_BALANCE_VAL) {
            return;
        }

        if (balance_val > 0) {
            vol_buf[0] = RealCalculateBalanceVol(main_vol, balance_val);
        } else if (balance_val < 0) {
            vol_buf[1] = RealCalculateBalanceVol(main_vol, -balance_val);
        }
    }

    public static void SetCustomMainVolume(int tmp_vol) {
        int tmp_buf[] = {
            0, 0
        };
        vdinJNI.SrcType srctype = vdinJNI.GetSrcType();

        if (GlobalConst.DEBUG_MODE_SELECT == GlobalConst.DEBUG_MODE_EMULATOR)
            return;

        if (tmp_vol < CC_MIN_SOUND_VOL || tmp_vol > CC_MAX_SOUND_VOL) {
            tmp_vol = CC_DEF_SOUND_VOL;
        }

        if (tmp_vol == 0) {
            mCustomMainVolume = tmp_vol;
        } else {
            if ((srctype == vdinJNI.SrcType.AV && vdinJNI.GetCurrentSrcInput() == vdinJNI.SrcId.CVBS0
                .toInt())) {
                mCustomMainVolume = tunerJNI.AddVolComp(tmp_vol);
            } else {
                mCustomMainVolume = tmp_vol;
            }
        }

        CalculateBalanceVol(mCustomMainVolume, mCurAudioBalance, tmp_buf);

        AudioCtlJNI.SetAmplifierVolume(tmp_buf[0], CC_MIN_SOUND_VOL, CC_MAX_SOUND_VOL, tmp_buf[1], CC_MIN_SOUND_VOL, CC_MAX_SOUND_VOL);

        mRealSupperBassVolume = CalRealSupperBassVolume(mCurSupperBassVolume, mCustomMainVolume);

        SetAudioSupperBassVolume();
    }

    public static int GetCustomMainVolume() {
        return mCustomMainVolume;
    }

    public static void setmCustomVolumeMute(boolean status) {
        mCustomVolumeMute = status;
        vdinJNI.SrcType srctype = vdinJNI.GetSrcType();

        Log.d(DBTAG, "setmCustomVolumeMute():: source type = "
            + srctype.toString());

        if (mCustomVolumeMute) {
            AudioAmplifierMuteOn();
        } else {
            if (srctype == vdinJNI.SrcType.AV
                || srctype == vdinJNI.SrcType.COMPONENT
                || srctype == vdinJNI.SrcType.HDMI
                || srctype == vdinJNI.SrcType.VGA) {
                if (vdinJNI.GetSigStatus() == vdinJNI.SigStatus.STABLE.ordinal()
                    && vdinJNI.GetSigFormat() != vdinJNI.SigFormat.NULL.ordinal())
                    AudioAmplifierMuteOff();
            } else {
                AudioAmplifierMuteOff();
            }
        }
    }

    public static boolean ismCustomVolumeMute() {
        return mCustomVolumeMute;
    }

    public static void AudioMainVolumeMuteOn() {
        SetCustomMainVolume(0);
    }

    public static void AudioMainVolumeMuteOff() {
        SetCustomMainVolume(mCurAudioVolume);
    }

    public static void setmCurAudioVolumeInc(boolean status) {
        mCurAudioVolumeInc = status;
    }

    public static boolean ismCurAudioVolumeInc() {
        return mCurAudioVolumeInc;
    }

    public static void SetAudioMainVolume() {
        if (ismCustomVolumeMute())
            AudioMainVolumeMuteOn();
        else
            AudioMainVolumeMuteOff();
    }

    public static int GetCurAudioVolume() {
        return mCurAudioVolume;
    }

    public static int RealSaveCurAudioVolume(int tmp_vol) {
        if (GlobalConst.DEBUG_MODE_SELECT == GlobalConst.DEBUG_MODE_EMULATOR)
            return CC_DEF_SOUND_VOL;

        if (tmp_vol < CC_MIN_SOUND_VOL || tmp_vol > CC_MAX_SOUND_VOL) {
            tmp_vol = CC_DEF_SOUND_VOL;
        }

        if (mCurAudioVolume < tmp_vol)
            setmCurAudioVolumeInc(true);
        else
            setmCurAudioVolumeInc(false);
        mCurAudioVolume = tmp_vol;
        // SetCustomMainVolume(mCurAudioVolume);
        eepromJNI.SaveAudioSetting(eepromJNI.AudioFunc.MAIN_VOLUME, mCurAudioVolume);

        return mCurAudioVolume;
    }

    public static int LoadCurAudioVolume() {
        if (GlobalConst.DEBUG_MODE_SELECT == GlobalConst.DEBUG_MODE_EMULATOR)
            return CC_DEF_SOUND_VOL;

        mCurAudioVolume = (byte) eepromJNI.LoadAudioSetting(eepromJNI.AudioFunc.MAIN_VOLUME);
        if (mCurAudioVolume < CC_MIN_SOUND_VOL
            || mCurAudioVolume > CC_MAX_SOUND_VOL) {
            RealSaveCurAudioVolume(CC_DEF_SOUND_VOL);
        }
        setmCurAudioVolumeInc(true);

        return mCurAudioVolume;
    }

    public static int SaveCurAudioVolume(int tmp_vol) {
        if (GlobalConst.DEBUG_MODE_SELECT == GlobalConst.DEBUG_MODE_EMULATOR)
            return CC_DEF_SOUND_VOL;

        return RealSaveCurAudioVolume(tmp_vol);
    }

    public static int GetCurAudioBassVolume() {
        return mCurAudioBassVolume;
    }

    public static int GetCurAudioTrebleVolume() {
        return mCurAudioTrebleVolume;
    }

    private static void LoadCustomBassVolume() {
        if (GlobalConst.DEBUG_MODE_SELECT == GlobalConst.DEBUG_MODE_EMULATOR)
            return;

        mCustomBassVolume = (byte) eepromJNI.LoadAudioSetting(eepromJNI.AudioFunc.BASS_VOLUME);
        if (mCustomBassVolume < CC_MIN_SOUND_VOL
            || mCustomBassVolume > CC_MAX_SOUND_VOL) {
            RealSaveCustomBassVolume(CC_DEF_BASS_TREBLE_VOL);
        }
    }

    private static void LoadCustomTrebleVolume() {
        if (GlobalConst.DEBUG_MODE_SELECT == GlobalConst.DEBUG_MODE_EMULATOR)
            return;

        mCustomTrebleVolume = (byte) eepromJNI.LoadAudioSetting(eepromJNI.AudioFunc.TREBLE_VOLUME);
        if (mCustomTrebleVolume < CC_MIN_SOUND_VOL
            || mCustomTrebleVolume > CC_MAX_SOUND_VOL) {
            RealSaveCustomTrebleVolume(CC_DEF_BASS_TREBLE_VOL);
        }
    }
    
    private static int CalAudioBassVolume(int tmp_vol) {
        if (tmp_vol < CC_MIN_SOUND_VOL || tmp_vol > CC_MAX_SOUND_VOL) {
            tmp_vol = CC_DEF_BASS_TREBLE_VOL;
        }

        if (disableEQ) {
            return mBassVolBuf[tmp_vol];
        } else {
            return (tmp_vol * GlobalConst.AUDIO_EQGAIN_RANGE / 100 - GlobalConst.AUDIO_EQGAIN_RANGE / 2);
        }
    }

    private static int CalAudioTrebleVolume(int tmp_vol) {
        if (tmp_vol < CC_MIN_SOUND_VOL || tmp_vol > CC_MAX_SOUND_VOL) {
            tmp_vol = CC_DEF_BASS_TREBLE_VOL;
        }

        if (disableEQ) {
            return mTrebleVolBuf[tmp_vol];
        } else {
            return (tmp_vol * GlobalConst.AUDIO_EQGAIN_RANGE / 100 - GlobalConst.AUDIO_EQGAIN_RANGE / 2);
        }
    }
    
    public static int SaveCustomAudioBassVolume(int tmp_vol) {
        if (GlobalConst.DEBUG_MODE_SELECT == GlobalConst.DEBUG_MODE_EMULATOR)
            return CC_DEF_BASS_TREBLE_VOL;
        
        if (tmp_vol < CC_MIN_SOUND_VOL || tmp_vol > CC_MAX_SOUND_VOL) {
            tmp_vol = CC_DEF_BASS_TREBLE_VOL;
        }
        
        RealSaveCustomBassVolume(tmp_vol);
        if (disableEQ) {
            mCurAudioBassVolume = tmp_vol;
            return RealSetBassVolume(mCurAudioBassVolume);
        } else {
        int eq_val;
            eq_val = CalAudioBassVolume(GetCurAudioBassVolume());
            return SaveCustomEQGain(GlobalConst.LOAD_AUDIO_BASS_POS, eq_val);
        }
    }

    public static int SaveCustomAudioTrebleVolume(int tmp_vol) {
        if (GlobalConst.DEBUG_MODE_SELECT == GlobalConst.DEBUG_MODE_EMULATOR)
            return CC_DEF_BASS_TREBLE_VOL;
        
        if (tmp_vol < CC_MIN_SOUND_VOL || tmp_vol > CC_MAX_SOUND_VOL) {
            tmp_vol = CC_DEF_BASS_TREBLE_VOL;
        }
        
        RealSaveCustomTrebleVolume(tmp_vol);
        if (disableEQ) {
            mCurAudioTrebleVolume = tmp_vol;
            return RealSetTrebleVolume(mCurAudioTrebleVolume);
        } else {
        int eq_val;
            eq_val = CalAudioTrebleVolume(GetCurAudioTrebleVolume());
            return SaveCustomEQGain(GlobalConst.LOAD_AUDIO_TREBLE_POS, eq_val);
        }
    }

    public static void RealSaveCustomBassVolume(int tmp_vol) {
        if (GlobalConst.DEBUG_MODE_SELECT == GlobalConst.DEBUG_MODE_EMULATOR)
            return;

        if (tmp_vol < CC_MIN_SOUND_VOL || tmp_vol > CC_MAX_SOUND_VOL) {
            tmp_vol = CC_DEF_BASS_TREBLE_VOL;
        }

        mCustomBassVolume = tmp_vol;
        eepromJNI.SaveAudioSetting(eepromJNI.AudioFunc.BASS_VOLUME, mCustomBassVolume);
    }

    public static void RealSaveCustomTrebleVolume(int tmp_vol) {
        if (GlobalConst.DEBUG_MODE_SELECT == GlobalConst.DEBUG_MODE_EMULATOR)
            return;

        if (tmp_vol < CC_MIN_SOUND_VOL || tmp_vol > CC_MAX_SOUND_VOL) {
            tmp_vol = CC_DEF_BASS_TREBLE_VOL;
        }

        mCustomTrebleVolume = tmp_vol;
        eepromJNI.SaveAudioSetting(eepromJNI.AudioFunc.TREBLE_VOLUME, mCustomTrebleVolume);
    }

    public static int  SetCurAudioBassVolume(int cur_vol) {
        if (GlobalConst.DEBUG_MODE_SELECT == GlobalConst.DEBUG_MODE_EMULATOR)
            return 0;

        if (cur_vol < CC_MIN_SOUND_VOL || cur_vol > CC_MAX_SOUND_VOL) {
            cur_vol = CC_DEF_BASS_TREBLE_VOL;
        }
        if (disableEQ) {
            mCurAudioBassVolume = cur_vol;
        } else {
        int tmp_val;
            tmp_val = GetCurEQGainBufItem(GlobalConst.LOAD_AUDIO_BASS_POS) + GlobalConst.AUDIO_EQGAIN_RANGE / 2;
        mCurAudioBassVolume = tmp_val * 100 / GlobalConst.AUDIO_EQGAIN_RANGE;
        }

        SaveCustomAudioBassVolume(mCurAudioBassVolume);
        if (GetCurAudioSoundMode() != GlobalConst.SOUNDMODE_USER) {
            RealSaveCurAudioSoundMode(GlobalConst.SOUNDMODE_USER);
            RealSaveCustomTrebleVolume(mCurAudioTrebleVolume);
        }
        return 1;
    }

    public static int  SetCurAudioTrebleVolume(int cur_vol) {
        if (GlobalConst.DEBUG_MODE_SELECT == GlobalConst.DEBUG_MODE_EMULATOR)
            return 0;

        if (cur_vol < CC_MIN_SOUND_VOL || cur_vol > CC_MAX_SOUND_VOL) {
            cur_vol = CC_DEF_BASS_TREBLE_VOL;
        }
        if (disableEQ) {
            mCurAudioTrebleVolume = cur_vol;
        } else {
        int tmp_val;
            tmp_val = GetCurEQGainBufItem(GlobalConst.LOAD_AUDIO_TREBLE_POS) + GlobalConst.AUDIO_EQGAIN_RANGE / 2;
        mCurAudioTrebleVolume = tmp_val * 100 / GlobalConst.AUDIO_EQGAIN_RANGE;
        }

        SaveCustomAudioTrebleVolume(mCurAudioTrebleVolume);
        if (GetCurAudioSoundMode() != GlobalConst.SOUNDMODE_USER) {
            RealSaveCurAudioSoundMode(GlobalConst.SOUNDMODE_USER);
            RealSaveCustomBassVolume(mCurAudioBassVolume);
        }
        return 1;
    }
    
    private static void SetSta339xAsSupperBass() {
        if (GetCurAudioSupperBassSwitch() == CC_SWITCH_ON) {
            STA33X_CrossOver(1);
            SetSta339xBiquads(1);
        } else {
            STA33X_CrossOver(0);
            SetSta339xBiquads(0);
        }
    }

    private static void SetSta339xBiquads(int set_flag) {
        int i = 0;
        int biquad_data[] = {
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
        };

        for (i = 0; i < 4; i++) {
            if (set_flag == 0) {
                System.arraycopy(mSta339xBiquads_00, i * 15, biquad_data, 0, 15);
            } else {
                System.arraycopy(mSta339xBiquads_01, i * 15, biquad_data, 0, 15);
            }
            AudioCtlJNI.SetSTA339XSpecialBiquad(i + 1, biquad_data);
        }
    }
    
    private static void STA33X_CrossOver(int set_flag) {
        if (set_flag == 0) {
            AudioCtlJNI.SetSTA339XSpecialRegister(0x0C, 0x00);
        } else {
            AudioCtlJNI.SetSTA339XSpecialRegister(0x0C, 0xA0);
        }
    }

    private static int CalRealSupperBassVolume(int CurSupperBassVolume,
        int CustomMainVolume) {
        if (CurSupperBassVolume < CC_MIN_SUPPERBASS_VOL
            || CurSupperBassVolume > CC_MAX_SUPPERBASS_VOL) {
            CurSupperBassVolume = CC_DEF_SUPPERBASS_VOL;
        }
        if (CustomMainVolume < CC_MIN_SOUND_VOL
            || CustomMainVolume > CC_MAX_SOUND_VOL) {
            CustomMainVolume = CC_DEF_SOUND_VOL;
        }
	
        return (CurSupperBassVolume * CustomMainVolume) / 100;
    }

    public static void AudioSupperBassVolumeMuteOn() {
        AudioCtlJNI.SetSupperBassVolume(0, CC_MIN_SUPPERBASS_VOL, CC_MAX_SUPPERBASS_VOL);
    }

    public static void AudioSupperBassVolumeMuteOff() {
        AudioCtlJNI.SetSupperBassVolume(mRealSupperBassVolume, CC_MIN_SUPPERBASS_VOL, CC_MAX_SUPPERBASS_VOL);
    }

    public static void SetAudioSupperBassVolume() {
        if ((mCurAudioSupperBassSwitch == CC_SWITCH_OFF)
            || (ismCustomVolumeMute()))
            AudioSupperBassVolumeMuteOn();
        else
            AudioSupperBassVolumeMuteOff();
    }

    public static int GetCurAudioSupperBassVolume() {
        return mCurSupperBassVolume;
    }

    public static int RealSaveCurAudioSupperBassVolume(int tmp_vol) {
        if (GlobalConst.DEBUG_MODE_SELECT == GlobalConst.DEBUG_MODE_EMULATOR)
            return CC_DEF_SUPPERBASS_VOL;

        if (tmp_vol < CC_MIN_SUPPERBASS_VOL || tmp_vol > CC_MAX_SUPPERBASS_VOL) {
            tmp_vol = CC_DEF_SUPPERBASS_VOL;
        }

        mCurSupperBassVolume = tmp_vol;
        mRealSupperBassVolume = CalRealSupperBassVolume(mCurSupperBassVolume, mCustomMainVolume);
        // SetSupperBassVolume(mRealSupperBassVolume, CC_MIN_SUPPERBASS_VOL,
        // CC_MAX_SUPPERBASS_VOL);
        eepromJNI.SaveAudioSetting(eepromJNI.AudioFunc.SUPPERBASS_VOLUME, mCurSupperBassVolume);

        return mCurSupperBassVolume;
    }

    public static int LoadCurAudioSupperBassVolume() {
        if (GlobalConst.DEBUG_MODE_SELECT == GlobalConst.DEBUG_MODE_EMULATOR)
            return CC_DEF_SUPPERBASS_VOL;

        mCurSupperBassVolume = (byte) eepromJNI.LoadAudioSetting(eepromJNI.AudioFunc.SUPPERBASS_VOLUME);
        if (mCurSupperBassVolume < CC_MIN_SUPPERBASS_VOL
            || mCurSupperBassVolume > CC_MAX_SUPPERBASS_VOL) {
            RealSaveCurAudioSupperBassVolume(CC_DEF_SUPPERBASS_VOL);
        }

        mRealSupperBassVolume = CalRealSupperBassVolume(mCurSupperBassVolume, mCustomMainVolume);

        return mCurSupperBassVolume;
    }

    public static int SaveCurAudioSupperBassVolume(int tmp_vol) {
        if (GlobalConst.DEBUG_MODE_SELECT == GlobalConst.DEBUG_MODE_EMULATOR)
            return CC_DEF_SUPPERBASS_VOL;

        return RealSaveCurAudioSupperBassVolume(tmp_vol);
    }

    public static int GetCurAudioSupperBassSwitch() {
        return mCurAudioSupperBassSwitch;
    }

    public static int RealSaveCurAudioSupperBassSwitch(int tmp_val) {
        if (GlobalConst.DEBUG_MODE_SELECT == GlobalConst.DEBUG_MODE_EMULATOR)
            return tmp_val;

        if (tmp_val != CC_SWITCH_ON && tmp_val != CC_SWITCH_OFF) {
            tmp_val = CC_SWITCH_OFF;
        }

        mCurAudioSupperBassSwitch = tmp_val;
        
        SetSta339xAsSupperBass();
        
        eepromJNI.SaveAudioSetting(eepromJNI.AudioFunc.SUPPERBASS_SWITCH, mCurAudioSupperBassSwitch);

        return mCurAudioSupperBassSwitch;
    }

    public static int LoadCurAudioSupperBassSwitch() {
        if (GlobalConst.DEBUG_MODE_SELECT == GlobalConst.DEBUG_MODE_EMULATOR)
            return 0;

        mCurAudioSupperBassSwitch = eepromJNI.LoadAudioSetting(eepromJNI.AudioFunc.SUPPERBASS_SWITCH);
        if (mCurAudioSupperBassSwitch != CC_SWITCH_ON
            && mCurAudioSupperBassSwitch != CC_SWITCH_OFF) {
            RealSaveCurAudioSupperBassSwitch(CC_SWITCH_OFF);
        }
        
        SetSta339xAsSupperBass();

        return mCurAudioSupperBassSwitch;
    }

    public static int SaveCurAudioSupperBassSwitch(int tmp_val) {
        if (GlobalConst.DEBUG_MODE_SELECT == GlobalConst.DEBUG_MODE_EMULATOR)
            return tmp_val;

        return RealSaveCurAudioSupperBassSwitch(tmp_val);
    }

    public static int GetCurAudioBalance() {
        return mCurAudioBalance;
    }

    public static int RealSaveCurAudioBalance(int tmp_val) {
        if (GlobalConst.DEBUG_MODE_SELECT == GlobalConst.DEBUG_MODE_EMULATOR)
            return tmp_val;

        if (tmp_val < CC_MIN_SOUND_BALANCE_VAL
            || tmp_val > CC_MAX_SOUND_BALANCE_VAL) {
            tmp_val = CC_DEF_SOUND_BALANCE_VAL;
        }

        mCurAudioBalance = tmp_val;
        eepromJNI.SaveAudioSetting(eepromJNI.AudioFunc.BALANCE_VAL, mCurAudioBalance);

        // SetCustomMainVolume(mCurAudioVolume);

        return mCurAudioBalance;
    }

    public static int LoadCurAudioBalance() {
        if (GlobalConst.DEBUG_MODE_SELECT == GlobalConst.DEBUG_MODE_EMULATOR)
            return 0;

        mCurAudioBalance = eepromJNI.LoadAudioSetting(eepromJNI.AudioFunc.BALANCE_VAL);
        if (mCurAudioBalance < CC_MIN_SOUND_BALANCE_VAL
            || mCurAudioBalance > CC_MAX_SOUND_BALANCE_VAL) {
            RealSaveCurAudioBalance(CC_DEF_SOUND_BALANCE_VAL);
        }

        return mCurAudioBalance;
    }

    public static int SaveCurAudioBalance(int tmp_val) {
        if (GlobalConst.DEBUG_MODE_SELECT == GlobalConst.DEBUG_MODE_EMULATOR)
            return 0;

        // change from 0---+100 to -50---+50
        return RealSaveCurAudioBalance(tmp_val - 50);
    }

    public static int GetCurAudioSRSSurround() {
        return mCurAudioSRSSurround;
    }

    public static int RealSaveCurAudioSrsSurround(int tmp_val) {
        if (GlobalConst.DEBUG_MODE_SELECT == GlobalConst.DEBUG_MODE_EMULATOR)
            return tmp_val;

        if (tmp_val != CC_SWITCH_ON && tmp_val != CC_SWITCH_OFF) {
            tmp_val = CC_SWITCH_OFF;
        }

        mCurAudioSRSSurround = tmp_val;

        SetDspSRS();
        
        eepromJNI.SaveAudioSetting(eepromJNI.AudioFunc.SRS_SWITCH, mCurAudioSRSSurround);

        return mCurAudioSRSSurround;
    }

    public static int LoadCurAudioSrsSurround() {
        if (GlobalConst.DEBUG_MODE_SELECT == GlobalConst.DEBUG_MODE_EMULATOR)
            return 0;

        mCurAudioSRSSurround = eepromJNI.LoadAudioSetting(eepromJNI.AudioFunc.SRS_SWITCH);
        if (mCurAudioSRSSurround != CC_SWITCH_ON
            && mCurAudioSRSSurround != CC_SWITCH_OFF) {
            RealSaveCurAudioSrsSurround(CC_SWITCH_OFF);
        }

        return mCurAudioSRSSurround;
    }

    public static int SaveCurAudioSrsSurround(int tmp_val) {
        if (GlobalConst.DEBUG_MODE_SELECT == GlobalConst.DEBUG_MODE_EMULATOR)
            return tmp_val;

        return RealSaveCurAudioSrsSurround(tmp_val);
    }

    public static int GetCurAudioSrsDialogClarity() {
        return mCurAudioSrsDialogClarity;
    }

    public static int RealSaveCurAudioSrsDialogClarity(int tmp_val) {
        if (GlobalConst.DEBUG_MODE_SELECT == GlobalConst.DEBUG_MODE_EMULATOR)
            return tmp_val;

        if (tmp_val != CC_SWITCH_ON && tmp_val != CC_SWITCH_OFF) {
            tmp_val = CC_SWITCH_OFF;
        }

        mCurAudioSrsDialogClarity = tmp_val;
        
        SetDspSRS();
        
        eepromJNI.SaveAudioSetting(eepromJNI.AudioFunc.DIALOG_CLARITY_SWITCH, mCurAudioSrsDialogClarity);

        return mCurAudioSrsDialogClarity;
    }

    public static int LoadCurAudioSrsDialogClarity() {
        if (GlobalConst.DEBUG_MODE_SELECT == GlobalConst.DEBUG_MODE_EMULATOR)
            return 0;

        mCurAudioSrsDialogClarity = eepromJNI.LoadAudioSetting(eepromJNI.AudioFunc.DIALOG_CLARITY_SWITCH);
        if (mCurAudioSrsDialogClarity != CC_SWITCH_ON
            && mCurAudioSrsDialogClarity != CC_SWITCH_OFF) {
            RealSaveCurAudioSrsDialogClarity(CC_SWITCH_OFF);
        }

        return mCurAudioSrsDialogClarity;
    }

    public static int SaveCurAudioSrsDialogClarity(int tmp_val) {
        if (GlobalConst.DEBUG_MODE_SELECT == GlobalConst.DEBUG_MODE_EMULATOR)
            return tmp_val;

        return RealSaveCurAudioSrsDialogClarity(tmp_val);
    }

    public static int GetCurAudioSrsTruBass() {
        return mCurAudioSrsTruBass;
    }

    public static int RealSaveCurAudioSrsTruBass(int tmp_val) {
        if (GlobalConst.DEBUG_MODE_SELECT == GlobalConst.DEBUG_MODE_EMULATOR)
            return tmp_val;

        if (tmp_val != CC_SWITCH_ON && tmp_val != CC_SWITCH_OFF) {
            tmp_val = CC_SWITCH_OFF;
        }

        mCurAudioSrsTruBass = tmp_val;
        
        SetDspSRS();
        
        eepromJNI.SaveAudioSetting(eepromJNI.AudioFunc.TRUEBASS_SWITCH, mCurAudioSrsTruBass);

        return mCurAudioSrsTruBass;
    }

    public static int LoadCurAudioSrsTruBass() {
        if (GlobalConst.DEBUG_MODE_SELECT == GlobalConst.DEBUG_MODE_EMULATOR)
            return 0;

        mCurAudioSrsTruBass = eepromJNI.LoadAudioSetting(eepromJNI.AudioFunc.TRUEBASS_SWITCH);
        if (mCurAudioSrsTruBass != CC_SWITCH_ON
            && mCurAudioSrsTruBass != CC_SWITCH_OFF) {
            RealSaveCurAudioSrsTruBass(CC_SWITCH_OFF);
        }

        return mCurAudioSrsTruBass;
    }

    public static int SaveCurAudioSrsTruBass(int tmp_val) {
        if (GlobalConst.DEBUG_MODE_SELECT == GlobalConst.DEBUG_MODE_EMULATOR)
            return tmp_val;

        return RealSaveCurAudioSrsTruBass(tmp_val);
    }

    public static int GetCurAudioSoundMode() {
        return mCurAudioSoundMode;
    }

    public static int RealSaveCurAudioSoundMode(int tmp_val) {
        if (GlobalConst.DEBUG_MODE_SELECT == GlobalConst.DEBUG_MODE_EMULATOR)
            return tmp_val;

        if (tmp_val < GlobalConst.SOUNDMODE_START
            || tmp_val > GlobalConst.SOUNDMODE_END) {
            tmp_val = GlobalConst.SOUNDMODE_STD;
        }

        mCurAudioSoundMode = tmp_val;
        eepromJNI.SaveAudioSetting(eepromJNI.AudioFunc.SOUND_MODE_VAL, mCurAudioSoundMode);

        return mCurAudioSoundMode;
    }

    public static int LoadCurAudioSoundMode() {
        if (GlobalConst.DEBUG_MODE_SELECT == GlobalConst.DEBUG_MODE_EMULATOR)
            return 0;

        mCurAudioSoundMode = eepromJNI.LoadAudioSetting(eepromJNI.AudioFunc.SOUND_MODE_VAL);
        if (mCurAudioSoundMode < GlobalConst.SOUNDMODE_START
            || mCurAudioSoundMode > GlobalConst.SOUNDMODE_END) {
            RealSaveCurAudioSoundMode(GlobalConst.SOUNDMODE_STD);
        }

        return mCurAudioSoundMode;
    }

    public static int SaveCurAudioSoundMode(int tmp_val) {
        if (GlobalConst.DEBUG_MODE_SELECT == GlobalConst.DEBUG_MODE_EMULATOR)
            return tmp_val;

        RealSaveCurAudioSoundMode(tmp_val);
        if (disableEQ) {
            return SetSpecialModeBTVolume(mCurAudioSoundMode);
        } else {
        SetSpecialModeEQGain(mCurAudioSoundMode);
            SetCurAudioBassVolume(mCurAudioBassVolume);
            return SetCurAudioTrebleVolume(mCurAudioTrebleVolume);
        }
    }

    public static int GetCurAudioWallEffect() {
        return mCurAudioWallEffect;
    }

    public static int RealSaveCurAudioWallEffect(int tmp_val) {
        if (GlobalConst.DEBUG_MODE_SELECT == GlobalConst.DEBUG_MODE_EMULATOR)
            return tmp_val;

        if (tmp_val != CC_SWITCH_ON && tmp_val != CC_SWITCH_OFF) {
            tmp_val = CC_SWITCH_OFF;
        }

        mCurAudioWallEffect = tmp_val;
        eepromJNI.SaveAudioSetting(eepromJNI.AudioFunc.WALL_EFFCT_SWITCH, mCurAudioWallEffect);

        return mCurAudioWallEffect;
    }

    public static int LoadCurAudioWallEffect() {
        if (GlobalConst.DEBUG_MODE_SELECT == GlobalConst.DEBUG_MODE_EMULATOR)
            return 0;

        mCurAudioWallEffect = eepromJNI.LoadAudioSetting(eepromJNI.AudioFunc.WALL_EFFCT_SWITCH);
        if (mCurAudioWallEffect != CC_SWITCH_ON
            && mCurAudioWallEffect != CC_SWITCH_OFF) {
            RealSaveCurAudioWallEffect(CC_SWITCH_OFF);
        }

        return mCurAudioWallEffect;
    }

    public static int SaveCurAudioWallEffect(int tmp_val) {
        if (GlobalConst.DEBUG_MODE_SELECT == GlobalConst.DEBUG_MODE_EMULATOR)
            return tmp_val;

        RealSaveCurAudioWallEffect(tmp_val);
        if (disableEQ) {
            return SetSpecialModeBTVolume(mCurAudioSoundMode);
        } else {
        return SetSpecialModeEQGain(mCurAudioSoundMode);
    }
    }

    // public static int GetCurAudioEQMode() {
    // return mCurAudioEQMode;
    // }

    // public static int SaveCurAudioSpecialEQMode(int tmp_val) {
    // if (GlobalConst.DEBUG_MODE_SELECT == GlobalConst.DEBUG_MODE_EMULATOR)
    // return tmp_val;
    //
    // if (tmp_val < CC_EQ_MODE_START || tmp_val > CC_EQ_MODE_END) {
    // tmp_val = CC_EQ_MODE_START;
    // }
    //
    // mCurAudioEQMode = tmp_val;
    // eepromJNI.SaveAudioSetting(eepromJNI.AudioFunc.EQ_MODE_VAL,
    // mCurAudioEQMode);
    //
    // SetSpecialModeEQGain(mCurAudioEQMode);
    //
    // return tmp_val;
    // }

    // public static int LoadCurAudioEQMode() {
    // mCurAudioEQMode = eepromJNI
    // .LoadAudioSetting(eepromJNI.AudioFunc.EQ_MODE_VAL);
    // if (mCurAudioEQMode < CC_EQ_MODE_START
    // || mCurAudioEQMode > CC_EQ_MODE_END) {
    // SaveCurAudioSpecialEQMode(CC_EQ_MODE_START);
    // }
    //
    // return mCurAudioEQMode;
    // }

    // public static int SaveCurAudioEQMode(int tmp_val) {
    // if (GlobalConst.DEBUG_MODE_SELECT == GlobalConst.DEBUG_MODE_EMULATOR)
    // return tmp_val;
    //
    // return SaveCurAudioSpecialEQMode(tmp_val);
    // }

    public static int SaveCustomEQGain(int buf_index, int w_val) {
        if (GlobalConst.DEBUG_MODE_SELECT == GlobalConst.DEBUG_MODE_EMULATOR)
            return 0;

        if (buf_index >= 0 && buf_index < CC_EQ_BAND_CNT) {
            if (GetCurAudioSoundMode() != GlobalConst.SOUNDMODE_USER) {
                CopyEQGainBuf(mCurEQGainBuf, mCustomEQGainBuf);
                RealSaveCurAudioSoundMode(GlobalConst.SOUNDMODE_USER);
            }

            mCustomEQGainBuf[buf_index] = w_val;

            SetSpecialModeEQGain(GlobalConst.SOUNDMODE_USER);
            eepromJNI.SaveAudioEffGain(mCurEQGainBuf, CC_EQ_BAND_CNT);

            return 1;
        }

        return 0;
    }

    private static int LoadCustomEQGain(int nMinGain, int nMaxGain) {
        int i;

        if (GlobalConst.DEBUG_MODE_SELECT == GlobalConst.DEBUG_MODE_EMULATOR)
            return 0;

        eepromJNI.LoadAudioEffGain(mCustomEQGainBuf, CC_EQ_BAND_CNT);

        // CopyEQGainBuf(mCustomEQGainBuf, mCurEQGainBuf);

        for (i = 0; i < CC_EQ_BAND_CNT; i++) {
            if (mCustomEQGainBuf[i] < nMinGain
                || mCustomEQGainBuf[i] > nMaxGain) {
                break;
            }
        }

        if (i < CC_EQ_BAND_CNT) {
            System.arraycopy(mEQPresetValueBuf, GlobalConst.SOUNDMODE_STD
                * CC_EQ_BAND_CNT, mCustomEQGainBuf, 0, CC_EQ_BAND_CNT);
            eepromJNI.SaveAudioEffGain(mCustomEQGainBuf, CC_EQ_BAND_CNT);
            return 0;
        }

        return 1;
    }

    public static int RealSetEffectGain(int gain_val_buf[]) {
        int i = 0, nMinGain, nMaxGain;
        int mTempEQGainBuf[] = {
            0, 0, 0, 0, 0, 0
        };

        nMinGain = GetEQMinGainVal();
        nMaxGain = GetEQMaxGainVal();

        if (GetCurAudioWallEffect() == CC_SWITCH_ON) {
            for (i = 0; i < CC_EQ_BAND_CNT; i++) {
                mTempEQGainBuf[i] = mWallEffectValeBuf[i] + gain_val_buf[i];
                if (mTempEQGainBuf[i] < nMinGain) {
                    mTempEQGainBuf[i] = nMinGain;
                }
                if (mTempEQGainBuf[i] > nMaxGain) {
                    mTempEQGainBuf[i] = nMaxGain;
                }
            }
        } else {
            for (i = 0; i < CC_EQ_BAND_CNT; i++) {
                mTempEQGainBuf[i] = gain_val_buf[i];
                if (mTempEQGainBuf[i] < nMinGain) {
                    mTempEQGainBuf[i] = nMinGain;
                }
                if (mTempEQGainBuf[i] > nMaxGain) {
                    mTempEQGainBuf[i] = nMaxGain;
                }
            }
        }

        //return AudioCtlJNI.SetEffectGainValues(mTempEQGainBuf);
        return 0;
    }
    
    public static void SaveCustomEQGainBuf(int[] databuf){
    	CopyEQGainBuf(databuf, mCustomEQGainBuf);
    }
    
    public static int SetSpecialModeEQGain(int tmp_val) {
        if (GlobalConst.DEBUG_MODE_SELECT == GlobalConst.DEBUG_MODE_EMULATOR)
            return tmp_val;

        if (tmp_val < GlobalConst.SOUNDMODE_START
            || tmp_val > GlobalConst.SOUNDMODE_END) {
            tmp_val = GlobalConst.SOUNDMODE_STD;
        }

        if (tmp_val == GlobalConst.SOUNDMODE_USER) {
            CopyEQGainBuf(mCustomEQGainBuf, mCurEQGainBuf);
        } else {
            System.arraycopy(mEQPresetValueBuf, tmp_val * CC_EQ_BAND_CNT, mCurEQGainBuf, 0, CC_EQ_BAND_CNT);
        }

        RealSetEffectGain(mCurEQGainBuf);

        return tmp_val;
    }

    public static int SetCurEQGain() {
        if (GlobalConst.DEBUG_MODE_SELECT == GlobalConst.DEBUG_MODE_EMULATOR)
            return 0;

        RealSetEffectGain(mCurEQGainBuf);
        return 1;
    }

    public static int GetCurEQGain(int gain_buf[]) {
        if (GlobalConst.DEBUG_MODE_SELECT == GlobalConst.DEBUG_MODE_EMULATOR)
            return 0;

        CopyEQGainBuf(mCurEQGainBuf, gain_buf);
        return 1;
    }

    public static int GetCurEQGainBufItem(int buf_index) {
        if (GlobalConst.DEBUG_MODE_SELECT == GlobalConst.DEBUG_MODE_EMULATOR)
            return 0;

        if (buf_index >= 0 && buf_index < CC_EQ_BAND_CNT)
            return mCurEQGainBuf[buf_index];

        return 0;
    }

    public static int GetEQMinGainVal() {
        return CC_MIN_EQ_GAIN_VAL;
    }

    public static int GetEQMaxGainVal() {
        return CC_MAX_EQ_GAIN_VAL;
    }

    public static int GetSoundMinBalanceVal() {
        return CC_MIN_SOUND_BALANCE_VAL;
    }

    public static int GetSoundMaxBalanceVal() {
        return CC_MAX_SOUND_BALANCE_VAL;
    }

    private static void CopyEQGainBuf(int src_buf[], int dst_buf[]) {
        System.arraycopy(src_buf, 0, dst_buf, 0, CC_EQ_BAND_CNT);
    }
    
    public static void SetAudioLineOutMute(boolean isMute) {
        if (isMute == false) {
            osdJNI.SetGpioCtrl("w x 69 0");
        } else {
            osdJNI.SetGpioCtrl("w x 69 1");
        }
    }
    
    private static int SetSpecialModeBTVolume(int soundMode) {
        if (GlobalConst.DEBUG_MODE_SELECT == GlobalConst.DEBUG_MODE_EMULATOR)
            return soundMode;

        if (soundMode < GlobalConst.SOUNDMODE_START
            || soundMode > GlobalConst.SOUNDMODE_END) {
            soundMode = GlobalConst.SOUNDMODE_STD;
        }

        if (soundMode == GlobalConst.SOUNDMODE_USER) {
            mCurAudioBassVolume = mCustomBassVolume;
            mCurAudioTrebleVolume = mCustomTrebleVolume;
        } else {
            mCurAudioBassVolume = mBassVolume[soundMode];
            mCurAudioTrebleVolume = mTrebleVolume[soundMode];
        }
        RealSetBassVolume(mCurAudioBassVolume);
        return RealSetTrebleVolume(mCurAudioTrebleVolume);
    }
    
    private static int RealSetBassVolume(int bassVol) {
        int mTempBassVol;
        
        if (GetCurAudioWallEffect() == CC_SWITCH_ON) {
            mTempBassVol = mWEBsssVolume + bassVol;
            if (mTempBassVol < CC_MIN_SOUND_VOL) {
                mTempBassVol = CC_MIN_SOUND_VOL;
            } else if (mTempBassVol > CC_MAX_SOUND_VOL) {
                mTempBassVol = CC_MAX_SOUND_VOL;
            }
        } else {
            mTempBassVol = bassVol;
        }
        
        int eq_val;
        eq_val = CalAudioBassVolume(mTempBassVol);
        return AudioCtlJNI.SetAmplifierBassVolume(eq_val);
    }
    
    private static int RealSetTrebleVolume(int TrebleVol) {
        int mTempTrebleVol;
        
        if (GetCurAudioWallEffect() == CC_SWITCH_ON) {
            mTempTrebleVol = mWETrebleVolume + TrebleVol;
            if (mTempTrebleVol < CC_MIN_SOUND_VOL) {
                mTempTrebleVol = CC_MIN_SOUND_VOL;
            } else if (mTempTrebleVol > CC_MAX_SOUND_VOL) {
                mTempTrebleVol = CC_MAX_SOUND_VOL;
            }
        } else {
            mTempTrebleVol = TrebleVol;
        }
        
        int eq_val;
        eq_val = CalAudioTrebleVolume(mTempTrebleVol);
        return AudioCtlJNI.SetAmplifierTrebleVolume(eq_val);
    }

}
