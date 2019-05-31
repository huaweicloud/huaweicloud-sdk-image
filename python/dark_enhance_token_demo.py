# -*- coding:utf-8 -*-
from image_sdk.gettoken import get_token
from image_sdk.utils import encode_to_base64
from image_sdk.utils import decode_to_wave_file
from image_sdk.dark_enhance import dark_enhance
from image_sdk.utils import init_global_env
import json

if __name__ == '__main__':
    # Services currently support North China-Beijing(cn-north-1,cn-north-4), Asia Pacific-Hong Kong(ap-southeast-1)
    init_global_env('cn-north-1')

    #
    # access image dark enhance by token
    #
    user_name = '*****'
    password = '******'
    account_name = '*****'  # the same as user_name in commonly use

    token = get_token(user_name, password, account_name)

    # call interface use base64 file
    result = dark_enhance(token, encode_to_base64('data/dark-enhance-demo.bmp'), '0.95')
    result_obj = json.loads(result)
    decode_to_wave_file(result_obj['result'], 'data/dark-enhance-demo-token.bmp')