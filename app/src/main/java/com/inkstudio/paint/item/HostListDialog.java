package com.inkstudio.paint.item;

import java.util.List;

import com.inkstudio.paint.task.CheckIsConWifiTask;
import com.inkstudio.paint.util.WifiUtils;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class HostListDialog extends Dialog {
	ListAdapter la = null;
	public String currentSSID = null;
	public List<String> list = null;
	Context context = null;
	EasyPaint sp = null;
	public HostListDialog(final Context context,final EasyPaint sp) {
	
		super(context);
		this.context = context;
		this.sp = sp;
		// TODO Auto-generated constructor stub
		this.setContentView(R.layout.wifi_layout);
		this.setTitle("选择主机");
		 this.setCancelable(true);
		ListView l = (ListView)findViewById(R.id.wifilist);
		list = WifiUtils.getScanResult();
		System.out.println("list size:"+list.size());
		la = new ListAdapter(context, list);
		l.setAdapter(la);
		l.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
			Toast.makeText(context, "host name:"+list.get(position).toString(), 0).show();
				//Toast.makeText(this, "", 0).show();
			currentSSID = list.get(position).toString();
			CheckIsConWifiTask task = new CheckIsConWifiTask(sp, currentSSID);
			task.execute();
			dismiss();
			
			}
		});
		}
	public void showNoHost(){
		AlertDialog.Builder builder = new Builder(this.context);
		builder.setTitle("提示");
		builder.setIcon(R.drawable.logo);
		builder.setMessage("没有发现主机，是否刷新？");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
					list = WifiUtils.getScanResult();
					new HostListDialog(context,sp).show();
			}
			
		});
		builder.setNegativeButton("取消", null);
		builder.create();
		builder.show();
	}
	

}
