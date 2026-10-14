package com.profile.svc.c.dc;

import com.datastax.astra.client.DataAPIClient;
import com.datastax.astra.client.databases.Database;
import com.profile.svc.c.v.DSCV;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DSC {

    @Bean
    DataAPIClient dataAPIClient(DSCV dscv) {
        return new DataAPIClient(dscv.token());
    }

    @Bean
    Database database(DataAPIClient client, DSCV dscv){
        return client.getDatabase(dscv.endpoint(), dscv.keyspace());
    }
}
