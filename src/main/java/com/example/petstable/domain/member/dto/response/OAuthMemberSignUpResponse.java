package com.example.petstable.domain.member.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OAuthMemberSignUpResponse {

    private Long id;
    private String nickName;
}
