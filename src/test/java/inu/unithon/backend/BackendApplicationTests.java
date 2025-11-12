package inu.unithon.backend;

import inu.unithon.backend.domain.translate.repository.es.festivalContentTranslate.FestivalContentTranslateDocumentRepository;
import inu.unithon.backend.domain.translate.repository.es.festivalTranslate.FestivalTranslateDocumentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class BackendApplicationTests {

	@MockBean
	private FestivalTranslateDocumentRepository festivalTranslateDocumentRepository;

	@MockBean
	private FestivalContentTranslateDocumentRepository festivalContentTranslateDocumentRepository;


	@Test
	void contextLoads() {
	}

}
