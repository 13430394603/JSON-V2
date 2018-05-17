package com.JSON2.util;
/**
 * 
 * <b>UnicodeUtil ����װ����Unicode<b>
 * @author �� 
 * <br>2018��1��8�� ����12:37:21 
 *
 */
public class UnicodeUtil {
	public static String parseU(String str){
		if(str == null)
			return "" ;
		String result = "";  
        for (int i = 0; i < str.length(); i++) {  
            int chr1 = (char) str.charAt(i);  
            if (chr1 >= 19968 && chr1 <= 171941) {// ���ַ�Χ \u4e00-\u9fa5 (����)  
                result += "\\u" + Integer.toHexString(chr1);  
            } else {  
                result += str.charAt(i);  
            }  
        }
        return result ;  
	}
	//unicodeת�������ģ�����ɰ��������ַ���
	public static String parseC(String uStr){
		int start 	= 0, select  = 0;
		StringBuilder sb = new StringBuilder();
		if((select = uStr.indexOf("\\u")) == -1)
			return uStr;
		while(true) {
			select = uStr.indexOf("\\u", select);
			if(select == -1)
				break;
			if(start != select)
				sb.append(uStr.substring(start, select));
			String uStr_ = uStr.substring(select + 2, select + 6);
			char ch = (char) Integer.parseInt(uStr_, 16);
			sb.append(ch);
			select += 6;
			start = select;
		}
		if(start < uStr.length())
			sb.append(uStr.substring(start, uStr.length()));
		return sb.toString();
	}
	public boolean isChiness(char c){
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c) ;
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS  
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS  
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A  
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION  
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION  
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {  
            return true ;
        }  
        return false ;
	}
}
