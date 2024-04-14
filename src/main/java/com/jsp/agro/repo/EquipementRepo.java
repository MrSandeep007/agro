package com.jsp.agro.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jsp.agro.entity.Equipement;
import com.jsp.agro.entity.User;

public interface EquipementRepo extends JpaRepository<Equipement, Integer>{
	@Query("select e from Equipement e where e.name=?1")
	List<Equipement> findByName(String name);
	@Query("select e from Equipement e where e.user=?1")
	List<Equipement> findByUser(User user);
}
