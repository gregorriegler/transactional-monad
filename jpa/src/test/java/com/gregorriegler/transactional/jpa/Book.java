package com.gregorriegler.transactional.jpa;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
class Book {
    @Id
    String isbn;

    public Book() {
    }

    Book(String isbn) {
        this.isbn = isbn;
    }
}
