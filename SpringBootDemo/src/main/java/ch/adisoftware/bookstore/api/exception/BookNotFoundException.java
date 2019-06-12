package ch.adisoftware.bookstore.api.exception;

public class BookNotFoundException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5369202522844789755L;

	public BookNotFoundException() {
		super();
	}
	 
    public BookNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
