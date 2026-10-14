package com.profile.svc.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Optional;

@Builder
@Getter
public class ClientModel {
    Optional<UserDetails> userDetails;
    List<Project> projects;
    List<Book> books;
}
