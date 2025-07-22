package com.mats057.shortLn.business.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mats057.shortLn.business.models.Url;
import com.mats057.shortLn.controller.dtos.UrlRequestDTO;
import com.mats057.shortLn.controller.dtos.UrlCreateDTO;
import com.mats057.shortLn.controller.dtos.UrlStatsRequestDTO;
import com.mats057.shortLn.infrastructure.exceptions.InvalidUrlException;
import com.mats057.shortLn.infrastructure.exceptions.URLNotFoundException;
import com.mats057.shortLn.infrastructure.repositories.UrlRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShorteningService {

    @Autowired
    UrlRepo urlRepo;

    @Autowired
    SequenceGeneratorService sequenceGeneratorService;

    private static final String BASE62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public String encodeBase62(long num) {
        log.info("Base62 encoding started");
        StringBuilder sb = new StringBuilder();
        while (num > 0) {
            sb.append(BASE62.charAt((int) (num % 62)));
            num /= 62;
        }
        log.info("Base62 encoding finished");
        return sb.reverse().toString();
    }

    public UrlRequestDTO createUrl(UrlCreateDTO urlDto) {
        String url = urlDto.url();
        log.info("Creation of the URL:" + url + " started");
        if (!url.matches("^https?://.*")) {
            throw new InvalidUrlException("Invalid URL format: " + url);
        }

        try {
            Url urlModelExistent = urlRepo.findByUrl(url);
            if (urlModelExistent != null) {
                log.info("URL" + url + " already found in the database");
                return new UrlRequestDTO(urlModelExistent.getId(), urlModelExistent.getUrl(), urlModelExistent.getShortCode(),
                    urlModelExistent.getCreatedAt(),
                    urlModelExistent.getUpdatedAt());
            }
            long id = sequenceGeneratorService.generateSequence(Url.SEQUENCE_NAME);
            String shortCode = encodeBase62(id);
            Url urlModel = new Url(
                    id,
                    url,
                    shortCode,
                    new Date(System.currentTimeMillis()),
                    new Date(System.currentTimeMillis()),
                    0L);

            urlRepo.save(urlModel);
            log.info("Creation of the URL:" + url + " finished successfully: "+ urlModel.toString());
            return new UrlRequestDTO(urlModel.getId(), urlModel.getUrl(), urlModel.getShortCode(),
                    urlModel.getCreatedAt(),
                    urlModel.getUpdatedAt());
        } catch (Exception e) {
            log.error("Creation of the URL:" + url + " had an unexpected error" + e);
           throw new RuntimeException("Unexpected error creating the URL", e);
        }
    }

    public UrlRequestDTO retrieveUrl(String shortCode) {
        log.info("Retriving the URL with the shortCode:" + shortCode);
        try {
            Url urlModel = urlRepo.findByShortCode(shortCode);
            if (urlModel == null) {
                log.error("Short code" + shortCode + " not found in the database");
                throw new URLNotFoundException("Short code not found: " + shortCode);
            }
            urlModel.setAcessCount(urlModel.getAcessCount() + 1);
            urlRepo.save(urlModel);
            log.info("Information retrieved successfully:" + urlModel.toString());
            return new UrlRequestDTO(urlModel.getId(), urlModel.getUrl(), urlModel.getShortCode(),
                    urlModel.getCreatedAt(),
                    urlModel.getUpdatedAt());
        } catch (Exception e) {
            log.error("Error retrieving URL for short code: " + shortCode + " " + e);
            throw new InvalidUrlException("Error retrieving URL for short code: " + shortCode);
        }
    }

    public UrlRequestDTO updateUrl(String shortCode, UrlCreateDTO urlDto) {
        String url = urlDto.url();
        log.info("Updating the shortCode:" + shortCode + " with the URL: " +url);

        if (!url.matches("^https?://.*")) {
            throw new InvalidUrlException("Invalid URL format: " + url);
        }

        try {
            Url urlModel = urlRepo.findByShortCode(shortCode);
            if (urlModel == null) {
                log.error("Short code" + shortCode + " not found in the database");
                throw new URLNotFoundException("Short code not found: " + shortCode);
            }

            urlModel.setUrl(url);
            urlModel.setUpdatedAt(new Date(System.currentTimeMillis()));

            urlRepo.save(urlModel);
            log.info("Information updated successfully:" + urlModel.toString());
            return new UrlRequestDTO(urlModel.getId(), urlModel.getUrl(), urlModel.getShortCode(),
                    urlModel.getCreatedAt(),
                    urlModel.getUpdatedAt());
        } catch (Exception e) {
            log.error("Error updating URL for short code: " + shortCode);
            throw new InvalidUrlException("Error updating URL for short code: " + shortCode);
        }
    }

    public void deleteUrl(String shortCode) {
        log.info("Deleting the shortCode:" + shortCode);

        try {
            Url urlModel = urlRepo.findByShortCode(shortCode);
            if (urlModel == null) {
                log.error("Short code" + shortCode + " not found in the database");
                throw new URLNotFoundException("Short code not found: " + shortCode);
            }
            urlRepo.deleteByShortCode(shortCode);
            log.info("ShortCode deleted Successfully:" + shortCode);
        } catch (Exception e) {
            log.error("Error deleting short code: " + shortCode);
            throw new InvalidUrlException("Error deleting URL for short code: " + shortCode);
        }
    }

    public UrlStatsRequestDTO retrieveUrlStatistics(String shortCode) {
        log.info("Retriving the URL Stats with the shortCode:" + shortCode);

        try {
            Url urlModel = urlRepo.findByShortCode(shortCode);
            if (urlModel == null) {
                log.error("Short code" + shortCode + " not found in the database");
                throw new URLNotFoundException("Short code not found: " + shortCode);
            }
            log.info("Statistics information retrieved successfully:" + urlModel.toString());
            return new UrlStatsRequestDTO(urlModel.getId(), urlModel.getUrl(), urlModel.getShortCode(),
                    urlModel.getCreatedAt(),
                    urlModel.getUpdatedAt(), urlModel.getAcessCount() + 1);
        } catch (Exception e) {
            log.error("Error retrieving URL Stats for short code: " + shortCode);
            throw new InvalidUrlException("Error retrieving URL for short code: " + shortCode);
        }
    }

}
