package com.huawei.image.utils;

import com.cloud.apigateway.sdk.utils.Client;
import com.cloud.apigateway.sdk.utils.Request;
import com.cloud.sdk.http.HttpMethodName;
import com.huawei.image.entity.AuthInfo;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class AccessService {
    private AuthInfo authInfo;

    private int connectTimeout;
    private int readTimeout;
    private int writeTimeout;
    private OkHttpClient client;

    public AccessService(AuthInfo authInfo, int connectTimeout, int readTimeout, int writeTimeout) {
        this.authInfo = authInfo;
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
        this.writeTimeout = writeTimeout;
        this.client = new OkHttpClient.Builder().connectTimeout(this.connectTimeout, TimeUnit.MILLISECONDS)
                .readTimeout(this.readTimeout, TimeUnit.MILLISECONDS).writeTimeout(this.writeTimeout, TimeUnit.MILLISECONDS).build();
    }

    public Call post(String uri, String requestBody) throws Exception {
        Request request = initRequest(uri, HttpMethodName.POST);
        request.setBody(requestBody);

        okhttp3.Request signedRequest = Client.signOkhttp(request);
        return client.newCall(signedRequest);
    }

    private static String generateWholeUrl(String endPoint, String uri) {
        return String.format("%s%s", endPoint, uri);
    }

    public Call get(String uri) throws Exception {
        Request request = initRequest(uri, HttpMethodName.GET);
        okhttp3.Request signedRequest = Client.signOkhttp(request);
        return client.newCall(signedRequest);
    }

    public Call delete(String uri) throws Exception {
        Request request = initRequest(uri, HttpMethodName.DELETE);
        okhttp3.Request signedRequest = Client.signOkhttp(request);
        return client.newCall(signedRequest);
    }


    private void addParameters(Request request, String url) throws Exception {
        if (url.contains("?")) {
            String parameters = url.substring(url.indexOf("?") + 1);

            if (!"".equals(parameters)) {
                String[] parameterArray = parameters.split("&");

                for (String p : parameterArray) {
                    String key = p.split("=")[0];
                    String value = p.split("=")[1];
                    request.addQueryStringParam(key, value);
                }
            }
        }
    }

    private Request initRequest(String uri, HttpMethodName httpMethodName) throws Exception{
        Request request = new Request();
        String url = generateWholeUrl(this.authInfo.getEndpoint(), uri);

        request.setAppKey(this.authInfo.getAk());
        request.setAppSecrect(this.authInfo.getSk());
        request.setMethod(httpMethodName.name());
        request.addHeader("Content-Type", "application/json");
        request.setUrl(url);
        addParameters(request, url);
        return request;
    }
}
