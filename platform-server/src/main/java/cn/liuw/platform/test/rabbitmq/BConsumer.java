package cn.liuw.platform.test.rabbitmq;

import cn.liuw.platform.common.util.RabbitMQUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 消费者
 *
 * @author liuw
 * @date 2020/11/2
 */
public class BConsumer {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {
        //1、获取连接
        Connection connection = RabbitMQUtil.getConnection("localhost", 5672, "/", "guest", "guest");
        //2、声明通道
        Channel channel = connection.createChannel();
        //3、声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //5、消费队列
        /*
            true:表示自动确认，只要消息从队列中获取，无论消费者获取到消息后是否成功消费，都会认为消息已经成功消费
            false:表示手动确认，消费者获取消息后，服务器会将该消息标记为不可用状态，等待消费者的反馈，
                   如果消费者一直没有反馈，那么该消息将一直处于不可用状态，并且服务器会认为该消费者已经挂掉，不会再给其
                   发送消息，直到该消费者反馈。
         */

        channel.basicConsume(QUEUE_NAME, false, new BConsumerImpl(channel));

    }

}
