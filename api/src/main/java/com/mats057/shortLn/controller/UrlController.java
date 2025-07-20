package com.mats057.shortLn.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mats057.shortLn.business.services.ShorteningService;
import com.mats057.shortLn.controller.dtos.UrlCreateDTO;
import com.mats057.shortLn.controller.dtos.UrlRequestDTO;
import com.mats057.shortLn.controller.dtos.UrlStatsRequestDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RequiredArgsConstructor
@RestController
@RequestMapping("/shorten")
public class UrlController {

    @Value("${default_url}")
    private String defaultUrl;

    private final ShorteningService shorteningService;

    @PostMapping
    @Operation(
        summary = "Create a new shortened URL",
        description = "Endpoint responsible for creating a new URL shortener on the database",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "URL to be shortened",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    value = "{\"url\": \"https://www.example.com/some/long/url\"}"
                ),
                mediaType = "application/json"
            )
        ),
        responses = {
            @ApiResponse(responseCode = "201", description = "URL successfully shortened"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
        }
    )
    public ResponseEntity<UrlRequestDTO> createURL(@RequestBody UrlCreateDTO urlDto) {
        UrlRequestDTO createdUrl = shorteningService.createUrl(urlDto);
        return ResponseEntity
                .created(java.net.URI.create(defaultUrl + createdUrl.shortCode()))
                .body(createdUrl);
    }

    @GetMapping("/{shortCode}")
    @Operation(summary = "Get the shortened URL", description = "Endpoint responsible for retrieving an existent URL shortener on the database", responses = {
            @ApiResponse(responseCode = "200", description = "URL successfully found"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Short URL Not Found on the database")
    })
    public ResponseEntity<UrlRequestDTO> getOriginalURL(@PathVariable String shortCode) {
        return ResponseEntity.ok().body(shorteningService.retrieveUrl(shortCode));
    }

    @PutMapping("/{shortCode}")
    @Operation(summary = "Update the shortened URL", 
        description = "Endpoint responsible for updating an existent URL shortener on the database", 
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "URL to be updated",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    value = "{\"url\": \"https://www.example.com/some/updated/url\"}"
                ),
                mediaType = "application/json"
            )
        ),
        responses = {
            @ApiResponse(responseCode = "200", description = "URL successfully updated"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Short URL Not Found on the database")
    })
    public ResponseEntity<UrlRequestDTO> updateURL(@PathVariable String shortCode, @RequestBody UrlCreateDTO url) {
        return ResponseEntity.ok().body(shorteningService.updateUrl(shortCode, url));
    }

    @DeleteMapping("/{shortCode}")
    @Operation(summary = "Delete the shortened URL", description = "Endpoint responsible for deleting an URL shortener on the database", responses = {
            @ApiResponse(responseCode = "204", description = "Shortener successfully deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Short URL Not Found on the database")
    })
    public ResponseEntity<Void> deleteURL(@PathVariable String shortCode) {
        shorteningService.deleteUrl(shortCode);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{shortCode}/stats")
    @Operation(summary = "Get the shortened URL Statistics", description = "Endpoint responsible for retrieving Statistics infos of an existent URL shortener on the database", responses = {
            @ApiResponse(responseCode = "200", description = "URL Stats successfully found"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Short URL Not Found on the database")
    })
    public ResponseEntity<UrlStatsRequestDTO> getURLStats(@PathVariable String shortCode) {
        return ResponseEntity.ok().body(shorteningService.retrieveUrlStatistics(shortCode));
    }

}
