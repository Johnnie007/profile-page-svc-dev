package com.profile.svc.model;


import com.datastax.astra.client.tables.mapping.Column;
import com.datastax.astra.client.tables.mapping.EntityTable;
import com.datastax.astra.client.tables.mapping.PartitionBy;
import lombok.Data;
import lombok.NoArgsConstructor;


@EntityTable
@Data
@NoArgsConstructor
public class UserDetails {
    @PartitionBy(1)
    @Column( name="project_id")
    String id;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "last_name")
    String lastName;

    @Column(name = "details")
    String mainText;

    @Column(name = "additional_info")
    String subText;

    @Column( name="profile_image")
    Metadata profileImage;
}
