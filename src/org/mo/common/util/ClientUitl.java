package org.mo.common.util;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by moziqi on 2015/3/4 0004.
 * http://blog.csdn.net/yang_734664103/article/details/22025907
 */
public class ClientUitl {
    private static HttpParams httpParams;
    private static DefaultHttpClient httpClient;
    public static String JSESSIONID; //定义一个静态的字段，保存sessionID
    public final static String LOGIN_TICKET = "loginTicket";
    public final static String EXECUTION = "execution";
    private final static String LOGIN_TICKET_EXECUTION_URL = "http://www.znyunxt.cn/cas/login?service=http://www.znyunxt.cn/ep&get-lt=true";
    public final static String VCODE_URL = "http://www.znyunxt.cn/cas/Kaptcha.jpg";

    public static HttpClient getHttpClient() {
        // 创建 HttpParams 以用来设置 HTTP 参数（这一部分不是必需的）
        httpParams = new BasicHttpParams();
        // 设置连接超时和 Socket 超时，以及 Socket 缓存大小
        HttpConnectionParams.setConnectionTimeout(httpParams, 20 * 1000);
        HttpConnectionParams.setSoTimeout(httpParams, 20 * 1000);
        HttpConnectionParams.setSocketBufferSize(httpParams, 8192);
        // 设置重定向，缺省为 true
        HttpClientParams.setRedirecting(httpParams, true);
        // 设置 user agent
        String userAgent = "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2) Gecko/20100115 Firefox/3.6";
        HttpProtocolParams.setUserAgent(httpParams, userAgent);
        // 创建一个 HttpClient 实例
        // 注意 HttpClient httpClient = new HttpClient(); 是Commons HttpClient
        // 中的用法，在 Android 1.5 中我们需要使用 Apache 的缺省实现 DefaultHttpClient
        httpClient = new DefaultHttpClient(httpParams);
        return httpClient;
    }

    public static String doGet(String url, Map params) throws Exception {
        /* 建立HTTPGet对象 */
        String paramStr = "";
        if (params != null) {
            Iterator iter = params.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                Object key = entry.getKey();
                Object val = entry.getValue();
                paramStr += paramStr = "&" + key + "=" + val;
            }
        }
        if (!paramStr.equals("")) {
            paramStr = paramStr.replaceFirst("&", "?");
            url += paramStr;
        }
        HttpGet httpRequest = new HttpGet(url);
        String strResult = "doGetError";
        /* 发送请求并等待响应 */
        HttpResponse httpResponse = getHttpClient().execute(httpRequest);
        /* 若状态码为200 ok */
        if (httpResponse.getStatusLine().getStatusCode() == 200) {
            /* 读返回数据 */
            strResult = EntityUtils.toString(httpResponse.getEntity());
        } else {
            strResult = "Error Response: " + httpResponse.getStatusLine().toString();
        }
        Log.v("strResult", strResult);
        return strResult;
    }


    public static HttpResponse userLogin(String url, Map params) throws IOException {
        /* 建立HTTPGet对象 */
        String paramStr = "";
        if (params != null) {
            Iterator iter = params.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                Object key = entry.getKey();
                Object val = entry.getValue();
                paramStr += paramStr = "&" + key + "=" + val;
            }
        }
        if (!paramStr.equals("")) {
            paramStr = paramStr.replaceFirst("&", "?");
            url += paramStr;
        }
        HttpGet httpRequest = new HttpGet(url);
//        httpRequest.getParams().setParameter("http.protocol.max-redirects",30);
        HttpResponse httpResponse = getHttpClient().execute(httpRequest);
        return httpResponse;
    }


    public static String doPost(String url, List<NameValuePair> params) throws Exception {
        /* 建立HTTPPost对象 */
        HttpPost httpRequest = new HttpPost(url);
        String strResult = "doPostError";
        /* 添加请求参数到请求对象 */
        if (params != null && params.size() > 0) {
            httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
        }
        if (null != JSESSIONID) {
            httpRequest.setHeader("Cookie", "JSESSIONID=" + JSESSIONID);
        }
        /* 发送请求并等待响应 */
        HttpResponse httpResponse = getHttpClient().execute(httpRequest);
        /* 若状态码为200 ok */
        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            /* 读返回数据 */
            strResult = EntityUtils.toString(httpResponse.getEntity());
            /* 获取cookieStore */
            CookieStore cookieStore = httpClient.getCookieStore();
            List<Cookie> cookies = cookieStore.getCookies();
            for (int i = 0; i < cookies.size(); i++) {
                if ("JSESSIONID".equals(cookies.get(i).getName())) {
                    JSESSIONID = cookies.get(i).getValue();
                    break;
                }
            }
        }
        Log.v("strResult", strResult);
        return strResult;
    }

    /**
     * (1)loginTicket跟execution用如下方法获取
     * http://www.znyunxt.cn/cas/login?service=http://www.znyunxt.cn/ep&get-lt=true
     * 会得到类似如下格式的字符串：
     * var _loginTicket = "LT-578-DV1UMHE1rEi5Lp1geIkuElnl2uoh6o-cas.znyunxt.cn"; var _execution = "e1s1";
     * 双引号中的字符串是变化的，其他不变，分析出双引号中的值，分别对应loginTicket\execution的值
     *
     * @return
     */
    public static Map<String, String> getVarValue() {
        Map<String, String> map = null;
        try {
            map = new HashMap<String, String>();
            String result = doPost(LOGIN_TICKET_EXECUTION_URL, null);
            String[] split = result.split(";");
            String substring = split[0].substring(split[0].indexOf("\"") + 1, split[0].lastIndexOf("\""));
            String substring1 = split[1].substring(split[1].indexOf("\"") + 1, split[1].lastIndexOf("\""));
            map.put(LOGIN_TICKET, substring);
            map.put(EXECUTION, substring1);
        } catch (Exception e) {
            map = null;
            e.printStackTrace();
        }
        return map;
    }

}