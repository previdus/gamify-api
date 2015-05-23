package com.core.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.core.constants.GameConstants;
import com.core.domain.User;

public class AuthenticatonFilter implements Filter {

	private static final Logger log = LoggerFactory
			.getLogger(AuthenticatonFilter.class);

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		final String requestUri = httpRequest.getRequestURI();
		log.info("requestUri in AuthenticationFilter is:" + requestUri);
		if (requestUri.contains("login") || requestUri.contains("/api/"))
			chain.doFilter(request, response);
		else {
			HttpSession session = ((HttpServletRequest) request).getSession();
			User user = (User) session
					.getAttribute(GameConstants.SESSION_VARIABLE_LOGGEDIN_USER);
			if (user != null) {
				chain.doFilter(request, response);
			} else {
				((HttpServletResponse) response).sendRedirect("login");
			}
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
	}
}
