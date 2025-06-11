package com.es.prueba.security;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {

    @Autowired
    private RsaKeyProperties rsaKeys;

    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(rsaKeys.publicKey()).build();
    }

    @Bean
    public JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(rsaKeys.publicKey()).privateKey(rsaKeys.privateKey()).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // RUTAS PÚBLICAS
                        .requestMatchers("/login", "/register").permitAll()

                        // ENDPOINTS DE USUARIO
                        .requestMatchers(HttpMethod.PUT, "/usuarioUpdate").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/usuarioDelete/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/borrarMiCuenta").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/updateMiPerfil").authenticated()

                        // ENDPOINTS DE TAREAS
                        .requestMatchers(HttpMethod.POST, "/tareaRegister").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/mostrarTareas").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/buscarTareaNombre/{palabra}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/mostrarTareaId/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/eliminarTarea/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/insertarTarea").hasRole("ADMIN")

                        // ENDPOINTS DE DIRECCIÓN
                        .requestMatchers(HttpMethod.POST, "/direccionRegister").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/direccionUpdate").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/eliminarDireccion/{username}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/registerMiDireccion").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/updateMiDireccion").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/deleteMiDireccion").authenticated()

                        // RESTO DE RUTAS
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(AbstractHttpConfigurer::disable)
                .exceptionHandling(handling -> handling
                        .accessDeniedHandler(customAccessDeniedHandler)
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                )
                .build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter converter = new JwtGrantedAuthoritiesConverter();
        converter.setAuthorityPrefix("");
        converter.setAuthoritiesClaimName("roles");

        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(converter);
        return jwtConverter;
    }
}
