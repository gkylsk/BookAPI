package com.example.demo.Book;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.Users.JWTFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

//@WebMvcTest(BookController.class)
@WebMvcTest(controllers = BookController.class, excludeFilters = {
	    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JWTFilter.class)
	})
@AutoConfigureMockMvc(addFilters = false)
class BookControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private BookService bookService;
	
	Book bookOne;
	Book bookTwo;
	List<Book> bookList = new ArrayList<>();
	
	@BeforeEach
	void setUp() throws Exception {
		bookOne = new Book(1L,"Wildcard", "Marie Lu");
		bookTwo = new Book(2L,"Warcross", "Marie Lu");
		bookList.add(bookOne);
		bookList.add(bookTwo);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testViewAllBooks() throws Exception {
		when(bookService.viewAllBooks()).thenReturn(bookList);
		this.mockMvc.perform(get("/api/books")).andDo(print()).andExpect(status().isOk());
	}

	@Test
	void testViewBook() throws Exception {
		when(bookService.viewBook(1L)).thenReturn(Optional.of(bookOne));
		this.mockMvc.perform(get("/api/books/1")).andDo(print()).andExpect(status().isOk());
	}

	@Test
	void testAddBook() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = writer.writeValueAsString(bookOne);
		
		when(bookService.addBook(bookOne)).thenReturn("Success");
		this.mockMvc.perform(post("/api/books")
			.contentType(MediaType.APPLICATION_JSON)
			.content(requestJson))
			.andDo(print()).andExpect(status().isOk());
	}

	@Test
	void testUpdateBook() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = writer.writeValueAsString(bookOne);
		
		when(bookService.updateBook(bookOne.getId(),bookOne.getName(),bookOne.getAuthor())).thenReturn("Success");
		this.mockMvc.perform(put("/api/books/1")
			.contentType(MediaType.APPLICATION_JSON)
			.content(requestJson))
			.andDo(print()).andExpect(status().isOk());
	}

	@Test
	void testDeleteBook() throws Exception {
		when(bookService.deleteBook(bookOne.getId())).thenReturn("Success");
		this.mockMvc.perform(delete("/api/books/1")).andDo(print()).andExpect(status().isOk());
	}

}
