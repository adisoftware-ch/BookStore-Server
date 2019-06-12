package ch.adisoftware.bookstore.security.jwt;

import org.springframework.security.core.AuthenticationException;

public class InvalidJwtAuthenticationException extends AuthenticationException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3520228212176165092L;

	public InvalidJwtAuthenticationException(String e) {
        super(e);
    }
}
