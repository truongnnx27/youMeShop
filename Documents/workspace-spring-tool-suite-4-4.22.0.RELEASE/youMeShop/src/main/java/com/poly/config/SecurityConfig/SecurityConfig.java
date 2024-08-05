package com.poly.config.SecurityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.poly.service.CustomUserDetailService;
import com.poly.service.MySimpleUrlAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private CustomUserDetailService customUserDetailService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
	{
			http
					.authorizeHttpRequests((requests) -> requests
							.requestMatchers("/","/register**","/productDetail**","/formLogin**","/category/**","/login**","/DetailProduct/**").permitAll()
							.requestMatchers("/cart/**").authenticated()
							.anyRequest().hasAuthority("ADMIN")
							//.anyRequest().permitAll()
					)
					.formLogin((form) ->form
							.loginPage("/formLogin")
							.loginProcessingUrl("/login")
							.successHandler(myAuthenticationSuccessHandler())
							.usernameParameter("email")
	                        .passwordParameter("password")
	                        .permitAll().usernameParameter("email")
	                        .passwordParameter("password")
	                        .permitAll()
					)
					.logout((logout)-> logout
							.logoutRequestMatcher((new AntPathRequestMatcher("/logout")))
							.logoutSuccessUrl("/")
							.permitAll());
			return http.build();
	}
	
	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder);
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/css/**", "/js/**", "/img/**", "/lib/**", "/vendor/**", "/fonts/**",  "/images/**");
    }
	
	@Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        return new MySimpleUrlAuthenticationSuccessHandler();
    }
	
	
}
