package com.moseeker.servicemanager.config;

import javax.servlet.*;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Created by lucky8987 on 17/5/22.
 * spring mvc 简化web.xml配置
 */
public class WebInitializer implements WebApplicationInitializer {

    /**
     * 初始化spring mvc配置，优先加载
     * @param container
     */
    private void initializeSpringMVCConfig(ServletContext container) {
        AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();

        dispatcherContext.register(WebConfig.class);
        ServletRegistration.Dynamic dispatcher = container.addServlet("service manager", new DispatcherServlet(dispatcherContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }

    /**
     * 初始化过滤器
     * @param container
     */
    private void initializeFilter(ServletContext container) {
        FilterRegistration.Dynamic filterRegistration = container.addFilter("charset", CharacterEncodingFilter.class);
        filterRegistration.setInitParameter("encoding", "UTF-8");
        filterRegistration.setInitParameter("forceEncoding", "true");
        filterRegistration.addMappingForUrlPatterns(null, false, "/*");

    }


    /**
     * servlet 容器启动方法
     * @param servletContext
     * @throws ServletException
     */
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        initializeFilter(servletContext);
        initializeSpringMVCConfig(servletContext);
    }
}
