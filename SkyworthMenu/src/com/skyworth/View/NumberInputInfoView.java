package com.skyworth.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.View;

import com.skyworth.Listener.OtherViewCallbackListener;
import com.skyworth.SkyworthMenu.GlobalConst;
import com.skyworth.SkyworthMenu.Menucontrol;
import com.skyworth.SkyworthMenu.SearchDrawable;

public class NumberInputInfoView extends View {
    //private SearchDrawable SearchID;
    private Bitmap bgbmp = null;
    private String mNumberStr = "";
    private static int numberLimit = GlobalConst.TWO_TOOGLE_NUMBER;
    private int needNumber = GlobalConst.TWO_TOOGLE_NUMBER;
    private static int inputNumber = 0;
    private OtherViewCallbackListener numberInputInfoListener;

    public NumberInputInfoView(Context context, int number, int displayvalue) {
        super(context);

        this.setFocusable(true);
        setNumberLimit(number);
        needNumber = numberLimit;
        setInputNumber(displayvalue);
        setShowData();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (bgbmp != null) {
            canvas.drawBitmap(bgbmp, this.getPaddingLeft(),
                this.getPaddingTop(), null);
        }

        Paint paint = new Paint();
        paint.setTextSize(40);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        canvas.drawText(mNumberStr, this.getPaddingLeft() + 120,
            this.getPaddingTop() + 50, paint);
    }

    public static int getNumberLimit() {
        return numberLimit;
    }

    private static void setNumberLimit(int number) {
        numberLimit = number;
    }

    public static int getInputNumber() {
        return inputNumber;
    }

    private static void setInputNumber(int StrId) {
        inputNumber = StrId;
    }

    private void setShowData() {
        //SearchID = new SearchDrawable(this.getContext());
    	SearchDrawable.InitSearchDrawable(mContext);
        bgbmp = SearchDrawable.getBitmap("shortcut_bg_box_1");

        setmNumberStr(inputNumber);
    }

    private void setmNumberStr(int StrId) {
        if (StrId == GlobalConst.DISPLAY_TOOGLE_INFO) {
            switch (numberLimit) {
                case GlobalConst.ONE_TOOGLE_NUMBER:
                    mNumberStr = Menucontrol
                        .getResXmlString("toogle_display_one");

                    break;

                case GlobalConst.TWO_TOOGLE_NUMBER:
                    mNumberStr = Menucontrol
                        .getResXmlString("toogle_display_two");

                    break;

                case GlobalConst.THREE_TOOGLE_NUMBER:
                    mNumberStr = Menucontrol
                        .getResXmlString("toogle_display_three");

                    break;
                default:
                    break;
            }
        } else {
            needNumber--;
            switch (numberLimit) {
                case GlobalConst.ONE_TOOGLE_NUMBER:
                    mNumberStr = String.format("%1d", StrId);
                    break;

                case GlobalConst.TWO_TOOGLE_NUMBER:
                    mNumberStr = Menucontrol
                        .getResXmlString("toogle_display_one");

                    mNumberStr += String.format("%1d", StrId);
                    break;

                case GlobalConst.THREE_TOOGLE_NUMBER:
                    mNumberStr = Menucontrol
                        .getResXmlString("toogle_display_two");

                    mNumberStr += String.format("%1d", StrId);
                    break;
                default:
                    break;
            }
        }
    }

    // Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DEL: // TOOGLE KEY -/--/---
                if (numberLimit >= GlobalConst.THREE_TOOGLE_NUMBER)
                    numberLimit = GlobalConst.ONE_TOOGLE_NUMBER;
                else
                    numberLimit++;
                needNumber = numberLimit;
                setInputNumber(GlobalConst.DISPLAY_TOOGLE_INFO);
                setmNumberStr(inputNumber);
                this.invalidate();
                break;

            case KeyEvent.KEYCODE_0: // Êý×ÖÊäÈë
            case KeyEvent.KEYCODE_1:
            case KeyEvent.KEYCODE_2:
            case KeyEvent.KEYCODE_3:
            case KeyEvent.KEYCODE_4:
            case KeyEvent.KEYCODE_5:
            case KeyEvent.KEYCODE_6:
            case KeyEvent.KEYCODE_7:
            case KeyEvent.KEYCODE_8:
            case KeyEvent.KEYCODE_9:
                int number = keyCode - 7;
                switch (needNumber) {
                    case GlobalConst.ONE_TOOGLE_NUMBER:
                        String oldNumberStr1 = mNumberStr;
                        mNumberStr = mNumberStr.substring(1,
                            mNumberStr.length());
                        mNumberStr += String.format("%1d", number);
                        number = Integer.parseInt(mNumberStr);
                        if (numberLimit == GlobalConst.THREE_TOOGLE_NUMBER) {
                            if (checkInputNumber(number) == 1) {
                                this.invalidate();
                                numberInputInfoListener
                                    .NumberInputCallback(number);
                                return true;
                            } else if (checkInputNumber(number) == 0) {
                                mNumberStr = oldNumberStr1;
                            }
                        } else {
                            this.invalidate();
                            numberInputInfoListener.NumberInputCallback(number);
                            return true;
                        }
                        break;

                    case GlobalConst.TWO_TOOGLE_NUMBER:
                        mNumberStr = mNumberStr.substring(2,
                            mNumberStr.length());
                        mNumberStr += String.format("%1d", number);
                        if (numberLimit == GlobalConst.THREE_TOOGLE_NUMBER) {
                            String oldNumberStr2 = mNumberStr;
                            mNumberStr = mNumberStr.substring(1,
                                mNumberStr.length());
                            number = Integer.parseInt(mNumberStr);
                            if (checkInputNumber(number) == 1) {
                                this.invalidate();
                                numberInputInfoListener
                                    .NumberInputCallback(number);
                                return true;
                            } else if (checkInputNumber(number) == 2) {
                                setInputNumber(number);
                                mNumberStr = oldNumberStr2;
                                needNumber--;
                                this.invalidate();
                            }
                        } else {
                            setInputNumber(number);
                            needNumber--;
                            this.invalidate();
                        }
                        break;

                    case GlobalConst.THREE_TOOGLE_NUMBER:
                        mNumberStr = mNumberStr.substring(2,
                            mNumberStr.length());
                        mNumberStr += String.format("%1d", number);
                        setInputNumber(number);
                        needNumber--;
                        this.invalidate();
                        break;

                    default:
                        break;
                }
                break;

            case KeyEvent.KEYCODE_ENTER:
                numberInputInfoListener.NumberInputCallback(inputNumber);
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    private int checkInputNumber(int number) {
        switch (numberLimit) {
            case GlobalConst.ONE_TOOGLE_NUMBER:
                break;
            case GlobalConst.TWO_TOOGLE_NUMBER:
                break;

            case GlobalConst.THREE_TOOGLE_NUMBER:
                if (needNumber == GlobalConst.ONE_TOOGLE_NUMBER) {
                    if ((number >= GlobalConst.TV_CHANNEL_TOTAL_PROGRAM_COUNT)
                        || (number < GlobalConst.TV_CHANNEL_MIN_NUMBER))
                        return 0; // not valid
                } else if (needNumber == GlobalConst.TWO_TOOGLE_NUMBER) {
                    if (number >= 26)
                        return 1; // finished
                    else
                        return 2; // need to input more.
                }
                break;

            default:
                break;
        }
        return 1; // finished
    }

    public void setOtherViewCallbackListener(OtherViewCallbackListener listener) {
        numberInputInfoListener = listener;
    }

}
