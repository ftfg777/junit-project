package com.example.demo.repository;

import com.example.demo.domain.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
// DB 관련 테스트
// 컨트롤러 -> dto -> service -> entity 변환 -> repository -> DB
@DataJpaTest // DB 와 관련된 컴포넌트만 메모리에 띄움
public class BookRepositoryTest {

    @Autowired  // DI
    private BookRepository bookRepository;

    // 1. 책 등록
    @Test
    public void 책등록_test(){
        // given (데이터 준비)
        String title = "junit5";
        String author = "정찬우";
        Book book = Book.builder()
                .title(title)
                .author(author)
                .build();

        // when (테스트 실행)
        Book bookPS = bookRepository.save(book);

        // then (검증)
        assertThat(bookPS.getAuthor()).isEqualTo(author);
        assertThat(bookPS.getTitle()).isEqualTo(title);

    }

    // 2. 책 목록보기

    // 3. 책 한건보기

    // 4. 책 수정

    // 5. 책 삭제


}