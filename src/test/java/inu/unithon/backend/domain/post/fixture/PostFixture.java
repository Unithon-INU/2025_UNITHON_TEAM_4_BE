package inu.unithon.backend.domain.post.fixture;

import inu.unithon.backend.domain.member.entity.Member;
import inu.unithon.backend.domain.post.entity.Post;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;

public class PostFixture {

  public static Post createDefault(Long id, Member member) {
    Post post = Post.builder()
      .title("test-title")
      .content("test-content")
      .thumbnailUrl("test-thumbnailUrl")
      .member(member)
      .build();

    ReflectionTestUtils.setField(post, "id", id);
    return post;
  }
}
