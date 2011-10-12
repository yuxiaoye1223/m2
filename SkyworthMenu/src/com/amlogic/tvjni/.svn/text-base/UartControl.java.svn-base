package com.amlogic.tvjni;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.AttributeSet;
import android.util.Log;


public class UartControl {
    private Context mContext;
    String Tag = "MultakControl";
    
    public UartControl(Context context, AttributeSet attrs) {
        mContext = context;
        mContext.startService(mIntent);
        mContext.bindService(mIntent, mConnection, Context.BIND_AUTO_CREATE);
    }

    private Intent mIntent = new Intent("com.amlogic.tvjni.TvService");
    private Itvservice mItvservice = null;
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.d(Tag, "......UartControl onServiceConnected start......\n");
           mItvservice = Itvservice.Stub.asInterface(service);
        }
        public void onServiceDisconnected(ComponentName className) {
            Log.d(Tag, "......UartControl onServiceDisconnected .........\n");
            mItvservice = null;
        }
    };
    public void uartSendVolumn(String vol)
    {	
    	try {
				mItvservice.UartSend("shortcut_common_vol_", vol);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
    	return;
    }
    public String getParams(String Type)
    {
    	int ret = 0;
    	String Temp = null;
    	if (Type.equals("Volumn"))
    	{
    		try {
				ret = mItvservice.GetTvProgressBar("shortcut_common_vol_");
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		return String.valueOf(ret);
    	}
    	else if (Type.equals("CHANNEL"))
    	{
    		try {
				ret = mItvservice.GetTvParam("shortcut_common_source_");
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		switch(ret)
    		{
    		case 0:
    			Temp = "TV";
    			break;
    		case 1:
    			Temp = "AV";
    			break;
    		case 2:
    			Temp = "YUV";
    			break;
    		case 3:
    			Temp = "HDMI1";
    			break;
    		case 4:
    			Temp = "HDMI2";
    			break;
    		case 5:
    			Temp = "HDMI3";
    			break;
    		case 6:
    			Temp = "VGA";
    			break;
    		}
    		return Temp;
    	}
    	else if (Type.equals("PictureMode"))
    	{
    		try {
				ret = mItvservice.GetTvParam("shortcut_setup_video_picture_mode_");
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		switch(ret)
    		{
	    		case 0:
	    			Temp = "STD";
	    			break;
	    		case 1:
	    			Temp = "VIVID";
	    			break;
	    		case 2:
	    			Temp = "SOFT";
	    			break;
	    		case 3:
	    			Temp = "USER";
	    			break;
    		}
    		return Temp;
    	}
    	else if (Type.equals("SoundMode"))
    	{
    		try {
				ret = mItvservice.GetTvParam("shortcut_setup_audio_sound_mode_");
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		switch(ret)
    		{
	    		case 0:
	    			Temp = "STD";
	    			break;
	    		case 1:
	    			Temp = "MUSIC";
	    			break;
	    		case 2:
	    			Temp = "NEWS";
	    			break;
	    		case 3:
	    			Temp = "THEATER";
	    			break;
	    		case 4:
	    			Temp = "USER";
	    			break;
    		}
    		return Temp;
    	}
    	else
    	{
    		return null;
    	}
    }
    public void SourceSwitch(String channel) {
    	Bundle tvbundle = new Bundle();
		if (channel.equals("shortcut_common_source_tv")) {
			tvbundle.putString("mode", "TV");
		} else if (channel.equals("shortcut_common_source_av1")) {
			tvbundle.putString("mode", "AV1");
		} else if (channel.equals("shortcut_common_source_yuv1")) {
			tvbundle.putString("mode", "YUV1");
		} else if (channel.equals("shortcut_common_source_hdmi1")) {
			tvbundle.putString("mode", "HDMI1");
		} else if (channel.equals("shortcut_common_source_hdmi2")) {
			tvbundle.putString("mode", "HDMI2");
		} else if (channel.equals("shortcut_common_source_hdmi3")) {
			tvbundle.putString("mode", "HDMI3");
		} else if (channel.equals("shortcut_common_source_vga")) {
			tvbundle.putString("mode", "VGA");
		} 
		Intent intent = new Intent("android.intent.action.testMenu");
		intent.putExtras(tvbundle);
		mContext.startActivity(intent);
		
    }
    
    public void VoiceSwitch(String channel) {
    	String get = channel;
    	if (channel.equals("shortcut_setup_audio_sound_mode_std")) {
    		
		} else if (channel.equals("shortcut_setup_audio_sound_mode_music")) {

		} else if (channel.equals("shortcut_setup_audio_sound_mode_news")) {

		} else if (channel.equals("shortcut_setup_audio_sound_mode_theater")) {

		} else if (channel.equals("shortcut_setup_audio_sound_mode_user")) {

		} else
		{
			get = null;
			Log.e(Tag,"VoiceSwitch Error Input");
			return;
		}
    	get = channel;
    	try {
				mItvservice.UartSend(channel, "0");
			} catch (RemoteException e) {
				e.printStackTrace();
			}
    	
    	return;
		//调用服务的函数
    }
    
    public void PhotoSwitch(String channel) {
    	String get = channel;
    	if (channel.equals("shortcut_setup_video_picture_mode_std")) {
    		
		} else if (channel.equals("shortcut_setup_video_picture_mode_vivid")) {
			
		} else if (channel.equals("shortcut_setup_video_picture_mode_soft")) {
			
		} else if (channel.equals("shortcut_setup_video_picture_mode_user")) {
			
	    } else
		{
			get = null;
			Log.e(Tag,"PhotoSwitch Error Input");
			return;
		}
		get = channel;
		
		try {
				mItvservice.UartSend(channel, "0");
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		return;
		
    }

    public void CallTvJni(String value) {
		/*if (mItvservice != null)
			try {
				//call tvjni function
				//volumn example
				mItvservice.UartSend("shortcut_common_vol_", value);
			} catch (RemoteException e) {
				e.printStackTrace();
			}*/
    }

    private boolean unbindFlag = true;
    public void CallUartcontrolunbindservice() {
        if (unbindFlag) {
            unbindFlag = false;
            mContext.unbindService(mConnection);
            Log.d("uart",
                    "........ call Uartcontrol unbindService(mConnection) .........\n");
        }
    }
    // rembemer to unbindservice when you exit your application

}