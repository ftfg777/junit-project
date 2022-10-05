package com.example.demo.service;

import com.example.demo.domain.Book;
import com.example.demo.repository.BookRepository;
import com.example.demo.util.MailSender;
import com.example.demo.web.dto.response.BookListRespDto;
import com.example.demo.web.dto.response.BookRespDto;
import com.example.demo.web.dto.request.BookSaveReqDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

// 기능들이 트랜잭션을 잘 타는지 테스트
@ActiveProfiles("dev")
@ExtendWith(MockitoExtension.class) //가짜 메모리 환경 생성
public class BookServiceTest {

    @InjectMocks // BookService 환경에서 @Mock 사용
    private BookService bookService;

    @Mock // 가짜 메모리 환경에 띄움
    private BookRepository bookRepository;
    @Mock
    private MailSender mailSender;

    @Test
    public void 책등록하기(){
        // given
        BookSaveReqDto dto = new BookSaveReqDto();
        dto.setAuthor("정찬우");
        dto.setTitle("junit");

        // stub (가정)
        when(bookRepository.save(any())).thenReturn(dto.toEntity());
        when(mailSender.send()).thenReturn(true);

        // when
        BookRespDto bookRespDto = bookService.책등록하기(dto);

        // then
        assertThat(bookRespDto.getAuthor()).isEqualTo(dto.getAuthor());
        assertThat(bookRespDto.getTitle()).isEqualTo(dto.getTitle());
    }

    @Test
    public void 책목록보기(){
        // given (파라미터로 들어올 데이터)

        // stub (가정)
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "junit", "정찬우"));
        books.add(new Book(2L, "spring", "석지영"));

        when(bookRepository.findAll()).thenReturn(books);

        // when
        BookListRespDto bookListRespDto = bookService.책목록보기();

        // then
        assertThat(bookListRespDto.getItems().get(0).getTitle()).isEqualTo("junit");
        assertThat(bookListRespDto.getItems().get(0).getAuthor()).isEqualTo("정찬우");
        assertThat(bookListRespDto.getItems().get(1).getTitle()).isEqualTo("spring");
        assertThat(bookListRespDto.getItems().get(1).getAuthor()).isEqualTo("석지영");
    }

    @Test
    public void 책한건보기(){
        // given
        Long id = 1L;

        // stub
        Book book = new Book(1L, "테스트", "정찬우");
        Optional<Book> bookOP = Optional.of(book);
        when(bookRepository.findById(id)).thenReturn(bookOP);

        // when
        BookRespDto bookRespDto = bookService.책한건보기(id);

        // then
        assertThat(bookRespDto.getAuthor()).isEqualTo(bookOP.get().getAuthor());
        assertThat(bookRespDto.getTitle()).isEqualTo(bookOP.get().getTitle());
    }

    @Test
    public void 책수정하기(){
        // given
        Long id = 1L;
        BookSaveReqDto dto = new BookSaveReqDto();
        dto.setTitle("수정할거야");
        dto.setAuthor("김찬우");

        // stub
        Book book = new Book(1L, "테스트", "정찬우");
        Optional<Book> bookOP = Optional.of(book);

        when(bookRepository.findById(id)).thenReturn(bookOP);

        // when
        BookRespDto bookRespDto = bookService.책수정하기(id, dto);

        // then
        assertThat(bookRespDto.getTitle()).isEqualTo(dto.getTitle());
        assertThat(bookRespDto.getAuthor()).isEqualTo(dto.getAuthor());

    }


}