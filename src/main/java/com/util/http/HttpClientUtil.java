package com.util.http;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil {

	static Log log = LogFactory.getLog(HttpClientUtil.class);
//	static HttpClient httpClient = new HttpClient();
	/**
	 * get http contenr buy URL
	 * @param URL URL 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static synchronized String getHttpContent(String URL) {
		//if the URL is null.return empty
		if (URL == null)
			return "";
		else {
			if (URL.toLowerCase().indexOf("http") == -1) {
				URL = "http://" + URL;
				URL = URL.toLowerCase();
			}

			
			HttpClient httpClient = new HttpClient();
			httpClient.setHttpConnectionFactoryTimeout(1000);
			
			GetMethod getMethod = new GetMethod(URL);
			
			getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
					new DefaultHttpMethodRetryHandler());
			try {
				// execute getMethod
				int statusCode = httpClient.executeMethod(getMethod);
				
				if (statusCode != HttpStatus.SC_OK) {
					return "";
				} else {
					// get content
//					byte[] responseBody = getMethod.getResponseBody();
//					return new String(responseBody,"utf-8");
					
					String res=getMethod.getResponseBodyAsString();
					
					return res;
					 
				}

			} catch (HttpException e) {
				e.printStackTrace();
				return "";
			} catch (IOException e) {
				return "";
			} finally {
				// Release Connection
				getMethod.releaseConnection();
			}

		}
	}
	

	protected static int REQUEST_TIMEOUT = 10*1000;
	protected static int CLICK_REQUEST_TIMEOUT = 10*1000;
		public static  String getPageHtml(String strurl,String charcode){
			StringBuffer sb = new StringBuffer("");
			String s;
			try {			
				URL url = new URL(strurl);
				BufferedReader brTopfree = new BufferedReader(new InputStreamReader(
						url.openStream(), charcode));
				
				while ((s = brTopfree.readLine()) != null) {
					sb.append(s);
				}
				brTopfree.close();			
			}catch (Exception e)
			{
				return null;
			}
			return sb.toString();
		}
	
	public static String getPageHtml(String url) throws Exception {
		String ret = null;
		try {
			URL adreq_url = new URL(url);

			HttpURLConnection con = (HttpURLConnection) adreq_url
					.openConnection();
			con.setConnectTimeout(REQUEST_TIMEOUT);
			con.setReadTimeout(REQUEST_TIMEOUT);

			con.setRequestProperty("Content-Type", "text/html;charset=UTF-8"); 
			con.setRequestProperty("Charsert", "UTF-8");
			con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			InputStream is = con.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is,"utf-8"));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			ret=sb.toString();

		} catch (Exception e) {
			throw new Exception("访问地址strurl="+url+",编码charcode=utf-8时出错"+e.getMessage());
		}
		return ret;
	}
	public static String getPageHtml(String url,Map<String, String> paraMap) throws Exception {
		StringBuilder urlPara=new StringBuilder();
		Set<String> keyset= paraMap.keySet();
		for (String key : keyset) {
			String value=paraMap.get(key);
			if(urlPara.length()>0){
				urlPara.append("&");
			}
			urlPara.append(key).append("=").append(value);
		}
		if(urlPara.length()>0){
			url=url+"?"+urlPara.toString();
		}
		return getPageHtml(url);
	}
	public static String getPageHtml_Post(String url) throws Exception {
		String ret = null;
		try {
			String urlPage=null;
			String urlPara=null;
			if(url!=null){
				int pos=url.indexOf("?");
				if(pos>0){
					urlPage=url.substring(0,pos);
					urlPara=url.substring(pos+1);
				}
				URL adreq_url = new URL(urlPage);
				
				HttpURLConnection con = (HttpURLConnection) adreq_url
				.openConnection();
				con.setRequestMethod("POST");// 提交模式
				con.setConnectTimeout(REQUEST_TIMEOUT);
				con.setReadTimeout(REQUEST_TIMEOUT);
				con.setRequestProperty("Content-Type",
	                "application/x-www-form-urlencoded");
				con.setRequestProperty("Charsert", "UTF-8");
				con.setDoOutput(true);
				con.setDoInput(true);// 是否输入参数
				con.setUseCaches(false);
				
				byte[] bypes = urlPara.getBytes("utf-8");
		        con.getOutputStream().write(bypes);// 输入参数
		        
		        
				InputStream is = con.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(is,"utf-8"));
				String line = null;
				StringBuilder sb = new StringBuilder();
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
				ret=sb.toString();
			}

		} catch (Exception e) {
			log.debug("访问地址strurl="+url+",编码charcode=utf-8时出错"+e.getMessage());
		}
		return ret;
	}
	
	public static String getPageHtml_Post(String url,Map<String, String> paraMap) throws Exception {
		String ret = null;
		try {
			String urlPage=url;
			StringBuilder urlPara=new StringBuilder();
			if(urlPage!=null){
				Set<String> keyset= paraMap.keySet();
				for (String key : keyset) {
					String value=paraMap.get(key);
					if(urlPara.length()>0){
						urlPara.append("&");
					}
					urlPara.append(key).append("=").append(URLEncoder.encode(value));
					
				}
				URL adreq_url = new URL(urlPage);
				
				HttpURLConnection con = (HttpURLConnection) adreq_url
				.openConnection();
				con.setRequestMethod("POST");// 提交模式
				con.setConnectTimeout(REQUEST_TIMEOUT);
				con.setReadTimeout(REQUEST_TIMEOUT);

				
				
				con.setRequestProperty("Content-Type",
	                "application/x-www-form-urlencoded");
				con.setRequestProperty("Charsert", "UTF-8");
				con.setDoOutput(true);
				con.setDoInput(true);// 是否输入参数
				con.setUseCaches(false);
				
				byte[] bypes = urlPara.toString().getBytes("utf-8");
		        con.getOutputStream().write(bypes);// 输入参数
		        
		        
				InputStream is = con.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(is,"utf-8"));
				String line = null;
				StringBuilder sb = new StringBuilder();
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
				ret=sb.toString();
			}

		} catch (Exception e) {
			log.debug("访问地址strurl="+url+",编码charcode=utf-8时出错"+e.getMessage());
		}
		return ret;
	}
	

	
	public static String getPageHtml_PostJsonBody(String url,String body) throws Exception {
		String ret = null;
		try {
			String urlPage=null;
			String urlPara=null;
			if(url!=null){


				urlPage=url;
				urlPara=body;

				URL adreq_url = new URL(urlPage);
				
				HttpURLConnection con = (HttpURLConnection) adreq_url
				.openConnection();
				con.setRequestMethod("POST");// 提交模式
				con.setConnectTimeout(REQUEST_TIMEOUT);
				con.setReadTimeout(REQUEST_TIMEOUT);
				con.setRequestProperty("Content-Type","application/json");
				con.setRequestProperty("Charset", "UTF-8");
				con.setDoOutput(true);
				con.setDoInput(true);// 是否输入参数
				con.setUseCaches(false);
				
				byte[] bypes = urlPara.getBytes("utf-8");
		        con.getOutputStream().write(bypes);// 输入参数
		        
		        
				InputStream is = con.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(is,"utf-8"));
				String line = null;
				StringBuilder sb = new StringBuilder();
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
				ret=sb.toString();
			}

		} catch (Exception e) {
			log.debug("访问地址strurl="+url+",编码charcode=utf-8时出错"+e.getMessage());
		}
		return ret;
	}
	
	public static String doGet(String api_url) {
		URL url;
		HttpURLConnection urlConnection;
		StringBuffer content = new StringBuffer();
		try {
			url = new URL(api_url);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
			urlConnection.setRequestProperty("charset", "UTF-8");
			urlConnection.setDoOutput(true);
			urlConnection.setDoInput(true);
			urlConnection.setUseCaches(false);
			// 获取响应结果
			InputStream in = urlConnection.getInputStream();

			byte[] temp = new byte[4096];
			for (int len; (len = in.read(temp)) != -1;) {
				content.append(new String(temp, 0, len, "utf-8"));
			}

		} catch (MalformedURLException e1) {
			content.append(e1.getMessage());
			e1.printStackTrace();
		} catch (IOException e) {
			content.append(e.getMessage());
			e.printStackTrace();
		}
		return content.toString();
	}
	
	public static String getString(String url) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		try {
			HttpResponse response = httpClient.execute(httpGet);
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity httpEntity = response.getEntity();
				String charset = EntityUtils.getContentCharSet(httpEntity);
				if(charset == null){
					charset = "utf-8";
				}
				return dump(httpEntity, charset);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return "";
	}
	
	
	public static String dump(HttpEntity entity, String charset) throws IOException {
		String resulthtmlsource = "";
		InputStream input = entity.getContent();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(input, charset));
			resulthtmlsource = IOUtils.toString(br);
		} catch (Exception e) {
			e.printStackTrace();
			resulthtmlsource = "";
			return null;
		} finally {
			IOUtils.closeQuietly(input);
		}
		System.out.println(resulthtmlsource);
		return resulthtmlsource;
	}
	
	public static boolean test(String url) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		try {
			long startTime = System.currentTimeMillis();
			HttpResponse response = httpClient.execute(httpGet);
			System.out.println(System.currentTimeMillis()-startTime);
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				return true;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return false;
	}
	
	public static String post(String url, String json) throws ClientProtocolException, IOException {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		StringEntity s = new StringEntity(json, "UTF-8");
		s.setContentType("application/json");
		post.setEntity(s);

		Long startTime = System.currentTimeMillis();
		HttpResponse res = client.execute(post);
		System.out.println(System.currentTimeMillis() - startTime);
		if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			HttpEntity entity = res.getEntity();
			String charset = EntityUtils.getContentCharSet(entity);
			if(charset == null){
				charset = "utf-8";
			}
			return dump(entity,charset);
		}
		return "";
	}
	
	
	public static void main(String[] args) {
		try {
			String s=getString("http://test.zhangkuo.net/adwoadmin/business/business_getBusinessName.do?orderid=123");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


	
}
