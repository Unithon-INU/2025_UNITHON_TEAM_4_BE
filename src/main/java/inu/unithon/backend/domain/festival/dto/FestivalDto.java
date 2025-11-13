package inu.unithon.backend.domain.festival.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FestivalDto {
    private String addr1;
    private String addr2;
    private String areacode;
    private Long contentid;
    private String contenttypeid;   //
    private String createdtime;
    private String firstimage;
    private String firstimage2;
    private String mapx;
    private String mapy;
    private String modifiedtime;
    private String tel;
    private String title;
    private String zipcode; // 안써도 됨
    private String overview;
    private String dist;    // 축제에서의 거리
    private String eventstartdate;
    private String eventenddate;
//    private List<FestivalDto> item;  // 중첩된 item 지원
}