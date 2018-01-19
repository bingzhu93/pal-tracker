package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    private boolean FORCE_HTTPS;

    public SecurityConfiguration(@Value("${SECURITY_FORCE_HTTPS}") boolean force_https) {
        FORCE_HTTPS = force_https;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if (this.FORCE_HTTPS) {
            http.requiresChannel().anyRequest().requiresSecure();
        }
        http
                .authorizeRequests().antMatchers("/**").hasRole("USER")
                .and()
                .httpBasic()
                .and()
                .csrf().disable();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
           .inMemoryAuthentication()
           .withUser("user")
           .password("password")
           .roles("USER");
    }
}
