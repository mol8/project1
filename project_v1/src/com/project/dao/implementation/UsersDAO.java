package com.project.dao.implementation;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Session;

import com.project.pojo.Patient;
import com.project.pojo.Users;

public class UsersDAO implements com.project.dao.layer.UsersDAO{
	
	private static final Logger logger = Logger.getLogger(UsersDAO.class);

	@Override
	public Users getUserByUserName(String username) {
		try{
			Session session = HibernateConnection.doHibernateConnection().openSession();
			Users user = (Users) session.createQuery("From Users where username='"+username+"'").uniqueResult();
			return user;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean addUser(Users user) {
		try {
			Session session = HibernateConnection.doHibernateConnection().openSession();
			session.beginTransaction();

			// insertamos en la BBDD
			session.save(user);
			session.getTransaction().commit();
			session.close();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		
	}

	@Override
	public List<Users> getAllUsers() {
		logger.info("Iniciamos getAllStudies()");
		Session session = HibernateConnection.doHibernateConnection().openSession();
		
		List<Users> allUsers = session.createQuery("From Users").list();
		
		logger.info("Numero de pacientes encontrados: "+ allUsers.size());
		for (Users user : allUsers) {
			Hibernate.initialize(user.getPatients());
			logger.info("Nombre del paciente:"+user.getName() + " " + user.getSurename());
		}		
		
		session.close();	
		return allUsers;
	}

	@Override
	public boolean deleteUser(Integer iduser) {
		try {
			Session session = HibernateConnection.doHibernateConnection().openSession();

			List<Users> usuarios =(List<Users>) session.createQuery("From Users where iduser='" + iduser + "'").list();

			if (usuarios != null && usuarios.get(0) != null) {
				session.beginTransaction();
				session.delete(usuarios.get(0));
				session.getTransaction().commit();
				session.close();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}

	@Override
	public boolean updateUser(Users user) {
		try {
			Session session = HibernateConnection.doHibernateConnection().openSession();
			session.beginTransaction();

			// insertamos en la BBDD
			session.update(user);

			session.getTransaction().commit();

			session.close();

			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Users getUserByID(Integer iduser) {
		try{
			Session session = HibernateConnection.doHibernateConnection().openSession();
			Users user = (Users) session.createQuery("From Users where iduser='"+iduser+"'").uniqueResult();
			session.close();
			return user;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

}
