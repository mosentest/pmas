package org.mo.znyunxt.util;

import java.util.HashMap;

public class Mylogic implements DataCallBack{
	String token;
	
	public void onCreate(){
		HttpUtil.requestUrl("www.baidu.com", new HashMap<String, String>(), this);
	}

	@Override
	public void onData(String str) {
		
	}
}
