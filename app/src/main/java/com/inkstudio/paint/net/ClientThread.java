package com.inkstudio.paint.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.inkstudio.paint.databean.ChatBean;
import com.inkstudio.paint.databean.DataPacket;
import com.inkstudio.paint.databean.DataProtocol;
import com.inkstudio.paint.databean.ShapeBean;

import android.os.Handler;
import android.os.Message;
/**
 * <p>锟酵伙拷锟剿斤拷锟斤拷锟斤拷息锟竭筹拷</p>
 * @author SUCHU
 *
 */
public class ClientThread extends Thread {
	public Socket socket;
	public ObjectOutputStream ops = null;
	public ObjectInputStream ois = null;
	public boolean flag= true;
	public Handler handler;
	public ClientThread(Socket socket,Handler handler){
		this.handler = handler;
		this.socket = socket;
		try {
			ops = new ObjectOutputStream(this.socket.getOutputStream());
			ois = new ObjectInputStream(this.socket.getInputStream());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void run(){
		 ObjectInputStream ois = null;
		while(flag){
			try {
				ois = new ObjectInputStream(socket.getInputStream());
				Object obj = ois.readObject();
				DataPacket packet = (DataPacket)obj;
				int type = packet.getHeader();
				Message msg = new Message();
				switch(type){
					case DataProtocol.SHAPE_BEAN:
					
						ShapeBean sn = packet.getShapeBean();
						msg.what = 0x101;
						msg.obj = sn;
						handler.sendMessage(msg);
						obj=null;
						sn = null;
						break;
					case DataProtocol.MESSAGE_BEAN:
						ChatBean cb = (ChatBean)packet.getChatBean();
						msg.what = 0x102;
						msg.obj = cb;
						handler.sendMessage(msg);
						obj=null;
						sn = null;
						break;
					case DataProtocol.SERVER_CLOSE:
						msg.what=DataProtocol.SERVER_CLOSE;
						handler.sendMessage(msg);
						obj = null;
						sn = null;
						break;
				}
			} catch (Exception e) {
			}
		}
		
	}
	public void closeClient(){
		flag =false;
		if(socket!=null){
			try {
				this.interrupt();
				System.out.println("close client");
				socket.close();
				socket= null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
}
