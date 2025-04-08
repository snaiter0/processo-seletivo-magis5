package io.git.portal.logistica.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableSpringDataWebSupport(pageSerializationMode = PageSerializationMode.VIA_DTO)
public class RestTemplateConfiguration {

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
