package com.JSON2.json;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
/**
 * 
 * @ClassName: JSONArray 
 * @Description: TODO(负责接收JSONObject对象并传给JSONTokener - 了解众多类详情可以读取readme) 
 * @author 威 
 * @date 2017年12月14日
 *
 */
public class JSONArray{
	//JSONObject对象集
	private List<JSONObject> containerlists = new ArrayList<JSONObject>() ;
	/**
	 * 
	 * @Title: put 
	 * @Description: TODO(获取json字符串数据) 
	 * @param json
	 * void
	 *
	 */
	public void add(JSONObject json){
		containerlists.add(json) ;
	}
	
	/**
	 * 
	 * @Title: length 
	 * @Description: TODO(获取JSONArray长度) 
	 * @return
	 * int
	 *
	 */
	public int length(){
		return containerlists.size() ;
	}
	/**
	 * 
	 * 获取JSONArray对象集中下标为index的JSONObject对象
	 * @see
	 * @param index
	 * @return
	 * JSONObject
	 *
	 */
	public JSONObject getJSONObject(int index) {
		return containerlists.get(index) ;
	}
	/**
	 * 
	 * 获取整个对象集 的List集合格式返回
	 * @see
	 * @return
	 * List<JSONObject>
	 *
	 */
	public List<JSONObject> get(){
		return containerlists ;
	}
	
	public String toString(){
		return new JSON().parseString(this) ;
	}
	
	public void iterator(){
		containerlists.iterator();
	} 
	public void forEach(Consumer<? super JSONObject> action){
		containerlists.forEach(action);
	}
}
