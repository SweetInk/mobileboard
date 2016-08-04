package com.inkstudio.paint.common;

import com.inkstudio.paint.databean.ShapeBean;

/**
 * 形状工厂类
 * @author Administrator
 *
 */
public class ShapeFactory {
	public IShape shape = null;
	public IShape createShape(int type){
		switch(type){
			case Shape.SHAPE_LINE:
				shape = new Line();
				shape.setType(type);
				break;
			case Shape.SHAPE_CIRCLE:
				shape = new Circle();
				shape.setType(type);
				break;
			case Shape.SHPAE_RECT:
				shape = new Rect();
				shape.setType(type);
				break;
			case Shape.SHAPE_PENCIL:
				shape = new Pencil();
				shape.setType(type);
				break;
			case Shape.SHAPE_ERASER:
				shape = new Eraser();
				shape.setType(type);
				break;
			case Shape.SHAPE_TEXT:
				shape = new PText();
				shape.setType(Shape.SHAPE_TEXT);
				break;
		}
		return shape;
		
	}
	/**
	 * 解码数据
	 * @param sn
	 * @return
	 */
	public IShape generateShpae(ShapeBean sn){
		switch(sn.getType()){
		case Shape.SHAPE_LINE:
			shape = new Line();
			shape.encodeShapeData(sn);
		break;
		case Shape.SHAPE_CIRCLE:
			shape = new Circle();
			shape.encodeShapeData(sn);
			break;
		case Shape.SHPAE_RECT:
			shape = new Rect();
			shape.encodeShapeData(sn);
			break;
		case Shape.SHAPE_PENCIL:
			shape = new Pencil();
			shape.encodeShapeData(sn);
			break;
		case Shape.SHAPE_ERASER:
			shape = new Eraser();
			shape.encodeShapeData(sn);
			break;
		case Shape.SHAPE_TEXT:
			shape= new PText();
			shape.encodeShapeData(sn);
			break;
		}
		return this.shape;
	}
}
