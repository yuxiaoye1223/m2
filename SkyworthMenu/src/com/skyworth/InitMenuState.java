package com.skyworth;

import com.skyworth.SkyworthMenu.GlobalConst;

public class InitMenuState {

    public String GetInitType(String type) {
        if ((type.equals(GlobalConst.ENTRANCE_TYPE_ANALOG_TV))
            || (type.equals(GlobalConst.ENTRANCE_TYPE_AV))
            || (type.equals(GlobalConst.ENTRANCE_TYPE_YUV))
            || (type.equals(GlobalConst.ENTRANCE_TYPE_HDMI))
            || (type.equals(GlobalConst.ENTRANCE_TYPE_VGA))) {
            return "tv_set";
        } else if (type.equals("Music")) {
            return "music";
        } else if (type.equals("Picture")) {
            return "picture";
        } else if (type.equals("Txt")) {
            return "txt";
        } else if (type.equals("MusPic")) {
            return "music";
        } else if (type.equals("MusT")) {
            return "music";
        } else if (type.equals("PicT")) {
            return "picture";
        } else if (type.equals("MusPT")) {
            return "music";
        } else
            return "";
    }

}
