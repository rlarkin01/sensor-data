package com.example.sensordata;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component

public class RequestLoggingFilter implements Filter {
    private static final Logger logger = LogManager.getLogger("SensorData");
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String apiVersion = req.getHeader("X-API-Version");
        String agent = req.getHeader("user-agent");
        logger.info("I'm running ${java:runtime}");
        logger.info("Saw user agent: " + agent);
        logger.info("Saw API Version " + apiVersion);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
