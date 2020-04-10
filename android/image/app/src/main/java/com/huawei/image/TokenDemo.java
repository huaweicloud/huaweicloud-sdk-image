package com.huawei.image;

import android.graphics.Bitmap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cloud.sdk.util.StringUtils;
import com.huawei.image.utils.HttpClientUtils;
import com.huawei.image.utils.ServiceAccessBuilder;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 使用Token认证方式访问服务
 */
public class TokenDemo {
    public static String projectName = "cn-north-4"; // 此处，请输入服务的区域信息，目前支持华北-北京(cn-north-4)、华东上海一(cn-east-3)以及亚太-香港(ap-southeast-1)

    /**
     * 构造使用Token方式访问服务的请求Token对象
     *
     * @param username    用户名
     * @param passwd      密码
     * @param domainName  域名
     * @param projectName 项目名称
     * @return 构造访问的JSON对象
     */
    private static JSONObject requestBody(String username, String passwd, String domainName, String projectName) {
        JSONObject auth = new JSONObject();

        JSONObject identity = new JSONObject();

        JSONArray methods = new JSONArray();
        methods.add("password");
        identity.put("methods", methods);

        JSONObject password = new JSONObject();

        JSONObject user = new JSONObject();
        user.put("name", domainName);
        user.put("password", passwd);

        JSONObject domain = new JSONObject();
        domain.put("name", domainName);
        user.put("domain", domain);

        password.put("user", user);

        identity.put("password", password);

        JSONObject scope = new JSONObject();

        JSONObject scopeProject = new JSONObject();
        scopeProject.put("name", projectName);

        scope.put("project", scopeProject);

        auth.put("identity", identity);
        auth.put("scope", scope);

        JSONObject params = new JSONObject();
        params.put("auth", auth);
        return params;
    }

    /**
     * 获取Token参数， 注意，此函数的目的，主要为了从HTTP请求返回体中的Header中提取出Token
     * 参数名为: X-Subject-Token
     *
     * @param username    用户名
     * @param password    密码
     * @param projectName 区域名，可以参考http://developer.huaweicloud.com/dev/endpoint
     * @return 包含Token串的返回体，
     * @throws Exception
     */
    public static Call getToken(String username, String password, String projectName) throws Exception {
        JSONObject requestBody = requestBody(username, password, username, projectName);
        String url = "https://iam.myhuaweicloud.com/v3/auth/tokens";

        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", MediaType.parse("application/json").toString());
        Call call = HttpClientUtils.post(url, headers, requestBody);
        return call;
    }

    /**
     * 图像标签，使用Token认证方式访问服务
     *
     * @param token  token认证串
     * @param bitmap 图像
     * @throws Exception
     */
    public static void imageTagging(String token, Bitmap bitmap, Callback callback) throws Exception {
        String url = ServiceAccessBuilder.getCurrentEndpoint(projectName) + "/v1.0/image/tagging";
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Auth-Token", token);

        String fileBase64Str = HttpClientUtils.BitmapStrByBase64(bitmap);
        JSONObject json = new JSONObject();
        json.put("image", fileBase64Str);
        json.put("limit", -1);
        json.put("language", "zh");
        json.put("threshold", 60);
        Call call = HttpClientUtils.post(url, headers, json);
        call.enqueue(callback);
    }

    /**
     * 图像去雾，使用Token认证方式访问服务
     *
     * @param token  token认证串
     * @param bitmap 图像
     * @throws Exception
     */
    public static void defog(String token, Bitmap bitmap, Callback callback) throws Exception {
        String url = ServiceAccessBuilder.getCurrentEndpoint(projectName) + "/v1.0/vision/defog";
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Auth-Token", token);

        String fileBase64Str = HttpClientUtils.BitmapStrByBase64(bitmap);
        JSONObject json = new JSONObject();
        json.put("image", fileBase64Str);
        json.put("gamma", 1.5);
        json.put("natural_look", true);

        Call call = HttpClientUtils.post(url, headers, json);
        call.enqueue(callback);
    }

    /**
     * 名人识别，使用Token认证方式访问服务
     *
     * @param token  token认证串
     * @param bitmap 图像
     * @throws IOException
     */
    public static void celebrityRecognition(String token, Bitmap bitmap, Callback callback) throws Exception {
        String url = ServiceAccessBuilder.getCurrentEndpoint(projectName) + "/v1.0/image/celebrity-recognition";
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Auth-Token", token);

        String fileBase64Str = HttpClientUtils.BitmapStrByBase64(bitmap);
        JSONObject json = new JSONObject();
        json.put("image", fileBase64Str);
        json.put("threshold", "0.48");

        Call call = HttpClientUtils.post(url, headers, json);
        call.enqueue(callback);
    }

    /**
     * 低光照增强，使用Token认证方式访问服务
     *
     * @param token  token认证串
     * @param bitmap 图像
     * @throws IOException
     */
    public static void darkEnhance(String token, Bitmap bitmap, Callback callback) throws Exception {
        String url = ServiceAccessBuilder.getCurrentEndpoint(projectName) + "/v1.0/vision/dark-enhance";
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Auth-Token", token);

        String fileBase64Str = HttpClientUtils.BitmapStrByBase64(bitmap);
        JSONObject json = new JSONObject();
        json.put("image", fileBase64Str);
        json.put("brightness", 0.9);

        Call call = HttpClientUtils.post(url, headers, json);
        call.enqueue(callback);
    }

    /**
     * 超分重建，使用Token认证方式访问服务
     *
     * @param token  token认证串
     * @param bitmap 图像
     * @throws Exception
     */
    public static void superResolution(String token, Bitmap bitmap, Callback callback) throws Exception {
        String url = ServiceAccessBuilder.getCurrentEndpoint(projectName) + "/v1.0/vision/super-resolution";
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Auth-Token", token);

        String fileBase64Str = HttpClientUtils.BitmapStrByBase64(bitmap);

        JSONObject json = new JSONObject();
        json.put("image", fileBase64Str);
        json.put("scale", 3);
        json.put("model", "ESPCN");

        Call call = HttpClientUtils.post(url, headers, json);
        call.enqueue(callback);
    }

    /**
     * 翻拍识别，使用Token认证方式访问服务
     *
     * @param token  token认证串
     * @param bitmap 图像
     * @throws Exception
     */
    public static void recaptureDetect(String token, Bitmap bitmap, Callback callback) throws Exception {
        String url = ServiceAccessBuilder.getCurrentEndpoint(projectName) + "/v1.0/image/recapture-detect";
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        headers.put("X-Auth-Token", token);

        String fileBase64Str = HttpClientUtils.BitmapStrByBase64(bitmap);

        JSONObject json = new JSONObject();
        json.put("image", fileBase64Str);
        json.put("threshold", 0.95);
        json.put("scene", new String[]{"recapture"});

        Call call = HttpClientUtils.post(url, headers, json);
        call.enqueue(callback);
    }

}

