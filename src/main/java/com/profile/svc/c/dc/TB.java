package com.profile.svc.c.dc;

import com.datastax.astra.client.databases.Database;
import com.datastax.astra.client.tables.Table;
import com.profile.svc.c.v.DSCV;
import com.profile.svc.model.Book;
import com.profile.svc.model.UserDetails;
import com.profile.svc.model.Project;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TB {

    @Bean("projectsTbc")
    Table<Project> projectTbc(Database db, DSCV dscv) {
        return db.getTable(dscv.tbn().projects(), Project.class);
    }

    @Bean("bookDetailsTbc")
    Table<Book> bookDetailsTbc(Database db, DSCV dscv) {
        return db.getTable(dscv.tbn().book(), Book.class);
    }

    @Bean("userDetailsTbc")
    Table<UserDetails> userDetailsTbc(Database db, DSCV dscv) {
        return db.getTable(dscv.tbn().details(), UserDetails.class);
    }
}
