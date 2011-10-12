package com.skyworth.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.skyworth.Listener.SkyworthMenuListener;
import com.skyworth.SkyworthMenu.GlobalConst;
import com.skyworth.SkyworthMenu.Menucontrol;
import com.skyworth.View.MenuItem.MenuStatus;
import com.skyworth.control.playerStatus;
import com.skyworth.control.tvsetting;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

@SuppressWarnings("unchecked")
public class MenuGroup1 extends View {

    private List<MenuItem> itemlist = new ArrayList();
    private MenuItem leftArrow;
    private MenuItem rightArrow;
    private MenuItem background;
    private MenuProgress progress = new MenuProgress();
    private List<String> Menu;
    private List<String> TvSetMenu;
    private List<String> ProgramMenu;
    private List<String> MusicMenu;
    private List<String> PicMenu;
    private List<String> TextMenu;
    private List<String> Setup3DMenu;
    private List<String> DefaultMenu;
    private int AbsoluteFoucsID = 0;
    private int RelativeFoucsID = 0;
    private int CurShowPage = 0;
    private int ItemTotal = 0;
    private String ShowState = null;
    public int leftoffset = 0;
    private SkyworthMenuListener MyCheckedMenu;
    private int myType;
    private Paint ItemPaint = new Paint();

    public MenuGroup1(Context context, AttributeSet attrs, int type) {
        super(context, attrs);
        this.setFocusable(true);
        Menu = new ArrayList<String>();
        TvSetMenu = new ArrayList<String>();
        ProgramMenu = new ArrayList<String>();
        MusicMenu = new ArrayList<String>();
        PicMenu = new ArrayList<String>();
        TextMenu = new ArrayList<String>();
        Setup3DMenu = new ArrayList<String>();
        DefaultMenu = new ArrayList<String>();
        myType = type;

        progress.setType(myType);

        ItemPaint.setColor(Color.WHITE);
        ItemPaint.setTextSize(30);
        ItemPaint.setAntiAlias(true);
        ItemPaint.setTextAlign(Paint.Align.CENTER);
//        this.setOnFocusChangeListener(focusListener);
    }

    // public void updateLanguage(){
    // Rect myRect = new Rect();
    // myRect.left = leftArrow.getRefreshRect().left;
    // myRect.top = itemlist.get(0).getRefreshRect().top;
    // myRect.right = 1920;
    // myRect.bottom = myRect.top +GlobalConst.MENU_ITEM_HIGHTH;
    // myinvalidate(myRect);
    // }

    public void updateMGUI() {
        Rect myRect = new Rect();
        myRect.left = leftArrow.getRefreshRect().left;
        myRect.top = itemlist.get(0).getRefreshRect().top;
        myRect.right = 1920;
        myRect.bottom = myRect.top + GlobalConst.MENU_ITEM_HIGHTH;
        myinvalidate(myRect);
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        background.myDraw(canvas, ItemPaint, ShowState);
        progress.myDraw(canvas);
        for (int i = 0; i < itemlist.size(); i++) {
            itemlist.get(i).myDraw(canvas, ItemPaint, ShowState);
        }
        rightArrow.myDraw(canvas, ItemPaint, ShowState);
        leftArrow.myDraw(canvas, ItemPaint, ShowState);
    }

    public void update() {
        if (CurShowPage < 0)
            return;

        hideAllItem();
        background.setVisible(true);
        int StartIndex = 0;
        int CurShowCount = 0;
        ItemTotal = Menu.size();
        StartIndex = CurShowPage * GlobalConst.MENU_COUNT_PERPAGE_SHOW;
        CurShowCount = ItemTotal - StartIndex;
        if (CurShowCount > GlobalConst.MENU_COUNT_PERPAGE_SHOW)
            CurShowCount = GlobalConst.MENU_COUNT_PERPAGE_SHOW;

        for (int i = 0; i < CurShowCount; i++) {
            if (i == RelativeFoucsID) {
                itemlist.get(StartIndex + i).setStatus(MenuStatus.focus);
            } else {
                if (checkDisableMenuItem(StartIndex + i)) {
                    itemlist.get(StartIndex + i).setStatus(MenuStatus.disable);
                } else {
                    itemlist.get(StartIndex + i).setStatus(MenuStatus.unfocus);
                }
            }
            itemlist.get(StartIndex + i).setVisible(true);
        }
        if (CurShowPage > 0)
            leftArrow.setVisible(true);
        if ((ItemTotal - StartIndex) > CurShowCount)
            rightArrow.setVisible(true);

        if (myType != MenuGroup.noplayer) {
            progress.update();
            progress.setVisible(true);
        }
    }

    protected void onLayout(boolean changed, int left, int top, int right,
        int bottom) {

    }
    
//    public void onWindowFocusChanged(boolean hasWindowFocus){
//    	Log.d("Haha","MG1 onWindowFocusChanged...hasWindowFocus:" + hasWindowFocus);
//    }
//    
//    OnFocusChangeListener focusListener = new OnFocusChangeListener(){
//        public void onFocusChange(View v, boolean hasFocus) {
//        	Log.d("Haha","MG1 onFocusChange...hasFocus:" + hasFocus);
//        }
//    };

    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	//if(MyCheckedMenu != null)
    	//	MyCheckedMenu.RemoveSourceSwitchMessage();
        switch (keyCode) {
            case 0xCF:
            case 0xCE:
            case 0xCD:
            case 0xCC:
            case 0xCB:
            case 0xCA:
            case 0xC9:
            case 0xC8:
            case 0xC7:
            case 0xC6:
            case 0xC5:
            case 0xC4:
            case 0xC3:
            case 0xC2:
            case 0xC1:
                onKeyDown(KeyEvent.KEYCODE_DPAD_LEFT, event);
                break;

            case 0xFF:
            case 0xFE:
            case 0xFD:
            case 0xFC:
            case 0xFB:
            case 0xFA:
            case 0xF9:
            case 0xF8:
            case 0xF7:
            case 0xF6:
            case 0xF5:
            case 0xF4:
            case 0xF3:
            case 0xF2:
            case 0xF1:
                onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, event);
                break;

            case KeyEvent.KEYCODE_DPAD_LEFT:
                if (AbsoluteFoucsID > 0) {
                    int oldId = AbsoluteFoucsID;
                    int oldPage = CurShowPage;
                    AbsoluteFoucsID--;
                    if (((AbsoluteFoucsID % GlobalConst.MENU_COUNT_PERPAGE_SHOW) == (GlobalConst.MENU_COUNT_PERPAGE_SHOW - 1))
                        && (CurShowPage != 0))
                        CurShowPage--;
                    int refreshCount = 2;
                    while (itemlist.get(AbsoluteFoucsID).getStatus() == MenuStatus.disable) {
                        if (AbsoluteFoucsID > 0) {
                            AbsoluteFoucsID--;
                            if (((AbsoluteFoucsID % GlobalConst.MENU_COUNT_PERPAGE_SHOW) == (GlobalConst.MENU_COUNT_PERPAGE_SHOW - 1))
                                && (CurShowPage != 0))
                                CurShowPage--;
                            refreshCount++;
                        } else {
                            AbsoluteFoucsID = oldId;
                            CurShowPage = oldPage;
                            break;
                        }
                    }

                    if (AbsoluteFoucsID != oldId) {
                        RelativeFoucsID = AbsoluteFoucsID
                            % GlobalConst.MENU_COUNT_PERPAGE_SHOW;
                        refreshUI(keyCode, refreshCount);
                        MyCheckedMenu.CheckedMenuHandle(
                            KeyEvent.KEYCODE_DPAD_LEFT, RelativeFoucsID,
                            Menu.get(AbsoluteFoucsID));
                        return super.onKeyDown(keyCode, event);
                    }
                }
                break;

            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if (AbsoluteFoucsID < (ItemTotal - 1)) {
                    int oldId = AbsoluteFoucsID;
                    int oldPage = CurShowPage;
                    AbsoluteFoucsID++;
                    if ((AbsoluteFoucsID % GlobalConst.MENU_COUNT_PERPAGE_SHOW) == 0)
                        CurShowPage++;
                    int refreshCount = 2;
                    while (itemlist.get(AbsoluteFoucsID).getStatus() == MenuStatus.disable) {
                        if (AbsoluteFoucsID < (ItemTotal - 1)) {
                            AbsoluteFoucsID++;
                            if ((AbsoluteFoucsID % GlobalConst.MENU_COUNT_PERPAGE_SHOW) == 0)
                                CurShowPage++;
                            refreshCount++;
                        } else {
                            AbsoluteFoucsID = oldId;
                            CurShowPage = oldPage;
                            break;
                        }
                    }
                    if (AbsoluteFoucsID != oldId) {
                        RelativeFoucsID = AbsoluteFoucsID
                            % GlobalConst.MENU_COUNT_PERPAGE_SHOW;
                        refreshUI(keyCode, refreshCount);
                        MyCheckedMenu.CheckedMenuHandle(
                            KeyEvent.KEYCODE_DPAD_RIGHT, RelativeFoucsID,
                            Menu.get(AbsoluteFoucsID));
                        return super.onKeyDown(keyCode, event);
                    }
                }
                break;

            case KeyEvent.KEYCODE_DPAD_UP:
            case KeyEvent.KEYCODE_DPAD_DOWN:
                MyCheckedMenu.CheckedMenuHandle(KeyEvent.KEYCODE_DPAD_UP,
                    RelativeFoucsID, Menu.get(AbsoluteFoucsID));
                break;
            case KeyEvent.KEYCODE_ENTER:
                MyCheckedMenu.CheckedMenuHandle(KeyEvent.KEYCODE_ENTER,
                    RelativeFoucsID, Menu.get(AbsoluteFoucsID));
                if (Menu.get(AbsoluteFoucsID).equals(
                    "shortcut_setup_audio_mute_panel_"))
                    return true;
                break;

            case KeyEvent.KEYCODE_BACK:
            case KeyEvent.KEYCODE_MENU: //for layer two menu UI back to layer one
                if (ShowState.equals("program") || ShowState.equals("Setup3D")) {
                	Log.d("Haha","back to layer 1..........");
                    MyCheckedMenu.BackMenuHandle();
                    return true;
                } else
                	if(keyCode == KeyEvent.KEYCODE_BACK)
                		MyCheckedMenu.CallMenucontrolunbindservice();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void initFocusID() {
        // for(int i =0; i<itemlist.size();i++)
        // if(RelativeFoucsID == i)
        // itemlist.get(i).setStatus(MenuStatus.focus);
        // else
        // itemlist.get(i).setStatus(MenuStatus.unfocus);
        update();
    }

    public int getAbsFocusID() {
        return AbsoluteFoucsID;
    }

    public void setFocusID(int FocusID) {
        AbsoluteFoucsID = FocusID;
        RelativeFoucsID = FocusID % GlobalConst.MENU_COUNT_PERPAGE_SHOW;
        CurShowPage = FocusID / GlobalConst.MENU_COUNT_PERPAGE_SHOW;

        for (int i = 0; i < itemlist.size(); i++)
            if (RelativeFoucsID == i)
                itemlist.get(i).setStatus(MenuStatus.focus);
            else
                itemlist.get(i).setStatus(MenuStatus.unfocus); // 110226
    }

    public int getNextFocusId() {
        int idx = 0;
        int desindex = 0;
        for (idx = 0; idx < itemlist.size(); idx++) {
            if ((itemlist.get(idx).getItemName()
                .equals("shortcut_setup_sys_pc_set_"))) {
                if (AbsoluteFoucsID == idx && idx > 0) {
                    if (MyCheckedMenu.CheckMenuItemStatus(
                        "shortcut_setup_sys_pc_set_", 1))
                        return idx - 1;
                }
            } else if (itemlist.get(idx).getItemName()
                .equals("shortcut_setup_audio_mute_panel_")) {
                if (AbsoluteFoucsID == idx && idx > 0) {
                    if (MyCheckedMenu.CheckMenuItemStatus(
                        "shortcut_setup_audio_mute_panel_", 1))
                        return idx + 1;
                }
            } else if (itemlist.get(idx).getItemName()
                .equals("shortcut_video_3d_setup_")) {
                if (AbsoluteFoucsID == idx) {
                    if (MyCheckedMenu.CheckMenuItemStatus(
                        "shortcut_video_3d_setup_", 1)) {
                        return idx + 1;
                    } else {
                        return AbsoluteFoucsID;
                    }
                }
            } else if (itemlist.get(idx).getItemName()
                .equals("shortcut_setup_video_hue_")) {
                if (AbsoluteFoucsID == idx && idx > 0) {
                    if (MyCheckedMenu.CheckMenuItemStatus(
                        "shortcut_setup_video_hue_", 1))
                        return idx - 1;
                }
            }
        }

        return AbsoluteFoucsID;
    }

    public void notifydataFinish() {

        Rect myrect = new Rect();
        int topSpace = 0;
        if (myType == MenuGroup.nobar_player || myType == MenuGroup.bar_player)
            topSpace = 140;
        else
            topSpace = 60;
        /********** left arrow **********/
        myrect.top = topSpace;
        if (GlobalConst.OSD_DISPLAY_HALF_FLAG == 2)
            myrect.top = myrect.top / 2;
        myrect.left = 8;
        myrect.right = myrect.left + 27;
        myrect.bottom = myrect.top + 40;
        leftArrow = new MenuItem(this.getContext(), "shortcut_bg_arrow_left",
            myrect, true);
        /********** right arrow **********/
        myrect.top = topSpace;
        if (GlobalConst.OSD_DISPLAY_HALF_FLAG == 2)
            myrect.top = myrect.top / 2;
        if (GlobalConst.OSD_DISPLAY_HALF_FLAG == 1)
            myrect.left = 1920 - 920 - 64;
        else
            myrect.left = 1885;
        myrect.right = myrect.left + 27;
        myrect.bottom = myrect.top + 40;
        rightArrow = new MenuItem(this.getContext(), "shortcut_bg_arrow_right",
            myrect, true);
        /********** background arrow **********/
        myrect.top = 0;
        myrect.left = 0;
        myrect.right = 1920;
        myrect.bottom = 200;
        String bgid = null;
        if (myType == MenuGroup.bar_player)
            bgid = "shortcut_bg_bar_progress";
        else if (myType == MenuGroup.nobar_player)
            bgid = "shortcut_bg_bar_info";
        else
            bgid = "shortcut_bg_bar";
        background = new MenuItem(this.getContext(), bgid, myrect, true);

    }

    private Rect getItemSlot(int pos) {
        if (pos >= ItemTotal)
            return null;

        getLeftOffSet();

        int leftSpace = 35;
        int topSpace = 0;
        if (GlobalConst.OSD_DISPLAY_HALF_FLAG == 1)
            leftSpace = leftSpace / 2;

        if (myType == MenuGroup.bar_player)
            topSpace = 90;
        else if (myType == MenuGroup.nobar_player)
            topSpace = 75;

        Rect mrect = new Rect();
        if (GlobalConst.OSD_DISPLAY_HALF_FLAG == 1)
            mrect.left = leftSpace + leftoffset + pos
                % GlobalConst.MENU_COUNT_PERPAGE_SHOW
                * GlobalConst.MENU_ITEM_WIDTH / 2;
        else
            mrect.left = leftSpace + leftoffset + pos
                % GlobalConst.MENU_COUNT_PERPAGE_SHOW
                * GlobalConst.MENU_ITEM_WIDTH;
        mrect.top = topSpace;
        mrect.right = mrect.left + GlobalConst.MENU_ITEM_WIDTH;
        mrect.bottom = mrect.top + GlobalConst.MENU_ITEM_HIGHTH;
        return mrect;
    }

    public void AddMenuItem(String MI, String MediaType) {
        if (MediaType.equals("tv_set"))
            TvSetMenu.add(MI);
        else if (MediaType.equals("program"))
            ProgramMenu.add(MI);
        else if (MediaType.equals("music"))
            MusicMenu.add(MI);
        else if (MediaType.equals("picture"))
            PicMenu.add(MI);
        else if (MediaType.equals("txt"))
            TextMenu.add(MI);
        else if (MediaType.equals("Setup3D"))
            Setup3DMenu.add(MI);
        else
            DefaultMenu.add(MI);
    }

    public void SetShowState(String state) {

        ShowState = state;
        // unFocusBitmapSet.clear();
        // FocusBitmapSet.clear();
        if (ShowState.equals("music")) {
            if (MusicMenu.size() != 0)
                Menu = MusicMenu;
        } else if (ShowState.equals("picture")) {
            if (PicMenu.size() != 0)
                Menu = PicMenu;
        } else if (ShowState.equals("txt")) {
            if (TextMenu.size() != 0)
                Menu = TextMenu;
        } else if (ShowState.equals("Setup3D")) {
            if (Setup3DMenu.size() != 0)
                Menu = Setup3DMenu;
        } else if (ShowState.equals("tv_set")) {
            if (TvSetMenu.size() != 0)
                Menu = TvSetMenu;
        } else if (ShowState.equals("program")) {
            if (ProgramMenu.size() != 0)
                Menu = ProgramMenu;
        } else {
            if (DefaultMenu.size() != 0)
                Menu = DefaultMenu;
        }
        ItemTotal = Menu.size();
        initAllItem();

    }

    public void showFirstSelectFrame() {
        MyCheckedMenu.CheckedMenuHandle(KeyEvent.KEYCODE_DPAD_LEFT,
            RelativeFoucsID, Menu.get(AbsoluteFoucsID));
    }

    public void setMenuGroupListener(SkyworthMenuListener MGL) {
        MyCheckedMenu = MGL;
    }

    // //////////////////////////subsidiary get
    // info//////////////////////////////////////
    public String getAbsoluteFoucsItem() {
        return Menu.get(AbsoluteFoucsID);
    }

    public int getAbsoluteFoucsID() {
        return AbsoluteFoucsID;
    }

    public int getRelativeFoucsID() {
        return RelativeFoucsID;
    }

    public void getLeftOffSet() {
        ItemTotal = Menu.size();
        if (ItemTotal < GlobalConst.MENU_COUNT_PERPAGE_SHOW)
            this.leftoffset = (GlobalConst.MENU_COUNT_PERPAGE_SHOW - ItemTotal)
                / 2 * GlobalConst.MENU_ITEM_WIDTH
                + (GlobalConst.MENU_COUNT_PERPAGE_SHOW - ItemTotal) % 2
                * GlobalConst.MENU_ITEM_WIDTH / 2;
        else
            this.leftoffset = 0;

    }

    public String GetShowState() {
        return ShowState;
    }

    public int GetFocusID(String id) {
        int focus = -1;
        int total = Menu.size();
        for (int i = 0; i < total; i++)
            if (Menu.get(i).equals(id)) {
                focus = i;
                break;
            }
        return focus;
    }

    // //////////////////////////finish//////////////////////////////////////////////

    // ////////////////////progress start///////////////////////////
    public void set_seek_bar_info(int i_total, int i_cur_pos) {

        progress.bartotal = i_total;
        progress.bar_cur_pos = i_cur_pos;
        // this.postInvalidate(40, 70+BeatHight,1880,90+BeatHight);
        // this.postInvalidate(1620, 25+BeatHight,1900,60+BeatHight);
        this.myinvalidate(40, 25, 1900, 90);

    }

    public void set_play_name(String name) {
        progress.playername = name;
        if (myType == MenuGroup.bar_player)
            this.myinvalidate(80, 20, 980, 60);
        else if (myType == MenuGroup.nobar_player)
            this.myinvalidate(80, 20, 980, 70);
    }

    public void set_seek_bar_info(String type, int i_total, int i_cur_pos) {
        if (ShowState.equals(type)) {
            progress.bartotal = i_total;
            progress.bar_cur_pos = i_cur_pos;
            // this.postInvalidate(40, 70+BeatHight,1880,90+BeatHight);
            // this.postInvalidate(1620, 25+BeatHight,1900,60+BeatHight);
            this.myinvalidate(40, 70, 1900, 90);
        }
    }

    public void set_play_name(String type, String name) {
        if (ShowState.equals(type)) {
            progress.playername = name;
            if (myType == MenuGroup.bar_player)
                this.myinvalidate(80, 20, 980, 60);
            else if (myType == MenuGroup.nobar_player)
                this.myinvalidate(80, 20, 980, 70);
        }
    }

    public void setplayerPosScale(String type, String scale) {
        if (ShowState.equals(type)) {
            progress.playerPosScale = scale;
            this.myinvalidate(1520, 25, 1620, 60);
        }
    }

    public void setplayerPosScale(String scale) {
        progress.playerPosScale = scale;
        this.myinvalidate(1520, 25, 1620, 60);
    }

    // ///////////////////////progress
    // finish////////////////////////////////////

    public void UpdataStatus(String ID) {
        Menu.set(AbsoluteFoucsID, ID);
        clearItemBitmapAndsetID(AbsoluteFoucsID, ID);
        myinvalidate(itemlist.get(AbsoluteFoucsID).getRefreshRect());
    }

    public int HandleSpecialKeyID(int key) {
        int focus = -1;
        if (key == 85) // play_PAUSE
        {
            int total = Menu.size();
            for (int i = 0; i < total; i++)
                if (Menu.get(i).equals("shortcut_common_play_")
                    || Menu.get(i).equals("shortcut_common_pause_")) {
                    focus = i;
                    break;
                }
        } else if (key == 86) // play_STOP
        {
            int total = Menu.size();
            for (int i = 0; i < total; i++)
                if (Menu.get(i).equals("shortcut_common_stop_")) {
                    focus = i;
                    break;
                }
        } else if (key == 90) // play_FF
        {
            int total = Menu.size();
            for (int i = 0; i < total; i++)
                if (Menu.get(i).contains("shortcut_common_ff")) {
                    focus = i;
                    break;
                }
        } else if (key == 92)// play_FB
        {
            int total = Menu.size();
            for (int i = 0; i < total; i++)
                if (Menu.get(i).contains("shortcut_common_fb")) {
                    focus = i;
                    break;
                }
        } else if (key == 88)// play_PROE
        {
            int total = Menu.size();
            for (int i = 0; i < total; i++)
                if (Menu.get(i).contains("prev")) {
                    focus = i;
                    break;
                }
        } else if (key == 87) // play_NEXT
        {
            int total = Menu.size();
            for (int i = 0; i < total; i++)
                if (Menu.get(i).contains("next")) {
                    focus = i;
                    break;
                }
        } else if (key == 91) // mute
        {
            int total = Menu.size();
            for (int i = 0; i < total; i++)
                if (Menu.get(i).contains("shortcut_common_mute_")) {
                    focus = i;
                    break;
                }
        }
        return focus;
    }

    public void UpdataMenuData(Map<Integer, String> map) {
        for (int i = 0; i < GlobalConst.MENU_COUNT_PERPAGE_SHOW; i++) {
            String data = map.get(i);
            if (data != null) {
                Menu.set(i, data);
                clearItemBitmapAndsetID(i, data);
            }
        }
        if (myType == MenuGroup.bar_player)
            this.myinvalidate(0, 90, this.getWidth(), this.getHeight());
        else if (myType == MenuGroup.nobar_player)
            this.myinvalidate(0, 75, this.getWidth(), this.getHeight());
        else
            this.myinvalidate();
    }

    public void recoverPlayerStatus(List<String> list) {
        if (list == null)
            return;
        for (int i = 0; i < list.size(); i++) {
            int total = Menu.size();
            for (int ii = 0; ii < total; ii++) {
                if (list.get(i).equals(playerStatus.player_PAUSE)
                    && Menu.get(ii).contains("shortcut_common_pause_")) {
                    Menu.set(ii, "shortcut_common_play_");
                    clearItemBitmapAndsetID(ii, "shortcut_common_play_");
                    break;
                } else if (list.get(i).equals(playerStatus.player_PLAY)
                    && Menu.get(ii).contains("shortcut_common_play_")) {
                    Menu.set(ii, "shortcut_common_pause_");
                    clearItemBitmapAndsetID(ii, "shortcut_common_pause_");
                    break;
                } else if (list.get(i).equals(playerStatus.player_FB)
                    && Menu.get(ii).contains("shortcut_common_fb")) {
                    Menu.set(ii, "shortcut_common_fb_");
                    clearItemBitmapAndsetID(ii, "shortcut_common_fb_");
                    break;
                } else if (list.get(i).equals(playerStatus.player_FF)
                    && Menu.get(ii).contains("shortcut_common_ff")) {
                    Menu.set(ii, "shortcut_common_ff_");
                    clearItemBitmapAndsetID(ii, "shortcut_common_ff_");
                    break;
                }
            }

        }
        if (myType == MenuGroup.bar_player)
            this.myinvalidate(0, 90, this.getWidth(), this.getHeight());
        else if (myType == MenuGroup.nobar_player)
            this.myinvalidate(0, 75, this.getWidth(), this.getHeight());
        else
            this.myinvalidate();
    }

    public void recoverPlayerStatus(String type, List<String> list) {
        if (list == null)
            return;
        if (ShowState.equals(type))
            recoverPlayerStatus(list);
        else {
            List<String> tempMenu = null;
            if (type.equals("music")) {
                if (MusicMenu.size() != 0)
                    tempMenu = MusicMenu;
            } else if (type.equals("picture")) {
                if (PicMenu.size() != 0)
                    tempMenu = PicMenu;
            } else if (type.equals("txt")) {
                if (TextMenu.size() != 0)
                    tempMenu = TextMenu;
            }
            if (tempMenu == null)
                return;
            for (int i = 0; i < list.size(); i++) {
                int total = tempMenu.size();
                for (int ii = 0; ii < total; ii++) {
                    if (list.get(i).equals(playerStatus.player_PAUSE)
                        && tempMenu.get(ii).contains("shortcut_common_pause_")) {
                        tempMenu.set(ii, "shortcut_common_play_");
                        break;

                    } else if (list.get(i).equals(playerStatus.player_PLAY)
                        && tempMenu.get(ii).contains("shortcut_common_play_")) {
                        tempMenu.set(ii, "shortcut_common_pause_");
                        break;
                    } else if (list.get(i).equals(playerStatus.player_FB)
                        && tempMenu.get(ii).contains("shortcut_common_fb")) {
                        tempMenu.set(ii, "shortcut_common_fb_");
                        break;
                    } else if (list.get(i).equals(playerStatus.player_FF)
                        && tempMenu.get(ii).contains("shortcut_common_ff")) {
                        tempMenu.set(ii, "shortcut_common_ff_");
                        break;
                    }

                }
            }
        }
    }

    private void clearItemBitmapAndsetID(int id, String itemID) {
        if (id < itemlist.size()) {
            if (itemlist.get(id) != null) {
                itemlist.get(id).recycleBitmap();
                itemlist.get(id).setItemID(itemID);
            }
        }
    }

    public void initAllItem() {
        for (int i = 0; i < itemlist.size(); i++) {
            clearItemBitmapAndsetID(i, null);
        }
        if (itemlist.size() > 0)
            itemlist.removeAll(itemlist);
        for (int i = 0; i < Menu.size(); i++)
            itemlist.add(new MenuItem(this.getContext(), Menu.get(i), getItemSlot(i)));
        // for(int i =0; i<itemlist.size();i++)
        // if(RelativeFoucsID == i)
        // itemlist.get(i).setStatus(MenuStatus.focus);
        // else
        // itemlist.get(i).setStatus(MenuStatus.unfocus);
        setFocusID(0);
        // for(int i =0; i<itemlist.size();i++)
        // if(RelativeFoucsID == i)
        // itemlist.get(i).setStatus(MenuStatus.focus);
        // else
        // itemlist.get(i).setStatus(MenuStatus.unfocus);

    }

    public void initAllItemStatus() {
        for (int i = 0; i < itemlist.size(); i++) {
            clearItemBitmapAndsetID(i, null);
        }
        if (itemlist.size() > 0)
            itemlist.removeAll(itemlist);
        for (int i = 0; i < Menu.size(); i++)
            itemlist.add(new MenuItem(this.getContext(), Menu.get(i), getItemSlot(i)));
        // for(int i =0; i<itemlist.size();i++)
        // if(RelativeFoucsID == i)
        // itemlist.get(i).setStatus(MenuStatus.focus);
        // else
        // itemlist.get(i).setStatus(MenuStatus.unfocus);
        // setFocusID(0);
        // for(int i =0; i<itemlist.size();i++)
        // if(RelativeFoucsID == i)
        // itemlist.get(i).setStatus(MenuStatus.focus);
        // else
        // itemlist.get(i).setStatus(MenuStatus.unfocus);

    }

    // ///////////////////////////private
    // function////////////////////////////////////
    private void hideAllItem() {
        for (int i = 0; i < itemlist.size(); i++) {
            itemlist.get(i).setVisible(false);
            itemlist.get(i).setStatus(MenuStatus.unfocus);
        }
        rightArrow.setVisible(false);
        leftArrow.setVisible(false);
        progress.setVisible(false);
    }

    private void refreshUI(int keyCode, int total) {
        Rect myRect = new Rect();
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_LEFT:
                if (RelativeFoucsID < GlobalConst.MENU_COUNT_PERPAGE_SHOW - 1) {
                    myRect.left = itemlist.get(AbsoluteFoucsID)
                        .getRefreshRect().left;
                    myRect.top = itemlist.get(AbsoluteFoucsID).getRefreshRect().top;
                    myRect.right = myRect.left + GlobalConst.MENU_ITEM_WIDTH
                        * total;
                    myRect.bottom = myRect.top + GlobalConst.MENU_ITEM_HIGHTH;
                } else {
                    myRect.left = leftArrow.getRefreshRect().left;
                    myRect.top = itemlist.get(0).getRefreshRect().top;
                    myRect.right = 1920;
                    myRect.bottom = myRect.top + GlobalConst.MENU_ITEM_HIGHTH;
                }
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:

                if (RelativeFoucsID == 0
                    && (AbsoluteFoucsID % GlobalConst.MENU_COUNT_PERPAGE_SHOW == 0)) {
                    myRect.left = 8;
                    myRect.top = itemlist.get(0).getRefreshRect().top;
                    myRect.right = 1920;
                    myRect.bottom = myRect.top + GlobalConst.MENU_ITEM_HIGHTH;
                } else {
                    myRect.left = itemlist.get(AbsoluteFoucsID - (total - 1))
                        .getRefreshRect().left;
                    myRect.top = itemlist.get(AbsoluteFoucsID - (total - 1))
                        .getRefreshRect().top;
                    myRect.right = myRect.left + GlobalConst.MENU_ITEM_WIDTH
                        * total;
                    myRect.bottom = myRect.top + GlobalConst.MENU_ITEM_HIGHTH;
                }

                break;

        }
        if (GlobalConst.OSD_DISPLAY_HALF_FLAG == 2)
            myRect.bottom = myRect.bottom + 540;
        myRect.left = 0;
        myRect.right = 1920;

        myinvalidate(myRect);
    }

    public void myinvalidate(Rect myRect) {
        update();
        this.invalidate(myRect);
    }

    public void myinvalidate(int i, int j, int k, int l) {
        update();
        this.invalidate(i, j, k, l);
    }

    public void myinvalidate() {
        update();
        this.invalidate();
    }

    private boolean checkDisableMenuItem(int id) {
        if (Menu.get(id).equals("shortcut_setup_sys_woofer_vol_")) {
            if (tvsetting.GetCurAudioSupperBassSwitch() == 0)
                return true;
        } else if (Menu.get(id).equals("shortcut_setup_audio_voice_")
            || Menu.get(id).equals("shortcut_setup_audio_increase_bass_")) {
            if (tvsetting.GetCurAudioSRSSurround() == 0)
                return true;
        } else if (Menu.get(id).equals("shortcut_setup_audio_mute_panel_")) {
            return MyCheckedMenu.CheckMenuItemStatus(
                "shortcut_setup_audio_mute_panel_", id);
        } else if (Menu.get(id).equals("shortcut_setup_sys_pc_set_")) {
            return MyCheckedMenu.CheckMenuItemStatus(
                "shortcut_setup_sys_pc_set_", id);
        } else if (Menu.get(id).equals("shortcut_setup_video_hue_")) {
            return MyCheckedMenu.CheckMenuItemStatus(
                "shortcut_setup_video_hue_", id);
        } else if (Menu.get(id).equals("shortcut_setup_video_dnr_")) {
            return MyCheckedMenu.CheckMenuItemStatus(
                "shortcut_setup_video_dnr_", id);
        } else if (Menu.get(id).equals("shortcut_video_3d_setup_")) {
            return MyCheckedMenu.CheckMenuItemStatus(
                "shortcut_video_3d_setup_", id);
        } else if (Menu.get(id).equals("shortcut_video_3d_lr_switch_")
            || Menu.get(id).equals("shortcut_video_3d_3d2d_")) {
        	
        	if (Menu.get(id).equals("shortcut_video_3d_lr_switch_")){
	        	if(MyCheckedMenu.CheckMenuItemStatus("shortcut_video_3d_3d2d_", id))
	        		return true;
        	}
        	
        	if((tvsetting.entrance.equals(GlobalConst.ENTRANCE_TYPE_ANALOG_TV))
                    || (tvsetting.entrance.equals(GlobalConst.ENTRANCE_TYPE_AV))
                    || (tvsetting.entrance.equals(GlobalConst.ENTRANCE_TYPE_YUV))
                    || (tvsetting.entrance.equals(GlobalConst.ENTRANCE_TYPE_HDMI))
                    || (tvsetting.entrance.equals(GlobalConst.ENTRANCE_TYPE_VGA))) {
        	
	        	if(tvsetting.entrance.equals(GlobalConst.ENTRANCE_TYPE_HDMI)){
	        		//if(tvsetting.GetSigTransFormat() == 0)
	        		//	return true;
	        		//else if(tvsetting.GetSigTransFormat()>5)
	        		//	return false;
	        		
	        		if(tvsetting.GetSigTransFormat() == 0){
	        		//if(tvsetting.Get3DStatus()!=1){
	            		if(tvsetting.Get3DStatus()==3||tvsetting.Get3DStatus()==4)
	            			return false;
	            		else 
	            			return true;
	            	}
	            	else
	            		return false;
	        	}
	        	else if(tvsetting.entrance.equals(GlobalConst.ENTRANCE_TYPE_YUV)){
            		if(tvsetting.Get3DStatus()==3||tvsetting.Get3DStatus()==4)
            			return false;
            		else 
            			return true;
	        	}
	        	else
	        		return true;
        	}
        	else
        	{
        		
        		if(tvsetting.Get3DStatus()!=1){
        		//if(tvsetting.GetSigTransFormat()<=5){
        			if(tvsetting.Get3DStatus()==3||tvsetting.Get3DStatus()==4)
        				return false;
        			else 
        				return true;
        		}
        		else
        			return false;
        	}
        		
        } else if (Menu.get(id).equals("shortcut_video_3d_dof_")) {
        	if((tvsetting.entrance.equals(GlobalConst.ENTRANCE_TYPE_ANALOG_TV))
                    || (tvsetting.entrance.equals(GlobalConst.ENTRANCE_TYPE_AV))
                    || (tvsetting.entrance.equals(GlobalConst.ENTRANCE_TYPE_YUV))
                    || (tvsetting.entrance.equals(GlobalConst.ENTRANCE_TYPE_HDMI))
                    || (tvsetting.entrance.equals(GlobalConst.ENTRANCE_TYPE_VGA))) {
	            //if (tvsetting.GetSigTransFormat()<=5) {
	            	if(tvsetting.Get3DStatus() == 2)
	            		return false;
	            	else
	            		return true;
	            //} 
	            //else
	            //	return false;
	        }
        	else
        	{
        		//if(tvsetting.Get3DStatus()!=1){
					if (tvsetting.Get3DStatus() == 2)
						return false;
					else
						return true;
				//} else
				//	return false;
        	}
        } else if (Menu.get(id).equals("shortcut_setup_video_display_mode_")) {
        	if((tvsetting.entrance.equals(GlobalConst.ENTRANCE_TYPE_ANALOG_TV))
                    || (tvsetting.entrance.equals(GlobalConst.ENTRANCE_TYPE_AV))
                    || (tvsetting.entrance.equals(GlobalConst.ENTRANCE_TYPE_YUV))
                    || (tvsetting.entrance.equals(GlobalConst.ENTRANCE_TYPE_HDMI))
                    || (tvsetting.entrance.equals(GlobalConst.ENTRANCE_TYPE_VGA))) {
        		if(MyCheckedMenu.GetNosignalFlag())
        			return true;
	        	if(tvsetting.GetSigTransFormat()==0
	        			&&(tvsetting.Get3DStatus()==0||tvsetting.Get3DStatus()==1)) 
	                return false;
	        	else
	        		return true;
        	}
        	else
        	{
        		if(tvsetting.Get3DStatus()==0)
        			return false;
        		else
        			return true;
        	}
        } else if (Menu.get(id).equals("shortcut_common_sync_play_")) {
            return MyCheckedMenu.CheckMenuItemStatus("shortcut_common_sync_play_", id);
        }

        return false;
    }

}
