package com.app.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private String sl;
    private Long id;
    private String title;
    private String description;
    private String authors;
    private String image;
    private String categories;
    private Long ratingsCount;
    private String previewLink;
    private String infoLink;
    private String publishedDate;
}
