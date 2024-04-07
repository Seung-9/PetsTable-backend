package com.example.petstable.domain.member.entity;

import com.example.petstable.global.exception.ApiException;
import jakarta.persistence.*;
import lombok.*;

import java.util.regex.Pattern;

import static com.example.petstable.global.exception.message.MemberMessage.INVALID_NICKNAME;

@Entity
@AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@Table(name = "member")
public class MemberEntity extends BaseTimeEntity {

    // 알파벳(대소문자), 한글 자음, 한글 모음으로 이루어져 있으며 2자에서 6자 사이
    private static final Pattern NICKNAME_REGEX = Pattern.compile("^[a-zA-Zㄱ-ㅎㅏ-ㅣ가-힣]{2,6}$");

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String email; // 이메일
    private String nickName; // 닉네임

    private String imageUrl;

    @Enumerated(value = EnumType.STRING)
    private SocialType socialType; // NAVER, KAKAO, APPLE
    private String socialId;

    @Enumerated(value = EnumType.STRING)
    private RoleType roleType; // ADMIN, MEMBER

    @Enumerated(EnumType.STRING)
    private Status status;

    // 닉네임 검증
    private void validateNickname(String nickname) {
        if (!NICKNAME_REGEX.matcher(nickname).matches()) {
            throw new ApiException(INVALID_NICKNAME);
        }
    }

    public void registerOAuthMember(String email, String nickname) {
        validateNickname(nickname);
        this.nickName = nickname;
        if (email != null) {
            this.email = email;
        }
    }

    public boolean isRegisteredOAuthMember() {
        return nickName != null;
    }
}
