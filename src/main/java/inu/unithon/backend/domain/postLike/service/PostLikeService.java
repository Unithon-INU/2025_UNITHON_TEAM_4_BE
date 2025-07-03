package inu.unithon.backend.domain.postLike.service;

import inu.unithon.backend.domain.member.entity.Member;
import inu.unithon.backend.domain.post.entity.Post;
import inu.unithon.backend.domain.post.repository.PostRepository;
import inu.unithon.backend.domain.postLike.dto.PostLikeResponseDto;
import inu.unithon.backend.domain.postLike.entity.PostLike;
import inu.unithon.backend.domain.postLike.repository.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

    @Transactional
    public boolean toggleLike(Member member, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        return postLikeRepository.findByMemberAndPost(member, post)
                .map(like -> {
                    postLikeRepository.delete(like);
                    post.decreaseLikeCount(); // 좋아요 숫자 감소
                    return false;
                })
                .orElseGet(() -> {
                    postLikeRepository.save(PostLike.builder().member(member).post(post).build());
                    post.increaseLikeCount(); // 좋아요 숫자 증가
                    return true;
                });
    }

    public List<PostLikeResponseDto> getLikedPosts(Member member) {
        return postLikeRepository.findAllByMember(member).stream()
                .map(postLike -> PostLikeResponseDto.from(postLike.getPost()))
                .toList();
    }

}
