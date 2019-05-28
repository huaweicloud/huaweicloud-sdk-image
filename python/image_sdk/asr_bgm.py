# -*- coding:utf-8 -*-

import sys
import json
import image_sdk.ais as ais
import image_sdk.utils as utils
import image_sdk.signer as signer

#
# access asr, asr_bgm,post data by token
#
def asr_bgm(token, url):
    endpoint = utils.get_endpoint(ais.AisService.IMAGE_SERVICE)
    _url = 'https://%s%s' % (endpoint, ais.ImageURI.ASR_BGM)

    _data = {
        "url": url,
    }

    status_code, resp = utils.request_token(_url, _data, token)
    if sys.version_info.major < 3:
        return resp.decode('unicode-escape').encode('utf-8')
    else:
        return resp.decode('unicode_escape')


#
# access asr, asr_bgm,post data by ak,sk
#
def asr_bgm_aksk(_ak, _sk, url):
    endpoint = utils.get_endpoint(ais.AisService.IMAGE_SERVICE)
    _url = 'https://%s%s' % (endpoint, ais.ImageURI.ASR_BGM)

    sig = signer.Signer()
    sig.AppKey = _ak
    sig.AppSecret = _sk
    _data = {
        "url": url,
    }

    kreq = signer.HttpRequest()
    kreq.scheme = "https"
    kreq.host = endpoint
    kreq.uri = "/v1.0/bgm/recognition"
    kreq.method = "POST"
    kreq.headers = {"Content-Type": "application/json"}
    kreq.body = json.dumps(_data)

    status_code, resp = utils.request_aksk(sig, kreq, _url)
    if sys.version_info.major < 3:
        return resp.decode('unicode-escape').encode('utf-8')
    else:
        return resp.decode('unicode_escape')


