package com.example.demo.repository;

import com.example.demo.domain.Book;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


// DB 관련 테스트
// 컨트롤러 -> dto -> service -> entity 변환 -> repository -> DB
@DataJpaTest // DB 와 관련된 컴포넌트만 메모리에 띄움
@TestMethodOrder(MethodOrderer.MethodName.class)
public class BookRepositoryTest {

    @Autowired  // DI
    private BookRepository bookRepository;

    //@BeforeAll  테스트 시작 전 한 번만 실행
    @BeforeEach // 각 테스트 시작 전 한 번씩 실행
    public void 데이터준비(){
        String title = "junit5";
        String author = "정찬우";
        Book book = Book.builder()
                .title(title)
                .author(author)
                .build();
        Book bookPS = bookRepository.save(book);
    }

    // 1. 책 등록
    @Test
    public void A_책등록_test(){
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
        // 트랜잭션 종료 (저장된 데이터 초기화)
    }

    // 2. 책 목록보기
    @Test
    public void B_책목록보기_test(){
        // given (데이터 준비)
        String title = "junit5";
        String author = "정찬우";

        // when (테스트 실행)
        List<Book> booksPS = bookRepository.findAll();

        // then (검증)
        assertThat(booksPS.get(0).getTitle()).isEqualTo(title);
        assertThat(booksPS.get(0).getAuthor()).isEqualTo(author);

    }

    // 3. 책 한건보기
    @Sql("classpath:db/tableInit.sql")
    @Test
    public void C_책한건보기_test(){
        // given (데이터 준비)
        String title = "junit5";
        String author = "정찬우";

        // when (테스트 실행)
        Book bookPS = bookRepository.findById(1L).get();

        // then (검증)
        assertThat(bookPS.getAuthor()).isEqualTo(author);
        assertThat(bookPS.getTitle()).isEqualTo(title);

    }

    // 4. 책 삭제
    @Sql("classpath:db/tableInit.sql")
    @Test
    public void D_책삭제_test(){
        // given (데이터 준비)
        Long id = 1L;

        // when (테스트 실행)
        bookRepository.deleteById(id);

        // then (검증)
        assertFalse(bookRepository.findById(id).isPresent());
    }

    // 5. 책 수정


}