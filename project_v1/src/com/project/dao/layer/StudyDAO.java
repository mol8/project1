package com.project.dao.layer;

import java.util.List;

import com.project.pojo.Study;

public interface StudyDAO {
	
	public List<Study> getAllStudies();
	
	public Study getStudyByID(Integer idstudy);
	
	public boolean modificaStudy(Study study);
	
	public boolean addStudy (Study study);
	
	public boolean deleteStudy(Integer idstudy);

}
