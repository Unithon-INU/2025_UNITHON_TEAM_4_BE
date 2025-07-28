package inu.unithon.backend.domain.post.service;

import inu.unithon.backend.domain.member.entity.Member;
import inu.unithon.backend.domain.member.entity.Role;
import inu.unithon.backend.domain.member.repository.MemberRepository;
import inu.unithon.backend.domain.member.service.MemberService;
import inu.unithon.backend.domain.post.dto.request.PostCreateRequest;
import inu.unithon.backend.domain.post.dto.request.PostUpdateRequest;
import inu.unithon.backend.domain.post.dto.response.PostResponse;
import inu.unithon.backend.domain.post.dto.response.PostDetailResponse;
import inu.unithon.backend.domain.post.entity.Post;
import inu.unithon.backend.domain.post.entity.PostImage;
import inu.unithon.backend.domain.post.repository.PostRepository;
import inu.unithon.backend.global.exception.CustomException;
import inu.unithon.backend.global.exception.CommonErrorCode;
import inu.unithon.backend.global.service.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static inu.unithon.backend.global.exception.CommonErrorCode.FORBIDDEN;
import static inu.unithon.backend.global.exception.CommonErrorCode.POST_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {

  private final PostRepository postRepository;
  private final MemberService memberService;
  private final S3Service s3Service;
  private final MemberRepository memberRepository;

  @Override
  public void create(Long memberId, PostCreateRequest postCreateRequest, List<MultipartFile> images) {
    if (images == null || images.isEmpty()) {
      throw new CustomException(CommonErrorCode.NO_IMAGE_INPUT);
    }

    List<String> imageUrls = images.stream()
      .map(s3Service::uploadImage)
      .toList();

    Member member = memberService.getMember(memberId);

    Post post = Post.builder()
      .title(postCreateRequest.getTitle())
      .content(postCreateRequest.getContent())
      .thumbnailUrl(imageUrls.getFirst())
      .member(member)
      .build();

    int index = 1;
    for (String imageUrl : imageUrls) {
      PostImage postImage = PostImage.builder()
        .imageUrl(imageUrl)
        .orderIndex(index++)
        .post(post)
        .build();
      post.addImage(postImage);
    }

    postRepository.save(post);
    member.increasePostCount(); // post 개수 증가
    log.info("Create Post : ${}", postCreateRequest.getTitle());
  }

  @Override
  @Transactional(readOnly = true)
  public PostDetailResponse getPost(Long postId) {

    Post post = postRepository.findById(postId)
      .orElseThrow(() -> new CustomException(POST_NOT_FOUND));
    log.info("Get Post : ${}", postId);

    return PostDetailResponse.fromPost(post);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<PostResponse> getAllPosts(int page, int size) {
    Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
    Page<Post> posts =  postRepository.findAll(pageable);

    return posts.map(PostResponse::fromPost);
  }

  @Override
  public PostDetailResponse updatePost(Long memberId, Long postId, PostUpdateRequest postUpdateRequest, List<MultipartFile> images) {
    Post post = postRepository.findById(postId)
      .orElseThrow(() -> new CustomException(POST_NOT_FOUND));

    if (!post.getMember().getId().equals(memberId)) throw new CustomException(FORBIDDEN);

// todo : 걍 수정할거 개많음... 업데이트를 어떤식으로 진행 해야 하는지.. 사진 업데이트는 어떻게 할까??

//    List<String> imageUrls = images.stream()
//      .map(s3Service::uploadImage)
//      .toList();
//
//    int index = 1;
//    for (String imageUrl : imageUrls) {
//      PostImage postImage = PostImage.builder()
//        .imageUrl(imageUrl)
//        .orderIndex(index++)
//        .post(post)
//        .build();
//      post.addImage(postImage);
//    }
//
//    post.updatePost(postUpdateRequest, images);

    log.info("Update Post : ${}", postUpdateRequest.getTitle());
    return PostDetailResponse.fromPost(post);
  }

  @Override
  public Long delete(Long memberId, Long postId) {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new CustomException(POST_NOT_FOUND));

    Member member = memberService.getMember(memberId);
    Member postOwner = post.getMember();

    if(member.getRole()!=Role.ADMIN) {
      if (!postOwner.getId().equals(memberId)) throw new CustomException(FORBIDDEN);
    }

    postRepository.deleteById(postId);
    postOwner.decreasePostCount();

    log.info("Delete Post : ${}", postId);
    return postId;
  }

}
