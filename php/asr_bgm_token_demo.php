<?php
/**
 * 视频背景音乐识别服务的token方式请求示例
 */
require "./image_sdk/gettoken.php";
require "./image_sdk/asr_bgm.php";
require "./image_sdk/utils.php";

// region目前支持华北-北京(cn-north-4)、亚太-香港(ap-southeast-1)
init_region($region = 'cn-north-4');

$username = "********";      // 配置用户名
$password = "********";      // 密码
$domainName = "*********";   // 配置用户名

$token = get_token($username, $password, $domainName);

// obs链接需要和region区域一致，不同的region的obs资源不共享
$data_url = "https://sdk-obs-source-save.obs.cn-north-4.myhuaweicloud.com/bgm_recognition";

$result = asr_bgm($token, $data_url);
echo $result;
$resultobj = json_decode($result, true);
echo $resultobj["result"]["audio_name"];
