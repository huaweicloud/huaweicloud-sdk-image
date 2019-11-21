# -*- coding:utf-8 -*-
from image_sdk.asr_bgm import asr_bgm_aksk
from image_sdk.utils import init_global_env

if __name__ == '__main__':
    # Services currently support North China-Beijing(cn-north-4), Asia Pacific-Hong Kong(ap-southeast-1)
    init_global_env('cn-north-4')

    #
    # access asr, asr_bgm,post data by ak,sk
    #
    app_key = '*************'
    app_secret = '*************'

    # The OBS link should match the region, and the OBS resources of different regions are not shared
    demo_data_url = 'https://sdk-obs-source-save.obs.cn-north-4.myhuaweicloud.com/bgm_recognition'
    result = asr_bgm_aksk(app_key, app_secret, demo_data_url)
    print(result)