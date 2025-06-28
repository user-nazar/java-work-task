package com.example.library.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3)
    @Pattern(regexp = "^[A-Z].*", message = "Title must start with a capital letter")
    private String title;

    @NotBlank
    @Pattern(
        regexp = "^[A-Z][a-z]+ [A-Z][a-z]+$",
        message = "Author must be two capitalized words, e.g. Paulo Coelho"
    )
    private String author;

    private int amount = 1;

    @ManyToMany(mappedBy = "borrowedBooks")
    private Set<Member> borrowedBy = new HashSet<>();

    public Book() {}

    public Book(String title, String author, int amount) {
        this.title = title;
        this.author = author;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Set<Member> getBorrowedBy() {
        return borrowedBy;
    }

    public void setBorrowedBy(Set<Member> borrowedBy) {
        this.borrowedBy = borrowedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return Objects.equals(title, book.title) &&
               Objects.equals(author, book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author);
    }

    @Override
    public String toString() {
        return title + " by " + author;
    }
}
