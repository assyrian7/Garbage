package com.peerbuds.testrestservice.ETC;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

public class Database {
	
	private static JdbcTemplate jdbcTemplate;
	private static ApplicationContext applicationContext;
	
	public static JdbcTemplate getTemplate(){
		applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		jdbcTemplate = (JdbcTemplate)applicationContext.getBean("jdbcTemplate");
		return jdbcTemplate;
	}
	
	
}

