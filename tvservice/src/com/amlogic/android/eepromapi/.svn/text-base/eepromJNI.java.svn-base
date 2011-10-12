package com.amlogic.android.eepromapi;

import android.R.integer;
import android.util.Log;
import java.util.concurrent.TimeUnit;

import com.amlogic.android.vdinapi.vdinJNI;
import com.amlogic.android.vdinapi.vdinJNI.*;
import com.amlogic.android.vppapi.vppJNI;
import com.amlogic.android.vppapi.vppJNI.*;
import com.amlogic.android.afeapi.afeJNI;
import com.amlogic.android.afeapi.afeJNI.*;
import com.amlogic.android.osdapi.osdJNI;
import com.amlogic.android.osdapi.osdJNI.*;
import com.amlogic.android.audioctlapi.AudioCtlJNI;
import com.amlogic.android.audioctlapi.AudioCustom;
import com.amlogic.android.dreampanel.dreampanelJNI;
import com.amlogic.android.tunerapi.tunerJNI;
import com.amlogic.tvjni.GlobalConst;

import android.os.SystemProperties;

//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
//
//
//note: eeprom save/load UI value, not real setting value
//xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
public class eepromJNI {
    final static String DBTAG = "EEPROM";
    final static String EEPROM_SIZE = "4K_BYTE"; // "8K_BYTE"
    static int WPctl = -1;

    final static int RESERVE_LEN = 64; // dont change!!
    final static int HDCP_LEN = 329;
    final static int MAC_LEN = 6;
    final static int VGA_EDID_LEN = 256; // dont change!!
    final static int HDMI_EDID_LEN = 256;
    final static int CHANNEL_INFO_LEN = 2048;
    final static int VIDEO_SETTING_LEN = 128;
    final static int VGA_ADJUSTMENT_LEN = 480;
    final static int ADC_CALIBRATION_LEN = 160; // max 4 source
    final static int SOURCE_SELECT_LEN = 2;
    final static int TV_INFO_LEN = 1; // current channel no.
    final static int FUNC_3D_LEN = 4;
    final static int AUDIO_SETTING_LEN = 16;
    final static int AUDIO_EFFECT_GAIN_LEN = 8;
    final static int AUDIO_HDVOL_LEN = 14;

    final static int ONE_CHANINFO_LEN = 4;
    final static int ONE_VGA_FMT_LEN = 4;
    final static int ONE_VDIEO_SETTING_LEN = 10;
    final static int ONE_GAINOFFSET_LEN = 64;
    final static int ONE_HDVOL_LEN = 7;
    final static int ONE_BYTE_LEN = 1;
    final static int MISC_LEN = 16;
    final static int RGB_OGO_LEN = 19;
    final static int FACTORY_SETTING_LEN = 16;
    final static int FACTORY_OPTION_LEN = 16;
    final static int NEW_EEPROM_FLAG_LEN = 3;
    final static int EEPROM_DATE_LEN = 10;
    final static int BARCODE_LEN = 32;
    final static int SSC_LEN = 32;

    private static class BlockStartAddr {
        static int RESERVE_NULL = 0;
        static int CHARACTER_ADDR = 10;
        static int HDCP = 64;
        static int MAC = 393;
        static int RESERVE_AREA0 = 400;
        static int VGA_EDID = 448;
        static int HDMI_EDID = 736;
        static int NEW_EEPROM_FLAG1 = 1024;
        static int CHANNEL_INFO = 1056;
        static int VIDEO_SETTING = 3104;
        static int VGA_ADJUSTMENT = 3232;
        static int ADC_CALIBRATION = 3712;
        static int NEW_EEPROM_FLAG2 = 3904;
        static int SOURCE_SELECT = 3936;
        static int TV_INFO = 3940;
        static int AV_COLORSYS = 3942;
        static int NUMBER_LIMIT = 3944;
        // static int FUNC_3D = 3946;
        static int GAMMA_SEL = 3952;
        static int RESERVE_AREA1 = 3968;
        static int SYS_STATUS = 4000;
        static int FACTORY_SETTING = 4032;
        static int FACTORY_OPTION = 4048;
        static int AUDIO_SETTING = 4096;
        static int AUDIO_EFFECT_GAIN = 4160;
        static int NEW_EEPROM_FLAG3 = 4192;
        static int MISC = 4224;
        static int NAVIGATE_FLAG = 4280;
        static int RGB_OGO = 4320;
        static int AUDIO_HDVOL = 4352;
        static int ONOFF_MUSIC_HDVOL = 4368;
        static int LEFT_FOR_FUTURE = 4384;
        static int EEPROM_DATE = 8128;
        static int INIT_TIMES = 8138;
        static int BARCODE = 8160;
        static int ADDR_END = 8192;

        static void SelectEepromSize(String eepsize) {
            if (eepsize.equals("4K_BYTE")) {
                RESERVE_NULL = 0;
                CHARACTER_ADDR = 10;
                HDCP = 32;
                MAC = 361;
                NEW_EEPROM_FLAG1 = 379;
                RESERVE_AREA0 = 384;
                VGA_EDID = 416;
                HDMI_EDID = 672;
                CHANNEL_INFO = 928;
                VIDEO_SETTING = 2976;
                VGA_ADJUSTMENT = 3104;
                ADC_CALIBRATION = 3584;
                NEW_EEPROM_FLAG2 = 3744;
                SOURCE_SELECT = 3746;
                TV_INFO = 3748;
                AV_COLORSYS = 3750;
                NUMBER_LIMIT = 3752;
                // FUNC_3D = 3754;
                GAMMA_SEL = 3758;
                NAVIGATE_FLAG = 3760;
                RESERVE_AREA1 = 3776;
                FACTORY_SETTING = 3808;
                FACTORY_OPTION = 3824;
                AUDIO_SETTING = 3840;
                AUDIO_EFFECT_GAIN = 3872;
                NEW_EEPROM_FLAG3 = 3900;
                MISC = 3904;
                RGB_OGO = 3936;
                AUDIO_HDVOL = 3968;
                ONOFF_MUSIC_HDVOL = 3984;
                SYS_STATUS = 4000;
                LEFT_FOR_FUTURE = 4002;
                EEPROM_DATE = 4032;
                INIT_TIMES = 4042;
                BARCODE = 4064;
                ADDR_END = 4096;

            } else if (eepsize.equals("8K_BYTE")) {
                Log.d(DBTAG, "8k byte eeprom.");
                RESERVE_NULL = 0;
                CHARACTER_ADDR = 10;
                HDCP = 64;
                MAC = 393;
                RESERVE_AREA0 = 400;
                VGA_EDID = 448;
                HDMI_EDID = 736;
                NEW_EEPROM_FLAG1 = 1024;
                CHANNEL_INFO = 1056;
                VIDEO_SETTING = 3104;
                VGA_ADJUSTMENT = 3232;
                ADC_CALIBRATION = 3712;
                NEW_EEPROM_FLAG2 = 3904;
                SOURCE_SELECT = 3936;
                TV_INFO = 3940;
                AV_COLORSYS = 3942;
                NUMBER_LIMIT = 3944;
                // FUNC_3D = 3946;
                GAMMA_SEL = 3952;
                NAVIGATE_FLAG = 3954;
                RESERVE_AREA1 = 3968;
                SYS_STATUS = 4000;
                FACTORY_SETTING = 4032;
                FACTORY_OPTION = 4048;
                AUDIO_SETTING = 4096;
                AUDIO_EFFECT_GAIN = 4160;
                NEW_EEPROM_FLAG3 = 4192;
                MISC = 4224;
                RGB_OGO = 4288;
                AUDIO_HDVOL = 4352;
                ONOFF_MUSIC_HDVOL = 4368;
                LEFT_FOR_FUTURE = 4384;
                EEPROM_DATE = 8128;
                INIT_TIMES = 8138;
                BARCODE = 8160;
                ADDR_END = 8192;
            }
            Log.d(DBTAG, (ADDR_END / 1024) + "K Byte EEPROM.");
        }

        public void GetFreeByteSize() {
            ;
        }
    }

    public enum SourceInput {
        TV, AV1, YPBPR1, HDMI1, HDMI2, HDMI3, VGA, END
    }

    /* by SourceType, every function use 1 byte to save */
    public enum VideoFunc {
        BRIGHTNESS,
        CONTRAST,
        COLOR,
        HUE,
        SHARPNESS,
        COLOR_TEMP,
        NOISE_RE,
        PICTURE_MODE,
        ASPECT_RAT,
        END;
    }

    public enum AudioFunc {
        MAIN_VOLUME,
        BALANCE_VAL,
        SRS_SWITCH,
        DIALOG_CLARITY_SWITCH,
        TRUEBASS_SWITCH,
        SOUND_MODE_VAL,
        EQ_MODE_VAL,
        SUPPERBASS_SWITCH,
        SUPPERBASS_VOLUME,
        BASS_VOLUME,
        TREBLE_VOLUME,
        WALL_EFFCT_SWITCH
    }

    public enum VGAFunc {
        HPOSITION, VPOSITION, CLOCK, PHASE, AUTO_FLAG
    }

    public enum Setting3D {
        MODE, LRSWITCH, DEPTH, MODE3Dto2D, END
    }

    public enum ADCSrcId {
        VGA, COMP0, END // COMP1, COMP2
    }

    public enum MISCFunc {
        LANGUAGE,
        POWERONSRC,
        KEYSOUND,
        COLOR_MANAGEMENT,
        BACKLIGHT,
        DREAMPANEL,
        ONOFFMUSIC,
        LISTMODE,
        REPEATMODE,
        SWITCHMODE,
        PLAYMODE,
        END
    }

    public enum PanelType {
        LG42E83, LG47E83, LG55E83, LG42E85, LG47E85, END
    }

    public enum FactoryFunc {
        BURN_FLAG,
        ONEKEY_ONOFF,
        AUTOCAL_FLAG,
        FACTORY_MODE_FLAG,
        SSC_RANGE,
        SSC_LOWRANGE,
        SSC_CYSLE,
        SSC_ONOFF,
        LVDS_DELAY_FLAG_A,
        LVDS_DELAY_FLAG_B,
        LVDS_DELAY_FLAG_C,
        LVDS_DELAY_FLAG_D,
        END
    }

    public enum FactoryOption {
        DEF_HDCP_FLAG,
        ONLINE_MUSIC_FLAG,
        LVDS,
        START_PIC_FALG,
        STANDBY_MODE_FLAG,
        HDMI_EQ_MODE,
        HDMI_INTERNAL_MODE_BYTE1,
        HDMI_INTERNAL_MODE_BYTE2,
        HDMI_INTERNAL_MODE_BYTE3,
        HDMI_INTERNAL_MODE_BYTE4,
        SERIALMODE_SWITCH,
        END
    }

    public enum HDVolSrc {
        TV, OTHERS, END
    }

    public enum SysStatus {
        NORMAL, STANDBY, END
    }

    final static int NAVIGATE_SEARCH_ON = 0x13;
    // modify three data bellow will init all eeprom data.
    final static int EEPROM_FLAG1_DATA = 0x69;
    final static int EEPROM_FLAG2_DATA = 0x4b;
    final static int EEPROM_FLAG3_DATA = 0x5a;
    final static int[] DEF_MAC_ADDR = {
        0x00, 0x1A, 0x9A, 0xE1, 0x00, 0x00
    };
    final static int CHARACTER_DATA = 0x8a;

    private final static int HdmiEdid[] = { // 256 byte
        0x00,
        0xFF,
        0xFF,
        0xFF,
        0xFF,
        0xFF,
        0xFF,
        0x00,
        0x4D,
        0x77,
        0x00,
        0x32,
        0x00,
        0x00,
        0x00,
        0x00,
        0x1B,
        0x12,
        0x01,
        0x03,
        0x80,
        0x46,
        0x28,
        0x78,
        0xCA,
        0x0D,
        0xC9,
        0xA0,
        0x57,
        0x47,
        0x98,
        0x27,
        0x12,
        0x48,
        0x4C,
        0xBF,
        0xCF,
        0x00,
        0x61,
        0x00,
        0x61,
        0x40,
        0x61,
        0x80,
        0x61,
        0xC0,
        0x81,
        0x00,
        0x81,
        0x40,
        0x81,
        0x80,
        0x81,
        0xC0,
        0x01,
        0x1D,
        0x00,
        0x72,
        0x51,
        0xD0,
        0x1E,
        0x20,
        0x6E,
        0x28,
        0x55,
        0x00,
        0xC4,
        0x8E,
        0x21,
        0x00,
        0x00,
        0x1E,
        0x00,
        0x00,
        0x00,
        0xFD,
        0x00,
        0x2F,
        0x50,
        0x1E,
        0x50,
        0x0F,
        0x00,
        0x0A,
        0x20,
        0x20,
        0x20,
        0x20,
        0x20,
        0x20,
        0x00,
        0x00,
        0x00,
        0xFC,
        0x00,
        0x73,
        0x6B,
        0x79,
        0x77,
        0x6F,
        0x72,
        0x74,
        0x68,
        0x2D,
        0x6C,
        0x63,
        0x64,
        0x0A,
        0x00,
        0x00,
        0x00,
        0x10,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x01,
        0xF0,
        0x02,
        0x03,
        0x21,
        0x71,
        0x4B,
        0x84,
        0x03,
        0x05,
        0x0A,
        0x12,
        0x13,
        0x14,
        0x15,
        0x10,
        0x1F,
        0x06,
        0x23,
        0x0F,
        0x07,
        0x07,
        0x83,
        0x4F,
        0x00,
        0x00,
        0x68,
        0x03,
        0x0C,
        0x00,
        0x10,
        0x00,
        0x38,
        0x2D,
        0x00,
        0x01,
        0x1D,
        0x00,
        0x72,
        0x51,
        0xD0,
        0x1E,
        0x20,
        0x6E,
        0x28,
        0x55,
        0x00,
        0xE8,
        0x12,
        0x11,
        0x00,
        0x00,
        0x1E,
        0x8C,
        0x0A,
        0xD0,
        0x90,
        0x20,
        0x40,
        0x31,
        0x20,
        0x0C,
        0x40,
        0x55,
        0x00,
        0xE8,
        0x12,
        0x11,
        0x00,
        0x00,
        0x18,
        0x8C,
        0x0A,
        0xD0,
        0x8A,
        0x20,
        0xE0,
        0x2D,
        0x10,
        0x10,
        0x3E,
        0x96,
        0x00,
        0xE8,
        0x12,
        0x11,
        0x00,
        0x00,
        0x18,
        0x01,
        0x1D,
        0x80,
        0x18,
        0x71,
        0x1C,
        0x16,
        0x20,
        0x58,
        0x2C,
        0x25,
        0x00,
        0xC4,
        0x8E,
        0x21,
        0x00,
        0x00,
        0x9E,
        0x01,
        0x1D,
        0x80,
        0xD0,
        0x72,
        0x1C,
        0x16,
        0x20,
        0x10,
        0x2C,
        0x25,
        0x80,
        0xC4,
        0x8E,
        0x21,
        0x00,
        0x00,
        0x9E,
        0x00,
        0x00,
        0x00,
        0x00,
        0x0E
    };

    private final static int VgaEdid[] = { // 256 byte
        0x00,
        0xff,
        0xff,
        0xff,
        0xff,
        0xff,
        0xff,
        0x00,
        0x4d,
        0x77,
        0x01,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x01,
        0x0f,
        0x01,
        0x03,
        0x1c,
        0x3c,
        0x22,
        0x78,
        0x0a,
        0x0d,
        0xc9,
        0xa0,
        0x57,
        0x47,
        0x98,
        0x27,
        0x12,
        0x48,
        0x4c,
        0xbf,
        0xef,
        0x00,
        0x01,
        0x01,
        0x01,
        0x01,
        0x01,
        0x01,
        0x01,
        0x01,
        0x01,
        0x01,
        0x01,
        0x01,
        0x01,
        0x01,
        0x01,
        0x01,
        0x01,
        0x1d,
        0x00,
        0x72,
        0x51,
        0xd0,
        0x1e,
        0x20,
        0x6e,
        0x28,
        0x55,
        0x00,
        0xc4,
        0x8e,
        0x21,
        0x00,
        0x00,
        0x1e,
        0xf3,
        0x39,
        0x80,
        0x18,
        0x71,
        0x38,
        0x2d,
        0x40,
        0x58,
        0x2c,
        0x45,
        0x00,
        0xc4,
        0x8e,
        0x21,
        0x00,
        0x00,
        0x1e,
        0x00,
        0x00,
        0x00,
        0xfc,
        0x00,
        0x53,
        0x6b,
        0x79,
        0x77,
        0x6f,
        0x72,
        0x74,
        0x68,
        0x20,
        0x4c,
        0x43,
        0x44,
        0x0a,
        0x00,
        0x00,
        0x00,
        0xfd,
        0x00,
        0x31,
        0x4c,
        0x0f,
        0x50,
        0x0e,
        0x00,
        0x0a,
        0x20,
        0x20,
        0x20,
        0x20,
        0x20,
        0x20,
        0x00,
        0x46,
        0x02,
        0x03,
        0x20,
        0x74,
        0x4B,
        0x84,
        0x10,
        0x1F,
        0x05,
        0x13,
        0x14,
        0x01,
        0x02,
        0x11,
        0x06,
        0x15,
        0x23,
        0x09,
        0x07,
        0x03,
        0x83,
        0x01,
        0x00,
        0x00,
        0x67,
        0x03,
        0x0C,
        0x00,
        0x10,
        0x00,
        0xB8,
        0x2D,
        0x01,
        0x1D,
        0x00,
        0xBC,
        0x52,
        0xD0,
        0x1E,
        0x20,
        0xB8,
        0x28,
        0x55,
        0x40,
        0xC4,
        0x8E,
        0x21,
        0x00,
        0x00,
        0x1E,
        0x01,
        0x1D,
        0x80,
        0xD0,
        0x72,
        0x1C,
        0x16,
        0x20,
        0x10,
        0x2C,
        0x25,
        0x80,
        0xC4,
        0x8E,
        0x21,
        0x00,
        0x00,
        0x9E,
        0x8C,
        0x0A,
        0xD0,
        0x8A,
        0x20,
        0xE0,
        0x2D,
        0x10,
        0x10,
        0x3E,
        0x96,
        0x00,
        0x13,
        0x8E,
        0x21,
        0x00,
        0x00,
        0x18,
        0x8C,
        0x0A,
        0xD0,
        0x90,
        0x20,
        0x40,
        0x31,
        0x20,
        0x0C,
        0x40,
        0x55,
        0x00,
        0x13,
        0x8E,
        0x21,
        0x00,
        0x00,
        0x18,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x00,
        0x78
    };
    private final static int[] HdcpHead = {
        0x53,
        0x4B,
        0x59,
        0x01,
        0x00,
        0x10,
        0x0D,
        0x15,
        0x3A,
        0x8E,
        0x99,
        0xEE,
        0x2A,
        0x55,
        0x58,
        0xEE,
        0xED,
        0x4B,
        0xBE,
        0x00,
        0x74,
        0xA9,
        0x00,
        0x10,
        0x0A,
        0x21,
        0xE3,
        0x30,
        0x66,
        0x34,
        0xCE,
        0x9C,
        0xC7,
        0x8B,
        0x51,
        0x27,
        0xF9,
        0x0B,
        0xAD,
        0x09
    };

    private final static int DEF_SRC_IN = SourceInput.TV.ordinal();
    private final static int DEF_VGA_FMT = SigFormat.VGA_1280X768P_59D995.ordinal();
    private final static int DEF_COLOR_SYS = GlobalConst.CVBS_COLORSYS_AUTO;
    // private final static int DEF_AUDIO_STD =
    private final static int DEF_ONOFF_MUSIC_VOL = 0;

    // AV, COMPONENT, VGA, HDMI, MPEG,
    private final static int[][] DEF_VIDEO_SETTING = { // Default UI value
        // BRIGHTNESS, CONTRAST, COLOR, HUE, SHARPNESS, COLOR_TEMP, NOISE_RE,
        // PICTURE_MODE, ASPECT_RAT, RESERVED
        {
            50,
            50,
            50,
            50,
            50,
            ColorTemp.STANDARD.ordinal(),
            NoiseRd.AUTO.ordinal(),
            PictureMode.STANDARD.ordinal(),
            ScreenMode.MODE169.ordinal(),
            -1
        },
        {
            50,
            50,
            50,
            50,
            50,
            ColorTemp.STANDARD.ordinal(),
            NoiseRd.AUTO.ordinal(),
            PictureMode.STANDARD.ordinal(),
            ScreenMode.MODE169.ordinal(),
            -1
        },
        {
            50,
            50,
            50,
            50,
            0,
            ColorTemp.STANDARD.ordinal(),
            NoiseRd.AUTO.ordinal(),
            PictureMode.STANDARD.ordinal(),
            ScreenMode.MODE169.ordinal(),
            -1
        },
        {
            50,
            50,
            50,
            50,
            50,
            ColorTemp.STANDARD.ordinal(),
            NoiseRd.AUTO.ordinal(),
            PictureMode.STANDARD.ordinal(),
            ScreenMode.MODE169.ordinal(),
            -1
        },
        {
            50,
            50,
            50,
            50,
            50,
            ColorTemp.STANDARD.ordinal(),
            NoiseRd.AUTO.ordinal(),
            PictureMode.STANDARD.ordinal(),
            ScreenMode.MODE169.ordinal(),
            -1
        },
    };

    private final static int AudioSetingDef[] = {
        AudioCustom.CC_DEF_SOUND_VOL, // MAIN_VOLUME,
        50 + AudioCustom.CC_DEF_SOUND_BALANCE_VAL, // BALANCE_VAL,
        AudioCustom.CC_SWITCH_OFF, // SRS_SWITCH,
        AudioCustom.CC_SWITCH_OFF, // DIALOG_CLARITY_SWITCH,
        AudioCustom.CC_SWITCH_OFF, // TRUEBASS_SWITCH,
        GlobalConst.SOUNDMODE_STD, // SOUND_MODE_VAL,
        0xFF, // EQ_MODE_VAL,
        AudioCustom.CC_SWITCH_OFF, // SUPPERBASS_SWITCH,
        AudioCustom.CC_DEF_SUPPERBASS_VOL, // SUPPERBASS_VOLUME,
        AudioCustom.CC_DEF_BASS_TREBLE_VOL, // BASS_VOLUME,
        AudioCustom.CC_DEF_BASS_TREBLE_VOL, // TREBLE_VOLUME,
        AudioCustom.CC_SWITCH_OFF, // WALL_EFFCT_SWITCH
        0xFF, // NULL
        0xFF, // NULL
        0xFF, // NULL
        0xFF, // NULL
    };

    private final static int AudioEffGainDef[] = {
        AudioCustom.CC_DEF_EQ_GAIN_VAL,
        AudioCustom.CC_DEF_EQ_GAIN_VAL,
        AudioCustom.CC_DEF_EQ_GAIN_VAL,
        AudioCustom.CC_DEF_EQ_GAIN_VAL,
        AudioCustom.CC_DEF_EQ_GAIN_VAL,
        AudioCustom.CC_DEF_EQ_GAIN_VAL,
    };

    private final static int DEF_AUDIO_HDVOL_TV[] = {
        AudioCustom.DEF_TV_VOL_001,
        AudioCustom.DEF_TV_VOL_010,
        AudioCustom.DEF_TV_VOL_030,
        AudioCustom.DEF_TV_VOL_050,
        AudioCustom.DEF_TV_VOL_070,
        AudioCustom.DEF_TV_VOL_090,
        AudioCustom.DEF_TV_VOL_100,
    };
    private final static int DEF_AUDIO_HDVOL_OTHERS[] = {
        AudioCustom.DEF_AV_VOL_001,
        AudioCustom.DEF_AV_VOL_010,
        AudioCustom.DEF_AV_VOL_030,
        AudioCustom.DEF_AV_VOL_050,
        AudioCustom.DEF_AV_VOL_070,
        AudioCustom.DEF_AV_VOL_090,
        AudioCustom.DEF_AV_VOL_100,
    };

    private final static int DEF_MISC_SETTING[] = {
        1, // LANGUAGE
        0, // POWERONSRC
        0, // KEYSOUND
        1, // COLOR_MANAGEMENT
        100, // BACKLIGHT
        2, // DREAMPANEL
        1, // ONOFFMUSIC
        1, // LISTMODE
        1, // REPEATMODE
        1, // SWITCHMODE
        1, // PLAYMODE
    };

    private final static int[] DEF_FACTORY_SETTING = {
        0, // BURN_FLAG
        1, // ONEKEY_ONOFF
        0, // AUTOCAL_FLAG
        0, // FACTORY_MODE_FLAG
        0, // SSC_RANGE
        0, // SSC_LOWRANGE
        0, // SSC_CYSLE
        0, // SSC_ONOFF
        3, // LVDS_DELAY_FLAG_A
        5, // LVDS_DELAY_FLAG_B
        5, // LVDS_DELAY_FLAG_C
        3, // LVDS_DELAY_FLAG_D
    };

    private final static int[] DEF_FACTORY_OPTION = {
        0, // DEF_HDCP_FLAG
        1, // ONLINE_MUSIC_FLAG
        0, // LVDS
        0, // START_PIC_FALG
        0, // STANDBY_MODE_FLAG
        0, // HDMI_EQ_MODE
        0, // HDMI_INTERNAL_MODE_BYTE1
        0, // HDMI_INTERNAL_MODE_BYTE2
        0, // HDMI_INTERNAL_MODE_BYTE3
        0, // HDMI_INTERNAL_MODE_BYTE4
        0, // SERIALMODE_SWITCH
    };

    private final static int[] DEF_VGA_ADJUSTMENT = {
        50, // HPOSITION
        50, // VPOSITION
        50, // CLOCK
        50, // PHASE
        0, // AUTO_FLAG
    };

    public static int[][] AllChInfo = new int[GlobalConst.TV_CHANNEL_TOTAL_PROGRAM_COUNT][ONE_CHANINFO_LEN];
    public static int[][] VideoSettingBySrc = new int[SrcType.END.ordinal()][ONE_VDIEO_SETTING_LEN];
    public static int[][] AudioHdVol = new int[HDVolSrc.END.ordinal()][ONE_HDVOL_LEN];
    private static int MiscData[] = new int[MISC_LEN];
    // private static int[] Func3DData = new int[Setting3D.END.ordinal()];
    private static int[] SrcInput = new int[2];
    private static int CurChannelNumData = 0;
    private static int AVColorData = 0;
    private static int NumberInputLimitData = 0;
    private static int GammaSel = 0;
    private static int NavigateFlagData = 0;
    private static int OnoffMusicVol = 0;

    private static int AudioSetingData[] = {
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
    };

    private static int AudioEffGain[] = {
        -1, -1, -1, -1, -1, -1,
    };

    private static int VGAData[] = {
        -1, -1, -1, -1, -1, -1,
    };

    private static boolean isBusOff = false;
    private static int FacSettingData[] = new int[FACTORY_SETTING_LEN];

    /****************************************
     * 
     * @brief: init eeprom module
     ***************************************/
    private static void SetWriteProtect(boolean isProtection) {
        WPctl = osdJNI.SetGpioCtrl("R x 0");
        // Log.d(DBTAG,"write protect pin = " + WPctl);

        if (isProtection == false) {
            osdJNI.SetGpioCtrl("w x 0 0");
            // Log.d(DBTAG, "off protect");
        } else {
            osdJNI.SetGpioCtrl("w x 0 1");
            // Log.d(DBTAG,"on protect!!!!");
        }
    }

    private static boolean CheckEEPError() {
        int OffsetAddr = BlockStartAddr.NEW_EEPROM_FLAG1;

        if (ReadEepromOneByte(0, OffsetAddr) != EEPROM_FLAG1_DATA) {
            OffsetAddr = BlockStartAddr.NEW_EEPROM_FLAG2;
            if (ReadEepromOneByte(0, OffsetAddr) != EEPROM_FLAG2_DATA) {
                OffsetAddr = BlockStartAddr.NEW_EEPROM_FLAG3;
                if (ReadEepromOneByte(0, OffsetAddr) != EEPROM_FLAG3_DATA) {
                    Log.d(DBTAG, "check eeprom ERROR!");
                    return true;
                }
            }
        }
        return false;
    }

    public static void LoadAllChInfoBuf() {
        int offsetAddr = BlockStartAddr.CHANNEL_INFO;
        int[] buf = new int[GlobalConst.TV_CHANNEL_TOTAL_PROGRAM_COUNT
            * ONE_CHANINFO_LEN];

        buf = ReadEepromNByte(offsetAddr, buf.length);

        for (int i = 0; i < GlobalConst.TV_CHANNEL_TOTAL_PROGRAM_COUNT; i++) {
            for (int j = 0; j < ONE_CHANINFO_LEN; j++) {
                AllChInfo[i][j] = buf[i * ONE_CHANINFO_LEN + j];
            }
        }

        Log.d(DBTAG, "init all channel info OK!");
    }

    public static void LoadAllVppBySrcBuf() {
        int offsetAddr = BlockStartAddr.VIDEO_SETTING;
        final int BUFLEN = SrcType.END.ordinal() * ONE_VDIEO_SETTING_LEN;
        int[] buf = new int[BUFLEN];

        buf = ReadEepromNByte(offsetAddr, buf.length);

        for (int i = 0; i < SrcType.END.ordinal(); i++) {
            Log.d(DBTAG, "load video settting buf: " + i);

            for (int j = 0; j < ONE_VDIEO_SETTING_LEN; j++) {
                VideoSettingBySrc[i][j] = buf[i * ONE_VDIEO_SETTING_LEN + j];
                Log.d(DBTAG, "data: " + VideoSettingBySrc[i][j]);
            }
        }

        /** doesnt save display mode in VideoPlay etc. **/
        VideoSettingBySrc[SrcType.MPEG.ordinal()][VideoFunc.ASPECT_RAT.ordinal()] = ScreenMode.MODE169.ordinal();
    }

    public static void LoadMiscSettingBuf() {
        int offsetAddr = BlockStartAddr.MISC;

        MiscData = ReadEepromNByte(offsetAddr, MiscData.length);

        for (int i = 0; i < MiscData.length; i++)
            Log.d(DBTAG, "load misc data :" + MiscData[i]);
    }

    public static void LoadOtherByteSettingBuf() {
        int offsetAddr = BlockStartAddr.SOURCE_SELECT;
        final int LOAD_LEN = BlockStartAddr.RESERVE_AREA1 - offsetAddr + 1;

        int[] buf = new int[LOAD_LEN];

        buf = ReadEepromNByte(offsetAddr, buf.length);

        SrcInput[0] = buf[BlockStartAddr.SOURCE_SELECT - offsetAddr];
        SrcInput[1] = buf[BlockStartAddr.SOURCE_SELECT - offsetAddr + 1];
        Log.d(DBTAG, "load one byte setting: source input = " + SrcInput[0]);

        CurChannelNumData = buf[BlockStartAddr.TV_INFO - offsetAddr];
        Log.d(DBTAG, "load one byte setting: current channel no. = "
            + CurChannelNumData);

        AVColorData = buf[BlockStartAddr.AV_COLORSYS - offsetAddr];
        Log.d(DBTAG, "load one byte setting: av color system = " + AVColorData);

        NumberInputLimitData = buf[BlockStartAddr.NUMBER_LIMIT - offsetAddr];
        Log.d(DBTAG, "load one byte setting: no. limit = "
            + NumberInputLimitData);

        // Func3DData[Setting3D.MODE.ordinal()] = buf[BlockStartAddr.FUNC_3D
        // - offsetAddr];
        // Func3DData[Setting3D.LRSWITCH.ordinal()] = buf[BlockStartAddr.FUNC_3D
        // - offsetAddr + 1];
        // Func3DData[Setting3D.DEPTH.ordinal()] = buf[BlockStartAddr.FUNC_3D
        // - offsetAddr + 2];
        // Func3DData[Setting3D.MODE3Dto2D.ordinal()] =
        // buf[BlockStartAddr.FUNC_3D
        // - offsetAddr + 3];

        GammaSel = buf[BlockStartAddr.GAMMA_SEL - offsetAddr];
        Log.d(DBTAG, "load one byte setting: gamma select = " + GammaSel);

        NavigateFlagData = buf[BlockStartAddr.NAVIGATE_FLAG - offsetAddr];
        Log.d(DBTAG, "load one byte setting: navigate flag = "
            + NavigateFlagData);
    }

    public static void InitEeprom() {
        OpenI2CModule(0); // current usage: always set '0'
        SetWriteProtect(true);

        BlockStartAddr.SelectEepromSize(EEPROM_SIZE);

        if (CheckEEPError()) {
            Log.d(DBTAG, ".....Load default value to EEP.........");
            SetEEPToDefault();
        } else {
            // this is temp, later should be put into busoff function.
            if (ReadCharaterData() != CHARACTER_DATA) {
                WriteCharaterData();
            }
            Log.d(DBTAG, "Load user setting...");
            afeJNI.vgaGainOffset = LoadADCGainOffset(ADCSrcId.VGA);
            afeJNI.comp0GainOffset = LoadADCGainOffset(ADCSrcId.COMP0);
            vppJNI.gRGBogo = LoadRgbOgo();
        }

        LoadAllVppBySrcBuf();
        LoadAllChInfoBuf();
        LoadMiscSettingBuf();
        LoadFactorySettingBuf();
        // wk 110805 temply cancel this.
        // LoadOnOffMusicVolBuf();
        // LoadAudioHdVolBuf();
        LoadOtherByteSettingBuf();
        // SaveSysStatus(SysStatus.NORMAL);
    }

    public static void LoadHdcpKey() {
        if (IsLoadDefaultHdcp()) {
            Log.d(DBTAG, "load default hdcp key!");
            LoadHdcpKey(0);
        } else {
            Log.d(DBTAG, "load eeprom hdcp key!");
            newLoadHdcpKey(0, HdcpHead, BlockStartAddr.HDCP);
        }
    }

    public static int IsHdcpKeyOk() {
        return IsHdcpKeyRight();
    }

    public static void SetDefaultMacAddr() {
        WriteEepromNByte(BlockStartAddr.MAC, MAC_LEN, DEF_MAC_ADDR);
    }

    public static int[] GetMacAddr() {
        int[] tempbuf = new int[MAC_LEN];

        tempbuf = ReadEepromNByte(BlockStartAddr.MAC, MAC_LEN);

        // if(tempbuf[0] != 0x00)
        // SetDefaultMacAddr();
        // for(int i=0; i<MAC_LEN; i++)
        // Log.d(DBTAG,"get mac addr = " + tempbuf[i]);

        return tempbuf;
    }

    public static void SaveMacAddr() {
        int[] macaddr = new int[MAC_LEN];
        String prefix = SystemProperties.get("ubootenv.var.ethaddr");

        macaddr[0] = Integer.parseInt((prefix.substring(0, 2)), 16);
        macaddr[1] = Integer.parseInt((prefix.substring(3, 5)), 16);
        macaddr[2] = Integer.parseInt((prefix.substring(6, 8)), 16);
        macaddr[3] = Integer.parseInt((prefix.substring(9, 11)), 16);
        macaddr[4] = Integer.parseInt((prefix.substring(12, 14)), 16);
        macaddr[5] = Integer.parseInt((prefix.substring(15, 17)), 16);

        // for(int i=0; i<macaddr.length; i++){
        // Log.d(DBTAG,"mac addr :" + macaddr[i]);
        // }
        WriteEepromNByte(BlockStartAddr.MAC, MAC_LEN, macaddr);
    }

    public static void SetAdbState(int flag) {
        if (flag == 1)
            SystemProperties.set("persist.service.adb.enable", "1");
        else
            SystemProperties.set("persist.service.adb.enable", "0");

    }

    public static int GetAdbState() {
        String prefix = SystemProperties.get("persist.service.adb.enable");
        if (prefix.equals("1"))
            return 1;
        else
            return 0;
    }

    public static void SetUsbbootState(int flag) {

        if (flag == 1)
            SystemProperties.set("ubootenv.var.usbboot", "1");
        else
            SystemProperties.set("ubootenv.var.usbboot", "0");

    }

    public static int GetUsbbootState() {
        String prefix = SystemProperties.get("ubootenv.var.usbboot");
        if (prefix.equals("0"))
            return 0;
        else
            return 1;
    }

    public static void SetUartSwitch(int flag) {
        int index;
        String prefix = SystemProperties.get("ubootenv.var.console");
        index = prefix.indexOf("ttyS0");
        // Log.d("test", "index ="+prefix);
        // Log.d("test", "SetUartSwitch(int flag) ="+prefix);
        if (flag == 1) {

            // Log.d("test", "SetUartSwitch(int flag) index = 1");
            if(index == -1)
            {
            	Log.d("test", "SetUartSwitch(int flag) index = 1");
                SystemProperties.set("ubootenv.var.console", "ttyS0,115200n8");
            }
            return;
        } else
        {
        	if(index != -1)
        	{
        		Log.d("test", "SetUartSwitch(int flag) index = 0");
                SystemProperties.set("ubootenv.var.console", " ");
        	}
        }
        // Log.d("test", "SetUartSwitch(int flag) index = 0");
    }

    public static void SetLvdsState(int flag) {
        if (flag == 1) {
            SystemProperties.set("ubootenv.var.lvds", "port_reverse,jeida");
            SaveFactoryOption(FactoryOption.LVDS, 1);
            // Log.d(Tag, "set ubootenv.var.usbboot.lvds port_reverse,jeida");
        } else {
            SystemProperties.set("ubootenv.var.lvds", "port_reverse");
            SaveFactoryOption(FactoryOption.LVDS, 0);
            // Log.d(Tag, "set ubootenv.var.lvds port_reverse");
        }

    }

    public static int GetLvdsState() {
        String prefix = SystemProperties.get("ubootenv.var.lvds");
        if (prefix.equals("port_reverse,jeida"))
            return 1;
        else
            return 0;
    }

    public static void CloseEeprom() {
        CloseI2CModule(0);
    }

    public static void SaveDefaultVgaEdid() { // for testing!!
        int funOffsetAddr = BlockStartAddr.VGA_EDID;

        WriteEepromNByte(funOffsetAddr, VGA_EDID_LEN, VgaEdid);
    }

    public static void SaveDefaultHdmiEdid() {
        int funOffsetAddr = BlockStartAddr.HDMI_EDID;

        WriteEepromNByte(funOffsetAddr, HDMI_EDID_LEN, HdmiEdid);
    }

    public static int[] LoadVgaEdid() {
        if (VgaEdid.length != 256)
            Log.d(DBTAG, "vga edid legnth == " + VgaEdid.length + "(bytes).");

        return VgaEdid;
    }

    public static int[] LoadHdmiEdid() {
        if (HdmiEdid.length != 256)
            Log.d(DBTAG, "hdmi edid legnth == " + HdmiEdid.length + "(bytes).");

        return HdmiEdid;
    }

    /**************************************************************************
     * 
     * 
     ***************************************************************************/
    public static void LoadAll_UserSetting() {

    }

    /**************************************************************************
     * 
     * SET EEPROM DEFAULT
     ***************************************************************************/
    public static void SetEEPToDefault() {
        // SetDefaultMacAddr();
        SetDefaultSourceInput();
        SetDefaultVideoSetting();
        SetDefaultVgaAdjustment();
        // SetDefault3DFunc();
        SetDefaultAudioEffGain();
        SetDefaultAudioSetting();
        SetDefaultChannelInfo();
        SetDefaultTvCurrentChannel();
        SetDefaultAvColorSys();
        SetDefaultNumberInputLimit();
        SetDefaultMiscSetting();
        SetDefaultNavigateFlag();
        SetDefaultAdcGainOffset();
        SetDefaultFactorySetting();
        SetDefaultFactoryOption();
        SetDefaultRbgOgo();
        SetDefaultSysStatus();
        // wk 110805 temply cancel this.
        // SetDefaultOnOffMusicVol();
        // SetDefaultAudioHdVol();

        SetDefaultEepromDate();
        WriteCharaterData();
        WriteEepromFlag();
    }

    public static void setBurnDefault() {
        SetDefaultSourceInput();
        SetDefaultVideoSetting();
        SetFactoryChannelInfo();
        SetDefaultAudioEffGain();
        SetDefaultAudioSetting();
        UpdateDefaultAudioSetting();
        SetDefaultTvCurrentChannel();
        SaveNumberInputLimit(GlobalConst.ONE_TOOGLE_NUMBER);
        SetDefaultMiscSetting();
        SaveNavigateFlag(0);
    }

    public static void setFacOutDefault() {
        // the follow need to add according production request.
        SetDefaultSourceInput();
        SetDefaultVideoSetting();
        SetDefaultVgaAdjustment();
        // SetDefault3DFunc();
        SetDefaultAudioEffGain();
        SetDefaultAudioSetting();
        UpdateDefaultAudioSetting();
        SetFactoryChannelInfo();
        SetDefaultTvCurrentChannel();
        SetDefaultAvColorSys();
        SetDefaultNumberInputLimit();
        SetDefaultMiscSetting();
        // SetDefaultNavigateFlag();
        SaveNavigateFlag(1);
    }

    /**************************************************************************
     * 
     * SOURCE INPUT
     ***************************************************************************/
    private static void SetDefaultSysStatus() {
        SaveSysStatus(SysStatus.NORMAL);
    }

    private static int SystemAutoSuspending = 0;

    public static void SetSystemAutoSuspending(int num) {
        SystemAutoSuspending = num;
    }

    public static int GetSystemAutoSuspending() {
        return SystemAutoSuspending;
    }

    public static void SaveSysStatus(SysStatus systatus) {
        final int SYS_NORMAL = 0xaa;
        final int SYS_STANDBY = 0x55;

        switch (systatus) {
            default:
            case NORMAL:
                WriteEepromOneByte(BlockStartAddr.SYS_STATUS, SYS_NORMAL);
                break;
            case STANDBY:
                WriteEepromOneByte(BlockStartAddr.SYS_STATUS, SYS_STANDBY);
                break;
        }
    }

    public static int GetSysStatus() {
        // final int SYS_NORMAL = 0xaa;
        // final int SYS_STANDBY = 0x55;
        return ReadEepromOneByte(0, BlockStartAddr.SYS_STATUS);
    }

    private static void SetDefaultSourceInput() {
        int addr, value = DEF_SRC_IN;

        /* default source */
        addr = BlockStartAddr.SOURCE_SELECT;
        WriteEepromOneByte(addr, value);
        addr = BlockStartAddr.SOURCE_SELECT + 1;
        WriteEepromOneByte(addr, value);

        SrcInput[0] = DEF_SRC_IN;
        SrcInput[1] = DEF_SRC_IN;
    }

    public static void SaveSourceInput(int windowSel, int value) {
        if (value == LoadSourceInput(windowSel))
            return;

        int addr;
        if (windowSel == 0)
            addr = BlockStartAddr.SOURCE_SELECT;
        else
            addr = BlockStartAddr.SOURCE_SELECT + 1;
        if (value < 0 || value >= SourceInput.END.ordinal())
            value = SourceInput.TV.ordinal();

        WriteEepromOneByte(addr, value);
        Log.d(DBTAG, "save input src = " + value);

        if (windowSel == 0)
            SrcInput[0] = value;
        else
            SrcInput[1] = value;
    }

    public static int LoadSourceInput(int windowSel) {
        int val = SrcInput[windowSel];

        if (val < 0 || val >= SourceInput.END.ordinal())
            return DEF_SRC_IN; // return default
        else
            return val;
    }

    /**************************************************************************
     * 
     * VPP SETTING
     ***************************************************************************/
    public static void SetDefaultVideoSetting() {
        int addr, value = 0, srctype = 0, func;
        int[] settingBuf = new int[VIDEO_SETTING_LEN];

        /* video setting */
        // BRIGHTNESS, CONTRAST, COLOR, HUE, SHARPNESS, COLOR_TEMP, NOISE_RE,
        // PICTURE_MODE, ASPECT_RAT, ALL;

        for (srctype = SrcType.AV.ordinal(); srctype <= SrcType.MPEG.ordinal(); srctype++) {
            addr = BlockStartAddr.VIDEO_SETTING + srctype
                * ONE_VDIEO_SETTING_LEN;
            WriteEepromNByte(addr, ONE_VDIEO_SETTING_LEN, DEF_VIDEO_SETTING[srctype]);
        }

        // VideoSettingBySrc = DEF_VIDEO_SETTING;
        for (int i = SrcType.AV.ordinal(); i < SrcType.END.ordinal(); i++) {
            for (int j = 0; j < ONE_VDIEO_SETTING_LEN; j++) {
                VideoSettingBySrc[i][j] = DEF_VIDEO_SETTING[i][j];
            }
        }
    }

    public static void SaveVideoSettingDirect(SrcType srcType,
        VideoFunc videoFunc, int value) {
        int src = srcType.ordinal();
        int videofunc = videoFunc.ordinal();

        int typOffsetAddr = BlockStartAddr.VIDEO_SETTING + src
            * ONE_VDIEO_SETTING_LEN;
        int funOffsetAddr = typOffsetAddr + videofunc;

        VideoSettingBySrc[src][videofunc] = value;
        WriteEepromOneByte(funOffsetAddr, value);
        Log.d(DBTAG, "SaveVideoSettingDirect:" + srcType.toString() + " : "
            + videoFunc.toString() + " : " + value);
    }

    public static void SaveVideoSetting(SrcType srcType, VideoFunc videoFunc,
        int value) {
        int src = srcType.ordinal();
        int videofunc = videoFunc.ordinal();

        if (LoadVideoSetting(srcType, videoFunc) == value) {
            return;
        }

        int typOffsetAddr = BlockStartAddr.VIDEO_SETTING + src
            * ONE_VDIEO_SETTING_LEN;
        int funOffsetAddr = typOffsetAddr + videofunc;

        VideoSettingBySrc[src][videofunc] = value;

        WriteEepromOneByte(funOffsetAddr, value);

        Log.d(DBTAG, "SaveVideoSetting:" + srcType.toString() + " : "
            + videoFunc.toString() + " : " + value);
    }

    public static int LoadVideoSetting(SrcType srcType, VideoFunc videoFunc) {
        {
            int pic_mode = 0;
            int val = 50;
            int typOffsetAddr = BlockStartAddr.VIDEO_SETTING
                + srcType.ordinal() * ONE_VDIEO_SETTING_LEN;
            int funOffsetAddr = typOffsetAddr
                + VideoFunc.PICTURE_MODE.ordinal();

            pic_mode = VideoSettingBySrc[srcType.ordinal()][VideoFunc.PICTURE_MODE.ordinal()];

            if (pic_mode != PictureMode.USER.ordinal()) {
                if (videoFunc == VideoFunc.BRIGHTNESS
                    || videoFunc == VideoFunc.CONTRAST
                    || videoFunc == VideoFunc.COLOR
                    // ||videoFunc == VideoFunc.HUE
                    || videoFunc == VideoFunc.SHARPNESS) {
                    val = vppJNI.vppJNIGetVideoSetting(srcType, pic_mode, videoFunc);
                    Log.d(DBTAG, "LoadVideoSettingLocal:" + srcType.toString()
                        + " : " + videoFunc.toString() + " : " + val + " : "
                        + pic_mode);
                    return val;
                }
            }
        }

        int src = srcType.ordinal();
        int videofunc = videoFunc.ordinal();
        int val = DEF_VIDEO_SETTING[src][videofunc];

        val = VideoSettingBySrc[src][videofunc];

        if (videofunc >= videoFunc.BRIGHTNESS.ordinal()
            && videofunc <= videoFunc.SHARPNESS.ordinal()) {
            if (val < 0 || val > 100)
                val = DEF_VIDEO_SETTING[src][videofunc];
        } else if (videoFunc == videoFunc.COLOR_TEMP) {
            if (val < 0 || val >= ColorTemp.END.ordinal())
                val = DEF_VIDEO_SETTING[src][videofunc];
        } else if (videoFunc == videoFunc.NOISE_RE) {
            if (val < 0 || val >= vppJNI.NoiseRd.END.ordinal())
                val = DEF_VIDEO_SETTING[src][videofunc];
        } else if (videoFunc == videoFunc.ASPECT_RAT) {
            if (val < 0 || val >= ScreenMode.END.ordinal())
                val = DEF_VIDEO_SETTING[src][videofunc];
        }

        Log.d(DBTAG, "LoadVideoSetting:" + srcType.toString() + " : "
            + videoFunc.toString() + " : " + val);
        return val;
    }

    /**************************************************************************
     * 
     * VGA TIMING ADJUSTMENT
     ***************************************************************************/
    private static boolean IsVGADataError(int vgaFmt, VGAFunc vgaFunc) {
        /* format changed */
        if (VGAData[0] != vgaFmt) {
            return true;
        }

        /* never load from EEP */
        int val = VGAData[vgaFunc.ordinal() + 1];
        if (val == -1) {
            return true;
        }

        /* range invalid */
        if (val < 0 || (val & 0x7f) > 100) {
            return true;
        }

        return false;
    }

    private static void SetDefaultVgaAdjustment() {
        int funOffsetAddr;
        int fmtAmount = (VGA_ADJUSTMENT_LEN / ONE_VGA_FMT_LEN);
        int[] allVgaAdjParam = new int[VGA_ADJUSTMENT_LEN];

        funOffsetAddr = BlockStartAddr.VGA_ADJUSTMENT;

        if (DEF_VGA_ADJUSTMENT[VGAFunc.AUTO_FLAG.ordinal()] == 1) {
            DEF_VGA_ADJUSTMENT[VGAFunc.CLOCK.ordinal()] |= 0x80;
        } else {
            DEF_VGA_ADJUSTMENT[VGAFunc.CLOCK.ordinal()] &= 0x7f;
        }

        for (int i = 0; i < fmtAmount; i++) {
            for (int j = 0; j < ONE_VGA_FMT_LEN; j++)
                allVgaAdjParam[i * ONE_VGA_FMT_LEN + j] = DEF_VGA_ADJUSTMENT[j];
        }

        WriteEepromNByte(funOffsetAddr, VGA_ADJUSTMENT_LEN, allVgaAdjParam);

        for (int i = 0; i < (VGAFunc.AUTO_FLAG.ordinal() + 1); i++) {
            VGAData[i] = -1;
        }
        // allVgaAdjParam = ReadEepromNByte(funOffsetAddr,VGA_ADJUSTMENT_LEN);
        // for(int i=0; i<480; i++)
        // Log.d("TEST","VGA SETTING =" + allVgaAdjParam[i]);
    }

    public static void SaveVgaAdjustment(int vgaFmt, VGAFunc vgaFunc, int value) {
        // Log.d(Haha","save vgaFunc:" + " vgaFmt:" + vgaFmt + "
        // vgaFunc" + vgaFunc + " value:" + value);
        // if(value == LoadVgaAdjustment(vgaFmt,vgaFunc)){
        // return ;
        // }

        int fmtOffsetAddr, funOffsetAddr;

        if (vgaFmt <= SigFormat.NULL.ordinal()
            || vgaFmt >= SigFormat.VGA_MAX.ordinal()) {
            vgaFmt = DEF_VGA_FMT;
        }

        fmtOffsetAddr = BlockStartAddr.VGA_ADJUSTMENT + ONE_VGA_FMT_LEN
            * (vgaFmt - 1);
        funOffsetAddr = fmtOffsetAddr + vgaFunc.ordinal();

        VGAData[0] = vgaFmt;
        if (vgaFunc == VGAFunc.CLOCK) {
            if (LoadVgaAdjustment(vgaFmt, VGAFunc.AUTO_FLAG) == 1) {
                value = value | 0x80;
                VGAData[VGAFunc.CLOCK.ordinal() + 1] = value;
                VGAData[VGAFunc.AUTO_FLAG.ordinal() + 1] = 1;
            } else {
                VGAData[VGAFunc.CLOCK.ordinal() + 1] = value;
                VGAData[VGAFunc.AUTO_FLAG.ordinal() + 1] = 0;
            }
        } else {
            VGAData[vgaFunc.ordinal() + 1] = value;
        }

        if (vgaFunc == VGAFunc.CLOCK)
            Log.d(DBTAG, "SaveVgaAdjustment:Clock = " + value);
        WriteEepromOneByte(funOffsetAddr, value);
    }

    public static int LoadVgaAdjustment(int vgaFmt, VGAFunc vgaFunc) {
        int val;
        int adjParam = vgaFunc.ordinal();

        if (IsVGADataError(vgaFmt, vgaFunc)) {
            int fmtOffsetAddr, funOffsetAddr;

            if (vgaFmt <= SigFormat.NULL.ordinal()
                || vgaFmt >= SigFormat.VGA_MAX.ordinal()) {
                vgaFmt = DEF_VGA_FMT;
            }

            fmtOffsetAddr = BlockStartAddr.VGA_ADJUSTMENT + ONE_VGA_FMT_LEN
                * (vgaFmt - 1);

            if (vgaFunc == VGAFunc.AUTO_FLAG)
                funOffsetAddr = fmtOffsetAddr + vgaFunc.CLOCK.ordinal();
            else
                funOffsetAddr = fmtOffsetAddr + vgaFunc.ordinal();

            val = ReadEepromOneByte(funOffsetAddr);

            if (vgaFunc == VGAFunc.PHASE)
                VGAData[0] = vgaFmt;

            VGAData[adjParam + 1] = val;

            if (vgaFunc == VGAFunc.AUTO_FLAG) {
                // Log.d(DBTAG,"return eeprom auto_flag value = " + (val>>7));
                VGAData[adjParam + 1] = (val >> 7);// val;
                return (val >> 7);
            }
        } else {
            val = VGAData[adjParam + 1];
            // Log.d(DBTAG,"return ram auto_flag value = " + val);
            if (vgaFunc == VGAFunc.AUTO_FLAG) {
                return val;
            }
        }

        if (val < 0 || (val & 0x7f) > 100)
            val = DEF_VGA_ADJUSTMENT[adjParam];

        // switch(vgaFunc){
        // case HPOSITION:
        // afeJNI.vgaTimingAdj.HPosition = 50 - val;
        // break;
        // case VPOSITION:
        // afeJNI.vgaTimingAdj.VPosition = val - 50;
        // break;
        // case CLOCK:
        // afeJNI.vgaTimingAdj.Clock = (val & 0x7f) - 50;
        // break;
        // case PHASE:
        // afeJNI.vgaTimingAdj.Phase = val*31/100;
        // break;
        // }

        return (val & 0x7f);
    }

    public static int IsAutoAdjFlag(int vgaFmt) {
        return LoadVgaAdjustment(vgaFmt, VGAFunc.AUTO_FLAG);
    }

    public static afeJNI.VgaTimingAdj LoadVgaAllAdjustment(int vgaFmt) {
        afeJNI.VgaTimingAdj adjParam = new afeJNI.VgaTimingAdj();

        adjParam.HPosition = 50 - LoadVgaAdjustment(vgaFmt, VGAFunc.HPOSITION);
        adjParam.VPosition = LoadVgaAdjustment(vgaFmt, VGAFunc.VPOSITION) - 50;
        adjParam.Clock = LoadVgaAdjustment(vgaFmt, VGAFunc.CLOCK) - 50;
        int uiValue = LoadVgaAdjustment(vgaFmt, VGAFunc.PHASE);
        if (uiValue >= 95)
            adjParam.Phase = 31;
        else if (uiValue < 5)
            adjParam.Phase = 0;
        else
            adjParam.Phase = (uiValue - 2) / 3;

        return adjParam;
    }

    /**************************************************************************
     * 
     * ADC GAIN/OFFSET
     ***************************************************************************/
    public static void SetDefaultAdcGainOffset() {
        afeJNI.comp0GainOffset.a_analog_gain = 0x77;
        afeJNI.comp0GainOffset.a_analog_clamp = 0x47;
        afeJNI.comp0GainOffset.a_digital_offset1 = 0x00;
        afeJNI.comp0GainOffset.a_digital_gain = 0x3cf;
        afeJNI.comp0GainOffset.a_digital_offset2 = 0x1c;

        afeJNI.comp0GainOffset.b_analog_gain = 0xb0;
        afeJNI.comp0GainOffset.b_analog_clamp = 0x42;
        afeJNI.comp0GainOffset.b_digital_offset1 = 0x00;
        afeJNI.comp0GainOffset.b_digital_gain = 0x383;
        afeJNI.comp0GainOffset.b_digital_offset2 = 0x40;

        afeJNI.comp0GainOffset.c_analog_gain = 0x94;
        afeJNI.comp0GainOffset.c_analog_clamp = 0x3c;
        afeJNI.comp0GainOffset.c_digital_offset1 = 0x00;
        afeJNI.comp0GainOffset.c_digital_gain = 0x383;
        afeJNI.comp0GainOffset.c_digital_offset2 = 0x40;

        afeJNI.comp0GainOffset.d_analog_gain = 0x00;
        afeJNI.comp0GainOffset.d_analog_clamp = 0x00;
        afeJNI.comp0GainOffset.d_digital_offset1 = 0x00;
        afeJNI.comp0GainOffset.d_digital_gain = 0x00;
        afeJNI.comp0GainOffset.d_digital_offset2 = 0x00;

        afeJNI.vgaGainOffset.a_analog_gain = 0x9e;
        afeJNI.vgaGainOffset.a_analog_clamp = 0x3e;
        afeJNI.vgaGainOffset.a_digital_offset1 = 0x00;
        afeJNI.vgaGainOffset.a_digital_gain = 0x400;
        afeJNI.vgaGainOffset.a_digital_offset2 = 0x00;

        afeJNI.vgaGainOffset.b_analog_gain = 0xb0;
        afeJNI.vgaGainOffset.b_analog_clamp = 0x42;
        afeJNI.vgaGainOffset.b_digital_offset1 = 0x00;
        afeJNI.vgaGainOffset.b_digital_gain = 0x400;
        afeJNI.vgaGainOffset.b_digital_offset2 = 0x00;

        afeJNI.vgaGainOffset.c_analog_gain = 0x94;
        afeJNI.vgaGainOffset.c_analog_clamp = 0x3b;
        afeJNI.vgaGainOffset.c_digital_offset1 = 0x00;
        afeJNI.vgaGainOffset.c_digital_gain = 0x400;
        afeJNI.vgaGainOffset.c_digital_offset2 = 0x00;

        afeJNI.vgaGainOffset.d_analog_gain = 0x00;
        afeJNI.vgaGainOffset.d_analog_clamp = 0x00;
        afeJNI.vgaGainOffset.d_digital_offset1 = 0x00;
        afeJNI.vgaGainOffset.d_digital_gain = 0x00;
        afeJNI.vgaGainOffset.d_digital_offset2 = 0x00;

        SaveADCGainOffset(ADCSrcId.COMP0, afeJNI.comp0GainOffset);
        SaveADCGainOffset(ADCSrcId.VGA, afeJNI.vgaGainOffset);

        Log.d(DBTAG, "eeprom set ADC gain/offset default value!");
    }

    public static void SaveADCGainOffset(ADCSrcId adcSrcId,
        AdcGainOffset adcGainOffset) {
        int offsetAddr;
        int buf[] = new int[40];

        offsetAddr = BlockStartAddr.ADC_CALIBRATION + ONE_GAINOFFSET_LEN
            * adcSrcId.ordinal();

        buf = adcGainOffset.ToByteArrary();

        WriteEepromNByte(offsetAddr, 40, buf);
    }

    public static AdcGainOffset LoadADCGainOffset(ADCSrcId adcSrcId) {
        int adcCalOffsetAddr;
        int buf[] = new int[40];

        adcCalOffsetAddr = BlockStartAddr.ADC_CALIBRATION + ONE_GAINOFFSET_LEN
            * adcSrcId.ordinal();

        AdcGainOffset gainOffset = new AdcGainOffset();

        ReadEepromNByte(0, adcCalOffsetAddr, 40, buf);

        gainOffset = gainOffset.FromByteArrary(buf);

        // Log.d(DBTAG, "Load ADC addr = " + adcCalOffsetAddr);

        return gainOffset;
    }

    /******************************************
     * HDMI/VGA Edid
     * 
     * @brief: store hdmi edid, total 256 bytes
     ******************************************/
    public static void SaveHDMIEdid() {
        int hdmiEdidOffsetAddr;

        hdmiEdidOffsetAddr = BlockStartAddr.HDMI_EDID;

        WriteEepromNByte(hdmiEdidOffsetAddr, HDMI_EDID_LEN, HdmiEdid);
    }

    public static void SaveVGAEdid() {
        int vgaEdidOffsetAddr;

        vgaEdidOffsetAddr = BlockStartAddr.VGA_EDID;

        // Log.d(DBTAG, "Save VGA edid addr = " + vgaEdidOffsetAddr);

        WriteEepromNByte(vgaEdidOffsetAddr, VGA_EDID_LEN, VgaEdid);
    }

    /*************************************
     * 3D Functions
     * 
     * @brief:
     ************************************/
    // private static void SetDefault3DFunc() {
    // int addrStart = BlockStartAddr.FUNC_3D;
    // int addr = addrStart + Setting3D.MODE.ordinal();
    //
    // WriteEepromOneByte(addr, 0);
    //
    // addr = addrStart + Setting3D.LRSWITCH.ordinal();
    // WriteEepromOneByte(addr, 0);
    //
    // addr = addrStart + Setting3D.DEPTH.ordinal();
    // WriteEepromOneByte(addr, 0);
    //
    // addr = addrStart + Setting3D.MODE3Dto2D.ordinal();
    // WriteEepromOneByte(addr, 0);
    //
    // Func3DData[Setting3D.MODE.ordinal()] = 0;
    // Func3DData[Setting3D.LRSWITCH.ordinal()] = 0;
    // Func3DData[Setting3D.DEPTH.ordinal()] = 0;
    // Func3DData[Setting3D.MODE3Dto2D.ordinal()] = 0;
    // }

    // public static void Save3DFunc(Setting3D funcSel, int value) {
    //
    // if (value == Load3DFunc(funcSel))
    // return;
    //
    // int addr = BlockStartAddr.FUNC_3D + funcSel.ordinal();
    //
    // switch (funcSel) {
    // case MODE:
    // if (value < 0 || value > 4)
    // value = 0;
    // break;
    // case LRSWITCH:
    // if (value < 0 || value > 1)
    // value = 0;
    // break;
    // case DEPTH:
    // if (value < 0 || value > 5) // 6 stage
    // value = 0;
    // break;
    // case MODE3Dto2D:
    // if (value < 0 || value > 2)
    // value = 0;
    // break;
    // }
    //
    // Func3DData[funcSel.ordinal()] = value;
    // WriteEepromOneByte(addr, value);
    // }

    // public static int Load3DFunc(Setting3D funcSel) {
    // int value = Func3DData[funcSel.ordinal()];
    //
    // switch (funcSel) {
    // case MODE:
    // if (value < 0 || value > 4)
    // value = 0;
    // break;
    // case LRSWITCH:
    // if (value < 0 || value > 1)
    // value = 0;
    // break;
    // case DEPTH:
    // if (value < 0 || value > 5) // 6 stage
    // value = 0;
    // break;
    // case MODE3Dto2D:
    // if (value < 0 || value > 2)
    // value = 0;
    // break;
    // }
    //
    // return value;
    // }

    /*************************************
     * audio functions
     ************************************/
    private static void SetDefaultAudioSetting() {
        for (int idx = 0; idx < AudioSetingData.length; idx++) {
            AudioSetingData[idx] = -1;
        }
        int typOffsetAddr = BlockStartAddr.AUDIO_SETTING;
        WriteEepromNByte(typOffsetAddr, AudioSetingDef.length, AudioSetingDef);
    }

    private static void UpdateDefaultAudioSetting() {
        AudioCustom.SaveCurAudioVolume(AudioCustom.CC_DEF_SOUND_VOL);
        AudioCustom.SaveCurAudioBalance(50 + AudioCustom.CC_DEF_SOUND_BALANCE_VAL);
        AudioCustom.SaveCurAudioSrsSurround(AudioCustom.CC_SWITCH_OFF);
        AudioCustom.SaveCurAudioSrsDialogClarity(AudioCustom.CC_SWITCH_OFF);
        AudioCustom.SaveCurAudioSrsTruBass(AudioCustom.CC_SWITCH_OFF);
        AudioCustom.SaveCurAudioSupperBassSwitch(AudioCustom.CC_SWITCH_OFF);
        AudioCustom.SaveCurAudioSupperBassVolume(AudioCustom.CC_DEF_SUPPERBASS_VOL);
        AudioCustom.SaveCustomEQGainBuf(AudioEffGainDef);
        AudioCustom.SetCurAudioBassVolume(AudioCustom.CC_DEF_BASS_TREBLE_VOL);
        AudioCustom.SetCurAudioTrebleVolume(AudioCustom.CC_DEF_BASS_TREBLE_VOL);
        AudioCustom.SaveCurAudioSoundMode(GlobalConst.SOUNDMODE_STD);
        AudioCustom.SaveCurAudioWallEffect(AudioCustom.CC_SWITCH_OFF);
    }

    public static void SaveAudioSetting(AudioFunc audioFunc, int value) {
        if (audioFunc.ordinal() == AudioFunc.BALANCE_VAL.ordinal()) {
            // change from -50---+50 to 0---+100
            value += 50;
        }

        if (value == LoadAudioSetting(audioFunc))
            return;

        int typOffsetAddr = BlockStartAddr.AUDIO_SETTING;
        int funOffsetAddr = typOffsetAddr + audioFunc.ordinal();

        // Log.d(DBTAG, "Save Audio setting addr =" + funOffsetAddr);
        AudioSetingData[audioFunc.ordinal()] = value;
        WriteEepromOneByte(funOffsetAddr, (byte) value & 0xFF);
    }

    public static int LoadAudioSetting(AudioFunc audioFunc) {
        int val;
        if (-1 == AudioSetingData[audioFunc.ordinal()]) {
            int typOffsetAddr = BlockStartAddr.AUDIO_SETTING;
            int funOffsetAddr = typOffsetAddr + audioFunc.ordinal();
            val = (byte) ReadEepromOneByte(0, funOffsetAddr);
            // Log.d(DBTAG, "Load Audio setting addr =" + funOffsetAddr);
            AudioSetingData[audioFunc.ordinal()] = val;
        } else {
            val = AudioSetingData[audioFunc.ordinal()];
        }

        if (audioFunc.ordinal() == AudioFunc.BALANCE_VAL.ordinal()) {
            // change from 0---+100 to -50---+50
            val -= 50;
        }
        return val;
    }

    private static void SetDefaultAudioEffGain() {
        for (int i = 0; i < AudioEffGain.length; i++) {
            AudioEffGain[i] = -1;
        }

        int typOffsetAddr = BlockStartAddr.AUDIO_EFFECT_GAIN;
        WriteEepromNByte(typOffsetAddr, AudioEffGainDef.length, AudioEffGainDef);
    }

    public static void SaveAudioEffGain(int gain_buf[], int buf_size) {
        int typOffsetAddr = BlockStartAddr.AUDIO_EFFECT_GAIN;
        int tmp_buf[] = {
            0, 0, 0, 0, 0, 0
        };

        if (buf_size > tmp_buf.length)
            buf_size = tmp_buf.length;
        for (int i = 0; i < buf_size; i++) {
            tmp_buf[i] = gain_buf[i];
            AudioEffGain[i] = tmp_buf[i];
        }
        WriteEepromNByte(typOffsetAddr, buf_size, tmp_buf);
    }

    public static void LoadAudioEffGain(int gain_buf[], int buf_size) {
        int i;

        if (AudioEffGain[0] == -1) {
            int typOffsetAddr = BlockStartAddr.AUDIO_EFFECT_GAIN;
            int tmp_buf[] = {
                0, 0, 0, 0, 0, 0
            };

            ReadEepromNByte(0, typOffsetAddr, buf_size, tmp_buf);
            if (buf_size > tmp_buf.length)
                buf_size = tmp_buf.length;
            for (i = 0; i < buf_size; i++) {
                AudioEffGain[i] = (byte) tmp_buf[i];
                gain_buf[i] = AudioEffGain[i];
            }
        } else {
            for (i = 0; i < buf_size; i++) {
                gain_buf[i] = AudioEffGain[i];
            }
        }
    }

    public static void LoadOnOffMusicVolBuf() {
        int offsetAddr = BlockStartAddr.ONOFF_MUSIC_HDVOL;
        OnoffMusicVol = ReadEepromOneByte(0, offsetAddr);
    }

    public static void SetDefaultOnOffMusicVol() {
        SaveOnOffMusicVol(DEF_ONOFF_MUSIC_VOL);
    }

    public static int LoadOnOffMusicVol() {
        return OnoffMusicVol;
    }

    public static void SaveOnOffMusicVol(int vol) {
        int offsetAddr = BlockStartAddr.ONOFF_MUSIC_HDVOL;

        WriteEepromOneByte(offsetAddr, vol);
        OnoffMusicVol = vol;
    }

    public static void LoadAudioHdVolBuf() {
        int offsetAddr = BlockStartAddr.AUDIO_HDVOL;
        int[] buf = new int[AUDIO_HDVOL_LEN];
        buf = ReadEepromNByte(offsetAddr, AUDIO_HDVOL_LEN);

        for (int j = 0; j < HDVolSrc.END.ordinal(); j++) {
            for (int i = 0; i < ONE_HDVOL_LEN; i++) {
                AudioHdVol[j][i] = buf[j * ONE_HDVOL_LEN + i];
                Log.d(DBTAG, "load audio hd vol:" + AudioHdVol[j][i]);
            }
        }

    }

    public static void SetDefaultAudioHdVol() {
        SaveHDVol(true, DEF_AUDIO_HDVOL_TV);
        SaveHDVol(false, DEF_AUDIO_HDVOL_OTHERS);
    }

    public static void LoadHDVol(boolean isTvSrc, int hd_vol_buf[]) {
        if (isTvSrc == true) {
            for (int i = 0; i < hd_vol_buf.length; i++)
                hd_vol_buf[i] = AudioHdVol[HDVolSrc.TV.ordinal()][i];
        } else {
            for (int i = 0; i < hd_vol_buf.length; i++)
                hd_vol_buf[i] = AudioHdVol[HDVolSrc.OTHERS.ordinal()][i];
        }
    }

    public static void SaveHDVol(boolean isTvSrc, int hd_vol_buf[]) {
        int offsetAddr = BlockStartAddr.AUDIO_HDVOL;

        if (isTvSrc == true) {
            WriteEepromNByte(offsetAddr, hd_vol_buf.length, hd_vol_buf);
            for (int i = 0; i < hd_vol_buf.length; i++)
                AudioHdVol[HDVolSrc.TV.ordinal()][i] = hd_vol_buf[i];
        } else {
            WriteEepromNByte(offsetAddr + ONE_HDVOL_LEN, hd_vol_buf.length, hd_vol_buf);
            for (int i = 0; i < hd_vol_buf.length; i++)
                AudioHdVol[HDVolSrc.OTHERS.ordinal()][i] = hd_vol_buf[i];
        }
    }

    /**************************************************************************
     * 
     * TV CHANNEL
     ***************************************************************************/
    public static void SaveChannelInfo(int channelIdx,
        tunerJNI.ChannelInfo chanInfo) {
        int typOffsetAddr = BlockStartAddr.CHANNEL_INFO;
        int chanOffsetAddr = typOffsetAddr + channelIdx * ONE_CHANINFO_LEN;
        int infoBuf[] = new int[ONE_CHANINFO_LEN];

        infoBuf[0] = chanInfo.volcomp;
        infoBuf[1] = (chanInfo.div & 0xff00) >> 8;
        infoBuf[2] = chanInfo.div & 0x00ff;
        // infoBuf[3]:bit7 jump bit6 aft bit5--bit4 videostd bit3 null
        // bit2--bit0 soundstd
        if (chanInfo.jump == true)
            infoBuf[3] = 0x80 | (chanInfo.videostd << 4 & 0x0030)
                | (chanInfo.soundstd & 0x0007);
        else
            infoBuf[3] = 0x00 | (chanInfo.videostd << 4 & 0x0030)
                | (chanInfo.soundstd & 0x0007);

        if (chanInfo.aft == true)
            infoBuf[3] = infoBuf[3] | 0x40;
        else
            infoBuf[3] = infoBuf[3] & 0xbf;

        Log.d(DBTAG, "Save ChanInfo chanOffsetAddr =" + chanOffsetAddr);

        for (int i = 0; i < ONE_CHANINFO_LEN; i++) {
            AllChInfo[channelIdx][i] = infoBuf[i];
            Log.d(DBTAG, "ram channel info: Index=" + channelIdx + " ->"
                + infoBuf[i]);
        }

        WriteEepromNByte(chanOffsetAddr, ONE_CHANINFO_LEN, infoBuf);
    }

    public static void SaveChannelInfoVolcomp(int channelIdx, int chVolcomp) {
        int typOffsetAddr = BlockStartAddr.CHANNEL_INFO;
        int chanOffsetAddr = typOffsetAddr + channelIdx * ONE_CHANINFO_LEN;

        AllChInfo[channelIdx][0] = chVolcomp;
        WriteEepromOneByte(chanOffsetAddr, chVolcomp);
    }

    public static void SaveChannelInfoFreq(int channelIdx, int chDivFreq) {
        int typOffsetAddr = BlockStartAddr.CHANNEL_INFO;
        int chanOffsetAddr = typOffsetAddr + channelIdx * ONE_CHANINFO_LEN + 1;
        int infoBuf[] = new int[2];

        infoBuf[0] = (chDivFreq & 0xff00) >> 8;
        infoBuf[1] = chDivFreq & 0x00ff;

        AllChInfo[channelIdx][1] = infoBuf[0];
        AllChInfo[channelIdx][2] = infoBuf[1];
        WriteEepromNByte(chanOffsetAddr, 2, infoBuf);
    }

    public static void SaveChannelInfoJump(int channelIdx, boolean chjump) {
        int typOffsetAddr = BlockStartAddr.CHANNEL_INFO;
        int chanOffsetAddr = typOffsetAddr + channelIdx * ONE_CHANINFO_LEN + 3;
        int info = ReadEepromOneByte(0, chanOffsetAddr);

        if (chjump == true)
            info = info | 0x80;
        else
            info = info & 0x7f;

        AllChInfo[channelIdx][3] = info;
        WriteEepromOneByte(chanOffsetAddr, info);
    }

    public static void SaveChannelInfoAft(int channelIdx, boolean chAft) {
        int typOffsetAddr = BlockStartAddr.CHANNEL_INFO;
        int chanOffsetAddr = typOffsetAddr + channelIdx * ONE_CHANINFO_LEN + 3;
        int info = ReadEepromOneByte(0, chanOffsetAddr);

        if (chAft == true)
            info = info | 0x40;
        else
            info = info & 0xbf;

        AllChInfo[channelIdx][3] = info;
        WriteEepromOneByte(chanOffsetAddr, info);
    }

    public static void SaveChannelInfoVideostd(int channelIdx, int val) {
        int typOffsetAddr = BlockStartAddr.CHANNEL_INFO;
        int chanOffsetAddr = typOffsetAddr + channelIdx * ONE_CHANINFO_LEN + 3;
        int info = ReadEepromOneByte(0, chanOffsetAddr);

        info = info & 0x00cf | (val << 4 & 0x0030);

        AllChInfo[channelIdx][3] = info;
        WriteEepromOneByte(chanOffsetAddr, info);
    }

    public static void SaveChannelInfoSoundstd(int channelIdx, int val) {
        int typOffsetAddr = BlockStartAddr.CHANNEL_INFO;
        int chanOffsetAddr = typOffsetAddr + channelIdx * ONE_CHANINFO_LEN + 3;
        int info = ReadEepromOneByte(0, chanOffsetAddr);

        info = info & 0x00f8 | (val & 0x0007);

        AllChInfo[channelIdx][3] = info;
        WriteEepromOneByte(chanOffsetAddr, info);
    }

    public static tunerJNI.ChannelInfo LoadChannelInfo(int channelIdx) {
        tunerJNI.ChannelInfo chanInfo = new tunerJNI.ChannelInfo();

        chanInfo.volcomp = AllChInfo[channelIdx][0];
        if (chanInfo.volcomp > GlobalConst.PROGRAM_VOL_CORRECT_MAXVALUE)
            chanInfo.volcomp = GlobalConst.PROGRAM_VOL_CORRECT_DEFAULT;

        chanInfo.div = (chanInfo.div & 0x00ff)
            | (AllChInfo[channelIdx][1] << 8);
        chanInfo.div = (chanInfo.div & 0xff00) | AllChInfo[channelIdx][2];

        if ((AllChInfo[channelIdx][3] & 0x80) == 0x80)
            chanInfo.jump = true;
        else
            chanInfo.jump = false;

        if ((AllChInfo[channelIdx][3] & 0x40) == 0x40)
            chanInfo.aft = true;
        else
            chanInfo.aft = false;

        chanInfo.videostd = (AllChInfo[channelIdx][3] & 0x0030) >> 4;
        if (chanInfo.videostd > GlobalConst.CVBS_COLORSYS_MAXVALUE)
            chanInfo.videostd = GlobalConst.CVBS_COLORSYS_PAL;

        chanInfo.soundstd = (AllChInfo[channelIdx][3] & 0x0007);
        if (chanInfo.soundstd > GlobalConst.TV_CHANNEL_SOUNDSYS_MAXVALUE)
            chanInfo.soundstd = GlobalConst.TV_CHANNEL_SOUNDSYS_DK;

        return chanInfo;
    }

    public static int LoadChannelInfoFreq(int channelIdx) {
        int freq = 0;

        freq = (freq & 0x00ff) | (AllChInfo[channelIdx][1] << 8);
        freq = (freq & 0xff00) | AllChInfo[channelIdx][2];
        freq = tunerJNI.Div2Freq(freq);

        return freq;
    }

    public static boolean LoadChannelInfoJump(int channelIdx) {
        boolean jump;

        if ((AllChInfo[channelIdx][3] & 0x80) == 0x80)
            jump = true;
        else
            jump = false;

        // Log.d(DBTAG, "load channel jump = " + jump);
        return jump;
    }

    public static void SetDefaultChannelInfo() {
        int[] buf = new int[] {
            0, 0x6, 0x7f, 0x80
        };
        int typOffsetAddr = BlockStartAddr.CHANNEL_INFO;
        int wrlenth = 1024;
        int[] wrbuf = new int[1024];

        for (int j = 0; j < wrlenth;) {
            for (int i = 0; i < 4; i++) {
                AllChInfo[j / 4][i] = buf[i];
                wrbuf[j++] = buf[i];
            }
        }
        // for (int j=0;j<1024;j++) {
        // Log.d("TUNER", "cleanbuf[" + j + "] = " +
        // Integer.toHexString(wrbuf[j])+ " ");
        // }
        Log.d(DBTAG, "Clean chanInfo addr =" + typOffsetAddr);

        WriteEepromNByte(typOffsetAddr, wrlenth, wrbuf);
    }

    public static void SetFactoryChannelInfo() {

        int[] frequency = new int[] {
            49750000,
            136250000,
            160250000,
            168250000,
            416250000,
            456250000,
            471250000,
            863250000,
            49750000,
            217250000,
            471250000,
            527250000,
            783250000
        };
        int f_if = 38900000;
        int[] colorSys = {
            1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 2, 1, 1
        }; // auto:0,pal:1,ntsc:2
        int[] soundSys = {
            0, 2, 0, 0, 0, 1, 3, 0, 0, 2, 3, 1, 0
        }; // dk:0,I:1,BG:2,M:3,AUTO:4
        int chDivFreq;
        for (int i = 0; i < 13; i++) {
            SaveChannelInfoSoundstd(i + 1, soundSys[i]);
            SaveChannelInfoVideostd(i + 1, colorSys[i]);
            SaveChannelInfoAft(i + 1, true);
            SaveChannelInfoJump(i + 1, false);
            SaveChannelInfoVolcomp(i + 1, 10);
            chDivFreq = (frequency[i] + f_if) / 50000;
            SaveChannelInfoFreq(i + 1, chDivFreq);
        }
        int[] buf = new int[] {
                0, 0x6, 0x7f, 0x80
            };
        int typOffsetAddr = BlockStartAddr.CHANNEL_INFO;
        WriteEepromNByte(typOffsetAddr, 4, buf);
        for(int j = 0; j<4;j++)
        {
        	AllChInfo[0][j] =  buf[j];
        }
        typOffsetAddr = BlockStartAddr.CHANNEL_INFO + 14*4;
        int wrlenth = 1024 - 14*4;
        int[] wrbuf = new int [wrlenth];
        int k = 0;
        for (int j = 0; j < wrlenth;) {
            for (int i = 0; i < 4; i++) {
            	k = j/4 + 14;
	            AllChInfo[k][i]=buf[i];
	            wrbuf[j++] = buf[i];
            }
        }  
        Log.d(DBTAG, "Clean chanInfo addr =" + typOffsetAddr);
        WriteEepromNByte(typOffsetAddr, wrlenth, wrbuf);          
    }

    public static void cleanEmptyChannelSkip(int channelIdx) {
        // only set skip flag.
        for (int i = channelIdx; i < GlobalConst.TV_CHANNEL_TOTAL_PROGRAM_COUNT; i++) {
            // if not find any channel,do nothing.
            if (i == GlobalConst.AUTO_SEARCH_START_CHANNEL)
                break;
            SaveChannelInfoJump(i, true);
        }
    }

    public static void SaveAutoSearchInfo(int[] allchannelinfo) {
        int OffsetAddr = BlockStartAddr.CHANNEL_INFO;
        // for (int i=0;i<1024;i++) {
        // Log.d("TUNER", "SaveAutoSearchInfobuf[" + i + "] = " +
        // Integer.toHexString(allchannelinfo[i])+ " ");
        // }
        WriteEepromNByte(OffsetAddr, 1024, allchannelinfo);
        for (int i = 0; i < GlobalConst.TV_CHANNEL_TOTAL_PROGRAM_COUNT; i++) {
            for (int j = 0; j < ONE_CHANINFO_LEN; j++) {
                AllChInfo[i][j] = allchannelinfo[i * ONE_CHANINFO_LEN + j];
            }
        }
    }

    public static int[] LoadAllCHInfo() {
        // int[] allchannelinfo = new int[1024];
        // int OffsetAddr = BlockStartAddr.CHANNEL_INFO;
        // ReadEepromNByte(0,OffsetAddr,1024,allchannelinfo);
        // for (int i=0;i<1024;i++) {
        // Log.d("TUNER", "RDbuf[" + i + "] = " +
        // Integer.toHexString(allchannelinfo[i])+ " ");
        // }

        // return allchannelinfo;
        int[] buf = new int[GlobalConst.TV_CHANNEL_TOTAL_PROGRAM_COUNT
            * ONE_CHANINFO_LEN];

        for (int i = 0; i < GlobalConst.TV_CHANNEL_TOTAL_PROGRAM_COUNT; i++) {
            for (int j = 0; j < ONE_CHANINFO_LEN; j++) {
                buf[i * ONE_CHANINFO_LEN + j] = AllChInfo[i][j];
            }
        }

        return buf;
    }

    private static void SetDefaultTvCurrentChannel() {
        int OffsetAddr = BlockStartAddr.TV_INFO;
        WriteEepromOneByte(OffsetAddr, GlobalConst.TV_CHANNEL_DEFAULT_NUMBER);

        CurChannelNumData = GlobalConst.TV_CHANNEL_DEFAULT_NUMBER;
    }

    public static void SaveTvCurrentChannel(int value) {
        if (LoadTvCurrentChannel() == value) {
            return;
        }

        int OffsetAddr = BlockStartAddr.TV_INFO;

        WriteEepromOneByte(OffsetAddr, value);

        CurChannelNumData = value;

        Log.d(DBTAG, "Save current channel addr =" + OffsetAddr);
    }

    public static int LoadTvCurrentChannel() {
        if (CurChannelNumData < GlobalConst.TV_CHANNEL_MIN_NUMBER
            || CurChannelNumData >= GlobalConst.TV_CHANNEL_TOTAL_PROGRAM_COUNT)
            return GlobalConst.TV_CHANNEL_DEFAULT_NUMBER;
        else
            return CurChannelNumData;
    }

    private static void SetDefaultAvColorSys() {
        int OffsetAddr = BlockStartAddr.AV_COLORSYS;
        WriteEepromOneByte(OffsetAddr, GlobalConst.CVBS_COLORSYS_AUTO);

        AVColorData = DEF_COLOR_SYS;
    }

    public static void SaveAvColorSys(int value) {
        if (value == AVColorData)
            return;

        int OffsetAddr = BlockStartAddr.AV_COLORSYS;

        WriteEepromOneByte(OffsetAddr, value);
        AVColorData = value;
    }

    public static int LoadAvColorSys() {
        if (AVColorData > GlobalConst.CVBS_COLORSYS_MAXVALUE)
            AVColorData = DEF_COLOR_SYS;

        return AVColorData;
    }

    private static void SetDefaultNumberInputLimit() {
        int OffsetAddr = BlockStartAddr.NUMBER_LIMIT;
        WriteEepromOneByte(OffsetAddr, GlobalConst.TWO_TOOGLE_NUMBER);

        NumberInputLimitData = GlobalConst.TWO_TOOGLE_NUMBER;
    }

    public static void SaveNumberInputLimit(int value) {
        if (value == LoadNumberInputLimit())
            return;

        int OffsetAddr = BlockStartAddr.NUMBER_LIMIT;
        WriteEepromOneByte(OffsetAddr, value);

        NumberInputLimitData = value;
    }

    public static int LoadNumberInputLimit() {
        if (NumberInputLimitData < GlobalConst.ONE_TOOGLE_NUMBER
            || NumberInputLimitData > GlobalConst.THREE_TOOGLE_NUMBER)
            NumberInputLimitData = GlobalConst.TWO_TOOGLE_NUMBER; // default is
                                                                  // --

        return NumberInputLimitData;
    }

    /**************************************************************************
     * 
     * MISC SETTING
     ***************************************************************************/
    // LANGUAGE, POWERONSRC, SLEEPTIME, COLOR_MANAGEMENT, BACKLIGHT, DREAMPANEL,
    // ONOFFMUSIC, LISTMODE, REPEATMODE, SWITCHMODE, PLAYMODE
    private static void SetDefaultMiscSetting() {
        int BaseAddr = BlockStartAddr.MISC;
        int OffsetAddr = BlockStartAddr.MISC;

        OffsetAddr = BaseAddr + MISCFunc.LANGUAGE.ordinal();
        WriteEepromOneByte(OffsetAddr, DEF_MISC_SETTING[MISCFunc.LANGUAGE.ordinal()]);

        OffsetAddr = BaseAddr + MISCFunc.POWERONSRC.ordinal();
        WriteEepromOneByte(OffsetAddr, DEF_MISC_SETTING[MISCFunc.POWERONSRC.ordinal()]);

        OffsetAddr = BaseAddr + MISCFunc.KEYSOUND.ordinal();
        WriteEepromOneByte(OffsetAddr, DEF_MISC_SETTING[MISCFunc.KEYSOUND.ordinal()]);

        OffsetAddr = BaseAddr + MISCFunc.COLOR_MANAGEMENT.ordinal();
        WriteEepromOneByte(OffsetAddr, DEF_MISC_SETTING[MISCFunc.COLOR_MANAGEMENT.ordinal()]);

        OffsetAddr = BaseAddr + MISCFunc.BACKLIGHT.ordinal();
        WriteEepromOneByte(OffsetAddr, DEF_MISC_SETTING[MISCFunc.BACKLIGHT.ordinal()]);

        OffsetAddr = BaseAddr + MISCFunc.DREAMPANEL.ordinal();
        WriteEepromOneByte(OffsetAddr, DEF_MISC_SETTING[MISCFunc.DREAMPANEL.ordinal()]);

        OffsetAddr = BaseAddr + MISCFunc.ONOFFMUSIC.ordinal();
        WriteEepromOneByte(OffsetAddr, DEF_MISC_SETTING[MISCFunc.ONOFFMUSIC.ordinal()]);

        OffsetAddr = BaseAddr + MISCFunc.LISTMODE.ordinal();
        WriteEepromOneByte(OffsetAddr, DEF_MISC_SETTING[MISCFunc.LISTMODE.ordinal()]);

        OffsetAddr = BaseAddr + MISCFunc.REPEATMODE.ordinal();
        WriteEepromOneByte(OffsetAddr, DEF_MISC_SETTING[MISCFunc.REPEATMODE.ordinal()]);

        OffsetAddr = BaseAddr + MISCFunc.SWITCHMODE.ordinal();
        WriteEepromOneByte(OffsetAddr, DEF_MISC_SETTING[MISCFunc.SWITCHMODE.ordinal()]);

        OffsetAddr = BaseAddr + MISCFunc.PLAYMODE.ordinal();
        WriteEepromOneByte(OffsetAddr, DEF_MISC_SETTING[MISCFunc.PLAYMODE.ordinal()]);

        for (int i = 0; i < DEF_MISC_SETTING.length; i++) {
            MiscData[i] = DEF_MISC_SETTING[i];
        }
        // int idx = 0;
        // for (idx = 0; idx <= MISCFunc.PLAYMODE.ordinal(); idx++) {
        // MiscData[idx] = -1;
        // }
        // Log.d(DBTAG, "Reset the MISC EEP to default.");
    }

    public static void SaveMiscSetting(MISCFunc MiscFunc, int value) {
        if (value == LoadMiscSetting(MiscFunc))
            return;

        Log.d(DBTAG, "SaveMiscSetting of " + MiscFunc + " Value is:" + value);

        int func = MiscFunc.ordinal();
        int offsetAddr = BlockStartAddr.MISC + func;

        WriteEepromOneByte(offsetAddr, value);
        MiscData[func] = value;
    }

    public static int LoadMiscSetting(MISCFunc MiscFunc) {
        int value;
        int func = MiscFunc.ordinal();

        value = MiscData[func];

        switch (MiscFunc) { // return default if load fail
            case COLOR_MANAGEMENT:
                if (value < 0 || value > 2)
                    value = 1;
                break;

            case BACKLIGHT:
                if (value < 0 || value > 100)
                    value = 100;
                break;

            case DREAMPANEL:
                if (value < 0 || value > 3)
                    value = 0;
                break;

            default:
                break;
        }

        // Log.d(DBTAG, "LoadMiscSetting of " + MiscFunc + " Value is:" +
        // value);
        return value;
    }

    /**************************************************************************
     * 
     * NAVIGATE FLAG
     ***************************************************************************/
    private static void SetDefaultNavigateFlag() {
        int OffsetAddr = BlockStartAddr.NAVIGATE_FLAG;

        WriteEepromOneByte(OffsetAddr, NAVIGATE_SEARCH_ON);
        NavigateFlagData = NAVIGATE_SEARCH_ON;
    }

    public static void SaveNavigateFlag(int value) {
        if (value == LoadNavigateFlag())
            return;

        int OffsetAddr = BlockStartAddr.NAVIGATE_FLAG;
        if (value == 1)
            value = NAVIGATE_SEARCH_ON; // onflag is special.

        WriteEepromOneByte(OffsetAddr, value);
        NavigateFlagData = value;
    }

    public static int LoadNavigateFlag() {
        int value;

        if (NavigateFlagData == NAVIGATE_SEARCH_ON)
            value = 1; // onflag is special,go to search channel.
        else
            value = 0; // other value is off.

        return value;
    }

    public static int ReadCharaterData() {
        return ReadEepromOneByte(0, BlockStartAddr.CHARACTER_ADDR);
    }

    public static void WriteCharaterData() {
        int OffsetAddr = BlockStartAddr.CHARACTER_ADDR;
        WriteEepromOneByte(OffsetAddr, CHARACTER_DATA);
    }

    /**************************************************************************
     * 
     * IS NEW EEPROM
     ***************************************************************************/
    public static void WriteEepromFlag() {
        int OffsetAddr = BlockStartAddr.NEW_EEPROM_FLAG1;
        WriteEepromOneByte(OffsetAddr, EEPROM_FLAG1_DATA);

        OffsetAddr = BlockStartAddr.NEW_EEPROM_FLAG2;
        WriteEepromOneByte(OffsetAddr, EEPROM_FLAG2_DATA);

        OffsetAddr = BlockStartAddr.NEW_EEPROM_FLAG3;
        WriteEepromOneByte(OffsetAddr, EEPROM_FLAG3_DATA);
    }

    /**************************************************************************
     * 
     * FACTORY SETTING
     ***************************************************************************/
    public static void LoadFactorySettingBuf() {
        int offsetAddr = BlockStartAddr.FACTORY_SETTING;
        FacSettingData = ReadEepromNByte(offsetAddr, FACTORY_SETTING_LEN);

        for (int i = 0; i < FACTORY_SETTING_LEN; i++)
            Log.d(DBTAG, "load FactorySetting data :" + FacSettingData[i]);
    }

    public static void SetDefaultFactorySetting() {
        int offsetAddr = BlockStartAddr.FACTORY_SETTING;
        for (int i = 0; i < FactoryFunc.END.ordinal(); i++) {
            if (i < FACTORY_SETTING_LEN) {
                WriteEepromOneByte(offsetAddr + i, DEF_FACTORY_SETTING[i]);
                FacSettingData[i] = DEF_FACTORY_SETTING[i];
            }
        }
    }

    public static int LoadFactorySetting(FactoryFunc facFunction) {
        int func = facFunction.ordinal();
        if (func >= FACTORY_SETTING_LEN)
            return -1;

        int value;
        value = FacSettingData[func];
        return value;
    }

    public static int SaveFactorySetting(FactoryFunc facfunction, int value) {
        int func = facfunction.ordinal();
        if (func >= FACTORY_SETTING_LEN)
            return -1;

        int OffsetAddr = BlockStartAddr.FACTORY_SETTING + func;
        WriteEepromOneByte(OffsetAddr, value);
        FacSettingData[func] = value;
        return 0;
    }

    /* if ADC auto-calibration by source/fmt change, return true */
    /* if ADC auto-calibration only in factory mode, return false */
    public static boolean IsAdcAutoCal() {
        int value = LoadFactorySetting(FactoryFunc.AUTOCAL_FLAG);

        if (value == 1)
            return true;
        else if (value == 0)
            return false;
        else
            return false;
    }

    public static int IsFactoryModeOn() {
        int value = LoadFactorySetting(FactoryFunc.FACTORY_MODE_FLAG);

        if (value == 1)
            return 1;
        else
            return 0;
    }

    public static boolean IsBurnFlagOn() {
        int value = LoadFactorySetting(FactoryFunc.BURN_FLAG);
        if (value == GlobalConst.BURN_FLAG_ON)
            return true;
        else
            return false;
    }

    public static int IsOneKeyFlagOn() {
        int value = LoadFactorySetting(FactoryFunc.ONEKEY_ONOFF);

        if (value == 1)
            return 1;
        else
            return 0;
    }

    // ////////////////////////ssc
    public static int[] GetSscDate() {
        int[] buf = new int[SSC_LEN];

        buf[0] = LoadFactorySetting(FactoryFunc.SSC_RANGE);
        buf[1] = LoadFactorySetting(FactoryFunc.SSC_LOWRANGE);
        buf[2] = LoadFactorySetting(FactoryFunc.SSC_CYSLE);
        buf[3] = LoadFactorySetting(FactoryFunc.SSC_ONOFF);

        return buf;
    }

    public static void SetSscDate(int[] buf, int len) {
        if (len > SSC_LEN)
            return;

        SaveFactorySetting(FactoryFunc.SSC_RANGE, buf[0]);
        SaveFactorySetting(FactoryFunc.SSC_LOWRANGE, buf[1]);
        SaveFactorySetting(FactoryFunc.SSC_CYSLE, buf[2]);
        SaveFactorySetting(FactoryFunc.SSC_ONOFF, buf[3]);

    }

    /**************************************************************************
     * 
     * FACTORY OPTION
     ***************************************************************************/
    public static void SetDefaultFactoryOption() {
        int offsetAddr = BlockStartAddr.FACTORY_OPTION;

        WriteEepromNByte(offsetAddr, FactoryOption.END.ordinal(), DEF_FACTORY_OPTION);
    }

    public static int LoadFactoryOption(FactoryOption facOption) {
        if (facOption.ordinal() >= FACTORY_OPTION_LEN)
            return -1;

        int OffsetAddr = BlockStartAddr.FACTORY_OPTION + facOption.ordinal();
        return ReadEepromOneByte(OffsetAddr);
    }

    public static int SaveFactoryOption(FactoryOption facOption, int value) {
        if (facOption.ordinal() >= FACTORY_OPTION_LEN)
            return -1;

        int OffsetAddr = BlockStartAddr.FACTORY_OPTION + facOption.ordinal();
        WriteEepromOneByte(OffsetAddr, value);
        return 0;
    }

    /* if load default hdcp key, return true */
    /* if load eeprom hdcp key, return false */
    public static boolean IsLoadDefaultHdcp() {
        int value = LoadFactoryOption(FactoryOption.DEF_HDCP_FLAG);
        if (value == GlobalConst.LOAD_DEF_HDCP)
            return true;
        else
            return false;
    }

    public static boolean getOnLineMusicFlag() {
        int value = LoadFactoryOption(FactoryOption.ONLINE_MUSIC_FLAG);
        // Log.d(DBTAG, "eepromJNI.getOnLineMusicFlag .value = " + value);
        if (value == GlobalConst.ONLINE_MUSIC_OFF)
            return false;
        else
            return true;
    }

    public static boolean FacGetSerialPortSwitchFlag() {
        int value = LoadFactoryOption(FactoryOption.SERIALMODE_SWITCH);
        Log.d(DBTAG, "getSerialPortSwitchFlag .value = " + value);
        if (value == GlobalConst.SERIALPORT_ON)
            return true;
        else
            return false;
    }

    public static void FacSetSerialPortSwitchFlag(boolean flag) {
        if (flag) {
            SaveFactoryOption(FactoryOption.SERIALMODE_SWITCH, GlobalConst.SERIALPORT_ON);
            SetUartSwitch(1);
        } else {
            SaveFactoryOption(FactoryOption.SERIALMODE_SWITCH, 0);
            SetUartSwitch(0);
        }
    }

    public static boolean getStartPicFlag() {
        int value = LoadFactoryOption(FactoryOption.START_PIC_FALG);
        // Log.d(DBTAG, "eepromJNI.getStartPicFlag .value = " + value);
        if (value == GlobalConst.START_PIC_ON)
            return true;
        else
            return false;
    }

    public static boolean getStandByModeFlag() {
        int value = LoadFactoryOption(FactoryOption.STANDBY_MODE_FLAG);
        // Log.d(DBTAG, "getStandByModeFlag .value = " + value);
        if (value == GlobalConst.STANDBY_FAST) // resume mode
        {
            // Log.d("good","sunny:eep read fast true");
            return true;
        } else // restart mode
        {
            // Log.d("good","sunny:eep read completely false");
            return false;
        }
    }

    public static int FacGetHdmiInternalMode() {
        int OffsetAddr = BlockStartAddr.FACTORY_OPTION
            + FactoryOption.HDMI_INTERNAL_MODE_BYTE1.ordinal();
        int[] data = ReadEepromNByte(OffsetAddr, 4);
        int value = 0;
        for (int i = 0; i < 4; i++) {
            value <<= 8;
            value += data[i];
            Log.d("good", "FacGetHdmiInternalMode value=" + value);
        }
        return value;
    }

    public static void FacSetHdmiInternalMode(int value) {
        int[] data = {
            0, 0, 0, 0
        };
        for (int i = 3; i >= 0; i--) {
            data[i] = (byte) value;
            value >>= 8;
            Log.d("good", "FacSetHdmiInternalMode data=" + data[i]);
        }
        int OffsetAddr = BlockStartAddr.FACTORY_OPTION
            + FactoryOption.HDMI_INTERNAL_MODE_BYTE1.ordinal();
        WriteEepromNByte(OffsetAddr, 4, data);
    }

    /**************************************************************************
     * 
     * RGB OGO
     ***************************************************************************/
    public static void SetDefaultRbgOgo() {
        vppJNI.gRGBogo.en = 0x01;
        vppJNI.gRGBogo.r_pre_offset = 0x00;
        vppJNI.gRGBogo.g_pre_offset = 0x00;
        vppJNI.gRGBogo.b_pre_offset = 0x00;
        vppJNI.gRGBogo.r_gain = 1024;
        vppJNI.gRGBogo.g_gain = 1024;
        vppJNI.gRGBogo.b_gain = 1024;
        vppJNI.gRGBogo.r_post_offset = 0x00;
        vppJNI.gRGBogo.g_post_offset = 0x00;
        vppJNI.gRGBogo.b_post_offset = 0x00;
        SaveRgbOgo(vppJNI.gRGBogo);
    }

    public static void SaveRgbOgo(RGBogo rgbOgo) {
        int offsetAddr = BlockStartAddr.RGB_OGO;
        int buf[] = new int[RGB_OGO_LEN];

        // Log.d(DBTAG,"set adc address ="+
        // BlockStartAddr.ADC_CALIBRATION);
        buf = rgbOgo.ToByteArrary();

        // Log.d(DBTAG, "Save ADC addr = " + adcCalOffsetAddr);

        WriteEepromNByte(offsetAddr, RGB_OGO_LEN, buf);
    }

    public static RGBogo LoadRgbOgo() {
        int offsetAddr = BlockStartAddr.RGB_OGO;
        int buf[] = new int[RGB_OGO_LEN];

        // Log.d(DBTAG,"load adc address ="+ adcCalOffsetAddr);

        ReadEepromNByte(0, offsetAddr, RGB_OGO_LEN, buf);

        vppJNI.gRGBogo = vppJNI.gRGBogo.FromByteArrary(buf);

        // Log.d("VPP","load eeprom gRGBogo value :");
        // vppJNI.gRGBogo.DbPrint();

        return vppJNI.gRGBogo;
    }

    public static void FacSet_RGBogo(String channelSelect,
        String ganioffsetSel, int value) {

        if (ganioffsetSel.equals("gain")) {
            if (channelSelect.equals("r")) {
                vppJNI.gRGBogo.r_gain = value;
            } else if (channelSelect.equals("g")) {
                vppJNI.gRGBogo.g_gain = value;
            } else if (channelSelect.equals("b")) {
                vppJNI.gRGBogo.b_gain = value;
            }
        } else if (ganioffsetSel.equals("pre_offset")) {
            if (channelSelect.equals("r")) {
                vppJNI.gRGBogo.r_pre_offset = value;
            } else if (channelSelect.equals("g")) {
                vppJNI.gRGBogo.g_pre_offset = value;
            } else if (channelSelect.equals("b")) {
                vppJNI.gRGBogo.b_pre_offset = value;
            }
        } else if (ganioffsetSel.equals("post_offset")) {
            if (channelSelect.equals("r")) {
                vppJNI.gRGBogo.r_post_offset = value;
            } else if (channelSelect.equals("g")) {
                vppJNI.gRGBogo.g_post_offset = value;
            } else if (channelSelect.equals("b")) {
                vppJNI.gRGBogo.b_post_offset = value;
            }
        }

        vppJNI.gRGBogo.en = 1;
        vppJNI.gRGBogo.DbPrint();
        vppJNI.SetRGBogo(vppJNI.gRGBogo);
        SaveRgbOgo(vppJNI.gRGBogo);
    }

    public static int FacGet_RGBogo(String channelSelect, String ganioffsetSel) {

        if (ganioffsetSel.equals("gain")) {
            if (channelSelect.equals("r")) {
                return vppJNI.gRGBogo.r_gain;
            } else if (channelSelect.equals("g")) {
                return vppJNI.gRGBogo.g_gain;
            } else if (channelSelect.equals("b")) {
                return vppJNI.gRGBogo.b_gain;
            }
        } else if (ganioffsetSel.equals("pre_offset")) {
            if (channelSelect.equals("r")) {
                return vppJNI.gRGBogo.r_pre_offset;
            } else if (channelSelect.equals("g")) {
                return vppJNI.gRGBogo.g_pre_offset;
            } else if (channelSelect.equals("b")) {
                return vppJNI.gRGBogo.b_pre_offset;
            }
        } else if (ganioffsetSel.equals("post_offset")) {
            if (channelSelect.equals("r")) {
                return vppJNI.gRGBogo.r_post_offset;
            } else if (channelSelect.equals("g")) {
                return vppJNI.gRGBogo.g_post_offset;
            } else if (channelSelect.equals("b")) {
                return vppJNI.gRGBogo.b_post_offset;
            }
        }
        return 0;
    }

    /**************************************************************************
     * 
     * EEPROM DATE
     ***************************************************************************/
    public static String LoadEepromDate() {
        int value;
        char[] eepromDate = new char[EEPROM_DATE_LEN];

        for (int i = 0; i < EEPROM_DATE_LEN; i++) {
            int OffsetAddr = BlockStartAddr.EEPROM_DATE + i;
            value = ReadEepromOneByte(0, OffsetAddr);
            if ((value >= 65) && (value <= 90) // A---Z
                || (value >= 48) && (value <= 57)) { // 0---9
                eepromDate[i] = (char) value;
            } else {
                eepromDate[i] = (char) 32;
            }
        }
        return String.valueOf(eepromDate);
    }

    public static void SetDefaultEepromDate() {
        int[] defData = new int[] {
            0x49, 0x4e, 0x49, 0x54, 0x20, 0x49, 0x53, 0x20, 0x4f, 0x4b
        };
        int typOffsetAddr = BlockStartAddr.EEPROM_DATE;
        WriteEepromNByte(typOffsetAddr, EEPROM_DATE_LEN, defData);

        // this is used to record init times.
        typOffsetAddr = BlockStartAddr.INIT_TIMES;
        int times = ReadEepromOneByte(0, typOffsetAddr);
        if (times < 255)
            times++;
        else
            times = 0;
        WriteEepromOneByte(typOffsetAddr, times);
    }

    /**************************************************************************
     * 
     * BAR CODE
     ***************************************************************************/
    public static String LoadBarCode() {
        int value;
        char[] barCode = new char[BARCODE_LEN];

        for (int i = 0; i < BARCODE_LEN; i++) {
            int OffsetAddr = BlockStartAddr.BARCODE + i;
            value = ReadEepromOneByte(0, OffsetAddr);
            if ((value >= 65) && (value <= 90) // A---Z
                || (value >= 48) && (value <= 57) // 0---9
                || (value == 32) // space
                || (value == 45)) { // -
                barCode[i] = (char) value;
            } else {
                barCode[i] = (char) 32;
            }
        }
        return String.valueOf(barCode);
    }

    /**************************************************************************
     * 
     * PANEL SELECT
     ***************************************************************************/
    public static void SetDefaultPanelType() {
        int offsetAddr = BlockStartAddr.GAMMA_SEL;

        WriteEepromOneByte(offsetAddr, PanelType.LG42E83.ordinal());
        GammaSel = PanelType.LG42E83.ordinal();
    }

    public static void SetCurPanelType(int value) {
        int OffsetAddr = BlockStartAddr.GAMMA_SEL;

        WriteEepromOneByte(OffsetAddr, value);
        GammaSel = value;
    }

    public static PanelType LoadCurPanelType() {
        switch (GammaSel) {// value){
            case 0:
                Log.d(DBTAG, "set panel-> LG42E83");
                return PanelType.LG42E83;
            case 1:
                Log.d(DBTAG, "set panel-> LG47E83");
                return PanelType.LG47E83;
            case 2:
                Log.d(DBTAG, "set panel-> LG55E83");
                return PanelType.LG55E83;
            case 3:
                Log.d(DBTAG, "set panel-> LG42E85");
                return PanelType.LG42E85;
            case 4:
                Log.d(DBTAG, "set panel-> LG47E85");
                return PanelType.LG47E85;
            default:
                Log.d(DBTAG, "set panel-> LG42E83");
                return PanelType.LG42E83;
        }
    }

    public static int GetCurPanel() {
        return GammaSel;
    }

    /**************************************************************************
     * 
     * PUBLIC EEPROM READ/WRITE
     ***************************************************************************/
    public static void WriteEepromOneByte(int offset, int value) {
        SetWriteProtect(false);
        WriteEepromOneByte(0, offset, value);
        SetWriteProtect(true);
    }

    public static void WriteEepromNByte(int offset, int len, int[] buf) {
        SetWriteProtect(false);
        WriteEepromNByte(0, offset, len, buf);
        SetWriteProtect(true);
    }

    public static int ReadEepromOneByte(int offset) {
        return ReadEepromOneByte(0, offset);
    }

    public static int[] ReadEepromNByte(int offset, int len) {
        int[] buf = new int[len];
        ReadEepromNByte(0, offset, len, buf);
        return buf;
    }

    /**************************************************************************
     * 
     * IIC BUS ON/OFF
     ***************************************************************************/
    public static void IICBusOn() {
        isBusOff = true;
        SetWriteProtect(false);
        IICBusOnOff(0);
    }

    public static void IICBusOff() {
        IICBusOnOff(1);
        SetWriteProtect(true);
        isBusOff = false;
    }

    public static boolean IsBusOff() {
        return isBusOff;
    }

    /*************************************
     * test functions
     ************************************/
    public static void TestEepromRW() {
        int i, j, k;
        int buf[] = new int[233];

        for (i = 0; i < 233; i++) {
            buf[i] = i;
        }

        WriteEepromNByte(0, 7776, 233, buf);

        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            Log.d(DBTAG, "delay error!!");
            e.printStackTrace();
        }

        ReadEepromNByte(0, 7776, 233, buf);

        for (i = 0; i < 233; i++)
            Log.d(DBTAG, "from EEPROM, buf-" + (i + 7776) + " =" + buf[i]);

        Log.d(DBTAG, "VGA EDID = " + BlockStartAddr.VGA_EDID);
        Log.d(DBTAG, "HDMI EDID = " + BlockStartAddr.HDMI_EDID);
        Log.d(DBTAG, "SOURCE ID = " + BlockStartAddr.SOURCE_SELECT);
        Log.d(DBTAG, "CHANNAL INFO = " + BlockStartAddr.CHANNEL_INFO);
        Log.d(DBTAG, "VIDEO SETTING = " + BlockStartAddr.VIDEO_SETTING);
        Log.d(DBTAG, "AUDIO SETTING = " + BlockStartAddr.AUDIO_SETTING);
        Log.d(DBTAG, "VGA ADJUSTMENT = " + BlockStartAddr.VGA_ADJUSTMENT);
        Log.d(DBTAG, "ADC CALIBRATION = " + BlockStartAddr.ADC_CALIBRATION);
        Log.d(DBTAG, "HDCP = " + BlockStartAddr.HDCP);
        Log.d(DBTAG, "MAC = " + BlockStartAddr.MAC);
    }

    /***************************************************
     * load liberary & JNI methods
     ***************************************************/
    static {
        System.loadLibrary("eeprom_api");
    }

    /****************************
     * NOTE: please set selI2C =0
     ****************************/
    static native int OpenI2CModule(int selI2C);

    static native int CloseI2CModule(int selI2C);

    static native int IsI2CModuleOpen(int selI2C);

    static native int WriteEepromOneByte(int selI2C, int offset, int value);

    static native int WriteEepromNByte(int selI2C, int offset, int len,
        int[] buf);

    static native int ReadEepromOneByte(int selI2C, int offset);

    static native int ReadEepromNByte(int selI2C, int offset, int len, int[] buf);

    static native int EreaseEeprom(int selI2C);

    static native int GetEepromByteSize();

    static native int LoadHdcpKey(int selI2C); //

    static native int newLoadHdcpKey(int selI2C, int[] header, int hdcpAddrInEep);

    static native int LoadHdmiEdid(int selI2C);// move eeprom edid data

    static native int IICBusOnOff(int isOn); // isOn=1 -> bus on, isOn=0 -> bus
                                             // off
    // to related place

    static native int IsHdcpKeyRight();
}
