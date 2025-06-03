package inu.unithon.backend.domain.festival.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.*;

@Getter
@NoArgsConstructor
public class FestivalDto {
    private String addr1;
    private String addr2;
    private String areacode;
    private String contentid;
    private String contenttypeid;
    private String createdtime;
    private String firstimage;
    private String firstimage2;
    private String mapx;
    private String mapy;
    private String modifiedtime;
    private String tel;
    private String title;
    private String zipcode;
    private String overview;
    private List<FestivalDto> item;  // 중첩된 item 지원
}
