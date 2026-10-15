package com.profile.svc.repository;

import com.datastax.astra.client.core.query.Filters;
import com.datastax.astra.client.tables.Table;
import com.profile.svc.model.Book;
import com.profile.svc.repository.interfaces.BookDetailsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Repository
public class BookDetailsRepositoryImpl implements BookDetailsRepository {

    private final Table<Book> collection;

    public BookDetailsRepositoryImpl(@Qualifier("bookDetailsTbc") Table<Book> booksTbc) {
        this.collection = booksTbc;
    }
    @Override
    public List<Book> findBooksBySeries(String seriesId) {
        try {
            return collection.find(Filters.eq("series_id", seriesId)).toList();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Book> findBookById(UUID id) {
        try {
           return collection.findOne(Filters.eq("book_id", id));
        } catch (Exception e) {
            log.error("Error retrieving book by ID: {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Book> getAllBooks() {
        try {
            return collection.findAll().toList();
        } catch (Exception e) {
            log.error("Error retrieving book by ID: {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }

    }
}
