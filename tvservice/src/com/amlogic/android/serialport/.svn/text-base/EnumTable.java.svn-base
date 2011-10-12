package com.amlogic.android.serialport;

import android.os.SystemProperties;

public class EnumTable {

    private String channel;
    private String mac1, mac2;
    private ParamTable pt = new ParamTable();

    public void SetChannel(String ch) {
        channel = ch;
    }

    public ParamTable GetParamAttribute(int cmdname, int cmdvalue) {

        switch (cmdname) {
        // case 0x7d: IR
            case 0x11:
                pt.sname = "Brightness_USER";
                pt.svalue = String.valueOf(cmdvalue);
                return pt;
            case 0x15:
                pt.sname = "Contrast_USER";
                pt.svalue = String.valueOf(cmdvalue);
                return pt;
            case 0x19:
                pt.sname = "Color_USER";
                pt.svalue = String.valueOf(cmdvalue);
                return pt;
            case 0x1D:
                pt.sname = "Sharpness_USER";
                pt.svalue = String.valueOf(cmdvalue);
                return pt;
            case 0x21:
                pt.sname = channel + "_NR";
                switch (cmdvalue) {
                    case 0:
                        pt.svalue = "OFF";
                        break;
                    case 1:
                        pt.svalue = "LOW";
                        break;
                    case 2:
                        pt.svalue = "MID";
                        break;
                    case 3:
                        pt.svalue = "HIGH";
                        break;
                    default:
                        pt.svalue = "AUTO";
                }

                return pt;
            case 0x25:
                pt.sname = "PictureMode";
                switch (cmdvalue) {
                    case 0:
                        pt.svalue = "STD";
                        break;
                    case 1:
                        pt.svalue = "VIVID";
                        break;
                    case 2:
                        pt.svalue = "SOFT";
                        break;
                    case 3:
                        pt.svalue = "USER";
                        break;
                    default:
                        pt.svalue = "STD";
                }

                return pt;
            case 0x29:
                pt.sname = "SoundMode";
                switch (cmdvalue) {
                    case 0:
                        pt.svalue = "STD";
                        break;
                    case 1:
                        pt.svalue = "MUSIC";
                        break;
                    case 2:
                        pt.svalue = "NEWS";
                        break;
                    case 3:
                        pt.svalue = "THEATER";
                        break;
                    case 4:
                        pt.svalue = "USER";
                        break;
                    default:
                        pt.svalue = "STD";
                }
                return pt;

            case 0x2D:
                pt.sname = "INCREASE_BASS";
                if (cmdvalue == 1)
                    pt.svalue = "ON";
                else
                    pt.svalue = "OFF";
                return pt;
            case 0x31:
                pt.sname = "VOICE";
                if (cmdvalue == 1)
                    pt.svalue = "ON";
                else
                    pt.svalue = "OFF";
                return pt;
            case 0x35:
                pt.sname = "SRS";
                if (cmdvalue == 1)
                    pt.svalue = "ON";
                else
                    pt.svalue = "OFF";
                return pt;
            case 0x39:
                pt.sname = "BackLight";
                pt.svalue = String.valueOf(cmdvalue);
                return pt;
            case 0x3D:
                return null;
            case 0x41:
                pt.sname = "Language";
                if (cmdvalue == 1)
                    pt.svalue = "CHN";
                else
                    pt.svalue = "ENG";
                return pt;
            case 0x45:
                pt.sname = "SleepTime";
                if (cmdvalue == 0)
                    pt.svalue = "0";
                else if (cmdvalue == 1)
                    pt.svalue = "5";
                else if (cmdvalue == 2)
                    pt.svalue = "15";
                else if (cmdvalue == 3)
                    pt.svalue = "30";
                else if (cmdvalue == 4)
                    pt.svalue = "60";
                else if (cmdvalue == 5)
                    pt.svalue = "90";
                else
                    pt.svalue = "120";
                return pt;
            case 0x49:
                pt.sname = "PowerOnSource";
                switch (cmdvalue) {
                    case 0:
                        pt.svalue = "MEMORY";
                        break;
                    case 1:
                        pt.svalue = "TV";
                        break;
                    case 2:
                    case 3:
                    case 4:
                        pt.svalue = "AV";
                        break;
                    case 5:
                        break;
                    case 6:
                    case 7:
                        pt.svalue = "YUV";
                        break;
                    case 8:
                        pt.svalue = "HDMI1";
                        break;
                    case 9:
                        pt.svalue = "HDMI2";
                        break;
                    case 10:
                        pt.svalue = "HDMI3";
                        break;
                    case 11:
                        pt.svalue = "VGA";
                        break;
                    case 12:
                        pt.svalue = "COOCAA";
                        break;
                    default:
                        pt.svalue = "COOCAA";
                }

                return pt;
            case 0x4D:
                pt.sname = "KeyTone";
                if (cmdvalue == 1)
                    pt.svalue = "ON";
                else
                    pt.svalue = "OFF";
                return pt;
            case 0x51:
                return null;
            case 0x55:
                pt.sname = channel + "_Hue";
                pt.svalue = String.valueOf(cmdvalue);
                return pt;
                // case 0x59:
                // int highswitch=cmdvalue&0xff00;
                // int lowvol=cmdvalue&0x00ff;
                // pt.sname="SubwooferSwitch";
                // if(highswitch==0)
                // pt.svalue="OFF";
                // else
                // pt.svalue="ON";
                // pt.sname="SubwooferVol";
                // pt.svalue=String.valueOf(lowvol);
                // return pt;
            case 0x5D:
                pt.sname = "DreamPanel";
                if (cmdvalue == 0)
                    pt.svalue = "OFF";
                else if (cmdvalue == 1)
                    pt.svalue = "SENSOR";
                else if (cmdvalue == 2)
                    pt.svalue = "SCENE";
                else if (cmdvalue == 3)
                    pt.svalue = "ALL";
                else if (cmdvalue == 4)
                    pt.svalue = "DEMO";
                return pt;
            case 0x61:
                pt.sname = "SixColor";
                if (cmdvalue == 0)
                    pt.svalue = "OFF";
                else if (cmdvalue == 1)
                    pt.svalue = "OPTI";
                else if (cmdvalue == 2)
                    pt.svalue = "ENHANCE";
                else
                    pt.svalue = "DEMO";
                return pt;
            case 0x65:
                int high = (cmdvalue >> 8) & 0xff;
                int low = cmdvalue & 0xff;
                if (high == 0)
                    pt.sname = "SoundMode_USER_EQ_100";
                else if (high == 1)
                    pt.sname = "SoundMode_USER_EQ_300";
                else if (high == 2)
                    pt.sname = "SoundMode_USER_EQ_1K";
                else if (high == 3)
                    pt.sname = "SoundMode_USER_EQ_3K";
                else
                    pt.sname = "SoundMode_USER_EQ_10K";
                pt.svalue = String.valueOf(low);
                return pt;
            case 0x79:
                pt.sname = "ColorTemp";
                if (cmdvalue == 0)
                    pt.svalue = "COLD";
                else if (cmdvalue == 1)
                    pt.svalue = "STD";
                else
                    pt.svalue = "WARM";
                return pt;
            case 0x7f:
                pt.sname = "Balance";
                pt.svalue = String.valueOf(cmdvalue);// 0-100(-50~50)
                return pt;
            case 0x83:
                pt.sname = "LocalDimming";
                if (cmdvalue == 0)
                    pt.svalue = "OFF";
                else if (cmdvalue == 1)
                    pt.svalue = "ON";
                else
                    pt.svalue = "DEMO";
                return pt;
            case 0x87:
                pt.sname = "MEMC";
                if (cmdvalue == 0)
                    pt.svalue = "OFF";
                else if (cmdvalue == 1)
                    pt.svalue = "WEAK";
                else if (cmdvalue == 2)
                    pt.svalue = "MID";
                else
                    pt.svalue = "STRONG";
                return pt;
            case 0x8b:
                pt.sname = "PowerOnMusic";
                if (cmdvalue == 1)
                    pt.svalue = "ON";
                else
                    pt.svalue = "OFF";
                return pt;
            case 0x8f:
                pt.sname = "WallEffects";
                if (cmdvalue == 1)
                    pt.svalue = "ON";
                else
                    pt.svalue = "OFF";
                return pt;
            case 0x95:
                pt.sname = "CHANNEL";
                switch (cmdvalue) {
                    case 0:
                    case 1:
                        pt.svalue = "TV";
                        break;
                    case 2:
                    case 3:
                    case 4:
                        pt.svalue = "AV";
                        break;
                    case 5:
                        break;
                    case 6:
                    case 7:
                        pt.svalue = "YUV";
                        break;
                    case 8:
                        pt.svalue = "HDMI1";
                        break;
                    case 9:
                        pt.svalue = "HDMI2";
                        break;
                    case 10:
                        pt.svalue = "HDMI3";
                        break;
                    case 11:
                        pt.svalue = "VGA";
                        break;
                    case 12:
                        pt.svalue = "COOCAA";
                        break;
                    default:
                        pt.svalue = "COOCAA";
                }
                return pt;
            case 0x99:// MAC addr 1,2
                mac1 = ConvertMac(cmdvalue);
                break;
            case 0x9d:// MAC addr 3,4
                mac2 = ConvertMac(cmdvalue);
                break;
            case 0x9f:// MAC addr 5,6
                String mac3 = ConvertMac(cmdvalue);

                String mac = mac1 + ":" + mac2 + ":" + mac3;
                String varible = "ethaddr";
                String prefix = SystemProperties.get("ro.ubootenv.varible.prefix");
                SystemProperties.set(prefix + "." + varible, mac);
                pt.sname = "MAC";
                pt.svalue = mac;
                return pt;
            case 0xAF:
                pt.sname = channel + "_DisplayMode";
                if (cmdvalue == 0)
                    pt.svalue = "169";
                else if (cmdvalue == 1)
                    pt.svalue = "PERSON";
                else if (cmdvalue == 2)
                    pt.svalue = "THEATER";
                else if (cmdvalue == 3)
                    pt.svalue = "SUBTITLE";
                else if (cmdvalue == 4)
                    pt.svalue = "4:3";
                else
                    pt.svalue = "PANORA";
                return pt;
            case 0xD0: // Panel info
                pt.sname = "PanelInfor";
                pt.svalue = String.valueOf(cmdvalue);
                return pt;

        }

        return null;
    }

    public String ConvertMac(int cmdvalue) {
        String ret = "";
        int high = (cmdvalue >> 8) & 0xff;
        int low = cmdvalue & 0xff;
        ret += FormatToHexStr(high) + ":" + FormatToHexStr(low);
        return ret;
    }

    public String FormatToHexStr(int hlbyte) {// hlbyte need to &0xff
        String hex = Integer.toHexString(hlbyte);
        if (hex.length() == 1) {
            hex = '0' + hex;
        }
        return hex.toUpperCase();
    }

    private SettingParam param = new SettingParam();

    public SettingParam ConvertToSend(String cmdname, String cmdvalue) {

        if (cmdvalue == null || cmdvalue.equals(""))
            return null;

        if (cmdname.toLowerCase().contains("brightness")) {
            param.name = 0x12;
            param.value = Integer.parseInt(cmdvalue);
            return param;
        } else if (cmdname.toLowerCase().contains("contrast")) {
            param.name = 0x16;
            param.value = Integer.parseInt(cmdvalue);
            return param;
        } else if (cmdname.toLowerCase().contains("colour")) {
            param.name = 0x1a;
            param.value = Integer.parseInt(cmdvalue);
            return param;
        } else if (cmdname.toLowerCase().contains("sharpness")) {
            param.name = 0x1E;
            param.value = Integer.parseInt(cmdvalue);
            return param;
        } else if (cmdname.toLowerCase().contains("nr")) {
            param.name = 0x22;
            param.value = Integer.parseInt(cmdvalue);
            return param;
        } else if (cmdname.toLowerCase().contains("picturemode")) {
            param.name = 0x26;
            param.value = Integer.parseInt(cmdvalue);
            return param;
        } else if (cmdname.toLowerCase().contains("soundmode")) {
            param.name = 0x2a;
            param.value = Integer.parseInt(cmdvalue);
            return param;
        } else if (cmdname.toLowerCase().contains("increase_bass")) {
            param.name = 0x2E;
            param.value = Integer.parseInt(cmdvalue);
            return param;
        } else if (cmdname.toLowerCase().contains("voice")) {
            param.name = 0x32;
            param.value = Integer.parseInt(cmdvalue);
            return param;
        } else if (cmdname.toLowerCase().contains("srs")) {
            param.name = 0x36;
            param.value = Integer.parseInt(cmdvalue);
            return param;
        } else if (cmdname.toLowerCase().contains("backlight")) {
            param.name = 0x3a;
            param.value = Integer.parseInt(cmdvalue);
            return param;
        } else if (cmdname.toLowerCase().contains("audioonly")) {
            param.name = 0x3e;
            param.value = Integer.parseInt(cmdvalue);
            return param;
        } else if (cmdname.toLowerCase().contains("language")) {
            param.name = 0x42;
            param.value = Integer.parseInt(cmdvalue);
            return param;
        } else if (cmdname.toLowerCase().contains("sleeptime")) {
            param.name = 0x46;
            param.value = Integer.parseInt(cmdvalue);
            return param;
        } else if (cmdname.toLowerCase().contains("srcselect")) {
            param.name = 0x4A;
            param.value = Integer.parseInt(cmdvalue);
            return param;
        } else if (cmdname.toLowerCase().contains("keysound")) {
            param.name = 0x4e;
            param.value = Integer.parseInt(cmdvalue);
            return param;
        } else if (cmdname.toLowerCase().contains("adntask")) {
            param.name = 0x52;
            param.value = Integer.parseInt(cmdvalue);
            return param;
        } else if (cmdname.toLowerCase().contains("hue")) {
            param.name = 0x56;
            param.value = Integer.parseInt(cmdvalue);
            return param;
        } else if (cmdname.toLowerCase().contains("subwoofer")) {
            param.name = 0x5a;
            param.value = Integer.parseInt(cmdvalue);
            return param;
        } else if (cmdname.toLowerCase().contains("dreampanel")) {
            param.name = 0x5e;
            param.value = Integer.parseInt(cmdvalue);
            return param;
        } else if (cmdname.toLowerCase().contains("sixcolor")) {
            param.name = 0x62;
            param.value = Integer.parseInt(cmdvalue);
            return param;
        }
        // EQ
        else if (cmdname.toLowerCase().contains("eq100")) {
            param.name = 0x66;
            param.value = Integer.parseInt(cmdvalue);
            return param;
        } else if (cmdname.toLowerCase().contains("colortemp")) {
            param.name = 0x7a;
            param.value = Integer.parseInt(cmdvalue);
            return param;
        } else if (cmdname.toLowerCase().contains("balance")) {
            param.name = 0x80;
            param.value = Integer.parseInt(cmdvalue);
            return param;
        } else if (cmdname.toLowerCase().contains("localdimming")) {
            param.name = 0x84;
            param.value = Integer.parseInt(cmdvalue);
            return param;
        } else if (cmdname.toLowerCase().contains("memc")) {
            param.name = 0x88;
            param.value = Integer.parseInt(cmdvalue);
            return param;
        } else if (cmdname.toLowerCase().contains("poweronmusic")) {
            param.name = 0x8c;
            param.value = Integer.parseInt(cmdvalue);
            return param;
        } else if (cmdname.toLowerCase().contains("biguayinxiao")) {
            param.name = 0x90;
            param.value = Integer.parseInt(cmdvalue);
            return param;
        }
        // Default
        else if (cmdname.toLowerCase().contains("setdefault")) {
            param.name = 0x93;
            param.value = Integer.parseInt(cmdvalue);
            return param;
        }
        // Source switch
        else if (cmdname.toLowerCase().contains("shortcut_common_source")) {
            param.name = 0x96;
            param.value = Integer.parseInt(cmdvalue);
            return param;
        }
        // MAC addr
        else if (cmdname.toLowerCase().contains("mac")) {
            param.name = 0x9a;
            param.value = Integer.parseInt(cmdvalue);
            return param;
        }
        // Network trouble
        else if (cmdname.toLowerCase().contains("nettrouble")) {
            param.name = 0xa3;
            param.value = Integer.parseInt(cmdvalue);
            return param;
        }
        // netupdate
        else if (cmdname.toLowerCase().contains("netupdate")) {
            param.name = 0xa5;
            param.value = Integer.parseInt(cmdvalue);
            return param;
        }
        // diskfull
        else if (cmdname.toLowerCase().contains("diskfull")) {
            param.name = 0xa9;
            param.value = Integer.parseInt(cmdvalue);
            return param;
        }
        // nodisk
        else if (cmdname.toLowerCase().contains("nodisk")) {
            param.name = 0xab;
            param.value = Integer.parseInt(cmdvalue);
            return param;
        }
        // apolloready
        else if (cmdname.toLowerCase().contains("apolloready")) {
            param.name = 0xad;
            param.value = Integer.parseInt(cmdvalue);
            return param;
        }
        // displaymode
        else if (cmdname.toLowerCase().contains("displaymode")) {
            param.name = 0xb0;
            param.value = Integer.parseInt(cmdvalue);
            return param;
        }
        // volumn
        else if (cmdname.toLowerCase().contains("volumn")) {
            param.name = 0xB4;
            param.value = Integer.parseInt(cmdvalue);
            return param;
        }
        // osd on off
        else if (cmdname.toLowerCase().contains("osdonoff")) {
            param.name = 0xbc;
            param.value = Integer.parseInt(cmdvalue);
            return param;
        }
        // player state
        else if (cmdname.toLowerCase().contains("playerstate")) {
            param.name = 0xc4;
            param.value = Integer.parseInt(cmdvalue);
            return param;
        }
        // homepage
        else if (cmdname.toLowerCase().contains("homepage")) {
            param.name = 0xc8;
            param.value = Integer.parseInt(cmdvalue);
            return param;
        }
        // 3D game
        else if (cmdname.toLowerCase().contains("3dgame")) {
            param.name = 0xcc;
            param.value = Integer.parseInt(cmdvalue);
            return param;
        }
        // volumeosd
        else if (cmdname.toLowerCase().contains("volumeosd")) {
            param.name = 0xD4;
            param.value = Integer.parseInt(cmdvalue);
            return param;
        }

        return null;
    }

    public enum onoff {
        OFF, ON
    }

    public enum SOURCE {
        tPIP_OFF,
        tPIP_SUB_SOURCE_TV,
        tPIP_SUB_SOURCE_AV1,
        tPIP_SUB_SOURCE_AV2,
        tPIP_SUB_SOURCE_AV3,
        tPIP_SUB_SOURCE_SVIDEO,
        tPIP_SUB_SOURCE_HDMI1,
        tPIP_SUB_SOURCE_HDMI2,
        tPIP_SUB_SOURCE_HDMI3,
        tPIP_SUB_SOURCE_INVALID,
    };

    public static final int DEFAULT_JI_XIN_NAME = 0x01;

    public static final int tJI_XIN_NAME_8K80 = 0x00;
    public static final int tJI_XIN_NAME_8K81 = 0x01;
    public static final int tJI_XIN_NAME_8K81_NO_PIP = 0x02;
    public static final int tJI_XIN_NAME_8K79_NO_PIP_NO_FLASH = 0x03;
    public static final int tJI_XIN_NAME_8K83_NO_PIP_NO_FLASH = 0x04;
    public static final int tJI_XIN_NAME_8K83_NO_PIP = 0x05;
    public static final int tJI_XIN_NAME_8K81_CNTV = 0x06;
    public static final int tJI_XIN_NAME_8K81_BBTV = 0x07;
    public static final int tJI_XIN_NAME_8K76 = 0x08;
    public static final int tJI_XIN_NAME_8K83_NO_PIP_NO_FLASH_E62_MODEL = 0x09;
    public static final int tJI_XIN_NAME_8K83_E62_MODEL_NO_DIAL_XUNLEI = 0x0A;

    public static final int tJI_XIN_NAME_8M80 = 0x80;
    public static final int tJI_XIN_NAME_INVALID = 0x81;

    public enum tJI_XIN_NAME {
        tJI_XIN_NAME_8K80(0x00),
        tJI_XIN_NAME_8K81(0x01),
        tJI_XIN_NAME_8K81_NO_PIP(0x02),
        tJI_XIN_NAME_8K79_NO_PIP_NO_FLASH(0x03),
        tJI_XIN_NAME_8K83_NO_PIP_NO_FLASH(0x04),
        tJI_XIN_NAME_8K83_NO_PIP(0x05),
        tJI_XIN_NAME_8K81_CNTV(0x06),
        tJI_XIN_NAME_8K81_BBTV(0x07),
        tJI_XIN_NAME_8K76(0x08),
        tJI_XIN_NAME_8K83_NO_PIP_NO_FLASH_E62_MODEL(0x09),
        tJI_XIN_NAME_8K83_E62_MODEL_NO_DIAL_XUNLEI(0x0A),
        tJI_XIN_NAME_8M80(0x80),
        tJI_XIN_NAME_INVALID(0x81);

        private final int value;

        private tJI_XIN_NAME(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public static final int tTuiSongNeiRong_PPS = 0;
    public static final int tTuiSongNeiRong_XUNLEI = 0xff;
    public static final int DEFAULT_TUI_SONG = 0xff;

    public enum tDOWNLOAD {
        tPOWER_ON_ASK_CONTINUE_DOWNLOAD,
        tPOWER_OFF_ASK_CONTINUE_DOWNLOAD,
        tNETWORK_DISCONNECT,
        tNETWORK_CABLE_PLUG_OUT,
        tUSB_PLUG_OUT,
        tUSB_DISK_FULL,
        tDOWNLOAD_COMPLETE,
        tUPDATING,
        tNETWORK_UPDATE_FINISH,
        tINVALID_MSG_TYPE,
    };

    public enum tDISPLAYMODE {
        tDISPLAYMODE_FULL,
        tDISPLAYMODE_MOVIE,
        tDISPLAYMODE_CAPTION,
        tDISPLAYMODE_4_TO_3,
        tDISPLAYMODE_PANORAMIC,
        tDISPLAYMODE_INVALID,
    };

    public enum tNRMODE {
        tNRMODE_OFF,
        tNRMODE_LOW,
        tNRMODE_MID,
        tNRMODE_HIGH,
        tNRMODE_AUTO,
        tNRMODE_INVALID,
    };

    public enum tPICMODE {
        tPICMODE_STANDARD,
        tPICMODE_VIVID,
        tPICMODE_SOFT,
        tPICMODE_USER,
        tPICMODE_INVALID,
    };

    public enum tSOUNDMODE {
        tSOUNDMODE_STANDARD,
        tSOUNDMODE_MUSIC,
        tSOUNDMODE_NEWS,
        tSOUNDMODE_MOVIE,
        tSOUNDMODE_USER,
        tSOUNDMODE_INVALID,
    };

    public enum tBOOTSOURCE {
        tBOOTSOURCE_AUTO,
        tBOOTSOURCE_TV,
        tBOOTSOURCE_AV1,
        tBOOTSOURCE_AV2,
        tBOOTSOURCE_AV3,
        tBOOTSOURCE_SVIDEO,
        tBOOTSOURCE_YUV1,
        tBOOTSOURCE_YUV2,
        // tBOOTSOURCE_YUV3,
        tBOOTSOURCE_HDMI1,
        tBOOTSOURCE_HDMI2,
        tBOOTSOURCE_HDMI3,
        tBOOTSOURCE_VGA,
        tBOOTSOURCE_COOCAA,
        tBOOTSOURCE_INVALID,
    };

    public enum tSIXCOLOR {
        tSIXCOLOR_OFF,
        tSIXCOLOR_OPTIMIZE,
        tSIXCOLOR_ENHANCE,
        tSIXCOLOR_COLOR_DEMO,
        tSIXCOLOR_DYNAMIC_DEMO,
        tSIXCOLOR_INVALID,
    };

    public enum tDREAMPANEL {
        tDREAMPANEL_OFF,
        tDREAMPANEL_ENVIRON_LIGHT,
        tDREAMPANEL_IMAGE_CONTENT,
        tDREAMPANEL_MULTI_DETECT,
        tDREAMPANEL_INVALID,
    };

    public enum COOCAA_OSD {
        COOCAA_FULL_SCREEN_OSD, COOCAA_SHORT_CUT_OSD, COOCAA_OSD_OFF,
    };
}
