package com.inkstudio.paint.item;

import java.util.List;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListAdapter extends BaseAdapter {
	public List<String> list = null;
	public LayoutInflater mInflater = null;
	public ListAdapter(Context context,List list) {
		this.list = list;
		this.mInflater = LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if(convertView==null){
			convertView = mInflater.inflate(R.layout.wifihost_list, null);
			holder = new ViewHolder();
			holder.wifiHost = (TextView)convertView.findViewById(R.id.time);
			convertView.setTag(holder);
			holder.wifiHost.setText(list.get(position).toString());
				
		
		}
		else {
			holder=  (ViewHolder) convertView.getTag();
		}
		
		return convertView;
	}
	static class ViewHolder{
		public TextView wifiHost;
	} 

}
