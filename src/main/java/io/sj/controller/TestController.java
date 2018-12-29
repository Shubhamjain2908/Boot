package io.sj.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import io.sj.entity.User;
import io.sj.exception.APIErrorCodes;
import io.sj.exception.ApplicationException;
import io.sj.service.JsonService;
import io.sj.utils.JWTTokenGenerator;
import io.sj.utils.JWTVerify;

@RestController
@RequestMapping("/multipart")
public class TestController 
{
	@Autowired
	JsonService j;
	
	@GetMapping
	public String g(HttpServletRequest request) 
	{
		throw ApplicationException.createBadRequest(APIErrorCodes.REQUEST_PARAM_INVALID,"Shubham","Jain");
		//return request.getUserPrincipal()+ " : "+request.getContextPath() +" : "+request.getPathInfo()+" : "+request.getRemoteAddr()+" : "+request.getRemoteHost()+" : "+request.getRemotePort()+" : "+request.getRemoteUser()+" : "+request.getAuthType()+" : "+request.getRequestURI()+" : "+request.getRequestURL();
	} 
	
	@PostMapping(value="/audit")
	public User au() 
	{
		System.out.println("Audit Controller");
		return j.saveU();
	}
	
	@PostMapping(value="/v",produces=MediaType.APPLICATION_JSON)
	public String readMap(@RequestParam Map<String,String> allRequestParams) 
	{
		if(allRequestParams.keySet().isEmpty()) 
		{
			return "No key";
		}
		if(allRequestParams.containsKey("s") && !allRequestParams.get("s").isEmpty() || allRequestParams.containsKey("d") && !allRequestParams.get("d").isEmpty()) 
		{
			return "SSSSSSS";
		}
		return "Key";
	}
	
	@PostMapping("/json")
	public List<String> re(@RequestParam("file") MultipartFile file) 
	{
		return j.jsonResponse(file);
	}
	
	@PostMapping(value="/map",produces=MediaType.APPLICATION_JSON)
	public Map<Integer, String> mp() 
	{
		Map<Integer, String> m1=new HashMap<Integer, String>();
		m1.put(1,"sunday");
		m1.put(2,"monday");
		m1.put(3,"tuesday");
		m1.put(4,"wednesday");
		m1.put(5,"thursday");
		m1.put(6,"friday");
		m1.put(7,"saturday");
		m1.put(8,"sunday"); 	//Duplication
		return m1;
	}
	
	@PostMapping("/jwt")
	public String s() 
	{
		JWTTokenGenerator j = new JWTTokenGenerator();
		return j.createJWT("1", "SJ", "TT", 100000);
	}
	
	@PostMapping("/jwt/verify")
	public String v() 
	{
		JWTVerify jv = new JWTVerify();
		JWTTokenGenerator j = new JWTTokenGenerator();
		System.err.println(j.createJWT("1", "SJ", "TT", 100000));
		jv.parseJWT(j.createJWT("1", "SJ", "TT", 100000));
		return j.createJWT("1", "SJ", "TT", 100000);
	}
}
