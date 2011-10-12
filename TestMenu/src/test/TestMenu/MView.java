package test.TestMenu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class MView extends View {

    private Paint pa = new Paint();

    public MView(Context context) {
        super(context);
        this.setFocusable(false);
        pa.setColor(Color.RED);
        pa.setTextSize(100);
        pa.setAntiAlias(true);
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText("M", this.getPaddingLeft() + 1650,
            this.getPaddingTop() + 200, pa);
    }

}
