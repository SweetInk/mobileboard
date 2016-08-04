package com.inkstudio.paint.item;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.inkstudio.paint.common.Brush;
import com.inkstudio.paint.common.Colors;
import com.inkstudio.paint.common.IShape;
import com.inkstudio.paint.common.Shape;
import com.inkstudio.paint.common.ShapeFactory;
import com.inkstudio.paint.databean.DataPacket;
import com.inkstudio.paint.databean.DataProtocol;
import com.inkstudio.paint.databean.ShapeBean;
import com.inkstudio.paint.net.ServerThread;
import com.inkstudio.paint.util.Tool;

/**
 * 自定义画布类
 * 
 * @author SUCHU
 *
 */
public class PaintView extends View {
	public EditText et;
	float x, y;
	public Bitmap cacheBitmap = null;
	boolean flag = true;
	public Paint paint = null;
	public Path path = null;
	public IShape shape, s2 = null;
	public Socket socket = null;
	public Canvas cacheCanvas = null;
	public ShapeFactory factory;
	public static int width = 0;
	public static int height = 0;
	public static Tool tool = null;
	boolean finish = false;

	public PaintView(Context context, AttributeSet att) {

		super(context, att);
		// t.setx
		tool = new Tool(context);
		factory = new ShapeFactory();

		width = context.getResources().getDisplayMetrics().widthPixels;
		height = context.getResources().getDisplayMetrics().heightPixels;
		cacheBitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.ARGB_8888);
		path = new Path();

		cacheCanvas = new Canvas(cacheBitmap);
		cacheCanvas.drawColor(Color.WHITE);
		paint = Brush.getBrush();
		paint.setTextSize(25);
	}

	/**
	 * 更新服务器及客户端传来的图形数据
	 * 
	 * @param ShapeBean
	 */
	public void update(ShapeBean sn) {
		Log.i("TYPE", this.shape.type + "");
		s2 = factory.generateShpae(sn);
		if (!s2.move)
			s2.draw(cacheCanvas);
		reDraw();
	}

	public void clearCanvas() {
		this.cacheCanvas.drawColor(Color.WHITE);
		reDraw();
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.WHITE);
		canvas.drawBitmap(cacheBitmap, 0, 0, new Paint(4));
		//this.shape.draw(canvas);
		if (this.shape != null)
			this.shape.draw(canvas);

		if (this.s2 != null) {
			if (this.s2.move)
				this.s2.draw(canvas);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			onTouchDown(x, y);
			if (shape.getType() == Shape.SHAPE_TEXT) {
				this.et.setVisibility(View.VISIBLE);
				et.setX(x);
				et.setY(y);
			}
			break;
		case MotionEvent.ACTION_MOVE:
			onTouchMove(x, y);
			if (shape.getType() == Shape.SHAPE_TEXT) {
				et.setX(x);
				et.setY(y);
				
			}else{
			sendData();
			}break;
		case MotionEvent.ACTION_UP:
			onTouchUp(x, y);
			if(this.shape.type==Shape.SHAPE_TEXT){
					if(this.shape.finished)sendData();
			}
			else{
				sendData();
			}
			
			break;
		}
		reDraw();
		return true;
	}

	public void cancelInput() {
		this.et.setVisibility(View.GONE);
		this.et.setText("");
	}
	// EditText Widget
	public void finishTextInput() {
		float x = this.et.getX() + this.et.getPaddingLeft();
		float y = this.et.getY() + et.getHeight() - this.et.getPaddingTop()
				- et.getPaddingBottom();
		this.cacheCanvas.drawText(this.et.getText().toString(), x, y,
				Brush.getBrush2(1, this.shape.color));
		this.shape.x = x;
		this.shape.y = y;
		this.shape.str = et.getText().toString();
		this.shape.toText();
		this.shape.finished = true;
		sendData();
	
		this.et.setText("");
		this.et.setVisibility(View.GONE);
		this.finish = true;
	}

	/**
	 * 
	 */
	public void sendData() {
		System.out.println("SendData");
		DataPacket packet = new DataPacket();
		//this.shape.decodeData();
		
		packet.setHeader(DataProtocol.SHAPE_BEAN);
		
		packet.setShapeBean(this.shape.getShapeData());
		System.out.println("x:"+this.shape.getShapeData().x+",y:"+this.shape.getShapeData().y);
		System.out.println("String:" + this.shape.shapeBean.getStr()+",boolean:"+this.shape.getShapeData().isFinished()
				+ "Type:" + this.shape.getType());
		if (this.shape.color == null) {
			System.out.println("Color: NULL");
		}
		if (this.shape.getShapeData().getColor() == null) {

			System.out.println("Color: NULL");
		}
		switch (EasyPaint.TYPE) {
		case EasyPaint.SERVER:
			ServerThread.push2Aclient(packet);
			break;
		case EasyPaint.CLIENT:
			if (this.socket == null) {
				if (flag) {
					Toast.makeText(getContext(), "没连接主机，离线模式!", 0).show();
					flag = !flag;
				}
			} else {
				pushToServer(packet);
			}
			break;
		}
	}

	/**
	 * 手指按下
	 * 
	 * @param x
	 * @param y
	 */
	public void onTouchDown(float x, float y) {
		this.shape.onTouchDown(x, y, this.cacheCanvas);

	}

	/**
	 * 手指移动
	 * 
	 * @param x
	 * @param y
	 */
	public void onTouchMove(float x, float y) {
		this.shape.onToudchMove(x, y, this.cacheCanvas);
	}

	/**
	 * 手指抬起
	 * 
	 * @param x
	 * @param y
	 */
	public void onTouchUp(float x, float y) {
		this.shape.onTouchUp(x, y, this.cacheCanvas);

	}

	/**
	 * 重绘
	 */
	public void reDraw() {
		invalidate();
	}

	/**
	 * 获取图形
	 * 
	 * @return
	 */
	public IShape getShape() {
		return shape;
	}

	public void setShape(IShape shape) {
		this.shape = shape;
		ShapeInit();
	}

	/**
	 * 图形形状颜色及画板初始化
	 */
	public void ShapeInit() {
		this.shape.color = new Colors();
		final int color1 = getDrawingColor();
		this.shape.color.a = Color.alpha(color1);
		this.shape.color.r = Color.red(color1);
		this.shape.color.g = Color.green(color1);
		this.shape.color.b = Color.blue(color1);
		this.shape.width = getLineWidth();
	}

	/**
	 * 获取画笔颜色
	 * 
	 * @return int
	 */
	public int getDrawingColor() {
		// TODO Auto-generated method stub
		return this.paint.getColor();
	}

	/**
	 * 获取画笔宽度
	 * 
	 * @return int
	 */
	public int getLineWidth() {
		// TODO Auto-generated method stub
		return (int) this.paint.getStrokeWidth();
	}

	/**
	 * 设置画笔宽度
	 * 
	 * @param progress
	 */
	public void setLineWidth(int progress) {
		// TODO Auto-generated method stub
		this.paint.setStrokeWidth(progress);
	}

	/**
	 * 获取socket对象
	 * 
	 * @return
	 */
	public Socket getSocket() {
		return socket;
	}

	/**
	 * 设置socket对象
	 * 
	 * @param socket
	 */
	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	/**
	 * 推送到服务器
	 * 
	 * @param ishape
	 */
	public void pushToServer(DataPacket packet) {
		try {
			ObjectOutputStream ops = new ObjectOutputStream(
					this.socket.getOutputStream());
			ops.writeObject(packet);
			ops.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public EditText getEt() {
		return et;
	}

	public void setEt(EditText et) {
		this.et = et;
	}

}
