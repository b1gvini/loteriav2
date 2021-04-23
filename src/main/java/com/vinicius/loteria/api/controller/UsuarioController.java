package com.vinicius.loteria.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.vinicius.loteria.domain.aposta.Aposta;
import com.vinicius.loteria.domain.aposta.services.ApostaServices;
import com.vinicius.loteria.domain.usuario.Usuario;
import com.vinicius.loteria.domain.usuario.services.UsuarioServices;

@RestController
@RequestMapping("/")
public class UsuarioController {

	@Autowired
	UsuarioServices usuarioService;
	
	@Autowired
	ApostaServices apostaService;

	@GetMapping("/usuarios")
	public List<Usuario> listar() {

		return usuarioService.buscarTodosUsuarios();
	}
	
	@GetMapping("{email}")
	public List<Aposta> apostas(@PathVariable String email){
		return apostaService.listar(email);
	}

	@PostMapping("/apostar")
	public ResponseEntity<?> Apostar(@Valid @RequestBody Usuario usuario) {
		return usuarioService.apostar(usuario);
	}
}
