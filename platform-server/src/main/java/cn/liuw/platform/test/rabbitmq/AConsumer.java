package cn.liuw.platform.test.rabbitmq;

import cn.liuw.platform.common.constant.QueueConstant;
import cn.liuw.platform.common.util.RabbitMQUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;
import lombok.extern.slf4j.Slf4j;

/**
 * 消费者
 *
 * @author liuw
 * @date 2020/11/2
 */
@Slf4j
public class AConsumer {

    public static void main(String[] args) throws Exception {
        //1、获取连接
        Connection connection = RabbitMQUtil.getConnection("localhost", 5672, "/", "guest", "guest");
        //2、声明通道
        Channel channel = connection.createChannel();
        //3、声明队列
        channel.queueDeclare(QueueConstant.QUEUE_NAME_COMMON, false, false, false, null);

        DeliverCallback deliverCallback = (consumerTag, deliver) -> {
            try {
                String message = new String(deliver.getBody(), "UTF-8");
                log.info(" start deliverCallback is : '{}'", message);
                Thread.sleep(1500);
                log.info(" end deliverCallback is : '{}'", message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
            }

        };

        channel.basicConsume(QueueConstant.QUEUE_NAME_COMMON, true, deliverCallback, consumerTag -> {
            log.info("basic Consume...");
        });
    }

}
