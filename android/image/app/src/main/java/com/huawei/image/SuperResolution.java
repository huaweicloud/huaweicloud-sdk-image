package com.huawei.image;

import android.graphics.Bitmap;

import com.huawei.image.utils.AccessService;
import com.huawei.image.utils.HttpClientUtils;

import com.alibaba.fastjson.JSONObject;
import okhttp3.Call;
import okhttp3.Callback;

public class SuperResolution {
    private AccessService service;

    public SuperResolution(AccessService service) {
        this.service = service;
    }

    public void superResolution(Bitmap bitmap, Callback callback) throws Exception {
        //
        // 2.构建访问去雾服务需要的参数
        //
        String uri = "/v1.0/vision/super-resolution";
        String fileBase64Str = HttpClientUtils.BitmapStrByBase64(bitmap);

        JSONObject json = new JSONObject();
        json.put("image", fileBase64Str);
        json.put("scale", 3);
        json.put("model", "ESPCN");

        // 3.传入去雾服务对应的uri参数, 传入去雾服务需要的参数，
        // 该参数主要通过JSON对象的方式传入, 使用POST方法调用服务
        Call call = service.post(uri, json.toJSONString());
        call.enqueue(callback);
    }
}
