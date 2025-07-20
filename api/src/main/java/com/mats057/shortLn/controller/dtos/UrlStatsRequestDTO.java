package com.mats057.shortLn.controller.dtos;

import java.util.Date;

public record UrlStatsRequestDTO(Long id, String url, String shortCode, Date createdAt, Date updatedAt, Long acessCount) {

}
