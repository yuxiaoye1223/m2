package com.amlogic.tvjni;

interface Itvservice {
	void UartSend(String name,String value);
	void SaveParam(String name,String value);
	String GetParam(String name);
	int GetTvParam(String MenuItemName);
	int GetTvProgressBar(String MenuItemName);
	void SaveCustomEQGain(int item, int val);
	void GetCurEQGain(inout int[] dataBuf);
	int GetCurAudioSoundMode();
	void SaveCurAudioSoundMode(int soundmode);
	void SetCurAudioTrebleVolume(int cur_vol);
	void SetCurAudioBassVolume(int cur_vol);
	int GetCurAudioSupperBassSwitch();
	int GetCurAudioSRSSurround();
	int GetCurAudioVolume();
	void SaveCurAudioVolume(int progress);
	int num();
	void setnum(int num);
	void addorreducenum(int flag);
	void setSearchHandler();
	void setSignalDetectHandler();
	void setVGAMessageHandler();
	void setDreampanelHandler();
	void setDreampanelDemo(boolean flag);
	void setTunerLastChannel();
	void setTunerChannel(int num);
	void updateTunerFrequencyUI();
	boolean getSearchStop();
	void SearchChanel(boolean f,boolean s,boolean t);
	boolean getSearchDown();
	void setSearchDown(boolean flag);
	void FintTune(int val);
	boolean getAutoSearchOn();
	int getMaxFreq();
	int getMinFreq();
	int getMaxVHFHFreq();
	int getMaxVHFLFreq();
	int manuFreq();
	void exchangeChannelInfo(int sourceCh,int targetCh);
	void SaveTvCurrentChannel();
	int getCurrnetChNumber();
	boolean ChInfojump();
	boolean ChInfoaft();
	int ChInfovideostd();
	int ChInfosoundstd();
	boolean ismCurAudioVolumeInc();
	void SetAudioMainVolume();
	boolean GetCurSourceTV();
	boolean GetCurSourceAV1();
	boolean GetCurSourceYPBPR1();
	boolean GetCurSourceHDMI1();
	boolean GetCurSourceHDMI2();
	boolean GetCurSourceHDMI3();
	boolean GetCurSourceVGA();
	int changeTunerChannel(boolean flag);
	void changeCurrentChannelToLastChannel();
	boolean ismCustomVolumeMute();
	void setmCustomVolumeMute(boolean flag);
	int GetAmplifierMute();
	void SetAudioSupperBassVolume();
	int GetAvColorSys();
	String GetSigFormatName();
	int LoadNumberInputLimit();
	void SaveNumberInputLimit(int flag);
	int LoadNavigateFlag();
	void SaveNavigateFlag(int flag);
	void changeCurrentChannelToChannel(int flag);
	void setViiColorDemo(int demoMode);
	void setBaseColor(int cmMode);
	void StartTv();
	boolean CloseTv();
	int LineInSelectChannel(int num);
	void IICBusOn();
	void IICBusOff();
	void Factory_WriteEepromOneByte(int offset, int value);
	void Factory_WriteEepromNByte(int offset, int len, inout int[] buf);
	int Factory_ReadEepromOneByte(int offset);
	int[] Factory_ReadEepromNByte(int offset, int len);
	void FacSet_RGBogo(String channelSelect, String ganioffsetSel, int value);
	int FacGet_RGBogo(String channelSelect, String ganioffsetSel);
	int FacSet_AdcAutoCal();
	int FacGet_AdcAutoCalResult();
	int FacGet_AdcAutoCalStatus();
	void FacSet_BURN_FLAG(boolean turnOn);
	boolean FacGet_BURN_FLAG();
	void FacSet_ONEKEY_ONOFF(int value);
	int FacGet_ONEKEY_ONOFF();
	void SetDefaultMacAddr();
	int[] GetMacAddr();
	void SaveMacAddr();
	int GetAdbState();
	void SetAdbState(int flag);
	String LoadBarCode();
	int GetfactoryFlag();
	void SetfactoryFlag(int flag);
	boolean FacGet_DEF_HDCP_FLAG();
	void FacSet_DEF_HDCP_FLAG(boolean loadDef);
	int InitEepromData();
	void factoryBurnMode(boolean flag);
	String LoadEepromDate();
	void UiSetAudioMutePanel(int value);
	void SetBurnDefault();
	void SetFacOutDefault();
	void SetSSC(int range,int lowrange,int cycle,boolean onoff);
	int[] GetSscDate();
	int GetUsbbootState();
	void SetUsbbootState(int flag);
	int GetSigTransFormat();
	int Get3DStatus();
	void SetStandbyKeyMode(boolean SetSSC);
	void FacSetPanelType(int paneltype);
	String FacGetPanelType();
	int GetLvdsState();
	void SetLvdsState(int flag);
	int isVgaFmtInHdmi();
	boolean GetOnLineMusicFlag();
	void SetViewMode(int mode);
	void UiSetScrMode(int mode);
	boolean GetStartPicFlag();
	void SetOnLineMusicFlag(boolean isOn);
	void SetStartPicFlag(boolean isOn);
	 boolean GetStandByModeFlag();
	void SetStandByModeFlag(boolean isOn);
	void SaveSysStandby();
	int GetSysStatus();
	int GetSystemAutoSuspending();
	void SetSystemAutoSuspending(int num);
	void FacSetHdmiEQMode(int value);
	int FacGetHdmiEQMode();
	void FacSetHdmiInternalMode(int value);
	int FacGetHdmiInternalMode();
	boolean isSrcSwtichDone();
	void FacSetSerialPortSwitchFlag(boolean flag);
	boolean FacGetSerialPortSwitchFlag();
	void ResumeLastBLValue();
	void setWhiteScreen();
}