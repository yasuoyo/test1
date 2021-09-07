package com.example.test.service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;


/**
 * @program: diditech-wechat-new
 * @description:
 * @author: Zqm
 * @create: 2021-08-19 14:13
 **/
@Service
@Slf4j
public class MySubcribe implements MessageListener {

    @Override
    public void onMessage(Message message, byte[] pattern) {
        System.out.println("接收数据:"+message.toString());
        System.out.println("订阅频道:"+new String(message.getChannel()));
    }

}
