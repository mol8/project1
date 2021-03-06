package com.project.dao.implementation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;

import com.project.pojo.Study;

public class StudyDAO implements com.project.dao.layer.StudyDAO {

	private static final Logger logger = Logger.getLogger(StudyDAO.class);

	@Override
	public List<Study> getAllStudies() {
		logger.info("Iniciamos getAllStudies()");
		Session session = HibernateConnection.doHibernateConnection().openSession();

		List<Study> allStudies = session.createQuery("From Study").list();

		logger.info("Numero de estudios encontrados: " + allStudies.size());
		for (Study study : allStudies) {
			Hibernate.initialize(study.getPatient());
			Hibernate.initialize(study.getPatient().getUsers());
			Hibernate.initialize(study.getEquipment());
			logger.info(study.getIdstudy() + " " + study.getScheduledProcedureStepStartDateTime().toLocaleString());
			logger.info("Nombre del paciente:" + study.getPatient().getUsers().getName());
		}

		session.close();

		return allStudies;
	}
	
	@Override
	public List<Study> getTodayStudies() {
		logger.info("Iniciamos getTodayStudies()");
		Session session = HibernateConnection.doHibernateConnection().openSession();
		
		//Obtenemos la fecha del dia de hoy
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date inicioHoy = new Date();
		inicioHoy.setHours(0);
		inicioHoy.setMinutes(0);
		inicioHoy.setSeconds(0);
		
		Date finHoy = new Date();
		finHoy.setHours(23);
		finHoy.setMinutes(59);
		finHoy.setSeconds(59);
		
		String inicioHoyString = dateFormat.format(inicioHoy);
		String finHoyString = dateFormat.format(finHoy);
		
		logger.info("Hora inicio hoy: "+inicioHoyString);
		logger.info("Hora fin hoy: "+finHoyString);

		Query query = session.createQuery("From Study where scheduledProcedureStepStartDateTime >= :inicioHoy AND scheduledProcedureStepEndDateTime <= :finHoy AND status <> 'CANCELADO'");
		query.setParameter("inicioHoy", inicioHoy);
		query.setParameter("finHoy", finHoy);
		
		List<Study> todayStudies = query.list();
		
		logger.info("Numero de estudios encontrados: " + todayStudies.size());
		for (Study study : todayStudies) {
			Hibernate.initialize(study.getPatient());
			Hibernate.initialize(study.getPatient().getUsers());
			Hibernate.initialize(study.getEquipment());
			logger.info(study.getIdstudy() + " " + study.getScheduledProcedureStepStartDateTime().toLocaleString());
			logger.info("Nombre del paciente:" + study.getPatient().getUsers().getName());
		}

		session.close();

		return todayStudies;
	}

	@Override
	public Study getStudyByID(Integer idstudy) {
		logger.info("Buscamos el estudio con idstudy: " + idstudy);

		Study study = new Study();

		Session session = HibernateConnection.doHibernateConnection().openSession();

		Query query = session.createQuery("from Study where idstudy = :idstudy ");
		query.setParameter("idstudy", idstudy);
		List<Study> list = query.list();

		logger.info("Numero de estudios encontrados: " + list.size());

		if (list.size() == 1) {
			study = (Study) list.get(0);
			Hibernate.initialize(study.getPatient());
			Hibernate.initialize(study.getPatient().getUsers());
			Hibernate.initialize(study.getEquipment());
		}

		session.close();
		return study;
	}
	
	@Override
	public Study getStudyByDicomStudyInstanceUid(String dicomStudyInstanceUid) {
		logger.info("Buscamos el estudio con dicomStudyInstanceUid: " + dicomStudyInstanceUid);

		Study study = new Study();

		Session session = HibernateConnection.doHibernateConnection().openSession();

		Query query = session.createQuery("from Study where dicomStudyInstanceUid = :dicomStudyInstanceUid ");
		query.setParameter("dicomStudyInstanceUid", dicomStudyInstanceUid);
		List<Study> list = query.list();

		logger.info("Numero de estudios encontrados: " + list.size());

		if (list.size() == 1) {
			study = (Study) list.get(0);
			Hibernate.initialize(study.getPatient());
			if(study.getPatient()==null){
				//nada
			}else{
				Hibernate.initialize(study.getPatient().getUsers());
			}
				
			Hibernate.initialize(study.getEquipment());
		}

		session.close();
		return study;
	}

	@Override
	public List<Study> getStudyPROGRAMADOS() {
		logger.info("Buscamos todos los estudios con status: PROGRAMADO");

		Session session = HibernateConnection.doHibernateConnection().openSession();

		Query query = session.createQuery("from Study where status = :status");
		query.setParameter("status", "PROGRAMADO");
		List<Study> allStudies = query.list();

		logger.info("Numero de estudios encontrados: " + allStudies.size());
		for (Study study : allStudies) {
			Hibernate.initialize(study.getPatient());
			Hibernate.initialize(study.getPatient().getUsers());
			Hibernate.initialize(study.getEquipment());
			logger.info(study.getIdstudy() + " " + study.getScheduledProcedureStepStartDateTime().toLocaleString());
			logger.info("Nombre del paciente:" + study.getPatient().getUsers().getName());
		}

		session.close();

		return allStudies;
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

	@Override
	public List<Study> getStudyEquipoHorario(int idequipment, Date startTime, Date endTime) {
		logger.info("Buscamos si existen estudios programados para la misma maquina en el mismo horario");
		Session session = HibernateConnection.doHibernateConnection().openSession();

		Query query = session.createQuery(
				"from Study where idequipment = :idequipment and NOT( (:startTime < scheduledProcedureStepStartDateTime and :endTime <= scheduledProcedureStepStartDateTime)OR(:startTime >= scheduledProcedureStepEndDateTime and :endTime > scheduledProcedureStepEndDateTime))");
		query.setParameter("idequipment", idequipment);
		query.setParameter("startTime", startTime);
		query.setParameter("endTime", endTime);
		List<Study> studies = query.list();

		logger.info("Numero de estudios encontrados: " + studies.size());
		/*
		 * for (Study study : allStudies) {
		 * Hibernate.initialize(study.getPatient());
		 * Hibernate.initialize(study.getPatient().getUsers());
		 * Hibernate.initialize(study.getEquipment());
		 * logger.info(study.getIdstudy() + " " +
		 * study.getScheduledProcedureStepStartDateTime().toLocaleString());
		 * logger.info("Nombre del paciente:"+study.getPatient().getUsers().
		 * getName()); }
		 */

		session.close();

		return studies;
	}

	@Override
	public List<Study> getStudyPatientHorario(String idpatient, Date startTime, Date endTime) {
		Session session = HibernateConnection.doHibernateConnection().openSession();

		Query query = session.createQuery(
				"from Study where idpatient = :idpatient and NOT( (:startTime < scheduledProcedureStepStartDateTime and :endTime <= scheduledProcedureStepStartDateTime)OR(:startTime >= scheduledProcedureStepEndDateTime and :endTime > scheduledProcedureStepEndDateTime))");
		query.setParameter("idpatient", idpatient);
		query.setParameter("startTime", startTime);
		query.setParameter("endTime", endTime);
		List<Study> studies = query.list();

		logger.info("Numero de estudios encontrados: " + studies.size());
		/*
		 * for (Study study : allStudies) {
		 * Hibernate.initialize(study.getPatient());
		 * Hibernate.initialize(study.getPatient().getUsers());
		 * Hibernate.initialize(study.getEquipment());
		 * logger.info(study.getIdstudy() + " " +
		 * study.getScheduledProcedureStepStartDateTime().toLocaleString());
		 * logger.info("Nombre del paciente:"+study.getPatient().getUsers().
		 * getName()); }
		 */

		session.close();

		return studies;
	}

	@Override
	public List<Study> getStudyPatientHorarioDistinto(int idstudy, String idpatient, Date startTime,
			Date endTime) {
		Session session = HibernateConnection.doHibernateConnection().openSession();

		Query query = session.createQuery(
				"from Study where idpatient = :idpatient and idstudy != :idstudy and NOT( (:startTime < scheduledProcedureStepStartDateTime and :endTime <= scheduledProcedureStepStartDateTime)OR(:startTime >= scheduledProcedureStepEndDateTime and :endTime > scheduledProcedureStepEndDateTime))");
		query.setParameter("idpatient", idpatient);
		query.setParameter("idstudy", idstudy);
		query.setParameter("startTime", startTime);
		query.setParameter("endTime", endTime);
		List<Study> studies = query.list();

		logger.info("Numero de estudios encontrados: " + studies.size());
		/*
		 * for (Study study : allStudies) {
		 * Hibernate.initialize(study.getPatient());
		 * Hibernate.initialize(study.getPatient().getUsers());
		 * Hibernate.initialize(study.getEquipment());
		 * logger.info(study.getIdstudy() + " " +
		 * study.getScheduledProcedureStepStartDateTime().toLocaleString());
		 * logger.info("Nombre del paciente:"+study.getPatient().getUsers().
		 * getName()); }
		 */

		session.close();

		return studies;
	}

	@Override
	public List<Study> getStudyEquipoHorarioDistinto(int idstudy, int idequipment, Date startTime,
			Date endTime) {
		logger.info("Buscamos si existen estudios programados para la misma maquina en el mismo horario");
		Session session = HibernateConnection.doHibernateConnection().openSession();

		Query query = session.createQuery(
				"from Study where idequipment = :idequipment and idstudy != :idstudy and NOT( (:startTime < scheduledProcedureStepStartDateTime and :endTime <= scheduledProcedureStepStartDateTime)OR(:startTime >= scheduledProcedureStepEndDateTime and :endTime > scheduledProcedureStepEndDateTime))");
		query.setParameter("idequipment", idequipment);
		query.setParameter("idstudy", idstudy);
		query.setParameter("startTime", startTime);
		query.setParameter("endTime", endTime);
		List<Study> studies = query.list();

		logger.info("Numero de estudios encontrados: " + studies.size());
		/*
		 * for (Study study : allStudies) {
		 * Hibernate.initialize(study.getPatient());
		 * Hibernate.initialize(study.getPatient().getUsers());
		 * Hibernate.initialize(study.getEquipment());
		 * logger.info(study.getIdstudy() + " " +
		 * study.getScheduledProcedureStepStartDateTime().toLocaleString());
		 * logger.info("Nombre del paciente:"+study.getPatient().getUsers().
		 * getName()); }
		 */

		session.close();

		return studies;
	}

	@Override
	public boolean existsStudyByUID(String dicomStudyInstanceUid) {
		
		logger.info("Buscamos el estudio con dicomStudyInstanceUid: " + dicomStudyInstanceUid);

		Study study = new Study();

		Session session = HibernateConnection.doHibernateConnection().openSession();

		Query query = session.createQuery("from Study where dicomStudyInstanceUid = :dicomStudyInstanceUid ");
		query.setParameter("dicomStudyInstanceUid", dicomStudyInstanceUid);
		List<Study> list = query.list();

		logger.info("Numero de estudios encontrados: " + list.size());

		session.close();
		
		if (list.size() == 1) {
			return true;
		}

		

		return false;
	}

	@Override
	public List<Study> getEndStudiesByidPatient(String idPatient) {
		logger.info("Buscamos los estudios del usuario con username y con status: FINALIZADO");

		Session session = HibernateConnection.doHibernateConnection().openSession();

		Query query = session.createQuery("from Study where status = :status and idpatient = :idpatient");
		query.setParameter("status", "FINALIZADO");
		query.setParameter("idpatient", idPatient);
		List<Study> stdies = query.list();

		logger.info("Numero de estudios encontrados: " + stdies.size());
		for (Study study : stdies) {
			Hibernate.initialize(study.getPatient());
			Hibernate.initialize(study.getPatient().getUsers());
			Hibernate.initialize(study.getEquipment());
			logger.info(study.getIdstudy() + " " + study.getScheduledProcedureStepStartDateTime().toLocaleString());
			logger.info("Nombre del paciente:" + study.getPatient().getUsers().getName());
		}

		session.close();

		return stdies;
	}

	@Override
	public List<Study> getStudiesByidPatient(String idPatient) {
		logger.info("Buscamos los estudios del usuario con username");

		Session session = HibernateConnection.doHibernateConnection().openSession();

		Query query = session.createQuery("from Study where idpatient = :idpatient");
		query.setParameter("idpatient", idPatient);
		List<Study> studies = query.list();

		logger.info("Numero de estudios encontrados: " + studies.size());
		for (Study study : studies) {
			Hibernate.initialize(study.getPatient());
			Hibernate.initialize(study.getPatient().getUsers());
			Hibernate.initialize(study.getEquipment());
			logger.info(study.getIdstudy() + " " + study.getScheduledProcedureStepStartDateTime().toLocaleString());
			logger.info("Nombre del paciente:" + study.getPatient().getUsers().getName());
		}

		session.close();

		return studies;
	}

}
