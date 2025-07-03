package inu.unithon.backend.domain.postLike.repository;

import inu.unithon.backend.domain.member.entity.Member;
import inu.unithon.backend.domain.post.entity.Post;
import inu.unithon.backend.domain.postLike.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> findByMemberAndPost(Member member, Post post);
    List<PostLike> findAllByMember(Member member);
}
