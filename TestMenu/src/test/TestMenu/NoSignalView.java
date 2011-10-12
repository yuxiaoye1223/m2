package test.TestMenu;

import com.skyworth.SkyworthMenu.GlobalConst;
import com.skyworth.SkyworthMenu.Menucontrol;
import com.skyworth.View.ChannelInfoView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.widget.AbsoluteLayout;

public class NoSignalView extends View {
    private Bitmap bgbmp = null;
    private String chInfo = null;
    private String signalInfo = null;
    private Paint pa = new Paint();
    private int offsetx = 192;
    private int offsety = 108;
    private String str_lan;
    private boolean nosignal = true;

    public NoSignalView(Context context, int curSource) {
        super(context);
        bgbmp = BitmapFactory
            .decodeResource(this.getResources(), R.drawable.bg);
        bgbmp = Bitmap.createScaledBitmap(bgbmp, 450, 300, true);
        str_lan = context.getResources().getConfiguration().locale
            .getLanguage();
        setShowSourceData(curSource);
        this.setFocusable(false);
        pa.setColor(Color.WHITE);
        pa.setTextSize(50);
        pa.setTextAlign(Paint.Align.CENTER);
        pa.setAntiAlias(true);
    }

    public void setNosignalFlag(boolean nosig) {
        nosignal = nosig;
        if (nosignal) {
            if (str_lan.equals("en")) {
                signalInfo = this.getContext().getString(R.string.nosignal_en);
            } else {
                signalInfo = this.getContext().getString(R.string.nosignal_ch);
            }
        } else {
            if (str_lan.equals("en")) {
                signalInfo = this.getContext()
                    .getString(R.string.notsupport_en);
            } else {
                signalInfo = this.getContext()
                    .getString(R.string.notsupport_ch);
            }
        }
    }

    public void UpdatePosition() {
        offsetx += 192 + 50;
        offsety += 108 + 50;
        if (offsetx + 50 > 1920 || offsety + 200 > 1080) {
            offsetx = 192;
            offsety = 108;
        }
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int posY = offsety;
        int posX = offsetx;
        if (str_lan.equals("en")) {
            posX = offsetx - 100;
        }
        canvas.drawBitmap(bgbmp, this.getPaddingLeft() + posX,
            this.getPaddingTop() + posY, null);
        pa.setTextSize(50);
        canvas.drawText(chInfo, this.getPaddingLeft() + posX + 225,
            this.getPaddingTop() + posY + 80, pa);
        
        if ((nosignal == false) && (str_lan.equals("en")))
            pa.setTextSize(60);
            else
            pa.setTextSize(80);
        canvas.drawText(signalInfo, this.getPaddingLeft() + posX + 225,
            this.getPaddingTop() + posY + 180, pa);
    }

    private void setShowSourceData(int curSource) {
        switch (curSource) {
            case GlobalConst.TV_SOURCE:
                chInfo = Menucontrol.getResXmlString("tv_source_name");
                break;

            case GlobalConst.AV1_SOURCE:
                chInfo = Menucontrol.getResXmlString("av1_source_name");
                break;

            case GlobalConst.YPBPR1_SOURCE:
                chInfo = Menucontrol.getResXmlString("ypbpr1_source_name");
                break;

            case GlobalConst.HDMI1_SOURCE:
                chInfo = Menucontrol.getResXmlString("hdmi1_source_name");
                break;

            case GlobalConst.HDMI2_SOURCE:
                chInfo = Menucontrol.getResXmlString("hdmi2_source_name");
                break;

            case GlobalConst.HDMI3_SOURCE:
                chInfo = Menucontrol.getResXmlString("hdmi3_source_name");
                break;

            case GlobalConst.VGA_SOURCE:
                chInfo = Menucontrol.getResXmlString("vga_source_name");
                break;

            default:
                break;
        }
    }
}
