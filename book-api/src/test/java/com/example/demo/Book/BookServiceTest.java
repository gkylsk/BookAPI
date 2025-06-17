package com.example.demo.Book;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class BookServiceTest {

	@Mock
	private BookRepository bookRepository;
	
	@InjectMocks
	private BookService bookService;
	
	AutoCloseable autoCloseable;
	Book book;
	
	@BeforeEach
	void setUp() throws Exception {
		autoCloseable = MockitoAnnotations.openMocks(this);
		book = new Book("Wildcard", "Marie Lu");
	}

	@AfterEach
	void tearDown() throws Exception {
		autoCloseable.close();
	}

	@Test
	void testViewAllBooks() {
		when(bookRepository.findAll()).thenReturn(
				new ArrayList<Book>(Collections.singleton(book)));
		assertThat(bookService.viewAllBooks().get(0).getAuthor()).isEqualTo(book.getAuthor());
	}

	@Test
	void testViewBook() {
		when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
		assertThat(bookService.viewBook(book.getId()).get().getName()).isEqualTo(book.getName());
	}

	@Test
	void testAddBook() {
		when(bookRepository.save(book)).thenReturn(book);
		assertThat(bookService.addBook(book)).isEqualTo("Success");
	}

	@Test
	void testUpdateBook() {
		when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
		when(bookRepository.save(book)).thenReturn(book);
		assertThat(bookService.updateBook(book.getId(),book.getName(), book.getAuthor())).isEqualTo("Success");
	}

	@Test
	void testDeleteBook() {
		doAnswer(Answers.CALLS_REAL_METHODS).when(bookRepository).deleteById(book.getId());
		assertThat(bookService.deleteBook(book.getId())).isEqualTo("Success");
	}

}
