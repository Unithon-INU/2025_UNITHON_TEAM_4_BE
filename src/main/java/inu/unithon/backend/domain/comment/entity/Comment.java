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

  private Long memberId;  // member ID
  private String memberName;
  private String memberProfileImageUrl;
  private Long likes;

  @Builder
  public Comment(String content, Post post, Long memberId, String memberName, String memberProfileImageUrl) {
    this.content = content;
    this.post = post;
    this.memberId = memberId;
    this.memberName = memberName;
    this.memberProfileImageUrl = memberProfileImageUrl;
    this.likes = 0L;
  }

  public void updateContent(String newContent) {
    this.content = newContent;
  }
  public void increaseLikeCount() {
    this.likes++;
  }
  public void decreaseLikeCount() {
    this.likes = Math.max(0, this.likes - 1);
  }


}
