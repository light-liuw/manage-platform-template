package cn.liuw.platform.test.rabbitmq;

import cn.liuw.platform.common.constant.QueueConstant;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 消费者
 *
 * @author liuw
 * @date 2020/11/2
 */
@Slf4j
public class CConsumer {

    public static void main(String[] args) throws Exception {
        // 1. 创建并配置connectionFactory
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        // 2. 获取连接
        Connection connection = connectionFactory.newConnection();
        //创建线程池
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4, 4, 4, TimeUnit.SECONDS, new LinkedBlockingDeque<>());
        threadPoolExecutor.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(4);
                consumeMsg(connection);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        threadPoolExecutor.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(4);
                consumeMsg(connection);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        threadPoolExecutor.shutdown();

    }

    public static void consumeMsg(Connection connection) throws IOException {
        //3. 通过connection获取channel
        Channel channel = connection.createChannel();
        //4. 通过channel声明queue
        channel.queueDeclare(QueueConstant.QUEUE_NAME_COMMON_C, false, false, false, null);
        channel.basicQos(1);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message;
            try {
                message = new String(delivery.getBody(), "UTF-8");
                System.out.println(Thread.currentThread().getName() + " Received '" + message + "'");
            } finally {
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                System.out.println(Thread.currentThread().getName() + " Received Done");
            }
        };
        channel.basicConsume(QueueConstant.QUEUE_NAME_COMMON_C, false, deliverCallback, consumerTag -> {
        });
    }

}
