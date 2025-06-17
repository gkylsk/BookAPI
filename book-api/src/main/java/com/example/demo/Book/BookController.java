package com.example.demo.Book;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/books")
public class BookController {

	@Autowired
	private BookService bookService;
	
	@GetMapping
	public List<Book> viewAllBooks() {
		return bookService.viewAllBooks();
	}
	
	@GetMapping("{id}")
	public Optional<Book> viewBook(@PathVariable Long id) {
		return bookService.viewBook(id);
	}
	
	@PostMapping
	public void addBook(@RequestBody Book book) {
		bookService.addBook(book);
	}
	
	@PutMapping("{id}")
	public void updateBook(@PathVariable Long id,@RequestParam(required = false) String name,@RequestParam(required = false) String author) {
		bookService.updateBook(id, name, author);
	}
	
	@DeleteMapping("{id}")
	public void deleteBook(@PathVariable Long id) {
		bookService.deleteBook(id);
	}
}
