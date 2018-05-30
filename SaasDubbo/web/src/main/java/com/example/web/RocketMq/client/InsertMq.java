package com.example.web.RocketMq.client;
import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class InsertMq {
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

    public void defaultMQProducer(JSONObject para) {
        DefaultMQProducer producer = new DefaultMQProducer(producerGroup);
        producer.setNamesrvAddr(namesrvAddr);
        try {
            producer.start();
            JSONObject jsonObject=JSONObject.fromObject(para);
            Message message = new Message("TopicTest", "pushing",
                    jsonObject.toString().getBytes());
           /* StopWatch stopWatch=new StopWatch();
            stopWatch.start();

            for(int i=0;i<10;i++){*/
            SendResult sendResult= null;
            try {
                sendResult = producer.send(message);
            } catch (RemotingException e) {
                e.printStackTrace();
            } catch (MQBrokerException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("发送响应MsgiId"+sendResult.getMsgId()+"，发送状态"+ sendResult.getSendStatus()+" ,,,,"+sendResult.toString());
//            }
//            stopWatch.stop();
            /*System.out.println("----------------发送一万条消息耗时：" + stopWatch.getTotalTimeMillis());*/
        } catch (MQClientException e) {
            e.printStackTrace();
        } finally {
            producer.shutdown();
        }
    }
}
