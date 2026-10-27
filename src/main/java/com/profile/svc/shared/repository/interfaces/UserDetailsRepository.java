package com.profile.svc.shared.repository.interfaces;

import com.profile.svc.shared.model.UserDetails;

import java.util.Optional;

public interface UserDetailsRepository {
    Optional<UserDetails> obtainDetails();
    void saveUserDetails(UserDetails userDetails);
}
