package com.br.algafood.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.br.algafood.api.AlgaLinks;
import com.br.algafood.api.controller.UsuarioController;
import com.br.algafood.api.model.UsuarioDTO;
import com.br.algafood.domain.model.Usuario;

@Component
public class UsuarioDTOAssembler extends RepresentationModelAssemblerSupport<Usuario, UsuarioDTO> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AlgaLinks algaLinks;

	public UsuarioDTOAssembler() {
		super(UsuarioController.class, UsuarioDTO.class);
	}

	@Override
	public UsuarioDTO toModel(Usuario usuario) {

		UsuarioDTO usuarioDTO = createModelWithId(usuario.getId(), usuario);
		modelMapper.map(usuario, usuarioDTO);

		usuarioDTO.add(algaLinks.linkToUsuarios("usuarios"));
		usuarioDTO.add(algaLinks.linkToGruposUsuario(usuario.getId(), "grupos-usuario"));

		return usuarioDTO;
	}
	
	@Override
    public CollectionModel<UsuarioDTO> toCollectionModel(Iterable<? extends Usuario> entities) {
        return super.toCollectionModel(entities)
            .add(linkTo(UsuarioController.class).withSelfRel());
    }   

//	public List<UsuarioDTO> toCollectionModel(Collection<Usuario> usuarios) {
//		return usuarios.stream().map(usuario -> toModel(usuario)).collect(Collectors.toList());
//	}
}
