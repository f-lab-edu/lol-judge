package edu.flab.global.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import edu.flab.global.filter.LoginCheckFilter;
import jakarta.servlet.Filter;

@Configuration
public class FilterRegisterConfiguration {
	@Bean
	public FilterRegistrationBean<Filter> loginCheckFilter() {
		FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
		filterFilterRegistrationBean.setFilter(new LoginCheckFilter());
		filterFilterRegistrationBean.setOrder(1);
		filterFilterRegistrationBean.addUrlPatterns("/api/*");
		return filterFilterRegistrationBean;
	}
}
