package inu.unithon.backend.global.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    public static final String FESTIVAl_DETAIL_QUEUE = "festival.detail.queue";
    public static final String FESTIVAL_TRANSLATE_QUEUE = "festival.translate.queue";
    public static final String FESTIVAL_DETAIL_TRANSLATE_QUEUE = "festival.detail.translate.queue";
    public static final String FESTIVAL_EXCHANGE = "festival.exchange";
    public static final String FESTIVAL_DETAIL_ROUTING_KEY = "festival.detail";
    public static final String FESTIVAL_TRANSLATE_ROUTING_KEY = "festival.translate";
    public static final String FESTIVAL_DETAIL_TRANSLATE_ROUTING_KEY = "festival.detail.translate";

    @Bean
    public TopicExchange festivalExchange() {
        return new TopicExchange(FESTIVAL_EXCHANGE, true, false);
    }
    // DircectExchange를 안쓰고 TopciExchange를 사용한 이유는 처음에 detail api 호출 과 list translate 를 동시에 보내고
    // detail api 호출이 끝나면 detail translate를 실행하기 위해 topic exchange 를 사용했습니당.

    @Bean
    public Queue festivalDetailQueue() {
        return new Queue(FESTIVAl_DETAIL_QUEUE, true);
    }

    @Bean
    public Queue festivalTranslateQueue() {
        return new Queue(FESTIVAL_TRANSLATE_QUEUE, true);
    }

    @Bean
    public Queue festivalDetailTranslateQueue() {
        return new Queue(FESTIVAL_DETAIL_TRANSLATE_QUEUE, true);
    }

    @Bean
    public Binding bindingDetail() {
        return BindingBuilder.bind(festivalDetailQueue()).to(festivalExchange()).with(FESTIVAL_DETAIL_ROUTING_KEY);
    }

    @Bean
    public Binding bindingTranslate() {
        return BindingBuilder.bind(festivalTranslateQueue()).to(festivalExchange()).with(FESTIVAL_TRANSLATE_ROUTING_KEY);
    }

    @Bean
    public Binding bindingDetailTranslate() {
        return BindingBuilder.bind(festivalDetailTranslateQueue()).to(festivalExchange()).with(FESTIVAL_DETAIL_TRANSLATE_ROUTING_KEY);
    }



}
