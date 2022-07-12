package com.algaworks.glauber.algafood.infrastructure.repository;

import static com.algaworks.glauber.algafood.domain.exception.MessagesExceptions.MSG_ENTITY_NOT_FOUND;

import java.util.Arrays;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.NoResultException;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.algaworks.glauber.algafood.domain.exception.EntityNotFoundException;
import com.algaworks.glauber.algafood.domain.repository.CustomJpaRepository;

public class CustomJpaRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> 
	implements CustomJpaRepository<T, ID>{
	
	private EntityManager entityManager;

	public CustomJpaRepositoryImpl(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.entityManager = entityManager;
	}

	@Override
	public Optional<T> buscarPrimeiro() {
		var jpql = "from " + getDomainClass().getName();
		
		T entity = entityManager.createQuery(jpql, getDomainClass())
				.setMaxResults(1)
				.getSingleResult();
		
		return Optional.ofNullable(entity);
	}

	@Override
	public T findByIdOrElseThrow(ID id) {
		Class<T> entityClass = getDomainClass();
		
		try {
			T entity = entityManager.createQuery(String
					.format("from %s where %s = :id",
			            entityClass.getName(),
			            Arrays.stream(entityClass.getDeclaredFields())
			                    .filter(field -> field.isAnnotationPresent(Id.class)).findFirst().get().getName()), 
					entityClass)
						.setParameter("id", id)
						.getSingleResult();

			return entity;
			
		} catch (NoResultException e) {
			throw new EntityNotFoundException(String.format(MSG_ENTITY_NOT_FOUND, entityClass.getSimpleName(), id));
		}
		
	}

	@Override
	public void detach(T entity) {
		entityManager.detach(entity);
	}

}
