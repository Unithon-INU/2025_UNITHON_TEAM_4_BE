package inu.unithon.backend.domain.festival.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Builder;


@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class FestivalIntroDto {
    private String contentid;
    private String contenttypeid;
    private String sponsor1;
    private String sponsor1tel;
    private String sponsor2;
    private String sponsor2tel;
    private String eventenddate;
    private String playtime;
    private String eventplace;
    private String eventhomepage;
    private String agelimit;
    private String bookingplace;
    private String placeinfo;
    private String subevent;
    private String program;
    private String eventstartdate;
    private String usetimefestival;
    private String discountinfofestival;
    private String spendtimefestival;
    private String festivalgrade;
}