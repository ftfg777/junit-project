package com.example.demo.service;

import com.example.demo.domain.Book;
import com.example.demo.repository.BookRepository;
import com.example.demo.util.MailSender;
import com.example.demo.web.dto.BookRespDto;
import com.example.demo.web.dto.BookSaveRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
// 클 -> C -> S -> R -> PS -> DB
public class BookService {

    private final BookRepository bookRepository;
    private final MailSender mailSender;

    // 1. 책등록
    @Transactional(rollbackFor = RuntimeException.class)
    public BookRespDto 책등록하기(BookSaveRespDto dto){
        Book bookPS = bookRepository.save(dto.toEntity());
        if(bookPS != null){
            if (!mailSender.send()){
                throw new RuntimeException("메일이 전송되지 않았습니다.");
            }
        }
        return new BookRespDto().toDto(bookPS);
    }

    // 2. 책목록보기
    public List<BookRespDto> 책목록보기(){
        return bookRepository.findAll().stream()
                .map(bookPS -> new BookRespDto().toDto(bookPS))
                .collect(Collectors.toList());
    }

    // 3. 책한건보기
    public BookRespDto 책한건보기(Long id){
        Optional<Book> bookOP = bookRepository.findById(id);
        if(bookOP.isPresent()){
            return new BookRespDto().toDto(bookOP.get());
        }else{
            throw new NullPointerException("해당 아이디를 찾을 수 없습니다. ID = " + id);
        }
    }

    // 4. 책삭제
    @Transactional(rollbackFor = RuntimeException.class)
    public void 책삭제하기(Long id){
        bookRepository.deleteById(id);
    }

    // 5. 책수정
    @Transactional(rollbackFor = RuntimeException.class)
    public BookRespDto 책수정하기(Long id, BookSaveRespDto dto){
        Optional<Book> bookOP = bookRepository.findById(id);
        if (bookOP.isPresent()){
            Book bookPS = bookOP.get();
            bookPS.update(dto.getTitle(), dto.getAuthor());
            return new BookRespDto().toDto(bookPS);
        }else{
            throw new RuntimeException("해당 아이디를 찾을 수 없습니다. ID = " + id);
        }
    }
}
