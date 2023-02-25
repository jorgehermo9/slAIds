package es.hackUDC.slAIds.rest.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// @Configuration
// @EnableWebSecurity
// public class SecurityConfig {

//   // @Bean
//   // public AuthenticationManager
//   // authenticationManager(AuthenticationConfiguration authConfig) throws
//   // Exception {
//   // final List<GlobalAuthenticationConfigurerAdapter> configurers = new
//   // ArrayList<>();
//   // configurers.add(new GlobalAuthenticationConfigurerAdapter() {
//   // @Override
//   // public void configure(AuthenticationManagerBuilder auth) throws Exception {
//   // // auth.doSomething()
//   // }
//   // });
//   // return authConfig.getAuthenticationManager();
//   // }
//   @Bean
//   public AuthenticationManager authenticationManagerCustom(HttpSecurity http) throws Exception {
//     return http.getSharedObject(AuthenticationManagerBuilder.class)
//         .build();
//   }

//   @Bean
//   public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

//     return http
//         // other configuration options
//         .authorizeHttpRequests(authCustomizer -> authCustomizer
//             .requestMatchers(HttpMethod.POST, "/api/subscriptions").permitAll())
//         .build();
//   }

//   @Autowired
//   SecurityFilterChain securityFilterChain;

//   protected void configure(HttpSecurity http) throws Exception {

//     http.cors().and().csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//         .addFilter(new JwtFilter(authenticationManagerCustom, jwtGenerator))
//         // Authorize GET to /chat/prompt GET request with sprin 3.0 security
//         .authorizeRequests().requestMatchers("/chat/prompt").permitAll();
//   }

//   @Bean
//   public CorsConfigurationSource corsConfigurationSource() {

//     CorsConfiguration config = new CorsConfiguration();
//     UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

//     config.setAllowCredentials(true);
//     config.setAllowedOriginPatterns(Arrays.asList("*"));
//     config.addAllowedHeader("*");
//     config.addAllowedMethod("*");

//     source.registerCorsConfiguration("/**", config);

//     return source;

//   }

// }

@Configuration
public class SecurityConfig {

  @Bean
  @Order(1)
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.cors().and().csrf().disable()
        .authorizeHttpRequests((authorize) -> authorize
            .requestMatchers("/users/**").permitAll()
            .anyRequest().authenticated())
        .apply(new MyCustomDsl());
    return http.build();
  }
}

class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
  @Autowired
  private JwtGenerator jwtGenerator;

  @Override
  public void configure(HttpSecurity http) throws Exception {
    AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
    http.addFilter(new JwtFilter(authenticationManager, jwtGenerator));
  }

  public static MyCustomDsl customDsl() {
    return new MyCustomDsl();
  }
}
