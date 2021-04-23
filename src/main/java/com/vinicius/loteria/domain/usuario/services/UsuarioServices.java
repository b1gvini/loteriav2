package com.vinicius.loteria.domain.usuario.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.vinicius.loteria.api.exceptions.exception.GeralException;
import com.vinicius.loteria.domain.aposta.Aposta;
import com.vinicius.loteria.domain.aposta.services.ApostaServices;
import com.vinicius.loteria.domain.usuario.Usuario;
import com.vinicius.loteria.domain.usuario.repository.UsuarioRepository;

@Service
public class UsuarioServices {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ApostaServices apostaServices;
	
	public Usuario buscar(String email) {
		Usuario usuarioExistente = usuarioRepository.findByEmail(email); //TODO corrigir bug ao cadastrar nova aposta
		if (usuarioExistente == null) {
			throw new GeralException("Usuário Não Encontrado", HttpStatus.NOT_FOUND);
		}
		return usuarioExistente;
	}
	
	public Usuario salvarNovoUsuario(Usuario usuario) {
		return usuarioRepository.save(usuario);
		
	}
	
	public ResponseEntity<?> apostar(Usuario usuario) {
		Usuario usuarioEncontrado = usuarioRepository.findByEmail(usuario.getEmail());
		if(usuarioEncontrado == null) {
			usuarioRepository.save(usuario);
			Aposta response = apostaServices.salvar(usuario);
			return new ResponseEntity<Aposta>(response, HttpStatus.CREATED);
		}else {
			apostaServices.salvar(usuarioEncontrado);
			return new ResponseEntity<Usuario>(usuarioEncontrado, HttpStatus.OK);
		}
	}
	
	public List<Usuario> buscarTodosUsuarios(){
		return usuarioRepository.findAll();
	}
	
}
