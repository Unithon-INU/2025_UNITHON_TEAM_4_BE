package inu.unithon.backend.domain.post.entity;

import inu.unithon.backend.domain.comment.entity.Comment;
import inu.unithon.backend.domain.member.entity.Member;
import inu.unithon.backend.domain.post.dto.request.PostUpdateRequest;
import inu.unithon.backend.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Post extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long likes; // 좋아요 갯수 todo : 정합성, 동시성 컨트롤.
  private String title;
  private String content;
  private String thumbnailUrl;

  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<PostImage> images = new ArrayList<>();

  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<Comment> comments = new ArrayList<>();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id", nullable = false)
  private Member member;

  @Builder
  public Post(String title, String content, String thumbnailUrl, Member member) {
    this.likes = 0L;
    this.title = title;
    this.content = content;
    this.thumbnailUrl = thumbnailUrl;
    this.member = member;
  }

  public void addImage(PostImage image) {
    images.add(image);
    image.setPost(this);
  }

  public void increaseLikeCount() {
    this.likes++;
  }

  public void decreaseLikeCount() {
    this.likes = Math.max(0, this.likes - 1);
  }


  public void updatePost(PostUpdateRequest updateRequest, List<PostImage> images) {
    this.title = updateRequest.getTitle();
    this.content = updateRequest.getContent();
    this.images = images;
  }
}
