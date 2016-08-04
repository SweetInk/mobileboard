package com.inkstudio.paint.net;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import android.util.Log;
/**
 * 连接服务器线程
 * @author SUCHU
 *
 */
public class ConnectThread extends Thread{
	public Socket socket = null;//客户端socket
	public String ip;//服务器ip
	public int port;//服务器端口
	public ConnectThread (String ip,int port){
		this.ip = ip;
		this.port = port;
		Log.i("Connect 构造方法", "");
	}
	public void run(){
		try {
			socket = new Socket(ip, port);
			Log.i("连接成功", "");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * <p>返回客户端socket</p>
	 * @return Socket
	 */
	public Socket getSocket(){
		if(this.socket!=null&&this.socket.isConnected()){
			return this.socket;
		}
		return socket;
	}

}
