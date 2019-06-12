package ch.adisoftware.SpringBootDemo;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import ch.adisoftware.bookstore.persistence.model.dto.BookDTO;
import ch.adisoftware.bookstore.persistence.model.entity.Book;

@SpringBootTest
public class ModelMapperTest {
	
	private ModelMapper modelMapper = new ModelMapper();
	 
    @Test
    public void testConvertBookToBookDTO_thenOK() {
        Book book = new Book();
        book.setId(Long.valueOf(1));
        book.setTitle(randomAlphabetic(6));
        book.setAuthor(randomAlphabetic(6));
 
        BookDTO bootDTO = modelMapper.map(book, BookDTO.class);
        assertEquals(book.getId(), bootDTO.getId());
        assertEquals(book.getTitle(), bootDTO.getTitle());
        assertEquals(book.getAuthor(), bootDTO.getAuthor());
    }
 
    @Test
    public void testConvertBookDTOToBook_thenOK() {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(Long.valueOf(1));
        bookDTO.setTitle(randomAlphabetic(6));
        bookDTO.setAuthor(randomAlphabetic(6));
 
        Book book = modelMapper.map(bookDTO, Book.class);
        assertEquals(bookDTO.getId(), book.getId());
        assertEquals(bookDTO.getTitle(), book.getTitle());
        assertEquals(bookDTO.getAuthor(), book.getAuthor());
    }

}
