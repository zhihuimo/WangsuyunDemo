package com.wangsu;

import com.alibaba.fastjson.JSONObject;
import com.cnc.cloudv.util.auth.ApiAuthUtil;
import com.cnc.cloudv.util.httpclient.CloudvHttpResponse;
import sun.misc.BASE64Encoder;

import java.util.*;


public class TestDemo {
    //通过秘钥管理获取的秘租
    private final static String ACCESS_KEY = "ffffddca016c10005ee9333b00000000";
    private final static String SECRET_KEY = "7f17ac7bd3733f04ff5a8b1471f91c9e";
    //这里固定为云视频API域名
    private final static String API_HOST = "api.cloudv.haplat.net";

    public static void main(String[] args)  throws Exception {
        Map<String, String> customHeaderMap = new HashMap<String, String>(2);
        //可以支持自定义头部
        customHeaderMap.put("from", "test-authentification-sdk");
        //请求接口的URI
        //  String requestURI = "/vod/videoManage/getVideoList";
        String requestURI = "/vod/videoManage/pullVideo";

        String fileName="脱贫攻坚系列之八：中国脱贫经验对世界的影响.mp4";
        String fetchUrl = "http://www.giged.cn:8081/dislesson/web/resource/course/202003/9635586a-74ba-44ad-b846-6dcdf6431dd0/TPGJXLZBGTPJYDSJDYX1583400460470.mp4";
        //application/json方式的post请求调用
        String a1 = "[{"+"\"fileName\":"+"\""+fileName+"\""+","+"\"fetchUrl\":"+"\""+fetchUrl+"\"}]";
        String a3 = new BASE64Encoder().encode(a1.getBytes()).replaceAll("\n","").replaceAll("\r","");
        String body = "{"+"fileList:"+"\""+a3+"\"}";

        CloudvHttpResponse cloudvHttpResponse = ApiAuthUtil.callPostJsonBodyRequest(API_HOST, ACCESS_KEY, SECRET_KEY, requestURI, customHeaderMap, body);

        log(cloudvHttpResponse);

        System.out.print("调用完毕");

    }

    public static void log(CloudvHttpResponse cloudvHttpResponse) throws Exception {
        //每次请求统一会带上一个X-WS-RequestId用来方便排查问题
        System.out.println("返回结果是：" + cloudvHttpResponse + " , requestId = " + cloudvHttpResponse.getHeaders().get("X-WS-RequestId")) ;
        System.out.println("----------------------------------------------------------------");
        System.out.println(cloudvHttpResponse.getBody());

    }

}