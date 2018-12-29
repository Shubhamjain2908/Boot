package io.sj.controller;

import java.io.IOException;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import io.sj.service.JsonService;

@RestController("/ed")
public class ExcelToDb 
{
	@Autowired
	private JsonService jS;
	
	
	@PostMapping(produces=MediaType.APPLICATION_JSON)
	public String ed(@RequestParam("file") MultipartFile file) throws IOException 
	{
		Gson gson = new Gson();
		 // convert your list to json
		 String jsonCartList = gson.toJson(jS.jsonResponse(file));
		return jsonCartList;
	} 
}
