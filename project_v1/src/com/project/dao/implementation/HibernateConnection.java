package com.project.dao.implementation;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import com.project.pojo.Equipment;
import com.project.pojo.Patient;
import com.project.pojo.Study;
import com.project.pojo.Users;



public class HibernateConnection {
	
	public static SessionFactory sessionFactory;
	
	public static SessionFactory doHibernateConnection(){
		Properties database = new Properties();
		database.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
		database.setProperty("hibernate.connection.username", "root");
		database.setProperty("hibernate.connection.password", "r00t");
		database.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/minirisdb");
		database.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		
		Configuration cfg = new Configuration()
				.setProperties(database)
				.addPackage("com.project.pojo")
				.addAnnotatedClass(Users.class)
				.addAnnotatedClass(Study.class)
				.addAnnotatedClass(Patient.class)
				.addAnnotatedClass(Equipment.class);
		
		
		StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties());
		sessionFactory = cfg.buildSessionFactory(ssrb.build());
		
		return sessionFactory;
	}

}
