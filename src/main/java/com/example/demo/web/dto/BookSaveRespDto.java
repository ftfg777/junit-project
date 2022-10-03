package com.example.demo.web.dto;

import com.example.demo.domain.Book;
import lombok.Getter;
import lombok.Setter;

@Setter
public class BookSaveRespDto {
    private String title;
    private String author;

    public Book toEntity(){
        return Book.builder()
                .title(title)
                .author(author)
                .build();
    }
}


