package com.profile.svc.shared.model;


import com.datastax.astra.client.tables.mapping.Column;
import com.datastax.astra.client.tables.mapping.PartitionBy;
import com.datastax.astra.client.tables.mapping.PartitionSort;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;


@Data
public class Book {
    @PartitionBy(1)
    @Column(name="series_id")
    String seriesId;
    @PartitionSort(position = 1)
    @Column(name="book_id")
    UUID id;
    @Column(name="title")
    String title;
    @Column(name="category")
    String category;
    @Column(name="details")
    String details;
    @Column(name="format")
    String format;
    @Column(name="price")
    BigDecimal price;
    @Column(name="inventory")
    Integer inventory;
    @Column(name="print_to_order_link")
    String printToOrder;
    @Column(name="book_pdf_version")
    Metadata pdf;
    @Column(name="pages")
    String numOfPages;
    @Column(name="cover_image_metadata")
    Metadata coverImage;
    @Column(name="publish_details")
    PublishData publishData;
}
