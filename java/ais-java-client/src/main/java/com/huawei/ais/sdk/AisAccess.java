package com.huawei.ais.sdk;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;

import com.cloud.sdk.http.HttpMethodName;
import com.huawei.ais.auth.AccessServiceImpl;
import com.huawei.ais.common.AuthInfo;
import com.huawei.ais.sdk.util.HttpClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AisAccess extends AccessServiceImpl{

	private static final Logger logger = LoggerFactory.getLogger(AisAccess.class);
	/**
	 * 服务名
	 */
	private static final String SERVICE_NAME = "ais";
		
	public int connectionTimeout = HttpClientUtils.DEFAULT_CONNECTION_TIMEOUT;
	public int connectionRequestTimeout = HttpClientUtils.DEFAULT_CONNECTION_REQUEST_TIMEOUT;
	public int socketTimeout = HttpClientUtils.DEFAULT_SOCKET_TIMEOUT;
	public int retryTimes = HttpClientUtils.DEFAULT_RETRY_TIMES;
	public int DEFAULT_MAX_REQUEST_TIME = 10000;
	
	public AisAccess(AuthInfo authInfo) {
		super(AisAccess.SERVICE_NAME, authInfo.getRegion(), authInfo.getAk(), authInfo.getSk());
		this.authInfo = authInfo;
	}
	
	public AisAccess(AuthInfo authInfo, int connectionTimeout, int connectionRequestTimeout, int socketTimeout, int retryTimes) {
		super(AisAccess.SERVICE_NAME, authInfo.getRegion(), authInfo.getAk(), authInfo.getSk());
		this.authInfo = authInfo;
		
		this.connectionTimeout = connectionTimeout;
		this.connectionRequestTimeout = connectionRequestTimeout;
		this.socketTimeout = socketTimeout;
		this.retryTimes = retryTimes;
		
	}
	
	/**
	 * 基本的认证信息
	 */
	private AuthInfo authInfo = null;

	@Override
	protected CloseableHttpClient getHttpClient()
			throws KeyManagementException, KeyStoreException, NoSuchAlgorithmException {
		return HttpClientUtils.acceptsUntrustedCertsHttpClient(false, null, this.connectionTimeout, this.connectionRequestTimeout, this.socketTimeout);
	}

	protected boolean useDefaultHttpClient()
	{
		return false;
	}

	public HttpResponse put(String requestUrl, String putBody) {

		HttpResponse response = null;

		Long startTime = System.currentTimeMillis();
		int retries = 0;
		while (retries <= retryTimes){
			try {
				Long requestTime = System.currentTimeMillis();
				if ((requestTime - startTime) > DEFAULT_MAX_REQUEST_TIME){
					logger.error("Failure to process request, time used {},The request time has exceeded the maximum limit", (requestTime - startTime));
					break;
				}

				URL url = new URL(generateWholeUrl(authInfo.getEndPoint(), requestUrl));
				HttpMethodName httpMethod = HttpMethodName.PUT;

				InputStream content = new ByteArrayInputStream(putBody.getBytes());
				response = access(url, content, (long) putBody.getBytes().length, httpMethod);
				break;
			} catch (Exception e) {
				if (retries < 1){
					logger.error("Failure to process request, request body {}, cause by:", putBody, e);
				}else {
					logger.error("Failure to process request,  request body {}, retry time {}, cause by:", putBody, retries, e);
				}
			}finally {
				retries++;
			}
		}
		return response;
	}

	public HttpResponse get(String requestUrl) {

		HttpResponse response = null;

		Long startTime = System.currentTimeMillis();
		int retries = 0;
		while (retries <= retryTimes){
			try {
				Long requestTime = System.currentTimeMillis();
				if ((requestTime - startTime) > DEFAULT_MAX_REQUEST_TIME){
					logger.error("Failure to process request, time used {},The request time has exceeded the maximum limit", (requestTime - startTime));
					break;
				}

				URL url = new URL(generateWholeUrl(authInfo.getEndPoint(), requestUrl));
				HttpMethodName httpMethod = HttpMethodName.GET;
				response = access(url, httpMethod);
				break;
			} catch (Exception e) {
				if (retries < 1){
					logger.error("Failure to process request, url {}, cause by:", requestUrl, e);
				}else {
					logger.error("Failure to process request, url {}, retry time {}, cause by:", requestUrl, retries, e);
				}
			}finally {
				retries++;
			}
		}
		return response;
	}

	public HttpResponse post(String requestUrl, String postbody) {

		URL url = null;
		try {
			url = new URL(generateWholeUrl(authInfo.getEndPoint(), requestUrl));
		} catch (MalformedURLException e) {
			logger.error("Handling url information failed, url {}, cause by:", requestUrl, e);
		}
		InputStream content = new ByteArrayInputStream(postbody.getBytes());
		HttpMethodName httpMethod = HttpMethodName.POST;
		HttpResponse response = null;

		Map<String, String> header = new HashMap<String, String>();
		header.put("Content-Type", ContentType.APPLICATION_JSON.toString());

		Long startTime = System.currentTimeMillis();
		int retries = 0;
		while (retries <= retryTimes){
			try {
				Long requestTime = System.currentTimeMillis();
				if ((requestTime - startTime) > DEFAULT_MAX_REQUEST_TIME){
					logger.error("Failure to process request, time used {},the request time has exceeded the maximum limit", (requestTime - startTime));
					break;
				}

				response = access(url, header, content, (long) postbody.getBytes().length, httpMethod);
				break;
			} catch (Exception e) {
				if (retries < 1){
					logger.error("Failure to process request, request body {}, cause by:", postbody, e);
				}else {
					logger.error("Failure to process request, request body {}, retry time {}, cause by:", postbody, retries, e);;
				}
			}finally {
				retries++;
			}
		}
		return response;
	}

	public HttpResponse post(String requestUrl, HttpEntity entity) {

		URL url = null;
		try {
			url = new URL(generateWholeUrl(authInfo.getEndPoint(), requestUrl));
		} catch (MalformedURLException e) {
			logger.error("Failure to process request, url {}, cause by:", requestUrl, e);
		}
		HttpMethodName httpMethod = HttpMethodName.POST;
		HttpResponse response = null;
		String requestBody = null;

		try {
			requestBody = convertStreamToString(entity.getContent());
		}catch (Exception e){
			logger.error("Failure to get request body, cause by:", e);
		}

		Map<String, String> header = new HashMap<String, String>();
		header.put("Content-Type", ContentType.APPLICATION_JSON.toString());

		Long startTime = System.currentTimeMillis();
		int retries = 0;
		while (retries <= retryTimes){
			try {
				Long requestTime = System.currentTimeMillis();
				if ((requestTime - startTime) > DEFAULT_MAX_REQUEST_TIME){
					logger.error("Failure to process request, time used {},The request time has exceeded the maximum limit", (requestTime - startTime));
					break;
				}
				response = accessEntity(url, header, entity, (long) entity.getContentLength(), httpMethod);
				break;
			} catch (Exception e) {
				if (retries < 1){
					logger.error("Failure to process request, request body {}, cause by:", requestBody, e);
				}else {
					logger.error("Failure to process request, request body {}, retry time {}, cause by:", requestBody, retries, e);
				}
			}finally {
				retries++;
			}
		}
		return response;
	}
	
	//
	// Generate the whole url for the specific ais service
	//
	private static String generateWholeUrl(String endPoint, String uri) {
		return String.format("%s%s", endPoint, uri);
	}

	public static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;

		try {
			while((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException var13) {
			var13.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException var12) {
				var12.printStackTrace();
			}

		}

		return sb.toString();
	}

}
