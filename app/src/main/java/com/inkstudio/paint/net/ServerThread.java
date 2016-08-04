package com.inkstudio.paint.net;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import com.inkstudio.paint.databean.DataPacket;
import com.inkstudio.paint.databean.DataProtocol;
import com.inkstudio.paint.databean.ShapeBean;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class ServerThread extends Thread{
	public static  ArrayList<Socket>list;
	public ServerSocket serverSocket = null;
	public boolean flag =true;
	public RepostThread repostThread = null;
	public Socket socket= null;
	public  Handler handler= null;
	Socket ket  = null;
	
	/**
	 * 推送数据到各个客户端
	 * @param shapeBean 图形数据
	 */
	public static void push2Aclient(DataPacket packet){
		
		if(list!=null){
		for(Socket socket:list){
			try {
				ObjectOutputStream opt = new ObjectOutputStream(socket.getOutputStream());
				opt.writeObject(packet);
				opt.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		}
	}
	/**
	 * 服务器线程构造方法
	 * @param handler
	 */
	public ServerThread(Handler handler){
		list = new ArrayList<Socket>();
		this.flag = true;
		this.handler = handler;
		try {
			serverSocket = new ServerSocket(1315);
			System.out.println("InetAddress:"+serverSocket.getLocalSocketAddress());
			this.handler.sendEmptyMessage(0x100);
			Log.i("SUCCESS", "");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public boolean  closeServer(){
		DataPacket dp = new DataPacket();
		dp.setHeader(DataProtocol.SERVER_CLOSE);
		this.push2Aclient(dp);
		flag = false;
		
		//new ServerThread(handler);
		Log.i("关闭线程", "");
		
		new Thread(){
			public void run(){	
				try {
			ket  = new Socket("localhost",1315);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}}}.start();
		try {
			serverSocket.close();
		if(ket!=null)	ket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return !flag;
		
	}
	public void run(){
		while(flag){
			try {
				socket = serverSocket.accept();
				System.out.println("new client joined...");
				this.handler.sendEmptyMessage(0x101);
				//为每个用户开启一个线程
				new RepostThread(socket, this.handler).start();
				list.add(socket);
				Log.i("DEBUG", "新客户端加入");
			} catch (Exception e) {
				Log.e("Exception", e.getMessage());
				e.printStackTrace();
			//	this.interrupt();
				//flag = false;
			}
		}
	}
}
