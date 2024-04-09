package com.example.petstable.global.exception.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberMessage implements ResponseMessage {

    MEMBER_NOT_FOUND("해당 유저는 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    INACTIVE_MEMBER("해당 유저는 존재하지 않습니다.", HttpStatus.UNAUTHORIZED),
    INVALID_NICKNAME("닉네임은 영어, 한글로만 구성된 2~6자여야 합니다.", HttpStatus.BAD_REQUEST),
    INVALID_SOCIAL("소셜 정보가 올바르지 않습니다.", HttpStatus.BAD_REQUEST),
    LOGIN_SUCCESS("로그인 성공", HttpStatus.OK);

    private final String message;
    private final HttpStatus status;
}
