package com.example.demo.RocketMq.server;
import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.example.demo.domain.User;
import com.example.demo.service.impl.serviceImpl;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
public class reciveMq  {
    @Autowired
    serviceImpl service;
    /*
  消费者
   * */
    @Value("${rocket.mq.consumer-group}")
    private String consumerGroup;
    /*
     * NameAddr
     * */
    @Value("${rocket.mq.namesrv-addr}")
    private String namesrvAddr;
    public  void defaultMQPushConsumer() throws MQClientException {
        //消费者的组名
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroup);
        //指定NameServer地址，多个地址以 ; 隔开
        consumer.setNamesrvAddr(namesrvAddr);
            //订阅PushTopic下Tag为push的消息
            consumer.subscribe("TopicTest", "pushing");
            //设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
            //如果非第一次启动，那么按照上次消费的位置继续消费
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext Context) {
                    for (MessageExt messageExt : msgs) {
                        try {
                            String jsonStr = new String(messageExt.getBody(), "utf-8");
                            System.out.println(jsonStr);
                            User user = (User) JSONObject.toBean(JSONObject.fromObject(jsonStr), User.class);
                            String username = user.getUsername();
                            String password = user.getPassword();
                            User user1 = new User();
                            user1.setUsername(username);
                            user1.setPassword(password);
                            service.insert(user1);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }

                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
            consumer.start();
        }
    }