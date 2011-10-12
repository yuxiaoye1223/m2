package test.TestMenu.mtwo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import com.skyworth.SkyworthMenu.GlobalConst;
import com.amlogic.tvjni.Itvservice;
import test.TestMenu.R;
import test.TestMenu.TestMenu;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AbsoluteLayout;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.os.SystemProperties;

import com.skyworth.MenuDataBase;
import com.skyworth.control.tvsetting;


/********************************************
 * 
 * @author Skyworh.sunny
 * 该activity显示工厂模式菜单，按菜单键向下切换菜单页，上下键选中item，左右键调节item或进入该选项调节
 * 添加菜单页在setListData（）方法中进行，按左右键处理在optionChangeHandler()方法中进行
 * 从eeprom中取值使用eepromRead()方法，写入eeprom使用eepromWhrite()方法
 ********************************************/
public class FactoryMenuMain extends Activity{
    /** Called when the activity is first created. */
	private int systembusy = 1;
	private View viewFactoryMenuMain,viewPassword;			//FactoryMenuMain这个activity的view
	private ListView list;						//ListView的view
	private LayoutInflater inflater;
	private ArrayList<HashMap<String, Object>> listitem;	//ListView的数据列表
	private SimpleAdapter factoryListAdapter;
	private RelativeLayout layout;
	private TextView cpuDateText,eepDateText,sortwareVersionText,selectedItemName,selectedItemContent,ubootVerText;
	private EditText passwordInputView;
	private Button ensureButton,cancelButton,backspaceButton,lock1Button,lock2Button,lock3Button,lock4Button,lock5Button,lock6Button;
	private BarcodeView viewBarCode;
	
	private NonKeyActionView viewInfoText;
	
	//以下为工厂模式需调节的数据，其余数据应从EEPROM中读取
	public static int rGain,gGain,bGain,rOff,gOff,bOff;	
	public static boolean singleKeySwitchOn = true;
	private boolean eepromDataChanged = false;
//	private int eepromAddressPage,eepromAddressOffset,eepromData;	//存储当前修改数值的eep信息
	private int dataOfAddressPage=8888,dataOfAddressOffset=8888,dataOfData=8888;	//存储eeprom操作页里的页码、偏移量和数据信息
	private int initItemId=0;
	private int dataEMCCycle = 0,dataEMCAmp=1,dataEMCAmpLow=0;
	private int hdmiEQMax = GetEqModeMax();
//	private int hdmiEQMax = 8;	
	private boolean dataEMCSwitch=false,bMmode=false,bInitMenu=false,bOutsetOK=false,bEditable = false,
					bHdmiEqNotSaved=false, bHdmiIntNotSaved=false, bFacResetReady = false;
	
	private static final int	PAGE_MAIN=0;
	private static final int	PAGE_WHITEBALANCE=1;
	private static final int	PAGE_EMC_CONTROL=2;
	private static final int	PAGE_ADVANCED=3;
	private static final int	PAGE_ADVANCED_PAGE2=4;
	private static final int	PAGE_EEPROM_RW=5;
	private static final int	PAGE_ADVANCED_START=PAGE_ADVANCED;
	private static final int	PAGE_ADVANCED_END=PAGE_ADVANCED_PAGE2;
	private static final String PASSWORD = "123456";
	final static int MAC_LEN = 6;
	private int[] panelID = {R.string.panel42e83,R.string.panel47e83,
			R.string.panel55e83,R.string.panel42e85,R.string.panel47e85};
	

	private int pageNum;
	private String cpuDate,softwareVersion;

	private Handler handler;
	private Thread progressThread;
	tvsetting tvset;
	private boolean initFirstKeyPressed=false;
	private String str_lan = "";
	private StringBuffer numberArea = new StringBuffer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	tvset=new tvsetting(this);
    	this.bindService(mIntent, mConnection, Context.BIND_AUTO_CREATE); //test
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		inflater = (LayoutInflater) this
		.getSystemService(FactoryMenuMain.LAYOUT_INFLATER_SERVICE);
		viewFactoryMenuMain = inflater.inflate(R.layout.factorymenu_main, null);
		
		layout = (RelativeLayout)viewFactoryMenuMain.findViewById(R.id.main_layout);
		setContentView(viewFactoryMenuMain);

		//软件版本
		cpuDateText=(TextView)viewFactoryMenuMain.findViewById(R.id.CPU_Date);
		sortwareVersionText = (TextView)viewFactoryMenuMain.findViewById(R.id.softwareVersion);
		String tempString[] =SystemProperties.get("ro.version").split("\\.");
		softwareVersion = SystemProperties.get("ro.version");
		{
			int stringCheck = 0;
			for(;stringCheck<tempString.length;stringCheck++)
			{
				if(tempString[stringCheck].length() != 4)
					break;
			}
			if(stringCheck == 4)
			{
				cpuDate = "8A02-VER"+tempString[1].substring(0, 1)+"."+tempString[1].substring(1, 4)+"-11"+tempString[2];
			}
			else
			{
				cpuDate = "8A02_VERSION_ERROR";
			}
		}
		cpuDateText.setText(cpuDate);
		sortwareVersionText.setText(softwareVersion);
		///eeprom日期
		String temps=tvsetting.LoadEepromDate();
		eepDateText = (TextView)viewFactoryMenuMain.findViewById(R.id.EEP_Date);
		eepDateText.setText("8A02-"+temps);
		//uboot版本获取
		String varible = "ubootversion"; //ubootversion地址在spi里面的变量名
	    String prefix = SystemProperties.get("ro.ubootenv.varible.prefix"); //首先获得参数区变量的前缀
	    temps = SystemProperties.get(prefix + "." + varible); //带上前缀获得ubootversion
	    ubootVerText = (TextView)viewFactoryMenuMain.findViewById(R.id.ubootVersion);
	    ubootVerText.setText(temps);
		
//		itemInfoText=(TextView)viewFactoryMenuMain.findViewById(R.id.item1_info);
		
		Intent intent=getIntent();
		pageNum = intent.getIntExtra("listpage", 0);

		//获取listView的view
		list=(ListView)viewFactoryMenuMain.findViewById(R.id.FactoryMainList);
		//下面这句是自定义选中item显示效果，去掉这一句可以使白平衡数据调节速度每10秒增加4个按键
		//		list.setSelector(R.drawable.spacebar);		
		
		setListData();	//为list的数据列表赋值、创建适配器、绑定适配器。
		list.setOnItemClickListener(new AdapterView.OnItemClickListener(){

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) 
			{
				itemEnterKeyProcess(arg0, arg1, arg2,arg3);
			}
		});
		list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				selectedItemName = (TextView) arg1.findViewById(R.id.ListText);
				selectedItemContent = (TextView) arg1.findViewById(R.id.ListButton);
				if(initFirstKeyPressed)		//当第一次确认初始化后按上下键会进入这里
				{
					initFirstKeyPressed = false;
					HashMap<String, Object> tempMap = new HashMap<String, Object>();
					tempMap = listitem.get(initItemId);
					tempMap.put("itemOption", getResources().getString(R.string.start));
					listitem.set(initItemId, tempMap);
					factoryListAdapter.notifyDataSetChanged();
				}
				if(bHdmiEqNotSaved)
				{
					bHdmiEqNotSaved = false;
					HashMap<String, Object> tempMap = new HashMap<String, Object>();
					tempMap = listitem.get(initItemId);
					int value = tvsetting.FacGetHdmiEQMode();
					tempMap.put("itemOption", value);
					listitem.set(initItemId, tempMap);
					factoryListAdapter.notifyDataSetChanged();
				}
				if(bHdmiIntNotSaved)
				{
					bHdmiIntNotSaved = false;
					HashMap<String, Object> tempMap = new HashMap<String, Object>();
					tempMap = listitem.get(initItemId);
					int value = (Integer) tempMap.get("itemValue");
					tempMap.put("itemOption", "0x"+Integer.toHexString(value));
					listitem.set(initItemId, tempMap);
					factoryListAdapter.notifyDataSetChanged();
				}
				if(bFacResetReady)
				{
					bFacResetReady = false;
					HashMap<String, Object> tempMap = new HashMap<String, Object>();
					tempMap = listitem.get(initItemId);
					tempMap.put("itemOption", getResources().getString(R.string.start));
					listitem.set(initItemId, tempMap);
					factoryListAdapter.notifyDataSetChanged();					
				}
			}
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});

		viewBarCode=new BarcodeView(this);
		
		//密码输入框的view，以及绑定按钮监听器		
		viewPassword=inflater.inflate(R.layout.password, null);
		ensureButton = (Button) viewPassword.findViewById(R.id.item_ensure);
		cancelButton = (Button) viewPassword.findViewById(R.id.item_back);
		backspaceButton = (Button) viewPassword.findViewById(R.id.item_backspace);
		lock1Button = (Button) viewPassword.findViewById(R.id.item_locknum1);
		lock2Button = (Button) viewPassword.findViewById(R.id.item_locknum2);
		lock3Button = (Button) viewPassword.findViewById(R.id.item_locknum3);
		lock4Button = (Button) viewPassword.findViewById(R.id.item_locknum4);
		lock5Button = (Button) viewPassword.findViewById(R.id.item_locknum5);
		lock6Button = (Button) viewPassword.findViewById(R.id.item_locknum6);
		passwordInputView = (EditText) viewPassword.findViewById(R.id.item_editPart);
		
		
		ensureButton.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					passwordInputView.getText().clear();
					ensureButton.setVisibility(View.INVISIBLE);
					layout.removeView(viewPassword);
					pageNum=PAGE_ADVANCED;
					setListData();
				}
			});		
		cancelButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				layout.removeView(viewPassword);
			}
		});
		backspaceButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				int count =passwordInputView.getText().length();
				if(count!=0){
					passwordInputView.getText().delete(count-1, count);
					if(passwordInputView.getText().toString().equals("123456"))
					{
						ensureButton.setVisibility(View.VISIBLE);
						ensureButton.requestFocus();
					}
					else
					{
						ensureButton.setVisibility(View.INVISIBLE);
					}					
				}
			}
		});
		lock1Button.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
	
				String number=(String)lock1Button.getText();
				Byte value =Byte.parseByte(number);
				if (value++==9) value=0;
				lock1Button.setText(value.toString());
				passLockJudge();
			}
		});
		lock2Button.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
	
				String number=(String)lock2Button.getText();
				Byte value =Byte.parseByte(number);
				if (value++==9) value=0;
				lock2Button.setText(value.toString());
				passLockJudge();
			}
		});
		lock3Button.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
	
				String number=(String)lock3Button.getText();
				Byte value =Byte.parseByte(number);
				if (value++==9) value=0;
				lock3Button.setText(value.toString());
				passLockJudge();
			}
		});	
		lock4Button.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
	
				String number=(String)lock4Button.getText();
				Byte value =Byte.parseByte(number);
				if (value++==9) value=0;
				lock4Button.setText(value.toString());
				passLockJudge();
			}
		});	
		lock5Button.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
	
				String number=(String)lock5Button.getText();
				Byte value =Byte.parseByte(number);
				if (value++==9) value=0;
				lock5Button.setText(value.toString());
				passLockJudge();
			}
		});	
		lock6Button.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
	
				String number=(String)lock6Button.getText();
				Byte value =Byte.parseByte(number);
				if (value++==9) value=0;
				lock6Button.setText(value.toString());
				passLockJudge();
			}
		});				
		viewInfoText = new NonKeyActionView(FactoryMenuMain.this);
		viewInfoText.setRemoveViewListener(new RemoveViewListener(){
			public void removeMyView() {
				layout.removeView(viewInfoText);
			}
		});

		
		handler=new Handler(){

			@Override
			public void handleMessage(Message msg) {
				
				layout.removeView(viewInfoText);
			}
			
		};
		
		initUpdateUIReceiver();
    }
    
    private UpdateUIReceiver mReceiver = null;
    private void initUpdateUIReceiver() {
        mReceiver = new UpdateUIReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(GlobalConst.RECEIVE_TVSERVICE_MSG);
        filter.addAction(GlobalConst.RECEIVE_FACOTRY_MSG);
        registerReceiver(mReceiver, filter);
    }
    
    private class UpdateUIReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(GlobalConst.RECEIVE_TVSERVICE_MSG)) {
                Message msg = intent.getParcelableExtra("msg");
                switch (msg.arg1) {
				case GlobalConst.UART_GOOUT_FACTORY_MENU:
//					if(viewPassword.isShown())
//					{
//						layout.removeView(viewPassword);
//						ensureButton.setVisibility(View.INVISIBLE);
//					}
//					if(this.getCurrentFocus() == list && pageNum!=PAGE_MAIN)
//					{
//						if(pageNum == PAGE_EEPROM_RW) pageNum=PAGE_ADVANCED;
//						else pageNum=PAGE_MAIN;
//						setListData();
//					}
					///////goto testmenu activity
					{
						unbindService(mConnection);  ////close tvseriver
						setResultBundles();	
						unregisterReceiver(mReceiver);
						FactoryMenuMain.this.finish();
					}
				break;
				case GlobalConst.UART_GOTO_IICBusOff:
					
					if(viewInfoText.isShown())
					{
						;

					}
					else
					{
						RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(500,100);
						params.addRule(RelativeLayout.CENTER_IN_PARENT);
						viewInfoText.setInfoText(R.string.busoff);
						layout.addView(viewInfoText,params);
						viewInfoText.requestFocus();
						tvsetting.IICBusOn();	//i2c stop

					}


                	break;
                case GlobalConst.UART_GOOUT_IICBusOff:
                	if(viewInfoText.isShown())
        			{
        				tvsetting.IICBusOff();	//recovery
        				layout.removeView(viewInfoText);
        			}
        			
                	break;
                	
                case GlobalConst.UART_KEY_testdefault: ///3eH

                	tvsetting.SetBurnDefault();
                	tvsetting.SaveCurAudioVolume(50);
                	tvsetting.uartsend("shortcut_setup_sys_dream_panel_off","");
//                	tvsetting.uartsend("shortcut_setup_sys_sleep_time_off","");
                	if (tvsetting.ismCustomVolumeMute()) 
                		sendBroadcast(new Intent("mutekey_press"));
                	bInitMenu=true;
                	tvsetting.SetUsbbootState(1);
                	setListData();	// refresh facory menu
             

                break;
                case GlobalConst.UART_KEY_outfactory:  ///3bh
                	facSetUserDefault();   			
                	break;
                default:
                    break;
				}
                
               
            } else if (action.equals(GlobalConst.RECEIVE_FACOTRY_MSG)) {
                Message msg = intent.getParcelableExtra("msg");
                if (msg.arg1 == GlobalConst.MSG_VDINJNI_DISPLAY_CHANNEL_INFO)
                    ;
            }
        }

    };

    
/**********************************************************
 * 以下方法创建listview的数据列表，并创建适配器、绑定适配器到listview中去，
 * 返回值为当前显示的数据列表。
 * 该方法用pageNum作为分类依据，每个case创建一页的listitem，对应一页工厂模
 * 式菜单。如果需要添加新的工厂模式页，请在switch后面增加case，而每页里的
 * item则用listitem.add(map);来添加，每add一次增加一个item。
 * 请注意有些项目需要存入EEPROM，这些项需要在map中构建EEPROM地址。
 */
 
    private ArrayList<HashMap<String, Object>> setListData()
    {
    	//创建数据列表，每一行都是一个hashmap组，每个hashmap根据R.layout.factorymenu_item，都是
    	//两个textview，第一个textview放工厂调节项名称，第二个textview放调节内容。
    	//有些项目需要存入EEPROM，这些项的map中有"itemAddressPage"和"itemAddressPos",分别对应EEPROM地址的页码和相对位置，这些项不会被对应到listview当中去
		listitem=new ArrayList<HashMap<String,Object>>();
		
		HashMap<String, Object> map=new HashMap<String, Object>(); 
		int value = 0;
		switch(pageNum)
		{
		case PAGE_MAIN:		//首页

//			//第一行 BUS OFF
//			map.put("itemText", getResources().getString(R.string.busoff));
//			map.put("itemOption", getResources().getString(R.string.Arrow));
//			listitem.add(map);
			//第一行，高级设置
			map.put("itemText", getResources().getString(R.string.advanced));
			map.put("itemOption", getResources().getString(R.string.Arrow));
			listitem.add(map);
			//第二行 白平衡自动校正
			map=new HashMap<String, Object>();
			map.put("itemText", getResources().getString(R.string.AutoADC));
			map.put("itemOption", getResources().getString(R.string.start));
			listitem.add(map);	
			//第三行，白平衡
			map=new HashMap<String, Object>();
			map.put("itemText", getResources().getString(R.string.WhiteBalance));
			map.put("itemOption", getResources().getString(R.string.Arrow));
			listitem.add(map);
			//第四行，老化模式
			map=new HashMap<String, Object>();
			map.put("itemText", getResources().getString(R.string.M_mode));
			map.put("itemOption", getResources().getString(R.string.Arrow));
			listitem.add(map);
			//第五行，EMC_CONTROL
			map=new HashMap<String, Object>();
			map.put("itemText", getResources().getString(R.string.EMC_CONTROL));
			map.put("itemOption", getResources().getString(R.string.Arrow));		
			listitem.add(map);
			//第六行，单键模式开关
			map=new HashMap<String, Object>();
			value = tvsetting.FacGet_ONEKEY_ONOFF();
			map.put("itemText", getResources().getString(R.string.SingleKeySwitch));
			map.put("itemOption", (value==1?getResources().getString(R.string.On):getResources().getString(R.string.Off)));
//			map.put("itemAddressPage", 0);		//eeprom页码
//			map.put("itemAddressPos", 0);		//eeprom相对位置
			listitem.add(map);
	
			//第七行，配屏panel type
			String tempStr = tvsetting.FacGetPanelType();
			if(tempStr.equals("LG42E83"))
				value = 0;
			else if(tempStr.equals("LG47E83"))
				value = 1;
			else if(tempStr.equals("LG55E83"))
				value = 2;
			else if(tempStr.equals("LG42E85"))
				value = 3;
			else if(tempStr.equals("LG47E85"))
				value = 4;			
			else	//error
			{
				value = 5;		
				tvsetting.FacSetPanelType(0);
			}
			map=new HashMap<String, Object>();
			map.put("itemText", getResources().getString(R.string.PanelType));
			if(value>=0 && value <5)
				map.put("itemOption", getResources().getString(panelID[value]));
			else
				map.put("itemOption", getResources().getString(R.string.TypeError));
			map.put("itemValue", value);
			listitem.add(map);	

			break;
		case PAGE_WHITEBALANCE:	//白平衡调整页

			//第二行RGAIN
			value = tvsetting.FacGet_RGBogo("r", "gain");
			if(value > 2047 || value < 0)	//protect	
			{
				value = 1024;	
				tvsetting.FacSet_RGBogo("r", "gain",value);
			}
			map=new HashMap<String, Object>();
			map.put("itemText", getResources().getString(R.string.RGAIN));
			map.put("itemOption", value);	//注意这里可以直接赋int值，无需转换为string
//			map.put("itemAddressPage", 1);		//eeprom页码
//			map.put("itemAddressPos", 1);		//eeprom相对位置
			listitem.add(map);
			//第三行GGAIN
			value = tvsetting.FacGet_RGBogo("g", "gain");
			if(value > 2047 || value < 0)	//protect	
			{
				value = 1024;	
				tvsetting.FacSet_RGBogo("g", "gain",value);
			}
			map=new HashMap<String, Object>();
			map.put("itemText", getResources().getString(R.string.GGAIN));
			map.put("itemOption", value);
//			map.put("itemAddressPage", 2);		//eeprom页码
//			map.put("itemAddressPos", 2);		//eeprom相对位置			
			listitem.add(map);
			//第四行BGAIN
			value = tvsetting.FacGet_RGBogo("b", "gain");
			if(value > 2047 || value < 0)	//protect	
			{
				value = 1024;	
				tvsetting.FacSet_RGBogo("b", "gain",value);
			}
			map=new HashMap<String, Object>();
			map.put("itemText", getResources().getString(R.string.BGAIN));
			map.put("itemOption", value);
//			map.put("itemAddressPage", 3);		//eeprom页码
//			map.put("itemAddressPos", 3);		//eeprom相对位置			
			listitem.add(map);
			//第五行ROFF
			value = tvsetting.FacGet_RGBogo("r", "post_offset");
			if(value > 1023 || value < -1024)	//protect	
			{
				value = 0;	
				tvsetting.FacSet_RGBogo("r", "post_offset",value);
			}			
			map=new HashMap<String, Object>();
			map.put("itemText", getResources().getString(R.string.ROFF));
			map.put("itemOption", value);
//			map.put("itemAddressPage", 4);		//eeprom页码
//			map.put("itemAddressPos", 4);		//eeprom相对位置			
			listitem.add(map);		
			//第六行GOFF
			value = tvsetting.FacGet_RGBogo("g", "post_offset");
			if(value > 1023 || value < -1024)	//protect	
			{
				value = 0;	
				tvsetting.FacSet_RGBogo("g", "post_offset",value);
			}				
			map=new HashMap<String, Object>();
			map.put("itemText", getResources().getString(R.string.GOFF));
			map.put("itemOption", value);
//			map.put("itemAddressPage", 5);		//eeprom页码
//			map.put("itemAddressPos", 5);		//eeprom相对位置			
			listitem.add(map);	
			//第七行BOFF
			value = tvsetting.FacGet_RGBogo("b", "post_offset");
			if(value > 1023 || value < -1024)	//protect	
			{
				value = 0;	
				tvsetting.FacSet_RGBogo("b", "post_offset",value);
			}				
			map=new HashMap<String, Object>();
			map.put("itemText", getResources().getString(R.string.BOFF));
			map.put("itemOption", value);
//			map.put("itemAddressPage", 6);		//eeprom页码
//			map.put("itemAddressPos", 6);		//eeprom相对位置			
			listitem.add(map);	
			break;
		case PAGE_EEPROM_RW:	//EEPROM读写
			//初始化eeprom页码、偏移量、数据，以避免无目的地操作数据而弹出保存项的情况
			dataOfAddressPage=0;
			dataOfAddressOffset=0;
			dataOfData=tvsetting.Factory_ReadEepromOneByte(0);
			
			//第一行 页码
			map.put("itemText", getResources().getString(R.string.EEP_page));
			map.put("itemOption", "0x"+Long.toHexString(dataOfAddressPage));
			map.put("itemValue", dataOfAddressPage);
			listitem.add(map);	
			//第二行 偏移量
			map=new HashMap<String, Object>();
			map.put("itemText", getResources().getString(R.string.EEP_pos));
			map.put("itemOption", "0x"+Long.toHexString(dataOfAddressOffset));
			map.put("itemValue", dataOfAddressOffset);
			listitem.add(map);		
			//第三行 数据
			map=new HashMap<String, Object>();
			map.put("itemText", getResources().getString(R.string.EEP_data));
			map.put("itemOption", "0x"+Long.toHexString(dataOfData));
			map.put("itemValue", dataOfData);		
			listitem.add(map);	
//			//第四行 保存		隐藏条目，当数据项发生变化时显示，保存后消失

			//第五行 初始化
			map=new HashMap<String, Object>();
			map.put("itemText", getResources().getString(R.string.EEP_init));
			map.put("itemOption", getResources().getString(R.string.start));
			map.put("itemInfo", null);
			listitem.add(map);
			
//			itemInfoText.setText("0x"+Long.toHexString(dataOfAddressPage));
//			itemInfoText.setVisibility(View.VISIBLE);
			break;
		case PAGE_ADVANCED:		//高级设置
			//第一行   pageup
			map.put("itemText", getResources().getString(R.string.pageup));
			map.put("itemOption",  getResources().getString(R.string.Arrow));
			listitem.add(map);	
			//第二行   pagedown
			map=new HashMap<String, Object>();
			map.put("itemText", getResources().getString(R.string.pagedown));
			map.put("itemOption",  getResources().getString(R.string.Arrow));
			listitem.add(map);				
			//第三行 EEP读写
			map=new HashMap<String, Object>();
			map.put("itemText", getResources().getString(R.string.EEPROM_RW));
			map.put("itemOption",  getResources().getString(R.string.Arrow));
			listitem.add(map);	
			//第四行 option1	hdcp from
			boolean flag = tvsetting.GetHdcpFlag();
			map=new HashMap<String, Object>();
			map.put("itemText", getResources().getString(R.string.OPT1));
			map.put("itemOption", flag?getResources().getString(R.string.hdcpdefault):getResources().getString(R.string.hdcpeep));
			
			listitem.add(map);	
			//第五行 option2	USBBOOT
			map=new HashMap<String, Object>();
			map.put("itemText", getResources().getString(R.string.OPT2));
			value = tvsetting.GetUsbbootState();
			map.put("itemOption", (value==1?getResources().getString(R.string.On):getResources().getString(R.string.Off)));			
			map.put("itemValue", value);
//			map.put("itemAddressPage", 5);		//eeprom页码
//			map.put("itemAddressPos", 5);		//eeprom相对位置			
			listitem.add(map);	
			//第六行 option6 standby mode
			map=new HashMap<String, Object>();
			map.put("itemText", getResources().getString(R.string.OPT6));
			map.put("itemOption",(tvsetting.GetStandByModeFlag()?getResources().getString(R.string.standby_simple):getResources().getString(R.string.standby_completely)));
			listitem.add(map);	
			//第七行 adbswitch
			value = tvsetting.GetAdbState();;
			map=new HashMap<String, Object>();
			map.put("itemText", getResources().getString(R.string.adbSwitch));
			map.put("itemOption",(value==1?getResources().getString(R.string.On):getResources().getString(R.string.Off)));
			listitem.add(map);	
			//第八行MAC地址
			map=new HashMap<String, Object>();
			map.put("itemText", getResources().getString(R.string.MACAddr));
			map.put("itemOption",getResources().getString(R.string.MACGet));
			listitem.add(map);
			//第九行 恢复出厂设置
			map=new HashMap<String, Object>();
			map.put("itemText", getResources().getString(R.string.facotry_reset));
			map.put("itemOption", getResources().getString(R.string.start));
			listitem.add(map);			
			//第十行 option3 LVDS
//			value = eepromRead(0,0);
			map=new HashMap<String, Object>();
			map.put("itemText", getResources().getString(R.string.OPT3));
			value = tvsetting.GetLvdsState();
			map.put("itemOption", (value==0?getResources().getString(R.string.LVDS_VESA):getResources().getString(R.string.LVDS_JEIDA)));			
			listitem.add(map);
			//第十一行 option4		power on picture
			map=new HashMap<String, Object>();
			map.put("itemText", getResources().getString(R.string.OPT4));
			map.put("itemOption",(tvsetting.GetStartPicFlag()?getResources().getString(R.string.On):getResources().getString(R.string.Off)));
			listitem.add(map);
			//第十二行 option5		online music
			map=new HashMap<String, Object>();
			map.put("itemText", getResources().getString(R.string.OPT5));
			map.put("itemOption",(tvsetting.GetOnLineMusicFlag()?getResources().getString(R.string.On):getResources().getString(R.string.Off)));
			listitem.add(map);
			break;
		case PAGE_ADVANCED_PAGE2:		//高级设置
			//第一行   pageup
			map.put("itemText", getResources().getString(R.string.pageup));
			map.put("itemOption",  getResources().getString(R.string.Arrow));
			listitem.add(map);	
			//第二行   pagedown
			map=new HashMap<String, Object>();
			map.put("itemText", getResources().getString(R.string.pagedown));
			map.put("itemOption",  getResources().getString(R.string.Arrow));
			listitem.add(map);	
			//第三行HDMIEQMode
			map=new HashMap<String, Object>();
			value = tvsetting.FacGetHdmiEQMode();
			map.put("itemText", getResources().getString(R.string.HDMIEQMode));
			map.put("itemOption",value);
			listitem.add(map);	
			//第四行 HDMIInternalMode
			map=new HashMap<String, Object>();
			value = tvsetting.FacGetHdmiInternalMode();
			map.put("itemText", getResources().getString(R.string.HDMIInternalMode));
			map.put("itemOption", "0x"+Integer.toHexString(value));
			map.put("itemValue", value);		
			listitem.add(map);
			//第五行 serialport_switch
			boolean serialFlag = tvsetting.FacGetSerialPortSwitchFlag();
			map=new HashMap<String, Object>();
			map.put("itemText", getResources().getString(R.string.serialportSwitch));
			map.put("itemOption",(serialFlag?getResources().getString(R.string.On):getResources().getString(R.string.Off)));	
			listitem.add(map);
			break;			
		case PAGE_EMC_CONTROL:	//扩频
			//第一行  扩频开关 0：关  1：开
			int temp[]=tvsetting.GetSscDate();
			dataEMCSwitch = temp[3]==1?true:false;
			dataEMCCycle = temp[2];
			dataEMCAmpLow = temp[1];
			dataEMCAmp = temp[0];
			if(!(dataEMCCycle>=0 && dataEMCCycle<15)			//protect
					||!(dataEMCAmpLow>=0 && dataEMCAmpLow<4)
					||!(dataEMCAmp>=0 && dataEMCAmp<5))
			{
				dataEMCSwitch = false;
				dataEMCCycle = 0;
				dataEMCAmpLow = 0;
				dataEMCAmp = 0;
				tvsetting.SetSSC(dataEMCAmp, dataEMCAmpLow, dataEMCCycle, dataEMCSwitch);
			}
			
			map.put("itemText", getResources().getString(R.string.SPREAD_SP_EN));
			map.put("itemOption", (dataEMCSwitch?getResources().getString(R.string.On):getResources().getString(R.string.Off)));
//			map.put("itemAddressPage", 5);		//eeprom页码
//			map.put("itemAddressPos", 5);		//eeprom相对位置			
			listitem.add(map);	
			//第二行 扩频周期
			value = dataEMCCycle;
			map=new HashMap<String, Object>();
			map.put("itemText", getResources().getString(R.string.SP_PERIOD));
			map.put("itemOption", value);
//			map.put("itemAddressPage", 5);		//eeprom页码
//			map.put("itemAddressPos", 5);		//eeprom相对位置			
			listitem.add(map);		
			//第三行 扩频幅度高位
			value = dataEMCAmp;
			map=new HashMap<String, Object>();
			map.put("itemText", getResources().getString(R.string.SP_AMPLITUDE_MSB));
			map.put("itemOption", value);
//			map.put("itemAddressPage", 5);		//eeprom页码
//			map.put("itemAddressPos", 5);		//eeprom相对位置			
			listitem.add(map);	
			//第四行 扩频幅度低位
			value = dataEMCAmpLow;
			map=new HashMap<String, Object>();
			map.put("itemText", getResources().getString(R.string.SP_AMPLITUDE));
			map.put("itemOption", value);
//			map.put("itemAddressPage", 5);		//eeprom页码
//			map.put("itemAddressPos", 5);		//eeprom相对位置			
			listitem.add(map);			
//			//第五行 扩频电流
////			value = eepromRead(0,0);
//			map=new HashMap<String, Object>();
//			map.put("itemText", getResources().getString(R.string.LVDS_ICTRL));
//			map.put("itemOption", value);
////			map.put("itemAddressPage", 5);		//eeprom页码
////			map.put("itemAddressPos", 5);		//eeprom相对位置			
//			listitem.add(map);	
			break;
		default:
			break;
		}
		
		//创建适配器，将list数据列表的内容适配到listview中去。
		factoryListAdapter=new SimpleAdapter(this,listitem,	
				R.layout.factorymenu_item,	//listview item的XML实现，即每一行的显示格式。
				//请注意如果修改这个xml文件，不要使用wrap_content，而要使用固定的大小，否则会导致画面刷新速度下降
				new String[]{"itemText","itemOption"},	//这里是from，即要写入的内容
				new int[]{R.id.ListText,R.id.ListButton}//这里是to，即写到哪个位置去
				);
  		list.setAdapter(factoryListAdapter);	//将数据适配到list中去。
    	return listitem;
    }
	

/***********************************************
* 以下为复写的按键处理函数，
*/

    @Override
	protected void onPause() {
		// TODO Auto-generated method stub
//    	tvsetting.SetfactoryFlag(0);
		super.onPause();
	} 

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
//    	tvsetting.SetfactoryFlag(1);

		Timer clock2 = new Timer();
        clock2.schedule(new TimerTask() {

            public void run() {
		// TODO Auto-generated method stub
		systembusy = 0;
            }
        }, 800);
		super.onStart();
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(systembusy==1)
			return true;
		if(viewBarCode.isShown())
		{
			layout.removeView(viewBarCode);
			return true;
		}
		switch(keyCode)
		{
		case 115:		//volumn-
		case KeyEvent.KEYCODE_DPAD_LEFT:
			if(this.getCurrentFocus() == list)
			{
				int direction = -1;
				if(optionChangeHandler(direction))
					return true;
			}
				break;
			
		case 114:		//volumn+
		case KeyEvent.KEYCODE_DPAD_RIGHT:	
			if(this.getCurrentFocus() == list)
			{
				int direction = 1;
				if(optionChangeHandler(direction))
					return true;
			}
				break;
			
		case KeyEvent.KEYCODE_MENU:
			if(this.getCurrentFocus() == list)
			{
				if(pageNum == PAGE_EEPROM_RW) pageNum=PAGE_ADVANCED;
				else if(pageNum!=PAGE_MAIN) pageNum=PAGE_MAIN;
				setListData();
				return true;
			}
			break;
		case KeyEvent.KEYCODE_DPAD_DOWN:	//ch-
		case 136:
			if(this.getCurrentFocus() == list)
			{
				int selecorPosition = (int)list.getSelectedItemId();
				if(selecorPosition == list.getCount()-1)	//最后一项
				{
					selecorPosition=0;						//第一项
					list.setSelection(selecorPosition);
					return true;
				}
				else
				{
					list.setSelection(++selecorPosition);
					return true;
				}				
			}
			break;
		case KeyEvent.KEYCODE_DPAD_UP:	//ch+
		case 135:
			if(this.getCurrentFocus() == list)
			{
				int selecorPosition = (int)list.getSelectedItemId();
				if(selecorPosition == 0)				//第一项
				{
					selecorPosition=list.getCount()-1;	//最后一项
					list.setSelection(selecorPosition);
					return true;
				}
				else
				{
					list.setSelection(--selecorPosition);
					return true;
				}
			}
				break;	
		case KeyEvent.KEYCODE_0:
		case KeyEvent.KEYCODE_1:
		case KeyEvent.KEYCODE_2:
		case KeyEvent.KEYCODE_3:
		case KeyEvent.KEYCODE_4:
		case KeyEvent.KEYCODE_5:
		case KeyEvent.KEYCODE_6:
		case KeyEvent.KEYCODE_7:
		case KeyEvent.KEYCODE_8:
		case KeyEvent.KEYCODE_9:
			if(viewPassword.isShown())
			{
				Integer keyNum =keyCode - 7;
				passwordInputView.getText().append(keyNum.toString());
	
				if(passwordInputView.getText().toString().equals(PASSWORD))
				{
					ensureButton.setVisibility(View.VISIBLE);
					ensureButton.requestFocus();
				}
				else
				{
					ensureButton.setVisibility(View.INVISIBLE);
					backspaceButton.requestFocus();
				}	
				return true;
			}
			else if(bEditable)
			{
				long tempLong;
				if(numberArea.toString().equals(getResources().getString(R.string.tobeinput)))
				{
					numberArea = new StringBuffer();
					tempLong = 0;
				}
				else
				{
					tempLong = Long.parseLong(numberArea.toString());
				}
				Integer keyNum =keyCode - 7;
				if(tempLong < 2147483647)	//0x7fffffff
					numberArea.append(keyNum.toString());
				tempLong = Long.parseLong(numberArea.toString());
				if(tempLong > 2147483647)
					numberArea.delete(numberArea.length()-1, numberArea.length());
				int selecorPosition = (int)list.getSelectedItemId();
				HashMap<String, Object> tempMap = listitem.get(selecorPosition);
				tempMap.put("itemOption", numberArea.toString());
				listitem.set(selecorPosition, tempMap);
				factoryListAdapter.notifyDataSetChanged();
				bHdmiIntNotSaved = true;
				return true;
			}
			else
			{
				int chNumber = keyCode - 7;
				if (tvsetting.GetCurSourceTV()&& tvsetting.getCurrnetChNumber() != chNumber) {
					tvsetting.changeCurrentChannelToChannel(chNumber);
					Intent intent = new Intent(GlobalConst.RECEIVE_FACOTRY_MSG);
	                Message msg = new Message();
	                msg.arg1 = GlobalConst.MSG_VDINJNI_DISPLAY_CHANNEL_INFO;
//	                msg.what = GlobalConst.DISPLAY_CHANNEL_INFO;
	                intent.putExtra("msg", msg);
	                sendBroadcast(intent);
	            }
				return true;
			}
		case KeyEvent.KEYCODE_BACK:
			if(viewPassword.isShown())
			{
				layout.removeView(viewPassword);
				ensureButton.setVisibility(View.INVISIBLE);
				return true;
			}
			if(this.getCurrentFocus() == list && pageNum!=PAGE_MAIN)
			{
				if(pageNum == PAGE_EEPROM_RW) pageNum=PAGE_ADVANCED;
				else pageNum=PAGE_MAIN;
				setListData();
				return true;
			}
			///////goto testmenu activity
			{
				unbindService(mConnection);  ////close tvseriver
				setResultBundles();	
				unregisterReceiver(mReceiver);
				this.finish();
			}
			break;
		case KeyEvent.KEYCODE_MUTE:
			sendBroadcast(new Intent("mutekey_press"));
			break;
		case KeyEvent.KEYCODE_DEL:
			if(this.getCurrentFocus() != list)
			{
				int count =passwordInputView.getText().length();
				if(count!=0){
					passwordInputView.getText().delete(count-1, count);
					if(passwordInputView.getText().toString().equals("123456"))
					{
						ensureButton.setVisibility(View.VISIBLE);
						ensureButton.requestFocus();
					}
					else
					{
						ensureButton.setVisibility(View.INVISIBLE);
					}					
				}
				return true;
			}
			else if(bEditable)
			{
				if(!numberArea.toString().equals(getResources().getText(R.string.tobeinput)))
				{
					numberArea.delete(numberArea.length()-1, numberArea.length());
					int selecorPosition = (int)list.getSelectedItemId();
					HashMap<String, Object> tempMap = listitem.get(selecorPosition);
					if(numberArea.length() == 0)
						numberArea = new StringBuffer(getResources().getText(R.string.tobeinput));
					tempMap.put("itemOption", numberArea.toString());
					listitem.set(selecorPosition, tempMap);
					factoryListAdapter.notifyDataSetChanged();
					bHdmiIntNotSaved = true;
				}
			}
			break;			
		case 141:	//bus off key
			if(viewInfoText.isShown())
			{
				tvsetting.IICBusOff();	//recovery
				layout.removeView(viewInfoText);
				return true;
			}
			else
			{
				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(500,100);
				params.addRule(RelativeLayout.CENTER_IN_PARENT);
				viewInfoText.setInfoText(R.string.busoff);
				layout.addView(viewInfoText,params);
				viewInfoText.requestFocus();
				tvsetting.IICBusOn();	//i2c stop
				return true;
			}
		case 120:	//barcode
			{
			viewBarCode	= new BarcodeView(this);
			RelativeLayout.LayoutParams dialogparams;
			dialogparams = new RelativeLayout.LayoutParams(900, 400);
			dialogparams.addRule(RelativeLayout.CENTER_IN_PARENT);
			layout.addView(viewBarCode,dialogparams);
			viewBarCode.requestFocus();
			return true;	
			}
			
           /////////////////////////factory mode
        case 145:///factory 3eH 
        {
        	tvsetting.SetBurnDefault();
        	tvsetting.SaveCurAudioVolume(50);
        	tvsetting.uartsend("shortcut_setup_sys_dream_panel_off","");
//        	tvsetting.uartsend("shortcut_setup_sys_sleep_time_off","");
        	if (tvsetting.ismCustomVolumeMute()) 
        		sendBroadcast(new Intent("mutekey_press"));
        	bInitMenu=true;
        	tvsetting.SetUsbbootState(1);
        	setListData();	// refresh facory menu
        	break;
        }
		case 146:	//0x3f factory key
		case 97:	//display info
		{
			setResultBundles();			
			this.finish();
		}
			return true;
		case 121:	//white balance key
			if(pageNum != PAGE_WHITEBALANCE)
			{
				pageNum = PAGE_WHITEBALANCE;
				setListData();
				return true;
			}
			break;
		case 139:	//adc auto adj key

			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(900,250);
			params.addRule(RelativeLayout.CENTER_IN_PARENT);
			viewInfoText.setInfoText(R.string.ADCPreparing);
			viewInfoText.setInfoWidth(900);
			viewInfoText.setInfoHeight(250);
			layout.addView(viewInfoText,params);
			viewInfoText.requestFocus();
			return true;
		case 140:	//M_mode key
            tvsetting.FacSet_BURN_FLAG(true);
            tvsetting.SetStandbyKeyMode(true);
            bInitMenu = true;
			bMmode = true;
			{
				setResultBundles();				
				this.finish();
			}
			tvsetting.setWhiteScreen();
            tvsetting.SetBurnDefault();
			return true;
		case 142:///factoty 3BH go out factory  ///A CLASS KEY
			{
				facSetUserDefault();
			}
        	break;
        case 144:///factory 3dH channel add+
        case 143:///factory 3cH channel  -
        {
        	int currnetch=0;
        	String common_source="shortcut_common_source_";
        	currnetch = tvsetting.GetTvParam(common_source);
        	if(keyCode == 144)
        	{
        		if(currnetch++ ==6)
        			currnetch=0;
        	}
        	else
        	{
        		if(currnetch-- ==0)
        			currnetch=6;       		
        	}
        	switch(currnetch)
        	{
        	  case 0:
        		  tvsetting.uartsend("shortcut_common_source_tv","");
        	      break;
        	  case 1:
        		  tvsetting.uartsend("shortcut_common_source_av1","");
        		  break;
        	  case 2:
        		  tvsetting.uartsend("shortcut_common_source_yuv1","");
        		  break;
        	  case 3:
        		  tvsetting.uartsend("shortcut_common_source_hdmi1","");
        		  break;
        	  case 4:
        		  tvsetting.uartsend("shortcut_common_source_hdmi2","");
        		  break;
        	  case 5:
        		  tvsetting.uartsend("shortcut_common_source_hdmi3","");
        		  break;
        	  case 6:
        		  tvsetting.uartsend("shortcut_common_source_vga","");
        		  break;
        	  default:
        		  tvsetting.uartsend("shortcut_common_source_tv","");
        	}
        	tvsetting.setSignalDetectHandler();
        	bInitMenu = true;
        	return true;
        }
        case 138:///factory 37H goto AV1
        {
            if (!tvsetting.GetCurSourceAV1()) 
           	{
           		tvsetting.uartsend("shortcut_common_source_av1","");
           		bInitMenu = true;
           	}
        	tvsetting.setSignalDetectHandler();
        	return true;
        }   
        case 131:///factoty 32H goto YUV1
        {
            if (!tvsetting.GetCurSourceYPBPR1())
           	{
           		tvsetting.uartsend("shortcut_common_source_yuv1","");
           		bInitMenu = true;
           	}
        	tvsetting.setSignalDetectHandler();
        	return true;
        }  
        case 128:///factoty 2FH goto HDMI1
        {
            if (!tvsetting.GetCurSourceHDMI1())
           	{
           		tvsetting.uartsend("shortcut_common_source_hdmi1","");
           		bInitMenu = true;
           	}
        	tvsetting.setSignalDetectHandler();
        	return true;
        }
        case 127:///factoty 2EH goto HDMI2
        {
        	if (!tvsetting.GetCurSourceHDMI2())
           	{
           		tvsetting.uartsend("shortcut_common_source_hdmi2","");
           		bInitMenu = true;
           	}
        	tvsetting.setSignalDetectHandler();
        	return true;
        }   
        case 126:///factoty 2DH goto HDMI3
        {
            if (!tvsetting.GetCurSourceHDMI3())
           	{
           		tvsetting.uartsend("shortcut_common_source_hdmi3","");
           		bInitMenu = true;
           	}
        	tvsetting.setSignalDetectHandler();
        	return true;
        }    
        case 129:///factoty 30H goto VGA
        {
            if (!tvsetting.GetCurSourceVGA())
           	{
           		tvsetting.uartsend("shortcut_common_source_vga","");
           		bInitMenu = true;
           	}
        	tvsetting.setSignalDetectHandler();
        	return true;
        }          
		}

		return super.onKeyDown(keyCode, event);
	}




/*********************************************************************************
 * 此函数主要处理eeprom存储，当按键松开时才保存数值，以避免调节白平衡等需快速调节项目时
 * 因为eeprom通讯而影响速度	
 * 请注意click操作是不会引起onkeyup方法被调用的，因此这里的操作可能有部分需要被复制到clicklister
 * 里去。
 */
	@Override
public boolean onKeyUp(int keyCode, KeyEvent event) {
	// TODO Auto-generated method stub、
		if(systembusy==1)
			return true;
		switch(keyCode)
		{
		case 115:		//volumn-
		case KeyEvent.KEYCODE_DPAD_LEFT:
		case 114:		//volumn+
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			//如有数据需要存入eeprom
			if(eepromDataChanged)
			{
//				eepromWhrite(eepromAddressPage, eepromAddressOffset, eepromData);
				eepromDataChanged=false;
				return true;
			}
			//下面这个if是如果当前项是eeprom页码和偏移量，则当按键松开时读取该位置的eeprom数据
			if(pageNum==PAGE_EEPROM_RW &&( list.getSelectedItemId()== 0||list.getSelectedItemId()==1))
			{
//				if(dataOfAddressOffset!=8888 && dataOfAddressPage!=8888)
				{
					HashMap<String, Object> tempMap = listitem.get(2);	//读取数据行map
					int offsetAddr= dataOfAddressPage<<8;
					offsetAddr += dataOfAddressOffset;
					int curData=tvsetting.Factory_ReadEepromOneByte(offsetAddr);
//					int curData=eepromRead(dataOfAddressPage, dataOfAddressOffset);
					tempMap.put("itemOption", "0x"+Long.toHexString(curData));
					tempMap.put("itemValue", curData);					
					listitem.set(2,tempMap);
				}	
				factoryListAdapter.notifyDataSetChanged();
				return true;
			}
			break;
		}
	return super.onKeyUp(keyCode, event);
}
///test
    private Intent mIntent = new Intent("com.amlogic.tvjni.TvService");
    private Itvservice mItvservice = null;
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            //Log.d(Tag, "......UartControl onServiceConnected start......\n");
           mItvservice = Itvservice.Stub.asInterface(service);
           tvset.setItvService(mItvservice);
        }
        public void onServiceDisconnected(ComponentName className) {
            //Log.d(Tag, "......UartControl onServiceDisconnected .........\n");
            mItvservice = null;
        }
    };

    
    
   



    
	/**************************************************************************
	 * 此函数处理左右按键，direc代表方向，-1为左，+1为右。return值表示是否对按键进行了处理
	 * @param direc
	 * @return
	 */
	boolean optionChangeHandler(int direc)  
	{
		int direction = direc;
		boolean dataChanged = false,pageChanged = false;		//dataChanged表明是否有item项内容改变，pageChanged表明是否有翻页行为发生
		int selecorPosition = (int)list.getSelectedItemId();	//当前高亮的项目
		HashMap<String, Object> map;
		int value=0;
		
		map=listitem.get(selecorPosition);
		String itemName=(String) map.get("itemText");
		switch(pageNum)
		{
		case PAGE_MAIN:	//首页
			if(itemName.equals(getResources().getString(R.string.busoff)))
			{
//				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(500,100);
//				params.addRule(RelativeLayout.CENTER_IN_PARENT);
//				viewInfoText.setInfoText(R.string.busoff);
//				layout.addView(viewInfoText,params);
//				viewInfoText.requestFocus();
//				tvsetting.IICBusOff();
//				return true;
			}
			else if(itemName.equals(getResources().getString(R.string.WhiteBalance)))
			{
				pageNum = PAGE_WHITEBALANCE;
				pageChanged = true;				
			}
			else if(itemName.equals(getResources().getString(R.string.M_mode)))
			{
	            tvsetting.FacSet_BURN_FLAG(true);
	            tvsetting.SetStandbyKeyMode(true);
	            bInitMenu = true;
				bMmode = true;
				{
					setResultBundles();				
					this.finish();
				}
				tvsetting.setWhiteScreen();
	            tvsetting.SetBurnDefault();				
				return true;				
			}		
			else if(itemName.equals(getResources().getString(R.string.EMC_CONTROL)))
			{
				pageNum = PAGE_EMC_CONTROL;
				pageChanged = true;	
			}		
			else if(itemName.equals(getResources().getString(R.string.SingleKeySwitch)))
			{
				String s=(String) map.get("itemOption");
				map.put("itemOption", s.equals(getResources().getString(R.string.On))?getResources().getString(R.string.Off):getResources().getString(R.string.On));
				listitem.set(selecorPosition,map);
				tvsetting.FacSet_ONEKEY_ONOFF(s.equals(getResources().getString(R.string.On))?0:1);
				dataChanged = true;				
			}	
			else if(itemName.equals(getResources().getString(R.string.advanced)))
			{
				RelativeLayout.LayoutParams dialogparams;
				dialogparams = new RelativeLayout.LayoutParams(600, 350);
				dialogparams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
				dialogparams.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
				passwordInputView.getText().clear();	//显示view前先清空password文本框
				layout.addView(viewPassword,dialogparams);
				viewPassword.requestFocus();
				return true;
			}
			else if(itemName.equals(getResources().getString(R.string.AutoADC)))		//白平衡自动校正
			{
				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(900,250);
				params.addRule(RelativeLayout.CENTER_IN_PARENT);
				viewInfoText.setInfoText(R.string.ADCPreparing);
				viewInfoText.setInfoWidth(900);
				viewInfoText.setInfoHeight(250);
				layout.addView(viewInfoText,params);
				viewInfoText.requestFocus();
				return true;
			}			
			else if(itemName.equals(getResources().getString(R.string.PanelType)))	//配屏选择 panel type
			{
				value = (Integer) map.get("itemValue");
				value +=direction;
				if(value > 4)
					value = 0;
				else if(value < 0)
					value = 4;
				map.put("itemValue",value);
				tvsetting.FacSetPanelType(value);
				map.put("itemOption", getResources().getString(panelID[value]));
				listitem.set(selecorPosition,map);
				dataChanged = true;
			}
			break;
		case PAGE_WHITEBALANCE:	//白平衡页
			{						//手动白平衡
//				valueChangeAndSave(direction,255,0,map,selecorPosition);
				////////////////////////
				int value1;	
				String ganioffsetSel=" ",channelSelect=" ";
				if(itemName.equals(getResources().getString(R.string.RGAIN)))
				{
					ganioffsetSel = "gain";
					channelSelect = "r";
					valueChangeAndSave(direction,2048,0,map,selecorPosition,false);
				}
				else if(itemName.equals(getResources().getString(R.string.GGAIN)))
				{
					ganioffsetSel = "gain";
					channelSelect = "g";
					valueChangeAndSave(direction,2048,0,map,selecorPosition,false);
				}
				else if(itemName.equals(getResources().getString(R.string.BGAIN)))
				{
					ganioffsetSel = "gain";
					channelSelect = "b";
					valueChangeAndSave(direction,2048,0,map,selecorPosition,false);
				}	
				else if(itemName.equals(getResources().getString(R.string.ROFF)))
				{
					ganioffsetSel = "post_offset";
					channelSelect = "r";
					valueChangeAndSave(direction,1023,-1024,map,selecorPosition,false);
				}	
				else if(itemName.equals(getResources().getString(R.string.GOFF)))
				{
					ganioffsetSel = "post_offset";
					channelSelect = "g";
					valueChangeAndSave(direction,1023,-1024,map,selecorPosition,false);
				}	
				else if(itemName.equals(getResources().getString(R.string.BOFF)))
				{
					ganioffsetSel = "post_offset";
					channelSelect = "b";
					valueChangeAndSave(direction,1023,-1024,map,selecorPosition,false);
				}
				dataChanged = true;
				value1=(Integer)map.get("itemOption");
				tvsetting.FacSet_RGBogo(channelSelect,ganioffsetSel,value1);
			}
			break;
		case PAGE_ADVANCED:	//高级设置页
		case PAGE_ADVANCED_PAGE2:
			if(itemName.equals(getResources().getString(R.string.EEPROM_RW)))		//EEPROM读写
			{
				pageNum=PAGE_EEPROM_RW;
				pageChanged=true;
			}
			else if(itemName.equals(getResources().getString(R.string.adbSwitch)))	//adb switch
			{
				String s=(String) map.get("itemOption");
				map.put("itemOption", s.equals(getResources().getString(R.string.On))?getResources().getString(R.string.Off):getResources().getString(R.string.On));
				if(s.equals(getResources().getString(R.string.On)))
					tvsetting.SetAdbState(0);
				else
					tvsetting.SetAdbState(1);
				listitem.set(selecorPosition,map);
				eepromDataChanged=true;
				dataChanged = true;
			}
			else if(itemName.contains((getResources().getString(R.string.MACAddr))))	//mac地址
			{
				String s=(String) map.get("itemOption");
				if (s.equals(getResources().getString(R.string.MACGet)))	//read
				{
					map.put("itemOption",getResources().getString(R.string.MACDefault));
					map.put("itemText", getResources().getString(R.string.MACAddr));
				}
				else
				{
					map.put("itemOption",getResources().getString(R.string.MACGet));
					map.put("itemText", getResources().getString(R.string.MACAddr));					
				}
				listitem.set(selecorPosition,map);
				dataChanged = true;
			}
			else if(itemName.equals(getResources().getString(R.string.OPT1)))	//hdcp defalt pos.
			{
				String s=(String) map.get("itemOption");
				if(s.equals(getResources().getString(R.string.hdcpdefault)))
				{
					tvsetting.SetHdcpFlag(false);
					map.put("itemOption",getResources().getString(R.string.hdcpeep));
				}
				else
				{
					tvsetting.SetHdcpFlag(true);
					map.put("itemOption",getResources().getString(R.string.hdcpdefault));					
				}
				listitem.set(selecorPosition,map);
				dataChanged = true;
			}
			else if(itemName.equals(getResources().getString(R.string.OPT2)))	//usbboot
			{
				String s=(String) map.get("itemOption");
				if(s.equals(getResources().getString(R.string.On)))
				{
					map.put("itemOption",getResources().getString(R.string.Off));
					tvsetting.SetUsbbootState(0);
				}
				else
				{
					map.put("itemOption",getResources().getString(R.string.On));					
					tvsetting.SetUsbbootState(1);
				}
				listitem.set(selecorPosition,map);
				dataChanged = true;				
			}
			else if(itemName.equals(getResources().getString(R.string.OPT3)))	//LVDS
			{
				String s=(String) map.get("itemOption");
				if(s.equals(getResources().getString(R.string.LVDS_VESA)))
				{
					map.put("itemOption",getResources().getString(R.string.LVDS_JEIDA));
					tvsetting.SetLvdsState(1);
				}
				else
				{
					map.put("itemOption",getResources().getString(R.string.LVDS_VESA));					
					tvsetting.SetLvdsState(0);
				}
				listitem.set(selecorPosition,map);
				dataChanged = true;				
			}			
			else if(itemName.equals(getResources().getString(R.string.OPT4)))	//poweron picture
			{
				String s=(String) map.get("itemOption");
				if(s.equals(getResources().getString(R.string.On)))
				{
					map.put("itemOption",getResources().getString(R.string.Off));
					tvsetting.SetStartPicFlag(false);
				}
				else
				{
					map.put("itemOption",getResources().getString(R.string.On));					
					tvsetting.SetStartPicFlag(true);
				}
				listitem.set(selecorPosition,map);
				dataChanged = true;				
			}
			else if(itemName.equals(getResources().getString(R.string.OPT5)))	//online music
			{
				String s=(String) map.get("itemOption");
				if(s.equals(getResources().getString(R.string.On)))
				{
					map.put("itemOption",getResources().getString(R.string.Off));
					tvsetting.SetOnLineMusicFlag(false);
				}
				else
				{
					map.put("itemOption",getResources().getString(R.string.On));					
					tvsetting.SetOnLineMusicFlag(true);
				}
				listitem.set(selecorPosition,map);
				dataChanged = true;				
			}			
			else if(itemName.equals(getResources().getString(R.string.OPT6)))	//standby mode
			{
				String s=(String) map.get("itemOption");
				if(s.equals(getResources().getString(R.string.standby_completely)))
				{
					map.put("itemOption",getResources().getString(R.string.standby_simple));
					tvsetting.SetStandByModeFlag(true);
//					Log.d("good","sunny:facotry fast true");
				}
				else
				{
					map.put("itemOption",getResources().getString(R.string.standby_completely));					
					tvsetting.SetStandByModeFlag(false);
//					Log.d("good","sunny:facotry completely false");
				}
				listitem.set(selecorPosition,map);
				dataChanged = true;				
			}
			else if(itemName.equals((getResources().getString(R.string.HDMIEQMode))))	//hdmi_eq_mode
			{
				valueChangeAndSave(direction,hdmiEQMax,0,map,selecorPosition,false);
				bHdmiEqNotSaved = true;
				initItemId = selecorPosition;
				dataChanged = true;
			}
			else if(itemName.equals((getResources().getString(R.string.HDMIInternalMode))))	//HDMIInternalMode
			{
				numberArea = new StringBuffer((getResources().getString(R.string.tobeinput)));
				bEditable = true;
				map.put("itemOption",numberArea);
				listitem.set(selecorPosition,map);
				dataChanged = true;	
				bHdmiIntNotSaved = true;
				initItemId = selecorPosition;
			}
			else if(itemName.equals(getResources().getString(R.string.facotry_reset)))	//初始化	
			{
				if(!bFacResetReady)
				{
					map.put("itemOption", getResources().getString(R.string.confirmAgain));
					dataChanged = true;
					bFacResetReady = true;
					initItemId = selecorPosition;
				}
				else
				{
					bFacResetReady = false;
					selectedItemContent.setText(R.string.start);
					facSetUserDefault();
					return true;
				}
			}
			else if(itemName.equals((getResources().getString(R.string.pageup))))
			{
				if(pageNum-- == PAGE_ADVANCED_START)
				{
					pageNum = PAGE_ADVANCED_END;
				}
				pageChanged = true;
			}
			else if(itemName.equals((getResources().getString(R.string.pagedown))))
			{
				if(pageNum++ == PAGE_ADVANCED_END)
				{
					pageNum = PAGE_ADVANCED_START;
				}
				pageChanged = true;
			}
			else if(itemName.equals((getResources().getString(R.string.serialportSwitch))))
			{
				String s=(String) map.get("itemOption");
				tvsetting.FacSetSerialPortSwitchFlag(s.equals(getResources().getString(R.string.On))?false:true);
				map.put("itemOption",(s.equals(getResources().getString(R.string.On)))?getResources().getString(R.string.Off):getResources().getString(R.string.On));
				listitem.set(selecorPosition,map);
				dataChanged = true;				
			}
			break;	
		case PAGE_EEPROM_RW:	//eeprom读写页
			if(itemName.equals(getResources().getString(R.string.EEP_init)))	//初始化	
			{
				if(!initFirstKeyPressed)
				{
					map.put("itemOption", getResources().getString(R.string.confirmAgain));
					dataChanged = true;
					initFirstKeyPressed = true;
					initItemId = selecorPosition;
				}
				else
				{
					initFirstKeyPressed = false;
//					map.put("itemOption", getResources().getString(R.string.start));
//					listitem.set(selecorPosition, map);
//					factoryListAdapter.notifyDataSetChanged();
					selectedItemContent.setText(R.string.start);
					RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(800,100);
					params.addRule(RelativeLayout.CENTER_IN_PARENT);
					viewInfoText.setInfoText(R.string.initialProgressing);
					viewInfoText.setInfoWidth(800);
					layout.addView(viewInfoText,params);
					viewInfoText.requestFocus();
	
					Runnable myrun = new Runnable(){
	
						public void run() {
							try {
								tvsetting.InitEepromData();
								Thread.sleep(1000);
								
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}							
							Message msg=handler.obtainMessage();
							msg.sendToTarget();
						}
						
					};
					progressThread = new Thread(myrun);
					progressThread.start();
					return true;
				}
			}
			else if(itemName.equals(getResources().getString(R.string.EEP_save)))	//保存
			{
//				eepromWhrite(dataOfAddressPage, dataOfAddressOffset, dataOfData);
				int offsetAddr = dataOfAddressPage<<8;
				offsetAddr += dataOfAddressOffset;
				tvsetting.Factory_WriteEepromOneByte(offsetAddr,dataOfData);
				listitem.remove(3);
				list.setSelection(0);
				dataChanged = true;
			}	
			else{						//eeprom数据操作
				value=(Integer) map.get("itemValue");	//获取option数值
				value+=direction;
				if(itemName.equals(getResources().getString(R.string.EEP_page)))
				{
					if(value<0)	value=15;
					else if(value>15) value=0;					
				}
				else
				{
				if(value<0)	value=255;
				else if(value>255) value=0;
				}
				map.put("itemValue", value);
				map.put("itemOption", "0x"+Long.toHexString(value));
				listitem.set(selecorPosition,map);	
				if(itemName.equals(getResources().getString(R.string.EEP_page)))
				{
					dataOfAddressPage = value;
					if(list.getCount()==5) listitem.remove(3);	// 操作此项隐藏保存项
				}
				else if(itemName.equals(getResources().getString(R.string.EEP_pos)))
				{	
					dataOfAddressOffset = value;
					if(list.getCount()==5) listitem.remove(3);	// 操作此项隐藏保存项
				}
				else
				{
//					if(list.getCount()==4 && dataOfAddressPage!=8888 &&dataOfAddressOffset!=8888)
					if(list.getCount()==4)
					{
						map=new HashMap<String, Object>();
						map.put("itemText", getResources().getString(R.string.EEP_save));
						map.put("itemOption", getResources().getString(R.string.start));
						map.put("itemInfo", null);
						listitem.add(3, map);	//操作数据项，且页码和偏移量已经设置好则显示保存项
					}
					dataOfData = value;
				}
				dataChanged = true;
//				//第四行 保存
			}
			break;	
		case PAGE_EMC_CONTROL:	//扩频
			if(itemName.equals(getResources().getString(R.string.SPREAD_SP_EN)))
			{
				String s=(String) map.get("itemOption");
				map.put("itemOption", s.equals(getResources().getString(R.string.On))?getResources().getString(R.string.Off):getResources().getString(R.string.On));
				listitem.set(selecorPosition,map);
				if(s.equals(getResources().getString(R.string.On)))
					dataEMCSwitch = false;
				else 
					dataEMCSwitch = true;
				eepromDataChanged=true;
				dataChanged = true;	
			}
			else if(itemName.equals(getResources().getString(R.string.SP_AMPLITUDE)))
			{
				valueChangeAndSave(direction,3,0,map,selecorPosition,false);
				dataEMCAmpLow=(Integer) map.get("itemOption");
				dataChanged = true;				
			}
			else if(itemName.equals(getResources().getString(R.string.SP_PERIOD)))
			{
				valueChangeAndSave(direction,14,0,map,selecorPosition,false);
				dataEMCCycle=(Integer) map.get("itemOption");
				dataChanged = true;				
			}	
			else if(itemName.equals(getResources().getString(R.string.SP_AMPLITUDE_MSB)))
			{
				valueChangeAndSave(direction,4,0,map,selecorPosition,false);
				dataEMCAmp=(Integer) map.get("itemOption");
				dataChanged = true;				
			}	
//			else if(itemName.equals(getResources().getString(R.string.LVDS_ICTRL)))
//			{
//				valueChangeAndSave(direction,3,0,map,selecorPosition);
//				dataChanged = true;				
//			}	
			tvsetting.SetSSC(dataEMCAmp,dataEMCAmpLow, dataEMCCycle, dataEMCSwitch);
			break;
		default: break;				//新增加的页请在这里处理
		}
		if(dataChanged){
			factoryListAdapter.notifyDataSetChanged();	//通知adaptor数据有变，进行刷新
			return true;
		}
		else if(pageChanged)	//有翻页行为
		{
			setListData();		//重建Listview内容，刷新显示
			return true;					
		}
		else return false;
	}

/****************************************************************************
 * 该函数对密码锁的6位输入进行判断，如果和密码相等，则显示“进入”按钮，否则隐藏该按钮。
 * 同步刷新editview中的数据显示。	
 */
private	void passLockJudge()
	{

		passwordInputView.getText().clear();
		
		String number=(String)lock1Button.getText();
		Byte value =Byte.parseByte(number);
		passwordInputView.getText().append(value.toString());
		
		number=(String)lock2Button.getText();
		value =Byte.parseByte(number);
		passwordInputView.getText().append(value.toString());
		
		number=(String)lock3Button.getText();
		value =Byte.parseByte(number);
		passwordInputView.getText().append(value.toString());
		
		number=(String)lock4Button.getText();
		value =Byte.parseByte(number);
		passwordInputView.getText().append(value.toString());
		
		number=(String)lock5Button.getText();
		value =Byte.parseByte(number);
		passwordInputView.getText().append(value.toString());
		
		number=(String)lock6Button.getText();
		value =Byte.parseByte(number);
		passwordInputView.getText().append(value.toString());
		
		if(passwordInputView.getText().toString().equals(PASSWORD))
		{
			ensureButton.setVisibility(View.VISIBLE);
			ensureButton.requestFocus();
		}
		else
		{
			ensureButton.setVisibility(View.INVISIBLE);
		}
			
	}	
/**********************************************************************************
 * 该方法对每次按左右键后当前数值进行步进，并将计算得出的数值存储到eepromData变量中去，
 * 并将该变量的eeprom地址存储到eepromAddressPage、eepromAddressOffset中去，等待按键
 * 松开时存储到eeprom中去
 * @param step	步进
 * @param max
 * @param min
 * @param map	当前调整项的hashmap
 * @param pos	当前调整项的标号
 * @param isHex	当前数值是否为16进制显示
 */
private	void valueChangeAndSave(int step,int max,int min,HashMap<String, Object> map,int pos, boolean isHex)
	{
		int mStep = step;
		int mMax=max;
		int mMin=min;
		HashMap<String, Object> mMap = map;
		int selecorPosition = pos;
		int value=(Integer)( !isHex?mMap.get("itemOption"):mMap.get("itemValue"));	//获取option数值
		value+=mStep;
		if(value<mMin)	value=mMax;
		else if(value>mMax) value=mMin;
		if(!isHex)		//十进制显示的数字在这里
		{
			map.put("itemOption", value);
//			selectedItemContent.setText(Integer.toString(value));
		}
		else								//十六进制显示的数字在这里
		{
			map.put("itemOption", "0x"+Long.toHexString(value));
			map.put("itemValue", value);	
//			selectedItemContent.setText("0x"+Long.toHexString(value));
		}
		listitem.set(selecorPosition,mMap);	
		eepromDataChanged=true;	
	}
	private void setResultBundles()
	{
		Intent intent = new Intent();
		intent.putExtra("M_mode", bMmode);
		intent.putExtra("Source_changed", bInitMenu);
		intent.putExtra("outset_ok", bOutsetOK);
		setResult(RESULT_OK, intent);		
	}
	
	
	public int GetEqModeMax() {
        File f1 = new File("/sys/module/tvin_hdmirx/parameters/eq_mode_max");
        try {
            int adcvaule = 0;
            // FileWriter fw = new FileWriter(f1);
            // BufferedWriter buf = new BufferedWriter(fw);
            FileReader fw = new FileReader(f1);
            BufferedReader buf = new BufferedReader(fw);
            // buf.write(state);
            adcvaule = Integer.parseInt(buf.readLine());
            buf.close();
            Log.d("test", "get /sys/module/tvin_hdmirx/parameters/eq_mode_max: " + adcvaule);
            return adcvaule;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
	
	
	public int GetEqModeValue() {
        File f1 = new File("/sys/module/tvin_hdmirx/parameters/eq_mode");
        try {
            int adcvaule = 0;
            // FileWriter fw = new FileWriter(f1);
            // BufferedWriter buf = new BufferedWriter(fw);
            FileReader fw = new FileReader(f1);
            BufferedReader buf = new BufferedReader(fw);
            // buf.write(state);
            adcvaule = Integer.parseInt(buf.readLine());
            buf.close();
            Log.d("test", "get /sys/module/tvin_hdmirx/parameters/eq_mode: " + adcvaule);
            return adcvaule;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
	
	private void SetEqModeValue(int value) {
    	//tvsetting.SaveSysStandby();
        try {
        	String writerstring;
        	writerstring = "eq_mode"+Integer.toString(value)+"\n";
            BufferedWriter writer = new BufferedWriter(new FileWriter("/sys/class/hdmirx/hdmirx0/debug"), 2);
            try {
                writer.write(writerstring);
            } finally {
                writer.close();
            }
            Log.d("test", "/sys/module/tvin_hdmirx/parameters/eq_mode "
                + "/sys/module/tvin_hdmirx/parameters/eq_mode  ok!"+value+hdmiEQMax);
        } catch (IOException e) {
            Log.e("test", "/sys/module/tvin_hdmirx/parameters/eq_mode "
                + "/sys/module/tvin_hdmirx/parameters/eq_mode", e);
        }
    }
	
	public int GetInternalModeValue() {
        File f1 = new File("/sys/module/tvin_hdmirx/parameters/internal_mode");
        try {
            int adcvaule = 0;
            // FileWriter fw = new FileWriter(f1);
            // BufferedWriter buf = new BufferedWriter(fw);
            FileReader fw = new FileReader(f1);
            BufferedReader buf = new BufferedReader(fw);
            // buf.write(state);
            adcvaule = Integer.parseInt(buf.readLine());
            buf.close();
            Log.d("test", "get /sys/module/tvin_hdmirx/parameters/eq_mode: " + adcvaule);
            return adcvaule;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
	
	private void SetInternalModeValue(int value) {
    	//tvsetting.SaveSysStandby();
        try {
        	String writerstring;
        	writerstring = "internal_mode"+Integer.toString(value)+"\n";
            BufferedWriter writer = new BufferedWriter(new FileWriter("/sys/class/hdmirx/hdmirx0/debug"), 2);
            try {
                writer.write(writerstring);
            } finally {
                writer.close();
            }
            Log.d("test", "/sys/module/tvin_hdmirx/parameters/internal_mode "
                + "/sys/module/tvin_hdmirx/parameters/internal_mode ok");
        } catch (IOException e) {
            Log.e("test", "/sys/module/tvin_hdmirx/parameters/internal_mode "
                + "/sys/module/tvin_hdmirx/parameters/internal_mode", e);
        }
    }
/*********************************************************************************
 * 本函数处理listitem的其中一项有确认键按下时的操作。
 * @param arg0
 * @param arg1	当前item的view
 * @param arg2	item id
 * @param arg3
 */
	private void itemEnterKeyProcess(AdapterView<?> arg0, View arg1, int arg2,
			long arg3)
	{

		TextView tempView =(TextView) arg1.findViewById(R.id.ListText);
		String curItem = (String) tempView.getText();
		if(curItem.contains(getResources().getString(R.string.MACAddr)))
		{
			HashMap<String, Object> tempMap = listitem.get(arg2);
			String tempStr = (String) tempMap.get("itemText");
			tempStr = (String) tempMap.get("itemOption");
			if(tempStr.equals(getResources().getString(R.string.MACDefault)))
			{
				tvsetting.SetDefaultMacAddr();
			}
			else if(tempStr.equals(getResources().getString(R.string.MACGet)))
			{
				tvsetting.SaveMacAddr();
			}
			int[] macaddr = tvsetting.GetMacAddr();
			StringBuffer s=new StringBuffer();
			for(int j=0;j<MAC_LEN;j++)
			{
				if(macaddr[j]!=0)
				{
					if(j!=MAC_LEN -1)
						tempStr = Integer.toHexString(macaddr[j])+":";
					else 
						tempStr = Integer.toHexString(macaddr[j]);
					s.append(tempStr.toUpperCase());
				}
				else
					s.append("00:");
			}
			tempMap.put("itemText", getResources().getString(R.string.MACAddr)+":"+s);
			listitem.set(arg2,tempMap);
			factoryListAdapter.notifyDataSetChanged();
		}
		else if(curItem.equals(getResources().getString(R.string.HDMIInternalMode)))
		{
			if(bEditable)
			{
				HashMap<String, Object> tempMap = listitem.get(arg2);
				// if no data input
				if(numberArea.toString().equals(getResources().getString(R.string.tobeinput)))
				{
					int value = (Integer) tempMap.get("itemValue");
					tempMap.put("itemOption","0x"+Integer.toHexString(value));
				}
				else
				{
					int value = Integer.parseInt(numberArea.toString());
					tempMap.put("itemOption","0x"+Integer.toHexString(value));
					tempMap.put("itemValue",value);
					tvsetting.FacSetHdmiInternalMode(value);
					SetInternalModeValue(value);
//					Log.d("good","HDMIInternalMode value="+value);
				}
				listitem.set(arg2,tempMap);
				factoryListAdapter.notifyDataSetChanged();
				bEditable = false;
				bHdmiIntNotSaved = false;
			}
		}
		else if(curItem.equals(getResources().getString(R.string.HDMIEQMode)))
		{
			TextView contentView =(TextView) arg1.findViewById(R.id.ListButton);
			String curContent = (String) contentView.getText();
			int value = Integer.parseInt(curContent);
			tvsetting.FacSetHdmiEQMode(value);
			SetEqModeValue(value);
			bHdmiEqNotSaved = false;
//			Log.d("good","HDMIEQMode value="+value);					
		}
		else optionChangeHandler(1);			//handle the enter key as the right key

		//如有数据需要存入eeprom
		if(eepromDataChanged)
		{
			eepromDataChanged=false;
		}	

		//下面这个if是如果当前项是eeprom页码和偏移量，则当按键松开时读取该位置的eeprom数据
		if(pageNum==PAGE_EEPROM_RW &&( list.getSelectedItemId()== 0||list.getSelectedItemId()==1))
		{
			{
				HashMap<String, Object> tempMap = listitem.get(2);	//读取数据行map
				int offsetAddr= dataOfAddressPage<<8;
				offsetAddr += dataOfAddressOffset;
				int curData=tvsetting.Factory_ReadEepromOneByte(offsetAddr);
				tempMap.put("itemOption", "0x"+Long.toHexString(curData));
				tempMap.put("itemValue", curData);	
				listitem.set(2,tempMap);
			}	
			factoryListAdapter.notifyDataSetChanged();
		}				
			
	}
	void facSetUserDefault()
	{
		bMmode= false;
		bInitMenu = true;
		bOutsetOK=true;
		setResultBundles();				
		this.finish();
		tvsetting.FacSet_BURN_FLAG(false);
        tvsetting.SetFacOutDefault();
		tvsetting.FacSet_ONEKEY_ONOFF(0);
		tvsetting.SetUsbbootState(0);
		MenuDataBase mdb = new MenuDataBase(this);  
		mdb.saveTvOtherParam("shortcut_setup_sys_filelist_mode_folder","SysSetupMenu" );
		str_lan = getResources().getConfiguration().locale.getLanguage();
		if(str_lan.equals("en"))
		{
			String[] mentemp = {"shortcut_setup_sys_language_zh",""};
            tvsetting.SettingLanguage(mentemp); 
		}		
	}
}