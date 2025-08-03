package inu.unithon.backend.global.rabbitMq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitMqProducer {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitMqConfig rabbitMqConfig;

    public void DetailSend(String contentId){
        rabbitTemplate.convertAndSend(
                rabbitMqConfig.getExchangeName(),
                rabbitMqConfig.getDetailRoutingKey(),
                contentId
        );
    }

    public void TranslateSend(String contentId){
        rabbitTemplate.convertAndSend(
                rabbitMqConfig.getTranslateQueueName(),
                rabbitMqConfig.getTranslateRoutingKey(),
                contentId
        );
    }

    public void DetailTranslateSend(String contentId){
        rabbitTemplate.convertAndSend(
                rabbitMqConfig.getDetailTranslateQueueName(),
                rabbitMqConfig.getDetailTranslateRoutingKey(),
                contentId
        );
    }

}
