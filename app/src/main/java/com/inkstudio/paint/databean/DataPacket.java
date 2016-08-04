package com.inkstudio.paint.databean;

import java.io.Serializable;

public class DataPacket implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6318039259020122614L;
	
	private int header = DataProtocol.MESSAGE_BEAN;
	
	private  int headerType;
	
	private ChatBean chatBean;
	
	private ShapeBean shapeBean;
	
	public int getHeader() {
		return header;
	}
	public void setHeader(int header) {
		this.header = header;
	}
	public ChatBean getChatBean() {
		return chatBean;
	}
	public void setChatBean(ChatBean chatBean) {
		this.chatBean = chatBean;
	}
	public ShapeBean getShapeBean() {
		return shapeBean;
	}
	public void setShapeBean(ShapeBean shapeBean) {
		this.shapeBean = shapeBean;
	}
	public int getHeaderType() {
		return headerType;
	}
	public void setHeaderType(int headerType) {
		this.headerType = headerType;
	}

}
