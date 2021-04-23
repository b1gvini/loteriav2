package com.vinicius.loteria.domain.aposta.services;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.vinicius.loteria.api.exceptions.exception.GeralException;
import com.vinicius.loteria.domain.aposta.Aposta;
import com.vinicius.loteria.domain.aposta.repository.ApostaRepository;
import com.vinicius.loteria.domain.usuario.Usuario;
import com.vinicius.loteria.domain.usuario.repository.UsuarioRepository;

@Service
public class ApostaServices {
	
	@Autowired
	ApostaRepository apostaRepository;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	private static String gerarAposta() {
		String result = "";
	     for (int i = 0; i < 6; i++) {
	        int numAleatorio = (int)(Math.random() * 99 ) + 1;
	        result+= " " + Integer.toString(numAleatorio);
	     }
	     String resultado = result.substring(1);
	     return resultado;
	   }
	
	public Aposta salvar(Usuario usuario) {
		Aposta aposta = new Aposta();
		aposta.setCriado_em(OffsetDateTime.now());
		aposta.setNumeroDaSorte(ApostaServices.gerarAposta());
		aposta.setUsuario(usuario);
		return apostaRepository.save(aposta);
	}
	
	public List<Aposta> listar(String email) {
		Usuario usuarioExistente = usuarioRepository.findByEmail(email);
		if (usuarioExistente == null) {
			throw new GeralException("Usuário Não Encontrado", HttpStatus.NOT_FOUND);
		}
		List<Aposta> apostas = new ArrayList<>();
		apostas = apostaRepository.findAllApostasById(usuarioExistente.getId());
		return apostas;
	}
	
}
