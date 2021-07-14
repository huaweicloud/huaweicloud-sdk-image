<?php
/**
 * 图像翻拍识别服务ak,sk 方式请求的示例
 */
require "./image_sdk/recapture_detect.php";
require "./image_sdk/utils.php";

// region目前支持华北-北京(cn-north-4)、中国-香港(ap-southeast-1)
init_region($region = 'cn-north-4');

$app_key = "*************";
$app_secret = "*************";

$filepath = "./data/recapture-detect-demo.jpg";
$data = file_to_base64($filepath);

$data_url = "https://sdk-obs-source-save.obs.cn-north-4.myhuaweicloud.com/recapture-detect.jpg";

// 图片的base64 的方式请求接口
$result = recapture_detect_aksk($app_key, $app_secret, $data, "", 0.99, array("recapture"));
echo $result;

// 图片的osb的url 方式请求接口
$result = recapture_detect_aksk($app_key, $app_secret, "", $data_url, 0.99, array("recapture"));
echo $result;
