/**
 * 背景音乐识别服务token 方式请求的使用示例
 */
var bgm = require("./image_sdk/asr_bgm");
var token = require("./image_sdk/gettoken");
var utils = require("./image_sdk/utils");

// 初始化服务的区域信息，目前支持华北-北京(cn-north-4)、亚太-香港(ap-southeast-1)等区域信息
utils.initRegion("cn-north-4");

var username = "*******";        // 配置用户名
var domain_name = "*******";     // 配置用户名
var password = "*******";        // 密码

// obs链接需要和region区域一致，不同的region的obs资源不共享
obsUrl = "https://sdk-obs-source-save.obs.cn-north-4.myhuaweicloud.com/bgm_recognition";
token.getToken(username, domain_name, password, function (token) {

    bgm.asr_bgm(token, obsUrl, function (result) {
        console.log(result);
    });
});