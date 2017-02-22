package com.overseer.config;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * Web configuration.
 */
@Configuration
public class WebConfiguration implements EmbeddedServletContainerCustomizer {

    /**
     * The paths which must be resolved by angular have nothing to do with backend, thus
     * backend has no knowledge which paths angular is aware of and which are not.
     * If a resource is not resolved by the controllers and resource handlers this is
     * <code>HttpStatus.NOT_FOUND</code> case for backend. This configuration allows angular
     * to take care of navigation on the app.
     *
     * @param configurableEmbeddedServletContainer container.
     */
    @Override
    public void customize(ConfigurableEmbeddedServletContainer configurableEmbeddedServletContainer) {
        configurableEmbeddedServletContainer.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/index.html"));
    }
}
