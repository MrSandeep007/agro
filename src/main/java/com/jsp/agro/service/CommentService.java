package com.jsp.agro.service;

import java.util.ArrayList;  
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jsp.agro.DAO.CommentDAO;
import com.jsp.agro.DAO.PostDAO;
import com.jsp.agro.DAO.UserDAO;
import com.jsp.agro.entity.Comment;
import com.jsp.agro.entity.Post;
import com.jsp.agro.entity.User;
import com.jsp.agro.exception.CommentNotFoundException;
import com.jsp.agro.exception.PostNotFoundException;
import com.jsp.agro.exception.UserNotFoundException;
import com.jsp.agro.util.ResponseStructure;

@Service
public class CommentService {
	@Autowired
	CommentDAO cdao;
	@Autowired
	PostDAO pdao;
	@Autowired
	UserDAO udao;
	
	public ResponseEntity<ResponseStructure<Comment>> saveComment(int post_id, int user_id, String comment){
		ResponseStructure<Comment> rs= new ResponseStructure<Comment>();
		Post post=pdao.fetchPostById(post_id);
		if(post!=null) {
			User user=udao.findById(user_id);
			if(user!=null) {
				Comment com=new Comment();
				com.setComment(comment);
				com.setUser(user);
				cdao.saveComment(com);
				List<Comment> li=new ArrayList<Comment>();
				li.add(com);
				li.addAll(post.getComment());
				post.setComment(li);
				pdao.updatePost(post);
				rs.setMessage("Comment added successfully");
				rs.setStatus(HttpStatus.OK.value());
				rs.setData(com);
				return new ResponseEntity<ResponseStructure<Comment>>(rs,HttpStatus.OK);
			}
			else {
				throw new UserNotFoundException("User Not found with "+ user_id);
			}
		}
		else {
			throw new PostNotFoundException("Post Not found with Id"+ post_id);
		}
	}
	public ResponseEntity<ResponseStructure<String>> deleteComment(int id){
		ResponseStructure<String> rs=new ResponseStructure<String>();
		Comment comment = cdao.fetchCommentById(id);
		if(comment!=null) {
			List<Post> posts = pdao.fetchAll();
			for(Post p:posts) {
				if(p.getComment().contains(comment)) {
					p.getComment().remove(comment);
					pdao.updatePost(p);
				}
			}
			cdao.deleteComment(id);
			rs.setMessage("comment deleted successfully");
			rs.setStatus(HttpStatus.OK.value());
			rs.setData("Comment deleted successfully with id"+id);
			return new ResponseEntity<ResponseStructure<String>>(rs, HttpStatus.OK);
		}
		else {
			throw new CommentNotFoundException("Comment not found with Id:"+id);
		}
	}
	
}
