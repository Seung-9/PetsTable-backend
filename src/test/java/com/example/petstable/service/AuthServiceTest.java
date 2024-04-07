package com.example.petstable.service;


import com.example.petstable.domain.member.entity.MemberEntity;
import com.example.petstable.domain.member.entity.SocialType;
import com.example.petstable.domain.member.repository.MemberRepository;
import com.example.petstable.domain.member.service.AuthService;
import com.example.petstable.global.auth.dto.request.AppleLoginRequest;
import com.example.petstable.global.auth.dto.response.AppleTokenResponse;
import com.example.petstable.global.auth.ios.AppleOAuthUserProvider;
import com.example.petstable.global.auth.ios.AppleSocialMemberResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthServiceTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private AuthService authService;

    @MockBean
    private AppleOAuthUserProvider appleOAuthUserProvider;

    @Test
    @DisplayName("가입이 안 되어있으면 이메일 값을 보내고 isRegistered 값을 false로")
    void appleOAuthNotRegistered() {
        String expected = "ssg@apple.com";

        when(appleOAuthUserProvider.getApplePlatformMember(anyString()))
                .thenReturn(new AppleSocialMemberResponse("20191476", expected));

        AppleTokenResponse actual = authService.appleOAuthLogin(new AppleLoginRequest("token"));

        Assertions.assertAll(
                () -> assertThat(actual.getToken()).isNull(),
                () -> assertThat(actual.getEmail()).isEqualTo(expected),
                () -> assertThat(actual.getIsRegistered()).isFalse()
        );
    }

    @Test
    @DisplayName("이미 가입된 경우 토큰, 이메일, isRegistered 값을 true로")
    void appleOAuthRegistered() {
        String expected = "ssg@apple.com";

        MemberEntity member = MemberEntity.builder()
                .email("ssg@apple.com")
                .nickName("Seung-9")
                .imageUrl(null)
                .socialType(SocialType.APPLE)
                .socialId("20191476")
                .build();

        memberRepository.save(member);

        when(appleOAuthUserProvider.getApplePlatformMember(anyString()))
                .thenReturn(new AppleSocialMemberResponse("20191476", expected));

        AppleTokenResponse actual = authService.appleOAuthLogin(new AppleLoginRequest("token"));

        assertAll(
                () -> assertThat(actual.getToken()).isNotNull(),
                () -> assertThat(actual.getEmail()).isEqualTo(expected),
                () -> assertThat(actual.getIsRegistered()).isTrue()
        );
    }
}