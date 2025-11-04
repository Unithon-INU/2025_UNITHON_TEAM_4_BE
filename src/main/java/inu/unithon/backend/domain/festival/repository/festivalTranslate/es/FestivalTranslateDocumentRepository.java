package inu.unithon.backend.domain.festival.repository.festivalTranslate.es;

import inu.unithon.backend.domain.festival.document.FestivalTranslateDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


public interface FestivalTranslateDocumentRepository extends ElasticsearchRepository<FestivalTranslateDocument, String>, FestivalTranslateDocumentRepositoryCustom {
}

