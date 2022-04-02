package com.br.algafood.api.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.br.algafood.api.assembler.FotoProdutoDTOAssembler;
import com.br.algafood.api.model.FotoProdutoDTO;
import com.br.algafood.api.model.input.ProdutoFotoInputDTO;
import com.br.algafood.domain.model.FotoProduto;
import com.br.algafood.domain.model.Produto;
import com.br.algafood.domain.service.CadastroProdutoService;
import com.br.algafood.domain.service.FotoProdutoService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class ProdutoFotoController {
	
	@Autowired
	private CadastroProdutoService cadastroProdutoService;

	@Autowired
	private FotoProdutoService fotoProdutoService;
	
	@Autowired
	private FotoProdutoDTOAssembler assembler;
	
	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public FotoProdutoDTO atualizarFoto(@PathVariable Long restauranteId, 
			@PathVariable Long produtoId, @Valid ProdutoFotoInputDTO produtoFotoInputDTO) throws IOException {
	
		Produto produto = cadastroProdutoService.buscarOuFalhar(restauranteId, produtoId);
		MultipartFile arquivo = produtoFotoInputDTO.getArquivo();
		
		FotoProduto foto = new FotoProduto();
		foto.setProduto(produto);
		foto.setDescricao(produtoFotoInputDTO.getDescricao());
		foto.setContentType(arquivo.getContentType());
		foto.setTamanho(arquivo.getSize());
		foto.setNomeArquivo(arquivo.getOriginalFilename());
		
		FotoProduto fotoProdutoSalva = fotoProdutoService.salvar(foto, arquivo.getInputStream());
		return assembler.toModel(fotoProdutoSalva);
	}
}
