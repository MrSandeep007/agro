package com.jsp.agro.DAO;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jsp.agro.entity.Comment;
import com.jsp.agro.repo.CommentRepo;

@Repository
public class CommentDAO {
	@Autowired
	CommentRepo repo;
	public Comment saveComment(Comment comment) {
		return repo.save(comment);	
	}
	public void deleteComment(int id){
		repo.deleteById(id);
	}
	public Comment fetchCommentById(int id) {
		Optional<Comment> comment = repo.findById(id);
		if(comment.isPresent()) {
			return comment.get();
		}
		return null;
	}
}
