package com.zhou.provider.rabbimq.controller;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
public class RabbitMq {

    /**
     * 简单模式（Simple）:消息的生产者
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("/providerMessage")
    public String providerMessage() throws IOException, InterruptedException {
        //1连接
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
        //4,发送消息，routingKey必须与queue一致

        //开启一个线程发送消息
        ((Runnable) () -> {

            for (int i = 0; i < 100000; i++) {

                String msg = "msg1" + i;
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    channel.basicPublish("", "testQueue", null, msg.getBytes());
                    System.out.println("发送消息为："+msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).run();



        //关闭
        channel.close();
        connection.close();


       /* 加while()查看channel,连接
        去掉while，客户端就断开了，后台不显示channel,连接*/
       /*  while(true){

        }*/
       return "发送消息成功请看控制台";

    }
}
