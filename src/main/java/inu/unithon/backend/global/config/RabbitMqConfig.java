package inu.unithon.backend.global.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMqConfig {
    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.queues.detail.name}")
    private String detailQueueName;

    @Value("${rabbitmq.queues.detail.routing-key}")
    private String detailRoutingKey;

    @Value("${rabbitmq.queues.translate.name}")
    private String translateQueueName;

    @Value("${rabbitmq.queues.translate.routing-key}")
    private String translateRoutingKey;

    @Value("${rabbitmq.queues.detail-translate.name}")
    private String detailTranslateQueueName;

    @Value("${rabbitmq.queues.detail-translate.routing-key}")
    private String detailTranslateRoutingKey;

    @Bean
    public TopicExchange festivalExchange() {
        return new TopicExchange(exchangeName, true, false);
    }
    // DircectExchange를 안쓰고 TopciExchange를 사용한 이유는 처음에 detail api 호출 과 list translate 를 동시에 보내고
    // detail api 호출이 끝나면 detail translate를 실행하기 위해 topic exchange 를 사용했습니당.

    @Bean
    public Queue festivalDetailQueue() {
        return new Queue(detailQueueName, true);
    }

    @Bean
    public Queue festivalTranslateQueue() {
        return new Queue(translateQueueName, true);
    }

    @Bean
    public Queue festivalDetailTranslateQueue() {
        return new Queue(detailTranslateQueueName, true);
    }

    @Bean
    public Binding bindingDetail() {
        return BindingBuilder.bind(festivalDetailQueue()).to(festivalExchange()).with(detailRoutingKey);
    }

    @Bean
    public Binding bindingTranslate() {
        return BindingBuilder.bind(festivalTranslateQueue()).to(festivalExchange()).with(translateRoutingKey);
    }

    @Bean
    public Binding bindingDetailTranslate() {
        return BindingBuilder.bind(festivalDetailTranslateQueue()).to(festivalExchange()).with(detailTranslateRoutingKey);
    }



}
