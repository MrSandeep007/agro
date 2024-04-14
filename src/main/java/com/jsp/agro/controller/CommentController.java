package com.jsp.agro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsp.agro.entity.Comment;
import com.jsp.agro.service.CommentService;
import com.jsp.agro.util.ResponseStructure;

@RestController
public class CommentController {
	@Autowired
	CommentService service;
	
	@PostMapping("/savecomment")
	public ResponseEntity<ResponseStructure<Comment>> saveComment(@RequestParam int postId, @RequestParam int userId, @RequestParam String comment){
		return service.saveComment(postId, userId, comment);
	}
	@DeleteMapping("/deletecomment")
	public ResponseEntity<ResponseStructure<String>> deleteComment(@RequestParam int id){
		return service.deleteComment(id);
	}
}
