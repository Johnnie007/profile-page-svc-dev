package com.profile.svc.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@Entity
public class Series {
    @Id
    String id;
    @Column(name="seriesName")
    String seriesName;
    @ElementCollection
    List<Book> book;

}
