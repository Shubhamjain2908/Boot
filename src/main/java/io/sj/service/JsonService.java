package io.sj.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.sj.entity.User;
import io.sj.repository.UserRepository;

@Service
public class JsonService 
{
	@Autowired
	private UserRepository uR;
	
	public List<String> jsonResponse(MultipartFile file) 
	{
		BufferedReader br  = null;
		try 
		{
			br=new BufferedReader(new InputStreamReader(file.getInputStream()));
			List<String> ll=br.lines().collect(Collectors.toList());
			return ll;
		}
		catch(Exception e) {return null;}
	}
	
	public User saveU() 
	{
		User u=new User();
		u.setName("Shubham");
		u.setUsername("Shubhamjain2908");
		System.out.println("DTO");
		return uR.save(u);
	}
}
