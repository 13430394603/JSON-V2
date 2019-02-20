package com.JSON2.json;

import com.JSON2.util.TypeToolsGenerics;
import com.JSON2.util.UnicodeUtil;
import com.JSON2.util.JSONAmbiguitychar;
/**
 * 
 * <b>JSON数据的处理<b>
 * <pre>
 * 处理的方式：
 * 	对象转换字符串
 * 	字符串转换成对象
 * </pre>
 * @author 威 
 * <br>2017年12月14日
 *
 */
public class JSON{
	private static JSON instance = new JSON();
	public static JSON getInstance(){
		return instance;
	}
	/**
	 * 
	 * 对象转换字符串 
	 * @see
	 * @return
	 * String
	 *
	 */
	public String parseString(Object object){ return _toString(object);}
	//将对象转换成字符串起始处
	private String _toString(Object obj){
		if(obj.getClass().getSimpleName().equals("JSONArray")){
			return _toStirngArray((JSONArray) obj);
		}
		return _toStringObject((JSONObject) obj);
	}
	//对JSONObject的处理方式
	private String _toStringObject(JSONObject obj){
		StringBuffer str = new StringBuffer(600);
		boolean sepBool = true;
		for(String key : obj.getKeySet()){
			str.append(sepBool ? "" : ", ");
			sepBool = false;
			str.append("\"" + key + "\":");
			Object value = obj.get(key);
			if(value.getClass().getSimpleName().equals("JSONArray")){
				str.append(_toStirngArray((JSONArray) value));
				continue;
			}
			else if(value.getClass().getSimpleName().equals("JSONObject")){
				str.append(_toStringObject((JSONObject) value));
				continue;
			}
			/*
			january 28,2019
			废弃原因：更简便的写法
			if(TypeToolsGenerics.getType(value).equals("int") 
					|| TypeToolsGenerics.getType(value).equals("boolean")
					|| TypeToolsGenerics.getType(value).equals("double")
					|| TypeToolsGenerics.getType(value).equals("long")
					|| TypeToolsGenerics.getType(value).equals("float"))
				str.append(value);   
			else
				//增加非法符的检测
				str.append("\"" + UnicodeUtil.parseU((String) value) + "\""); 
			*/
			//检查非法符 
			//之前是在put方法中进行检查，放在此处比较完善，避免不经过put而漏检查
			str	.append(value instanceof String 
					? "\"" + UnicodeUtil.parseU(JSONAmbiguitychar.doCheckAndReplace((String) value) + "\"") 
					: value);
		}
		return "{" + str.toString() + "}";
	}
	//对JSONArray的处理方式
	private String _toStirngArray(JSONArray arr){
		StringBuffer str = new StringBuffer(600);
		boolean sepBool = true;
		for(int i = 0; i < arr.get().size(); i ++){
			str.append(sepBool ? "" : ",");
			sepBool = false; 
			str.append(_toStringObject(arr.getJSONObject(i)));
		}
		return "[" + str.toString() + "]";
	}	
	
	/**
	 *
	 * 字符串转换成对象
	 * @see
	 * @return
	 * Object
	 *
	 */
	public Object parseObject(String str){ return _parseObject(str);}
	//判断处理方式
	private Object _parseObject(String str){
		char c = str.trim().charAt(0);
		if(c == '['){
			return _dealArray(str.substring(1, str.length()-1));
		}else if (c == '{'){
			return _dealObject(str.substring(1, str.length()-1));
		}else{
			//
			System.out.println("数据有误，str = " + str); 
		}
		return null;
	}
	/**
	 * 
	 * 数组处理方式 
	 * @see<pre>
	 * 参数
	 * 	去掉前后‘[’，‘]’字符
	 * </pre>
	 * @param str
	 * @return
	 * JSONArray
	 *
	 */
	private JSONArray _dealArray(String str){
		/*while((tempIndex = str.indexOf(",", tempIndex + 1)) != -1){
			if(!_isObjectEndCharOfArray(tempIndex + 1, str)) continue;
			selectIndex = str.indexOf('{', selectIndex);
			arr.add(_dealObject(str.substring(selectIndex + 1, str.lastIndexOf("}", tempIndex)).trim()));
			selectIndex = tempIndex + 1;
		}
		selectIndex = str.indexOf('{', selectIndex);
		arr.add(_dealObject(str.substring(selectIndex + 1, str.length() - 1)));
		return arr;*/
		int selectIndex = 0;
		int tempIndex = 0;
		JSONArray arr = new JSONArray();
		int endIndex;
		//while((tempIndex = str.indexOf(",", tempIndex + 1)) != -1){
		while(true){
			//if(_isObjectEndCharOfArray(tempIndex + 1, str)) continue;
			endIndex = _getEndIndex(str, selectIndex + 1, '{', '}');
			//selectIndex = str.indexOf('{', selectIndex);
			//alert("处理数组集字符串 ， endIndex - " + endIndex + "，总共的长度 - " + str.length);
			arr.add(_dealObject(str.substring(selectIndex + 1, endIndex).trim()));
			selectIndex = str.indexOf('{', endIndex);
			//selectIndex = tempIndex + 1;
			//alert("结束位置 - " + endIndex + "，总共的长度 - " + str.length + "，是否应该结束 - " + ((endIndex + 1) >= str.length));
			if((endIndex + 1) >= str.length()) return arr;
			
		}
	}
	//在数组集字符串中找对象字符串的结束标记
	/*private boolean _isObjectEndCharOfArray(int startIndex, String str){
		int index = startIndex;
		while(true){
			char c = str.charAt(index++);
			if(c == ' '){
				continue;
			}else if(c == '{'){
				return true;
			}
			return false;
		}
	}	*/
	/**
	 * 
	 * 对象处理方式
	 * @see<pre>
	 * 参数
	 * 	去掉前后‘{’，‘}’字符
	 * </pre>
	 * @param str
	 * @return
	 * JSONObject
	 *
	 */
	private JSONObject _dealObject(String str){
		int selectIndex = 0;
		int tempIndex = 0;
		String tempKey = "";		//对象中的属性
		Object tempValue = null;     //对象中的属性值
		JSONObject object = new JSONObject(); //{}
		while((tempIndex = str.indexOf(':', tempIndex + 1)) != -1){
			tempKey = str.substring(selectIndex, tempIndex).trim();
			selectIndex = tempIndex + 1;
			Character tempChar;
			if((tempChar = _isObject(selectIndex, str)) != null){
				int endIndex;
				if(tempChar == '{'){
					selectIndex = str.indexOf('{', selectIndex);
					endIndex = _getEndIndex(str, selectIndex + 1, '{', '}');
				}else{
					selectIndex = str.indexOf('[', selectIndex);
					endIndex = _getEndIndex(str, selectIndex + 1, '[', ']');
				}
				tempValue = _parseObject(str.substring(selectIndex, endIndex + 1).trim());
				tempIndex = endIndex;
				selectIndex = endIndex + 2;
			}else{
				int tempIndex_;
				int tempEndIndex_ = str.indexOf("}", selectIndex); //逗号符在括号之前才有效 january 26,2019
				tempIndex_ = str.indexOf(",", selectIndex);
				tempValue = str.substring(selectIndex, 
					(tempIndex_ == -1 
						? str.length() 
						: (tempEndIndex_ == -1 || tempIndex_ < tempEndIndex_ 
							? tempIndex_ 
							: tempIndex_-1
						)
					)
				);
				if(_isString((String) tempValue)){
					//tempValue = _rep((String) tempValue).trim(); january 28, 2019
					//原版本是通过JSONObject的putRedu去完成解析，现在直接在此处解析
					tempValue = JSONAmbiguitychar.doReducte(_rep((String) tempValue).trim());
				}else if(tempValue.equals("false") || tempValue.equals("true")){
					tempValue = Boolean.parseBoolean((String) tempValue);
				}
				else{
					if(((String) tempValue).indexOf(".") != -1)
						tempValue = Double.valueOf(((String) tempValue).trim());
					else tempValue = Integer.valueOf(((String) tempValue).trim());
				}
				selectIndex = tempIndex_ + 1; 
			}
			//object.putRedu(_rep(tempKey.trim()), tempValue); january 28, 2019
			//原版本是通过JSONObject的putRedu去完成解析
			object.put(_rep(tempKey.trim()), tempValue);
		}
		
		return object;
	}
		
	private boolean _isString(String value){
		return value.indexOf("\"") != -1 || value.indexOf("\'") != -1;
	}
	//将上引号去除
	private String _rep(String str){
		return UnicodeUtil.parseC(str.replaceAll("\"", ""));
	}	
	//判断对象的属性值是否为对象
	private Character _isObject(int startIndex, String str){
		int index = startIndex;
		while(true){
			char c = str.charAt(index++);
			if(c == ' '){
				continue;
			}else if(c == '[' || c == '{'){
				return c;
			}else{
				return null;
			}
		}
	}
	
	//当属性值为对象时，获取该对象字符串的结束位置
	private Integer _getEndIndex(String str, int startIndex, char c1, char c2){
		//alert("查找结束位置的开始 - " + startIndex);
		int selectIndex, tempIndex;
		selectIndex = tempIndex = startIndex;
		int index;
		while(true){
			//alert("查找结束位置处理中 selectIndex查找'" + c2 + "', selectIndex" + selectIndex + "， 长度" + str.length);
			selectIndex = str.indexOf(c2, selectIndex);
			index = str.indexOf(c1, tempIndex);	
			
			//alert("查找结束位置处理中 ， index - "+ index + ", selectIndex - " + selectIndex);
			if( index != -1 && index < selectIndex) {
				int index2 = str.indexOf(c1, index + 2); //是否还存在着c1字符 （每一次循环正常是只存在一个c1）
				//alert("index - " + index + ", index2 - " + index2);
				if(index2 != -1 && index2 < selectIndex)	//存在需要找到结束的位置
				{
					//alert("对象中包含对象 - " + index2);
					selectIndex = _getEndIndex(str, index + 1, c1, c2);
				}
				tempIndex = selectIndex = selectIndex + 1;
				continue;
			}
			return selectIndex;
		}
	}
	public static void main(String[] args){
		String str = "{\"orderList\":[{\"img\":\"../image/test/tako.jpg\", \"color\":\"绿色\", \"size\":\"L\", \"goodsId\":23, \"price\":189, \"name\":\"tako小衣\", \"count\":1, \"discount\":0.88, \"id\":1},{\"img\":\"../image/test/tako1.jpg\", \"color\":\"绿色\", \"size\":\"XL\", \"goodsId\":24, \"price\":188, \"name\":\"tako小衣\", \"count\":1, \"discount\":0.88, \"id\":2}], \"addresId\":2}";
		String num = "88.8";
		//System.out.println(Integer.valueOf(num)); 
		System.out.println(num.indexOf(".")!=-1);
	}
}
