package inu.unithon.backend.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

@Configuration
public class ElasticsearchConfig extends ElasticsearchConfiguration {

  @Value("${spring.data.elasticsearch.client.rest.uris}")
  private String[] uris;

  @Value("${spring.data.elasticsearch.client.rest.username:}")
  private String username;

  @Value("${spring.data.elasticsearch.client.rest.password:}")
  private String password;

  @Override
  public ClientConfiguration clientConfiguration() {
    if (username != null && !username.isEmpty() && password != null && !password.isEmpty()) {
      // 보안 인증 포함된 빌더 체인
      return ClientConfiguration.builder()
        .connectedTo(uris)
        .withBasicAuth(username, password)
        .build();
    } else {
      // 인증 없는 빌더 체인
      return ClientConfiguration.builder()
        .connectedTo(uris)
        .build();
    }
  }
}
