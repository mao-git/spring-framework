package com.devs4j.di;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import com.devs4j.di.lifecycle.ExplicitBean;
import com.devs4j.di.lifecycle.LifeCycleBean;

@SpringBootApplication
public class SpringFrameworkInicialApplication {

	@Bean(initMethod = "init", destroyMethod = "destroy")
	public ExplicitBean getBean() {
		return new ExplicitBean();
	}
	
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(SpringFrameworkInicialApplication.class, args);
		LifeCycleBean bean = context.getBean(LifeCycleBean.class);
	}

}
