package com.project.util;


	
	import java.io.File;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
	import javax.xml.parsers.DocumentBuilderFactory;
	import javax.xml.parsers.ParserConfigurationException;
	import javax.xml.transform.Transformer;
	import javax.xml.transform.TransformerException;
	import javax.xml.transform.TransformerFactory;
	import javax.xml.transform.dom.DOMSource;
	import javax.xml.transform.stream.StreamResult;

	import org.w3c.dom.Attr;
	import org.w3c.dom.Document;
	import org.w3c.dom.Element;

import com.project.pojo.Equipment;
import com.project.pojo.Patient;
import com.project.pojo.Study;
import com.project.pojo.Users;

	public class writeXML {
		
		private String path;
		private Study study;
		private Users user;
		private Patient patient;
		private Equipment equipment;

		public writeXML(String path, Study study, Users user, Patient patient, Equipment equipment) {
			super();
			this.path = path;
			this.study = study;
			this.user = user;
			this.patient = patient;
			this.equipment = equipment;
		}







		public String generateXML() {
			
			String fileName = "study_"+study.getDicomStudyInstanceUid()+".xml";

			try {
				File fileXML = new File(path+File.separator+"study_"+study.getDicomStudyInstanceUid()+".xml");
				// si el fichero existe lo borramos y lo creamos nuevo
				if (fileXML.exists()) {
					fileXML.delete();
					fileXML.createNewFile();
				}
	
				DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	
				// root elements
				Document doc = docBuilder.newDocument();
				Element rootElement = doc.createElement("ORM_O01");
				doc.appendChild(rootElement);
	
				// staff elements
				Element PID = doc.createElement("PID");
				rootElement.appendChild(PID);
	
				// set attribute to staff element
				//Attr attr = doc.createAttribute("id");
				//attr.setValue("1");
				//staff.setAttributeNode(attr);
	
				// shorten way
				// staff.setAttribute("id", "1");
	
				// patientID
				Element patientID = doc.createElement("patientID");
				patientID.appendChild(doc.createTextNode(patient.getIdpatient()));
				PID.appendChild(patientID);
	
				// patient given name
				Element patientGivenName = doc.createElement("givenName");
				patientGivenName.appendChild(doc.createTextNode(user.getName()));
				PID.appendChild(patientGivenName);
	
				// patient family name
				Element patientFamilyName = doc.createElement("patientFamilyName");
				patientFamilyName.appendChild(doc.createTextNode(user.getSurename()));
				PID.appendChild(patientFamilyName);
						
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String dob = sdf.format(patient.getDateOfBirth());
				// patient dob
				Element patientDob = doc.createElement("patientDob");
				patientDob.appendChild(doc.createTextNode(dob));
				PID.appendChild(patientDob);
				
				// patient sex
				Element patientSex = doc.createElement("patientSex");
				patientSex.appendChild(doc.createTextNode(patient.getSex()));
				PID.appendChild(patientSex);
				
				
				// PV1 elements
				Element PV1 = doc.createElement("PV1");
				rootElement.appendChild(PV1);
				
				// facility
				Element facility = doc.createElement("facility");
				facility.appendChild(doc.createTextNode(study.getRequestingService()));
				PV1.appendChild(facility);
				
				// referring Physician
				Element referringPhysicianFamilyName = doc.createElement("referringPhysicianFamilyName");
				referringPhysicianFamilyName.appendChild(doc.createTextNode(study.getReferringPhysician()));
				PV1.appendChild(referringPhysicianFamilyName);
				
				// referring Physician
				Element referringPhysicianGivenname = doc.createElement("referringPhysicianGivenname");
				referringPhysicianGivenname.appendChild(doc.createTextNode(study.getReferringPhysician()));
				PV1.appendChild(referringPhysicianGivenname);
				
				// referring Physician ID
				Element referringPhysicianID = doc.createElement("referringPhysicianID");
				referringPhysicianID.appendChild(doc.createTextNode(study.getReferringPhysician()));
				PV1.appendChild(referringPhysicianID);
				
				// ORC elements
				Element ORC = doc.createElement("ORC");
				rootElement.appendChild(ORC);
				
				// OrderControl
				Element orderControl = doc.createElement("orderControl");
				orderControl.appendChild(doc.createTextNode("CA"));
				ORC.appendChild(orderControl);
				
				// name space ID
				Element nameSpaceID = doc.createElement("nameSpaceID");
				nameSpaceID.appendChild(doc.createTextNode(study.getIssuer()));
				ORC.appendChild(nameSpaceID);
				
				// Universal ID
				Element universalID = doc.createElement("universalID");
				universalID.appendChild(doc.createTextNode(study.getDicomStudyInstanceUid()));
				ORC.appendChild(universalID);
				
				// OBR elements
				Element OBR = doc.createElement("OBR");
				rootElement.appendChild(OBR);

				// Accession Number
				Element accessionNumber = doc.createElement("accessionNumber");
				accessionNumber.appendChild(doc.createTextNode(study.getAccessionNumber()));
				OBR.appendChild(accessionNumber);
				
				// name space id obr
				Element nameSpaceIDOBR = doc.createElement("nameSpaceIDOBR");
				nameSpaceIDOBR.appendChild(doc.createTextNode(study.getDicomStudyInstanceUid()));
				OBR.appendChild(nameSpaceIDOBR);
				
				// name space id obr
				Element universalIDOBR = doc.createElement("universalIDOBR");
				universalIDOBR.appendChild(doc.createTextNode(study.getIssuer()));
				OBR.appendChild(universalIDOBR);
				
				// requested procedure code
				Element requestedProcedureCode = doc.createElement("requestedProcedureCode");
				requestedProcedureCode.appendChild(doc.createTextNode(study.getRequestedProcedureCode()));
				OBR.appendChild(requestedProcedureCode);
				
				// requested procedure description
				Element requestedProcedureDescription = doc.createElement("requestedProcedureDescription");
				requestedProcedureDescription.appendChild(doc.createTextNode(study.getRequestedProcedureDescription()));
				OBR.appendChild(requestedProcedureDescription);
				
				// nome of coding system
				Element nameOfCodingSystem = doc.createElement("nameOfCodingSystem");
				nameOfCodingSystem.appendChild(doc.createTextNode(study.getRequestedProcedureDescription()));
				OBR.appendChild(nameOfCodingSystem);
				
				// alternate identifier
				Element alternateIdentifier = doc.createElement("alternateIdentifier");
				alternateIdentifier.appendChild(doc.createTextNode(study.getRequestedProcedureCode()));
				OBR.appendChild(alternateIdentifier);
				
				// AETitle
				Element AETitle = doc.createElement("AETitle");
				AETitle.appendChild(doc.createTextNode(equipment.getAeTitle()));
				OBR.appendChild(AETitle);
				
				// Modality
				Element modality = doc.createElement("Modality");
				modality.appendChild(doc.createTextNode(equipment.getModality()));
				OBR.appendChild(modality);
				
				// ModalityID
				Element modalityID = doc.createElement("modalityID");
				modalityID.appendChild(doc.createTextNode(equipment.getModality()));
				OBR.appendChild(modalityID);
				
				// write the content into xml file
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(fileXML);
	
				// Output to console for testing
				// StreamResult result = new StreamResult(System.out);
	
				transformer.transform(source, result);
	
				System.out.println("File saved!");
	
			} catch (ParserConfigurationException pce) {
				pce.printStackTrace();
  
			} catch (TransformerException tfe) {
				tfe.printStackTrace();
  
			}catch(Exception e){
	  
			}
			return fileName;
		}
}
