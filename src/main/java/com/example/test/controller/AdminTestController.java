package com.example.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/admin")
public class AdminTestController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/home")
    @ResponseBody
    public String productInfo() {
        return " admin home page ";
    }
    @GetMapping("/publish")
    @ResponseStatus(HttpStatus.OK)
    public void publish() {
        System.out.println("执行发布");
        redisTemplate.convertAndSend("channel1", "Hello, I'm Tom!");
    }

    @GetMapping("/push")
    @ResponseStatus(HttpStatus.OK)
    public void publish2() {
        System.out.println("执行发布");
        redisTemplate.convertAndSend("channel2", "Hello, I'm Nick!");
    }
}
