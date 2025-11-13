package inu.unithon.backend.global.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.cfg.CoercionAction;
import com.fasterxml.jackson.databind.cfg.CoercionInputShape;
import com.fasterxml.jackson.databind.type.LogicalType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration  // ✅ 스프링이 자동으로 이 설정 클래스를 읽게 해줌
public class JacksonConfig {

    @Bean  // ✅ 스프링 컨테이너에 ObjectMapper 빈 등록
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        // ""(빈 문자열)을 null로 간주하도록 설정
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

        // POJO 변환 시에도 "" -> null 허용
        objectMapper.coercionConfigFor(LogicalType.POJO)
                .setCoercion(CoercionInputShape.EmptyString, CoercionAction.AsNull);

        return objectMapper;
    }
}
