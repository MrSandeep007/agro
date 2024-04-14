package com.jsp.agro.service;
 
import java.util.HashMap; 
import java.util.List;
import java.util.Map;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.jsp.agro.exception.CommentNotFoundException;
import com.jsp.agro.exception.EmailNotFoundException;
import com.jsp.agro.exception.EquipementNotFound;
import com.jsp.agro.exception.ImageNotFoundException;
import com.jsp.agro.exception.IncorrectPasswordException;
import com.jsp.agro.exception.PostNotFoundException;
import com.jsp.agro.exception.UserAlreadyExistException;
import com.jsp.agro.exception.UserNotFoundException;
import com.jsp.agro.util.ResponseStructure;

@RestControllerAdvice
public class UserExceptionHandler extends ResponseEntityExceptionHandler{
	ResponseStructure<String> rs= new ResponseStructure<String>();

	@ExceptionHandler(UserAlreadyExistException.class)
	public ResponseEntity<ResponseStructure<String>> userExist(UserAlreadyExistException e) {
		rs.setMessage("User Already exist");
		rs.setStatus(HttpStatus.BAD_REQUEST.value());
		rs.setData(e.getMessage());
		return new ResponseEntity<ResponseStructure<String>>(rs,HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(EmailNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> emailNotFound(EmailNotFoundException e) {
		rs.setMessage("Email Not Found");
		rs.setStatus(HttpStatus.BAD_REQUEST.value());
		rs.setData(e.getMessage());
		return new ResponseEntity<ResponseStructure<String>>(rs,HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(IncorrectPasswordException.class)
	public ResponseEntity<ResponseStructure<String>> incorrectPassword(IncorrectPasswordException e) {
		rs.setMessage("Password Incorrect");
		rs.setStatus(HttpStatus.BAD_REQUEST.value());
		rs.setData(e.getMessage());
		return new ResponseEntity<ResponseStructure<String>>(rs,HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> invalidUser(UserNotFoundException e) {
		rs.setMessage("Invalid User");
		rs.setStatus(HttpStatus.NOT_FOUND.value());
		rs.setData(e.getMessage());
		return new ResponseEntity<ResponseStructure<String>>(rs,HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(ImageNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> invalidImage(ImageNotFoundException e) {
		rs.setMessage("Invalid Image");
		rs.setStatus(HttpStatus.NOT_FOUND.value());
		rs.setData(e.getMessage());
		return new ResponseEntity<ResponseStructure<String>>(rs,HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(PostNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> invalidPost(PostNotFoundException e) {
		rs.setMessage("Invalid Post");
		rs.setStatus(HttpStatus.NOT_FOUND.value());
		rs.setData(e.getMessage());
		return new ResponseEntity<ResponseStructure<String>>(rs,HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(CommentNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> invalidComment(CommentNotFoundException e){
		rs.setMessage("invalid Comment");
		rs.setStatus(HttpStatus.BAD_REQUEST.value());
		rs.setData(e.getMessage());
		return new ResponseEntity<ResponseStructure<String>>(rs, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(EquipementNotFound.class)
	public ResponseEntity<ResponseStructure<String>> invalidEquiepement(EquipementNotFound e){
		rs.setMessage("invalid Equipement");
		rs.setStatus(HttpStatus.BAD_REQUEST.value());
		rs.setData(e.getMessage());
		return new ResponseEntity<ResponseStructure<String>>(rs, HttpStatus.BAD_REQUEST);
	}
//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	public Map<String, String> handleValidationExceptions(
//	  MethodArgumentNotValidException ex) {
//		ResponseStructure<Map<String, String>> rsm=new ResponseStructure<Map<String,String>>();
//	    Map<String, String> errors = new HashMap<>();
//	    ex.getBindingResult().getAllErrors().forEach((error) -> {
//	        String fieldName = ((FieldError) error).getField();
//	        String errorMessage = error.getDefaultMessage();
//	        errors.put(fieldName, errorMessage);
//	    });
////	    rsm.setMessage("Invalid data");
////	    rsm.setStatus(HttpStatus.BAD_REQUEST.value());
////	    rsm.setData(errors);
////	    return new ResponseEntity<ResponseStructure<Map<String,String>>>(rsm,HttpStatus.BAD_REQUEST);
//	    return errors;
//	}
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			org.springframework.http.HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<ObjectError> error = ex.getAllErrors();
		Map<String, String> map = new HashMap<String, String>();
		ResponseStructure<Object> structure = new ResponseStructure<>();

		for (ObjectError objectError : error) {
			String filedName = ((FieldError) objectError).getField();
			String message = ((FieldError) objectError).getDefaultMessage();
			map.put(filedName, message);

		}
		structure.setMessage("provide valid details");
		structure.setStatus(HttpStatus.NOT_FOUND.value());
		structure.setData(map);

		return new ResponseEntity<Object>(structure, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ResponseStructure<Object>> handleConstraintViolationException(ConstraintViolationException ex) {
		ResponseStructure<Object> structure = new ResponseStructure();
		Map<String, String> map = new HashMap<String, String>();

		for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
			String field = violation.getPropertyPath().toString();
			String message = violation.getMessage();
			map.put(field, message);

		}

		structure.setMessage("provide proper details");
		structure.setStatus(HttpStatus.BAD_REQUEST.value());
		structure.setData(map);

		return new ResponseEntity<ResponseStructure<Object>>(structure, HttpStatus.BAD_REQUEST);

	}
}
