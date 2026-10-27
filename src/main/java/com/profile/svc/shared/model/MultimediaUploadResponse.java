package com.profile.svc.shared.model;

public record MultimediaUploadResponse(
        String id,
        String url,
        String fileFormat,
        long fileSize
) {
}
