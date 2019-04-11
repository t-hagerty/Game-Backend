package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.Date;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner
{

	public static void main(String args[])
	{
		SpringApplication.run(DemoApplication.class, args);
	}

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public void run(String... strings) throws Exception
	{
		ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		UserDAO userDAO = (UserDAO) context.getBean("userDAO");
		User user1 = new User(1, "test", "1234", "test@gmail.com", new Date(1));
		userDAO.insert(user1);
		User user2 = userDAO.findById(1);
		System.out.println(user2);
		context.close();
	}
}