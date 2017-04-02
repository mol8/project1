package com.project.dao.registry;

import com.project.dao.implementation.EquipmentDAO;
import com.project.dao.implementation.PatientDAO;
import com.project.dao.implementation.StudyDAO;
import com.project.dao.implementation.UsersDAO;

public class RegistryDAO {
	
	public static com.project.dao.layer.UsersDAO usersDAO;
	public static com.project.dao.layer.EquipmentDAO equipmentDAO;
	public static com.project.dao.layer.PatientDAO patientDAO;
	public static com.project.dao.layer.StudyDAO studyDAO;
	
	static{
		usersDAO = new UsersDAO();
		equipmentDAO = new EquipmentDAO();
		patientDAO = new PatientDAO();
		studyDAO = new StudyDAO();
				
	}

	public static com.project.dao.layer.UsersDAO getUsersDAO() {
		return usersDAO;
	}

	public static void setUsersDAO(com.project.dao.layer.UsersDAO usersDAO) {
		RegistryDAO.usersDAO = usersDAO;
	}

	public static com.project.dao.layer.EquipmentDAO getEquipmentDAO() {
		return equipmentDAO;
	}

	public static void setEquipmentDAO(com.project.dao.layer.EquipmentDAO equipmentDAO) {
		RegistryDAO.equipmentDAO = equipmentDAO;
	}

	public static com.project.dao.layer.PatientDAO getPatientDAO() {
		return patientDAO;
	}

	public static void setPatientDAO(com.project.dao.layer.PatientDAO patientDAO) {
		RegistryDAO.patientDAO = patientDAO;
	}

	public static com.project.dao.layer.StudyDAO getStudyDAO() {
		return studyDAO;
	}

	public static void setStudyDAO(com.project.dao.layer.StudyDAO studyDAO) {
		RegistryDAO.studyDAO = studyDAO;
	}
	
	
	
	

}
