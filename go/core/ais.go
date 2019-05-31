package core

const (
	// domain name for get token
	IAM_ENPOINT string = "iam.cn-north-1.myhuaweicloud.com"

	// the uri for get token
	IAM_TOKEN string = "/v3/auth/tokens"

	// instrument recognition uri
	INSTRUMENT = "/v1.0/image/classify/instrument"

	// the uri for Service image tagging
	IMAGE_TAGGING string = "/v1.0/image/tagging"

	// the uri for Service asr bgm
	ASR_BGM string = "/v1.0/bgm/recognition"

	// the uri for Service celebrity recognition
	CELEBRITY_RECOGNITION string = "/v1.0/image/celebrity-recognition"

	// the uri for Service dark enhance
	DARK_ENHANCE string = "/v1.0/vision/dark-enhance"

	// the uri for Service image defog
	IMAGE_DEFOG string = "/v1.0/vision/defog"

	// the uri for Service recapture detect
	RECAPTURE_DETECT string = "/v1.0/image/recapture-detect"

	// the uri for Service super resolution
	SUPER_RESOLUTION string = "/v1.0/vision/super-resolution"
	
	// image service type 
	IMAGE string = "image"
	
	// the max retry times
	RETRY_MAX_TIMES int = 3
)
