package org.sj.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FilenameUtils;
import org.sj.exception.BadRequestException;
import org.sj.exception.DataNotFoundException;
import org.sj.exception.FileNotSupportedException;
import org.sj.repository.ZipRepository;
import org.sj.service.encryption.CryptoTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ZipService 
{
	
	private static final String tempFiles="E:\\Boot\\course_API\\log\\Zip";
	private static final String tempZip="E:\\Boot\\course_API\\log\\UNZip\\temp.zip";
	private static String Key="";
	static final int BUFFER = 2048;
	
	CryptoTest crypt=new CryptoTest();
	
	@Autowired
	private ZipRepository zR;
	
	public String zip(String key,List<MultipartFile> file) 
	{
		try 
		{
			readZip(key,file);
		}
		catch(Exception e) {}
		String A=zipping();
		if(A.equals("Success")) 
		{
			deleteUploadedFile();
			return crypt.encryptCrypto(Key, tempZip);
		}
		return "Processing failed";
	}	
	
	private String readZip(String key,List<MultipartFile> file) throws IllegalStateException, IOException 
	{
		Key=key;
		if(Key=="" || file.isEmpty()) 
		{
			throw new BadRequestException("Key value and uploaded files should not be null");
		}
		for (int i = 0; i < file.size(); i++) 
		{
			String filename=file.get(i).getOriginalFilename();
			File f=new File(tempFiles+"\\"+filename);
			file.get(i).transferTo(f);
		}
		return "Success";
	}
	
	private String zipping() 
	{
		try 
        {
            BufferedInputStream origin = null;
            FileOutputStream dest = new FileOutputStream(tempZip);
            CheckedOutputStream checksum = new CheckedOutputStream(dest, new Adler32());
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(checksum));
            //out.setMethod(ZipOutputStream.DEFLATED);
            byte data[] = new byte[BUFFER];
            // get a list of files from current directory
            File f = new File(tempFiles);
            String files[] = f.list();

            for (int i = 0; i < files.length; i++) 
            {
                System.out.println("Adding: " + files[i]);
                FileInputStream fi = new FileInputStream(tempFiles+"\\"+files[i]);
                origin = new BufferedInputStream(fi, BUFFER);
                ZipEntry entry = new ZipEntry(files[i]);
                out.putNextEntry(entry);
                int count;
                while ((count = origin.read(data, 0,BUFFER)) != -1) 
                {
                    out.write(data, 0, count);
                }
                origin.close();
            }
            out.close();
            System.out.println("checksum:" + checksum.getChecksum().getValue());
            if(checksum.getChecksum().getValue()>0) 
            {
            	return "Success";
            }
            else {return "Error";}
        } 
        catch (IOException e) 
        {
            System.out.println(e);
            return e.toString();
        }
	}
	
	public String deleteUploadedFile() 
	{
		try 
		{
			File folder = new File(tempFiles);
			for (File file : folder.listFiles()) 
			{
				file.delete();
		  	}
			return "Dleted";
		}
		catch (Exception e) 
		{
			throw new DataNotFoundException("Files cannot be processed : 404 internal error");
		}
	}

	public String unZip(String k,MultipartFile decryptedFile) 
	{
		File f=null;
		//Empty or not
		if(decryptedFile.isEmpty()) 
		{
			throw new BadRequestException("Uploaded file should not be am empty file");
		}
		
		//CHECK FILE EXTENSION
		String filename=decryptedFile.getOriginalFilename();
		String ext1 = FilenameUtils.getExtension(filename);
		System.out.println(filename+" : "+ext1);
		if(!ext1.equals("encrypted")) 
		{
			throw new FileNotSupportedException("This file cannot be decrypted : "
					+ "File either damaged or it's extension has been changed");
		}
		
		//Convert MultipartFile into File(io)
		try 
		{
			f=new File(tempFiles+"\\"+filename);
			decryptedFile.transferTo(f);
		}
		catch(Exception e) 
		{
			throw new BadRequestException("Current request is not MutipartFile");
		}
		
		return crypt.decryptCrypto(k, f);
		
	}
	
}
