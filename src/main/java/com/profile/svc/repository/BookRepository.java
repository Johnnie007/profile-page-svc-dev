package com.profile.svc.repository;


import com.profile.svc.model.Book;
import com.profile.svc.model.Series;

import java.util.List;

public interface BookRepository {
    List<Book> findBooksBySeries(String name);
    List<Book> retrieveLatestBooks();
    Book findBookById(String id);
}
