package org.sj.controller;

import org.sj.entity.Log;
import org.sj.service.FileHandlerService;
import org.sj.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class LoggerController 
{	
		
	@Autowired
	LogService lS;

    @RequestMapping(method=RequestMethod.POST,value="/logger")
    public Log handleFileUpload(@RequestParam("file") MultipartFile file) 
    {
    	String grs="";
    	if (!file.isEmpty()) 
    	{
    		FileHandlerService f=new FileHandlerService();
    		grs=f.getFile(file);
    	}
    	
    	if(grs.equals("Success")) 
    	{
    		return lS.generateAll();
    	}
    	else 
    	{
    		return null;
    	}
    }

}
