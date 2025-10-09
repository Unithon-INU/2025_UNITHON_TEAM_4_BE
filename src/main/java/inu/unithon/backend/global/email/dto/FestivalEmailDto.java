package inu.unithon.backend.global.email.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FestivalEmailDto {
  String email;
  String subject;
  String festivalName;
  LocalDateTime festivalStart;
  LocalDateTime festivalEnd;

  @Builder
  public FestivalEmailDto(String email, String subject, String festivalName, LocalDateTime festivalStart, LocalDateTime festivalEnd) {
    this.email = email;
    this.subject = subject;
    this.festivalName = festivalName;
    this.festivalStart = festivalStart;
    this.festivalEnd = festivalEnd;
  }
}
