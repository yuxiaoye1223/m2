package com.amlogic.android.vdinapi;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import com.amlogic.android.afeapi.afeJNI;
import com.amlogic.android.audioctlapi.*;
import com.amlogic.android.tunerapi.tunerJNI;
import com.amlogic.android.vppapi.vppJNI;
import com.amlogic.android.vppapi.vppJNI.ScreenMode;
import com.amlogic.android.osdapi.osdJNI;
import com.amlogic.android.dreampanel.dreampanelJNI;
import com.amlogic.android.eepromapi.eepromJNI;
import com.amlogic.android.eepromapi.eepromJNI.*;
import com.amlogic.tvjni.GlobalConst;
import com.amlogic.tvjni.tvservice;

class SignalDetectThr implements Runnable {
    String DBTAG = "TV JAVA VDIN";
    private static AtomicInteger preTrans_Fmt = new AtomicInteger(0);
    private static AtomicInteger preFmt = new AtomicInteger(0);
    private static AtomicInteger preStatus = new AtomicInteger(0);
    private static int StableCount = 1;

    SignalDetectThr() {
        this.preTrans_Fmt.set(vdinJNI.SigTransFmt.TFMT_2D.ordinal());
        this.preStatus.set(vdinJNI.SigStatus.UN_STABLE.ordinal());
        this.preFmt.set(vdinJNI.SigFormat.NULL.ordinal());
        vdinJNI.gParam.status = vdinJNI.SigStatus.UN_STABLE.ordinal();
        vdinJNI.gParam.fmt = vdinJNI.SigFormat.NULL.ordinal();
        vdinJNI.gParam.trans_fmt = vdinJNI.SigTransFmt.TFMT_2D.ordinal();
    }

    private boolean IsFmtChange() {
        if (preFmt.get() != vdinJNI.gParam.fmt) {
            Log.d(DBTAG, "FORMAT Change: " + preFmt.get() + "-->"
                + vdinJNI.gParam.fmt);
            return true;
        } else
            return false;
    }

    private boolean IsSigChange() {
        if (preStatus.get() != vdinJNI.gParam.status) {
            if (preStatus.get() == vdinJNI.SigStatus.STABLE.ordinal()) {
                vdinJNI.TurnOnBlueScreen(2);
                Log.d(DBTAG, "If pre status is stable, enable blackscreen in IsSignalChange!");
                AudioCustom.AudioAmplifierMuteOn();
                AudioCustom.SetAudioLineOutMute(true);
                Log.d(DBTAG, "STATUS Change: MUTE AUDIO in IsSignalChange");
            }
            if (vdinJNI.CheckStableCount != 0) {
                vdinJNI.CheckStableCount = 0;
                Log.d(DBTAG, "Clear CheckStableCount in IsSigChange");
            }
            if (vdinJNI.SourceSwitchDoneCount != 0) {
                vdinJNI.SourceSwitchDoneCount = 0;
                Log.d(DBTAG, "Clear SourceSwitchDoneCount in IsSigChange");
            }
            Log.d(DBTAG, "STATUS Change: " + preStatus.get() + "-->"
                + vdinJNI.gParam.status);
            return true;
        } else
            return false;
    }

    private boolean IsSigTrans2D_3DFmtChange() {
        if (preTrans_Fmt.get() != vdinJNI.gParam.trans_fmt) {
            if ((preTrans_Fmt.get() == vdinJNI.SigTransFmt.TFMT_2D.ordinal())
                || (vdinJNI.gParam.trans_fmt == vdinJNI.SigTransFmt.TFMT_2D.ordinal())) {
                Log.d(DBTAG, "Trans_Fmt 2D_3D Change: " + preTrans_Fmt.get()
                    + "-->" + vdinJNI.gParam.trans_fmt);
                return true;
            } else {
                return false;
            }
        } else
            return false;
    }

    private boolean IsSigTrans3DFmtChange() {
        if (preTrans_Fmt.get() != vdinJNI.gParam.trans_fmt) {
            if ((preTrans_Fmt.get() == vdinJNI.SigTransFmt.TFMT_2D.ordinal())
                || (vdinJNI.gParam.trans_fmt == vdinJNI.SigTransFmt.TFMT_2D.ordinal())) {
                Log.d(DBTAG, "Trans_3D Fmt Change: " + preTrans_Fmt.get()
                    + "-->" + vdinJNI.gParam.trans_fmt);
                return false;
            } else {
                return true;
            }
        } else
            return false;
    }

    private boolean IsSigStableToUnStable() {
        if (preStatus.get() == vdinJNI.SigStatus.STABLE.ordinal()
            && vdinJNI.gParam.status == vdinJNI.SigStatus.UN_STABLE.ordinal()) {
            Log.d(DBTAG, "STATUS change: STABLE->UNSTABLE");
            return true;
        } else
            return false;
    }

    private boolean IsSigStableToUnSupport() {
        if (preStatus.get() == vdinJNI.SigStatus.STABLE.ordinal()
            && vdinJNI.gParam.status == vdinJNI.SigStatus.NOT_SUPPORT.ordinal()) {
            Log.d(DBTAG, "STATUS change: STABLE->NOT_SUPPORT");
            return true;
        } else
            return false;
    }

    private boolean IsSigStableToNull() {
        if (preStatus.get() == vdinJNI.SigStatus.STABLE.ordinal()) {
            if (vdinJNI.gParam.status == vdinJNI.SigStatus.NULL.ordinal()) {
                Log.d(DBTAG, "STATUS change: STABLE->NULL");
                return true;
            } else if (vdinJNI.gParam.status == vdinJNI.SigStatus.NO_SIGNAL.ordinal()) {
                Log.d(DBTAG, "STATUS change: STABLE->NO_SIGNAL");
                vdinJNI.channelchange = false;
                return true;
            } else
                return false;
        } else
            return false;
    }

    private boolean IsSigUnStableToUnSupport() {
        if (preStatus.get() == vdinJNI.SigStatus.UN_STABLE.ordinal()
            && vdinJNI.gParam.status == vdinJNI.SigStatus.NOT_SUPPORT.ordinal()) {
            Log.d(DBTAG, "STATUS change: UNSTABLE->NOT_SUPPORT");
            return true;
        } else
            return false;
    }

    private boolean IsSigUnStableToNull() {
        if (preStatus.get() == vdinJNI.SigStatus.UN_STABLE.ordinal()) {
            if (vdinJNI.gParam.status == vdinJNI.SigStatus.NULL.ordinal()) {
                Log.d(DBTAG, "STATUS change: UNSTABLE->NULL");
                return true;
            } else if (vdinJNI.gParam.status == vdinJNI.SigStatus.NO_SIGNAL.ordinal()) {
                Log.d(DBTAG, "STATUS change: UNSTABLE->NO_SIGNAL");
                vdinJNI.channelchange = false;
                return true;
            } else
                return false;
        } else
            return false;
    }

    private boolean IsNullToNoSig() {
        if (preStatus.get() == vdinJNI.SigStatus.NULL.ordinal()) {
            if (vdinJNI.gParam.status == vdinJNI.SigStatus.NO_SIGNAL.ordinal()) {
                Log.d(DBTAG, "STATUS change: NULL -> NO_SIGNAL");
                vdinJNI.channelchange = false;
                return true;
            } else {
                Log.d(DBTAG, "STATUS change: NULL -> !NO_SIGNAL");
                return false;
            }
        } else
            return false;
    }

    private boolean IsSigStable() {
        if (vdinJNI.gParam.status == vdinJNI.SigStatus.STABLE.ordinal()) {
            return true;
        } else {
            return false;
        }
    }

    private boolean IsNoSignal() {
        if ((vdinJNI.gParam.status == vdinJNI.SigStatus.NO_SIGNAL.ordinal())
            || (vdinJNI.gParam.status == vdinJNI.SigStatus.NULL.ordinal())) {
            return true;
        } else {
            return false;
        }
    }

    private boolean IsSignalUnSupport() {
        if (vdinJNI.gParam.status == vdinJNI.SigStatus.NOT_SUPPORT.ordinal()) {
            return true;
        } else {
            return false;
        }
    }

    public void run() {
        while (vdinJNI.gParam.isTurnOnSigDetectThr == true) {
            if (vdinJNI.gParam.isStartSigDetectThr == true) {
                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                    preTrans_Fmt.set(vdinJNI.gParam.trans_fmt);
                    preFmt.set(vdinJNI.gParam.fmt);
                    preStatus.set(vdinJNI.gParam.status);
                    vdinJNI.GetSignalInfo(0, vdinJNI.gParam);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Log.e(DBTAG, "signal detect delay error!!!");
                } finally {
                    if (IsSigChange() == true) {
                        if (IsSigStable() == true) {
                            vdinJNI.gParam.isSigDetectExecDone = false;
                            if (vdinJNI.GetCurrentSrcInput() != vdinJNI.SrcId.CVBS0.toInt()) {
                                if (vdinJNI.is50HzFrameRateFmt() == true) {
                                    vdinJNI.SetDisplayVFreq(50);
                                    Log.d(DBTAG, "SetDisplayVFreq 50HZ");
                                } else {
                                    vdinJNI.SetDisplayVFreq(60);
                                    Log.d(DBTAG, "SetDisplayVFreq 60HZ");
                                }
                                vppJNI.ResumeLastBLValue();
                            }
                            if (vdinJNI.Get3DStatus() == vdinJNI.Mode3D.AUTO.ordinal()) {
                                vdinJNI.RemoveTvinPath(1);
                                if (vdinJNI.Get3DMode() != vdinJNI.Mode3D.AUTO.ordinal())
                                    vdinJNI.SetCurrent3DMode(vdinJNI.Mode3D.AUTO.ordinal());
                                if (vdinJNI.gParam.trans_fmt == vdinJNI.SigTransFmt.TFMT_2D.ordinal()) {
                                    vdinJNI.Set2Dto3D(0);
                                    vdinJNI.AddTvinPath(vdinJNI.TvPath.VDIN_DEINTERLACE_AMVIDEO.ordinal());
                                    vdinJNI.SetDisplayModeFor3D(0);
                                    Log.d(DBTAG, "Add 2D Path in 2D-AUTO");
                                } else {
                                    vdinJNI.Set2Dto3D(1);
                                    vdinJNI.AddTvinPath(vdinJNI.TvPath.VDIN_3D_AMVIDEO.ordinal());
                                    vdinJNI.SetDisplayModeFor3D(1);
                                    vdinJNI.Send3DCommand(vdinJNI.Mode3D.AUTO.ordinal());
                                    Log.d(DBTAG, "Add 3D Path in 3D-AUTO");
                                }
                            } else {
                                Log.d(DBTAG, "It's not 3D auto mode in signal change stable");
                            }
                            if (0 == vdinJNI.StartDecoder(0, true)) {
                                StableCount = 25;
                                Log.d(DBTAG, "Signal change to stable, start decoder and count down!");
                            } else {
                                vdinJNI.gParam.isSigDetectExecDone = true;
                                Log.w(DBTAG, "StartDecoder faild in signal change stable!");
                                continue;
                            }
                        } else {
                            if (IsSigStableToUnStable() == true) {
                                vdinJNI.TurnOnBlueScreen(2);
                                vdinJNI.StopDecoder(0);
                                Log.d(DBTAG, "Enable blackscreen for signal change in StableToUnStable!");
                            } else if (IsSigStableToUnSupport() == true) {
                                vdinJNI.TurnOnBlueScreen(1);
                                if (vdinJNI.isSrcSwtichDone()) {
                                    vdinJNI.StopDecoder(0);
                                    Log.d(DBTAG, "Enable bluescreen for signal change in StableToUnSupport!");
                                } else {
                                    vdinJNI.SourceSwitchDoneCount = 30;
                                    Log.d(DBTAG, "Enable bluescreen for signal change in source switch StableToUnSupport, source switch count down.");
                                }
                            } else if (IsSigUnStableToUnSupport() == true) {
                                vdinJNI.TurnOnBlueScreen(1);
                                if (vdinJNI.isSrcSwtichDone()) {
                                    vdinJNI.StopDecoder(0);
                                    Log.d(DBTAG, "Enable bluescreen for signal change in UnStableToUnSupport!");
                                } else {
                                    vdinJNI.SourceSwitchDoneCount = 30;
                                    Log.d(DBTAG, "Enable bluescreen for signal change in source switch UnStableToUnSupport, source switch count down.");
                                }
                            } else if (IsSigStableToNull() == true) {
                                vdinJNI.TurnOnBlueScreen(1);
                                if (vdinJNI.isSrcSwtichDone()) {
                                    vdinJNI.StopDecoder(0);
                                    Log.d(DBTAG, "Enable bluescreen for signal change in StableToNull!");
                                } else {
                                    vdinJNI.SourceSwitchDoneCount = 30;
                                    Log.d(DBTAG, "Enable blackscreen for signal change in source switch StableToNull, source switch count down.");
                                }
                            } else if (IsSigUnStableToNull() == true) {
                                vdinJNI.TurnOnBlueScreen(1);
                                if (vdinJNI.isSrcSwtichDone()) {
                                    Log.d(DBTAG, "Enable bluescreen for signal change in UnStableToNull!");
                                } else {
                                    vdinJNI.SourceSwitchDoneCount = 30;
                                    Log.d(DBTAG, "Enable blackscreen for signal change in source switch UnStableToNull, source switch count down.");
                                }
                            } else if (IsNullToNoSig() == true) {
                                vdinJNI.TurnOnBlueScreen(1);
                                if (vdinJNI.isSrcSwtichDone()) {
                                    Log.d(DBTAG, "Enable bluescreen for signal change in NullToNoSignal!");
                                } else {
                                    vdinJNI.SourceSwitchDoneCount = 30;
                                    Log.d(DBTAG, "Enable bluescreen for signal change in source switch NullToNoSignal, source switch count down.");
                                }
                            }
                        }
                    } else {
                        if (IsSigStable() == true) {
                            if (IsFmtChange() == true) {
                                vdinJNI.gParam.isSigDetectExecDone = false;
                                vdinJNI.TurnOnBlueScreen(2);
                                vdinJNI.StopDecoder(0);
                                vdinJNI.CheckStableCount = 50;
                            } else if (IsSigTrans2D_3DFmtChange()) {
                                vdinJNI.gParam.isSigDetectExecDone = false;
                                vdinJNI.TurnOnBlueScreen(2);
                                vdinJNI.StopDecoder(0);
                            } else {
                                if (StableCount > 0) {
                                    if (--StableCount == 0) {
                                        vdinJNI.TurnOnBlueScreen(0);
                                        // adjust dsp read and write pointer
                                        AudioCtlJNI.ResetDSPRWPtr(0);
                                        if (tunerJNI.searchflag == true) {
                                            AudioCustom.AudioAmplifierMuteOn();
                                            AudioCustom.SetAudioLineOutMute(true);
                                        } else {
                                            AudioCustom.AudioAmplifierMuteOff();
                                            AudioCustom.SetAudioLineOutMute(false);
                                            vdinJNI.sendMessageToUI(GlobalConst.MSG_VDINJNI_DISPLAY_CHANNEL_INFO);
                                        }
                                        vdinJNI.SourceSwitchDoneCount = 30;
                                        Log.d(DBTAG, "Enable video for signal stable, audio mute off, source switch count down.");
                                    } else {
                                        Log.d(DBTAG, "In signal stable, StableCount : "
                                            + StableCount);
                                    }
                                } else if (vdinJNI.CheckStableCount > 0) {
                                    if (--vdinJNI.CheckStableCount == 0) {
                                        vdinJNI.gParam.status = vdinJNI.SigStatus.UN_STABLE.ordinal();
                                        vdinJNI.gParam.isSigDetectExecDone = true;
                                        Log.d(DBTAG, "Set status to UN_STABLE for fmt change or tv channel swich!");
                                        continue;
                                    }
                                } else if (vdinJNI.SourceSwitchDoneCount > 0) {
                                    Log.d(DBTAG, "In signal stable, vdinJNI.SourceSwitchDoneCount : "
                                        + vdinJNI.SourceSwitchDoneCount);
                                    if (--vdinJNI.SourceSwitchDoneCount == 0) {
                                        vdinJNI.gParam.isSrcSwitchExecDone = true;
                                        Log.d(DBTAG, "Signal stable, source switch done.");
                                    }
                                }
                            }
                        } else if (IsNoSignal() == true) {
                            if (vdinJNI.SourceSwitchDoneCount > 0) {
                                if (--vdinJNI.SourceSwitchDoneCount == 0) {
                                    vdinJNI.gParam.isSrcSwitchExecDone = true;
                                    Log.d(DBTAG, "No signal, source switch done.");
                                }
                                Log.d(DBTAG, "In no signal, vdinJNI.SourceSwitchDoneCount : "
                                    + vdinJNI.SourceSwitchDoneCount);
                            }
                        } else if (IsSignalUnSupport() == true) {
                            if (vdinJNI.SourceSwitchDoneCount > 0) {
                                if (--vdinJNI.SourceSwitchDoneCount == 0) {
                                    vdinJNI.gParam.isSrcSwitchExecDone = true;
                                    Log.d(DBTAG, "Unsupport signal, source switch done.");
                                } else {
                                    Log.d(DBTAG, "In unsupport signal, vdinJNI.SourceSwitchDoneCount : "
                                        + vdinJNI.SourceSwitchDoneCount);
                                }
                            }
                        }
                    }
                    switch (vdinJNI.gParam.status) {
                        case 1:
                            vdinJNI.sendMessageToUI(GlobalConst.MSG_SIGNAL_VGA_NO_SIGNAL);
                            if (!tunerJNI.searchflag)
                                vdinJNI.sendMessageDelayToUI(GlobalConst.MSG_NO_SIGNAL_DETECT, GlobalConst.IS_NO_SIGNAL, 2000);
                            break;
                        case 3:
                            vdinJNI.sendMessageToUI(GlobalConst.MSG_SIGNAL_NOT_SUPPORT);
                            break;
                        case 0:
                        case 2:
                        case 4:
                            if (vdinJNI.menuHandler != null) {
                                if (vdinJNI.menuHandler.hasMessages(GlobalConst.IS_NO_SIGNAL))
                                    vdinJNI.menuHandler.removeMessages(GlobalConst.IS_NO_SIGNAL);
                            }
                            vdinJNI.sendMessageToUI(GlobalConst.MSG_SIGNAL_STABLE);
                            break;
                        default:
                            break;
                    }
                }
                vdinJNI.gParam.isSigDetectExecDone = true;
            } else {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
}

/**********************************************************************
 * important class(structure) of Tvin
 **********************************************************************/
class TvinParam {
    public int inputWindow = 0;
    public int curSource = eepromJNI.SourceInput.TV.ordinal();
    public int avColorSys = GlobalConst.CVBS_COLORSYS_AUTO;
    public int port = vdinJNI.SrcId.NULL.toInt();
    public int trans_fmt = vdinJNI.SigTransFmt.TFMT_2D.ordinal();
    public int fmt = vdinJNI.SigFormat.NULL.ordinal();
    public int status = vdinJNI.SigStatus.NULL.ordinal();
    public int fmtRatio = vdinJNI.FmtRatio.RT169.ordinal();
    public int colorFmt = vdinJNI.ColorFmt.YUV422.ordinal();
    /** for normal overscan **/
    public int cutHs = 0;
    public int cutHe = 0;
    public int cutVs = 0;
    public int cutVe = 0;
    /** for L/R or B/T 3d mode overscan **/
    public int cutTop = 0;
    public int cutLeft = 0;
    public long flag = -1;
    public long reserved = -1;
    public int mode3D = vdinJNI.Mode3D.DISABLE.ordinal();
    public int status3D = vdinJNI.Mode3D.DISABLE.ordinal();
    public int TvinPathId = vdinJNI.TvPath.VDIN_DEINTERLACE_AMVIDEO.ordinal();
    public boolean isDecoStart = false;
    public boolean isTurnOnSigDetectThr = false;
    public boolean isStartSigDetectThr = false;
    public boolean isSigDetectExecDone = true;
    public boolean isSrcSwitchExecDone = true;
    public boolean isTurnOnOverScan = true;

    public TvinParam() {

    }

    public int getSrcId() {
        return this.port;
    }

    public int getFmtRatio() {
        if (this.port >= vdinJNI.SrcId.VGA0.toInt()
            && this.port <= vdinJNI.SrcId.VGA7.toInt()) {
            this.fmtRatio = vdinJNI.FmtRatio.RT169.ordinal();
        } else if (this.port >= vdinJNI.SrcId.COMP0.toInt()
            && this.port <= vdinJNI.SrcId.COMP7.toInt()) {
            if ((this.fmt >= vdinJNI.SigFormat.COMP_480P_60D000.ordinal())
                && (this.fmt <= vdinJNI.SigFormat.COMP_576I_50D000.ordinal())) {
                this.fmtRatio = vdinJNI.FmtRatio.RT43.ordinal();
                Log.d("TV JAVA VDIN", "component format ratio(4:3)");
            } else {
                this.fmtRatio = vdinJNI.FmtRatio.RT169.ordinal();
                Log.d("TV JAVA VDIN", "component format ratio(16:9)");
            }
        } else if (this.port >= vdinJNI.SrcId.CVBS0.toInt()
            && this.port <= vdinJNI.SrcId.SVIDEO7.toInt()) {
            this.fmtRatio = vdinJNI.FmtRatio.RT43.ordinal();
            Log.d("TV JAVA VDIN", "cvbs format ratio(4:3)");
        } else {
            this.fmtRatio = vdinJNI.FmtRatio.RT169.ordinal();
        }

        if (getSrcType() == vdinJNI.SrcType.MPEG) {
            this.fmtRatio = vdinJNI.FmtRatio.RT169.ordinal();
            Log.d("TV JAVA VDIN", "MPEG set 16:9!");
        }

        return fmtRatio;
    }

    public vdinJNI.SrcType getSrcType() {
        if (vdinJNI.gParam.port >= vdinJNI.SrcId.CVBS0.toInt()
            && vdinJNI.gParam.port <= vdinJNI.SrcId.SVIDEO7.toInt()) {
            return vdinJNI.SrcType.AV;
        } else if (vdinJNI.gParam.port >= vdinJNI.SrcId.COMP0.toInt()
            && vdinJNI.gParam.port <= vdinJNI.SrcId.COMP7.toInt()) {
            return vdinJNI.SrcType.COMPONENT;
        } else if (vdinJNI.gParam.port >= vdinJNI.SrcId.VGA0.toInt()
            && vdinJNI.gParam.port <= vdinJNI.SrcId.VGA7.toInt()) {
            return vdinJNI.SrcType.VGA;
        } else if (vdinJNI.gParam.port >= vdinJNI.SrcId.HDMI0.toInt()
            && vdinJNI.gParam.port <= vdinJNI.SrcId.HDMI7.toInt()) {
            return vdinJNI.SrcType.HDMI;
        }
        return vdinJNI.SrcType.MPEG;
    }
}

// xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
//
//
//
// current usage: only one window
// xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
public class vdinJNI {
    private static final String DBTAG = "TV JAVA VDIN";
    public static boolean SrcSwitchUsingThread = true;
    public static Handler menuHandler = null;
    public static int CheckStableCount = 0;
    public static int SourceSwitchDoneCount = 0;

    public enum SrcId {
        NULL(0x0000),
        MPEG0(0x0100),
        BT656(0x0200),
        BT601(0x0201),
        VGA0(0x0400),
        VGA1(0x0401),
        VGA2(0x0402),
        VGA3(0x0403),
        VGA4(0x0404),
        VGA5(0x0405),
        VGA6(0x0406),
        VGA7(0x0407),
        COMP0(0x0800),
        COMP1(0x0801),
        COMP2(0x0802),
        COMP3(0x0803),
        COMP4(0x0804),
        COMP5(0x0805),
        COMP6(0x0806),
        COMP7(0x0807),
        CVBS0(0x1000),
        CVBS1(0x1001),
        CVBS2(0x1002),
        CVBS3(0x1003),
        CVBS4(0x1004),
        CVBS5(0x1005),
        CVBS6(0x1006),
        CVBS7(0x1007),
        SVIDEO0(0x2000),
        SVIDEO1(0x2001),
        SVIDEO2(0x2002),
        SVIDEO3(0x2003),
        SVIDEO4(0x2004),
        SVIDEO5(0x2005),
        SVIDEO6(0x2006),
        SVIDEO7(0x2007),
        HDMI0(0x4000),
        HDMI1(0x4001),
        HDMI2(0x4002),
        HDMI3(0x4003),
        HDMI4(0x4004),
        HDMI5(0x4005),
        HDMI6(0x4006),
        HDMI7(0x4007),
        DVIN0(0x8000),
        MAX(0x10000000);

        private int val;

        SrcId(int val) {
            this.val = val;
        }

        public int toInt() {
            return this.val;
        }
    }

    public enum Mode3D {
        DISABLE,
        AUTO,
        MODE_2D_TO_3D,
        LR,
        BT,
        OFF_LR_SWITCH,
        ON_LR_SWITCH,
        FIELD_DEPTH,
        OFF_3D_TO_2D,
        L_3D_TO_2D,
        R_3D_TO_2D,
        OFF_LR_SWITCH_BT,
        ON_LR_SWITCH_BT,
        OFF_3D_TO_2D_BT,
        L_3D_TO_2D_BT,
        R_3D_TO_2D_BT,
    }

    public enum SrcType {
        AV, COMPONENT, VGA, HDMI, MPEG, END
    }

    public static enum SigTransFmt {
        TFMT_2D,
        TFMT_3D_LRH_OLOR,
        TFMT_3D_LRH_OLER,
        TFMT_3D_LRH_ELOR,
        TFMT_3D_LRH_ELER,
        TFMT_3D_TB,
        TFMT_3D_FP,
        TFMT_3D_FA,
        TFMT_3D_LA,
        TFMT_3D_LRF,
        TFMT_3D_LD,
        TFMT_3D_LDGD,
    }

    public static enum SigStatus {
        NULL, NO_SIGNAL, UN_STABLE, NOT_SUPPORT, STABLE,
    }

    public enum SigFormat {
        NULL,
        // VGA Formats
        VGA_512X384P_60D147,
        VGA_560X384P_60D147,
        VGA_640X200P_59D924,
        VGA_640X350P_85D080,
        VGA_640X400P_59D940,
        VGA_640X400P_85D080,
        VGA_640X400P_59D638,
        VGA_640X400P_56D416,
        VGA_640X480P_66D619,
        VGA_640X480P_66D667, // 10
        VGA_640X480P_59D940,
        VGA_640X480P_60D000,
        VGA_640X480P_72D809,
        VGA_640X480P_75D000_A,
        VGA_640X480P_85D008,
        VGA_640X480P_59D638,
        VGA_640X480P_75D000_B,
        VGA_640X870P_75D000,
        VGA_720X350P_70D086,
        VGA_720X400P_85D039, // 20
        VGA_720X400P_70D086,
        VGA_720X400P_87D849,
        VGA_720X400P_59D940,
        VGA_720X480P_59D940,
        VGA_768X480P_59D896,
        VGA_800X600P_56D250,
        VGA_800X600P_60D000,
        VGA_800X600P_60D000_A,
        VGA_800X600P_60D317,
        VGA_800X600P_72D188, // 30
        VGA_800X600P_75D000,
        VGA_800X600P_85D061,
        VGA_832X624P_75D087,
        VGA_848X480P_84D751,
        VGA_960X600P_59D635,
        VGA_1024X768P_59D278,
        VGA_1024X768P_60D000,
        VGA_1024X768P_60D000_A,
        VGA_1024X768P_60D000_B,
        VGA_1024X768P_74D927, // 40
        VGA_1024X768P_60D004,
        VGA_1024X768P_70D069,
        VGA_1024X768P_75D029,
        VGA_1024X768P_84D997,
        VGA_1024X768P_74D925,
        VGA_1024X768P_75D020,
        VGA_1024X768P_70D008,
        VGA_1024X768P_75D782,
        VGA_1024X768P_77D069,
        VGA_1024X768P_71D799, // 50
        VGA_1024X1024P_60D000,
        VGA_1152X864P_60D000,
        VGA_1152X864P_70D012,
        VGA_1152X864P_75D000,
        VGA_1152X864P_84D999,
        VGA_1152X870P_75D062,
        VGA_1152X900P_65D950,
        VGA_1152X900P_66D004,
        VGA_1152X900P_76D047,
        VGA_1152X900P_76D149, // 60
        VGA_1280X720P_59D855,
        VGA_1280X720P_60D000_A,
        VGA_1280X720P_60D000_B,
        VGA_1280X720P_60D000_C,
        VGA_1280X720P_60D000_D,
        VGA_1280X768P_59D870,
        VGA_1280X768P_59D995,
        VGA_1280X768P_60D100,
        VGA_1280X768P_60D100_A,
        VGA_1280X768P_74D893, // 70
        VGA_1280X768P_84D837,
        VGA_1280X800P_59D810,
        VGA_1280X800P_59D810_A,
        VGA_1280X800P_60D000,
        VGA_1280X800P_60D000_A,
        VGA_1280X960P_60D000,
        VGA_1280X960P_60D000_A,
        VGA_1280X960P_75D000,
        VGA_1280X960P_85D002,
        VGA_1280X1024P_60D020, // 80
        VGA_1280X1024P_60D020_A,
        VGA_1280X1024P_75D025,
        VGA_1280X1024P_85D024,
        VGA_1280X1024P_59D979,
        VGA_1280X1024P_72D005,
        VGA_1280X1024P_60D002,
        VGA_1280X1024P_67D003,
        VGA_1280X1024P_74D112,
        VGA_1280X1024P_76D179,
        VGA_1280X1024P_66D718, // 90
        VGA_1280X1024P_66D677,
        VGA_1280X1024P_76D107,
        VGA_1280X1024P_59D996,
        VGA_1280X1024P_60D000,
        VGA_1360X768P_59D799,
        VGA_1360X768P_60D015,
        VGA_1360X768P_60D015_A,
        VGA_1360X850P_60D000,
        VGA_1360X1024P_60D000,
        VGA_1366X768P_59D790, // 100
        VGA_1366X768P_60D000,
        VGA_1400X1050P_59D978,
        VGA_1440X900P_59D887,
        VGA_1440X1080P_60D000,
        VGA_1600X900P_60D000,
        VGA_1600X1024P_60D000,
        VGA_1600X1200P_60D000,
        VGA_1600X1200P_65D000,
        VGA_1600X1200P_70D000,
        VGA_1680X1080P_60D000, // 110
        VGA_1920X1080P_59D963,
        VGA_1920X1080P_60D000,
        VGA_1920X1200P_59D950, // 113
        VGA_MAX,
        // Component Formats
        COMP_480P_60D000, // 115
        COMP_480I_59D940,
        COMP_576P_50D000,
        COMP_576I_50D000,
        COMP_720P_59D940,
        COMP_720P_50D000,
        COMP_1080P_23D976,
        COMP_1080P_24D000,
        COMP_1080P_25D000,
        COMP_1080P_30D000,
        COMP_1080P_50D000,
        COMP_1080P_60D000,
        COMP_1080I_47D952,
        COMP_1080I_48D000,
        COMP_1080I_50D000_A,
        COMP_1080I_50D000_B,
        COMP_1080I_50D000_C,
        COMP_1080I_60D000, // 132
        COMP_MAX,
        // HDMI Formats
        HDMI_640x480P_60Hz, // 134
        HDMI_720x480P_60Hz,
        HDMI_1280x720P_60Hz,
        HDMI_1920x1080I_60Hz,
        HDMI_1440x480I_60Hz,
        HDMI_1440x240P_60Hz,
        HDMI_2880x480I_60Hz,
        HDMI_2880x240P_60Hz,
        HDMI_1440x480P_60Hz,
        HDMI_1920x1080P_60Hz,
        HDMI_720x576P_50Hz,
        HDMI_1280x720P_50Hz,
        HDMI_1920x1080I_50Hz_A,
        HDMI_1440x576I_50Hz,
        HDMI_1440x288P_50Hz,
        HDMI_2880x576I_50Hz,
        HDMI_2880x288P_50Hz,
        HDMI_1440x576P_50Hz,
        HDMI_1920x1080P_50Hz,
        HDMI_1920x1080P_24Hz,
        HDMI_1920x1080P_25Hz,
        HDMI_1920x1080P_30Hz,
        HDMI_2880x480P_60Hz,
        HDMI_2880x576P_60Hz,
        HDMI_1920x1080I_50Hz_B,
        HDMI_1920x1080I_100Hz,
        HDMI_1280x720P_100Hz,
        HDMI_720x576P_100Hz,
        HDMI_1440x576I_100Hz,
        HDMI_1920x1080I_120Hz,
        HDMI_1280x720P_120Hz,
        HDMI_720x480P_120Hz,
        HDMI_1440x480I_120Hz,
        HDMI_720x576P_200Hz,
        HDMI_1440x576I_200Hz,
        HDMI_720x480P_240Hz,
        HDMI_1440x480I_240Hz,
        HDMI_1280x720P_24Hz,
        HDMI_1280x720P_25Hz,
        HDMI_1280x720P_30Hz,
        HDMI_1920x1080P_120Hz,
        HDMI_1920x1080P_100Hz, // 175
        HDMI_1280x720P_60Hz_FRAME_PACKING, // 176
        HDMI_1280x720P_50Hz_FRAME_PACKING,
        HDMI_1280x720P_24Hz_FRAME_PACKING,
        HDMI_1280x720P_30Hz_FRAME_PACKING,
        HDMI_1920x1080I_60Hz_FRAME_PACKING,
        HDMI_1920x1080I_50Hz_FRAME_PACKING,
        HDMI_1920x1080P_24Hz_FRAME_PACKING,
        HDMI_1920x1080P_30Hz_FRAME_PACKING, // 183
        HDMI_800x600, // 184
        HDMI_1024x768,
        HDMI_720_400,
        HDMI_1280_768,
        HDMI_1280_800,
        HDMI_1280_960,
        HDMI_1280_1024,
        HDMI_1360_768,
        HDMI_1366_768,
        HDMI_1600_1200,
        HDMI_1920_1200,
        HDMI_RESERVE1,
        HDMI_RESERVE2,
        HDMI_RESERVE3,
        HDMI_RESERVE4,
        HDMI_RESERVE5,
        HDMI_RESERVE6,
        HDMI_RESERVE7,
        HDMI_RESERVE8,
        HDMI_RESERVE9,
        HDMI_RESERVE10,
        HDMI_RESERVE11,
        HDMI_RESERVE12,
        HDMI_RESERVE13,
        HDMI_RESERVE14, // 208
        HDMI_720x480P_60Hz_FRAME_PACKING,
        HDMI_720x576P_50Hz_FRAME_PACKING, // 210
        HDMI_MAX,
        // Video Formats
        CVBS_NTSC_M, // 212
        CVBS_NTSC_443,
        CVBS_PAL_I,
        CVBS_PAL_M,
        CVBS_PAL_60,
        CVBS_PAL_CN,
        CVBS_SECAM, // 218
        // 656 Formats
        BT656IN_576I, // 219
        BT656IN_480I, // 220
        // 601 Formats
        BT601IN_576I, // 221
        BT601IN_480I, // 222
        // Camera Formats
        CAMERA_640X480P_30Hz, // 223
        CAMERA_800X600P_30Hz,
        CAMERA_1024X768P_30Hz,
        CAMERA_1920X1080P_30Hz,
        CAMERA_1280X720P_30Hz, // 227
        MAX, // 228
    }

    public static String SigFormatName[] = {
        "",
        // VGA Formats
        "512X384 60Hz",
        "560X384 60Hz",
        "640X200 60Hz",
        "640X350 85Hz",
        "640X400 60Hz",
        "640X400 85Hz",
        "640X400 60Hz",
        "640X400 56Hz",
        "640X480 66Hz",
        "640X480 66Hz", // 10
        "640X480 60Hz",
        "640X480 60Hz",
        "640X480 72Hz",
        "640X480 75Hz",
        "640X480 85Hz",
        "640X480 60Hz",
        "640X480 75Hz",
        "640X870 75Hz",
        "720X350 70Hz",
        "720X400 85Hz", // 20
        "720X400 70Hz",
        "720X400 87Hz",
        "720X400 60Hz",
        "720X480 60Hz",
        "768X480 60Hz",
        "800X600 56Hz",
        "800X600 60Hz",
        "800X600 60Hz",
        "800X600 60Hz",
        "800X600 72Hz", // 30
        "800X600 75Hz",
        "800X600 85Hz",
        "832X624 75Hz",
        "848X480 84Hz",
        "960X600 60Hz",
        "1024X768 60Hz",
        "1024X768 60Hz",
        "1024X768 60Hz",
        "1024X768 60Hz",
        "1024X768 74Hz", // 40
        "1024X768 60Hz",
        "1024X768 70Hz",
        "1024X768 75Hz",
        "1024X768 84Hz",
        "1024X768 74Hz",
        "1024X768 75Hz",
        "1024X768 70Hz",
        "1024X768 75Hz",
        "1024X768 77Hz",
        "1024X768 71Hz", // 50
        "1024X1024 60Hz",
        "1152X864 60Hz",
        "1152X864 70Hz",
        "1152X864 75Hz",
        "1152X864 84Hz",
        "1152X870 75Hz",
        "1152X900 65Hz",
        "1152X900 66Hz",
        "1152X900 76Hz",
        "1152X900 76Hz", // 60
        "1280X720 60Hz",
        "1280X720 60Hz",
        "1280X720 60Hz",
        "1280X720 60Hz",
        "1280X720 60Hz",
        "1280X768 60Hz",
        "1280X768 60Hz",
        "1280X768 60Hz",
        "1280X768 60Hz",
        "1280X768 74Hz", // 70
        "1280X768 84Hz",
        "1280X800 60Hz",
        "1280X800 60Hz",
        "1280X800 60Hz",
        "1280X800 60Hz",
        "1280X960 60Hz",
        "1280X960 60Hz",
        "1280X960 75Hz",
        "1280X960 85Hz",
        "1280X1024 60Hz", // 80
        "1280X1024 60Hz",
        "1280X1024 75Hz",
        "1280X1024 85Hz",
        "1280X1024 60Hz",
        "1280X1024 72Hz",
        "1280X1024 60Hz",
        "1280X1024 67Hz",
        "1280X1024 74Hz",
        "1280X1024 76Hz",
        "1280X1024 66Hz", // 90
        "1280X1024 66Hz",
        "1280X1024 76Hz",
        "1280X1024 60Hz",
        "1280X1024 60Hz",
        "1360X768 60Hz",
        "1360X768 60Hz",
        "1360X768 60Hz",
        "1360X850 60Hz",
        "1360X1024 60Hz",
        "1366X768 60Hz", // 100
        "1366X768 60Hz",
        "1400X1050 60Hz",
        "1440X900 60Hz",
        "1440X1080 60Hz",
        "1600X900 60Hz",
        "1600X1024 60Hz",
        "1600X1200 60Hz",
        "1600X1200 65Hz",
        "1600X1200 70Hz",
        "1680X1080 60Hz", // 110
        "1920X1080 60Hz",
        "1920X1080 60Hz",
        "1920X1200 60Hz", // 113
        "VGA MAX",
        // Component Formats
        "480P 60Hz", // 115
        "480I 60Hz",
        "576P 50Hz",
        "576I 50Hz",
        "720P 60Hz",
        "720P 50Hz",
        "1080P 23Hz",
        "1080P 24Hz",
        "1080P 25Hz",
        "1080P 30Hz",
        "1080P 50Hz",
        "1080P 60Hz",
        "1080I 47Hz",
        "1080I 48Hz",
        "1080I 50Hz",
        "1080I 50Hz",
        "1080I 50Hz",
        "1080I 60Hz", // 132
        "YPBPR MAX",
        // HDMI Formats
        "640X480 60Hz", // 134
        "480P 60Hz",
        "720P 60Hz",
        "1080I 60Hz",
        "480I 60Hz",
        "240P 60Hz",
        "480I 60Hz",
        "240P 60Hz",
        "480P 60Hz",
        "1080P 60Hz",
        "576P 50Hz",
        "720P 50Hz",
        "1080I 50Hz",
        "576I 50Hz",
        "288P 50Hz",
        "576I 50Hz",
        "288P 50Hz",
        "576P 50Hz",
        "1080P 50Hz",
        "1080P 24Hz",
        "1080P 25Hz",
        "1080P 30Hz",
        "480P 60Hz",
        "576P 60Hz",
        "1080I 50Hz",
        "1080I 100Hz",
        "720P 100Hz",
        "576P 100Hz",
        "576I 100Hz",
        "1080I 120Hz",
        "720P 120Hz",
        "480P 120Hz",
        "480I 120Hz",
        "576P 200Hz",
        "576I 200Hz",
        "480P 240Hz",
        "480I 240Hz",
        "720P 24Hz",
        "720P 25Hz",
        "720P 30Hz",
        "1080P 120Hz",
        "1080P 100Hz", // 175
        "720P 60Hz", // 176
        "720P 50Hz",
        "720P 24Hz",
        "720P 30Hz",
        "1080I 60Hz",
        "1080I 50Hz",
        "1080P 24Hz",
        "1080P 30Hz", // 183
        "800X600 60Hz", // 184
        "1024X768 60Hz",
        "720X400 60Hz",
        "1280X768 60Hz",
        "1280X800 60Hz",
        "1280X960 60Hz",
        "1280X1024 60Hz",
        "1360X768 60Hz",
        "1366X768 60Hz",
        "1600X1200 60Hz",
        "1920X1200 60Hz",
        "RESERVE1",
        "RESERVE2",
        "RESERVE3",
        "RESERVE4",
        "RESERVE5",
        "RESERVE6",
        "RESERVE7",
        "RESERVE8",
        "RESERVE9",
        "RESERVE10",
        "RESERVE11",
        "RESERVE12",
        "RESERVE13",
        "RESERVE14", // 208
        "720X480 60Hz",
        "720X576 50Hz", // 210
        "HDMI MAX",
        // Video Formats
        "NTSC M", // 212
        "NTSC 443",
        "PAL I",
        "PAL M",
        "PAL 60",
        "PAL CN",
        "SECAM", // 218
        // 656 Formats
        "576I", // 219
        "480I", // 220
        // 601 Formats
        "576I", // 221
        "480I", // 222
        // Camera Formats
        "640X480 30Hz", // 223
        "800X600 30Hz",
        "1024X768 30Hz",
        "1920X1080 30Hz",
        "1280X720 30Hz", // 227
        "ALL MAX", // 228
    };

    public enum FmtRatio {
        RT43, RT169,
    }

    public enum ColorFmt {
        RGB444, YUV422, YUV444, TVIN_COLOR_FMT_MAX,
    }

    public enum TvPath {
        VDIN_AMVIDEO,
        VDIN_DEINTERLACE_AMVIDEO,
        VDIN_3D_AMVIDEO,
        DECODER_3D_AMVIDEO,
        DECODER_AMVIDEO,
    }

    public static TvinParam gParam = new TvinParam();

    private static int BlueScreenType = 0;

    /***************************************************
     * 
     * for UI
     * 
     ***************************************************/
    public static void Open3DModule() {
        OpenPPMGRModule();
    }

    public static void Close3DModule() {
        ClosePPMGRModule();
    }

    public static void InitTvin(int windowSel) {
        OpenVDINModule(0); // current usage: always set '0'
        // OpenPPMGRModule();
        AddTvinPath(TvPath.VDIN_DEINTERLACE_AMVIDEO.ordinal());
        LoadHDCP();
        vppJNI.DisableVideoLayer(1);
        vppJNI.SetSyncEnable(0);
        vppJNI.UiSetBlackExtension(1, 0x30, 0x24, 0x18, 0x40);
        gParam.port = SrcId.NULL.toInt();
        gParam.fmt = SigFormat.NULL.ordinal();
        gParam.status = SigStatus.NULL.ordinal();
        vdinJNI.CreateSigDetectThr();
    }

    public static boolean CloseTvin(int windowSel) {
        boolean IsCloseTvDone = false;

        TurnOnBlueScreen(2);

        KillSigDetectThr();
        if (gParam.isDecoStart == false) {
            if (GetCurrentSrcInput() == SrcId.CVBS0.toInt()) {
                SetDICFG(0);
                Log.d("TV JAVA VDIN", "CloseTvin: SetDICFG(0) in TV source.");
            } else {
                SetDIBuffMgrMode(0);
            }
        } else {
            SetDIBuffMgrMode(0);
            StopDec(0);
            gParam.isDecoStart = false;
        }
        gParam.port = SrcId.NULL.toInt();
        gParam.mode3D = vdinJNI.Mode3D.DISABLE.ordinal();
        gParam.status3D = vdinJNI.Mode3D.DISABLE.ordinal();
        gParam.isSrcSwitchExecDone = true;
        int val = eepromJNI.LoadVideoSetting(SrcType.MPEG, VideoFunc.SHARPNESS);
        vppJNI.UiSetSharpness(true, 50);
        vppJNI.DisableVideoLayer(0);
        vppJNI.SetSyncEnable(1);
        vppJNI.UiSetBlackExtension(0, 0x30, 0x24, 0x18, 0x40);
        vppJNI.UiSetDNLP(1, 0xf0, 4, 4, 1);
        
        for (int i = 0; i < 10; i++) {
            if (isSrcSwtichDone() == true && isDetectThrDone() == true) {
                break;
            } else {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        IsCloseTvDone = true;
        Set3DOvserScan(false);
        RemoveTvinPath(1);
        Send3DCommand(vdinJNI.Mode3D.DISABLE.ordinal());
        ClosePort(0);
        CloseVDINModule(windowSel);

        return IsCloseTvDone;
    }

    public static void CreateSigDetectThr() {
        Thread sigDetecThr = new Thread(new SignalDetectThr());
        gParam.isDecoStart = false;
        sigDetecThr.setName("SignalDetectThr");
        sigDetecThr.setPriority(Thread.MAX_PRIORITY);
        gParam.isTurnOnSigDetectThr = true;
        gParam.isStartSigDetectThr = false;
        sigDetecThr.start();
        Log.d("TV JAVA VDIN", "CreateSigDetectThr.");
    }

    public static void KillSigDetectThr() {
        int i = 0;
        gParam.isTurnOnSigDetectThr = false;
        for (i = 0; i < 4; i++) {
            if (isDetectThrDone() == false) {
                Log.w("TV JAVA VDIN", "KillSigDetectThr: Source detect thread is busy, please wait 50ms...");
                DelayMs(50);
            } else {
                Log.d("TV JAVA VDIN", "KillSigDetectThr: Source detect thread is idle now, go ahead...");
                break;
            }
        }
        if (i == 4) {
            Log.w("TV JAVA VDIN", "KillSigDetectThr: 200ms delay Timeout, have to go");
        }
        Log.d("TV JAVA VDIN", "KillSigDetectThr.");
    }

    public static void SetCheckStableCount(int count) {
        CheckStableCount = count;
    }

    public static boolean isSrcSwtichDone() {
        return gParam.isSrcSwitchExecDone;
    }

    public static boolean isDetectThrDone() {
        return gParam.isSigDetectExecDone;
    }

    public static void StartSigDetectThr() {
        gParam.status = vdinJNI.SigStatus.UN_STABLE.ordinal();
        gParam.fmt = vdinJNI.SigFormat.NULL.ordinal();
        gParam.trans_fmt = vdinJNI.SigTransFmt.TFMT_2D.ordinal();
        gParam.isStartSigDetectThr = true;
        Log.d("TV JAVA VDIN", "StartSigDetectThr.");
    }

    public static void StopSigDetectThr() {
        int i = 0;
        gParam.isStartSigDetectThr = false;
        for (i = 0; i < 4; i++) {
            if (isDetectThrDone() == false) {
                Log.w("TV JAVA VDIN", "StopSigDetectThr: Source detect thread is busy, please wait 50ms...");
                DelayMs(50);
            } else {
                Log.d("TV JAVA VDIN", "StopSigDetectThr: Source detect thread is idle now, go ahead...");
                break;
            }
        }
        if (i == 4) {
            Log.w("TV JAVA VDIN", "StopSigDetectThr: 200ms delay Timeout, have to go");
        }
        Log.d("TV JAVA VDIN", "StopSigDetectThr.");
    }

    /*
     * 0:close blue screen & turn on video 1:turn on blue screen 2:close blue
     * screen & close video
     */
    public static void TurnOnBlueScreen(int type) {
        if (GetSrcType() == SrcType.MPEG) {
            Log.d("TV JAVA VDIN", "mpeg type:black screen");
            vppJNI.SetScreenColor(0, 16, 128, 128); // Show black with vdin0
                                                    // postblending disabled
            BlueScreenType = type;
            return;
        }

        if (type == 0) {
            if (gParam.mode3D == Mode3D.MODE_2D_TO_3D.ordinal()) {
                for (int i = 0; i < 10; i++) {
                    DelayMs(100);
                    if (GetVscalerStatus() == 1) {
                        Log.d(DBTAG, "turn off v-scaler!!");
                        break;
                    } else {
                        Log.d(DBTAG, "wait for turn off v-scaler...");
                    }
                }
            }
            vppJNI.SetScreenColor(3, 16, 128, 128); // Show black with vdin0
                                                    // postblending enabled
        } else if (type == 1)
            vppJNI.SetScreenColor(0, 41, 240, 110); // Show blue with vdin0
                                                    // postblending disabled
        else
            vppJNI.SetScreenColor(0, 16, 128, 128); // Show black with vdin0
                                                    // postblending disabled
        BlueScreenType = type;
    }

    public static int GetBlueScreenType() {
        return BlueScreenType;
    }

    public static int StopDecoder(int windowSel) {
        Log.d("TV JAVA VDIN", "in StopDecoder()......................");

        if (gParam.isDecoStart == true) {
            if (StopDec(windowSel) >= 0) {
                Log.d("TV JAVA VDIN", "stop ok!");
                gParam.isDecoStart = false;
                return 0;
            } else {
                Log.d("TV JAVA VDIN", "StopDecoder Failed!");
            }
        } else {
            Log.d("TV JAVA VDIN", "Decoder never start, no need StopDecoder!");
            return 1;
        }
        return -1;
    }

    public static int StartDecoder(int windowSel, boolean isTurnOnOverScan) {
        if ((gParam.isDecoStart == false)
            && (gParam.fmt != SigFormat.NULL.ordinal())
            && (gParam.status == vdinJNI.SigStatus.STABLE.ordinal())) {
            Log.d(DBTAG, "SetOverScan, isTurnOnOverScan : " + isTurnOnOverScan);
            SetOverScan(isTurnOnOverScan);
            if (StartDec(windowSel, gParam) >= 0) {
                Log.d(DBTAG, "StartDecoder succeed.");
                gParam.isDecoStart = true;

                if (GetSrcType() == SrcType.VGA) {
                    // need to do auto-adjustment
                    if (eepromJNI.IsAutoAdjFlag(gParam.fmt) == 0) {
                        Log.d("TV JAVA VDIN", "vga format first auto adjustment!!");
                        afeJNI.RunVgaAutoAdjThr();
                    } else {
                        afeJNI.vgaTimingAdj = eepromJNI.LoadVgaAllAdjustment(gParam.fmt);
                        afeJNI.SetVGAAdjParam(afeJNI.vgaTimingAdj);
                    }
                    afeJNI.SetAdcGainOffset(afeJNI.vgaGainOffset);
                } else if (GetSrcType() == SrcType.COMPONENT) {
                    afeJNI.SetAdcGainOffset(afeJNI.comp0GainOffset);
                } else if (GetSrcType() == SrcType.AV) {
                    if (vdinJNI.GetCurrentSrcInput() == vdinJNI.SrcId.CVBS1.toInt()) {
                        int avcolor = eepromJNI.LoadAvColorSys();
                        afeJNI.SetCVBSStd(avcolor);
                    }

                    int huevalue = eepromJNI.LoadVideoSetting(SrcType.AV, VideoFunc.HUE);
                    vppJNI.UiSetHue(huevalue);
                    if (gParam.fmt == vdinJNI.SigFormat.CVBS_NTSC_M.ordinal()
                        || gParam.fmt == vdinJNI.SigFormat.CVBS_NTSC_443.ordinal()) {
                        vdinJNI.sendMessageToUI(GlobalConst.MSG_SIGNAL_FORMAT_NTSC);
                    } else {
                        vdinJNI.sendMessageToUI(GlobalConst.MSG_SIGNAL_FORMAT_NOT_NTSC);
                    }
                } else if (GetSrcType() == SrcType.HDMI) {
                    int scrmode = eepromJNI.LoadVideoSetting(GetSrcType(), VideoFunc.ASPECT_RAT);

                    if (vdinJNI.GetSigFormat() == SigFormat.HDMI_1920x1080P_60Hz.ordinal()
                        || vdinJNI.GetSigFormat() == SigFormat.HDMI_1920x1080I_60Hz_FRAME_PACKING.ordinal()
                        || vdinJNI.GetSigFormat() == SigFormat.HDMI_1920x1080I_50Hz_FRAME_PACKING.ordinal()
                        || vdinJNI.GetSigFormat() == SigFormat.HDMI_1920x1080P_24Hz_FRAME_PACKING.ordinal()
                        || vdinJNI.GetSigFormat() == SigFormat.HDMI_1920x1080P_30Hz_FRAME_PACKING.ordinal()) {
                        if (scrmode == ScreenMode.FULL.ordinal())
                            vppJNI.UiSetScrMode(scrmode);
                    }

                    if (vdinJNI.isVgaFmtInHdmi() == 1) {
                        Log.d(DBTAG, "VGA format in HDMI source");
                        if (scrmode != ScreenMode.MODE169.ordinal()
                            && scrmode != ScreenMode.MODE43.ordinal()) {
                            Log.d(DBTAG, "force to 16:9 display without saving to eeprom");
                            // mode 16:9 without saving to eeprom
                            vppJNI.UiSetScrMode(0xff);
                        }
                    }
                }
            } else {
                Log.d(DBTAG, "StartDecoder failed.");
                return -1;
            }
        } else {
            Log.d("TV JAVA VDIN", "Can not StartDecoder," + "isDecoStart:"
                + gParam.isDecoStart + ", fmt:" + gParam.fmt);
            return -1;
        }
        return 0;
    }

    static void CloseSrcInput(int windowSel) {
        if (gParam.port != SrcId.NULL.toInt())
            ClosePort(windowSel);
    }

    static void OpenSrcInput(int windowSel) {
        if (gParam.port != SrcId.NULL.toInt())
            OpenPort(0, gParam.port);
        Log.d("TV JAVA VDIN", "open port=" + gParam.port);
    }

    private static void LoadHDCP() {
        String[] cmd = {
            "./dec"
        };
        eepromJNI.LoadHdcpKey();
        SendCommand(cmd);
    }

    public static void RemoveTvinPath(int pathid) {
        int ret = -1;
        if (pathid == 0) {
            for (int i = 0; i < 10; i++) {
                ret = RmDefPath();
                Log.d("TV JAVA VDIN", "rm DefaultPath return = " + ret);
                if (ret > 0) {
                    Log.e("TV JAVA VDIN", "remove DefaultPath OK, i = " + i);
                    break;
                } else {
                    Log.d("TV JAVA VDIN", "remove DefaultPath faild, i = " + i);
                    DelayMs(100);
                }
            }
        } else {
            for (int i = 0; i < 10; i++) {
                ret = RmTvPath();
                Log.d("TV JAVA VDIN", "rm TvPath return = " + ret);
                if (ret > 0) {
                    Log.e("TV JAVA VDIN", "remove TvPath OK, delay i = " + i);
                    break;
                } else {
                    Log.e("TV JAVA VDIN", "remove TvPath faild, delay i = " + i);
                    DelayMs(100);
                }
            }
        }
    }

    public static void AddTvinPath(int pathid) {
        int ret = -1;
        for (int i = 0; i < 10; i++) {
            ret = AddTvPath(pathid);
            if (ret > 0) {
                Log.e("TV JAVA VDIN", "Add TvinPath OK, delay i = " + i);
                gParam.TvinPathId = pathid;
                break;
            } else {
                Log.e("TV JAVA VDIN", "Add TvinPath faild, delay i = " + i);
                DelayMs(100);
            }
        }
    }

    public static void SetOverScan(boolean isTurnOn) {
        if (isTurnOn == false) {
            gParam.cutHs = 0;
            gParam.cutHe = 0;
            gParam.cutVs = 0;
            gParam.cutVe = 0;
            gParam.isTurnOnOverScan = false;
            Log.d("TV JAVA VDIN", "turn off OverScan().");
            return;
        }

        if (Get3DStatus() == Mode3D.DISABLE.ordinal()
            || Get3DStatus() == Mode3D.MODE_2D_TO_3D.ordinal()
            || Get3DStatus() == Mode3D.LR.ordinal()
            || Get3DStatus() == Mode3D.BT.ordinal()) {
            Log.d("TV JAVA VDIN", "3D mode DISABLE/DISABLE, turn on overscan()");
        } else if (Get3DStatus() == Mode3D.AUTO.ordinal()) {
            if (gParam.trans_fmt == SigTransFmt.TFMT_2D.ordinal()) {
                Log.d("TV JAVA VDIN", "3D mode is AUTO,2D -> turn on overscan()");
            } else {
                gParam.cutHs = 0;
                gParam.cutHe = 0;
                gParam.cutVs = 0;
                gParam.cutVe = 0;
                gParam.isTurnOnOverScan = false;
                Log.d("TV JAVA VDIN", "3D mode is AUTO,3D -> turn off overscan()");
                return; // return when 3D turn on
            }
        } else {
            gParam.cutHs = 0;
            gParam.cutHe = 0;
            gParam.cutVs = 0;
            gParam.cutVe = 0;
            gParam.isTurnOnOverScan = false;
            Log.d("TV JAVA VDIN", "turn off OverScan() in 3D mode.");
            return; // return when 3D turn on
        }
        /** CVBS **/
        if (GetSrcType() == SrcType.AV) {
            if (GetSigFormat() == SigFormat.CVBS_NTSC_M.ordinal()
                || GetSigFormat() == SigFormat.CVBS_NTSC_443.ordinal()) {
                Log.d("TV JAVA VDIN", "CVBS 480i CUT OKK% !!");
                gParam.cutHs = 30;
                gParam.cutHe = 720 - 30 - 1;
                gParam.cutVs = 2;
                gParam.cutVe = 240 - 2 - 1; // 9
            } else if (GetSigFormat() >= SigFormat.CVBS_PAL_I.ordinal()
                && GetSigFormat() <= SigFormat.CVBS_SECAM.ordinal()) {
                Log.d("TV JAVA VDIN", "CVBS 576i CUT OKK% !!");
                gParam.cutHs = 32;
                gParam.cutHe = 720 - 32 - 1;
                gParam.cutVs = 6;
                gParam.cutVe = 288 - 8 - 1;
            }
            gParam.isTurnOnOverScan = true;
            Log.d("TV JAVA VDIN", "turn on OverScan() in AV.");
            /** YPbPr **/
        } else if (GetSrcType() == SrcType.COMPONENT) {
            if (GetSigFormat() == SigFormat.COMP_480P_60D000.ordinal()) {
                Log.d("TV JAVA VDIN", "COMPONENT 480p CUT!!");
                gParam.cutHs = 27;
                gParam.cutHe = 720 - 19 - 1;
                gParam.cutVs = 6;
                gParam.cutVe = 480 - 4 - 1;
            } else if (GetSigFormat() == SigFormat.COMP_480I_59D940.ordinal()) {
                Log.d("TV JAVA VDIN", "COMPONENT 480i CUT!!");
                gParam.cutHs = 15;
                gParam.cutHe = 720 - 23 - 1;
                gParam.cutVs = 2;
                gParam.cutVe = 240 - 3 - 1;
            } else if (GetSigFormat() == SigFormat.COMP_576P_50D000.ordinal()) {
                Log.d("TV JAVA VDIN", "COMPONENT 576p CUT!!");
                gParam.cutHs = 20;
                gParam.cutHe = 720 - 18 - 1;
                gParam.cutVs = 7;
                gParam.cutVe = 576 - 7 - 1;
            } else if (GetSigFormat() == SigFormat.COMP_576I_50D000.ordinal()) {
                Log.d("TV JAVA VDIN", "COMPONENT 576i CUT!!");
                gParam.cutHs = 18;
                gParam.cutHe = 720 - 20 - 1;
                gParam.cutVs = 3;
                gParam.cutVe = 288 - 5 - 1;
            } else if (GetSigFormat() == SigFormat.COMP_720P_59D940.ordinal()
                || GetSigFormat() == SigFormat.COMP_720P_50D000.ordinal()) {
                Log.d("TV JAVA VDIN", "COMPONENT 720p CUT!!");
                gParam.cutHs = 26;
                gParam.cutHe = 1280 - 30 - 1;
                gParam.cutVs = 14;
                gParam.cutVe = 720 - 14 - 1;
            } else if (GetSigFormat() >= SigFormat.COMP_1080P_23D976.ordinal()
                && GetSigFormat() <= SigFormat.COMP_1080I_60D000.ordinal()) {
                gParam.cutHs = 36;
                gParam.cutHe = 1920 - 40 - 1;
                if (isDeinterlaceFmt() == true) {
                    Log.d("TV JAVA VDIN", "COMPONENT 1080i CUT!!");
                    gParam.cutVs = 8;
                    gParam.cutVe = 540 - 9 - 1;
                } else {
                    Log.d("TV JAVA VDIN", "COMPONENT 1080p CUT!!");
                    gParam.cutVs = 16;
                    gParam.cutVe = 1080 - 16 - 1;
                }
            }

            if (Get3DStatus() == Mode3D.LR.ordinal()
                || Get3DStatus() == Mode3D.BT.ordinal()) {
                gParam.cutTop = gParam.cutVs;
                gParam.cutLeft = gParam.cutHs;
                gParam.cutHs = 0;
                gParam.cutHe = 0;
                gParam.cutVs = 0;
                gParam.cutVe = 0;
                gParam.isTurnOnOverScan = false;
                Set3DOvserScan(true);
            } else {
                gParam.isTurnOnOverScan = true;
                Set3DOvserScan(false);
            }
            Log.d("TV JAVA VDIN", "turn on OverScan() in YPbPr.");
            /** HDMI **/
        } else if (GetSrcType() == SrcType.HDMI) {
            if (GetSigFormat() == SigFormat.HDMI_640x480P_60Hz.ordinal()
                || GetSigFormat() == SigFormat.HDMI_1920x1080P_60Hz.ordinal()
                || GetSigFormat() == SigFormat.HDMI_1024x768.ordinal()
                || GetSigFormat() == SigFormat.HDMI_800x600.ordinal()
                || GetSigFormat() == SigFormat.HDMI_720_400.ordinal()
                || GetSigFormat() == SigFormat.HDMI_1280_768.ordinal()
                || GetSigFormat() == SigFormat.HDMI_1280_800.ordinal()
                || GetSigFormat() == SigFormat.HDMI_1280_960.ordinal()
                || GetSigFormat() == SigFormat.HDMI_1280_1024.ordinal()
                || GetSigFormat() == SigFormat.HDMI_1360_768.ordinal()
                || GetSigFormat() == SigFormat.HDMI_1366_768.ordinal()
                || GetSigFormat() == SigFormat.HDMI_1600_1200.ordinal()
                || GetSigFormat() == SigFormat.HDMI_1920_1200.ordinal()
                || GetSigFormat() == SigFormat.HDMI_1920x1080I_60Hz_FRAME_PACKING.ordinal()
                || GetSigFormat() == SigFormat.HDMI_1920x1080I_50Hz_FRAME_PACKING.ordinal()
                || GetSigFormat() == SigFormat.HDMI_1920x1080P_24Hz_FRAME_PACKING.ordinal()
                || GetSigFormat() == SigFormat.HDMI_1920x1080P_30Hz_FRAME_PACKING.ordinal()) {
                gParam.cutHs = 0;
                gParam.cutHe = 0;
                gParam.cutVs = 0;
                gParam.cutVe = 0;
                gParam.isTurnOnOverScan = false;
                Log.d("TV JAVA VDIN", "HDMI turn off OverScan() in vga format or framepacking.");
                return;
            } else if (GetSigFormat() == SigFormat.HDMI_720x480P_60Hz.ordinal()
                || GetSigFormat() == SigFormat.HDMI_720x480P_120Hz.ordinal()
                || GetSigFormat() == SigFormat.HDMI_720x480P_240Hz.ordinal()) {
                gParam.cutHs = 19;
                gParam.cutHe = 720 - 19 - 1;
                gParam.cutVs = 5;
                gParam.cutVe = 480 - 5 - 1;
                Log.d("TV JAVA VDIN", "HDMI 480p CUT!!");
            } else if (GetSigFormat() == SigFormat.HDMI_1440x480P_60Hz.ordinal()
                || GetSigFormat() == SigFormat.HDMI_1440x480I_60Hz.ordinal()
                || GetSigFormat() == SigFormat.HDMI_1440x480I_120Hz.ordinal()
                || GetSigFormat() == SigFormat.HDMI_1440x480I_240Hz.ordinal()) {
                gParam.cutHs = 31;
                gParam.cutHe = 1440 - 47 - 1;
                if (isDeinterlaceFmt() == true) {
                    gParam.cutVs = 2;
                    gParam.cutVe = 240 - 3 - 1;
                    Log.d("TV JAVA VDIN", "HDMI 1440i CUT!!");
                } else {
                    gParam.cutVs = 18;
                    gParam.cutVe = 480 - 18 - 1;
                    Log.d("TV JAVA VDIN", "HDMI 1440p CUT!!");
                }
            } else if (GetSigFormat() == SigFormat.HDMI_2880x480P_60Hz.ordinal()
                || GetSigFormat() == SigFormat.HDMI_2880x480I_60Hz.ordinal()) {
                gParam.cutHs = 112;
                gParam.cutHe = 2880 - 112 - 1;
                if (isDeinterlaceFmt() == true) {
                    gParam.cutVs = 10;
                    gParam.cutVe = 240 - 10 - 1;
                    Log.d("TV JAVA VDIN", "HDMI 2880i CUT!!");
                } else {
                    gParam.cutVs = 18;
                    gParam.cutVe = 480 - 18 - 1;
                    Log.d("TV JAVA VDIN", "HDMI 2880p CUT!!");
                }
            } else if (GetSigFormat() == SigFormat.HDMI_1440x240P_60Hz.ordinal()) {
                gParam.cutHs = 56;
                gParam.cutHe = 1440 - 56 - 1;
                gParam.cutVs = 10;
                gParam.cutVe = 240 - 10 - 1;
                Log.d("TV JAVA VDIN", "HDMI 1440x240p CUT!!");
            } else if (GetSigFormat() == SigFormat.HDMI_2880x240P_60Hz.ordinal()) {
                gParam.cutHs = 112;
                gParam.cutHe = 2880 - 112 - 1;
                gParam.cutVs = 10;
                gParam.cutVe = 240 - 10 - 1;
                Log.d("TV JAVA VDIN", "HDMI 2880x240p CUT!!");
            } else if (GetSigFormat() == SigFormat.HDMI_720x576P_50Hz.ordinal()
                || GetSigFormat() == SigFormat.HDMI_720x576P_100Hz.ordinal()
                || GetSigFormat() == SigFormat.HDMI_720x576P_200Hz.ordinal()) {
                gParam.cutHs = 18;
                gParam.cutHe = 720 - 18 - 1;
                gParam.cutVs = 6;
                gParam.cutVe = 576 - 6 - 1;
                Log.d("TV JAVA VDIN", "HDMI 720x576p CUT!!");
            } else if (GetSigFormat() == SigFormat.HDMI_1440x576I_50Hz.ordinal()
                || GetSigFormat() == SigFormat.HDMI_1440x576P_50Hz.ordinal()
                || GetSigFormat() == SigFormat.HDMI_1440x576I_100Hz.ordinal()
                || GetSigFormat() == SigFormat.HDMI_1440x576I_200Hz.ordinal()) {
                gParam.cutHs = 35;
                gParam.cutHe = 1440 - 41 - 1;
                if (isDeinterlaceFmt() == true) {
                    gParam.cutVs = 3;
                    gParam.cutVe = 288 - 5 - 1;
                    Log.d("TV JAVA VDIN", "HDMI 1440x576i CUT!!");
                } else {
                    gParam.cutVs = 22;
                    gParam.cutVe = 576 - 22 - 1;
                    Log.d("TV JAVA VDIN", "HDMI 1440x576p CUT!!");
                }
            } else if (GetSigFormat() == SigFormat.HDMI_2880x576I_50Hz.ordinal()
                || GetSigFormat() == SigFormat.HDMI_2880x576P_60Hz.ordinal()) {
                gParam.cutHs = 112;
                gParam.cutHe = 2880 - 112 - 1;
                if (isDeinterlaceFmt() == true) {
                    gParam.cutVs = 12;
                    gParam.cutVe = 288 - 12 - 1;
                    Log.d("TV JAVA VDIN", "HDMI 2880x576i CUT!!");
                } else {
                    gParam.cutVs = 22;
                    gParam.cutVe = 576 - 22 - 1;
                    Log.d("TV JAVA VDIN", "HDMI 2880x576p CUT!!");
                }
            } else if (GetSigFormat() == SigFormat.HDMI_1440x288P_50Hz.ordinal()) {
                gParam.cutHs = 56;
                gParam.cutHe = 1440 - 56 - 1;
                gParam.cutVs = 12;
                gParam.cutVe = 288 - 12 - 1;
                Log.d("TV JAVA VDIN", "HDMI 1440x288p CUT!!");
            } else if (GetSigFormat() == SigFormat.HDMI_2880x288P_50Hz.ordinal()) {
                gParam.cutHs = 112;
                gParam.cutHe = 2880 - 112 - 1;
                gParam.cutVs = 12;
                gParam.cutVe = 288 - 12 - 1;
                Log.d("TV JAVA VDIN", "HDMI 2880x288p CUT!!");
            } else if (GetSigFormat() == SigFormat.HDMI_1280x720P_60Hz.ordinal()
                || GetSigFormat() == SigFormat.HDMI_1280x720P_50Hz.ordinal()
                || GetSigFormat() == SigFormat.HDMI_1280x720P_100Hz.ordinal()
                || GetSigFormat() == SigFormat.HDMI_1280x720P_120Hz.ordinal()
                || GetSigFormat() == SigFormat.HDMI_1280x720P_24Hz.ordinal()
                || GetSigFormat() == SigFormat.HDMI_1280x720P_25Hz.ordinal()
                || GetSigFormat() == SigFormat.HDMI_1280x720P_30Hz.ordinal()
                || GetSigFormat() == SigFormat.HDMI_1280x720P_60Hz_FRAME_PACKING.ordinal()
                || GetSigFormat() == SigFormat.HDMI_1280x720P_50Hz_FRAME_PACKING.ordinal()
                || GetSigFormat() == SigFormat.HDMI_1280x720P_24Hz_FRAME_PACKING.ordinal()
                || GetSigFormat() == SigFormat.HDMI_1280x720P_30Hz_FRAME_PACKING.ordinal()) {
                gParam.cutHs = 30;
                gParam.cutHe = 1280 - 30 - 1;
                gParam.cutVs = 14;
                gParam.cutVe = 720 - 14 - 1;
                Log.d("TV JAVA", "HDMI 720p CUT!!");
            } else if (GetSigFormat() == SigFormat.HDMI_1920x1080I_60Hz.ordinal()
                || GetSigFormat() == SigFormat.HDMI_1920x1080I_50Hz_A.ordinal()
                || GetSigFormat() == SigFormat.HDMI_1920x1080P_50Hz.ordinal()
                || GetSigFormat() == SigFormat.HDMI_1920x1080P_24Hz.ordinal()
                || GetSigFormat() == SigFormat.HDMI_1920x1080P_25Hz.ordinal()
                || GetSigFormat() == SigFormat.HDMI_1920x1080P_30Hz.ordinal()
                || GetSigFormat() == SigFormat.HDMI_1920x1080I_50Hz_B.ordinal()
                || GetSigFormat() == SigFormat.HDMI_1920x1080I_100Hz.ordinal()
                || GetSigFormat() == SigFormat.HDMI_1920x1080I_120Hz.ordinal()
                || GetSigFormat() == SigFormat.HDMI_1920x1080P_120Hz.ordinal()
                || GetSigFormat() == SigFormat.HDMI_1920x1080P_100Hz.ordinal()) {
                gParam.cutHs = 36;
                gParam.cutHe = 1920 - 36 - 1;
                if (isDeinterlaceFmt() == true) {
                    Log.d("TV JAVA", "HDMI 1080i CUT!!");
                    gParam.cutVs = 8;
                    gParam.cutVe = 540 - 8 - 1;
                } else {
                    Log.d("TV JAVA", "HDMI 1080p CUT!!");
                    gParam.cutVs = 18;
                    gParam.cutVe = 1080 - 18 - 1;
                }
            }

            if (Get3DStatus() == Mode3D.LR.ordinal()
                || Get3DStatus() == Mode3D.BT.ordinal()) {
                gParam.cutTop = gParam.cutVs;
                gParam.cutLeft = gParam.cutHs;
                gParam.cutHs = 0;
                gParam.cutHe = 0;
                gParam.cutVs = 0;
                gParam.cutVe = 0;
                gParam.isTurnOnOverScan = false;
                Set3DOvserScan(true);
            } else {
                gParam.isTurnOnOverScan = true;
                Set3DOvserScan(false);
            }

            Log.d("TV JAVA VDIN", "turn on OverScan() in HDMI.");
            /** VGA **/
        } else if (GetSrcType() == SrcType.VGA) {
            gParam.cutHs = 0;
            gParam.cutHe = 0;
            gParam.cutVs = 0;
            gParam.cutVe = 0;
            gParam.isTurnOnOverScan = false;
            Log.d("TV JAVA VDIN", "turn off OverScan() in VGA.");
            /** MPEG **/
        } else if (GetSrcType() == SrcType.MPEG) {
            ;
        }
    }

    /****************************************************
     * param : 1-> select display window, for PIP/PBP.. 2-> select source input
     * 3-> select audio channel
     ****************************************************/
    public static void SourceSwitch(int windowSel, int sourceId,
        int audio_channel) {
        if (vdinJNI.isSrcSwtichDone()) {
            // before SourceSwitch must set 3dmode to default
            if (sourceId >= vdinJNI.SrcId.HDMI0.toInt()
                && sourceId <= vdinJNI.SrcId.HDMI7.toInt()) {
                vdinJNI.SetCurrent3DMode(vdinJNI.Mode3D.AUTO.ordinal());
                vdinJNI.SetCurrent3DStatus(vdinJNI.Mode3D.AUTO.ordinal());
            } else {
                vdinJNI.SetCurrent3DMode(vdinJNI.Mode3D.DISABLE.ordinal());
                vdinJNI.SetCurrent3DStatus(vdinJNI.Mode3D.DISABLE.ordinal());
            }

            if (SrcSwitchUsingThread == true) {
                Thread SrcSwitch = new Thread(new SourceSwitchThread());
                SrcSwitch.setName("SourceSwitchThread");
                SrcSwitch.setPriority(Thread.MAX_PRIORITY);
                SourceSwitchThread.SetWindowSel(windowSel);
                SourceSwitchThread.SetSrcId(sourceId);
                SourceSwitchThread.SetAudioChannel(audio_channel);
                SrcSwitch.start();
            } else {
                SourceSwitchThread.RealSwitchSource(windowSel, sourceId, audio_channel);
            }
        } else {
            Log.d("TV JAVA VDIN", "SourceSwitch, isSrcSwtichDone == false, you can not do source switch, please be patient.");
        }
    }

    public static int GetFmtRatio() {
        return gParam.getFmtRatio();
    }

    public static SrcType GetSrcType() {
        return gParam.getSrcType();
    }

    public static int GetCurSource() {
        return gParam.curSource;
    }

    public static void SetCurSource(int src) {
        gParam.curSource = src;
    }

    public static int GetAvColorSys() {
        return gParam.avColorSys;
    }

    public static void SetAvColorSys(int val) {
        gParam.avColorSys = val;
    }

    public static int GetSigStatus() {
        return gParam.status;
    }

    public static int GetSigTransFormat() {
        return gParam.trans_fmt;
    }

    public static int GetSigFormat() {
        return gParam.fmt;
    }

    public static String GetSigFormatName() {
        String _str = " ";
        SigFormat[] sigfmt = SigFormat.values();
        int index = GetSigFormat();
        for (SigFormat fmt : sigfmt) {
            if (fmt.ordinal() == index)
                _str = vdinJNI.SigFormatName[index];
        }
        Log.d("TV JAVA VDIN", "The format info is:" + _str);
        return _str;
    }

    public static int GetCurrentSrcInput() {
        return gParam.port;
    }

    public static int Get3DStatus() {
        return gParam.status3D;
    }

    public static int Get3DMode() {
        return gParam.mode3D;
    }

    public static void SetCurrent3DMode(int mode) {
        gParam.mode3D = mode;
        Log.d("TV JAVA VDIN", "SetCurrent3DMode: " + gParam.mode3D);
    }

    public static void SetCurrent3DStatus(int mode) {
        gParam.status3D = mode;
        Log.d("TV JAVA VDIN", "SetCurrent3DStatus: " + gParam.status3D);
    }

    public static boolean isDeinterlaceFmt() {
        if (gParam.fmt == SigFormat.COMP_480I_59D940.ordinal()
            || gParam.fmt == SigFormat.COMP_576I_50D000.ordinal()
            || gParam.fmt == SigFormat.COMP_1080I_47D952.ordinal()
            || gParam.fmt == SigFormat.COMP_1080I_48D000.ordinal()
            || gParam.fmt == SigFormat.COMP_1080I_50D000_A.ordinal()
            || gParam.fmt == SigFormat.COMP_1080I_50D000_B.ordinal()
            || gParam.fmt == SigFormat.COMP_1080I_50D000_C.ordinal()
            || gParam.fmt == SigFormat.COMP_1080I_60D000.ordinal()
            || gParam.fmt == SigFormat.HDMI_1920x1080I_60Hz.ordinal()
            || gParam.fmt == SigFormat.HDMI_1440x480I_60Hz.ordinal()
            || gParam.fmt == SigFormat.HDMI_2880x480I_60Hz.ordinal()
            || gParam.fmt == SigFormat.HDMI_1920x1080I_50Hz_A.ordinal()
            || gParam.fmt == SigFormat.HDMI_1440x576I_50Hz.ordinal()
            || gParam.fmt == SigFormat.HDMI_2880x576I_50Hz.ordinal()
            || gParam.fmt == SigFormat.HDMI_1920x1080I_50Hz_B.ordinal()
            || gParam.fmt == SigFormat.HDMI_1920x1080I_100Hz.ordinal()
            || gParam.fmt == SigFormat.HDMI_1440x576I_100Hz.ordinal()
            || gParam.fmt == SigFormat.HDMI_1920x1080I_120Hz.ordinal() // 130
            || gParam.fmt == SigFormat.HDMI_1440x480I_120Hz.ordinal()
            || gParam.fmt == SigFormat.HDMI_1440x576I_200Hz.ordinal()
            || gParam.fmt == SigFormat.HDMI_1440x480I_240Hz.ordinal()
            || gParam.fmt == SigFormat.HDMI_1920x1080I_60Hz_FRAME_PACKING.ordinal()
            || gParam.fmt == SigFormat.HDMI_1920x1080I_50Hz_FRAME_PACKING.ordinal()
            || gParam.fmt == SigFormat.CVBS_NTSC_M.ordinal()
            || gParam.fmt == SigFormat.CVBS_NTSC_443.ordinal()
            || gParam.fmt == SigFormat.CVBS_PAL_I.ordinal()
            || gParam.fmt == SigFormat.CVBS_PAL_M.ordinal()
            || gParam.fmt == SigFormat.CVBS_PAL_60.ordinal()
            || gParam.fmt == SigFormat.CVBS_PAL_CN.ordinal()
            || gParam.fmt == SigFormat.CVBS_SECAM.ordinal()) {
            Log.d("TV JAVA VDIN", "Interlace format.");
            return true;
        } else {
            Log.d("TV JAVA VDIN", "Progressive format.");
            return false;
        }
    }

    public static boolean is50HzFrameRateFmt() {
        /** component **/
        if (gParam.fmt == SigFormat.COMP_576P_50D000.ordinal()
            || gParam.fmt == SigFormat.COMP_576I_50D000.ordinal()
            || gParam.fmt == SigFormat.COMP_720P_50D000.ordinal()
            || gParam.fmt == SigFormat.COMP_1080P_50D000.ordinal()
            || gParam.fmt == SigFormat.COMP_1080I_50D000_A.ordinal()
            || gParam.fmt == SigFormat.COMP_1080I_50D000_B.ordinal()
            || gParam.fmt == SigFormat.COMP_1080I_50D000_C.ordinal()
            || gParam.fmt == SigFormat.COMP_1080I_50D000_A.ordinal()
            /** hdmi **/
            || gParam.fmt == SigFormat.HDMI_720x576P_50Hz.ordinal()
            || gParam.fmt == SigFormat.HDMI_1280x720P_50Hz.ordinal()
            || gParam.fmt == SigFormat.HDMI_1920x1080I_50Hz_A.ordinal()
            || gParam.fmt == SigFormat.HDMI_1440x576I_50Hz.ordinal()
            || gParam.fmt == SigFormat.HDMI_1440x288P_50Hz.ordinal()
            || gParam.fmt == SigFormat.HDMI_2880x576I_50Hz.ordinal()
            || gParam.fmt == SigFormat.HDMI_2880x288P_50Hz.ordinal()
            || gParam.fmt == SigFormat.HDMI_1440x576P_50Hz.ordinal()
            || gParam.fmt == SigFormat.HDMI_1920x1080P_50Hz.ordinal()
            || gParam.fmt == SigFormat.HDMI_1920x1080I_50Hz_B.ordinal()
            || gParam.fmt == SigFormat.HDMI_1280x720P_50Hz_FRAME_PACKING.ordinal()
            || gParam.fmt == SigFormat.HDMI_1920x1080I_50Hz_FRAME_PACKING.ordinal()
            /** cvbs **/
            || gParam.fmt == SigFormat.CVBS_PAL_I.ordinal()
            || gParam.fmt == SigFormat.CVBS_PAL_M.ordinal()
            || gParam.fmt == SigFormat.CVBS_PAL_CN.ordinal()
            || gParam.fmt == SigFormat.CVBS_SECAM.ordinal()) {
            Log.d("TV JAVA VDIN", "Frame rate == 50Hz");
            return true;
        } else {
            Log.d("TV JAVA VDIN", "Frame rate != 50Hz");
            return false;
        }
    }

    public static int isVgaFmtInHdmi() {
        SrcType srctype = GetSrcType();
        int fmt = GetSigFormat();

        if (srctype == SrcType.HDMI) {
            if (fmt == SigFormat.HDMI_640x480P_60Hz.ordinal()
                || fmt == SigFormat.HDMI_800x600.ordinal()
                || fmt == SigFormat.HDMI_1024x768.ordinal()
                || fmt == SigFormat.HDMI_720_400.ordinal()
                || fmt == SigFormat.HDMI_1280_768.ordinal()
                || fmt == SigFormat.HDMI_1280_800.ordinal()
                || fmt == SigFormat.HDMI_1280_960.ordinal()
                || fmt == SigFormat.HDMI_1280_1024.ordinal()
                || fmt == SigFormat.HDMI_1360_768.ordinal()
                || fmt == SigFormat.HDMI_1366_768.ordinal()
                || fmt == SigFormat.HDMI_1600_1200.ordinal()
                || fmt == SigFormat.HDMI_1920_1200.ordinal()) {
                Log.d(DBTAG, "HDMI source : VGA format!");
                return 1;
            } else {
                Log.d(DBTAG, "HDMI source : not VGA format");
                return -1;
            }
        }

        return -1;
    }

    //
    public static void SetViewMode(int mode) {
        vppJNI.SetScaleParam(0, 0, 0, 0);
        vppJNI.SetDisplayMode(1);
        SetPpmgrMode(mode);
    }

    // 0- normal mode
    // 1- full strech: in video play/3D mode have to set this mode
    // 2- 16:9
    // 3- movie mode
    public static void SetScreenMode(int mode) {
        vppJNI.SetDisplayMode(mode);
    }

    public static void SetDisplayModeFor3D(int mode3D_on_off) {
        int pre_displaymode;
        pre_displaymode = eepromJNI.LoadVideoSetting(GetSrcType(), VideoFunc.ASPECT_RAT);
        if (1 == mode3D_on_off) {
            if (pre_displaymode == vppJNI.ScreenMode.MODE43.ordinal()) {
                SetPpmgrMode(1);
            } else {
                SetPpmgrMode(2);
            }
            vppJNI.SetScaleParam(0, 0, (1920 - 1), (1080 - 1));
            vppJNI.SetDisplayMode(1);
        } else {
            SetPpmgrMode(0);
            vppJNI.UiSetScrMode(pre_displaymode);
        }
    }

    public static void Set3DOvserScan(boolean isTurnOn) {
        if (isTurnOn == false) {
            Log.d("TV JAVA VDIN", "turn off Set-3D-OvserScan");
            Set3DOvserScan(0, 0);
            return;
        }

        if (Get3DStatus() == Mode3D.LR.ordinal()
            || Get3DStatus() == Mode3D.BT.ordinal()) {
            Log.d("TV JAVA VDIN", "Set-3D-OverScan in L/R or B/T mode");
            Log.d("TV JAVA VDIN", "cutTop:" + gParam.cutTop + " cutLeft:"
                + gParam.cutLeft);
            Set3DOvserScan(gParam.cutTop, gParam.cutLeft);
        } else {
            Log.d("TV JAVA VDIN", "turn off Set-3D-OvserScan");
            Set3DOvserScan(0, 0);
        }
    }

    public static void Set3DMode(int mode) {
        int pre_mode = gParam.mode3D;
        int pre_mvc_mode = 0;
        if (pre_mode == mode || GetSrcType() == SrcType.VGA) {
            Log.d("TV JAVA VDIN", "Set3DMode faild, pre_mode:" + pre_mode
                + ", mode:" + mode + ", SrcType:" + GetSrcType());
            return;
        }

        if (mode != Mode3D.FIELD_DEPTH.ordinal())
            gParam.mode3D = mode;

        if ((mode == Mode3D.DISABLE.ordinal())
            || (mode == Mode3D.AUTO.ordinal())
            || (mode == Mode3D.MODE_2D_TO_3D.ordinal())
            || (mode == Mode3D.LR.ordinal()) || (mode == Mode3D.BT.ordinal())) {
            gParam.status3D = gParam.mode3D;
        }

        if (mode == Mode3D.DISABLE.ordinal()) { // 3D -> normal
            Log.d("TV JAVA VDIN", "3D->Normal, 3D->Tvin path");
            if (GetSrcType() == SrcType.MPEG) {
                ;
            } else {
                AudioCustom.AudioAmplifierMuteOn();
                AudioCustom.SetAudioLineOutMute(true);

                TurnOnBlueScreen(2);
                StopSigDetectThr();
                DelayMs(10);
                StopDecoder(0);
                DelayMs(20);
                RemoveTvinPath(1);
                Set2Dto3D(0);
                if (vdinJNI.GetCurSource() == eepromJNI.SourceInput.TV.ordinal())
                    vdinJNI.SetDIBuffMgrMode(1);
                AddTvinPath(TvPath.VDIN_DEINTERLACE_AMVIDEO.ordinal());
                SetDisplayModeFor3D(0);
                StartSigDetectThr();
            }
        } else if (pre_mode == Mode3D.DISABLE.ordinal()) { // normal -> 3D
            Log.d("TV JAVA VDIN", "Normal->3D, Tvin path->3D path");
            if (GetSrcType() == SrcType.MPEG) {
                ;
            } else {
                AudioCustom.AudioAmplifierMuteOn();
                AudioCustom.SetAudioLineOutMute(true);

                TurnOnBlueScreen(2);
                StopSigDetectThr();
                vdinJNI.SetDIBuffMgrMode(0);
                DelayMs(10);
                StopDecoder(0);
                DelayMs(20);
                RemoveTvinPath(1);
                Set2Dto3D(1);
                AddTvinPath(TvPath.VDIN_3D_AMVIDEO.ordinal());
                SetDisplayModeFor3D(1);
                StartSigDetectThr();
            }
        } else if (pre_mode == Mode3D.AUTO.ordinal()) {
            if (mode == Mode3D.MODE_2D_TO_3D.ordinal()
                || mode == Mode3D.LR.ordinal() || mode == Mode3D.BT.ordinal()) {
                if (GetSrcType() == SrcType.MPEG) {
                    ;
                } else {
                    if (gParam.TvinPathId != TvPath.VDIN_3D_AMVIDEO.ordinal()) {
                        AudioCustom.AudioAmplifierMuteOn();
                        AudioCustom.SetAudioLineOutMute(true);

                        TurnOnBlueScreen(2);
                        StopSigDetectThr();
                        DelayMs(10);
                        StopDecoder(0);
                        DelayMs(20);
                        RemoveTvinPath(1);
                        Set2Dto3D(1);
                        AddTvinPath(TvPath.VDIN_3D_AMVIDEO.ordinal());
                        SetDisplayModeFor3D(1);
                        StartSigDetectThr();
                        Log.d("TV JAVA VDIN", "VDIN Normal AUTO->2D_3D/LR/BT, Tvin path->3D path");
                    } else {
                        Log.d("TV JAVA VDIN", "VDIN Normal AUTO->2D_3D/LR/BT, It's already 3D path");
                    }
                }
            }
        } else if (pre_mode == Mode3D.MODE_2D_TO_3D.ordinal()) {
            if (mode == Mode3D.LR.ordinal() || mode == Mode3D.BT.ordinal()) {
                if (GetSrcType() == SrcType.MPEG) {
                    ;
                } else {
                    /** in order to re-set cut window **/
                    StopSigDetectThr();
                    DelayMs(10);
                    StopDecoder(0);
                    DelayMs(20);
                    StartSigDetectThr();
                }
            }
        } else if (pre_mode == Mode3D.LR.ordinal()
            || pre_mode == Mode3D.BT.ordinal()) {
            if (mode == Mode3D.MODE_2D_TO_3D.ordinal()) {
                if (GetSrcType() == SrcType.MPEG) {
                    ;
                } else {
                    /** in order to re-set cut window **/
                    StopSigDetectThr();
                    DelayMs(10);
                    StopDecoder(0);
                    DelayMs(20);
                    StartSigDetectThr();
                }
            }
        }

        int val = eepromJNI.LoadVideoSetting(GetSrcType(), eepromJNI.VideoFunc.SHARPNESS);
        vppJNI.UiSetSharpness(true, val);

        Send3DCommand(mode);

        // delay the frequency of key press and let the thread of signal detect
        // started to run.
        DelayMs(500);
    }

    public static int DepthMapTbl[] = {
        -64, // -16
        -60, // -15
        -56, // -14
        -52, // -13
        -49, // -12
        -46, // -11
        -43, // -10
        -40, // -09
        -37, // -08
        -34, // -07
        -31, // -06
        -28, // -05
        -25, // -04
        -22, // -03
        -19, // -02
        -16, // -01
        -13, // 0
        3, // 1
        6, // 2
        9, // 3
        12, // 4
        15, // 5
        18, // 6
        21, // 7
        24, // 8
        28, // 9
        32, // 10
        36, // 11
        40, // 12
        44, // 13
        48, // 14
        52, // 15
        56, // 16
    };

    public static void SetDepthOf2Dto3D(int uiValue) { // -16~16
        int tmpValue = DepthMapTbl[uiValue + 16];
        SetDepthOfField(tmpValue * 2);
    }

    public static void CloseSrcPort() {
        ClosePort(0);
    }

    public static void OpenSrcPort(SourceInput srcinput) {
        switch (srcinput) {
            case TV:
                OpenPort(0, SrcId.CVBS0.toInt());
                break;
            case AV1:
                OpenPort(0, SrcId.CVBS1.toInt());
                break;
            case YPBPR1:
                OpenPort(0, SrcId.COMP0.toInt());
                break;
            case HDMI1:
                OpenPort(0, SrcId.HDMI0.toInt());
                break;
            case HDMI2:
                OpenPort(0, SrcId.HDMI1.toInt());
                break;
            case HDMI3:
                OpenPort(0, SrcId.HDMI2.toInt());
                break;
            case VGA:
                OpenPort(0, SrcId.VGA0.toInt());
                break;
        }
    }

    public static void OpenSrcId(int srcId) {
        OpenPort(0, srcId);
    }

    /***************************************************
     *  
    ***************************************************/
    public static void DelayMs(int ms) {
        try {
            TimeUnit.MILLISECONDS.sleep(ms);
        } catch (InterruptedException e) {
            Log.d("TV JAVA VDIN", "delay error!!");
            e.printStackTrace();
        }
    }

    private static void SendCommand(String[] cmd) {
        try {
            Process child = Runtime.getRuntime().exec(cmd);

            String line = null;

            BufferedReader reader = new BufferedReader(new InputStreamReader(child.getInputStream()));
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*********************************************************************
     * UI Relate Function
     ********************************************************************/
    public static void setSignalDetectHandler(Handler tmpHandler) {
        menuHandler = tmpHandler;
    }

    // for delay
    public static boolean channelchange = false;

    public static void sendMessageDelayToUI(int MsgType, int value,
        int delaytime) {
        if (!channelchange) {
            if (menuHandler != null) {
                Message msg = menuHandler.obtainMessage();
                if (msg != null) {
                    msg.arg1 = MsgType;
                    msg.what = value;
                    if (!menuHandler.hasMessages(msg.what))
                        menuHandler.sendMessageDelayed(msg, delaytime);
                    msg = null;
                }
            }
        }
    }

    public static void sendMessageToUI(int MsgType) {
        if (menuHandler != null) {
            Message msg = menuHandler.obtainMessage();
            if (msg != null) {
                msg.arg1 = MsgType;
                menuHandler.sendMessage(msg);
                msg = null;
            }
        }
    }

    /***************************************************
     * load liberary & JNI methods
     ***************************************************/
    static {
        System.loadLibrary("vdin_api");
    }

    static native int IsVDINModuleOpen(int windowSel);

    static native int OpenVDINModule(int windowSel);

    static native int CloseVDINModule(int windowSel);

    static native int GetSignalInfo(int windowSel, TvinParam tvinSigParam);

    public static native int GetHistgram(int histgram_buf[]);

    static native int StopDec(int windowSel);

    static native int StartDec(int windowSel, TvinParam tvinSinParam);

    static native int OpenPort(int windowSel, long sourceId);

    static native int ClosePort(int windowSel);

    static native int OpenPPMGRModule();

    static native int ClosePPMGRModule();

    static native int Send3DCommand(int commd);

    static native int AddTvPath(int selPath);

    static native int RmTvPath();

    static native int RmDefPath();

    static native int SetDisplayVFreq(int freq);

    static native int SetDepthOfField(int halfPixelCount);

    static native int Set2Dto3D(int on_off);

    static native int SetDIBuffMgrMode(int mgr_mode);

    static native int SetDICFG(int cfg);

    static native int SetPpmgrMode(int mode);

    static native int SetMVCMode(int mode);

    static native int GetMVCMode();

    static native int GetVscalerStatus();

    static native int Set3DOvserScan(int top, int left);

    static native int TurnOnBlackBarDetect(int isEnable);
    /** 1->Enable 0->Disable **/
}

class SourceSwitchThread implements Runnable {
    private static int window_sel;
    private static int source_id;
    private static int audio_channel;

    SourceSwitchThread() {
        vdinJNI.gParam.isSrcSwitchExecDone = true;
    }

    public static void SetWindowSel(int win_sel) {
        window_sel = win_sel;
    }

    public static void SetSrcId(int src_id) {
        source_id = src_id;
    }

    public static void SetAudioChannel(int audio_ch) {
        audio_channel = audio_ch;
    }

    public static boolean isSourceSwitchExecuteDone() {
        return vdinJNI.gParam.isSrcSwitchExecDone;
    }

    private static void ThreadDelayMS(int ms) {
        try {
            TimeUnit.MILLISECONDS.sleep(ms);
        } catch (InterruptedException e) {
            Log.d("TV JAVA VDIN", "delay error!!");
            e.printStackTrace();
        }
    }

    public static void RealSwitchSource(int window_sel, int source_id,
        int audio_channel) {
        int prePort = vdinJNI.gParam.port;
        int curPort = source_id;
        int ret = 0;

        if (prePort == curPort && tvservice.setdefault == false)
            return;

        vdinJNI.gParam.isSrcSwitchExecDone = false;

        Log.d("TV JAVA VDIN", "SourceSwitchThread, StopSigDetectThr.");
        vdinJNI.StopSigDetectThr();

        AudioCustom.AudioAmplifierMuteOn();
        AudioCustom.SetAudioLineOutMute(true);

        if (eepromJNI.IsBurnFlagOn() == false) {
            vdinJNI.TurnOnBlueScreen(2);
        }

        if (prePort == vdinJNI.SrcId.CVBS0.toInt()) {
            tunerJNI.KillAutoAFCThr();
            tunerJNI.muteTunerAudio();
        } else if (prePort == vdinJNI.SrcId.NULL.toInt()
            && curPort != vdinJNI.SrcId.CVBS0.toInt()) {
            Log.d("TV JAVA VDIN", "First power on, when source is not TV, mute TunerAudio");
            tunerJNI.muteTunerAudio();
        }

        Log.d("TV JAVA VDIN", "SourceSwitchThread, source switch start...");
        vdinJNI.SetDIBuffMgrMode(0);

        // not first call SourceSwitch() function
        if (prePort != vdinJNI.SrcId.NULL.toInt()) {
            Log.d("TV JAVA VDIN", "SourceSwitchThread, Stop decoder!");
            ret = vdinJNI.StopDecoder(window_sel);
            if (0 == ret) {
                vdinJNI.ClosePort(window_sel);
            } else if (1 == ret) {
                if (prePort == vdinJNI.SrcId.CVBS0.toInt()) {
                    vdinJNI.SetDIBuffMgrMode(1);
                    vdinJNI.SetDICFG(0);
                    vdinJNI.SetDIBuffMgrMode(0);
                    Log.w("TV JAVA VDIN", "SourceSwitchThread, force to disable DI.");
                }
                vdinJNI.ClosePort(window_sel);
            } else {
                Log.w("TV JAVA VDIN", "SourceSwitchThread, stop decoder failed.");
                vdinJNI.gParam.isSrcSwitchExecDone = true;
                return;
            }
        }

        vdinJNI.sendMessageDelayToUI(GlobalConst.MSG_CHANGE_CHANNEL, 0, 6000);
        vdinJNI.channelchange = true;
        if (vdinJNI.menuHandler != null) {
            if (vdinJNI.menuHandler.hasMessages(GlobalConst.IS_NO_SIGNAL))
                vdinJNI.menuHandler.removeMessages(GlobalConst.IS_NO_SIGNAL);
        }
        vdinJNI.sendMessageToUI(GlobalConst.MSG_SIGNAL_STABLE);

        vdinJNI.SetDepthOf2Dto3D(0); // set default depth
        vdinJNI.Set2Dto3D(0);
        vdinJNI.RemoveTvinPath(1);
        vdinJNI.AddTvinPath(vdinJNI.TvPath.VDIN_DEINTERLACE_AMVIDEO.ordinal());

        // current usage:only one window
        vdinJNI.gParam.inputWindow = window_sel;
        vdinJNI.gParam.port = curPort;

        if (curPort >= vdinJNI.SrcId.HDMI0.toInt()
            && curPort <= vdinJNI.SrcId.HDMI7.toInt()) {
            // hdmi_input

            // Stop Dsp
            AudioCtlJNI.mAudioDspStop();

            // Uninit Alsa
            AudioCtlJNI.mAlsaUninit(0);

            // Stop HDMI in
            AudioCtlJNI.AudioUtilsStopHDMIIn();

            // DSP Start
            AudioCustom.AudioDspStart();

            // Open Port
            if (vdinJNI.OpenPort(window_sel, source_id) < 0) {
                Log.e("TV JAVA VDIN", "SourceSwitchThread, OpenPort failed, window_sel : "
                    + window_sel + ", source_id : +" + source_id);
            }

            Log.d("TV JAVA VDIN", "SourceSwitchThread, Delay 500ms start.");
            ThreadDelayMS(500);
            Log.d("TV JAVA VDIN", "SourceSwitchThread, Delay 500ms end.");

            // Alsa Init
            AudioCtlJNI.mAlsaInit(0);

            // Start HDMI In
            AudioCtlJNI.AudioUtilsStartHDMIIn();
        } else if (curPort >= vdinJNI.SrcId.COMP0.toInt()
            && curPort <= vdinJNI.SrcId.SVIDEO7.toInt()) {
            // line-in_type:YPbPr,AV,S-Video

            // Stop Dsp
            AudioCtlJNI.mAudioDspStop();

            // Uninit Alsa
            AudioCtlJNI.mAlsaUninit(0);

            // Stop Line in
            AudioCtlJNI.AudioUtilsStopLineIn();

            // Select Channel
            AudioCtlJNI.LineInSelectChannel(audio_channel);

            // Open Port
            if (vdinJNI.OpenPort(window_sel, source_id) < 0) {
                Log.e("TV JAVA VDIN", "SourceSwitchThread, OpenPort failed, window_sel : "
                    + window_sel + ", source_id : +" + source_id);
            }

            Log.d("TV JAVA VDIN", "SourceSwitchThread, Delay 500ms start.");
            ThreadDelayMS(500);
            Log.d("TV JAVA VDIN", "SourceSwitchThread, Delay 500ms end.");

            if (curPort == vdinJNI.SrcId.CVBS0.toInt()) {
                tunerJNI.CreateAutoAFCThr();
                if (eepromJNI.IsBurnFlagOn() == true) {
                    tunerJNI.factoryBurnMode(true);
                } else {
                    tunerJNI.num = eepromJNI.LoadTvCurrentChannel();
                    tunerJNI.setTunerLastChannel();
                    tunerJNI.manualSetFreq(tunerJNI.num);
                }
            } else if (curPort == vdinJNI.SrcId.CVBS1.toInt()) {
                vdinJNI.SetAvColorSys(eepromJNI.LoadAvColorSys());
                afeJNI.SetCVBSStd(vdinJNI.GetAvColorSys());
            }

            // Alsa Init
            AudioCtlJNI.mAlsaInit(0);

            // Start Line In
            AudioCtlJNI.AudioUtilsStartLineIn();

            // DSP Start
            AudioCustom.AudioDspStart();
        } else if (curPort >= vdinJNI.SrcId.VGA0.toInt()
            && curPort <= vdinJNI.SrcId.VGA7.toInt()) {

            // Stop Dsp
            AudioCtlJNI.mAudioDspStop();

            // Uninit Alsa
            AudioCtlJNI.mAlsaUninit(0);

            // Stop Line in
            AudioCtlJNI.AudioUtilsStopLineIn();

            // Select Channel
            AudioCtlJNI.LineInSelectChannel(audio_channel);

            // Open Port
            if (vdinJNI.OpenPort(window_sel, source_id) < 0) {
                Log.e("TV JAVA VDIN", "SourceSwitchThread, OpenPort failed, window_sel : "
                    + window_sel + ", source_id : +" + source_id);
            }

            afeJNI.SetVgaEdidData();

            Log.d("TV JAVA VDIN", "SourceSwitchThread, Delay 500ms start.");
            ThreadDelayMS(500);
            Log.d("TV JAVA VDIN", "SourceSwitchThread, Delay 500ms end.");

            // Alsa Init
            AudioCtlJNI.mAlsaInit(0);

            // Start Line In
            AudioCtlJNI.AudioUtilsStartLineIn();

            // DSP Start
            AudioCustom.AudioDspStart();
        }

        if (curPort == vdinJNI.SrcId.CVBS0.toInt()) {
            AudioCtlJNI.SetLineInCaptureVolume(63, 0, 84, 63, 0, 84);
            AudioCustom.SetVolumeDigitLUTBuf(AudioCustom.CC_LUT_SEL_TV, 1);
        } else {
            AudioCtlJNI.SetLineInCaptureVolume(64, 0, 84, 64, 0, 84);
            AudioCustom.SetVolumeDigitLUTBuf(AudioCustom.CC_LUT_SEL_AV, 1);
        }

        if (curPort == vdinJNI.SrcId.CVBS0.toInt()) {
            vdinJNI.SetDIBuffMgrMode(1);
            vdinJNI.SetCurSource(eepromJNI.SourceInput.TV.ordinal());
            Log.d("TV JAVA VDIN", "SourceSwitchThread, SetCurSource TV.");
        } else if (curPort == vdinJNI.SrcId.CVBS1.toInt()) {
            vdinJNI.SetCurSource(eepromJNI.SourceInput.AV1.ordinal());
            Log.d("TV JAVA VDIN", "SourceSwitchThread, SetCurSource AV.");
        } else if (curPort == vdinJNI.SrcId.COMP0.toInt()) {
            vdinJNI.SetCurSource(eepromJNI.SourceInput.YPBPR1.ordinal());
            Log.d("TV JAVA VDIN", "SourceSwitchThread, SetCurSource YPBPR.");
        } else if (curPort == vdinJNI.SrcId.HDMI0.toInt()) {
            vdinJNI.SetCurSource(eepromJNI.SourceInput.HDMI1.ordinal());
            Log.d("TV JAVA VDIN", "SourceSwitchThread, SetCurSource HDMI1.");
        } else if (curPort == vdinJNI.SrcId.HDMI1.toInt()) {
            vdinJNI.SetCurSource(eepromJNI.SourceInput.HDMI2.ordinal());
            Log.d("TV JAVA VDIN", "SourceSwitchThread, SetCurSource HDMI2.");
        } else if (curPort == vdinJNI.SrcId.HDMI2.toInt()) {
            vdinJNI.SetCurSource(eepromJNI.SourceInput.HDMI3.ordinal());
            Log.d("TV JAVA VDIN", "SourceSwitchThread, SetCurSource HDMI3.");
        } else if (curPort == vdinJNI.SrcId.VGA0.toInt()) {
            vdinJNI.SetCurSource(eepromJNI.SourceInput.VGA.ordinal());
            Log.d("TV JAVA VDIN", "SourceSwitchThread, SetCurSource VGA.");
        } else if (curPort == vdinJNI.SrcId.MPEG0.toInt()) {
            Log.d("TV JAVA VDIN", "SourceSwitchThread, SetCurSource MPEG.");
        }

        eepromJNI.SaveSourceInput(0, vdinJNI.GetCurSource());

        osdJNI.SetOSDColorkey(0, 1, 0);

        vppJNI.LoadVppSetting(vdinJNI.GetSrcType());

        if (curPort == vdinJNI.SrcId.CVBS0.toInt())
            vppJNI.UiSetDNLP(1, 0, 2, 3, 1);
        else
            vppJNI.UiSetDNLP(1, 0, 2, 3, 2);

        vdinJNI.sendMessageToUI(GlobalConst.MSG_VDINJNI_DISPLAY_SOURCE_INFO);

        if (!eepromJNI.IsBurnFlagOn()) {
            vdinJNI.StartSigDetectThr();
        }
    }

    public void run() {
        if (isSourceSwitchExecuteDone() == true) {
            if (vdinJNI.isDetectThrDone() == true) {
                RealSwitchSource(window_sel, source_id, audio_channel);
            } else {
                Log.w("TV JAVA VDIN", "SourceSwitchThread, isDetectThrDone == false, source swith failed.");
            }
        } else {
            Log.w("TV JAVA VDIN", "SourceSwitchThread, isSourceSwitchExecuteDone == false, source swith failed.");
        }
    }
}
