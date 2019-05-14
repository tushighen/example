//package com.golomt.example.config;
//
//import com.golomt.example.resolver.ExcelViewResolver;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.MediaType;
//import org.springframework.web.accept.ContentNegotiationManager;
//import org.springframework.web.servlet.ViewResolver;
//import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
//import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
//import org.springframework.web.servlet.view.InternalResourceViewResolver;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Web Config @author Tushig
// */
//
//@Configuration
//public class WebConfig extends WebMvcConfigurerAdapter {
//
//    @Override
//    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
//        configurer
//                .defaultContentType(MediaType.APPLICATION_JSON)
//                .favorPathExtension(false);
//    }
//
//    @Override
//    public void configureViewResolvers(ViewResolverRegistry registry) {
//        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
//        resolver.setPrefix("/");
//        registry.viewResolver(resolver);
//    }
//
//    @Bean
//    public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
//        ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
//        resolver.setContentNegotiationManager(manager);
//
//        // Define all possible view resolvers
//        List<ViewResolver> resolvers = new ArrayList<>();
//
//        resolvers.add(excelViewResolver());
//        resolvers.add(indexViewResolver());
//
//        resolver.setViewResolvers(resolvers);
//        return resolver;
//    }
//
//    @Bean
//    public ViewResolver excelViewResolver() {
//        return new ExcelViewResolver();
//    }
//
//    @Bean
//    public ViewResolver indexViewResolver() {
//        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
//        resolver.setPrefix("/");
//        return resolver;
//    }
//
//}
