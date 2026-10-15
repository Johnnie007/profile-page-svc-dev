package com.profile.svc.repository.interfaces;


import com.profile.svc.model.Book;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookDetailsRepository {
    List<Book> findBooksBySeries(String name);
    Optional<Book> findBookById(UUID id);
    List<Book> getAllBooks();
}
