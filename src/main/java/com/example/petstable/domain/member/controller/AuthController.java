package com.example.petstable.domain.member.controller;

import com.example.petstable.domain.member.service.AuthService;
import com.example.petstable.global.auth.dto.request.AppleLoginRequest;
import com.example.petstable.global.auth.dto.response.AppleTokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "애플 OAuth 로그인")
    @PostMapping("/oauth/apple")
    public ResponseEntity<AppleTokenResponse> loginApple(@RequestBody @Valid AppleLoginRequest request) {
        AppleTokenResponse response = authService.appleOAuthLogin(request);
        return ResponseEntity.ok(response);
    }
}
