package com.xxzuo.wecombot;

import com.alibaba.fastjson2.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class weComBotUtils {

    private static final Logger log = LoggerFactory.getLogger(weComBot.class);

    private weComBotUtils() {

    }

    public static void sendMessageByWeComBotMarkdown(String webHook, String msg) {
        log.info("hook start send msg to weComBot");
        HttpResponse httpResponse = null;
        String response = "";
        try (CloseableHttpClient httpClient = HttpClients.createDefault()){
            Map<String, String> msgParams = new HashMap<>();
            msgParams.put("content", msg);
            Map<String, Object> webHookParams = new HashMap<String, Object>();
            webHookParams.put("msgtype", "markdown");
            webHookParams.put("markdown", msgParams);
            HttpPost httpPost = new HttpPost(webHook);
            httpPost.setHeader("Content-Type", "application/json; charset=UTF-8");
            StringEntity webHookEntity = new StringEntity(JSONObject.toJSONString(webHookParams), ContentType.APPLICATION_JSON);
            webHookEntity.setContentType("application/json");
            httpPost.setEntity(webHookEntity);
            httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                response = EntityUtils.toString(httpEntity, "utf-8");
            }
        } catch (IOException e) {
            log.error("weComBot send error! webhook: {}; send msg:{}; response: {};", webHook, msg, response, e);
        }
    }




}
