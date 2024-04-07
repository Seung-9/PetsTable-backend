package com.example.petstable.global.auth.ios;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AppleSocialMemberResponse {

    private String socialId;
    private String email;
}
