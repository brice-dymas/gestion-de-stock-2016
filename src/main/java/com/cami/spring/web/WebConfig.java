package com.cami.spring.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;

/**
 * @author gervais
 *
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages =
{
    "com.cami"
})
public class WebConfig extends WebMvcConfigurerAdapter
{

    /*
     * Configure View resolver to provide PDF output using lowagie pdf library
     * to generate PDF output for an object content
     */
    @Bean
    public InternalResourceViewResolver getInternalResourceViewResolver()
    {
        final InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/view/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    @Override
    public void configureContentNegotiation(final ContentNegotiationConfigurer configurer)
    {
        configurer.ignoreAcceptHeader(true).defaultContentType(MediaType.TEXT_HTML);
    }

    /**
     * Create the CNVR. Simplest setup - just pass in the
     * {@link ContentNegotiationManager}.
     *
     * @param manager The content negotiation manager to use.
     * @return A CNVR instance.
     */
    @Bean
    public ViewResolver contentNegotiatingViewResolver(final ContentNegotiationManager manager)
    {
        final ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
        resolver.setContentNegotiationManager(manager);
        final List<ViewResolver> resolvers = new ArrayList<>();
        resolvers.add(getInternalResourceViewResolver());
        resolver.setViewResolvers(resolvers);
        return resolver;
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry)
    {
        registry.addResourceHandler("/resources/**").addResourceLocations(
                "resources/bootstrap/");
    }

    @Bean
    public TilesConfigurer tilesConfigurer()
    {
        final TilesConfigurer tilesConfigurer = new TilesConfigurer();
        tilesConfigurer
                .setDefinitions(new String[]
                        {
                            "/WEB-INF/tiles/tiles-definitions.xml"
                });
        tilesConfigurer.setCheckRefresh(true);
        return tilesConfigurer;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor()
    {
        final LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("language");
        return localeChangeInterceptor;
    }

    @Bean(name = "localeResolver")
    public LocaleResolver sessionLocaleResolver()
    {
        final SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(new Locale("en"));

        return localeResolver;
    }

    @Bean
    public MultipartResolver multipartResolver()
    {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(5 * 1024 * 1024);
        return multipartResolver;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry)
    {
        registry.addInterceptor(localeChangeInterceptor());
    }

    @Bean
    public ReloadableResourceBundleMessageSource messageSource()
    {
        final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames(new String[]
        {
            "classpath:messages",
            "classpath:ValidationMessages",
            "WEB-INF/messages/agence/messages",
            "WEB-INF/messages/categorie/messages",
            "WEB-INF/messages/default/messages",
            "WEB-INF/messages/departement/messages",
            "WEB-INF/messages/entree/messages",
            "WEB-INF/messages/audit/messages",
            "WEB-INF/messages/perte/messages",
            "WEB-INF/messages/fourniture/messages",
            "WEB-INF/messages/ligneOperation/messages",
            "WEB-INF/messages/lot/messages",
            "WEB-INF/messages/operation/messages",
            "WEB-INF/messages/sortie/messages",
            "WEB-INF/messages/user/messages"
        });
        return messageSource;
    }

}
