package com.profile.svc.shared.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.Instant;
@Data
public class PublishData {
    @JsonProperty("published")
    boolean isPublished;
    @JsonProperty("published_date")
    Instant publishedDate;
}
