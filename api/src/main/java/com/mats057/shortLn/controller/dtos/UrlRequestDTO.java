package com.mats057.shortLn.controller.dtos;

import java.util.Date;

public record UrlRequestDTO(Long id, String url, String shortCode, Date createdAt, Date updatedAt) {

}
