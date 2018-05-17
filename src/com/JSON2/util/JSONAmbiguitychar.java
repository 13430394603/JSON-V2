package com.JSON2.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
/**
 * 
 * <b>处理JSON数据中冲突字符<b>
 * @author 威 
 * <br>2017年12月15日
 * @see<pre>
 * 冲突字符的处理
	左边是原字符 右边为转换
	[ - #5B#， 
	] - #5D#， 
	{ - #7B#， 
	} - #7D#， 
	: - #3A#， 
	" - #22#， 
	' - #27#，
	, - #28#，
 * </pre>
 */
public class JSONAmbiguitychar {
	private static Map<String, String> mapCode = new HashMap<String, String>() ;
	static{
		mapCode.put("[", "#5B#") ;
		mapCode.put("]", "#5D#") ;
		mapCode.put("{", "#7B#") ;
		mapCode.put("}", "#7D#") ;
		mapCode.put(":", "#3A#") ;
		mapCode.put("\"", "#22#") ;
		mapCode.put("\'", "#27#") ;
		mapCode.put(",", "#28#") ;
	}
	public static String doCheckAndReplace (String str){
		for(Entry<String, String> item : mapCode.entrySet()){
			if(str.indexOf(item.getKey()) != -1)
				str = str.replaceAll(item.getKey(), item.getValue()) ;
		}
		return str ;
	}
	public static String doReducte(String str){
		for(Entry<String, String> item : mapCode.entrySet()){
			String value = item.getValue() ;
			if(str.indexOf(value) != -1){
				str = str.replaceAll(value,  item.getKey()) ;
			}	
		}
		return str ;
	}
}
