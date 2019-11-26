/**
 * 名人识别检测服务token 方式请求的使用示例
 */
var recognition = require("./image_sdk/celebrity_recognition");
var token = require("./image_sdk/gettoken");
var utils = require("./image_sdk/utils");

// 初始化服务的区域信息，目前支持华北-北京(cn-north-4)、亚太-香港(ap-southeast-1)等区域信息
utils.initRegion("cn-north-4");

var username = "*******";        // 配置用户名
var domain_name = "*******";     // 配置用户名
var password = "*******";        // 密码

var filepath = "./data/celebrity-recognition.jpg";
var data = utils.changeFileToBase64(filepath);

// obs链接需要和region区域一致，不同的region的obs资源不共享
demo_data_url = 'https://sdk-obs-source-save.obs.cn-north-4.myhuaweicloud.com/celebrity-recognition.jpg';

token.getToken(username, domain_name, password, function (token) {

    recognition.celebrity_recognition(token, data, "", 0.48, function (result) {
        console.log(result);
    });

    recognition.celebrity_recognition(token, "", demo_data_url,0.48, function (result) {
        console.log(result);
    })
});