package com.skyworth.SkyworthMenu;

import android.content.Context;
import android.view.View;
import android.widget.AbsoluteLayout;

import com.skyworth.View.VolumeMuteInfoView;
import com.skyworth.control.tvsetting;

public class OtherInfoControl {
    private VolumeMuteInfoView volumeMuteInfoView = null;
    private Context mcontext;

    public OtherInfoControl(Context context) {
        mcontext = context;
    }

    public void initVolumeMuteInfo(AbsoluteLayout layout) {
        if (volumeMuteInfoView == null) {
            volumeMuteInfoView = new VolumeMuteInfoView(mcontext);
            AbsoluteLayout.LayoutParams paramp = new AbsoluteLayout.LayoutParams(
                213, 97, 5, 1080 - 302);
            layout.addView(volumeMuteInfoView, paramp);
            volumeMuteInfoView.setVisibility(View.INVISIBLE);
        }
    }

    public void initPlayerVolumeMuteInfo(AbsoluteLayout layout) {
        if (volumeMuteInfoView == null) {
            volumeMuteInfoView = new VolumeMuteInfoView(mcontext);
            AbsoluteLayout.LayoutParams paramp = new AbsoluteLayout.LayoutParams(
                213, 97, 5, 1080 - 302 - 86);
            layout.addView(volumeMuteInfoView, paramp);
            volumeMuteInfoView.setVisibility(View.INVISIBLE);
        }
    }
    
    public void initAdvancedSetupMuteInfo(AbsoluteLayout layout) {
        if (volumeMuteInfoView == null) {
            volumeMuteInfoView = new VolumeMuteInfoView(mcontext);
            AbsoluteLayout.LayoutParams paramp = new AbsoluteLayout.LayoutParams(
                213, 97, 5, 1080 - 302 - 86 - 50);
            layout.addView(volumeMuteInfoView, paramp);
            volumeMuteInfoView.setVisibility(View.INVISIBLE);
        }
    }
    
    public void initMusicAlbumMuteInfo(AbsoluteLayout layout) {
        if (volumeMuteInfoView == null) {
            volumeMuteInfoView = new VolumeMuteInfoView(mcontext);
            AbsoluteLayout.LayoutParams paramp = new AbsoluteLayout.LayoutParams(
                213, 97, 5, 1080 - 302 - 86 - 130);
            layout.addView(volumeMuteInfoView, paramp);
            volumeMuteInfoView.setVisibility(View.INVISIBLE);
        }
    }
    
    public void setVisibile() {
        if (volumeMuteInfoView != null) {
            volumeMuteInfoView.setVisibility(View.VISIBLE);
        }
    }
    
    public void setVisibile(boolean force) {
        if(tvsetting.ismCustomVolumeMute() || force){
        	setVisibile();
        }
    }
    
    public void setInVisibile() {
        if (volumeMuteInfoView != null) {
            volumeMuteInfoView.setVisibility(View.INVISIBLE);
        }
    }
    
    public void updateIcon(){
        if (volumeMuteInfoView != null) {
            volumeMuteInfoView.setShowData();
            volumeMuteInfoView.invalidate();
        }
    }
    
    public void setMute() {
        setVisibile();
        tvsetting.setmCustomVolumeMute(true); // Mute On
    }

    public void setUnMute() {
        setInVisibile();
        tvsetting.setmCustomVolumeMute(false); // Mute Off
    }

    public void ChangeVolumeMuteInfoStatus() {
        if (tvsetting.ismCustomVolumeMute()) {
            setUnMute();
        } else {
            setMute();
        }
    }

}
