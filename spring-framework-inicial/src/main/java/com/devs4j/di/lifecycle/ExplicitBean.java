package com.devs4j.di.lifecycle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;

public class ExplicitBean {
	
	private static final Logger log = LoggerFactory.getLogger(ExplicitBean.class);

	public void init() {
		log.info("Init method");
	}
	
	public void destroy() {
		log.info("Destroy method");
	}
}
