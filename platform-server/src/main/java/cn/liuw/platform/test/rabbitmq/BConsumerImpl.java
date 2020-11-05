package cn.liuw.platform.test.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Random;

/**
 * @author liuw
 * @date 2020/11/2
 */
@Slf4j
public class BConsumerImpl extends DefaultConsumer {
    
    private Channel channel;

    public BConsumerImpl(Channel channel) {
        super(channel);
        this.channel = channel;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
        String message = null;

        try {
            log.info("开始消费：{}", envelope.getDeliveryTag());
            message = new String(body, "UTF-8");
            Integer I = new Random().nextInt(10);
            if(I > 4) {
                int j = 1 /0;
            }
            Thread.sleep(1000);
            log.info("消费者接收到的消息是：{}", message);
            channel.basicAck(envelope.getDeliveryTag(), false);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }


    }
}
