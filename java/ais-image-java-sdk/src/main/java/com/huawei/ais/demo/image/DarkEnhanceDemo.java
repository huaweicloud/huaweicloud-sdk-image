package com.huawei.ais.demo.image;

import java.io.File;
import java.io.IOException;

import com.huawei.ais.demo.ServiceAccessBuilder;
import com.huawei.ais.sdk.AisAccessWithProxy;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;

import com.alibaba.fastjson.JSONObject;
import com.huawei.ais.demo.ResponseProcessUtils;
import com.huawei.ais.sdk.AisAccess;

/**
 *  低光照增强服务的使用示例类
 */
public class DarkEnhanceDemo {
	//
	// 低光照增强服务的使用示例函数
	//
	private static void darkEnhanceDemo() throws IOException {

		// 1. 配置好低光照增强服务的的基本信息,生成对应的一个客户端连接对象
		AisAccess service = ServiceAccessBuilder.builder()
				.ak("######")                       // your ak
				.sk("######")                       // your sk
				.region("cn-north-4")               // 图像识别服务目前支持华北-北京(cn-north-4)以及中国-香港(ap-southeast-1)
				.connectionTimeout(10000)            // 连接目标url超时限制
				.connectionRequestTimeout(5000)     // 连接池获取可用连接超时限制
				.socketTimeout(20000)               // 获取服务器响应数据超时限制
				.retryTimes(3)                      // 请求异常时的重试次数
				.build();

		try {
			
			//
			// 2.构建访问低光照增强服务需要的参数
			//
			String uri = "/v1.0/vision/dark-enhance";
			byte[] fileData = FileUtils.readFileToByteArray(new File("data/dark-enhance-demo-1.bmp"));
			String fileBase64Str = Base64.encodeBase64String(fileData);
	
			JSONObject json = new JSONObject();
			json.put("image", fileBase64Str);
			json.put("brightness", 0.9);

			// 3.传入低光照增强服务对应的uri参数, 传入低光照增强服务需要的参数，
			// 该参数主要通过JSON对象的方式传入, 使用POST方法调用服务
			HttpResponse response = service.post(uri, json.toJSONString());
			
			// 4.验证服务调用返回的状态是否成功，如果为200, 为成功, 否则失败。
			ResponseProcessUtils.processResponseStatus(response);
			
			// 5.处理服务返回的字符流，生成对应的低光照增强处理后对应的图片文件。
			ResponseProcessUtils.processResponseWithImage(response, "data/dark-enhance-demo-1.cooked.bmp");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
			// 6.使用完毕，关闭服务的客户端连接
			service.close();
		}
	}

	//
	// 主入口函数
	//
	public static void main(String[] args) throws IOException {

		// 测试入口函数
		darkEnhanceDemo();
	}
}
