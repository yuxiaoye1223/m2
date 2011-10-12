package com.skyworth;

import java.io.File;
import java.io.IOException;

import com.skyworth.SkyworthMenu.Menucontrol;

import android.content.Context;
import android.content.SharedPreferences;

public class MenuDataBase {

    private final String ListModeFile = "/data/skyapk/listmodefile";
    private Context mcontext;

    public MenuDataBase(Context context) {
        this.mcontext = context;
    }

    public String getListMode() {
        File file = new File(ListModeFile);
        if (!file.exists())
            return "FOLDER";
        else
            return "FILE";
    }

    public void setMusRepeatMode(String param) {
        SharedPreferences settings = mcontext.getSharedPreferences(
            "MusRepeatMode", 0);
        settings.edit().putString("MusRepeatMode", param).commit();
    }

    public void setPicRepeatMode(String param) {
        SharedPreferences settings = mcontext.getSharedPreferences(
            "PicRepeatMode", 0);
        settings.edit().putString("PicRepeatMode", param).commit();
    }

    public void setPicPlayMode(String param) {
        SharedPreferences settings = mcontext.getSharedPreferences(
            "PicPlayMode", 0);
        settings.edit().putString("PicPlayMode", param).commit();
    }

    public void setPicSwitchMode(String param) {
        SharedPreferences settings = mcontext.getSharedPreferences(
            "PicSwitchMode", 0);
        settings.edit().putString("PicSwitchMode", param).commit();
    }

    public void setVidRepeatMode(String param) {
        SharedPreferences settings = mcontext.getSharedPreferences(
            "VidRepeatMode", 0);
        settings.edit().putString("VidRepeatMode", param).commit();
    }

    
    public void setFullScreenMode(String param ) {
        SharedPreferences settings = mcontext.getSharedPreferences(
            "FullScreenMode", 0);
        settings.edit().putString("FullScreenMode", param).commit();
    }
    
    public void setLocalScreenMode(String param ) {
        SharedPreferences settings = mcontext.getSharedPreferences(
            "LocalScreenMode", 0);
        settings.edit().putString("LocalScreenMode", param).commit();
    }
    
    public void setRemoteScreenMode(String param ) {
        SharedPreferences settings = mcontext.getSharedPreferences(
            "RemoteScreenMode", 0);
        settings.edit().putString("RemoteScreenMode", param).commit();
    }
    
    public void saveTvOtherParam(String MenuItemName, String entrance) {

        if (entrance.equals("SysSetupMenu")) {
            if (MenuItemName.equals("shortcut_setup_sys_filelist_mode_folder")) {
                File file = new File(ListModeFile);
                if (file.exists())
                    file.delete();
            } else if (MenuItemName.equals("shortcut_setup_sys_filelist_mode_file")) {
                File file = new File(ListModeFile);
                if (!file.exists())
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        } else if (MenuItemName.contains("shortcut_common_playmode_")) {
            if (entrance.equals("Local") || entrance.equals("Xunlei")
                || entrance.equals("Voole")) {
                String tmp_val = null;
                if (MenuItemName.equals("shortcut_common_playmode_single"))
                    tmp_val = "SINGLE";
                else if (MenuItemName.equals("shortcut_common_playmode_folder"))
                    tmp_val = "FOLDER";
                else if (MenuItemName.equals("shortcut_common_playmode_rand"))
                    tmp_val = "RANDOM";
                if (tmp_val != null)
                    setVidRepeatMode(tmp_val);
            } else if (entrance.equals("Music") || entrance.equals("MusT")
                || entrance.equals("MusPT")) {
                String tmp_val = null;
                if (MenuItemName.equals("shortcut_common_playmode_single"))
                    tmp_val = "SINGLE";
                else if (MenuItemName.equals("shortcut_common_playmode_folder"))
                    tmp_val = "FOLDER";
                else if (MenuItemName.equals("shortcut_common_playmode_rand"))
                    tmp_val = "RANDOM";
                if (tmp_val != null)
                    setMusRepeatMode(tmp_val);
            } else if (entrance.equals("MusPic")) {
                if (Menucontrol.MediaType.equals("music")) {
                    String tmp_val = null;
                    if (MenuItemName.equals("shortcut_common_playmode_single"))
                        tmp_val = "SINGLE";
                    else if (MenuItemName
                        .equals("shortcut_common_playmode_folder"))
                        tmp_val = "FOLDER";
                    else if (MenuItemName
                        .equals("shortcut_common_playmode_rand"))
                        tmp_val = "RANDOM";
                    if (tmp_val != null)
                        setMusRepeatMode(tmp_val);
                } else {
                    String tmp_val = null;
                    if (MenuItemName.equals("shortcut_common_playmode_normal"))
                        tmp_val = "ORDER";
                    else if (MenuItemName
                        .equals("shortcut_common_playmode_folder"))
                        tmp_val = "FOLDER";
                    else if (MenuItemName
                        .equals("shortcut_common_playmode_rand"))
                        tmp_val = "RANDOM";
                    if (tmp_val != null)
                        setPicRepeatMode(tmp_val);
                }
            } else {
                String tmp_val = null;
                if (MenuItemName.equals("shortcut_common_playmode_normal"))
                    tmp_val = "ORDER";
                else if (MenuItemName.equals("shortcut_common_playmode_folder"))
                    tmp_val = "FOLDER";
                else if (MenuItemName.equals("shortcut_common_playmode_rand"))
                    tmp_val = "RANDOM";
                if (tmp_val != null)
                    setPicRepeatMode(tmp_val);
            }
        } else if (MenuItemName.contains("shortcut_picture_switch_time_")) {
            // only pic
            String tmp_val = null;
            if (MenuItemName.equals("shortcut_picture_switch_time_3s"))
                tmp_val = "3S";
            else if (MenuItemName.equals("shortcut_picture_switch_time_5s"))
                tmp_val = "5S";
            else if (MenuItemName.equals("shortcut_picture_switch_time_10s"))
                tmp_val = "10S";
            else if (MenuItemName.equals("shortcut_picture_switch_time_hand"))
                tmp_val = "HAND";
            if (tmp_val != null)
                setPicPlayMode(tmp_val);
        } else if (MenuItemName.contains("shortcut_picture_switch_mode_")) {
            // only pic
            String tmp_val = null;
            if (MenuItemName.equals("shortcut_picture_switch_mode_1"))
                tmp_val = "Normal";
            else if (MenuItemName.equals("shortcut_picture_switch_mode_2"))
                tmp_val = "Random";
            if (tmp_val != null)
                setPicSwitchMode(tmp_val);
        }else if (MenuItemName.contains("shortcut_videochat_fullscr_")) {
            String tmp_val = null;
            if (MenuItemName.equals("shortcut_videochat_fullscr_on"))
                tmp_val = "ON";
            else if (MenuItemName.equals("shortcut_videochat_fullscr_off"))
                tmp_val = "OFF";
            if (tmp_val != null)
            	setFullScreenMode(tmp_val);
        }else if (MenuItemName.contains("shortcut_videochat_local_video_")) {
            String tmp_val = null;
            if (MenuItemName.equals("shortcut_videochat_local_video_on"))
                tmp_val = "ON";
            else if (MenuItemName.equals("shortcut_videochat_local_video_off"))
                tmp_val = "OFF";
            if (tmp_val != null)
            	setLocalScreenMode(tmp_val);
        }else if (MenuItemName.contains("shortcut_videochat_remote_video_")) {
            String tmp_val = null;
            if (MenuItemName.equals("shortcut_videochat_remote_video_on"))
                tmp_val = "ON";
            else if (MenuItemName.equals("shortcut_videochat_remote_video_off"))
                tmp_val = "OFF";
            if (tmp_val != null)
            	setRemoteScreenMode(tmp_val);
        }
    }
}
