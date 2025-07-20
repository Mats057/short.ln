package com.mats057.shortLn.business.models;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "urls")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Url {

    @Transient
    public static final String SEQUENCE_NAME = "urls_sequence";

    @Id
    private Long id;
    private String url;
    private String shortCode;
    private Date createdAt;
    private Date updatedAt;
    private Long acessCount;
}
