package com.algaworks.glauber.algafood;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.algaworks.glauber.algafood.infrastructure.repository.CustomJpaRepositoryImpl;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class AlgafoodApiApplication {

	public static void main(String[] args) {
		//a partir dessa configuração todas as datas instanciadas pela aplicação será capturada a data no padrão UTC sem Offset - adiciona o Z no final da data
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		
		SpringApplication.run(AlgafoodApiApplication.class, args);
	}

}
