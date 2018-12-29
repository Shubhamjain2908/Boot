package org.sj.controller;

import javax.ws.rs.core.MediaType;

import org.sj.service.CsvJsonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class CsvJsonController 
{
	@Autowired
	CsvJsonService csv=new CsvJsonService();
	
	 @RequestMapping(method=RequestMethod.POST,value="/csv",produces=MediaType.APPLICATION_JSON)
	 public String handleCSVUpload(@RequestParam("file") MultipartFile file)
	 {
		 return csv.processCSV(file);
	 }
}
