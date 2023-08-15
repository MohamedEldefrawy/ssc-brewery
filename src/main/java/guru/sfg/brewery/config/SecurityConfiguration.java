package guru.sfg.brewery.config;

import guru.sfg.brewery.services.UserDetailService;
import guru.sfg.brewery.web.security.CustomPasswordEncoder;
import guru.sfg.brewery.web.security.RestHeaderAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailService userDetailService;

    @Bean
    public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
        return new SecurityEvaluationContextExtension();
    }

    public RestHeaderAuthFilter restHeaderAuthFilter(AuthenticationManager authenticationManager) {
        RestHeaderAuthFilter restHeaderAuthFilter = new RestHeaderAuthFilter(new AntPathRequestMatcher("/api/**"));
        restHeaderAuthFilter.setAuthenticationManager(authenticationManager);
        return restHeaderAuthFilter;

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(restHeaderAuthFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);
        http
                .authorizeRequests(expressionInterceptUrlRegistry ->
                        expressionInterceptUrlRegistry
                                .antMatchers("/h2-console/**").permitAll()
                                .antMatchers("/", "/webjars/**", "/resources/**").permitAll())
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin().and()
                .csrf().ignoringAntMatchers("/h2-console/**", "/api/**");

        http.headers().frameOptions().sameOrigin();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailService).passwordEncoder(passwordEncoder());
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return CustomPasswordEncoder.createDelegatingPasswordEncoder();
    }
}
