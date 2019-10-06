package com.auto.supplier.security;

import com.auto.supplier.commons.security.CorsConfigurationHelper;
import com.auto.supplier.properties.AutoSupplierProperty;
import com.auto.supplier.services.impl.CustomUserAuthenticationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableJpaRepositories(basePackages = { "com.auto.supplier.entities",
    "com.auto.supplier.repositories" })
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Profile("prod")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private  CustomUserAuthenticationServiceImpl customUserAuthenticationService;

  @Autowired
  private  AutoSupplierProperty autoSupplierProperty;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .anyRequest().authenticated()
        .and()
        .httpBasic()
        .and()
        .csrf().disable();

    http.cors().configurationSource(corsConfiguration());

    http.authorizeRequests().anyRequest().authenticated();
  }

  CorsConfigurationSource corsConfiguration() {
    return CorsConfigurationHelper.createCorsConfiguration(autoSupplierProperty.getCors());
  }

  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder(11);
  }

  @Bean
  public DaoAuthenticationProvider authProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(customUserAuthenticationService);
    authProvider.setPasswordEncoder(encoder());
    return authProvider;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(authProvider());
  }
}
