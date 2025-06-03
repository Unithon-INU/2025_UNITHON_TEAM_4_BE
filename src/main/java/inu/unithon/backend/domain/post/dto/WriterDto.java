package inu.unithon.backend.domain.post.dto;


import inu.unithon.backend.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WriterDto {
  private Long id;
  private String name;

  public static WriterDto from(Member member) {
    return WriterDto.builder()
      .id(member.getId())
      .name(member.getName())
      .build();
  }
}
