package ch.adisoftware.bookstore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import ch.adisoftware.bookstore.persistence.model.entity.Book;
import ch.adisoftware.bookstore.persistence.model.entity.User;
import ch.adisoftware.bookstore.persistence.repo.BookRepository;
import ch.adisoftware.bookstore.persistence.repo.UserRepository;

import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    BookRepository books;

    @Autowired
    UserRepository users;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        this.books.save(new Book("titel titel 1", "autor autor 1"));
        this.books.save(new Book("titel titel 2", "autor autor 2"));

        this.books.findAll().forEach(v -> System.out.println(" Book :" + v.toString()));

        this.users.save(new User(
            "user",
            this.passwordEncoder.encode("password"),
            Arrays.asList( "ROLE_USER")));

        this.users.save(new User(
            "admin",
            this.passwordEncoder.encode("password"),
            Arrays.asList("ROLE_USER", "ROLE_ADMIN")));

        this.users.findAll().forEach(v -> System.out.println(" User :" + v.toString()));
    }
}
