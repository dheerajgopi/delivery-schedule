package com.logistics.deliveryscheduler.config;

import java.util.List;

import com.logistics.deliveryscheduler.common.RequestContext;
import com.logistics.deliveryscheduler.config.interceptor.GlobalInterceptors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Contains MVC configurations like interceptors, content negotiation, general parameter settings etc.
 */
@Configuration
public class CustomizedWebConfig implements WebMvcConfigurer {

    /**
     * Stores request scoped data.
     */
    private RequestContext requestContext;

    /**
     * List of all global interceptors.
     */
    private List<GlobalInterceptors> globalInterceptors;

    @Autowired
    public CustomizedWebConfig(
            final RequestContext requestContext,
            final List<GlobalInterceptors> globalInterceptors
    ) {
        this.requestContext = requestContext;
        this.globalInterceptors = globalInterceptors;
    }

    /**
     * Add interceptors to the registry.
     *
     * @param registry
     */
    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        this.globalInterceptors.forEach(registry::addInterceptor);
    }

    /**
     * Content negotiation settings.
     *
     * @param configurer
     */
    @Override
    public void configureContentNegotiation(final ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.APPLICATION_JSON);
    }

}
