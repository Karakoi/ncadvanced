package com.overseer.config;

import com.overseer.service.impl.report.resolver.PdfViewResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;

import java.util.ArrayList;
import java.util.List;

/**
 * Web MVC configuration.
 */
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

    /**
     * Configure default media type by {@link ContentNegotiationConfigurer}.
     *
     * @param configurer negotiation configurer
     */
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer
                .defaultContentType(MediaType.APPLICATION_JSON)
                .favorPathExtension(true);
    }

    /**
     * Configure Content negotiating view resolver.
     *
     * @return configured resolver
     */
    @Bean
    public ViewResolver contentNegotiatingViewResolver() {
        ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();

        List<ViewResolver> resolvers = new ArrayList<>();

        resolvers.add(pdfViewResolver());

        resolver.setViewResolvers(resolvers);
        return resolver;
    }

    /**
     * Configure View resolver to provide Pdf output using iText library to
     * generate pdf output for an object content.
     *
     * @return pdf view resolver
     */
    @Bean
    public ViewResolver pdfViewResolver() {
        return new PdfViewResolver();
    }
}
