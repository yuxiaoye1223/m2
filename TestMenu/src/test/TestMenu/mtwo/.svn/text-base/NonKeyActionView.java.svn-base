package test.TestMenu.mtwo;




import com.skyworth.control.tvsetting;

import test.TestMenu.R;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NonKeyActionView extends LinearLayout {
	private TextView dialoginfo;
	private RemoveViewListener listener;
	private int adcAdjustCounter=0;
	private Handler mHandler = new Handler();
	
	private static final int ADC_FAILURE=1;
	private static final int ADC_SUCCESS=2;
	private static final int REMOVE_VIEW=3;
	
	public NonKeyActionView(Context context) {
		super(context);
		dialoginfo=new TextView(this.getContext());
		dialoginfo.setWidth(400);
		dialoginfo.setHeight(120);
		dialoginfo.setTextSize(64);
		dialoginfo.setBackgroundColor(0xaa0000aa);
		dialoginfo.setGravity(Gravity.CENTER);
		this.addView(dialoginfo);
		this.setFocusable(true);
		mHandler=new Handler(){

			@Override
			public void handleMessage(Message msg) {
				switch(msg.what)
				{
				case ADC_FAILURE:
					setInfoText(R.string.ADCFailure);
					break;
				case ADC_SUCCESS:
					setInfoText(R.string.ADCSuccess);
					break;
				case REMOVE_VIEW:
					listener.removeMyView();
					break;
				}
			}
			
		};
		
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch(keyCode)
		{
			case 141:
				if(dialoginfo.getText().equals(getResources().getString(R.string.busoff)))
				{
					return false;
				}
				return true;
			case 139:	//38h ADC AUTO ADJUST key
			case KeyEvent.KEYCODE_ENTER:
				if(dialoginfo.getText().equals(getResources().getString(R.string.ADCPreparing))
				||dialoginfo.getText().equals(getResources().getString(R.string.ADCFailure)))
				{
					setInfoText(R.string.ADCProgressing);
//					setInfoWidth(800);
//					setInfoHeight(120);
					Runnable myrun = new Runnable(){

						public void run() {
							try {
								tvsetting.FacSet_AdcAutoCal();
								Thread.sleep(500);
								while((tvsetting.FacGet_AdcAutoCalStatus()!=2)	// 0:undo 1:processing 2:done
										&& adcAdjustCounter++<20)				// 10s time limit
								{
									Thread.sleep(500);
								}
								Message msg=mHandler.obtainMessage();
								if(tvsetting.FacGet_AdcAutoCalResult()<0 || adcAdjustCounter>=20)
								{
									msg.what=ADC_FAILURE;
									msg.sendToTarget();
									adcAdjustCounter = 0;
								}
								else
								{
									msg.what=ADC_SUCCESS;
									msg.sendToTarget();
									adcAdjustCounter = 0;
									Thread.sleep(1000);
									msg=mHandler.obtainMessage();
									msg.what=REMOVE_VIEW;
									msg.sendToTarget();
								}

							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
						
					};
					Thread progressThread = new Thread(myrun);
					progressThread.start();
				}
				return true;	
			case KeyEvent.KEYCODE_MENU:
			case KeyEvent.KEYCODE_BACK:
				if(dialoginfo.getText().equals(getResources().getString(R.string.ADCPreparing))
					||dialoginfo.getText().equals(getResources().getString(R.string.ADCFailure)))
					listener.removeMyView();
				return true;
			default: 
				return true;
		}
//		return super.onKeyDown(keyCode, event);
	}
	public void setRemoveViewListener(RemoveViewListener tListener)
	{
		listener = tListener;
	}
	public void setInfoText(int stringId)
	{
		dialoginfo.setText(stringId);
	}
	public void setInfoWidth(int width)
	{
		dialoginfo.setWidth(width);
	}	
	public void setInfoHeight(int height)
	{
		dialoginfo.setHeight(height);
	}		
}
