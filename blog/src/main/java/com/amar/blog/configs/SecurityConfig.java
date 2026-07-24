package com.amar.blog.configs;

import com.amar.blog.security.JWTAuthenticationEntryPoint;
import com.amar.blog.security.JwtAuthenticationFilter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
@EnableMethodSecurity
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class SecurityConfig {


    private UserDetailsService userDetailsService;

    private JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Value("${app.cors.allowed-origins}")
    private String allowedOrigins;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService, JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public CorsConfigurationSource corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(List.of(allowedOrigins.split(",")));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowedMethods(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // Apply globally

        return source;
    }

   /* @Bean
    public CorsConfigurationSource corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        //CORS FOR ADMIN
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:4300"); // Allow Angular frontend origin
        config.addAllowedHeader("*"); // Allow all headers
        config.addAllowedMethod("*"); // Allow all HTTP methods
        source.registerCorsConfiguration("/**", config);

        //CORS FOR CLIENT
        CorsConfiguration clientConfig = new CorsConfiguration();
        clientConfig.setAllowedOrigins(List.of("http://localhost:4200"));
        clientConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        clientConfig.setAllowedHeaders(List.of("*"));
        clientConfig.setAllowCredentials(true);
        source.registerCorsConfiguration("/api/client/**", clientConfig);

        return source;
    }*/
    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors(c -> c.configurationSource(corsFilter()))
                .csrf().disable()
                .authorizeHttpRequests((authorize) -> {
                    //authorize.anyRequest().authenticated()
                authorize
                        .requestMatchers("/api/client/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/admin/**").permitAll()
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/**").permitAll()
                        .anyRequest().authenticated();
                }).httpBasic(Customizer.withDefaults())
                .exceptionHandling((exception) -> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint)
                ).sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));


        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

   /* commenting as we are going to implement database authentication instead of Inmemory
   @Bean
    public UserDetailsService userDetailsService() {
        UserDetails clientUser = User.builder().username("client").password(passwordEncoder().encode("client")).roles("USER").build();
        UserDetails adminUser = User.builder().username("admin").password(passwordEncoder().encode("admin")).roles("ADMIN").build();

        //putting plain password doesnot work with spring security so please configure Password encoder.
        return new InMemoryUserDetailsManager(clientUser, adminUser);
    }*/


}
