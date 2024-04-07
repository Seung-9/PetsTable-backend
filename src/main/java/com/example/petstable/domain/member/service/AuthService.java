package com.example.petstable.domain.member.service;

import com.example.petstable.domain.member.entity.MemberEntity;
import com.example.petstable.domain.member.entity.SocialType;
import com.example.petstable.domain.member.repository.MemberRepository;
import com.example.petstable.global.auth.dto.request.AppleLoginRequest;
import com.example.petstable.global.auth.dto.response.AppleTokenResponse;
import com.example.petstable.global.auth.ios.AppleOAuthUserProvider;
import com.example.petstable.global.auth.ios.AppleSocialMemberResponse;
import com.example.petstable.global.auth.ios.jwt.JwtTokenProvider;
import com.example.petstable.global.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.petstable.global.exception.message.MemberMessage.*;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AppleOAuthUserProvider appleOAuthUserProvider;

    public AppleTokenResponse appleOAuthLogin(AppleLoginRequest request) {
        AppleSocialMemberResponse appleSocialMember = appleOAuthUserProvider.getApplePlatformMember(request.getToken());

        String socialId = appleSocialMember.getSocialId();

        return memberRepository.findIdBySocialTypeAndSocialId(SocialType.APPLE, socialId)
                .map(memberId -> {
                    MemberEntity findMember = memberRepository.findById(memberId).orElseThrow(
                            () -> new ApiException(MEMBER_NOT_FOUND));
                    String token = issueToken(findMember);
                    return new AppleTokenResponse(token, findMember.getEmail(), true, socialId);
                }).orElseGet(() -> new AppleTokenResponse(null, appleSocialMember.getEmail(), false, socialId));
    }

    private String issueToken(final MemberEntity findMember) {
        String email = findMember.getEmail();

        return jwtTokenProvider.createToken(email);
    }
}
