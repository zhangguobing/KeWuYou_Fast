package com.weiwoju.kewuyou;

/**
 * Created by zhangguobing on 2017/4/21.
 */
public class Constants {

    public class Header {
        public static final String CONTENT_TYPE = "Content-Type";
        public static final String AUTHORIZATION = "Authorization";
        public static final String HTTP_TIMESTAMP = "Http-Timestamp";
        public static final String HTTP_APP_VERSION = "Http-App-Version";
        public static final String HTTP_APP_KEY = "Http-App-Key";
        public static final String HTTP_DEVICE_ID = "Http-Device-Id";
        public static final String HTTP_DEVICE_TYPE = "Http-Device-Type";
        public static final String HTTP_SIGNATURE = "Http-Signature";
        public static final String SESSION_ID = "sessionid";
    }

    public class HttpCode {
        public static final String HTTP_UNAUTHORIZED = "401";
        public static final String HTTP_SERVER_ERROR = "500";
        public static final String HTTP_NOT_HAVE_NETWORK = "600";
        public static final String HTTP_NETWORK_ERROR = "700";
        public static final String HTTP_UNKNOWN_ERROR = "800";
    }

    public class CustomHttpCode {
        public static final String HTTP_SUCCESS = "0";
        public static final String HTTP_SESSIONID_EXPIRE = "SESSIONID_EXPIRE";
    }

    public class Persistence {
        public static final String USER_INFO = "app.kewuyou.userinfo";
        public static final String LAST_LOGIN_PHONE = "app.kewuyou.phone";
        public static final String ACCESS_TOKEN = "app.kewuyou.access_token";
        public static final String REFRESH_TOKEN = "app.kewuyou.refresh_token";
        public static final String SHARE_FILE = "app.kewuyou.share";
        public static final String SESSION_ID = "app.kewuyou.session_id";
    }
}
