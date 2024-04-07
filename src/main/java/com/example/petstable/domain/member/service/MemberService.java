package com.example.petstable.domain.member.service;

import com.example.petstable.domain.member.dto.request.OAuthMemberSignUpRequest;
import com.example.petstable.domain.member.dto.response.OAuthMemberSignUpResponse;
import com.example.petstable.domain.member.entity.MemberEntity;
import com.example.petstable.domain.member.entity.SocialType;
import com.example.petstable.domain.member.repository.MemberRepository;
import com.example.petstable.global.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.petstable.global.exception.message.MemberMessage.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public OAuthMemberSignUpResponse signUpByOAuthMember(OAuthMemberSignUpRequest request) {
        SocialType socialType = SocialType.from(request.getSocialType());
        MemberEntity findMember = memberRepository.findBySocialTypeAndSocialId(socialType, request.getSocialId())
                .orElseThrow(() -> new ApiException(MEMBER_NOT_FOUND));

        findMember.registerOAuthMember(request.getEmail(), request.getNickname());
        return new OAuthMemberSignUpResponse(findMember.getId());
    }
}
