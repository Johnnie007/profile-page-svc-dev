package com.profile.svc.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
public class Book {
    @Id
    String id;
    @Column(name="title")
    String title;
    @Column(name="category")
    String category;
    @Column(name="details")
    String details;
    @Column(name="format")
    String format;
    @Column(name="price")
    String price;
    @Column(name="format")
    Integer inventory;
    @Column(name="print_to_order_link")
    String printToOrder;
    @Column(name="pdf_version")
    String pdf;
    @Column(name="pages")
    String numOfPages;
    @Column(name="cover")
    String coverImage;

    @Embeddable
    @Data
    static class Publish{
        @Column(name="is_published")
        boolean isPublished;
        @Column(name="published_date")
        Date publishedDate;
    }
}
