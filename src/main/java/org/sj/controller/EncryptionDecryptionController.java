package org.sj.controller;


import org.sj.service.UserService;
import org.sj.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EncryptionDecryptionController 
{
	@Autowired
	UserService uS=new UserService();
	
	@RequestMapping(method=RequestMethod.POST,value="/encrypt")
	public ResponseEntity<User> e(@RequestBody User user) throws Exception 
	{
		User savedUser = uS.saveUser(user);
		return new ResponseEntity<User>(savedUser, HttpStatus.CREATED);
	}
	
}
