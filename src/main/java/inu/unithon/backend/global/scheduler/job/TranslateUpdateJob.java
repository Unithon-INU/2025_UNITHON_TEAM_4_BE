package inu.unithon.backend.global.scheduler.job;

import inu.unithon.backend.global.exception.CommonErrorCode;
import inu.unithon.backend.global.exception.CustomException;
import org.quartz.*;
import lombok.extern.slf4j.Slf4j;
import inu.unithon.backend.domain.translate.service.TranslationService;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class TranslateUpdateJob implements Job {
    private final TranslationService translationService;
    public TranslateUpdateJob(TranslationService translationService) {
        this.translationService = translationService;
    }


    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            translationService.translateAllFestivals();
        } catch (Exception e) {
            JobExecutionException error = new JobExecutionException(e);
            log.error("Translate error: {}", e.getMessage(), e);
            error.setRefireImmediately(true);
            throw new CustomException(CommonErrorCode.LIST_UPDATE_FAILED);
        }


    }
}
