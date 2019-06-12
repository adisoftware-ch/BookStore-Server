package ch.adisoftware.bookstore.api.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.transaction.TransactionSystemException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ch.adisoftware.bookstore.api.exception.BookIdMismatchException;
import ch.adisoftware.bookstore.api.exception.BookNotFoundException;
import ch.adisoftware.bookstore.persistence.model.dto.BookDTO;
import ch.adisoftware.bookstore.persistence.model.entity.Book;
import ch.adisoftware.bookstore.persistence.repo.BookRepository;

@RestController
@RequestMapping("/api/books")
public class BookController {
 
    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private ModelMapper modelMapper;
 
    @GetMapping
    public Iterable<Book> findAll() {
        return bookRepository.findAll();
    }
 
    @GetMapping("/title/{bookTitle}")
    public List<BookDTO> findByTitle(@PathVariable String bookTitle) {
        List<Book> books = bookRepository.findByTitle(bookTitle);
        return books.stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());
    }
 
    @GetMapping("/{id}")
    public BookDTO findOne(@PathVariable Long id) {
    	Book book = bookRepository.findById(id)
          .orElseThrow(BookNotFoundException::new);
    	return modelMapper.map(book, BookDTO.class);
    }
 
    /**
     * Create a new book entry. Fields of book-entity will be validated using hibernate
     * validator (@Valid). Validation rules are configured in the entity-bean itself.
     * Result of negative validation will be processed by method "handleValidationExcpetions"
     * of this class.
     * 
     * @param bookDTO
     * @return
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO create(@Valid @RequestBody BookDTO bookDTO) {
        Book book = bookRepository.save(modelMapper.map(bookDTO, Book.class));
        return modelMapper.map(book, BookDTO.class);
    }
 
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bookRepository.findById(id)
          .orElseThrow(BookNotFoundException::new);
        bookRepository.deleteById(id);
    }
 
    @PutMapping("/{id}")
    public BookDTO updateBook(@RequestBody BookDTO bookDTO, @PathVariable Long id) {
        if (bookDTO.getId() != id) {
          throw new BookIdMismatchException();
        }
        bookRepository.findById(id)
          .orElseThrow(BookNotFoundException::new);
        Book book = bookRepository.save(modelMapper.map(bookDTO, Book.class));
        return modelMapper.map(book, BookDTO.class);
    }
    
    /**
     * springframework.validation: Send result of negative field validation back to client
     * as JSON containing the name and corresponding error-message for each field.
     * 
     * @param ex
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TransactionSystemException.class)
    public Map<String, String> handleValidationExceptions(TransactionSystemException ex) {
    	Map<String, String> errors = new HashMap<>();
    	
    	if (ex.getCause() != null && ex.getCause().getCause() != null && ex.getCause().getCause() instanceof ConstraintViolationException) {
	        ((ConstraintViolationException)ex.getCause().getCause()).getConstraintViolations().forEach(violation -> {
	            String fieldName = violation.getPropertyPath().toString();
	            String errorMessage = violation.getMessage();
	            errors.put(fieldName, errorMessage);
	        });
    	}
        return errors;
    }
}
