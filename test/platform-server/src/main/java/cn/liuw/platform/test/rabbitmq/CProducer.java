package cn.liuw.platform.test.rabbitmq;

import cn.liuw.platform.common.constant.QueueConstant;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * 生产者
 *
 * @author liuw
 * @date 2020/11/2
 */
@Slf4j
public class CProducer {

    public static void main(String[] args) throws Exception {
        // 1. 创建并配置connectionFactory
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        // 2. 获取连接
        Connection connection = connectionFactory.newConnection();
        // 3. 获取channel
        Channel channel = connection.createChannel();
        // 4. 声明队列
        channel.queueDeclare(QueueConstant.QUEUE_NAME_COMMON_C, false, false, false, null);
        // 持续产生消息
        while (true) {
            String message = "Producer" + LocalDateTime.now().toString();
            TimeUnit.SECONDS.sleep(1);
            channel.basicPublish("", QueueConstant.QUEUE_NAME_COMMON_C, null, message.getBytes("UTF-8"));
            log.info("send message: [{}]", message);
        }
    }
}
