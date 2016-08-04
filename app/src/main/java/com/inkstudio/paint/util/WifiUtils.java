package com.inkstudio.paint.util;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import com.inkstudio.paint.appbase.BaseApplication;
import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

public class WifiUtils
{
    public static WifiManager wifiManager = (WifiManager) (BaseApplication.getInstance()).getSystemService(Context.WIFI_SERVICE);
    private static WifiInfo wifiInfo;
   public static ConnectivityManager connManager =(ConnectivityManager) BaseApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);;
    private static List<WifiConfiguration> wifiConfigurationlist;
    public static DhcpInfo wifiDhcpInfo;
    @SuppressLint("ServiceCast")
	public static Context context ;
   
    public static  String intToIp(int i) {         
        
        return (i & 0xFF ) + "." +         
      ((i >> 8 ) & 0xFF) + "." +         
      ((i >> 16 ) & 0xFF) + "." +         
      ( i >> 24 & 0xFF) ;   
   }
    
    private  WifiUtils(Context context)
    {
    	this.context = context;
    	//wifiInfo.
    // 鍙栭敓鏂ゆ嫹WifiManager閿熸枻鎷烽敓鏂ゆ嫹
    wifiManager = (WifiManager) context
        .getSystemService(Context.WIFI_SERVICE);
    connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public WifiInfo getWifiConnectInfo()
    {
    wifiInfo = wifiManager.getConnectionInfo();
    return wifiInfo;
    }

    public static List<String> getScanResult()
    {
    	List<String> l = new ArrayList<String>();
    	l.clear();
    	Log.i("wifi鐑偣鏁伴噺",	 wifiManager.getScanResults().size()+"");
    	List<ScanResult> sr=wifiManager.getScanResults();
    	
    	for (ScanResult scanResult : sr) {
    		//System.out.println("SSID: "+scanResult.SSID);
    		//if(scanResult.SSID.contains("MobileBoard_")){
    			l.add(scanResult.SSID);
    			System.out.println("SSID GROUP->:"+scanResult.SSID);
    	//	}
		}
    	return l;
    
    }

    public List<WifiConfiguration> getConfiguration()
    {
    wifiConfigurationlist = wifiManager.getConfiguredNetworks();
    return wifiConfigurationlist;
    }

    public static DhcpInfo getDhcpInfo()
    {
    wifiDhcpInfo = wifiManager.getDhcpInfo();
    return wifiDhcpInfo;
    }

    /**
     * 閿熸枻鎷烽敓鏂ゆ嫹閿熼ズ纰夋嫹閿熸枻鎷蜂负閿熸枻鎷烽敓鏂ゆ嫹璇撮敓鏂ゆ嫹閿熸枻鎷烽敓锟�
     * 
     * @param ssid
     * @param passwd
     * @param type
     * @return
     */
    public static WifiConfiguration getCustomeWifiConfiguration(String ssid,
        String passwd, int type)
    {
    WifiConfiguration config = new WifiConfiguration();
    config.allowedAuthAlgorithms.clear();
    config.allowedGroupCiphers.clear();
    config.allowedKeyManagement.clear();
    config.allowedPairwiseCiphers.clear();
    config.allowedProtocols.clear();
    config.SSID = ssid;
    if (type == 1) // NOPASS
    {
        config.wepKeys[0] = "";
        config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        config.wepTxKeyIndex = 0;
    }
    if (type == 2) // WEP
    {
        config.hiddenSSID = true;
        config.wepKeys[0] = passwd;
        config.allowedAuthAlgorithms
            .set(WifiConfiguration.AuthAlgorithm.SHARED);
        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
        config.allowedGroupCiphers
            .set(WifiConfiguration.GroupCipher.WEP104);
        config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        config.wepTxKeyIndex = 0;
    }
    if (type == 3) // WPA
    {
        config.preSharedKey = passwd;
        config.hiddenSSID = true;
        config.allowedAuthAlgorithms
            .set(WifiConfiguration.AuthAlgorithm.OPEN);
        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
        config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
        config.allowedPairwiseCiphers
            .set(WifiConfiguration.PairwiseCipher.TKIP);
        // config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
        config.allowedPairwiseCiphers
            .set(WifiConfiguration.PairwiseCipher.CCMP);
        config.status = WifiConfiguration.Status.ENABLED;
    }
    if (type == 4) // WPA2psk test
    {
        config.preSharedKey = passwd;
        config.hiddenSSID = true;

        config.status = WifiConfiguration.Status.ENABLED;
        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
        config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
        config.allowedPairwiseCiphers
            .set(WifiConfiguration.PairwiseCipher.TKIP);
        config.allowedPairwiseCiphers
            .set(WifiConfiguration.PairwiseCipher.CCMP);
        config.allowedProtocols.set(WifiConfiguration.Protocol.RSN);

    }
    return config;

    }

    public static void connectWifi(String ssid,String passwd,int type){
    
    	if(isWifiApEnabled()){
    		closeWifiAp();
    		if(!wifiManager.isWifiEnabled()){
        		wifiManager.setWifiEnabled(true);
    	}
    	}
    	WifiConfiguration netConfig = 
                getCustomeWifiClientConfiguration(ssid, passwd,3);
            int wcgID = wifiManager.addNetwork(netConfig);
            boolean b = wifiManager.enableNetwork(wcgID, true);
            System.out.println("state:"+b);
            wifiManager.reconnect();
           
     
    	
    }
    public static  boolean isWifiConnected(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if(wifiNetworkInfo.isConnected())
        {
            return true ;
        }
     
        return false ;
    }
    /**
     * 閿熼叺浼欐嫹閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹鑼敓鏂ゆ嫹閿熻娇顏庢嫹閿熸枻鎷烽敓鏂ゆ嫹閿熻銈忔嫹閿熸枻鎷烽敓锟�
     * ssid閿熸枻鎷穚asswd 閿熸枻鎷烽敓鏂ゆ嫹鍓嶉敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷峰弻閿熸枻鎷烽敓鑴氣槄鎷�
     * @param ssid
     * @param passwd
     * @param type
     * @return
     */
    public static WifiConfiguration getCustomeWifiClientConfiguration(String ssid,
        String passwd, int type)
    {
    WifiConfiguration config = new WifiConfiguration();
    config.allowedAuthAlgorithms.clear();
    config.allowedGroupCiphers.clear();
    config.allowedKeyManagement.clear();
    config.allowedPairwiseCiphers.clear();
    config.allowedProtocols.clear();
    //鍙岄敓鏂ゆ嫹閿熻剼鎲嬫嫹閿熸枻鎷�
    config.SSID = "\"" + ssid + "\"";
    if (type == 1) // WIFICIPHER_NOPASS
    {
        config.wepKeys[0] = "";
        config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        config.wepTxKeyIndex = 0;
    }
    if (type == 2) // WIFICIPHER_WEP
    {
        config.hiddenSSID = true;
         config.wepKeys[0] = "\"" + passwd + "\"";
        config.allowedAuthAlgorithms
            .set(WifiConfiguration.AuthAlgorithm.SHARED);
        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
        config.allowedGroupCiphers
            .set(WifiConfiguration.GroupCipher.WEP104);
        config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        config.wepTxKeyIndex = 0;
    }
    if (type == 3) // WIFICIPHER_WPA
    {
        config.preSharedKey = "\"" + passwd + "\"";
        config.hiddenSSID = true;
        config.allowedAuthAlgorithms
            .set(WifiConfiguration.AuthAlgorithm.OPEN);
        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
        config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
        config.allowedPairwiseCiphers
            .set(WifiConfiguration.PairwiseCipher.TKIP);
        // config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
        config.allowedPairwiseCiphers
            .set(WifiConfiguration.PairwiseCipher.CCMP);
        config.status = WifiConfiguration.Status.ENABLED;
    }
    if (type == 4) // WPA2psk test
    {
        config.preSharedKey = "\"" + passwd + "\"";
        config.hiddenSSID = true;

        config.status = WifiConfiguration.Status.ENABLED;
        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
        config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
        config.allowedPairwiseCiphers
            .set(WifiConfiguration.PairwiseCipher.TKIP);
        config.allowedPairwiseCiphers
            .set(WifiConfiguration.PairwiseCipher.CCMP);
        config.allowedProtocols.set(WifiConfiguration.Protocol.RSN);

    }
    return config;

    }

    public static Boolean stratWifiAp(String ssid, String psd, int type)
    {
    	if(wifiManager.isWifiEnabled())
    		wifiManager.setWifiEnabled(false);
    Method method1 = null;
    try
    {
        method1 = wifiManager.getClass().getMethod("setWifiApEnabled",
            WifiConfiguration.class, boolean.class);
        WifiConfiguration netConfig = getCustomeWifiConfiguration(ssid,
            psd, type);

        method1.invoke(wifiManager, netConfig, true);
        return true;
    }
    catch (Exception e)
    {
        e.printStackTrace();
        return false;
    }
    }

    public static void closeWifiAp()
    {
    if (isWifiApEnabled())
    {
        try
        {
        Method method = wifiManager.getClass().getMethod(
            "getWifiApConfiguration");
        method.setAccessible(true);

        WifiConfiguration config = (WifiConfiguration) method
            .invoke(wifiManager);

        Method method2 = wifiManager.getClass().getMethod(
            "setWifiApEnabled", WifiConfiguration.class,
            boolean.class);
        method2.invoke(wifiManager, config, false);
        }
        catch (NoSuchMethodException e)
        {
        e.printStackTrace();
        }
        catch (IllegalArgumentException e)
        {
        e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
        e.printStackTrace();
        }
        catch (InvocationTargetException e)
        {
        e.printStackTrace();
        }
    }
    }

    public static boolean isWifiApEnabled()
    {
    try
    {
        Method method = wifiManager.getClass().getMethod("isWifiApEnabled");
        method.setAccessible(true);
        return (Boolean) method.invoke(wifiManager);

    }
    catch (NoSuchMethodException e)
    {
        e.printStackTrace();
    }
    catch (Exception e)
    {
        e.printStackTrace();
    }

    return false;
    }
}