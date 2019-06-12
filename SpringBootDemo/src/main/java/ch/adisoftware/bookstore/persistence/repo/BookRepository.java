package ch.adisoftware.bookstore.persistence.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ch.adisoftware.bookstore.persistence.model.entity.Book;

public interface BookRepository extends CrudRepository<Book, Long> {
    List<Book> findByTitle(String title);
}
