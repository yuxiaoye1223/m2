package com.amlogic.tvjni;

public class GlobalConst {
//    public static final int MENU_DISAPPEAR_TIME = 10;
//    public static final int NUMBERINPUTINFO_DISAPPEAR_TIME = 5;
//    public static final int CHANNELINFO_DISAPPEAR_TIME = 5;
//    public static final int VOLUMEINFO_DISAPPEAR_TIME = 5;
//    public static final int MENU_CLEAR = 1;
//    public static final int CHANNELINFO_CLEAR = 2;
//    public static final boolean DIRECTION_UP = true;
//    public static final boolean DIRECTION_DOWN = false;

//    public static final int MENU_ITEM_HIGHTH = 145;
//    public static final int ITEM_COUNT_PERMENURPAGE_SHOW = 9;
    public static final int MIN_VALUE_MENU_SHOW = 0;
    public static final int MAX_VALUE_MENU_SHOW = 100;
//    public static final int DEFAULT_VALUE_MENU_SHOW = 50;
    public static final int PROGRAM_VOL_CORRECT_MAXVALUE = 20;
    public static final int PROGRAM_VOL_CORRECT_DEFAULT = 10;

//    public static final String MENU_SHOW_STATE_MUSIC = "music";
//    public static final String MENU_SHOW_STATE_PICTURE = "picture";
//    public static final String MENU_SHOW_STATE_TXT = "txt";
//    public static final String MENU_SHOW_STATE_TV_SET = "tv_set";
//    public static final String MENU_SHOW_STATE_PROGRAM = "program";
//    public static final String MENU_SHOW_STATE_SETUP3D = "Setup3D";

    public static int DEBUG_MODE_EMULATOR = 0; // debug at emulator
    public static int DEBUG_MODE_CONSOLE = 1; // debug at console
    public static final int DEBUG_MODE_SELECT = DEBUG_MODE_CONSOLE;

//    public static final String ENTRANCE_TYPE_ANALOG_TV = "AnalogTV";
    
    //handleServiceMessage use and must follow following sequence
    public static final int MSG_TUNER_UPDATE_FREQUENCY = 100;
    public static final int MSG_TUNER_FIND_CHANNEL = 101;
    public static final int MSG_TUNER_AUTOSEARCH_FINISHED = 102;
    public static final int MSG_TUNER_MANUALSEARCH_FINISHED = 103;
    public static final int MSG_TUNER_MANUALSEARCH_ABORTED = 104;
    public static final int MSG_VDINJNI_DISPLAY_CHANNEL_INFO = 105;
    public static final int MSG_SIGNAL_VGA_NO_SIGNAL = 106;
    public static final int MSG_VGA_AUTO_BEGIN = 107;
    public static final int MSG_VGA_AUTO_DONE = 108;
    public static final int MSG_NO_SIGNAL_DETECT = 109;
    public static final int MSG_SIGNAL_STABLE = 110;
    public static final int MSG_SIGNAL_FORMAT_NTSC = 111;
    public static final int MSG_SIGNAL_FORMAT_NOT_NTSC = 112;
    public static final int MSG_SIGNAL_NOT_SUPPORT = 113;
    public static final int MSG_CHANGE_CHANNEL = 114;
    public static final int MSG_DERAMPANEL_DEMO_UPDATE = 115;
    public static final int MSG_VDINJNI_DISPLAY_SOURCE_INFO = 116;
    
//    public static final int TUNER_OP_MANUAL_SEARCH = 0;
//    public static final int TUNER_OP_FINE = 1;
//    public static final int TUNER_OP_AUTO_SEARCH = 2;
    public static final int AUTO_SEARCH_START_CHANNEL = 1;
    public static final int TV_CHANNEL_TOTAL_PROGRAM_COUNT = 256;
    public static final int TV_CHANNEL_MIN_NUMBER = 0;
    public static final int TV_CHANNEL_DEFAULT_NUMBER = 1;
    
    public static final int CVBS_COLORSYS_AUTO = 0;
    public static final int CVBS_COLORSYS_PAL = 1;
    public static final int CVBS_COLORSYS_NTSC = 2;
    public static final int CVBS_COLORSYS_MAXVALUE = CVBS_COLORSYS_NTSC;
    
    public static final int TV_CHANNEL_SOUNDSYS_DK = 0;
    public static final int TV_CHANNEL_SOUNDSYS_I = 1;
    public static final int TV_CHANNEL_SOUNDSYS_BG = 2;
    public static final int TV_CHANNEL_SOUNDSYS_M = 3;
    public static final int TV_CHANNEL_SOUNDSYS_AUTO = 4;
    public static final int TV_CHANNEL_SOUNDSYS_MUTE = 5;
    public static final int TV_CHANNEL_SOUNDSYS_MAXVALUE = TV_CHANNEL_SOUNDSYS_M;

    public static final int SOUNDMODE_START = 0;
    public static final int SOUNDMODE_STD = SOUNDMODE_START + 0;
    public static final int SOUNDMODE_MUSIC = SOUNDMODE_START + 1;
    public static final int SOUNDMODE_NEWS = SOUNDMODE_START + 2;
    public static final int SOUNDMODE_THEATER = SOUNDMODE_START + 3;
    public static final int SOUNDMODE_USER = SOUNDMODE_START + 4;
    public static final int SOUNDMODE_END = SOUNDMODE_USER;

    public static final int AUDIO_EQGAIN_RANGE = 20;  //-10---+10
//    public static final int LOAD_AUDIO_EQGAIN_POS = 0;  //now EQ has 6 parameters,only need 5.
    public static final int LOAD_AUDIO_BASS_POS = 0;
    public static final int LOAD_AUDIO_TREBLE_POS = 4;

    public static final int IS_NO_SIGNAL = 8;
    
    public static final int ONE_TOOGLE_NUMBER = 1;
    public static final int TWO_TOOGLE_NUMBER = 2;
    public static final int THREE_TOOGLE_NUMBER = 3;
//    public static final int DISPLAY_TOOGLE_INFO = -1;
    
//    public static final int EXCHANGE_CHANNEL = 1;
//    public static final int UPDATE_CHANNEL = 2;
//    public static final int BACK_TO_MENU = 3;
    
//    public static final int TV_SOURCE = 0;
//    public static final int AV1_SOURCE = 1;
//    public static final int YPBPR1_SOURCE = 2;
//    public static final int HDMI1_SOURCE = 3;
//    public static final int HDMI2_SOURCE = 4;
//    public static final int HDMI3_SOURCE = 5;
//    public static final int VGA_SOURCE = 6;
    
    
    public static final int BURN_FLAG_ON = 0x8a;
    public static final int LOAD_DEF_HDCP = 0x8a;
    public static final int ONLINE_MUSIC_OFF = 0x8a;
    public static final int START_PIC_ON = 0x8a;
    public static final int STANDBY_FAST = 0x8a;
    public static final int SERIALPORT_ON = 0x8a;






    public static final int UART_GOTO_FACTORY_MENU = 0x8B;
    public static final int UART_GOOUT_FACTORY_MENU = 0x8C;
    public static final int UART_GOTO_BURN_MODE = 0x8D;
    public static final int UART_GOOUT_BURN_MODE = 0x8E;
    public static final int UART_GOTO_IICBusOff = 0x90;
    public static final int UART_GOOUT_IICBusOff = 0x91;
    public static final int UART_KEY_testdefault = 0x92;///3eH
    public static final int UART_KEY_outfactory = 0x93;///3bH
    public static final int UART_KEY_COCOK = 0x94;///2CH
    public static final int UART_KEY_LAN_TEST = 0x95;///2aH
    public static final int UART_KEY_LAN_COCOK_UPLAYER = 0x96;///2cH  2BH
    public static final int UART_KEY_LAN_DISPLAY_MAC = 0x97;///22H
    public static final int UART_DIRECTION_UP = 0xa0;
    public static final int UART_DIRECTION_DOWN = 0xa1;

    
    
    
    public static final String RECEIVE_TVSERVICE_MSG = "com.amlogic.tvservice.msg";
    
}
