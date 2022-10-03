package com.example.demo.service;

import com.example.demo.domain.Book;
import com.example.demo.repository.BookRepository;
import com.example.demo.util.MailSender;
import com.example.demo.web.dto.BookRespDto;
import com.example.demo.web.dto.BookSaveRespDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

// 기능들이 트랜잭션을 잘 타는지 테스트
@ExtendWith(MockitoExtension.class) //가짜 메모리 환경 생성
public class BookServiceTest {

    @InjectMocks // BookService 환경에서 @Mock 사용
    private BookService bookService;

    @Mock // 가짜 메모리 환경에 띄움
    private BookRepository bookRepository;
    @Mock
    private MailSender mailSender;

    @Test
    public void 책등록하기_test(){
        // given
        BookSaveRespDto dto = new BookSaveRespDto();
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
    public void 책목록보기_test(){
        // given (파라미터로 들어올 데이터)

        // stub (가정)
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "junit", "정찬우"));
        books.add(new Book(2L, "spring", "가설"));

        when(bookRepository.findAll()).thenReturn(books);

        // when
        List<BookRespDto> bookRespDtoList = bookService.책목록보기();

        // then
        assertThat(bookRespDtoList.get(0).getTitle()).isEqualTo("junit");
        assertThat(bookRespDtoList.get(0).getAuthor()).isEqualTo("정찬우");
        assertThat(bookRespDtoList.get(1).getTitle()).isEqualTo("spring");
        assertThat(bookRespDtoList.get(1).getAuthor()).isEqualTo("가설");
    }

}