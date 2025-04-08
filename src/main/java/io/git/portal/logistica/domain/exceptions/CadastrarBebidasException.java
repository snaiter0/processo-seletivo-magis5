package io.git.portal.logistica.domain.exceptions;

import io.git.portal.logistica.adapters.in.controller.dto.request.CadastroBebidasRequisicao;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class CadastrarBebidasException extends RuntimeException{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private CadastroBebidasRequisicao cadastro;


	public CadastrarBebidasException() {
		super();
	}
	public CadastrarBebidasException(String message) {
		super(message);
	}

	public CadastrarBebidasException(String message, Throwable cause) {
		super(message, cause);
	}
	public CadastrarBebidasException(Throwable cause) {
		super(cause);
	}

	protected CadastrarBebidasException(String message, Throwable cause,
                                        boolean enableSuppression,
                                        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CadastrarBebidasException(String message, CadastroBebidasRequisicao cadastroBebidasRequisicao) {
		super(message);
		this.cadastro=cadastroBebidasRequisicao;
	}

}
