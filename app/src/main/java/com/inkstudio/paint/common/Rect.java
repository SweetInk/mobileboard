package com.inkstudio.paint.common;
import android.graphics.Canvas;
import android.util.Log;
/**
 * ������
 * @author Administrator
 *
 */
public class Rect extends IShape {
	private static final long serialVersionUID = 3963751859913297480L;

	@Override
	public void draw(Canvas canvas) {

		if(this.x < this.ex && this.y < this.ey){
			canvas.drawRect(this.x, this.y, this.ex, this.ey, Brush.getBrush(this.width, this.color));
		}else if(this.x < this.ex && this.y > this.ey){
			canvas.drawRect(this.x, this.ey, this.ex, this.y, Brush.getBrush(this.width, this.color));
		}else if(this.x > this.ex && this.y < this.ey){
			canvas.drawRect(this.ex, this.y, this.x, this.ey, Brush.getBrush(this.width, this.color));
		}else if(this.x > this.ex && this.y > this.ey){
			canvas.drawRect(this.ex, this.ey, this.x, this.y, Brush.getBrush(this.width, this.color));
		}
	}

}
