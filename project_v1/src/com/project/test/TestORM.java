package com.project.test;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.project.pojo.Equipment;
import com.project.pojo.Patient;
import com.project.pojo.Study;
import com.project.pojo.Users;
import com.project.util.SendMail;
import com.project.util.generateORM;
import com.project.util.sendORM;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.llp.LLPException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:**/<context>.xml" })
public class TestORM {
	
	@Test
	public void testORM(){
		//creamos un mensaje ORM	
		//para la generacion del ORM utilizamos la libreria HAPI
		//se han creado una serie de utilidades para la creacion del ORM y envio
		Date aux = new Date(1984, 11, 13);
		
		Equipment testEquipment = new Equipment("testModality", "testAEtitle", null);
		testEquipment.setIdequipment(99);
		Patient testPatient = new Patient("testID", null, aux, "TEST", null);
		Users testUser = new Users("testName", "testSurename", "testUsername", "testEmail", "testPassword", "testRole", null);
		Study testStudy = new Study(null, testEquipment, "testUID", "testReferringPhysician", "testissuer", "testRequestingService", "testRequestingProcedure", "testDescription", null, "testStatus", null);
		
		generateORM generate_ORM = new generateORM();
		generate_ORM.setPID_patientID(testPatient.getIdpatient());
		generate_ORM.setPID_givenName(testUser.getName());
		generate_ORM.setPID_familyName(testUser.getSurename());
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); 
		String dob = sdf.format(testPatient.getDateOfBirth()); 
		generate_ORM.setPID_dateOfBirth(dob);
		generate_ORM.setPID_sex(testPatient.getSex());
		
		generate_ORM.setPV1_facility(testStudy.getRequestingService());
		generate_ORM.setPV1_attendingDoctorFamilyName(testStudy.getReferringPhysician());
		generate_ORM.setPV1_attendingDoctorGivenName(testStudy.getReferringPhysician());
		generate_ORM.setPV1_attendingDoctorID(testStudy.getReferringPhysician());
		
		generate_ORM.setORC_orderControl("NW");
		generate_ORM.setORC_namespaceID(testStudy.getIssuer());
		generate_ORM.setORC_universalID(testStudy.getDicomStudyInstanceUid());
		
		generate_ORM.setOBR2_placeOrderNumber_entityIdentifier(testStudy.getIssuer());
		generate_ORM.setOBR2_placeOrderNumber_namespaceID(testStudy.getDicomStudyInstanceUid());
		generate_ORM.setOBR2_placeOrderNumber_universalID(testStudy.getIssuer());
		
		generate_ORM.setOBR4_universalServiceIdentifier_identifier(testStudy.getRequestedProcedureCode());
		generate_ORM.setOBR4_universalServiceIdentifier_text(testStudy.getRequestedProcedureDescription());
		generate_ORM.setOBR4_universalServiceIdentifier_nameOfCodingSystem(testStudy.getRequestedProcedureDescription());
		generate_ORM.setOBR4_universalServiceIdentifier_alternateIdentifier(testStudy.getRequestedProcedureCode());
		
		generate_ORM.setOBR18_placerField1(testEquipment.getAeTitle());
		generate_ORM.setOBR19_placerFiel2(testEquipment.getModality());
		generate_ORM.setOBR24_diagnosticServSectID(testEquipment.getIdequipment().toString());
		
		//lo enviamos en el mismo hilo de ejecucion
		
		try {
			//leemos los datos de configuracion de envio del fichero de configuracion
			Properties prop = new Properties();
	    	InputStream input = null;   	
	       	
	    	final String dir = System.getProperty("user.dir") + "//WebContent//WEB-INF//resources//";
	        System.out.println("current dir = " + dir);     
			input = new FileInputStream(dir + File.separator +"config.properties");
	        //Users/mol/Documents/GitRepository/project_v1/WebContent/WEB-INF/resources

			// load a properties file
			prop.load(input);			
			
			int incomingPort=Integer.parseInt(prop.getProperty("incomingPort"));
			boolean useTLS=Boolean.parseBoolean(prop.getProperty("useTLS"));
			int outgoingPort=Integer.parseInt(prop.getProperty("outgoingPort"));
			String sendingIP=prop.getProperty("sendingIP");
			
			sendORM send_ORM = new sendORM(incomingPort,useTLS,outgoingPort,sendingIP,generate_ORM.orm());
			send_ORM.send();
		} catch (HL7Exception | IOException | ParseException | LLPException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			assertTrue(false);
		}
		
		assertTrue(true);
	}

}