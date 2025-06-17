package com.example.demo.Book;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class BookRepositoryTest {

	@Autowired
	private BookRepository bookRepository;
	Book book;
	
	@BeforeEach
	void setUp() throws Exception {
		book = new Book("Wildcard", "Marie Lu");
		bookRepository.save(book);
	}

	@AfterEach
	void tearDown() throws Exception {
		book = null;
		bookRepository.deleteAll();
	}

	@Test
	void testFindByName_Found() {
		Optional<Book> books = bookRepository.findByName("Wildcard");
		assertThat(books.get().getId()).isEqualTo(book.getId());
		assertThat(books.get().getAuthor()).isEqualTo(book.getAuthor());
	}
	
	@Test
	void testFindByName_NotFound() {
		Optional<Book> books = bookRepository.findByName("Warcross");
		assertThat(books.isEmpty()).isTrue();
	}

	@Test
	void testFindByAuthor_Found() {
		Optional<Book> books = bookRepository.findByAuthor("Marie Lu");
		assertThat(books.get().getId()).isEqualTo(book.getId());
		assertThat(books.get().getName()).isEqualTo(book.getName());
	}
	
	@Test
	void testFindByAuthor_NotFound() {
		Optional<Book> books = bookRepository.findByAuthor("Dustin Thao");
		assertThat(books.isEmpty()).isTrue();
	}

}
