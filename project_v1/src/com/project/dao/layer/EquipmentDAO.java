package com.project.dao.layer;

import java.util.List;

import com.project.pojo.Equipment;


public interface EquipmentDAO {
	
	public Equipment getEquipmentByID(Integer idequipment);
	
	public Equipment getEquipmentByaeTitle(String aeTitle);
	
	public List<Equipment> getAllEquipments();
	
	public boolean updateEquipment(Equipment equipment);
	
	public boolean addEquipment (Equipment equipment);
	
	public boolean deleteEquipment(Integer idequipment);

	public Equipment getEquipmentByModality(String modality);

}
