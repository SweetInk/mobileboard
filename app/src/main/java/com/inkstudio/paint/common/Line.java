package com.inkstudio.paint.common;

import android.graphics.Canvas;
import android.util.Log;
/**
 * œﬂÃı¿‡
 * @author Administrator
 *
 */
public class Line extends IShape {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5057872624098970480L;

	@Override
	public void draw(Canvas canvas) {
		canvas.drawLine(x, y, ex, ey, Brush.getBrush(width, color));
	}

}
