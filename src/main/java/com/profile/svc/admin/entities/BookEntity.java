package com.profile.svc.admin.entities;

import com.profile.svc.shared.model.PublishData;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class BookEntity {
    private UUID id;
    private String seriesId;
    private String title;
    private String category;
    private String details;
    private String format;
    private BigDecimal price;
    private Integer inventory;
    private String printToOrder;
    private MultipartFile pdf;
    private String numOfPages;
    private PublishData publishData;
    private MultipartFile coverImage;
}
