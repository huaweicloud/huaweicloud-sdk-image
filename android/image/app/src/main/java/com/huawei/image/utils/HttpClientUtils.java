package com.huawei.image.utils;

import android.graphics.Bitmap;
import android.util.Base64;

import com.alibaba.fastjson.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class HttpClientUtils {
    public static int DEFAULT_CONNECTION_TIMEOUT = 1000;
    public static int DEFAULT_READ_TIMEOUT = 5000;
    public static int DEFAULT_WRITE_TIMEOUT = 5000;
    public static OkHttpClient client;

    static{
        client = new OkHttpClient.Builder().connectTimeout(DEFAULT_CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.MILLISECONDS).writeTimeout(DEFAULT_WRITE_TIMEOUT, TimeUnit.MILLISECONDS).build();
    }

    public static Call post(String url, Map<String, String> headers, JSONObject option) throws Exception{
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), option.toJSONString());
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .headers(addHeader(headers))
                .post(requestBody)
                .build();
        return client.newCall(request);
    }

    public static Call get(String url, Map<String, String> headers) throws Exception{
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .headers(addHeader(headers))
                .get()
                .build();
        return client.newCall(request);
    }

    public static Call delete(String url, Map<String, String> headers) throws Exception{
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .headers(addHeader(headers))
                .delete()
                .build();
        return client.newCall(request);
    }

    public static Headers addHeader(Map<String, String> headerMaps){
        Headers.Builder headerBuilder = new Headers.Builder();
        if (headerMaps != null && !headerMaps.isEmpty()) {
            for (String key : headerMaps.keySet()) {
                headerBuilder.add(key, headerMaps.get(key));
            }
        }
        return headerBuilder.build();
    }

    public static String BitmapStrByBase64(Bitmap bit) {
        if (isEmptyBitmap(bit)) return null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        byte[] bytes = bos.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    public static boolean isEmptyBitmap(final Bitmap src) {
        return src == null || src.getWidth() == 0 || src.getHeight() == 0;
    }

    public static String formatString(String text){

        StringBuilder stringBuilder = new StringBuilder();
        String indentString = "";

        boolean inQuotes = false;
        boolean isEscaped = false;

        for (int i = 0; i < text.length(); i++) {
            char letter = text.charAt(i);

            switch (letter) {
                case '\\':
                    isEscaped = !isEscaped;
                    break;
                case '"':
                    if (!isEscaped) {
                        inQuotes = !inQuotes;
                    }
                    break;
                default:
                    isEscaped = false;
                    break;
            }

            if (!inQuotes && !isEscaped) {
                switch (letter) {
                    case '{':
                    case '[':
                        stringBuilder.append("\n" + indentString + letter + "\n");
                        indentString = indentString + "\t";
                        stringBuilder.append(indentString);
                        break;
                    case '}':
                    case ']':
                        indentString = indentString.replaceFirst("\t", "");
                        stringBuilder.append("\n" + indentString + letter);
                        break;
                    case ',':
                        stringBuilder.append(letter + "\n" + indentString);
                        break;
                    default:
                        stringBuilder.append(letter);
                        break;
                }
            } else {
                stringBuilder.append(letter);
            }
        }

        return stringBuilder.toString();
    }

}
