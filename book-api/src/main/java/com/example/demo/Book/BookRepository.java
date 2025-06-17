package com.example.demo.Book;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

	Optional<Book> findByName(String name);
	Optional<Book> findByAuthor(String author);
}
