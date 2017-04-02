package com.project.util;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

import com.project.controllers.loginController;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.v24.group.ORM_O01_PATIENT_VISIT;
import ca.uhn.hl7v2.model.v24.message.ORM_O01;
import ca.uhn.hl7v2.model.v24.segment.MSH;
import ca.uhn.hl7v2.model.v24.segment.OBR;
import ca.uhn.hl7v2.model.v24.segment.ORC;
import ca.uhn.hl7v2.model.v24.segment.PID;
import ca.uhn.hl7v2.model.v24.segment.PV1;
import ca.uhn.hl7v2.parser.Parser;

public class generateORM{
	
	private static final Logger logger = Logger.getLogger(generateORM.class);
	
	private String PID_familyName;
	private String PID_givenName;
	private String PID_patientID;
	private String PID_dateOfBirth;
	private String PID_sex;
	
	private String PV1_facility;
	
	private String PV1_attendingDoctorID;
	private String PV1_attendingDoctorFamilyName;
	private String PV1_attendingDoctorGivenName;
	
	private String ORC_orderControl;
	private String ORC_namespaceID;
	private String ORC_universalID;
		
	private String OBR2_placeOrderNumber_entityIdentifier;
	private String OBR2_placeOrderNumber_namespaceID;
	private String OBR2_placeOrderNumber_universalID;
	private String OBR4_universalServiceIdentifier_identifier;
	private String OBR4_universalServiceIdentifier_text;
	private String OBR4_universalServiceIdentifier_nameOfCodingSystem;
	private String OBR4_universalServiceIdentifier_alternateIdentifier;
	private String OBR18_placerField1;
	private String OBR19_placerFiel2;
	private String OBR24_diagnosticServSectID;

	public generateORM() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ORM_O01 orm() throws HL7Exception, IOException, ParseException {
		ORM_O01 orm = new ORM_O01();
		orm.initQuickstart("ORM", "O01" , "T");
		
		// Populate the MSH Segment
        MSH mshSegment = orm.getMSH();
        //MSH-3 sending application
        mshSegment.getSendingApplication().getNamespaceID().setValue("TestSendingSystem");
        //MSH-13 sequence number
        //mshSegment.getSequenceNumber().setValue("123");
        //MSH-7 date time of message
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        //Calendar calendar = Calendar.getInstance();
        //System.out.println(sdf.format(calendar.getTime()));
        //mshSegment.getDateTimeOfMessage().getTs1_TimeOfAnEvent().setValue(calendar);
        //mshSegment.getDateTimeOfMessage().getTimeOfAnEvent().setValue(calendar);
        
        //Populate PID segment
        PID pid = orm.getPATIENT().getPID();
        pid.getPatientName(0).getFamilyName().getSurname().setValue(PID_familyName);
        pid.getPatientName(0).getGivenName().setValue(PID_givenName);
        pid.getPatientIdentifierList(0).getID().setValue(PID_patientID);
        
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        //Calendar calendar = Calendar.getInstance();
        //System.out.println(sdf.format(calendar.getTime()));
        
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateInString = "19820213";
        //Date date = sdf.parse(dateInString);
        pid.getPid7_DateTimeOfBirth().getTs1_TimeOfAnEvent().setValue(PID_dateOfBirth);
        //pid.getPid7_DateTimeOfBirth().getTs2_DegreeOfPrecision().setValue("yyyyMMdd");
        pid.getPid8_AdministrativeSex().setValue(PID_sex);
        
        //Populate PV1 segment
        PV1 pv1 = orm.getPATIENT().getPATIENT_VISIT().getPV1();
        pv1.getAssignedPatientLocation().getFacility().getNamespaceID().setValue(PV1_facility);
        pv1.insertAttendingDoctor(0);
        pv1.getAttendingDoctor()[0].getIDNumber().setValue(PV1_attendingDoctorID);
        pv1.getAttendingDoctor()[0].getFamilyName().getSurname().setValue(PV1_attendingDoctorFamilyName);
        pv1.getAttendingDoctor()[0].getGivenName().setValue(PV1_attendingDoctorGivenName);
        
        //Populate ORC segment
        ORC orc = orm.getORDER().getORC();
        //orc.getOrderControl().setValue("NW");
        orc.getOrderControl().setValue(ORC_orderControl);
        orc.getPlacerOrderNumber().getNamespaceID().setValue(ORC_namespaceID);
        orc.getPlacerOrderNumber().getUniversalID().setValue(ORC_universalID);
        
        //Populate OBR segment
        OBR obr = orm.getORDER().getORDER_DETAIL().getOBR();
        
        obr.getObr2_PlacerOrderNumber().getEi1_EntityIdentifier().setValue(OBR2_placeOrderNumber_entityIdentifier);
        obr.getObr2_PlacerOrderNumber().getEi2_NamespaceID().setValue(OBR2_placeOrderNumber_namespaceID);
        obr.getObr2_PlacerOrderNumber().getEi3_UniversalID().setValue(OBR2_placeOrderNumber_universalID);
        
        obr.getObr4_UniversalServiceIdentifier().getCe1_Identifier().setValue(OBR4_universalServiceIdentifier_identifier);
        obr.getObr4_UniversalServiceIdentifier().getCe2_Text().setValue(OBR4_universalServiceIdentifier_text);
        obr.getObr4_UniversalServiceIdentifier().getCe3_NameOfCodingSystem().setValue(OBR4_universalServiceIdentifier_nameOfCodingSystem);
        obr.getObr4_UniversalServiceIdentifier().getCe4_AlternateIdentifier().setValue(OBR4_universalServiceIdentifier_alternateIdentifier);
        
        obr.getObr18_PlacerField1().setValue(OBR18_placerField1);
        
        obr.getObr19_PlacerField2().setValue(OBR19_placerFiel2);
   
        obr.getObr24_DiagnosticServSectID().setValue(OBR24_diagnosticServSectID);
		
		//Print message pipeline
		// Now, let's encode the message and look at the output
        HapiContext context = new DefaultHapiContext();
        Parser parser = context.getPipeParser();
        String encodedMessage = parser.encode(orm);
        logger.info(encodedMessage);
        //System.out.println("Printing ER7 Encoded Message:");          
        //System.out.println(encodedMessage);
        
        //Print message xml
        //parser = context.getXMLParser();
        //encodedMessage = parser.encode(orm);
        //System.out.println("Printing XML Encoded Message:");          
        //System.out.println(encodedMessage);
        
		return orm;		
	}

	public String getPID_familyName() {
		return PID_familyName;
	}

	public void setPID_familyName(String pID_familyName) {
		PID_familyName = pID_familyName;
	}

	public String getPID_givenName() {
		return PID_givenName;
	}

	public void setPID_givenName(String pID_givenName) {
		PID_givenName = pID_givenName;
	}

	public String getPID_patientID() {
		return PID_patientID;
	}

	public void setPID_patientID(String pID_patientID) {
		PID_patientID = pID_patientID;
	}

	public String getPID_dateOfBirth() {
		return PID_dateOfBirth;
	}

	public void setPID_dateOfBirth(String pID_dateOfBirth) {
		PID_dateOfBirth = pID_dateOfBirth;
	}

	public String getPID_sex() {
		return PID_sex;
	}

	public void setPID_sex(String pID_sex) {
		PID_sex = pID_sex;
	}

	public String getPV1_facility() {
		return PV1_facility;
	}

	public void setPV1_facility(String pV1_facility) {
		PV1_facility = pV1_facility;
	}

	public String getPV1_attendingDoctorID() {
		return PV1_attendingDoctorID;
	}

	public void setPV1_attendingDoctorID(String pV1_attendingDoctorID) {
		PV1_attendingDoctorID = pV1_attendingDoctorID;
	}

	public String getPV1_attendingDoctorFamilyName() {
		return PV1_attendingDoctorFamilyName;
	}

	public void setPV1_attendingDoctorFamilyName(String pV1_attendingDoctorFamilyName) {
		PV1_attendingDoctorFamilyName = pV1_attendingDoctorFamilyName;
	}

	public String getPV1_attendingDoctorGivenName() {
		return PV1_attendingDoctorGivenName;
	}

	public void setPV1_attendingDoctorGivenName(String pV1_attendingDoctorGivenName) {
		PV1_attendingDoctorGivenName = pV1_attendingDoctorGivenName;
	}

	public String getORC_orderControl() {
		return ORC_orderControl;
	}

	public void setORC_orderControl(String oRC_orderControl) {
		ORC_orderControl = oRC_orderControl;
	}

	public String getORC_namespaceID() {
		return ORC_namespaceID;
	}

	public void setORC_namespaceID(String oRC_namespaceID) {
		ORC_namespaceID = oRC_namespaceID;
	}

	public String getORC_universalID() {
		return ORC_universalID;
	}

	public void setORC_universalID(String oRC_universalID) {
		ORC_universalID = oRC_universalID;
	}

	public String getOBR2_placeOrderNumber_entityIdentifier() {
		return OBR2_placeOrderNumber_entityIdentifier;
	}

	public void setOBR2_placeOrderNumber_entityIdentifier(String oBR2_placeOrderNumber_entityIdentifier) {
		OBR2_placeOrderNumber_entityIdentifier = oBR2_placeOrderNumber_entityIdentifier;
	}

	public String getOBR2_placeOrderNumber_namespaceID() {
		return OBR2_placeOrderNumber_namespaceID;
	}

	public void setOBR2_placeOrderNumber_namespaceID(String oBR2_placeOrderNumber_namespaceID) {
		OBR2_placeOrderNumber_namespaceID = oBR2_placeOrderNumber_namespaceID;
	}

	public String getOBR2_placeOrderNumber_universalID() {
		return OBR2_placeOrderNumber_universalID;
	}

	public void setOBR2_placeOrderNumber_universalID(String oBR2_placeOrderNumber_universalID) {
		OBR2_placeOrderNumber_universalID = oBR2_placeOrderNumber_universalID;
	}

	public String getOBR4_universalServiceIdentifier_identifier() {
		return OBR4_universalServiceIdentifier_identifier;
	}

	public void setOBR4_universalServiceIdentifier_identifier(String oBR4_universalServiceIdentifier_identifier) {
		OBR4_universalServiceIdentifier_identifier = oBR4_universalServiceIdentifier_identifier;
	}

	public String getOBR4_universalServiceIdentifier_text() {
		return OBR4_universalServiceIdentifier_text;
	}

	public void setOBR4_universalServiceIdentifier_text(String oBR4_universalServiceIdentifier_text) {
		OBR4_universalServiceIdentifier_text = oBR4_universalServiceIdentifier_text;
	}

	public String getOBR4_universalServiceIdentifier_nameOfCodingSystem() {
		return OBR4_universalServiceIdentifier_nameOfCodingSystem;
	}

	public void setOBR4_universalServiceIdentifier_nameOfCodingSystem(
			String oBR4_universalServiceIdentifier_nameOfCodingSystem) {
		OBR4_universalServiceIdentifier_nameOfCodingSystem = oBR4_universalServiceIdentifier_nameOfCodingSystem;
	}

	public String getOBR4_universalServiceIdentifier_alternateIdentifier() {
		return OBR4_universalServiceIdentifier_alternateIdentifier;
	}

	public void setOBR4_universalServiceIdentifier_alternateIdentifier(
			String oBR4_universalServiceIdentifier_alternateIdentifier) {
		OBR4_universalServiceIdentifier_alternateIdentifier = oBR4_universalServiceIdentifier_alternateIdentifier;
	}

	public String getOBR18_placerField1() {
		return OBR18_placerField1;
	}

	public void setOBR18_placerField1(String oBR18_placerField1) {
		OBR18_placerField1 = oBR18_placerField1;
	}

	public String getOBR19_placerFiel2() {
		return OBR19_placerFiel2;
	}

	public void setOBR19_placerFiel2(String oBR19_placerFiel2) {
		OBR19_placerFiel2 = oBR19_placerFiel2;
	}

	public String getOBR24_diagnosticServSectID() {
		return OBR24_diagnosticServSectID;
	}

	public void setOBR24_diagnosticServSectID(String oBR24_diagnosticServSectID) {
		OBR24_diagnosticServSectID = oBR24_diagnosticServSectID;
	}
	
	

}