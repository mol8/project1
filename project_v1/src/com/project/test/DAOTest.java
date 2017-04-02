package com.project.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.project.dao.registry.RegistryDAO;
import com.project.pojo.Equipment;
import com.project.pojo.Users;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:**/<context>.xml" })
public class DAOTest {
	
	private static Equipment equipment;
	
	private static String modality = "TEST";
	private static String aeTitle = "TEST";
	
	@BeforeClass
	public static void startClass(){
		equipment = new Equipment(modality,aeTitle, null);
	}
	
	@Test
	public void equipmentADDTest(){
		//introducimos el equipo en la BBDD.
		RegistryDAO.getEquipmentDAO().addEquipment(equipment);
		Equipment equipmentObtenido = RegistryDAO.getEquipmentDAO().getEquipmentByaeTitle(aeTitle);
		assertEquals(equipment.getIdequipment(), equipmentObtenido.getIdequipment());
	}
	
	@Test
	public void equipmentDELETETest(){
		//Borramos el equipo de la BBDD.
		Equipment equipmentAux = RegistryDAO.getEquipmentDAO().getEquipmentByaeTitle(aeTitle);
		RegistryDAO.getEquipmentDAO().deleteEquipment(equipmentAux.getIdequipment());
		equipmentAux = RegistryDAO.getEquipmentDAO().getEquipmentByaeTitle(aeTitle);
		assertNull(null, equipmentAux.getAeTitle());
	}

}