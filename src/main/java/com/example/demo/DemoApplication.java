package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.Date;
import java.util.List;

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
		JDBCUserDAO jdbcUserDAO = (JDBCUserDAO) context.getBean("jdbcUserDAO");
		try
		{
			User user1 = new User(1, "test", "1234", "test@gmail.com", new Date(1));
			jdbcUserDAO.insert(user1);
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
		User user2 = jdbcUserDAO.findById(1);
		System.out.println(user2);

		List<User> users = jdbcUserDAO.findAll();
		System.out.println(users);

		System.out.println(jdbcUserDAO.findUserNameById(1));
        System.out.println(jdbcUserDAO.findEmailById(2));
        System.out.println(jdbcUserDAO.findDateCreatedById(1));

		context.close();
	}
}