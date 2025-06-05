package inu.unithon.backend.global.service;

import org.springframework.web.multipart.MultipartFile;

public interface S3Service {

  String uploadImage(MultipartFile file);
  void deleteImage(String imageUrl);
}
