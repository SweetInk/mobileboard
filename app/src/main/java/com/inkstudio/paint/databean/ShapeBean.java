package com.inkstudio.paint.databean;

import java.io.Serializable;

import com.inkstudio.paint.common.Colors;
import com.inkstudio.paint.common.Paths;
/**
 * 图像数据Bean
 * @author SUCHU
 *
 */
public class ShapeBean implements Serializable {

	private static final long serialVersionUID = 99067903490510052L;
	public int type;
	public float x;
	public float y;
	public float ex;
	public float ey;
	public Paths path;
	public boolean move;
	public String str;
	public boolean finished;
	public Colors color;
	public int width;
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public float getEx() {
		return ex;
	}
	public void setEx(float ex) {
		this.ex = ex;
	}
	public float getEy() {
		return ey;
	}
	public void setEy(float ey) {
		this.ey = ey;
	}
	
	public void setColor(Colors color) {
		this.color = color;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public Colors getColor() {
		return this.color;
	}
	public Paths getPath() {
		return path;
	}
	public void setPath(Paths path) {
		this.path = path;
	}
	public boolean isMove() {
		return move;
	}
	public void setMove(boolean move) {
		this.move = move;
	}
	public String getStr() {
		return str;
	}
	public void setStr(String str) {
		this.str = str;
	}
	public boolean isFinished() {
		return finished;
	}
	public void setFinished(boolean finished) {
		this.finished = finished;
	}
	

}
