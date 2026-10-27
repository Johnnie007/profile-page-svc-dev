package com.profile.svc.c.dc;

import com.profile.svc.shared.model.Book;
import com.profile.svc.shared.model.ClientModel;
import com.profile.svc.shared.model.Project;
import com.profile.svc.shared.model.UserDetails;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class CacheSerializerTest {

    @Test
    void clientModelCanBeSerializedAndDeserializedWithItsConcreteType() {
        ClientModel original = ClientModel.builder()
                .userDetails(Optional.of(new UserDetails()))
                .projects(List.of(new Project()))
                .books(List.of(new Book()))
                .build();
        JacksonJsonRedisSerializer<ClientModel> serializer =
                new JacksonJsonRedisSerializer<>(ClientModel.class);

        ClientModel restored = serializer.deserialize(serializer.serialize(original));

        assertInstanceOf(ClientModel.class, restored);
        assertEquals(1, restored.getProjects().size());
        assertEquals(1, restored.getBooks().size());
        assertEquals(Optional.of(new UserDetails()), restored.getUserDetails());
    }
}
