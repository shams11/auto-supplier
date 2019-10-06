
package com.auto.supplier.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import java.util.stream.Stream;

@Configuration
@EnableWebSecurity
@Profile("!prod")
public class DevWebSecurityConfig extends WebSecurityConfig {


}
