package ch.adisoftware.bookstore.api.exception;

public class BookIdMismatchException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3571149189833966858L;

	public BookIdMismatchException() {
		super();
	}
	
	public BookIdMismatchException(String message, Throwable cause) {
        super(message, cause);
    }

}
