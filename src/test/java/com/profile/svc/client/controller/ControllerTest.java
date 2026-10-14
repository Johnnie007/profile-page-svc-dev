package com.profile.svc.client.controller;

import com.profile.svc.client.service.ClientService;
import com.profile.svc.model.ClientModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ControllerTest {

    @Test
    void getProjectsReturnsClientDetails() {
        ClientService service = mock(ClientService.class);
        ClientModel clientModel = ClientModel.builder().build();
        when(service.gatherClientData()).thenReturn(clientModel);
        ClientController controller = new ClientController(service);

        var response = controller.getProjects();

        assertEquals(200, response.getStatusCode().value());
        assertSame(clientModel, response.getBody());
        verify(service).gatherClientData();
    }
}
