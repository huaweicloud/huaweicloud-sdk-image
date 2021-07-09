<?php
/**
 * 图像超分重建服务ak,sk 方式请求的示例
 */
require "./image_sdk/super_resolution.php";
require "./image_sdk/utils.php";

// region目前支持华北-北京(cn-north-4)、中国-香港(ap-southeast-1)
init_region($region = 'cn-north-4');

$app_key = "*************";
$app_secret = "*************";

$filepath = "./data/super-resolution-demo.png";
$data = file_to_base64($filepath);

$result = super_resolution_aksk($app_key, $app_secret, $data, 3, "ESPCN");
echo $result;
$resultobj = json_decode($result, true);
$basestr = $resultobj["result"];
base64_to_file("data/super-resolution-aksk.png", $basestr);
