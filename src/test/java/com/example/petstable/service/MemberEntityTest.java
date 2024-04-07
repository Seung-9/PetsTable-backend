package com.example.petstable.service;

import com.example.petstable.domain.member.entity.MemberEntity;
import com.example.petstable.domain.member.entity.SocialType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class MemberEntityTest {

    @Test
    @DisplayName("회원 가입 시 입력받은 정보로 수정한다")
    void registerOAuthMember() {
        MemberEntity member = MemberEntity.builder()
                .email("ssg@apple.com")
                .socialType(SocialType.APPLE)
                .socialId("123456789")
                .build();


        member.registerOAuthMember("20191476@apple.com", "Seung");

        assertAll(
                () -> assertThat(member.getEmail()).isEqualTo("20191476@apple.com"),
                () -> assertThat(member.getNickName()).isEqualTo("Seung")
        );
    }

    @Test
    @DisplayName("회원 가입 시 입력받은 정보 중 이메일이 null 이면 이메일은 수정되지 않는다")
    void registerOAuthMemberWhenEmailIsNull() {
        MemberEntity member = MemberEntity.builder()
                .email("ssg@apple.com")
                .socialType(SocialType.APPLE)
                .socialId("123456789")
                .build();

        member.registerOAuthMember(null, "Seung");

        assertAll(
                () -> assertThat(member.getEmail()).isNotNull(),
                () -> assertThat(member.getNickName()).isEqualTo("Seung")
        );
    }
}
