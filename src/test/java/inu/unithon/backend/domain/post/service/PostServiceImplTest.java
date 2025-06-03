package inu.unithon.backend.domain.post.service;

import inu.unithon.backend.domain.member.entity.Member;
import inu.unithon.backend.domain.auth.fixture.AuthFixture;
import inu.unithon.backend.domain.post.dto.response.PostResponse;
import inu.unithon.backend.domain.post.entity.Post;
import inu.unithon.backend.domain.post.fixture.PostFixture;
import inu.unithon.backend.domain.post.repository.PostRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PostServiceImplTest {

  @Mock
  private PostRepository postRepository;

  @InjectMocks
  private PostServiceImpl postService;

  private Member member1;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this); // Mockito 초기화

    member1 = AuthFixture.create(1L, "testEmail@gmail.com", "frozzun");
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void create() {
//    // given
//    PostCreateRequest request = PostCreateRequest.builder()
//      .title("test-title")
//      .content("test-content")
//      .thumbnailUrl("test-thumbnailUrl")
//      .images(List.of())
//      .member(member1)
//      .build();
//
//    // when
//    postService.create(1L, request);
//
//    // then
  }

  @Test
  void getPost() {
    // given
    Long postId = 1L;
    Post post = PostFixture.createDefault(postId, member1);
    when(postRepository.findById(postId)).thenReturn(Optional.of(post));

    // when
    PostResponse result = postService.getPost(postId);

    // then
    assertNotNull(result);
    assertEquals(post.getLikes(), result.getLikes());
    assertEquals(post.getTitle(), result.getTitle());
    assertEquals(post.getContent(), result.getContent());
    assertEquals(post.getThumbnailUrl(), result.getThumbnailUrl());
    assertEquals(post.getImages().size(), result.getImages().size());
    assertEquals(post.getComments().size(), result.getComments().size());

    // writer 비교
    assertEquals(post.getMember().getName(), result.getWriter().getName());
    assertEquals(post.getMember().getId(), result.getWriter().getId());

    verify(postRepository).findById(postId);
  }

  @Test
  void getAllPosts() {
    // given
    Post post1 = PostFixture.createDefault(1L, member1);
    Post post2 = PostFixture.createDefault(2L, member1);
    when(postRepository.findAll()).thenReturn(List.of(post1, post2));

    // when
    List<PostResponse> result = postService.getAllPosts();

    // then
    assertEquals(2, result.size());
    verify(postRepository).findAll();
  }

  @Test
  void updatePost() {
    // given

    // when

    // then

  }

  @Test
  void delete() {

  }
}