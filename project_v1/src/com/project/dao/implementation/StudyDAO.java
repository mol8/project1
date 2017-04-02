package com.project.dao.implementation;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;

import com.project.pojo.Study;

public class StudyDAO implements com.project.dao.layer.StudyDAO{
	
	private static final Logger logger = Logger.getLogger(StudyDAO.class);

	@Override
	public List<Study> getAllStudies() {
		logger.info("Iniciamos getAllStudies()");
		Session session = HibernateConnection.doHibernateConnection().openSession();
		
		List<Study> allStudies = session.createQuery("From Study").list();

		
		
		
		logger.info("Numero de estudios encontrados: "+ allStudies.size());
		for (Study study : allStudies) {
			Hibernate.initialize(study.getPatient());
			Hibernate.initialize(study.getPatient().getUsers());
			Hibernate.initialize(study.getEquipment());
			logger.info(study.getIdstudy() + " " + study.getScheduledProcedureStepStartDateTime().toLocaleString());
			logger.info("Nombre del paciente:"+study.getPatient().getUsers().getName());
		}		
		
		session.close();
		
		return allStudies;
	}

	@Override
	public Study getStudyByID(Integer idstudy) {				
		logger.info("Buscamos el estudio con idstudy: "+idstudy);
		
		Study study = new Study();
		
		Session session = HibernateConnection.doHibernateConnection().openSession();
		
		Query query = session.createQuery("from Study where idstudy = :idstudy ");
		query.setParameter("idstudy", idstudy);
		List<Study> list = query.list();
		
		logger.info("Numero de estudios encontrados: "+ list.size());
		
		if(list.size()==1){
			study = (Study) list.get(0);
			Hibernate.initialize(study.getPatient());
			Hibernate.initialize(study.getPatient().getUsers());
			Hibernate.initialize(study.getEquipment());
		}
		
		session.close();
		return study;
	}

	@Override
	public boolean modificaStudy(Study study) {
		try {
			Session session = HibernateConnection.doHibernateConnection().openSession();
			session.beginTransaction();

			// insertamos en la BBDD
			session.update(study);

			session.getTransaction().commit();

			session.close();

			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean addStudy(Study study) {
		try {
			Session session = HibernateConnection.doHibernateConnection().openSession();
			session.beginTransaction();

			// insertamos en la BBDD
			session.save(study);
			session.getTransaction().commit();
			session.close();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteStudy(Integer idstudy) {
		try {
			Session session = HibernateConnection.doHibernateConnection().openSession();

			List<Study> studies = session.createQuery("From Study where idstudy='" + idstudy + "'").list();

			if (studies != null && studies.get(0) != null) {
				session.beginTransaction();
				session.delete(studies.get(0));
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
