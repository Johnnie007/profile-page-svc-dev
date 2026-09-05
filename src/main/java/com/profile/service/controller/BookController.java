package com.profile.service.controller;

import com.profile.service.model.Book;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/book")
public class BookController {

    @GetMapping("/{series}")
    public ResponseEntity<List<Book>> getBooksBySeries(){
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(){
        return null;
    }

    @GetMapping("/recent")
    public ResponseEntity<List<Book>> getBooks(){
        return null;
    }




}
