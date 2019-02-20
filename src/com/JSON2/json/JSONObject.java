package com.JSON2.json;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.JSON2.util.JSONAmbiguitychar;

import java.util.Set;
/**
 * 
 * @ClassName: JSONObject 
 * @Description: TODO(接收一组键值对数据) 
 * @author 威 
 * @date 2017年12月14日
 *
 */
public class JSONObject{
	private Map<String, Object> map = new HashMap<String, Object>();
	/**
	 * 
	 * 此方法会进行字符冲突处理
	 * @see
	 * @param key
	 * @param value
	 * void
	 *
	 */
	public void put(String key, Object value) {
		if(value == null)
			value = "";
		/*
		january 28, 2019
		已经在JSON中进行检查
		else if(value instanceof String)
			value = JSONAmbiguitychar.doCheckAndReplace((String) value);
		*/
		map.put(key, value);
	}
	/**
	 * 
	 * 执行此方法时输入的数据将被还原成该数据的原始状态 ( 即可能带有冲突字符)
	 * @see<pre>
	 * 	此方法的执行处一般会在JSON对象中将字符串转换成对象时
	 * <pre>
	 * @param key
	 * @param value
	 * void
	 *
	 */
	/*
	january 28, 2019
	public void putRedu(String key, Object value) {
		if(value == null)
			value = "";
		else if(value instanceof String)
			value = JSONAmbiguitychar.doReducte((String) value);
		map.put(key, value);
	}
	*/
	/**
	 * 
	 * 未进行字符冲突还原
	 * @see
	 * 如果对象时通过JSON对象中将字符串转换而来， 那么已经进行过冲突还原
	 * @param key
	 * @return
	 * Object
	 *
	 */
	public Object get(String key){
		return map.get(key);
	}
	/**
	 * 
	 * 进行字符冲突还原 废弃
	 * @see
	 * 此对象为new出来，而不是通过parseObject方法而来
	 * @param key
	 * @return
	 * Object
	 *
	 */
	/*
	january 28, 2019
	public Object getRedu(String key){
		Object value = get(key);
		if(value instanceof String)
			value = JSONAmbiguitychar.doReducte((String) value);
		return value;
	}
	*/
	/**
	 * 
	 * 获取entrySet
	 * @see
	 * @return
	 * Set<Entry<String,Object>>
	 *
	 */
	public Set<Entry<String, Object>> getSet(){
		return map.entrySet();
	}
	/**
	 * 
	 * 获取keySet
	 * @see
	 * @return
	 * Set<String>
	 *
	 */
	public Set<String> getKeySet(){
		return map.keySet();
	}
	public String toString(){
		return new JSON().parseString(this);
	}
}