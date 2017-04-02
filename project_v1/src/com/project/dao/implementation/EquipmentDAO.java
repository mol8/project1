package com.project.dao.implementation;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;

import com.project.pojo.Equipment;

public class EquipmentDAO implements com.project.dao.layer.EquipmentDAO{
	
	private static final Logger logger = Logger.getLogger(EquipmentDAO.class);

	@Override
	public Equipment getEquipmentByID(Integer idequipment) {
		logger.info("Buscamos el equipo con idequipment: "+idequipment);
		
		Equipment equipment = new Equipment();
		
		Session session = HibernateConnection.doHibernateConnection().openSession();
		
		Query query = session.createQuery("from Equipment where idequipment = :idequipment ");
		query.setParameter("idequipment", idequipment);
		List<Equipment> list = query.list();
		
		logger.info("Numero de equipos encontrados: "+ list.size());
		
		if(list.size()==1){
			equipment = (Equipment) list.get(0);
			Hibernate.initialize(equipment.getStudies());
		}
		
		session.close();
		return equipment;
	}

	@Override
	public List<Equipment> getAllEquipments() {
		logger.info("Buscamos todos los equipos.");
		
		Equipment equipment = new Equipment();
		
		Session session = HibernateConnection.doHibernateConnection().openSession();
		
		List<Equipment> list = session.createQuery("from Equipment where idequipment = :idequipment ").list();
		
		logger.info("Numero de equipos encontrados: "+ list.size());
		
		if(list.size()==1){
			equipment = (Equipment) list.get(0);
			Hibernate.initialize(equipment.getStudies());
		}
		session.close();
		return list;
	}

	@Override
	public boolean updateEquipment(Equipment equipment) {
		try {
			Session session = HibernateConnection.doHibernateConnection().openSession();
			session.beginTransaction();

			// insertamos en la BBDD
			session.update(equipment);

			session.getTransaction().commit();

			session.close();

			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean addEquipment(Equipment equipment) {
		try {
			Session session = HibernateConnection.doHibernateConnection().openSession();
			session.beginTransaction();

			// insertamos en la BBDD
			session.save(equipment);
			session.getTransaction().commit();
			session.close();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteEquipment(Integer idequipment) {
		try {
			Session session = HibernateConnection.doHibernateConnection().openSession();

			List<Equipment> equipments = session.createQuery("From Equipment where idequipment='" + idequipment + "'").list();

			if (equipments != null && equipments.get(0) != null) {
				session.beginTransaction();
				session.delete(equipments.get(0));
				session.getTransaction().commit();
				session.close();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Equipment getEquipmentByaeTitle(String aeTitle) {
		logger.info("Buscamos el equipo con aeTitle: "+aeTitle);
		
		Equipment equipment = new Equipment();
		
		Session session = HibernateConnection.doHibernateConnection().openSession();
		
		Query query = session.createQuery("from Equipment where aeTitle = :aeTitle ");
		query.setParameter("aeTitle", aeTitle);
		List<Equipment> list = query.list();
		
		logger.info("Numero de equipos encontrados: "+ list.size());
		
		if(list.size()==1){
			equipment = (Equipment) list.get(0);
			Hibernate.initialize(equipment.getStudies());
		}
		
		session.close();
		return equipment;
	}

}
