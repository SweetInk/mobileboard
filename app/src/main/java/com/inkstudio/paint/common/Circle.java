package com.inkstudio.paint.common;
import android.graphics.Canvas;

/**
 * ‘≤–Œ¿‡
 * @author SUCHU
 *
 */
public class Circle extends IShape {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7635535655829216769L;

	@Override
	public void draw(Canvas canvas) {
		canvas.drawCircle(this.x + (this.ex - this.x) / 2.0F, this.y+ (this.ey - this.y) / 2.0F, Math.abs(this.x - this.ex) / 2.0F, Brush.getBrush(this.width,this.color));	
	}

}
