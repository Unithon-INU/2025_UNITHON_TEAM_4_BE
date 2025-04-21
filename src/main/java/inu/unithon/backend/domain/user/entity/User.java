package inu.unithon.backend.domain.user.entity;

import inu.unithon.backend.domain.post.entity.Post;
import inu.unithon.backend.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String username;
  private String profileImageUrl;
  private String email;
  private String password;
  private String phone;

  @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL , orphanRemoval = true, fetch = FetchType.LAZY)
  private List<Post> posts;

  @Builder
  public User(String username, String profileImageUrl, String email, String password, String phone) {
    this.username = username;
    this.profileImageUrl = profileImageUrl;
    this.email = email;
    this.password = password;
    this.phone = phone;
  }
}
