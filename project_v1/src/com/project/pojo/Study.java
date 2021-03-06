package com.project.pojo;
// default package
// Generated Aug 5, 2017 6:05:05 PM by Hibernate Tools 3.4.0.CR1

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * Study generated by hbm2java
 */
@Entity
@Table(name = "study", catalog = "minirisdb", uniqueConstraints = @UniqueConstraint(columnNames = "dicom_study_instance_uid"))
public class Study implements java.io.Serializable {

	private Integer idstudy;
	private Patient patient;
	private Equipment equipment;
	private String dicomStudyInstanceUid;
	private String referringPhysician;
	private String issuer;
	private String requestingService;
	private String requestedProcedureCode;
	private String requestedProcedureDescription;
	private Date scheduledProcedureStepStartDateTime;
	private String status;
	private Date scheduledProcedureStepEndDateTime;
	private String url;
	private String accessionNumber;

	public Study() {
	}

	public Study(Patient patient, Equipment equipment, String dicomStudyInstanceUid, String referringPhysician,
			String issuer, String requestingService, String requestedProcedureCode,
			String requestedProcedureDescription, Date scheduledProcedureStepStartDateTime, String status,
			Date scheduledProcedureStepEndDateTime, String url, String accessionNumber) {
		this.patient = patient;
		this.equipment = equipment;
		this.dicomStudyInstanceUid = dicomStudyInstanceUid;
		this.referringPhysician = referringPhysician;
		this.issuer = issuer;
		this.requestingService = requestingService;
		this.requestedProcedureCode = requestedProcedureCode;
		this.requestedProcedureDescription = requestedProcedureDescription;
		this.scheduledProcedureStepStartDateTime = scheduledProcedureStepStartDateTime;
		this.status = status;
		this.scheduledProcedureStepEndDateTime = scheduledProcedureStepEndDateTime;
		this.url = url;
		this.accessionNumber = accessionNumber;
	}
	
	public Study(Patient patient, Equipment equipment) {
		this.patient = patient;
		this.equipment = equipment;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "idstudy", unique = true, nullable = false)
	public Integer getIdstudy() {
		return this.idstudy;
	}

	public void setIdstudy(Integer idstudy) {
		this.idstudy = idstudy;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idpatient")
	public Patient getPatient() {
		return this.patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idequipment")
	public Equipment getEquipment() {
		return this.equipment;
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}

	@Column(name = "dicom_study_instance_uid", unique = true, length = 100)
	public String getDicomStudyInstanceUid() {
		return this.dicomStudyInstanceUid;
	}

	public void setDicomStudyInstanceUid(String dicomStudyInstanceUid) {
		this.dicomStudyInstanceUid = dicomStudyInstanceUid;
	}

	@Column(name = "referring_physician", length = 50)
	public String getReferringPhysician() {
		return this.referringPhysician;
	}

	public void setReferringPhysician(String referringPhysician) {
		this.referringPhysician = referringPhysician;
	}

	@Column(name = "issuer", length = 50)
	public String getIssuer() {
		return this.issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	@Column(name = "requesting_service", length = 50)
	public String getRequestingService() {
		return this.requestingService;
	}

	public void setRequestingService(String requestingService) {
		this.requestingService = requestingService;
	}

	@Column(name = "requested_procedure_code", length = 50)
	public String getRequestedProcedureCode() {
		return this.requestedProcedureCode;
	}

	public void setRequestedProcedureCode(String requestedProcedureCode) {
		this.requestedProcedureCode = requestedProcedureCode;
	}

	@Column(name = "requested_procedure_description", length = 50)
	public String getRequestedProcedureDescription() {
		return this.requestedProcedureDescription;
	}

	public void setRequestedProcedureDescription(String requestedProcedureDescription) {
		this.requestedProcedureDescription = requestedProcedureDescription;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "scheduled_procedure_step_start_date_time", length = 19)
	public Date getScheduledProcedureStepStartDateTime() {
		return this.scheduledProcedureStepStartDateTime;
	}

	public void setScheduledProcedureStepStartDateTime(Date scheduledProcedureStepStartDateTime) {
		this.scheduledProcedureStepStartDateTime = scheduledProcedureStepStartDateTime;
	}

	@Column(name = "status", length = 11)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "scheduled_procedure_step_end_date_time", length = 19)
	public Date getScheduledProcedureStepEndDateTime() {
		return this.scheduledProcedureStepEndDateTime;
	}

	public void setScheduledProcedureStepEndDateTime(Date scheduledProcedureStepEndDateTime) {
		this.scheduledProcedureStepEndDateTime = scheduledProcedureStepEndDateTime;
	}

	@Column(name = "url", length = 100)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "accession_number", length = 50)
	public String getAccessionNumber() {
		return this.accessionNumber;
	}

	public void setAccessionNumber(String accessionNumber) {
		this.accessionNumber = accessionNumber;
	}
	
	public String fechaString(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy"); 
		String currentData = sdf.format(date); 
		return currentData;
	}
	
	public String horaString(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm"); 
		String currentData = sdf.format(date); 
	return currentData;
}

}
