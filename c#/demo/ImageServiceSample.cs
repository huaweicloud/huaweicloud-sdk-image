using System;
using Image.Models;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;

namespace ImageDemo
{
    class ImageServiceSample
    {
        static void Main(string[] args)
        {
            // Services currently support North China-Beijing (cn-north-4), CN-Hong Kong (ap-southeast-1)
            String regionName = "*******";
            String username = "*******";
            String password = "*******";
            String domainName = "*******";

            // domain names for image service
            String IMAGE_ENDPOINT = ImageService.getEndponit(regionName);

            // get token domain name 
            String IAM_ENPOINT = "iam.myhuaweicloud.com";

            String token = Authentication.GetToken(username, domainName, password, regionName, IAM_ENPOINT);

            // image tagging service example
            ImageTagging(token, IMAGE_ENDPOINT);

            // asr bgm service example
            AsrBgm(token, IMAGE_ENDPOINT);

            // celebrity recognition service example
            CelebrityRecognition(token, IMAGE_ENDPOINT);

            // dark enhance service example
            DarkEnhance(token, IMAGE_ENDPOINT);

            // image defog detect service example
            ImageDefog(token, IMAGE_ENDPOINT);

            // image recapture detect service example
            RecaptureDetect(token, IMAGE_ENDPOINT);

            // image super resolution service example
            SuperResolution(token, IMAGE_ENDPOINT);

        }

        private static void ImageTagging(String token, String endpoint)
        {
            // The obs url of file
            String dataUrl = "";
            // The confidence interval
            float threshold = 60;
            // The tagging language
            String language = "en";
            // The tagging amount limit of return
            int limit = 5;          

            // post data by native file
            String image = utils.ConvertFileToBase64("../../data/image-tagging-demo.jpg");

            String reslut = ImageService.ImageTaggingToken(token, image, dataUrl, threshold, language, limit, endpoint);
            Console.WriteLine(reslut);

            // The OBS link must match the region, and the OBS resources of different regions are not shared
            dataUrl = "https://sdk-obs-source-save.obs.cn-north-4.myhuaweicloud.com/tagging-normal.jpg";

            // post data by obs url
            reslut = ImageService.ImageTaggingToken(token, "", dataUrl, 60, "en", 5, endpoint);
            Console.WriteLine(reslut);
            Console.ReadKey();

        }

        private static void AsrBgm(String token, String endpoint)
        {
            // The OBS link must match the region, and the OBS resources of different regions are not shared
            String dataUrl = "https://sdk-obs-source-save.obs.cn-north-4.myhuaweicloud.com/bgm_recognition";

            // post data by obs url
            String reslut = ImageService.AsrBgmToken(token, dataUrl, endpoint);
            Console.WriteLine(reslut);
            Console.ReadKey();
        }

        private static void CelebrityRecognition(String token, String endpoint)
        {
            // The obs url of file
            String dataUrl = "";
            // The confidence interval,default 0.48f (0-1)
            float threshold = 0.48f;   

            // post data by native file
            String data = utils.ConvertFileToBase64("../../data/celebrity-recognition.jpg");
            String reslut = ImageService.CelebrityRecognitionToken(token, data, dataUrl, threshold, endpoint);
            Console.WriteLine(reslut);

            // The OBS link must match the region, and the OBS resources of different regions are not shared
            dataUrl = "https://sdk-obs-source-save.obs.cn-north-4.myhuaweicloud.com/celebrity-recognition.jpg";

            // post data by obs url
            reslut = ImageService.CelebrityRecognitionToken(token, "", dataUrl, threshold, endpoint);
            Console.WriteLine(reslut);
            Console.ReadKey();

        }

        private static void DarkEnhance(String token, String endpoint)
        {
            // The brightness interval,default 0.9f
            float brightness = 0.9f;   

            // post data by native file
            String data = utils.ConvertFileToBase64("../../data/dark-enhance-demo.bmp");

            String reslut = ImageService.DarkEnhanceToken(token, data, brightness, endpoint);
            JObject joResult = (JObject)JsonConvert.DeserializeObject(reslut);
            String filePath = utils.Base64ToFileAndSave(joResult["result"].ToString(), @"../../data/dark-enhance-token.bmp");
            Console.WriteLine(filePath);
            Console.ReadKey();
        }

        private static void ImageDefog(String token, String endpoint)
        {
            // Is natural 
            bool natural_look = true;
            // The gama correction value,default 1.5. range : [0.1,10]
            float gamma = 1.5f;         

            // post data by native file
            String data = utils.ConvertFileToBase64("../../data/defog-demo.png");
            String reslut = ImageService.ImageDefogToken(token, data, gamma, natural_look, endpoint);

            JObject joResult = (JObject)JsonConvert.DeserializeObject(reslut);
            String resultPath = utils.Base64ToFileAndSave(joResult["result"].ToString(), @"../../data/defog-demo-token.png");

            Console.WriteLine(resultPath);
            Console.ReadKey();
        }

        private static void RecaptureDetect(String token, String endpoint)
        {
            // The obs url of file
            String dataUrl = "";
            // The image content confidence interval,"politics" default 0.95f.range of  [0-1] 
            float threshold = 0.95f;
            // The scene of recapture detect
            JArray scene = new JArray();    
            scene.Add("recapture");

            // post data by native file
            String data = utils.ConvertFileToBase64("../../data/recapture-detect-demo.jpg");
            String reslut = ImageService.RecaptureDetectToken(token, data, dataUrl, threshold, scene, endpoint);
            Console.WriteLine(reslut);

            // The OBS link must match the region, and the OBS resources of different regions are not shared
            dataUrl = "https://sdk-obs-source-save.obs.cn-north-4.myhuaweicloud.com/recapture-detect.jpg";

            // post data by obs url
            reslut = ImageService.RecaptureDetectToken(token, "", dataUrl, threshold, scene, endpoint);
            Console.WriteLine(reslut);
            Console.ReadKey();
        }

        private static void SuperResolution(String token, String endpoint)
        {
            int scale = 3;                  // The result of magnification， default 3 ,only 3 or 4   
            String model = "ESPCN";         // The algorithm pattern,default ESPCN

            // post data by native file
            String data = utils.ConvertFileToBase64("../../data/super-resolution-demo.png");
            String reslut = ImageService.SuperResolutionToken(token, data, scale, model, endpoint);

            JObject joResult = (JObject)JsonConvert.DeserializeObject(reslut);
            String filePath = utils.Base64ToFileAndSave(joResult["result"].ToString(), @"../../data/super-resolution-token.png");
            Console.WriteLine(filePath);
            Console.ReadKey();
        }
    }
}
