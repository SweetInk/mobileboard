package com.inkstudio.paint.common;

import java.io.Serializable;

import android.graphics.Color;
/**
 * 自定义颜色类
 * 包装颜色值
 * @author SUCHU
 *
 */
public class Colors extends Color implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6667883971493182737L;
	public int a;
	public int r;
	public int g;
	public int b;
	public Colors(int a,int r,int g,int b){
		this.a =a;
		this.r =r;
		this.g=g;
		this.b=b;
	}
	public Colors(){}

	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}
	
}
   
