package com.william.news;

import com.google.common.base.Preconditions;
import com.william.news.dataaccess.NewsUserDetailsServiceImpl;
import com.william.news.rememberme.JpaPersistentTokenRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private final NewsUserDetailsServiceImpl newsUserDetailsService;
    private final JpaPersistentTokenRepository jpaPersistentTokenRepository;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(NewsUserDetailsServiceImpl newsUserDetailsService,
                          JpaPersistentTokenRepository jpaPersistentTokenRepository,
                          PasswordEncoder passwordEncoder) {
        Preconditions.checkNotNull(newsUserDetailsService, "NewsUserDetailService cannot be null");
        Preconditions.checkNotNull(jpaPersistentTokenRepository, "JpaPersistentTokenRepository cannot be null");
        Preconditions.checkNotNull(passwordEncoder, "PasswordEncoder cannot be null");
        this.newsUserDetailsService = newsUserDetailsService;
        this.jpaPersistentTokenRepository = jpaPersistentTokenRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(newsUserDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/signup/**",  "/login", "/logout", "/signin/**").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/h2/**").fullyAuthenticated()
                .anyRequest()
                .authenticated()

                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl("/authenticate")
                .failureUrl("/login?error")
                .defaultSuccessUrl("/news", true)


                .and()
                .logout()
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID")

                .and()
                .rememberMe()
                .rememberMeServices(rememberMeServices())

                .and()
                .csrf().disable().headers().frameOptions().sameOrigin();
    }


    public RememberMeServices rememberMeServices() {
        return new PersistentTokenBasedRememberMeServices("newsapp", newsUserDetailsService, jpaPersistentTokenRepository);

    }

    @Override
    public void configure(WebSecurity web)  {
        web
                .ignoring()
                .antMatchers("/resources/**");
    }


}
