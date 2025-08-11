package inu.unithon.backend.global.rabbitMq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitMqConsumer {

    @RabbitListener(queues = "#{@rabbitMqConfig.detailQueueName}")
    public void detailReceive(String contentId){
        log.info("전달받은 contentId: {}", contentId);
    }

    @RabbitListener(queues = "#{@rabbitMqConfig.translateQueueName}")
    public void translateReceive(String contentId){
        log.info("전달받은 contentId: {}", contentId);
    }

    @RabbitListener(queues = "#{@rabbitMqConfig.detailTranslateQueueName}")
    public void detailTranslateReceive(String contentId){
        log.info("전달받은 contentId: {}", contentId);
    }

}
