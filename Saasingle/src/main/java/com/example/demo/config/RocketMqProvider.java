package com.example.demo.config;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
@EnableConfigurationProperties(RocketMqProties.class)
public class RocketMqProvider {
    //此处做个说明给小伙伴们
    //如果我们不使用Bean的形式 其实就是相当于在主方法里配置地址，组等 当我们使用Bean的
    //形式我们会发现变量变得更加灵活 直接从配置文件中取出来 方法 例如 default.start()h和
    //default.shutdown()方法 直接写好 不需要每次的重复写 这就是Bean的神奇疗效。
    @Resource
    RocketMqProties rocketMqProties;

    @Bean(name = "defaultProducer" ,initMethod="start",destroyMethod = "shutdown")
    public DefaultMQProducer defaultMQProducer(){
        final DefaultMQProducer defaultMQProducer=new DefaultMQProducer();
        defaultMQProducer.setNamesrvAddr(rocketMqProties.getNamesrvAddr());
        defaultMQProducer.setProducerGroup(rocketMqProties.getProducerGroup());
        defaultMQProducer.setInstanceName(String.valueOf(System.currentTimeMillis()));
        return defaultMQProducer;
    }
}
