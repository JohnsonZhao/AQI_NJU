package edu.nju.aqi.meta;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Map;

public class HttpRequestUtils {

		private static String charset = "utf-8";
		private static String proxyHost = null;
		private static Integer proxyPort = null;
		
		public static String doGet(String url) throws Exception {
			URL localURL = new URL(url);
			URLConnection connection = openConnection(localURL);
			HttpURLConnection httpURLConnection  = (HttpURLConnection) connection;
			
			httpURLConnection.setRequestProperty("Accept-Charset", charset);
			httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			
			InputStream inputStream = null;
			InputStreamReader inputStreamReader = null;
			BufferedReader reader = null;
			StringBuffer result = new StringBuffer();
			String tmpLine = null;
			
			if (httpURLConnection.getResponseCode() >= 300) {
				throw new Exception("HTTP request is not success, error code is " + httpURLConnection.getResponseCode());
			}
			
			try{
				inputStream = httpURLConnection.getInputStream();
				inputStreamReader = new InputStreamReader(inputStream);
				reader = new BufferedReader(inputStreamReader);
				
				while ((tmpLine = reader.readLine()) != null) {
					result.append(tmpLine);
				}
			}finally{
				if (reader != null) {
					reader.close();
				}
				if (inputStreamReader != null) {
					inputStreamReader.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
			}
			return result.toString();
		}
		
		public static String doPost(String url, Map<String, String> paramterMap) throws Exception{
			StringBuffer buffer = new StringBuffer();
			if (paramterMap != null) {
				Iterator<String> iterator = paramterMap.keySet().iterator();
				String key = null;
				String value = null;
				while (iterator.hasNext()) {
					key = iterator.next();
					value = paramterMap.get(key) == null ? "" : (String) paramterMap.get(key);
					buffer.append(key).append("=").append(value);
					if (iterator.hasNext()) {
						buffer.append("&");
					}
				}
			}
			URL localURL = new URL(url);
			URLConnection connection = openConnection(localURL);
			HttpURLConnection httpURLConnection = (HttpURLConnection) connection;

			httpURLConnection.setDoOutput(true);
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setRequestProperty("Accept-Charset", charset);
	        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	        httpURLConnection.setRequestProperty("Content-Length", String.valueOf(buffer.length()));
	        
	        OutputStream outputStream = null;
	        OutputStreamWriter outputStreamWriter = null;
	        InputStream inputStream = null;
	        InputStreamReader inputStreamReader = null;
	        BufferedReader reader = null;
	        StringBuffer resultBuffer = new StringBuffer();
	        String tempLine = null;
	        
	        try {
	            outputStream = httpURLConnection.getOutputStream();
	            outputStreamWriter = new OutputStreamWriter(outputStream);
	            
	            outputStreamWriter.write(buffer.toString());
	            outputStreamWriter.flush();
	            
	            if (httpURLConnection.getResponseCode() >= 300) {
	                throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
	            }
	            
	            inputStream = httpURLConnection.getInputStream();
	            inputStreamReader = new InputStreamReader(inputStream);
	            reader = new BufferedReader(inputStreamReader);
	            
	            while ((tempLine = reader.readLine()) != null) {
	                resultBuffer.append(tempLine);
	            }
	            
	        } finally {
	            if (outputStreamWriter != null) {
	                outputStreamWriter.close();
	            }
	            if (outputStream != null) {
	                outputStream.close();
	            }
	            if (reader != null) {
	                reader.close();
	            }
	            if (inputStreamReader != null) {
	                inputStreamReader.close();
	            }
	            if (inputStream != null) {
	                inputStream.close();
	            }
	        }
	        return resultBuffer.toString();
		}
		
		private static URLConnection openConnection(URL localURL){
			URLConnection connection = null;
			try {
			if (proxyHost != null && proxyPort != null) {
				Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
					connection = localURL.openConnection(proxy);
			}else {
				connection = localURL.openConnection();
			}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return connection;
		}

}
