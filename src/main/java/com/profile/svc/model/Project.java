package com.profile.svc.model;

import com.datastax.astra.client.tables.mapping.Column;
import com.datastax.astra.client.tables.mapping.EntityTable;
import com.datastax.astra.client.tables.mapping.PartitionBy;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@EntityTable
@Data
@NoArgsConstructor
public class Project {
    @PartitionBy(1)
    @Column( name="project_id")
    UUID id;

    @Column( name="first_name")
    String name;

    @Column( name="category")
    List<String> category;

    @Column( name="deployment_date")
    Instant deployedDate;

    @Column( name="link")
    String externalLink;

    @Column( name="stack")
    List<String> techStack;

    @Column( name="asset_metadata_type")
    Metadata imageMetaData;
}
