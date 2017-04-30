package com.project.dao.layer;

import java.util.Date;
import java.util.List;

import com.project.pojo.Study;

public interface StudyDAO {
	
	public List<Study> getAllStudies();
	
	public Study getStudyByID(Integer idstudy);
	
	public boolean modificaStudy(Study study);
	
	public boolean addStudy (Study study);
	
	public boolean deleteStudy(Integer idstudy);

	public List<Study> getStudyPROGRAMADOS();
	
	public List<Study> getStudyEquipoHorario(int idequipment, Date startTime, Date endTime);
	
	public List<Study> getStudyPatientHorario(String idpatient, Date startTime, Date endTime);

	public List<Study> getStudyPatientHorarioDistinto(int idstudy, String idpatient, Date startTime,
			Date endTime);

	public List<Study> getStudyEquipoHorarioDistinto(int idstudy, int idequipment, Date startTime,
			Date endTime);

	public Study getStudyByDicomStudyInstanceUid(String dicomStudyInstanceUid);

	public List<Study> getTodayStudies();

}
