package inu.unithon.backend.domain.post.service;

import inu.unithon.backend.domain.post.dto.request.PostCreateRequest;
import inu.unithon.backend.domain.post.dto.request.PostUpdateRequest;
import inu.unithon.backend.domain.post.dto.response.PostResponse;

import java.util.List;

public interface PostService {
  // todo : 글 Create
  void create(Long memberId, PostCreateRequest postCreateRequest);

  // todo : 글 Read(단일 세부)
  PostResponse getPost(Long postId);

  // todo : 글 Read(전체 목록)
  List<PostResponse> getAllPosts();

  // todo : 글 Update
  PostResponse updatePost(Long memberId, Long postId, PostUpdateRequest postUpdateRequest);

  // todo : 글 Delete
  Long delete(Long memberId, Long postId);
}
