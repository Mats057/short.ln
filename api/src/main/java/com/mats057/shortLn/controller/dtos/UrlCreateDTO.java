package com.mats057.shortLn.controller.dtos;

import jakarta.validation.constraints.NotBlank;

public record UrlCreateDTO(@NotBlank(message = "URL cannot be blank") String url) {
}
