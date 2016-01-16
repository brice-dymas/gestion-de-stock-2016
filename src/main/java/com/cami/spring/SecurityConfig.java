/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.spring;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author Brice GUEMKAM <briceguemkam@gmail.com>
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{

    @Autowired
    DataSource dataSource;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception
    {

        auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder())
                .usersByUsernameQuery(
                        "select username,password, enabled from users where enabled=true and username=?")
                .authoritiesByUsernameQuery(
                        "select user_username, role from user_roles where user_username=?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.authorizeRequests()
                .antMatchers("/index.xhtml").authenticated()
                .antMatchers("/agence/").authenticated()
                .antMatchers("/agence/new").authenticated()
                .antMatchers("/agence/**/edit").authenticated()
                .antMatchers("/agence/**/update").authenticated()
                .antMatchers("/agence/show").authenticated()
                .antMatchers("/categorie/").authenticated()
                .antMatchers("/categorie/new").authenticated()
                .antMatchers("/categorie/**/edit").authenticated()
                .antMatchers("/categorie/**/update").authenticated()
                .antMatchers("/categorie/show").authenticated()
                .antMatchers("/entree/").authenticated()
                .antMatchers("/entree/new").authenticated()
                .antMatchers("/entree/**/edit").authenticated()
                .antMatchers("/entree/**/update").authenticated()
                .antMatchers("/entree/show").authenticated()
                .antMatchers("/fournisseur/").authenticated()
                .antMatchers("/fournisseur/new").authenticated()
                .antMatchers("/fournisseur/**/edit").authenticated()
                .antMatchers("/fournisseur/**/update").authenticated()
                .antMatchers("/fournisseur/show").authenticated()
                .antMatchers("/lot/").authenticated()
                .antMatchers("/lot/new").authenticated()
                .antMatchers("/lot/**/edit").authenticated()
                .antMatchers("/lot/**/update").authenticated()
                .antMatchers("/lot/show").authenticated()
                .antMatchers("/service/").authenticated()
                .antMatchers("/service/new").authenticated()
                .antMatchers("/service/**/edit").authenticated()
                .antMatchers("/service/**/update").authenticated()
                .antMatchers("/service/show").authenticated()
                .antMatchers("/sortie/").authenticated()
                .antMatchers("/sortie/new").authenticated()
                .antMatchers("/sortie/**/edit").authenticated()
                .antMatchers("/sortie/**/update").authenticated()
                .antMatchers("/sortie/show").authenticated()
                .antMatchers("/user/**/edit").authenticated()
                .antMatchers("/user/**/editSimpleUser").authenticated()
                .antMatchers("/user/**/update").authenticated()
                .antMatchers("/user/**/**/updateSimpleUser").authenticated()
                .antMatchers("/user/**/show").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/user/**").access("hasRole('ROLE_ADMIN')")
                //                .antMatchers("/").authenticated()
                .and()
                .formLogin().loginPage("/login").failureUrl("/login?error")
                .usernameParameter("username").passwordParameter("password").and()
                .exceptionHandling().accessDeniedPage("/403")
                .and().logout().logoutSuccessUrl("/")
                .and().csrf();

    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }
}
