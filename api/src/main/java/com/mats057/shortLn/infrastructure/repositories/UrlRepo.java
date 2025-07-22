package com.mats057.shortLn.infrastructure.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mats057.shortLn.business.models.Url;

public interface UrlRepo extends MongoRepository<Url, Long> {

    Url findByShortCode(String shortCode);
    Url findByUrl(String url);
    void deleteByShortCode(String shortCode);

}
