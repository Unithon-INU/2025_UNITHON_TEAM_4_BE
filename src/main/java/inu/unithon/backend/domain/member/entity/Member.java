package inu.unithon.backend.domain.member.entity;

import inu.unithon.backend.domain.member.dto.request.UpdateProfileRequestDto;
import inu.unithon.backend.domain.member.dto.response.ProfileResponseDto;
import inu.unithon.backend.domain.post.entity.Post;
import inu.unithon.backend.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String profileImageUrl;
  private String email;
  private String password;

  @Enumerated(EnumType.STRING)
  private Role role;

  @OneToMany(mappedBy = "member" , cascade = CascadeType.ALL , orphanRemoval = true, fetch = FetchType.LAZY)
  private List<Post> posts;

  @Builder
  public Member(String name, String profileImageUrl, String email, String password, Role role) {
    this.name = name;
    this.profileImageUrl = profileImageUrl;
    this.email = email;
    this.password = password;
    this.role = role;
  }

  public void updateMember(String name, String profileImageUrl, String email, Role role) {
    this.name = name;
    this.profileImageUrl = profileImageUrl;
    this.email = email;
    this.role = role;
  }

  public void updatePassword(String password) {
    this.password = password;
  }
}
