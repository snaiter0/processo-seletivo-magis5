package io.git.portal.logistica.domain.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SecaoException extends RuntimeException{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	public SecaoException() {
		super();
	}
	public SecaoException(String message) {
		super(message);
	}

	public SecaoException(String message, Throwable cause) {
		super(message, cause);
	}
	public SecaoException(Throwable cause) {
		super(cause);
	}

	protected SecaoException(String message, Throwable cause,
                             boolean enableSuppression,
                             boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
