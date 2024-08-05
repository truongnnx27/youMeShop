package com.poly.service;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import aj.org.objectweb.asm.Handle;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class MySimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler{
	
	protected Log logger= LogFactory.getLog(this.getClass());
	private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			org.springframework.security.core.Authentication authentication) throws IOException, ServletException {
		
		
		handle(request, response, authentication);
        clearAuthenticationAttributes(request);
		
		
	}

	
	private void handle(HttpServletRequest request, 
						HttpServletResponse response,
						org.springframework.security.core.Authentication authentication) throws IOException{
		String targetUrl= determineTargetUrl(authentication);
		
		if (response.isCommitted()) {
			logger.debug("Response has already been committed. Unable to redirect to "+ targetUrl);
			return;
		}
		
		redirectStrategy.sendRedirect(request, response, targetUrl);
		
	}

	private String determineTargetUrl(final org.springframework.security.core.Authentication authentication) {
		Map<String, String> roleTargetUrlMap= new HashMap<>();
		roleTargetUrlMap.put("USER", "/");
		roleTargetUrlMap.put("ADMIN", "/admin/form");
		
		final Collection<? extends GrantedAuthority> authorities= authentication.getAuthorities();
		for(final GrantedAuthority grantedAuthority : authorities)
		{
			String authorityName= grantedAuthority.getAuthority();
			if (roleTargetUrlMap.containsKey(authorityName)) {
				return roleTargetUrlMap.get(authorityName);
			}
		}
		throw new IllegalStateException();
	}

	private void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session= request.getSession();
		if (session== null) {
			return;
		}
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		
	}

	

	
	
}
