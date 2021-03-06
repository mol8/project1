package com.project.dao.layer;

import java.util.List;

import com.project.pojo.Patient;
import com.project.pojo.Study;

public interface PatientDAO {
	
	public Patient getPatientByID(String string);
	public Patient getPatientByUser(int iduser);
	
	public List<Patient> getAllPatients();
	
	public boolean modificaPatient(Patient patient);
	
	public boolean addPatient (Patient patient);
	
	public boolean deletePatient(String idpatient);
	
	public List<Patient> getAllPatients_ACTIVOS();


}
