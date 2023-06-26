package guru.sfg.brewery.config;

import guru.sfg.brewery.web.security.CustomPasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests(expressionInterceptUrlRegistry ->
                        expressionInterceptUrlRegistry
                                .antMatchers("/", "/webjars/**", "/resources/**").permitAll()
                                .antMatchers(HttpMethod.GET, "/api/v1/beer/**").permitAll()
                                .antMatchers(HttpMethod.GET, "/api/v1/beerUpc/**").permitAll())
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin().and()
                .httpBasic();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("admin").password("{bcrypt}$2a$10$l/zJs2Mz/UDMxTxoHCrBiebKmlobzr2CIHs1zE.IA6BXSWRkVHCzK").roles("ADMIN");
        auth.inMemoryAuthentication().withUser("customer").password("{ldap}{SSHA}FIssqgp+nDV56gFV+WiJ2iUuEU1G7sJb46umKQ==").roles("CUSTOMER");
        auth.inMemoryAuthentication().withUser("user").password("{sha256}17cf776648dce8a2065ba5b6085901740a7f54939985bb887686c29050f19ad682cced7908ccfcd9").roles("CUSTOMER");
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return CustomPasswordEncoder.createDelegatingPasswordEncoder();
    }
}
