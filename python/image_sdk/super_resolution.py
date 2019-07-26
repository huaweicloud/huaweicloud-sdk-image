# -*- coding:utf-8 -*-

import sys
import json
import image_sdk.ais as ais
import image_sdk.utils as utils
import image_sdk.signer as signer

#
# access image super resolution,post data by token
#
def super_resolution(token, image, scale=3, model="ESPCN"):
    endpoint = utils.get_endpoint(ais.AisService.IMAGE_SERVICE)
    _url = 'https://%s%s' % (endpoint, ais.ImageURI.SUPER_RESOLUTION)

    if sys.version_info.major >= 3:
        if image:
            image = image.decode("utf-8")

    _data = {
        "image": image,
        "scale": scale,
        "model": model
    }

    status_code, resp = utils.request_token(_url, _data, token)
    if sys.version_info.major < 3:
        return resp
    else:
        return resp.decode('utf-8')

#
# access image super resolution enhance,post data by sk,sk
#
def super_resolution_aksk(_ak, _sk, image, scale=3, model="ESPCN"):
    endpoint = utils.get_endpoint(ais.AisService.IMAGE_SERVICE)
    _url = 'https://%s%s' % (endpoint, ais.ImageURI.SUPER_RESOLUTION)

    sig = signer.Signer()
    sig.AppKey = _ak
    sig.AppSecret = _sk

    if sys.version_info.major >= 3:
        if image:
            image = image.decode("utf-8")

    _data = {
        "image": image,
        "scale": scale,
        "model": model
    }

    kreq = signer.HttpRequest()
    kreq.scheme = "https"
    kreq.host = endpoint
    kreq.uri = "/v1.0/vision/super-resolution"
    kreq.method = "POST"
    kreq.headers = {"Content-Type": "application/json"}
    kreq.body = json.dumps(_data)

    status_code, resp = utils.request_aksk(sig, kreq, _url)
    if sys.version_info.major < 3:
        return resp
    else:
        return resp.decode('utf-8')

