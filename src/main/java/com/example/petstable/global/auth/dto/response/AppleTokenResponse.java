package com.example.petstable.global.auth.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AppleTokenResponse {

    private String token;
    private String email;
    private Boolean isRegistered;
    private String socialId;
}
