package com.game.common.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class HttpClientUtils {
    /**
     * 普通的get数据方式
     *
     * @param url
     * @return
     * @throws HttpException
     * @throws IOException
     */
    public static String GetData(String url) throws HttpException, IOException {

        HttpClient client = new HttpClient();
        GetMethod method = null;
        StringBuffer stringBuffer = new StringBuffer();
        try {
            method = new GetMethod(url);
            method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 20000);
            client.executeMethod(method);
            InputStream inputStream = method.getResponseBodyAsStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String str = "";
            while ((str = br.readLine()) != null) {
                stringBuffer.append(str);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (null != method) {
                method.abort();
                method.releaseConnection();
            }
        }
        return stringBuffer.toString();
    }

    /**
     * 普通的post数据方式
     *
     * @param url
     * @param data
     * @return
     * @throws HttpException
     * @throws IOException
     */
    public static String PostData(String url, List<NameValuePair> data) throws HttpException, IOException {
        HttpClient client = new HttpClient();
        PostMethod method = null;
        String ret = "";
        try {
            client.getHttpConnectionManager().getParams().setSoTimeout(2000);
            method = new PostMethod(url);
            method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
            method.getParams().setParameter("content-type", "json");
            method.setRequestBody(data.toArray(new NameValuePair[0]));
            client.executeMethod(method);
            BufferedReader reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream()));
            StringBuffer stringBuffer = new StringBuffer();
            String str = "";
            while ((str = reader.readLine()) != null) {
                stringBuffer.append(str);
            }
            ret = stringBuffer.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (null != method) {
                method.abort();
                method.releaseConnection();
            }
        }
        return ret;
    }
}
