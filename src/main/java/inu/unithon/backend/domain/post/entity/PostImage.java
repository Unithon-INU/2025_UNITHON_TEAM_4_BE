package inu.unithon.backend.domain.post.entity;

import inu.unithon.backend.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PostImage extends BaseEntity {

  @Id
  @GeneratedValue
  private Long id;

  private String imageUrl;

  private int orderIndex; // 이미지 정렬 순서

  @Setter
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_id", nullable = false)
  private Post post;

  @Builder
  public PostImage(String imageUrl, int orderIndex, Post post) {
    this.imageUrl = imageUrl;
    this.orderIndex = orderIndex;
    this.post = post;
  }

}
