package com.jonquass.service.dropwizard.filter;

import com.jonquass.service.dropwizard.config.ApplicationConfig;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public record CrossOriginFilter(ApplicationConfig config) implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        httpResponse.addHeader("Access-Control-Allow-Origin", config.getAccessControlAllowOrigin());
        httpResponse.addHeader("Access-Control-Allow-Methods", "GET, OPTIONS");
        httpResponse.addHeader("Access-Control-Allow-Headers", "*");

        if (httpRequest.getMethod().equals("OPTIONS")) {
            httpResponse.setStatus(HttpServletResponse.SC_ACCEPTED);
            return;
        }

        chain.doFilter(request, response);
    }
}
