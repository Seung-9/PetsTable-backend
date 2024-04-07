package com.example.petstable.domain.member.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoleType {

    ADMIN("ROLE_ADMIN"),
    MEMBER("ROLE_USER");

    private final String value;
}
