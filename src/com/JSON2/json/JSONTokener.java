package com.JSON2.json;

public class JSONTokener {
	public static JSON json = new JSON() ;
	public static Object parseObject(String str) {
		return json.parseObject(str) ;
	}
	public static String parseString(Object object){
		return json.parseString(object) ;
	}
}
