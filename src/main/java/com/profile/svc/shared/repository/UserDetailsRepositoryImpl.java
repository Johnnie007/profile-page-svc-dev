package com.profile.svc.shared.repository;

import com.datastax.astra.client.tables.Table;
import com.profile.svc.shared.model.UserDetails;
import com.profile.svc.shared.repository.interfaces.UserDetailsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Slf4j
@Repository
public class UserDetailsRepositoryImpl implements UserDetailsRepository {
    private final Table<UserDetails> table;

    public UserDetailsRepositoryImpl(@Qualifier("userDetailsTbc") Table<UserDetails> detailsTbc) {
        this.table = detailsTbc;
    }
    @Override
    public Optional<UserDetails> obtainDetails() {
        try{
            return table.findAll().stream().findFirst();
        } catch (Exception e) {
            log.error("Error retrieving user details: {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }

    }

    @Override
    public void saveUserDetails(UserDetails userDetails) {
        try {
            table.insertOne(userDetails);
        } catch (Exception e) {
            log.error("Error saving User Details: {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
