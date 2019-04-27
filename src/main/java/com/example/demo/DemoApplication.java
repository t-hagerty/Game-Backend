package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages="com.example.demo")
public class DemoApplication implements CommandLineRunner
{
    //@Autowired
    //JdbcTemplate jdbcTemplate;

	public static void main(String args[])
	{
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		SpringApplication.run(DemoApplication.class, args);
        context.close();
	}

	@Override
	public void run(String... strings) throws Exception
	{

	}
}