package com.skyworth.SkyworthMenu;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsoluteLayout;

import com.amlogic.tvjni.Itvservice;
import com.skyworth.DynamicBoxData;
import com.skyworth.InitMenuState;
import com.skyworth.MenuDataBase;
import com.skyworth.MenuUIOp;
import com.skyworth.Listener.MenuCallbackListener;
import com.skyworth.Listener.SkyworthMenuListener;
import com.skyworth.View.MenuGroup1;
import com.skyworth.XmlParse.SCMenuItem;
import com.skyworth.XmlParse.SCMenuManager;
import com.skyworth.XmlParse.StringItem;
import com.skyworth.XmlParse.StringManager;
import com.skyworth.XmlParse.TreeNode;
import com.skyworth.control.tvsetting;

@SuppressWarnings("deprecation")
public class Menucontrol extends AbsoluteLayout implements SkyworthMenuListener {
    private String entrance;
    private ArrayList<String> SelectContext, SelectContextID;
    private static SourceData SourceData;
    private InitMenuState Initmenustate;
    private List<MenuCallbackListener> MenulistenerList;
    public static ArrayList<String> InitState;
    public static List<StringItem> XMLStringItems;
    private DynamicBoxData DynamicData = null;
    public static String MediaType = null;
    public MenuUIOp menuUIOp = null;
    private int MenuL1FocusIndex = 0;
    private int MenuFocusID = 0;
    private MenuDataBase mdb = null;
    private CheckedStore checkedstore= new CheckedStore();
    private boolean audioMutePanel = false;
    public int fontsize = -1;
    private boolean online = false;
    
    public boolean isConnectSerDone = false;
    public Menucontrol(Context context,AttributeSet attrs,String enter,ArrayList<String> initstate) {
        super(context, attrs);
        entrance = enter;
        InitState = initstate;
        mdb = new MenuDataBase(context);
        tvsetting.setEntrance(entrance);
        //tvsetting.setVGAMessageHandler();
        //tvsetting.setSignalDetectHandler();
        SearchDrawable.InitSearchDrawable(context);
        InitData();
        setMenuData();
        // showMenu();
    }

    // ////////////////////////////////////////////////////////////////////////
    // PicSoundDisplayModeSource
    public void SelectFrameShortCut(String item) {
    	
		if (item.equals("shortcut_setup_video_display_mode_")) {
			if((entrance.equals(GlobalConst.ENTRANCE_TYPE_ANALOG_TV))
                    || (entrance.equals(GlobalConst.ENTRANCE_TYPE_AV))
                    || (entrance.equals(GlobalConst.ENTRANCE_TYPE_YUV))
                    || (entrance.equals(GlobalConst.ENTRANCE_TYPE_HDMI))
                    || (entrance.equals(GlobalConst.ENTRANCE_TYPE_VGA))) {
				if (menuUIOp != null)
        		if(menuUIOp.nosignalflag)
        			return ;
	        	if(tvsetting.GetSigTransFormat()==0
	        			&&(tvsetting.Get3DStatus()==0||tvsetting.Get3DStatus()==1)) 
	                ;
	        	else
	        		return ;
        	}
        	else
        	{
        		if(tvsetting.Get3DStatus()!=0)
        			return ;
        	}
		}
    		
        if (Menucontrol.this.getVisibility() == View.VISIBLE
            && menuUIOp.getMGInstance().getAbsoluteFoucsItem().equals(item)
            && menuUIOp.getSFInstance().getFocusFlag()) {
            menuUIOp.getSFInstance().onKeyDown(KeyEvent.KEYCODE_DPAD_DOWN,
                new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_DOWN));
        } else {

            BackMenuHandle();
            menuUIOp.HideSelectFrame();

            postUpdateShortCutMassage(item);
        }
    }

    public void Select3DModeShortCut(String item) {
        String focusSubItem = "shortcut_video_3d_mode_";
        if(CheckMenuItemStatus("shortcut_video_3d_setup_", 0))
        	return ;
        if (Menucontrol.this.getVisibility() == View.VISIBLE
            && menuUIOp.getMGInstance().getAbsoluteFoucsItem()
                .equals(focusSubItem)
            && menuUIOp.getSFInstance().getFocusFlag()) {
            menuUIOp.getSFInstance().onKeyDown(KeyEvent.KEYCODE_DPAD_DOWN,
                new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_DOWN));
        } else {
            if (GetMenuShowState() != GlobalConst.MENU_SHOW_STATE_SETUP3D) {

                BackMenuHandle();

                int absoluteID = menuUIOp.getMGInstance().GetFocusID(item);
                if (absoluteID < 0)
                    return;
                menuUIOp.RsfSmg();
                menuUIOp.getMGInstance().setFocusID(absoluteID);
                CheckedMenuHandle(KeyEvent.KEYCODE_ENTER, menuUIOp
                    .getMGInstance().getRelativeFoucsID(), item);
            }
            menuUIOp.HideSelectFrame();
            postUpdateShortCutMassage(focusSubItem);
        }
    }

    public void ShowTvManualSearchDialog(boolean direction) {
        String item = "shortcut_program_setup_";
        String focusSubItem = "shortcut_program_manual_search_";
        int absoluteID;

        if (menuUIOp.isTunerOPDialogShow())
            return;

        BackMenuHandle();
        hideAllSubMenu();

        absoluteID = menuUIOp.getMGInstance().GetFocusID(item);
        if (absoluteID < 0)
            return;
        menuUIOp.getMGInstance().setFocusID(absoluteID);
        MenuL1FocusIndex = menuUIOp.getMGInstance().getAbsoluteFoucsID();
        SetMenuShowState("program");
        updateAll();

        absoluteID = menuUIOp.getMGInstance().GetFocusID(focusSubItem);
        if (absoluteID < 0)
            return;
        menuUIOp.getMGInstance().setFocusID(absoluteID);
        MenuFocusID = menuUIOp.getMGInstance().getRelativeFoucsID();
        menuUIOp.showTunerOperateDialog(focusSubItem);

        menuUIOp.enterTvManualSearch(direction);
    }

    public void ShowVolumeBarShortCut() {

        BackMenuHandle();

        // int
        // absoluteID=SourceData.GetAbsoluteIDFromXML("shortcut_common_vol_");
        int absoluteID = menuUIOp.getMGInstance().GetFocusID(
            "shortcut_common_vol_");
        if (absoluteID < 0)
            return;
        menuUIOp.RsfSmg();
        // the next two lines must not chang order.
        menuUIOp.getMGInstance().setFocusID(absoluteID);
        if (Menucontrol.this.getVisibility() == View.INVISIBLE)
            Menucontrol.this.setVisibility(View.VISIBLE);
        ShowProgressBar("shortcut_common_vol_");
    }

    public void handleSpecialKey(int key) {
    	BackMenuHandle();
        int focus = menuUIOp.getMGInstance().HandleSpecialKeyID(key);
        if (focus != -1) {
            menuUIOp.RpbvSmg();
            menuUIOp.RsfSmg();
            menuUIOp.getMGInstance().setFocusID(focus);
            menuUIOp.getMGInstance().myinvalidate();
            menuUIOp.getMGInstance().requestFocus();
            menuUIOp.getMGInstance().onKeyDown(KeyEvent.KEYCODE_ENTER,
                new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
            if (Menucontrol.this.getVisibility() == View.INVISIBLE) {
                Menucontrol.this.setVisibility(View.VISIBLE);
            }
        }
    }

    // ////////////////////////////////////////////////////////////////////////

    public void setOnlineFlag(boolean flag){
    	online = flag;
    }
    
    public void setMenuCallbackListener(MenuCallbackListener listener) {
        // Menulistener = listener;
        addListener(listener);
    }

    public void cleanListener() {
        MenulistenerList = null;
    }

    public void addListener(MenuCallbackListener listener) {
        if (MenulistenerList != null)
            MenulistenerList.add(listener);
        else {
            MenulistenerList = new ArrayList<MenuCallbackListener>();
            MenulistenerList.add(listener);
        }
    }

    public String getPlayType() {
        return MediaType;
    }

    // //////for get tv other params
    // music
    public String getMusRepeatMode() {
        SharedPreferences settings = this.getContext().getSharedPreferences(
            "MusRepeatMode", 0);
        return settings.getString("MusRepeatMode", "FOLDER");
    }

    // pic
    public String getPicRepeatMode() {
        SharedPreferences settings = this.getContext().getSharedPreferences(
            "PicRepeatMode", 0);
        return settings.getString("PicRepeatMode", "FOLDER");
    }

    public void setPicPlayMode(String param) {
        SharedPreferences settings = this.getContext().getSharedPreferences(
            "PicPlayMode", 0);
        settings.edit().putString("PicPlayMode", param).commit();
    }

    public String getPicPlayMode() {
        SharedPreferences settings = this.getContext().getSharedPreferences(
            "PicPlayMode", 0);
        return settings.getString("PicPlayMode", "3S");
    }

    public String getPicSwitchMode() {
        SharedPreferences settings = this.getContext().getSharedPreferences(
            "PicSwitchMode", 0);
        return settings.getString("PicSwitchMode", "Random");
    }

    // video
    public String getVidRepeatMode() {
        SharedPreferences settings = this.getContext().getSharedPreferences(
            "VidRepeatMode", 0);
        return settings.getString("VidRepeatMode", "FOLDER");
    }
    
    //coocachat 0810 >>>>>>
    public String getFullScreenMode() {
        SharedPreferences settings = this.getContext().getSharedPreferences(
            "FullScreenMode", 0);
        return settings.getString("FullScreenMode", "OFF");
    }
    
    public void setFullScreenMode(String param ) {
        SharedPreferences settings = this.getContext().getSharedPreferences(
            "FullScreenMode", 0);
        settings.edit().putString("FullScreenMode", param).commit();
    }
    
    public String getLocalScreenMode() {
        SharedPreferences settings = this.getContext().getSharedPreferences(
            "LocalScreenMode", 0);
        return settings.getString("LocalScreenMode", "OFF");
    }
    
    public void setLocalScreenMode(String param ) {
        SharedPreferences settings = this.getContext().getSharedPreferences(
            "LocalScreenMode", 0);
        settings.edit().putString("LocalScreenMode", param).commit();
    }
    
    public String getRemoteScreenMode() {
        SharedPreferences settings = this.getContext().getSharedPreferences(
            "RemoteScreenMode", 0);
        return settings.getString("RemoteScreenMode", "OFF");
    }
    
    public void setRemoteScreenMode(String param ) {
        SharedPreferences settings = this.getContext().getSharedPreferences(
            "RemoteScreenMode", 0);
        settings.edit().putString("RemoteScreenMode", param).commit();
    }  
    //coocachat 0810 <<<<<<
    
    // //////end for get tv other params
    public boolean selFrameFlag=false;
    public void CheckedMenuHandle(int Key, int FocusID, String MenuItemName) {

        String UIType = SourceData.GetUITypeFromXML(MenuItemName);
        if (Key == KeyEvent.KEYCODE_DPAD_UP) {
            if (UIType.equals("SelectBox")) {
                Key = KeyEvent.KEYCODE_ENTER;
            } else if (UIType.equals("DynamicBox")) {
                if (MenuItemName.equals("shortcut_program_no_"))
                    Key = KeyEvent.KEYCODE_ENTER;
            } else
                return;
        }

        MenuFocusID = FocusID;
        if (Key == KeyEvent.KEYCODE_DPAD_LEFT
            || Key == KeyEvent.KEYCODE_DPAD_RIGHT) {
            menuUIOp.HideSelectFrame();
            if (UIType.equals("SelectBox") || UIType.equals("SelectBoxCommand")
                || UIType.equals("DynamicBox"))
                postUpdateSFMessage(FocusID, MenuItemName, UIType, 300);

        } else {
            if (Key == KeyEvent.KEYCODE_ENTER) {
                if (MenuItemName.equals("shortcut_setup_audio_equalizer_")) {
                    // menuUIOp.showBalancer(mdb.InitBalancer());
                    try {
                        if (mItvservice != null)
                            menuUIOp.showBalancer(mItvservice
                                .GetCurAudioSoundMode());
                    } catch (RemoteException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();

                    }
                } else if (MenuItemName.equals("shortcut_setup_sys_pc_set_")) {
                    menuUIOp.showVgaAdjustDialog();
                } else if (UIType.equals("StatusCommand")) {
                    HandleMenuCommand(MenuItemName);
                } else if (UIType.equals("SelectBox")
                    || UIType.equals("SelectBoxCommand")
                    || UIType.equals("DynamicBox")) {
                    if (menuUIOp.getSFInstance().getVisibility() == View.VISIBLE)
                        menuUIOp.setSFFocus();
                    else {
                        updateSF(MenuItemName, UIType);
                        if (menuUIOp.getSFInstance().getVisibility() == View.VISIBLE)
                            menuUIOp.setSFFocus();
                    }
                } else if (UIType.equals("Command")) {
                    String Id = SourceData.GetAnotherStatusID(MenuItemName);
                    if (Id != null) {
                        menuUIOp.getMGInstance().UpdataStatus(Id);
                        HandleMenuCommand(Id);
                        if(GetMenuShowState().equals("picture")){
                        	checkedstore.setCheckedIDValue("shortcut_picture_rotate_", 0);
                        }
                    } else {
                        HandleMenuCommand(MenuItemName);
                        if(GetMenuShowState().equals("picture")){
                        	if(MenuItemName.equals("shortcut_common_prev_") || MenuItemName.equals("shortcut_common_next_"))
                        		checkedstore.setCheckedIDValue("shortcut_picture_rotate_", 0);
                        }
                    }
                } else if (UIType.equals("SliderBar")) {

                    ShowProgressBar(MenuItemName);
                } else if (UIType.equals("Dialog")) {
                    if (MenuItemName.equals("shortcut_setup_sys_recovery_")) {
                        menuUIOp.showDialog();
                    } else if (MenuItemName.equals("shortcut_setup_audio_mute_panel_")) {
                        menuUIOp.showMutePanelView();
                    }
                } else if (UIType.equals("TunerOperateDialog")) {
                    if ((MenuItemName.equals("shortcut_program_manual_search_"))
                        || (MenuItemName.equals("shortcut_program_fine_"))
                        || (MenuItemName.equals("shortcut_program_auto_search_"))) {
                        menuUIOp.showTunerOperateDialog(MenuItemName);
                    }
                } else if (UIType.equals("ProgramEditDialog")) {
                    menuUIOp.showProgramEditDialog(tvsetting
                        .getCurrnetChNumber());
                } else if (UIType.equals("ChannelSetupSwitch")) {
                    MenuL1FocusIndex = menuUIOp.getMGInstance()
                        .getAbsoluteFoucsID();
                    SetMenuShowState("program");
                    menuUIOp.ShowL2Menu();
                } else if (UIType.equals("Setup3D")) {
                    MenuL1FocusIndex = menuUIOp.getMGInstance()
                        .getAbsoluteFoucsID();
                    SetMenuShowState("Setup3D");
                    
                    if((entrance.equals(GlobalConst.ENTRANCE_TYPE_ANALOG_TV))
                            || (entrance.equals(GlobalConst.ENTRANCE_TYPE_AV))
                            || (entrance.equals(GlobalConst.ENTRANCE_TYPE_YUV))
                            || (entrance.equals(GlobalConst.ENTRANCE_TYPE_VGA)))
                    {
                    	checkedstore.setCheckedIDValue("shortcut_video_3d_mode_", tvsetting.Get3DStatus());
                    }
                    else if(entrance.equals(GlobalConst.ENTRANCE_TYPE_HDMI)){
                    	if(tvsetting.GetSigTransFormat() == 0){
                    		if(!selFrameFlag)
                    		checkedstore.setCheckedIDValue("shortcut_video_3d_mode_", 0);
                    	}
                    	else
                    	{
                    		checkedstore.setCheckedIDValue("shortcut_video_3d_mode_", 1);
                    	}
                    }
                    else if(entrance.equals("Local") || entrance.equals("Xunlei")
                    		|| entrance.equals("Voole"))
                    {
                    	checkedstore.setCheckedIDValue("shortcut_video_3d_mode_", tvsetting.Get3DStatus());
                    }
                    
                    menuUIOp.ShowL2Menu();
                }
            }
        }
    }

    public void BackMenuHandle() {
        if ((GetMenuShowState() == GlobalConst.MENU_SHOW_STATE_PROGRAM)
            || (GetMenuShowState() == GlobalConst.MENU_SHOW_STATE_SETUP3D)) {
            menuUIOp.HideSelectFrame();
            SetMenuShowState(Initmenustate.GetInitType(entrance));
            menuUIOp.BackToL1Menu(MenuL1FocusIndex);
        }
    }

    String tvsource = "";
    public void RemoveSourceSwitchMessage(){
        if (handlerUpdateSF.hasMessages(3))
            handlerUpdateSF.removeMessages(3);
    }
    public void SelectFrameToMenuHandle(int checked_id, String ID) {
        if (ID.equals("__RIGHT__")) {
            menuUIOp.RsfSmg(KeyEvent.KEYCODE_DPAD_RIGHT);
        } else if (ID.equals("__LEFT__")) {
            menuUIOp.RsfSmg(KeyEvent.KEYCODE_DPAD_LEFT);
        } else if (ID.equals("__BACK__")) {
            menuUIOp.HideSelectFrame();
            menuUIOp.getMGInstance().setFocusable(true);
            menuUIOp.getMGInstance().requestFocus();
        } else if (ID.equals("shortcut_common_sync_control_music")
            || ID.equals("shortcut_common_sync_control_picture")
            || ID.equals("shortcut_common_sync_control_txt")) {
            String mediaType = null;
            String Svalue = SourceData.GetValueFromXML(ID);
            mediaType = getChinaString(XMLStringItems, "MUSIC");
            if (Svalue.equals(mediaType)) {
                SetMenuShowState("music");
                MediaType = "music";
            } else {
                mediaType = getChinaString(XMLStringItems, "PICTURE");
                if (Svalue.equals(mediaType)) {
                    SetMenuShowState("picture");
                    MediaType = "picture";
                } else {
                    mediaType = getChinaString(XMLStringItems, "TEXT");
                    if (Svalue.equals(mediaType)) {
                        SetMenuShowState("txt");
                        MediaType = "txt";
                    }
                }
            }
            menuUIOp.getMGInstance().initFocusID();
            menuUIOp.RsfSmg();
            HandleMenuCommand(ID);
        } else {
            checkedstore.setCheckedIDValue(menuUIOp.getMGInstance()
                .getAbsoluteFoucsItem(), checked_id);
            if(menuUIOp.getMGInstance().getAbsoluteFoucsItem().equals("shortcut_common_source_")
            		//&&(tvsetting.GetTvParam("shortcut_common_source_")==checked_id)
            		&&((entrance.equals(GlobalConst.ENTRANCE_TYPE_ANALOG_TV))
            	            || (entrance.equals(GlobalConst.ENTRANCE_TYPE_AV))
            	            || (entrance.equals(GlobalConst.ENTRANCE_TYPE_YUV))
            	            || (entrance.equals(GlobalConst.ENTRANCE_TYPE_HDMI))
            	            || (entrance.equals(GlobalConst.ENTRANCE_TYPE_VGA))))
            {
            	//;//do nothing
            	if(tvsetting.GetTvParam("shortcut_common_source_") == checked_id)
            		;
            	else
            	{
            		Log.e("Menucontrol", "tvsetting.isSrcSwtichDone() = " + tvsetting.isSrcSwtichDone());
            		if(tvsetting.isSrcSwtichDone()){
            			HandleMenuCommand(ID);
            		}
            		//tvsource = ID;
                    //if (handlerUpdateSF.hasMessages(3))
                    //    handlerUpdateSF.removeMessages(3);
                    //handlerUpdateSF.sendEmptyMessageDelayed(3, 0);
            	}
            }
            else
            	HandleMenuCommand(ID);
            menuUIOp.RsfSmg();

            if (ID.equals("shortcut_setup_sys_six_color_colordemo")
               || ID.equals("shortcut_setup_sys_six_color_splitdemo")) {
                menuUIOp.showViiDemoView(ID);
            } else if (ID.equals("shortcut_setup_sys_dream_panel_demo")) {
                menuUIOp.showDreampanelDemo();
            } else if (ID.equals("shortcut_video_3d_mode_2d3d")||ID.equals("shortcut_video_3d_mode_lr")||ID.equals("shortcut_video_3d_mode_ud")) {
            	menuUIOp.showThreeDiGlasses();
            } 
            
            if(entrance.equals(GlobalConst.ENTRANCE_TYPE_HDMI)){
            	if(ID.equals("shortcut_video_3d_mode_off")||ID.equals("shortcut_video_3d_mode_2d3d"))
            		selFrameFlag=true;
            }
        }
    }

    public void ProgressBarToMenuHandle(int progress, boolean isChanged,
        String MenuItemName) {
        if (isChanged) {
            checkedstore.setCheckedIDValue(menuUIOp.getMGInstance()
                .getAbsoluteFoucsItem(), progress);

            HandleMenuCommand(MenuItemName, progress + "");
        } else {
            menuUIOp.RpbvSmg();
        }

    }

    private void ShowSelectFrame(int Focus, int initnum, boolean ifselect) {
        menuUIOp.showSelectFrame(Focus, initnum, ifselect, SelectContext,
            SelectContextID);
    }

    private void ShowProgressBar(String MenuItemName) {
        int val = 0;
        try {
            if (mItvservice != null)
                val = mItvservice.GetTvProgressBar(MenuItemName);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (val == -1) {
            if (checkedstore.getCheckedIDIndex(MenuItemName) < 0 && !MenuItemName.equals("shortcut_video_3d_dof_"))
                checkedstore.setCheckedIDValue(MenuItemName,GlobalConst.DEFAULT_VALUE_MENU_SHOW);
            val = checkedstore.getCheckedIDValue(MenuItemName);
        }
        menuUIOp.showProgressBar(MenuItemName, val);
    }

    public void TunerOperateToMenuHandle(boolean isAutoFinished) {
        menuUIOp.removeTunerOperateDialog();
        if (isAutoFinished) {
            BackMenuHandle();
            this.setVisibility(View.INVISIBLE);
            this.set_focus(false);
        }
    }

    public void ProgramEditToMenuHandle(int handleMode, int sourceCh,
        int targetCh) {
        switch (handleMode) {
            case GlobalConst.EXCHANGE_CHANNEL:
                tvsetting.exchangeChannelInfo(sourceCh, targetCh);
                break;

            case GlobalConst.UPDATE_CHANNEL:
                tvsetting.setTunerChannel(sourceCh);
                break;

            case GlobalConst.BACK_TO_MENU:
                menuUIOp.removeProgramEditDialog();
                break;

            default:
                break;
        }
    }

    public void ViiDemoViewToDemoHandle(int demoMode) {
        tvsetting.setViiColorDemo(demoMode);
    }

    public void ViiDemoViewToMenuHandle() {
        if (menuUIOp.isViiDemoViewShow()) {
//            tvsetting.setViiColorDemo(0);
            tvsetting.setBaseColor(1);  //ColorManageMode.OPTIMIZE
            menuUIOp.removeViiDemoView();
        }
    }

    public void DreampanelDemoToMenuHandle() {
        if (menuUIOp.isDreampanelDemoShow()) {
            // must clear demo flag
            tvsetting.setDreampanelDemo(false);
            menuUIOp.removeDreampanelDemo();
        }
    }

    public void ThreeDiglassesToMenuHandle() {
        if (menuUIOp.isThreeDiglassesShow()) {
            menuUIOp.hideThreeDiGlasses();
        }
    }
    
    public void setCheckedIDValue(String str_id, int checked_id) {
        checkedstore.setCheckedIDValue(str_id, checked_id);
    }
    
    public int getCheckedIDValue(String str_id) {
        return checkedstore.getCheckedIDValue(str_id);
    }
    
    public void VideoSetup3D(String MenuItem) {
    	if(MenuItem.equals("shortcut_video_3d_mode_off"))
    		UiSet2DScrMode();
        tvsetting.SettingSetup3DControl(MenuItem);
    }
    
    public void UiSet2DScrMode() {
       	int val = tvsetting.GetTvParam("shortcut_setup_video_display_mode_");
		if(val != -1)
			tvsetting.UiSetScrMode(val);
    }
    
    public void SetFullSrceen() {
        try {
            if (mItvservice != null)
                mItvservice.UartSend("shortcut_setup_video_display_mode_169", "");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void ShowTvSearchNavigateDialog() {
        menuUIOp.showTvSearchNavigateDialog();
    }

    public void NavigateDialogToMenuHandle(boolean isToSearch) {
        menuUIOp.removeTvSearchNavigateDialog();
        if (isToSearch) {
            menuUIOp.showTunerOperateDialog("shortcut_program_auto_search_");
        } else {
            this.setVisibility(View.INVISIBLE);
            this.set_focus(false);
        }
    }

    public void VgaAdjustDialogHandle(int id, int value) {
        String str = Integer.toString(value);
        switch (id) {
            case 0:
                HandleMenuCommand("shortcut_setup_sys_pc_set_auto_");
                break;

            case 1:
                HandleMenuCommand("shortcut_setup_sys_pc_set_hor_", str);
                break;

            case 2:
                HandleMenuCommand("shortcut_setup_sys_pc_set_ver_", str);
                break;

            case 3:
                HandleMenuCommand("shortcut_setup_sys_pc_set_freq_", str);
                break;

            case 4:
                HandleMenuCommand("shortcut_setup_sys_pc_set_pha_", str);
                break;

            default:
                break;
        }
    }

    public void VgaAdjustDialogToMenuHandle() {
        menuUIOp.removeVgaAdjustDialog();
    }
    
    public void handleCallbackMenuState(String... Menu){
    	for (int i = 0; i < MenulistenerList.size(); i++) {
    		//Log.d("Haha", "The entrance is:" + entrance + "  index is:" + i );
    		if(entrance.equals("SysSetupMenu"))
    			MenulistenerList.get(i).CallbackMenuState(Menu);
    	}
    }
    
    private void HandleMenuCommand(String... Menu) {
        // this is better that SettingTVControl function is done before
        // CallbackMenuState function.
        if ((entrance.equals(GlobalConst.ENTRANCE_TYPE_ANALOG_TV))
            || (entrance.equals(GlobalConst.ENTRANCE_TYPE_AV))
            || (entrance.equals(GlobalConst.ENTRANCE_TYPE_YUV))
            || (entrance.equals(GlobalConst.ENTRANCE_TYPE_HDMI))
            || (entrance.equals(GlobalConst.ENTRANCE_TYPE_VGA))) {
            if (GetMenuShowState() == GlobalConst.MENU_SHOW_STATE_TV_SET) {
                tvsetting.SettingTVControl(Menu);
            } else if (GetMenuShowState() == GlobalConst.MENU_SHOW_STATE_PROGRAM) {
            	tvsetting.SettingProgramControl(Menu);
            } else if (GetMenuShowState() == GlobalConst.MENU_SHOW_STATE_SETUP3D) {
                
	        	if(Menu[0].equals("shortcut_video_3d_mode_auto")){
	        		if(tvsetting.Get3DStatus()==1){
	                	checkedstore.setCheckedIDValue("shortcut_video_3d_3d2d_", 0);
	        			//return ;
	        		}
	        	} else if(Menu[0].equals("shortcut_video_3d_mode_2d3d")||Menu[0].equals("shortcut_video_3d_mode_lr")
	            		||Menu[0].equals("shortcut_video_3d_mode_ud")) {
	        		checkedstore.setCheckedIDValue("shortcut_video_3d_3d2d_", 0);
	        		checkedstore.setCheckedIDValue("shortcut_video_3d_lr_switch_", 0);
	        	} else if(Menu[0].equals("shortcut_video_3d_3d2d_off")){
	        		checkedstore.setCheckedIDValue("shortcut_video_3d_lr_switch_", 0);
	        	}
            	
	        	tvsetting.SettingSetup3DControl(Menu);
            }
        } else {
            if (GetMenuShowState() == GlobalConst.MENU_SHOW_STATE_SETUP3D){
	        	if(Menu[0].equals("shortcut_video_3d_mode_auto")){
	        		if(tvsetting.Get3DStatus()==1){
	                	checkedstore.setCheckedIDValue("shortcut_video_3d_3d2d_", 0);
	        			//return ;
	        		}
	        	} else if(Menu[0].equals("shortcut_video_3d_mode_2d3d")||Menu[0].equals("shortcut_video_3d_mode_lr")
	            		||Menu[0].equals("shortcut_video_3d_mode_ud")) {
	        		if(tvsetting.GetTvParam("shortcut_setup_video_display_mode_") == 4)
	        			tvsetting.SetViewMode(1);
	        		else
	        			tvsetting.SetViewMode(2);
	        		checkedstore.setCheckedIDValue("shortcut_video_3d_3d2d_", 0);
	        		checkedstore.setCheckedIDValue("shortcut_video_3d_lr_switch_", 0);
	        	} else if(Menu[0].equals("shortcut_video_3d_3d2d_off")){
	        		checkedstore.setCheckedIDValue("shortcut_video_3d_lr_switch_", 0);
	        	} else if(Menu[0].equals("shortcut_video_3d_mode_off")){
	        		UiSet2DScrMode();
	        	}
	        	
	        	tvsetting.SettingSetup3DControl(Menu);
            }
            else {
            	tvsetting.SettingTVControl(Menu);
                mdb.saveTvOtherParam(Menu[0], entrance);
            }

        }

        for (int i = 0; i < MenulistenerList.size(); i++) {
            if (MediaType.equals("music"))
                MenulistenerList.get(i).CallbackMenuState("Audio", Menu[0]);
            else if (MediaType.equals("picture"))
                MenulistenerList.get(i).CallbackMenuState("Picture", Menu[0]);
            else if (MediaType.equals("txt"))
                MenulistenerList.get(i).CallbackMenuState("Text", Menu[0]);
            else
                MenulistenerList.get(i).CallbackMenuState(Menu);

            if (Menu[0].contains("shortcut_common_source")) {
                MenulistenerList.get(i).CallbackMenuState("BackTo3D");
                if ((!entrance.equals(GlobalConst.ENTRANCE_TYPE_ANALOG_TV))
                    && (!entrance.equals(GlobalConst.ENTRANCE_TYPE_AV))
                    && (!entrance.equals(GlobalConst.ENTRANCE_TYPE_YUV))
                    && (!entrance.equals(GlobalConst.ENTRANCE_TYPE_HDMI))
                    && (!entrance.equals(GlobalConst.ENTRANCE_TYPE_VGA))) {
                    Bundle tvbundle = new Bundle();
                    if (Menu[0].equals("shortcut_common_source_tv")) {
                        tvbundle.putString("mode", "TV");
                    } else if (Menu[0].equals("shortcut_common_source_av1")) {
                        tvbundle.putString("mode", "AV1");
                    } else if (Menu[0].equals("shortcut_common_source_yuv1")) {
                        tvbundle.putString("mode", "YUV1");
                    } else if (Menu[0].equals("shortcut_common_source_hdmi1")) {
                        tvbundle.putString("mode", "HDMI1");
                    } else if (Menu[0].equals("shortcut_common_source_hdmi2")) {
                        tvbundle.putString("mode", "HDMI2");
                    } else if (Menu[0].equals("shortcut_common_source_hdmi3")) {
                        tvbundle.putString("mode", "HDMI3");
                    } else if (Menu[0].equals("shortcut_common_source_vga")) {
                        tvbundle.putString("mode", "VGA");
                    }
                    
                    Intent intent = new Intent("android.intent.action.testMenu");
                    intent.putExtras(tvbundle);
                    this.getContext().startActivity(intent);
                    return ;
                }
            }
        }

    }

    // .....................start uartservice process.........................
    private Intent mIntent = new Intent("com.amlogic.tvjni.TvService");
    private Itvservice mItvservice = null;
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.d("uart", "......Menucontrol onServiceConnected start......\n");
            mItvservice = Itvservice.Stub.asInterface(service);
            isConnectSerDone = true;
            tvsetting.setItvService(mItvservice);
            Log.d("uart", "......Menucontrol onServiceConnected end......\n");
            showMenu();
        }

        public void onServiceDisconnected(ComponentName className) {
            Log.d("uart", "......Menucontrol onServiceDisconnected .........\n");
            mItvservice = null;
            isConnectSerDone = false;
        }
    };

    public Itvservice GetIuartService() {
        return mItvservice;
    }

    boolean unbindFlag = true;

    public void CallMenucontrolunbindservice() {
        if (unbindFlag) {
            unbindFlag = false;
            this.getContext().unbindService(mConnection);
            Log.d("uart",
                "........ call Menucontrol unbindService(mConnection) .........\n");
        }
    }

    public void BalancerKeyListener(boolean doit, int item, int val) {

        if (doit) {
            if (mItvservice != null)
                try {
                    mItvservice.SaveCustomEQGain(item
                        + GlobalConst.LOAD_AUDIO_EQGAIN_POS, val
                        - GlobalConst.AUDIO_EQGAIN_RANGE / 2);
                } catch (RemoteException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            if (item == (GlobalConst.LOAD_AUDIO_BASS_POS - GlobalConst.LOAD_AUDIO_EQGAIN_POS)) {
                if (mItvservice != null)
                    try {
                        mItvservice.SetCurAudioBassVolume(val);
                    } catch (RemoteException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
            } else if (item == (GlobalConst.LOAD_AUDIO_TREBLE_POS - GlobalConst.LOAD_AUDIO_EQGAIN_POS)) {
                if (mItvservice != null)
                    try {
                        mItvservice.SetCurAudioTrebleVolume(val);
                    } catch (RemoteException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
            }
            HandleMenuCommand("shortcut_setup_audio_equalizer_");
        } else {
            menuUIOp.RbalancerSmg();
        }

    }

    public void BalancerKeyListener(int soundmode) {

        // Initmenustate.BalancerKeyProcess(soundmode);
        if (mItvservice != null)
            try {
                mItvservice.SaveCurAudioSoundMode(soundmode);
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        HandleMenuCommand("shortcut_setup_audio_equalizer_");
    }

    public void DialogManage(boolean doit) {
        // if (doit) {
        // new Thread(new Runnable() {
        // public void run() {
        // //Initmenustate.DialogSetDefault();
        // }
        // }).start();
        //
        // }
        if (doit) {
            if (mItvservice != null)
                try {
                    mItvservice.UartSend("shortcut_setup_sys_recovery_", null);
                } catch (RemoteException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
//            tvsetting.uartsend("shortcut_setup_sys_sleep_time_off","");
            menuUIOp.RdialogSmg();
            String[] menuStrings = {"shortcut_setup_sys_recovery_"};
            handleCallbackMenuState(menuStrings);
            String str_lan = Menucontrol.this.getResources().getConfiguration().locale.getLanguage();
            if (str_lan.equals("en")) {
                tvsetting.SettingTVControl("shortcut_setup_sys_language_zh");
            }
        } else {
            menuUIOp.RdialogSmg();
        }
    }

    public void MutePanelViewToMenuHandle(boolean isMute) {
        audioMutePanel = isMute;
        menuUIOp.removeMutePanelView();
        
        if (audioMutePanel == true) {
            this.setVisibility(View.INVISIBLE);
            this.set_focus(false);
            HandleMenuCommand("shortcut_setup_audio_mute_panel_");
        }
    }
    
    public boolean isAudioMutePanel() {
        return audioMutePanel;
    }
    
    public void setAudioMutePanel(boolean isMute) {
        audioMutePanel = isMute;
    }
    
    // ........................end uartservice
    // process.............................

    private void SetMenuShowState(String state) {
        menuUIOp.getMGInstance().SetShowState(state);
    }

    private String GetMenuShowState() {
        return menuUIOp.getMGInstance().GetShowState();
    }

    public void updatamenu(Map<Integer, String> map) {
        // MG.UpdataMenuData(map);
        menuUIOp.getMGInstance().UpdataMenuData(map);

    }

    // public void updatamenu(String type,Map<Integer,String> map)
    // {
    // // MG.UpdataMenuData(map);
    // menuUIOp.getMGInstance().UpdataMenuData(type,map);
    //
    // }
    
    public boolean CheckVolBarExist(){
    	return menuUIOp.GetVolProgressBarExist();
    }

    public void setVisibility(int visibility) {
    	Log.d("Haha","setVisibility......" + visibility);
        /* force to update UI */
        if (visibility == View.VISIBLE) {
            if (null != menuUIOp) {
                menuUIOp.menuuiopUpdateUI();
            }
        }

        if (visibility == View.INVISIBLE){
            hideAllSubMenu();
        }
        super.setVisibility(visibility);
        if (visibility == View.VISIBLE)
            menuUIOp.getMGInstance().showFirstSelectFrame();
    }

    public void hideAllSubMenu() {
        menuUIOp.RbalancerSmg();
        menuUIOp.RdialogSmg();
        menuUIOp.RpbvSmg();
        menuUIOp.RsfSmg();
        menuUIOp.removeMutePanelView();
        menuUIOp.hideThreeDiGlasses();
        ViiDemoViewToMenuHandle();
        DreampanelDemoToMenuHandle();
        menuUIOp.removeProgramEditDialog();
        menuUIOp.removeVgaAdjustDialog();
    }

    // Joey
    public void updateAll() {
        if (null != SourceData) {
            SourceData.InitXMLData();
            SourceData.getXmlString();
        }

        if (null != menuUIOp) {
            menuUIOp.menuuiopUpdateUI(0);
        }
    }

    public void SFupdatefortony() {
    	if(menuUIOp!=null && menuUIOp.getSFInstance()!=null)
    		menuUIOp.getSFInstance().sfupdate();
    }
    
    public void updateMenuUI(int absFocusID, boolean initData) {
        if (null != SourceData && initData) {
            SourceData.InitXMLData();
            SourceData.getXmlString();
        }

        if (null != menuUIOp) {
            menuUIOp.HideSelectFrame();
            menuUIOp.menuuiopUpdateUI(absFocusID);
        }
    }
    
    public void connservice(){
        this.getContext().bindService(mIntent, mConnection, Context.BIND_AUTO_CREATE);
    }

    private void InitData() {
    	isConnectSerDone = false;

        MenulistenerList = new ArrayList<MenuCallbackListener>();
        SelectContext = new ArrayList<String>();
        SelectContextID = new ArrayList<String>();

        SourceData = new SourceData();
        Initmenustate = new InitMenuState();
        // this.getContext().startService(mIntent);
        this.getContext().bindService(mIntent, mConnection, Context.BIND_AUTO_CREATE);
        SourceData.InitXMLData();
        SourceData.getXmlString();
        
        DynamicData = new DynamicBoxData();
        menuUIOp = new MenuUIOp(this, entrance);

    }

    public boolean IsConnectSerDone(){
    	return isConnectSerDone; 
    }
    public void set_focus(Boolean b_focus) {
        if (null != menuUIOp)
            menuUIOp.setMGFocus(b_focus);
        this.setFocusable(b_focus);
    }

    // ***********************************************************************
    public void set_play_name(String name) {
        // MG.set_play_name(name);
        menuUIOp.getMGInstance().set_play_name(name);
    }

    public void set_seek_bar_info(int i_total, int i_cur_pos) {
        // MG.set_seek_bar_info(i_total, i_cur_pos);
        menuUIOp.getMGInstance().set_seek_bar_info(i_total, i_cur_pos);
    }

    public void set_play_name(String type, String name) {
        // MG.set_play_name(type,name);
        menuUIOp.getMGInstance().set_play_name(type, name);
    }

    public void set_seek_bar_info(String type, int i_total, int i_cur_pos) {
        // MG.set_seek_bar_info(type,i_total, i_cur_pos);
        menuUIOp.getMGInstance().set_seek_bar_info(type, i_total, i_cur_pos);
    }

    public void setplayerPosScale(String type, String myscale) {
        // MG.setplayerPosScale(type, myscale);
        menuUIOp.getMGInstance().setplayerPosScale(type, myscale);
    }

    public void setplayerPosScale(String myscale) {
        // MG.setplayerPosScale(type, myscale);
        menuUIOp.getMGInstance().setplayerPosScale(myscale);
    }

    public void setDynamicsubtitle(ArrayList<String> data) {
        DynamicData.setsubtitle(data);
    }

    public void setDynamicsoundtrack(ArrayList<String> data) {
        DynamicData.setsoundtrack(data);
    }

    private String getChinaString(List<StringItem> stringItems, String name) {
        String value = null;
        for (StringItem si : stringItems) {
            if (si.name.equals(name))
                value = si.value;
        }
        return value;
    }

    public static String getResXmlString(String name) {
        String value = null;
        for (StringItem si : XMLStringItems) {
            if (si.name.equals(name))
                value = si.value;
        }
        return value;
    }

    public static String getShortCutValue(String id) {
        return SourceData.GetShortCutValueFromXML(id);
    }

    // ************************************************************

    // *****************************Menu
    // Handle*************************************

    private void setMenuData() {
        SourceData.GetBarData();
        SetMenuShowState(Initmenustate.GetInitType(entrance));
        menuUIOp.getMGInstance().notifydataFinish();
    }

    private void showMenu() {
        // menuUIOp.setFirstItem(CheckMenuItemStatus("shortcut_video_3d_setup_",
        // 0));
        menuUIOp.showMenu();
    }

    public MenuGroup1 getMenuIns() {
        return menuUIOp.getMGInstance();
    }

    public boolean isHideMenu() {
        return menuUIOp.isHideMenu();
    }

    // *****************************Menu Handle
    // finish*****************************

    class SourceData {
        private TreeNode<SCMenuItem> root;

        public void InitXMLData() {
            Context friendContext;
            try {
                String str_lan = Menucontrol.this.getResources()
                    .getConfiguration().locale.getLanguage();// Joey 20110609
                friendContext = Menucontrol.this.getContext()
                    .createPackageContext("com.amlogic.ui.res",
                        Context.CONTEXT_IGNORE_SECURITY);
                AssetManager assets = friendContext.getAssets();
                try {
                    InputStream im;
                    if (str_lan.equals("en"))
                        im = assets.open("shortcutmenutree_en.xml");
                    else
                        im = assets.open("shortcutmenutree.xml");
                    // InputStream im=assets.open("shortcutmenutree.xml");
                    SCMenuManager manager = new SCMenuManager(im, entrance);
                    root = manager.getMenuRoot();
                    im.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (NameNotFoundException e1) {
                e1.printStackTrace();
            }
        }

        public void getXmlString() {
            try {
                String str_lan = Menucontrol.this.getResources()
                    .getConfiguration().locale.getLanguage();// Joey 20110609
                Context friendContext = Menucontrol.this.getContext()
                    .createPackageContext("com.amlogic.ui.res",
                        Context.CONTEXT_IGNORE_SECURITY);
                AssetManager assets = friendContext.getAssets();
                try {
                    InputStream is;// = assets.open("strings.xml");
                    if (str_lan.equals("en"))
                        is = assets.open("strings_en.xml");
                    else
                        is = assets.open("strings.xml");
                    // InputStream is = assets.open("strings.xml");
                    StringManager smanager = new StringManager(is);
                    XMLStringItems = smanager.getStringItems();
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (NameNotFoundException e) {
                e.printStackTrace();
            }
        }

        private void GetBarData() {

        	if(root == null){
        		Log.e("Menucontrol", "GetBarData error..........root == null");
        		InitXMLData();
        		//return ;
        	}
        	
            for (int i = 0; i < root.getChildren().size(); i++) {
                TreeNode<SCMenuItem> node = root.getChildren().get(i);
                if (node.getData().id.equals("TxtPlayControl"))
                    MediaType = "txt";
                else if (node.getData().id.equals("PicturePlayControl"))
                    MediaType = "picture";
                else if (node.getData().id.equals("MusicPlayControl"))
                    MediaType = "music";

                else if (node.getData().id.equals("TVControl"))
                    MediaType = "tv_set";
                else if (node.getData().id.equals("ProgramControl"))
                    MediaType = "program";
                else if (node.getData().id.equals("Setup3DControl"))
                    MediaType = "Setup3D";
                else
                    MediaType = "defult";

                for (int j = 0; j < node.getChildren().size(); j++) {

                    String UIType = node.getChildren().get(j).getData().uiType;

                    if (!(UIType.equals("StatusCommand"))) {
                        menuUIOp.getMGInstance().AddMenuItem(
                            new String(node.getChildren().get(j).getData().id),
                            MediaType);
                    } else if (node.getChildren().get(j).getChildren().size() != 0) {
                        if (Menucontrol.InitState != null)
                            for (int ii = 0; ii < Menucontrol.InitState.size(); ii++) {
                                for (int jj = 0; jj < node.getChildren().get(j)
                                    .getChildren().size(); jj++) {
                                    if (node.getChildren().get(j).getChildren()
                                        .get(jj).getData().id
                                        .equals(Menucontrol.InitState.get(ii))) {
                                        menuUIOp.getMGInstance().AddMenuItem(
                                            new String(node.getChildren()
                                                .get(j).getChildren().get(jj)
                                                .getData().id), MediaType);
                                        ii = Menucontrol.InitState.size();
                                        break;
                                    }
                                }
                            }
                    }
                }
            }
            if (entrance.equals("MusPT") || entrance.equals("MusPic")
                || entrance.equals("MusT"))
                MediaType = "music";
            else if (entrance.equals("PicT"))
                MediaType = "picture";

        }

        public String GetShortCutValueFromXML(String ID) {
            String Svalue = null;
            for (int i = 0; i < root.getChildren().size(); i++) {
                TreeNode<SCMenuItem> node = root.getChildren().get(i);
                for (int j = 0; j < node.getChildren().size(); j++) {

                    if (node.getChildren().get(j).getData().id.equals(ID)) {
                        Svalue = node.getChildren().get(j).getData().value;
                        return Svalue;
                    }

                    for (int jj = 0; jj < node.getChildren().get(j)
                        .getChildren().size(); jj++) {
                        if (node.getChildren().get(j).getChildren().get(jj)
                            .getData().id.equals(ID)) {

                            Svalue = node.getChildren().get(j).getChildren()
                                .get(jj).getData().value;

                            return Svalue;
                        }
                    }
                }
            }
            return Svalue;
        }

        public String GetValueFromXML(String ID) {
            String Svalue = null;
            for (int i = 0; i < root.getChildren().size(); i++) {
                TreeNode<SCMenuItem> node = root.getChildren().get(i);
                for (int j = 0; j < node.getChildren().size(); j++) {

                    for (int jj = 0; jj < node.getChildren().get(j)
                        .getChildren().size(); jj++) {
                        if (node.getChildren().get(j).getChildren().get(jj)
                            .getData().id.equals(ID)) {

                            Svalue = node.getChildren().get(j).getChildren()
                                .get(jj).getData().value;
                            j = node.getChildren().size();
                            i = root.getChildren().size();
                            break;
                        }
                    }
                }
            }
            return Svalue;
        }

        public String GetUITypeFromXML(String ID) {
            String UIType = null;
            for (int i = 0; i < root.getChildren().size(); i++) {
                TreeNode<SCMenuItem> node = root.getChildren().get(i);
                for (int j = 0; j < node.getChildren().size(); j++) {

                    if (node.getChildren().get(j).getData().id.equals("status")) {
                        for (int jj = 0; jj < node.getChildren().get(j)
                            .getChildren().size(); jj++) {
                            if (node.getChildren().get(j).getChildren().get(jj)
                                .getData().id.equals(ID)) {
                                UIType = node.getChildren().get(j)
                                    .getChildren().get(jj).getData().uiType;
                                j = node.getChildren().size();
                                i = root.getChildren().size();
                                break;
                            }
                        }

                    } else if (node.getChildren().get(j).getData().id
                        .equals(ID)) {
                        UIType = node.getChildren().get(j).getData().uiType;
                        i = root.getChildren().size();
                        break;
                    }
                }
            }
            return UIType;
        }

        public int GetAbsoluteIDFromXML(String ID) {
            int AbsoluteID = -1;
            for (int i = 0; i < root.getChildren().size(); i++) {
                TreeNode<SCMenuItem> node = root.getChildren().get(i);
                for (int j = 0; j < node.getChildren().size(); j++) {
                    if (node.getChildren().get(j).getData().id.equals(ID)) {
                        AbsoluteID = j;
                        i = root.getChildren().size();
                        break;
                    }
                }
            }
            return AbsoluteID;
        }

        public void SetSelectBox(String ID) {
            for (int i = 0; i < root.getChildren().size(); i++) {
                TreeNode<SCMenuItem> node = root.getChildren().get(i);

                if (MediaType.equals("txt") || MediaType.equals("picture")
                    || MediaType.equals("music")) {
                    if (!((node.getData().id.equals("TxtPlayControl") && MediaType
                        .equals("txt"))
                        || (node.getData().id.equals("PicturePlayControl") && MediaType
                            .equals("picture")) || (node.getData().id
                        .equals("MusicPlayControl") && MediaType
                        .equals("music")))) {
                        continue;
                    }
                }

                for (int j = 0; j < node.getChildren().size(); j++) {

                    if (node.getChildren().get(j).getData().id.equals(ID)) {
                        SelectContext.clear();
                        SelectContextID.clear();
                        for (int jj = 0; jj < node.getChildren().get(j)
                            .getChildren().size(); jj++) {
                            if (entrance.equals(GlobalConst.ENTRANCE_TYPE_VGA)
                                && ID.equals("shortcut_setup_video_display_mode_")) {
                                // Log.d("Haha","jj is:" + jj);
                                // if(jj == 4)
                                // continue;
                            }
                            SelectContext.add(node.getChildren().get(j)
                                .getChildren().get(jj).getData().value);
                            SelectContextID.add(node.getChildren().get(j)
                                .getChildren().get(jj).getData().id);
                        }
                        i = root.getChildren().size();
                        break;
                    }
                }
            }
        }

        public String GetAnotherStatusID(String ID) {
            String Svalue = null;
            for (int i = 0; i < root.getChildren().size(); i++) {
                TreeNode<SCMenuItem> node = root.getChildren().get(i);
                for (int j = 0; j < node.getChildren().size(); j++) {
                    if (node.getChildren().get(j).getData().id.equals("status"))
                        for (int jj = 0; jj < node.getChildren().get(j)
                            .getChildren().size(); jj++) {
                            if (node.getChildren().get(j).getChildren().get(jj)
                                .getData().id.equals(ID)) {

                                if (jj == (node.getChildren().get(j)
                                    .getChildren().size() - 1))
                                    Svalue = node.getChildren().get(j)
                                        .getChildren().get(0).getData().id;
                                else
                                    Svalue = node.getChildren().get(j)
                                        .getChildren().get(jj + 1).getData().id;
                                j = node.getChildren().size();
                                i = root.getChildren().size();
                                break;
                            }
                        }
                }
            }
            return Svalue;
        }
    }

    private void postUpdateSFMessage(int FocusID, String MenuItemName,
        String UIType, int timer) {
        if (handlerUpdateSF.hasMessages(1))
        handlerUpdateSF.removeMessages(1);
        Message msg = handlerUpdateSF.obtainMessage(1);
        msg.arg1 = FocusID;
        Bundle bundle = new Bundle();
        bundle.putString("MenuItemName", MenuItemName);
        bundle.putString("UIType", UIType);
        msg.setData(bundle);
        handlerUpdateSF.sendMessageDelayed(msg, timer);
    }

    private Handler handlerUpdateSF = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (MenuFocusID == msg.arg1) {
                        Bundle bundle = msg.getData();
                        String MenuItemName = bundle.getString("MenuItemName");
                        String UIType = bundle.getString("UIType");
                        updateSF(MenuItemName, UIType);

                    }
                    break;
                case 2:
                    String item = (String) msg.obj;
                    SourceData.SetSelectBox(item);
                    int absoluteID = menuUIOp.getMGInstance().GetFocusID(item);
                    if (absoluteID < 0)
                        return;
                    // for progressbar exist case
                    menuUIOp.RpbvSmg();
                    menuUIOp.getMGInstance().setFocusID(absoluteID);
                    menuUIOp.getMGInstance().getLeftOffSet();
                    int val = 0;

                    try {
                        if (mItvservice != null)
                            val = mItvservice.GetTvParam(item);
                    } catch (RemoteException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    if (val == -1)
                        val = checkedstore.getCheckedIDValue(item);

                    ShowSelectFrame(menuUIOp.getMGInstance()
                        .getRelativeFoucsID(), val, true);
                    if (Menucontrol.this.getVisibility() == View.INVISIBLE) {
                        Menucontrol.this.setVisibility(View.VISIBLE);
                    }

                    menuUIOp.getMGInstance().myinvalidate();
                    menuUIOp.setSFFocus();
                    break;
                    
                case 3:
            		//if(tvsetting.isSrcSwtichDone())
            		//	HandleMenuCommand(tvsource);
                	break;
                case 4:
                	DialogManage(true);
                	break;
                default:
                    break;
            }
        }
    };

    private void updateSF(String MenuItemName, String UIType) {
        if (UIType.equals("SelectBox")) {
            SourceData.SetSelectBox(MenuItemName);
            int tmp_val = 0;
            if (MenuItemName.equals("shortcut_setup_sys_filelist_mode_")) {
                String tmp = mdb.getListMode();
                if (tmp.equals("FOLDER"))
                    tmp_val = 1;
                else
                    tmp_val = 0;
            } else if (MenuItemName.equals("shortcut_common_playmode_")) {
                if (entrance.equals("Local") || entrance.equals("Xunlei")
                    || entrance.equals("Voole")) {
                    String tmp = getVidRepeatMode();
                    if (tmp.equals("SINGLE"))
                        tmp_val = 0;
                    else if (tmp.equals("FOLDER"))
                        tmp_val = 1;
                    else if (tmp.equals("RANDOM"))
                        tmp_val = 2;
                } else if (entrance.equals("Music") || entrance.equals("MusT")
                    || entrance.equals("MusPT")) {
                    String tmp = getMusRepeatMode();
                    if (tmp.equals("SINGLE"))
                        tmp_val = 0;
                    else if (tmp.equals("FOLDER"))
                        tmp_val = 1;
                    else if (tmp.equals("RANDOM"))
                        tmp_val = 2;
                } else if (entrance.equals("MusPic")) {
                    if (MediaType.equals("music")) {
                        String tmp = getMusRepeatMode();
                        if (tmp.equals("SINGLE"))
                            tmp_val = 0;
                        else if (tmp.equals("FOLDER"))
                            tmp_val = 1;
                        else if (tmp.equals("RANDOM"))
                            tmp_val = 2;
                    } else { // MediaType = "picture";
                        String tmp = getPicRepeatMode();
                        if (tmp.equals("ORDER"))
                            tmp_val = 0;
                        else if (tmp.equals("FOLDER"))
                            tmp_val = 1;
                        else if (tmp.equals("RANDOM"))
                            tmp_val = 2;
                    }
                } else {
                    String tmp = getPicRepeatMode();
                    if (tmp.equals("ORDER"))
                        tmp_val = 0;
                    else if (tmp.equals("FOLDER"))
                        tmp_val = 1;
                    else if (tmp.equals("RANDOM"))
                        tmp_val = 2;
                }
            } else if (MenuItemName.equals("shortcut_picture_switch_time_")) {
                // only pic
                String tmp = getPicPlayMode();
                if (tmp.equals("3S"))
                    tmp_val = 0;
                else if (tmp.equals("5S"))
                    tmp_val = 1;
                else if (tmp.equals("10S"))
                    tmp_val = 2;
                else
                    tmp_val = 3;
            } else if (MenuItemName.equals("shortcut_picture_switch_mode_")) {
                // only pic
                String tmp = getPicSwitchMode();
                if (tmp.equals("Normal"))
                    tmp_val = 0;
                else if (tmp.equals("Random"))
                    tmp_val = 1;
            } else if (MenuItemName.equals("shortcut_videochat_fullscr_")) {
            	String tmp = getFullScreenMode();
                if (tmp.equals("OFF"))
                    tmp_val = 1;
                else 
                    tmp_val = 0;
            } 
            
            else if (MenuItemName.equals("shortcut_videochat_local_video_")) {
            	String tmp = getLocalScreenMode();
                if (tmp.equals("OFF"))
                    tmp_val = 1;
                else 
                    tmp_val = 0;
            }
            
            else if (MenuItemName.equals("shortcut_videochat_remote_video_")) {
            	String tmp = getRemoteScreenMode();
                if (tmp.equals("OFF"))
                    tmp_val = 1;
                else 
                    tmp_val = 0;
            }else if(MenuItemName.equals("shortcut_txt_fontsize_") && fontsize != -1){
            	tmp_val = fontsize % 3;
            	checkedstore.setCheckedIDValue(MenuItemName, tmp_val);
            	Log.d("Haha", "tmp value is:" + tmp_val);
            	fontsize = -1;
            }
            else {
                try {
                    if (mItvservice != null)
                        tmp_val = mItvservice.GetTvParam(MenuItemName);
                } catch (RemoteException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                
                if(tvsetting.isVgaFmtInHdmi() == 1 && MenuItemName.equals("shortcut_setup_video_display_mode_")){
                	
                    if (tmp_val != 0 && tmp_val != 4)
                    	tmp_val = 0;
                }
                
                if (tmp_val == -1) {
                	tmp_val = checkedstore.getCheckedIDValue(MenuItemName);
                }
                	
            }

            ShowSelectFrame(MenuFocusID, tmp_val, true);
        } else if (UIType.equals("SelectBoxCommand")) {
            SourceData.SetSelectBox(MenuItemName);
            ShowSelectFrame(MenuFocusID, -1, false);
        } else if (UIType.equals("DynamicBox")) {
            if (MenuItemName.equals("shortcut_program_no_")) {
                GetTvChanelProgramData(MenuItemName);
                try {
                    ShowSelectFrame(MenuFocusID, mItvservice.num(), true);
                } catch (RemoteException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else {
                ArrayList<String> Data = null;
                if (MenuItemName.equals("shortcut_video_subtitle_")) {
                    Data = DynamicData.getsubtitle();
                } else if (MenuItemName.equals("shortcut_video_soundtrack_")) {
                    Data = DynamicData.getsoundtrack();
                }

                if (Data != null && Data.size() != 0) {
                    SelectContext.clear();
                    SelectContextID.clear();
                    SelectContext = new ArrayList<String>(Data);
                    SelectContextID = new ArrayList<String>(Data);
                    ShowSelectFrame(MenuFocusID, -1, true);
                } else
                    menuUIOp.HideSelectFrame();
            }
        } else
            menuUIOp.HideSelectFrame();
    }

    private void postUpdateShortCutMassage(String item) {
        if (handlerUpdateSF.hasMessages(1))
        handlerUpdateSF.removeMessages(1);
        Message msg = handlerUpdateSF.obtainMessage(2);
        msg.obj = item;
        handlerUpdateSF.sendMessageDelayed(msg, 250);
    }

    private void GetTvChanelProgramData(String MenuItemName) {
        int i;
        SelectContext.clear();
        SelectContextID.clear();
        for (i = GlobalConst.TV_CHANNEL_MIN_NUMBER; i < GlobalConst.TV_CHANNEL_TOTAL_PROGRAM_COUNT; i++) {
            SelectContext.add(String.format("%3d", i));
            SelectContextID.add(String.format("%s%03d", MenuItemName, i));
        }
    }

    public void handleServiceMessage(Message msg) {
        menuUIOp.handleServiceMessage(msg);
    }

    public boolean isfffb=false; 
    public void setfffbflag(boolean flag){
    	isfffb = flag;
    }
    public boolean CheckMenuItemStatus(String MenuItemName, int id) {
        if ((MenuItemName.equals("shortcut_setup_audio_mute_panel_"))
            || (MenuItemName.equals("shortcut_setup_sys_pc_set_"))
            || (MenuItemName.equals("shortcut_video_3d_setup_"))) {
            
            if(MenuItemName.equals("shortcut_video_3d_setup_")){
                if (entrance.equals(GlobalConst.ENTRANCE_TYPE_VGA))
                    return true;
                else if(entrance.equals(GlobalConst.ENTRANCE_TYPE_HDMI)){
                	if(tvsetting.isVgaFmtInHdmi() == 1){ //vga format
                		return true;
                	}
                }
                
                if(entrance.equals("Local") || entrance.equals("Xunlei")
                    || entrance.equals("Voole")){
                	if(isfffb)
                		return true;
                }
                
            }
            if (menuUIOp != null) {
                if (menuUIOp.nosignalflag) {
                    return true;
                } else {
                    return false;
                }
            }
            return true;
        } else if (MenuItemName.equals("shortcut_setup_video_hue_")) {
            if (menuUIOp != null) {
                if (menuUIOp.nosignalflag) {
                    return true;
                } else {
                    if (menuUIOp.ntscsignalflag) {
                        return false;
                    } else {
                        return true;
                    }
                }
            }
            return true;
        } else if (MenuItemName.equals("shortcut_setup_video_dnr_")) {
            if (entrance.equals(GlobalConst.ENTRANCE_TYPE_VGA)) {
                return true;
            } else {
                return false;
            }
        } else if (MenuItemName.equals("shortcut_video_3d_3d2d_")) {
            if (getCheckedIDValue("shortcut_video_3d_3d2d_") != 0) {
                return true;
            } else {
                return false;
            }
        } else if (MenuItemName.equals("shortcut_common_sync_play_")) {
			if(online)
        		return true;
        	else
        		return false;
        }

        return false;
    }

    public boolean CheckSelectFrameItemDisable(String id) {
        // TODO Auto-generated method stub
    	
    	if (id.equals("shortcut_setup_sys_six_color_splitdemo")){
    		if (entrance.equals(GlobalConst.ENTRANCE_TYPE_HDMI)){
                if (tvsetting.GetSigTransFormat() != 0)
                    return true;
            }
    		if (getCheckedIDValue("shortcut_video_3d_mode_") == 2)
    		    return true;
    		else
    		return GetNosignalFlag();
    	}

    	if(entrance.equals(GlobalConst.ENTRANCE_TYPE_HDMI)){
    		Log.d("BUG","CheckSelectFrameItemDisable:: HDMI input:id="+id);
    		Log.d("BUG","format name=" + tvsetting.GetSigFormatName());
    		if( id.equals("shortcut_setup_video_display_mode_subtitle")||
    			id.equals("shortcut_setup_video_display_mode_theater")||	
    			id.equals("shortcut_setup_video_display_mode_personal")||
    			id.equals("shortcut_setup_video_display_mode_panorama")){
		    		if(tvsetting.isVgaFmtInHdmi() == 1){ //vga format
		    			return true;
		    		}else
		    			return false;
    		}
    	}
    	
    	if ((entrance.equals(GlobalConst.ENTRANCE_TYPE_ANALOG_TV))
            || (entrance.equals(GlobalConst.ENTRANCE_TYPE_AV))
            || (entrance.equals(GlobalConst.ENTRANCE_TYPE_YUV))
            || (entrance.equals(GlobalConst.ENTRANCE_TYPE_HDMI))
            || (entrance.equals(GlobalConst.ENTRANCE_TYPE_VGA))) {
            if (GetMenuShowState() == GlobalConst.MENU_SHOW_STATE_SETUP3D) {
                
                if (id.equals("shortcut_video_3d_mode_off")) {
                    if (entrance.equals(GlobalConst.ENTRANCE_TYPE_HDMI)){
                        if (tvsetting.GetSigTransFormat() == 0)
                            return false;
                        else
                            return true;
                    }
                    
                } else if (id.equals("shortcut_video_3d_mode_auto")) {
                    if (entrance.equals(GlobalConst.ENTRANCE_TYPE_HDMI)){
                        if (tvsetting.GetSigTransFormat() == 0)
                            return true;
                        else if(tvsetting.GetSigTransFormat()>5)
                            return false;
                    }
                    else
                        return true;
                    
                } else if (id.equals("shortcut_video_3d_mode_2d3d")) {
                    if (entrance.equals(GlobalConst.ENTRANCE_TYPE_HDMI)) {
                        if (tvsetting.GetSigTransFormat() == 0)
                            return false;
                        else
                            return true;
                    } else if (entrance.equals(GlobalConst.ENTRANCE_TYPE_VGA)) {
                        return true;
                    } 
                    
                } else if (id.equals("shortcut_video_3d_mode_lr")
                    || id.equals("shortcut_video_3d_mode_ud")) {
                	if (entrance.equals(GlobalConst.ENTRANCE_TYPE_YUV))
                		return false;
                	else if(entrance.equals(GlobalConst.ENTRANCE_TYPE_HDMI)){
                		if (tvsetting.GetSigTransFormat() == 0)
                			return false;
                	}
                	return true;
                }
            }
        } else if (GetMenuShowState() == GlobalConst.MENU_SHOW_STATE_SETUP3D) {

            if (id.equals("shortcut_video_3d_mode_off")
            		||id.equals("shortcut_video_3d_mode_2d3d")
                    || id.equals("shortcut_video_3d_mode_lr")
                    || id.equals("shortcut_video_3d_mode_ud")) {
                if (tvsetting.Get3DStatus() == 1)
                    return true;
            } else if (id.equals("shortcut_video_3d_mode_auto")) {
                if (tvsetting.Get3DStatus() != 1)
                    return true;
            } 
        }
        
        return false;
    }

	public boolean GetNosignalFlag() {
		// TODO Auto-generated method stub
		if(menuUIOp!=null){
			if(menuUIOp.nosignalflag)
				return true;
		}
		return false;
	}

	public void SetDefaultMessage() {
		// TODO Auto-generated method stub
		handlerUpdateSF.sendEmptyMessage(4);
	}
}
