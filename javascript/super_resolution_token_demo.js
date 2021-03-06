/**
 * 超分重建服务token方式请求的使用示例
 */
var supresol = require("./image_sdk/super_resolution");
var token = require("./image_sdk/gettoken");
var utils = require("./image_sdk/utils");

// 初始化服务的区域信息，目前支持华北-北京(cn-north-4)、中国-香港(ap-southeast-1)等区域信息
utils.initRegion("cn-north-4");

var username = "*******";        // 配置用户名
var domain_name = "*******";     // 配置用户名
var password = "*******";        // 密码

var filepath = "./data/super-resolution-demo.png";
var data = utils.changeFileToBase64(filepath);

/**
 * token 方式获取结果
 * @type {string}
 */
token.getToken(username, domain_name, password, function (token) {

    supresol.super_resolution(token, data, 3, "ESPCN", function (result) {
        var resultObj = JSON.parse(result);
        utils.getFileByBase64Str("./data/super-resolution-demo-token.png", resultObj.result);
    })
});
