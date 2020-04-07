package com.javapath.springbootsecurityapp.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Inmemory authentication
        /*
        auth.inMemoryAuthentication()
                .withUser("user")
                .password("user")
                .roles("USER")
                .and()
                .withUser("admin")
                .password("admin")
                .roles("ADMIN")
                .and()
                .withUser("super")
                .password("super")
                .roles("SUPER");*/

        // jdbc authentication
        auth.jdbcAuthentication()
                .dataSource(dataSource) //; method2: load users using schema.sql, data.sql with default schema, then spring security willfetch data user data out of the box
                // method4: Incase if we do not use default schema, still we can write query based on our own schema as follows
                .usersByUsernameQuery("select username,password,enabled"
                                + "from users"
                                + "where username = ?")
                .authoritiesByUsernameQuery("selct username,authority"
                                + "from authorities"
                                + "where username=?");
                /* method1: adding users in program itself
                .withDefaultSchema()
                .withUser(
                        User.withUsername("admin")
                        .password("admin")
                        .roles("ADMIN")
                )
                .withUser(
                        User.withUsername("super")
                                .password("super")
                                .roles("SUPER")
                )
                .withUser(
                        User.withUsername("user")
                                .password("user")
                                .roles("USER")
                );*/

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/super").hasAnyRole("SUPER","ADMIN")
                .antMatchers("/user").hasAnyRole("USER","ADMIN","SUPER")
                .antMatchers("/").permitAll()
                .and()
                .formLogin();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
}
