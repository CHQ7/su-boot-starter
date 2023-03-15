package com.yunqi.starter.rabbit.configuration;

import com.yunqi.starter.common.json.Json;
import com.yunqi.starter.rabbit.publisher.RabbitSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * RabbitMQ
 * Created by @author CHQ on 2022/9/12
 */
@Slf4j
@Configuration
@ConditionalOnClass(ConnectionFactory.class)
@EnableConfigurationProperties(RabbitProperties.class)
public class RabbitAutoConfiguration {

    private final RabbitProperties properties;


    public RabbitAutoConfiguration(RabbitProperties properties) {
        this.properties = properties;
    }

    /**
     * 配置RabbitMQ连接信息
     * @return  factory
     */
    @Bean
    public ConnectionFactory connectionFactory() {
        if(properties.getIsLog()){
            log.info("自动装配 -> Rabbit组件");
            log.info("打印 -> spring.rabbitmq 配置:\n{}", Json.toJson(properties));
        }

        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost(properties.getHost());
        factory.setPort(properties.getPort());
        factory.setUsername(properties.getUsername());
        factory.setPassword(properties.getPassword());
        return factory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        return rabbitTemplate;
    }

    @Bean
    public RabbitSender publisher(RabbitTemplate rabbitTemplate){
        return new RabbitSender(rabbitTemplate);
    }

    /**
     * 定义队列
     * @return 构造一个新队列
     */
    @Bean
    public Queue queue() {
        // durable: 是否持久化
        // exclusive: 仅创建者可以使用的私有队列，断开后自动删除
        // autoDelete: 当所有消费客户端连接端库后，是否自动删除队列
        return new Queue(RabbitConfig.QUEUE_NAME, true, false, false);
    }

    /**
     * 定义发布/订阅模式下的交换机
     * @return  构造一个新交换器
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(RabbitConfig.EXCHANGE, true, false);
    }


    /**
     * 将队列绑定到交换机上
     * @return  绑定交换器和队列
     */
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(fanoutExchange());
    }

}
