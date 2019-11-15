<?php
/**
 * 名人识别检测服务的token 方式请求示例
 */
require "./image_sdk/gettoken.php";
require "./image_sdk/celebrity_recognition.php";
require "./image_sdk/utils.php";

// region目前支持华北-北京(cn-north-4)、亚太-香港(ap-southeast-1)
init_region($region = 'cn-north-4');

$username = "********";      // 配置用户名
$password = "********";      // 密码
$domainName = "*********";   // 配置用户名

$filepath = "./data/celebrity-recognition.jpg";
$data = file_to_base64($filepath);

// obs链接需要和region区域一致，不同的region的obs资源不共享
$data_url = "https://sdk-obs-source-save.obs.cn-north-4.myhuaweicloud.com/celebrity-recognition.jpg";

$token = get_token($username, $password, $domainName);

// 图片base64方式请求接口
$result = celebrity_recognition($token, $data, "");
echo $result;

// 图片的obs 的url方式请求接口
$result = celebrity_recognition($token, "", $data_url);
echo $result;

