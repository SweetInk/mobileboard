package com.inkstudio.paint.item;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class Server extends Thread {
	boolean close = false;
	ServerSocket serverSocket = null;
	Context context;
	Handler handler = null;
	public static ArrayList<Socket> list = null;
	public void disconnect(){
		PrintWriter pw;
		for(Socket socket:list){
		try {
			pw = new PrintWriter(socket.getOutputStream());
			pw.println("exit");
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}}
	}
//	public static void sendToAll(Circle c){
//		for(Socket s:list){
//			try {
//				s.setSendBufferSize(64*1024);
//				ObjectOutputStream ois = new ObjectOutputStream(s.getOutputStream());
//				ois.writeObject(c);
//				ois.flush();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				
//			}
//			
//		}
//	}
	
//	public static void sendToAll(Action action){
//		for(Socket s:list){
//			try {
//				s.setSendBufferSize(64*1024);
//				ObjectOutputStream ois = new ObjectOutputStream(s.getOutputStream());
//				ois.writeObject(action);
//				ois.flush();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				
//			}
//			
//		}
//	}
	
	public Server(Context context, Handler handler) {
		this.context = context;
		this.handler = handler;

		list = new ArrayList<Socket>();

		try {
			serverSocket = new ServerSocket(1315);
			Message msg = new Message();
			msg.what = 0x101;
			handler.sendMessage(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while (!close) {
			try {
				Socket socket = serverSocket.accept();
				Message msg = new Message();
				msg.what = 0x102;
				handler.sendMessage(msg);
				new AcceptThread(socket).start();
				Log.i("DEBUG", "新用户加入");
				list.add(socket);
			} catch (IOException e) {
			
				e.printStackTrace();
				close = true;
			}
		}
	}

	class AcceptThread extends Thread {
		Socket socket = null;
		boolean flag = true;
		String exit = "";
		public AcceptThread(Socket s) {
			this.socket = s;
			try {
			//Log.i("读取大小",	this.socket.getReceiveBufferSize()+"");
			this.socket.setReceiveBufferSize(64*1024);
			this.socket.setSendBufferSize(64*1024);
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void run() {
			ObjectInputStream ois = null;

			while (flag) {
				try {		
					ois = new ObjectInputStream(
							socket.getInputStream());
					Object obj = ois.readObject();
					for(Socket socket:list){
						socket.setSendBufferSize(64*1024);
						if(!this.socket.equals(socket)){
					Log.i("读取数据中。。。", "--------------");
						ObjectOutputStream opt = new ObjectOutputStream(socket.getOutputStream());
						opt.writeObject(obj);
						opt.flush();
					Log.i("-------------->", obj.toString());
					}
						
					}
				//	Circle c =(Circle)obj;
					//Action a = (Action)obj;
					Message msg = new Message();
					msg.what = 0x104;
					//msg.obj = a;
					handler.sendMessage(msg);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					flag = false;
					Message msg = new Message();
					msg.what  =0x105;
					handler.sendMessage(msg);
					socket = null;
					ois = null;
					this.interrupt();
				}
			}
		}
	}
}
