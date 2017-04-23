package com.project.dao.implementation;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;

import com.project.pojo.Patient;
import com.project.pojo.Study;

public class PatientDAO implements com.project.dao.layer.PatientDAO{
	
	private static final Logger logger = Logger.getLogger(StudyDAO.class);

	@Override
	public Patient getPatientByID(String idPatient) {
		logger.info("Buscamos el estudio con idPatient: "+idPatient);
		
		Patient patient = new Patient();
		
		Session session = HibernateConnection.doHibernateConnection().openSession();
		
		Query query = session.createQuery("from Patient where idpatient = :idpatient ");
		query.setParameter("idpatient", idPatient);
		List<Patient> list = query.list();
		
		logger.info("Numero de pacientes encontrados: "+ list.size());
		
		if(list.size()==1){
			patient = (Patient) list.get(0);
			Hibernate.initialize(patient.getUsers());
		}
		
		session.close();
		return patient;
	}
	
	@Override
	public Patient getPatientByUser(int iduser) {
		logger.info("Buscamos el paciente que pertenezcan a un user: "+iduser);
		
		Patient patient = new Patient();
		
		Session session = HibernateConnection.doHibernateConnection().openSession();
		
		Query query = session.createQuery("from Patient where users.iduser = :iduser");
		query.setParameter("iduser", iduser);
		List<Patient> list = query.list();
		
		logger.info("Numero de pacientes encontrados: "+ list.size());
		
		if(list.size()==1){
			patient = (Patient) list.get(0);
			Hibernate.initialize(patient.getUsers());
		}
		
		session.close();
		return patient;
	}

	@Override
	public List<Patient> getAllPatients() {
		logger.info("Iniciamos getAllStudies()");
		Session session = HibernateConnection.doHibernateConnection().openSession();
		
		List<Patient> allPatient = session.createQuery("From Patient").list();

		logger.info("Numero de pacientes encontrados: "+ allPatient.size());
		for (Patient patient : allPatient) {
			Hibernate.initialize(patient.getUsers());
			Hibernate.initialize(patient.getStudies());
			logger.info("Nombre del paciente:"+patient.getUsers().getName() + " " + patient.getUsers().getSurename());
		}		
		
		session.close();
		
		return allPatient;
	}

	@Override
	public boolean modificaPatient(Patient patient) {
		try {
			Session session = HibernateConnection.doHibernateConnection().openSession();
			session.beginTransaction();

			// insertamos en la BBDD
			session.update(patient);

			session.getTransaction().commit();

			session.close();

			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean addPatient(Patient patient) {
		try {
			Session session = HibernateConnection.doHibernateConnection().openSession();
			session.beginTransaction();

			// insertamos en la BBDD
			session.save(patient);
			session.getTransaction().commit();
			session.close();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deletePatient(String idpatient) {
		try {
			Session session = HibernateConnection.doHibernateConnection().openSession();

			List<Patient> patient = session.createQuery("From Patient where idpatient='" + idpatient + "'").list();

			if (patient != null && patient.get(0) != null) {
				session.beginTransaction();
				session.delete(patient.get(0));
				session.getTransaction().commit();
				session.close();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
