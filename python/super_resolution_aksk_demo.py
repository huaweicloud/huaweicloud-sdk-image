# -*- coding:utf-8 -*-
from image_sdk.utils import encode_to_base64
from image_sdk.utils import decode_to_wave_file
from image_sdk.super_resolution import super_resolution_aksk
from image_sdk.utils import init_global_env
import json

if __name__ == '__main__':
    # Services currently support North China-Beijing(cn-north-4), CN-Hong Kong(ap-southeast-1)
    init_global_env('cn-north-4')

    #
    # access image super resolution enhance,post data by sk,sk
    #
    app_key = '*************'
    app_secret = '************'

    # call interface use base64 file
    result = super_resolution_aksk(app_key, app_secret, encode_to_base64('data/super-resolution-demo.png'), 3)
    result_obj = json.loads(result)
    decode_to_wave_file(result_obj['result'], 'data/super-resolution-demo-aksk.png')
