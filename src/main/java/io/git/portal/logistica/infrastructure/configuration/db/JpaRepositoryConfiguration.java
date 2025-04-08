package io.git.portal.logistica.infrastructure.configuration.db;

import io.git.portal.logistica.infrastructure.persistence.BebidaRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackageClasses = BebidaRepository.class)
public class JpaRepositoryConfiguration {

}
