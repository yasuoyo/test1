package com.example.test.config;

import com.example.test.service.Impl.MySubcribe;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * @program: diditech-wechat-new
 * @description:
 * @author: Zqm
 * @create: 2021-08-19 14:11
 **/
@Configuration
public class MyRedisMessageListenerContainer {

    private RedisConnectionFactory factory;

    @Bean
    MessageListenerAdapter messageListener() {
        return new MessageListenerAdapter(new MySubcribe());
    }

    @Bean
    public RedisMessageListenerContainer redisContainer(RedisConnectionFactory factory) {
        this.factory = factory;
        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(factory);
        container.addMessageListener(messageListener(), new ChannelTopic("channel1"));
        container.addMessageListener(messageListener(), new ChannelTopic("channel2"));
        return container;
    }

}
