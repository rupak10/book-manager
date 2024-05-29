package com.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "authors", nullable = false)
    private String authors;

    @Column(name = "categories", nullable = false)
    private String categories;

    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "ratings_count", nullable = false)
    private Long ratingsCount;

    @Column(name = "preview_link", nullable = false)
    private String previewLink;

    @Column(name = "info_link", nullable = false)
    private String infoLink;

    @Column(name = "published_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date publishedDate;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_at")
    private Timestamp updatedAt;
}
