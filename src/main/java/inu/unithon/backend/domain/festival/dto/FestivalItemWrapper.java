package inu.unithon.backend.festival.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class FestivalItemWrapper {
    private List<FestivalDto> item;
}
