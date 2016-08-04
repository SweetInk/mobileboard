package com.inkstudio.paint.common;

import com.inkstudio.paint.databean.ShapeBean;
import com.inkstudio.paint.util.Tool;

import android.graphics.Canvas;

public class Eraser extends IShape {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1832444622259852881L;
	 private float mX;
	  private float mY;
	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.drawPath(path, Brush.getBrush(this.width, new Colors(255,255,255,255)));
	}
	public Eraser() {
		// TODO Auto-generated constructor stub
		path =  new Paths();
		tmpPath = new Paths();
	}
	@Override
	public void onTouchDown(float x, float y, Canvas canvas) {
		this.shapeBean = new ShapeBean();
		path =  new Paths();
		this.tmpPath.reset();
		this.path.reset();
		this.path.moveTo(x, y);
		this.tmpPath.moveTo(Tool.px2dp(x),Tool.px2dp(y));
		this.mX = x;
		this.mY =y;
	}
	
	@Override
	public void onToudchMove(float x, float y, Canvas canvas) {
		move  =true;
		float f1  =Math.abs(x-this.mX);
		float f2 = Math.abs(y-this.mY);
		 if ((f1 >= 4.0F) || (f2 >= 4.0F))
		    {
		      this.path.quadTo(this.mX, this.mY, (x + this.mX) / 2.0F, (y + this.mY) / 2.0F);
		      this.tmpPath.quadTo(Tool.px2dp(this.mX), Tool.px2dp(this.mY), Tool.px2dp( (x + this.mX) / 2.0F), Tool.px2dp((y + this.mY) / 2.0F));
		      this.mX = x;
		      this.mY = y;
		    }
		    draw(canvas);
		    decodeData();
	}
	@Override
	public void onTouchUp(float x, float y, Canvas canvas) {
		move = false;
		this.path.lineTo(this.mX, this.mY);
		this.tmpPath.lineTo(Tool.px2dp(this.mX), Tool.px2dp(this.mY));
		decodeData();
	    draw(canvas);
	    this.path.reset();
	    this.tmpPath.reset();
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
