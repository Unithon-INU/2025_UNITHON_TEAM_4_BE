package inu.unithon.backend.global.rabbitMq;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

@Configuration
@EnableRabbit
public class RabbitMqListnerConfig {

    @Bean
    public SimpleRabbitListenerContainerFactory ListenerFactory(ConnectionFactory connectionFactory) {

        var f = new SimpleRabbitListenerContainerFactory();
        f.setConnectionFactory(connectionFactory);
        f.setPrefetchCount(3); // 워커가 가져가는 메시지 개수래용
        f.setConcurrentConsumers(3); // 워커 개수래용
        f.setMaxConcurrentConsumers(10); // 최대 워커 개수래용
        f.setAcknowledgeMode(AcknowledgeMode.AUTO); // ack 받으면 자동으로 메시지 삭제
        f.setDefaultRequeueRejected(false); // 예외 발생 시 메시지를 다시 큐에 넣지 않음

        var retry = new RetryTemplate();
        var policy = new SimpleRetryPolicy();
        policy.setMaxAttempts(1);            //1번 재시도
        retry.setRetryPolicy(policy);
        var backoff = new FixedBackOffPolicy();
        backoff.setBackOffPeriod(1000);                // 2초 간격
        retry.setBackOffPolicy(backoff);
        // 예외 발생시 핸들링
        f.setAdviceChain(RetryInterceptorBuilder.stateless().retryOperations(retry).build());
        return f;


    }
}
