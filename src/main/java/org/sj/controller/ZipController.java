package org.sj.controller;

import java.io.IOException;
import java.util.List;

import org.sj.service.ZipProtectionService;
import org.sj.service.ZipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class ZipController 
{
	@Autowired
	ZipService zS=new ZipService();
	
	@Autowired
	ZipProtectionService zP=new ZipProtectionService();
	
	@RequestMapping(method=RequestMethod.POST,value="/zip")
	public String zEncryption(@RequestParam String key,@RequestParam List<MultipartFile> file) throws IllegalStateException, IOException 
	{
		return zP.getFiles(key, file);
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/encryptzip")
	public String s(@RequestParam String key,@RequestParam List<MultipartFile> file) throws IllegalStateException, IOException 
	{
		return zS.zip(key,file);
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/decryptzip")
	public String ss(@RequestParam String key,@RequestParam MultipartFile decryptedFile) throws IllegalStateException, IOException 
	{
		return zS.unZip(key, decryptedFile);
	}
}
