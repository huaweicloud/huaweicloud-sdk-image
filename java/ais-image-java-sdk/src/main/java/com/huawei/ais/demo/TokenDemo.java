package com.huawei.ais.demo;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huawei.ais.sdk.util.HttpClientUtils;

/**
 * 使用Token认证方式访问服务
 */
public class TokenDemo {
    private static final String projectName = "cn-north-4"; // 此处，请输入服务的区域信息，目前支持华北-北京(cn-north-4)以及中国-香港(ap-southeast-1)
    public static int connectionTimeout = 10000; //连接目标url超时限制参数
    public static int connectionRequestTimeout = 5000;//连接池获取可用连接超时限制参数
    public static int socketTimeout = 20000;//获取服务器响应数据超时限制参数

    /**
     * 构造使用Token方式访问服务的请求Token对象
     *
     * @param username    用户名
     * @param passwd      密码
     * @param domainName  域名
     * @param projectName 项目名称
     * @return 构造访问的JSON对象
     */
    private static String requestBody(String username, String passwd, String domainName, String projectName) {
        JSONObject auth = new JSONObject();

        JSONObject identity = new JSONObject();

        JSONArray methods = new JSONArray();
        methods.add("password");
        identity.put("methods", methods);

        JSONObject password = new JSONObject();

        JSONObject user = new JSONObject();
        user.put("name", username);
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
        return params.toJSONString();
    }

    /**
     * 获取Token参数， 注意，此函数的目的，主要为了从HTTP请求返回体中的Header中提取出Token
     * 参数名为: X-Subject-Token
     *
     * @param username    用户名
     * @param password    密码
     * @param projectName 区域名，可以参考http://developer.huaweicloud.com/dev/endpoint
     * @return 包含Token串的返回体，
     * @throws URISyntaxException
     * @throws UnsupportedOperationException
     * @throws IOException
     */
    private static String getToken(String username, String password, String domainName, String projectName)
            throws URISyntaxException, UnsupportedOperationException, IOException {
        String requestBody = requestBody(username, password, domainName, projectName);
        String url = "https://iam.myhuaweicloud.com/v3/auth/tokens";

        Header[] headers = new Header[]{new BasicHeader("Content-Type", ContentType.APPLICATION_JSON.toString())};
        StringEntity stringEntity = new StringEntity(requestBody,
                "utf-8");

        HttpResponse response = HttpClientUtils.post(url, headers, stringEntity, connectionTimeout, connectionRequestTimeout, socketTimeout);
        Header[] xst = response.getHeaders("X-Subject-Token");
        return xst[0].getValue();

    }

    /**
     * 图像标签使用Base64编码后的文件方式，使用Token认证方式访问服务
     *
     * @param token    token认证串
     * @param formFile 文件路径
     * @throws IOException
     */
    public static void requestImageTaggingBase64(String token, String formFile) throws IOException {
        String url = ServiceAccessBuilder.getCurrentEndpoint(projectName) + "/v1.0/image/tagging";
        Header[] headers = new Header[]{new BasicHeader("X-Auth-Token", token), new BasicHeader("Content-Type", "application/json")};
        String requestBody = toBase64Str(formFile);
        StringEntity stringEntity = new StringEntity(requestBody, "utf-8");
        try {
            HttpResponse response = HttpClientUtils.post(url, headers, stringEntity, connectionTimeout, connectionRequestTimeout, socketTimeout);
            System.out.println(response);
            String content = IOUtils.toString(response.getEntity().getContent());
            System.out.println(JSON.toJSONString(JSON.parse(content.toString()), SerializerFeature.PrettyFormat));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 名人识别使用Base64编码后的文件方式，使用Token认证方式访问服务
     *
     * @param token    token认证串
     * @param formFile 文件路径
     * @throws IOException
     */
    public static void requestCelebrityRecognitionBase64(String token, String formFile) throws IOException {
        String url = ServiceAccessBuilder.getCurrentEndpoint(projectName) + "/v1.0/image/celebrity-recognition";
        Header[] headers = new Header[]{new BasicHeader("X-Auth-Token", token), new BasicHeader("Content-Type", "application/json")};
        String requestBody = toBase64Str(formFile);
        StringEntity stringEntity = new StringEntity(requestBody, "utf-8");
        try {
            HttpResponse response = HttpClientUtils.post(url, headers, stringEntity, connectionTimeout, connectionRequestTimeout, socketTimeout);
            System.out.println(response);
            String content = IOUtils.toString(response.getEntity().getContent());
            System.out.println(JSON.toJSONString(JSON.parse(content.toString()), SerializerFeature.PrettyFormat));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 背景音乐识别使用url访问服务，使用Token认证方式访问服务
     *
     * @param token  token认证串
     * @param bgmUrl 背景音乐的
     * @throws IOException
     */
    public static void requestAsrBgm(String token, String bgmUrl) throws IOException {
        String url = ServiceAccessBuilder.getCurrentEndpoint(projectName) + "/v1.0/bgm/recognition";
        Header[] headers = new Header[]{new BasicHeader("X-Auth-Token", token), new BasicHeader("Content-Type", "application/json")};
        JSONObject json = new JSONObject();
        json.put("url", bgmUrl);
        StringEntity stringEntity = new StringEntity(json.toString(), "utf-8");
        try {
            HttpResponse response = HttpClientUtils.post(url, headers, stringEntity, connectionTimeout, connectionRequestTimeout, socketTimeout);
            System.out.println(response);
            String content = IOUtils.toString(response.getEntity().getContent());
            System.out.println(JSON.toJSONString(JSON.parse(content.toString()), SerializerFeature.PrettyFormat));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 翻拍识别使用Base64编码后的文件方式，使用Token认证方式访问服务
     *
     * @param token    token认证串
     * @param formFile 文件路径
     * @throws IOException
     */
    public static void requestRecaptureDetectionBase64(String token, String formFile) throws IOException {
        String url = ServiceAccessBuilder.getCurrentEndpoint(projectName) + "/v1.0/image/recapture-detect";
        Header[] headers = new Header[]{new BasicHeader("X-Auth-Token", token), new BasicHeader("Content-Type", "application/json")};
        byte[] fileData = FileUtils.readFileToByteArray(new File(formFile));
        String fileBase64Str = Base64.encodeBase64String(fileData);

        JSONObject json = new JSONObject();
        json.put("image", fileBase64Str);
        json.put("threshold", 0.95);
        json.put("scene", new String[]{"recapture"});
        StringEntity stringEntity = new StringEntity(json.toJSONString(), "utf-8");

        try {
            HttpResponse response = HttpClientUtils.post(url, headers, stringEntity, connectionTimeout, connectionRequestTimeout, socketTimeout);
            System.out.println(response);
            String content = IOUtils.toString(response.getEntity().getContent());
            System.out.println(JSON.toJSONString(JSON.parse(content.toString()), SerializerFeature.PrettyFormat));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 低光照增强使用Base64编码后的文件方式，使用Token认证方式访问服务
     *
     * @param token    token认证串
     * @param formFile 文件路径
     * @throws IOException
     */
    public static void requestDarkEnhanceBase64(String token, String formFile) throws IOException {
        String url = ServiceAccessBuilder.getCurrentEndpoint(projectName) + "/v1.0/vision/dark-enhance";
        Header[] headers = new Header[]{new BasicHeader("X-Auth-Token", token), new BasicHeader("Content-Type", "application/json")};
        JSONObject json = new JSONObject();
        byte[] fileData = FileUtils.readFileToByteArray(new File(formFile));
        String fileBase64Str = Base64.encodeBase64String(fileData);
        json.put("image", fileBase64Str);
        json.put("brightness", 0.9);
        String requestBody = json.toJSONString();
        StringEntity stringEntity = new StringEntity(requestBody, "utf-8");
        try {
            HttpResponse response = HttpClientUtils.post(url, headers, stringEntity, connectionTimeout, connectionRequestTimeout, socketTimeout);

            //验证服务调用返回的状态是否成功，如果为200, 为成功, 否则失败。
            ResponseProcessUtils.processResponseStatus(response);

            //处理服务返回的字符流，生成对应的低光照增强处理后对应的图片文件。
            ResponseProcessUtils.processResponseWithImage(response, "data/dark-enhance-demo-1.cooked.bmp");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 超分重建使用Base64编码后的文件方式，使用Token认证方式访问服务
     *
     * @param token    token认证串
     * @param formFile 文件路径
     * @throws IOException
     */
    public static void requestSuperResolution(String token, String formFile) throws IOException {
        String url = ServiceAccessBuilder.getCurrentEndpoint(projectName) + "/v1.0/vision/super-resolution";
        Header[] headers = new Header[]{new BasicHeader("X-Auth-Token", token), new BasicHeader("Content-Type", "application/json")};
        JSONObject json = new JSONObject();
        byte[] fileData = FileUtils.readFileToByteArray(new File(formFile));
        String fileBase64Str = Base64.encodeBase64String(fileData);
        json.put("image", fileBase64Str);
        json.put("scale", 3);
        json.put("model", "ESPCN");
        String requestBody = json.toJSONString();
        StringEntity stringEntity = new StringEntity(requestBody, "utf-8");
        try {
            HttpResponse response = HttpClientUtils.post(url, headers, stringEntity, connectionTimeout, connectionRequestTimeout, socketTimeout);

            //验证服务调用返回的状态是否成功，如果为200, 为成功, 否则失败。
            ResponseProcessUtils.processResponseStatus(response);

            //处理服务返回的字符流，生成对应的超分重建处理后对应的图片文件。
            ResponseProcessUtils.processResponseWithImage(response, "data/super-resolution-demo-1.token.bmp");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 图像去雾使用Base64编码后的文件方式，使用Token认证方式访问服务
     *
     * @param token    token认证串
     * @param formFile 文件路径
     * @throws IOException
     */
    public static void requestDefogBase64(String token, String formFile) throws IOException {
        String url = ServiceAccessBuilder.getCurrentEndpoint(projectName) + "/v1.0/vision/defog";
        Header[] headers = new Header[]{new BasicHeader("X-Auth-Token", token), new BasicHeader("Content-Type", "application/json")};
        JSONObject json = new JSONObject();
        byte[] fileData = FileUtils.readFileToByteArray(new File(formFile));
        String fileBase64Str = Base64.encodeBase64String(fileData);
        json.put("image", fileBase64Str);
        json.put("gamma", 1.5);
        json.put("natural_look", true);
        String requestBody = json.toJSONString();
        StringEntity stringEntity = new StringEntity(requestBody, "utf-8");
        try {
            HttpResponse response = HttpClientUtils.post(url, headers, stringEntity, connectionTimeout, connectionRequestTimeout, socketTimeout);

            //验证服务调用返回的状态是否成功，如果为200, 为成功, 否则失败。
            ResponseProcessUtils.processResponseStatus(response);

            //处理服务返回的字符流，生成对应的图像去雾处理后对应的图片文件。
            ResponseProcessUtils.processResponseWithImage(response, "data/defog-demo-1-token.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将二进制文件转为经Base64编码之后的请求访问对象（JSON串格式）
     *
     * @param file 文件名
     * @return 包含被Base64编码之后的文件字符流的JSON对象
     * @throws IOException
     */
    public static String toBase64Str(String file) throws IOException {
        byte[] fileData = FileUtils.readFileToByteArray(new File(file));
        String fileBase64Str = Base64.encodeBase64String(fileData);
        JSONObject json = new JSONObject();
        json.put("image", fileBase64Str);

        return json.toJSONString();
    }

    /**
     * 调用主入口函数
     */
    public static void main(String[] args) throws URISyntaxException, UnsupportedOperationException, IOException {
        String username = "zhangshan";    // 此处，请输入用户名
        String password = "*******";      // 此处，请输入对应用户名的密码
        String domainName = "*******";    // 账户为主账号，此处输入与用户名一致，若为子账号，此处输入为主账户用户名

        String token = getToken(username, password, domainName, projectName);
        System.out.println(token);
        requestImageTaggingBase64(token, "data/image-tagging-demo-1.jpg");

        requestCelebrityRecognitionBase64(token, "data/celebrity-recognition.jpg");

        requestRecaptureDetectionBase64(token, "data/recapture-detect-demo-1.jpg");

        requestDarkEnhanceBase64(token, "data/dark-enhance-demo-1.bmp");

        requestAsrBgm(token, "https://sdk-obs-source-save.obs.cn-north-4.myhuaweicloud.com/bgm_recognition");

        requestDefogBase64(token, "data/defog-demo-1.png");

        requestSuperResolution(token, "data/super-resolution-demo-1.png");

    }

}
