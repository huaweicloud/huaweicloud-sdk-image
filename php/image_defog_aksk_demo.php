<?php
/**
 * 图像去雾服务aksk 方式请求的示例
 */
require "./image_sdk/image_defog.php";
require "./image_sdk/utils.php";

// region目前支持华北-北京(cn-north-4)、中国-香港(ap-southeast-1)
init_region($region = 'cn-north-4');

$app_key = "*************";
$app_secret = "*************";

$filepath = "./data/defog-demo.png";
$image = file_to_base64($filepath);

$result = image_defog_aksk($app_key, $app_secret, $image, 1.5, true);
print_r($result);
$resultobj = json_decode($result, true);
$basestr = $resultobj["result"];
base64_to_file("data/defog-demo-aksk.png", $basestr);
