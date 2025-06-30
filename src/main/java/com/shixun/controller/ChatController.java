package com.shixun.controller;

import com.shixun.pojo.DSMessage;
import com.shixun.pojo.Result;
import com.shixun.service.GeoService;
import lombok.RequiredArgsConstructor;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/ai")
public class ChatController
{
    private final ChatClient chatClient;

    @Autowired
    private GeoService geoService;
    //deepseek服务
    @PostMapping(path = "/chat", produces = "text/html;charset=UTF-8")
    public Flux<String> chat(@RequestBody DSMessage  message)
    {
        if(message.getContent().startsWith("/")) {
            String msg = message.getContent().substring(1);
            Result result = geoService.getDailyWeather(geoService.getlocationId(msg));

            String information = "";
            if (result != null && result.getCode() == 1 && result.getData() != null) {
                // 获取data Map
                Map<String, Object> data = (Map<String, Object>) result.getData();

                // 检查data中是否有now字段
                if (data.containsKey("now")) {
                    Map<String, Object> now = (Map<String, Object>) data.get("now");

                    // 拼接now中的键值对
                    StringBuilder sb = new StringBuilder();
                    for (Map.Entry<String, Object> entry : now.entrySet()) {
                        sb.append("\"").append(entry.getKey()).append("\": \"")
                                .append(entry.getValue()).append("\",\n");
                    }

                    // 移除最后的逗号和换行
                    if (sb.length() > 0) {
                        sb.setLength(sb.length() - 2);
                    }
                    information = sb.toString();
                }
            }

            return chatClient.prompt()
                    .user(information)
                    .stream()
                    .content();
        } else {
            return chatClient.prompt().user(message.getContent()).stream().content();
        }
    }
}
