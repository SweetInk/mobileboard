package com.inkstudio.paint.common;

import java.io.Serializable;

import com.inkstudio.paint.databean.ShapeBean;
import com.inkstudio.paint.item.PaintView;
import com.inkstudio.paint.util.Tool;

import android.graphics.Canvas;

/**
 * 所有图形的父类
 * @author SUCHU
 *
 */
public abstract class IShape implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4198856093218513502L;
	public float x;
	public float y;
	public float ex;
	public float ey;
	public Colors color;
	public boolean finished;
	public int width;
	public boolean move = false;
	public  int type ;
	public String str = "" ;
	public ShapeBean shapeBean;
	public Paths path,tmpPath;
	public abstract void draw(Canvas canvas);
	
	
	public void onTouchDown(float x,float y,Canvas canvas){
		init();
		shapeBean = new ShapeBean();
		this.x = x;
		this.y = y;
		this.finished = false;
		move = false;
		this.ex = x;
		this.ey = y;
		decodeData();
	
	}
	
	public void onToudchMove(float x,float y,Canvas Canvas){
		this.ex = x;
		this.ey = y;
		move = true;
		decodeData();
	}
	
	public void onTouchUp(float x,float y,Canvas canvas){
		this.ex = x;
		this.ey = y;
		move = false;
		decodeData();
		draw(canvas);
		init();
	}
	
	private void init() {
		this.x = 0f;
		this.y = 0f;
		this.ex = 0f;
		this.ey = 0f;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	/**
	 * 获取图形数据
	 * @return
	 */
	public ShapeBean getShapeData(){	
		return shapeBean;
	}
	/**
	 * 解码数据
	 * @param shapeBean
	 */
	public void encodeShapeData(ShapeBean shapeBean){
		this.x = Tool.dp2px(shapeBean.getX());
		this.y = Tool.dp2px(shapeBean.getY());
		this.ex = Tool.dp2px(shapeBean.getEx());
		this.ey =Tool.dp2px(shapeBean.getEy());
		this.str = shapeBean.getStr();
		this.finished = shapeBean.isFinished();
		this.color = shapeBean.getColor();
		this.width = shapeBean.getWidth();
		this.type = shapeBean.getType();
		this.path = shapeBean.getPath();
		this.move = shapeBean.isMove();
		
	}
	/**
	 * 封装数据
	 */
	public void decodeData(){
		shapeBean.setX(Tool.px2dp(this.x));
		shapeBean.setY(Tool.px2dp(this.y));
		shapeBean.setEx(Tool.px2dp(ex));
		shapeBean.setEy(Tool.px2dp(ey));
		shapeBean.setType(type);
		shapeBean.setStr(this.str);
		shapeBean.setColor(color);
		shapeBean.setFinished(this.finished);
		shapeBean.setWidth(width);
		shapeBean.setMove(move);
		shapeBean.setPath(path);
	}
	
	public void toText(){
		this.shapeBean.setStr(this.str);
		this.shapeBean.setFinished(true);
		
	}
}
