package com.huawei.image.utils;

import com.huawei.image.entity.AuthInfo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceAccessBuilder {
    private String ak;
    private String sk;
    private String region;
    private String endpoint;

    private int connectionTimeout = 1000;
    private int readTimeout = 5000;
    private int writeTimeout = 5000;


    private static Map<String, String> endponitMap = new ConcurrentHashMap<>();

    static {
        /*  内容审核服务的区域和终端节点信息可以从如下地址查询
         *  http://developer.huaweicloud.com/dev/endpoint
         * */
        endponitMap.put("cn-north-1", "https://image.cn-north-1.myhuaweicloud.com");
        endponitMap.put("cn-north-4", "https://image.cn-north-4.myhuaweicloud.com");
        endponitMap.put("ap-southeast-1", "https://image.ap-southeast-1.myhuaweicloud.com");
        endponitMap.put("cn-east-3", "https://image.cn-east-3.myhuaweicloud.com");
    }

    public static ServiceAccessBuilder builder() {
        return new ServiceAccessBuilder();
    }

    public AccessService build() {
        return new AccessService(new AuthInfo(ak, sk, region, endpoint), connectionTimeout, readTimeout, writeTimeout);
    }

    public ServiceAccessBuilder ak(String ak) {
        this.ak = ak;
        return this;
    }

    public ServiceAccessBuilder sk(String sk) {
        this.sk = sk;
        return this;
    }

    public ServiceAccessBuilder region(String region) {
        this.region = region;
        this.endpoint = getCurrentEndpoint(region);
        return this;
    }

    public ServiceAccessBuilder connectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
        return this;
    }

    public ServiceAccessBuilder readTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

    public ServiceAccessBuilder writeTimeout(int writeTimeout) {
        this.writeTimeout = writeTimeout;
        return this;
    }


    /**
     * 用于根据服务的区域信息获取服务域名
     */
    public static String getCurrentEndpoint(String region) {
        return endponitMap.get(region);
    }

}
