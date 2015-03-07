package org.mo.znyunxt.util;

import java.util.Map;

public class HttpUtil {
	public final static String AAA="aaaa";
	public static void requestUrl(String url,Map<String,String> param,DataCallBack dataCallBack){
		//do request
		String str = "请求获得的+参数";
		dataCallBack.onData(str);
	}
	
}
