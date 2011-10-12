package com.skyworth.SkyworthMenu;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class SearchDrawable {
    private static AssetManager assets;
    private static Bitmap btp = null;
    //private static ArrayList<HashMap<String, Bitmap>> BitmapList = new ArrayList<HashMap<String, Bitmap>>();
    private static HashMap<String, Bitmap> BitmapList = new HashMap<String, Bitmap>();
    private static Context friendContext;
    public static void InitSearchDrawable(Context context) {
        try {
        	if(friendContext == null)
        		friendContext = context.createPackageContext("com.amlogic.ui.res", Context.CONTEXT_IGNORE_SECURITY);
        	if(assets == null)
        		assets = friendContext.getAssets();
        } catch (NameNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    public static Bitmap getBitmap(String str) {
    	if(!BitmapList.isEmpty()){
	    	if(BitmapList.containsKey(str)){
	    		//Log.d("SearchDrawable", "BitmapList.containsKey........."+str);
	    		if(BitmapList.get(str) != null && !BitmapList.get(str).isRecycled())
	    			return BitmapList.get(str);
	    		else
	    		{
	    			Log.e("SearchDrawable", "BitmapList.get(str) error........."+str);
	    		}
	    	}
    	}
        try {
            InputStream is = assets.open(str + ".png");
            btp = BitmapFactory.decodeStream(is);
            is.close();
            BitmapList.put(str, btp);
            //Log.d("SearchDrawable", "BitmapList.put str btp........."+str);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if(btp.isRecycled()){
        	Log.e("SearchDrawable", "btp.isRecycled()........."+str);
        	//getBitmap(str);
        }
        return btp;
    }

    // public InputStream DrawableID(String id,boolean focus){
    // if(focus == true)
    // return getBitmap(id+"sel");
    // else
    // return getBitmap(id+"unsel");
    // }

}
