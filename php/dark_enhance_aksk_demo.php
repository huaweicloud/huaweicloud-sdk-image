<?php
/**
 * 低光照增强ak,sk 方式请求的服务示例
 */

require "./image_sdk/dark_enhance.php";
require "./image_sdk/utils.php";

// region目前支持华北-北京(cn-north-4)、中国-香港(ap-southeast-1)
init_region($region = 'cn-north-4');

$app_key = "*************";
$app_secret = "*************";

$filepath = "./data/dark-enhance-demo.bmp";
$image = file_to_base64($filepath);

$result = dark_enhance_aksk($app_key, $app_secret, $image, 0.9);
print_r($result);
$resultobj = json_decode($result, true);
$basestr = $resultobj["result"];
base64_to_file("data/dark-enhance-demo-aksk.bmp", $basestr);
