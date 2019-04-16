package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.sql.Date;
import java.util.ArrayList;
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
            User user2= new User(2, "bob", "abcd", "bob@aol.com", new Date(119, 4, 12));
            List<User> newUsers = new ArrayList<>();
            newUsers.add(user1);
            newUsers.add(user2);
            jdbcUserDAO.insertBatch1(newUsers);
            System.out.println("Inserted rows: " + newUsers);
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
        System.out.println(" FindAll: " + jdbcUserDAO.findAll());
        jdbcUserDAO.insertBatch2("UPDATE USERS SET PASSWORD ='aBetterPassword'");
		List<User> users = jdbcUserDAO.findAll();
		System.out.println("Updated: " + users);

        JDBCLevelDAO jdbcLevelDAO = (JDBCLevelDAO) context.getBean("jdbcLevelDAO");
        try
        {
            byte[] blob1 = new byte[] {0x12, 0x34, 0x56, 0x78};
            Level level1 = new Level(1, 2, "testMap", new Date(2), 9.5f, 0.50f, new SerialBlob(blob1));
            byte[] blob2 = new byte[] {0x65, 0x43, 0x21};
            Level level2 = new Level(2, 2, "demoMap", new Date(120, 1, 1), 2.7f, 0.98f, new SerialBlob(blob2));
            List<Level> newLevels = new ArrayList<>();
            newLevels.add(level1);
            newLevels.add(level2);
            byte[] blob3 = new byte[] {0x00, 0x00, 0x00};
            Level level3 = new Level(3, 1, "trialMap", new Date(3), 5.7f, 1.0f, new SerialBlob(blob3));
            jdbcLevelDAO.insertBatch1(newLevels);
            jdbcLevelDAO.insert(level3);
            System.out.println("Inserted rows: " + newLevels);
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
        Level level4 = jdbcLevelDAO.findById(1);
        System.out.println(level4);
        System.out.println(" FindAll: " + jdbcLevelDAO.findAll());
        jdbcUserDAO.insertBatch2("UPDATE LEVELS SET NAME ='easy_level'");
        List<Level> levels = jdbcLevelDAO.findAll();
        System.out.println("Updated: " + levels);
        System.out.println(jdbcLevelDAO.findAuthorIdById(1));
        System.out.println(jdbcUserDAO.findById(jdbcLevelDAO.findAuthorIdById(1)));
        System.out.println(jdbcLevelDAO.findNameById(1));
        System.out.println(jdbcLevelDAO.findDateSubmittedById(1));
        System.out.println(jdbcLevelDAO.findRatingById(1));
        System.out.println(jdbcLevelDAO.findPercentWonById(1));
        System.out.println(jdbcLevelDAO.findLevelMapById(1));
        jdbcUserDAO.insertBatch2("TRUNCATE TABLE LEVELS");
        System.out.println(" Cleared: " + jdbcLevelDAO.findAll());
        jdbcUserDAO.insertBatch2("DELETE FROM USERS");
        System.out.println(" Cleared: " + jdbcUserDAO.findAll());
		context.close();
	}
}