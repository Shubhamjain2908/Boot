package org.sj.service.utility;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

public class DeleteLogger 
{
	
	public String deleteLog(String type)
    {
        try
        {
            Files.deleteIfExists(Paths.get("E:\\Boot\\course_API\\log\\"+type+".log"));
        }
        catch(NoSuchFileException e)
        {
            System.out.println("No such file/directory exists");
            return e.toString();
        }
        catch(DirectoryNotEmptyException e)
        {
            System.out.println("Directory is not empty.");
            return e.toString();
        }
        catch(IOException e)
        {
            System.out.println("Invalid permissions.");
            return e.toString();
        }
        return "Deletion successful.";
    }
	
	
	public String deleteTempLog()
    {
        try 
        {
			File file = new File("E:\\Boot\\course_API\\log\\temp.log");
			 
			if(file.delete())
			{
			    return "File deleted successfully";
			}
			else
			{
			    return "Failed to delete the file";
			}
		} 
        catch (Exception e) 
        {
			return e.toString();
		}
    }
}
