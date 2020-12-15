package cn.liuw.platform.common.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author liuw
 * @date 2020/11/2
 */
public class RabbitMQUtil {

    /**
     * 
     * @param host      IP
     * @param port      端口号
     * @param vHost     虚拟主机
     * @param userName  用户名
     * @param passWord  密码
     * @return
     * @throws Exception
     */
    public static Connection getConnection(String host,int port,String vHost,String userName,String passWord) throws Exception{
        //1、定义连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //2、设置服务器地址
        factory.setHost(host);
        //3、设置端口
        factory.setPort(port);
        //4、设置虚拟主机、用户名、密码
        factory.setVirtualHost(vHost);
        factory.setUsername(userName);
        factory.setPassword(passWord);
        // 
        //5、通过连接工厂获取连接
        Connection connection = factory.newConnection();
        return connection;
    }
}
