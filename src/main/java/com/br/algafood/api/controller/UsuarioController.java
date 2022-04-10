package com.br.algafood.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.br.algafood.api.assembler.UsuarioDTOAssembler;
import com.br.algafood.api.assembler.UsuarioInputDTODisassembler;
import com.br.algafood.api.model.UsuarioDTO;
import com.br.algafood.api.model.input.SenhaInputDTO;
import com.br.algafood.api.model.input.UsuarioComSenhaInputDTO;
import com.br.algafood.api.model.input.UsuarioInputDTO;
import com.br.algafood.api.openapi.controller.UsuarioControllerOpenApi;
import com.br.algafood.domain.exception.CidadeNaoEncontradaException;
import com.br.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.br.algafood.domain.exception.NegocioException;
import com.br.algafood.domain.model.Usuario;
import com.br.algafood.domain.repository.UsuarioRepository;
import com.br.algafood.domain.service.CadastroUsuarioService;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController implements UsuarioControllerOpenApi {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private CadastroUsuarioService cadastroUsuario;

	@Autowired
	private UsuarioDTOAssembler usuarioDTOAssembler;

	@Autowired
	private UsuarioInputDTODisassembler usuarioInputDTODisassembler;

	@Override
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public CollectionModel<UsuarioDTO> listar() {
		return usuarioDTOAssembler.toCollectionModel(usuarioRepository.findAll());
	}

	@Override
	@GetMapping(path = "/{usuarioId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public UsuarioDTO buscar(@PathVariable Long usuarioId) {
		Usuario usuario = cadastroUsuario.buscarOuFalhar(usuarioId);
		return usuarioDTOAssembler.toModel(usuario);
	}

	@Override
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioDTO adicionar(@RequestBody @Valid UsuarioComSenhaInputDTO usuarioInput) {
		Usuario usuario = usuarioInputDTODisassembler.toDomainObject(usuarioInput);
		return usuarioDTOAssembler.toModel(cadastroUsuario.salvar(usuario));

	}

	@Override
	@PutMapping(path = "/{usuarioId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public UsuarioDTO atualizar(@PathVariable Long usuarioId, @RequestBody @Valid UsuarioInputDTO usuarioInput) {
		try {
			Usuario usuarioAtual = cadastroUsuario.buscarOuFalhar(usuarioId);

			usuarioInputDTODisassembler.copyToDomainObject(usuarioInput, usuarioAtual);

			return usuarioDTOAssembler.toModel(cadastroUsuario.salvar(usuarioAtual));
		} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	@Override
	@PutMapping("/{usuarioId}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterarSenha(@PathVariable Long usuarioId, @RequestBody @Valid SenhaInputDTO senha) {
        cadastroUsuario.alterarSenha(usuarioId, senha.getSenhaAtual(), senha.getNovaSenha());
    }    

}
