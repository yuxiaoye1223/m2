package com.skyworth;

import java.util.ArrayList;

import com.skyworth.SkyworthMenu.GlobalConst;
import com.skyworth.SkyworthMenu.Menucontrol;
import com.skyworth.View.DreamPanelDemoView;
import com.skyworth.View.MutePanelView;
import com.skyworth.View.ThreeDiGlassesView;
import com.skyworth.View.TunerOperateDialog;
import com.skyworth.View.ProgramEditDialog;
import com.skyworth.View.Balancer;
import com.skyworth.View.DialogView;
import com.skyworth.View.MenuGroup;
import com.skyworth.View.MenuGroup1;
import com.skyworth.View.SelectFrame;
import com.skyworth.View.TvSearchNavigateDialog;
import com.skyworth.View.VgaAdjustDialog;
import com.skyworth.View.ViiDemoView;
import com.skyworth.View.progressBarView;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsoluteLayout;

public class MenuUIOp {

    private MenuGroup1 MG = null;
    private SelectFrame SF = null;
    private SelectFrame SF2 = null;
    private progressBarView pBV = null;
    private Balancer balancer = null;
    private DialogView dialog = null;
    private MutePanelView mutePanelView = null;
    private Menucontrol mControl = null;
    private TunerOperateDialog TunerOPDialog = null;
    private ProgramEditDialog ProgramEditDialog = null;
    private ViiDemoView viiDemoView = null;
    private DreamPanelDemoView dreamPanelDemoView = null;
    private TvSearchNavigateDialog navigateDialog = null;
    private VgaAdjustDialog vgaAdjustDialog = null;
    private ThreeDiGlassesView threeDiGlassesView = null;
    private boolean hideMenu = true;
    private boolean vgaautoshowinfo = false;
    private String _entrance = null;
    private int MGroupBGKind = MenuGroup.noplayer;
    public boolean nosignalflag = true;
    public boolean ntscsignalflag = false;

    public MenuUIOp(Menucontrol menucontrol, String entrance) {
        mControl = menucontrol;
        getMGroupBGKind(entrance);
        _entrance = entrance;
        MG = new MenuGroup1(mControl.getContext(), null, MGroupBGKind);
        MG.setMenuGroupListener(mControl);
        SF = new SelectFrame(mControl.getContext(), null, _entrance);
        SF.setSelectFrameListener(mControl);
        SF.setFocusable(false);
        mControl.addView(SF);
        if (GlobalConst.OSD_DISPLAY_HALF_FLAG != 0) {
            SF2 = new SelectFrame(mControl.getContext(), null, _entrance);
            SF2.setSelectFrameListener(mControl);
            SF2.setFocusable(false);
            mControl.addView(SF2);
        }
        setNosigFlag();
    }

    public void setNosigFlag() {
        // Log.d("Haha","setNosigFlag:" + _entrance + " " + nosignalflag);
        if (_entrance == null) {
            nosignalflag = false;
            return;
        }

        if (_entrance.equals(GlobalConst.ENTRANCE_TYPE_ANALOG_TV)
            || _entrance.equals(GlobalConst.ENTRANCE_TYPE_AV)
            || _entrance.equals(GlobalConst.ENTRANCE_TYPE_YUV)
            || _entrance.equals(GlobalConst.ENTRANCE_TYPE_VGA)
            || _entrance.equals(GlobalConst.ENTRANCE_TYPE_HDMI)) {
            nosignalflag = true;
        } else {
            nosignalflag = false;
        }

    }

    public boolean GetVolProgressBarExist(){
    	if(pBV!=null && pBV.GetCurrentMenuItemName().equals("shortcut_common_vol_"))
    		return true;
    	return false;
    }
    
    public void menuuiopUpdateUI() {
        if (null != MG) {
            MG.initAllItemStatus();
            MG.setFocusID(MG.getNextFocusId());
            MG.updateMGUI();
        }
    }

    public void menuuiopUpdateUI(int absFocusID) {
        if (mControl.getVisibility() == View.VISIBLE) {
            if (null != MG) {
                MG.initAllItem();
                MG.setFocusID(absFocusID);
                MG.updateMGUI();
            }
        }
    }

    public void setFirstItem(boolean setNext) {
        if (setNext) {
            if (MG.getAbsFocusID() == 0)
                MG.setFocusID(MG.getAbsFocusID() + 1);
        }
    }

    public void showMenu() {
    	if(MG != null && MG.getParent() == null){
	        AbsoluteLayout.LayoutParams paramp;
	        int menu_offset_y = 0;
	        if (GlobalConst.OSD_DISPLAY_HALF_FLAG == 2)
	            menu_offset_y = 540;
	        if (MGroupBGKind == MenuGroup.nobar_player
	            || MGroupBGKind == MenuGroup.bar_player)
	            paramp = new AbsoluteLayout.LayoutParams(1920, 238 + menu_offset_y,
	                0, 1080 - 238 - menu_offset_y);
	        else
	            paramp = new AbsoluteLayout.LayoutParams(1920, 152 + menu_offset_y,
	                0, 1080 - 152 - menu_offset_y);
	        MG.setLayoutParams(paramp);
	        MG.initFocusID();
	        //mControl.removeAllViews();
	        mControl.addView(MG);
	        MG.setFocusable(true);
	        MG.requestFocus();
	        MG.showFirstSelectFrame();
    	}
    }

    public void ShowL2Menu() {
        MG.setFocusable(true);
        MG.requestFocus();
        MG.initFocusID();
        MG.invalidate();
        MG.showFirstSelectFrame();
    }

    public void BackToL1Menu(int FocusID) {
        MG.setFocusable(true);
        MG.requestFocus();
        MG.setFocusID(FocusID);
        MG.myinvalidate();
    }

    public void showBalancer(int soundMode) {
        MG.setFocusable(false);
        mControl.removeViewInLayout(MG);

        AbsoluteLayout.LayoutParams paramp = new AbsoluteLayout.LayoutParams(
            627, 376, (1920 - 627) / 2, 240);
        balancer = new Balancer(mControl.getContext(), null);
        balancer.setBalancerKeyListener(mControl);
        balancer.setLayoutParams(paramp);

        balancer.initBalancerRescource(mControl.XMLStringItems, soundMode);
        mControl.addView(balancer);

        balancer.setFocusable(true);
        balancer.requestFocus();
//        setHideMenu(false);
    }

    public void showDialog() {
        MG.setFocusable(false);
        AbsoluteLayout.LayoutParams paramp = new AbsoluteLayout.LayoutParams(
            702, 480, (1920 - 702) / 2, 250);
        dialog = new DialogView(mControl.getContext(), null);
        dialog.setLayoutParams(paramp);
        dialog.initDialogResource(mControl.XMLStringItems);
        dialog.setDialogListener(mControl);

        mControl.addView(dialog);
        dialog.setFocusable(true);
        dialog.requestFocus();
    }

    public void showMutePanelView() {
        MG.setFocusable(false);
        mControl.removeViewInLayout(MG);

        AbsoluteLayout.LayoutParams paramp = new AbsoluteLayout.LayoutParams(
            702, 480, (1920 - 702) / 2, (1080 - 480) / 2);
        mutePanelView = new MutePanelView(mControl.getContext(), null);
        mutePanelView.setMutePanelViewListener(mControl);
        mutePanelView.setLayoutParams(paramp);
        mControl.addView(mutePanelView);
        mutePanelView.setFocusable(true);
        mutePanelView.requestFocus();
        setHideMenu(false);
    }
    
    public void showProgramEditDialog(int chNumber) {
        int tmp_w = 702, tmp_h = 480;
        int tmp_left = (1920 - tmp_w) / 2;
        int tmp_top = (1080 - tmp_h) / 2;

        MG.setFocusable(false);
        mControl.removeViewInLayout(MG);
        AbsoluteLayout.LayoutParams paramp = new AbsoluteLayout.LayoutParams(
            tmp_w, tmp_h, tmp_left, tmp_top);

        ProgramEditDialog = new ProgramEditDialog(mControl.getContext(), null,
            chNumber);
        ProgramEditDialog.setLayoutParams(paramp);
        ProgramEditDialog.initDialogResource();
        ProgramEditDialog.setProgramEditDialogListener(mControl);
        mControl.addView(ProgramEditDialog);
        ProgramEditDialog.setFocusable(true);
        ProgramEditDialog.requestFocus();
//        setHideMenu(false);
    }

    public void removeProgramEditDialog() {
        if (ProgramEditDialog != null) {
            mControl.removeViewInLayout(ProgramEditDialog);
            ProgramEditDialog = null;
            mControl.addView(MG);
            MG.setFocusable(true);
            MG.requestFocus();
//            setHideMenu(true);
        }
    }

    public void showViiDemoView(String MenuItemName) {
        MG.setFocusable(false);
        mControl.removeViewInLayout(MG);
        AbsoluteLayout.LayoutParams paramp;
        if (MenuItemName == "shortcut_setup_sys_six_color_colordemo") {
            paramp = new AbsoluteLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT, 1600, 800);
        } else {
            paramp = new AbsoluteLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT, 800, 900);
        }
        viiDemoView = new ViiDemoView(mControl.getContext(), MenuItemName);
        viiDemoView.setLayoutParams(paramp);
        viiDemoView.setViiDemoViewListener(mControl);
        mControl.addView(viiDemoView);
        viiDemoView.setFocusable(true);
        viiDemoView.requestFocus();
        setHideMenu(false);
    }

    public void removeViiDemoView() {
        if (viiDemoView != null) {
            mControl.removeViewInLayout(viiDemoView);
            viiDemoView = null;
            mControl.addView(MG);
            MG.setFocusable(true);
            MG.requestFocus();
            setHideMenu(true);
        }
    }

    private Handler handlerUIOp = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    hideThreeDiGlasses();
                    break;
            }
        }
    };
    
    public void showThreeDiGlasses() {
		if(threeDiGlassesView==null){
			threeDiGlassesView = new ThreeDiGlassesView(mControl.getContext());
			AbsoluteLayout.LayoutParams paramp = new AbsoluteLayout.LayoutParams(
					702, 480, (1920-702)/2, 300);
			threeDiGlassesView.setLayoutParams(paramp);
			mControl.addView(threeDiGlassesView);
        } else {
            threeDiGlassesView.setVisibility(View.VISIBLE);
		}
		
        if (handlerUIOp.hasMessages(1)) {
			handlerUIOp.removeMessages(1);
        }
		handlerUIOp.sendEmptyMessageDelayed(1, 3000);
	}
	
    public void hideThreeDiGlasses() {
		if(threeDiGlassesView!=null){
			threeDiGlassesView.setVisibility(View.INVISIBLE);
		}
	}
    
    public void showDreampanelDemo() {
        MG.setFocusable(false);
        mControl.removeViewInLayout(MG);
        AbsoluteLayout.LayoutParams paramp = new AbsoluteLayout.LayoutParams(
            1920, 400, 0, 680);
        dreamPanelDemoView = new DreamPanelDemoView(mControl.getContext());
        dreamPanelDemoView.setLayoutParams(paramp);
        dreamPanelDemoView.setDreamPanelDemoListener(mControl);
        mControl.addView(dreamPanelDemoView);
        dreamPanelDemoView.setFocusable(true);
        dreamPanelDemoView.requestFocus();
        setHideMenu(false);
    }

    public void removeDreampanelDemo() {
        if (dreamPanelDemoView != null) {
            mControl.removeViewInLayout(dreamPanelDemoView);
            dreamPanelDemoView = null;
            mControl.addView(MG);
            MG.setFocusable(true);
            MG.requestFocus();
            setHideMenu(true);
        }
    }

    public void showTvSearchNavigateDialog() {
        int tmp_w = 702, tmp_h = 480;
        int tmp_left = (1920 - tmp_w) / 2;
        int tmp_top = (1080 - tmp_h) / 2;

        MG.setFocusable(false);
        mControl.removeViewInLayout(MG);
        AbsoluteLayout.LayoutParams paramp = new AbsoluteLayout.LayoutParams(
            tmp_w, tmp_h, tmp_left, tmp_top);

        navigateDialog = new TvSearchNavigateDialog(mControl.getContext());
        navigateDialog.setLayoutParams(paramp);
        navigateDialog.initDialogResource();
        navigateDialog.setNavigateDialogListener(mControl);
        mControl.addView(navigateDialog);
        navigateDialog.setFocusable(true);
        navigateDialog.requestFocus();
        setHideMenu(false);
    }

    public void removeTvSearchNavigateDialog() {
        if (navigateDialog != null) {
            mControl.removeViewInLayout(navigateDialog);
            navigateDialog = null;
            mControl.addView(MG);
            MG.setFocusable(true);
            MG.requestFocus();
            setHideMenu(true);
        }
    }

    public void showVgaAdjustDialog() {
        MG.setFocusable(false);
        mControl.removeViewInLayout(MG);
        SF.setFocusable(false);
        mControl.removeViewInLayout(SF);
        if (GlobalConst.OSD_DISPLAY_HALF_FLAG != 0) {
            SF2.setFocusable(false);
            mControl.removeViewInLayout(SF2);
        }
        AbsoluteLayout.LayoutParams paramp = new AbsoluteLayout.LayoutParams(
            627, 376, (1920 - 627) / 2, 240);
        vgaAdjustDialog = new VgaAdjustDialog(mControl.getContext());
        if (vgaautoshowinfo == true)
            vgaAdjustDialog.SetAdjustFlag(true);
        vgaAdjustDialog.setLayoutParams(paramp);
        vgaAdjustDialog.setVgaAdjustDialogListener(mControl);
        mControl.addView(vgaAdjustDialog);
        vgaAdjustDialog.setFocusable(true);
        vgaAdjustDialog.requestFocus();
//        setHideMenu(false);
    }

    public void removeVgaAdjustDialog() {
        if (vgaAdjustDialog != null) {
            mControl.removeViewInLayout(vgaAdjustDialog);
            vgaAdjustDialog.SetAdjustFlag(false);
            vgaAdjustDialog = null;
            mControl.addView(MG);
            mControl.addView(SF);
            if (GlobalConst.OSD_DISPLAY_HALF_FLAG != 0)
                mControl.addView(SF2);
            HideSelectFrame();
            MG.setFocusable(true);
            MG.requestFocus();
//            setHideMenu(true);
        }
    }

    public void removeVgaAdjustDialogAndAll() {
        if (vgaAdjustDialog != null) {
            mControl.removeViewInLayout(vgaAdjustDialog);
            vgaAdjustDialog.SetAdjustFlag(false);
            vgaAdjustDialog = null;
            mControl.addView(MG);
            mControl.addView(SF);
            if (GlobalConst.OSD_DISPLAY_HALF_FLAG != 0)
                mControl.addView(SF2);
            HideSelectFrame();
            mControl.setVisibility(View.INVISIBLE);
        }
    }

    public void showSelectFrame(int Focus, int initnum, boolean ifselect,
        ArrayList<String> SelectContext, ArrayList<String> SelectContextID) {
        int offset = 0;// 20101202
        int osd_half_offset = 0;
        int osd_half_offset_y = 0;
        if (MGroupBGKind == MenuGroup.noplayer)
            offset = 152;// 20101202 name or process bar except tv_set bar temp
        else
            offset = 238;
        SF.setSFData(SelectContext, SelectContextID, ifselect);
        if (GlobalConst.OSD_DISPLAY_HALF_FLAG != 0)
            SF2.setSFData(SelectContext, SelectContextID, ifselect);
        int[] Hight = {
        80, 135, 190, 245, 300, 355, 410, 465, 528, 583
        };
        int hightTotal = 0;
        if (SelectContext.size() > GlobalConst.ITEM_COUNT_PERMENURPAGE_SHOW)
            hightTotal = GlobalConst.ITEM_COUNT_PERMENURPAGE_SHOW;
        else
            hightTotal = SelectContext.size();
        if (GlobalConst.OSD_DISPLAY_HALF_FLAG == 1)
            osd_half_offset = 2;
        else
            osd_half_offset = 1;
        if (GlobalConst.OSD_DISPLAY_HALF_FLAG == 2)
            osd_half_offset_y = Hight[hightTotal - 1] / 2 + 152 / 2;
        AbsoluteLayout.LayoutParams paramp = new AbsoluteLayout.LayoutParams(
            256, Hight[hightTotal - 1], GlobalConst.MENU_ITEM_WIDTH * Focus
                / osd_half_offset + MG.leftoffset, 1080 - offset
                - Hight[hightTotal - 1] + osd_half_offset_y);

        AbsoluteLayout.LayoutParams paramp2 = new AbsoluteLayout.LayoutParams(
            256, Hight[hightTotal - 1], GlobalConst.MENU_ITEM_WIDTH * Focus
                / osd_half_offset + MG.leftoffset + 960, 1080 - offset
                - Hight[hightTotal - 1]);

        AbsoluteLayout.LayoutParams paramp3 = new AbsoluteLayout.LayoutParams(
            256, Hight[hightTotal - 1], GlobalConst.MENU_ITEM_WIDTH * Focus
                / osd_half_offset + MG.leftoffset, 1080 - offset
                - Hight[hightTotal - 1] - 540 + osd_half_offset_y);

        Log.d("test",
            "1080 - offset - Hight[hightTotal - 1] + osd_half_offset_y ="
                + (1080 - offset - Hight[hightTotal - 1] + osd_half_offset_y));
        SF.setVisibility(View.VISIBLE);
        if (initnum != -1)
            SF.initSelectItem(initnum);
        SF.setLayoutParams(paramp);
        if (GlobalConst.OSD_DISPLAY_HALF_FLAG == 1) {
            SF2.setVisibility(View.VISIBLE);
            if (initnum != -1)
                SF2.initSelectItem(initnum);
            SF2.setLayoutParams(paramp2);
        }

        if (GlobalConst.OSD_DISPLAY_HALF_FLAG == 2) {
            SF2.setVisibility(View.VISIBLE);
            if (initnum != -1)
                SF2.initSelectItem(initnum);
            SF2.setLayoutParams(paramp3);
        }

    }

    public void HideSelectFrame() {
        if (SF.getVisibility() != View.INVISIBLE) {
            SF.restoreSFData();
        }
        if (GlobalConst.OSD_DISPLAY_HALF_FLAG != 0)
            if (SF2.getVisibility() != View.INVISIBLE) {
                SF2.restoreSFData();
            }
    }

    public void showProgressBar(String MenuItemName, String initnum) {
        if (pBV == null) {
            int offset = 0;
            if (MGroupBGKind == MenuGroup.noplayer) {
                offset = 152;
            } else {
                offset = 238;
            }
            MG.setFocusable(false);
            mControl.removeViewInLayout(MG);
            AbsoluteLayout.LayoutParams paramp = new AbsoluteLayout.LayoutParams(
                1920, offset, 0, 1080 - offset);
            pBV = new progressBarView(mControl.getContext(), null,
                MenuItemName, MGroupBGKind);
            pBV.setProgressBarListener(mControl);
            if (initnum != null && !initnum.equals("")) {
                pBV.setProgress(Integer.parseInt(initnum));
            }
            mControl.addView(pBV, paramp);
            pBV.requestFocus();
        }
    }

    public void showProgressBar(String MenuItemName, int num) {
        if (pBV == null) {
            int offset = 0;
            if (MGroupBGKind == MenuGroup.noplayer) {
                offset = 152;
            } else {
                offset = 238;
            }
            MG.setFocusable(false);
            mControl.removeViewInLayout(MG);
            int volum_offset_y = 0;
            if (GlobalConst.OSD_DISPLAY_HALF_FLAG == 2)
                volum_offset_y = 540;
            AbsoluteLayout.LayoutParams paramp = new AbsoluteLayout.LayoutParams(
                1920, offset + volum_offset_y, 0, 1080 - offset
                    - volum_offset_y);
            pBV = new progressBarView(mControl.getContext(), null,
                MenuItemName, MGroupBGKind);
            pBV.setProgressBarListener(mControl);
            if ((num < GlobalConst.MIN_VALUE_MENU_SHOW)
                || (num > GlobalConst.MAX_VALUE_MENU_SHOW)) {
            	if(!MenuItemName.equals("shortcut_video_3d_dof_"))
            		num = GlobalConst.DEFAULT_VALUE_MENU_SHOW;
            }
            pBV.setProgress(num);
            mControl.addView(pBV, paramp);
            pBV.setFocusable(true);
            pBV.requestFocus();
        }
    }

    public void showTunerOperateDialog(String MenuItemName) {
        int tmp_w = 702, tmp_h = 480;
        int tmp_left = (1920 - tmp_w) / 2;
        int tmp_top = (1080 - tmp_h) / 2;

        MG.setFocusable(false);
        mControl.removeViewInLayout(MG);
        AbsoluteLayout.LayoutParams paramp = new AbsoluteLayout.LayoutParams(
            tmp_w, tmp_h, tmp_left, tmp_top);
        TunerOPDialog = new TunerOperateDialog(mControl.getContext(), null,
            MenuItemName);
        TunerOPDialog.setTunerOperateDialogListener(mControl);
        TunerOPDialog.setLayoutParams(paramp);
        mControl.addView(TunerOPDialog);
        TunerOPDialog.setFocusable(true);
        TunerOPDialog.requestFocus();
        setHideMenu(false);
    }

    public void removeTunerOperateDialog() {
        if (TunerOPDialog != null) {
            mControl.removeViewInLayout(TunerOPDialog);
            TunerOPDialog = null;
            mControl.addView(MG);
            MG.setFocusable(true);
            MG.requestFocus();
            setHideMenu(true);
        }
    }

    public boolean isProgramEditDialogShow() {
        if (ProgramEditDialog != null)
            return true;
        else
            return false;
    }

    public boolean isViiDemoViewShow() {
        if (viiDemoView != null)
            return true;
        else
            return false;
    }

    public boolean isDreampanelDemoShow() {
        if (dreamPanelDemoView != null)
            return true;
        else
            return false;
    }

    public boolean isThreeDiglassesShow() {
        if ((threeDiGlassesView != null)
            && (threeDiGlassesView.getVisibility() == View.VISIBLE))
            return true;
        else
            return false;
    }
    
    public boolean isTunerOPDialogShow() {
        if (TunerOPDialog != null)
            return true;
        else
            return false;
    }

    public void enterTvManualSearch(boolean direction) {
        if (TunerOPDialog != null) {
            if (direction)
                TunerOPDialog.onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT,
                    new KeyEvent(KeyEvent.ACTION_DOWN,
                        KeyEvent.KEYCODE_DPAD_RIGHT));
            else
                TunerOPDialog.onKeyDown(KeyEvent.KEYCODE_DPAD_LEFT,
                    new KeyEvent(KeyEvent.ACTION_DOWN,
                        KeyEvent.KEYCODE_DPAD_LEFT));
        }
    }

    public void RpbvSmg() {
        if (pBV != null) {
            mControl.removeViewInLayout(pBV);
            pBV = null;
            mControl.addView(MG);
            MG.setFocusable(true);
            MG.requestFocus();
            MG.myinvalidate();
        }
    }

    public void RpbvhideSmg() {
        if (pBV != null) {
            mControl.removeViewInLayout(pBV);
            pBV = null;
            MG.setFocusable(true);
            MG.requestFocus();
        }
    }

    public void RsfSmg() {
        if (SF.getVisibility() != View.INVISIBLE) {
            HideSelectFrame();
            // MG.initFocusID();
            MG.update();
            MG.setFocusable(true);
            MG.requestFocus();
        }
    }

    public void RsfSmg(int Key) {
        HideSelectFrame();
        MG.setFocusable(true);
        MG.requestFocus();
        MG.onKeyDown(Key, new KeyEvent(Key, Key));
    }

    public void RbalancerSmg() {
        if (balancer != null) {
            mControl.removeViewInLayout(balancer);
            balancer = null;
            mControl.addView(MG);
            MG.setFocusable(true);
            MG.requestFocus();
//            setHideMenu(true);
        }
    }

    public void RdialogSmg() {
        if (dialog != null) {
            mControl.removeViewInLayout(dialog);
            dialog = null;
            MG.setFocusable(true);
            MG.requestFocus();
        }

    }

    public void removeMutePanelView() {
        if (mutePanelView != null) {
            mControl.removeViewInLayout(mutePanelView);
            mutePanelView = null;
            mControl.addView(MG);
            MG.setFocusable(true);
            MG.requestFocus();
            setHideMenu(true);
        }
    }

    public void setSFFocus() {
        if (SF != null) {
            SF.setmyFocus();
            MG.setFocusable(false);
            SF.setFocusable(true);
            SF.requestFocus();
            if (GlobalConst.OSD_DISPLAY_HALF_FLAG != 0)
                SF.SetotherFrame(SF2);

        }
        if (GlobalConst.OSD_DISPLAY_HALF_FLAG != 0)
            if (SF2 != null) {
                SF2.setmyFocus();
                MG.setFocusable(false);
                SF2.setFocusable(true);
                SF2.requestFocus();
                SF2.SetotherFrame(SF);

            }

    }

    public void setMGFocus(boolean focus) {
        if (null != MG) {
            MG.setFocusable(focus);
            if (focus) {
                MG.requestFocus();
            }
        }
    }
    
    public Menucontrol getmCInstance(){
    	return mControl;
    }

    public MenuGroup1 getMGInstance() {
        return MG;
    }

    public SelectFrame getSFInstance() {
        return SF;
    }

    public boolean isHideMenu() {
        return hideMenu;
    }

    public void setHideMenu(boolean hideMenu) {
        this.hideMenu = hideMenu;
    }

    // **************************************************************
    private void getMGroupBGKind(String entrance) {
        if (entrance.equals("Local") || entrance.equals("Music")
            || entrance.equals("Xunlei") || entrance.equals("Voole")
            || entrance.equals("MusPT") || entrance.equals("MusPic")
            || entrance.equals("MusT")) {
            MGroupBGKind = MenuGroup.bar_player;
        } else if (entrance.equals("Picture") || entrance.equals("Txt")
            || entrance.equals("PicT")) {
            MGroupBGKind = MenuGroup.nobar_player;
        } else if (entrance.equals(GlobalConst.ENTRANCE_TYPE_ANALOG_TV)
            || entrance.equals(GlobalConst.ENTRANCE_TYPE_AV)
            || entrance.equals(GlobalConst.ENTRANCE_TYPE_YUV)
            || entrance.equals(GlobalConst.ENTRANCE_TYPE_HDMI)
            || entrance.equals(GlobalConst.ENTRANCE_TYPE_VGA)) {
            MGroupBGKind = MenuGroup.noplayer;
        }

    }

    public void handleServiceMessage(Message msg) {
        // Log.d("Haha", "........The handleServiceMessage is:" + msg +
        // _entrance);

        if (msg.arg1 == GlobalConst.MSG_SIGNAL_VGA_NO_SIGNAL) {
            if (nosignalflag == false) {
                Log.d("Haha", "........signal lose........");
                nosignalflag = true;
                if (mControl.getVisibility() == View.VISIBLE) {
                    menuuiopUpdateUI(MG.getNextFocusId()); // current item - 1
                    if (vgaAdjustDialog != null
                        && _entrance.equals(GlobalConst.ENTRANCE_TYPE_VGA)) {
                        Log.d("Haha",
                            "........ signal lose and remove VGA dialog ........");
                        removeVgaAdjustDialogAndAll();
                        vgaautoshowinfo = false;
                    }
                }
            }
            return;
        }

        if (msg.arg1 == GlobalConst.MSG_SIGNAL_STABLE) {
            if (nosignalflag == true) {
                Log.d("Haha", "........signal active........");
                nosignalflag = false;
                if (mControl.getVisibility() == View.VISIBLE) {
                    menuuiopUpdateUI(MG.getNextFocusId()); // current item - 1
                }
            }
            return;
        }

        if (msg.arg1 == GlobalConst.MSG_SIGNAL_FORMAT_NTSC) {
            if (ntscsignalflag == false) {
                ntscsignalflag = true;
                menuuiopUpdateUI(MG.getNextFocusId()); // current item - 1
                Log.d("Haha", "........NTSC signal........");
            }
            return;
        }

        if (msg.arg1 == GlobalConst.MSG_SIGNAL_FORMAT_NOT_NTSC) {
            if (ntscsignalflag == true) {
                ntscsignalflag = false;
		RpbvSmg();
                menuuiopUpdateUI(MG.getNextFocusId()); // current item - 1
                Log.d("Haha", "........Not NTSC signal........");
            }
            return;
        }

        if (msg.arg1 == GlobalConst.MSG_DERAMPANEL_DEMO_UPDATE) {
            if (dreamPanelDemoView != null) {
                dreamPanelDemoView.refreshDreamPanelDisplay(msg.getData()
                    .getIntArray("dreampanel_data"));
            }
            return;
        }

        if (_entrance.equals(GlobalConst.ENTRANCE_TYPE_ANALOG_TV)) {
            if (TunerOPDialog != null) {
                if (msg.arg1 == GlobalConst.MSG_TUNER_UPDATE_FREQUENCY) {
                    TunerOPDialog.updateTunerOpFrequency(msg.getData().getInt(
                        "tuner_freq"));
                } else if (msg.arg1 == GlobalConst.MSG_TUNER_FIND_CHANNEL) {
                    TunerOPDialog.updateTunerOpFindChannel(msg.getData()
                        .getInt("tuner_channel"));
                } else if (msg.arg1 == GlobalConst.MSG_TUNER_AUTOSEARCH_FINISHED) {
                    TunerOPDialog.updateAutoSearchFinished();
                } else if (msg.arg1 == GlobalConst.MSG_TUNER_MANUALSEARCH_FINISHED) {
                    TunerOPDialog.updateManualSearchFinished();
                } else if (msg.arg1 == GlobalConst.MSG_TUNER_MANUALSEARCH_ABORTED) {
                    TunerOPDialog.updateManualSearchAborted();
                }
            }
            return;
        }

        if (_entrance.equals(GlobalConst.ENTRANCE_TYPE_VGA)) {
            if (vgaAdjustDialog != null) {
                if (msg.arg1 == GlobalConst.MSG_VGA_AUTO_BEGIN)
                    vgaAdjustDialog.SetAdjustFlag(true);
                else if (msg.arg1 == GlobalConst.MSG_VGA_AUTO_DONE) {
                    vgaAdjustDialog.SetAdjustFlag(false);
                    if (vgaautoshowinfo == true)
                        removeVgaAdjustDialogAndAll();
                    else
                        removeVgaAdjustDialog();
                }
                vgaautoshowinfo = false;
            } else if (msg.arg1 == GlobalConst.MSG_VGA_AUTO_BEGIN) {
                vgaautoshowinfo = true;
                mControl.setVisibility(View.VISIBLE);
                // mControl.set_focus(true);
                showVgaAdjustDialog();
            }
        }
    }

}
