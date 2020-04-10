package com.huawei.image;

import android.graphics.Bitmap;

import com.alibaba.fastjson.JSONObject;
import com.huawei.image.utils.AccessService;
import com.huawei.image.utils.HttpClientUtils;

import okhttp3.Call;
import okhttp3.Callback;

public class ImageTagging {
    private AccessService service;

    public ImageTagging(AccessService service) {
        this.service = service;
    }

    public void imageTagging(Bitmap bit, Callback callback) throws Exception {
        //
        // 2.构建访问图片标签服务需要的参数
        //
        String uri = "/v1.0/image/tagging";
        String fileBase64Str = HttpClientUtils.BitmapStrByBase64(bit);

        // 注：请求参数详情可参考 https://support.huaweicloud.com/api-image/image_03_0025.html
        JSONObject json = new JSONObject();
        json.put("image", fileBase64Str);
        json.put("limit", -1);
        json.put("language", "zh");
        json.put("threshold", 60);

        // 3.传入图片标签服务对应的uri参数, 传入图片标签服务需要的参数，
        // 该参数主要通过JSON对象的方式传入, 使用POST方法调用服务
        Call call = service.post(uri, json.toJSONString());
        call.enqueue(callback);
    }

    public void imageTagging(String url, Callback callback) throws Exception {
        //
        // 2.构建访问图片标签服务需要的参数
        //
        String uri = "/v1.0/image/tagging";

        // 注：请求参数详情可参考 https://support.huaweicloud.com/api-image/image_03_0025.html
        JSONObject json = new JSONObject();
        json.put("url", url);
        json.put("limit", -1);
        json.put("language", "zh");
        json.put("threshold", 60);

        // 3.传入图片标签服务对应的uri参数, 传入图片标签服务需要的参数，
        // 该参数主要通过JSON对象的方式传入, 使用POST方法调用服务
        Call call = service.post(uri, json.toJSONString());
        call.enqueue(callback);
    }
}
