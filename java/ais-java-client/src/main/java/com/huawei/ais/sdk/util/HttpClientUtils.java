package com.huawei.ais.sdk.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;

import com.huawei.ais.common.ProxyHostInfo;


public class HttpClientUtils {

    public static int DEFAULT_CONNECTION_TIMEOUT = 5000;
    public static int DEFAULT_CONNECTION_REQUEST_TIMEOUT = 1000;
    public static int DEFAULT_SOCKET_TIMEOUT = 5000;
    public static int DEFAULT_RETRY_TIMES = 3;
    public static int SERVER_TIRED_CODE = 429;

    public static CloseableHttpClient acceptsUntrustedCertsHttpClient(boolean withProxy, ProxyHostInfo hostInfo, int connectionTimeout, int connectionRequestTimeout, int socketTimeout)
            throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        HttpClientBuilder b = HttpClientBuilder.create();

        /**
         * set http proxy
         */

        b.setDefaultRequestConfig(
                RequestConfig.custom().setConnectTimeout(connectionTimeout).setConnectionRequestTimeout(connectionRequestTimeout).setSocketTimeout(socketTimeout).build()
        );

        if (withProxy) {
            HttpHost proxy = new HttpHost(hostInfo.getHostName(), hostInfo.getPort());
            b.setProxy(proxy);
            CredentialsProvider credsProvider = new BasicCredentialsProvider();
            credsProvider.setCredentials(
                    new AuthScope(proxy.getHostName(), proxy.getPort()),
                    new UsernamePasswordCredentials(hostInfo.getUserName(), hostInfo.getPassword()));
            b.setDefaultCredentialsProvider(credsProvider);
        }

        SSLContext sslContext = new SSLContextBuilder().useProtocol("TLSv1.2").loadTrustMaterial(null, new TrustStrategy() {
            public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                return true;
            }
        }).build();
        b.setSSLContext(sslContext);
        b.setConnectionTimeToLive(180, TimeUnit.SECONDS);

        HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;

        SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", sslSocketFactory)
                .build();

        PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        connMgr.setMaxTotal(200);
        connMgr.setDefaultMaxPerRoute(100);
        b.setConnectionManager(connMgr);
        CloseableHttpClient client = b.build();
        return client;
    }

    public static CloseableHttpClient getDefaultClient(int connectionRequestTimeout, int connectionTimeout, int socketTimeout) throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sslContext = new SSLContextBuilder().useProtocol("TLSv1.2").build();
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .setConnectTimeout(connectionTimeout)
                .setSocketTimeout(socketTimeout)
                .build();
        HostnameVerifier hostnameVerifier = new DefaultHostnameVerifier();

        SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory()).register("https", sslSocketFactory)
                .build();

        PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        return HttpClientBuilder.create()
                .setConnectionManager(connMgr)
                .setMaxConnTotal(200)
                .setMaxConnPerRoute(100)
                .setSSLContext(sslContext)
                .setDefaultRequestConfig(requestConfig)
                .setConnectionTimeToLive(180, TimeUnit.SECONDS)
                .build();
    }

    public static CloseableHttpClient acceptsUntrustedCertsHttpClient() throws KeyManagementException, KeyStoreException, NoSuchAlgorithmException {
        return acceptsUntrustedCertsHttpClient(false, null, DEFAULT_CONNECTION_TIMEOUT, DEFAULT_CONNECTION_REQUEST_TIMEOUT, DEFAULT_SOCKET_TIMEOUT);
    }

    public static CloseableHttpClient getHttpClient(boolean sslVerification, int connectionTimeout, int connectionRequestTimeout, int socketTimeout) throws KeyManagementException, KeyStoreException, NoSuchAlgorithmException {
        return sslVerification ? getDefaultClient(connectionRequestTimeout, connectionTimeout, socketTimeout) : acceptsUntrustedCertsHttpClient(false, null, connectionTimeout, connectionRequestTimeout, socketTimeout);
    }

    public static HttpResponse post(String url, Header[] headers, HttpEntity entity, boolean sslVerification, int connectionTimeout, int connectionRequestTimeout, int socketTimeout) {
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = null;
        try {
            httpClient = getHttpClient(sslVerification, connectionTimeout, connectionRequestTimeout, socketTimeout);
            HttpPost post = new HttpPost(url);
            if (null != headers) {
                post.setHeaders(headers);
            }
            if (null != entity) {
                post.setEntity(entity);
            }
            response = httpClient.execute(post);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return response;
    }


    public static HttpResponse put(String url, Header[] headers, HttpEntity entity, boolean sslVerification, int connectionTimeout, int connectionRequestTimeout, int socketTimeout) {
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = null;
        try {
            httpClient = getHttpClient(sslVerification, connectionTimeout, connectionRequestTimeout, socketTimeout);
            HttpPut put = new HttpPut(url);
            if (null != headers) {
                put.setHeaders(headers);
            }
            if (null != entity) {
                put.setEntity(entity);
            }
            response = httpClient.execute(put);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return response;
    }

    public static HttpResponse get(String url, Header[] headers, boolean sslVerification, int connectionTimeout, int connectionRequestTimeout, int socketTimeout) {
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = null;
        try {
            httpClient = HttpClientUtils.getHttpClient(sslVerification, connectionTimeout, connectionRequestTimeout, socketTimeout);
            HttpGet get = new HttpGet(url);
            if (null != headers) {
                get.setHeaders(headers);
            }
            response = httpClient.execute(get);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return response;
    }

    public static HttpResponse delete(String url, Header[] headers, boolean sslVerification, int connectionTimeout, int connectionRequestTimeout, int socketTimeout) {
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = null;
        try {
            httpClient = HttpClientUtils.getHttpClient(sslVerification, connectionTimeout, connectionRequestTimeout, socketTimeout);
            HttpDelete get = new HttpDelete(url);
            if (null != headers) {
                get.setHeaders(headers);
            }
            response = httpClient.execute(get);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return response;
    }

    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

    public static boolean needRetry(int status) {
        return status >= 500 || status == SERVER_TIRED_CODE;
    }
}
