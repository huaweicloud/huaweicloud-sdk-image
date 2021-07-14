<?php
/**
 * 名人识别检测服务的ak,sk 方式请求示例
 */
require "./image_sdk/celebrity_recognition.php";
require "./image_sdk/utils.php";

// region目前支持华北-北京(cn-north-4)、中国-香港(ap-southeast-1)
init_region($region = 'cn-north-4');

$app_key = "*************";
$app_secret = "*************";

$filepath = "./data/celebrity-recognition.jpg";
$data = file_to_base64($filepath);

// obs链接需要和region区域一致，不同的region的obs资源不共享
$data_url = "https://sdk-obs-source-save.obs.cn-north-4.myhuaweicloud.com/celebrity-recognition.jpg";

// 图片的base64 的方式请求接口
$result = celebrity_recognition_aksk($app_key, $app_secret, $data, "");
echo $result;

// 图片的osb的url 方式请求接口
$result = celebrity_recognition_aksk($app_key, $app_secret, "", $data_url);
echo $result;
