/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: D:\\skyworthm2\\Packages\\tvservice\\src\\com\\amlogic\\tvjni\\Itvservice.aidl
 */
package com.amlogic.tvjni;
public interface Itvservice extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.amlogic.tvjni.Itvservice
{
private static final java.lang.String DESCRIPTOR = "com.amlogic.tvjni.Itvservice";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.amlogic.tvjni.Itvservice interface,
 * generating a proxy if needed.
 */
public static com.amlogic.tvjni.Itvservice asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = (android.os.IInterface)obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.amlogic.tvjni.Itvservice))) {
return ((com.amlogic.tvjni.Itvservice)iin);
}
return new com.amlogic.tvjni.Itvservice.Stub.Proxy(obj);
}
public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_UartSend:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
this.UartSend(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_SaveParam:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
this.SaveParam(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_GetParam:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _result = this.GetParam(_arg0);
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_GetTvParam:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int _result = this.GetTvParam(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_GetTvProgressBar:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
int _result = this.GetTvProgressBar(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_SaveCustomEQGain:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
this.SaveCustomEQGain(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_GetCurEQGain:
{
data.enforceInterface(DESCRIPTOR);
int[] _arg0;
_arg0 = data.createIntArray();
this.GetCurEQGain(_arg0);
reply.writeNoException();
reply.writeIntArray(_arg0);
return true;
}
case TRANSACTION_GetCurAudioSoundMode:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.GetCurAudioSoundMode();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_SaveCurAudioSoundMode:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.SaveCurAudioSoundMode(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_SetCurAudioTrebleVolume:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.SetCurAudioTrebleVolume(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_SetCurAudioBassVolume:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.SetCurAudioBassVolume(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_GetCurAudioSupperBassSwitch:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.GetCurAudioSupperBassSwitch();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_GetCurAudioSRSSurround:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.GetCurAudioSRSSurround();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_GetCurAudioVolume:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.GetCurAudioVolume();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_SaveCurAudioVolume:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.SaveCurAudioVolume(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_num:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.num();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setnum:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.setnum(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_addorreducenum:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.addorreducenum(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_setSearchHandler:
{
data.enforceInterface(DESCRIPTOR);
this.setSearchHandler();
reply.writeNoException();
return true;
}
case TRANSACTION_setSignalDetectHandler:
{
data.enforceInterface(DESCRIPTOR);
this.setSignalDetectHandler();
reply.writeNoException();
return true;
}
case TRANSACTION_setVGAMessageHandler:
{
data.enforceInterface(DESCRIPTOR);
this.setVGAMessageHandler();
reply.writeNoException();
return true;
}
case TRANSACTION_setDreampanelHandler:
{
data.enforceInterface(DESCRIPTOR);
this.setDreampanelHandler();
reply.writeNoException();
return true;
}
case TRANSACTION_setDreampanelDemo:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
this.setDreampanelDemo(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_setTunerLastChannel:
{
data.enforceInterface(DESCRIPTOR);
this.setTunerLastChannel();
reply.writeNoException();
return true;
}
case TRANSACTION_setTunerChannel:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.setTunerChannel(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_updateTunerFrequencyUI:
{
data.enforceInterface(DESCRIPTOR);
this.updateTunerFrequencyUI();
reply.writeNoException();
return true;
}
case TRANSACTION_getSearchStop:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.getSearchStop();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_SearchChanel:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
boolean _arg1;
_arg1 = (0!=data.readInt());
boolean _arg2;
_arg2 = (0!=data.readInt());
this.SearchChanel(_arg0, _arg1, _arg2);
reply.writeNoException();
return true;
}
case TRANSACTION_getSearchDown:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.getSearchDown();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_setSearchDown:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
this.setSearchDown(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_FintTune:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.FintTune(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_getAutoSearchOn:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.getAutoSearchOn();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_getMaxFreq:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getMaxFreq();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getMinFreq:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getMinFreq();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getMaxVHFHFreq:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getMaxVHFHFreq();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getMaxVHFLFreq:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getMaxVHFLFreq();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_manuFreq:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.manuFreq();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_exchangeChannelInfo:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
this.exchangeChannelInfo(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_SaveTvCurrentChannel:
{
data.enforceInterface(DESCRIPTOR);
this.SaveTvCurrentChannel();
reply.writeNoException();
return true;
}
case TRANSACTION_getCurrnetChNumber:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getCurrnetChNumber();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_ChInfojump:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.ChInfojump();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_ChInfoaft:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.ChInfoaft();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_ChInfovideostd:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.ChInfovideostd();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_ChInfosoundstd:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.ChInfosoundstd();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_ismCurAudioVolumeInc:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.ismCurAudioVolumeInc();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_SetAudioMainVolume:
{
data.enforceInterface(DESCRIPTOR);
this.SetAudioMainVolume();
reply.writeNoException();
return true;
}
case TRANSACTION_GetCurSourceTV:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.GetCurSourceTV();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_GetCurSourceAV1:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.GetCurSourceAV1();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_GetCurSourceYPBPR1:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.GetCurSourceYPBPR1();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_GetCurSourceHDMI1:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.GetCurSourceHDMI1();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_GetCurSourceHDMI2:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.GetCurSourceHDMI2();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_GetCurSourceHDMI3:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.GetCurSourceHDMI3();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_GetCurSourceVGA:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.GetCurSourceVGA();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_changeTunerChannel:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
int _result = this.changeTunerChannel(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_changeCurrentChannelToLastChannel:
{
data.enforceInterface(DESCRIPTOR);
this.changeCurrentChannelToLastChannel();
reply.writeNoException();
return true;
}
case TRANSACTION_ismCustomVolumeMute:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.ismCustomVolumeMute();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_setmCustomVolumeMute:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
this.setmCustomVolumeMute(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_GetAmplifierMute:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.GetAmplifierMute();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_SetAudioSupperBassVolume:
{
data.enforceInterface(DESCRIPTOR);
this.SetAudioSupperBassVolume();
reply.writeNoException();
return true;
}
case TRANSACTION_GetAvColorSys:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.GetAvColorSys();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_GetSigFormatName:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _result = this.GetSigFormatName();
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_LoadNumberInputLimit:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.LoadNumberInputLimit();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_SaveNumberInputLimit:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.SaveNumberInputLimit(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_LoadNavigateFlag:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.LoadNavigateFlag();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_SaveNavigateFlag:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.SaveNavigateFlag(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_changeCurrentChannelToChannel:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.changeCurrentChannelToChannel(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_setViiColorDemo:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.setViiColorDemo(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_setBaseColor:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.setBaseColor(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_StartTv:
{
data.enforceInterface(DESCRIPTOR);
this.StartTv();
reply.writeNoException();
return true;
}
case TRANSACTION_CloseTv:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.CloseTv();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_LineInSelectChannel:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _result = this.LineInSelectChannel(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_IICBusOn:
{
data.enforceInterface(DESCRIPTOR);
this.IICBusOn();
reply.writeNoException();
return true;
}
case TRANSACTION_IICBusOff:
{
data.enforceInterface(DESCRIPTOR);
this.IICBusOff();
reply.writeNoException();
return true;
}
case TRANSACTION_Factory_WriteEepromOneByte:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
this.Factory_WriteEepromOneByte(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_Factory_WriteEepromNByte:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
int[] _arg2;
_arg2 = data.createIntArray();
this.Factory_WriteEepromNByte(_arg0, _arg1, _arg2);
reply.writeNoException();
reply.writeIntArray(_arg2);
return true;
}
case TRANSACTION_Factory_ReadEepromOneByte:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _result = this.Factory_ReadEepromOneByte(_arg0);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_Factory_ReadEepromNByte:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
int[] _result = this.Factory_ReadEepromNByte(_arg0, _arg1);
reply.writeNoException();
reply.writeIntArray(_result);
return true;
}
case TRANSACTION_FacSet_RGBogo:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
int _arg2;
_arg2 = data.readInt();
this.FacSet_RGBogo(_arg0, _arg1, _arg2);
reply.writeNoException();
return true;
}
case TRANSACTION_FacGet_RGBogo:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
int _result = this.FacGet_RGBogo(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_FacSet_AdcAutoCal:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.FacSet_AdcAutoCal();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_FacGet_AdcAutoCalResult:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.FacGet_AdcAutoCalResult();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_FacGet_AdcAutoCalStatus:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.FacGet_AdcAutoCalStatus();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_FacSet_BURN_FLAG:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
this.FacSet_BURN_FLAG(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_FacGet_BURN_FLAG:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.FacGet_BURN_FLAG();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_FacSet_ONEKEY_ONOFF:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.FacSet_ONEKEY_ONOFF(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_FacGet_ONEKEY_ONOFF:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.FacGet_ONEKEY_ONOFF();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_SetDefaultMacAddr:
{
data.enforceInterface(DESCRIPTOR);
this.SetDefaultMacAddr();
reply.writeNoException();
return true;
}
case TRANSACTION_GetMacAddr:
{
data.enforceInterface(DESCRIPTOR);
int[] _result = this.GetMacAddr();
reply.writeNoException();
reply.writeIntArray(_result);
return true;
}
case TRANSACTION_SaveMacAddr:
{
data.enforceInterface(DESCRIPTOR);
this.SaveMacAddr();
reply.writeNoException();
return true;
}
case TRANSACTION_GetAdbState:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.GetAdbState();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_SetAdbState:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.SetAdbState(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_LoadBarCode:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _result = this.LoadBarCode();
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_GetfactoryFlag:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.GetfactoryFlag();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_SetfactoryFlag:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.SetfactoryFlag(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_FacGet_DEF_HDCP_FLAG:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.FacGet_DEF_HDCP_FLAG();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_FacSet_DEF_HDCP_FLAG:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
this.FacSet_DEF_HDCP_FLAG(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_InitEepromData:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.InitEepromData();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_factoryBurnMode:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
this.factoryBurnMode(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_LoadEepromDate:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _result = this.LoadEepromDate();
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_UiSetAudioMutePanel:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.UiSetAudioMutePanel(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_SetBurnDefault:
{
data.enforceInterface(DESCRIPTOR);
this.SetBurnDefault();
reply.writeNoException();
return true;
}
case TRANSACTION_SetFacOutDefault:
{
data.enforceInterface(DESCRIPTOR);
this.SetFacOutDefault();
reply.writeNoException();
return true;
}
case TRANSACTION_SetSSC:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
boolean _arg3;
_arg3 = (0!=data.readInt());
this.SetSSC(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
return true;
}
case TRANSACTION_GetSscDate:
{
data.enforceInterface(DESCRIPTOR);
int[] _result = this.GetSscDate();
reply.writeNoException();
reply.writeIntArray(_result);
return true;
}
case TRANSACTION_GetUsbbootState:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.GetUsbbootState();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_SetUsbbootState:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.SetUsbbootState(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_GetSigTransFormat:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.GetSigTransFormat();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_Get3DStatus:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.Get3DStatus();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_SetStandbyKeyMode:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
this.SetStandbyKeyMode(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_FacSetPanelType:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.FacSetPanelType(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_FacGetPanelType:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _result = this.FacGetPanelType();
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_GetLvdsState:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.GetLvdsState();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_SetLvdsState:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.SetLvdsState(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_isVgaFmtInHdmi:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.isVgaFmtInHdmi();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_GetOnLineMusicFlag:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.GetOnLineMusicFlag();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_SetViewMode:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.SetViewMode(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_UiSetScrMode:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.UiSetScrMode(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_GetStartPicFlag:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.GetStartPicFlag();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_SetOnLineMusicFlag:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
this.SetOnLineMusicFlag(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_SetStartPicFlag:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
this.SetStartPicFlag(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_GetStandByModeFlag:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.GetStandByModeFlag();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_SetStandByModeFlag:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
this.SetStandByModeFlag(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_SaveSysStandby:
{
data.enforceInterface(DESCRIPTOR);
this.SaveSysStandby();
reply.writeNoException();
return true;
}
case TRANSACTION_GetSysStatus:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.GetSysStatus();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_GetSystemAutoSuspending:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.GetSystemAutoSuspending();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_SetSystemAutoSuspending:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.SetSystemAutoSuspending(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_FacSetHdmiEQMode:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.FacSetHdmiEQMode(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_FacGetHdmiEQMode:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.FacGetHdmiEQMode();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_FacSetHdmiInternalMode:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.FacSetHdmiInternalMode(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_FacGetHdmiInternalMode:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.FacGetHdmiInternalMode();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_isSrcSwtichDone:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.isSrcSwtichDone();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_FacSetSerialPortSwitchFlag:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
this.FacSetSerialPortSwitchFlag(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_FacGetSerialPortSwitchFlag:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.FacGetSerialPortSwitchFlag();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_ResumeLastBLValue:
{
data.enforceInterface(DESCRIPTOR);
this.ResumeLastBLValue();
reply.writeNoException();
return true;
}
case TRANSACTION_setWhiteScreen:
{
data.enforceInterface(DESCRIPTOR);
this.setWhiteScreen();
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.amlogic.tvjni.Itvservice
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
public void UartSend(java.lang.String name, java.lang.String value) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(name);
_data.writeString(value);
mRemote.transact(Stub.TRANSACTION_UartSend, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void SaveParam(java.lang.String name, java.lang.String value) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(name);
_data.writeString(value);
mRemote.transact(Stub.TRANSACTION_SaveParam, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public java.lang.String GetParam(java.lang.String name) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(name);
mRemote.transact(Stub.TRANSACTION_GetParam, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int GetTvParam(java.lang.String MenuItemName) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(MenuItemName);
mRemote.transact(Stub.TRANSACTION_GetTvParam, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int GetTvProgressBar(java.lang.String MenuItemName) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(MenuItemName);
mRemote.transact(Stub.TRANSACTION_GetTvProgressBar, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public void SaveCustomEQGain(int item, int val) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(item);
_data.writeInt(val);
mRemote.transact(Stub.TRANSACTION_SaveCustomEQGain, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void GetCurEQGain(int[] dataBuf) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeIntArray(dataBuf);
mRemote.transact(Stub.TRANSACTION_GetCurEQGain, _data, _reply, 0);
_reply.readException();
_reply.readIntArray(dataBuf);
}
finally {
_reply.recycle();
_data.recycle();
}
}
public int GetCurAudioSoundMode() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_GetCurAudioSoundMode, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public void SaveCurAudioSoundMode(int soundmode) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(soundmode);
mRemote.transact(Stub.TRANSACTION_SaveCurAudioSoundMode, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void SetCurAudioTrebleVolume(int cur_vol) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(cur_vol);
mRemote.transact(Stub.TRANSACTION_SetCurAudioTrebleVolume, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void SetCurAudioBassVolume(int cur_vol) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(cur_vol);
mRemote.transact(Stub.TRANSACTION_SetCurAudioBassVolume, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public int GetCurAudioSupperBassSwitch() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_GetCurAudioSupperBassSwitch, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int GetCurAudioSRSSurround() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_GetCurAudioSRSSurround, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int GetCurAudioVolume() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_GetCurAudioVolume, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public void SaveCurAudioVolume(int progress) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(progress);
mRemote.transact(Stub.TRANSACTION_SaveCurAudioVolume, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public int num() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_num, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public void setnum(int num) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(num);
mRemote.transact(Stub.TRANSACTION_setnum, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void addorreducenum(int flag) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(flag);
mRemote.transact(Stub.TRANSACTION_addorreducenum, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void setSearchHandler() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_setSearchHandler, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void setSignalDetectHandler() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_setSignalDetectHandler, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void setVGAMessageHandler() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_setVGAMessageHandler, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void setDreampanelHandler() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_setDreampanelHandler, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void setDreampanelDemo(boolean flag) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((flag)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_setDreampanelDemo, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void setTunerLastChannel() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_setTunerLastChannel, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void setTunerChannel(int num) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(num);
mRemote.transact(Stub.TRANSACTION_setTunerChannel, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void updateTunerFrequencyUI() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_updateTunerFrequencyUI, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public boolean getSearchStop() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getSearchStop, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public void SearchChanel(boolean f, boolean s, boolean t) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((f)?(1):(0)));
_data.writeInt(((s)?(1):(0)));
_data.writeInt(((t)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_SearchChanel, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public boolean getSearchDown() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getSearchDown, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public void setSearchDown(boolean flag) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((flag)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_setSearchDown, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void FintTune(int val) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(val);
mRemote.transact(Stub.TRANSACTION_FintTune, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public boolean getAutoSearchOn() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getAutoSearchOn, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int getMaxFreq() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getMaxFreq, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int getMinFreq() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getMinFreq, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int getMaxVHFHFreq() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getMaxVHFHFreq, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int getMaxVHFLFreq() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getMaxVHFLFreq, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int manuFreq() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_manuFreq, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public void exchangeChannelInfo(int sourceCh, int targetCh) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(sourceCh);
_data.writeInt(targetCh);
mRemote.transact(Stub.TRANSACTION_exchangeChannelInfo, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void SaveTvCurrentChannel() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_SaveTvCurrentChannel, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public int getCurrnetChNumber() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getCurrnetChNumber, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public boolean ChInfojump() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_ChInfojump, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public boolean ChInfoaft() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_ChInfoaft, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int ChInfovideostd() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_ChInfovideostd, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int ChInfosoundstd() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_ChInfosoundstd, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public boolean ismCurAudioVolumeInc() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_ismCurAudioVolumeInc, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public void SetAudioMainVolume() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_SetAudioMainVolume, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public boolean GetCurSourceTV() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_GetCurSourceTV, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public boolean GetCurSourceAV1() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_GetCurSourceAV1, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public boolean GetCurSourceYPBPR1() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_GetCurSourceYPBPR1, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public boolean GetCurSourceHDMI1() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_GetCurSourceHDMI1, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public boolean GetCurSourceHDMI2() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_GetCurSourceHDMI2, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public boolean GetCurSourceHDMI3() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_GetCurSourceHDMI3, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public boolean GetCurSourceVGA() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_GetCurSourceVGA, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int changeTunerChannel(boolean flag) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((flag)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_changeTunerChannel, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public void changeCurrentChannelToLastChannel() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_changeCurrentChannelToLastChannel, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public boolean ismCustomVolumeMute() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_ismCustomVolumeMute, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public void setmCustomVolumeMute(boolean flag) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((flag)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_setmCustomVolumeMute, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public int GetAmplifierMute() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_GetAmplifierMute, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public void SetAudioSupperBassVolume() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_SetAudioSupperBassVolume, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public int GetAvColorSys() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_GetAvColorSys, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public java.lang.String GetSigFormatName() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_GetSigFormatName, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int LoadNumberInputLimit() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_LoadNumberInputLimit, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public void SaveNumberInputLimit(int flag) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(flag);
mRemote.transact(Stub.TRANSACTION_SaveNumberInputLimit, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public int LoadNavigateFlag() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_LoadNavigateFlag, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public void SaveNavigateFlag(int flag) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(flag);
mRemote.transact(Stub.TRANSACTION_SaveNavigateFlag, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void changeCurrentChannelToChannel(int flag) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(flag);
mRemote.transact(Stub.TRANSACTION_changeCurrentChannelToChannel, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void setViiColorDemo(int demoMode) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(demoMode);
mRemote.transact(Stub.TRANSACTION_setViiColorDemo, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void setBaseColor(int cmMode) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(cmMode);
mRemote.transact(Stub.TRANSACTION_setBaseColor, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void StartTv() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_StartTv, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public boolean CloseTv() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_CloseTv, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int LineInSelectChannel(int num) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(num);
mRemote.transact(Stub.TRANSACTION_LineInSelectChannel, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public void IICBusOn() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_IICBusOn, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void IICBusOff() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_IICBusOff, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void Factory_WriteEepromOneByte(int offset, int value) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(offset);
_data.writeInt(value);
mRemote.transact(Stub.TRANSACTION_Factory_WriteEepromOneByte, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void Factory_WriteEepromNByte(int offset, int len, int[] buf) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(offset);
_data.writeInt(len);
_data.writeIntArray(buf);
mRemote.transact(Stub.TRANSACTION_Factory_WriteEepromNByte, _data, _reply, 0);
_reply.readException();
_reply.readIntArray(buf);
}
finally {
_reply.recycle();
_data.recycle();
}
}
public int Factory_ReadEepromOneByte(int offset) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(offset);
mRemote.transact(Stub.TRANSACTION_Factory_ReadEepromOneByte, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int[] Factory_ReadEepromNByte(int offset, int len) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int[] _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(offset);
_data.writeInt(len);
mRemote.transact(Stub.TRANSACTION_Factory_ReadEepromNByte, _data, _reply, 0);
_reply.readException();
_result = _reply.createIntArray();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public void FacSet_RGBogo(java.lang.String channelSelect, java.lang.String ganioffsetSel, int value) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(channelSelect);
_data.writeString(ganioffsetSel);
_data.writeInt(value);
mRemote.transact(Stub.TRANSACTION_FacSet_RGBogo, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public int FacGet_RGBogo(java.lang.String channelSelect, java.lang.String ganioffsetSel) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(channelSelect);
_data.writeString(ganioffsetSel);
mRemote.transact(Stub.TRANSACTION_FacGet_RGBogo, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int FacSet_AdcAutoCal() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_FacSet_AdcAutoCal, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int FacGet_AdcAutoCalResult() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_FacGet_AdcAutoCalResult, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int FacGet_AdcAutoCalStatus() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_FacGet_AdcAutoCalStatus, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public void FacSet_BURN_FLAG(boolean turnOn) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((turnOn)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_FacSet_BURN_FLAG, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public boolean FacGet_BURN_FLAG() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_FacGet_BURN_FLAG, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public void FacSet_ONEKEY_ONOFF(int value) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(value);
mRemote.transact(Stub.TRANSACTION_FacSet_ONEKEY_ONOFF, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public int FacGet_ONEKEY_ONOFF() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_FacGet_ONEKEY_ONOFF, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public void SetDefaultMacAddr() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_SetDefaultMacAddr, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public int[] GetMacAddr() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int[] _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_GetMacAddr, _data, _reply, 0);
_reply.readException();
_result = _reply.createIntArray();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public void SaveMacAddr() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_SaveMacAddr, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public int GetAdbState() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_GetAdbState, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public void SetAdbState(int flag) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(flag);
mRemote.transact(Stub.TRANSACTION_SetAdbState, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public java.lang.String LoadBarCode() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_LoadBarCode, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int GetfactoryFlag() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_GetfactoryFlag, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public void SetfactoryFlag(int flag) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(flag);
mRemote.transact(Stub.TRANSACTION_SetfactoryFlag, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public boolean FacGet_DEF_HDCP_FLAG() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_FacGet_DEF_HDCP_FLAG, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public void FacSet_DEF_HDCP_FLAG(boolean loadDef) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((loadDef)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_FacSet_DEF_HDCP_FLAG, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public int InitEepromData() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_InitEepromData, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public void factoryBurnMode(boolean flag) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((flag)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_factoryBurnMode, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public java.lang.String LoadEepromDate() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_LoadEepromDate, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public void UiSetAudioMutePanel(int value) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(value);
mRemote.transact(Stub.TRANSACTION_UiSetAudioMutePanel, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void SetBurnDefault() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_SetBurnDefault, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void SetFacOutDefault() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_SetFacOutDefault, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void SetSSC(int range, int lowrange, int cycle, boolean onoff) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(range);
_data.writeInt(lowrange);
_data.writeInt(cycle);
_data.writeInt(((onoff)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_SetSSC, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public int[] GetSscDate() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int[] _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_GetSscDate, _data, _reply, 0);
_reply.readException();
_result = _reply.createIntArray();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int GetUsbbootState() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_GetUsbbootState, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public void SetUsbbootState(int flag) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(flag);
mRemote.transact(Stub.TRANSACTION_SetUsbbootState, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public int GetSigTransFormat() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_GetSigTransFormat, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int Get3DStatus() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_Get3DStatus, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public void SetStandbyKeyMode(boolean SetSSC) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((SetSSC)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_SetStandbyKeyMode, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void FacSetPanelType(int paneltype) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(paneltype);
mRemote.transact(Stub.TRANSACTION_FacSetPanelType, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public java.lang.String FacGetPanelType() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_FacGetPanelType, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int GetLvdsState() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_GetLvdsState, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public void SetLvdsState(int flag) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(flag);
mRemote.transact(Stub.TRANSACTION_SetLvdsState, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public int isVgaFmtInHdmi() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isVgaFmtInHdmi, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public boolean GetOnLineMusicFlag() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_GetOnLineMusicFlag, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public void SetViewMode(int mode) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(mode);
mRemote.transact(Stub.TRANSACTION_SetViewMode, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void UiSetScrMode(int mode) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(mode);
mRemote.transact(Stub.TRANSACTION_UiSetScrMode, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public boolean GetStartPicFlag() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_GetStartPicFlag, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public void SetOnLineMusicFlag(boolean isOn) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((isOn)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_SetOnLineMusicFlag, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void SetStartPicFlag(boolean isOn) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((isOn)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_SetStartPicFlag, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public boolean GetStandByModeFlag() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_GetStandByModeFlag, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public void SetStandByModeFlag(boolean isOn) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((isOn)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_SetStandByModeFlag, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void SaveSysStandby() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_SaveSysStandby, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public int GetSysStatus() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_GetSysStatus, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public int GetSystemAutoSuspending() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_GetSystemAutoSuspending, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public void SetSystemAutoSuspending(int num) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(num);
mRemote.transact(Stub.TRANSACTION_SetSystemAutoSuspending, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void FacSetHdmiEQMode(int value) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(value);
mRemote.transact(Stub.TRANSACTION_FacSetHdmiEQMode, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public int FacGetHdmiEQMode() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_FacGetHdmiEQMode, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public void FacSetHdmiInternalMode(int value) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(value);
mRemote.transact(Stub.TRANSACTION_FacSetHdmiInternalMode, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public int FacGetHdmiInternalMode() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_FacGetHdmiInternalMode, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public boolean isSrcSwtichDone() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isSrcSwtichDone, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public void FacSetSerialPortSwitchFlag(boolean flag) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((flag)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_FacSetSerialPortSwitchFlag, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public boolean FacGetSerialPortSwitchFlag() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_FacGetSerialPortSwitchFlag, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
public void ResumeLastBLValue() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_ResumeLastBLValue, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
public void setWhiteScreen() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_setWhiteScreen, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_UartSend = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_SaveParam = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_GetParam = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_GetTvParam = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_GetTvProgressBar = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_SaveCustomEQGain = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_GetCurEQGain = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_GetCurAudioSoundMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_SaveCurAudioSoundMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_SetCurAudioTrebleVolume = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_SetCurAudioBassVolume = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTION_GetCurAudioSupperBassSwitch = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
static final int TRANSACTION_GetCurAudioSRSSurround = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
static final int TRANSACTION_GetCurAudioVolume = (android.os.IBinder.FIRST_CALL_TRANSACTION + 13);
static final int TRANSACTION_SaveCurAudioVolume = (android.os.IBinder.FIRST_CALL_TRANSACTION + 14);
static final int TRANSACTION_num = (android.os.IBinder.FIRST_CALL_TRANSACTION + 15);
static final int TRANSACTION_setnum = (android.os.IBinder.FIRST_CALL_TRANSACTION + 16);
static final int TRANSACTION_addorreducenum = (android.os.IBinder.FIRST_CALL_TRANSACTION + 17);
static final int TRANSACTION_setSearchHandler = (android.os.IBinder.FIRST_CALL_TRANSACTION + 18);
static final int TRANSACTION_setSignalDetectHandler = (android.os.IBinder.FIRST_CALL_TRANSACTION + 19);
static final int TRANSACTION_setVGAMessageHandler = (android.os.IBinder.FIRST_CALL_TRANSACTION + 20);
static final int TRANSACTION_setDreampanelHandler = (android.os.IBinder.FIRST_CALL_TRANSACTION + 21);
static final int TRANSACTION_setDreampanelDemo = (android.os.IBinder.FIRST_CALL_TRANSACTION + 22);
static final int TRANSACTION_setTunerLastChannel = (android.os.IBinder.FIRST_CALL_TRANSACTION + 23);
static final int TRANSACTION_setTunerChannel = (android.os.IBinder.FIRST_CALL_TRANSACTION + 24);
static final int TRANSACTION_updateTunerFrequencyUI = (android.os.IBinder.FIRST_CALL_TRANSACTION + 25);
static final int TRANSACTION_getSearchStop = (android.os.IBinder.FIRST_CALL_TRANSACTION + 26);
static final int TRANSACTION_SearchChanel = (android.os.IBinder.FIRST_CALL_TRANSACTION + 27);
static final int TRANSACTION_getSearchDown = (android.os.IBinder.FIRST_CALL_TRANSACTION + 28);
static final int TRANSACTION_setSearchDown = (android.os.IBinder.FIRST_CALL_TRANSACTION + 29);
static final int TRANSACTION_FintTune = (android.os.IBinder.FIRST_CALL_TRANSACTION + 30);
static final int TRANSACTION_getAutoSearchOn = (android.os.IBinder.FIRST_CALL_TRANSACTION + 31);
static final int TRANSACTION_getMaxFreq = (android.os.IBinder.FIRST_CALL_TRANSACTION + 32);
static final int TRANSACTION_getMinFreq = (android.os.IBinder.FIRST_CALL_TRANSACTION + 33);
static final int TRANSACTION_getMaxVHFHFreq = (android.os.IBinder.FIRST_CALL_TRANSACTION + 34);
static final int TRANSACTION_getMaxVHFLFreq = (android.os.IBinder.FIRST_CALL_TRANSACTION + 35);
static final int TRANSACTION_manuFreq = (android.os.IBinder.FIRST_CALL_TRANSACTION + 36);
static final int TRANSACTION_exchangeChannelInfo = (android.os.IBinder.FIRST_CALL_TRANSACTION + 37);
static final int TRANSACTION_SaveTvCurrentChannel = (android.os.IBinder.FIRST_CALL_TRANSACTION + 38);
static final int TRANSACTION_getCurrnetChNumber = (android.os.IBinder.FIRST_CALL_TRANSACTION + 39);
static final int TRANSACTION_ChInfojump = (android.os.IBinder.FIRST_CALL_TRANSACTION + 40);
static final int TRANSACTION_ChInfoaft = (android.os.IBinder.FIRST_CALL_TRANSACTION + 41);
static final int TRANSACTION_ChInfovideostd = (android.os.IBinder.FIRST_CALL_TRANSACTION + 42);
static final int TRANSACTION_ChInfosoundstd = (android.os.IBinder.FIRST_CALL_TRANSACTION + 43);
static final int TRANSACTION_ismCurAudioVolumeInc = (android.os.IBinder.FIRST_CALL_TRANSACTION + 44);
static final int TRANSACTION_SetAudioMainVolume = (android.os.IBinder.FIRST_CALL_TRANSACTION + 45);
static final int TRANSACTION_GetCurSourceTV = (android.os.IBinder.FIRST_CALL_TRANSACTION + 46);
static final int TRANSACTION_GetCurSourceAV1 = (android.os.IBinder.FIRST_CALL_TRANSACTION + 47);
static final int TRANSACTION_GetCurSourceYPBPR1 = (android.os.IBinder.FIRST_CALL_TRANSACTION + 48);
static final int TRANSACTION_GetCurSourceHDMI1 = (android.os.IBinder.FIRST_CALL_TRANSACTION + 49);
static final int TRANSACTION_GetCurSourceHDMI2 = (android.os.IBinder.FIRST_CALL_TRANSACTION + 50);
static final int TRANSACTION_GetCurSourceHDMI3 = (android.os.IBinder.FIRST_CALL_TRANSACTION + 51);
static final int TRANSACTION_GetCurSourceVGA = (android.os.IBinder.FIRST_CALL_TRANSACTION + 52);
static final int TRANSACTION_changeTunerChannel = (android.os.IBinder.FIRST_CALL_TRANSACTION + 53);
static final int TRANSACTION_changeCurrentChannelToLastChannel = (android.os.IBinder.FIRST_CALL_TRANSACTION + 54);
static final int TRANSACTION_ismCustomVolumeMute = (android.os.IBinder.FIRST_CALL_TRANSACTION + 55);
static final int TRANSACTION_setmCustomVolumeMute = (android.os.IBinder.FIRST_CALL_TRANSACTION + 56);
static final int TRANSACTION_GetAmplifierMute = (android.os.IBinder.FIRST_CALL_TRANSACTION + 57);
static final int TRANSACTION_SetAudioSupperBassVolume = (android.os.IBinder.FIRST_CALL_TRANSACTION + 58);
static final int TRANSACTION_GetAvColorSys = (android.os.IBinder.FIRST_CALL_TRANSACTION + 59);
static final int TRANSACTION_GetSigFormatName = (android.os.IBinder.FIRST_CALL_TRANSACTION + 60);
static final int TRANSACTION_LoadNumberInputLimit = (android.os.IBinder.FIRST_CALL_TRANSACTION + 61);
static final int TRANSACTION_SaveNumberInputLimit = (android.os.IBinder.FIRST_CALL_TRANSACTION + 62);
static final int TRANSACTION_LoadNavigateFlag = (android.os.IBinder.FIRST_CALL_TRANSACTION + 63);
static final int TRANSACTION_SaveNavigateFlag = (android.os.IBinder.FIRST_CALL_TRANSACTION + 64);
static final int TRANSACTION_changeCurrentChannelToChannel = (android.os.IBinder.FIRST_CALL_TRANSACTION + 65);
static final int TRANSACTION_setViiColorDemo = (android.os.IBinder.FIRST_CALL_TRANSACTION + 66);
static final int TRANSACTION_setBaseColor = (android.os.IBinder.FIRST_CALL_TRANSACTION + 67);
static final int TRANSACTION_StartTv = (android.os.IBinder.FIRST_CALL_TRANSACTION + 68);
static final int TRANSACTION_CloseTv = (android.os.IBinder.FIRST_CALL_TRANSACTION + 69);
static final int TRANSACTION_LineInSelectChannel = (android.os.IBinder.FIRST_CALL_TRANSACTION + 70);
static final int TRANSACTION_IICBusOn = (android.os.IBinder.FIRST_CALL_TRANSACTION + 71);
static final int TRANSACTION_IICBusOff = (android.os.IBinder.FIRST_CALL_TRANSACTION + 72);
static final int TRANSACTION_Factory_WriteEepromOneByte = (android.os.IBinder.FIRST_CALL_TRANSACTION + 73);
static final int TRANSACTION_Factory_WriteEepromNByte = (android.os.IBinder.FIRST_CALL_TRANSACTION + 74);
static final int TRANSACTION_Factory_ReadEepromOneByte = (android.os.IBinder.FIRST_CALL_TRANSACTION + 75);
static final int TRANSACTION_Factory_ReadEepromNByte = (android.os.IBinder.FIRST_CALL_TRANSACTION + 76);
static final int TRANSACTION_FacSet_RGBogo = (android.os.IBinder.FIRST_CALL_TRANSACTION + 77);
static final int TRANSACTION_FacGet_RGBogo = (android.os.IBinder.FIRST_CALL_TRANSACTION + 78);
static final int TRANSACTION_FacSet_AdcAutoCal = (android.os.IBinder.FIRST_CALL_TRANSACTION + 79);
static final int TRANSACTION_FacGet_AdcAutoCalResult = (android.os.IBinder.FIRST_CALL_TRANSACTION + 80);
static final int TRANSACTION_FacGet_AdcAutoCalStatus = (android.os.IBinder.FIRST_CALL_TRANSACTION + 81);
static final int TRANSACTION_FacSet_BURN_FLAG = (android.os.IBinder.FIRST_CALL_TRANSACTION + 82);
static final int TRANSACTION_FacGet_BURN_FLAG = (android.os.IBinder.FIRST_CALL_TRANSACTION + 83);
static final int TRANSACTION_FacSet_ONEKEY_ONOFF = (android.os.IBinder.FIRST_CALL_TRANSACTION + 84);
static final int TRANSACTION_FacGet_ONEKEY_ONOFF = (android.os.IBinder.FIRST_CALL_TRANSACTION + 85);
static final int TRANSACTION_SetDefaultMacAddr = (android.os.IBinder.FIRST_CALL_TRANSACTION + 86);
static final int TRANSACTION_GetMacAddr = (android.os.IBinder.FIRST_CALL_TRANSACTION + 87);
static final int TRANSACTION_SaveMacAddr = (android.os.IBinder.FIRST_CALL_TRANSACTION + 88);
static final int TRANSACTION_GetAdbState = (android.os.IBinder.FIRST_CALL_TRANSACTION + 89);
static final int TRANSACTION_SetAdbState = (android.os.IBinder.FIRST_CALL_TRANSACTION + 90);
static final int TRANSACTION_LoadBarCode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 91);
static final int TRANSACTION_GetfactoryFlag = (android.os.IBinder.FIRST_CALL_TRANSACTION + 92);
static final int TRANSACTION_SetfactoryFlag = (android.os.IBinder.FIRST_CALL_TRANSACTION + 93);
static final int TRANSACTION_FacGet_DEF_HDCP_FLAG = (android.os.IBinder.FIRST_CALL_TRANSACTION + 94);
static final int TRANSACTION_FacSet_DEF_HDCP_FLAG = (android.os.IBinder.FIRST_CALL_TRANSACTION + 95);
static final int TRANSACTION_InitEepromData = (android.os.IBinder.FIRST_CALL_TRANSACTION + 96);
static final int TRANSACTION_factoryBurnMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 97);
static final int TRANSACTION_LoadEepromDate = (android.os.IBinder.FIRST_CALL_TRANSACTION + 98);
static final int TRANSACTION_UiSetAudioMutePanel = (android.os.IBinder.FIRST_CALL_TRANSACTION + 99);
static final int TRANSACTION_SetBurnDefault = (android.os.IBinder.FIRST_CALL_TRANSACTION + 100);
static final int TRANSACTION_SetFacOutDefault = (android.os.IBinder.FIRST_CALL_TRANSACTION + 101);
static final int TRANSACTION_SetSSC = (android.os.IBinder.FIRST_CALL_TRANSACTION + 102);
static final int TRANSACTION_GetSscDate = (android.os.IBinder.FIRST_CALL_TRANSACTION + 103);
static final int TRANSACTION_GetUsbbootState = (android.os.IBinder.FIRST_CALL_TRANSACTION + 104);
static final int TRANSACTION_SetUsbbootState = (android.os.IBinder.FIRST_CALL_TRANSACTION + 105);
static final int TRANSACTION_GetSigTransFormat = (android.os.IBinder.FIRST_CALL_TRANSACTION + 106);
static final int TRANSACTION_Get3DStatus = (android.os.IBinder.FIRST_CALL_TRANSACTION + 107);
static final int TRANSACTION_SetStandbyKeyMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 108);
static final int TRANSACTION_FacSetPanelType = (android.os.IBinder.FIRST_CALL_TRANSACTION + 109);
static final int TRANSACTION_FacGetPanelType = (android.os.IBinder.FIRST_CALL_TRANSACTION + 110);
static final int TRANSACTION_GetLvdsState = (android.os.IBinder.FIRST_CALL_TRANSACTION + 111);
static final int TRANSACTION_SetLvdsState = (android.os.IBinder.FIRST_CALL_TRANSACTION + 112);
static final int TRANSACTION_isVgaFmtInHdmi = (android.os.IBinder.FIRST_CALL_TRANSACTION + 113);
static final int TRANSACTION_GetOnLineMusicFlag = (android.os.IBinder.FIRST_CALL_TRANSACTION + 114);
static final int TRANSACTION_SetViewMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 115);
static final int TRANSACTION_UiSetScrMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 116);
static final int TRANSACTION_GetStartPicFlag = (android.os.IBinder.FIRST_CALL_TRANSACTION + 117);
static final int TRANSACTION_SetOnLineMusicFlag = (android.os.IBinder.FIRST_CALL_TRANSACTION + 118);
static final int TRANSACTION_SetStartPicFlag = (android.os.IBinder.FIRST_CALL_TRANSACTION + 119);
static final int TRANSACTION_GetStandByModeFlag = (android.os.IBinder.FIRST_CALL_TRANSACTION + 120);
static final int TRANSACTION_SetStandByModeFlag = (android.os.IBinder.FIRST_CALL_TRANSACTION + 121);
static final int TRANSACTION_SaveSysStandby = (android.os.IBinder.FIRST_CALL_TRANSACTION + 122);
static final int TRANSACTION_GetSysStatus = (android.os.IBinder.FIRST_CALL_TRANSACTION + 123);
static final int TRANSACTION_GetSystemAutoSuspending = (android.os.IBinder.FIRST_CALL_TRANSACTION + 124);
static final int TRANSACTION_SetSystemAutoSuspending = (android.os.IBinder.FIRST_CALL_TRANSACTION + 125);
static final int TRANSACTION_FacSetHdmiEQMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 126);
static final int TRANSACTION_FacGetHdmiEQMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 127);
static final int TRANSACTION_FacSetHdmiInternalMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 128);
static final int TRANSACTION_FacGetHdmiInternalMode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 129);
static final int TRANSACTION_isSrcSwtichDone = (android.os.IBinder.FIRST_CALL_TRANSACTION + 130);
static final int TRANSACTION_FacSetSerialPortSwitchFlag = (android.os.IBinder.FIRST_CALL_TRANSACTION + 131);
static final int TRANSACTION_FacGetSerialPortSwitchFlag = (android.os.IBinder.FIRST_CALL_TRANSACTION + 132);
static final int TRANSACTION_ResumeLastBLValue = (android.os.IBinder.FIRST_CALL_TRANSACTION + 133);
static final int TRANSACTION_setWhiteScreen = (android.os.IBinder.FIRST_CALL_TRANSACTION + 134);
}
public void UartSend(java.lang.String name, java.lang.String value) throws android.os.RemoteException;
public void SaveParam(java.lang.String name, java.lang.String value) throws android.os.RemoteException;
public java.lang.String GetParam(java.lang.String name) throws android.os.RemoteException;
public int GetTvParam(java.lang.String MenuItemName) throws android.os.RemoteException;
public int GetTvProgressBar(java.lang.String MenuItemName) throws android.os.RemoteException;
public void SaveCustomEQGain(int item, int val) throws android.os.RemoteException;
public void GetCurEQGain(int[] dataBuf) throws android.os.RemoteException;
public int GetCurAudioSoundMode() throws android.os.RemoteException;
public void SaveCurAudioSoundMode(int soundmode) throws android.os.RemoteException;
public void SetCurAudioTrebleVolume(int cur_vol) throws android.os.RemoteException;
public void SetCurAudioBassVolume(int cur_vol) throws android.os.RemoteException;
public int GetCurAudioSupperBassSwitch() throws android.os.RemoteException;
public int GetCurAudioSRSSurround() throws android.os.RemoteException;
public int GetCurAudioVolume() throws android.os.RemoteException;
public void SaveCurAudioVolume(int progress) throws android.os.RemoteException;
public int num() throws android.os.RemoteException;
public void setnum(int num) throws android.os.RemoteException;
public void addorreducenum(int flag) throws android.os.RemoteException;
public void setSearchHandler() throws android.os.RemoteException;
public void setSignalDetectHandler() throws android.os.RemoteException;
public void setVGAMessageHandler() throws android.os.RemoteException;
public void setDreampanelHandler() throws android.os.RemoteException;
public void setDreampanelDemo(boolean flag) throws android.os.RemoteException;
public void setTunerLastChannel() throws android.os.RemoteException;
public void setTunerChannel(int num) throws android.os.RemoteException;
public void updateTunerFrequencyUI() throws android.os.RemoteException;
public boolean getSearchStop() throws android.os.RemoteException;
public void SearchChanel(boolean f, boolean s, boolean t) throws android.os.RemoteException;
public boolean getSearchDown() throws android.os.RemoteException;
public void setSearchDown(boolean flag) throws android.os.RemoteException;
public void FintTune(int val) throws android.os.RemoteException;
public boolean getAutoSearchOn() throws android.os.RemoteException;
public int getMaxFreq() throws android.os.RemoteException;
public int getMinFreq() throws android.os.RemoteException;
public int getMaxVHFHFreq() throws android.os.RemoteException;
public int getMaxVHFLFreq() throws android.os.RemoteException;
public int manuFreq() throws android.os.RemoteException;
public void exchangeChannelInfo(int sourceCh, int targetCh) throws android.os.RemoteException;
public void SaveTvCurrentChannel() throws android.os.RemoteException;
public int getCurrnetChNumber() throws android.os.RemoteException;
public boolean ChInfojump() throws android.os.RemoteException;
public boolean ChInfoaft() throws android.os.RemoteException;
public int ChInfovideostd() throws android.os.RemoteException;
public int ChInfosoundstd() throws android.os.RemoteException;
public boolean ismCurAudioVolumeInc() throws android.os.RemoteException;
public void SetAudioMainVolume() throws android.os.RemoteException;
public boolean GetCurSourceTV() throws android.os.RemoteException;
public boolean GetCurSourceAV1() throws android.os.RemoteException;
public boolean GetCurSourceYPBPR1() throws android.os.RemoteException;
public boolean GetCurSourceHDMI1() throws android.os.RemoteException;
public boolean GetCurSourceHDMI2() throws android.os.RemoteException;
public boolean GetCurSourceHDMI3() throws android.os.RemoteException;
public boolean GetCurSourceVGA() throws android.os.RemoteException;
public int changeTunerChannel(boolean flag) throws android.os.RemoteException;
public void changeCurrentChannelToLastChannel() throws android.os.RemoteException;
public boolean ismCustomVolumeMute() throws android.os.RemoteException;
public void setmCustomVolumeMute(boolean flag) throws android.os.RemoteException;
public int GetAmplifierMute() throws android.os.RemoteException;
public void SetAudioSupperBassVolume() throws android.os.RemoteException;
public int GetAvColorSys() throws android.os.RemoteException;
public java.lang.String GetSigFormatName() throws android.os.RemoteException;
public int LoadNumberInputLimit() throws android.os.RemoteException;
public void SaveNumberInputLimit(int flag) throws android.os.RemoteException;
public int LoadNavigateFlag() throws android.os.RemoteException;
public void SaveNavigateFlag(int flag) throws android.os.RemoteException;
public void changeCurrentChannelToChannel(int flag) throws android.os.RemoteException;
public void setViiColorDemo(int demoMode) throws android.os.RemoteException;
public void setBaseColor(int cmMode) throws android.os.RemoteException;
public void StartTv() throws android.os.RemoteException;
public boolean CloseTv() throws android.os.RemoteException;
public int LineInSelectChannel(int num) throws android.os.RemoteException;
public void IICBusOn() throws android.os.RemoteException;
public void IICBusOff() throws android.os.RemoteException;
public void Factory_WriteEepromOneByte(int offset, int value) throws android.os.RemoteException;
public void Factory_WriteEepromNByte(int offset, int len, int[] buf) throws android.os.RemoteException;
public int Factory_ReadEepromOneByte(int offset) throws android.os.RemoteException;
public int[] Factory_ReadEepromNByte(int offset, int len) throws android.os.RemoteException;
public void FacSet_RGBogo(java.lang.String channelSelect, java.lang.String ganioffsetSel, int value) throws android.os.RemoteException;
public int FacGet_RGBogo(java.lang.String channelSelect, java.lang.String ganioffsetSel) throws android.os.RemoteException;
public int FacSet_AdcAutoCal() throws android.os.RemoteException;
public int FacGet_AdcAutoCalResult() throws android.os.RemoteException;
public int FacGet_AdcAutoCalStatus() throws android.os.RemoteException;
public void FacSet_BURN_FLAG(boolean turnOn) throws android.os.RemoteException;
public boolean FacGet_BURN_FLAG() throws android.os.RemoteException;
public void FacSet_ONEKEY_ONOFF(int value) throws android.os.RemoteException;
public int FacGet_ONEKEY_ONOFF() throws android.os.RemoteException;
public void SetDefaultMacAddr() throws android.os.RemoteException;
public int[] GetMacAddr() throws android.os.RemoteException;
public void SaveMacAddr() throws android.os.RemoteException;
public int GetAdbState() throws android.os.RemoteException;
public void SetAdbState(int flag) throws android.os.RemoteException;
public java.lang.String LoadBarCode() throws android.os.RemoteException;
public int GetfactoryFlag() throws android.os.RemoteException;
public void SetfactoryFlag(int flag) throws android.os.RemoteException;
public boolean FacGet_DEF_HDCP_FLAG() throws android.os.RemoteException;
public void FacSet_DEF_HDCP_FLAG(boolean loadDef) throws android.os.RemoteException;
public int InitEepromData() throws android.os.RemoteException;
public void factoryBurnMode(boolean flag) throws android.os.RemoteException;
public java.lang.String LoadEepromDate() throws android.os.RemoteException;
public void UiSetAudioMutePanel(int value) throws android.os.RemoteException;
public void SetBurnDefault() throws android.os.RemoteException;
public void SetFacOutDefault() throws android.os.RemoteException;
public void SetSSC(int range, int lowrange, int cycle, boolean onoff) throws android.os.RemoteException;
public int[] GetSscDate() throws android.os.RemoteException;
public int GetUsbbootState() throws android.os.RemoteException;
public void SetUsbbootState(int flag) throws android.os.RemoteException;
public int GetSigTransFormat() throws android.os.RemoteException;
public int Get3DStatus() throws android.os.RemoteException;
public void SetStandbyKeyMode(boolean SetSSC) throws android.os.RemoteException;
public void FacSetPanelType(int paneltype) throws android.os.RemoteException;
public java.lang.String FacGetPanelType() throws android.os.RemoteException;
public int GetLvdsState() throws android.os.RemoteException;
public void SetLvdsState(int flag) throws android.os.RemoteException;
public int isVgaFmtInHdmi() throws android.os.RemoteException;
public boolean GetOnLineMusicFlag() throws android.os.RemoteException;
public void SetViewMode(int mode) throws android.os.RemoteException;
public void UiSetScrMode(int mode) throws android.os.RemoteException;
public boolean GetStartPicFlag() throws android.os.RemoteException;
public void SetOnLineMusicFlag(boolean isOn) throws android.os.RemoteException;
public void SetStartPicFlag(boolean isOn) throws android.os.RemoteException;
public boolean GetStandByModeFlag() throws android.os.RemoteException;
public void SetStandByModeFlag(boolean isOn) throws android.os.RemoteException;
public void SaveSysStandby() throws android.os.RemoteException;
public int GetSysStatus() throws android.os.RemoteException;
public int GetSystemAutoSuspending() throws android.os.RemoteException;
public void SetSystemAutoSuspending(int num) throws android.os.RemoteException;
public void FacSetHdmiEQMode(int value) throws android.os.RemoteException;
public int FacGetHdmiEQMode() throws android.os.RemoteException;
public void FacSetHdmiInternalMode(int value) throws android.os.RemoteException;
public int FacGetHdmiInternalMode() throws android.os.RemoteException;
public boolean isSrcSwtichDone() throws android.os.RemoteException;
public void FacSetSerialPortSwitchFlag(boolean flag) throws android.os.RemoteException;
public boolean FacGetSerialPortSwitchFlag() throws android.os.RemoteException;
public void ResumeLastBLValue() throws android.os.RemoteException;
public void setWhiteScreen() throws android.os.RemoteException;
}
