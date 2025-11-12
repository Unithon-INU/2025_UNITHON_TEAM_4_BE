package inu.unithon.backend.domain.like.festivalLike.repository;

import inu.unithon.backend.domain.like.festivalLike.entity.FestivalLike;
import inu.unithon.backend.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FestivalLikeRepository extends JpaRepository<FestivalLike, Long> {
    Optional<FestivalLike> findByMemberAndContentId(Member member, String contentId);
    List<FestivalLike> findAllByMember(Member member);
}
