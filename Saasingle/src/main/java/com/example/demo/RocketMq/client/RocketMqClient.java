package com.example.demo.RocketMq.client;
import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import javax.annotation.PostConstruct;
@Component
public class RocketMqClient {
    /*
    * 生产者组名
    */
    @Value("${rocket.mq.producer-group}")
    private String producerGroup;
   /*
   *NameServer地址
    */
   @Value("${rocket.mq.namesrv-addr}")
    private String namesrvAddr;

   @PostConstruct
    public void defaultMQProducer() throws RemotingException, InterruptedException, MQBrokerException {
       DefaultMQProducer producer = new DefaultMQProducer(producerGroup);
       producer.setNamesrvAddr(namesrvAddr);
       try {
           producer.start();
           Message message=new Message("TopicTest","push",
                   "发送消息---what are you saying--".getBytes());
           StopWatch stopWatch=new StopWatch();
           stopWatch.start();

           for(int i=0;i<10;i++){
               SendResult sendResult=producer.send(message);
               System.out.println("发送响应MsgiId"+sendResult.getMsgId()+"，发送状态"+ sendResult.getSendStatus());
           }
           stopWatch.stop();
           System.out.println("----------------发送一万条消息耗时：" + stopWatch.getTotalTimeMillis());
       } catch (MQClientException e) {
           e.printStackTrace();
       }finally {
           producer.shutdown();
       }

   }
}
