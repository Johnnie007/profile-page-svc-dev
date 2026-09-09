package com.profile.svc.model;

import lombok.AllArgsConstructor;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
public class Book {
    UUID id;
    String title;
    String category;
    String details;
    String format;
    String price;
    Integer inventory;
    String printToOrder;
    String pdf;
    String numOfPages;
    String coverImage;

    static class Publish{
        boolean isPublished;
        Date PublishedDate;
    }
}
