package com.inkstudio.paint.common;

import android.graphics.Canvas;

public class PText extends IShape {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5680174946564472613L;

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		if(finished){
			canvas.drawText(this.str, ex, ey, Brush.getBrush2(1, color));
			this.finished = false;
		}
	}

}
