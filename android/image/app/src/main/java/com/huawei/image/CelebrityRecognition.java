package com.huawei.image;

import android.graphics.Bitmap;

import com.alibaba.fastjson.JSONObject;
import com.huawei.image.utils.AccessService;
import com.huawei.image.utils.HttpClientUtils;

import okhttp3.Call;
import okhttp3.Callback;

public class CelebrityRecognition {
    private AccessService service;

    public CelebrityRecognition(AccessService service) {
        this.service = service;
    }

    public void celebrityRecognition(Bitmap bitmap, Callback callback) throws Exception {
        //
        // 2.构建访问名人识别服务需要的参数
        //
        String uri = "/v1.0/image/celebrity-recognition";
        String fileBase64Str = HttpClientUtils.BitmapStrByBase64(bitmap);

        // 注：请求参数详情可参考 https://support.huaweicloud.com/api-image/image_03_0027.html
        JSONObject json = new JSONObject();
        json.put("image", fileBase64Str);
        json.put("threshold", "0.48");

        // 3.传入名人识别服务对应的uri参数, 传入名人识别服务需要的参数，
        // 该参数主要通过JSON对象的方式传入, 使用POST方法调用服务
        Call call = service.post(uri, json.toJSONString());
        call.enqueue(callback);
    }

    public void celebrityRecognition(String url, Callback callback) throws Exception {
        //
        // 2.构建访问名人识别服务需要的参数
        //
        String uri = "/v1.0/image/celebrity-recognition";

        // 注：请求参数详情可参考 https://support.huaweicloud.com/api-image/image_03_0027.html
        JSONObject json = new JSONObject();
        json.put("url", url);
        json.put("threshold", "0.48");

        // 3.传入名人识别服务对应的uri参数, 传入名人识别服务需要的参数，
        // 该参数主要通过JSON对象的方式传入, 使用POST方法调用服务
        Call call = service.post(uri, json.toJSONString());
        call.enqueue(callback);
    }
}
