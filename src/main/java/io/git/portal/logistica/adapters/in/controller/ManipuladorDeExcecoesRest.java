package io.git.portal.logistica.adapters.in.controller;

import io.git.portal.logistica.domain.exceptions.CadastrarBebidasException;
import io.git.portal.logistica.domain.exceptions.SecaoException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


/**
 * Classe responsavel por personalizar a configuração de captura e lançamento de exceptions
 * padrões do servlet do Spring, a herança só é necessária no caso da necessidade de sobreescrita
 * do tratamento padrão, caso contrário o @ControllerAdvice poderia ser utilizado diretamente na
 * controller.
 */

@ControllerAdvice
public class ManipuladorDeExcecoesRest extends ResponseEntityExceptionHandler {


	/**
	 * Exemplo de configuracao personalizada do servlet para capturar exceptions ao receber possiveis requisicoes na interface REST
	 * 
	 * @param ex Exception capturada pela lista do @ExceptionHandler
	 * @param request requisição que gerou a exception.
	 * @return Response Entity padrão com msg e status personalizados de acordo com cara exception capturada pela anotacao @ExceptionHandler. 
	 */
	@ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
	protected ResponseEntity<Object> tratarConflitoDeParametros(RuntimeException ex, WebRequest request) {
		return new ResponseEntity<>(montarResposta(
				"Falha ao receber parametros, verifique os dados fornecidos e seu devido formato."),
				new HttpHeaders(),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("Erro interno: " + ex.getMessage());
	}



	@ExceptionHandler(value = { MethodArgumentTypeMismatchException.class})
	protected ResponseEntity<Object> tratarTipoDeParametroInvalido(RuntimeException ex, WebRequest request) {

		Map<String, Object> parametrosAdicionais = new HashMap<>();
		parametrosAdicionais.put("parametros recebidos: ", request.getParameterMap());
		
		return new ResponseEntity<>(montarResposta(parametrosAdicionais,
				"Falha ao tentar registrar nova bebida, verifique os dados fornecidos. "+ ex.getMessage()),
				new HttpHeaders(),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = { SecaoException.class, CadastrarBebidasException.class })
	protected ResponseEntity<Object> tratarFalhaAoInserirNovasBebidasNaSecao(CadastrarBebidasException ex, WebRequest request) {

		Map<String, Object> parametrosAdicionais = new HashMap<>();
		parametrosAdicionais.put("parametros recebidos: ", request.getParameterMap());
		parametrosAdicionais.put("corpo recebido: ", ex.getCadastro());

		return new ResponseEntity<>(montarResposta(parametrosAdicionais,
				"Falha ao tentar registrar novas bebidas, verifique os dados fornecidos como o tipo " +
						"de bebida e seu volume. "+ ex.getMessage()),
				new HttpHeaders(),
				HttpStatus.CONFLICT);
	}

	/**
	 * Monta Response que será devolvido ao solicitante.
	 *
	 * @param mensagem mensagem que será exibida no response que será devolvido
	 * @return Objeto de resposta que será devolvido ao solicitante conforme a exception lançada.
	 */
	private Map<String, Object> montarResposta(String mensagem){
		Map<String, Object> response = new HashMap<>();
		response.put("mensagem", mensagem);
		response.put("horario", LocalDateTime.now());
		return response;
	}

	private Map<String, Object> montarResposta(Map<String, Object> entradasAdicionais, String mensagem){
        Map<String, Object> response = new HashMap<>(entradasAdicionais);
		response.put("mensagem", mensagem);
		response.put("horario", LocalDateTime.now());
		return response;
	}

}