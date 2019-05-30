using System;


namespace Image.Models
{
    public class Ais
    {
        // the uri for get token 
        public static String IAM_TOKEN = "/v3/auth/tokens";

        // the uri for Service image tagging 
        public static String IMAGE_TAGGING = "/v1.0/image/tagging";

        // the uri for Service asr bgm 
        public static String ASR_BGM = "/v1.0/bgm/recognition";

        // the uri for Service celebrity recognition
        public static String CELEBRITY_RECOGNITION = "/v1.0/image/celebrity-recognition";

        // the uri for Service dark enhance
        public static String DARK_ENHANCE = "/v1.0/vision/dark-enhance";

        // the uri for Service image defog
        public static String IMAGE_DEFOG = "/v1.0/vision/defog";

        // the uri for Service recapture detect
        public static String RECAPTURE_DETECT = "/v1.0/image/recapture-detect";

        // the uri for Service super resolution
        public static String SUPER_RESOLUTION = "/v1.0/vision/super-resolution";

        // retry max times
        public static int RETRY_MAX_TIMES = 3;
    }
}
