package com.amlogic.android.audioctlapi;

public class AudioCtlJNI {
    // audio alsa Init & Uninit JNI API functions
    public static native int mAlsaInit(int tmp_sleep);

    public static native int mAlsaUninit(int tmp_sleep);

    // audio dsp Init & Uninit JNI API functions
    public static native int mAudioDspStart();

    public static native int mAudioDspStop();

    // audio control JNI API functions
    public static native int SetAudioInSource(int source_type);

    public static native int GetAudioInSource();

    public static native int SetHPPlaybackSwitch(int switch_val);

    public static native int GetHPPlaybackSwitch();

    public static native int SetHeadSetPlayBackVolume(int l_vol, int l_min_vol,
        int l_max_vol, int r_vol, int r_min_vol, int r_max_vol);

    public static native int GetHeadSetPlayBackVolume(int vol_buf[],
        int l_min_vol, int l_max_vol, int r_min_vol, int r_max_vol);

    public static native int SetLineInCaptureVolume(int l_vol, int l_min_vol,
        int l_max_vol, int r_vol, int r_min_vol, int r_max_vol);

    public static native int GetLineInCaptureVolume(int vol_buf[],
        int l_min_vol, int l_max_vol, int r_min_vol, int r_max_vol);

    public static native int SetLineOutPlayBackVolume(int l_vol, int l_min_vol,
        int l_max_vol, int r_vol, int r_min_vol, int r_max_vol);

    public static native int GetLineOutPlayBackVolume(int vol_buf[],
        int l_min_vol, int l_max_vol, int r_min_vol, int r_max_vol);

    public static native int SetAmplifierVolume(int l_vol, int l_min_vol,
        int l_max_vol, int r_vol, int r_min_vol, int r_max_vol);

    public static native int GetAmplifierVolume(int vol_buf[], int l_min_vol,
        int l_max_vol, int r_min_vol, int r_max_vol);

    public static native int SetSupperBassVolume(int vol, int min_vol,
        int max_vol);

    public static native int GetSupperBassVolume(int min_vol, int max_vol);

    public static native int SetAmplifierMute(int mute_state);

    public static native int GetAmplifierMute();

    public static native int SetLOUTPlayBackSwitch(int switch_val);

    public static native int GetLOUTPlayBackSwitch();

    public static native int SetLeftLineInSelect(int line_in_number);

    public static native int GetLeftLineInSelect();

    public static native int SetRightLineInSelect(int line_in_number);

    public static native int GetRightLineInSelect();

    public static native int SetSPPlayBackSwitch(int switch_val);

    public static native int GetSPPlayBackSwitch();

    public static native int LineInSelectChannel(int line_in_number);

    public static native int SetMainVolumeDigitLUTBuf(int digit_lut_buf[]);

    public static native int SetSupperBassVolumeDigitLUTBuf(int digit_lut_buf[]);

    public static native int SetAmplifierTrebleVolume(int treble_vol);

    public static native int SetAmplifierBassVolume(int bass_vol);

    public static native int SetSTA339XSpecialRegister(int reg_addr,
        int reg_data);

    public static native int GetSTA339XSpecialRegister(int reg_addr);

    public static native int SetSTA339XSpecialBiquad(int biquad_ind,
        int data_buf[]);

    public static native int GetSTA339XSpecialBiquad(int biquad_ind,
        int data_buf[]);

    // audio effect JNI API functions
    public static native int mAudioEffectInit();

    public static native int mAudioEffectUninit();

    public static native int SetEffectPresetVal(int eq_mode);

    public static native int GetEffectPresetVal(int eq_mode, int gain_val_buf[]);

    public static native int GetEffectGainValues(int gain_val_buf[]);

    public static native int SetEffectGainValues(int gain_val_buf[]);

    public static native int SetSrsSurroundSwitch(int switch_val);

    public static native int SetSrsTruBassSwitch(int switch_val);

    public static native int SetSrsTruBassGain(int gain_val);

    public static native int SetSrsDialogClaritySwitch(int switch_val);

    public static native int SetSrsDialogClarityGain(int gain_val);

    public static native int ResetDSPRWPtr(int para1);

    // audio utils API functions
    public static native int AudioUtilsInit();

    public static native int AudioUtilsUninit();

    public static native int AudioUtilsStartLineIn();

    public static native int AudioUtilsStartHDMIIn();

    public static native int AudioUtilsStopLineIn();

    public static native int AudioUtilsStopHDMIIn();
}
