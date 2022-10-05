package com.example.demo.web;

import com.example.demo.domain.Book;
import com.example.demo.repository.BookRepository;
import com.example.demo.service.BookService;
import com.example.demo.web.dto.request.BookSaveReqDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.*;

// 클라이언트와 테스트
// 통합 테스트 (c, s, r)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class BookApiControllerTest {


    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private TestRestTemplate testRestTemplate;

    private static ObjectMapper objectMapper;
    private static HttpHeaders headers;
    @BeforeAll
    public static void init(){
        objectMapper = new ObjectMapper();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }
    @BeforeEach
    public void 데이터준비(){
        String title = "junit5";
        String author = "정찬우";
        Book book = Book.builder()
                .title(title)
                .author(author)
                .build();
        Book bookPS = bookRepository.save(book);
    }

    @Test
    public void saveBook_test() throws Exception {
        // given
        BookSaveReqDto bookSaveReqDto = new BookSaveReqDto();
        bookSaveReqDto.setAuthor("컨트롤러테스트");
        bookSaveReqDto.setTitle("컨트롤러테스트");

        String body = objectMapper.writeValueAsString(bookSaveReqDto);

        // when
        HttpEntity<String> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = testRestTemplate.exchange("/api/v1/book", HttpMethod.POST, request, String.class);

        // then
        DocumentContext documentContext = JsonPath.parse(response.getBody());
        String title = documentContext.read("$.body.title");
        String author = documentContext.read("$.body.author");

        assertThat(title).isEqualTo(bookSaveReqDto.getTitle());
        assertThat(author).isEqualTo(bookSaveReqDto.getAuthor());

    }

    @Sql("classpath:db/tableInit.sql")
    @Test
    public void getBookList_test(){
        // given

        // when
        HttpEntity<String> request = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = testRestTemplate.exchange("/api/v1/book", HttpMethod.GET, request, String.class);

        System.out.println(response.getBody());

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        Integer code = documentContext.read("$.code");
        String title = documentContext.read("$.body.items[0].title");


        // then
        assertThat(code).isEqualTo(1);
        assertThat(title).isEqualTo("junit5");

    }

}