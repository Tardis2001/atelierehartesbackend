package com.renata.atelierehartesbackend.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SigninResponseDto {
    private String status;
    private String token;
}
