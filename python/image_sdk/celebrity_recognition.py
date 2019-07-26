# -*- coding:utf-8 -*-

import sys
import json
import image_sdk.ais as ais
import image_sdk.utils as utils
import image_sdk.signer as signer


#
# access image tagging
#
def celebrity_recognition(token, image, url, threshold=4.8):
    endpoint = utils.get_endpoint(ais.AisService.IMAGE_SERVICE)
    _url = 'https://%s%s' % (endpoint, ais.ImageURI.CELEBRITY_RECOGNITION)

    if sys.version_info.major >= 3:
        if image:
            image = image.decode("utf-8")

    _data = {
        "image": image,
        "url": url,
        "threshold": threshold
    }

    status_code, resp = utils.request_token(_url, _data, token)
    if sys.version_info.major < 3:
        return resp.decode('unicode-escape').encode('utf-8')
    else:
        return resp.decode('unicode_escape')


#
# access image tagging ，post data by ak,sk
#
def celebrity_recognition_aksk(_ak, _sk, image, url, threshold=4.8):
    endpoint = utils.get_endpoint(ais.AisService.IMAGE_SERVICE)
    _url = 'https://%s%s' % (endpoint, ais.ImageURI.CELEBRITY_RECOGNITION)

    sig = signer.Signer()
    sig.AppKey = _ak
    sig.AppSecret = _sk

    if sys.version_info.major >= 3:
        if image:
            image = image.decode("utf-8")

    _data = {
        "image": image,
        "url": url,
        "threshold": threshold,
    }

    kreq = signer.HttpRequest()
    kreq.scheme = "https"
    kreq.host = endpoint
    kreq.uri = "/v1.0/image/celebrity-recognition"
    kreq.method = "POST"
    kreq.headers = {"Content-Type": "application/json"}
    kreq.body = json.dumps(_data)

    status_code, resp = utils.request_aksk(sig, kreq, _url)
    if sys.version_info.major < 3:
        return resp.decode('unicode-escape').encode('utf-8')
    else:
        return resp.decode('unicode_escape')
