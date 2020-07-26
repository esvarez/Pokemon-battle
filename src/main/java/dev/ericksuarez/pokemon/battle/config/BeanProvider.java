package dev.ericksuarez.pokemon.battle.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ericksuarez.pokemon.battle.util.DamageDealer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.net.http.HttpClient;

@Configuration
public class BeanProvider {

    @Bean
    @Scope("singleton")
    public HttpClient httpClient() {
        return HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
    }

    @Bean
    @Scope("singleton")
    public ObjectMapper objectMapper() {
        return new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Bean
    @Scope("singleton")
    public DamageDealer damageDealer() {
        return new DamageDealer();
    }
}
