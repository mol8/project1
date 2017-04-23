package com.project.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pixelmed.dicom.DicomDirectoryRecordFactory.StudyDirectoryRecord;
import com.pixelmed.dicom.DicomException;
import com.pixelmed.dicom.UIDGenerator;
import com.project.dao.registry.RegistryDAO;
import com.project.pojo.Equipment;
import com.project.pojo.Patient;
import com.project.pojo.Study;
import com.project.pojo.Users;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:**/<context>.xml" })
public class DAOTest {
	
	private static Equipment equipment;
	private static Patient patient;
	private static Users user;
	private static Study study;
	
	
	private static String modality = "TEST";
	private static String aeTitle = "TEST";
	
	private static String name = "TEST";
	private static String surename = "TEST";
	private static String username = "TEST";
	private static String email = "TEST";
	private static String password = "TEST";
	private static String role = "NO_ACTIVO";
	
	private static String idpatient = "TEST";
	
	private static String dicomStudyInstanceUid = "TEST";
	
	@BeforeClass
	public static void startClass(){
		equipment = new Equipment(modality,aeTitle, null);
		user = new Users(name, surename, username, email, password, role, null);
		patient = new Patient(idpatient, null);
		study = new Study(null, null);
		study.setDicomStudyInstanceUid(dicomStudyInstanceUid);	
	}
	
	@Test
	public void equipmentTest(){
		//introducimos el equipo en la BBDD.
		RegistryDAO.getEquipmentDAO().addEquipment(equipment);
		Equipment equipmentObtenido = RegistryDAO.getEquipmentDAO().getEquipmentByaeTitle(aeTitle);
		assertEquals(equipment.getIdequipment(), equipmentObtenido.getIdequipment());
		
		//Borramos el equipo de la BBDD.
		Equipment equipmentAux = RegistryDAO.getEquipmentDAO().getEquipmentByaeTitle(aeTitle);
		RegistryDAO.getEquipmentDAO().deleteEquipment(equipmentAux.getIdequipment());
		equipmentAux = RegistryDAO.getEquipmentDAO().getEquipmentByaeTitle(aeTitle);
		assertNull(null, equipmentAux.getAeTitle());
	}
	
	@Test
	public void userTest(){
		//introducimos un usuario en la BBDD
		RegistryDAO.getUsersDAO().addUser(user);
		Users userObtenido = RegistryDAO.getUsersDAO().getUserByUserName(username);
		assertEquals(user.getUsername(), userObtenido.getUsername());
		
		//Borramos el usuario de la BBDD.
		RegistryDAO.getUsersDAO().deleteUser(userObtenido.getIduser());
		userObtenido = RegistryDAO.getUsersDAO().getUserByUserName(username);
		assertNull(null, userObtenido);
		
	}
	
	@Test
	public void patientTest(){
		//introducimos un paciente en la BBDD
		RegistryDAO.getPatientDAO().addPatient(patient);
		Patient patientObtenido = RegistryDAO.getPatientDAO().getPatientByID(idpatient);
		assertEquals(patient.getIdpatient(), patientObtenido.getIdpatient());
		
		//Borramos el paciente de la BBDD.
		Patient patientAux = RegistryDAO.getPatientDAO().getPatientByID(idpatient);
		RegistryDAO.getPatientDAO().deletePatient(idpatient);
		patientAux = RegistryDAO.getPatientDAO().getPatientByID(idpatient);
		assertNull(null, patientAux.getIdpatient());
	}
	
	@Test
	public void studyTest(){
		//introducimos un estudio en la BBDD
		RegistryDAO.getStudyDAO().addStudy(study);
		Study estudioObtenido = RegistryDAO.getStudyDAO().getStudyByDicomStudyInstanceUid(dicomStudyInstanceUid);
		assertEquals(study.getDicomStudyInstanceUid(), estudioObtenido.getDicomStudyInstanceUid());
		
		//Borramos el estudio de la BBDD.
		Study studyAux = RegistryDAO.getStudyDAO().getStudyByDicomStudyInstanceUid(dicomStudyInstanceUid);
		RegistryDAO.getStudyDAO().deleteStudy(studyAux.getIdstudy());
		studyAux = RegistryDAO.getStudyDAO().getStudyByDicomStudyInstanceUid(dicomStudyInstanceUid);
		assertNull(null, studyAux.getDicomStudyInstanceUid());
	}

}