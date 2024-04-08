package com.example.petstable.domain.member.service;

import com.example.petstable.domain.member.dto.response.TokenResponse;
import com.example.petstable.domain.member.entity.MemberEntity;
import com.example.petstable.domain.member.entity.SocialType;
import com.example.petstable.domain.member.repository.MemberRepository;
import com.example.petstable.global.auth.dto.request.AppleLoginRequest;
import com.example.petstable.global.auth.ios.AppleOAuthUserProvider;
import com.example.petstable.global.auth.ios.AppleSocialMemberResponse;
import com.example.petstable.global.auth.ios.jwt.JwtTokenProvider;
import com.example.petstable.global.exception.PetsTableException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.petstable.global.exception.message.MemberMessage.*;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AppleOAuthUserProvider appleOAuthUserProvider;

    public TokenResponse appleOAuthLogin(AppleLoginRequest request) {
        AppleSocialMemberResponse appleSocialMember = appleOAuthUserProvider.getAppleMember(request.getToken());

        return generateTokenResponse(SocialType.APPLE, appleSocialMember.getEmail(), appleSocialMember.getSocialId());
    }

    public TokenResponse generateTokenResponse(SocialType socialType, String email, String socialId) {
        return memberRepository.findIdBySocialTypeAndSocialId(socialType, socialId)
                .map(memberId -> {
                    MemberEntity findMember = memberRepository.findById(memberId).orElseThrow(
                            () -> new PetsTableException(MEMBER_NOT_FOUND.getStatus(), MEMBER_NOT_FOUND.getMessage(), 404));
                    String accessToken = issueAccessToken(findMember);

                    if (!findMember.isRegisteredOAuthMember()) {
                        return new TokenResponse(accessToken, findMember.getEmail(), false, socialId);
                    }

                    return new TokenResponse(accessToken, findMember.getEmail(), true, socialId);

                }).orElseGet(() -> {
                    // 회원가입 되어있지 않은 경우 유저를 새로 저장하여 토큰 발급
                    // 만약 유저에 대한 정보가 불충분(이메일, socialId 뿐)하므로 새로운 Member 생성자를 생성
                    MemberEntity oauthMember = MemberEntity.builder()
                            .email(email)
                            .socialType(SocialType.APPLE)
                            .socialId(socialId)
                            .build();
                    MemberEntity savedMember = memberRepository.save(oauthMember);
                    String accessToken = issueAccessToken(savedMember);

                    return new TokenResponse(accessToken, email, false, socialId);
                });
    }

    private String issueAccessToken(final MemberEntity findMember) {
        return jwtTokenProvider.createAccessToken(findMember.getId());
    }
}
