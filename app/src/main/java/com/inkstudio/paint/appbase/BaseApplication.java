package com.inkstudio.paint.appbase;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.inkstudio.paint.databean.ChatBean;
import com.inkstudio.paint.databean.DataPacket;

/**
 * BaseApplication
 * @author suchu
 *
 */
public class BaseApplication extends Application {
	private static BaseApplication instance;
	private int type;
	private Socket socket;
	private ObjectInputStream ois ;
	private ObjectOutputStream oos;
	private List<ChatBean> msgList;

	
	public void init(){
		ois = null;
		oos = null;
		socket = null;
	}
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		msgList = new ArrayList<ChatBean>();
		
		System.out.println("BaseApplication Init~!");
		if(instance == null){
			instance = this;
		}
	}
	public static  Handler hd = new Handler(){
		
		public void handleMessage(android.os.Message msg) {
		
			Log(""+msg.obj);
		};
	};
	public static void Log(String str){
		Toast.makeText(instance, str, Toast.LENGTH_SHORT).show();
	}
	public static void Debug(String str){
		Message msg = new Message();
		msg.obj = str;
		hd.sendMessage(msg);
	}
	public void test(){
		System.out.println("测试appBase");
	}
	public static BaseApplication getInstance(){
		return instance;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	public ObjectInputStream getOis() {
		return ois;
	}
	public void setOis(ObjectInputStream ois) {
		this.ois = ois;
	}
	public ObjectOutputStream getOos() {
		return oos;
	}
	public void setOos(ObjectOutputStream oos) {
		this.oos = oos;
	}
	/**
	 * 发送数据到服务器
	 * @param obj
	 */
	public void pushToServer(DataPacket obj){
		try {
			if(this.oos!=null){
			oos.writeObject(obj);
			oos.flush();
			}else{
				Toast.makeText(this, "offline~!", Toast.LENGTH_SHORT).show();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}
	
	
	
	public List<ChatBean> getMsgList() {
		return msgList;
	}
	public void setMsgList(List<ChatBean> msgList) {
		this.msgList = msgList;
	}
	
	
}
