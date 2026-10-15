package com.profile.svc.c.v;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "astra")
public record DSCV (
    String token,
    String endpoint,
    String keyspace,
    Tbn tbn
) {
    public record Tbn(
            String projects,
            String book,
            String details
    ){}
}
