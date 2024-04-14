package com.jsp.agro.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.jsp.agro.DAO.EquipementDAO;
import com.jsp.agro.DAO.UserDAO;
import com.jsp.agro.entity.Equipement;
import com.jsp.agro.entity.User;
import com.jsp.agro.exception.EquipementNotFound;
import com.jsp.agro.exception.UserNotFoundException;
import com.jsp.agro.util.ResponseStructure;

@Service
public class EquipementService {
	@Autowired
	EquipementDAO edao;
	@Autowired 
	UserDAO udao;
	ResponseStructure<Equipement> rs= new ResponseStructure<Equipement>();
	ResponseStructure<List<Equipement>> rss= new ResponseStructure<List<Equipement>>();
	public ResponseEntity<ResponseStructure<Equipement>> saveEquipement(String name,float costPerHour,int noOfItems,int uid){
		User u=udao.findById(uid);
		if(u!=null) {
			Equipement e=new Equipement();
			e.setName(name);
			e.setCostPerHour(costPerHour);
			e.setNoOfItems(noOfItems);
			e.setUser(u);
			edao.saveEquipement(e);
			rs.setMessage("Equipement saved Successfully");
			rs.setStatus(HttpStatus.ACCEPTED.value());
			rs.setData(e);
			return new ResponseEntity<ResponseStructure<Equipement>>(rs,HttpStatus.ACCEPTED);
		}
		else {
			throw new UserNotFoundException("User Not found with the Id: "+uid);
		}
	}
	public ResponseEntity<ResponseStructure<Equipement>> fetchById(int id){
		Equipement e = edao.fetchById(id);
		if(e!=null) {
			rs.setMessage("Equipement Found");
			rs.setStatus(HttpStatus.FOUND.value());
			rs.setData(e);
			return new ResponseEntity<ResponseStructure<Equipement>>(rs,HttpStatus.FOUND);
		}
		else {
			throw new EquipementNotFound("Equipement Not found with id: "+ id);
		}
	}
	public ResponseEntity<ResponseStructure<List<Equipement>>> fetchAll(){
		List<Equipement> e = edao.fetchAll();
		if(e.isEmpty()) {
			throw new EquipementNotFound("No Equipements available");
		}
		else {
			rss.setMessage("Equipement Found");
			rss.setStatus(HttpStatus.FOUND.value());
			rss.setData(e);
			return new ResponseEntity<ResponseStructure<List<Equipement>>>(rss,HttpStatus.FOUND);
		}
	}
	public ResponseEntity<ResponseStructure<List<Equipement>>> fetchByName(String name){
		List<Equipement> e = edao.fetchByName(name);
		if(e.isEmpty()) {
			throw new EquipementNotFound("Equipement Not found with name:"+ name);
		}
		else {
			rss.setMessage("Equipement Found");
			rss.setStatus(HttpStatus.FOUND.value());
			rss.setData(e);
			return new ResponseEntity<ResponseStructure<List<Equipement>>>(rss,HttpStatus.FOUND);
		}
	}
	public ResponseEntity<ResponseStructure<List<Equipement>>> fetchByUser(int uid){
		User u = udao.findById(uid);
		if(u!=null) {
			List<Equipement> e = edao.fetchByUser(u);
			if(e.isEmpty()) {
				throw new EquipementNotFound("Equipement Not found with User ID"+ uid);
			}
			else {
				rss.setMessage("Equipement Found");
				rss.setStatus(HttpStatus.FOUND.value());
				rss.setData(e);
				return new ResponseEntity<ResponseStructure<List<Equipement>>>(rss,HttpStatus.FOUND);
			}
		}
		else {
			throw new UserNotFoundException("User not found with Id : "+uid);
		}
	}
	public ResponseEntity<ResponseStructure<Equipement>> updateEquipement(int id,String name,float costPerHour,int noOfItems,int uid){
		User u=udao.findById(uid);
		if(u!=null) {
			Equipement e = edao.fetchById(id);
			if(e!=null) {
				e.setId(id);
				e.setName(name);
				e.setCostPerHour(costPerHour);
				e.setNoOfItems(noOfItems);
				e.setUser(u);
				edao.updateEquipement(e);
				rs.setMessage("Equipement updated Successfully");
				rs.setStatus(HttpStatus.ACCEPTED.value());
				rs.setData(e);
				return new ResponseEntity<ResponseStructure<Equipement>>(rs,HttpStatus.ACCEPTED);
			}
			else {
				throw new EquipementNotFound("Equipement not found with id: "+id);
			}
		}
		else {
			throw new UserNotFoundException("User Not found with the Id: "+uid);
		}
	}
	public ResponseEntity<ResponseStructure<String>> deleteEquipement(int id){
		ResponseStructure<String> rsa=new ResponseStructure<String>();
		Equipement e = edao.fetchById(id);
		if(e!=null) {
			edao.deleteEquipement(id);
			rsa.setMessage("Equipement deleted Successfully");
			rsa.setStatus(HttpStatus.OK.value());
			rsa.setData("equipement with id :"+id+" is deleted successfully");
			return new ResponseEntity<ResponseStructure<String>>(rsa,HttpStatus.OK);
		}
		else {
			throw new EquipementNotFound("Equipement Not found with Id: "+id);
		}
	}
	
}
