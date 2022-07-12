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

import com.algaworks.glauber.algafood.api.assembler.UserInputDisassembler;
import com.algaworks.glauber.algafood.api.assembler.UserModelAssembler;
import com.algaworks.glauber.algafood.api.model.UserModel;
import com.algaworks.glauber.algafood.api.model.input.PassworInput;
import com.algaworks.glauber.algafood.api.model.input.UserInput;
import com.algaworks.glauber.algafood.api.model.input.UserWithPasswordInput;
import com.algaworks.glauber.algafood.domain.model.User;
import com.algaworks.glauber.algafood.domain.repository.UserRepository;
import com.algaworks.glauber.algafood.domain.service.UserRegistrationService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserModelAssembler userModelAssembler;
	
	@Autowired
	private UserInputDisassembler userInputDisassembler;
	
	@Autowired
	private UserRegistrationService userRegistrationService;

	@GetMapping
	public List<UserModel> index() {
		return userModelAssembler.toCollectionModel(userRepository.findAll());
	}
	
	@GetMapping("/{userId}")
	public UserModel show(@PathVariable Long userId) {
		return userModelAssembler.toModel(userRegistrationService.findUserByIdOrElseThrow(userId));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UserModel create(@RequestBody @Valid UserWithPasswordInput userWithPasswordInput) {
		return userModelAssembler.toModel(userRegistrationService.save(userInputDisassembler.toDomainObject(userWithPasswordInput)));
	}
	
	@PutMapping("/{userId}")
	public UserModel update(@PathVariable Long userId, @RequestBody @Valid  UserInput userInput) {
		User currentUser = userRegistrationService.findUserByIdOrElseThrow(userId);
		
		userInputDisassembler.copyToDomainObject(userInput, currentUser);
		
		return userModelAssembler.toModel(userRegistrationService.save(currentUser));
	}
	
	@PutMapping("/{userId}/password")
	public void updatePassword(@PathVariable Long userId, @RequestBody @Valid PassworInput passworInput) {
		userRegistrationService.updateSenha(userId, passworInput.getCurrentPassword(), passworInput.getNewPassword());
	}
	
	@DeleteMapping("/{userId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long userId) {
		userRegistrationService.delete(userId);
	}
}
