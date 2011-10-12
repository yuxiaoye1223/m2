package test.TestMenu.mtwo;




import com.skyworth.control.tvsetting;

import test.TestMenu.R;
import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BarcodeView extends LinearLayout {
	private View mview;
	private TextView barView,macView;
	private final static int MAC_LEN = 6;
	public BarcodeView(Context context) {
		super(context);
		LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(900,400);
		this.setLayoutParams(params);
		LayoutInflater inflater = LayoutInflater.from(this.getContext());
		mview =inflater.inflate(R.layout.barcode, null);
		this.addView(mview);
		this.setFocusable(true);
		
		barView=(TextView) mview.findViewById(R.id.barcodeContent);
		String temps = tvsetting.LoadBarCode();
//		char [] check = temps.toCharArray();
//		for(int i = 0;i<check.length;i++)
//		{
//			if( check[i] != '-'&&!(check[i]>='A'&&check[i]<='Z')&&
//					!(check[i]>='a'&&check[i]<='z')&&!(check[i]>='0'&&check[i]<='9'))
//				check[i] = ' ';
//		}
//		temps = new String(check);
		barView.setText(temps);
		macView=(TextView) mview.findViewById(R.id.macContent);
		int macaddr[] = new int[MAC_LEN];
		macaddr = tvsetting.GetMacAddr();
		StringBuffer s=new StringBuffer();
		for(int j=0;j<MAC_LEN;j++)
		{
			if(macaddr[j]!=0)
			{
				if(j!=MAC_LEN -1)
					temps = Integer.toHexString(macaddr[j])+":";
				else 
					temps = Integer.toHexString(macaddr[j]);
				s.append(temps.toUpperCase());
			}
			else
				s.append("00:");
		}
		macView.setText(s);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch(keyCode)
		{
			case KeyEvent.KEYCODE_DPAD_LEFT:
			case 115:	//vol-
				return true;				
		}
		return super.onKeyDown(keyCode, event);
	}
}
