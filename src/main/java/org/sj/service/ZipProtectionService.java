package org.sj.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.sj.exception.BadRequestException;
import org.sj.exception.DataNotFoundException;
import org.sj.exception.FileNotSupportedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
 
@Service
public class ZipProtectionService 
{
	
	private static final String tempFiles="E:\\Boot\\course_API\\log\\Zip";
	private static final String tempZip="E:\\Boot\\course_API\\log\\UNZip\\temp.zip";
	
	public String getFiles(String key,List<MultipartFile> file) 
	{
		ArrayList<File> addedFiles=saveZipFile(file);
		if(!addedFiles.isEmpty())
		{
			String res=createProtectedZip(key,addedFiles);
			if(!res.isEmpty())
			{
				deleteTempFiles();
			}
			return res;
		}
		else 
		{
			throw new FileNotSupportedException("This file cannot be decrypted");
		}
	}
	
	private ArrayList<File> saveZipFile(List<MultipartFile> file) 
	{
		try 
		{
			ArrayList<File> filesToAdd = new ArrayList<>();
			
			for (int i = 0; i < file.size(); i++) 
			{
				String filename=file.get(i).getOriginalFilename();
				File f=new File(tempFiles+"\\"+filename);
				file.get(i).transferTo(f);
				filesToAdd.add(f);	//files added
			}
			
			return filesToAdd;
		}
		catch(Exception e) 
		{
			throw new BadRequestException("Files cannot be saved");
		}
	}
	
	private String createProtectedZip(String key,ArrayList<File> addedFiles) 
	{
		for (int i = 0; i < addedFiles.size(); i++) 
		{
			System.out.println(addedFiles.get(i));
		}
		try 
        {
            ZipFile zipFile = new ZipFile(tempZip);
            ZipParameters parameters = new ZipParameters();
            
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE); // set compression method to deflate compression
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
            parameters.setEncryptFiles(true);
            parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
            parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
            parameters.setPassword(key);
             
            zipFile.addFiles(addedFiles, parameters);
        }
        catch (ZipException e)
        {
            e.printStackTrace();
            return e.toString();
        }
		return "Successfully Created";
	}
	
	public void deleteTempFiles() 
	{
		try 
		{
			File folder = new File(tempFiles);
			for (File file : folder.listFiles()) 
			{
				file.delete();
		  	}
			//Files.deleteIfExists(Paths.get(tempZip));
		}
		catch (Exception e) 
		{
			throw new DataNotFoundException("Files cannot be processed : 404 internal error");
		}
	}
}
