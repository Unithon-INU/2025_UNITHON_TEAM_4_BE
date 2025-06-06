package inu.unithon.backend.domain.festival.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Builder;


import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FestivalInfoResponseDto {
    private Response response;

    @Getter
    @NoArgsConstructor
    public static class Response {
        private Header header;
        private Body body;
    }

    @Getter
    @NoArgsConstructor
    public static class Header {
        private String resultCode;
        private String resultMsg;
    }

    @Getter
    @NoArgsConstructor
    public static class Body {
        private Items items;
        private int numOfRows;
        private int pageNo;
        private int totalCount;

        @Getter
        @NoArgsConstructor
        public static class Items {
            private List<FestivalInfoDto> item;
        }
    }
}