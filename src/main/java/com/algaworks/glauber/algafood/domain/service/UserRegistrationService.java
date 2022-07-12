package com.algaworks.glauber.algafood.domain.service;

import static com.algaworks.glauber.algafood.domain.exception.MessagesExceptions.MSG_ENTITY_IN_USE;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.glauber.algafood.domain.exception.BusinessException;
import com.algaworks.glauber.algafood.domain.exception.EntityInUseException;
import com.algaworks.glauber.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.glauber.algafood.domain.exception.UserAlreadyExistsForEmailInformed;
import com.algaworks.glauber.algafood.domain.exception.UserNotFoundException;
import com.algaworks.glauber.algafood.domain.model.User;
import com.algaworks.glauber.algafood.domain.model.UserGroup;
import com.algaworks.glauber.algafood.domain.repository.UserRepository;

@Service
public class UserRegistrationService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserGroupRegistrationService userGroupRegistrationService;
	
	@Transactional
	public User save(User user) {
		userRepository.detach(user);
		
		String email = user.getEmail();
		
		userRepository.findByEmailIgnoreCase(email).ifPresent((userAlreadyExists) -> {
			if (!userAlreadyExists.equals(user)) {
				throw new UserAlreadyExistsForEmailInformed(userAlreadyExists.getEmail());
			}
		});
		
		return userRepository.save(user);
	}
	
	@Transactional
	public void updateSenha(Long userId, String currentPassword, String newPassword) {
		User user = findUserByIdOrElseThrow(userId);
		
		if (user.doesNotMatchPassword(currentPassword)) {
			throw new BusinessException("Senha atual informada não coincide com a senha do usuário.");
		}
		
		user.setPassword(newPassword); 
		userRepository.flush();
	}
	
	@Transactional
	public void associateGroup(Long userId, Long groupId) {
		User user = findUserByIdOrElseThrow(userId);
		UserGroup group = userGroupRegistrationService.findUserGroupByIdOrElseThrow(groupId);
		
		user.associate(group);
	}
	
	@Transactional
	public void disassociateGroup(Long userId, Long groupId) {
		User user = findUserByIdOrElseThrow(userId);
		UserGroup group = userGroupRegistrationService.findUserGroupByIdOrElseThrow(groupId);
		
		user.disassociate(group);
	}
	
	@Transactional
	public void delete(Long userId) {
		try {
			userRepository.deleteById(userId);
			userRepository.flush();
			
		} catch (EmptyResultDataAccessException e) {
			throw new UserNotFoundException(userId);
			
		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(String.format(MSG_ENTITY_IN_USE, "Usuário", userId));
		}
	}
	
	public User findUserByIdOrElseThrow(Long userId) {
		try {
			return userRepository.findByIdOrElseThrow(userId);
		} catch (EntityNotFoundException e) {
			throw new UserNotFoundException(userId);
		}
	}
}
