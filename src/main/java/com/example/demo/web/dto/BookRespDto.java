package com.example.demo.web.dto;

import com.example.demo.domain.Book;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class BookRespDto {

    private Long id;
    private String title;
    private String author;

    public BookRespDto toDto(Book book){
        this.id = book.getId();
        this.author = book.getAuthor();
        this.title = book.getTitle();
        return this;
    }
}
