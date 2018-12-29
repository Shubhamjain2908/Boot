package org.sj.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.sj.exception.BadRequestException;
import org.sj.exception.StorageException;
import org.springframework.web.multipart.MultipartFile;

public class FileHandlerService 
{
	public String getFile(MultipartFile file) 
	{
		String filename=file.getOriginalFilename();
		String ext1 = FilenameUtils.getExtension(filename);
		System.out.println(filename+" : "+ext1);
		if(!ext1.equals("log")) 
		{
			throw new BadRequestException("Only log file allowed");
		}
		else 
		{
			String read=readFile(file);
			return read;
		}
	}
	
	public String readFile(MultipartFile file) 
	{
		try 
        {
            byte[] bytes = file.getBytes();
            String completeData = new String(bytes);
            String save=saveFile(completeData);
            //String[] rows = completeData.split("#");
            //String[] columns = rows[0].split(",");
            if(save.equals("Success")) 
            {
              	return "Success";
            }
            else 
            {
            	return save;
            }
        }
        catch(Exception e)
		{
        	throw new StorageException("File cannot be readable");
        }
	}
	
	public String saveFile(String fileData) 
	{
		try 
	    {	
			BufferedWriter output = null;
			File file = new File("E:\\Boot\\course_API\\log\\temp.log");
			output = new BufferedWriter(new FileWriter(file));
			output.write(fileData);
			if ( output != null ) 
			{
				output.close();
			}
			return "Success";
		} 
		catch(IOException e) 
		{
			System.out.println(e);
			return e.toString();
		}
	}
}
