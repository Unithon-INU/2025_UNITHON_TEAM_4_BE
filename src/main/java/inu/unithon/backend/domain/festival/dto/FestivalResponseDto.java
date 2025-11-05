package inu.unithon.backend.domain.festival.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;


import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FestivalResponseDto {
    private FestivalResponse response;

    @Getter
    @NoArgsConstructor
    public static class FestivalResponse {
        private FestivalHeader header;
        private FestivalBody body;
    }

    @Getter
    @NoArgsConstructor
    public static class FestivalHeader {
        private String resultCode;
        private String resultMsg;
    }

    @Getter
    @NoArgsConstructor
    public static class FestivalBody {
        private Items items;
        private int numOfRows;
        private int pageNo;
        private int totalCount;

        @Getter
        @NoArgsConstructor
        public static class Items {
            private List<FestivalDto> item;
        }
    }
}