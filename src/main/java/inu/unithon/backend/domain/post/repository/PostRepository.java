package inu.unithon.backend.domain.post.repository;

import inu.unithon.backend.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
