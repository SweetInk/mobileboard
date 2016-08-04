package com.inkstudio.paint.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.inkstudio.paint.appbase.BaseApplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

public class Tool {
	public static Context context = null;
	public Tool (Context context){
		this.context = context;
	}
		/**
		 *  dp转px
		 * @param value
		 * @return float
		 */
    public static float dp2px(float value) {
        final float scale = context.getResources().getDisplayMetrics().densityDpi;
        return  (value * (scale / 160) + 0.5f);
    }


    /**
     * px转dp.
     *
     * @param value the value
     * @return the float
     */
    public static float px2dp(float value) {
        final float scale = context.getResources().getDisplayMetrics().densityDpi;
        return  ((value * 160) / scale + 0.5f);
    }
    public static String getDate(){
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    	return sdf.format(new Date());
    }
    public static String getDate2(){
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
    	return sdf.format(new Date());
    }
    
    /**
     * 输出到内存卡中
     * @param View v 画布对象
     * @return
     */
    public static Bitmap createBitmap(View v){
    
    	Bitmap bm = Bitmap.createBitmap(v.getWidth(),v.getHeight(),Bitmap.Config.ARGB_8888);
    	Canvas c = new Canvas(bm);
    	v.draw(c);
    	if(v==null){
    		System.out.println("Null PaintView Object");
    	}
    	FileOutputStream fos = null;
    	String rootPath  = Environment.getExternalStorageDirectory().getPath()+"/";
    	File dir = new File(rootPath+"WhitBorad");
    	if(!dir.exists())
    		dir.mkdir();
    	String prefix = dir.getAbsolutePath()+"/"+"whiteborad-"+getDate2()+".png";
    	
   	File f = new File(prefix);
    	try {
			if(f.createNewFile())
				System.out.println("Success to create new File:"+f.getAbsolutePath());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
		
			fos = new FileOutputStream(prefix);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	bm.compress(Bitmap.CompressFormat.PNG, 90, fos);
    	Toast.makeText(BaseApplication.getInstance(), "成功保存到:"+prefix, Toast.LENGTH_SHORT).show();
    	return bm;
    	
    	
    }

}
