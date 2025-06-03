package inu.unithon.backend.domain.festival.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FestivalInfoDto {
    private String contentid;
    private String contenttypeid;
    private String serialnum;
    private String infoname;
    private String infotext;
    private String fldgubun;
}
