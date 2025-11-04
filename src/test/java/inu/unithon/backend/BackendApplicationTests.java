package inu.unithon.backend;

import inu.unithon.backend.domain.festival.repository.festivalTranslate.es.FestivalTranslateDocumentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class BackendApplicationTests {

	@MockBean
	private FestivalTranslateDocumentRepository festivalTranslateDocumentRepository;


	@Test
	void contextLoads() {
	}

}
