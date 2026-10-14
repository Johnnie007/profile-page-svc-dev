package com.profile.svc.client.service;

import com.profile.svc.model.ClientModel;
import com.profile.svc.repository.interfaces.BookDetailsRepository;
import com.profile.svc.repository.interfaces.ProjectRepository;
import com.profile.svc.repository.interfaces.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private BookDetailsRepository bookDetailsRepository;

    public ClientModel gatherClientData() {

        return ClientModel.builder()
            .userDetails(userDetailsRepository.obtainDetails())
            .projects(projectRepository.findAllProjects())
            .books(bookDetailsRepository.getAllBooks())
            .build();
    }
}
