package inu.unithon.backend.domain.post.repository;

import inu.unithon.backend.domain.post.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {
}
