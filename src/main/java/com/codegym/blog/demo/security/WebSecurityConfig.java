package com.codegym.blog.demo.security;

import com.codegym.blog.demo.model.Entity.User;
import com.codegym.blog.demo.repository.UserRepository;
import com.codegym.blog.demo.service.ActionService.UserService;
import com.codegym.blog.demo.service.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.PostConstruct;
import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @PostConstruct
    public void init() {
        List<User> users = (List<User>) userRepository.findAll();
        if (users.isEmpty()) {
            User user = new User();
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encoder().encode("admin"));
            userRepository.save(user);
        }
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public RestAuthenticationEntryPoint restServicesEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(passwordEncoder.encoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().ignoringAntMatchers("/**");
        http.httpBasic().authenticationEntryPoint(restServicesEntryPoint());
        http.authorizeRequests()
                .antMatchers("/", "/login","/register").permitAll()
                .antMatchers(HttpMethod.PUT,"/blogs").hasAnyAuthority("MEMBER", "ADMIN")
                .antMatchers(HttpMethod.PATCH,"/blogs").hasAnyAuthority("MEMBER", "ADMIN")
                .antMatchers(HttpMethod.DELETE,"/blogs").hasAnyAuthority("MEMBER", "ADMIN")
                .antMatchers(HttpMethod.POST,"/blogs").hasAnyAuthority("MEMBER", "ADMIN")
                .antMatchers(HttpMethod.GET,"/blogs/personal").hasAnyAuthority("MEMBER","ADMIN")
                .antMatchers(HttpMethod.DELETE,"/profile").hasAnyAuthority("MEMBER","ADMIN")
                .antMatchers("/admin").hasAnyAuthority("ADMIN")
                .and().csrf().disable();
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler());
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.cors();
    }
}
