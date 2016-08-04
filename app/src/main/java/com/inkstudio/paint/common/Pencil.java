package com.inkstudio.paint.common;

import android.graphics.Canvas;
import android.graphics.Path;

import com.inkstudio.paint.common.IShape;
import com.inkstudio.paint.databean.ShapeBean;
import com.inkstudio.paint.util.Tool;
/**
 * Ëæ±ÊÀà(Ç¦±Ê)
 * @author SUCHU
 *
 */
public class Pencil extends IShape {

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	  private float mX;
	  private float mY;
	@Override
	public void draw(Canvas canvas) {
		canvas.drawPath(path,Brush.getBrush(this.width, this.color));
	}
	public Pencil(){
		path =  new Paths();
		tmpPath = new Paths();
		
	
	}
	@Override
	public void onTouchDown(float x, float y, Canvas canvas) {
		this.shapeBean = new ShapeBean();
		path =  new Paths();
		tmpPath = new Paths();
		this.path.reset();
		this.tmpPath.reset();
		this.path.moveTo(x, y);
		this.tmpPath.moveTo(Tool.px2dp(x),Tool.px2dp(y));
		this.mX = x;
		this.mY =y;
		this.move = false;
	}
	
	@Override
	public void onToudchMove(float x, float y, Canvas canvas) {
		
		float f1  =Math.abs(x-this.mX);
		float f2 = Math.abs(y-this.mY);
	//	path.lineTo(x, y);
		 if ((f1 >= 4.0F) || (f2 >= 4.0F))
		    {
		      this.path.quadTo(this.mX, this.mY, (x + this.mX) / 2.0F, (y + this.mY) / 2.0F);
		      this.tmpPath.quadTo(Tool.px2dp(this.mX), Tool.px2dp(this.mY), Tool.px2dp( (x + this.mX) / 2.0F), Tool.px2dp((y + this.mY) / 2.0F));
		      this.mX = x;
		      this.mY = y;
		    }
		    draw(canvas);
		    move = true;
		    decodeData();
	}
	@Override
	public void onTouchUp(float x, float y, Canvas canvas) {
		move = false;
		this.path.lineTo(this.mX, this.mY);
		this.tmpPath.lineTo(Tool.px2dp(this.mX), Tool.px2dp(this.mY));
		decodeData();
	    this.tmpPath.reset();
	    this.path.reset();
	    draw(canvas);
	}
	public void decodeData(){
		this.shapeBean.setPath(tmpPath);
		shapeBean.setEx(ex);
		shapeBean.setEy(ey);
		shapeBean.setType(type);
		shapeBean.setColor(color);
		shapeBean.setWidth(width);
	}
}
