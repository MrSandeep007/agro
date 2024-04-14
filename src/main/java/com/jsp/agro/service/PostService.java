package com.jsp.agro.service;

import java.io.IOException; 
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jsp.agro.DAO.ImageDAO;
import com.jsp.agro.DAO.PostDAO;
import com.jsp.agro.DAO.UserDAO;
import com.jsp.agro.entity.Image;
import com.jsp.agro.entity.Post;
import com.jsp.agro.entity.User;
import com.jsp.agro.exception.PostNotFoundException;
import com.jsp.agro.exception.UserNotFoundException;
import com.jsp.agro.util.ResponseStructure;

@Service
public class PostService {
	@Autowired
	PostDAO pdao;
	@Autowired
	UserDAO udao;
	@Autowired
	ImageDAO idao;
	
	ResponseStructure<Post> rs= new ResponseStructure<Post>();
	public ResponseEntity<ResponseStructure<Post>> savePost(int userId, MultipartFile file, String caption, String location) throws IOException{
		User user=udao.findById(userId);
		if(user!=null) {
			Image image= new Image();
			image.setName(file.getOriginalFilename());
			image.setData(file.getBytes());
			Post post=new Post();
			post.setImage(image);
			post.setCaption(caption);
			post.setLocation(location);
			post.setDate(LocalDateTime.now());
			pdao.savePost(post);
			List<Post> p=new ArrayList<Post>();
			p.add(post);
			p.addAll(user.getPost());
			user.setPost(p);
			udao.updateUser(user);
			rs.setMessage("Post uplaoded successfully");
			rs.setStatus(HttpStatus.ACCEPTED.value());
			rs.setData(post);
			return new ResponseEntity<ResponseStructure<Post>>(rs,HttpStatus.ACCEPTED);
		}
		else {
			throw new UserNotFoundException("User not found with id: "+ userId);
		}
	}
	public ResponseEntity<ResponseStructure<Post>> fetchPostById(int id){
		Post post=pdao.fetchPostById(id);
		if(post!=null) {
			rs.setMessage("Post Fetched successfully");
			rs.setStatus(HttpStatus.FOUND.value());
			rs.setData(post);
			return new ResponseEntity<ResponseStructure<Post>>(rs,HttpStatus.FOUND);
		}
		else {
			throw new PostNotFoundException("Post not found with Id: "+id);
		}
	}
	public ResponseEntity<ResponseStructure<String>> deletePostById(int id) {
		ResponseStructure<String> rss= new ResponseStructure<String>();
		Post p=pdao.fetchPostById(id);
		if(p!=null) {
			List<User> user = udao.findAll();
			for(User u:user) {
				if(u.getPost().contains(p)) {
					u.getPost().remove(p);
					udao.updateUser(u);
				}
				
			}
			Image i=p.getImage();
			p.setImage(null);
			idao.deleteImage(i);
			pdao.deletePost(id);
			rss.setMessage("Post Deleted Successfully");
			rss.setStatus(HttpStatus.OK.value());
			rss.setData("Post deleted with the id : "+id);
			return new ResponseEntity<ResponseStructure<String>>(rss, HttpStatus.OK);
		}
		else {
			throw new PostNotFoundException("Post not found with Id : "+id);
		}
	}
	public ResponseEntity<ResponseStructure<Post>> updatePost(Post post){
		pdao.updatePost(post);
		rs.setMessage("Post updated successfully");
		rs.setStatus(HttpStatus.ACCEPTED.value());
		rs.setData(post);
		return new ResponseEntity<ResponseStructure<Post>>(rs,HttpStatus.ACCEPTED);
	}
	
}
