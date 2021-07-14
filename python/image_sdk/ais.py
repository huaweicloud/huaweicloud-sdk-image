#!/usr/bin/python
# -*- coding: utf-8 -*-

class AisEndpoint:
    IAM_ENPOINT = 'iam.myhuaweicloud.com'


class ImageURI:
    ASR_BGM = '/v1.0/bgm/recognition'
    CELEBRITY_RECOGNITION = '/v1.0/image/celebrity-recognition'
    DARK_ENHANCE = '/v1.0/vision/dark-enhance'
    IMAGE_DEFOG = '/v1.0/vision/defog'
    IMAGE_TAGGING = '/v1.0/image/tagging'
    RECAPTURE_DETECT = '/v1.0/image/recapture-detect'
    SUPER_RESOLUTION = '/v1.0/vision/super-resolution'

class AisService:
    IMAGE_SERVICE = 'image'
    REGION_MSG = 'region_name'
    CERTIFICATE_VALIDATION = True
    DEFAULT_TIMEOUT = 20

