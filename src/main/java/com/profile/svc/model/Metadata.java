package com.profile.svc.model;
import com.datastax.astra.client.tables.mapping.Column;
import lombok.Data;

@Data
public class Metadata {

    @Column( name="image_public_id" )
    String id;

    @Column( name="secure_url" )
    String url;

    @Column( name="file_format" )
    String fileFormat;

    @Column( name="file_size_bytes" )
    Long fileSize;
}
