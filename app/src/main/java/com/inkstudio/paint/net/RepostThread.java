package com.inkstudio.paint.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import android.os.Handler;
import android.os.Message;

import com.inkstudio.paint.databean.ChatBean;
import com.inkstudio.paint.databean.DataPacket;
import com.inkstudio.paint.databean.DataProtocol;
import com.inkstudio.paint.databean.ShapeBean;

/**
 * 消息转换接受线程
 * @author SUCHU
 *
 */
public class RepostThread extends Thread{
	public Socket socket= null;
	public boolean flag = true;
	public Handler handler = null;
	public ObjectInputStream ois = null;
	public ObjectOutputStream oos = null;
	public RepostThread(Socket socket,Handler handler){
		this.socket = socket;
		this.handler = handler;
		try {
			ois = new ObjectInputStream(this.socket.getInputStream());
			oos = new ObjectOutputStream(this.socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag = false;
		}
	}
	public void run(){
		while (flag) {
			ObjectInputStream ois = null;
			try {
				ois = new ObjectInputStream(this.socket.getInputStream());
				Object obj = ois.readObject();
				DataPacket packet = (DataPacket)obj;
				int type = packet.getHeader();
				Message msg = new Message();
				System.out.println("get message from client....");
				switch(type){
				case DataProtocol.SHAPE_BEAN:
					
					ShapeBean sn = packet.getShapeBean();
					msg.what = 0x102;
					msg.obj = sn;
					handler.sendMessage(msg);
					break;
				case DataProtocol.MESSAGE_BEAN:
					ChatBean cb = (ChatBean)packet.getChatBean();
					System.out.println("reveive from client:"+cb.getContent());
					msg.what = 0x103;
					msg.obj  = cb;
					handler.sendMessage(msg);
					break;
				}
				for(Socket socket:ServerThread.list){
					if(!this.socket.equals(socket)){
						ObjectOutputStream	ops = new ObjectOutputStream(socket.getOutputStream());
						ops.writeObject(obj);
						ops.flush();
					}
				}
				
				obj = null;

			}
			 catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				flag = false;
			}
		}
	}
}
