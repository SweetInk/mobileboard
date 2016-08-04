package com.inkstudio.paint.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.inkstudio.paint.item.EasyPaint;
import com.inkstudio.paint.net.ServerThread;
import com.inkstudio.paint.util.WifiUtils;

public class CheckWifiApEnableTask extends AsyncTask<Void, Integer, Integer>{
	boolean flag = true;
	ProgressDialog pd = null;
	public EasyPaint context = null;
	 public CheckWifiApEnableTask(EasyPaint context) {
		// TODO Auto-generated constructor stub
		 this.context = context;
	}
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		EasyPaint.setTYPE(EasyPaint.SERVER);
		
		WifiUtils.stratWifiAp("MobileBoard_"+context.str4(), "test1234", 3);
		pd = new ProgressDialog(context);
		pd.setTitle("提示");
		pd.setMessage("创建热点中...");
		pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pd.setCancelable(false);
		pd.show();
		
	}
	@Override
	protected Integer doInBackground(Void... params) {
		// TODO Auto-generated method stub
		while(flag){
			if(WifiUtils.isWifiApEnabled()){
				flag = false;
				
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//this.cancel(!flag);
		}
		return null;
	}
	@Override
	protected void onPostExecute(Integer result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		context.serverThread = new ServerThread(context.handler);
		context.serverThread.start();
		pd.cancel();
	}
	
	
}