/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.spring.core;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 *
 * @author Brice GUEMKAM <briceguemkam@gmail.com>
 * this class will load the springSecurityFilterChain automatically so no need
 * to write it again in rh web.xml file
 */
public class SpringSecurityInitializer extends AbstractSecurityWebApplicationInitializer
{

    @Override
    protected void beforeSpringSecurityFilterChain(ServletContext servletContext)
    {
        FilterRegistration.Dynamic characterEncodingFilter = servletContext.addFilter("encodingFilter", new CharacterEncodingFilter());
        characterEncodingFilter.setInitParameter("encoding", "UTF-8");
        characterEncodingFilter.setInitParameter("forceEncoding", "true");
        characterEncodingFilter.addMappingForUrlPatterns(null, false, "/*");
    }

}
