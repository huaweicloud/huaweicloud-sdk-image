/**
 * 图像分析服务类请求url常量配置信息
 * @type
 */
var ais = {

    // token请求域名
    IAM_ENPOINT: 'iam.myhuaweicloud.com',

    // token请求uri
    AIS_TOKEN: '/v3/auth/tokens',

    // 背景音乐识别服务的uri
    ASR_BGM: '/v1.0/bgm/recognition',

    // 图像标签服务uri
    IMAGE_TAGGING: '/v1.0/image/tagging',

    // 图像翻拍检测服务uri
    RECAPTURE_DETECT: '/v1.0/image/recapture-detect',

    // 图像低光照增强服务uri
    DARK_ENHANCE: '/v1.0/vision/dark-enhance',

    // 图像去雾服务uri
    DEFOG: '/v1.0/vision/defog',

    // 超分重建服务uri
    SURPER_RESOLUTION: '/v1.0/vision/super-resolution',

    // 名人识别服务的uri
    CELEBRITY_RECOGNITION: '/v1.0/image/celebrity-recognition',

    // image服务的服务类别
    IMAGE_SERVICE: 'image',

    // 最大重试次数
    RETRY_TIMES_MAX: 3

};
module.exports = ais;