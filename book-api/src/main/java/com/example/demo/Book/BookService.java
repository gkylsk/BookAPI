package com.example.demo.Book;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;
	
	public List<Book> viewAllBooks() {
		return bookRepository.findAll();
	}
	
	public Optional<Book> viewBook(Long id) {
		return bookRepository.findById(id);
	}
	

	
	public String addBook(Book book) {
		bookRepository.save(book);
		return "Success";
	}
	
	@Transactional
	public String updateBook(Long id, String name, String author) {
		Book book = bookRepository.findById(id)
				.orElseThrow(() -> new IllegalStateException(
						"Book with id"+ id + "does not exists"
						));
		
		if(name != null &&
				name.length() > 0 &&
				!Objects.equals(name, book.getName())) {
			Optional<Book> bookOptional = bookRepository.findByName(name);
			if(bookOptional.isPresent()) {
				throw new IllegalStateException("Book Already exists");
			}
			book.setName(name);
		}
		
		if(author != null &&
				author.length() > 0 &&
				!Objects.equals(author, book.getAuthor())) {
			book.setAuthor(author);
		}
		
		bookRepository.save(book);
		return "Success";
	}
	
	public String deleteBook(Long id) {
		bookRepository.deleteById(id);
		return "Success";
	}
}
