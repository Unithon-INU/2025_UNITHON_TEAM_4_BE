package inu.unithon.backend.domain.member.service;

import inu.unithon.backend.domain.auth.fixture.AuthFixture;
import inu.unithon.backend.domain.member.entity.Member;
import inu.unithon.backend.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceImplTest {

  @Mock
  private MemberRepository memberRepository;

  @InjectMocks
  private MemberServiceImpl memberService;

  private Member admin;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this); // Mockito 초기화

    admin = AuthFixture.createAdmin(1L, "testEmail@gmail.com", "frozzun");
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void getMyProfile() {
  }

  @Test
  void getProfile() {
  }

  @Test
  void updateProfile() {
  }

  @Test
  void deleteProfile() {
  }

  @Test
  void updatePassword() {
  }

  @Test
  void updateProfileImage() {
  }

  @Test
  void getMembers() {
  }

  @Test
  void deleteMember() {
  }

  @Test
  void getMember() {
  }
}