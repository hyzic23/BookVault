package com.beta.bookvault.service;

import com.beta.bookvault.dtos.BookResponse;
import com.beta.bookvault.entity.Book;
import com.beta.bookvault.excecptions.ResourceNotFoundException;
import com.beta.bookvault.mapper.BookMapper;
import com.beta.bookvault.repository.AuthorRepository;
import com.beta.bookvault.repository.BookRepository;
import com.beta.bookvault.repository.TagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookService bookService;


    @Test
    void getBookById_whenExists_returnsResponse() {
        //Arrange
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Clean Code");

        BookResponse expected = new BookResponse();
        expected.setId(1L);
        expected.setTitle("Clean Code");

        when(bookRepository.findByIdWithDetails(1L))
                .thenReturn(Optional.of(book));
        when(bookMapper.toResponse(book)).thenReturn(expected);

        //Act
        BookResponse actual = bookService.getBookById(1L);

        //Assert
        assertThat(actual).isNotNull();
        assertThat(actual.getTitle()).isEqualTo("Clean Code");
        verify(bookRepository).findByIdWithDetails(1L);
    }

    @Test
    void getBookById_whenMissing_throwsException() {
        when(bookRepository.findByIdWithDetails(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.getBookById(999L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("999");
    }

    @Test
    void deleteBook_whenMissing_throwsException() {
        when(bookRepository.existsById(999L)).thenReturn(false);

        assertThatThrownBy(() -> bookService.deleteBook(999L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

}
