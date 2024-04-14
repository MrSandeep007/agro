package com.jsp.agro.DAO;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jsp.agro.entity.Comment;
import com.jsp.agro.entity.Image;
import com.jsp.agro.entity.Post;
import com.jsp.agro.repo.PostRepo;

@Repository
public class PostDAO {
	@Autowired
	PostRepo repo;
	public Post savePost(Post post){
		return repo.save(post);
	}
	public Post fetchPostById(int id) {
		Optional<Post> post = repo.findById(id);
		if(post.isEmpty()) {
			return null;
		}
		return post.get();
	}
	public Post updatePost(Post post){
		Post p= repo.findById(post.getId()).get();
		if(post.getId()!=0){
			p.setId(post.getId());
		}
		if(post.getComment()!=null) {
			p.setComment(post.getComment());
		}
		if(post.getDate()!=null) {
			p.setDate(post.getDate());
		}
		if(post.getCaption()!=null) {
			p.setCaption(post.getCaption());
		}
		if(post.getImage()!=null) {
			p.setImage(post.getImage());
		}
		if(post.getLocation()!=null) {
			p.setLocation(post.getLocation());
		}
		return repo.save(p);
	}
	public void deletePost(int id){
		repo.deleteById(id);
	}
	public List<Post> fetchAll(){
		return repo.findAll();
	}
}
