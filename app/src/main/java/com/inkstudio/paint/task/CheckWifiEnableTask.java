package com.inkstudio.paint.task;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.inkstudio.paint.item.EasyPaint;
import com.inkstudio.paint.item.HostListDialog;
import com.inkstudio.paint.util.WifiUtils;

public class CheckWifiEnableTask extends AsyncTask<Void, Integer, Integer>{
	boolean flag = true;
	ProgressDialog pd = null;
	public EasyPaint context = null;
	 public CheckWifiEnableTask(EasyPaint context) {
		// TODO Auto-generated constructor stub
		 this.context = context;
	}
	@Override
	protected void onPreExecute() {
		
		// TODO Auto-generated method stub
		super.onPreExecute();
		pd = new ProgressDialog(context);
		EasyPaint.setTYPE(EasyPaint.CLIENT);
		pd.setTitle("提示");
		pd.setMessage("开启wifi中...");
		pd.setCancelable(false);
		pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pd.show();
		
	}
	@Override
	protected Integer doInBackground(Void... params) {
		// TODO Auto-generated method stub
		while(flag){
			System.out.println("AsyncTask executing...");
			if(WifiUtils.isWifiApEnabled()){
				WifiUtils.closeWifiAp();
	    	}
			if(!WifiUtils.wifiManager.isWifiEnabled()){
    			WifiUtils.wifiManager.setWifiEnabled(true);
    	}
    		else{
    			try {
    				Thread.sleep(1500);
    			} catch (InterruptedException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    			flag =false;
    		}
		//Log.i("Listening",null);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//this.cancel(!flag);
			System.out.println("AsyncTask ending...");
		}
		return 1;
	}
	
	@Override
	protected void onPostExecute(Integer result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	
		pd.cancel();
		HostListDialog hd = new HostListDialog(context,context);
		if(hd.list.size()<=0){
			hd.showNoHost();
		}
		else{
		hd.show();
	
		}
		System.out.println("has executed!!");
	}
	
}