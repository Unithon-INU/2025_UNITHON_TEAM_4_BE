package inu.unithon.backend.domain.comment.entity;

import inu.unithon.backend.domain.post.entity.Post;
import inu.unithon.backend.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Comment extends BaseEntity {
  @Id
  @GeneratedValue
  private Long id;

  private String content;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_id", nullable = false)
  private Post post;

  private String memberId;
  private String memberName;
  private String memberProfileImageUrl;

  @Builder
  public Comment(String content, String memberId, String memberName, String memberProfileImageUrl) {
    this.content = content;
    this.memberId = memberId;
    this.memberName = memberName;
    this.memberProfileImageUrl = memberProfileImageUrl;
  }

}
