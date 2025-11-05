package inu.unithon.backend.domain.festivalLike.service;

import inu.unithon.backend.domain.festivalLike.entity.FestivalLike;
import inu.unithon.backend.domain.festivalLike.repository.FestivalLikeRepository;
import inu.unithon.backend.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FestivalLikeService {

    private final FestivalLikeRepository festivalLikeRepository;

    // 좋아요 토글
    public boolean addLike(Member member, String contentId, String title, String imageUrl, String address) {
        Optional<FestivalLike> existingLike = festivalLikeRepository.findByMemberAndContentId(member, contentId);

        if (existingLike.isPresent()) {
            // 이미 좋아요한 경우 삭제
            festivalLikeRepository.delete(existingLike.get());
            return false;
        } else {
            // 좋아요 추가
            FestivalLike festivalLike = FestivalLike.builder()
                    .member(member)
                    .contentId(contentId)
                    .title(title)
                    .imageUrl(imageUrl)
                    .address(address)
                    .build();

            festivalLikeRepository.save(festivalLike);
            return true;
        }
    }


    @Transactional(readOnly = true)
    public List<FestivalLike> getLikedFestivals(Member member) {
        return festivalLikeRepository.findAllByMember(member);
    }
}
