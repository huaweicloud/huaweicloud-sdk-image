<?php
/**
 * 图像翻拍识别服务token 方式请求的示例
 */
require "./image_sdk/gettoken.php";
require "./image_sdk/recapture_detect.php";
require "./image_sdk/utils.php";

// region目前支持华北-北京(cn-north-4)、亚太-香港(ap-southeast-1)
init_region($region = 'cn-north-4');

$username = "********";      // 配置用户名
$password = "********";      // 密码
$domainName = "*********";   // 配置用户名

$filepath = "./data/recapture-detect-demo.jpg";
$data = file_to_base64($filepath);

$data_url = "https://ais-sample-data.obs.myhuaweicloud.com/recapture-detect.jpg";

$token = get_token($username, $password, $domainName);

// 图片base64方式请求接口
$result = recapture_detect($token, $data, "", 0.99, array("recapture"));
echo $result;

// 图片的obs 的url方式请求接口
$result = recapture_detect($token, "", $data_url, 0.99, array("recapture"));
echo $result;
