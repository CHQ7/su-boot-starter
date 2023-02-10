package com.yunqi.starter.rabbit.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * RabbitMQ
 * Created by @author CHQ on 2022/9/12
 */
@Configuration
public class RabbitAutoConfiguration {

    /**
     * 创建队列
     * @return 构造一个新队列
     */
    @Bean
    public Queue queue() {
        // 是否持久化
        boolean durable = true;
        // 仅创建者可以使用的私有队列，断开后自动删除
        boolean exclusive = false;
        // 当所有消费客户端连接端库后，是否自动删除队列
        boolean autoDelete = false;
        //一般设置一下队列的持久化就好,其余两个就是默认false
        return new Queue(RabbitConfig.QUEUE_NAME, durable, exclusive, autoDelete);
    }

    /**
     * 创建Direct交换器
     * @return  构造一个新交换器
     */
    @Bean
    public DirectExchange exchange() {
        boolean durable = true;
        boolean autoDelete = false;
        return new DirectExchange(RabbitConfig.EXCHANGE,durable,autoDelete);
    }


    /**
     * 绑定交换器和队列通过路由键
     * @return  绑定交换器和队列
     */
    @Bean
    public Binding bindingDirect() {
        return BindingBuilder.bind(queue()).to(exchange()).with(RabbitConfig.ROUTEKEY);
    }

}
