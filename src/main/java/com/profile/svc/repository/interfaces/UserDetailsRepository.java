package com.profile.svc.repository.interfaces;

import com.profile.svc.model.UserDetails;

import java.util.Optional;

public interface UserDetailsRepository {
    Optional<UserDetails> obtainDetails();
}
