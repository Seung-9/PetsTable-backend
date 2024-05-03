package com.example.petstable.domain.pet.controller;

import com.example.petstable.domain.pet.dto.request.PetInfoRequest;
import com.example.petstable.domain.pet.dto.response.PetInfoResponse;
import com.example.petstable.domain.pet.service.PetService;
import com.example.petstable.global.auth.ios.auth.LoginUserId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "반려동물 관련 컨트롤러")
@RestController
@RequiredArgsConstructor
@RequestMapping("/{memberId}/pets")
public class PetController {

    private final PetService petService;

    @Operation(summary = "반려동물 추가")
    @PostMapping()
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<PetInfoResponse> addPet(@RequestBody @Valid PetInfoRequest pet, @LoginUserId Long memberId) {
        PetInfoResponse petInfoResponse = petService.registerPet(memberId, pet);
        return ResponseEntity.ok(petInfoResponse);
    }

}
