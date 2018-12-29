package org.sj.controller;

import java.util.List;

import org.sj.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class HelloController
{
	@Autowired
	HelloService hS=new HelloService();
	
	@RequestMapping("/hello")
	public String h() 
	{
		return "Hiii";
	}
	
	@RequestMapping("/testing")
	public String user(MultipartFile file) 
	{
		return hS.readFile(file);
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/multipart")
	public List<String> readMulti(@RequestParam("file") MultipartFile file) 
	{
		return hS.readMulti(file);
	}
}
