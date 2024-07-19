package com.domjur.alltime.auth.config;

import lombok.Data;
import org.jboss.resteasy.client.jaxrs.internal.ResteasyClientBuilderImpl;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {

    @Value("${keycloak.serverurl}")
    public String serverUrl;
    @Value("${keycloak.realm}")
    public String realm;
    @Value("${keycloak.clientId}")
    public String clientId;
    @Value("${keycloak.clientSecret}")
    public String clientSecret;
    @Value("${keycloak.userName}")
    public String userName;
    @Value("${keycloak.password}")
    public String password;


    public KeycloakConfig() {
    }
    @Bean
    public Keycloak keycloak(){

            Keycloak keycloak = KeycloakBuilder.builder()
                    .serverUrl(serverUrl)
                    .realm(realm)
                    .grantType(OAuth2Constants.PASSWORD)
                    .username(userName)
                    .password(password)
                    .clientId(clientId)
                    .clientSecret(clientSecret)
                    .resteasyClient( new ResteasyClientBuilderImpl()
                            .connectionPoolSize(10)
                            .build()
                    )
                    .build();
        return keycloak;
    }
}