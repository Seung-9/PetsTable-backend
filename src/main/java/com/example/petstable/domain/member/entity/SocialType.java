package com.example.petstable.domain.member.entity;

import com.example.petstable.global.exception.ApiException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Objects;

import static com.example.petstable.global.exception.message.MemberMessage.*;

@AllArgsConstructor @NoArgsConstructor
@Getter
public enum SocialType {

    NOMAL("petstable"),
    NAVER("naver"),
    KAKAO("kakao"),
    APPLE("apple");

    private String value;

    public static SocialType from(String value) {
        return Arrays.stream(values())
                .filter(it -> Objects.equals(it.value, value))
                .findFirst()
                .orElseThrow(() -> new ApiException(INVALID_SOCIAL));
    }
}
