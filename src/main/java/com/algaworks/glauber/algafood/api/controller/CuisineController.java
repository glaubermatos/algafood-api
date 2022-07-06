package com.algaworks.glauber.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.glauber.algafood.api.assembler.CuisineInputDisassembler;
import com.algaworks.glauber.algafood.api.assembler.CuisineModelAssembler;
import com.algaworks.glauber.algafood.api.model.CuisineModel;
import com.algaworks.glauber.algafood.api.model.input.CuisineInput;
import com.algaworks.glauber.algafood.domain.model.Cuisine;
import com.algaworks.glauber.algafood.domain.repository.CuisineRepository;
import com.algaworks.glauber.algafood.domain.service.CuisineRegistrationService;

@RestController
@RequestMapping("/cuisines")
public class CuisineController {
	
	@Autowired
	private CuisineRepository cuisineRepository;
	
	@Autowired
	private CuisineRegistrationService cuisineRegistrationService;
	
	@Autowired
	private CuisineModelAssembler cuisineModelAssembler;
	
	@Autowired
	private CuisineInputDisassembler cuisineInputDisassembler;
	

	@GetMapping
	public List<CuisineModel> listar() {
		return cuisineModelAssembler.toCollectionModel(cuisineRepository.findAll());
	}
	
	@GetMapping("/{cuisineId}")
	@ResponseStatus(HttpStatus.OK)
	public CuisineModel buscar(@PathVariable Long cuisineId) {
		return cuisineModelAssembler.toModel(cuisineRegistrationService
				.findCuisineByIdOrElseThrow(cuisineId));	
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CuisineModel criar(@RequestBody @Valid CuisineInput cuisineInput) {
		return cuisineModelAssembler.toModel(cuisineRegistrationService
				.salvar(cuisineInputDisassembler.toDomaiObject(cuisineInput)));
	}
	
	@PutMapping("/{cuisineId}")
	public Cuisine atualizar(@PathVariable Long cuisineId, @RequestBody @Valid CuisineInput cuisineInput) {
		Cuisine currentCuisine = cuisineRegistrationService.findCuisineByIdOrElseThrow(cuisineId);
		
		cuisineInputDisassembler.copyToDomainObject(cuisineInput, currentCuisine);
		
		//BeanUtils.copyProperties(cozinha, currentCuisine, "id");
		
		return cuisineRegistrationService.salvar(currentCuisine);
	}
	
//	@DeleteMapping("/{cuisineId}")
//	public ResponseEntity<?> remover(@PathVariable Long cuisineId) {		
//		try {
//			cuisineRegistrationService.excluir(cuisineId);
//			return ResponseEntity.noContent().build();
//			
//		} catch (EntityNotFoundException e) {
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//			
//		} catch (EntityInUseException e) {
//			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
//		}
//	}
	
	@DeleteMapping("/{cuisineId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cuisineId) {		
		cuisineRegistrationService.excluir(cuisineId);
	}
	
	/*
	 * Sem a necessidade de criar classes de exceptions de domínio, usa a classe
	 * ResponseStatusException passando o código HTTP desejado,
	 * O metodo da classe de serviço apenas repassa as exceções para o controller tratálas
	 * Em projetos pequenos ou MVPs é interessante o uso para ganhar tempo
	 */
//	@DeleteMapping("/{cuisineId}")
//	@ResponseStatus(HttpStatus.NO_CONTENT)
//	public void remover(@PathVariable Long cuisineId) {		
//		try {
//			cuisineRegistrationService.excluir(cuisineId);
//			
//		} catch (EmptyResultDataAccessException e) {
//			throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Cozinha de código %d não existe", cuisineId));
//			
//		} catch (DataIntegrityViolationException e) {
//			throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("Cozinha de código %d não pode ser removida, pois está em uso", cuisineId));
//		}
//	}
}
