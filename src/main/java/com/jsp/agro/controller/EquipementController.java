package com.jsp.agro.controller;

import java.util.List; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.agro.entity.Equipement;
import com.jsp.agro.service.EquipementService;
import com.jsp.agro.util.ResponseStructure;

@RestController
public class EquipementController {
	@Autowired
	EquipementService service;
	
	@PostMapping("/saveequip")
	public ResponseEntity<ResponseStructure<Equipement>> saveEquipement(@RequestParam String name,@RequestParam float costPerHour,@RequestParam int noOfItems,@RequestParam int uid){
		return service.saveEquipement(name, costPerHour, noOfItems, uid);
	}
	@GetMapping("/fetchebyid")
	public ResponseEntity<ResponseStructure<Equipement>> fetchById(@RequestParam int id){
		return service.fetchById(id);
	}
	@GetMapping("/fetcheall")
	public ResponseEntity<ResponseStructure<List<Equipement>>> fetchAll(){
		return service.fetchAll();
	}
	@GetMapping("/fetchebyname")
	public ResponseEntity<ResponseStructure<List<Equipement>>> fetchByName(@RequestParam String name){
		return service.fetchByName(name);
	}
	@GetMapping("/fetchebyuser")
	public ResponseEntity<ResponseStructure<List<Equipement>>> fetchByUser(@RequestParam int uid){
		return service.fetchByUser(uid);
	}
	@PutMapping("/updateequip")
	public ResponseEntity<ResponseStructure<Equipement>> updateEquipement(@RequestParam int id,@RequestParam String name,@RequestParam float costPerHour,@RequestParam int noOfItems,@RequestParam int uid){
		return service.updateEquipement(id, name, costPerHour, noOfItems, uid);
	}
	@DeleteMapping("/deleteequip")
	public ResponseEntity<ResponseStructure<String>> updateEquipement(@RequestParam int id){
		return service.deleteEquipement(id);
	}
}
