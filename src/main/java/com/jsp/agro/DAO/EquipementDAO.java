package com.jsp.agro.DAO;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsp.agro.entity.Equipement;
import com.jsp.agro.entity.User;
import com.jsp.agro.repo.EquipementRepo;

@Service
public class EquipementDAO {
	@Autowired
	EquipementRepo repo;
	
	public Equipement saveEquipement(Equipement equipement){
		return repo.save(equipement);
	}
	public Equipement fetchById(int id){
		Optional<Equipement> o = repo.findById(id);
		if(o.isEmpty()) {
			return null;
		}
		return o.get();
	}
	public List<Equipement> fetchAll(){
		return repo.findAll();
	}
	public List<Equipement> fetchByName(String name){
		return repo.findByName(name);
	}
	public List<Equipement> fetchByUser(User user){
		return repo.findByUser(user);
	}
	public Equipement updateEquipement(Equipement equipement){
		Equipement e = fetchById(equipement.getId());
		if(equipement.getId()!=0) {
			e.setId(equipement.getId());
		}
		if(equipement.getName()!=null) {
			e.setName(equipement.getName());
		}
		if(equipement.getNoOfItems()!=0) {
			e.setNoOfItems(equipement.getNoOfItems());
		}
		if(equipement.getCostPerHour()!=0) {
			e.setCostPerHour(equipement.getCostPerHour());
		}
		if(equipement.getUser()!=null) {
			e.setUser(equipement.getUser());
		}
		return repo.save(e);
	}
	public void deleteEquipement(int id){
		repo.deleteById(id);
	}
}

