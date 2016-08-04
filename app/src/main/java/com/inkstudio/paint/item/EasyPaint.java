package com.inkstudio.paint.item;


import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.text.Html;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

import com.inkstudio.paint.appbase.BaseApplication;
import com.inkstudio.paint.common.IShape;
import com.inkstudio.paint.common.Shape;
import com.inkstudio.paint.common.ShapeFactory;
import com.inkstudio.paint.databean.ChatBean;
import com.inkstudio.paint.databean.DataProtocol;
import com.inkstudio.paint.databean.ShapeBean;
import com.inkstudio.paint.item.ColorPickerDialog.OnColorChangedListener;
import com.inkstudio.paint.net.ClientThread;
import com.inkstudio.paint.net.ConnectThread;
import com.inkstudio.paint.net.ServerThread;
import com.inkstudio.paint.task.CheckWifiApEnableTask;
import com.inkstudio.paint.task.CheckWifiEnableTask;
import com.inkstudio.paint.util.Tool;
import com.inkstudio.paint.util.WifiUtils;
public class EasyPaint extends FragmentActivity {
	 private DrawerLayout mDrawer_layout;  //抽屉式布�?
	 private LinearLayout mMenu_layout;
	  public static int DRAW_TYPE = Shape.SHAPE_PENCIL;
	  public ServerThread serverThread = null;
	  public ClientThread clientThread = null;
	  public ConnectThread connectThread = null;
	  Context context = null;
	  IShape shape = null;
	  ShapeFactory factory = null;
	  EditText et = null;
	
	boolean fullScreen;
	public static final int SERVER = 0;
	public static final int CLIENT = 1;
	public static int TYPE = CLIENT;
	public String svrip = "";
	public Socket s =null;
	

	private Dialog currentDialog; 
	public PaintView doodleView=null;
	private AtomicBoolean dialogIsVisible = new AtomicBoolean(); 
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	public void setShape(int type){
		this.factory = new ShapeFactory();
		this.shape = factory.createShape(type);
		doodleView.setShape(shape);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
	}
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
//		menu.add(0, 1, 0, "测试菜单");
//        menu.add(0, 2, 0, "Sample");
        menu.add(0, 3, 0, "完成输入");

		//super.onCreateContextMenu(menu, v, menuInfo);
	}
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId())
		{
		case 3:
			//Toast.makeText(getApplicationContext(), "完成", 0).show();
			doodleView.finishTextInput();
			break;
		}
		return super.onContextItemSelected(item);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		 
		// setContentView(R.layout.easypaint);
		 getWindow().addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
		 setContentView(R.layout.activity_main);
		 doodleView = (PaintView)findViewById(R.id.paintview);
		 et = (EditText) findViewById(R.id.editText1);
		 doodleView.setEt(et);
		 this.registerForContextMenu(et);

		 et.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				//Toast.makeText(getApplicationContext(), "Long Tap", 0).show();
				return false;
			}
		});
		 this.setShape(DRAW_TYPE);
	
	        mDrawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
	        mMenu_layout = (LinearLayout) findViewById(R.id.menu_layout);
	        ListView menu_listview = (ListView) mMenu_layout.findViewById(R.id.menu_listView);
	        ArrayList<HashMap<String, String>> tempMapList = DataBuiltUtils.getMainMapList();
	        menu_listview.setAdapter(new MenuListViewAdapter( getApplicationContext(), tempMapList));
	        //菜单ListView设置监听事件
	        menu_listview.setOnItemClickListener(new DrawerItemClickListener());
	        mDrawer_layout.setDrawerListener(new DrawerListener() {
				
				@Override
				public void onDrawerStateChanged(int arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onDrawerSlide(View arg0, float arg1) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onDrawerOpened(View arg0) {
					// TODO Auto-generated method stub
//					Toast.makeText(getApplicationContext(), "Left Menu Shownd", 0).show();
					doodleView.cancelInput();
				}
				
				@Override
				public void onDrawerClosed(View arg0) {
					// TODO Auto-generated method stub
					
				}
			});
	        context = this;
	
	}
	
	public Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			
			switch(TYPE){
				case SERVER:{
					
					switch(msg.what){
						case 0x100:
							AlertDialog.Builder b = new Builder(EasyPaint.this);
							b.setTitle("提示");
							b.setMessage("主机开启成功！");
							b.setNegativeButton("确定", null);
							b.create();
							b.show();
							break;
						case 0x101:showTips("新客户端加入连接");break;
						case 0x102:
							ShapeBean sn = (ShapeBean) msg.obj;
							doodleView.update(sn);
							break;
						case 0x103:
							ChatBean cb = (ChatBean)msg.obj;
							 cb.setIscomeMsg(!cb.isIscomeMsg());
							
							 break;
							
					}
				} break;
				case CLIENT:{
					switch(msg.what){
					 case 0x100:
						 AlertDialog.Builder b = new Builder(EasyPaint.this);
							b.setTitle("提示");
							b.setMessage("成功连接到主机！");
							b.setNegativeButton("确定", null);
							b.create();
							b.show();
						 break;
					 case 0x101:
					 ShapeBean sn = (ShapeBean) msg.obj;
						doodleView.update(sn);
					 	break;
					 case 0x102:
						 ChatBean cb = (ChatBean)msg.obj;
						 cb.setIscomeMsg(!cb.isIscomeMsg());
						 break;
					 case DataProtocol.SERVER_CLOSE:
						 Toast.makeText(getApplicationContext(), "server close", Toast.LENGTH_SHORT).show();
						 break;
					}
				}break;
			}
			
		};
	};
	
	
	 public class DrawerItemClickListener implements OnItemClickListener ,OnColorChangedListener{
	    	
	        @Override
	        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
	        	
	            switch (position){
	                case 0:
	                	new ColorPickerDialog(context, this, doodleView.paint.getColor()).show();
	                    break;
	                case 1:
	                 showLineWidthDialog();
	                    break;
	                case 2:
	                	//Toast.makeText(getApplicationContext(), "调试中...\n感谢支持！", 0).show();
	                	setShape(Shape.SHAPE_TEXT);
	                	break;
	                case 3:
	                	setShape(Shape.SHAPE_PENCIL);
	                	break;
	                	
	                case 4:
	                	setShape(Shape.SHPAE_RECT);
	                    break;
	                case 5:
	                	setShape(Shape.SHAPE_CIRCLE);
	                	break;
	                case 6:
	                	setShape(Shape.SHAPE_LINE);
	                	break;
	                case 7:
	                	setShape(Shape.SHAPE_ERASER);
	                	break;
	                case 8:
	                	doodleView.clearCanvas();
	                	break;
	            }
	            mDrawer_layout.closeDrawer(mMenu_layout);//点击后关闭mMenu_layout
	        }

			@Override
			public void colorChanged(int color) {
				// TODO Auto-generated method stub
				//Toast.makeText(context, "color Changed:"+color, Toast.LENGTH_SHORT).show();
			//	Log.i(">>>>Color:", "color:"+color);
			
				doodleView.paint.setColor(color);
				doodleView.ShapeInit();
			}
	    }
	public String str4(){
		UUID uid = UUID.randomUUID();
		return uid.toString().substring(0, 4);
		
	}

	public void showCloseApDialog(){
		AlertDialog.Builder b = new Builder(this);
		b.setTitle("提示");
		b.setMessage("当前已开启主机，是否关闭？");
		b.setNegativeButton("取消", null);
		b.setPositiveButton("确定", new DialogInterface.OnClickListener(){
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
					showTips("test");
					serverThread.closeServer();
					CheckWifiEnableTask task2 = new CheckWifiEnableTask(EasyPaint.this);
					task2.execute();
			}
			
		} );
		
		b.create();
		b.show();
	}
	
	public void showCloseClientDialog(){
		AlertDialog.Builder b = new Builder(this);
		b.setTitle("提示");
		b.setMessage("当前已连接到主机，是否断开？");
		b.setNegativeButton("取消", null);
		b.setPositiveButton("确定", new DialogInterface.OnClickListener(){
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
					showTips("test");
					clientThread.closeClient();
					CheckWifiApEnableTask task2 = new CheckWifiApEnableTask(EasyPaint.this);
					task2.execute();
			}
			
		} );
		
		b.create();
		b.show();
	}
	
	public boolean onOptionsItemSelected(MenuItem item)
	{
		doodleView.paint.setXfermode(null);
		switch(item.getItemId()){
			case R.id.wifi:
				if(!WifiUtils.isWifiConnected(this)){
				if(!WifiUtils.isWifiApEnabled()){
			//	EasyPaint.setTYPE(SERVER);
			
				CheckWifiApEnableTask task = new CheckWifiApEnableTask(this);
				task.execute();
				}
				else{
					showTips("您已开启了主机,无需再次开启！");
				}
				}else{
					showCloseClientDialog();
				}
				break;
			//连接主机
			case R.id.con:
				
				if(!WifiUtils.wifiManager.isWifiEnabled()){
					if(serverThread!=null&&WifiUtils.isWifiApEnabled()){
						showCloseApDialog();
						System.out.println("closing server...");
					}
					else{
						System.out.println("Debug step 2~!");
						CheckWifiEnableTask task2 = new CheckWifiEnableTask(EasyPaint.this);
						task2.execute();
					}
				
				
				}
				else{
					HostListDialog hd = new HostListDialog(EasyPaint.this,EasyPaint.this);
					if(hd.list.size()<=0){
						hd.showNoHost();
					}
					else{
					hd.show();
				
					}
				}
				
				break;
				
			case R.id.save:
				Tool.createBitmap(doodleView);
				break;
				case R.id.about:
					AlertDialog.Builder builder = new Builder(this);
					builder.setTitle("关于移动白板");
					builder.setMessage(Html.fromHtml(getResources().getString(R.string.copyright)));
					builder.setNegativeButton("确定", null);
					builder.create();
					builder.show();
					break;
					
					
					
				
		}
		return super.onOptionsItemSelected(item);
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.toolsmenu, menu);
		return true;
	}
	
	
		   
		   public void showTips(String str){
			   Toast.makeText(this, str, 0).show();
		   }
		   
		   
//显示颜色选择对话
 
   //显示画笔大小
  private void showLineWidthDialog()
   {
      // create the dialog and inflate its content
      currentDialog = new Dialog(this);
      currentDialog.setContentView(R.layout.width_dialog);
      currentDialog.setTitle(R.string.title_line_width_dialog);
      currentDialog.setCancelable(true);
      
      // get widthSeekBar and configure it
      SeekBar widthSeekBar = 
         (SeekBar) currentDialog.findViewById(R.id.widthSeekBar);
      widthSeekBar.setOnSeekBarChangeListener(widthSeekBarChanged);
      widthSeekBar.setProgress(doodleView.getLineWidth()); 
       
      // set the Set Line Width Button's onClickListener
      Button setLineWidthButton = 
         (Button) currentDialog.findViewById(R.id.widthDialogDoneButton);
      setLineWidthButton.setOnClickListener(setLineWidthButtonListener);
      
      dialogIsVisible.set(true); // dialog is on the screen
      currentDialog.show(); // show the dialog      
   } // end method showLineWidthDialog

   // OnSeekBarChangeListener for the SeekBar in the width dialog
   private OnSeekBarChangeListener widthSeekBarChanged = 
      new OnSeekBarChangeListener() 
      {
         Bitmap bitmap = Bitmap.createBitmap( // create Bitmap
            400, 100, Bitmap.Config.ARGB_8888);
         Canvas canvas = new Canvas(bitmap);
         
         @Override
         public void onProgressChanged(SeekBar seekBar, int progress,
            boolean fromUser) 
         {  
            ImageView widthImageView = (ImageView) 
               currentDialog.findViewById(R.id.widthImageView);
            Paint p = new Paint();
            p.setColor(doodleView.getDrawingColor());
            p.setStrokeCap(Paint.Cap.ROUND);
            p.setStrokeWidth(progress);
            bitmap.eraseColor(Color.WHITE);
            canvas.drawLine(30, 50, 370, 50, p);
            widthImageView.setImageBitmap(bitmap);
         } 
         @Override
         public void onStartTrackingTouch(SeekBar seekBar) 
         {
         } 
   
         
         @Override
         public void onStopTrackingTouch(SeekBar seekBar) 
         {
         } 
      }; 
      private OnClickListener setLineWidthButtonListener = 
    	      new OnClickListener() 
    	      {
    	         @Override
    	         public void onClick(View v) 
    	         {
    	            SeekBar widthSeekBar = 
    	               (SeekBar) currentDialog.findViewById(R.id.widthSeekBar);
    	   
    	            // set the line color
    	            doodleView.setLineWidth(widthSeekBar.getProgress());
    	            doodleView.ShapeInit();
    	            dialogIsVisible.set(false); // dialog is not on the screen
    	            currentDialog.dismiss(); 
    	            currentDialog = null; 
    	         } 
    	      }; 
	public String getSvrip() {
		return svrip;
	}

	public void setSvrip(String svrip) {
		this.svrip = svrip;
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		System.out.println("Applicaiton stoped!");
		
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		System.out.println("Applicaiton destoryed!");
		// TODO Auto-generated method stub

		WifiUtils.closeWifiAp();
		if(serverThread!=null){
		if(serverThread.closeServer())Toast.makeText(getApplicationContext(), "服务线程关闭", 0).show();;
		}
		if(clientThread!=null){
			//acceptThread.disconnect();
			clientThread.flag = false;
		}
		if(WifiUtils.wifiManager.isWifiEnabled()){
			WifiUtils.wifiManager.setWifiEnabled(false);
		}
		Toast.makeText(getApplicationContext(), "程序关闭", 0).show();
	
		
	}

	public static  int getTYPE() {
		return TYPE;
	}

	public static void setTYPE(int tYPE) {
		TYPE = tYPE;
	}
		public Socket getS() {
		return s;
	}

	public void setS(Socket s) {
		this.s = s;
	} 
	
}
