/**
 * 翻拍识别服务ak,sk请求方式的使用示例
 */
var recapture = require("./image_sdk/recapture_detect");
var utils = require("./image_sdk/utils");

// 初始化服务的区域信息，目前支持华北-北京(cn-north-4)、亚太-香港(ap-southeast-1)等区域信息
utils.initRegion("cn-north-4");

var app_key = "*************";
var app_secret = "************";

var filepath = "./data/recapture-detect-demo.jpg";
var data = utils.changeFileToBase64(filepath);

// obs链接需要和region区域一致，不同的region的obs资源不共享
demo_data_url = "https://sdk-obs-source-save.obs.cn-north-4.myhuaweicloud.com/recapture-detect.jpg";

recapture.recapture_detect_aksk(app_key, app_secret, data, "", 0.99, ['recapture'], function (result) {
    console.log(result);
});

recapture.recapture_detect_aksk(app_key, app_secret, "", demo_data_url, 0.99, ['recapture'], function (result) {
    console.log(result);
});