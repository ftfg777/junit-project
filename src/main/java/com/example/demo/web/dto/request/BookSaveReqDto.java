package com.example.demo.web.dto.request;

import com.example.demo.domain.Book;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
public class BookSaveReqDto {
    @Size(min = 1, max = 50)
    @NotBlank // null, ""검사
    private String title;
    @Size(min = 2, max = 20)
    @NotBlank
    private String author;

    @Builder
    public BookSaveReqDto(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public Book toEntity(){
        return Book.builder()
                .title(title)
                .author(author)
                .build();
    }
}


