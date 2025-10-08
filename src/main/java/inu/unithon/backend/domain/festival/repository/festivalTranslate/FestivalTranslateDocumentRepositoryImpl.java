package inu.unithon.backend.domain.festival.repository.festivalTranslate;

import inu.unithon.backend.domain.festival.document.FestivalTranslateDocument;
import inu.unithon.backend.domain.festival.entity.TranslateLanguage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.elasticsearch.client.elc.QueryBuilders.boolQuery;
import static org.springframework.data.elasticsearch.client.elc.QueryBuilders.multiMatchQuery;
import static org.springframework.data.elasticsearch.client.elc.QueryBuilders.termQuery;
import static org.springframework.data.elasticsearch.client.elc.QueryBuilders.rangeQuery;

@Repository
@RequiredArgsConstructor
public class FestivalTranslateDocumentRepositoryImpl implements FestivalTranslateDocumentRepositoryCustom {

  private final ElasticsearchOperations esOperations;

  @Override
  public Page<FestivalTranslateDocument> searchByKeyword(TranslateLanguage language, String keyword, Pageable pageable) {

    NativeQuery query = NativeQuery.builder()
      .withQuery(QueryBuilders.boolQuery()
        .must(QueryBuilders.termQuery("language", language.name()))
        .must(QueryBuilders.multiMatchQuery(keyword)
          .fields("title", "title.eng", "title.chn", "title.jpn", "title.fra", "title.rus", "title.spa", "content", "address")
        )
      )
      .withPageable(pageable)
      .build();

    List<FestivalTranslateDocument> results = esOperations.search(query, FestivalTranslateDocument.class)
      .stream()
      .map(SearchHit::getContent)
      .collect(Collectors.toList());

    long total = esOperations.count(query, FestivalTranslateDocument.class);

    return new PageImpl<>(results, pageable, total);
  }

  @Override
  public Page<FestivalTranslateDocument> searchByPeriod(TranslateLanguage language, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {

    NativeQuery query = NativeQuery.builder()
      .withQuery(QueryBuilders.boolQuery()
        .must(QueryBuilders.termQuery("language", language.name()))
        .must(QueryBuilders.rangeQuery("startDate").gte(startDate))
        .must(QueryBuilders.rangeQuery("endDate").lte(endDate))
      )
      .withPageable(pageable)
      .build();

    List<FestivalTranslateDocument> results = esOperations.search(query, FestivalTranslateDocument.class)
      .stream()
      .map(SearchHit::getContent)
      .collect(Collectors.toList());

    long total = esOperations.count(query, FestivalTranslateDocument.class);

    return new PageImpl<>(results, pageable, total);
  }
}
