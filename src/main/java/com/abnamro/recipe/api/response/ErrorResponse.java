package com.abnamro.recipe.api.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponse {
    private LocalDateTime timestamp;
    private String code;
    private String message;
    private Object details;
}
