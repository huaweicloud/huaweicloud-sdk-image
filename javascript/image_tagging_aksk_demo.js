/**
 * 图像标签检测服务aksk请求方式的使用示例
 */
var tagging = require("./image_sdk/image_tagging");
var utils = require("./image_sdk/utils");

// 初始化服务的区域信息，目前支持华北-北京(cn-north-4)、中国-香港(ap-southeast-1)等区域信息
utils.initRegion("cn-north-4");

var app_key = "*************";
var app_secret = "************";

var filepath = "./data/image-tagging-demo.jpg";
var data = utils.changeFileToBase64(filepath);

// obs链接需要和region区域一致，不同的region的obs资源不共享
demo_data_url = "https://sdk-obs-source-save.obs.cn-north-4.myhuaweicloud.com/tagging-normal.jpg";

tagging.image_tagging_aksk(app_key, app_secret, data, "", 60, "en", 5, function (result) {
    console.log(result);
});

tagging.image_tagging_aksk(app_key, app_secret, "", demo_data_url, 60, "en", 5, function (result) {
    console.log(result);
});