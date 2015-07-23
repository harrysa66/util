package com.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HttpUrl {

	protected  Log log = LogFactory.getLog(getClass());
	public  String getPageHtml(String strurl,String charcode){
		StringBuffer sb = new StringBuffer("");
		String s;
		try {			
			URL url = new URL(strurl);
			BufferedReader brTopfree = new BufferedReader(new InputStreamReader(
					url.openStream(), charcode));
			while ((s = brTopfree.readLine()) != null) {
				sb.append(s + "\r\n");
			}
			brTopfree.close();			
		}catch (Exception e)
		{
			log.error("访问地址strurl="+strurl+",编码charcode="+charcode+"时出错"+e.getMessage());
			return null;
		}
		return sb.toString();
	}
	
	protected  int REQUEST_TIMEOUT = 6*60*1000;
	protected  int CLICK_REQUEST_TIMEOUT = 10000;
	public  String getPageHtml1(String url,String charcode) {
//		String url="http://www.ip138.com:8080/search.asp?action=mobile&mobile="+phone;
		String ret = null;
		try {
			URL adreq_url = new URL(url);

			HttpURLConnection con = (HttpURLConnection) adreq_url
					.openConnection();
			con.setConnectTimeout(REQUEST_TIMEOUT);
			con.setReadTimeout(REQUEST_TIMEOUT);
//			con.set
			InputStream is = con.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is,charcode));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			return sb.toString().toLowerCase();

		} catch (Exception e) {
			log.error("getPageHtml1访问地址url="+url+",编码charcode="+charcode+"时出错"+e.getMessage());
		}
		return ret;
	}
	
	/**
	 * 读取一个网页全部内容
	 * 
	 * @throws Exception
	 */
	public  String getPageHtml(String strurl){
		return getPageHtml(strurl,"utf-8");
	}
}
