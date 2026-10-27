package com.profile.svc.c.v;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cloudinary")
public record CV (
    String cloudName,
    String key,
    String secret
) {}
