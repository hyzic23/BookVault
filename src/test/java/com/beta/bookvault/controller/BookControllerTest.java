//package com.beta.bookvault.controller;
//
//import com.beta.bookvault.dtos.BookResponse;
//import com.beta.bookvault.excecptions.GlobalExceptionHandler;
//import com.beta.bookvault.excecptions.ResourceNotFoundException;
//import com.beta.bookvault.service.BookService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
////import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Import;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.springframework.test.web.servlet.MockMvc;
//
//
////import org.junit.jupiter.api.Test;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
////import org.springframework.context.annotation.Import;
////import org.springframework.data.domain.Page;
////import org.springframework.data.domain.PageImpl;
////import org.springframework.data.domain.PageRequest;
////import org.springframework.test.context.bean.override.mockito.MockitoBean;
////import org.springframework.test.web.servlet.MockMvc;
////import tools.jackson.databind.ObjectMapper;
//
//import java.util.List;
//
//import static java.lang.reflect.Array.get;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(BookController.class)
//@Import(GlobalExceptionHandler.class)
//public class BookControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @MockitoBean
//    private BookService bookService;
//
//    @Test
//    void getAllBooks_returnsPageOfBooks() throws Exception {
//        // Arrange
//        BookResponse b1 = new BookResponse();
//        b1.setId(1L);
//        b1.setTitle("Clean Code");
//
//        Page<BookResponse> page = new PageImpl<>(
//                List.of(b1), PageRequest.of(0, 10), 1
//        );
//        when(bookService.getAllBooks(any())).thenReturn(page);
//
//        // Act & Assert
//        mockMvc.perform(get("/api/books"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content[0].title").value("Clean Code"))
//                .andExpect(jsonPath("$.content[0].id").value(1))
//                .andExpect(jsonPath("$.totalElements").value(1));
//    }
//
//    @Test
//    void getBook_whenMissing_returns404() throws Exception {
//        when(bookService.getBookById(999L))
//                .thenThrow(new ResourceNotFoundException("Book not found with id: 999"));
//
//        mockMvc.perform(get("/api/books/999"))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.status").value(404))
//                .andExpect(jsonPath("$.message").value("Book not found with id: 999"));
//    }
//
//    @Test
//    void createBook_withInvalidBody_returns400() throws Exception {
//        String invalidJson = """
//                {
//                  "year": 2008,
//                  "authorId": 1
//                }
//                """;
//
//        mockMvc.perform(post("/api/books")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(invalidJson))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.fields.title").exists());
//    }
//
//    @Test
//    void createBook_withValidBody_returns201() throws Exception {
//        String json = """
//                {
//                  "title": "Clean Code",
//                  "year": 2008,
//                  "authorId": 1,
//                  "tagIds": [1]
//                }
//                """;
//
//        BookResponse response = new BookResponse();
//        response.setId(42L);
//        response.setTitle("Clean Code");
//
//        when(bookService.createBook(any())).thenReturn(response);
//
//        mockMvc.perform(post("/api/books")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(json))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.id").value(42))
//                .andExpect(jsonPath("$.title").value("Clean Code"));
//    }
//}
