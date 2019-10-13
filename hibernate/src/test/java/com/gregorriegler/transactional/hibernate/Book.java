package com.gregorriegler.transactional.hibernate;

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
