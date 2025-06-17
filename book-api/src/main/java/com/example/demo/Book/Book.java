package com.example.demo.Book;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table
public class Book {

	@Id
//	@SequenceGenerator(
//			name = "book_sequence",
//			sequenceName = "book_sequence",
//			allocationSize = 1
//	)
//	@GeneratedValue(
//			strategy = GenerationType.SEQUENCE,
//			generator = "book_sequence"
//	)
	private Long id;
	private String name;
	private String author;
	
	public Book() {
		super();
	}

	public Book(Long id,String name, String author) {
		super();
		this.id = id;
		this.name = name;
		this.author = author;
	}
	
	public Book(String name, String author) {
		super();
		this.name = name;
		this.author = author;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
	
}
