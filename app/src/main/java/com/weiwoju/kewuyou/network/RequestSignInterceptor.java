package com.weiwoju.kewuyou.network;

import android.text.TextUtils;
import android.util.Log;

import com.google.common.net.PercentEscaper;
import com.weiwoju.kewuyou.context.AppConfig;
import com.weiwoju.kewuyou.context.AppContext;
import com.weiwoju.kewuyou.context.AppCookie;
import com.weiwoju.kewuyou.util.AppUtils;
import com.weiwoju.kewuyou.util.MD5Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okio.Buffer;

public class RequestSignInterceptor implements Interceptor {

    private static final String LOG_TAG = RequestSignInterceptor.class.getSimpleName();

    private static final String APP_KEY = AppConfig.APP_KEY;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        return chain.proceed(sign(request));
    }

    /**
     * 对请求进行签名
     * @param request 请求
     * @return 签名后的请求
     */
    private synchronized Request sign(Request request) {
        try {
            request = addCommonParams(request);

            Map<String, String> params = new HashMap<>();
            collectQueryParameters(request, params);
            collectBodyParameters(request, params);

            String signature = generateSignature(params);
            // 将签名写入参数中
            return writeSignature(signature, request);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "请求签名失败");
        }
        return request;
    }

    /**
     * 手机get请求参数
     * @param request 请求
     * @param out 参数对
     */
    private void collectQueryParameters(Request request, Map<String, String> out) {
        String url = request.url().toString();
        int q = url.indexOf('?');
        if (q >= 0) {
            out.putAll(decodeForm(url.substring(q + 1)));
        }
    }

    /**
     * 收集post请求参数
     * @param request 请求
     * @param out 参数对
     */
    private void collectBodyParameters(Request request, Map<String, String> out) throws IOException {
        if (request.body() != null && request.body().contentType() != null) {
            String contentType = request.body().contentType().toString();
            if (contentType.equals("application/x-www-form-urlencoded")) {
                Buffer buf = new Buffer();
                request.body().writeTo(buf);
                InputStream payload = buf.inputStream();
                out.putAll(decodeForm(payload));
            }
        }
    }

    private String generateSignature(Map<String,String> params){
        try {
            String sessionId = AppCookie.getSessionId();
            if(sessionId != null){
                params.put("sessionid",sessionId);
            }

            Map<String,String> mapParams = sortMapByKey(params);

            String sign = "";
            for (Map.Entry<String,String> entry : mapParams.entrySet()){
                sign = sign + "&" + entry.getKey() + "=" + URLDecoder.decode(entry.getValue(),"UTF-8");
            }
            sign = sign.substring(1);
            sign = sign + "&key=" + APP_KEY;

            return MD5Utils.MD5(sign).toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 使用 Map按key进行排序
     * @param map
     * @return
     */
    public static Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        Map<String, String> sortMap = new TreeMap<>(
                new MapKeyComparator());

        sortMap.putAll(map);

        return sortMap;
    }

    static class MapKeyComparator implements Comparator<String> {

        @Override
        public int compare(String str1, String str2) {

            return str1.compareTo(str2);
        }
    }

    /**
     * 添加公共参数
     * @return a new request with common params
     */
    private Request addCommonParams(Request request){
        if("GET".equals(request.method())){
            HttpUrl.Builder builder = request.url()
                    .newBuilder()
                    .addEncodedQueryParameter("nonce_str", getRandomStr32())
                    .addEncodedQueryParameter("timestamp", System.currentTimeMillis() / 1000 + "")
                    .addEncodedQueryParameter("ver_no", AppUtils.getPackageInfo(AppContext.getContext()).versionName)
                    .addEncodedQueryParameter("device", "pad");

            return request.newBuilder()
                    .method(request.method(), request.body())
                    .url(builder.build())
                    .build();
        }else{
            FormBody.Builder newFormBody = new FormBody.Builder();
            FormBody oldFormBody = (FormBody) request.body();
            for (int i = 0;i<oldFormBody.size();i++){
                newFormBody.addEncoded(oldFormBody.encodedName(i), oldFormBody.encodedValue(i));
            }
            newFormBody.add("nonce_str", getRandomStr32());
            newFormBody.add("timestamp",System.currentTimeMillis() / 1000 + "");
            newFormBody.add("ver_no",AppUtils.getPackageInfo(AppContext.getContext()).versionName);
            return request.newBuilder().method(request.method(), newFormBody.build()).build();
        }
    }


    /**
     * 获取32位随机字符串
     */
    public static String getRandomStr32() {
        String base = "0123456789abcdefghijklmnopqlstuvwxyz";
        int baseCount = base.length();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 32; i++) {
            int pos = (int) (Math.random() * baseCount);
            sb.append(base.substring(pos, pos+1));
        }
        return sb.toString();
    }

    /**
     * 将签名添加到请求后返回新的请求
     * @param signature 签名
     * @param request 请求
     */
    private Request writeSignature(String signature, Request request) {
        if(signature == null) return request;

        if("GET".equals(request.method())){
            HttpUrl.Builder builder = request.url()
                    .newBuilder()
                    .addEncodedQueryParameter("sign", signature);

            return request.newBuilder()
                    .method(request.method(), request.body())
                    .url(builder.build())
                    .build();
        }else{
            FormBody.Builder newFormBody = new FormBody.Builder();
            FormBody oidFormBody = (FormBody) request.body();
            for (int i = 0;i<oidFormBody.size();i++){
                newFormBody.addEncoded(oidFormBody.encodedName(i),oidFormBody.encodedValue(i));
            }
            newFormBody.add("sign", signature);
            return request.newBuilder().method(request.method(), newFormBody.build()).build();
        }
    }


    /**
     * 从字符串中解析出参数对
     * @param form 字符串
     * @return 解析后的参数对
     */
    private Map<String, String> decodeForm(String form) {
        Map<String, String> params = new HashMap<>();
        if (TextUtils.isEmpty(form)) {
            return params;
        }
        for (String nvp : form.split("\\&")) {
            int equals = nvp.indexOf('=');
            String name;
            String value;
            if (equals < 0) {
                name = nvp;
                value = null;
            } else {
                name = nvp.substring(0, equals);
                value = nvp.substring(equals + 1);
            }
            params.put(name, value);
        }
        return params;
    }

    /**
     * 从输入流中解析出参数对
     * @param inputStream 输入流
     * @return 解析后的参数对
     * @throws IOException
     */
    private Map<String, String> decodeForm(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line = reader.readLine();
        while (line != null) {
            sb.append(line);
            line = reader.readLine();
        }
        return decodeForm(sb.toString());
    }
}
