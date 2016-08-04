package com.inkstudio.paint.common;


import com.inkstudio.paint.util.Tool;

import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.util.Log;
/**
 * 画笔构造类
 * @author SUCHU
 *
 */
public class Brush extends Paint{
	public static Paint paint = null;
	public static int BRUSH_SIZE = 4;
	  public static MaskFilter BLUR;
	  public static MaskFilter EMBOSS;
	  private static Brush brush = new Brush();
	  private static Brush brush2 = new Brush();
	  private static MaskFilter mBlur;
	public static Paint getBrush(int width,Colors color){
		paint= new Paint(Paint.DITHER_FLAG);
		paint.setStrokeWidth(width);
		paint.setColor(Color.argb(color.a,color.r,color.g,color.b));
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeJoin(Paint.Join.ROUND);
		paint.setStrokeCap(Paint.Cap.ROUND);
		paint.setTextSize(25);
		
		paint.setAntiAlias(true);
		paint.setDither(true);
		 paint.setMaskFilter(null);
		 BLUR = new BlurMaskFilter(6.0F + BRUSH_SIZE, BlurMaskFilter.Blur.NORMAL);
		    EMBOSS = new EmbossMaskFilter(new float[] { 1.0F, 1.0F, 1.0F }, 0.4F, 6.0F, 3.5F);
		
		return paint;
	}
	
	public static Paint getBrush2(int width,Colors color){
		paint= new Paint(Paint.DITHER_FLAG);
		paint.setStrokeWidth(width);
		paint.setColor(Color.argb(color.a,color.r,color.g,color.b));
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeJoin(Paint.Join.ROUND);
		paint.setStrokeCap(Paint.Cap.ROUND);
		paint.setTextSize(Tool.dp2px(20));
		paint.setStyle(Paint.Style.FILL);
		paint.setAntiAlias(true);
		paint.setDither(true);
		 paint.setMaskFilter(null);
		 BLUR = new BlurMaskFilter(6.0F + BRUSH_SIZE, BlurMaskFilter.Blur.NORMAL);
		    EMBOSS = new EmbossMaskFilter(new float[] { 1.0F, 1.0F, 1.0F }, 0.4F, 6.0F, 3.5F);
		
		return paint;
	}
	
	public static Paint getBrush(){
		paint= new Paint(Paint.DITHER_FLAG);
		paint.setStrokeWidth(BRUSH_SIZE);
		paint.setColor(Color.BLACK);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeJoin(Paint.Join.ROUND);
		paint.setStrokeCap(Paint.Cap.ROUND);
		paint.setAntiAlias(true);
		paint.setDither(true);
		 paint.setMaskFilter(null);
		 BLUR = new BlurMaskFilter(6.0F + BRUSH_SIZE, BlurMaskFilter.Blur.NORMAL);
		    EMBOSS = new EmbossMaskFilter(new float[] { 1.0F, 1.0F, 1.0F }, 0.4F, 6.0F, 3.5F);
		Log.i("画笔创建", "");
		return paint;
	}
	
	public Brush() {
	}

}
