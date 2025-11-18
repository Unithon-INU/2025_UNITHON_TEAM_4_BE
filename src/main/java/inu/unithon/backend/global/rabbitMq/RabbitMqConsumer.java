package inu.unithon.backend.global.rabbitMq;

import inu.unithon.backend.domain.festival.update.FestivalDetailSave;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import inu.unithon.backend.global.rabbitMq.RabbitMqListnerConfig;
import inu.unithon.backend.domain.translate.service.TranslationService;


@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitMqConsumer {

    private final FestivalDetailSave festivalDetailSave;
    private final TranslationService translationService;

    @RabbitListener(queues = "#{@rabbitMqConfig.detailQueueName}",
    containerFactory = "ListenerFactory")
    public void detailReceive(String contentId){
        log.info("전달받은 contentId: {}", contentId);
        try{
            festivalDetailSave.saveDetail(contentId);
        } catch (Exception e) {
            log.error("축제 상세 정보 저장 실패: {}", e.getMessage());
            // 예외 처리 로직 추가 가능
        }
    }

    @RabbitListener(queues = "#{@rabbitMqConfig.translateQueueName}", containerFactory = "ListenerFactory")
    public void translateReceive(String message){


        log.info("#@$#@$@#!@#!전달받은 메시지: {}", message);
        try{
            translationService.translateAllFestivals();
        } catch (Exception e) {
            log.error("translate fail: {}", e.getMessage());
        }
    }

    @RabbitListener(queues = "#{@rabbitMqConfig.detailTranslateQueueName}")
    public void detailTranslateReceive(String contentId){
        log.info("전달받은 contentId: {}", contentId);
    }

}
