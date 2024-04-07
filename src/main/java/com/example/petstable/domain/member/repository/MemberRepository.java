package com.example.petstable.domain.member.repository;//package com.example.animalrecipe.domain.member.repository;

import com.example.petstable.domain.member.entity.MemberEntity;
import com.example.petstable.domain.member.entity.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    Optional<MemberEntity> findByEmail(String email);

    Optional<MemberEntity> findByNickName(String nickName);

    @Query("select m.id from MemberEntity m where m.socialType = :platform and m.socialId = :socialId")
    Optional<MemberEntity> findIdBySocialTypeAndSocialId(SocialType socialType, String socialId);
}
