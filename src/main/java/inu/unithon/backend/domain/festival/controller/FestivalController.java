package inu.unithon.backend.festival.controller;

import inu.unithon.backend.festival.dto.FestivalResponseWrapper;
import inu.unithon.backend.festival.service.FestivalService;
import inu.unithon.backend.global.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/festivals")
@RequiredArgsConstructor
public class FestivalController {

    private final FestivalService festivalService;

    @GetMapping("/{lang}/area")
    public ResponseEntity<ResponseDto<?>> getFestivalByArea(
            @PathVariable String lang,
            @RequestParam(required = false) String areaCode,
            @RequestParam(defaultValue = "10") String numOfRows,
            @RequestParam(defaultValue = "1") String pageNo
    ) {
        FestivalResponseWrapper response = festivalService.getFestivalList(lang, areaCode);
        return ResponseEntity.ok(ResponseDto.success(response));
    }
}
