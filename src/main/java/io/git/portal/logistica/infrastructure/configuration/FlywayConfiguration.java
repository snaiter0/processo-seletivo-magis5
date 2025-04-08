package io.git.portal.logistica.infrastructure.configuration;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.output.MigrateResult;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class FlywayConfiguration{
	
    @Bean
    @ConditionalOnProperty(name = "spring.flyway.enabled", havingValue = "true", matchIfMissing = false)
    public MigrateResult FlywayConfig(DataSource dataSource) {
        return Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/migration") // onde os scripts de migração estão localizados
                .load().migrate();
    }
}