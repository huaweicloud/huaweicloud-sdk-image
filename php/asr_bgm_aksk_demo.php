<?php
/**
 *  视频背景音乐识别服务的ak,sk请求方式示例
 */

require "./image_sdk/asr_bgm.php";
require "./image_sdk/utils.php";

// region目前支持华北-北京(cn-north-4)、中国-香港(ap-southeast-1)
init_region($region = 'cn-north-4');

$app_key = "*************";
$app_secret = "*************";

// obs链接需要和region区域一致，不同的region的obs资源不共享
$data_url = "https://sdk-obs-source-save.obs.cn-north-4.myhuaweicloud.com/bgm_recognition";

$result = asr_bgm_aksk($app_key, $app_secret, $data_url);
echo $result;
$resultobj = json_decode($result, true);
echo $resultobj["result"]["audio_name"];

