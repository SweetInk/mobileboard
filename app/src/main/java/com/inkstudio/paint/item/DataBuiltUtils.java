package com.inkstudio.paint.item;

import java.util.ArrayList;
import java.util.HashMap;

public class DataBuiltUtils {

	public static ArrayList<HashMap<String,String>> getMainMapList(){
		String [] menus={"颜色","画笔","文字","随笔","矩形","圆","直线","橡皮","清空"};
		ArrayList<HashMap<String, String>> tempMapList = new ArrayList<HashMap<String,String>>();
		
		for(String str:menus){
			HashMap<String, String> tempMap = new HashMap<String, String>();
			tempMap.put("menu", str);
			tempMapList.add(tempMap);
		}
		return tempMapList;
	}
}