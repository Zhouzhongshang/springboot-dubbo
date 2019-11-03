package com.zhou.consumer.controller.Rabbitmq.Controller;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class RabbitmQ {

    /**
     * 简单模式的消费者
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/consumerMessage")
    public void  consumerMessage() throws Exception {
        //1,建立连接
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setVirtualHost("/zzs");
        Connection connection=factory.newConnection();

        //2,建立通道
        Channel channel=connection.createChannel();
        //3,定义队列
        //durable true 持久化,重启服务器后，数据还有
        //exclusive true,只能通过当前连接消费 false
        //autoDelete true 队列中消息处理完后，自动删除队列
        //arguments 参数
        channel.queueDeclare("testQueue", true, false, false, null);

        //4,创建消费者
        QueueingConsumer consumer=new QueueingConsumer(channel);
        //autoAck:自动回复消息
        channel.basicConsume("testQueue", true, consumer);

        //5,取消息
            while(true)
            {
            QueueingConsumer.Delivery delivery=consumer.nextDelivery();
            byte[] data=delivery.getBody();
            String mString=new String(data);
            System.out.println("消费者取到："+mString);
        }
    }
}
