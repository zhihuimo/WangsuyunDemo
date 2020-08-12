package com.wangsu;

import com.alibaba.fastjson.JSONObject;
import com.cnc.cloudv.util.auth.ApiAuthUtil;
import com.cnc.cloudv.util.httpclient.CloudvHttpResponse;
import sun.misc.BASE64Encoder;

import java.util.*;


public class TestAuthUtil {
    //通过秘钥管理获取的秘租
    private final static String ACCESS_KEY = "ffffddca016c10005ee9333b00000000";
    private final static String SECRET_KEY = "7f17ac7bd3733f04ff5a8b1471f91c9e";
    //这里固定为云视频API域名
    private final static String API_HOST = "api.cloudv.haplat.net";

    public  void act(String fileName,String fetchUrl) throws Exception {
        Map<String, String> customHeaderMap = new HashMap<String, String>(2);
        //可以支持自定义头部
        customHeaderMap.put("from", "test-authentification-sdk");
        //请求接口的URI
      //  String requestURI = "/vod/videoManage/getVideoList";
        String requestURI = "/vod/videoManage/pullVideo";


        //application/json方式的post请求调用
          String a1 = "[{"+"\"fileName\":"+"\""+fileName+"\""+","+"\"fetchUrl\":"+"\""+fetchUrl+"\"}]";
        String a3 = new BASE64Encoder().encode(a1.getBytes()).replaceAll("\n","").replaceAll("\r","");
        String body = "{"+"fileList:"+"\""+a3+"\"}";

        CloudvHttpResponse cloudvHttpResponse = ApiAuthUtil.callPostJsonBodyRequest(API_HOST, ACCESS_KEY, SECRET_KEY, requestURI, customHeaderMap, body);
        JSONObject jsonObject = JSONObject.parseObject(cloudvHttpResponse.getBody());

        JSONObject data1 = jsonObject.getJSONObject("data");
        String r = data1.getString("taskId");
        System.out.println(r);
        ResultsUtil resultsUtil = new ResultsUtil();
        System.out.println("线程在这个时间停止了"+new Date());
        try {
            Thread.sleep(1000*60);
        } catch (InterruptedException e) {

            e.printStackTrace();
        }
        System.out.println("线程在这个时间又开始了"+new Date());
        if (r!=null){
            resultsUtil.results(r);
        }


        log(cloudvHttpResponse);

      /*  //get请求的调用例子
        String canonicalQueryString = "videoName=" + java.net.URLEncoder.encode("测", "utf-8") + "&pageIndex=2&pageSize=5&format=json";
        cloudvHttpResponse = ApiAuthUtil.callGetRequest(API_HOST, ACCESS_KEY, SECRET_KEY, requestURI, customHeaderMap, canonicalQueryString);
        //form-Urlencoded方式的post请求调用示例
       // body = "videoName=" + java.net.URLEncoder.encode("测", "utf-8") + "&pageIndex=2&pageSize=5&format=json";
        cloudvHttpResponse = ApiAuthUtil.callPostFormUrlencodedBodyRequest(API_HOST, ACCESS_KEY, SECRET_KEY, requestURI, customHeaderMap, body);
        log(cloudvHttpResponse);


       /* //通过map方式传入URL参数的请求方式
        Map<String, String> map = new HashMap<String, String>();
        map.put("videoName", "测");
        map.put("pageIndex", "2");
        map.put("pageSize", "5");
        map.put("format", "json");
        cloudvHttpResponse = ApiAuthUtil.callPostFormUrlencodedBodyRequest(API_HOST, ACCESS_KEY, SECRET_KEY, requestURI, customHeaderMap, map);
        log(cloudvHttpResponse);*/

        System.out.print("调用完毕");

    }

    public static void log(CloudvHttpResponse cloudvHttpResponse) throws Exception {
        //每次请求统一会带上一个X-WS-RequestId用来方便排查问题
        System.out.println("返回结果是：" + cloudvHttpResponse + " , requestId = " + cloudvHttpResponse.getHeaders().get("X-WS-RequestId")) ;
        System.out.println("----------------------------------------------------------------");
        System.out.println(cloudvHttpResponse.getBody());

    }

}