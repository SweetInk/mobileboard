package com.inkstudio.paint.task;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Message;

import com.inkstudio.paint.appbase.BaseApplication;
import com.inkstudio.paint.item.EasyPaint;
import com.inkstudio.paint.net.ClientThread;
import com.inkstudio.paint.util.WifiUtils;

public class CheckIsConWifiTask extends AsyncTask<Void, Integer, Integer>{
	public EasyPaint context;
	ProgressDialog pd = null;
	public boolean flag = true;
	public String SSID = null;
	public  CheckIsConWifiTask(EasyPaint context,String ssid) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.SSID = ssid;
	}
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		pd = new ProgressDialog(context);
		WifiUtils.connectWifi(this.SSID, "test1234", 3);
		pd.setTitle("提示");
		pd.setMessage("连接主机"+this.SSID+"中...");
		pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pd.setCancelable(false);
		pd.show();
	}
	
	@Override
	protected Integer doInBackground(Void... params) {
		// TODO Auto-generated method stub
		while(flag){
			boolean f = WifiUtils.isWifiConnected(context);
			System.out.println("AsyncTask executing..."+f);
			
			if(!f){
				
			}else flag =false;
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("AsyncTask ending...");
		return null;
	}
	@Override
	protected void onPostExecute(Integer result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		pd.cancel();
		final String ip =WifiUtils.intToIp(WifiUtils.getDhcpInfo().serverAddress);	
		new Thread(){
			public void run(){
				try {
					context.s = new Socket(ip,1315);
					if(context.s!=null){
						Message msg = new Message();
						msg.what = 0x100;
						context.handler.sendMessage(msg);
						this.interrupt();
						context.clientThread = new ClientThread(context.s, context.handler);
						context.doodleView.setSocket(context.s);
						BaseApplication.getInstance().setSocket(context.s);
						context.clientThread.start();
					}
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
	}
}
