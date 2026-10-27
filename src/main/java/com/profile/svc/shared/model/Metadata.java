package com.profile.svc.shared.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Metadata {

    @JsonProperty("image_public_id")
    String id;

    @JsonProperty("secure_url")
    String url;

    @JsonProperty("file_format")
    String fileFormat;

    @JsonProperty("file_size_bytes")
    Long fileSize;

    public static Metadata from(MultimediaUploadResponse response) {
        Metadata metadata = new Metadata();
        metadata.setId(response.id());
        metadata.setUrl(response.url());
        metadata.setFileFormat(response.fileFormat());
        metadata.setFileSize(response.fileSize());
        return metadata;
    }
}
