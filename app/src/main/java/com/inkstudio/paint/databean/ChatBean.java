package com.inkstudio.paint.databean;

import java.io.Serializable;

public class ChatBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4360974063392879345L;
	public String content;
	public String time;
	public int userImage;
	public boolean iscomeMsg;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getUserImage() {
		return userImage;
	}
	public void setUserImage(int userImage) {
		this.userImage = userImage;
	}
	public boolean isIscomeMsg() {
		return iscomeMsg;
	}//add comment
	public void setIscomeMsg(boolean iscomeMsg) {
		this.iscomeMsg = iscomeMsg;
	}
}
