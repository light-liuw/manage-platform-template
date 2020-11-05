package cn.liuw.platform.test.rabbitmq;

import cn.liuw.platform.common.constant.QueueConstant;
import cn.liuw.platform.common.util.RabbitMQUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import lombok.extern.slf4j.Slf4j;

/**
 * 生产者
 *
 * @author liuw
 * @date 2020/11/2
 */
@Slf4j
public class AProducer {

    public static void main(String[] args) {
        try {
            //1、获取连接
            Connection connection = RabbitMQUtil.getConnection("localhost", 5672, "/", "guest", "guest");
            //2、声明信道
            Channel channel = connection.createChannel();
            //3、声明(创建)队列
            channel.queueDeclare(QueueConstant.QUEUE_NAME_COMMON, false, false, false, null);
            //4、定义消息内容
            String message = "hello rabbitmq ";
            //5、发布消息
            channel.basicPublish("", QueueConstant.QUEUE_NAME_COMMON, null, message.getBytes());
            log.info("[x] Sent '{}'", message);
            //6、关闭通道
            channel.close();
            //7、关闭连接
            connection.close();

            log.info("publish success...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
