package inu.unithon.backend.domain.like.postLike.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import inu.unithon.backend.domain.member.entity.Member;
import inu.unithon.backend.domain.post.entity.Post;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class PostLike {

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore //post 정보를 직렬화할 때 무한 루프 방지
    private Post post;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

}
