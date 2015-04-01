package org.mo.common.util;

import com.loopj.android.http.AsyncHttpClient;

import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultRedirectHandler;
import org.apache.http.protocol.HttpContext;

/**
 * Created by moziqi on 2015/3/31 0031.
 */
public class MyAsyncHttpClient extends AsyncHttpClient {
    @Override
    public void setEnableRedirects(final boolean enableRedirects) {
        super.setEnableRedirects(enableRedirects);
        ((DefaultHttpClient) getHttpClient()).setRedirectHandler(new DefaultRedirectHandler() {
            @Override
            public boolean isRedirectRequested(HttpResponse response, HttpContext context) {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 301 || statusCode == 302) {
                    return enableRedirects;
                }
                return enableRedirects;
            }
        });
    }
}
