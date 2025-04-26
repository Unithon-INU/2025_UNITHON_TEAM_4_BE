package inu.unithon.backend.domain.member.entity;

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
  private String phone;

  @Enumerated(EnumType.STRING)
  private Role role;

  @OneToMany(mappedBy = "member" , cascade = CascadeType.ALL , orphanRemoval = true, fetch = FetchType.LAZY)
  private List<Post> posts;

  @Builder
  public Member(String name, String profileImageUrl, String email, String password, String phone, Role role) {
    this.name = name;
    this.profileImageUrl = profileImageUrl;
    this.email = email;
    this.password = password;
    this.phone = phone;
    this.role = role;
  }
}
