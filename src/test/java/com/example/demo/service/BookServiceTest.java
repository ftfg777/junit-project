package com.example.demo.service;

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
        assertThat(dto.getAuthor()).isEqualTo(bookRespDto.getAuthor());
        assertThat(dto.getTitle()).isEqualTo(bookRespDto.getTitle());
    }

}