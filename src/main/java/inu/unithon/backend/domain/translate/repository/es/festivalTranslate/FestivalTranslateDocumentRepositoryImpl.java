package inu.unithon.backend.domain.translate.repository.es.festivalTranslate;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.json.JsonData;
import inu.unithon.backend.domain.translate.document.FestivalTranslateDocument;
import inu.unithon.backend.domain.translate.entity.TranslateLanguage;
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

@Repository
@RequiredArgsConstructor
public class FestivalTranslateDocumentRepositoryImpl implements FestivalTranslateDocumentRepositoryCustom {

  private final ElasticsearchOperations esOperations;

  @Override
  public Page<FestivalTranslateDocument> searchByKeyword(TranslateLanguage language, String keyword, Pageable pageable) {

    // ELC DSL 방식으로 쿼리 구성
    Query query = Query.of(q -> q
      .bool(b -> b
        .must(m -> m.term(t -> t.field("language").value(language.name())))
        .must(m -> m.multiMatch(mm -> mm
          .fields("title", "title.eng", "title.chn", "title.jpn", "title.fra", "title.rus", "title.spa", "content", "address")
          .query(keyword)
        ))
      )
    );

    NativeQuery nativeQuery = NativeQuery.builder()
      .withQuery(query)
      .withPageable(pageable)
      .build();

    List<FestivalTranslateDocument> results = esOperations.search(nativeQuery, FestivalTranslateDocument.class)
      .stream()
      .map(SearchHit::getContent)
      .collect(Collectors.toList());

    long total = esOperations.count(nativeQuery, FestivalTranslateDocument.class);

    return new PageImpl<>(results, pageable, total);
  }

  @Override
  public Page<FestivalTranslateDocument> searchByPeriod(TranslateLanguage language, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {

    Query query = Query.of(q -> q
      .bool(b -> b
        .must(m -> m.term(t -> t.field("language").value(language.name())))
        .must(m -> m.range(r -> r.field("startDate").gte(JsonData.of(startDate.toString()))))
        .must(m -> m.range(r -> r.field("endDate").lte(JsonData.of(endDate.toString()))))
      )
    );

    NativeQuery nativeQuery = NativeQuery.builder()
      .withQuery(query)
      .withPageable(pageable)
      .build();

    List<FestivalTranslateDocument> results = esOperations.search(nativeQuery, FestivalTranslateDocument.class)
      .stream()
      .map(SearchHit::getContent)
      .collect(Collectors.toList());

    long total = esOperations.count(nativeQuery, FestivalTranslateDocument.class);

    return new PageImpl<>(results, pageable, total);
  }
}
