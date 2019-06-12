package ch.adisoftware.bookstore.api.exception;

public class UserNotFoundException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5369202522844789755L;

	public UserNotFoundException() {
		super();
	}
	 
    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
