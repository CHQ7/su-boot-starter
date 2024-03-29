package com.yunqi.starter.rabbit.publisher;

import com.yunqi.starter.common.lang.util.NutMap;
import com.yunqi.starter.rabbit.configuration.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * Created by @author CHQ on 2023/3/15
 */
@Slf4j
public class RabbitSender {

    private final RabbitTemplate rabbitTemplate;


    public RabbitSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     *  发送消息给订阅队列
     * @param message 消息
     */
    public void send(NutMap message) {
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, "", message);
    }


    /**
     *
     * @param exchange  交换器名称
     * @param message   消息
     */
    public void send(String exchange, NutMap message) {
        rabbitTemplate.convertAndSend(exchange, "", message);
    }

}
