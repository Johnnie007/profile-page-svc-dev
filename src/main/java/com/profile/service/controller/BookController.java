package com.profile.service.controller;

import com.profile.service.model.Book;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/book")
public class BookController {

    @GetMapping("/{series}")
    public ResponseEntity<List<Book>> getBooksBySeries(@PathVariable("series") String seriesName){
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("series") UUID seriesName){
        return null;
    }

    @GetMapping("/recent")
    public ResponseEntity<List<Book>> getBooks(){
        return null;
    }




}
