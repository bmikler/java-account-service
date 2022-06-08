package account.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import static account.user.model.UserRole.*;


@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable().headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers("/actuator/shutdown").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/api/auth/signup").permitAll()
                .antMatchers(HttpMethod.POST,"/api/auth/changepass").hasAnyRole(USER.name(), ACCOUNTANT.name(), ADMINISTRATOR.name())
                .antMatchers(HttpMethod.GET, "/api/empl/payment").hasAnyRole(USER.name(), ACCOUNTANT.name())
                .antMatchers(HttpMethod.POST, "/api/acct/payments").hasRole(ACCOUNTANT.name())
                .antMatchers(HttpMethod.PUT, "/api/acct/payments").hasRole(ACCOUNTANT.name())
                .antMatchers( "/api/admin/**").hasRole(ADMINISTRATOR.name())
                .antMatchers(HttpMethod.GET,"/api/security/events").hasRole(AUDITOR.name())
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .and()
                .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.
                authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);

        return provider;
    }
}
