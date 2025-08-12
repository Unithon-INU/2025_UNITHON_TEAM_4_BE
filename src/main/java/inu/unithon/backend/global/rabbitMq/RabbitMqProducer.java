package inu.unithon.backend.global.rabbitMq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitMqProducer {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitMqConfig rabbitMqConfig;

    public void detailSend(String contentId){
        rabbitTemplate.convertAndSend(
                rabbitMqConfig.getExchangeName(),
                rabbitMqConfig.getDetailRoutingKey(),
                contentId
        );
    }

    public void translateSend(String contentId){
        rabbitTemplate.convertAndSend(
                rabbitMqConfig.getExchangeName(),
                rabbitMqConfig.getTranslateRoutingKey(),
                contentId
        );
    }

    public void detailTranslateSend(String contentId){
        rabbitTemplate.convertAndSend(
                rabbitMqConfig.getExchangeName(),
                rabbitMqConfig.getDetailTranslateRoutingKey(),
                contentId
        );
    }

}
