package com.profile.svc.service;

import com.profile.svc.model.Book;
import com.profile.svc.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BookService {
    private BookRepository bookRepository;

    public List<Book> retrieveBooksAllBooks(){
        try {
            return bookRepository.retrieveLatestBooks();
        } catch (Exception ex){
            throw new RuntimeException("Failed to load: "+ ex.getMessage());
        }
    }
}
