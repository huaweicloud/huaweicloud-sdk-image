package com.huawei.image;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.cloud.sdk.util.StringUtils;
import com.huawei.image.utils.AccessService;
import com.huawei.image.utils.HttpClientUtils;
import com.huawei.image.utils.ServiceAccessBuilder;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    //
    // 1.ak、sk方式需先初始化AccessService对象
    //
    private AccessService service = ServiceAccessBuilder.builder()
            .ak("######")
            .sk("######")
            .region("cn-north-4")
            .connectionTimeout(1000)
            .writeTimeout(5000)
            .readTimeout(5000)
            .build();
    private String result;
    private Bitmap bitmap;
    private String token = "";
    private TextView resultView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化图像标签图像数据
        ImageView imageView = findViewById(R.id.imageView);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tagging);
        imageView.setImageBitmap(bitmap);

        Button confirmBtn = (Button) findViewById(R.id.confirmBtn);
        resultView = findViewById(R.id.resultText);

        confirmBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 图像标签
                 imageTagging(bitmap);

                // 名人识别
                // celebrityRecognition(bitmap);

                // 图像去雾
                // defog(bitmap);

                // 低光照增强
                // darkEnhance(bitmap);

                // 超分重建
                // superResolution(bitmap);

                // 翻拍识别
                // recaptureDetect(bitmap);

                // token方式示例
                String username = "######";
                String password = "######";
                String region = "cn-north-4";
                TokenDemo.projectName = region;

                // tokenSample(username, password, region);
            }

        });
    }

    private void tokenSample(String username, String password, String region) {
        getToken(username, password, region, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                result = e.getMessage();
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                token = response.header("X-Subject-Token");
                // 图像标签
                imageTaggingToken(bitmap);

                // 名人识别
                // celebrityRecognitionToken(bitmap);

                // 图像去雾
                // defogToken(bitmap);

                // 低光照增强
                // darkEnhanceToken(bitmap);

                // 超分重建
                // superResolutionToken(bitmap);

                // 翻拍识别
                // recaptureDetectToken(bitmap);
            }
        });
    }

    private void imageTagging(Bitmap bitmap) {
        ImageTagging initInstance = new ImageTagging(service);
        try {
            initInstance.imageTagging(bitmap, getCallback());
        } catch (Exception e) {
            e.printStackTrace();
            responseErrorMsg(e.getMessage());
        }

    }

    private void imageTaggingToken(Bitmap bitmap) {
        try {
            if (StringUtils.isNullOrEmpty(token)) {
                responseErrorMsg("Token is empty.");
                return;
            }
            TokenDemo.imageTagging(token, bitmap, getCallback());
        } catch (Exception e) {
            e.printStackTrace();
            responseErrorMsg(e.getMessage());
        }

    }

    private void celebrityRecognition(Bitmap bitmap) {
        CelebrityRecognition initInstance = new CelebrityRecognition(service);
        try {
            initInstance.celebrityRecognition(bitmap, getCallback());
        } catch (Exception e) {
            e.printStackTrace();
            responseErrorMsg(e.getMessage());
        }
    }

    private void celebrityRecognitionToken(Bitmap bitmap) {
        try {
            if (StringUtils.isNullOrEmpty(token)) {
                responseErrorMsg("Token is empty.");
                return;
            }
            TokenDemo.celebrityRecognition(token, bitmap, getCallback());
        } catch (Exception e) {
            e.printStackTrace();
            responseErrorMsg(e.getMessage());
        }

    }

    private void defog(Bitmap bitmap) {
        Defog initInstance = new Defog(service);
        try {
            initInstance.defog(bitmap, getCallback());
        } catch (Exception e) {
            e.printStackTrace();
            responseErrorMsg(e.getMessage());
        }
    }

    private void defogToken(Bitmap bitmap) {
        try {
            if (StringUtils.isNullOrEmpty(token)) {
                responseErrorMsg("Token is empty.");
                return;
            }
            TokenDemo.defog(token, bitmap, getCallback());
        } catch (Exception e) {
            e.printStackTrace();
            responseErrorMsg(e.getMessage());
        }

    }

    private void darkEnhance(Bitmap bitmap) {
        DarkEnhance initInstance = new DarkEnhance(service);
        try {
            initInstance.darkEnhance(bitmap, getCallback());
        } catch (Exception e) {
            e.printStackTrace();
            responseErrorMsg(e.getMessage());
        }
    }

    private void darkEnhanceToken(Bitmap bitmap) {
        try {
            if (StringUtils.isNullOrEmpty(token)) {
                responseErrorMsg("Token is empty.");
                return;
            }
            TokenDemo.darkEnhance(token, bitmap, getCallback());
        } catch (Exception e) {
            e.printStackTrace();
            responseErrorMsg(e.getMessage());
        }

    }

    private void superResolution(Bitmap bitmap) {
        SuperResolution initInstance = new SuperResolution(service);
        try {
            initInstance.superResolution(bitmap, getCallback());
        } catch (Exception e) {
            e.printStackTrace();
            responseErrorMsg(e.getMessage());
        }
    }

    private void superResolutionToken(Bitmap bitmap) {
        try {
            if (StringUtils.isNullOrEmpty(token)) {
                responseErrorMsg("Token is empty.");
                return;
            }
            TokenDemo.superResolution(token, bitmap, getCallback());
        } catch (Exception e) {
            e.printStackTrace();
            responseErrorMsg(e.getMessage());
        }
    }

    private void recaptureDetect(Bitmap bitmap) {
        RecaptureDetection initInstance = new RecaptureDetection(service);
        try {
            initInstance.recaptureDetection(bitmap, getCallback());
        } catch (Exception e) {
            e.printStackTrace();
            responseErrorMsg(e.getMessage());
        }
    }

    private void recaptureDetectToken(Bitmap bitmap) {
        try {
            if (StringUtils.isNullOrEmpty(token)) {
                responseErrorMsg("Token is empty.");
                return;
            }
            TokenDemo.recaptureDetect(token, bitmap, getCallback());
        } catch (Exception e) {
            e.printStackTrace();
            responseErrorMsg(e.getMessage());
        }
    }


    private void getToken(String username, String password, String region, Callback callback) {
        try {
            Call call = TokenDemo.getToken(username, password, region);
            call.enqueue(callback);
        } catch (Exception e) {
            e.printStackTrace();
            responseErrorMsg(e.getMessage());
        }
    }

    private void responseErrorMsg(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
            }
        });
    }


    private Callback getCallback() {
        return new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                result = e.toString();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        resultView.setText(result);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String resultStr = response.body().string();
                JSON json = JSON.parseObject(resultStr);
                result = HttpClientUtils.formatString(json.toJSONString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        resultView.setText(result);
                    }
                });

            }
        };
    }
}
