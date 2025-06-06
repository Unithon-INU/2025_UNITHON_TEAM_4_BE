package inu.unithon.backend.domain.festival.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Builder;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FestivalInfoDto {
    private String contentid;
    private String contenttypeid;
    private String serialnum;
    private String infoname;
    private String infotext;
    private String fldgubun;
}