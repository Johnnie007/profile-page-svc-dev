package com.profile.svc.model;

import com.datastax.astra.client.tables.mapping.Column;
import lombok.Data;

import java.time.Instant;
import java.util.Date;
@Data
public class PublishData {
    @Column(name="is_published")
    boolean isPublished;
    @Column(name="published_date")
    Instant publishedDate;
}
