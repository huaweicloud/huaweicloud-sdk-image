<?php
/**
 * 图像识别服务请求url常量及配置信息
 * Created by PhpStorm.
 * User: Administrator
 * Date: 2018/11/16
 * Time: 10:44
 */

// token请求域名
define("IAM_ENPOINT", "iam.myhuaweicloud.com");

// token请求uri
define("AIS_TOKEN", "/v3/auth/tokens");

// 背景音乐识别服务的uri
define("ASR_BGM", "/v1.0/bgm/recognition");

// 图像标签服务uri
define("IMAGE_TAGGING", "/v1.0/image/tagging");

// 图像翻拍检测服务uri
define("RECAPTURE_DETECT", "/v1.0/image/recapture-detect");

// 图像低光照增强服务uri
define("DARK_ENHANCE", "/v1.0/vision/dark-enhance");

// 图像去雾服务uri
define("DEFOG", "/v1.0/vision/defog");

// 超分重建服务uri
define("SURPER_RESOLUTION", "/v1.0/vision/super-resolution");

// 名人识别服务的uri
define("CELEBRITY_RECOGNITION", "/v1.0/image/celebrity-recognition");

// 图像服务类别
define("IMAGE", "image");

// 异步查询任务失败最大重试次数
define("RETRY_MAX_TIMES", 3);