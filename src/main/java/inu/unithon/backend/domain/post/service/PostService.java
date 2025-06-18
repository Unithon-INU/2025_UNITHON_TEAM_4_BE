package inu.unithon.backend.domain.post.service;

import inu.unithon.backend.domain.post.dto.request.PostCreateRequest;
import inu.unithon.backend.domain.post.dto.request.PostUpdateRequest;
import inu.unithon.backend.domain.post.dto.response.PostResponse;
import inu.unithon.backend.domain.post.dto.response.PostDetailResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {

  /**
   * 글 작성
   * @param memberId
   * @param postCreateRequest
   * @param images
   */
  void create(Long memberId, PostCreateRequest postCreateRequest, List<MultipartFile> images);

  /**
   * 단일 글 세부사항 조회
   * @param postId
   * @return
   */
  PostDetailResponse getPost(Long postId);

  /**
   * 전체 글 조회
   * @param page
   * @param size
   * @return
   */
  Page<PostResponse> getAllPosts(int page, int size);

  // todo : 글 Update
  PostDetailResponse updatePost(Long memberId, Long postId, PostUpdateRequest postUpdateRequest, List<MultipartFile> images);

  /**
   * 글 삭제
   * @param memberId
   * @param postId
   * @return
   */
  Long delete(Long memberId, Long postId);
}
