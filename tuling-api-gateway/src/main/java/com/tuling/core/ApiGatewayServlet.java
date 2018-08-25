package com.tuling.core;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Yuanp on 2018/8/25.
 */
public class ApiGatewayServlet extends HttpServlet {
    private ApplicationContext applicationContext;
    private ApiGatewayHandle apiGatewayHandle;

    @Override
    public void init() throws ServletException {
        applicationContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        apiGatewayHandle = applicationContext.getBean(ApiGatewayHandle.class);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        apiGatewayHandle.handle(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        apiGatewayHandle.handle(req, resp);
    }
}
