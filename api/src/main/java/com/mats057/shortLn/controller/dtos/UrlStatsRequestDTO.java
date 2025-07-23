package com.mats057.shortLn.controller.dtos;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UrlStatsRequestDTO(@NotNull Long id, @NotBlank String url, @NotBlank String shortCode,
        @NotNull Date createdAt, @NotNull Date updatedAt, @NotNull Long accessCount) {

}
