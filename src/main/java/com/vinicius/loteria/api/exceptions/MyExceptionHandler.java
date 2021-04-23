package com.vinicius.loteria.api.exceptions;

import java.time.OffsetDateTime;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.vinicius.loteria.api.exceptions.exception.GeralException;

@ControllerAdvice
public class MyExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	MessageSource messageSource;

	@ExceptionHandler(GeralException.class)	
	public ResponseEntity<Object> handleNegocio(GeralException ex, WebRequest request) {
		HttpStatus status = ex.getStatus();	
		StandardError problema = new StandardError();
		problema.setStatus(status.value());
		problema.setTitulo(ex.getMessage());
		problema.setDataHora(OffsetDateTime.now());				
		
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}	
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		
		ArrayList<StandardError.Campo> campos = new ArrayList<StandardError.Campo>();
		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			String nome = ((FieldError)error).getField();
			String mensagem = messageSource.getMessage(error, LocaleContextHolder.getLocale());
			campos.add(new StandardError.Campo(nome, mensagem));
		}
		
		StandardError problema = new StandardError();
		problema.setStatus(status.value());
		problema.setTitulo("Requisição com campos faltando ou preenchidos incorretamente");
		problema.setDataHora(OffsetDateTime.now());
		
		problema.setCampos(campos);
		
		return super.handleExceptionInternal(ex, problema, headers, status, request);
	}
}
