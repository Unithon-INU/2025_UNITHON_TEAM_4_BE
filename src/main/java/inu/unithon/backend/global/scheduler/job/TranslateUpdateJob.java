package inu.unithon.backend.global.scheduler.job;

import inu.unithon.backend.global.exception.CommonErrorCode;
import inu.unithon.backend.global.exception.CustomException;
import org.quartz.*;
import lombok.extern.slf4j.Slf4j;
import inu.unithon.backend.domain.translate.service.TranslationService;
import org.springframework.stereotype.Component;
import inu.unithon.backend.global.rabbitMq.RabbitMqProducer;


@Slf4j
@Component
public class TranslateUpdateJob implements Job {
    private final TranslationService translationService;
    private final RabbitMqProducer rabbitMqProducer;
    public TranslateUpdateJob(TranslationService translationService , RabbitMqProducer rabbitMqProducer) {
        this.translationService = translationService;
        this.rabbitMqProducer = rabbitMqProducer;

    }



    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            rabbitMqProducer.translateSend();
        } catch (Exception e) {
            JobExecutionException error = new JobExecutionException(e);
            log.error("Translate error: {}", e.getMessage(), e);
            error.setRefireImmediately(true);
            throw new CustomException(CommonErrorCode.LIST_UPDATE_FAILED);
        }


    }
}
