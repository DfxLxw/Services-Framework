package com.example.demo.config;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.protocol.heartbeat.MessageModel;
import com.example.demo.RocketMq.server.reciveMq;
import com.example.demo.RocketMq.server.testMQ;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
@EnableConfigurationProperties(RocketMqProties.class)
public class RocketMqConsumer {
      //这里也做一下说明
     //这里其实也是配bean也是把一切写活
    //当然这里我们把外部需要的全部按Bean的形式配置进去即可，这种方法值得借鉴，同时保证了 启动就是监听
    @Value("${rocket.mq.topic}")
    private  String topic;
    @Resource
    private RocketMqProties rocketMQProperties;
    @Resource
    private testMQ ReciveMq;

    @Bean(name = "ReciveMqdefaultConsumer",initMethod = "start",destroyMethod = "shutdown")
    public <T extends MessageListenerConcurrently> DefaultMQPushConsumer ReciveMqdefaultConsumer(@Value("${rocket.mq.tag}")String tag) throws MQClientException {
        return registeConsumer(topic,tag, (T) ReciveMq);

    }

    private <T extends MessageListenerConcurrently> DefaultMQPushConsumer registeConsumer( final String topic,final String tag, final T reciveMq) throws MQClientException {
         DefaultMQPushConsumer defaultMQPushConsumer=defaultInstance();
          defaultMQPushConsumer.subscribe(topic ,tag);
          defaultMQPushConsumer.registerMessageListener(reciveMq);
          return defaultMQPushConsumer;
    }

    private DefaultMQPushConsumer defaultInstance() {
        DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer();
        defaultMQPushConsumer.setConsumerGroup(rocketMQProperties.getConsumerGroup());
        defaultMQPushConsumer.setNamesrvAddr(rocketMQProperties.getNamesrvAddr());
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        defaultMQPushConsumer.setConsumeMessageBatchMaxSize(1024);
        defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
         return  defaultMQPushConsumer;
    }
}
