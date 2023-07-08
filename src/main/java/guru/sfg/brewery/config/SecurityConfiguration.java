package guru.sfg.brewery.config;

import guru.sfg.brewery.services.UserDetailService;
import guru.sfg.brewery.web.security.CustomPasswordEncoder;
import guru.sfg.brewery.web.security.RestHeaderAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailService userDetailService;

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
                                .antMatchers("/", "/webjars/**", "/resources/**").permitAll()
                                .antMatchers("/beers/find", "/beers*").hasAnyRole(Roles.ADMIN.name(), Roles.USER.name(), Roles.CUSTOMER.name())
//                                .antMatchers(HttpMethod.DELETE, "/api/v1/beer/**").hasAnyRole(Roles.ADMIN.name(), Roles.USER.name(), Roles.CUSTOMER.name())
                                .mvcMatchers(HttpMethod.GET, "/api/v1/beerUpc/{upc}").hasAnyRole(Roles.ADMIN.name(), Roles.USER.name(), Roles.CUSTOMER.name())
                                .antMatchers(HttpMethod.GET, "/api/v1/beer/**").hasAnyRole(Roles.ADMIN.name(), Roles.USER.name(), Roles.CUSTOMER.name())
                                .antMatchers("/api/v1/breweries/**").hasRole(Roles.CUSTOMER.name())
                                .antMatchers("/breweries/**").hasRole(Roles.CUSTOMER.name())
                                .antMatchers("/customers/**").hasAnyRole(Roles.ADMIN.name(), Roles.CUSTOMER.name())
                                .mvcMatchers("/brewery/breweries").hasAnyRole(Roles.ADMIN.name(), Roles.CUSTOMER.name()))
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin().and()
                .csrf().disable()
                .httpBasic();

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
