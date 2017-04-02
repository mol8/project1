package com.project.util;


	
	import java.io.File;

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
	import com.project.pojo.Study;

	public class writeXML {
		
		private File fileXML;
		private String path;
		private HttpServletRequest request;
		private Study study;

		public writeXML() {
			super();
			// TODO Auto-generated constructor stub
		}

		public void generateXML(Study study) {

			try {
				path=request.getSession().getServletContext().getRealPath("/")+"//WEB-INF//resources";
				fileXML = new File(path+File.separator+"study_"+study.getDicomStudyInstanceUid()+".xml");
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
				Element staff = doc.createElement("Staff");
				rootElement.appendChild(staff);
	
				// set attribute to staff element
				Attr attr = doc.createAttribute("id");
				attr.setValue("1");
				staff.setAttributeNode(attr);
	
				// shorten way
				// staff.setAttribute("id", "1");
	
				// firstname elements
				Element firstname = doc.createElement("firstname");
				firstname.appendChild(doc.createTextNode("yong"));
				staff.appendChild(firstname);
	
				// lastname elements
				Element lastname = doc.createElement("lastname");
				lastname.appendChild(doc.createTextNode("mook kim"));
				staff.appendChild(lastname);
	
				// nickname elements
				Element nickname = doc.createElement("nickname");
				nickname.appendChild(doc.createTextNode("mkyong"));
				staff.appendChild(nickname);
	
				// salary elements
				Element salary = doc.createElement("salary");
				salary.appendChild(doc.createTextNode("100000"));
				staff.appendChild(salary);
	
				// write the content into xml file
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new File("C:\\file.xml"));
	
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
		}
}
