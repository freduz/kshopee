package com.daniel.kshopee.payload;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class JwtAuthResponse {
    private String accessToken;
    private static final String tokenType = "Bearer";
}
