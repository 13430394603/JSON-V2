package com.JSON2.test;

import org.junit.Test;

import com.JSON2.json.JSON;
import com.JSON2.json.JSONArray;
import com.JSON2.json.JSONObject;
import com.JSON2.util.TypeToolsGenerics;
import com.JSON2.util.UnicodeUtil;
/*
 	html使用案例：
 	var obj = JSON.parse(text) ;
	var len = 0 ;
	for(var name in obj){
		alert(obj[len].name)
		len++ ;
	}
*/
public class test {
	public static void main(String[] args){
		String str = "[{\"index\":34, \"name\":[{\"index\":40, \"name\":\"yy\"}]}]" ;
		JSON p = new JSON() ;
		Object obj = p.parseObject(str) ;
		//测试生成对象中的属性值是对象
		JSONObject o = new JSONObject() ;
		o.put("id", 1) ;
		o.put("name", "陈继威") ;
		o.put("age", 18) ;
		JSONObject o1 = new JSONObject() ;
		o1.put("昵称", "亮子") ;
		o1.put("座右铭", "\"习惯性优秀\"") ;
		o.put("object", o1) ;
		//测试把生成的对象转换成字符串
		System.out.println(p.parseString(o)) ;
		//测试把字符串转换成对象后转换成字符串
		System.out.println(p.parseString(p.parseObject(p.parseString(o)))) ;
		//测试创建数组集对象 内部属性值有对象  最后转换成字符串
		JSONArray arr = new JSONArray() ;
		arr.add(o) ;
		
		System.out.println(p.parseString(arr)) ;
		
		//测试创建数组集对象 内部属性值包含数组集和对象 最后将对象转换成字符串后转换成对象，又将对象转换成字符串
		JSONArray arr1 = new JSONArray() ;
		
		//属性值中的数组集对象
		JSONArray arr2 = new JSONArray() ;
		JSONObject o2 = new JSONObject() ;
		o2.put("name", "亮子") ;
		
		//属性值中的数组集对象
		JSONArray arr3 = new JSONArray() ;
		JSONObject o3 = new JSONObject() ;
		o3.put("name", "亮子") ;
		o3.put("id", "习惯性优秀") ;
		arr3.add(o3) ;
		
		o2.put("id", arr3) ;
		
		arr2.add(o2) ;
		
		o.put("object", arr2) ;
		arr1.add(o) ;
		
		System.out.println(((JSONArray)p.parseObject(p.parseString(arr1))).getJSONObject(0)) ;
		//测试 将字符串转换成对象，又将对象转换成字符串
		System.out.println(p.parseString(p.parseObject(p.parseString(arr1)))) ;
		
		
		JSONArray arr6 = new JSONArray() ;
		JSONObject obj6 = new JSONObject() ;
		obj6.put("id", "haha") ;
		obj6.put("name", "haha") ;
		obj6.put("price", "haha") ;
			obj6.put("sizes", "haha") ;
			obj6.put("colors", "haha") ;
			arr6.add(obj6) ;
		System.out.println(new JSON().parseString(arr6)) ;
		String str10 = "[{\"obj\":{\"id\":0000, \"time\":\"2017-12-26 18#3A#55\", \"money\":10000}, \"busi\":[{\"storeName\":\"衣品之家\", \"deliveryState\":false, \"progressState\":true, \"logistics\":{\"shipAddress\":\"广东省-广州市-广东白云学院\", \"receiptAddress\":\"广西省-北海市-贵海花园一期\"}, \"goods\":[{\"id\":0001, \"path\":\"../image/test/tako5.jpg\", \"name\":\"好看的上衣\", \"price\":50, \"size\":\"L\", \"color\":\"粉红\", \"count\":2},{\"id\":0002, \"path\":\"../image/test/tako5.jpg\", \"name\":\"好看的上衣2\", \"size\":\"L\", \"price\":280, \"color\":\"粉红\", \"count\":3}]}]}]" ;
		System.out.println(((JSONArray) p.parseObject(str10)).toString()) ;
		
		boolean flag = false ;
		System.out.println(TypeToolsGenerics.getType(flag)) ;
		
		//System.out.println(UnicodeUtil.getInstance().parseC("\u5a01")) ;
		char letter = (char) Integer.parseInt("\u5a01", 16) ;
		System.out.println(letter) ;
		
	}
}
