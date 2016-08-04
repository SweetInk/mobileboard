package com.inkstudio.paint.databean;

import java.io.Serializable;

public class DataProtocol implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int SHAPE_BEAN = 0x101; 		//data header
	public static final int MESSAGE_BEAN = 0x102;		//data header
	public static  final int SEND_MSG =0x103; 			//ChatMessage
	public static  final int REG_NAME  = 0x104;		//Register User
	public static  final int CLIENT_EXIT = 0x105;		//Client exit
	public static  final int SERVER_CLOSE = 0x106;		//Shutdown server
}
