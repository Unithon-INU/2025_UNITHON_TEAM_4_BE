package inu.unithon.backend.domain.post.service;

import inu.unithon.backend.domain.member.service.MemberService;
import inu.unithon.backend.domain.post.dto.ImageDto;
import inu.unithon.backend.domain.post.dto.request.PostCreateRequest;
import inu.unithon.backend.domain.post.dto.request.PostUpdateRequest;
import inu.unithon.backend.domain.post.dto.response.PostResponse;
import inu.unithon.backend.domain.post.entity.Post;
import inu.unithon.backend.domain.post.entity.PostImage;
import inu.unithon.backend.domain.post.repository.PostRepository;
import inu.unithon.backend.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static inu.unithon.backend.global.exception.ErrorCode.FORBIDDEN;
import static inu.unithon.backend.global.exception.ErrorCode.POST_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {

  private final PostRepository postRepository;
  private final MemberService memberService;

  @Override
  public void create(Long memberId, PostCreateRequest postCreateRequest) {

    Post post = Post.builder()
      .title(postCreateRequest.getTitle())
      .content(postCreateRequest.getContent())
      .thumbnailUrl(postCreateRequest.getThumbnailUrl())
      .member(memberService.getMember(memberId))
      .build();

    int index = 1;
    for (ImageDto dto : postCreateRequest.getImages()) {
      PostImage postImage = PostImage.builder()
        .imageUrl(dto.getImageUrl())
        .orderIndex(index++)
        .post(post)
        .build();
      post.addImage(postImage);
    }

    postRepository.save(post);
    log.info("Create Post : ${}", postCreateRequest.getTitle());
  }

  @Override
  @Transactional(readOnly = true)
  public PostResponse getPost(Long postId) {

    Post post = postRepository.findById(postId)
      .orElseThrow(() -> new CustomException(POST_NOT_FOUND));
    log.info("Get Post : ${}", postId);

    return PostResponse.from(post);
  }

  @Override
  @Transactional(readOnly = true)
  public List<PostResponse> getAllPosts() {
    List<Post> posts = postRepository.findAll();
    log.info("Get All Posts : ${}", posts);

    return posts.stream()
      .map(PostResponse::from)
      .toList();
  }

  @Override
  public PostResponse updatePost(Long memberId, Long postId, PostUpdateRequest postUpdateRequest) {
    Post post = postRepository.findById(postId)
      .orElseThrow(() -> new CustomException(POST_NOT_FOUND));

    if (!post.getMember().getId().equals(memberId)) throw new CustomException(FORBIDDEN);

    PostResponse postResponse = PostResponse.from(post);
    log.info("Update Post : ${}", postUpdateRequest.getTitle());
    return postResponse;
  }

  @Override
  public Long delete(Long memberId, Long postId) {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new CustomException(POST_NOT_FOUND));

    if (!post.getMember().getId().equals(memberId)) throw new CustomException(FORBIDDEN);
    postRepository.deleteById(postId);
    log.info("Delete Post : ${}", postId);
    return postId;
  }
}
