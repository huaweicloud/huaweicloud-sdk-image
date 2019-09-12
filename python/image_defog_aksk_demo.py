# -*- coding:utf-8 -*-
from image_sdk.utils import encode_to_base64
from image_sdk.utils import decode_to_wave_file
from image_sdk.image_defog import image_defog_aksk
from image_sdk.utils import init_global_env
import json

if __name__ == '__main__':
    # Services currently support North China-Beijing(cn-north-4), Asia Pacific-Hong Kong(ap-southeast-1)
    init_global_env('cn-north-4')

    #
    # access image defog,post data by ak,sk
    #
    app_key = '*************'
    app_secret = '************'

    # call interface use base64 file
    result = image_defog_aksk(app_key, app_secret, encode_to_base64('data/defog-demo.png'), '1.5')
    result_obj = json.loads(result)
    decode_to_wave_file(result_obj['result'], 'data/defog-demo-aksk.png')

