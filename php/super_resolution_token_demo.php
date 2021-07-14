<?php
/**
 * 图像超分重建服务token 方式请求的示例
 */
require "./image_sdk/gettoken.php";
require "./image_sdk/super_resolution.php";
require "./image_sdk/utils.php";

// region目前支持华北-北京(cn-north-4)、中国-香港(ap-southeast-1)
init_region($region = 'cn-north-4');

$username = "********";      // 配置用户名
$password = "********";      // 密码
$domainName = "*********";   // 配置用户名

$filepath = "./data/super-resolution-demo.png";
$data = file_to_base64($filepath);

$token = get_token($username, $password, $domainName);

$result = super_resolution($token, $data, 4, "ESPCN");
echo $result;
$resultobj = json_decode($result, true);
$basestr = $resultobj["result"];
base64_to_file("data/super-resolution-token.png", $basestr);