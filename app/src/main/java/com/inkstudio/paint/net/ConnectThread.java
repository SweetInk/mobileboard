package com.inkstudio.paint.net;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import android.util.Log;
/**
 * ���ӷ������߳�
 * @author SUCHU
 *
 */
public class ConnectThread extends Thread{
	public Socket socket = null;//�ͻ���socket
	public String ip;//������ip
	public int port;//�������˿�
	public ConnectThread (String ip,int port){
		this.ip = ip;
		this.port = port;
		Log.i("Connect ���췽��", "");
	}
	public void run(){
		try {
			socket = new Socket(ip, port);
			Log.i("���ӳɹ�", "");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * <p>���ؿͻ���socket</p>
	 * @return Socket
	 */
	public Socket getSocket(){
		if(this.socket!=null&&this.socket.isConnected()){
			return this.socket;
		}
		return socket;
	}

}
