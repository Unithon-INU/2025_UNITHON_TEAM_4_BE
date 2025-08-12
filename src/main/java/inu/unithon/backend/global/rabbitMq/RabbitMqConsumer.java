package inu.unithon.backend.global.rabbitMq;

import inu.unithon.backend.domain.festival.update.FestivalDetailSave;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitMqConsumer {

    private final FestivalDetailSave festivalDetailSave;

    @RabbitListener(queues = "#{@rabbitMqConfig.detailQueueName}")
    public void detailReceive(String contentId){
        log.info("전달받은 contentId: {}", contentId);
        try{
            festivalDetailSave.saveDetail(contentId);
        } catch (Exception e) {
            log.error("축제 상세 정보 저장 실패: {}", e.getMessage());
            // 예외 처리 로직 추가 가능
        }
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
