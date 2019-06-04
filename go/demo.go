package main

import (
	"image_sdk/src/sdk"
	"encoding/json"
	"fmt"
)

func main() {
	/*
	 * Services currently support North China-Beijing 1 (cn-north-1),
	 * North China-Beijing 4 (cn-north-4), Asia Pacific-Hong Kong (ap-southeast-1)
	 */
	sdk.InitRegion("cn-north-1")
	
	ak := "*******" // your AppKey
	sk := "*******" // your AppSecret

	// The sample for asr bgm service
	// Test_BgmAkskDemo(ak, sk)

	// The sample for image celebrity recognition service
	// Test_CelebrityRecognitionAkskDemo(ak, sk)

	// The sample for image defog service
	// Test_ImageDefogAkskDemo(ak, sk)

	// The sample for image tagging service
	// Test_ImageTaggingAkskDemo(ak, sk)
	
	// The sample for instrument recognition service
	// Test_InstrumentAkskDemo(ak, sk)

	// The sample for image dark enhance
	// Test_DarkEnhanceAkskDemo(ak, sk)

	// The sample for recapture detect
	// Test_RecaptureDetectAkskDemo(ak, sk)

	// The sample for super resolution
	Test_SuperResolutionAkskDemo(ak, sk)
	
}

func Test_InstrumentAkskDemo(ak string, sk string) {

	// post data by url
	url := "http://img.ikstatic.cn/MTU0NjQ2NzM1MTk0NCM5MzAjanBn.jpg"
	var threshold float32 = 0.5
	result := sdk.InstrumentAksk(ak, sk, "", url, threshold)
	fmt.Println(result)

	// post data by native file
	filepath := "data/instrument.jpg"
	image := sdk.ChangeFileToBase64(filepath)

	result = sdk.InstrumentAksk(ak, sk, image, "", threshold)
	fmt.Println(result)

}

func Test_BgmAkskDemo(ak string, sk string) {

	// The OBS link should match the region, and the OBS resources of different regions are not shared
	url := "https://obs-test-llg.obs.cn-north-1.myhuaweicloud.com/bgm_recognition"
	result := sdk.AsrBgmAksk(ak, sk, url)
	fmt.Println(result)
}

func Test_CelebrityRecognitionAkskDemo(ak string, sk string) {
	var threshold float32 = 0.48

	// The OBS link should match the region, and the OBS resources of different regions are not shared
	url := "https://ais-sample-data.obs.cn-north-1.myhuaweicloud.com/celebrity-recognition.jpg"
	result := sdk.CelebrityRecognitionAksk(ak, sk, "", url, threshold)
	fmt.Println(result)

	// post data by native file
	filepath := "data/celebrity-recognition.jpg"
	image := sdk.ChangeFileToBase64(filepath)
	result = sdk.CelebrityRecognitionAksk(ak, sk, image, "", threshold)
	fmt.Println(result)
}

func Test_DarkEnhanceAkskDemo(ak string, sk string) {
	var brightness float32 = 0.9
	var resultMap map[string]interface{}

	filepath := "data/dark-enhance-demo.bmp"
	image := sdk.ChangeFileToBase64(filepath)
	result := sdk.DarkEnhanceAksk(ak, sk, image, brightness)
	json.Unmarshal([]byte(result), &resultMap)
	sdk.Base64ToFile("data/dark-enhance-demo-aksk.bmp", resultMap["result"].(string))
}

func Test_ImageDefogAkskDemo(ak string, sk string) {

	var resultMap map[string]interface{}

	var naturalLook bool = true
	var gamma float32 = 1.5

	filepath := "data/defog-demo.png"
	image := sdk.ChangeFileToBase64(filepath)
	result := sdk.ImageDefogAksk(ak, sk, image, gamma, naturalLook)
	json.Unmarshal([]byte(result), &resultMap)
	sdk.Base64ToFile("data/defog-demo-aksk.png", resultMap["result"].(string))
}

func Test_ImageTaggingAkskDemo(ak string, sk string) {
	var language string = "en"
	var limit int = -1
	var threshold float32 = 60.0

	// The OBS link should match the region, and the OBS resources of different regions are not shared
	url := "https://ais-sample-data.obs.myhuaweicloud.com/tagging-normal.jpg"
	result := sdk.ImageTaggingAksk(ak, sk, "", url, language, limit, threshold)
	fmt.Println(result)

	// post data by native file
	filepath := "data/image-tagging-demo.jpg"
	image := sdk.ChangeFileToBase64(filepath)
	result = sdk.ImageTaggingAksk(ak, sk, image, "", language, limit, threshold)
	fmt.Println(result)
}

func Test_RecaptureDetectAkskDemo(ak string, sk string) {

	var scene = []string{"recapture"}
	var threshold float32 = 0.95

	// The OBS link should match the region, and the OBS resources of different regions are not shared
	url := "https://ais-sample-data.obs.myhuaweicloud.com/recapture-detect.jpg"
	result := sdk.RecaptureDetectAksk(ak, sk, "", url, threshold, scene)
	fmt.Println(result)

	// post data by native file
	filepath := "data/recapture-detect-demo.jpg"
	image := sdk.ChangeFileToBase64(filepath)
	result = sdk.RecaptureDetectAksk(ak, sk, image, "", threshold, scene)
	fmt.Println(result)
}

func Test_SuperResolutionAkskDemo(ak string, sk string) {

	var resultMap map[string]interface{}

	var scale int = 3
	var model string = "ESPCN"

	filepath := "data/super-resolution-demo.png"
	image := sdk.ChangeFileToBase64(filepath)
	result := sdk.SuperResolutionAksk(ak, sk, image, scale, model)
	json.Unmarshal([]byte(result), &resultMap)
	sdk.Base64ToFile("data/super-resolution-demo-aksk.png", resultMap["result"].(string))
}