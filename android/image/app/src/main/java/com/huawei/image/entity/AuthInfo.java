package com.huawei.image.entity;

public class AuthInfo {
    private String ak;
    private String sk;
    private String region;
    private String endpoint;

    public AuthInfo(String ak, String sk, String region, String endpoint) {
        this.ak = ak;
        this.sk = sk;
        this.region = region;
        this.endpoint = endpoint;
    }

    public String getAk() {
        return ak;
    }

    public void setAk(String ak) {
        this.ak = ak;
    }

    public String getSk() {
        return sk;
    }

    public void setSk(String sk) {
        this.sk = sk;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
}
