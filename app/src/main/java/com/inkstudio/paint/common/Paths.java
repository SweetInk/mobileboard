package com.inkstudio.paint.common;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;

import com.inkstudio.paint.common.Paths.PathAction.PathActionType;
import com.inkstudio.paint.util.Tool;

import android.graphics.Path;
public class Paths extends Path implements Serializable {

/**
	 * 
	 */
	private static final long serialVersionUID = 439944429464056445L;
private ArrayList<PathAction> actions = new ArrayList<Paths.PathAction>();
private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
    in.defaultReadObject();
    System.out.println("∑¥–Ú¡–ªØ");
    drawThisPath();
}


 
@Override
public void moveTo(float x, float y) {
    actions.add(new ActionMove(x, y));
    super.moveTo(x, y);
}
 
@Override
public void lineTo(float x, float y){
    actions.add(new ActionLine(x, y));
    super.lineTo(x, y);
}
@Override
	public void quadTo(float x1, float y1, float x2, float y2) {
	actions.add(new ActionQuad(x1, y1, x2, y2));
		super.quadTo(x1, y1, x2, y2);
	}
//	@Override
//	public void reset() {
//		actions.add(new ActionReset());
//			super.reset();
//		}
// 
private void drawThisPath(){
    for(PathAction p : actions){
        if(p.getType().equals(PathActionType.MOVE_TO)){
            super.moveTo(Tool.dp2px(p.getX()), Tool.dp2px(p.getY()));
        } else if(p.getType().equals(PathActionType.LINE_TO)){
            super.lineTo(Tool.dp2px(p.getX()), Tool.dp2px(p.getY()));
        } else if(p.getType().equals(PathActionType.QUAD_TO)){
        	super.quadTo(Tool.dp2px(p.getX()), Tool.dp2px(p.getY()),Tool.dp2px(p.getX1()),Tool.dp2px(p.getY1()));
        } 
        else if(p.getType().equals(PathActionType.RESET)){
        	super.reset();
        }
        
    }
}
 
public interface PathAction {
    public enum PathActionType {LINE_TO,MOVE_TO,QUAD_TO,RESET};
    public PathActionType getType();
    public float getX();
    public float getY();
    public float getX1();
    public float getY1();
    
}
 
public class ActionMove implements PathAction, Serializable{
    private static final long serialVersionUID = -7198142191254133295L;
 
    private float x,y;
 
    public ActionMove(float x, float y){
        this.x = x;
        this.y = y;
    }
 
    @Override
    public PathActionType getType() {
        return PathActionType.MOVE_TO;
    }
 
    @Override
    public float getX() {
        return x;
    }
 
    @Override
    public float getY() {
        return y;
    }

	@Override
	public float getX1() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getY1() {
		// TODO Auto-generated method stub
		return 0;
	}
 
}
 
public class ActionLine implements PathAction, Serializable{
    private static final long serialVersionUID = 8307137961494172589L;
 
    private float x,y;
 
    public ActionLine(float x, float y){
        this.x = x;
        this.y = y;
    }
 
    @Override
    public PathActionType getType() {
        return PathActionType.LINE_TO;
    }
 
    @Override
    public float getX() {
        return x;
    }
 
    @Override
    public float getY() {
        return y;
    }

	@Override
	public float getX1() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getY1() {
		// TODO Auto-generated method stub
		return 0;
	}
 
}
public class ActionQuad implements PathAction, Serializable{
	private static final long serialVersionUID = -7649097116215580884L;
	private float x,y;
	 private float x1,y1;
	 public ActionQuad(float x,float y,float x1,float y1){
		 this.x = x;
		 this.y = y;
		 this.x1 = x1;
		 this.y1 = y1;
	 }
	@Override
	public PathActionType getType() {
		return PathActionType.QUAD_TO;
	}

	@Override
	public float getX() {
		return this.x;
	}

	@Override
	public float getY() {
		return this.y;
	}
	public float getX1(){
		return this.x1;
	}
	public float getY1(){
		return this.y1;
	}
	
}
public class ActionReset implements PathAction, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public PathActionType getType() {
		// TODO Auto-generated method stub
		return PathActionType.RESET;
	}

	@Override
	public float getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getX1() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getY1() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
}