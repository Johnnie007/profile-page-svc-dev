package com.profile.svc.shared.model;

import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ClientModel {
    Optional<UserDetails> userDetails;
    List<Project> projects;
    List<Book> books;
}
