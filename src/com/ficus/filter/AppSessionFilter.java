package com.ficus.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import com.ficus.app.AppKey;
import com.ficus.util.SessionUtil;

@WebFilter("/*")
public final class AppSessionFilter implements Filter {

    /**
     * Default constructor. 
     */
    public AppSessionFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		SessionUtil.currentSession().put(AppKey.SERVLET_SESSION, ((HttpServletRequest)request).getSession(true));
		SessionUtil.currentSession().put(AppKey.SERVLET_REQUEST, request);
		SessionUtil.currentSession().put(AppKey.SERVLET_RESPONSE, response);
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
