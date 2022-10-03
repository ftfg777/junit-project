package com.example.demo.web;

import com.example.demo.service.BookService;
import com.example.demo.web.dto.response.BookListRespDto;
import com.example.demo.web.dto.response.BookRespDto;
import com.example.demo.web.dto.request.BookSaveReqDto;
import com.example.demo.web.dto.response.CMRespDto;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BookApiController { // 컴포지션 = has 관계

    private final BookService bookService;

    // 1. 책등록
    // key=value&key=value
    // {"key": value, "key": value }
    @PostMapping("/api/v1/book")
    public ResponseEntity<?> saveBook(@RequestBody @Valid BookSaveReqDto dto, BindingResult bindingResult){

        // AOP 처리하는 게 좋음
        if (bindingResult.hasErrors()){
            Map<String, String> errorMap = new HashMap<>();
            for(FieldError fe : bindingResult.getFieldErrors()){
                errorMap.put(fe.getField(), fe.getDefaultMessage());
            }
            throw new RuntimeException(errorMap.toString());
        }
        BookRespDto bookRespDto = bookService.책등록하기(dto);
        return new ResponseEntity<>(CMRespDto.builder()
                .code(1)
                .msg("책 저장 성공")
                .body(bookRespDto)
                .build(), HttpStatus.CREATED); // 201 = insert
    }

    // 2. 책목록보기
    @GetMapping("/api/v1/book")
    public ResponseEntity<?> getBookList(){
        BookListRespDto bookListRespDto = bookService.책목록보기(); // 컬렉션 형태가 아닌 오브젝트 형태 List 받기

        return new ResponseEntity<>(CMRespDto.builder()
                .code(1)
                .msg("책 목록보기 성공")
                .body(bookListRespDto)
                .build(), HttpStatus.OK); // 200 = ok
    }

    // 3. 책한건보기
    @GetMapping("/api/v1/book/{id}")
    public ResponseEntity<?> getBookOne(@PathVariable Long id){
        BookRespDto bookRespDto = bookService.책한건보기(id);
        return new ResponseEntity<>(CMRespDto.builder()
                .code(1)
                .msg("책 한건보기 성공")
                .body(bookRespDto)
                .build(), HttpStatus.OK); // 200 = ok
    }

    // 4. 책삭제하기
    @DeleteMapping("/api/v1/book/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id){
        bookService.책삭제하기(id);
        return new ResponseEntity<>(CMRespDto.builder()
                .code(1)
                .msg("책 삭제하기 성공")
                .body(null)
                .build(), HttpStatus.OK); // 200 = ok
    }

    // 5. 책수정하기
    @PutMapping("/api/v1/book/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id,
                                        @RequestBody @Valid BookSaveReqDto bookSaveReqDto, BindingResult bindingResult){

        // AOP 처리하는 게 좋음
        if (bindingResult.hasErrors()){
            Map<String, String> errorMap = new HashMap<>();
            for(FieldError fe : bindingResult.getFieldErrors()){
                errorMap.put(fe.getField(), fe.getDefaultMessage());
            }
            throw new RuntimeException(errorMap.toString());
        }
        BookRespDto bookRespDto = bookService.책수정하기(id, bookSaveReqDto);

        return new ResponseEntity<>(CMRespDto.builder()
                .code(1)
                .msg("책 수정하기 성공")
                .body(bookRespDto)
                .build(), HttpStatus.OK); // 200 = ok

    }

}
